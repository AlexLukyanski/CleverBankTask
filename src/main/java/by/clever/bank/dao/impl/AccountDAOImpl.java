package by.clever.bank.dao.impl;

import by.clever.bank.bean.Account;
import by.clever.bank.bean.Bank;
import by.clever.bank.dao.AccountDAO;
import by.clever.bank.dao.BankDAO;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.connectionpool.ConnectionPool;
import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.dao.impl.constant.DBColumnAccountName;
import by.clever.bank.dao.impl.constant.DBColumnBankName;


import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    private final static String SELECT_BALANCE_FROM_ACCOUNT_SQL = "SELECT a_balance FROM account WHERE a_number=?";

    @Override
    public BigDecimal selectBalance(Connection connection, String accountNumber) throws DAOException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BALANCE_FROM_ACCOUNT_SQL)) {
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBigDecimal(DBColumnAccountName.BALANCE_COLUMN);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private final static String SELECT_ID_FROM_ACCOUNT_SQL = "SELECT a_id FROM account WHERE a_number=?";

    @Override
    public int selectAccountID(Connection connection, String accountNumber) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_FROM_ACCOUNT_SQL)) {
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(DBColumnAccountName.ID_COLUMN);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private final static String PUT_MONEY_TO_ACCOUNT_SQL = "UPDATE account SET a_balance=? WHERE a_number=?";

    public void changeBalance(Connection connection, BigDecimal newBalance, String accountNumber) throws DAOException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(PUT_MONEY_TO_ACCOUNT_SQL)) {
            preparedStatement.setBigDecimal(1, newBalance);
            preparedStatement.setString(2, accountNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private final static String SELECT_ALL_ACCOUNTS_SQL = "SELECT * from account";

    @Override
    public List<Account> selectIdAndNumberAndBalanceFromAllAccounts(Connection connection) throws DAOException {

        try (Statement statement = connection.createStatement()) {

            List<Account> accounts = new ArrayList<>();
            ResultSet result = statement.executeQuery(SELECT_ALL_ACCOUNTS_SQL);

            while (result.next()) {
                Account account = new Account();

                account.setId(result.getInt(DBColumnAccountName.ID_COLUMN));
                account.setNumber(result.getString(DBColumnAccountName.NUMBER_COLUMN));
                account.setBalance(result.getBigDecimal(DBColumnAccountName.BALANCE_COLUMN));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private final static String SELECT_ACCOUNT_BANK_NAME_SQL = "SELECT b_name from account JOIN bank ON " +
            "(SELECT a_bank from account WHERE a_id=?)=b_id";

    @Override
    public String takeBankName(Connection connection, int accountID) throws DAOException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BANK_NAME_SQL)) {
            preparedStatement.setInt(1, accountID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString(DBColumnBankName.NAME_COLUMN);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private final static String INSERT_NEW_ACCOUNT_SQL = "INSERT INTO account " +
            "(a_number,a_balance,a_bank,a_user) VALUES (?,?,?,?)";

    @Override
    public boolean createAccount(Account account, String bankName, int userID) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ACCOUNT_SQL)) {

            BankDAO bankDAO = DAOFactory.getInstance().getBankDAO();
            Bank bank = bankDAO.readBank(bankName);

            preparedStatement.setString(1, account.getNumber());
            preparedStatement.setBigDecimal(2, account.getBalance());
            preparedStatement.setInt(3, bank.getId());
            preparedStatement.setInt(4, userID);

            int insertionResult = preparedStatement.executeUpdate();

            if (insertionResult != 0) {
                return true;
            }
            return false;

        } catch (SQLException | ConnectionPoolException e) {
            System.out.println("sbesrhserhsrth");
            throw new DAOException(e);
        }
    }

    private final static String UPDATE_ACCOUNT_SQL = "UPDATE account SET" +
            " a_number=?,a_balance=?" +
            "WHERE a_id=(SELECT a_id FROM account WHERE a_number=?)";

    @Override
    public boolean updateAccount(Account oldAccount, Account newAccount) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_SQL)) {

            preparedStatement.setString(1, newAccount.getNumber());
            preparedStatement.setBigDecimal(2, newAccount.getBalance());
            preparedStatement.setString(3, oldAccount.getNumber());

            int insertionResult = preparedStatement.executeUpdate();

            if (insertionResult != 0) {
                return true;
            }
            return false;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String SELECT_ACCOUNT_SQL = "SELECT" +
            "a_id,a_number,a_balance,b_id,b_name " +
            "FROM users JOIN bank ON a_bank=b_id WHERE a_id=?";

    @Override
    public Account readAccount(int accountID) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_SQL)) {

            preparedStatement.setInt(1, accountID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Bank bank = setBank(resultSet);
            return setAccount(resultSet, bank);

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String DELETE_ACCOUNT_SQL = "DELETE FROM account WHERE ui_id=?";

    @Override
    public boolean deleteAccount(int accountID) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACCOUNT_SQL)) {

            preparedStatement.setInt(1, accountID);

            int deleteResult = preparedStatement.executeUpdate();

            if (deleteResult != 0) {
                return true;
            }
            return false;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private Bank setBank(ResultSet resultSet) throws SQLException {
        Bank bank = new Bank();
        bank.setId(resultSet.getInt(DBColumnBankName.ID_COLUMN));
        bank.setName(resultSet.getString(DBColumnBankName.NAME_COLUMN));
        return bank;
    }

    private Account setAccount(ResultSet resultSet, Bank bank) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt(DBColumnAccountName.ID_COLUMN));
        account.setNumber(resultSet.getString(DBColumnAccountName.NUMBER_COLUMN));
        account.setBalance(resultSet.getBigDecimal(DBColumnAccountName.BALANCE_COLUMN));
        account.setBank(bank);
        return account;
    }
}

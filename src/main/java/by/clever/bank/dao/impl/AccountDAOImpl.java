package by.clever.bank.dao.impl;

import by.clever.bank.bean.Account;
import by.clever.bank.dao.AccountDAO;
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
}

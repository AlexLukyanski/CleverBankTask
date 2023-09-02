package by.clever.bank.dao.impl;

import by.clever.bank.bean.Bank;
import by.clever.bank.dao.BankDAO;
import by.clever.bank.dao.connectionpool.ConnectionPool;
import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.dao.impl.constant.DBColumnBankName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDAOImpl implements BankDAO {

    private final static String INSERT_NEW_BANK_SQL = "INSERT INTO bank (b_name) VALUES (?)";

    @Override
    public boolean createBank(Bank bank) throws DAOException {

        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_BANK_SQL)) {

            preparedStatement.setString(1, bank.getName());
            int insertionResult = preparedStatement.executeUpdate();

            if (insertionResult != 0) {
                return true;
            }
            return false;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String UPDATE_BANK_SQL = "UPDATE bank SET b_name=? WHERE b_id=(SELECT b_id FROM bank WHERE b_name=?)";

    @Override
    public boolean updateBank(Bank oldBank, Bank newBank) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BANK_SQL)) {

            preparedStatement.setString(1, newBank.getName());
            preparedStatement.setString(2, oldBank.getName());
            int insertionResult = preparedStatement.executeUpdate();

            if (insertionResult != 0) {
                return true;
            }
            return false;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String SELECT_BANK_SQL = "SELECT b_id,b_name FROM bank WHERE b_name=?";

    @Override
    public Bank readBank(String bankName) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BANK_SQL)) {

            preparedStatement.setString(1, bankName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createBank(resultSet);
            }
            throw new DAOException("There is no bank with that name");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String DELETE_BANK_SQL = "DELETE FROM bank WHERE b_name=?";

    @Override
    public boolean deleteBank(String bankName) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BANK_SQL)) {

            preparedStatement.setString(1, bankName);

            int deleteResult = preparedStatement.executeUpdate();

            if (deleteResult != 0) {
                return true;
            }
            return false;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private Bank createBank(ResultSet resultSet) throws SQLException {
        Bank bank = new Bank();
        bank.setId(resultSet.getInt(DBColumnBankName.ID_COLUMN));
        bank.setName(resultSet.getString(DBColumnBankName.NAME_COLUMN));
        return bank;
    }
}

package by.clever.bank.dao.impl;

import by.clever.bank.dao.AccountDAO;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.dao.impl.constant.DBColumnName;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {

    private final static String SELECT_BALANCE_FROM_ACCOUNT_SQL = "SELECT a_balance FROM account WHERE a_number=?";

    public BigDecimal selectBalance(Connection connection, String accountNumber) throws DAOException {

        try (PreparedStatement preparedStatementOne = connection.prepareStatement(SELECT_BALANCE_FROM_ACCOUNT_SQL)) {
            preparedStatementOne.setString(1, accountNumber);
            ResultSet resultSet = preparedStatementOne.executeQuery();
            resultSet.next();
            return resultSet.getBigDecimal(DBColumnName.ACCOUNT_BALANCE_COLUMN);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private final static String PUT_MONEY_TO_ACCOUNT_SQL = "UPDATE account SET a_balance=? WHERE a_number=?";

    public void addMoneyToBalance(Connection connection, BigDecimal newBalance, String accountNumber) throws DAOException {

        try (PreparedStatement preparedStatementTwo = connection.prepareStatement(PUT_MONEY_TO_ACCOUNT_SQL)) {
            preparedStatementTwo.setBigDecimal(1, newBalance);
            preparedStatementTwo.setString(2, accountNumber);
            preparedStatementTwo.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}

package by.clever.bank.dao.impl;

import by.clever.bank.dao.AccountDAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {

    private final static String SELECT_BALANCE_FROM_ACCOUNT_SQL = "SELECT a_balance FROM account WHERE a_number=?";
    private final static String PUT_MONEY_TO_ACCOUNT_SQL = "UPDATE account SET a_balance=? WHERE a_number=?";

    @Override
    public boolean putMoneyToAccount(BigDecimal amount, String accountNumber, Connection connection) throws SQLException {

        try (PreparedStatement preparedStatementOne = connection.prepareStatement(SELECT_BALANCE_FROM_ACCOUNT_SQL);
             PreparedStatement preparedStatementTwo = connection.prepareStatement(PUT_MONEY_TO_ACCOUNT_SQL)) {

            connection.setAutoCommit(false);
            BigDecimal balance = selectBalance(preparedStatementOne, accountNumber);
            BigDecimal newBalance = balance.add(amount);
            updateBalance(preparedStatementTwo, newBalance, accountNumber);
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            connection.rollback();
        }
        return false;
    }

    private BigDecimal selectBalance(PreparedStatement preparedStatementOne, String accountNumber) throws SQLException {

        preparedStatementOne.setString(1, accountNumber);
        ResultSet resultSet = preparedStatementOne.executeQuery();
        BigDecimal balance = resultSet.getBigDecimal(1);

        return balance;
    }

    private void updateBalance(PreparedStatement preparedStatementTwo, BigDecimal newBalance, String accountNumber) throws SQLException {
        preparedStatementTwo.setBigDecimal(1, newBalance);
        preparedStatementTwo.setString(2, accountNumber);
        preparedStatementTwo.executeQuery();
    }
}

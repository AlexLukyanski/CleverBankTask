package by.clever.bank.transactionmanager.impl;

import by.clever.bank.dao.AccountDAO;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.connectionpool.ConnectionPool;
import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.transactionmanager.AccountTransactionManager;
import by.clever.bank.transactionmanager.exception.TransactionManagerException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class AccountTransactionManagerImpl implements AccountTransactionManager {

    private final static AccountDAO accountDAO = DAOFactory.getInstance().getAccountDAO();

    @Override
    public boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws SQLException, ConnectionPoolException {

        Connection connection = ConnectionPool.getInstance().takeConnection();
        BigDecimal balance;
        BigDecimal newBalance;

        try {
            connection.setAutoCommit(false);
            balance = accountDAO.selectBalance(connection, accountNumber);
            newBalance = balance.add(amount);
            accountDAO.addMoneyToBalance(connection, newBalance, accountNumber);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (DAOException | SQLException e) {
            connection.rollback();
            return false;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}

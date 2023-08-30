package by.clever.bank.transactionmanager.impl;

import by.clever.bank.bean.Account;
import by.clever.bank.bean.constant.TransactionType;
import by.clever.bank.dao.AccountDAO;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.TransactionDAO;
import by.clever.bank.dao.connectionpool.ConnectionPool;
import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.transactionmanager.AccountTransactionManager;
import by.clever.bank.transactionmanager.exception.TransactionManagerException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccountTransactionManagerImpl implements AccountTransactionManager {

    private final static AccountDAO accountDAO = DAOFactory.getInstance().getAccountDAO();
    private final static TransactionDAO transactionDAO = DAOFactory.getInstance().getTransactionDAO();

    @Override
    public boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws TransactionManagerException {
        try {
            return putMoneyTransaction(amount, accountNumber);
        } catch (ConnectionPoolException | SQLException e) {
            throw new TransactionManagerException(e);
        }
    }

    @Override
    public boolean withdrawMoneyFromAccount(BigDecimal amount, String accountNumber) throws TransactionManagerException {
        try {
            return withdrawMoneyTransaction(amount, accountNumber);
        } catch (ConnectionPoolException | SQLException e) {
            throw new TransactionManagerException(e);
        }
    }

    @Override
    public boolean transferMoneyBetweenAccounts(BigDecimal amount, String senderAccountNumber, String receiverAccountNumber) throws TransactionManagerException {
        try {
            return transferBetweenAccountsTransaction(amount, senderAccountNumber, receiverAccountNumber);
        } catch (ConnectionPoolException | SQLException e) {
            throw new TransactionManagerException(e);
        }
    }

    @Override
    public void chargeAccrual(BigDecimal percentage) throws TransactionManagerException {
        try {
            chargeAccrualTransaction(percentage);
        } catch (ConnectionPoolException | SQLException e) {
            throw new TransactionManagerException(e);
        }
    }

    private boolean putMoneyTransaction(BigDecimal amount, String accountNumber) throws SQLException, ConnectionPoolException, TransactionManagerException {

        Connection connection = ConnectionPool.getInstance().takeConnection();
        BigDecimal balance;
        BigDecimal newBalance;
        int accountID;

        try {
            connection.setAutoCommit(false);
            balance = accountDAO.selectBalance(connection, accountNumber);
            newBalance = balance.add(amount);
            accountDAO.changeBalance(connection, newBalance, accountNumber);
            accountID = accountDAO.selectAccountID(connection, accountNumber);
            transactionDAO.saveTransactionData(connection, amount, TransactionType.DEPOSIT, accountID);
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

    private boolean withdrawMoneyTransaction(BigDecimal amount, String accountNumber) throws SQLException, ConnectionPoolException, TransactionManagerException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        BigDecimal balance;
        BigDecimal newBalance;
        int accountID;

        try {
            connection.setAutoCommit(false);
            balance = accountDAO.selectBalance(connection, accountNumber);
            newBalance = calculateBalanceAfterWithdraw(amount, balance);
            accountDAO.changeBalance(connection, newBalance, accountNumber);
            accountID = accountDAO.selectAccountID(connection, accountNumber);
            transactionDAO.saveTransactionData(connection, amount, TransactionType.WITHDRAWAL, accountID);
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

    private boolean transferBetweenAccountsTransaction(BigDecimal amount, String senderAccountNumber, String receiverAccountNumber) throws SQLException, TransactionManagerException, ConnectionPoolException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        BigDecimal senderBalance;
        BigDecimal newSenderBalance;
        BigDecimal receiverBalance;
        BigDecimal newReceiverBalance;
        int senderAccountID;
        int receiverAccountID;

        try {
            connection.setAutoCommit(false);
            senderBalance = accountDAO.selectBalance(connection, senderAccountNumber);
            newSenderBalance = calculateBalanceAfterWithdraw(amount, senderBalance);
            accountDAO.changeBalance(connection, newSenderBalance, senderAccountNumber);
            receiverBalance = accountDAO.selectBalance(connection, receiverAccountNumber);
            newReceiverBalance = receiverBalance.add(amount);
            accountDAO.changeBalance(connection, newReceiverBalance, receiverAccountNumber);
            senderAccountID = accountDAO.selectAccountID(connection, senderAccountNumber);
            receiverAccountID = accountDAO.selectAccountID(connection, receiverAccountNumber);
            transactionDAO.saveTransactionData(connection, amount, TransactionType.TRANSFER_BETWEEN_ACCOUNTS, senderAccountID);
            transactionDAO.saveTransactionData(connection, amount, TransactionType.TRANSFER_BETWEEN_ACCOUNTS, receiverAccountID);
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

    private void chargeAccrualTransaction(BigDecimal percentage) throws SQLException, ConnectionPoolException {

        Connection connection = ConnectionPool.getInstance().takeConnection();
        List<Account> accounts;

        try {
            connection.setAutoCommit(false);
            accounts = accountDAO.selectIdAndNumberAndBalanceFromAllAccounts(connection);
            updateAllBalancesInAccounts(connection, accounts, percentage);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (DAOException | SQLException e) {
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private BigDecimal calculateBalanceAfterWithdraw(BigDecimal amount, BigDecimal balance) throws TransactionManagerException {

        int subtractionResult = balance.subtract(amount).intValue();

        if (subtractionResult >= 0) {
            return new BigDecimal(subtractionResult);
        } else {
            throw new TransactionManagerException("Insufficient funds");
        }
    }

    private void updateAllBalancesInAccounts(Connection connection, List<Account> accounts, BigDecimal percentage) throws DAOException {

        BigDecimal numberFromPercentage = percentage
                .setScale(4, RoundingMode.CEILING)
                .divide(BigDecimal.valueOf(100));

        for (Account account :
                accounts) {

            BigDecimal newBalance = account.getBalance()
                    .setScale(2, RoundingMode.CEILING)
                    .multiply(numberFromPercentage);

            accountDAO.changeBalance(connection, newBalance, account.getNumber());
        }
    }
}

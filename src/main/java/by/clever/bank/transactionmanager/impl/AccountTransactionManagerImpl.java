package by.clever.bank.transactionmanager.impl;

import by.clever.bank.bean.Account;
import by.clever.bank.bean.constant.TransactionType;
import by.clever.bank.dao.AccountDAO;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.TransactionDAO;
import by.clever.bank.dao.connectionpool.ConnectionPool;
import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.dto.Receipt;
import by.clever.bank.transactionmanager.AccountTransactionManager;
import by.clever.bank.transactionmanager.exception.TransactionManagerException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        Timestamp dateTime;
        int transactionID;
        String bankName;
        Receipt receipt;

        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            balance = accountDAO.selectBalance(connection, accountNumber);
            newBalance = balance.add(amount);
            accountDAO.changeBalance(connection, newBalance, accountNumber);
            accountID = accountDAO.selectAccountID(connection, accountNumber);
            dateTime = Timestamp.valueOf(LocalDateTime.now());
            transactionID = transactionDAO.selectTransactionID(connection, amount, TransactionType.DEPOSIT, accountID, dateTime);
            bankName = accountDAO.takeBankName(connection, accountID);
            receipt = createDepositOrWithrawReceipt(transactionID, dateTime, TransactionType.DEPOSIT, bankName, accountNumber, amount);
            transactionDAO.saveTransationToTXT(receipt);
            transactionDAO.saveTransactionData(connection, amount, TransactionType.DEPOSIT, accountID, dateTime);
            connection.commit();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
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
        Timestamp dateTime;
        int transactionID;
        String bankName;
        Receipt receipt;


        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            balance = accountDAO.selectBalance(connection, accountNumber);
            newBalance = calculateBalanceAfterWithdraw(amount, balance);
            accountDAO.changeBalance(connection, newBalance, accountNumber);
            accountID = accountDAO.selectAccountID(connection, accountNumber);
            dateTime = Timestamp.valueOf(LocalDateTime.now());
            transactionDAO.saveTransactionData(connection, amount, TransactionType.WITHDRAWAL, accountID, dateTime);
            transactionID = transactionDAO.selectTransactionID(connection, amount, TransactionType.WITHDRAWAL, accountID, dateTime);
            bankName = accountDAO.takeBankName(connection, accountID);
            receipt = createDepositOrWithrawReceipt(transactionID, dateTime, TransactionType.WITHDRAWAL, bankName, accountNumber, amount);
            transactionDAO.saveTransationToTXT(receipt);
            connection.commit();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
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
        Timestamp dateTime;
        int senderTransactionID;
        int receiverTransactionID;
        String senderBankName;
        String receiverBankName;
        Receipt senderReceipt;
        Receipt receiverReceipt;

        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            senderBalance = accountDAO.selectBalance(connection, senderAccountNumber);
            newSenderBalance = calculateBalanceAfterWithdraw(amount, senderBalance);
            accountDAO.changeBalance(connection, newSenderBalance, senderAccountNumber);
            receiverBalance = accountDAO.selectBalance(connection, receiverAccountNumber);
            newReceiverBalance = receiverBalance.add(amount);
            accountDAO.changeBalance(connection, newReceiverBalance, receiverAccountNumber);
            senderAccountID = accountDAO.selectAccountID(connection, senderAccountNumber);
            receiverAccountID = accountDAO.selectAccountID(connection, receiverAccountNumber);
            dateTime = Timestamp.valueOf(LocalDateTime.now());
            transactionDAO.saveTransactionData(connection, amount, TransactionType.TRANSFER_BETWEEN_ACCOUNTS, senderAccountID, dateTime);
            transactionDAO.saveTransactionData(connection, amount, TransactionType.TRANSFER_BETWEEN_ACCOUNTS, receiverAccountID, dateTime);
            senderTransactionID = transactionDAO.selectTransactionID(connection, amount, TransactionType.TRANSFER_BETWEEN_ACCOUNTS, senderAccountID, dateTime);
            receiverTransactionID = transactionDAO.selectTransactionID(connection, amount, TransactionType.TRANSFER_BETWEEN_ACCOUNTS, receiverAccountID, dateTime);
            senderBankName = accountDAO.takeBankName(connection, senderAccountID);
            receiverBankName = accountDAO.takeBankName(connection, receiverAccountID);
            senderReceipt = createTransferReceipt(senderTransactionID, dateTime,
                    TransactionType.TRANSFER_BETWEEN_ACCOUNTS, senderBankName, receiverBankName,
                    senderAccountNumber, receiverAccountNumber, amount);
            receiverReceipt = createTransferReceipt(receiverTransactionID, dateTime,
                    TransactionType.TRANSFER_BETWEEN_ACCOUNTS, senderBankName, receiverBankName,
                    senderAccountNumber, receiverAccountNumber, amount);
            transactionDAO.saveTransationToTXT(senderReceipt);
            transactionDAO.saveTransationToTXT(receiverReceipt);
            connection.commit();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
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
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            accounts = accountDAO.selectIdAndNumberAndBalanceFromAllAccounts(connection);
            updateAllBalancesInAccounts(connection, accounts, percentage);
            connection.commit();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
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

    private Receipt createDepositOrWithrawReceipt(int transactionID, Timestamp dateTime, TransactionType type, String bankName, String accountNumber, BigDecimal amount) {
        return new Receipt.ReceiptBuilder()
                .id(transactionID)
                .dateTime(dateTime.toLocalDateTime())
                .type(type.toString())
                .receiverBankName(bankName)
                .receiverAccountNumber(accountNumber)
                .amount(amount)
                .build();
    }

    private Receipt createTransferReceipt(int senderTransactionID, Timestamp dateTime,
                                          TransactionType type, String senderBankName, String receiverBankName,
                                          String senderAccountNumber, String receiverAccountNumber, BigDecimal amount) {

        return new Receipt.ReceiptBuilder()
                .id(senderTransactionID)
                .dateTime(dateTime.toLocalDateTime())
                .type(type.toString())
                .senderBankName(senderBankName)
                .receiverBankName(receiverBankName)
                .senderAccountNumber(senderAccountNumber)
                .receiverAccountNumber(receiverAccountNumber)
                .amount(amount)
                .build();
    }

}

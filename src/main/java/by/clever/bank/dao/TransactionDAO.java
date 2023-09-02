package by.clever.bank.dao;

import by.clever.bank.bean.Transaction;
import by.clever.bank.bean.constant.TransactionType;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.dto.Receipt;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;

/**
 * Interface to proceed all operations with Transaction entities in DAO
 */
public interface TransactionDAO {

    void saveTransactionData(Connection connection, BigDecimal amount, TransactionType type, int accountID, Timestamp dateTime) throws DAOException;

    int selectTransactionID(Connection connection, BigDecimal amount, TransactionType type, int accountID, Timestamp dateTime) throws DAOException;

    void saveTransationToTXT(Receipt receipt) throws DAOException;

    boolean createTransaction(Transaction transaction, int accountID) throws DAOException;

    boolean updateTransaction(Transaction transaction, int transactionID) throws DAOException;

    Transaction readTransaction(int transactionID) throws DAOException;

    boolean deleteTransaction(int transactionID) throws DAOException;
}

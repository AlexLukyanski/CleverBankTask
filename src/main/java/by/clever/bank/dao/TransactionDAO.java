package by.clever.bank.dao;

import by.clever.bank.bean.constant.TransactionType;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.dto.Receipt;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;

public interface TransactionDAO {

    void saveTransactionData(Connection connection, BigDecimal amount, TransactionType type, int accountID, Timestamp dateTime) throws DAOException;

    int selectTransactionID(Connection connection, BigDecimal amount, TransactionType type, int accountID, Timestamp dateTime) throws DAOException;
    void saveTransationToTXT(Receipt receipt) throws DAOException;
}

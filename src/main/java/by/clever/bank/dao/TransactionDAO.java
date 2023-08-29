package by.clever.bank.dao;

import by.clever.bank.bean.constant.TransactionType;
import by.clever.bank.dao.exception.DAOException;

import java.math.BigDecimal;
import java.sql.Connection;

public interface TransactionDAO {

    void saveTransactionData(Connection connection,BigDecimal amount, TransactionType type, int accountID) throws DAOException;
}

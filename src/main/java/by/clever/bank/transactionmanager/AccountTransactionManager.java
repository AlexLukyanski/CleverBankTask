package by.clever.bank.transactionmanager;

import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import by.clever.bank.transactionmanager.exception.TransactionManagerException;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface AccountTransactionManager {

    public boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws TransactionManagerException, SQLException, ConnectionPoolException;

    public boolean withdrawMoneyFromAccount(BigDecimal amount, String accountNumber) throws TransactionManagerException, SQLException, ConnectionPoolException;
}

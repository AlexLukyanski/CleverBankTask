package by.clever.bank.transactionmanager;

import by.clever.bank.transactionmanager.exception.TransactionManagerException;

import java.math.BigDecimal;

public interface AccountTransactionManager {

    public boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws TransactionManagerException;
}

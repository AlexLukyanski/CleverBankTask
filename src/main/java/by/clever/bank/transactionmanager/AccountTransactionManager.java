package by.clever.bank.transactionmanager;

import by.clever.bank.transactionmanager.exception.TransactionManagerException;

import java.math.BigDecimal;

public interface AccountTransactionManager {

    boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws TransactionManagerException;

    boolean withdrawMoneyFromAccount(BigDecimal amount, String accountNumber) throws TransactionManagerException;

    boolean transferMoneyBetweenAccounts(BigDecimal amount, String senderAccountNumber, String receiverAccountNumber) throws TransactionManagerException;

    void chargeAccrual(BigDecimal percentage) throws TransactionManagerException;
}

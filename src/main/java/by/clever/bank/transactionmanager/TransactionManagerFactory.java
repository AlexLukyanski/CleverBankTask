package by.clever.bank.transactionmanager;

import by.clever.bank.transactionmanager.impl.AccountTransactionManagerImpl;

public final class TransactionManagerFactory {

    private final AccountTransactionManager accountTransactionManager = new AccountTransactionManagerImpl();

    private TransactionManagerFactory() {

    }

    public static TransactionManagerFactory getInstance() {
        return TransactionManagerFactoryHelper.instance;
    }

    public AccountTransactionManager getAccountTransactionManager() {
        return accountTransactionManager;
    }

    public static class TransactionManagerFactoryHelper {
        private final static TransactionManagerFactory instance = new TransactionManagerFactory();
    }
}

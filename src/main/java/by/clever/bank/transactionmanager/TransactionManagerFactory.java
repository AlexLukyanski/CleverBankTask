package by.clever.bank.transactionmanager;

import by.clever.bank.transactionmanager.impl.AccountTransactionManagerImpl;
import lombok.Getter;

@Getter
public final class TransactionManagerFactory {

    private final AccountTransactionManager accountTransactionManager = new AccountTransactionManagerImpl();

    private TransactionManagerFactory() {

    }

    public static TransactionManagerFactory getInstance() {
        return TransactionManagerFactoryHelper.instance;
    }

    public static class TransactionManagerFactoryHelper {
        private final static TransactionManagerFactory instance = new TransactionManagerFactory();
    }
}

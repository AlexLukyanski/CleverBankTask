package by.clever.bank.transactionmanager;

import by.clever.bank.transactionmanager.impl.AccountTransactionManagerImpl;
import lombok.Getter;

/**
 * Factory for all TransactionManager classes
 */
@Getter
public final class TransactionManagerFactory {

    private final AccountTransactionManager accountTransactionManager = new AccountTransactionManagerImpl();

    private TransactionManagerFactory() {

    }

    /**
     *
     * @return specified transaction manager
     */
    public static TransactionManagerFactory getInstance() {
        return TransactionManagerFactoryHelper.instance;
    }

    public static class TransactionManagerFactoryHelper {
        private final static TransactionManagerFactory instance = new TransactionManagerFactory();
    }
}

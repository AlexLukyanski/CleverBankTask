package by.clever.bank.test.transactionmanager;

import by.clever.bank.service.AccountService;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.impl.AccountServiceImpl;
import by.clever.bank.transactionmanager.AccountTransactionManager;
import by.clever.bank.transactionmanager.TransactionManagerFactory;
import by.clever.bank.transactionmanager.impl.AccountTransactionManagerImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TransactionManagerFactoryTest {

    @Test
    public void should_Return_AccountTransactionManager_When_GetAccountTransactionManagerMethodInvoke() {
        //given

        //when
        AccountTransactionManager accountTransactionManager = TransactionManagerFactory.getInstance().getAccountTransactionManager();

        //then
        assertInstanceOf(AccountTransactionManagerImpl.class, accountTransactionManager);
    }
}

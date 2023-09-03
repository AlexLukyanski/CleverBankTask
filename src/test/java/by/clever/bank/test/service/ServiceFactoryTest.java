package by.clever.bank.test.service;


import by.clever.bank.service.*;
import by.clever.bank.service.impl.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ServiceFactoryTest {

    @Test
    public void should_Return_AccountService_When_GetAccountServiceMethodInvoke() {
        //given

        //when
        AccountService accountService = ServiceFactory.getInstance().getAccountService();

        //then
        assertInstanceOf(AccountServiceImpl.class, accountService);
    }

    @Test
    public void should_ReturnAccrualService_When_GetAccrualServiceMethodInvoke() {
        //given

        //when
        AccrualService accrualService = ServiceFactory.getInstance().getAccrualService();

        //then
        assertInstanceOf(AccrualServiceImpl.class, accrualService);
    }

    @Test
    public void should_Return_BankService_When_GetBankServiceMethodInvoke() {
        //given

        //when
        BankService bankService = ServiceFactory.getInstance().getBankService();

        //then
        assertInstanceOf(BankServiceImpl.class, bankService);
    }

    @Test
    public void should_Return_UserService_When_GetUserServiceMethodInvoke() {
        //given

        //when
        UserService userService = ServiceFactory.getInstance().getUserService();

        //then
        assertInstanceOf(UserServiceImpl.class, userService);
    }

    @Test
    public void should_Return_TransactionService_When_GetTransactionServiceMethodInvoke() {
        //given

        //when
        TransactionService transactionService = ServiceFactory.getInstance().getTransactionService();

        //then
        assertInstanceOf(TransactionServiceImpl.class, transactionService);
    }

}

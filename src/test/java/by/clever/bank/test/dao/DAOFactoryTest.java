package by.clever.bank.test.dao;

import by.clever.bank.dao.*;
import by.clever.bank.dao.impl.AccountDAOImpl;
import by.clever.bank.dao.impl.BankDAOImpl;
import by.clever.bank.dao.impl.TransactionDAOImpl;
import by.clever.bank.dao.impl.UserDAOImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DAOFactoryTest {

    @Test
    public void should_Return_AccountDAO_When_GetAccountDAOMethodInvoke() {
        //given

        //when
        AccountDAO accountDAO = DAOFactory.getInstance().getAccountDAO();

        //then
        assertInstanceOf(AccountDAOImpl.class, accountDAO);
    }

    @Test
    public void should_Return_TransactionDAO_When_GetTransactionDAOMethodInvoke() {
        //given

        //when
        TransactionDAO transactionDAO = DAOFactory.getInstance().getTransactionDAO();

        //then
        assertInstanceOf(TransactionDAOImpl.class, transactionDAO);
    }

    @Test
    public void should_Return_BankDAO_When_GetBankDAOMethodInvoke() {
        //given

        //when
        BankDAO bankDAO = DAOFactory.getInstance().getBankDAO();

        //then
        assertInstanceOf(BankDAOImpl.class, bankDAO);
    }

    @Test
    public void should_Return_UserDAO_When_GetUserDAOMethodInvoke() {
        //given

        //when
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

        //then
        assertInstanceOf(UserDAOImpl.class, userDAO);
    }
}

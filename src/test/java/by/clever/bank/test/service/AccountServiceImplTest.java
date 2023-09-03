package by.clever.bank.test.service;


import by.clever.bank.bean.Account;
import by.clever.bank.service.AccountService;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class AccountServiceImplTest {

    private AccountService accountService;

    @BeforeEach
    void setUpTest() {
        accountService = ServiceFactory.getInstance().getAccountService();
    }

    @Nested
    public class PutMoneyToAccountTest {
        @Test
        public void should_Throw_ServiceException_When_AmountArgumentIsNull() {
            //given
            BigDecimal amount = null;
            String accountNumber = "dfbxftj";

            //when
            Executable executable = () -> {
                accountService.putMoneyToAccount(amount, accountNumber);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_AccountNumberArgumentIsNull() {
            //given
            BigDecimal amount = BigDecimal.valueOf(10);
            String accountNumber = null;

            //when
            Executable executable = () -> {
                accountService.putMoneyToAccount(amount, accountNumber);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class WithdrawMoneyFromAccountTest {
        @Test
        public void should_Throw_ServiceException_When_AmountArgumentIsNull()  {
            //given
            BigDecimal amount = null;
            String accountNumber = "dfbxftj";

            //when
            Executable executable = () -> {
                accountService.withdrawMoneyFromAccount(amount, accountNumber);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_AccountNumberArgumentIsNull() {
            //given
            BigDecimal amount = BigDecimal.valueOf(10);
            String accountNumber = null;

            //when
            Executable executable = () -> {
                accountService.withdrawMoneyFromAccount(amount, accountNumber);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class TransferMoneyBetweenAccountsTest {
        @Test
        public void should_Throw_ServiceException_When_AmountArgumentIsNull() {
            //given
            BigDecimal amount = null;
            String senderAccountNumber = "sgsgherh";
            String receiverAccountNumber = "fgdrrhhr";

            //when
            Executable executable = () -> {
                accountService.transferMoneyBetweenAccounts(amount, senderAccountNumber, receiverAccountNumber);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_SenderAccountNumberArgumentIsNull() {
            //given
            BigDecimal amount = BigDecimal.valueOf(15);
            String senderAccountNumber = null;
            String receiverAccountNumber = "fgdrrhhr";

            //when
            Executable executable = () -> {
                accountService.transferMoneyBetweenAccounts(amount, senderAccountNumber, receiverAccountNumber);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_ReceiverAccountNumberArgumentIsNull() {
            //given
            BigDecimal amount = BigDecimal.valueOf(15);
            String senderAccountNumber = "sdgehreh";
            String receiverAccountNumber = null;

            //when
            Executable executable = () -> {
                accountService.transferMoneyBetweenAccounts(amount, senderAccountNumber, receiverAccountNumber);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class CreateAccountTest {

        @Test
        public void should_Throw_ServiceException_When_AccountArgumentIsNull() {
            //given
            Account account = null;
            String bankName = "sdfhh";
            int userID = 1;

            //when
            Executable executable = () -> {
                accountService.createAccount(account, bankName, userID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_BankNameArgumentIsNull() {
            //given
            Account account = new Account();
            String bankName = null;
            int userID = 1;

            //when
            Executable executable = () -> {
                accountService.createAccount(account, bankName, userID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_userIDArgumentIsLessOrEqualsZero() {
            //given
            Account account = new Account();
            String bankName = "sdfhh";
            int userID = 0;

            //when
            Executable executable = () -> {
                accountService.createAccount(account, bankName, userID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class UpdateAccountTest {

        @Test
        public void should_Throw_ServiceException_When_OldAccountArgumentIsNull() {
            //given
            Account oldAccount = null;
            Account newAccount = new Account();

            //when
            Executable executable = () -> {
                accountService.updateAccount(oldAccount, newAccount);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_NewAccountArgumentIsNull() {
            //given
            Account oldAccount = new Account();
            Account newAccount = null;

            //when
            Executable executable = () -> {
                accountService.updateAccount(oldAccount, newAccount);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class ReadAccountTest {
        @Test
        public void should_Throw_ServiceException_When_AccountIDArgumentIsLessOrEqualsZero() {
            //given
            int accountID = 0;

            //when
            Executable executable = () -> {
                accountService.readAccount(accountID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class DeleteAccountTest {
        @Test
        public void should_Throw_ServiceException_When_AccountIDArgumentIsLessOrEqualsZero() {
            //given
            int accountID = 0;

            //when
            Executable executable = () -> {
                accountService.deleteAccount(accountID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }
}
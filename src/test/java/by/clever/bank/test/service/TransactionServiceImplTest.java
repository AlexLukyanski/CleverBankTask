package by.clever.bank.test.service;

import by.clever.bank.bean.Bank;
import by.clever.bank.bean.Transaction;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.TransactionService;
import by.clever.bank.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionServiceImplTest {

    TransactionService transactionService;

    @BeforeEach
    void setUpTest() {
        transactionService = ServiceFactory.getInstance().getTransactionService();
    }

    @Nested
    public class CreateTransactionTest {
        @Test
        public void should_Throw_ServiceException_When_TransactionArgumentIsNull() {
            //given
            Transaction transaction = null;
            int accountID = 1;

            //when
            Executable executable = () -> {
                transactionService.createTransaction(transaction,accountID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_AccountIDArgumentIsNegativeOrZero() {
            //given
            Transaction transaction = new Transaction();
            int accountID = -1;

            //when
            Executable executable = () -> {
                transactionService.createTransaction(transaction,accountID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class UpdateTransactionTest {
        @Test
        public void should_Throw_ServiceException_When_TransactionArgumentIsNull() {
            //given
            Transaction transaction = null;
            int transactionID = 1;

            //when
            Executable executable = () -> {
                transactionService.updateTransaction(transaction, transactionID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_TransactionIDArgumentIsNegativeOrZero() {
            //given
            Transaction transaction = new Transaction();
            int transactionID = -3;

            //when
            Executable executable = () -> {
                transactionService.updateTransaction(transaction, transactionID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class ReadTransactionTest{
        @Test
        public void should_Throw_ServiceException_When_TransactionIDArgumentIsNegativeOrZero() {
            //given
            int transactionID = 0;

            //when
            Executable executable = () -> {
                transactionService.readTransaction(transactionID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class DeleteTransactionTest{

        @Test
        public void should_Throw_ServiceException_When_TransactionIDArgumentIsNegativeOrZero() {
            //given
            int transactionID = 0;

            //when
            Executable executable = () -> {
                transactionService.deleteTransaction(transactionID);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }
}

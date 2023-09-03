package by.clever.bank.test.service;

import by.clever.bank.bean.Bank;
import by.clever.bank.service.BankService;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankServiceImplTest {

    private BankService bankService;

    @BeforeEach
    void setUpTest() {
        bankService = ServiceFactory.getInstance().getBankService();
    }

    @Nested
    public class CreateBankTest {
        @Test
        public void should_Throw_ServiceException_When_BankArgumentIsNull() {
            //given
            Bank bank = null;

            //when
            Executable executable = () -> {
                bankService.createBank(bank);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class UpdateBankTest {
        @Test
        public void should_Throw_ServiceException_When_OldBankArgumentIsNull() {
            //given
            Bank oldBank = null;
            Bank newBank = new Bank();

            //when
            Executable executable = () -> {
                bankService.updateBank(oldBank, newBank);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }

        @Test
        public void should_Throw_ServiceException_When_NewBankArgumentIsNull() {
            //given
            Bank oldBank = new Bank();
            Bank newBank = null;

            //when
            Executable executable = () -> {
                bankService.updateBank(oldBank, newBank);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class ReadBankTest {
        @Test
        public void should_Throw_ServiceException_When_BankNameArgumentIsNull() {
            //given
            String bankName = null;

            //when
            Executable executable = () -> {
                bankService.readBank(bankName);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }

    @Nested
    public class DeleteBankTest{
        @Test
        public void should_Throw_ServiceException_When_BankNameArgumentIsNull() {
            //given
            String bankName = null;

            //when
            Executable executable = () -> {
                bankService.deleteBank(bankName);
            };

            //then
            assertThrows(ServiceException.class, executable);
        }
    }
}

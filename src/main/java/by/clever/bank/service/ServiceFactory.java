package by.clever.bank.service;

import by.clever.bank.service.impl.*;
import lombok.Getter;
/**
 * Factory for all Service classes
 */
@Getter
public final class ServiceFactory {

    private final AccountService accountService = new AccountServiceImpl();
    private final AccrualService accrualService = new AccrualServiceImpl();
    private final BankService bankService = new BankServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();

    private ServiceFactory() {

    }

    /**
     *
     * @return specified service
     */
    public static ServiceFactory getInstance() {
        return ServiceFactoryHelper.instance;
    }

    public static class ServiceFactoryHelper {
        private static final ServiceFactory instance = new ServiceFactory();
    }
}

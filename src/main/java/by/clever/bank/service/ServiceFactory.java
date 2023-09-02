package by.clever.bank.service;

import by.clever.bank.service.impl.AccountServiceImpl;
import by.clever.bank.service.impl.AccrualServiceImpl;
import by.clever.bank.service.impl.BankServiceImpl;
import by.clever.bank.service.impl.UserServiceImpl;
import lombok.Getter;

@Getter
public final class ServiceFactory {

    private final AccountService accountService = new AccountServiceImpl();
    private final AccrualService accrualService = new AccrualServiceImpl();
    private final BankService bankService = new BankServiceImpl();
    private final UserService userService = new UserServiceImpl();

    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        return ServiceFactoryHelper.instance;
    }

    public static class ServiceFactoryHelper {
        private static final ServiceFactory instance = new ServiceFactory();
    }
}

package by.clever.bank.service;

import by.clever.bank.service.impl.AccountServiceImpl;
import by.clever.bank.service.impl.AccrualServiceImpl;
import lombok.Getter;

@Getter
public final class ServiceFactory {

    private final AccountService accountService = new AccountServiceImpl();
    private final AccrualService accrualService = new AccrualServiceImpl();

    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        return ServiceFactoryHelper.instance;
    }

    public static class ServiceFactoryHelper {
        private static final ServiceFactory instance = new ServiceFactory();
    }
}

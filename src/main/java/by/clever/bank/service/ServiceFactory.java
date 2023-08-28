package by.clever.bank.service;

import by.clever.bank.service.impl.AccountServiceImpl;
import lombok.Getter;

@Getter
public final class ServiceFactory {

    private final AccountService accountService = new AccountServiceImpl();

    private ServiceFactory() {

    }

    public static final ServiceFactory getInstance() {
        return ServiceFactoryHelper.instance;
    }

    public static class ServiceFactoryHelper {
        private static final ServiceFactory instance = new ServiceFactory();
    }
}

package by.clever.bank.service;

import by.clever.bank.service.impl.AccountServiceImpl;


public final class ServiceFactory {

    private final AccountService accountService = new AccountServiceImpl();

    private ServiceFactory() {

    }

    public static final ServiceFactory getInstance() {
        return ServiceFactoryHelper.instance;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public static class ServiceFactoryHelper {
        private static final ServiceFactory instance = new ServiceFactory();
    }
}

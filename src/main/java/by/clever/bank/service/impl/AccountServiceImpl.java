package by.clever.bank.service.impl;

import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import by.clever.bank.service.AccountService;
import by.clever.bank.service.exception.ServiceException;
import by.clever.bank.transactionmanager.AccountTransactionManager;
import by.clever.bank.transactionmanager.TransactionManagerFactory;
import by.clever.bank.transactionmanager.exception.TransactionManagerException;

import java.math.BigDecimal;
import java.sql.SQLException;

public class AccountServiceImpl implements AccountService {

    private final static AccountTransactionManager transactionManager = TransactionManagerFactory.getInstance().getAccountTransactionManager();

    @Override
    public boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws ServiceException {

        try {
            return transactionManager.putMoneyToAccount(amount, accountNumber);
        } catch (TransactionManagerException | ConnectionPoolException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean withdrawMoneyFromAccount(BigDecimal amount, String accountNumber) throws ServiceException {
        try {
            return transactionManager.withdrawMoneyFromAccount(amount, accountNumber);
        } catch (TransactionManagerException | ConnectionPoolException | SQLException e) {
            throw new ServiceException(e);
        }
    }
}

package by.clever.bank.service.impl;

import by.clever.bank.service.AccountService;
import by.clever.bank.service.exception.ServiceException;
import by.clever.bank.transactionmanager.AccountTransactionManager;
import by.clever.bank.transactionmanager.TransactionManagerFactory;
import by.clever.bank.transactionmanager.exception.TransactionManagerException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountServiceImpl implements AccountService {

    private final static AccountTransactionManager transactionManager = TransactionManagerFactory.getInstance().getAccountTransactionManager();
    private int monthValue = LocalDateTime.now().getMonthValue();

    @Override
    public boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws ServiceException {

        try {
            return transactionManager.putMoneyToAccount(amount, accountNumber);
        } catch (TransactionManagerException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean withdrawMoneyFromAccount(BigDecimal amount, String accountNumber) throws ServiceException {
        try {
            return transactionManager.withdrawMoneyFromAccount(amount, accountNumber);
        } catch (TransactionManagerException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean transferMoneyBetweenAccounts(BigDecimal amount, String senderAccountNumber, String receiverAccountNumber) throws ServiceException {
        try {
            return transactionManager.transferMoneyBetweenAccounts(amount, senderAccountNumber, receiverAccountNumber);
        } catch (TransactionManagerException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkNecessity() {
        int currentMonthValue = LocalDateTime.now().getMonthValue();

        if (currentMonthValue != monthValue) {
            monthValue = currentMonthValue;
            return true;
        }
        return false;
    }

    @Override
    public void chargeAccrual(BigDecimal percentage) throws ServiceException {
        try {
            transactionManager.chargeAccrual(percentage);
        } catch (TransactionManagerException e) {
            throw new ServiceException(e);
        }
    }
}

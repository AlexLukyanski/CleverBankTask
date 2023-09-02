package by.clever.bank.service.impl;

import by.clever.bank.bean.Account;
import by.clever.bank.dao.AccountDAO;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.service.AccountService;
import by.clever.bank.service.exception.ServiceException;
import by.clever.bank.transactionmanager.AccountTransactionManager;
import by.clever.bank.transactionmanager.TransactionManagerFactory;
import by.clever.bank.transactionmanager.exception.TransactionManagerException;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {

    private final static AccountTransactionManager transactionManager = TransactionManagerFactory.getInstance().getAccountTransactionManager();
    private final static AccountDAO accountDAO = DAOFactory.getInstance().getAccountDAO();

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
    public boolean createAccount(Account account, String bankName, int userID) throws ServiceException {
        try {
            return accountDAO.createAccount(account, bankName, userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateAccount(Account oldAccount, Account newAccount) throws ServiceException {
        try {
            return accountDAO.updateAccount(oldAccount, newAccount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Account readAccount(int accountID) throws ServiceException {
        try {
            return accountDAO.readAccount(accountID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteAccount(int accountID) throws ServiceException {
        try {
            return accountDAO.deleteAccount(accountID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}

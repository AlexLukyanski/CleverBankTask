package by.clever.bank.service.impl;

import by.clever.bank.bean.Account;
import by.clever.bank.dao.AccountDAO;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.service.AccountService;
import by.clever.bank.service.exception.ServiceException;
import by.clever.bank.service.exception.ServiceValidationException;
import by.clever.bank.transactionmanager.AccountTransactionManager;
import by.clever.bank.transactionmanager.TransactionManagerFactory;
import by.clever.bank.transactionmanager.exception.TransactionManagerException;

import java.math.BigDecimal;

/**
 * Class to proceed all operations with Account entities in service
 */
public class AccountServiceImpl implements AccountService {

    private final static AccountTransactionManager transactionManager = TransactionManagerFactory.getInstance().getAccountTransactionManager();
    private final static AccountDAO accountDAO = DAOFactory.getInstance().getAccountDAO();

    /**
     *
     * @param amount
     * @param accountNumber
     * @return boolean if money was put
     * @throws ServiceException
     */
    @Override
    public boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws ServiceException {

        try {
            if (amount != null && accountNumber != null) {
                return transactionManager.putMoneyToAccount(amount, accountNumber);
            } else {
                throw new ServiceValidationException("Null arguments are not accepted");
            }
        } catch (TransactionManagerException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    /**
     *
     * @param amount
     * @param accountNumber
     * @return boolean if money was withdrawn
     * @throws ServiceException
     */
    @Override
    public boolean withdrawMoneyFromAccount(BigDecimal amount, String accountNumber) throws ServiceException {
        try {
            if (amount != null && accountNumber != null) {
                return transactionManager.withdrawMoneyFromAccount(amount, accountNumber);
            } else {
                throw new ServiceValidationException("Null arguments are not accepted");
            }
        } catch (TransactionManagerException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    /**
     *
     * @param amount
     * @param senderAccountNumber
     * @param receiverAccountNumber
     * @return boolean if money was transferred
     * @throws ServiceException
     */
    @Override
    public boolean transferMoneyBetweenAccounts(BigDecimal amount, String senderAccountNumber, String receiverAccountNumber) throws ServiceException {
        try {
            if (amount != null && senderAccountNumber != null && receiverAccountNumber != null) {
                return transactionManager.transferMoneyBetweenAccounts(amount, senderAccountNumber, receiverAccountNumber);
            } else {
                throw new ServiceValidationException("Null arguments are not accepted");
            }
        } catch (TransactionManagerException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    /**
     *
     * @param account
     * @param bankName
     * @param userID
     * @return boolean if account was created
     * @throws ServiceException
     */
    @Override
    public boolean createAccount(Account account, String bankName, int userID) throws ServiceException {
        try {
            if (account != null && bankName != null && userID > 0) {
                return accountDAO.createAccount(account, bankName, userID);
            } else {
                throw new ServiceValidationException("Null and negative or zero arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    /**
     *
     * @param oldAccount
     * @param newAccount
     * @return boolean if account was updated
     * @throws ServiceException
     */
    @Override
    public boolean updateAccount(Account oldAccount, Account newAccount) throws ServiceException {
        try {
            if (oldAccount != null && newAccount != null) {
                return accountDAO.updateAccount(oldAccount, newAccount);
            } else {
                throw new ServiceValidationException("Null and negative or zero arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    /**
     *
     * @param accountID
     * @return account entity
     * @throws ServiceException
     */
    @Override
    public Account readAccount(int accountID) throws ServiceException {
        try {
            if (accountID > 0) {
                return accountDAO.readAccount(accountID);
            } else {
                throw new ServiceValidationException("Negative or zero arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    /**
     *
     * @param accountID
     * @return boolean if account was deleted
     * @throws ServiceException
     */
    @Override
    public boolean deleteAccount(int accountID) throws ServiceException {
        try {
            if (accountID > 0) {
                return accountDAO.deleteAccount(accountID);
            } else {
                throw new ServiceValidationException("Negative or zero arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }
}

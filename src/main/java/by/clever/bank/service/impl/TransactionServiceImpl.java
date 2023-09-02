package by.clever.bank.service.impl;

import by.clever.bank.bean.Transaction;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.TransactionDAO;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.service.TransactionService;
import by.clever.bank.service.exception.ServiceException;
import by.clever.bank.service.exception.ServiceValidationException;

public class TransactionServiceImpl implements TransactionService {

    private final static TransactionDAO transactionDAO = DAOFactory.getInstance().getTransactionDAO();

    @Override
    public boolean createTransaction(Transaction transaction, int accountID) throws ServiceException {
        try {
            if (transaction != null && accountID > 0) {
                return transactionDAO.createTransaction(transaction, accountID);
            } else {
                throw new ServiceValidationException("Null and negative or zero arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateTransaction(Transaction transaction, int transactionID) throws ServiceException {
        try {
            if (transaction != null && transactionID > 0) {
                return transactionDAO.updateTransaction(transaction, transactionID);
            } else {
                throw new ServiceValidationException("Null and negative or zero arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Transaction readTransaction(int transactionID) throws ServiceException {
        try {
            if (transactionID > 0) {
                return transactionDAO.readTransaction(transactionID);
            } else {
                throw new ServiceValidationException("Negative or zero arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteTransaction(int transactionID) throws ServiceException {
        try {
            if (transactionID > 0) {
                return transactionDAO.deleteTransaction(transactionID);
            } else {
                throw new ServiceValidationException("Negative or zero arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }
}

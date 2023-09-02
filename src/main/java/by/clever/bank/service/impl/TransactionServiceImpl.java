package by.clever.bank.service.impl;

import by.clever.bank.bean.Transaction;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.TransactionDAO;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.service.TransactionService;
import by.clever.bank.service.exception.ServiceException;

public class TransactionServiceImpl implements TransactionService {

    private final static TransactionDAO transactionDAO = DAOFactory.getInstance().getTransactionDAO();

    @Override
    public boolean createTransaction(Transaction transaction, int accountID) throws ServiceException {
        try {
            return transactionDAO.createTransaction(transaction, accountID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateTransaction(Transaction transaction, int transactionID) throws ServiceException {
        try {
            return transactionDAO.updateTransaction(transaction, transactionID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Transaction readTransaction(int transactionID) throws ServiceException {
        try {
            return transactionDAO.readTransaction(transactionID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteTransaction(int transactionID) throws ServiceException {
        try {
            return transactionDAO.deleteTransaction(transactionID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}

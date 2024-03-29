package by.clever.bank.service;

import by.clever.bank.bean.Transaction;
import by.clever.bank.service.exception.ServiceException;

/**
 * Interface to proceed all operations with Transaction entities in service
 */
public interface TransactionService {

    boolean createTransaction(Transaction transaction, int accountID) throws ServiceException;

    boolean updateTransaction(Transaction transaction, int transactionID) throws ServiceException;

    Transaction readTransaction(int transactionID) throws ServiceException;

    boolean deleteTransaction(int transactionID) throws ServiceException;
}

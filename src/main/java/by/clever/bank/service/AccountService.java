package by.clever.bank.service;

import by.clever.bank.bean.Account;
import by.clever.bank.service.exception.ServiceException;

import java.math.BigDecimal;
/**
 * Interface to proceed all operations with Account entities in service
 */
public interface AccountService {

    boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws ServiceException;

    boolean withdrawMoneyFromAccount(BigDecimal amount, String accountNumber) throws ServiceException;

    boolean transferMoneyBetweenAccounts(BigDecimal amount, String senderAccountNumber, String receiverAccountNumber) throws ServiceException;

    boolean createAccount(Account account, String bankName, int userID) throws ServiceException;

    boolean updateAccount(Account oldAccount, Account newAccount) throws ServiceException;

    Account readAccount(int accountID) throws ServiceException;

    boolean deleteAccount(int accountID) throws ServiceException;

}

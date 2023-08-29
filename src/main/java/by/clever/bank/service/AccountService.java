package by.clever.bank.service;

import by.clever.bank.service.exception.ServiceException;

import java.math.BigDecimal;

public interface AccountService {

    boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws ServiceException;

    boolean withdrawMoneyFromAccount(BigDecimal amount, String accountNumber) throws ServiceException;

    boolean transferMoneyBetweenAccounts(BigDecimal amount, String senderAccountNumber, String receiverAccountNumber) throws ServiceException;

}

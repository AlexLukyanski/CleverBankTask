package by.clever.bank.service;

import by.clever.bank.service.exception.ServiceException;

import java.math.BigDecimal;

public interface AccountService {

    boolean putMoneyToAccount(BigDecimal amount, String accountNumber) throws ServiceException;

}

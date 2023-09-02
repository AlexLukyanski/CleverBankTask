package by.clever.bank.service;

import by.clever.bank.bean.Bank;
import by.clever.bank.service.exception.ServiceException;

public interface BankService {

    boolean createBank(Bank bank) throws ServiceException;

    boolean updateBank(Bank oldBank, Bank newBank) throws ServiceException;

    Bank readBank(String bankName) throws ServiceException;

    boolean deleteBank(String bankName) throws ServiceException;
}

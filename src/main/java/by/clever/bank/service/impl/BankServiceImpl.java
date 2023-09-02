package by.clever.bank.service.impl;

import by.clever.bank.bean.Bank;
import by.clever.bank.dao.BankDAO;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.service.BankService;
import by.clever.bank.service.exception.ServiceException;
import by.clever.bank.service.exception.ServiceValidationException;

public class BankServiceImpl implements BankService {

    private final static BankDAO bankDAO = DAOFactory.getInstance().getBankDAO();

    @Override
    public boolean createBank(Bank bank) throws ServiceException {

        try {
            if (bank != null) {
                return bankDAO.createBank(bank);
            } else {
                throw new ServiceValidationException("Null arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateBank(Bank oldBank, Bank newBank) throws ServiceException {
        try {
            if (oldBank != null && newBank != null) {
                return bankDAO.updateBank(oldBank, newBank);
            } else {
                throw new ServiceValidationException("Null arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Bank readBank(String bankName) throws ServiceException {
        try {
            if (bankName != null) {
                return bankDAO.readBank(bankName);
            } else {
                throw new ServiceValidationException("Null arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteBank(String bankName) throws ServiceException {
        try {
            if (bankName != null) {
                return bankDAO.deleteBank(bankName);
            } else {
                throw new ServiceValidationException("Null arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }
}

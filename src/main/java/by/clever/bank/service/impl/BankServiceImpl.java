package by.clever.bank.service.impl;

import by.clever.bank.bean.Bank;
import by.clever.bank.dao.BankDAO;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.service.BankService;
import by.clever.bank.service.exception.ServiceException;

public class BankServiceImpl implements BankService {

    private final static BankDAO bankDAO = DAOFactory.getInstance().getBankDAO();

    @Override
    public boolean createBank(Bank bank) throws ServiceException {

        try {
            return bankDAO.createBank(bank);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateBank(Bank oldBank, Bank newBank) throws ServiceException {
        try {
            return bankDAO.updateBank(oldBank, newBank);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Bank readBank(String bankName) throws ServiceException {
        try {
            return bankDAO.readBank(bankName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteBank(String bankName) throws ServiceException {
        try {
            return bankDAO.deleteBank(bankName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}

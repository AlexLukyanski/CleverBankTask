package by.clever.bank.service.impl;

import by.clever.bank.bean.Bank;
import by.clever.bank.dao.BankDAO;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.service.BankService;
import by.clever.bank.service.exception.ServiceException;
import by.clever.bank.service.exception.ServiceValidationException;

/**
 * Class to proceed all operations with Bank entities in service
 */
public class BankServiceImpl implements BankService {

    private final static BankDAO bankDAO = DAOFactory.getInstance().getBankDAO();

    /**
     *
     * @param bank
     * @return boolean if bank was created
     * @throws ServiceException
     */
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

    /**
     *
     * @param oldBank
     * @param newBank
     * @return boolean if bank was updated
     * @throws ServiceException
     */
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

    /**
     *
     * @param bankName
     * @return bank entity
     * @throws ServiceException
     */
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

    /**
     *
     * @param bankName
     * @return boolean if bank was deleted
     * @throws ServiceException
     */
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

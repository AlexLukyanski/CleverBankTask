package by.clever.bank.dao;

import by.clever.bank.bean.Bank;
import by.clever.bank.dao.exception.DAOException;
/**
 * Interface to proceed all operations with Bank entities in DAO
 */
public interface BankDAO {

    boolean createBank(Bank bank) throws DAOException;

    boolean updateBank(Bank oldBank, Bank newBank) throws DAOException;

    Bank readBank(String bankName) throws DAOException;

    boolean deleteBank(String bankName) throws DAOException;
}

package by.clever.bank.dao;

import by.clever.bank.bean.Bank;
import by.clever.bank.dao.exception.DAOException;

public interface BankDAO {

    boolean createBank(Bank bank) throws DAOException;

    boolean updateBank(Bank oldBank, Bank newBank) throws DAOException;

    Bank readBank(String bankName) throws DAOException;

    boolean deleteBank(String bankName) throws DAOException;
}

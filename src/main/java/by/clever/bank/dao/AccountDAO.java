package by.clever.bank.dao;

import by.clever.bank.bean.Account;
import by.clever.bank.dao.exception.DAOException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**
 * Interface to proceed all operations with Account entities in DAO
 */
public interface AccountDAO {

    BigDecimal selectBalance(Connection connection, String accountNumber) throws DAOException;

    int selectAccountID(Connection connection, String accountNumber) throws DAOException;

    void changeBalance(Connection connection, BigDecimal newBalance, String accountNumber) throws DAOException;

    List<Account> selectIdAndNumberAndBalanceFromAllAccounts(Connection connection) throws DAOException;

    String takeBankName(Connection connection, int accountID) throws DAOException;

    //Below are CRUD operations
    boolean createAccount(Account account, String bankName, int userID) throws DAOException;

    boolean updateAccount(Account oldAccount, Account newAccount) throws DAOException;

    Account readAccount(int accountID) throws DAOException;

    boolean deleteAccount(int accountID) throws DAOException;
}


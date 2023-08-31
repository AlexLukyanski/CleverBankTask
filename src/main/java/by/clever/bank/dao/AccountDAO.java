package by.clever.bank.dao;

import by.clever.bank.bean.Account;
import by.clever.bank.dao.exception.DAOException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public interface AccountDAO {

    BigDecimal selectBalance(Connection connection, String accountNumber) throws DAOException;

    int selectAccountID(Connection connection, String accountNumber) throws DAOException;

    void changeBalance(Connection connection, BigDecimal newBalance, String accountNumber) throws DAOException;

    List<Account> selectIdAndNumberAndBalanceFromAllAccounts(Connection connection) throws DAOException;

    String takeBankName(Connection connection, int accountID) throws DAOException;
}


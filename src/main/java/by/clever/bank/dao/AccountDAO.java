package by.clever.bank.dao;

import by.clever.bank.dao.exception.DAOException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public interface AccountDAO {

    BigDecimal selectBalance(Connection connection, String accountNumber) throws DAOException;

    void addMoneyToBalance(Connection connection, BigDecimal newBalance, String accountNumber) throws DAOException;
}

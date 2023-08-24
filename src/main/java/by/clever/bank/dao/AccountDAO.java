package by.clever.bank.dao;

import by.clever.bank.dao.exception.DAOException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public interface AccountDAO {

    boolean putMoneyToAccount(BigDecimal amount, String accountNumber, Connection connection) throws DAOException, SQLException;
}


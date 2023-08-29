package by.clever.bank.dao.impl;

import by.clever.bank.bean.constant.TransactionType;
import by.clever.bank.dao.TransactionDAO;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.dao.impl.constant.DBColumnName;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class TransactionDAOImpl implements TransactionDAO {

    private final static String INSERT_TRANSACTION_DATA_SQL = "INSERT INTO transactions" +
            "(t_type,t_datetime,t_amount,t_account) VALUES(?,?,?,?)";

    @Override
    public void saveTransactionData(Connection connection, BigDecimal amount, TransactionType type, int accountID) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_DATA_SQL)) {
            preparedStatement.setString(1, type.toString());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setBigDecimal(3, amount);
            preparedStatement.setInt(4, accountID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}

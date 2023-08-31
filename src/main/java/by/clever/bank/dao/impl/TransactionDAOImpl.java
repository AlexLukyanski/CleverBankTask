package by.clever.bank.dao.impl;

import by.clever.bank.bean.constant.TransactionType;
import by.clever.bank.dao.TransactionDAO;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.dao.impl.constant.DBColumnAccountName;
import by.clever.bank.dao.impl.constant.DBColumnTransactionName;
import by.clever.bank.dto.Receipt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

public class TransactionDAOImpl implements TransactionDAO {

    private final static String INSERT_TRANSACTION_DATA_SQL = "INSERT INTO transactions" +
            "(t_type,t_datetime,t_amount,t_account) VALUES(?,?,?,?)";

    @Override
    public void saveTransactionData(Connection connection, BigDecimal amount, TransactionType type, int accountID, Timestamp dateTime) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_DATA_SQL)) {
            preparedStatement.setString(1, type.toString());
            preparedStatement.setTimestamp(2, dateTime);
            preparedStatement.setBigDecimal(3, amount);
            preparedStatement.setInt(4, accountID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private final static String SELECT_TRANSACTION_ID_SQL = "SELECT t_id from transactions" +
            " WHERE t_type=? AND t_datetime=? AND t_amount=? AND t_account=?";

    @Override
    public int selectTransactionID(Connection connection, BigDecimal amount, TransactionType type, int accountID, Timestamp dateTime) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TRANSACTION_ID_SQL)) {
            preparedStatement.setString(1, type.toString());
            preparedStatement.setTimestamp(2, dateTime);
            preparedStatement.setBigDecimal(3, amount);
            preparedStatement.setInt(4, accountID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(DBColumnTransactionName.ID_COLUMN);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void saveTransationToTXT(Receipt receipt) throws DAOException {
        File file = createFile(receipt);
        String textToPrint = createTextToPrint(receipt);

        try (FileWriter fileWriter = new FileWriter(file)) {

            fileWriter.write(textToPrint);
            fileWriter.flush();

        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    private File createFile(Receipt receipt) {

        String pathName;
        File file;

        pathName = new StringBuilder(receipt.getTitle())
                .append(".txt")
                .toString();

        return new File(pathName);
    }

    private String createTextToPrint(Receipt receipt){
        return new StringBuilder(receipt.getTitle())
                .append("\n")
                .append("ID:      ")
                .append(receipt.getId())
                .append("\n")
                .append(receipt.getDateTime().toLocalDate().toString())
                .append("      ")
                .append(receipt.getDateTime().toLocalTime().toString())
                .toString();

    }
}

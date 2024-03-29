package by.clever.bank.dao.impl;


import by.clever.bank.bean.Transaction;
import by.clever.bank.bean.constant.TransactionType;
import by.clever.bank.dao.TransactionDAO;
import by.clever.bank.dao.connectionpool.ConnectionPool;
import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.dao.impl.constant.DBColumnTransactionName;
import by.clever.bank.dto.Receipt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.format.DateTimeFormatter;

/**
 * Class to proceed all operations with Transaction entities in DAO
 */
public class TransactionDAOImpl implements TransactionDAO {

    private final static String INSERT_TRANSACTION_DATA_SQL = "INSERT INTO transactions" +
            "(t_type,t_datetime,t_amount,t_account) VALUES(?,?,?,?)";

    /**
     *
     * @param connection
     * @param amount
     * @param type
     * @param accountID
     * @param dateTime
     * @throws DAOException
     */
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

    /**
     *
     * @param connection
     * @param amount
     * @param type
     * @param accountID
     * @param dateTime
     * @return transaction ID
     * @throws DAOException
     */
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

    /**
     *
     * @param receipt
     * @throws DAOException
     */
    @Override
    public void saveTransationToTXT(Receipt receipt) throws DAOException {
        File file = createFile(receipt);
        String textToPrint;

        if (receipt.getType().equals(TransactionType.DEPOSIT.toString()) ||
                receipt.getType().equals(TransactionType.WITHDRAWAL.toString())) {

            textToPrint = createTextToPrintDepositAndWithdraw(receipt);
        } else {
            textToPrint = createTextToPrintTransfer(receipt);
        }

        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8)) {
            fileWriter.write(textToPrint);
            fileWriter.flush();
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    /**
     *
     * @param transaction
     * @param accountID
     * @return boolean if transaction was created
     * @throws DAOException
     */
    @Override
    public boolean createTransaction(Transaction transaction, int accountID) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_DATA_SQL)) {

            preparedStatement.setString(1, transaction.getType().toString());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(transaction.getDateTime()));
            preparedStatement.setBigDecimal(3, transaction.getAmount());
            preparedStatement.setInt(4, accountID);
            preparedStatement.executeUpdate();
            int insertionResult = preparedStatement.executeUpdate();

            if (insertionResult != 0) {
                return true;
            }
            return false;

        } catch (SQLException | ConnectionPoolException e) {
            System.out.println("sbesrhserhsrth");
            throw new DAOException(e);
        }
    }

    private final static String UPDATE_TRANSACTION_SQL = "UPDATE transactions SET" +
            "t_type=?,t_datetime=?,t_amount=?" +
            "WHERE t_id=?";

    /**
     *
     * @param transaction
     * @param transactionID
     * @return boolean if transaction was updated
     * @throws DAOException
     */
    @Override
    public boolean updateTransaction(Transaction transaction, int transactionID) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TRANSACTION_SQL)) {

            preparedStatement.setString(1, transaction.getType().toString());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(transaction.getDateTime()));
            preparedStatement.setBigDecimal(3, transaction.getAmount());
            preparedStatement.setInt(4, transactionID);
            int insertionResult = preparedStatement.executeUpdate();

            if (insertionResult != 0) {
                return true;
            }
            return false;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String SELECT_TRANSACTION_SQL = "SELECT" +
            "t_id,t_type,t_datetime,t_amount" +
            "FROM transactions WHERE t_id=?";

    /**
     *
     * @param transactionID
     * @return created transaction entity
     * @throws DAOException
     */
    @Override
    public Transaction readTransaction(int transactionID) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TRANSACTION_SQL)) {

            preparedStatement.setInt(1, transactionID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return setTransaction(resultSet);

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String DELETE_TRANSACTION_SQL = "DELETE FROM transactions WHERE t_id=?";

    /**
     *
     * @param transactionID
     * @return boolean if transaction was deleted
     * @throws DAOException
     */
    @Override
    public boolean deleteTransaction(int transactionID) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRANSACTION_SQL)) {

            preparedStatement.setInt(1, transactionID);

            int deleteResult = preparedStatement.executeUpdate();

            if (deleteResult != 0) {
                return true;
            }
            return false;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private File createFile(Receipt receipt) {

        String pathName;
        File file;

        pathName = new StringBuilder()
                .append(receipt.getId())
                .append(".txt")
                .toString();

        return new File(pathName);
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private String createTextToPrintDepositAndWithdraw(Receipt receipt) {

        return new StringBuilder(receipt.getTitle())
                .append("\n")
                .append("Number:      ")
                .append(receipt.getId())
                .append("\n")
                .append(receipt.getDateTime().toLocalDate().format(formatter))
                .append("      ")
                .append(receipt.getDateTime().toLocalTime().withNano(0).toString())
                .append("\n")
                .append("Transaction type:      ")
                .append(receipt.getType())
                .append("\n")
                .append("Bank name:      ")
                .append(receipt.getReceiverBankName())
                .append("\n")
                .append("Account number:      ")
                .append(receipt.getReceiverAccountNumber())
                .append("\n")
                .append("Amount:      ")
                .append(receipt.getAmount().toString())
                .append(" BYN")
                .toString();
    }

    private String createTextToPrintTransfer(Receipt receipt) {

        return new StringBuilder(receipt.getTitle())
                .append("\n")
                .append("Number:      ")
                .append(receipt.getId())
                .append("\n")
                .append(receipt.getDateTime().toLocalDate().format(formatter))
                .append("      ")
                .append(receipt.getDateTime().toLocalTime().withNano(0).toString())
                .append("\n")
                .append("Transaction type:      ")
                .append(receipt.getType())
                .append("\n")
                .append("Receiver bank:      ")
                .append(receipt.getSenderBankName())
                .append("\n")
                .append("Sender bank:      ")
                .append(receipt.getReceiverBankName())
                .append("\n")
                .append("Receiver account:      ")
                .append(receipt.getSenderAccountNumber())
                .append("\n")
                .append("Sender account:      ")
                .append(receipt.getReceiverAccountNumber())
                .append("\n")
                .append("Amount:      ")
                .append(receipt.getAmount().toString())
                .append(" BYN")
                .toString();
    }

    private Transaction setTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getInt(DBColumnTransactionName.ID_COLUMN));
        transaction.setType(TransactionType.valueOf(resultSet.getString(DBColumnTransactionName.TYPE_COLUMN)));
        transaction.setDateTime(resultSet.getTimestamp(DBColumnTransactionName.DATE_TIME_COLUMN).toLocalDateTime());
        transaction.setAmount(resultSet.getBigDecimal(DBColumnTransactionName.AMOUNT_COLUMN));
        return transaction;
    }
}

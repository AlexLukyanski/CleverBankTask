package by.clever.bank.dao.impl;

import by.clever.bank.bean.User;
import by.clever.bank.dao.UserDAO;
import by.clever.bank.dao.connectionpool.ConnectionPool;
import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.dao.impl.constant.DBColumnUserName;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private final static String INSERT_NEW_USER_SQL = "INSERT INTO users " +
            "(ui_name,ui_surname,ui_patronymic,ui_dateofbirth,ui_phonenumber,ui_email) VALUES (?,?,?,?,?,?)";

    @Override
    public boolean createUser(User user) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER_SQL)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPatronymic());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getEmail());
            int insertionResult = preparedStatement.executeUpdate();

            if (insertionResult != 0) {
                return true;
            }
            return false;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String UPDATE_USER_SQL = "UPDATE users SET" +
            " ui_name=?,ui_surname=?,ui_patronymic=?,ui_dateofbirth=?,ui_phonenumber=?,ui_email=?" +
            "WHERE ui_id=(SELECT ui_id FROM users WHERE ui_phonenumber=?)";

    @Override
    public boolean updateUser(User oldUser, User newUser) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {

            preparedStatement.setString(1, newUser.getName());
            preparedStatement.setString(2, newUser.getSurname());
            preparedStatement.setString(3, newUser.getPatronymic());
            preparedStatement.setDate(4, Date.valueOf(newUser.getDateOfBirth()));
            preparedStatement.setString(5, newUser.getPhoneNumber());
            preparedStatement.setString(6, newUser.getEmail());
            preparedStatement.setString(7, oldUser.getPhoneNumber());
            int insertionResult = preparedStatement.executeUpdate();

            if (insertionResult != 0) {
                return true;
            }
            return false;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String SELECT_USER_SQL = "SELECT" +
            "ui_id,ui_name,ui_surname,ui_patronymic,ui_dateofbirth,ui_phonenumber,ui_email " +
            "FROM users WHERE ui_id=?";

    @Override
    public User readUser(int userID) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_SQL)) {

            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return setUser(resultSet);

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private final static String DELETE_USER_SQL = "DELETE FROM users WHERE ui_id=?";

    @Override
    public boolean deleteUser(int userID) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {

            preparedStatement.setInt(1, userID);

            int deleteResult = preparedStatement.executeUpdate();

            if (deleteResult != 0) {
                return true;
            }
            return false;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    private User setUser(ResultSet resultSet) throws SQLException {

        User user = new User();
        user.setId(resultSet.getInt(DBColumnUserName.ID_COLUMN));
        user.setName(resultSet.getString(DBColumnUserName.NAME_COLUMN));
        user.setSurname(resultSet.getString(DBColumnUserName.SURNAME_COLUMN));
        user.setPatronymic(resultSet.getString(DBColumnUserName.PATRONYMIC_COLUMN));
        user.setDateOfBirth(resultSet.getDate(DBColumnUserName.DATE_OF_BIRTH_COLUMN).toLocalDate());
        user.setPhoneNumber(resultSet.getString(DBColumnUserName.PHONE_NUMBER_COLUMN));
        user.setEmail(resultSet.getString(DBColumnUserName.EMAIL_COLUMN));
        return user;
    }
}

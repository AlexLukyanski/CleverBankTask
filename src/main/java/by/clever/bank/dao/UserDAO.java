package by.clever.bank.dao;

import by.clever.bank.bean.User;
import by.clever.bank.dao.exception.DAOException;

/**
 * Interface to proceed all operations with User entities in DAO
 */
public interface UserDAO {
    boolean createUser(User user) throws DAOException;

    boolean updateUser(User oldUser, User newUser) throws DAOException;

    User readUser(int userID) throws DAOException;

    boolean deleteUser(int userID) throws DAOException;
}

package by.clever.bank.service;

import by.clever.bank.bean.User;
import by.clever.bank.service.exception.ServiceException;

/**
 * Interface to proceed all operations with User entities in service
 */
public interface UserService {

    boolean createUser(User user) throws ServiceException;

    boolean updateUser(User oldUser, User newUser) throws ServiceException;

    User readUser(int userID) throws ServiceException;

    boolean deleteUser(int userID) throws ServiceException;
}

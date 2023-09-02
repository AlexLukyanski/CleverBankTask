package by.clever.bank.service.impl;

import by.clever.bank.bean.User;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.UserDAO;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.service.UserService;
import by.clever.bank.service.exception.ServiceException;
import by.clever.bank.service.exception.ServiceValidationException;

/**
 * Class to proceed all operations with User entities in service
 */
public class UserServiceImpl implements UserService {

    private final static UserDAO userDao = DAOFactory.getInstance().getUserDAO();

    /**
     *
     * @param user
     * @return boolean if user was created
     * @throws ServiceException
     */
    @Override
    public boolean createUser(User user) throws ServiceException {
        try {
            if (user != null) {
                return userDao.createUser(user);
            } else {
                throw new ServiceValidationException("Null arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    /**
     *
     * @param oldUser
     * @param newUser
     * @return boolean if user was updated
     * @throws ServiceException
     */
    @Override
    public boolean updateUser(User oldUser, User newUser) throws ServiceException {
        try {
            if (oldUser != null && newUser != null) {
                return userDao.updateUser(oldUser, newUser);
            } else {
                throw new ServiceValidationException("Null arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    /**
     *
     * @param userID
     * @return user entity
     * @throws ServiceException
     */
    @Override
    public User readUser(int userID) throws ServiceException {
        try {
            if (userID > 0) {
                return userDao.readUser(userID);
            } else {
                throw new ServiceValidationException("Negative or zero arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }

    /**
     *
     * @param userID
     * @return boolean if user was deleted
     * @throws ServiceException
     */
    @Override
    public boolean deleteUser(int userID) throws ServiceException {
        try {
            if (userID > 0) {
                return userDao.deleteUser(userID);
            } else {
                throw new ServiceValidationException("Negative or zero arguments are not accepted");
            }
        } catch (DAOException | ServiceValidationException e) {
            throw new ServiceException(e);
        }
    }
}

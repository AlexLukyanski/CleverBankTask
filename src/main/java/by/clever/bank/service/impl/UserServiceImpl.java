package by.clever.bank.service.impl;

import by.clever.bank.bean.User;
import by.clever.bank.dao.DAOFactory;
import by.clever.bank.dao.UserDAO;
import by.clever.bank.dao.exception.DAOException;
import by.clever.bank.service.UserService;
import by.clever.bank.service.exception.ServiceException;

public class UserServiceImpl implements UserService {

    private final static UserDAO userDao = DAOFactory.getInstance().getUserDAO();

    @Override
    public boolean createUser(User user) throws ServiceException {
        try {
            return userDao.createUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateUser(User oldUser, User newUser) throws ServiceException {
        try {
            return userDao.updateUser(oldUser, newUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User readUser(int userID) throws ServiceException {
        try {
            return userDao.readUser(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteUser(int userID) throws ServiceException {
        try {
            return userDao.deleteUser(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}

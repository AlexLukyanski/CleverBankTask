package by.clever.bank.dao;

import by.clever.bank.dao.impl.AccountDAOImpl;
import lombok.Getter;

@Getter
public final class DAOFactory {

    private final AccountDAO accountDAO = new AccountDAOImpl();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return DAOFactoryHelper.instance;
    }

    public static class DAOFactoryHelper {
        private final static DAOFactory instance = new DAOFactory();
    }
}

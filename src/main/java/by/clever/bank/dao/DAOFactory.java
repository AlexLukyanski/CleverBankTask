package by.clever.bank.dao;

import by.clever.bank.dao.impl.ClientDAOImpl;
import lombok.Getter;

@Getter
public final class DAOFactory {

    private ClientDAO clientDAO = new ClientDAOImpl();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return DAOFactoryHelper.instance;
    }

    public static class DAOFactoryHelper {
        private final static DAOFactory instance = new DAOFactory();
    }
}

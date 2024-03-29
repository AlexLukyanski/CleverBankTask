package by.clever.bank.dao;

import by.clever.bank.dao.impl.AccountDAOImpl;
import by.clever.bank.dao.impl.BankDAOImpl;
import by.clever.bank.dao.impl.TransactionDAOImpl;
import by.clever.bank.dao.impl.UserDAOImpl;
import lombok.Getter;

/**
 * Factory for all DAO classes
 */
@Getter
public final class DAOFactory {

    private final AccountDAO accountDAO = new AccountDAOImpl();
    private final TransactionDAO transactionDAO = new TransactionDAOImpl();
    private final BankDAO bankDAO = new BankDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    private DAOFactory() {
    }

    /**
     *
     * @return DAOFactory
     */
    public static DAOFactory getInstance() {
        return DAOFactoryHelper.instance;
    }

    public static class DAOFactoryHelper {
        private final static DAOFactory instance = new DAOFactory();
    }
}

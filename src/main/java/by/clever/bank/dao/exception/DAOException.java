package by.clever.bank.dao.exception;

import java.io.Serial;

/**
 * Class to handle exceptions in DAO. Just as it named
 */
public class DAOException extends Exception {

    @Serial
    private static final long serialVersionUID = 7665657603430758825L;

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String message, Exception e) {
        super(message, e);
    }
}

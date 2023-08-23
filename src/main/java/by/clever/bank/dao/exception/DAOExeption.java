package by.clever.bank.dao.exception;

import java.io.Serial;

public class DAOExeption extends Exception {

    @Serial
    private static final long serialVersionUID = 7665657603430758825L;

    public DAOExeption() {
        super();
    }

    public DAOExeption(String message) {
        super(message);
    }

    public DAOExeption(Exception e) {
        super(e);
    }

    public DAOExeption(String message, Exception e) {
        super(message, e);
    }
}

package by.clever.bank.dao.connectionpool;

import java.io.Serial;

public class ConnectionPoolException extends Exception {

    @Serial
    private static final long serialVersionUID = 2160294707646225541L;

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }

    public ConnectionPoolException() {

    }

    public ConnectionPoolException(String message) {
        super(message);
    }
}

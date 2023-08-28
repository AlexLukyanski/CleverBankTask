package by.clever.bank.service.exception;

import java.io.Serial;

public class ServiceException extends Exception {

    @Serial
    private static final long serialVersionUID = 4052212919521296160L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}

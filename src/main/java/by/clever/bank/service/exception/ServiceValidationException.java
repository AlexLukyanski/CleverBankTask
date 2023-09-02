package by.clever.bank.service.exception;

import java.io.Serial;

/**
 * Class to handle exceptions from validation in service. Just as it named
 */
public class ServiceValidationException extends Exception{
    @Serial
    private static final long serialVersionUID = 4165555630986320156L;


    public ServiceValidationException() {
        super();
    }

    public ServiceValidationException(String message) {
        super(message);
    }

    public ServiceValidationException(Exception e) {
        super(e);
    }

    public ServiceValidationException(String message, Exception e) {
        super(message, e);
    }
}

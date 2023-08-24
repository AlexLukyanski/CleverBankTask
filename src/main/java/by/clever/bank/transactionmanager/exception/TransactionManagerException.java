package by.clever.bank.transactionmanager.exception;

import java.io.Serial;

public class TransactionManagerException extends Exception {
    @Serial
    private static final long serialVersionUID = -8112495250053228913L;

    public TransactionManagerException() {
        super();
    }

    public TransactionManagerException(String message) {
        super(message);
    }

    public TransactionManagerException(Exception e) {
        super(e);
    }

    public TransactionManagerException(String message, Exception e) {
        super(message, e);
    }
}


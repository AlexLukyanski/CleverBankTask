package by.clever.bank.dao;

import java.math.BigDecimal;

public interface ClientDAO {

    boolean addDepositToAccount(BigDecimal amount);
}


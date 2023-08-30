package by.clever.bank.service;

import by.clever.bank.service.exception.ServiceException;

public interface AccrualService {

    void checkNecessity();
    void chargeAccrual() throws ServiceException;
}

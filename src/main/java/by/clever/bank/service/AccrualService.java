package by.clever.bank.service;

import by.clever.bank.service.exception.ServiceException;

/**
 * interface for specified daemon treads in service
 */
public interface AccrualService {

    void checkNecessity();
    void chargeAccrual() throws ServiceException;
}

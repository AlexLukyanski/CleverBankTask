package by.clever.bank.controller.impl;

import by.clever.bank.controller.Command;
import by.clever.bank.service.AccountService;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

public class ChargeAccrualOfInterest implements Command {

    private AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("Invokes through request are not allowed");
    }

    public void chargeAccrual(BigDecimal percentage) {
        try {
            accountService.chargeAccrual(percentage);
        } catch (ServiceException e) {
//            throw new RuntimeException(e);
        }
    }
}

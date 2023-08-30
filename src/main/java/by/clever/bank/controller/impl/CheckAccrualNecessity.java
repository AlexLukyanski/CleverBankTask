package by.clever.bank.controller.impl;

import by.clever.bank.controller.Command;
import by.clever.bank.service.AccountService;
import by.clever.bank.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

public class CheckAccrualNecessity implements Command {

    private AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("Invokes through request are not allowed");
    }

    public boolean checkNecessity() {
        return accountService.checkNecessity();
    }
}

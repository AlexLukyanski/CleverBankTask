package by.clever.bank.controller.impl;

import by.clever.bank.controller.Command;
import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.controller.constant.URLPattern;
import by.clever.bank.service.AccountService;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteAccountCommand implements Command {

    private final static AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            boolean executionResult = accountService.deleteAccount(Integer.parseInt(RequestParam.ACCOUNT_ID));

            if (executionResult) {
                response.sendRedirect(URLPattern.URL_TO_GOOD_PAGE);
            } else {
                response.sendRedirect(URLPattern.REDIRECT_TO_ERROR_PAGE);
            }
        } catch (ServiceException e) {
            response.sendRedirect(URLPattern.REDIRECT_TO_ERROR_PAGE);
        }
    }
}
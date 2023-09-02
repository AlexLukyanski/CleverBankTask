package by.clever.bank.controller.impl;

import by.clever.bank.bean.Account;
import by.clever.bank.controller.Command;
import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.controller.constant.URLPattern;
import by.clever.bank.service.AccountService;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

public class CreateAccountCommand implements Command {

    private final static AccountService accountService = ServiceFactory.getInstance().getAccountService();
    private final static Logger log = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = setAccount(request);
        String bankName = request.getParameter(RequestParam.ACCOUNT_BANK_NAME);
        int userID = Integer.parseInt(request.getParameter(RequestParam.USER_ID));

        try {
            boolean executionResult = accountService.createAccount(account, bankName, userID);

            if (executionResult) {
                response.sendRedirect(URLPattern.URL_TO_GOOD_PAGE);
            } else {
                response.sendRedirect(URLPattern.REDIRECT_TO_ERROR_PAGE);
            }
        } catch (ServiceException e) {
            log.log(Level.ERROR, "Something's wrong", e);
            response.sendRedirect(URLPattern.REDIRECT_TO_ERROR_PAGE);
        }
    }


    private Account setAccount(HttpServletRequest request) {

        Account account = new Account();
        account.setNumber(request.getParameter(RequestParam.NEW_ACCOUNT_NUMBER));
        account.setBalance(new BigDecimal(request.getParameter(RequestParam.NEW_ACCOUNT_BALANCE)));
        return account;
    }
}

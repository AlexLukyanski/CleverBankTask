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
import java.math.BigDecimal;

public class TransferMoneyBetweenAccountsCommand implements Command {
    private final static AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BigDecimal amount = new BigDecimal(request.getParameter(RequestParam.MONEY_AMOUNT));
        String senderAccountNumber = request.getParameter(RequestParam.SENDER_ACCOUNT_NUMBER);
        String receiverAccountNumber = request.getParameter(RequestParam.RECEIVER_ACCOUNT_NUMBER);

        try {
            boolean executionResult = accountService.transferMoneyBetweenAccounts(amount, senderAccountNumber, receiverAccountNumber);

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

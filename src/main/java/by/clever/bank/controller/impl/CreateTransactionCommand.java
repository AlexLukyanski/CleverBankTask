package by.clever.bank.controller.impl;

import by.clever.bank.bean.Transaction;
import by.clever.bank.bean.constant.TransactionType;
import by.clever.bank.controller.Command;
import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.controller.constant.URLPattern;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.TransactionService;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateTransactionCommand implements Command {

    private final static TransactionService transactionService = ServiceFactory.getInstance().getTransactionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = setTransaction(request);
        int accountID = Integer.parseInt(request.getParameter(RequestParam.ACCOUNT_ID));

        try {
            boolean executionResult = transactionService.createTransaction(transaction, accountID);

            if (executionResult) {
                response.sendRedirect(URLPattern.URL_TO_GOOD_PAGE);
            } else {
                response.sendRedirect(URLPattern.REDIRECT_TO_ERROR_PAGE);
            }
        } catch (ServiceException e) {
            response.sendRedirect(URLPattern.REDIRECT_TO_ERROR_PAGE);
        }
    }

    private Transaction setTransaction(HttpServletRequest request) {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.valueOf(request.getParameter(RequestParam.NEW_TRANSACTION_TYPE)));
        transaction.setDateTime(LocalDateTime.parse(request.getParameter(RequestParam.NEW_TRANSACTION_DATETIME)));
        transaction.setAmount(new BigDecimal(request.getParameter(RequestParam.NEW_TRANSACTION_AMOUNT)));
        return transaction;
    }
}

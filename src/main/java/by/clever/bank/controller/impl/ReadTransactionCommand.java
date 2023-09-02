package by.clever.bank.controller.impl;

import by.clever.bank.bean.Transaction;
import by.clever.bank.controller.Command;
import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.controller.constant.URLPattern;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.TransactionService;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ReadTransactionCommand implements Command {

    private final static TransactionService transactionService = ServiceFactory.getInstance().getTransactionService();
    private final static Logger log = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Transaction transaction = transactionService.readTransaction(Integer.parseInt(request.getParameter(RequestParam.TRANSACTION_ID)));

            if (transaction != null) {

                request.setAttribute(RequestParam.KEY_TO_TRANSACTION_BEAN, transaction);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(URLPattern.URL_TO_GOOD_PAGE);

                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                } else {
                    response.sendRedirect(URLPattern.REDIRECT_TO_ERROR_PAGE);
                }

            } else {
                response.sendRedirect(URLPattern.REDIRECT_TO_ERROR_PAGE);
            }
        } catch (ServiceException e) {
            log.log(Level.ERROR, "Something's wrong", e);
            response.sendRedirect(URLPattern.REDIRECT_TO_ERROR_PAGE);
        }
    }
}

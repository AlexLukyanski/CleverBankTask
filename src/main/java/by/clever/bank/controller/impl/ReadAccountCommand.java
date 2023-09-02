package by.clever.bank.controller.impl;

import by.clever.bank.bean.Account;
import by.clever.bank.controller.Command;
import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.controller.constant.URLPattern;
import by.clever.bank.service.AccountService;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ReadAccountCommand implements Command {

    private final static AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Account account = accountService.readAccount(Integer.parseInt(request.getParameter(RequestParam.ACCOUNT_ID)));

            if (account != null) {

                request.setAttribute(RequestParam.KEY_TO_ACCOUNT_BEAN, account);
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
            response.sendRedirect(URLPattern.REDIRECT_TO_ERROR_PAGE);
        }
    }
}

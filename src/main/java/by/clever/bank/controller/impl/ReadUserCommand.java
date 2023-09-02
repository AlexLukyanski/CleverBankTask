package by.clever.bank.controller.impl;

import by.clever.bank.bean.User;
import by.clever.bank.controller.Command;
import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.controller.constant.URLPattern;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.UserService;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ReadUserCommand implements Command {

    private final static UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = userService.readUser(Integer.parseInt(request.getParameter(RequestParam.USER_ID)));

            if (user != null) {

                request.setAttribute(RequestParam.KEY_TO_USER_BEAN, user);
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

package by.clever.bank.controller.impl;

import by.clever.bank.bean.User;
import by.clever.bank.controller.Command;
import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.controller.constant.URLPattern;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.UserService;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;

public class UpdateUserCommand implements Command {

    private final static UserService userService = ServiceFactory.getInstance().getUserService();
    private final static Logger log = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User oldUser = setOldUser(request);
        User newUser = setNewUser(request);

        try {
            boolean executionResult = userService.updateUser(oldUser, newUser);

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

    private User setOldUser (HttpServletRequest request){
        User user = new User();
        user.setName(request.getParameter(RequestParam.OLD_USER_NAME));
        user.setSurname(request.getParameter(RequestParam.OLD_USER_SURNAME));
        user.setPatronymic(request.getParameter(RequestParam.OLD_USER_PATRONYMIC));
        user.setDateOfBirth(LocalDate.parse(request.getParameter(RequestParam.OLD_USER_DATE_OF_BIRTH)));
        user.setPhoneNumber(request.getParameter(RequestParam.OLD_USER_PHONE_NUMBER));
        user.setEmail(request.getParameter(RequestParam.OLD_USER_EMAIL));
        return user;
    }

    private User setNewUser(HttpServletRequest request) {

        User user = new User();
        user.setName(request.getParameter(RequestParam.NEW_USER_NAME));
        user.setSurname(request.getParameter(RequestParam.NEW_USER_SURNAME));
        user.setPatronymic(request.getParameter(RequestParam.NEW_USER_PATRONYMIC));
        user.setDateOfBirth(LocalDate.parse(request.getParameter(RequestParam.NEW_USER_DATE_OF_BIRTH)));
        user.setPhoneNumber(request.getParameter(RequestParam.NEW_USER_PHONE_NUMBER));
        user.setEmail(request.getParameter(RequestParam.NEW_USER_EMAIL));
        return user;
    }
}

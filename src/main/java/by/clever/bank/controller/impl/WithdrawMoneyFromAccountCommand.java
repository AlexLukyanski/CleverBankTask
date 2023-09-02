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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Proceed specified command as it goes by its name
 */
public class WithdrawMoneyFromAccountCommand implements Command {

    private final static AccountService accountService = ServiceFactory.getInstance().getAccountService();
    private final static Logger log = LogManager.getRootLogger();

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BigDecimal amount = new BigDecimal(request.getParameter(RequestParam.MONEY_AMOUNT));
        String accountNumber = request.getParameter(RequestParam.ACCOUNT_NUMBER);

        try {
            boolean executionResult = accountService.withdrawMoneyFromAccount(amount, accountNumber);

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
}

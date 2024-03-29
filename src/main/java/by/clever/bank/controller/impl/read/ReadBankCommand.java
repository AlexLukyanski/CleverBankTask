package by.clever.bank.controller.impl.read;

import by.clever.bank.bean.Bank;
import by.clever.bank.controller.Command;
import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.controller.constant.URLPattern;
import by.clever.bank.service.BankService;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Proceed specified command as it goes by its name
 */
public class ReadBankCommand implements Command {

    private final static BankService bankService = ServiceFactory.getInstance().getBankService();
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
        try {
            Bank bank = bankService.readBank(request.getParameter(RequestParam.KEY_TO_BANK_NAME));

            if (bank != null) {

                request.setAttribute(RequestParam.KEY_TO_BANK_BEAN, bank);
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

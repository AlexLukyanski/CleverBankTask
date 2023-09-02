package by.clever.bank.controller.impl.update;

import by.clever.bank.bean.Bank;
import by.clever.bank.controller.Command;
import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.controller.constant.URLPattern;
import by.clever.bank.service.BankService;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class UpdateBankCommand implements Command {

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

        Bank oldBank = setOldBank(request);
        Bank newBank = setNewBank(request);

        try {
            boolean executionResult = bankService.updateBank(oldBank, newBank);

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

    private Bank setOldBank(HttpServletRequest request) {

        Bank bank = new Bank();
        bank.setName(request.getParameter(RequestParam.OLD_BANK_NAME));
        return bank;
    }

    private Bank setNewBank(HttpServletRequest request) {

        Bank bank = new Bank();
        bank.setName(request.getParameter(RequestParam.NEW_BANK_NAME));
        return bank;
    }
}

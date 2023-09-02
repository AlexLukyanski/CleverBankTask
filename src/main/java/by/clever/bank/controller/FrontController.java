package by.clever.bank.controller;

import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.dao.connectionpool.ConnectionPool;
import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import by.clever.bank.service.AccrualService;
import by.clever.bank.service.ServiceFactory;
import by.clever.bank.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The only servlet for this app. Delegate process requests to commands via CommandFactory
 *
 */
public final class FrontController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 6765026817858525349L;
    private static final CommandFactory commandFactory = new CommandFactory();
    private final static Logger log = LogManager.getRootLogger();

    public FrontController() {
        super();
    }

    /**
     * Initialize connection pool and two daemon threads here
     */
    @Override
    public void init() {
        initializeConnectionPool();
        accrualOfInterest();
    }

    @Override
    public void destroy() {

        try {
            ConnectionPool instance = ConnectionPool.getInstance();
            instance.clearConnectionQueue();
        } catch (ConnectionPoolException e) {
            log.log(Level.ERROR, "ConnectionPool broken", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        executeRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        executeRequest(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        executeRequest(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        executeRequest(request, response);
    }

    private void executeRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final String contentType = "text/html";
        response.setContentType(contentType);

        String name = request.getParameter(RequestParam.FRONT_CONTROLLER_ATTRIBUTE);
        Command command = commandFactory.getCommand(name);
        command.execute(request, response);
    }

    private void initializeConnectionPool() {
        try {
            ConnectionPool instance = ConnectionPool.getInstance();
            instance.initPoolData();

        } catch (ConnectionPoolException e) {
            log.log(Level.ERROR, "ConnectionPool broken", e);
        }
    }

    private void accrualOfInterest() {

        AccrualService accrualService = ServiceFactory.getInstance().getAccrualService();
        int defaultThreadPoolSize = 2;

        Runnable checkNecessity = accrualService::checkNecessity;
        Runnable chargeAccrual = () -> {
            try {
                accrualService.chargeAccrual();
            } catch (ServiceException e) {
                log.log(Level.ERROR, "Something broken", e);
            }
        };

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(defaultThreadPoolSize);
        executorService.scheduleAtFixedRate(checkNecessity, 30, 30, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(chargeAccrual, 1, 1, TimeUnit.DAYS);
    }
}

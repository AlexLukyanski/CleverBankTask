package by.clever.bank.controller;

import by.clever.bank.controller.async.AccrualOfInterest;
import by.clever.bank.controller.constant.RequestParam;
import by.clever.bank.dao.connectionpool.ConnectionPool;
import by.clever.bank.dao.connectionpool.ConnectionPoolException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

public final class FrontController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 6765026817858525349L;
    private static final CommandFactory commandFactory = new CommandFactory();

    public FrontController() {
        super();
    }

    @Override
    public void init() {
        initializeConnectionPool();

    }

    @Override
    public void destroy() {

        try {
            ConnectionPool instance = ConnectionPool.getInstance();
            instance.clearConnectionQueue();
        } catch (ConnectionPoolException e) {
//            log.log(Level.ERROR, "ConnectionPool broken", e);
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
//            log.log(Level.ERROR, "ConnectionPool broken", e);
        }
    }
}

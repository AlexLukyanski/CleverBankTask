package by.clever.bank.controller;

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

    public FrontController() {
        super();
    }

    @Override
    public void init() {
        try {
            ConnectionPool instance = ConnectionPool.getInstance();
            instance.initPoolData();

        } catch (ConnectionPoolException e) {
//            log.log(Level.ERROR, "ConnectionPool broken", e);
        }
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


    }
}

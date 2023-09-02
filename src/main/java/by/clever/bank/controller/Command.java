package by.clever.bank.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Interface for commands to implement. Each command process one and only one request
 */
public interface Command {

    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}

package com.nikitin.webproject.servlet;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.manager.MessageManager;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.manager.SessionManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Main controller to handle all requests.
 */
public class Controller extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Controller.class);
    private RequestHelper requestHelper = RequestHelper.getInstance();

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;

        HttpSession session = req.getSession(false);
        if (session == null || !req.isRequestedSessionIdValid()) {
            String errorMessage = MessageManager.getInstance().getProperty(MessageManager.SESSION_INTERRUPTED);
            req.setAttribute(SessionManager.getInstance().getProperty(SessionManager.ERROR_MESSAGE), errorMessage);
            page = PageManager.getInstance().getProperty(PageManager.ERROR_PAGE);
        } else {
            try {
                Command command = requestHelper.getCommand(req);
                page = command.execute(req, resp);
            } catch (ServletException e) {
                LOGGER.error("ServletException in Controller", e);
                String errorMessage = MessageManager.getInstance().getProperty(MessageManager.SERVLET_EXCEPTION);
                req.setAttribute(SessionManager.getInstance().getProperty(SessionManager.ERROR_MESSAGE), errorMessage);
                page = PageManager.getInstance().getProperty(PageManager.ERROR_PAGE);
            } catch (IOException e) {
                LOGGER.error("IOException in Controller", e);
                String errorMessage = MessageManager.getInstance().getProperty(MessageManager.IO_EXCEPTION);
                req.setAttribute(SessionManager.getInstance().getProperty(SessionManager.ERROR_MESSAGE), errorMessage);
                page = PageManager.getInstance().getProperty(PageManager.ERROR_PAGE);
            }
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.database.entity.*;
import com.nikitin.webproject.database.util.UserType;
import com.nikitin.webproject.manager.MessageManager;
import com.nikitin.webproject.manager.SessionManager;
import com.nikitin.webproject.service.MainMenu;
import com.nikitin.webproject.service.Service;
import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.manager.PageManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command. Login to application.
 */
public class LoginCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;

        /** Get login and password from session. */
        String login = request.getParameter(SessionManager.getInstance().getProperty(SessionManager.LOGIN));
        String pass = request.getParameter(SessionManager.getInstance().getProperty(SessionManager.PASSWORD));

        /** Get user from database. */
        User user = service.signIn(login, pass);

        /** EN - default language. */
        Language language = service.getLang(SessionManager.getInstance().getProperty(SessionManager.EN_LANG));
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_LANG), language);

        /** If user admin */
        if (user != null && user.getType().equals(UserType.ADMIN)) {

            /** Get necessary data from database. */
            List<MainMenu> menu = service.refreshRoutes();
            int freeBuses = service.freeBusesQuantity();
            int freeDrivers = service.freeDriversQuantity();

            /** Add to session necessary data to display it on page. */
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.USER), user);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.MENU), menu);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.FREE_BUSES), freeBuses);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.FREE_DRIVERS), freeDrivers);

            /** Go to another page, and set this page as "current" to session. */
            page = PageManager.getInstance().getProperty(PageManager.MAIN_PAGE);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE), page);

            /** If user driver */
        } else if (user != null && user.getType().equals(UserType.DRIVER)) {

            /** Get necessary data from database. */
            Bus bus = service.getBusById(user.getBusId());

            BusContent busContent = null;
            Route route = null;

            if (bus != null) {
                busContent = service.getBusContent(bus, language);
                route = service.getRouteById(bus.getRouteId());
            }

            /** Add to session necessary data to display it on page. */
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.USER), user);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS), bus);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS_CONTENT), busContent);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.ROUTE), route);

            /** Go to another page, and set this page as "current" to session. */
            page = PageManager.getInstance().getProperty(PageManager.CLIENT_PAGE);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE), page);

        } else {
            /** Go to error page. */
            String errorMessage = MessageManager.getInstance().getProperty(MessageManager.LOGIN_ERROR_MESSAGE);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.ERROR_MESSAGE), errorMessage);
            page = PageManager.getInstance().getProperty(PageManager.ERROR_PAGE);
        }

        return page;
    }
}

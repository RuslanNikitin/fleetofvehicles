package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.manager.SessionManager;
import com.nikitin.webproject.service.MainMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command. Main menu (Routes).
 */
public class RoutesCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /** Get necessary data from database. */
        List<MainMenu> menu = service.refreshRoutes();
        int freeBuses = service.freeBusesQuantity();
        int freeDrivers = service.freeDriversQuantity();

        /** Add to session necessary data to display it on page. */
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.MENU), menu);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.FREE_BUSES), freeBuses);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.FREE_DRIVERS), freeDrivers);

        /** Go to another page, and set this page as "current" to session. */
        String page = PageManager.getInstance().getProperty(PageManager.MAIN_PAGE);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE), page);

        return page;
    }
}

package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.manager.SessionManager;
import com.nikitin.webproject.service.DriverMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command. Show all drivers.
 */
public class DriversCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /** Get current language from session. */
        Language language = (Language) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_LANG));

        /** Refresh and add to session necessary data to display it on page. */
        List<DriverMenu> driverMenu = service.refreshDrivers(language);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.DRIVER_MENU), driverMenu);

        /** Go to another page, and set this page as "current" to session. */
        String page = PageManager.getInstance().getProperty(PageManager.DRIVERS_PAGE);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE), page);

        return page;
    }
}

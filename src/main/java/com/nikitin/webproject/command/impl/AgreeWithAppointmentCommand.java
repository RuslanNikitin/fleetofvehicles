package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.*;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.manager.SessionManager;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command. When driver is agreed with appointment and confirm it.
 */
public class AgreeWithAppointmentCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /** Get current page from session. */
        String page = (String) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE));

        /** Get current language from session. */
        Language language = (Language) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_LANG));

        /** Get bus and driver from session. */
        Bus bus = (Bus) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS));
        User driver = (User) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.USER));

        /** Update bus and driver status. */
        service.updateBusStatus(bus, Status.APPROVED);
        bus.setStatus(Status.APPROVED);
        service.updateDriverStatus(driver, Status.APPROVED);
        driver.setStatus(Status.APPROVED);

        /** Get necessary data from database. */
        BusContent busContent = service.getBusContent(bus, language);
        Route route = service.getRouteById(bus.getRouteId());

        /** Refresh and add to session necessary data to display it on page. */
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.USER), driver);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS), bus);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS_CONTENT), busContent);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.ROUTE), route);

        return page;
    }
}
package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.manager.SessionManager;
import com.nikitin.webproject.service.DriverMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command. Take off driver from bus.
 */
public class SetDriverToFreeCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int zeroID = 0;

        /** Get current page and language from session. */
        String page = (String) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE));
        Language language = (Language) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_LANG));

        /** Get driver ID from session. */
        int driverId = Integer.parseInt(request.getParameter(SessionManager.getInstance().getProperty(SessionManager.DRIVER_ID)));

        /** Get driver and bus from session. */
        User driver = service.getUserByUserId(driverId);
        Bus bus = service.getBusById(driver.getBusId());

        /** Update necessary data in database. */
        service.updateDriverStatus(driver, Status.AWAITING);
        service.updateBusStatus(bus, Status.AWAITING);
        service.updateDriverByBusId(driverId, zeroID);
        service.updateBusRouteId(bus, zeroID);

        /** Refresh and add to session necessary data to display it on page. */
        List<DriverMenu> driverMenu = service.refreshDrivers(language);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.DRIVER_MENU), driverMenu);

        return page;
    }
}
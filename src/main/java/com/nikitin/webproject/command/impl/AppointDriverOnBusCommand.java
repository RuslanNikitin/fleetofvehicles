package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.manager.SessionManager;
import com.nikitin.webproject.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command. Appoint driver on bus.
 */
public class AppointDriverOnBusCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /** Get current page from session. */
        String page = (String) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE));

        /** Get data from session. */
        int busId = Integer.parseInt(request.getParameter(SessionManager.getInstance().getProperty(SessionManager.BUS_ID)));
        int driverId = Integer.parseInt(request.getParameter(SessionManager.getInstance().getProperty(SessionManager.SELECTED_DRIVER)));

        /** Get current language from session. */
        Language language = (Language) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_LANG));

        /** Get data from database. */
        Bus bus = service.getBusById(busId);
        User driver = service.getDriverByBusId(bus);

        /** Update data in database. */
        service.updateDriverStatus(driver, Status.AWAITING);
        service.updateDriverByBusId(driverId, busId);

        /** Refresh and add to session necessary data to display it on page. */
        List<BusMenu> busMenu = service.refreshBuses(language);
        List<User> freeDriversList = service.freeDrivers();
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS_MENU), busMenu);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.FREE_DRIVER_LIST), freeDriversList);

        return page;
    }
}

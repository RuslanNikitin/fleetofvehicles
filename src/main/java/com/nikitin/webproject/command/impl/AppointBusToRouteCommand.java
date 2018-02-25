package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.entity.Route;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.manager.SessionManager;
import com.nikitin.webproject.service.BusWithContent;
import com.nikitin.webproject.service.RouteNumberMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command. Set bus on the route.
 */
public class AppointBusToRouteCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /** Get current page from session. */
        String page = (String) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE));

        /** Get bus from session. */
        int busId = Integer.parseInt(request.getParameter(SessionManager.getInstance().getProperty(SessionManager.SELECTED_BUS)));
        Bus bus = service.getBusById(busId);

        /** Get route from session. */
        Route route = (Route) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_ROUTE));

        /** Update data in database. */
        service.updateBusStatus(bus, Status.AWAITING);
        service.updateBusRouteId(bus, route.getId());

        /** Get current language from session. */
        Language language = (Language) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_LANG));

        /** Refresh and add to session necessary data to display it on page. */
        List<RouteNumberMenu> routeNumberMenu = service.refreshRouteNumberMenu(route, language);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.ROUTE_NUMBER_MENU), routeNumberMenu);
        List<BusWithContent> freeBuses = service.freeBuses(language);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.FREE_BUSES_LIST), freeBuses);

        return page;
    }
}
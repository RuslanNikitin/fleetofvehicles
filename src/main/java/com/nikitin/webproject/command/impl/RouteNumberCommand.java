package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.*;
import com.nikitin.webproject.manager.PageManager;
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
 * Command. Remove bus from the route.
 */
public class RouteNumberCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /** Get route ID and current language from session. */
        Language language = (Language) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_LANG));
        int routeId = Integer.parseInt(request.getParameter(SessionManager.getInstance().getProperty(SessionManager.ROUTE_ID)));

        /** Get necessary data from database. */
        Route route = service.getRouteById(routeId);
        List<RouteNumberMenu> routeNumberMenu = service.refreshRouteNumberMenu(route, language);
        List<BusWithContent> freeBuses = service.freeBuses(language);

        /** Add to session necessary data to display it on page. */
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.ROUTE_NUMBER_MENU), routeNumberMenu);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_ROUTE), route);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.FREE_BUSES_LIST), freeBuses);

        /** Go to another page, and set this page as "current" to session. */
        String page = PageManager.getInstance().getProperty(PageManager.ROUTE_NUMBER);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE), page);

        return page;
    }
}

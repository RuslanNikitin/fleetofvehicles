package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.entity.Route;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.service.BusMenu;
import com.nikitin.webproject.service.DriverMenu;
import com.nikitin.webproject.service.RouteNumberMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LanguageCommand implements Command {
    Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page = (String) request.getSession().getAttribute("currentPage");
        Language language = service.getLang(request.getParameter("lang"));

        // EN - default language
        if(language == null) {
            language = service.getLang("EN");
        }

        if(page != null && page.equals(PageManager.getInstance().getProperty(PageManager.ROUTE_NUMBER))) {
            Route route = (Route) request.getSession().getAttribute("currentRoute");
            List<RouteNumberMenu> routeNumberMenu = service.refreshRouteNumberMenu(route, language);
            request.getSession().setAttribute("routeNumberMenu", routeNumberMenu);
        }

        if(page != null && page.equals(PageManager.getInstance().getProperty(PageManager.BUSES_PAGE))) {
            List<BusMenu> busMenu = service.refreshBuses(language);
            request.getSession().setAttribute("busMenu", busMenu);
        }

        if(page != null && page.equals(PageManager.getInstance().getProperty(PageManager.DRIVERS_PAGE))) {
            List<DriverMenu> driverMenu = service.refreshDrivers(language);
            request.getSession().setAttribute("driverMenu", driverMenu);
        }

        request.getSession().setAttribute("currentLang", language);

        return page;
    }
}






























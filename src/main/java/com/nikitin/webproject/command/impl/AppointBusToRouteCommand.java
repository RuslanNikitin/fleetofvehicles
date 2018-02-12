package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.entity.Route;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.service.BusWithContent;
import com.nikitin.webproject.service.RouteNumberMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AppointBusToRouteCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = (String) request.getSession().getAttribute("currentPage");

        int busId = Integer.parseInt(request.getParameter("selectedBus"));

        Bus bus = service.getBusById(busId);

        Route route = (Route) request.getSession().getAttribute("currentRoute");

        service.updateBusStatus(bus, Status.AWAITING);

        service.updateBusRouteId(bus, route.getId());

        Language language = service.getLang(request.getParameter("lang"));

        // EN - default language
        if(language == null) {
            language = service.getLang("EN");
        }

        if(page != null && page.equals(PageManager.getInstance().getProperty(PageManager.ROUTE_NUMBER))) {
            List<RouteNumberMenu> routeNumberMenu = service.refreshRouteNumberMenu(route, language);
            request.getSession().setAttribute("routeNumberMenu", routeNumberMenu);
            List<BusWithContent> freeBuses = service.freeBuses(language);
            request.getSession().setAttribute("freeBusesList", freeBuses);
        }

        return page;
    }
}

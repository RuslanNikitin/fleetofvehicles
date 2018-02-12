package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.entity.Route;
import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.database.util.UserType;
import com.nikitin.webproject.service.RouteNumberMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RemoveBusFromRouteCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = (String) request.getSession().getAttribute("currentPage");

        Language language = (Language) request.getSession().getAttribute("currentLang");

        // EN - default language
        if (language == null) {
            language = service.getLang("EN");
        }

        int busId = Integer.parseInt(request.getParameter("busId"));
        Bus bus = service.getBusById(busId);

        Route route = (Route) request.getSession().getAttribute("currentRoute");

        service.updateBusRouteId(bus, 0);
        service.updateBusStatus(bus, Status.AWAITING);

        User driver = service.getUserByBusId(bus);

        if(driver != null) {
            service.updateUserStatus(driver, Status.AWAITING);
            service.updateDriverByBusId(driver.getId(), 0);
        }

        List<RouteNumberMenu> routeNumberMenu = service.refreshRouteNumberMenu(route, language);

        request.getSession().setAttribute("routeNumberMenu", routeNumberMenu);
        request.getSession().setAttribute("currentPage", page);

        return page;
    }
}

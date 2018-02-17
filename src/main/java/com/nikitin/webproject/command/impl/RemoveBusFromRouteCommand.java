package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.entity.Route;
import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.database.util.UserType;
import com.nikitin.webproject.manager.SessionManager;
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
        int zeroID = 0;
        String page = (String) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE));
        Language language = (Language) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_LANG));

        // EN - default language
        if (language == null) {
            language = service.getLang(SessionManager.getInstance().getProperty(SessionManager.EN_LANG));
        }

        int busId = Integer.parseInt(request.getParameter(SessionManager.getInstance().getProperty(SessionManager.BUS_ID)));
        Bus bus = service.getBusById(busId);

        Route route = (Route) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_ROUTE));

        service.updateBusRouteId(bus, zeroID);
        service.updateBusStatus(bus, Status.AWAITING);

        User driver = service.getUserByBusId(bus);

        if(driver != null) {
            service.updateUserStatus(driver, Status.AWAITING);
            service.updateDriverByBusId(driver.getId(), zeroID);
        }

        List<RouteNumberMenu> routeNumberMenu = service.refreshRouteNumberMenu(route, language);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.ROUTE_NUMBER_MENU), routeNumberMenu);

        return page;
    }
}

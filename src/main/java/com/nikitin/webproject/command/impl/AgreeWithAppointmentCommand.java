package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.*;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AgreeWithAppointmentCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;

        Language language = service.getLang("EN");      // default lang

        Bus bus = (Bus) request.getSession().getAttribute("bus");
        User user = (User) request.getSession().getAttribute("user");

        service.updateBusStatus(bus, Status.APPROVED);
        service.updateUserStatus(user, Status.APPROVED);

        BusContent busContent = null;
        Route route = null;

        if (bus != null) {
            busContent = service.getBusContent(bus, language);
            route = service.getRouteById(bus.getRouteId());
        }

        bus = service.getBusById(user.getBusId());
        user = service.getUserByBusId(bus);

        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("bus", bus);
        request.getSession().setAttribute("busContent", busContent);
        request.getSession().setAttribute("route", route);

        return PageManager.getInstance().getProperty(PageManager.CLIENT_PAGE);
    }
}

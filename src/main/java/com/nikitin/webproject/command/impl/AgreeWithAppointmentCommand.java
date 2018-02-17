package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.*;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.manager.SessionManager;
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

        // EN - default lang
        Language language = service.getLang(SessionManager.getInstance().getProperty(SessionManager.EN_LANG));

        Bus bus = (Bus) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS));
        User driver = (User) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.USER));

        service.updateBusStatus(bus, Status.APPROVED);
        service.updateUserStatus(driver, Status.APPROVED);

        BusContent busContent = null;
        Route route = null;

        if (bus != null) {
            busContent = service.getBusContent(bus, language);
            route = service.getRouteById(bus.getRouteId());
            bus.setStatus(Status.APPROVED);
            driver.setStatus(Status.APPROVED);
        }

//        bus = service.getBusById(user.getBusId());
//        user = service.getUserByBusId(bus);



        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.USER), driver);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS), bus);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS_CONTENT), busContent);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.ROUTE), route);

        page = PageManager.getInstance().getProperty(PageManager.CLIENT_PAGE);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE), page);

        return page;
    }
}

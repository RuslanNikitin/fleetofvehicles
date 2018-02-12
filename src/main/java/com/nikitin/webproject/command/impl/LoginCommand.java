package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.database.entity.*;
import com.nikitin.webproject.database.util.UserType;
import com.nikitin.webproject.manager.MessageManager;
import com.nikitin.webproject.service.MainMenu;
import com.nikitin.webproject.service.Service;
import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.manager.PageManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;

        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        User user = service.signIn(login, pass);

        // default language - EN
        Language language = service.getLang("EN");

        if (user != null && user.getType().equals(UserType.ADMIN)) {

            List<MainMenu> menu = service.refreshRoutes();
            int freeBuses = service.freeBusesQuantity();
            int freeDrivers = service.freeDriversQuantity();

            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("menu", menu);
            request.getSession().setAttribute("freeBuses", freeBuses);
            request.getSession().setAttribute("freeDrivers", freeDrivers);
            page = PageManager.getInstance().getProperty(PageManager.MAIN_PAGE);
            request.getSession().setAttribute("currentPage", page);

        } else if (user != null && user.getType().equals(UserType.DRIVER)) {

            Bus bus = service.getBusById(user.getBusId());
            BusContent busContent = null;
            Route route = null;

            if (bus != null) {
                busContent = service.getBusContent(bus, language);
                route = service.getRouteById(bus.getRouteId());
            }

            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("bus", bus);
            request.getSession().setAttribute("busContent", busContent);
            request.getSession().setAttribute("route", route);
            page = PageManager.getInstance().getProperty(PageManager.CLIENT_PAGE);
            request.getSession().setAttribute("currentPage", page);

        } else {
            String errorMessage = MessageManager.getInstance().getProperty(MessageManager.LOGIN_ERROR_MESSAGE);
            request.getSession().setAttribute("errorMessage", errorMessage);
            page = PageManager.getInstance().getProperty(PageManager.ERROR_PAGE);
        }

        request.getSession().setAttribute("currentLang", language);

        return page;
    }
}

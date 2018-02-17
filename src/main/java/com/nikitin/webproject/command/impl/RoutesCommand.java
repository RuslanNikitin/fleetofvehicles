package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.util.UserType;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.manager.SessionManager;
import com.nikitin.webproject.service.MainMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoutesCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;

        List<MainMenu> menu = service.refreshRoutes();
        int freeBuses = service.freeBusesQuantity();
        int freeDrivers = service.freeDriversQuantity();

        User user = (User) request.getSession().getAttribute(SessionManager.getInstance().getProperty(SessionManager.USER));

        if (user != null && user.getType().equals(UserType.ADMIN)) {
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.MENU), menu);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.FREE_BUSES), freeBuses);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.FREE_DRIVERS), freeDrivers);
            page = PageManager.getInstance().getProperty(PageManager.MAIN_PAGE);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE), page);
        } else {
            page = PageManager.getInstance().getProperty(PageManager.ERROR_PAGE);
        }

        return page;
    }
}

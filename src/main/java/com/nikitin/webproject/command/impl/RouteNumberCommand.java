package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.*;
import com.nikitin.webproject.database.util.UserType;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.service.BusWithContent;
import com.nikitin.webproject.service.RouteNumberMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RouteNumberCommand implements Command{
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;

        int routeId = Integer.parseInt(request.getParameter("routeId"));

        Route route = service.getRouteById(routeId);

        Language language = (Language) request.getSession().getAttribute("currentLang");

        // EN - default language
        if(language == null) {
            language = service.getLang("EN");
        }

        List<RouteNumberMenu> routeNumberMenu = service.refreshRouteNumberMenu(route, language);

        List<BusWithContent> freeBuses = service.freeBuses(language);

        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getType().equals(UserType.ADMIN)) {
            request.getSession().setAttribute("routeNumberMenu", routeNumberMenu);
            request.getSession().setAttribute("currentRoute", route);
            request.getSession().setAttribute("freeBusesList", freeBuses);
            page = PageManager.getInstance().getProperty(PageManager.ROUTE_NUMBER);
            request.getSession().setAttribute("currentPage", page);
        } else {
            page = PageManager.getInstance().getProperty(PageManager.ERROR_PAGE);
        }

        return page;
    }
}

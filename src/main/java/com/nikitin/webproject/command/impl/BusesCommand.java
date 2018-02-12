package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.util.UserType;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.service.BusMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BusesCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;

        Language language = (Language) request.getSession().getAttribute("currentLang");

        // EN - default language
        if(language == null) {
            language = service.getLang("EN");
        }

        List<BusMenu> busMenu = service.refreshBuses(language);
        int freeBuses = service.freeBusesQuantity();
        int freeDrivers = service.freeDriversQuantity();

        List<User> freeDriversList = service.freeDrivers();

        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getType().equals(UserType.ADMIN)) {
            request.getSession().setAttribute("busMenu", busMenu);
            request.getSession().setAttribute("freeBuses", freeBuses);
            request.getSession().setAttribute("freeDrivers", freeDrivers);
            request.getSession().setAttribute("freeDriversList", freeDriversList);
            page = PageManager.getInstance().getProperty(PageManager.BUSES_PAGE);
            request.getSession().setAttribute("currentPage", page);
        } else {
            page = PageManager.getInstance().getProperty(PageManager.ERROR_PAGE);
        }

        return page;
    }
}

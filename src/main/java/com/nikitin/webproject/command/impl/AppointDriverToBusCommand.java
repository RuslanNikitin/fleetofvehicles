package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AppointDriverToBusCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = (String) request.getSession().getAttribute("currentPage");

        int busId = Integer.parseInt(request.getParameter("busId"));
        int driverId = Integer.parseInt(request.getParameter("selectedDriver"));

        Language language = (Language) request.getSession().getAttribute("currentLang");

        // EN - default language
        if(language == null) {
            language = service.getLang("EN");
        }

        Bus bus = service.getBusById(busId);
        User driver = service.getUserByBusId(bus);

        service.updateUserStatus(driver, Status.AWAITING);
        service.updateDriverByBusId(driverId, busId);

        if(page != null && page.equals(PageManager.getInstance().getProperty(PageManager.BUSES_PAGE))) {
            List<BusMenu> busMenu = service.refreshBuses(language);
            List<User> freeDriversList = service.freeDrivers();
            request.getSession().setAttribute("busMenu", busMenu);
            request.getSession().setAttribute("freeDriversList", freeDriversList);
            page = PageManager.getInstance().getProperty(PageManager.BUSES_PAGE);
            request.getSession().setAttribute("currentPage", page);
        } else {
            page = PageManager.getInstance().getProperty(PageManager.ERROR_PAGE);
        }

        return page;
    }
}

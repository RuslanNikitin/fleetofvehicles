package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.database.util.UserType;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.service.DriverMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SetDriverToFreeCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = (String) request.getSession().getAttribute("currentPage");

        int driverId = Integer.parseInt(request.getParameter("driverId"));

        Language language = (Language) request.getSession().getAttribute("currentLang");

        // EN - default language
        if(language == null) {
            language = service.getLang("EN");
        }

        User driver = service.getUserByUserId(driverId);
        Bus bus = service.getBusById(driver.getBusId());

        service.updateUserStatus(driver, Status.AWAITING);
        service.updateBusStatus(bus, Status.AWAITING);

        service.updateDriverByBusId(driverId, 0);
        service.updateBusRouteId(bus, 0);

        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getType().equals(UserType.ADMIN)) {
            List<DriverMenu> driverMenu = service.refreshDrivers(language);
            request.getSession().setAttribute("driverMenu", driverMenu);
            request.getSession().setAttribute("currentPage", page);
        } else {
            page = PageManager.getInstance().getProperty(PageManager.ERROR_PAGE);
        }

        return page;

    }
}

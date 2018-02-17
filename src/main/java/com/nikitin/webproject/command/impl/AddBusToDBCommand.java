package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.BusContent;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.manager.SessionManager;
import com.nikitin.webproject.service.BusMenu;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddBusToDBCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;

        Language language = service.getLang(request.getParameter(SessionManager.getInstance().getProperty(SessionManager.LANG)));

        // EN - default language
        if (language == null) {
            language = service.getLang(SessionManager.getInstance().getProperty(SessionManager.EN_LANG));
        }

        String brandEN = request.getParameter(SessionManager.getInstance().getProperty(SessionManager.BRAND_EN));
        String modelEN = request.getParameter(SessionManager.getInstance().getProperty(SessionManager.MODEL_EN));
        String colorEN = request.getParameter(SessionManager.getInstance().getProperty(SessionManager.COLOR_EN));
        String brandRU = request.getParameter(SessionManager.getInstance().getProperty(SessionManager.BRAND_RU));
        String modelRU = request.getParameter(SessionManager.getInstance().getProperty(SessionManager.MODEL_RU));
        String colorRU = request.getParameter(SessionManager.getInstance().getProperty(SessionManager.COLOR_RU));
        String busNumber = request.getParameter(SessionManager.getInstance().getProperty(SessionManager.BUS_NUMBER));

        if (   brandEN == null || brandEN.isEmpty()
            || brandRU == null || brandRU.isEmpty()
            || modelEN == null || modelEN.isEmpty()
            || modelRU == null || modelRU.isEmpty()
            || colorEN == null || colorEN.isEmpty()
            || colorRU == null || colorRU.isEmpty()
            || busNumber == null || busNumber.isEmpty()) {

            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BRAND_EN), brandEN);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.MODEL_EN), modelEN);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.COLOR_EN), colorEN);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BRAND_RU), brandRU);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.MODEL_RU), modelRU);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.COLOR_RU), colorRU);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS_NUMBER), busNumber);

            page = PageManager.getInstance().getProperty(PageManager.ADD_BUS_PAGE);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE), page);

        } else {
            int busID = service.addNewBus(new Bus());
            BusContent busContentEN = new BusContent.Builder().setBrand(brandEN).setModel(modelEN).setColor(colorEN).setNumber(busNumber).build();
            BusContent busContentRU = new BusContent.Builder().setBrand(brandRU).setModel(modelRU).setColor(colorRU).setNumber(busNumber).build();

            service.addBusContent(busContentEN, busID, SessionManager.getInstance().getProperty(SessionManager.EN_LANG));
            service.addBusContent(busContentRU, busID, SessionManager.getInstance().getProperty(SessionManager.RU_LANG));

            List<BusMenu> busMenu = service.refreshBuses(language);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS_MENU), busMenu);

            page = PageManager.getInstance().getProperty(PageManager.BUSES_PAGE);
            request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE), page);
        }

        return page;
    }
}


























package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBusToDBCommand implements Command {
    private Service service = Service.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;

        String brandEN = request.getParameter("brandEN");
        String modelEN = request.getParameter("modelEN");
        String colorEN = request.getParameter("colorEN");

        String brandRU = request.getParameter("brandRU");
        String modelRU = request.getParameter("modelRU");
        String colorRU = request.getParameter("colorRU");

        String busNumber = request.getParameter("busNumber");

        Bus bus = new Bus.Builder().setRouteId(0).setStatus(Status.AWAITING).build();

        System.out.println(service.addNewBus(bus));

        page = PageManager.getInstance().getProperty(PageManager.BUSES_PAGE);
        request.getSession().setAttribute("currentPage", page);

        return page;
    }
}

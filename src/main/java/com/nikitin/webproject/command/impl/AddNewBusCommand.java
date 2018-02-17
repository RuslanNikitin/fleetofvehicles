package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.manager.PageManager;
import com.nikitin.webproject.manager.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddNewBusCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empty = "";

        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BRAND_EN), empty);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.MODEL_EN), empty);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.COLOR_EN), empty);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BRAND_RU), empty);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.MODEL_RU), empty);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.COLOR_RU), empty);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.BUS_NUMBER), empty);

        String page = PageManager.getInstance().getProperty(PageManager.ADD_BUS_PAGE);
        request.getSession().setAttribute(SessionManager.getInstance().getProperty(SessionManager.CURRENT_PAGE), page);

        return page;
    }
}

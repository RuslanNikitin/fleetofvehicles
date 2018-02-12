package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.manager.PageManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        return PageManager.getInstance().getProperty(PageManager.LOGIN_PAGE);
    }
}
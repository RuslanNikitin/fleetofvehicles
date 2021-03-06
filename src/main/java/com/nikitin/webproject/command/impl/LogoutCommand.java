package com.nikitin.webproject.command.impl;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.manager.PageManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command. Logout.
 */
public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /** Interrupt the session and go to login page. */
        request.getSession().invalidate();
        return PageManager.getInstance().getProperty(PageManager.LOGIN_PAGE);
    }
}

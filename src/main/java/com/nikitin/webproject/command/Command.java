package com.nikitin.webproject.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command interface. All inherited Commands handle pushed buttons.
 */
public interface Command {

    /**
     * Method handle data from request, and return address page to go.
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}

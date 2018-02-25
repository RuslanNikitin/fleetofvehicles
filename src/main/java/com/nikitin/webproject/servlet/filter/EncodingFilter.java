package com.nikitin.webproject.servlet.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * This filter is used to set the content type as UTF-8.
 */
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // ignore
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {

        /** Setting the character set for the request */
        request.setCharacterEncoding("UTF-8");

        /** pass the request on */
        next.doFilter(request, response);

        /** Setting the character set for the response */
        response.setContentType("text/html; charset=UTF-8");
    }

    @Override
    public void destroy() {
        // ignore
    }
}

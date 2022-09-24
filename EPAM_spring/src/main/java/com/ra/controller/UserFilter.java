package com.ra.controller;

import com.ra.model.entity.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Order(1)
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =  (HttpServletRequest)servletRequest;
        HttpServletResponse response =  (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();

        User loggedUser = (User)session.getAttribute("loggedUser");

        if (request.getServletPath().startsWith("/merchandiser") && (loggedUser == null || loggedUser.getRole() != 3)) {
            response.sendRedirect("/showError?code=401&description=Unauthorized");
            return;
        }

        if (request.getServletPath().startsWith("/cashier") && (loggedUser == null || loggedUser.getRole() != 1)) {
            response.sendRedirect("/showError?code=401&description=Unauthorized");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

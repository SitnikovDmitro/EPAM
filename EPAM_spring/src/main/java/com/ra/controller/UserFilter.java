package com.ra.controller;

import com.ra.model.entity.User;
import com.ra.model.enums.Lang;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//@Component
//@Order(1)
public class UserFilter {

    //@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*HttpServletRequest request =  (HttpServletRequest)servletRequest;
        HttpServletResponse response =  (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();

        if (session.getAttribute("utils") == null) session.setAttribute("utils", Utils.getInstance());
        if (session.getAttribute("lang") == null) session.setAttribute("lang", Lang.RU);

        if (request.getServletPath().startsWith("/user"))
            if (!(session.getAttribute("loggedUser") instanceof User)) {
                response.sendRedirect("/home?message=You have not logged");
                return;
            }

        if (request.getServletPath().startsWith("/master"))
            if (!(session.getAttribute("loggedMaster") instanceof Master)) {
                response.sendRedirect("/home?message=You have not logged");
                return;
            }

        if (request.getServletPath().startsWith("/manager"))
            if (!(session.getAttribute("loggedManager") instanceof Manager)) {
                response.sendRedirect("/home?message=You have not logged");
                return;
            }


        filterChain.doFilter(servletRequest, servletResponse);*/

        //if (response.getStatus() >= 400)
        //    response.sendRedirect("/error");
    }
}

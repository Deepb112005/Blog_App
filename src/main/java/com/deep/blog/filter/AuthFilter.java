package com.deep.blog.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/deletePost", "/editPost", "/newPost"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {

            session = req.getSession(true);

            if (req.getQueryString() == null) {
                session.setAttribute("redirectAfterLogin", req.getRequestURI());
            } else {
                session.setAttribute("redirectAfterLogin", req.getRequestURI() + "?" + req.getQueryString());
            }

            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);

    }
}

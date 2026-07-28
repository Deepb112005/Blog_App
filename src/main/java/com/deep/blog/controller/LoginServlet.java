package com.deep.blog.controller;

import com.deep.blog.entity.User;
import com.deep.blog.service.user.UserService;
import com.deep.blog.service.user.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if( session != null &&  session.getAttribute("userId") != null){
            resp.sendRedirect(req.getContextPath()+"/posts");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");


        User loggedInUser = userService.login(username, password);

        if (loggedInUser == null) {
            req.setAttribute("error", "invalid username or password");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("userId", loggedInUser.getId());
        session.setAttribute("username", loggedInUser.getUsername());

        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");

        resp.setStatus(HttpServletResponse.SC_OK);
        if (redirectUrl != null) {
            session.removeAttribute("redirectAfterLogin");
            resp.sendRedirect(redirectUrl);
        } else {
            resp.sendRedirect(req.getContextPath() + "/posts");
        }
    }
}

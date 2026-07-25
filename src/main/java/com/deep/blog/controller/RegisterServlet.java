package com.deep.blog.controller;

import com.deep.blog.entity.User;
import com.deep.blog.service.Exceptions.DuplicateUserException;
import com.deep.blog.service.user.UserService;
import com.deep.blog.service.user.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String rawPassword = req.getParameter("password");

        try {
            userService.register(username, email, rawPassword);
        } catch (DuplicateUserException e) {
            req.setAttribute("error",   e.getMessage() + " : might be duplicate credentials!"  );
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        User loggedInUser = userService.login(username, rawPassword);

        HttpSession session = req.getSession();
        session.setAttribute("userId", loggedInUser.getId());
        session.setAttribute("username", loggedInUser.getUsername());

        resp.sendRedirect(req.getContextPath() + "/posts");
    }
}

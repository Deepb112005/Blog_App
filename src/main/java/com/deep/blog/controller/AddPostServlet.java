package com.deep.blog.controller;

import com.deep.blog.entity.BlogPost;
import com.deep.blog.entity.User;
import com.deep.blog.service.Exceptions.InvalidPostDataException;
import com.deep.blog.service.blogPost.BlogPostService;
import com.deep.blog.service.blogPost.BlogPostServiceImpl;
import com.deep.blog.util.JpaUtil;
import com.deep.blog.util.ImageUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/newPost")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // after this, buffer will happen with disc itself, not memory
        maxFileSize = 1024 * 1024 * 10, //total size for one file max
        maxRequestSize = 1024 * 1024 * 15 // total post request must not exceed to this
)
public class AddPostServlet extends HttpServlet {


    private final BlogPostService blogPostService = new BlogPostServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/newPost.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        Part filePart = req.getPart("image");

        String savedFileName = ImageUtil.saveUploadedFile(filePart);

        BlogPost post = new BlogPost();
        post.setTitle(title);
        post.setContent(content);
        post.setImagePath(savedFileName);

        EntityManager em = JpaUtil.getEntityManager();
        Long userId = (Long) session.getAttribute("userId");
        User user = em.getReference(User.class, userId);
        post.setUser(user);
        em.close();

        try {
            blogPostService.createPost(post); // or updatePost(...)
        } catch (InvalidPostDataException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/newPost.jsp").forward(req, resp); // or editPost.jsp
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/posts");
    }

}

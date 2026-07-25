package com.deep.blog.controller;

import com.deep.blog.entity.BlogPost;
import com.deep.blog.service.Exceptions.InvalidPostDataException;
import com.deep.blog.service.Exceptions.UnauthorizedActionException;
import com.deep.blog.service.blogPost.BlogPostService;
import com.deep.blog.service.blogPost.BlogPostServiceImpl;
import com.deep.blog.util.ImageUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


@WebServlet("/editPost")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10 ,
        maxRequestSize =1024 * 1024 * 15
)
public class EditPostServlet extends HttpServlet {

    private final BlogPostService blogPostService = new BlogPostServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Long postId = Long.parseLong(req.getParameter("id"));
        BlogPost post = blogPostService.getPostById(postId);

        Long userId = (Long) session.getAttribute("userId");

        req.setAttribute("post", post);
        req.getRequestDispatcher("WEB-INF/views/editPost.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long postId = Long.parseLong(req.getParameter("id"));
        String newTitle = req.getParameter("title");
        String newContent = req.getParameter("content");

        Part part = req.getPart("image");
        String newImage = ImageUtil.saveUploadedFile(part);

        HttpSession session = req.getSession(false);
        Long userId = (Long)session.getAttribute("userId");

        try {
            blogPostService.updatePost(postId,newTitle,newContent,newImage,userId);
        } catch (UnauthorizedActionException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/posts.jsp").forward(req, resp);
            return;
        }catch (InvalidPostDataException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("post", blogPostService.getPostById(postId));
            req.getRequestDispatcher("/WEB-INF/views/editPost.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/posts");
    }
}

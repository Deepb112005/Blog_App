package com.deep.blog.controller;

import com.deep.blog.entity.BlogPost;
import com.deep.blog.service.blogPost.BlogPostService;
import com.deep.blog.service.blogPost.BlogPostServiceImpl;
import com.deep.blog.util.ImageUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/deletePost")
public class DeletePostServlet extends HttpServlet {

    private final BlogPostService blogPostService = new BlogPostServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Long userId = (Long) session.getAttribute("userId");


        Long postId = Long.parseLong(req.getParameter("id"));
        BlogPost post = blogPostService.getPostById(postId);

        if (post == null || !post.getUser().getId().equals(userId)) {
            req.setAttribute("error", " Unauthorized action ! you don t have permission to do this ");
            req.getRequestDispatcher("/WEB-INF/views/posts.jsp").forward(req, resp);
            return;
        }

        if(post.getImagePath() != null &&  ! post.getImagePath().isBlank()){
            ImageUtil.deleteFile(post.getImagePath());
        }

        blogPostService.deletePost(postId);

        resp.sendRedirect(req.getContextPath() + "/posts");
    }
}

package com.deep.blog.controller;

import com.deep.blog.entity.BlogPost;
import com.deep.blog.service.blogPost.BlogPostService;
import com.deep.blog.service.blogPost.BlogPostServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/blogPost/*")
public class BlogPostDetailsServlet extends HttpServlet {

    private final BlogPostService blogPostService = new BlogPostServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Long postId ;

        try{
            postId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST , "invalid postId");
            return;
        }

        BlogPost post = blogPostService.getPostById(postId);

        req.setAttribute("post" , post );
        req.getRequestDispatcher("/WEB-INF/views/blogPost.jsp").forward(req,resp);


    }
}

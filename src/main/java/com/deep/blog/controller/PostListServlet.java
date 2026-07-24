package com.deep.blog.controller;

import com.deep.blog.entity.BlogPost;
import com.deep.blog.service.blogPost.BlogPostService;
import com.deep.blog.service.blogPost.BlogPostServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/posts")
public class PostListServlet extends HttpServlet {

    private final BlogPostService blogPostService = new BlogPostServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        List<BlogPost> posts = blogPostService.getAllPost();
        request.setAttribute("posts", posts);
        request.getRequestDispatcher("/WEB-INF/views/posts.jsp").forward(request, response);
    }
}
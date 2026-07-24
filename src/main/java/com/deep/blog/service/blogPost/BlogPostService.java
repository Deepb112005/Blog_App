package com.deep.blog.service.blogPost;

import com.deep.blog.entity.BlogPost;

import java.util.List;

public interface BlogPostService {

    void createPost(BlogPost post);

    BlogPost getPostById(Long id);

    List<BlogPost> getAllPost();

    void deletePost(Long id);

    void updatePost(Long postId, String newTitle, String newContent, String newImagePath, Long currentUserId);
}

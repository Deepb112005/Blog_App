package com.deep.blog.dao.blogPost;

import com.deep.blog.entity.BlogPost;

import java.util.List;

public interface BlogPostDao {
    void save(BlogPost post);

    BlogPost findById(Long id);

    List<BlogPost> findAll();

    void update(BlogPost post);

    void delete(Long id);
}
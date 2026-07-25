package com.deep.blog.service.blogPost;

import com.deep.blog.dao.blogPost.BlogPostDao;
import com.deep.blog.dao.blogPost.BlogPostDaoImpl;
import com.deep.blog.entity.BlogPost;
import com.deep.blog.service.Exceptions.InvalidPostDataException;
import com.deep.blog.service.Exceptions.UnauthorizedActionException;
import com.deep.blog.util.ImageUtil;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostDao blogPostDao = new BlogPostDaoImpl();

    @Override
    public void createPost(BlogPost post) {
        validatePostData(post.getTitle(), post.getContent());
        post.setCreatedAt(LocalDateTime.now());
        blogPostDao.save(post);
    }

    @Override
    public void deletePost(Long id) {
        blogPostDao.delete(id);
    }

    @Override
    public List<BlogPost> getAllPost() {
        return blogPostDao.findAll();
    }

    @Override
    public BlogPost getPostById(Long id) {
        return blogPostDao.findById(id);
    }

    @Override
    public void updatePost(Long postId, String newTitle, String newContent, String newImagePath, Long currentUserId) {

        validatePostData(newTitle, newContent);
        BlogPost post = blogPostDao.findById(postId);

        if (post == null) {
            throw new UnauthorizedActionException("post not found for update");
        }
        if (!post.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedActionException("current user is not authorized to edit this post!");
        }


        post.setTitle(newTitle);
        post.setContent(newContent);

        if (newImagePath != null) {
            if(post.getImagePath() != null){
                ImageUtil.deleteFile(post.getImagePath());
            }
            post.setImagePath(newImagePath);
        }
        blogPostDao.update(post);
    }

    private void validatePostData(String title, String content) {
        if (title == null || title.isBlank()) {
            throw new InvalidPostDataException("Title cannot be empty.");
        }
        if (title.length() > 200) {
            throw new InvalidPostDataException("Title cannot exceed 200 characters.");
        }
        if (content == null || content.isBlank()) {
            throw new InvalidPostDataException("Content cannot be empty.");
        }
    }
}

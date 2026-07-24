package com.deep.blog.dao.blogPost;

import com.deep.blog.entity.BlogPost;
import com.deep.blog.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class BlogPostDaoImpl implements BlogPostDao {

    @Override
    public void save(BlogPost post) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(post);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public BlogPost findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(BlogPost.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<BlogPost> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT b FROM BlogPost b ORDER BY b.createdAt DESC", BlogPost.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(BlogPost post) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(post);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            BlogPost post = em.find(BlogPost.class, id);
            if (post != null) {
                em.remove(post);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
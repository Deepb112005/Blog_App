package com.deep.blog.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static EntityManagerFactory emf;

    private JpaUtil() {
    }

    public static void init() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("blogapp_unit");
        }
    }

    public static EntityManager getEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("EntityMangerFactory isn't initialized , check it in servlet context");
        }
        return emf.createEntityManager();
    }

    public static void shutDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }


}

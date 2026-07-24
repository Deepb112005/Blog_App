package com.deep.blog.listener;


import com.deep.blog.util.JpaUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        JpaUtil.init();
        System.out.println("EntityManagerfactory is created");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaUtil.shutDown();
        System.out.println("EntityManagerfactory is closed !");
    }
}

package com.bizwink.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by petersong on 16-1-30.
 */
public class SpringInit implements ServletContextListener {
    private static WebApplicationContext springContext;
    public SpringInit() {
        super();
    }

    public void contextInitialized(ServletContextEvent event) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("this is last destroyeed");
    }

    public static ApplicationContext getApplicationContext() {
        return springContext;
    }
}

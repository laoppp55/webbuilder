package com.bizwink.util;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by petersong on 16-1-30.
 */
public class SpringInit implements ServletContextListener {
    private static WebApplicationContext springContext;
    private static String indexPath;

    public SpringInit() {
        super();
        FileProps props = new FileProps("com/bizwink/util/config.properties");
        indexPath = props.getProperty("main.indexPath");
    }

    public void contextInitialized(ServletContextEvent event) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {

    }

    public static ApplicationContext getApplicationContext() {
        return springContext;
    }

    public static String getIndexPath() {
        return indexPath;
    }

    public static void setIndexPath(String indexPath) {
        SpringInit.indexPath = indexPath;
    }
}

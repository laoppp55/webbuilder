package com.bizwink.boot;

import com.bizwink.boot.util.Configurations;

import java.io.File;

/**
 * OpenCms Base class for static access to system wide properties
 * and helper functions, e.g. OpenCms logging oder OpenCms base path.
 *
 * @author Alexander Lucas
 * @version $Revision: 1.1.1.1 $ $Date: 2006/07/18 08:31:16 $
 */

public class CmsBase implements I_CmsLogChannels {
    /**
     * Reference to the OpenCms base path ("home directory" of OpenCms)
     */
    private static String c_basePath = null;

    /**
     * Reference to the system log
     */
    private static CmsLog c_cmsLog = null;

    /**
     * Indicates if the system log is initialized
     */
    protected static boolean c_servletLogging = false;

    /**
     * Initialize the logging mechanism of the Jserv
     *
     * @param config the configurations needed at initialization.
     */
    public static void initializeServletLogging(Configurations config) {
        c_cmsLog = new CmsLog("log", config);
        c_servletLogging = true;
    }

    /**
     * Check if the system logging is active.
     *
     * @return <code>true</code> if the logging is active, <code>false</code> otherwise.
     */
    public static boolean isLogging() {
        if (c_servletLogging) {
            return c_cmsLog.isActive();
        } else {
            return true;
        }
    }

    /**
     * Log a message into the OpenCms logfile.
     * If the logfile was not initialized (e.g. due tue a missing
     * ServletConfig while working with the console)
     * any log output will be written to the apache error log.
     *
     * @param channel The channel the message is logged into
     * @message The message to be logged,
     */
    public static void log(String channel, String message) {
        if (c_servletLogging) {
            c_cmsLog.log(channel, message);
        } else {
            System.err.println(message);
        }
    }

    /**
     * Set the base path to the given value
     */
    public static String setBasePath(String s) {
        if (s != null) {
            s = s.replace('\\', '/');
            s = s.replace('/', File.separatorChar);

            if (!s.endsWith(File.separator)) {
                s = s + File.separator;
            }

            log(C_OPENCMS_INIT, "[CmsBase] Setting OpenCms home folder to " + s + ". ");
            c_basePath = s;
        }
        return s;
    }

    /**
     * Get the OpenCms base path
     */
    public static String getBasePath() {
        return c_basePath;
    }

    /**
     * Get the OpenCms web-base path
     */
    public static String getWebBasePath() {
        File basePath = new File(c_basePath);
        String webBasePath = basePath.getParent();
        if (!webBasePath.endsWith(File.separatorChar + "")) {
            webBasePath += File.separatorChar;
        }
        return webBasePath;
    }

    /**
     * Get the OpenCms WebApplicationName
     */
    public static String getWebAppName() {
        File basePath = new File(c_basePath);
        String webAppName = basePath.getParentFile().getName();
        return webAppName;
    }

    public static String getAbsoluteWebPath(String s) {
        if (s == null) {
            return null;
        }

        File f = new File(s);
        if (!f.isAbsolute()) {
            if (c_basePath == null) {
                return null;
            } else {
                return getWebBasePath() + s;
            }
        } else {
            return s;
        }
    }

    public static String getAbsolutePath(String s) {
        if (s == null) {
            return null;
        }

        File f = new File(s);
        if (!f.isAbsolute()) {
            if (c_basePath == null) {
                return null;
            } else {
                return c_basePath + s;
            }
        } else {
            return s;
        }
    }

    public static String getPropertiesPath(boolean absolute) {
        //String result = "config/cms.properties";
        String result = "classes/com/bizwink/cms/server/config.properties";
        if (absolute) {
            if (c_basePath == null) {
                result = null;
            } else {
                result = c_basePath + result;
            }
        }
        return result;
    }
}
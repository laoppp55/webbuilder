package com.bizwink.mupload;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessControlException;
import java.util.*;
import javax.swing.JOptionPane;

// Referenced classes of package JUpload:
//            JUpload

public class startup extends Applet
{

    public AppletContext context;
    private JUpload jup;

    public startup()
    {
    }

    public String getAppletInfo()
    {
        return "JUpload Applet v0.63 (http://www.haller-systemservice.net/jupload/)";
    }

    public void debugInfo()
    {
        String props[][] = {
                {"file.separator", "File separator"},
                {"java.class.version", "Java class version number"},
                {"java.vendor", "Java vendor-specific string"},
                {"java.vendor.url", "Java vendor URL"},
                {"java.version", "Java version number"},
                {"os.arch", "Operating system architecture"},
                {"os.name", "Operating system name"},
                {"os.version", "Operating system version"},
                {"path.separator", "Path separator"}
        };
        debug("startup() debugInfo() " + getAppletInfo());
        Properties systemProperties = null;
        try
        {
            systemProperties = System.getProperties();
        }
        catch(AccessControlException ace)
        {
            JOptionPane.showMessageDialog(this, "You have to grant read permissions  to the applet for local use in the java.policy file in the java directory.\n");
            debug("*** STOPPING APPLET DUE TO PERMISSION PROBLEMS ***");
            stop();
            return;
        }
        String strKey;
        String strValue;
        for(Enumeration strKeys = systemProperties.keys(); strKeys.hasMoreElements(); debug("  >> " + strKey + " = [" + strValue + "]"))
        {
            strKey = (String)strKeys.nextElement();
            strValue = systemProperties.getProperty(strKey);
        }

    }

    public void init()
    {
        debug("startup() init()");
        debugInfo();
        context = getAppletContext();
    }

    public void start()
    {
        debug("startup() start()");
        if(getParameter("checkJavaVersion") != null)
        {
            if(!getParameter("checkJavaVersion").equalsIgnoreCase("true") && !getParameter("checkJavaVersion").equalsIgnoreCase("1") && !getParameter("checkJavaVersion").equalsIgnoreCase("on"))
                if(getParameter("checkJavaVersion").equalsIgnoreCase("yes"));
            debug("startup() start() checking for java version...");
            if(checkJavaVersion())
                jup = new JUpload(this);
        } else
        {
            debug("startup() start() no check for java version...checkJavaVersion parameter not found");
            jup = new JUpload(this);
        }
    }

    public void stop()
    {
        debug("startup() stop()");
        if(jup != null)
            jup.stop();
    }

    private boolean checkJavaVersion()
    {
        String javaVersion = System.getProperty("java.class.version");
        int dbJavaVersion = Integer.parseInt(javaVersion.substring(0, 2));
        if(dbJavaVersion < 47)
        {
            debug("startup() checkJavaVersion() java version " + dbJavaVersion + " not supported.");
            add(new Label("Your browser's java is too old."));
            add(new Label("Please go to java.sun.com and"));
            add(new Label("download the latest J2SE runtime."));
            add(new Label("(Internet Explorer 6 ist shipped with"));
            add(new Label("Java 1.1!)"));
            setVisible(true);
            try
            {
                URL pluginURL = new URL("http://java.sun.com/j2se/downloads.html");
                String strGotoURL = getParameter("checkJavaVersionGotoURL");
                if(strGotoURL != null && !strGotoURL.equalsIgnoreCase(""))
                    pluginURL = new URL(strGotoURL);
                getAppletContext().showDocument(pluginURL, "_blank");
            }
            catch(MalformedURLException e)
            {
                e.printStackTrace();
            }
            return false;
        } else
        {
            return true;
        }
    }

    private void debug(String s)
    {
        System.out.println(s);
        if(getParameter("debug") != null)
        {
            if(!getParameter("debug").equalsIgnoreCase("true") && !getParameter("debug").equalsIgnoreCase("1") && !getParameter("debug").equalsIgnoreCase("on"))
                if(getParameter("debug").equalsIgnoreCase("yes"));
            System.out.println(s);
        }
    }
}

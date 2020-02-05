package com.bizwink.mupload;

import java.applet.Applet;
import java.awt.Color;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

// Referenced classes of package JUpload:
//            startup

public class Configurator
{

    private static Properties defaultprops;
    private static Properties props;
    private static HashMap hashParameters;
    private static String settings[][] = {
        {
            "backgroundColor", "#e0e0e0", "String"
        }, {
            "completeURL", "", "URL"
        }, {
            "actionURL", "http://localhost/jupload/demo/JUpload.php", "URL"
        }, {
            "askAuthentification", "false", "Boolean"
        }, {
            "tagName", "uploadedFiles", "String"
        }, {
            "checkResponse", "true", "Boolean"
        }, {
            "showSuccessDialog", "true", "Boolean"
        }, {
            "imageFileFilter", "false", "Boolean"
        }, {
            "hideShowAll", "false", "Boolean"
        }, {
            "showPicturePreview", "true", "Boolean"
        }, {
            "addWindowTitle", "Add files", "String"
        }, {
            "imageURL", "", "String"
        }, {
            "labelFiles", "Files:", "String"
        }, {
            "labelBytes", "Bytes:", "String"
        }, {
            "labelAdd", "Add", "String"
        }, {
            "labelRemove", "Remove", "String"
        }, {
            "labelUpload", "Upload", "String"
        }, {
            "successDialogMessage", "Upload was reported to be successfull.", "String"
        }, {
            "successDialogTitle", "Upload successfull", "String"
        }, {
            "addToolTip", "Press to open file chooser to add files to upload queue", "String"
        }, {
            "removeToolTip", "Select files in queue and click this button to remove them from queue", "String"
        }, {
            "uploadToolTip", "Press to start uploading the files in the queue to the configured webserver", "String"
        }, {
            "useRecursivePaths", "false", "Boolean"
        }, {
            "debug", "true", "Boolean"
        }, {
            "checkJavaVersion", "true", "Boolean"
        }, {
            "checkJavaVersionGotoURL", "http://java.sun.com/j2se/downloads.html", "URL"
        }, {
            "browserCookie", "", "String"
        }, {
            "defaultAddDirectory", "", "String"
        }, {
            "usePutMethod", "false", "Boolean"
        }, {
            "maxFreeSpaceOnServer", "-1", "Long"
        }, {
            "maxFreeSpaceOnServerWarning", "File is too large for server", "String"
        }, {
            "maxFreeSpaceOnServerTitle", "File too large", "String"
        }, {
            "maxTotalRequestSize", "2000000", "Long"
        }, {
            "maxTotalRequestSizeWarning", "File too large for upload", "String"
        }, {
            "maxTotalRequestSizeTitle", "File too large", "String"
        }, {
            "customFileFilter", "false", "Boolean"
        }, {
            "customFileFilterDescription", "Customized", "String"
        }, {
            "customFileFilterExtensions", "gif,jpeg,jpg,png,bmp,tif", "String"
        }
    };

    public Configurator()
    {
    }

    public static URL getActionURL()
    {
        String strValue = (String)props.get("actionURL");
        System.out.println("action url=" + strValue);
        URL url = null;
        if(getUsePutMethod() && !strValue.endsWith("/"))
            strValue = strValue + "/";
        try
        {
            url = new URL(strValue);
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
            try
            {
                url = new URL((String)defaultprops.get("actionURL"));
            }
            catch(MalformedURLException e1)
            {
                e1.printStackTrace();
            }
        }
        return url;
    }

    public static String getAddToolTip()
    {
        return (String)props.get("addToolTip");
    }

    public static String getAddWindowTitle()
    {
        return (String)props.get("addWindowTitle");
    }

    public static boolean getAskAuthentificate()
    {
        String strValue = (String)props.get("askAuthentification");
        return checkBoolean(strValue);
    }

    public static Color getBackgroundColor()
    {
        String strBackgroundColor = (String)props.get("backgroundColor");
        Color colBackgroundColor = getColor(strBackgroundColor);
        return colBackgroundColor;
    }

    public static String getBrowserCookie()
    {
        return (String)props.get("browserCookie");
    }

    public static boolean getCheckResponse()
    {
        String strCheckResponse = (String)props.get("checkResponse");
        return checkBoolean(strCheckResponse);
    }

    public static URL getCompleteURL()
    {
        String strValue = (String)props.get("completeURL");
        URL url = null;
        try
        {
            url = new URL(strValue);
        }
        catch(MalformedURLException e)
        {
            debug("Configurator() getCompleteURL() could not get completeURL. error.");
            url = null;
        }
        return url;
    }

    public static boolean getCustomFileFilter()
    {
        String strValue = (String)props.get("customFileFilter");
        return checkBoolean(strValue);
    }

    public static String getCustomFileFilterDescription()
    {
        return (String)props.get("customFileFilterDescription");
    }

    public static String getCustomFileFilterExtensions()
    {
        return (String)props.get("customFileFilterExtensions");
    }

    public static boolean getDebug()
    {
        if(props == null)
        {
            return false;
        } else
        {
            String strValue = (String)props.get("debug");
            return checkBoolean(strValue);
        }
    }

    public static String getDefaultAddDirectory()
    {
        return (String)props.get("defaultAddDirectory");
    }

    public static String getHTTPTagName()
    {
        return (String)props.get("tagName");
    }

    public static boolean getHideShowAll()
    {
        String strValue = (String)props.get("hideShowAll");
        return checkBoolean(strValue);
    }

    public static boolean getImageFileFilter()
    {
        String strValue = (String)props.get("imageFileFilter");
        return checkBoolean(strValue);
    }

    public static String getImageURL()
    {
        return (String)props.get("imageURL");
    }

    public static String getLabelAdd()
    {
        return (String)props.get("labelAdd");
    }

    public static String getLabelBytes()
    {
        return (String)props.get("labelBytes");
    }

    public static String getLabelFiles()
    {
        return (String)props.get("labelFiles");
    }

    public static String getLabelRemove()
    {
        return (String)props.get("labelRemove");
    }

    public static String getLabelUpload()
    {
        return (String)props.get("labelUpload");
    }

    public static long getMaxFreeSpaceOnServer()
    {
        String strValue = (String)props.get("maxFreeSpaceOnServer");
        long lValue = 0L;
        try
        {
            lValue = Long.parseLong(strValue);
        }
        catch(NumberFormatException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lValue;
    }

    public static String getMaxFreeSpaceOnServerTitle()
    {
        return (String)props.get("maxFreeSpaceOnServerTitle");
    }

    public static String getMaxFreeSpaceOnServerWarning()
    {
        return (String)props.get("maxFreeSpaceOnServerWarning");
    }

    public static long getMaxTotalRequestSize()
    {
        String strValue = (String)props.get("maxTotalRequestSize");
        long lValue = 0L;
        try
        {
            lValue = Long.parseLong(strValue);
        }
        catch(NumberFormatException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lValue;
    }

    public static String getMaxTotalRequestSizeTitle()
    {
        return (String)props.get("maxTotalRequestSizeTitle");
    }

    public static String getMaxTotalRequestSizeWarning()
    {
        return (String)props.get("maxTotalRequestSizeWarning");
    }

    public static String getRemoveToolTip()
    {
        return (String)props.get("removeToolTip");
    }

    public static boolean getShowPicturePreview()
    {
        String strValue = (String)props.get("showPicturePreview");
        return checkBoolean(strValue);
    }

    public static boolean getShowSuccessDialog()
    {
        String strShowSuccessDialog = (String)props.get("ShowSuccessDialog");
        return checkBoolean(strShowSuccessDialog);
    }

    public static String getSuccessDialogMessage()
    {
        return (String)props.get("successDialogMessage");
    }

    public static String getSuccessDialogTitle()
    {
        return (String)props.get("successDialogTitle");
    }

    public static String getUploadToolTip()
    {
        return (String)props.get("uploadToolTip");
    }

    public static boolean getUsePutMethod()
    {
        String strValue = (String)props.get("usePutMethod");
        return checkBoolean(strValue);
    }

    public static boolean getUseRecursivePaths()
    {
        String strValue = (String)props.get("useRecursivePaths");
        return checkBoolean(strValue);
    }

    public static String helpPage()
    {
        String helpPage = "<applet \n  code=\"JUpload/startup.class\"\n  archive=\"JUpload.jar\"\n  width=\"450\"\n  height=\"200\"\n  alt=\"JUpload java applet for uploading multiple files at once with http post method\">\n <!-- Java Plug-In Options -->\n <param name=\"progressbar\" value=\"true\">\n <param name=\"boxmessage\" value=\"Loading JUpload Applet ...\">\n <param name=\"boxbgcolor\" value=\"#e0e0ff\">\n\n\n";
        for(int i = 0; i < settings.length; i++)
        {
            String strKey = settings[i][0];
            String strValue = settings[i][1];
            String strType = settings[i][2];
            helpPage = helpPage + "<PARAM NAME=\"" + strKey + "\" VALUE=\"" + strValue + "\"><!-- Type is " + strType + " -->\n";
        }

        helpPage = helpPage + "</applet>";
        return helpPage;
    }

    public static void readConfiguration(startup applet)
    {
        props = new Properties();
        defaultprops = new Properties();
        debug("Configurator() there are " + settings.length + " known parameters.");
        for(int i = 0; i < settings.length; i++)
        {
            String strKey = settings[i][0];
            String strDefaultValue = settings[i][1];
            String strKeyType = settings[i][2];
            String strUserValue = applet.getParameter(strKey);
            debug("Configurator() key=[" + strKey + "] default=[" + strDefaultValue + "] uservalue=[" + strUserValue + "] type=[" + strKeyType + "]");
            defaultprops.put(strKey, strDefaultValue);
            if(strUserValue == null)
                props.put(strKey, strDefaultValue);
            else
                props.put(strKey, strUserValue);
        }

    }

    protected static void changeProperty(String strKey, String strValue)
    {
        props.put(strKey, strValue);
    }

    private static Color getColor(String string)
    {
        Color col;
        try
        {
            String strRed = string.substring(1, 3);
            String strGreen = string.substring(3, 5);
            String strBlue = string.substring(5, 7);
            int iRed = Integer.parseInt(strRed, 16);
            int iGreen = Integer.parseInt(strGreen, 16);
            int iBlue = Integer.parseInt(strBlue, 16);
            col = new Color(iRed, iGreen, iBlue);
        }
        catch(NullPointerException e)
        {
            col = Color.white;
        }
        return col;
    }

    private static boolean checkBoolean(String strCheckResponse)
    {
        if(strCheckResponse == null)
            return false;
        if(strCheckResponse.equalsIgnoreCase("false"))
            return false;
        if(strCheckResponse.equalsIgnoreCase("0"))
            return false;
        if(strCheckResponse.equalsIgnoreCase("off"))
            return false;
        if(strCheckResponse.equalsIgnoreCase("no"))
            return false;
        if(strCheckResponse.equalsIgnoreCase("true"))
            return true;
        if(strCheckResponse.equalsIgnoreCase("1"))
            return true;
        if(strCheckResponse.equalsIgnoreCase("on"))
            return true;
        return !strCheckResponse.equalsIgnoreCase("yes") ? false : false;
    }

    private static void debug(String string)
    {
        System.out.println(string);
    }

}

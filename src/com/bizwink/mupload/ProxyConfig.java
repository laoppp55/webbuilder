package com.bizwink.mupload;

import java.io.PrintStream;
import java.net.URL;
import java.util.Properties;
import java.util.StringTokenizer;

// Referenced classes of package JUpload:
//            Configurator

public class ProxyConfig
{

    public static String strProxySettings;
    public static boolean useProxy = false;
    public static String proxyHostnamePort;
    public static String proxyHostname;
    public static String proxyPort;

    public ProxyConfig()
    {
    }

    public static void readConfiguration()
    {
        Properties proxySettings = new Properties();
        strProxySettings = System.getProperty("javaplugin.proxy.settings");
        debug("ProxyConfig() strProxySettings=" + strProxySettings);
        if(strProxySettings != null)
        {
            useProxy = true;
            for(StringTokenizer st = new StringTokenizer(strProxySettings, ";,"); st.hasMoreTokens();)
            {
                String strKey = st.nextToken();
                String strValue = st.nextToken();
                StringTokenizer st3 = new StringTokenizer(strKey, "=");
                String strProtocol = st3.nextToken();
                if(strProtocol.equalsIgnoreCase(Configurator.getActionURL().getProtocol()))
                {
                    debug("ProxyConfig() proxy found for protocol " + strProtocol + " in java plugin proxy settings");
                    proxyHostnamePort = st3.nextToken();
                    StringTokenizer st2 = new StringTokenizer(proxyHostnamePort, ":");
                    proxyHostname = st2.nextToken();
                    proxyPort = st2.nextToken();
                } else
                {
                    debug("ProxyConfig() do not need proxy for protocol " + strKey);
                }
            }

        }
        strProxySettings = System.getProperty("javaplugin.proxy.config.list");
        debug("ProxyConfig() strProxySettings=" + strProxySettings);
        if(strProxySettings != null)
        {
            useProxy = true;
            StringTokenizer st = new StringTokenizer(strProxySettings, ",;");
            do
            {
                String strKey = st.nextToken();
                if(!st.hasMoreTokens())
                    break;
                String strValue = st.nextToken();
                StringTokenizer st3 = new StringTokenizer(strKey, "=");
                String strProtocol = st3.nextToken();
                if(strProtocol.equalsIgnoreCase(Configurator.getActionURL().getProtocol()))
                {
                    debug("ProxyConfig() proxy found for protocol " + strProtocol + " in java plugin config list");
                    proxyHostnamePort = st3.nextToken();
                    StringTokenizer st2 = new StringTokenizer(proxyHostnamePort, ":");
                    proxyHostname = st2.nextToken();
                    proxyPort = st2.nextToken();
                } else
                {
                    debug("ProxyConfig() do not need proxy for protocol " + strKey);
                }
            } while(st.hasMoreTokens());
        }
        String strHttpProxyHost = System.getProperty("http.proxyHost");
        if(strHttpProxyHost != null && !strHttpProxyHost.equalsIgnoreCase(""))
        {
            debug("ProxyConfig() proxy settings found in http.* configuration (appletviewer)");
            useProxy = true;
            proxyHostname = System.getProperty("http.proxyHost");
            proxyPort = System.getProperty("http.proxyPort");
        }
        debug("ProxyConfig() current settings:");
        debug("ProxyConfig()   proxyHostname=" + proxyHostname);
        debug("ProxyConfig()   proxyPort=" + proxyPort);
    }

    private static void debug(String string)
    {
        if(Configurator.getDebug())
            System.out.println(string);
    }

}

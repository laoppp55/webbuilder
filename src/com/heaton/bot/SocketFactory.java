/*
 * SocketFactory.java
 *
 * Created on August 19, 2003, 7:15 PM
 */

package com.heaton.bot;
import java.net.*;
import java.io.*;

/**
 *
 * @author  jheaton
 */
public class SocketFactory {
    
    private static String proxyHost;
    private static int proxyPort;
    private static String proxyUID;
    private static String proxyPWD;
    
    public static void setProxyHost(String proxyHost) {
        SocketFactory.proxyHost = proxyHost;
    }
    
    public static void setProxyUID(String proxyUID) {
        SocketFactory.proxyUID = proxyUID;
    }
    
    public static void setProxyPWD(String proxyPWD) {
        SocketFactory.proxyPWD = proxyPWD;
    }
    
    public static void setProxyPort(int id) {
        SocketFactory.proxyPort = id;
    }
    
    public static String getProxyHost() {
        return proxyHost;
    }
    
    public static String getProxyUID() {
        return proxyUID;
    }
    
    public static String getProxyPWD() {
        return proxyPWD;
    }
    
    public static int getProxyPort() {
        return proxyPort;
    }
    
    public static boolean useProxy() {
        return( (proxyHost!=null) && (proxyHost.length()>0) );
    }
    
    public static void writeString(OutputStream out,String str)
    throws java.io.IOException {
        out.write( str.getBytes() );
        out.write( "\r\n".getBytes() );
        Log.log(Log.LOG_LEVEL_TRACE,"Socket Out:" + str );
    }
    
    public static String receive(InputStream in)
    throws IOException {
        String result = "";
        boolean done = false;
        
        while (!done) {
            int ch = in.read();
            switch (ch) {
                case 13:
                    break;
                case 10:
                    done=true;
                    break;
                default:
                    result+=(char)ch;
            }
            
        }
        //System.out.println("Recv:" + result );
        return result;
    }    
    
    
    static public Socket getSocket(String host,int port,boolean https) 
    throws IOException 
    {
        Socket socket;
        
        if( useProxy() ) {
            Log.log(Log.LOG_LEVEL_NORMAL,"Connection to: " + proxyHost+"(" + proxyPort + ")" );
            try {
                socket = new Socket(proxyHost,proxyPort);
            }
            catch(IOException e) {
                throw new IOException("Proxy connect failed: " + e.getMessage() );
            }
            
            if( https ) {
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                
                String str = "CONNECT " + host + ":" + port + " HTTP/1.0";
                Log.log(Log.LOG_LEVEL_NORMAL,"Tunnel: " + str );
                writeString(out,str);
                str = "User-Agent: Java Bot Package";
                writeString(out,str);
                writeString(out,"");
                out.flush();
                do {
                    str = receive(in);
                    Log.log(Log.LOG_LEVEL_TRACE,"Tunnel handshake: " + str );
                } while(str.length()>0);
                
                socket = SSL.getSSLSocket(host,port);
            }
        }
        else {
            if( https )
                socket = SSL.getSSLSocket(host,port);
            else
                socket = new Socket(host,port);
        }
        return socket;
        
    }
    
}

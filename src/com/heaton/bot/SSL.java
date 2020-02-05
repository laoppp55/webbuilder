package com.heaton.bot;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.security.*;

/**
 * The SSL class is used to provide an interface to JSSE without
 * making the entire package dependant on JSSE being installed.
 *
 * @author Jeff Heaton
 * @version 1.2
 */
public class SSL {

  /**
   * This is a static class, so it has a private constructor.
   */
  private SSL()
  {
  }

  /**
   * A factory used to create SSL sockets from. This
   * factory is created when the class is first loaded
   * and reused for each new socket.
   */
  protected static SSLSocketFactory factory = null;

  /**
   * This method is called to get an SSL client socket
   * for the specified host and port.
   *
   * @param host The host to connect to.
   * @param port The port to connect to.
   * @return A SSL socket.
   * @exception java.net.UnknownHostException
   * @exception java.io.IOException
   */
  static public Socket getSSLSocket(String host,int port)
  throws UnknownHostException,IOException
  {
    obtainFactory();
    SSLSocket socket = (SSLSocket) factory.createSocket(host,port);
    return socket;
  }
  
  static private void obtainFactory()
  {
    if ( factory==null ) {
      java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
      System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol");
      factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
    }      
  }
  
  static public Socket getSSLSocket(Socket s,String host,int port)
  throws UnknownHostException,IOException
  {
    obtainFactory();
    SSLSocket socket = (SSLSocket) factory.createSocket(s,host,port,true);
    return socket;
  }  



}

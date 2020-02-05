package com.bizwink.net.ftp;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public abstract class pullContentSocket extends HttpServlet {
  static final private String CONTENT_TYPE = "text/html; charset=GBK";
  protected int DEFAULT_PORT = 1313;
  private Thread thread;

  //Initialize global variables
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    try {
      thread = new Daemon(this);
      thread.start();
    } catch (Exception e) {

    }
  }

  // Returns the socket port on which this servlet will listen.
  // A servlet can specify the port in three ways: by using the socketPort
  // init parameter, by setting the DEFAULT_PORT variable before calling
  // super.init(), or by overriding this method's implementation
  protected int getSocketPort() {
    try { return Integer.parseInt(getInitParameter("socketPort")); }
    catch (NumberFormatException e) { return DEFAULT_PORT; }
  }


  public abstract void handleClient(Socket socket);

  //Clean up resources
  public void destroy() {
    // Stop the daemon thread
    try {
      thread.stop();
      thread = null;
    }
    catch (Exception e) {
      getServletContext().log(e, "Problem stopping server socket daemon thread");
    }
  }

}

class Daemon extends Thread {
    private ServerSocket serverSocket;
    private pullContentSocket servlet;

    public Daemon(pullContentSocket servlet) {
      this.servlet = servlet;
    }

    public void run() {
      try {
        serverSocket = new ServerSocket(servlet.getSocketPort());
      } catch (Exception e) {
        servlet.getServletContext().log(e,"Problem establishing server socket");
        return;
      }

      try {
        while(true) {
          try {
            servlet.handleClient(serverSocket.accept());
          } catch (IOException ioe) {
            servlet.getServletContext().log(ioe,"Problem accepting client's socket connection");
          }
        }
      } catch (ThreadDeath e) {
        try {
          serverSocket.close();
        } catch (IOException ioe) {
          servlet.getServletContext().log(ioe,"Problem closing server socket");
        }
      }
    }
}

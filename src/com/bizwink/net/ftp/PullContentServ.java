package com.bizwink.net.ftp;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.net.*;

import com.bizwink.net.ftp.pullContentSocket;

public class PullContentServ extends pullContentSocket {
  static final private String CONTENT_TYPE = "text/html; charset=GBK";
  //Initialize global variables
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public Date getDate() {
    return new Date();
  }

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    this.service(request,response);
    if ("object".equals(request.getParameter("format"))) {
      ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
      out.writeObject(getDate());
    } else {
      PrintWriter out = response.getWriter();
      out.println(getDate().toString());
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    this.service(request,response);
    doGet(request,response);
  }

  public void handleClient(Socket client) {
    new pullContentConnection(this,client);
  }

  //Clean up resources
  public void destroy() {
    super.destroy();
  }

  public void service(HttpServletRequest req,HttpServletResponse resp)
       throws ServletException,java.io.IOException
       {
        try{
             //Get the input stream for reading data from the client
             DataInputStream in = new DataInputStream(req.getInputStream());

             //We'll be sending binary data back to the client so
             //set the content type appropriately
             resp.setContentType(CONTENT_TYPE);

             //Data will always be written  to a byte array buffer so
             //that we can tell the client the length of the data
             ByteArrayOutputStream byteOut = new  ByteArrayOutputStream();

             //Create the output stream to be used to write the
             //data to our buffer
             DataOutputStream out = new DataOutputStream(byteOut);

             //Read the data from the client
             boolean ordinal = in.readBoolean();
             String date = in.readUTF();
             String userid = in.readUTF();

             //Evaluate the ordinal.true is reserved for initializing
             //the server
             if(ordinal == true)
             {
                   try{
                         int displayNum = 1000;

                         //Send the response back to the client indicating
                         //that the server object is ready for method calls.
                         out.writeBoolean(true);
                         out.writeInt(displayNum);

                   }
                   catch(Exception e)
                   {
                      System.out.println(e);
                   }
             }

             //Flush the contents of the output stream to the
             //byte array
             out.flush();

             //Get the buffer that is holding our response
             byte[] buf = byteOut.toByteArray();

             //Notify the client how much data is being sent
             resp.setContentLength(buf.length);

             //Send the buffer to the client
             ServletOutputStream servletOut = resp.getOutputStream();

             //Wrap up
             servletOut.write(buf);
             servletOut.close();
       }
       catch(IOException ex){
             //Re-throw as a tunnel exception
             ex.printStackTrace();
             throw new IOException(ex.getMessage());
       }
    }
}

class pullContentConnection extends Thread {
  PullContentServ servlet;
  Socket client;

  pullContentConnection(PullContentServ servlet,Socket client) {
    this.client = client;
    this.servlet = servlet;
    this.setPriority(NORM_PRIORITY - 1);
  }

  public void run() {
    try {
      DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
      String line = in.readLine();

      if ("object".equals(line)) {
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(servlet.getDate());
        out.close();
      } else {
        PrintStream out = new PrintStream(client.getOutputStream());
        out.println(servlet.getDate().toString());
        out.close();
      }

      client.close();
    } catch (IOException ioe) {
      servlet.getServletContext().log(ioe,"IOException while handling client request");
    } catch (Exception e) {
      servlet.getServletContext().log(e,"Exception while handling client request");
    }
  }
}

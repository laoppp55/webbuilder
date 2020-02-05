package com.bizwink.webtrend;

//Compile by Eric.March 25,2003
// Source File Name:   DrawPieServ.java

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.bizwink.cms.util.*;
import java.util.*;
import java.sql.*;
import java.lang.*;

public class conclusionDrawServPV extends HttpServlet{
/**
 * Services the HTTP request
 *
 * @param req The request from the client
 * @param resp The response from the servlet
 * @throws IOException to be by way of Exception
 * @throws ServletException
 */
 public void service(HttpServletRequest req,HttpServletResponse resp)
      throws ServletException,java.io.IOException
      {
       try{
            //Get the input stream for reading data from the client
            DataInputStream in = new DataInputStream(req.getInputStream());

            //We'll be sending binary data back to the client so
            //set the content type appropriately
            resp.setContentType("application/octet-stream");

            //Data will always be written  to a byte array buffer so
            //that we can tell the client the length of the data
            ByteArrayOutputStream byteOut = new  ByteArrayOutputStream();

            //Create the output stream to be used to write the
            //data to our buffer
            DataOutputStream out = new DataOutputStream(byteOut);

            //Read the data from the client
            boolean ordinal = in.readBoolean();
            String begindate = in.readUTF();
            String enddate = in.readUTF();
            String userid = in.readUTF();

            //Evaluate the ordinal.true is reserved for initializing
            //the server
            if(ordinal == true)
            {
                  try{
                        //Creat a new instance of the server object
                        IWebtrendManager webtrendMgr = WebtrendPeer.getInstance();
                        Webtrend webtrend = new Webtrend();
                        List list = webtrendMgr.searchconclusion(userid, begindate, enddate);
                        int i = 0;
                        String[] totalpv = new String[100];
                        String logdate[] = new String[100];

                        for(i=0;i<list.size();i++){
                            webtrend = (Webtrend)list.get(i);
                            totalpv[i] = webtrend.getTotalPageView();
                            logdate[i] = webtrend.getLogDate();
                        }

                        int total=list.size();
                        int displayNum = 0;

                        if (total < 15)
                              displayNum = total;
                        else
                              displayNum = 15;

                        //Define the variable for get the data

                        //Send the response back to the client indicating
                        //that the server object is ready for method calls.
                        out.writeBoolean(true);
                        out.writeInt(displayNum);

                        //Write the data to our internal buffer
                        for(i=0; i<displayNum; i++) {
                              out.writeBoolean(true);
                              out.writeUTF(totalpv[i]);
                              out.writeUTF(logdate[i]);
                        }
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


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

public class DrawServPV extends HttpServlet{
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
            String date = in.readUTF();
            String userid = in.readUTF();

            //Evaluate the ordinal.true is reserved for initializing
            //the server
            if(ordinal == true)
            {
                  try{
                        //Creat a new instance of the server object
                        IWebtrendManager webtrendMgr = WebtrendPeer.getInstance();
                        Webtrend webtrend = new Webtrend();
                        List list = webtrendMgr.searchprovinceid(userid, date);
                        int provinceid = 0;
                        int[][] count = new int[100][100];
                        String[] city = new String[15];

                        for(int i=0;i<list.size();i++){
                            webtrend = (Webtrend)list.get(i);
                            provinceid = webtrend.getProvinceID();
                        switch(provinceid){
                            case 0:
                                count[0][0] = count[0][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[0][1] = 0;
                                break;
                            case 1:
                                count[1][0] = count[1][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[1][1] = 1;
                                break;
                            case 2:
                                count[2][0] = count[2][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[2][1] = 2;
                                break;
                            case 3:
                                count[3][0] = count[3][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[3][1] = 3;
                                break;
                            case 4:
                                count[4][0] = count[4][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[4][1] = 4;
                                break;
                            case 5:
                                count[5][0] = count[5][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[5][1] = 5;
                                break;
                            case 6:
                                count[6][0] = count[6][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[6][1] = 6;
                                break;
                            case 7:
                                count[7][0] = count[7][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[7][1] = 7;
                                break;
                            case 8:
                                count[8][0] = count[8][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[8][1] = 8;
                                break;
                            case 9:
                                count[9][0] = count[9][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[9][1] = 9;
                                break;
                            case 10:
                                count[10][0] = count[10][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[10][1] = 10;
                                break;
                            case 11:
                                count[11][0] = count[11][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[11][1] = 11;
                                break;
                            case 12:
                                count[12][0] = count[12][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[12][1] = 12;
                                break;
                            case 13:
                                count[13][0] = count[13][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[13][1] = 13;
                                break;
                            case 14:
                                count[14][0] = count[14][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[14][1] = 14;
                                break;
                            case 15:
                                count[15][0] = count[15][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[15][1] = 15;
                                break;
                            case 16:
                                count[16][0] = count[16][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[16][1] = 16;
                                break;
                            case 17:
                                count[17][0] = count[17][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[17][1] = 17;
                                break;
                            case 18:
                                count[18][0] = count[18][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[18][1] = 18;
                                break;
                            case 19:
                                count[19][0] = count[19][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[19][1] = 19;
                                break;
                            case 20:
                                count[20][0] = count[20][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[20][1] = 20;
                                break;
                            case 21:
                                count[21][0] = count[21][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[21][1] = 21;
                                break;
                            case 22:
                                count[22][0] = count[22][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[22][1] = 22;
                                break;
                            case 23:
                                count[23][0] = count[23][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[23][1] = 23;
                                break;
                            case 24:
                                count[24][0] = count[24][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[24][1] = 24;
                                break;
                            case 25:
                                count[25][0] = count[25][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[25][1] = 25;
                                break;
                            case 26:
                                count[26][0] = count[26][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[26][1] = 26;
                                break;
                            case 27:
                                count[27][0] = count[27][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[27][1] = 27;
                                break;
                            case 28:
                                count[28][0] = count[28][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[28][1] = 28;
                                break;
                            case 29:
                                count[29][0] = count[29][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[29][1] = 29;
                                break;
                            case 30:
                                count[30][0] = count[30][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[30][1] = 30;
                                break;
                            case 31:
                                count[31][0] = count[31][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[31][1] = 31;
                                break;
                            case 32:
                                count[32][0] = count[32][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[32][1] = 32;
                                break;
                            case 33:
                                count[33][0] = count[33][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[33][1] = 33;
                                break;
                            case 34:
                                count[34][0] = count[34][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[34][1] = 34;
                                break;
                            case 35:
                                count[35][0] = count[35][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[35][1] = 35;
                                break;
                            case 36:
                                count[36][0] = count[36][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[36][1] = 36;
                                break;
                            case 37:
                                count[37][0] = count[37][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[37][1] = 37;
                                break;
                            case 38:
                                count[38][0] = count[38][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[38][1] = 38;
                                break;
                            case 39:
                                count[39][0] = count[39][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[39][1] = 39;
                                break;
                            case 40:
                                count[40][0] = count[40][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[40][1] = 40;
                                break;
                            case 41:
                                count[41][0] = count[41][0] + Integer.parseInt(webtrend.getUCount().trim());
                                count[41][1] = 41;
                                break;
                        }
                        }

                        int i = 0;
                        int j = 0;
                        int cpv = 0;
                        int cty = 0;
                        for(i=0;i<99;i++){
                            for(j=0;j<99-i;j++){
                                if(count[j][0]<count[j+1][0]){
                                    cpv = count[j][0];
                                    cty = count[j][1];
                                    count[j][0] = count[j+1][0];
                                    count[j][1] = count[j+1][1];
                                    count[j+1][0] = cpv;
                                    count[j+1][1] = cty;
                                }
                            }
                        }

                        int displayNum = 0;
                        for(i=1;i<100;i++)
                        {
                          if (count[i][0] != 0)
                            displayNum = displayNum + 1;
                        }
                        displayNum = displayNum + 1;

                        if (displayNum >= 15)
                              displayNum = 15;

                        for(i=0;i<displayNum;i++){
                            if(count[i][0] != 0){
                                webtrend = webtrendMgr.searchcity(count[i][1]);
                                city[i] = webtrend.getCityName();
                            }
                        }
                        //Define the variable for get the data

                        //Send the response back to the client indicating
                        //that the server object is ready for method calls.
                        out.writeBoolean(true);
                        out.writeInt(displayNum);

                        //Write the data to our internal buffer
                        for(i=0; i<displayNum; i++) {
                              out.writeBoolean(true);
                              out.writeInt(count[i][0]);
                              out.writeUTF(city[i]);
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


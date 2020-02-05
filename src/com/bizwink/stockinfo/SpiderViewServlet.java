package com.bizwink.stockinfo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.net.*;

import com.bizwink.cms.server.*;

public class SpiderViewServlet extends HttpServlet {
//*****  Mulit-threaded server, accepts multiple messages
//       default runs on jbssrv-cs.cs.unc.edu:8902

    boolean serverRunning = false;
    boolean closeflag = false;
    String host;
    int serverPort, port;
    int DEFAULT_PORT = 8902;
    String message;
    ListenServer listen;

    public void doGet (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            InetAddress here = InetAddress.getLocalHost ();
            host = here.getHostAddress ();
        }  // end try
        catch (UnknownHostException e) {
            System.out.println("setHostPort error: ");
            e.printStackTrace();
        }

        port = getSocketPort();

        if ( !serverRunning )  {
            listen = new ListenServer (port);
            listen.start ();
            message = "jbsServerThreaded Started on Tomcat,  Host " + host + ", port " + Integer.toString (port) ;
            serverRunning = true;
        }  // end firsttime
        else  {
            message = "jbsMonitorServelt already Running";
        }

        System.out.println(message);

        return;
    }  // end doGet

    // Returns the socket port on which this servlet will listen.
    // A servlet can specify the port in three ways: by using the socketPort
    // init parameter, by setting the DEFAULT_PORT variable before calling
    // super.init(), or by overriding this method's implementation
    protected int getSocketPort() {
        try { return Integer.parseInt(getInitParameter("socketPort")); }
        catch (NumberFormatException e) { return DEFAULT_PORT; }
    }
}  // end ServerThreaded

class ListenServer extends Thread  {
    ServerSocket listenSocket;
    int port;
    Socket connection;

    // **************  ListenServer
    ListenServer ( int p)  {
        super ();
        port = p;
    }  // end constructor


    // **************  run
    public void run  ()  {

        try  {
            listenSocket = new ServerSocket ( port );
            while ( true )  {
                Socket connection = listenSocket.accept();
                HandleServer handleServer = new HandleServer ( connection );
                handleServer.start ();
            }  // end while

        }  catch ( IOException e )  {
            System.out.println("listenSocket IOException: " );
            e.printStackTrace() ;
        }  // end catch
    }  // end run
}  // end ListenServer

class HandleServer extends Thread  {

    Socket connection;

    InputStream inStream;
    DataInputStream inDataStream;
    OutputStream outStream;
    DataOutputStream outDataStream;

    String message;

    // **************  HandleServer
    HandleServer ( Socket socket )  {
        super ();
        connection =  socket;
    }  // end constructor


    // **************  run
    public void run  ()  {
        String tempStr = "";
        String line = "";

        try  {
            outStream = connection.getOutputStream ();
            outDataStream = new DataOutputStream ( outStream );
            inStream = connection.getInputStream ();
            inDataStream = new DataInputStream ( inStream );

            boolean flag = false;

            //Write the data to our internal buffer
            while(true){
                List list = new ArrayList();
                ISpiderManager spiderMgr = SpiderPeer.getInstance();
                Spider spider = new Spider();

                try{
                    list = spiderMgr.getSpiderInfo();       //从数据库中获得Spider抓取的数据
                }catch(Exception e){
                    e.printStackTrace();
                }

                for(int i=0; i<3; i++) {                  //因为有三只股票，循环三次
                    spider = (Spider)list.get(i);
                    outDataStream.writeBoolean(true);
                    outDataStream.writeUTF(spider.getName());
                    outDataStream.writeBoolean(true);
                    outDataStream.writeUTF(spider.getNameid());
                    outDataStream.writeBoolean(true);
                    outDataStream.writeUTF(spider.getDanWei());
                    outDataStream.writeBoolean(true);
                    outDataStream.writeUTF(String.valueOf(spider.getDatetime()));
                    outDataStream.writeBoolean(true);
                    outDataStream.writeUTF(spider.getPrev_Close());
                    outDataStream.writeBoolean(true);
                    outDataStream.writeUTF(spider.getDay_range());
                    outDataStream.writeBoolean(true);
                    outDataStream.writeUTF(spider.getLast_trade());
                    outDataStream.writeBoolean(true);
                    outDataStream.writeUTF(spider.getVolume());
                    outDataStream.writeBoolean(true);
                    outDataStream.writeUTF(spider.getChange());
                }

                try{                                    //每间隔五分钟后再次访问数据库，重新获得最新数据
                    Thread.sleep(1*1000*60*5);
                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }
        }  // end try
        catch ( EOFException e ) {
            try  {
                connection.close ();
                System.out.println("HandleServer: EOFException, handleSocket closed ok");
                //e.printStackTrace();
                return;
            }
            catch ( IOException ee )  {
                System.out.println("HandleServer: IOException, handleSocket closed ok");
                //ee.printStackTrace();
                return;
            }  // end IOException

        }  // end catch EOFException
        catch ( IOException e )  {
            System.out.println("HandleServer: IOException caught");
            //e.printStackTrace();
            return;
        }  // end catch IOException
        finally{
            try{
                outDataStream.close();
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }  // end run

}  // end HandleServer

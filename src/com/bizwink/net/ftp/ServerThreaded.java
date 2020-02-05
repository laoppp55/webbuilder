package com.bizwink.net.ftp;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.net.*;

import com.bizwink.cms.server.*;
import com.bizwink.net.ftp.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ServerThreaded extends HttpServlet {
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
            message = "jbsServerThreaded already Running";
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
    ServerThreaded source;
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
        int siteid = 0;
        int publishway = 0;
        boolean ftpflag = false;
        String siteip = "";
        String sitename = "";
        String docpath = "";
        String appPath = "";
        String ftpuser = "";
        String ftppasswd = "";
        String filepath = "";
        List list = new ArrayList();
        FTPClient ftpconn = null;

        try  {
            outStream = connection.getOutputStream ();
            outDataStream = new DataOutputStream ( outStream );
            inStream = connection.getInputStream ();
            inDataStream = new DataInputStream ( inStream );

            //从applet中获得传过来的siteid
            siteid = inDataStream.readInt();
            appPath = inDataStream.readUTF();

            //判断用户发布类型
            IPullContentManager pullContentMgr = PullContentPeer.getInstance();
            publishway = pullContentMgr.getPublishWay(siteid);
            if(publishway == 0){
                ftpflag = true;
            }

            //获得sitename
            sitename = pullContentMgr.getSiteName(siteid);

            //如果用户发布类型为ftp,进行操作
            if(ftpflag == true){
                //获得site信息
                PullContent pullcontent = pullContentMgr.getSiteInfo(siteid);
                siteip = pullcontent.getSiteIP();
                docpath = pullcontent.getDocPath();
                ftpuser = pullcontent.getFtpUser();
                ftppasswd = pullcontent.getFtpPasswd();

                //根据取得的site信息建立ftp连接
                try{
                    ftpconn = new FTPClient(siteip, 21);
                    ftpconn.login(ftpuser, ftppasswd);
                }catch(Exception e){
                    e.printStackTrace();
                }

                //创建根节点的路径
                String localpath = appPath + "sites" + java.io.File.separator + sitename + java.io.File.separator;
                File file= new File(localpath);
                if ( !file.exists()) {
                    file.mkdirs();
                }

                list.add(docpath);

                while ((true)&&(list.size() != 0)){
                    //获取最后一个节点的内容
                    filepath = (String)list.get(list.size()-1);

                    try{
                        //对最后一个节点进行操作（读取它下面的文件）
                        ftpconn.chdir("/");
                        String[] filename = ftpconn.dir(filepath,true);

                        //删除最后一个节点
                        list.remove(list.size()-1);

                        if(filename.length > 0){
                            for(int i=0;i<filename.length;i++){
                                String name = filename[i];

                                //判断是NT还是Linux
                                String mid = filename[i].substring(0,filename[i].indexOf(" "));
                                if(mid.equals("total"))
                                    continue;

                                //判断处理节点的下一层的文件是否是文件夹
                                //若是文件夹，将该处理节点的下一层的文件夹加入到list
                                //若是文件，将该处理节点的下一层的文件输出
                                String dname = name.substring(name.lastIndexOf(" ")+1,name.length());
                                String midname = filepath + "/" + dname;

                                if((!midname.startsWith("."))&&(midname.indexOf(".") == -1)){
                                    list.add(midname);

                                    //创建各节点的路径
                                    String midfilename = midname.substring(docpath.length(),midname.length());
                                    String lopath = localpath + midfilename;

                                    File dir= new File(lopath);
                                    if ( !dir.exists()) {
                                        dir.mkdirs();
                                    }
                                }else{
                                    //将下载的文件名写入到输出流，供applet读取
                                    if(!dname.startsWith(".")){
                                        //文件名
                                        outDataStream.writeUTF(midname);

                                        //ftp成功标志
                                        boolean succ = ftptodest(ftpconn,localpath,midname,docpath);
                                        if(succ == false){
                                            outDataStream.writeBoolean(true);
                                        }else{
                                            outDataStream.writeBoolean(false);
                                        }

                                        //applet循环标志
                                        outDataStream.writeUTF("!!!");
                                    }
                                }
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }  // end while

                //给applet传送结束标志
                outDataStream.writeUTF(" ");
                outDataStream.writeBoolean(true);
                outDataStream.writeUTF("@@@");
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

    //FTP下载文件
    public static boolean ftptodest(FTPClient ftp,String lopath,String newfilename,String docpath) {
        boolean errflag = false;
        String dirfilename = "";
        String mdirfilename = "";
        String midfilename = "";
        try {
            ftp.chdir("/");

            try{
                midfilename = newfilename.substring(docpath.length(),newfilename.length());
                mdirfilename = lopath + midfilename;
            }catch(Exception e){}

            String ext = mdirfilename.substring(mdirfilename.lastIndexOf(".")+1,mdirfilename.length());
            //根据要上传文件的类型，设定编码方式
            if (ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("jpeg")||ext.equalsIgnoreCase("gif")||ext.equalsIgnoreCase("mp3")||ext.equalsIgnoreCase("asf"))
                ftp.setType(FTPTransferType.BINARY);
            else
                ftp.setType(FTPTransferType.ASCII);

            // put a local file to remote host（上传/下传）
            ftp.get(mdirfilename, newfilename);
        }catch (IOException ex) {
            System.out.println("Caught exception: " + ex.getMessage());
            errflag = true;
        }catch (FTPException ex) {
            System.out.println("Caught exception: " + ex.getMessage());
            errflag = true;
        }
        return errflag;
    }

}  // end HandleServer

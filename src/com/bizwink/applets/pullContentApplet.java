package com.bizwink.applets;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;

import com.bizwink.net.ftp.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Eric
 * @version 1.0
 */

public class pullContentApplet extends Applet {
  static final int DEFAULT_PORT = 8902;
  TextField httpText,httpObject,socketObject,RMIObject;
  TextArea sockArea;
  Button cancel;

  private int siteid;
  private String appPath;
  private int num;
  private String snum;
  private boolean closeflag;

  public void init() {
    siteid = Integer.parseInt(getParameter("siteid"));
    appPath = getParameter("realpath");

    setLayout(new BorderLayout());
    Panel west = new Panel();
    west.setLayout(new GridLayout(1,1));
    west.add(new Label("FTP Files: ",Label.RIGHT));
    add("West",west);

    Panel center = new Panel();
    center.setLayout(new GridLayout(1,1));
    sockArea = new TextArea();
    sockArea.setEditable(false);
    sockArea.setBackground(Color.white);
    center.add(sockArea);
    add("Center",center);

    /*Panel south = new Panel();
    cancel = new Button("取 消");
    south.add(cancel);
    add("South",south);*/
  }

  public void start() {
    refresh();
  }

  public void refresh() {
    getDateUsingSocketText(sockArea);
  }

  private void getDateUsingSocketText(TextArea sockArea) {
    try {
      Socket socket = new Socket(getCodeBase().getHost(),getSocketPort());
      Thread thread = new pullContentConnection(sockArea,socket,siteid,appPath);
      thread.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*public boolean handleEvent(Event event) {
    switch (event.id) {
      case Event.ACTION_EVENT:
        if(event.target == cancel) {
          close();
          return true;
        }
    }
    return false;
  }

  public void close() {
    closeflag = true;
  }*/

  private int getSocketPort() {
    try {
      return Integer.parseInt(getParameter("socketPort"));
    } catch (NumberFormatException e) {
      return DEFAULT_PORT;
    }
  }
}

class pullContentConnection extends Thread {
  Socket client;
  TextArea sockArea;
  InputStream in = null;
  int siteid = 0;
  String appPath = "";
  boolean closeflag = false;

  pullContentConnection(TextArea sockArea,Socket client,int siteid,String appPath) {
    this.client = client;
    this.sockArea = sockArea;
    this.siteid = siteid;
    this.appPath = appPath;
    this.setPriority(NORM_PRIORITY - 1);
  }

  public void run() {

    try {
      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream(byteOut);
      if(appPath != ""){
        dos.writeInt(siteid);
        dos.writeUTF(appPath);
      }
      //dos.writeBoolean(closeflag);
      dos.flush();
      byte[] buf = byteOut.toByteArray();

      PrintStream out = new PrintStream(client.getOutputStream());
      out.write(buf);
      out.flush();

      in = client.getInputStream();
      DataInputStream result = new DataInputStream(new BufferedInputStream(in));

      boolean flag = true;
      boolean comflag = false;
      String filename = "";
      String endflag = "";
      String info = "";

      this.sockArea.setForeground(Color.blue);
      this.sockArea.setText(" Connecting...");
      this.sockArea.append("\r\n");
      this.sockArea.append(" Please wait...");

      while (flag == true) {
        //从servlet中读取传送的数据
        filename = result.readUTF();
        this.sockArea.append("\r\n");
        this.sockArea.setForeground(Color.blue);
        info = " Ftp the file "+ filename + " ...";

        if(info.length() > 220)
          info = " Ftp the file ..." + info.substring(30);
        this.sockArea.append(info);

        comflag = result.readBoolean();
        if(comflag == true){
          this.sockArea.append("\r\n");
          this.sockArea.append("  Completed.");
        }else{
          this.sockArea.append("\r\n");
          this.sockArea.setForeground(Color.red);
          this.sockArea.append("  Ftp failed.");
        }

        //设置循环结束标志
        endflag = result.readUTF();
        if(endflag.equals("@@@")){
          flag = false;
        }
      }

      //显示结束信息
      this.sockArea.append("\r\n");
      this.sockArea.append(" All Completed and Successful!");
      client.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

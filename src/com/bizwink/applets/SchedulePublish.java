package com.bizwink.applets;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class SchedulePublish extends JApplet implements ActionListener
{
  static final int DEFAULT_PORT = 8902;
  private String userid = null;
  private String scheme = null;
  private String servername = null;
  private String serverport = null;
  private String realpath = null;

  OutputStream outStream;
  DataOutputStream outDataStream;
  InputStream in = null;
  Frame f = null;
  JTextArea sockArea;

  public void init()
  {
    userid = getParameter("username");
    scheme = getParameter("scheme");
    servername = getParameter("servername");
    serverport = getParameter("serverport");
    realpath = getParameter("realpath");
    int runflag = 0;

    //调用AutoPubApp.class去启动后台的自动发布进程
    try
    {
      URL url = new URL(scheme+"://"+servername+":"+serverport+"/webbuilder/autopubapp");
      URLConnection conn = url.openConnection();
      InputStream in = conn.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      runflag = Integer.parseInt(br.readLine());
      br.close();
      in.close();
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }

    Container contentPane = getContentPane();
    for(Container c = this; c != null; c = c.getParent())
    {
      if (c instanceof Frame)
        f = (Frame)c;
    }

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    gc.gridx = 0;
    gc.gridy = 0;
    //gc.gridwidth = 2;
    //gc.gridheight = 1;
    JButton b = new JButton("启动");
    b.addActionListener(this);
    buttonPanel.add(b, gc);
    contentPane.add(buttonPanel);

    gc.gridx = 1;
    gc.gridy = 0;
    //gc.gridwidth = 2;
    //gc.gridheight = 1;
    b = new JButton("挂起");
    b.addActionListener(this);
    buttonPanel.add(b, gc);
    contentPane.add(buttonPanel);

    gc.gridx = -1;
    gc.gridy = -1;
    sockArea = new JTextArea();
    buttonPanel.add(sockArea, gc);
    contentPane.add(buttonPanel);

    buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE,2),"自动发布系统",TitledBorder.CENTER,TitledBorder.TOP));
    contentPane.add(buttonPanel);

    if (runflag == 1)
    {
      this.sockArea.setText("发布系统启动");
    }
    else if (runflag == 0)
    {
      this.sockArea.setText("发布系统挂起");
    }
  }

  public void actionPerformed(ActionEvent e)
  {
    String cmd = e.getActionCommand();

    try
    {
      if (cmd.equals("启动"))
      {
        //送出数据
        Socket socket = new Socket(getCodeBase().getHost(),getSocketPort());
        outStream = socket.getOutputStream ();
        outDataStream = new DataOutputStream ( outStream );
        outDataStream.writeInt(1);
        outDataStream.writeUTF(userid + "\r\n");
        outDataStream.writeUTF(realpath + "\r\n");
        outDataStream.flush();

        //读回反应数据
        in = socket.getInputStream();
        DataInputStream result = new DataInputStream(new BufferedInputStream(in));
        String dataflag = result.readUTF();
        this.sockArea.setText(dataflag);
      }
      else if(cmd.equals("挂起"))
      {
        //发送命令给发布服务器
        Socket socket = new Socket(getCodeBase().getHost(),getSocketPort());
        outStream = socket.getOutputStream ();
        outDataStream = new DataOutputStream ( outStream );
        outDataStream.writeInt(0);
        outDataStream.writeUTF("\r\n");
        outDataStream.writeUTF("\r\n");
        outDataStream.flush();

        //从发布服务器获取返回信息
        in = socket.getInputStream();
        DataInputStream result = new DataInputStream(new BufferedInputStream(in));
        String dataflag = result.readUTF();
        this.sockArea.setText(dataflag);
      }
      else if (cmd.equals("停止"))
      {
        //发送命令给发布服务器
        Socket socket = new Socket(getCodeBase().getHost(),getSocketPort());
        outStream = socket.getOutputStream ();
        outDataStream = new DataOutputStream ( outStream );
        outDataStream.writeInt(2);
        outDataStream.writeUTF("\r\n");
        outDataStream.writeUTF("\r\n");
        outDataStream.flush();

        //从发布服务器获取返回信息
        in = socket.getInputStream();
        DataInputStream result = new DataInputStream(new BufferedInputStream(in));
        String dataflag = result.readUTF();
        this.sockArea.setText(dataflag);
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private int getSocketPort()
  {
    try
    {
      return Integer.parseInt(getParameter("socketPort"));
    }
    catch (NumberFormatException e)
    {
      return DEFAULT_PORT;
    }
  }
}
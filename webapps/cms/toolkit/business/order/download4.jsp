<%@page import="com.jspsmart.upload.*" contentType="text/html;charset=gb2312"%>
<%@ page import="java.sql.Timestamp"%>
<%
  //获取当前路径下的文件
   String path = request.getRealPath(".");
    Timestamp today = new Timestamp(System.currentTimeMillis());
  String todaytime = String.valueOf(today);
  todaytime = todaytime.substring(0, todaytime.indexOf(" "));
  todaytime = todaytime.replaceAll("-",""); 
  //生成下载连接
  String name = todaytime + "phone.txt";
  String url = path+"/"+name;
   //新建一个SmartUpload对象
   SmartUpload su = new SmartUpload();
   //初始化
   su.initialize(pageContext);
   // 设定contentDisposition为null以禁止浏览器自动打开文件
   su.setContentDisposition(null);
   su.downloadFile(url);

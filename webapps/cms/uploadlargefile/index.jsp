<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>上传进度条</title>
	<script type="text/javascript" src="./js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="./js/progressBar.js"></script>
	<link type="text/css" rel="stylesheet" href="./css/progressBar.css">
	
  </head>
  
  <body>
  <iframe id='target_upload' name='target_upload' src=''
			style='display: none'></iframe>
   <form action="./upload" id="uploadForm" enctype="multipart/form-data" method="post" target="target_upload">
   <input type="file" name="upload"> <input type="button" id="subButton" value="上传">
   </form>
   <div id="progress">
   <div id="title"><span id="text">上传进度</span><div id="close">X</div></div>
   <div id="progressBar">
   	<div id="uploaded"></div>
   	</div>
   	<div id="info"></div>
   </div>
  </body>
</html>

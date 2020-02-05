<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.bizwink.util.pub.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>接口调度用户认证</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script language="javascript">
	  function setFocus() {
		  document.formLogin.userName.focus();
	  }
	  function doSubmit() {
		    if(document.formLogin.userName.value == "") {
		    	alert("请输入用户名！");
			    return false;
		    }
		    return true;;
		  }
	</script>
  </head>
  <body onload="setFocus()">
  <form name ="formLogin" action="itfReadSchedule" method="post">
  <input type=hidden name="fromLogin" value="1" />
  <input type=hidden name="msg" />
  <table border=0 style="font:宋体;font-size:11pt;">
    <tr><td>用户名：</td>
           <td><input type="text" name="userName" style="width:150px" value="<%=StrUtil.toStr(request.getAttribute("userName"))%>"></td>
    </tr>
    <tr><td>密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
           <td><input type="password" name="pwd" style="width:150px" value="<%=StrUtil.toStr(request.getAttribute("pwd"))%>">
                  <font color=red><%=StrUtil.toStr(request.getAttribute("msg"))%></font></td>
    </tr>
  </table>
  <input type="submit" value="提交" onclick="return doSubmit();">
  </form>
  </body>
</html>

<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%
   	String UserName = (String)request.getParameter("userName");
   	String Pass = (String)request.getParameter("pass");

    IUregisterManager regMgr = UregisterPeer.getInstance();
    String sitename = request.getServerName();
    int Siteid = regMgr.getSiteid(sitename);

	int code = regMgr.login(UserName,Pass,Siteid);
	if(code==1){
		Uregister urg = new Uregister();
		urg = regMgr.getUserInfo(UserName,Siteid);
		session.setAttribute("urg",urg);
		response.sendRedirect("ok.jsp?name="+urg.getMemberid());
	}else{
		session.setAttribute("urg","");
	}
%>
<html>
  <head>
    <title>登陆</title>
    <script type="text/JavaScript"> 
    function check(){
    	var userName = document.all.userName.value;
    	var passWord = document.all.pass.value;
    	if(userName==""){
    		alert("请输入用户名！");
    		return false;
    	}
    	if(passWord == ""){
    		alert("请输入密码！");
    		return false;
    	}else{
    	var objXmlc;
	    if (window.ActiveXObject){
	    	objXmlc = new ActiveXObject("Microsoft.XMLHTTP");
		}else if (window.XMLHttpRequest){
	    	objXmlc = new XMLHttpRequest();
		}
		objXmlc.open("POST", "Userlogin.jsp?username="+userName+"&pass=" +passWord, false);
		objXmlc.send(null);       
		var res = objXmlc.responseText; 
		var re = res.split('.'); 		
		var retstrs = re[0];
		if(retstrs==0){
			alert("用户名或密码错误！");
			return false;
		}
	}
	document.all.form_id.action = "login.jsp";
	document.all.form_id.submit();
 }
 
 function zhuce(){
 	var url = "register.jsp";
 	window.location.href = url;
 }
    </script>
  </head>
  <body>
  <form id="form_id" name="form_id" mothod="post">
   <table>
     <tr>
       <td>用户名：</td>
       <td><input type="text" id="userName" name="userName"><td>
     </tr>
     <tr>
       <td>密码：</td>
       <td><input type="password" id="pass" name="pass"><td>
     </tr>
     <tr>
       <td><input type="button" value="登陆" onclick="javascript:check()"></td>
       <td><input type="button" value="注册" onclick="javascript:zhuce()"><td>
     </tr>
   </table>
   </form>
  </body>
</html>

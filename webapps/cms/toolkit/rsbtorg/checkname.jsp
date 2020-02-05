<%@ page import="com.bizwink.webapps.register.*" %>
<%@ page contentType="text/html;charset=gbk"%>
<%
String userid= request.getParameter("userid")==null?"":request.getParameter("userid");
IUregisterManager regMgr = UregisterPeer.getInstance();
String strout = "";
if(userid.trim().equals("")){
	//strout = "empty";
	strout = "用户名不能为空，请重新填写用户名!";
}else if(userid.trim().indexOf("@")!=-1){
	strout = "用户名不能含有@，请重新输入用户名！";
}else{
	if(regMgr.userExist(userid)){
		strout = "用户已经存在!";
	}else{
		strout = "success";
	}
}
out.write(strout);
%>





<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page  contentType="text/html;charset=GBK" language="java" %>

<%
	String Password = (String)request.getParameter("pass").trim();
	String Name = (String)request.getParameter("uid").trim();
	IUregisterManager regMgr =  UregisterPeer.getInstance();
    int siteid= ParamUtil.getIntParameter(request,"siteid",-1);
    int flag= regMgr.getPassword(Name,Password,siteid);
	if(flag==1){
		out.write(1+"-¾ÉÃÜÂëÕýÈ·!");
	}else{
		out.write(0+"-¾ÉÃÜÂë´íÎó!");
	}
	
%>
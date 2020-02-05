<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%
	String Password = (String)request.getParameter("pass");
	String Name = (String)request.getParameter("name");
    IUregisterManager regMgr = UregisterPeer.getInstance();
    String sitename = request.getServerName();
    String slash_sitename = StringUtil.replace(sitename,".","_");
    int Siteid = regMgr.getSiteid(sitename);

    Uregister ug= regMgr.login(Name,Password,Siteid);
	if(ug != null){
		out.write("欢迎<b>"+ Name + "</b>登录本网站<br>退出请点击<a href=/_commons/logout.jsp><font color=red>退出</font></a>");
        session.setAttribute("UserLogin",ug);
    }else{
		response.sendRedirect("/" + slash_sitename + "/_prog/login.jsp");
        //response.sendRedirect("/www_sinopec_com/_prog/login.jsp");
    }
%>
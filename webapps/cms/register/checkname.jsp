<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.register.IRegisterManager" %>
<%@ page import="com.bizwink.cms.register.RegisterPeer" %>
<%@page contentType="text/html;charset=gbk" %>
<%
    IRegisterManager speer= RegisterPeer.getInstance();
    String email= ParamUtil.getParameter(request,"name");
    String str= speer.checkUser(email);
    out.write(str);


%>
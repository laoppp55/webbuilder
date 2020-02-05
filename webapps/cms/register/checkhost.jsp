<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.register.IRegisterManager" %>
<%@ page import="com.bizwink.cms.register.RegisterPeer" %>
<%@page contentType="text/html;charset=gbk" %>
<%
    IRegisterManager speer= RegisterPeer.getInstance();
    String hostname= ParamUtil.getParameter(request,"host");
    System.out.println(hostname);
    String str= speer.checkHost(hostname);
    out.write(str);
%>
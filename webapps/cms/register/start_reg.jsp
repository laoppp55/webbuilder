<%@ page import="com.bizwink.cms.register.IRegisterManager" %>
<%@ page import="com.bizwink.cms.register.RegisterPeer" %>
<%@page contentType="text/html;charset=gbk" %>
<%
    IRegisterManager speer= RegisterPeer.getInstance();
    int hcode= speer.getCode();
    response.sendRedirect("/webbuilder/register/register.jsp?hcode=" + hcode);
%>
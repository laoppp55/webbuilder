<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    String cardnum = ParamUtil.getParameter(request,"cardnum");
    session.setAttribute("card",cardnum);
%>
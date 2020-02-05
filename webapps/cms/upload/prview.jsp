<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
String picname= ParamUtil.getParameter(request,"name");
%>
<img src="<%=picname%>"> 
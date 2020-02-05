<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.register.IRegisterManager" %>
<%@ page import="com.bizwink.cms.register.RegisterPeer" %>
<%@page contentType="text/html;charset=gbk" %>
<%
    IRegisterManager speer= RegisterPeer.getInstance();
    String email= ParamUtil.getParameter(request,"email");
    String str= speer.checkEmail(email);
    out.write(str);


%>
<%@ page isErrorPage="true" %>
<%@ page import="java.util.*,
    java.net.*,
    com.bizwink.util.*" contentType="text/html;charset=gbk"%>
<%
    String errorMessage = ParamUtil.getParameter(request,"msg");
    String errorMessage2 = (String)request.getParameter("message");
%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>错误</title>
<link rel=stylesheet type=text/css href=style/global.css></head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    if( errorMessage != null ) {
        out.println("错误信息:"+errorMessage);
    } else if(errorMessage2 !=null){
        out.println("错误信息:"+errorMessage2);
    }else{
        out.println("通用错误.exception:"+ exception);
    }
%>
</body></html>
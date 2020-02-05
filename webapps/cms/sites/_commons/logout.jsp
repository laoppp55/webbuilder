<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%
    Uregister ug= (Uregister)session.getAttribute("UserLogin");
    String fromurl = request.getHeader("REFERER");
    session.removeAttribute("UserLogin");
    if(ug != null){
        session.removeAttribute("UserLogin");
	response.sendRedirect("/");
    } else {
        response.sendRedirect("/");
    }
%>
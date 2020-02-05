<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@page contentType="text/html;charset=gbk"%>
<%
    Uregister ug = (Uregister)session.getAttribute("UserLogin");
    if (ug.getMemberid() != null) {
        System.out.println("用户登陆成功");
        out.write("欢迎<b>"+ ug.getMemberid() + "</b>登录本网站<br>退出请点击<a href=/_commons/logout.jsp><font color=red>退出</font></a>");
        if(ug.getUsertype() == 2)
            out.write("<br>查看请点击<a href=/internal/index.shtml><font color=red>查看</font></a>");
    } else {
        System.out.println("用户登陆失败");
        out.write("nologin");
    }
%>
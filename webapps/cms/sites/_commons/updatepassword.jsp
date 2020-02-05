<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@page contentType="text/html;charset=GBK"  %>
<%
    //通过session 获得用户id
    Uregister username = (Uregister) session.getAttribute("UserLogin");
    if(username == null){
        out.print("<script language=\"javascript\">alert(\"您还没有登录，请先登录！\");window.location=\"/\";</script>");
        return;
    }
    String password = ParamUtil.getParameter(request,"newpasswd");
    IUregisterManager uMgr = UregisterPeer.getInstance();
    int code = uMgr.updateUserPassword(username.getId(),password);
    String fromurl = request.getHeader("REFERER");
    if(code == 0){
        out.print("<script language=\"javascript\">alert(\"修改成功！\");window.location=\""+fromurl+"\";</script>");
    }else{
        out.print("<script language=\"javascript\">alert(\"修改失败！\");window.location=\""+fromurl+"\";</script>");
    }
%>
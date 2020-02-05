<%@ page import="com.bizwink.cms.security.AuthPeer" %>
<%@ page import="com.bizwink.cms.security.IAuthManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    IAuthManager authMgr = AuthPeer.getInstance();
    String username = ParamUtil.getParameter(request, "uid");

    //获取用户端的IP地址
    String user_ip = null;
    if (request.getHeader("x-forwarded-for") == null) {
        user_ip = request.getRemoteAddr();
    } else {
        user_ip = request.getHeader("x-forwarded-for");
    }

    int status  = authMgr.getUserLoginStatus(username,user_ip);

    out.print(status);
    out.flush();
%>

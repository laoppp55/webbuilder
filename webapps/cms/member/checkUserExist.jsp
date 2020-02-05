<%@ page import="com.bizwink.cms.util.*,
                com.bizwink.cms.register.*" contentType="text/html;charset=utf-8"%>
<%
    IRegisterManager registerManager = RegisterPeer.getInstance();
    String username = ParamUtil.getParameter(request, "uid");
    boolean status  = registerManager.userExist(username);
    if (status)
        out.print(1);
    else
        out.print(0);
    out.flush();
%>

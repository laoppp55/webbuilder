<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@page contentType="text/html;charset=gbk" %>
<%
    String username = ParamUtil.getParameter(request, "userid");
    String password = ParamUtil.getParameter(request, "passwd");
    String company = ParamUtil.getParameter(request, "comp");
    String msg = ParamUtil.getParameter(request, "msg");
    boolean doLogin = ParamUtil.getBooleanParameter(request, "doLogin");
    String redirect = request.getHeader("REFERER");
    int usertype = ParamUtil.getIntParameter(request, "usertype", 1);
    int siteid = ParamUtil.getIntParameter(request, "siteid", 39);

    System.out.println("username=" + username);
    System.out.println("password=" + password);

    String errorMessage = "";
    if (doLogin) {
        IUregisterManager uMgr = UregisterPeer.getInstance();
        try {
            Uregister uregister = uMgr.login(username, password, siteid);
            if (uregister.getMemberid() != null) {                    //转向模板选择页面
                session.setAttribute("UserLogin", uregister);
                response.sendRedirect("/index.shtml");
            } else {
                response.sendRedirect("/_prog/register.jsp");
            }
        } catch (Exception ue) {
            errorMessage = "用户名密码不符，重新输入!";
        }
    }
%>
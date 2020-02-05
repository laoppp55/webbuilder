<%@ page import="com.bizwink.cms.server.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
%>
<%
    String username = ParamUtil.getParameter(request, "username");
    String password = ParamUtil.getParameter(request, "password", true);
    String msg = ParamUtil.getParameter(request, "msg");
    boolean doLogin = ParamUtil.getBooleanParameter(request, "doLogin");
    String errorMessage = "";
    int siteid = 0;
    CmsServer.getInstance().init();
    System.out.println("doLogin=" + doLogin);
    System.out.println("username=" + username);
    System.out.println("password=" + password);

    if (doLogin) {
        IAuthManager authMgr = AuthPeer.getInstance();
        try {
            Auth authToken = authMgr.getAuth(username, password);
            if (authToken!=null)  {                    //转向模板选择页面
                session.setAttribute("CmsAdmin", authToken);
                session.setMaxInactiveInterval(60*60*1000);
                siteid = authToken.getSiteID();
                out.println(siteid);
                out.flush();
            } else {
                out.println("error");
                out.flush();
            }
        } catch (UnauthedException ue) {
            errorMessage = "用户名密码不符，重新输入!";
            out.println(errorMessage);
            out.flush();
        }
    } else {
        out.println("用户登录失败");
        out.flush();
    }
%>

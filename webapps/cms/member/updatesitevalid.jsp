<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK"%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    int siteid = ParamUtil.getIntParameter(request, "siteid", 0);
    int valid = ParamUtil.getIntParameter(request, "valid", 0);
    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
    siteMgr.updatesitevalid(siteid,valid);
    out.println("ok");
    return;
%>
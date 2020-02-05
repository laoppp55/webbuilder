<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
    List siteList = siteMgr.getAllSiteInfo();
%>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type="text/css" href="../style/global.css">
</head>

<BODY bgcolor=#dddddd text=#000000 link=#0000ff vlink=#0000ff alink=#ff0000>
<table border=0 cellspacing=0 cellpadding=0 width="100%">
    <tr height=6>
        <td></td>
    </tr>
    <tr>
        <td><img src="../menu-images/menu_new_root.gif" align="middle"><font color=blue>站点列表</font></td>
    </tr>
    <%
        for (int i = 0; i < siteList.size(); i++) {
            SiteInfo site = (SiteInfo) siteList.get(i);
    %>
    <tr>
        <td>
            <%if (i == siteList.size() - 1) {%><img src="../menu-images/menu_corner.gif" align="middle"><%} else {%><img
                src="../menu-images/menu_tee.gif" align="middle"><%}%><img src="../menu-images/menu_link_default.gif"
                                                                           align="middle">
            <a href="groupLogInfo.jsp?siteID=<%=site.getSiteid()%>" target="cmsright"><%=site.getDomainName()%>
            </a>
        </td>
    </tr>
    <%}%>
</table>
</BODY>
</html>
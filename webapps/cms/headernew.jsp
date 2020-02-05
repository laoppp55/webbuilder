<%@ page import="java.sql.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    String userid = authToken.getUserID();
    if (userid.compareToIgnoreCase("admin") != 0) {
        if (((authToken != null) && SecurityCheck.hasPermission(authToken, 1)) || SecurityCheck.hasPermission(authToken, 54)) {
            out.println("<td><a href=../column/indexnew.jsp?rightid=1 target=_self><img src='../images/lmsz.gif' border='0'></a></td>");
            //out.println("<td><a href='#' onclick='javascript:columnmgr(" + rightid + ");'><img src='../images/lmsz.gif' border='0'></a></td>");
        }

        if (((authToken != null) && SecurityCheck.hasPermission(authToken, 2)) || SecurityCheck.hasPermission(authToken, 54)) {
            out.println("<td><a href=../template/indexnew.jsp?rightid=2 target=_self><img src='../images/mjgl.gif' border='0'></a></td>");
        }

        //if (((authToken != null) && SecurityCheck.hasPermission(authToken, 2)) || SecurityCheck.hasPermission(authToken, 54)) {
        //    out.println("<td><a href=newtemplate/index.jsp?rightid=2 target=main><img src='images/mjgl.gif' border='0'></a></td>");
        //} else {
        //    out.println("<td><img src='images/mjgl1.gif' border='0'></td>");
        //}

        if (((authToken != null) && SecurityCheck.hasPermission(authToken, 3)) || SecurityCheck.hasPermission(authToken, 54)) {
            out.println("<td><a href=../article/indexnew.jsp?rightid=3 target=_self><img src='../images/wzbj.gif' border='0'></a></td>");
        }

        if (((authToken != null) && SecurityCheck.hasPermission(authToken, 7)) || SecurityCheck.hasPermission(authToken, 54)) {
            out.println("<td><a href=../articlemove/indexnew.jsp?rightid=7 target=_self><img src='../images/ydwz.gif' border='0'></a></td>");
        }

        if (((authToken != null) && SecurityCheck.hasPermission(authToken, 5)) || SecurityCheck.hasPermission(authToken, 54)) {
            out.println("<td><a href=../audit/indexnew.jsp?rightid=5 target=_self><img src='../images/wzsh.gif' border='0'></a></td>");
        }

        if (((authToken != null) && SecurityCheck.hasPermission(authToken, 6)) || SecurityCheck.hasPermission(authToken, 54) || SecurityCheck.hasPermission(authToken, 8)) {
            if (SecurityCheck.hasPermission(authToken, 6) || SecurityCheck.hasPermission(authToken, 54))
                out.println("<td><a href=../publish/indexnew.jsp?rightid=6 target=_self><img src='../images/wzfb.gif' border='0'></a></td>");
            else
                out.println("<td><a href=../publish/indexnew.jsp?rightid=8 target=_self><img src='../images/wzfb.gif' border='0'></a></td>");
        }

        if ((authToken != null) && !SecurityCheck.hasPermission(authToken, 54)) {
            out.println("<td><a href=member/changePass.jsp target=main><img src='../images/xgma.gif' border='0'></a></td>");
        }

        if ((authToken != null) && SecurityCheck.hasPermission(authToken, 54)) {
            out.println("<td><a href=member/index.jsp target=main><img src='../images/xtsz.gif' border='0'></a></td>");
        }

        //if ((authToken != null) && SecurityCheck.hasPermission(authToken, 54)) {
        //    out.println("<td><a href=toolkit/business/index1.jsp target=_parent><img src='../images/dzsw.jpg' border='0'></a></td>");
        //}

        if (authToken != null && SecurityCheck.hasPermission(authToken, 54)) {
            out.println("<td><a href=toolkit/index.jsp target=main><img src='../images/gjx.gif' border='0'></a></td>");
        }
    } else {
        out.println("<td><a href=member/admin_index.jsp target=main><img src='../images/yhgl.gif' border='0'></a></td>");
        out.println("<td><a href=member/siteManage.jsp target=main><img src='../images/zdgl.gif' border='0'></a></td>");
        out.println("<td><a href=adminx/autoPub.jsp target=main><img src='../images/swgl.gif' border='0'></a></td>");
        out.println("<td><a href=program/pmanager.jsp target=main><img src='../images/join2009572.jpg' border='0'></a></td>");
        out.println("<td><a href=member/listStyle.jsp target=main><img src='../images/yswj.gif' border='0'></a></td>");
        out.println("<td><a href=movecolumn/index.jsp target=main><img src='../images/lmqy.gif' border='0'></a></td>");
        out.println("<td><a href=member/logManage.jsp target=main><img src='../images/gzl.gif' border='0'></a></td>");
        //out.println("<td><a href=joincompany/list.jsp target=main>加盟商管理</a></td>");
        //out.println("<td><a href=navigator/listNavigator.jsp target=main>导航条管理</a></td>");
    }
%>

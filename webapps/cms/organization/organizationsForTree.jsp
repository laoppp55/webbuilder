<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.util.JSON" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-1-10
  Time: 下午11:50
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String treedata = null;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
        treedata = organizationService.getTreeDataForOrg(BigDecimal.valueOf(authToken.getSiteID()));
    }

    if (treedata!=null) {
        out.print(treedata);
    } else {
        out.print("nodata");
        out.flush();
    }
%>
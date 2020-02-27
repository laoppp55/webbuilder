<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.dubboservice.service.CompanyService" %>
<%@ page import="com.bizwink.dubboservice.service.SiteinfoService" %>
<%@ page import="com.bizwink.po.Siteinfo" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext appContext = SpringInit.getApplicationContext();
    String sitename = request.getServerName();
    Companyinfo companyinfo = null;
    if (appContext!=null) {
        CompanyService companyService = (CompanyService)appContext.getBean("CompanyService");
        SiteinfoService siteinfoService = (SiteinfoService)appContext.getBean("SiteinfoService");
        Siteinfo siteinfo = siteinfoService.selectBySitename(sitename);
        companyinfo = companyService.getCompany(siteinfo.getSITEID());
    }
%>
<html>
<head>
    <title></title>
</head>
<body>
<%=companyinfo.getCOMPANYLATITUDE()%>
<%=companyinfo.getCOMPANYLONGITUDE()%>
</body>
</html>

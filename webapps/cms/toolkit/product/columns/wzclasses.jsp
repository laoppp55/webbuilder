<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="stp" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/fn.tld" prefix="fn" %>
<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"%>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.io.RandomAccessFile" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.bizwink.service.WuziclassService" %>
<%@ page import="com.bizwink.po.WzClass" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    BigDecimal siteid = BigDecimal.valueOf(authToken.getSiteID());
    ApplicationContext appContext = SpringInit.getApplicationContext();
    String json_data = null;
    List<WzClass> ll = null;
    if (appContext!=null) {
        WuziclassService wuziclassService = (WuziclassService)appContext.getBean("wuziclassService");
        ll =  wuziclassService.getMainClasses(BigDecimal.valueOf(0));
    }
%>
<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>

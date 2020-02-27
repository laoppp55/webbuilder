<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-6-19
  Time: 上午10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.util.filter" %>
<%@ page import="com.bizwink.dao.TemplateService" %>
<%@ page import="com.bizwink.dao.TemplateManager" %>
<%@ page import="com.bizwink.po.Template" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = null;

    //入股SESSION失效，试试COOKIE是否可以读出来
    Cookie[] cookies = request.getCookies();
    String cookie_value = null;
    for(Cookie c :cookies ){
        if (c.getName().equalsIgnoreCase("AuthInfo_cookie")) {
            SecurityUtil securityUtil = new SecurityUtil();
            cookie_value = securityUtil.detrypt(c.getValue(),null);
            break;
        }
    }

    int retcode = 0;
    if (cookie_value != null) {
        int posi = cookie_value.indexOf("-");
        username = cookie_value.substring(0, posi);
        int columnid = ParamUtil.getIntParameter(request, "columnid", 0);
        int siteid =  ParamUtil.getIntParameter(request, "siteid", 0);
        String content = filter.excludeHTMLCode(ParamUtil.getParameter(request, "brief"));
        TemplateService templateService = new TemplateManager();
        Template template = new Template();
        template.setSITEID(BigDecimal.valueOf(siteid));
        template.setCOLUMNID(BigDecimal.valueOf(columnid));
        template.setCONTENT(content);
        template.setEDITOR(username);
        retcode = templateService.CreateTemplate(template);
    } else {
        retcode = -4;
    }


%>
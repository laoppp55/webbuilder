<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="com.bizwink.service.UsersService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-6-6
  Time: 上午10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    int nodeid = ParamUtil.getIntParameter(request,"id",0);
    String pagesstr = filter.excludeHTMLCode(request.getParameter("currpage"));
    int pages = 1;  //当前页
    if (pagesstr == null) {
        pages = 1;
    } else {
        pages = Integer.parseInt(pagesstr);
    }
    int range = 20;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    Organization rootOrganization = null;
    List<Users> users = new ArrayList<Users>();
    if (appContext!=null) {
        OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
        UsersService usersService = (UsersService)appContext.getBean("usersService");
        rootOrganization = organizationService.getRootOrganization(BigDecimal.valueOf(siteid));
        //System.out.println(rootOrganization.getID().intValue() + "==" + nodeid);
        if (nodeid == 0)
            users = usersService.getUsersByCustomer(BigDecimal.valueOf(siteid),BigDecimal.valueOf((pages-1)*range+1),BigDecimal.valueOf(pages*range));
        else if (rootOrganization.getID().intValue()==nodeid)
            users = usersService.getUsersByCustomer(BigDecimal.valueOf(siteid),BigDecimal.valueOf((pages-1)*range+1),BigDecimal.valueOf(pages*range));
        else {
            List<Organization> organizations = organizationService.getSubOrgtreeByParant(BigDecimal.valueOf(siteid),BigDecimal.valueOf(nodeid));
            List<Integer> orgids = new ArrayList<Integer>();
            for(int ii=0;ii<organizations.size();ii++) {
                orgids.add(organizations.get(ii).getID().intValue());
            }
            users = usersService.getUsersByParentOrgID(BigDecimal.valueOf(siteid),orgids,BigDecimal.valueOf((pages-1)*range+1),BigDecimal.valueOf(pages*range));
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>网站内容管理系统--栏目管理</title>
    <link rel="stylesheet" href="../design/css/layui.css">
    <link rel="stylesheet" href="../css/mycss.css">
    <link rel="stylesheet" href="../css/cms_css.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <!--引用xtree-->
    <script type="text/javascript" src="../design/layui-xtree.js"></script>
    <script>
        $(document).ready(function(){
            layui.use('element', function(){
                var element = layui.element;
            });
        });
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">CMS-栏目管理</div>
        <jsp:include page="../include/menu.jsp"></jsp:include>
    </div>

    <div class="layui-body" style="left: 0px">
        <!-- 内容主体区域 -->
        <div id="contentid">

        </div>
    </div>
    <div class="layui-footer" style="left: 0px">
        <!-- 底部固定区域 -->
        版权信息： © 2003-2019 北京潮虹伟业科技有限公司，版权所有。
    </div>
</div>
</body>
</html>

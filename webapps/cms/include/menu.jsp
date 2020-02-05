<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.cms.security.SecurityCheck" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username = authToken.getNickname();
%>
<script>
    function goout() {
        window.location.href = "../exit.jsp";
    }
</script>
<ul class="layui-nav layui-layout-left">
    <%if (((authToken != null) && SecurityCheck.hasPermission(authToken, 1)) || SecurityCheck.hasPermission(authToken, 54)) {%>
    <li class="layui-nav-item"><a href="../column/index_new.jsp?rightid=1">栏目管理</a></li>
    <%}%>
    <%if (((authToken != null) && SecurityCheck.hasPermission(authToken, 2)) || SecurityCheck.hasPermission(authToken, 54)) {%>
    <li class="layui-nav-item"><a href="../template/indexnew.jsp?rightid=2">模板管理</a></li>
    <%}%>
    <%if (((authToken != null) && SecurityCheck.hasPermission(authToken, 3)) || SecurityCheck.hasPermission(authToken, 54)) {%>
    <li class="layui-nav-item"><a href="../article/indexnew.jsp?rightid=3">文章管理</a></li>
    <%}%>
    <%if (((authToken != null) && SecurityCheck.hasPermission(authToken, 7)) || SecurityCheck.hasPermission(authToken, 54)) {%>
    <li class="layui-nav-item"><a href="../articlemove/indexnew.jsp?rightid=7">文章迁移</a></li>
    <%}%>
    <%if (((authToken != null) && SecurityCheck.hasPermission(authToken, 6)) || SecurityCheck.hasPermission(authToken, 54) || SecurityCheck.hasPermission(authToken, 8)) {%>
    <%if (SecurityCheck.hasPermission(authToken, 6) || SecurityCheck.hasPermission(authToken, 54)) {%>
    <li class="layui-nav-item"><a href="../publish/indexnew.jsp?rightid=6">文章发布</a></li>
    <%} else {%>
    <li class="layui-nav-item"><a href="../publish/indexnew.jsp?rightid=8">文章发布</a></li>
    <%}}%>
    <!--li class="layui-nav-item"><a href="javascript:;">系统设置</a></li-->
    <%if (authToken != null && SecurityCheck.hasPermission(authToken, 54)) {%>
    <li class="layui-nav-item">
        <a href="javascript:;">系统设置</a>
        <dl class="layui-nav-child">
            <dd><a href="../member/indexnew.jsp">用户管理</a></dd>
            <dd><a href="../organization/indexnew.jsp">组织架构管理</a></dd>
            <!--d><a href="../member/index.jsp">用户管理(旧)</a></dd>
            <dd><a href="../roles/index.jsp">角色管理</a></dd>
            <dd><a href="../rights/index.jsp">权限管理</a></dd-->
        </dl>
    </li>
    <%}%>
    <%if (authToken != null && SecurityCheck.hasPermission(authToken, 54)) {%>
    <li class="layui-nav-item"><a href="../toolkit/index.jsp">工具箱</a></li>
    <%}%>
</ul>
<ul class="layui-nav layui-layout-right">
    <li class="layui-nav-item">
        <a href="javascript:;">
            <img src="/webbuilder/images/help.gif" class="layui-nav-img">
            帮助
        </a>
        <dl class="layui-nav-child">
            <dd><a href="">基本资料</a></dd>
            <dd><a href="">安全设置</a></dd>
        </dl>
    </li>
    <li class="layui-nav-item"><a href="javascript:goout();">欢迎<font color="red"><%=username%></font>&nbsp;&nbsp;退出</a></li>
</ul>

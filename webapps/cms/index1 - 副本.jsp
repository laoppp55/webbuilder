<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.security.SecurityCheck" %>
<%--Created by IntelliJ IDEA.
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

    String deptid = authToken.getDepartment();
%>
<!DOCTYPE html>
<html>
<head>
    <title>网站内容管理系统</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="design/css/layui.css">
    <link rel="stylesheet" href="css/mycss.css">
    <link rel="stylesheet" href="css/cms_css.css">
    <link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/themes/icon.css">

    <script type="text/javascript" src="js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="js/json2.js"></script>
    <script type="text/javascript" src="design/layui.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>

    <!--引用xtree-->
    <script type="text/javascript" src="design/layui-xtree.js"></script>
    <script>
        $(document).ready(function(){
            layui.use('element', function(){
                var element = layui.element;
            });
        });
        function goout() {
            window.location.href = "exit.jsp";
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">网站内容管理系统</div>
        <jsp:include page="include/homepagemenu.jsp"></jsp:include>    </div>

    <div class="layui-body" style="left: 0px;top:80px;">
        <!-- 内容主体区域 -->
        <div id="contentid">
            <%if (SecurityCheck.hasPermission(authToken, 54)) {%>
            <iframe src="http://localhost:8080/toMainPage?depTId=&userType=1" id="iframepage" frameborder="0" scrolling="no" marginheight="0"
                    style="width:1400px;height:800px;"></iframe>
            <%} else {%>
            <iframe src="http://localhost:8080/toMainPage?depTId=<%=deptid%>&userType=0" id="iframepage" frameborder="0" scrolling="no" marginheight="0"
                    style="width:1400px;height:800px;"></iframe>
            <%}%>
            <!--div id="searchtimeid" style="padding-bottom: 20px">部门名称：工商局
                开始时间：<input type=text  class="easyui-datetimebox" name="bdatetime" value="" style="width:200px">
                结束时间：<input type=text  class="easyui-datetimebox" name="edatetime" value="" style="width:200px">
            </div>
            <div id="deptbrowseid" style="padding-bottom: 5px">
                <div style="float: left">部门信息浏览量排行TOP30的页面</div>  <div style="padding-left: 800px;padding-bottom: 10px;">查看更多排行</div>
                <div>
                    <table border="1" width="80%" align="center">
                        <tr style="height: 30px;background-color: #F7F7F7">
                            <td width="50%" align="center">页面标题</td>
                            <td align="center">浏览量</td>
                        </tr>
                    </table>
                </div>
            </div>

            <div id="deptpubid" style="padding-bottom: 5px"><img src="images/deptpub.png"></div>

            <div id="deptpubofmonthid" style="padding-bottom: 5px"><img src="images/1111.jpg"></div-->
        </div>
    </div>

    <div class="layui-footer" style="left: 0px;">
    </div>
</div>
</body>
</html>

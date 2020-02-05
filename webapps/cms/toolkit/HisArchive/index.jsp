<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/9
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>历史档案管理</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../design/css/layui.css">
    <script src="../../js/jquery-1.11.1.min.js"></script>
    <script src="../../design/layui.js"></script>
    <!--引用xtree-->
    <script src="../../design/layui-xtree.js"></script>
</head>
<body class="layui-layout-body">
<form class="layui-form">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header layui-bg-blue" align="center">关键词库管理</div>
        <div class="layui-side layui-bg-gray">
            <div class="layui-side-scroll">
                <ul class="layui-nav layui-nav-tree layui-bg-gray"  lay-filter="test">
                    <li class="layui-nav-item"><a href="javascript: statisticsByCol();"><span style="color:black" class="layui-icon-camera">档案案卷管理</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: statisticsByDept();"><span style="color:black" class="layui-icon-camera">档案文件管理</span></a></li>
                    <li class="layui-nav-item"><a href="../index.jsp"><span style="color:black" class="layui-icon-camera">返回</span></a></li>
                </ul>
            </div>
        </div>

        <div class="layui-body" style="left: 100px;top: 40px;">
            <!-- 内容主体区域 -->
            <div id="maincontant" style="padding:5px;"></div>
        </div>

        <div class="layui-footer"></div>
    </div>
</form>
<script>
    //JavaScript代码区域
    layui.use(["element","form"], function(){
        var element = layui.element;
        var form = layui.form;
    });
</script>
</body>
</html>

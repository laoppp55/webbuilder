<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-6-6
  Time: 上午10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>按部门统计工作量</title>
    <link rel="stylesheet" href="../../design/css/layui.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header layui-bg-blue" align="center">编辑工作量统计分析</div>
    <div class="layui-side layui-bg-gray">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree layui-bg-gray"  lay-filter="test">
                <li class="layui-nav-item"><a href="javascript: statisticsByCol();"><span style="color:black" class="layui-icon-camera">按栏目统计信息发布量</span></a></li>
                <li class="layui-nav-item"><a href="javascript: statisticsByDept();"><span style="color:black" class="layui-icon-camera">按部门统计信息发布量</span></a></li>
                <li class="layui-nav-item"><a href="javascript: statisticsByDeptAndCol();"><span style="color:black" class="layui-icon-camera">按部门、栏目统计信息发布量</span></a></li>
                <li class="layui-nav-item"><a href="javascript: statisticsByEditor();"><span style="color:black" class="layui-icon-camera">采编人员信息发布量统计</span></a></li>
                <li class="layui-nav-item"><a href=""><span style="color:black" class="layui-icon-camera">部门采编人员信息发布量统计</span></a></li>
                <li class="layui-nav-item"><a href=""><span style="color:black" class="layui-icon-camera">系统设置</span></a></li>
                <li class="layui-nav-item"><a href="../index.jsp"><span style="color:black" class="layui-icon-camera">返回</span></a></li>
            </ul>
        </div>
    </div>
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div id="maincontant" style="padding: 15px;">

        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © layui.com - 底部固定区域
    </div>
</div>
<script src="../../js/jquery-1.11.1.min.js"></script>
<script src="../../design/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
    });

    function statisticsByCol() {
        var htmlStr = '<input type="radio" name="timeSlice" value="1" />今天&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" />昨天&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" />一周&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" />30天&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="5" />自定义时间：';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" />-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" />&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="export" value="导出EXCEL" /></br></br>';
        htmlStr = htmlStr + '<img src="images/workingByCol.jpg" />';

        $("#maincontant").html(htmlStr);
    }

    function statisticsByDept() {
        var htmlStr = '<input type="radio" name="timeSlice" value="1" />今天&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" />昨天&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" />一周&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" />30天&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="5" />自定义时间：';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" />-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" />&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="export" value="导出EXCEL" /></br></br>';
        htmlStr = htmlStr + '<img src="images/workingByDept.jpg" />';

        $("#maincontant").html(htmlStr);
    }

    function statisticsByDeptAndCol() {
        var htmlStr = '<input type="radio" name="timeSlice" value="1" />今天&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" />昨天&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" />一周&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" />30天&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="5" />自定义时间：';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" />-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" />&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="export" value="导出EXCEL" /></br></br>';
        htmlStr = htmlStr + '<img src="images/workingByDeptAndCol.jpg" />';

        $("#maincontant").html(htmlStr);
    }

</script>
</body>
</html>

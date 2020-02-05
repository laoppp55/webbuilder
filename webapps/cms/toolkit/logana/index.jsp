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
    <title>网站访问量统计分析系统</title>
    <link rel="stylesheet" href="../../design/css/layui.css">
</head>
<body class="layui-layout-body">
<form class="layui-form">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header layui-bg-blue" align="center">网站访问量统计分析</div>
        <div class="layui-side layui-bg-gray">
            <div class="layui-side-scroll">
                <ul class="layui-nav layui-nav-tree layui-bg-gray">
                    <li class="layui-nav-item"><a href="javascript: analysisBySynthesis();"><span style="color:black" class="layui-icon-camera">流量综合分析</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: analysisByCol();"><span style="color:black" class="layui-icon-camera">栏目浏览量统计</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: analysisByActivity();"><span style="color:black" class="layui-icon-camera">网站活跃期分析</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: analysisByIP();"><span style="color:black" class="layui-icon-camera">IP地域分布分析</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: analysisByKeyword();"><span style="color:black" class="layui-icon-camera">查询关键字分析</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: analysisByOS();"><span style="color:black" class="layui-icon-camera">客户端操作系统</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: analysisByAgent();"><span style="color:black" class="layui-icon-camera">客户端浏览器</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: systemSetup();"><span style="color:black" class="layui-icon-camera">系统设置</span></a></li>
                    <li class="layui-nav-item"><a href="../index.jsp"><span style="color:black" class="layui-icon-camera">返回</span></a></li>
                </ul>
            </div>
        </div>

        <div class="layui-body" style="left: 100px;top: 40px;">
            <!-- 内容主体区域 -->
            <div id="maincontant" style="padding:5px;"></div>
        </div>

        <div class="layui-footer">
            <!-- 底部固定区域 -->
        </div>
    </div>
</form>

<script src="../../js/jquery-1.11.1.min.js"></script>
<script src="../../design/layui.js"></script>
<!--引用xtree-->
<script src="../../design/layui-xtree.js"></script>
<script>
    //JavaScript代码区域
    layui.use(["element","form"], function(){
        var element = layui.element;
        var form = layui.form;
    });

    function analysisBySynthesis() {
        var htmlStr = '<div class="layui-form-item"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked title="今天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="5" title="自定义时间:">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';
        htmlStr = htmlStr + '<img src="images/analysisBySynthesis.jpg" />';
        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function analysisByCol() {
        var htmlStr = '<div class="layui-form-item"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked  title="今天" />';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="5" title="自定义时间:">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';
        htmlStr = htmlStr + '<img src="images/analysisByCol.jpg" />';

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function analysisByActivity() {
        var htmlStr = '<div class="layui-form-item"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked title="今天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="5" title="自定义时间:">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';
        htmlStr = htmlStr + '<img src="images/analysisByActivity.jpg" />';

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function analysisByIP() {
        var htmlStr = '<div class="layui-form-item"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked title="今天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="5" title="自定义时间:">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';
        htmlStr = htmlStr + '<img src="images/analysisByIP.jpg" />';

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function analysisByKeyword() {
        var htmlStr = '<div class="layui-form-item"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked title="今天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="5" title="自定义时间:">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';
        htmlStr = htmlStr + '<img src="images/analysisByKeyword.jpg" />';

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function analysisByOS() {
        var htmlStr = '<div class="layui-form-item"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked title="今天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="5" title="自定义时间:">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';
        htmlStr = htmlStr + '<img src="images/analysisByOS.jpg" />';

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function analysisByAgent() {
        var htmlStr = '<div class="layui-form-item"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" title="今天" checked>';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="5" title="自定义时间:">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';
        htmlStr = htmlStr + '<img src="images/analysisByAgent.jpg" />';

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function systemSetup() {
        var htmlStr = '<div class="layui-form-item"><div class="layui-input-block">' +
            '<img src="images/analysisByAgent.jpg" />' +
            '<div align="center"><input type="button" name="dosave" value="提交" onclick="javascript:doSaveConfig();" class="layui-btn layui-bg-blue layui-edge-bottom layui-btn-fluid"></div>'+
            '<div></div>';

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function doSaveConfig() {
        alert("save config infomation");
    }
</script>
</body>
</html>

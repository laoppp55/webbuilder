<%@ page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="com.xml.IFormManager" %>
<%@ page import="com.xml.FormPeer" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String userid = authToken.getUserID();
    String deptid = authToken.getDepartment();
    String baseDir= application.getRealPath("/");
    String password = (String)session.getAttribute("AD_Pass");
    IFormManager formpeer= FormPeer.getInstance();
    String sitename=authToken.getSitename();


%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>工作量统计分析</title>
    <link rel="stylesheet" href="../../design/css/layui.css">
</head>
<body class="layui-layout-body">
<form class="layui-form">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <div class="layui-logo"><a href="../index1.jsp" class="layui-logo">CMS-工具箱<a></div>
            <jsp:include page="../../include/menu.jsp"></jsp:include>
        </div>
        <div class="layui-side layui-bg-gray">
            <div class="layui-side-scroll">
                <ul class="layui-nav layui-nav-tree layui-bg-gray"  lay-filter="test">
                    <li class="layui-nav-item"><a href="javascript: statisticsByCol();"><span style="color:black" class="layui-icon-camera">按栏目统计信息发布量</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: statisticsByDept();"><span style="color:black" class="layui-icon-camera">按部门统计信息发布量</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: statisticsByDeptAndCol();"><span style="color:black" class="layui-icon-camera">按部门、栏目统计信息发布量</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: statisticsByEditor();"><span style="color:black" class="layui-icon-camera">采编人员信息发布量统计</span></a></li>
                    <li class="layui-nav-item"><a href="javascript: statisticsByDeptEditor();"><span style="color:black" class="layui-icon-camera">部门采编人员信息发布量统计</span></a></li>
                    <!--li class="layui-nav-item"><a href="javascript: systemSetup();"><span style="color:black" class="layui-icon-camera">系统设置</span></a></li-->
                    <li class="layui-nav-item"><a href="../index.jsp"><span style="color:black" class="layui-icon-camera">返回</span></a></li>
                </ul>
            </div>
        </div>

        <div class="layui-body" style="left: 100px;top: 40px;">
            <!-- 内容主体区域 -->
            <div id="maincontant" style="padding:5px;">

            </div>
        </div>

        <div class="layui-footer"></div>
    </div>
</form>

<script src="../../js/jquery-1.11.1.min.js"></script>
<script src="../../design/layui.js"></script>
<!--引用xtree-->
<script src="../../design/layui-xtree.js"></script>
<script type="text/javascript" src="../../js/json2.js"></script>

<script>
    //JavaScript代码区域
    layui.use(["element","form"], function(){
        var element = layui.element;
        var form = layui.form;
    });

    function statisticsByCol() {
        var htmlStr = '<div class="layui-form-item" style="padding-top: 20px;"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked title="今天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';

        htmlobj=$.ajax({
            url:"workingByCol.jsp",
            data:{},
            dataType:'html',
            async:false,
            success:function(data){

            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
                alert(jqXHR.status);
                alert(jqXHR.readyState);
                alert(jqXHR.statusText);
                alert(textStatus);
                alert(errorThrown);
            }
        });

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function statisticsByDept() {
        var htmlStr = '<div class="layui-form-item" style="padding-top: 20px;"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked title="今天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';

        htmlobj=$.ajax({
            url:"removearticle.jsp",
            data:{
                article:articleID,
                start:currentPage,
                flag:flag,
                ascdesc:ascdesc,
                arttype:arttypeval,
                range:range
            },
            dataType:'json',
            async:false,
            success:function(jsondata){
                if (jsondata.result == "true") {
                    //var tr = document.getElementById('row' + articleID);
                    //tr.parentNode.removeChild(tr);
                    //tb.deleteRow(rownum+1);                //table对象根据行号删除表格中的行
                    articlesInfoList(columnID,currentPage,doSearchFlag,flag,ascdesc,null,null,null,arttypeval);
                    layer.alert('文章删除成功！！！')
                } else {
                    layer.alert('文章删除失败！！！');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
                alert(jqXHR.status);
                alert(jqXHR.readyState);
                alert(jqXHR.statusText);
                alert(textStatus);
                alert(errorThrown);
            }
        });

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function statisticsByDeptAndCol() {
        var htmlStr = '<div class="layui-form-item" style="padding-top: 20px;"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked title="今天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';

        htmlobj=$.ajax({
            url:"removearticle.jsp",
            data:{
                article:articleID,
                start:currentPage,
                flag:flag,
                ascdesc:ascdesc,
                arttype:arttypeval,
                range:range
            },
            dataType:'json',
            async:false,
            success:function(jsondata){
                if (jsondata.result == "true") {
                    //var tr = document.getElementById('row' + articleID);
                    //tr.parentNode.removeChild(tr);
                    //tb.deleteRow(rownum+1);                //table对象根据行号删除表格中的行
                    articlesInfoList(columnID,currentPage,doSearchFlag,flag,ascdesc,null,null,null,arttypeval);
                    layer.alert('文章删除成功！！！')
                } else {
                    layer.alert('文章删除失败！！！');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
                alert(jqXHR.status);
                alert(jqXHR.readyState);
                alert(jqXHR.statusText);
                alert(textStatus);
                alert(errorThrown);
            }
        });

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function statisticsByEditor() {
        var htmlStr = '<div class="layui-form-item" style="padding-top: 20px;"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked title="今天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';

        htmlobj=$.ajax({
            url:"removearticle.jsp",
            data:{
                article:articleID,
                start:currentPage,
                flag:flag,
                ascdesc:ascdesc,
                arttype:arttypeval,
                range:range
            },
            dataType:'json',
            async:false,
            success:function(jsondata){
                if (jsondata.result == "true") {
                    //var tr = document.getElementById('row' + articleID);
                    //tr.parentNode.removeChild(tr);
                    //tb.deleteRow(rownum+1);                //table对象根据行号删除表格中的行
                    articlesInfoList(columnID,currentPage,doSearchFlag,flag,ascdesc,null,null,null,arttypeval);
                    layer.alert('文章删除成功！！！')
                } else {
                    layer.alert('文章删除失败！！！');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
                alert(jqXHR.status);
                alert(jqXHR.readyState);
                alert(jqXHR.statusText);
                alert(textStatus);
                alert(errorThrown);
            }
        });

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function statisticsByDeptEditor() {
        var htmlStr = '<div class="layui-form-item" style="padding-top: 20px;"><div class="layui-input-block"><input type="radio" name="timeSlice" value="1" checked title="今天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="2" title="昨天">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="3" title="一周">';
        htmlStr = htmlStr + '<input type="radio" name="timeSlice" value="4" title="30天">';
        htmlStr = htmlStr + '<input type="text" name="startday" value="" placeholder="请输入开始时间" class="layui-input" style="width:200px;display:inline;">-';
        htmlStr = htmlStr + '<input type="text" name="endday" value="" placeholder="请输入结束时间" class="layui-input" style="width:200px;display:inline;">&nbsp;&nbsp;&nbsp;';
        htmlStr = htmlStr + '<input type="button" name="query" value="查询" class="layui-btn"><input type="button" name="export" value="导出EXCEL" class="layui-btn"><div></div></br></br>';
        htmlobj=$.ajax({
            url:"removearticle.jsp",
            data:{
                article:articleID,
                start:currentPage,
                flag:flag,
                ascdesc:ascdesc,
                arttype:arttypeval,
                range:range
            },
            dataType:'json',
            async:false,
            success:function(jsondata){
                if (jsondata.result == "true") {
                    //var tr = document.getElementById('row' + articleID);
                    //tr.parentNode.removeChild(tr);
                    //tb.deleteRow(rownum+1);                //table对象根据行号删除表格中的行
                    articlesInfoList(columnID,currentPage,doSearchFlag,flag,ascdesc,null,null,null,arttypeval);
                    layer.alert('文章删除成功！！！')
                } else {
                    layer.alert('文章删除失败！！！');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
                alert(jqXHR.status);
                alert(jqXHR.readyState);
                alert(jqXHR.statusText);
                alert(textStatus);
                alert(errorThrown);
            }
        });

        $("#maincontant").html(htmlStr);
        var form = layui.form;
        form.render();
    }

    function systemSetup() {
        var htmlStr = '<div class="layui-form-item"><div class="layui-input-block">' +
            '<img src="images/workingByDeptEditor.jpg" />' +
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

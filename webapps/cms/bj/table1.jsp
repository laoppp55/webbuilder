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
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../design/css/layui.css"  media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
<table class="layui-hide" id="test"></table>
<script src="../design/layui.all.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('table', function(){
        alert("hello word");
        var table = layui.table;
        table.render({
            elem: '#test'
            ,url:'/webbuilder/data/user.json'
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [[
                {field:'id', title: 'ID', sort: true}
                ,{field:'username', title: '用户名'} //width 支持：数字、百分比和不填写。你还可以通过 minWidth 参数局部定义当前单元格的最小宽度，layui 2.2.1 新增
                ,{field:'sex', title: '性别', sort: true}
                ,{field:'city', title: '城市'}
                ,{field:'sign', title: '签名'}
                ,{field:'classify', title: '职业', align: 'center'} //单元格内容水平居中
                ,{field:'experience', title: '积分', sort: true, align: 'right'} //单元格内容水平居中
                ,{field:'score', title: '评分', sort: true, align: 'right'}
                ,{field:'wealth', title: '财富', sort: true, align: 'right'}
            ]]
        });
    });
</script>
</body>
</html>

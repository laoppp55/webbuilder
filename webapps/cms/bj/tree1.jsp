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
<ul id="demo"></ul>
<script src="../design/layui.all.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.tree({
        elem: '#demo', //传入元素选择器
        nodes: [{ //节点
            name: '父节点1'
            ,children: [{
                name: '子节点11'
            },{
                name: '子节点12'
            }]
        },{
            name: '父节点2（可以点左侧箭头，也可以双击标题）'
            ,children: [{
                name: '子节点21'
                ,children: [{
                    name: '子节点211'
                }]
            }]
        }]
    });
</script>
</body>
</html>

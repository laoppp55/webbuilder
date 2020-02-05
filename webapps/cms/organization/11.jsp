<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-4-20
  Time: 下午10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<HTML>
<HEAD>
    <TITLE> ZTREE DEMO </TITLE>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../style/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="../js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>

    <SCRIPT LANGUAGE="JavaScript">
        var zTreeObj;
        // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
        var setting = {
            check: { /**复选框**/
            enable: false,
                chkboxType: {"Y":"", "N":""}
            },
            view: {
                //dblClickExpand: false,
                expandSpeed: 300 //设置树展开的动画速度，IE6下面没效果，
            },
            data: {
                simpleData: {   //简单的数据源，一般开发中都是从数据库里读取，API有介绍，这里只是本地的
                    enable: true,
                    idKey: "id",  //id和pid，这里不用多说了吧，树的目录级别
                    pIdKey: "pId",
                    rootPId: 0   //根节点
                }
            },
            callback: {     /**回调函数的设置，随便写了两个**/
                beforeClick: beforeClick,
                onCheck: onCheck
            }

        };

        function beforeClick(treeId, treeNode) {
            alert(treeNode.id);
        }

        function onCheck(e, treeId, treeNode) {
            alert("onCheck");
        }

        // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
        var nodes = [
            {id:1, pId:0, name: "父节点1"},
            {id:11, pId:1, name: "子节点1"},
            {id:12, pId:1, name: "子节点2"}
        ];
        $(document).ready(function(){
            zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, nodes);
        });
    </SCRIPT>
</HEAD>
<BODY>
<div>
    <ul id="treeDemo" class="ztree"></ul>
</div>
</BODY>
</HTML>


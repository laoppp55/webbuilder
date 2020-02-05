<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="stp" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/fn.tld" prefix="fn" %>
<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"%>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.io.RandomAccessFile" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.bizwink.service.WuziclassService" %>
<%@ page import="com.bizwink.po.WzClass" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    BigDecimal siteid = BigDecimal.valueOf(authToken.getSiteID());
    ApplicationContext appContext = SpringInit.getApplicationContext();
    String json_data = null;
    List<WzClass> ll = null;
    if (appContext!=null) {
        WuziclassService wuziclassService = (WuziclassService)appContext.getBean("wuziclassService");
        ll =  wuziclassService.getMainClasses(BigDecimal.valueOf(0));
    }

    System.out.println("size==" + ll.size());
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type="text/css" href="../css/global.css">
<link rel="stylesheet" href="/webbuilder/style/zTreeStyle/zTreeStyle.css" type="text/css">
<style type="text/css">
    div#rMenu {
        z-index: 4;
        position: absolute;//使用绝对定位
    width: 100px;
        visibility: hidden;
        top: 0;
        background-color: #555;
        padding: 1px;
    }

    div#rMenu ul {
        margin: 0px;
        padding: 0px;
    }

    div#rMenu ul li {
        font-size: 12px;
        line-height: 20px;
        margin: 0px;
        padding: 0px;
        cursor: pointer;
        list-style: none outside none;
        background-color: #DFDFDF;
        border-bottom: 1px solid #fff;
        padding-left: 3px;
    }

    div#rMenu ul li:hover {
        background: #eee;
    }
</style>
<script type="text/javascript" src="/webbuilder/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/webbuilder/js/jquery.form.js"></script>
<script type="text/javascript" src="/webbuilder/js/jquery.ztree.core-3.5.js"></script>
<script language="javascript">
var nodes = [
    {id:1, pId:0, name: "父节点1"},
    {id:11, pId:1, name: "子节点1"},
    {id:12, pId:1, name: "子节点2"}
];

/**ztree的参数配置，setting主要是设置一些tree的属性，是本地数据源，还是远程，动画效果，是否含有复选框等等**/
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
        //onCheck: onCheck,
        onRightClick: OnRightClick,
        onAsyncSuccess: onAsyncSuccess,//回调函数，在异步的时候，进行节点处理（有时间延迟的），后续章节处理
        onClick : menuShowNode
        //onClick: zTreeOnClick
    }
};

function beforeClick(treeId, treeNode) {
    zTree.selectNode(treeNode);
    alert(treeNode.id);
}

$(document).ready(function(){     //初始化ztree对象
    //zTree = $.fn.zTree.init($("#columnTree"),setting,<%=json_data%>);
    zTree = $.fn.zTree.init($("#columnTree"),setting,nodes);
    rMenu = $("#rMenu");
    //展开根节点
    expandNodes(zTree.getNodes());
});

function OnRightClick(event, treeId, treeNode) {
    if(treeNode == null) {
        var a = 1; // 什么都不做
    }else if(treeNode && treeNode.name) {
        curName = treeNode.name;
    }else {
        curName = undefined;
    }

    if (treeNode) {
        var top = $(window).scrollTop();
        zTree.selectNode(treeNode);
        if (treeNode.getParentNode()) {
            var isParent = treeNode.isParent;
            if(isParent){//非叶子节点
                showRMenu("firstNode", event.clientX, event.clientY+top);//处理位置，使用的是绝对位置
            }else{//叶子节点
                showRMenu("secondNode", event.clientX, event.clientY+top);
            }
        } else {
            showRMenu("root", event.clientX, event.clientY+top);//根节点
        }
    }
}

function showRMenu(type, x, y) {
    $("#rMenu ul").show();
    if (type == "root") {
        $("#m_add").show();
        $("#m_edit").hide();
        $("#m_editFile").hide();
        $("#m_delete").hide();
    } else if(type == "firstNode"){
        $("#m_add").show();
        $("#m_edit").show();
        $("#m_editFile").hide();
        $("#m_delete").show();
    }else if(type == "secondNode"){
        $("#m_add").show();
        $("#m_edit").show();
        //$("#m_editFile").show();
        $("#m_delete").show();
    }

    $("#rMenu").css({
        "top" : y + "px",
        "left" : x + "px",
        "visibility" : "visible"
    });

    //在当前页面绑定 鼠标事件
    $(document).bind("mousedown", onBodyMouseDown);
}

//事件触发 隐藏菜单
function onBodyMouseDown(event) {
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
        $("#rMenu").css({
            "visibility" : "hidden"
        });
    }
}

//隐式 隐藏右键菜单
function hideRMenu() {
    if(rMenu){
        $("#rMenu").css({
            "visibility" : "hidden"
        });
    }
    //取消绑定
    $(document).unbind("mousedown", onBodyMouseDown);
}

//异步加载完成
function onAsyncSuccess(event, treeId, treeNode, msg) {

}

//单击节点 显示节点
function menuShowNode() {
    var node = zTree.getSelectedNodes()[0];
    if (node) {
        if(!node.isParent){

        }
    } else {
        alert("未找到节点");
    }
}

//右键菜单 添加节点
function menuAddNode() {
    //隐藏菜单
    hideRMenu();

    var node = zTree.getSelectedNodes()[0];
    if (node) {
        pId = node.id;
        $("#addnode").css({
            "display" : "block"
        });
        insertnode.pid.value=pId;

    } else {
        alert("未找到节点");
    }
}

//右键菜单 编辑节点
function menuEditNode() {
    hideRMenu();
    alert("编辑节点")

    var node = zTree.getSelectedNodes()[0];
    if (node) {

    } else {
        alert("未找到节点");
    }
}

//右键菜单 内容节点
function menuEditContentNode() {
    hideRMenu();
    alert("编辑节点内容")

    var node = zTree.getSelectedNodes()[0];
    if (node) {

    } else {
        alert("未找到节点");
    }
}

//右键菜单删除节点
function menuDeleteNode() {
    hideRMenu();
    alert("删除节点")

    var node = zTree.getSelectedNodes()[0];
    if (node) {
        //easyUI 消息框
        top.$.messager.confirm("删除节点下的所有信息","确定要删除此节点吗？", function (r) {
            if (r) {
                var id = node.getParentNode().id;
                $.ajax({
                    url : node.id,
                    beforeSend:function(){

                    },
                    success : function(data) {
                        var msg = "删除成功";
                        if ($.trim(data) == "success") {
                            refreshNode(id);

                            //location.href = "about:blank";
                        } else {
                            msg = "删除失败";
                        }

                        top.$.messager.show({
                            showType:'fade',
                            showSpeed:2000,
                            msg:msg,
                            title:'删除操作',
                            timeout:1
                        });
                    }
                });
            }
        });
    } else {
        alert("未找到节点");
    }
}


//焦点
function focusNode(id) {
    var node = zTree.getNodeByParam("id", id, null);
    if (node) {
        zTree.selectNode(node);
    } else {
        setTimeout(function() {
            focusNode(id);
        }, 10);
    }

}

function expandNodes(nodes) {
    zTree.expandNode(nodes[0], true, false, false);
}

//刷新节点
function refreshNode(id) {
    var node = zTree.getNodeByParam("id", id, null);
    if (node) {
        zTree.reAsyncChildNodes(node, "refresh");
    } else {
        setTimeout(function() {
            refreshNode(id);
        }, 10);
    }
}

//刷新父节点
function refreshParentNode(id) {
    var node = zTree.getNodeByParam("id", id, null);
    var pNode = node.getParentNode();
    if (pNode) {
        refreshNode(pNode.id);
    } else {
        refreshNode("0");
    }
}

//为添加节点刷新
function refreshForAddNode(id) {
    var node = zTree.getNodeByParam("id", id, null);
    zTree.addNodes(node, {
        id : "xx",
        name : "new node"
    });
    zTree.reAsyncChildNodes(node, "refresh");
}

function cancel_insert() {
    $("#addnode").css({"display" : "none"})
}

function insert_node() {
    alert(insertnode.pid.value);
    id = insertnode.pid.value;
    refreshForAddNode(id)
    $("#addnode").css({"display" : "none"})
}

function drwzclassinfo() {
    var filename = $("#fileToUpload").val();
    if (filename == "") {
        alert("请选择上传文件！");
        return false;
    }

    if (filename.indexOf(".xls") == -1 && filename.indexOf(".xlsx")==-1) {
        alert("上传文件必须是EXCEL文件！");
        return false;
    }

    //alert("hello word===" + filename);

    //var myData = {
    //    "column": "< %=columnID%>"
    //};

    var ajaxFormOption = {
        type: "post",                                                     //提交方式
        dataType: "json",                                                //数据类型
        //data: myData,                                                      //自定义数据参数，视情况添加
        url: "/webbuilder/toolkit/product/columns/drWzClassinfo.jsp?doCreate=true",       //请求url
        success: function (data) {                                         //提交成功的回调函数
            alert(data);
        }
    };

    //不需要submit按钮，可以是任何元素的click事件
    $("#form1").ajaxSubmit(ajaxFormOption);
}
</script>
</head>
<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<div id="rMenu" class="rMenu">
    <ul>
        <li id="m_add" onclick="menuAddNode();">增加节点</li>
        <li id="m_edit" onclick="menuEditNode();">编辑节点</li>
        <li id="m_editFile" onclick="menuEditContentNode();">编辑内容</li>
        <li id="m_delete" onclick="menuDeleteNode();">删除节点</li>
    </ul>
</div>

<form enctype="multipart/form-data" id="form1">
    <table width="100%" align=center border=0>
        <tr height=20>
            <td width="20%" align=right>导入物资分类信息：</td>
            <td width="80%">
                <input type=file id="fileToUpload" name="file1" size=30>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="btnsubmit" value="  上传  " onclick="javascript:drwzclassinfo();">
            </td>
        </tr>
    </table>
</form>


<div class="ztree" id="columnTree">
</div>

<div id="addnode" style="display:none;position: absolute;top:100px;left:200px;width:500px;border:1px solid red;">
    <form name="insertnode" method="post">
        <input type="hidden" value="" name="pid">
        <input type="hidden" value="" name="pcode">
        <table border="0" align="center">
            <tr>
                <td>节点名称：</td>
                <td><input type="text" value="" name="nodename" maxlength="50"></td>
            </tr>
            <tr>
                <td>节点编码：</td>
                <td><input type="text" value="" name="nodecode" maxlength="50"></td>
            </tr>
            <tr>
                <td><input type="button" value="提交" name="tijiao" onclick="javascript:insert_node();"></td>
                <td><input type="button" value="取消" name="cancel" onclick="javascript:cancel_insert();"></td>
            </tr>
        </table>
    </form>
</div>

<div id="mainclass">
    <%
        int rows = ll.size() / 4;
        int extra = ll.size() % 4;
        out.println("<table>");
        for(int row = 0; row<rows-1; row++) {
            int begin = row * 4;
            int end = (row+1) * 4;
            out.print("<tr height=\"40px\">");
            for(int ii=begin; ii<end; ii++) {
                WzClass wzClass = ll.get(ii);
                out.print("<td><a href=\"wzclass.jsp?pcode=" + wzClass.getCODE() + "\" target=\"_blank\">" + wzClass.getNAME() + "</a></td>");
                out.print("<td width=\"50px\"></td>");
            }
            out.println("</tr>");
        }

        out.print("<tr height=\"40px\">");
        for(int ii=rows*4;ii<ll.size(); ii++) {
            if (ii<ll.size()){
                WzClass wzClass = ll.get(ii);
                out.print("<td><a href=\"wzclass.jsp?pcode=" + wzClass.getCODE() + "\" target=\"_blank\">" + wzClass.getNAME() + "</a></td>");
                out.print("<td width=\"50px\"></td>");
            } else {
                out.print("<td width=\"50px\" colspan=\"2\">&nbsp;</td>");
            }
        }
        out.println("</tr>");
        out.println("</table>");
    %>
</div>

</BODY>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-9-27
  Time: 下午9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String userid = authToken.getUserID();
    int sitetype = authToken.getSitetype();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    int customer = authToken.getSiteID();
    String verifycode = SecurityUtil.Encrypto("customer=" + customer + "&type=1");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <title>组织架构管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="../css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../design/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../css/column.css">

    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/treegrid-dnd.js"></script>

    <script type="text/javascript">
        var w = document.documentElement.clientWidth;
        var h = document.documentElement.clientHeight;
        //var w = document.documentElement.scrollWidth || document.body.scrollWidth;
        //var h = document.documentElement.scrollHeight || document.body.scrollHeight;
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><a href="../index1.jsp" class="layui-logo">CMS-组织架构管理</a></div>
        <jsp:include page="../include/menu.jsp"></jsp:include>
    </div>

    <div class="layui-body" style="left: 0px;">
        <div id="rightPane">
            <script type="text/javascript">
                document.write('<table id="tg" class="easyui-treegrid" title="企业组织架构管理"></table>');
            </script>
        </div>

        <div id="tb">
            <a href="#" onclick="javascript:appendCompany();" class="easyui-linkbutton" >增加公司</a>
            <a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >增加部门</a>
            <!--a href="#" onclick="javascript:delorg();" class="easyui-linkbutton" >批量删除</a>
            <a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >批量导入</a>
            <a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >上移</a>
            <a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >上移</a-->
        </div>
    </div>

    <jsp:include page="../include/homePageTail.jsp"></jsp:include>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        layui.use('element', function(){
            var element = layui.element;
        });

        firstCls=$.ajax({
            url:"<%=path%>/organization/organizations.jsp?tt=<%=Math.random()%>&verifycode=<%=verifycode%>&customer=<%=customer%>&type=1",
            dataType:'json',
            async:true,
            success:function(data){
                $('#tg').treegrid({
                    idField:'ID',
                    treeField:'NAME',
                    onContextMenu: onContextMenu,
                    toolbar: '#tb',
                    iconCls: 'icon-ok',
                    rownumbers: true,
                    animate: true,
                    collapsible: false,
                    fitColumns: true,
                    method: 'get',
                    onLoadSuccess: function(row){
                        $(this).treegrid('enableDnd', row?row.id:null);
                    },
                    onAfterEdit: function(row){
                        $(this).treegrid('enableDnd');
                    },
                    onCancelEdit: function(row){
                        $(this).treegrid('enableDnd');
                    },
                    onExpand:function(row) {
                    },
                    onCollapse:function(row) {
                    },
                    onBeforeExpand:function(row) {
                        expand(row);
                    },
                    onBeforeCollapse:function(row) {
                        //alert(row.name);
                    },
                    columns:[[
                        {field:'NAME',title:'名称',width:280},
                        {field:'CODE',title:'编码',width:150,align:'left'},
                        {field:'SHORTNAME',title:'简称',width:150,align:'left'},
                        {field:'CONTACTOR',title:'联系人',width:100,align:'left'},
                        {field:'PHONE',title:'电话',width:150,align:'left'},
                        {field:'MPHONE',title:'手机',width:150,align:'left'},
                        {field:'COTYPE',title:'类型',width:100,align:'center'},
                        {field:'ORDERID',title:'序号',width:80},
                        {field: 'deleteid',title: '删除',width: 50, align: 'center',
                            formatter: function (v, d, i) {
                                if (d.ISLEAF==0)
                                    return '<span title="删除" style="cursor:pointer;color:blue">' +
                                        '<a href="#" onclick="javascript:deleteCompanyOrDept('+ d.ID + ',' + '\'' +d.COTYPE + '\'' + ');">删除</a>' +
                                        '</span>'
                                else
                                    return '<span title="删除" style="cursor:pointer;color:blue">' +
                                        '</span>'
                            }
                        },
                        {field: 'updateid',title: '修改',width: 50, align: 'center',
                            formatter: function (v, d, i) {
                                return '<span title="修改" style="cursor:pointer;color:blue"><a href="#" onclick="javascript:updateCompanyOrDept('+ d.ID + ',' + '\'' +d.COTYPE + '\'' + ');">修改</a></span>'
                            }
                        }
                        //{field: 'newid',title: '新建',width: 100, align: 'center',
                        //    formatter: function (v, d, i) {
                        //        return '<span title="新建" style="cursor:pointer;color:blue"><a href="#" onclick="javascript:createnode('+ d.ID + ',' + '\'' +d.COTYPE + '\'' + ');">新建</a></span>' }
                        //}
                        //{field: 'auditid',title: '审核规则',width: 100, align: 'center',
                        //    formatter: function (v, d, i) {
                        //        return '<span title="审核规则" style="cursor:pointer;color:blue"><a href="#" onclick="javascript:deletenode();">审核规则</a></span>' }
                        //}

                    ]]
                });

                $('#tg').treegrid('loadData',data);//刷新
            }
        });
    });

    function expand(row){
        var therow = null;
        if (row == null)
            therow = $('#tg').treegrid('getSelected');
        else
            therow = row;
        if (therow){
            var rows = $('#tg').treegrid('getChildren', therow.ID);
            if (rows.length==0) {
                $.ajax({
                    url:"getOrganizationByParentID.jsp?parent=" + therow.ID + "&thtime=" + new Date().getTime() ,
                    dataType:'json',
                    async:false,
                    beforeSend: function () {
                        load();
                    },
                    complete: function () {
                        disLoad();
                    },
                    success:function(data){
                        for(var ii=0;ii<data.total;ii++) {
                            $('#tg').treegrid('append',{parent:therow.ID,data:[{"ID":data.rows[ii].ID,"NAME":data.rows[ii].NAME,"CODE":data.rows[ii].CODE,"SHORTNAME":data.rows[ii].SHORTNAME,"CONTACTOR":data.rows[ii].CONTACTOR,"PHONE":data.rows[ii].PHONE,"MPHONE":data.rows[ii].MPHONE,"COTYPE":data.rows[ii].COTYPE,"ORDERID":data.rows[ii].ORDERID,"ISLEAF":data.rows[ii].ISLEAF}]});
                        }
                        //$('#tg').treegrid('append',data);
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
                });
            }
        }
    }

    function collapse(row){
        var therow = null;
        if (row == null)
            therow = $('#tg').treegrid('getSelected');
        else
            therow = row;
        var rootnode = $('#tg').treegrid('getRoot');
        if (therow){
            if (therow.ID != rootnode.ID) {
                var rows = $('#tg').treegrid('getChildren', therow.ID);
                for(var ii=0; ii<rows.length; ii++) {
                    $('#tg').treegrid('getChildren',rows[ii].ID);
                }
            }
        }
    }

    //弹出加载层
    function load() {
        $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");
        $("<div class=\"datagrid-mask-msg\"></div>").html("获取栏目数据，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });
    }

    //取消加载层
    function disLoad() {
        $(".datagrid-mask").remove();
        $(".datagrid-mask-msg").remove();
    }

    function appendCompany(){
        var node = $('#tg').treegrid('getSelected');
        var retval = "";
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
        if (node == null)
            retval = window.open("addOrgAndCompany.jsp?pid=0" + "&customer=&type=1&verifycode=temp="+Math.random(),"CompanyWin","width=" + iWidth + ",height=" + iHeight + ",top=" + iTop + ", left=" + iLeft + ", toolbar=no, menubar=no, resizable=no,location=no, status=no");
        else {
            retval = window.open("addOrgAndCompany.jsp?pid=" + node.ID + "&customer=&type=1&verifycode=temp="+Math.random(),"CompanyWin","width=" + iWidth + ",height=" + iHeight + ",top=" + iTop + ", left=" + iLeft + ", toolbar=no, menubar=no, resizable=no,location=no, status=no");
        }
        window.location.reload();
    }

    function appendDept(){
        var node = $('#tg').treegrid('getSelected');
        if (node == null) alert("请选择部门或者公司");
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
        window.open("addOrgAndDept.jsp?pid=" + node.ID + "&customer=&type=1&verifycode=", "DepartmentWin", "width=" + iWidth + ",height=" + iHeight + ",top=" + iTop + ", left=" + iLeft + ", toolbar=no, menubar=no, resizable=no,location=no, status=no");
    }

    function createnodeinfo(rowid,orgtype) {
        alert("hello create" + rowid + "==" + orgtype);
    }

    function updateCompanyOrDept(rowid,orgtype) {
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
        if (orgtype==='公司') {
            var winStr = "editOrgAndCompany.jsp?orgid=" + rowid;
            var retval = window.open(winStr + "&customer=&type=1&verifycode=temp="+Math.random(),"UpdateCompanyWin","width=" + iWidth + ",height=" + iHeight + ",top=" + iTop + ", left=" + iLeft + ", toolbar=no, menubar=no, resizable=no,location=no, status=no");
        } else {
            var winStr = "editOrgAndDept.jsp?orgid=" + rowid;
            var retval = window.open(winStr + "&customer=&type=1&verifycode=temp="+Math.random(),"UpdateDepartmentWin","width=" + iWidth + ",height=" + iHeight + ",top=" + iTop + ", left=" + iLeft + ", toolbar=no, menubar=no, resizable=no,location=no, status=no");
        }
    }

    function updatenodeinfo(id,pid){
        $('#tg').treegrid('update',{id:id,row:{"CNAME":data.CNAME,"DIRNAME":data.DIRNAME,"ORDERID":data.ORDERID,"CREATEDATE":data.CREATEDATE}});
    }

    function deleteCompanyOrDept(rowid,orgtype) {
        if (orgtype==='公司') {
            var winStr = "removeOrganization.jsp?orgid=" + rowid + "&infotype=1";
            var retval = window.open(winStr, "removecol","width=1200,height=650,left=5,top=5,status,scrollbars");
        } else {
            var winStr = "removeOrganization.jsp?orgid=" + rowid + "&infotype=0";
            var retval = window.open(winStr, "removecol","width=1200,height=650,left=5,top=5,status,scrollbars");
        }
    }

    function deletenodeinfo(id,pid){
        $('#tg').treegrid('remove', id);
        //$('#tg').treegrid('reload', pid);
    }

    function onContextMenu(e,row){
        if (row){
            e.preventDefault();
            $(this).treegrid('select', row.id);
            $('#mm').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
        }
    }

    function moveUp() {
        var row = $('#tg').treegrid('getSelected');
        //获取当前节点的层级
        var level = $('#tg').treegrid('getLevel',row.id);
        //获取当前节点的父节点
        var current_root = $('#tg').treegrid('getRoot',row.id);
        //获取所有子节点
        var rows = $('#tg').treegrid('getChildren');
        //获取当前节点的所有同层级的节点
        var selected_rows = new Array(rows.length);
        var kk = 0;
        for (var ii=0;ii<rows.length;ii++) {
            var t_row = rows[ii];
            var t_level = $('#tg').treegrid('getLevel',t_row.id);
            var t_root = $('#tg').treegrid('getRoot',t_row.id);
            if (level==t_level && current_root.id==t_root.id) {
                selected_rows[kk] = t_row.id;
                kk = kk + 1;
            }
        }
        //获取当前节点的上一个节点
        var preid = 0;
        for (var ii=0;ii<kk;ii++) {
            var t_row_id = selected_rows[ii];
            if (t_row_id==row.id) {
                if (ii>0)
                    preid = selected_rows[ii - 1];
            }
        }


        //var selectRow = $('#datagrid-row-r1-2-'+row.id);
        //var pre = selectRow.prev();//此处获得上一节点，关键
        //var preid = pre.attr("node-id");
        //if(typeof(pre.attr("node-id"))=="undefined" || pre.attr("node-id").indexOf("L")==0) {
        if (preid == 0) {
            alert("无法移动！");
        }else {
            exchangeRow('up',"tg",row.id,preid);
        }
    }

    //下移
    function moveDown() {
        var row = $('#tg').treegrid('getSelected');
        //获取当前节点的层级
        var level = $('#tg').treegrid('getLevel',row.id);
        //获取当前节点的父节点
        var current_root = $('#tg').treegrid('getRoot',row.id);
        //获取所有子节点
        var rows = $('#tg').treegrid('getChildren');
        //获取当前节点的所有同层级的节点
        var selected_rows = new Array(rows.length);
        var kk = 0;
        for (var ii=0;ii<rows.length;ii++) {
            var t_row = rows[ii];
            var t_level = $('#tg').treegrid('getLevel',t_row.id);
            var t_root = $('#tg').treegrid('getRoot',t_row.id);
            if (level==t_level && current_root.id==t_root.id) {
                selected_rows[kk] = t_row.id;
                kk = kk + 1;
            }
        }
        //获取当前节点的下一个节点
        var nextid = 0;
        for (var ii=0;ii<kk;ii++) {
            var t_row_id = selected_rows[ii];
            if (t_row_id==row.id) {
                if (ii<(kk-1))
                    nextid = selected_rows[ii + 1];
            }
        }


        //var selectRow = $('#datagrid-row-r1-2-'+row.id);
        //var next = selectRow.next();                                //用jquery的next方法获得当前节点的下一节点
        //var nextid = next.attr("node-id");
        //if(typeof(next.attr("node-id"))=="undefined" || next.attr("node-id").indexOf("L")==0) {
        if (nextid == 0) {
            alert("无法移动！")
        }else {
            exchangeRow('down',"tg",row.id,nextid);
        }
    }

    function exchangeRow(type, gridname,row1_id,row2_id) {
        if ("up" == type) {
            var t_node = $('#' + gridname).treegrid("pop",row1_id);
            $('#' + gridname).treegrid("insert",{
                before:row2_id,
                data:t_node
            });
        } else if("down" == type) {
            var t_node = $('#' + gridname).treegrid("pop",row1_id);
            $('#' + gridname).treegrid("insert",{
                after:row2_id,
                data:t_node
            });
        }

        //修改数据库的排序信息

    }

    function removeIt(){
        var node = $('#tg').treegrid('getSelected');
        if (node){
            var orgtype = node.orgtype;
            alert(orgtype);
            $('#tg').treegrid('remove', node.id);
        }

        //数据库中删除该节点
    }

    function updateIt(){
        var node = $('#tg').treegrid('getSelected');
        alert(node.id + node.name);
        alert(node.brief);
        alert(node.unit);
        alert(node.keyword);
        alert(node.orderid);

        if (node){
            $('#tg').treegrid('update',{
                id:node.id,
                row: {
                    name:'petersong',
                    brief:5,
                    unit:node.begin,
                    keyword:node.end,
                    orderid:40
                }
            } );

            //数据库中修改该节点信息
        }
    }</script>
</body>
</html>

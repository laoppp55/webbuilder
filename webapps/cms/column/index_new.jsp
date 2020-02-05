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
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.service.ColumnService" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="com.bizwink.cms.security.SecurityCheck" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.security.PermissionSet" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.bizwink.cms.security.Permission" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.security.SecurityCheck" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String userid = authToken.getUserID();
    int sitetype = authToken.getSitetype();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    String tbuf = null;

    Column rootColumn = null;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        ColumnService columnService = (ColumnService)appContext.getBean("columnService");
        rootColumn = columnService.getRootColumnBySiteid(BigDecimal.valueOf(siteid));
    }
    int rootColumnID = rootColumn.getID().intValue();

    if (sitetype == 0 || sitetype == 2) {                             //0表示自己创建的网站，2表示完整拷贝模板网站
        if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
            //普通用户
            List clist = new ArrayList();
            PermissionSet p_set = authToken.getPermissionSet();
            Iterator iter1 = p_set.elements();
            while (iter1.hasNext()) {
                Permission permission = (Permission) iter1.next();
                if (rightid == permission.getRightID()) {
                    clist = permission.getColumnListOnRight();
                    break;
                }
            }
            //colTree = TreeManager.getInstance().getUserTree(userid, siteid, clist,rightid);
            if (clist.size() > 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<clist.size();ii++){
                    com.bizwink.cms.news.Column column = (com.bizwink.cms.news.Column)clist.get(ii);
                    if (ii<clist.size()-1) {
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"},\r\n";
                    }else{
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"#\"" + ",open:\"true\"" +"}\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }

        } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
            //站点管理员
            //colTree = TreeManager.getInstance().getSiteTree(siteid);
            IColumnManager columnManager = ColumnPeer.getInstance();
            List<com.bizwink.cms.news.Column> columns = columnManager.getColumnsForSite(siteid);
            if (columns.size() > 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<columns.size();ii++){
                    com.bizwink.cms.news.Column column = (com.bizwink.cms.news.Column)columns.get(ii);
                    if (ii<columns.size()-1) {
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"},\r\n";
                    }else{
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"#\"" + ",open:\"true\"" +"}\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
        }
    } else {                                                           //1表示共享模板网站的栏目和模板
        if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
            //普通用户
            List clist = new ArrayList();
            PermissionSet p_set = authToken.getPermissionSet();
            Iterator iter1 = p_set.elements();
            while (iter1.hasNext()) {
                Permission permission = (Permission) iter1.next();
                if (rightid == permission.getRightID()) {
                    clist = permission.getColumnListOnRight();
                    break;
                }
            }
            //colTree = TreeManager.getInstance().getUserTree(userid, siteid, clist,rightid);
            if (clist.size() > 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<clist.size();ii++){
                    com.bizwink.cms.news.Column column = (com.bizwink.cms.news.Column)clist.get(ii);
                    if (ii<clist.size()-1) {
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"},\r\n";
                    }else{
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"#\"" + ",open:\"true\"" +"}\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
        } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
            //站点管理员
            //colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
            IColumnManager columnManager = ColumnPeer.getInstance();
            List<com.bizwink.cms.news.Column> columns = columnManager.getColumnsForSite(siteid);
            if (columns.size() > 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<columns.size();ii++){
                    com.bizwink.cms.news.Column column = (com.bizwink.cms.news.Column)columns.get(ii);
                    if (ii<columns.size()-1) {
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"},\r\n";
                    }else{
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"#\"" + ",open:\"true\"" +"}\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
        }
    }

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>网站栏目管理</title>
    <link rel="stylesheet" type="text/css" href="../css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../design/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../css/column.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/treegrid-dnd.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <script type="text/javascript">
        var w = document.documentElement.clientWidth;
        var h = document.documentElement.clientHeight;
        //var w = document.documentElement.scrollWidth || document.body.scrollWidth;
        //var h = document.documentElement.scrollHeight || document.body.scrollHeight;

        function go(){
            window.document.location = "../exit.jsp";
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><a href="../index1.jsp" class="layui-logo">CMS-文章管理</a></div>
        <jsp:include page="../include/menu.jsp"></jsp:include>
    </div>

    <div class="layui-body" style="left: 0px;">
        <div id="rightPane">
            <script type="text/javascript">
                document.write('<table id="tg" class="easyui-treegrid" title="网站栏目结构管理"></table>');
            </script>
        </div>

        <div id="tb">
            <a href="#" onclick="javascript:createnode(<%=rootColumnID%>);" class="easyui-linkbutton" >增加栏目</a>
            <a href="#" onclick="javascript:createauditrule(<%=rootColumnID%>);" class="easyui-linkbutton" >创建审核规则</a>
            <a href="#" onclick="javascript:EditAttr(<%=rootColumnID%>);" class="easyui-linkbutton" >创建扩展属性</a>
            <!--a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >批量删除栏目</a>
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
            url:"getColumnsBySiteID.jsp?column=<%=rootColumnID%>&thtime=" + new Date().getTime() ,
            dataType:'json',
            async:true,
            success:function(data){
                $('#tg').treegrid({
                    idField:'ID',
                    treeField:'CNAME',
                    //onContextMenu: onContextMenu,
                    toolbar: '#tb',
                    iconCls: 'icon-ok',
                    rownumbers: true,
                    loadMsg: "数据加载中，请稍后...",
                    //animate: true,
                    //collapsible: true,
                    //fitColumns: true,
                    //method: 'get',
                    //onLoadSuccess: function(row){
                    //    $(this).treegrid('enableDnd', row?row.id:null);
                    //},
                    //onAfterEdit: function(row){
                    //    $(this).treegrid('enableDnd');
                    //},
                    //onCancelEdit: function(row){
                    //    $(this).treegrid('enableDnd');
                    //},
                    //onExpand:function(row) {
                    //   expand(row);
                    //},
                    //onCollapse:function(row) {
                    //},
                    onBeforeExpand:function(row) {
                        expand(row);
                    },
                    onBeforeCollapse:function(row) {
                        collapse(row);
                    },
                    columns:[[
                        {field:'ID',title:'序号',hidden: true,align:'right',width:0},
                        {field:'CNAME',title:'名称',width:w*0.25},
                        //{field:'CNAME',title:'名称',formatter:function(v, d, i){return d.CNAME + '<input type="checkbox" name=cb' + d.ID +'" />';},width:400},
                        {field:'DIRNAME',title:'目录名称',width:w*0.18,align:'left'},
                        {field:'ORDERID',title:'排序',width:w*0.06},
                        {field:'CREATEDATE',title:'创建日期',width:w*0.1},
                        {field: 'deleteid',title: '删除',width:w*0.06, align: 'center',
                            formatter: function (v, d, i) {
                                if (d.state === "open")
                                //return '<span title="删除" style="cursor:pointer;color:blue">&nbsp;</span>';
                                    return '<span title="删除" style="cursor:pointer;color:blue"><a href="javascript:deletenode(' + d.ID + ');">删除</a></span>'
                                else
                                    return '<span title="删除" style="cursor:pointer;color:blue">&nbsp;</span>'
                            }
                        },
                        {field: 'updateid',title: '修改',width: w*0.06, align: 'center',
                            formatter: function (v, d, i) {
                                return '<span title="修改" style="cursor:pointer;color:blue"><a href="javascript:updatenode(' + d.ID +');">修改</a></span>'
                            }
                        },
                        {field: 'newid',title: '新建',width: w*0.06, align: 'center',
                            formatter: function (v, d, i) {
                                return '<span title="新建" style="cursor:pointer;color:blue"><a href="#" onclick="javascript:createnode(' + d.ID + ');">新建</a></span>'
                            }
                        },
                        {field: 'auditid',title: '审核规则',width: w*0.1, align: 'center',
                            formatter: function (v, d, i) {
                                if (d.ISAUDITED==1)
                                    return '<span title="审核规则" style="cursor:pointer;color:blue"><a href="#" onclick="javascript:createauditrule(' + d.ID + ');">审核规则</a></span>'
                                else
                                    return '<span title="审核规则" style="cursor:pointer;color:blue">&nbsp;</span>'
                            }
                        }
                    ]]
                });

                $('#tg').treegrid('loadData',data);    //刷新
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
                    url:"getColumnsBySiteID.jsp?column=" + therow.ID + "&thtime=" + new Date().getTime() ,
                    dataType:'json',
                    async:true,
                    beforeSend: function () {
                        load();
                    },
                    complete: function () {
                        disLoad();
                    },
                    success:function(data){
                        //{parent: obj_node.PLM_ID,data:[{"ID":12,"_parentId":5,"CNAME":"领导班子","DIRNAME":"/xxgk/ldbz/","ORDERID":2}],
                        for(var ii=0;ii<data.total;ii++) {
                            if (typeof(data.rows[ii].state) == undefined){
                                $('#tg').treegrid('append',{parent:therow.ID,data:[{"ID":data.rows[ii].ID,"CNAME":data.rows[ii].CNAME,"DIRNAME":data.rows[ii].DIRNAME,"ORDERID":data.rows[ii].ORDERID,"CREATEDATE":data.rows[ii].CREATEDATE}]});
                            } else {
                                $('#tg').treegrid('append',{parent:therow.ID,data:[{"ID":data.rows[ii].ID,"CNAME":data.rows[ii].CNAME,"DIRNAME":data.rows[ii].DIRNAME,"ORDERID":data.rows[ii].ORDERID,"CREATEDATE":data.rows[ii].CREATEDATE,"state":data.rows[ii].state}]});
                                //$('#tg').treegrid('append',{parent:therow.ID,data:[{"ID":data.rows[ii].ID,"CNAME":data.rows[ii].CNAME,"DIRNAME":data.rows[ii].DIRNAME,"ORDERID":data.rows[ii].ORDERID,"CREATEDATE":data.rows[ii].CREATEDATE}]});
                            }
                        }

                        $('#tg').treegrid('toggle',therow.ID);
                        $('#tg').treegrid('toggle',therow.ID);
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

    function deletenode(rowid) {
        var winStr = "removecolumnnew.jsp?ID=" + rowid;
        var iWidth=window.screen.availWidth-200;                                                 //弹出窗口的宽度;
        var iHeight=window.screen.availHeight-100;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
        var retval = window.open(winStr, "removecol","width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top" + iTop + ",status,scrollbars");
    }

    function updatenode(rowid) {
        var winStr = "editcolumnnew.jsp?ID=" + rowid;
        var iWidth=window.screen.availWidth-200;                                                 //弹出窗口的宽度;
        var iHeight=window.screen.availHeight-100;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
        var retval = window.open(winStr, "updateol","width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top" + iTop + ",status,scrollbars");
    }

    function createnode(rowid) {
        var winStr = "createcolumnnew.jsp?parentID=" + rowid;
        var iWidth=window.screen.availWidth-200;                                                 //弹出窗口的宽度;
        var iHeight=window.screen.availHeight-100;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
        var retval = window.open(winStr, "createcol","width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top" + iTop + ",status,scrollbars");
    }

    function createauditrule(rowid) {
        var winStr = "add_column_audit.jsp?columnID=" + rowid;
        var retval = showModalDialog(winStr, "column_audit_rules_add", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
    }

    function deletenodeinfo(id,pid){
        $('#tg').treegrid('remove', id);
    }

    function updatenodeinfo(id,pid){
        $.ajax({
            url:"getColumnByID.jsp?column=" + id + "&thtime=" + new Date().getTime() ,
            dataType:'json',
            async:false,
            success:function(data){
                if (typeof(data.ID) != undefined){
                    $('#tg').treegrid('update',{id:id,row:{"CNAME":data.CNAME,"DIRNAME":data.DIRNAME,"ORDERID":data.ORDERID,"CREATEDATE":data.CREATEDATE}});
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }

    function createnodeinfo(id,pid){
        $.ajax({
            url:"getColumnByID.jsp?column=" + id + "&thtime=" + new Date().getTime() ,
            dataType:'json',
            async:false,
            success:function(data){
                if (typeof(data.ID) != undefined && pid!=<%=rootColumnID%>){
                    $('#tg').treegrid('append',{parent:data.PARENTID,data:[{"ID":data.ID,"CNAME":data.CNAME,"DIRNAME":data.DIRNAME,"ORDERID":data.ORDERID,"CREATEDATE":data.CREATEDATE}]});
                    var therow = $('#tg').treegrid('getSelected');
                    $('#tg').treegrid('toggle',pid);
                    $('#tg').treegrid('toggle',pid);
                    /*var newNode = {id:data.ID,pId:data.PARENTID,name:data.CNAME};
                    var zTree = $.fn.zTree.getZTreeObj("columnTree");
                    var parent_node = zTree.getNodeByTId("tree_" + data.PARENTID);
                    alert("获取的父节点==" + data.PARENTID);
                    zTree.addNodes(parent_node, newNode);*/
                    alert("增加栏目成功！！！");
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }

    function EditAttr(rootid){
        window.open("editattr.jsp?ID=" + rootid + "&from=1", "", "width=600,height=400,left=200,right=200,scrollbars,status");
    }

    function window_add(columnID)
    {
        var winStr = "add_column_audit.jsp?columnID=" + columnID;
        var retval = showModalDialog(winStr, "column_audit_rules_add", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
        if (retval == "ok") history.go(0);
    }

    function window_update(columnID)
    {
        var winStr = "update_column_audit.jsp?columnID=" + columnID;
        var retval = showModalDialog(winStr, "column_audit_rules_update", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
        if (retval == "ok") history.go(0);
    }

    function reloadTreeGrid(customer,pid,vcode,type) {
        alert(vcode);
        firstCls=$.ajax({
            url:"/OrgAction!getOrgByParant.do?tt=<%=Math.random()%>&verifycode="+vcode + "&customer=" + customer + "&pid=" + pid + "&type=" + type,
            dataType:'json',
            async:false,
            success:function(data){
                //alert(data.total);
                $('#tg').treegrid('loadData',data);//刷新
            }
        });
    }

    function deleteSubTree() {
        var row = $('#tg').treegrid('getSelected');
        var t_node = $('#tg').treegrid("pop",row.id);
        $('#tg').treegrid("insert",{
            before:2,
            data:t_node
        });
    }

    function formatProgress(value){
        if (value){
            var s = '<div style="width:100%;border:1px solid #ccc">' +
                '<div style="width:' + value + '%;background:#cc0000;color:#fff">' + value + '%' + '</div>'
            '</div>';
            return s;
        } else {
            return '';
        }
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

    /*var idIndex = 100;
     function append(){
     idIndex++;
     var d1 = new Date();
     var d2 = new Date();
     d2.setMonth(d2.getMonth()+1);
     var node = $('#tg').treegrid('getSelected');
     $('#tg').treegrid('append',{
     parent: node.id,
     data: [{
     id: idIndex,
     name: 'New Task'+idIndex,
     brief: parseInt(Math.random()*10),
     unit: $.fn.datebox.defaults.formatter(d1),
     keyword: $.fn.datebox.defaults.formatter(d2),
     orderid: parseInt(Math.random()*100)
     }]
     })

     //数据库中增加该节点信息
     }*/

    function appendCompany(){
        var node = $('#tg').treegrid('getSelected');
        var retval = "";
        if (node == null)
            retval = window.showModalDialog("addOrgAndCompany.jsp?pid=0" + "&customer=&type=1&verifycode=temp="+Math.random(),"","dialogWidth=800px;dialogHeight=1000px;center=yes");
        else {
            retval = window.showModalDialog("addOrgAndCompany.jsp?pid=" + node.ID + "&customer=&type=1&verifycode=temp="+Math.random(),"","dialogWidth=800px;dialogHeight=1000px;center=yes");
        }
        window.location.reload();
        //alert(retval);
    }

    function appendDept(){
        var node = $('#tg').treegrid('getSelected');
        if (node == null) alert("请选择部门或者公司");
        window.open("addOrgAndDept.jsp?pid=" + node.ID+"&customer=&type=1&verifycode=");
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

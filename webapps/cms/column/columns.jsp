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

    System.out.println(tbuf);

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>网站栏目管理</title>
    <meta http-equiv="pragma" content="no-cache" />
    <link rel="stylesheet" type="text/css" href="../css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/icon.css">
    <link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
    <!--link rel="stylesheet" type="text/css" href="../css/demo.css"-->
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/treegrid-dnd.js"></script>
    <script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" language="javascript">
        /**自定义的数据源，ztree支持json,数组，xml等格式的**/
        var columnnodes = <%=tbuf%>;
        /**ztree的参数配置，setting主要是设置一些tree的属性，是本地数据源，还是远程，动画效果，是否含有复选框等等**/
        var setting = {
            check: { /**复选框**/
            enable: false,
                chkboxType: {"Y":"", "N":""}
            },
            view: {
                //dblClickExpand: false,
                showIcon: false,
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
            parent.frames['cmsright'].location = "articles.jsp?rightid=<%=rightid%>&column=" + treeNode.id;
        }

        function onCheck(e, treeId, treeNode) {
            alert("onCheck");
        };
    </script>
</head>
<body>
<div style="margin:1px 0;">
    <div id="columntreeid" style="float: left;width: 100px;height: 100px">
        <div class="ztree" id="columnTree" style="background-color:yellow;"></div>
    </div>
    <div id="columngridid" align="right" style="float: right">
        <table id="tg" class="easyui-treegrid" title="网站栏目结构管理" style="width:1063px;height:600px"></table>
    </div>
</div>
<div id="tb">
    <a href="#" onclick="javascript:createnode(<%=rootColumnID%>);" class="easyui-linkbutton" >增加栏目</a>
    <a href="#" onclick="javascript:createauditrule(<%=rootColumnID%>);" class="easyui-linkbutton" >创建审核规则</a>
    <a href="#" onclick="javascript:EditAttr(<%=rootColumnID%>);" class="easyui-linkbutton" >创建扩展属性</a>
    <!--a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >批量删除栏目</a>
    <a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >上移</a>
    <a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >上移</a-->
</div>

<!--div id="mm" class="easyui-menu" style="width:120px;">
    <div onclick="createnode(<%=rootColumnID%>)" data-options="iconCls:'icon-add'">创建栏目</div>
    <div onclick="createauditrule(<%=rootColumnID%>)" data-options="iconCls:'icon-add'">创建审核规则</div>
    <div onclick="EditAttr(<%=rootColumnID%>)" data-options="iconCls:'icon-edit'">创建扩展属性</div>
    <div onclick="removeIt()" data-options="iconCls:'icon-remove'">批量删除栏目</div>
    <div onclick="moveUp()" data-options="iconCls:'icon-remove'">栏目上移</div>
    <div onclick="moveDown()" data-options="iconCls:'icon-remove'">栏目下移</div>
    <div class="menu-sep"></div>
    <div onclick="deleteSubTree()">删除下属组织</div>
    <div onclick="collapse()">关闭</div>
    <div onclick="expand(null)">展开</div>
</div-->
<script type="text/javascript">
$(document).ready(function(){
    var zTreeDemo = $.fn.zTree.init($("#columnTree"),setting, columnnodes);
    firstCls=$.ajax({
        url:"getColumnsBySiteID.jsp?column=0&thtime=" + new Date().getTime() ,
        dataType:'json',
        async:false,
        success:function(data){
            //alert(data);
            $('#tg').treegrid({
                idField:'ID',
                treeField:'CNAME',
                //onContextMenu: onContextMenu,
                toolbar: '#tb',
                iconCls: 'icon-ok',
                rownumbers: true,
                //loadMsg: "数据加载中，请稍后...",
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
                //   expend(row);
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
                    {field:'ID',title:'序号',hidden: true,align:'right',width:30},
                    {field:'CNAME',title:'名称',width:400},
                    //{field:'CNAME',title:'名称',formatter:function(v, d, i){return d.CNAME + '<input type="checkbox" name=cb' + d.ID +'" />';},width:400},
                    {field:'DIRNAME',title:'目录名称',width:230,align:'left'},
                    {field:'ORDERID',title:'排序',width:50},
                    {field:'CREATEDATE',title:'创建日期',width:100},
                    {field: 'deleteid',title: '删除',width:50, align: 'center',
                        formatter: function (v, d, i) {
                            if (d.state === "open")
                            //return '<span title="删除" style="cursor:pointer;color:blue">&nbsp;</span>';
                                return '<span title="删除" style="cursor:pointer;color:blue"><a href="javascript:deletenode(' + d.ID + ');">删除</a></span>'
                            else
                                return '<span title="删除" style="cursor:pointer;color:blue">&nbsp;</span>'
                        }
                    },
                    {field: 'updateid',title: '修改',width: 50, align: 'center',
                        formatter: function (v, d, i) {
                            return '<span title="修改" style="cursor:pointer;color:blue"><a href="javascript:updatenode(' + d.ID +');">修改</a></span>'
                        }
                    },
                    {field: 'newid',title: '新建',width: 50, align: 'center',
                        formatter: function (v, d, i) {
                            return '<span title="新建" style="cursor:pointer;color:blue"><a href="#" onclick="javascript:createnode(' + d.ID + ');">新建</a></span>'
                        }
                    },
                    {field: 'auditid',title: '审核规则',width: 100, align: 'center',
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
                async:false,
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
                        }
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
    alert("ffff");
}
//取消加载层
function disLoad() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}

function deletenode(rowid) {
    var winStr = "removecolumnnew.jsp?ID=" + rowid;
    var retval = window.open(winStr, "removecol","width=1200,height=650,left=5,top=5,status,scrollbars");
    //var retval = showModalDialog(winStr, "removecol", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
}

function updatenode(rowid) {
    var winStr = "editcolumnnew.jsp?ID=" + rowid;
    var retval = window.open(winStr, "updatecol","width=1200,height=650,left=5,top=5,status,scrollbars");
}

function createnode(rowid) {
    var winStr = "createcolumnnew.jsp?parentID=" + rowid;
    var retval = window.open(winStr, "createcol","width=1200,height=650,left=5,top=5,status,scrollbars");
    //var retval = showModalDialog(winStr, "createcol", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
}

function createauditrule(rowid) {
    var winStr = "add_column_audit.jsp?columnID=" + rowid;
    var retval = showModalDialog(winStr, "column_audit_rules_add", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
}

function deletenodeinfo(id,pid){
    $('#tg').treegrid('remove', id);
    //$('#tg').treegrid('reload', pid);
}

function updatenodeinfo(id,pid){
    $.ajax({
        url:"getColumnByID.jsp?column=" + id + "&thtime=" + new Date().getTime() ,
        dataType:'json',
        async:false,
        success:function(data){
            if (typeof(data.ID) != undefined){
                $('#tg').treegrid('update',{id:id,row:{"CNAME":data.CNAME,"DIRNAME":data.DIRNAME,"ORDERID":data.ORDERID,"CREATEDATE":data.CREATEDATE}});
                //$('#tg').treegrid('refresh', id);
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
            if (typeof(data.ID) != undefined){
                $('#tg').treegrid('append',{parent:data.PARENTID,data:[{"ID":data.ID,"CNAME":data.CNAME,"DIRNAME":data.DIRNAME,"ORDERID":data.ORDERID,"CREATEDATE":data.CREATEDATE}]});
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
}
</script>
</body>
</html>
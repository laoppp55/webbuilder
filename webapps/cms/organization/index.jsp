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
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.cms.util.FileUtil" %>
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

    List<Organization> organizations =null;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
        organizations = organizationService.getFirstLevelOrg(BigDecimal.valueOf(authToken.getSiteID()));
    }

    String jsondata=null;
    if (organizations!=null) {
        jsondata = "[\r\n";
        for(int ii=0;ii<organizations.size();ii++) {
            Organization organization = organizations.get(ii);
            int pid = organization.getPARENT().intValue();
            if (ii<organizations.size()-1) {
                if (pid==0)
                    jsondata = jsondata + "{id:" +organization.getID().intValue() + ",pId:"+ pid + ",name:\"" + organization.getNAME() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                else
                    jsondata = jsondata + "{id:" +organization.getID().intValue() + ",pId:"+ pid + ",name:\"" + organization.getNAME() +"\",url:\"\"" + "},\r\n";
            } else {
                if (pid==0)
                    jsondata = jsondata + "{id:" +organization.getID().intValue() + ",pId:"+ pid + ",name:\"" + organization.getNAME() +"\",url:\"\"" + ",open:\"true\"" +"}\r\n";
                else
                    jsondata = jsondata + "{id:" +organization.getID().intValue() + ",pId:"+ pid + ",name:\"" + organization.getNAME() +"\",url:\"\"" + "}\r\n";
            }
        }
        jsondata = jsondata +"]";
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <title>组织架构管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="../css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/icon.css">
    <link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="../css/column.css">
    <script type="text/javascript" src="../js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/treegrid-dnd.js"></script>
    <script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="../js/splitter.js"></script>
    <script type="text/javascript">
        var w = document.documentElement.clientWidth;
        var h = document.documentElement.clientHeight;
        //var w = document.documentElement.scrollWidth || document.body.scrollWidth;
        //var h = document.documentElement.scrollHeight || document.body.scrollHeight;
    </script>
</head>
<body>
<div id="rightPane">
    <script type="text/javascript">
        //alert(w);
        document.write('<table id="tg" class="easyui-treegrid" title="企业组织架构管理" style="width:' + w + 'px;height:' + h*0.95 + 'px"></table>');
    </script>
</div>
<!-- #splitterContainer -->
<div id="tb">
    <a href="#" onclick="javascript:appendCompany();" class="easyui-linkbutton" >增加公司</a>
    <a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >增加部门</a>
    <!--a href="#" onclick="javascript:delorg();" class="easyui-linkbutton" >批量删除</a>
    <a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >批量导入</a>
    <a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >上移</a>
    <a href="#" onclick="javascript:appendDept();" class="easyui-linkbutton" >上移</a-->
</div>

<script type="text/javascript">
    /**自定义的数据源，ztree支持json,数组，xml等格式的**/
    var orgnodes = <%=(jsondata==null)?"":jsondata%>;
    /**ztree的参数配置，setting主要是设置一些tree的属性，是本地数据源，还是远程，动画效果，是否含有复选框等等**/
    var setting = {
        check: { /**复选框**/
        enable: false,
            chkboxType: {"Y":"", "N":""}
        },
        view: {
            //dblClickExpand: false,
            fontCss : {color:"white"},
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
        firstCls=$.ajax({
            url:"getOrganizationByParentID.jsp?orgid=" + treeNode.id + "&thtime=" + new Date().getTime() ,
            dataType:'json',
            async:true,
            cache:false,
            success:function(data){
                $('#tg').treegrid({
                    idField:'ID',
                    treeField:'CNAME',
                    //onContextMenu: onContextMenu,
                    toolbar: '#tb',
                    iconCls: 'icon-ok',
                    rownumbers: true,
                    onBeforeExpand:function(row) {
                        expand(row);
                    },
                    onBeforeCollapse:function(row) {
                        collapse(row);
                    },
                    columns:[[
                        {field:'NAME',title:'名称',width:180},
                        {field:'ID',title:'编码',width:60,align:'right'},
                        {field:'COTYPE',title:'类型',width:400,align:'right'},
                        {field:'ORDERID',title:'序号',width:80},
                        {field: 'deleteid',title: '删除',width:w*0.04, align: 'center',
                            formatter: function (v, d, i) {
                                if (d.state === "open")
                                //return '<span title="删除" style="cursor:pointer;color:blue">&nbsp;</span>';
                                    return '<span title="删除" style="cursor:pointer;color:blue"><a href="javascript:deletenode(' + d.ID + ');">删除</a></span>'
                                else
                                    return '<span title="删除" style="cursor:pointer;color:blue">&nbsp;</span>'
                            }
                        },
                        {field: 'updateid',title: '修改',width: w*0.04, align: 'center',
                            formatter: function (v, d, i) {
                                return '<span title="修改" style="cursor:pointer;color:blue"><a href="javascript:updatenode(' + d.ID +');">修改</a></span>'
                            }
                        },
                        {field: 'newid',title: '新建',width: w*0.04, align: 'center',
                            formatter: function (v, d, i) {
                                return '<span title="新建" style="cursor:pointer;color:blue"><a href="#" onclick="javascript:createnode(' + d.ID + ');">新建</a></span>'
                            }
                        }
                        //{field: 'auditid',title: '审核规则',width: w*0.1, align: 'center',
                        //    formatter: function (v, d, i) {
                        //        if (d.ISAUDITED==1)
                        //            return '<span title="审核规则" style="cursor:pointer;color:blue"><a href="#" onclick="javascript:createauditrule(' + d.ID + ');">审核规则</a></span>'
                        //        else
                        //            return '<span title="审核规则" style="cursor:pointer;color:blue">&nbsp;</span>'
                        //    }
                        //}
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
    }

    function onCheck(e, treeId, treeNode) {
        alert("onCheck");
    };

    $(document).ready(function(){
        //$("#splitterContainer").splitter({minAsize:100,maxAsize:300,splitVertical:true,A:$('#leftPane'),B:$('#rightPane'),slave:$("#rightSplitterContainer"),closeableto:0});
        //$("#rightSplitterContainer").splitter({splitHorizontal:true,A:$('#rightTopPane'),B:$('#rightBottomPane'),closeableto:100});
        //var zTreeDemo = $.fn.zTree.init($("#orgTree"),setting, orgnodes);
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
                        {field:'NAME',title:'名称',width:180},
                        {field:'CODE',title:'编码',width:100,align:'left'},
                        {field:'CONTACTOR',title:'联系人',width:100,align:'left'},
                        {field:'PHONE',title:'电话',width:150,align:'left'},
                        {field:'MPHONE',title:'手机',width:150,align:'left'},
                        {field:'EMAIL',title:'邮件',width:200,align:'left'},
                        {field:'COTYPE',title:'类型',width:100,align:'center'},
                        {field:'ORDERID',title:'序号',width:80},
                        {field: 'deleteid',title: '删除',width: 100, align: 'center',
                            formatter: function (v, d, i) {
                                if (d.ISLEAF==0)
                                    return '<span title="删除" style="cursor:pointer;color:blue">' +
                                        '<a href="#" onclick="javascript:deletenode('+ d.ID + ',' + '\'' +d.COTYPE + '\'' + ');">删除</a>' +
                                        '</span>'
                                else
                                    return '<span title="删除" style="cursor:pointer;color:blue">' +
                                        '</span>'
                            }
                        },
                        {field: 'updateid',title: '修改',width: 100, align: 'center',
                            formatter: function (v, d, i) {
                                return '<span title="修改" style="cursor:pointer;color:blue"><a href="#" onclick="javascript:updatenode('+ d.ID + ',' + '\'' +d.COTYPE + '\'' + ');">修改</a></span>'
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
        alert("开始获取数据");
    }
    //取消加载层
    function disLoad() {
        $(".datagrid-mask").remove();
        $(".datagrid-mask-msg").remove();
    }

    function deletenode(rowid,orgtype) {
        alert("hello delete" + rowid + "==" + orgtype);
        if (orgtype==='公司') {
            var winStr = "removeOrganization.jsp?orgid=" + rowid + "&infotype=1";
            var retval = window.open(winStr, "removecol","width=1200,height=650,left=5,top=5,status,scrollbars");
        } else {
            var winStr = "removeOrganization.jsp?orgid=" + rowid + "&infotype=0";
            var retval = window.open(winStr, "removecol","width=1200,height=650,left=5,top=5,status,scrollbars");
        }
        //var retval = showModalDialog(winStr, "removecol", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
    }

    function updatenode(rowid,orgtype) {
        if (orgtype==='公司') {
            var winStr = "editOrgAndCompany.jsp?orgid=" + rowid;
            var retval = window.open(winStr + "&customer=&type=1&verifycode=temp="+Math.random(),"","dialogWidth=800px;dialogHeight=1000px;center=yes");
        } else {
            var winStr = "editOrgAndDept.jsp?orgid=" + rowid;
            var retval = window.open(winStr + "&customer=&type=1&verifycode=temp="+Math.random(),"","dialogWidth=800px;dialogHeight=1000px;center=yes");
        }
    }

    function createnode(rowid,orgtype) {
        alert("hello create" + rowid + "==" + orgtype);
        //var winStr = "createcolumnnew.jsp?parentID=" + rowid;
        //var retval = window.open(winStr, "createcol","width=1200,height=650,left=5,top=5,status,scrollbars");
        //var retval = showModalDialog(winStr, "createcol", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
    }

    function createauditrule(rowid) {
        var winStr = "add_column_audit.jsp?columnID=" + rowid;
        var retval = open(winStr, "column_audit_rules_add", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
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
        var retval = window.open(winStr, "column_audit_rules_add", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
        if (retval == "ok") history.go(0);
    }

    function window_update(columnID)
    {
        var winStr = "update_column_audit.jsp?columnID=" + columnID;
        var retval = window.open(winStr, "column_audit_rules_update", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
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
            retval = window.open("addOrgAndCompany.jsp?pid=0" + "&customer=&type=1&verifycode=temp="+Math.random(),"CompanyWin","dialogWidth=800px;dialogHeight=1000px;center=yes");
        else {
            retval = window.open("addOrgAndCompany.jsp?pid=" + node.ID + "&customer=&type=1&verifycode=temp="+Math.random(),"CompanyWin","dialogWidth=800px;dialogHeight=1000px;center=yes");
        }
        window.location.reload();
    }

    function appendDept(){
        var node = $('#tg').treegrid('getSelected');
        if (node == null) alert("请选择部门或者公司");
        window.open("addOrgAndDept.jsp?pid=" + node.ID+"&customer=&type=1&verifycode=","DepartmentWin","dialogWidth=400px;dialogHeight=600px;center=yes");
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

<%@ page import="java.sql.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.audit.*,
                 java.util.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);

    String userid = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int copyColumn = authToken.getCopyColumn();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);

    Tree colTree = null;

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
        colTree = TreeManager.getInstance().getUserTree(userid, siteid, clist,rightid);
    } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
        //站点管理员
        colTree = TreeManager.getInstance().getSiteTree(siteid);
    }

    final String TREE_ROOT = "<img src=../images/home.gif align=bottom border=0>";
    final String TREE_LINE = "<img src=../images/t1.gif align=top border=0>";
    final String TREE_FORK = "<img src=../images/t3.gif align=top border=0>";
    final String TREE_NODE = "<img src=../images/node.gif align=bottom border=0>&nbsp";
    final String COLUMN_DEL = "<img src=../images/button/del.gif align=bottom border=0 alt=\"删除栏目\">";
    final String COLUMN_EDIT = "<img src=../images/button/edit.gif align=middle border=0 alt=\"修改栏目\">";
    final String COLUMN_ADD = "<img src=../images/new.gif align=middle border=0 alt=\"新建栏目\">";
    final String TYPE_ADD = "<img src=../images/new.gif align=middle border=0 alt=\"添加分类\">";

    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = null;
    node[] treeNodes = colTree.getAllNodes();                     //获取该树的所有节点
    int pid[] = new int[colTree.getNodeNum()];                    //遍历树所需要的节点数组，存储当前未处理的节点
    int nodenum = 1;                                              //当前被处理节点的初始值
    int level = 0;                                                //判断当前节点是否有子节点
    StringBuffer buf = new StringBuffer();                        //存储生成的菜单树
    int childCount = 0;
    String tree = "";
    int ID = 0;
    int lineNr = 0;
    int treeRoot = colTree.getTreeRoot();
    int currentID = treeRoot;                                     //当前正在处理的节点

    IAuditManager auditMgr = AuditPeer.getInstance();

    do {
        tree = "";
        //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
        nodenum = nodenum - 1;

        for (int i = colTree.getNodeNum() - 1; i >= 0; i--) {
            if (treeNodes[i].getLinkPointer() == currentID) {
                nodenum = nodenum + 1;
                pid[nodenum] = treeNodes[i].getId();
            }
        }

        try {
            //System.out.println("currentID=" + currentID);
            column = columnMgr.getColumn(currentID);
            if (column.getParentID() > 0) {
                level = colTree.getNodeDepth(colTree, currentID) - 1;
                childCount = colTree.getChildCount(colTree, currentID);

                ID = column.getID();
                String CName = StringUtil.gb2iso4View(column.getCName());
                String EName = column.getEName();
                int orderid = column.getOrderID();
                String bgcolor = ((lineNr++ & 1) != 0) ? "#ffffff" : "#eeeeee";

                for (int i = 0; i < level - 1; i++)
                    tree = tree + TREE_LINE;
                tree = tree + TREE_FORK + TREE_NODE;

                buf.append("<tr height=20 class='menu' bordercolor='black' id='choice1'");
                buf.append(" onmouseover='movein(this)' ");
                buf.append(" onmouseout=\"moveout(this,'").append(bgcolor).append("')\"");
                buf.append(" bgcolor=").append(bgcolor).append(">\n");
                buf.append("<td width='52%' height=20>");
                buf.append(tree);

                buf.append("<a href='editcolumn.jsp?ID=").append(column.getID());
                buf.append("'>");

                buf.append(CName);
                buf.append("</a>");
                buf.append("</td>").append("\n");

                buf.append("<td width='16%' align=left>");
                buf.append("&nbsp;" + EName);
                buf.append("</td>").append("\n");

                buf.append("<td width='5%' align=center>");
                buf.append(orderid);
                buf.append("</td>").append("\n");

                buf.append("<td width='5%' align=center>");
                buf.append("<a href='editcolumn.jsp?ID=").append(ID);
                buf.append("'>");
                buf.append(COLUMN_EDIT);
                buf.append("</a></td>").append("\n");

                buf.append("<td width='5%' align=center>");
                buf.append("<a href='createcolumn.jsp?parentID=").append(ID);
                buf.append("'>");
                buf.append(COLUMN_ADD);
                buf.append("</a></td>").append("\n");
                /*buf.append("<td width='5%' align=center>");
                buf.append("<a href='javascript:createtype("+ID+")");
                buf.append("'>");
                buf.append(COLUMN_ADD);
                buf.append("</a></td>").append("\n");*/

                buf.append("<td width='5%' align=center>");
                if (childCount == 0) {
                    buf.append("<a href='removecolumn.jsp?ID=" + ID + "'\">");
                    buf.append(COLUMN_DEL + "</a>");
                } else {
                    buf.append("&nbsp;");
                }
                buf.append("</td>").append("\n");

                buf.append("<td width='7%' align=center>");

                //得到栏目审核状态(只有站点管理员才可以创建,并且当前栏目需要审核)
                if (SecurityCheck.hasPermission(authToken, 54) && column.getIsAudited() == 1) {
                    List userList = auditMgr.getUsers_hasAuditRight(column.getID(), siteid);
                    if (userList.size() > 0) {
                        boolean isExit = auditMgr.queryAuditRules(column.getID());
                        if (isExit)
                            buf.append("<a href=javascript:window_update(" + ID + ")><img src=../images/button/edit.gif align=middle border=0 alt=\"修改审核规则\"></a>");
                        else
                            buf.append("<a href=javascript:window_add(" + ID + ")><img src=../images/new.gif align=middle border=0 alt=\"新建审核规则\"></a>");
                    } else {
                        buf.append("<a href=javascript:window_alert()><img src=../images/new.gif align=middle border=0 alt=\"新建审核规则\"></a>");
                    }
                } else {
                    buf.append("&nbsp;");
                }
                buf.append("<td width='5%' align=center><a href='../publish/publishTree.jsp?column="+ID+"&rightid="+rightid+"'>发布</a></td>");
                buf.append("</td>").append("\n");
                buf.append("</tr>\n");
            }
        }
        catch (ColumnException e) {
            e.printStackTrace();
        }
        currentID = pid[nodenum];
    } while (nodenum > 0);
    //直到pid数组中没有待处理的节点为止

    int msgno = ParamUtil.getIntParameter(request, "msgno", -1);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>栏目管理</title>
    <link rel=stylesheet type="text/css" href="../style/global.css">
    <link rel="stylesheet" href="../design/css/layui.css">
    <style>
        .divcss5-b{ margin-left:10px;overflow-y:scroll; overflow-x:scroll;}
        /* css注释说明：设置第二个盒子与第一个盒子间距为10px，并设置了横纵滚动条样式 */
    </style>
    <script src="../design/layui.js"></script>
    <script language="javascript">
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

        function window_alert()
        {
            alert("没有授权用户！请先添加拥有<文章审核>权限的用户！");
        }

        function movein(which)
        {
            which.style.background = '#CECEFF';
        }

        function moveout(which, color)
        {
            which.style.background = color;
        }

        function EditAttr()
        {
            window.open("editattr.jsp?ID=<%=treeRoot%>&from=1", "", "width=600,height=400,left=200,right=200,scrollbars,status");
        }
        function createtype()
        {
            window.open("createnewtype.jsp?from=1", "", "width=600,height=400,left=200,right=200,scrollbars,status");
        }
    </script>
</head>
<body class="layui-layout-body" style="padding: 10px">
<%
    String[][] titlebars = {
            {"首页", "main.jsp"},
            {"栏目管理", ""}
    };
    String[][] operations = null;
    if (copyColumn == 1) {
        operations = new String[][] {
                //{"<a href=javascript:window_add(treeRoot);>文章分类</a>", ""},
                {"<a href=javascript:EditAttr();>扩展属性</a>", ""},
                {"新建栏目", "createcolumn.jsp?parentID=" + treeRoot},
                {"栏目复制", "copyColumn.jsp"}
        };

        if (!SecurityCheck.hasPermission(authToken, 54)) operations = null;
    } else {
        operations = new String[][] {
                {"<a href=javascript:window_add(" + treeRoot + ");>全站审核规则</a>", ""},
                {"<a href=javascript:EditAttr();>扩展属性</a>", ""},
                {"新建栏目", "createcolumn.jsp?parentID=" + treeRoot}
        };

        if (!SecurityCheck.hasPermission(authToken, 54)) operations = null;
    }
%>
<%@ include file="../inc/titlebar.jsp" %>
<%
    if (msgno == 0)
        out.println("<span class=cur>栏目创建成功</span>");
    else if (msgno == -1)
        out.println("<span class=cur></span>");
    else
        out.println("<span class=cur>栏目创建失败</span>");
%>
<div style="height: 500px;">
    <table class="layui-hidden" id="treeTable" lay-filter="treeTable"></table>
</div>
<script>
    var editObj=null,ptable=null,treeGrid=null,tableId='treeTable',layer=null;
    layui.config({
        base: '/webbuilder/design/extend/'
    }).extend({
        treeGrid:'treeGrid'
    }).use(['jquery','treeGrid','layer'], function(){
        var $=layui.jquery;
        treeGrid = layui.treeGrid;//很重要
        layer=layui.layer;
        ptable=treeGrid.render({
            id:tableId
            ,elem: '#'+tableId
            ,idField:'id'
            ,url:'/webbuilder/column/data2.json'
            ,cellMinWidth: 100
            ,treeId:'id'//树形id字段名称
            ,treeUpId:'pId'//树形父id字段名称
            ,treeShowName:'name'//以树形式显示的字段
            ,cols: [[
                {field:'name', edit:'text',width:300, title: '水果名称'}
                ,{field:'id',width:100, title: 'id'}
                ,{field:'pId', title: 'pid'}
                ,                {width:100,title: '操作', align:'center'/*toolbar: '#barDemo'*/
                    ,templet: function(d){
                        var html='';
                        var addBtn='<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">添加</a>';
                        var delBtn='<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
                        return addBtn+delBtn;
                    }
                }
            ]]
            ,page:false
        });

        treeGrid.on('tool('+tableId+')',function (obj) {
            if(obj.event === 'del'){//删除行
                del(obj);
            }else if(obj.event==="add"){//添加行
                add(obj.data);
            }
        });
    });

    function del(obj) {
        layer.confirm("你确定删除数据吗？如果存在下级节点则一并删除，此操作不能撤销！", {icon: 3, title:'提示'},
            function(index){//确定回调
                obj.del();
                layer.close(index);
            },function (index) {//取消回调
                layer.close(index);
            }
        );
    }


    var i=1000;
    //添加
    function add(pObj) {
        var param={};
        param.name='水果'+Math.random();
        param.id=++i;
        param.pId=pObj?pObj.id:0;
        treeGrid.addRow(tableId,pObj?pObj.LAY_TABLE_INDEX+1:0,param);
    }

    function print() {
        console.log(treeGrid.cache[tableId]);
        var loadIndex=layer.msg("对象已打印，按F12，在控制台查看！", {
            time:3000
            ,offset: 'auto'//顶部
            ,shade: 0
        });
    }
</script>
</body>

<!--BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<center>
    <table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width='100%'>
        <tr class=tine bgcolor="#dddddd" height=20>
            <td width='52%'>&nbsp;<!%=TREE_ROOT%>&nbsp;<!%=treeNodes[0].getEnName()%>
            </td>
            <td align=center width='16%'>目录名</td>
            <td align=center width='5%'>栏目排序</td>
            <td align=center width='5%'>修改</td>
            <td align=center width='5%'>新建</td>
            <td align=center width='5%'>删除</td>
            <td align=center width='7%'>审核规则</td>
            <td align=center width='5%'>发布导航树</td>
        </tr>
        <!%
            out.println(buf.toString());
        %>
    </table>
</center>
</BODY-->
</html>

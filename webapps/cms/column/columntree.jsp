<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript" src="../js/maintree.js"></script>

<STYLE>
BODY {
    FONT-SIZE: 12px;
    MARGIN: 10px 0px 0px;
    FONT-FAMILY: "宋体"
}

.skin0 {
    BORDER-RIGHT: black 2px solid;
    BORDER-TOP: black 2px solid;
    VISIBILITY: hidden;
    BORDER-LEFT: black 2px solid;
    WIDTH: 100px;
    CURSOR: default;
    LINE-HEIGHT: 20px;
    PADDING-TOP: 4px;
    BORDER-BOTTOM: black 2px solid;
    FONT-FAMILY: "宋体";
    POSITION: absolute;
    BACKGROUND-COLOR: menu;
    TEXT-ALIGN: left
}

.skin1 {
    BORDER-RIGHT: buttonhighlight 2px outset;
    BORDER-TOP: buttonhighlight 2px outset;
    FONT-SIZE: 10pt;
    VISIBILITY: hidden;
    BORDER-LEFT: buttonhighlight 2px outset;
    WIDTH: 100px;
    CURSOR: default;
    PADDING-TOP: 4px;
    BORDER-BOTTOM: buttonhighlight 2px outset;
    FONT-FAMILY: "宋体";
    POSITION: absolute;
    BACKGROUND-COLOR: menu;
    TEXT-ALIGN: left
}

.menuitems {
    PADDING-RIGHT: 1px;
    PADDING-LEFT: 10px;
    PADDING-BOTTOM: 2px;
    PADDING-TOP: 2px
}
</STYLE>

<SCRIPT language=javascript>
    <!--
    //定义菜单显示的外观，可以从上面定义的2种格式中选择其一
    var menuskin = "skin0";
    //是否在浏览器窗口的状态行中显示菜单项目条对应的链接字符串
    var display_url = false;

    function getEvent() {     //同时兼容ie和ff的写法
        if (document.all)    return window.event;
        func = getEvent.caller;
        while (func != null) {
            var arg0 = func.arguments[0];
            if (arg0) {
                if ((arg0.constructor == Event || arg0.constructor == MouseEvent)
                        || (typeof(arg0) == "object" && arg0.preventDefault && arg0.stopPropagation)) {
                    return arg0;
                }
            }
            funcfunc = func.caller;
        }
        return null;
    }

    function showmenuie5(columnid,rightid) {
        //获取当前鼠标右键按下后的位置，据此定义菜单显示的位置
        var event = arguments[0] || window.event;
        var rightedge = document.body.clientWidth - event.clientX;
        var bottomedge = document.body.clientHeight - event.clientY;

        //如果从鼠标位置到窗口右边的空间小于菜单的宽度，就定位菜单的左坐标（Left）为当前鼠标位置向左一个菜单宽度
        if (rightedge < ie5menu.offsetWidth)
            ie5menu.style.left = document.body.scrollLeft + event.clientX - ie5menu.offsetWidth;
        else
        //否则，就定位菜单的左坐标为当前鼠标位置
            ie5menu.style.left = document.body.scrollLeft + event.clientX;

        //如果从鼠标位置到窗口下边的空间小于菜单的高度，就定位菜单的上坐标（Top）为当前鼠标位置向上一个菜单高度
        if (bottomedge < ie5menu.offsetHeight)
            ie5menu.style.top = document.body.scrollTop + event.clientY - ie5menu.offsetHeight;
        else
        //否则，就定位菜单的上坐标为当前鼠标位置
            ie5menu.style.top = document.body.scrollTop + event.clientY;

        //设置菜单可见
        ie5menu.style.visibility = "visible";
        return false;
    }
    function hidemenuie5() {
        //隐藏菜单
        //很简单，设置visibility为hidden就OK！
        ie5menu.style.visibility = "hidden";
    }

    function highlightie5(evt) {
        //高亮度鼠标经过的菜单条项目

        //如果鼠标经过的对象是menuitems，就重新设置背景色与前景色
        //event.srcElement.className表示事件来自对象的名称，必须首先判断这个值，这很重要！
        var event = evt || window.event;
        var element = event.srcElement || event.target;
        if (element.className == "menuitems") {
            element.style.backgroundColor = "highlight";
            element.style.color = "white";

            //将链接信息显示到状态行
            //event.srcElement.url表示事件来自对象表示的链接URL
            if (display_url)
                window.status = event.srcElement.url;
        }
    }

    function lowlightie5(evt) {
        //恢复菜单条项目的正常显示
        var event = evt || window.event;
        var element = event.srcElement || event.target;
        if (element.className == "menuitems") {
            element.style.backgroundColor = "";
            element.style.color = "black";
            //window.status = "";
        }
    }

    //右键下拉菜单功能跳转
    function jumptoie5(evt) {
        //转到新的链接位置
        var event = evt || window.event;
        var element = event.srcElement || event.target;
        //var seltext=window.document.selection.createRange().text
        if (element.className == "menuitems") {
            //如果存在打开链接的目标窗口，就在那个窗口中打开链接
            if (element.getAttribute("target") != null)
            {
                window.open(element.getAttribute("url"), element.getAttribute("target"));
            }
            else
            //否则，在当前窗口打开链接
                window.location = element.getAttribute("url");
        }
    }
    //-->
</SCRIPT>
</head>
<%
    session.setAttribute("Current_URL",request.getRequestURI());
    String userid = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 54);
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
        //colTree = TreeManager.getInstance().getIndustryClassTree();
    }

    StringBuffer buf = new StringBuffer();                        //存储生成的菜单树
    if (colTree.getNodeNum() > 1) {
        node[] treeNodes = colTree.getAllNodes();                     //获取该树的所有节点
        int pid[] = new int[colTree.getNodeNum()];                    //遍历树所需要的节点数组，存储当前未处理的节点
        String parentMenuName[] = new String[colTree.getNodeNum()];   //存储某个节点的所有子节点的父菜单名称
        String parentMenu = "menu";                                     //存储当前节点的父菜单名称
        String menuName = "menu";                                     //存储当前节点的菜单名称
        int currentID = 0;                                            //当前正在处理的节点
        int i = 0;                                                      //循环变量
        int[] ordernum = new int[colTree.getNodeNum()];               //当前节点所有子节点的顺序号
        int orderNumber = 0;                                          //当前节点在同级节点的顺序号
        int nodenum = 1;                                              //当前被处理节点的初始值
        int subflag = 1;                                              //判断当前节点是否有子节点

        do {
            currentID = pid[nodenum];

            //设置当前处理节点的初始值
            if (currentID != 0) {
                parentMenu = parentMenuName[nodenum];
                orderNumber = ordernum[nodenum];
                menuName = "menu" + currentID;
            }

            //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
            //设置所有子节点的父菜单名称，设置所有子节点的序列号，把所有的子节点存入pid数组中
            subflag = 0;
            nodenum = nodenum - 1;
            for (i = 0; i < colTree.getNodeNum(); i++) {
                if (treeNodes[i].getLinkPointer() == currentID) {
                    nodenum = nodenum + 1;
                    pid[nodenum] = treeNodes[i].getId();
                    parentMenuName[nodenum] = menuName;
                    ordernum[nodenum] = subflag;
                    subflag = subflag + 1;
                }
            }

            //如果当前节点有子节点，生成当前节点的菜单
            if (subflag != 0) {
                buf.append("var " + menuName + " = null;\n");
                buf.append(menuName + " = new MTMenu();\n");
            }
            for (i = 0; i < colTree.getNodeNum(); i++) {
                if (treeNodes[i].getLinkPointer() == currentID) {
                    String name = "<span class=line id=href" + treeNodes[i].getId() + ">" + StringUtil.gb2iso4View(treeNodes[i].getChName()) + "</span>";
                    buf.append(menuName).append(".MTMAddItem(new MTMenuItem(\"");
                    buf.append(name).append("\",\"javascript:go(" + treeNodes[i].getId() + "); oncontextmenu=showmenuie5(" + treeNodes[i].getId() + "," + rightid + ");\",\"cmsleft\"));");
                    buf.append("\n");
                }
            }

            //关联当前菜单和他的父菜单
            if (subflag != 0 && currentID != 0)
                buf.append(parentMenu + ".items[" + orderNumber + "].MTMakeSubmenu(" + menuName + ");\r\n");
        } while (nodenum >= 1);
        //直到pid数组中没有待处理的节点为止
    }
%>
<script type="text/javascript">
    var needDrag = 0;
    <%=buf.toString()%>
</script>

<BODY <%if (buf.length() > 0) {%> onload="MTMStartMenu(true)"<%}%> leftMargin=0 topMargin=0 MARGINHEIGHT="0"
                                  MARGINWIDTH="0">
<input type=hidden name=justHref>

<DIV class=skin0 id=ie5menu onmouseover=highlightie5(event) onclick=jumptoie5(event); onmouseout=lowlightie5(event)>
    <DIV class=menuitems url="javascript:go(1);">新建栏目</DIV>
    <DIV class=menuitems url="javascript:go(2);">修改栏目</DIV>
    <DIV class=menuitems url="javascript:go(3);">删除栏目</DIV>
    <DIV class=menuitems url="javascript:go(4);">文件管理</DIV>
</DIV>

<select language=javascript>
    function go(flag)
    {
        if (flag == 1) {
            window.open("createcolumn.jsp?parentID=" + columnID,"","width=600,height=500,left=100,top=100");
        } else if (flag == 2) {
            window.open("editcolumn.jsp?ID=" + columnID,"","width=600,height=500,left=100,top=100");
        } else if (flag == 3) {
            window.open("removecolumn.jsp?ID=" + columnID,"","width=600,height=500,left=100,top=100");
        } else if (flag == 4) {
            window.open("/webbuilder/webedit/index.jsp?column=" + columnID + "&right=4","","width=850,height=600,left=5,top=5");
        }
    }
</select>

<SCRIPT language=JavaScript1.2>
    //如果当前浏览器是Internet Explorer，document.all就返回真

    //选择菜单方块的显示样式
    //ie5menu=document.getElementById("ie5menu");
    ie5menu.className = menuskin;

    //重定向鼠标右键事件的处理过程为自定义程序showmenuie5
    //document.getElementById("test").oncontextmenu = showmenuie5;
    //document.getElementById("test1").oncontextmenu = showmenuie5;

    //重定向鼠标左键事件的处理过程为自定义程序hidemenuie5
    document.body.onclick = hidemenuie5;

</SCRIPT>
</BODY>
</html>
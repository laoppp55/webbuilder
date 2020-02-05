<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int moveArticle = authToken.getMoveArticle();
%>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type="text/css" href="../style/global.css">
    <script type="text/javascript" src="../js/mtmcode.js"></script>
</head>
<%
    int siteid = authToken.getSiteID();

    Tree colTree = null;

    if(moveArticle == 0){
        colTree = TreeManager.getInstance().getSiteTree(siteid);
    }else{
        colTree = TreeManager.getInstance().getGlobalTree(siteid, 3);
    }
    node[] treeNodes = colTree.getAllNodes();                       //获取该树的所有节点
    int pid[] = new int[colTree.getNodeNum()];                        //遍历树所需要的节点数组，存储当前未处理的节点
    String parentMenuName[] = new String[colTree.getNodeNum()];       //存储某个节点的所有子节点的父菜单名称
    String parentMenu = "menu";                                     //存储当前节点的父菜单名称
    String menuName = "menu";                                     //存储当前节点的菜单名称
    int currentID = 0;                                            //当前正在处理的节点
    int i = 0;                                                      //循环变量
    int[] ordernum = new int[colTree.getNodeNum()];                   //当前节点所有子节点的顺序号
    int orderNumber = 0;                                          //当前节点在同级节点的顺序号
    int nodenum = 1;                                              //当前被处理节点的初始值
    int subflag = 1;                                              //判断当前节点是否有子节点
    StringBuffer buf = new StringBuffer();                        //存储生成的菜单树

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
                String name = "<font class=line>" + StringUtil.gb2iso4View(treeNodes[i].getChName()) + "</font>";
                buf.append(menuName).append(".MTMAddItem(new MTMenuItem(\"");
                buf.append(name).append("\",\"javascript:selectTColumn(" + treeNodes[i].getId() + ",'"+StringUtil.gb2iso4View(treeNodes[i].getChName())+"');\", \"cmsleft\"));");
                buf.append("\n");
            }
        }

        //关联当前菜单和他的父菜单
        if (subflag != 0 && currentID != 0)
            buf.append(parentMenu + ".items[" + orderNumber + "].MTMakeSubmenu(" + menuName + ");\r\n");
    } while (nodenum >= 1);
    //直到pid数组中没有待处理的节点为止
%>

<script type="text/javascript">
    var needDrag = 0;
    <%=buf.toString()%>
</script>

<BODY onload="MTMStartMenu(true)" leftMargin=0 topMargin=0 MARGINHEIGHT="0" MARGINWIDTH="0">
</BODY>
</html>
<script type="text/javascript">
    function selectTColumn(cid,cname) {
        //cname = cname.replace("`","'");
        //cname = cname.replace("#"," ");
        cname = cname.replace(new RegExp("#", "gm"), " ");
        cname = cname.replace(new RegExp("`", "gm"), "'");
        window.parent.opener.document.getElementById("columnname").value = cname;
        window.parent.opener.document.getElementById("columnid").value = cid;
        window.parent.opener.document.getElementById("columnname").focus();
        top.close();
    }
</script>
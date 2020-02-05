<%@ page import="com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
%>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type="text/css" href="../css/cms.css">
    <script type="text/javascript" src="../js/tree.js"></script>
    <script type="text/javascript" src="../js/mtmcode.js"></script>
</head>
<%
    int siteid = authToken.getSiteID();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    Tree colTree = TreeManager.getInstance().getComapnyTypeTree(siteid);

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
<input type=hidden name=justHref id="justHref">

<div id=ie5menu style="width:120px;position:absolute;display:none;">
    <TABLE border=0 cellpadding=0 cellspacing=0 class=Menu width=100% onselectstart="return false;"
           oncontextmenu="return false;" scroll=no>
        <tr>
            <td width=100% class=RightBg>
                <TABLE border=0 cellpadding=0 cellspacing=0 width=100%>
                    <tr id=d01>
                        <td height=20 class=MouseOut
                            onMouseOver="this.className='MouseOver';" onMouseOut="this.className='MouseOut';">&nbsp;文章列表
                        </td>
                    </tr>
                    <tr id=d02>
                        <td height=20 class=MouseOut
                            onMouseOver="this.className='MouseOver';" onMouseOut="this.className='MouseOut';">&nbsp;上传文件列表
                        </td>
                    </tr>
                    <tr>
                        <td height=2 align=center>
                            <TABLE border=0 cellpadding=0 cellspacing=0 width=100% height=2>
                                <tr>
                                    <td height=1 class=HrShadow></td>
                                </tr>
                                <tr>
                                    <td height=1 class=HrHighLight></td>
                                </tr>
                            </TABLE>
                        </td>
                    </tr>
                    <tr id=d03>
                        <td height=20 class=MouseOut
                            onMouseOver="this.className='MouseOver';" onMouseOut="this.className='MouseOut';">&nbsp;待发布文章
                        </td>
                    </tr>
                    <tr id=d04>
                        <td height=20 class=MouseOut
                            onMouseOver="this.className='MouseOver';" onMouseOut="this.className='MouseOut';">&nbsp;已发布文章
                        </td>
                    </tr>
                    <tr id=d05>
                        <td height=20 class=MouseOut
                            onMouseOver="this.className='MouseOver';" onMouseOut="this.className='MouseOut';">&nbsp;发布失败文章
                        </td>
                    </tr>
                    <tr id=d06>
                        <td height=20 class=MouseOut
                            onMouseOver="this.className='MouseOver';" onMouseOut="this.className='MouseOut';">&nbsp;待审核文章
                        </td>
                    </tr>
                    <tr id=d07>
                        <td height=20 class=MouseOut
                            onMouseOver="this.className='MouseOver';" onMouseOut="this.className='MouseOut';">&nbsp;退稿文章
                        </td>
                    </tr>
                    <tr id=d08>
                        <td height=20 class=MouseOut
                            onMouseOver="this.className='MouseOver';" onMouseOut="this.className='MouseOut';">&nbsp;归档文章
                        </td>
                    </tr>
                    <tr id=d09>
                        <td height=20 class=MouseOut
                            onMouseOver="this.className='MouseOver';" onMouseOut="this.className='MouseOut';">&nbsp;未用文章
                        </td>
                    </tr>
                </TABLE>
            </td>
        </tr>
    </TABLE>
</div>
</BODY>
</html>

<script type="text/javascript">
    function go(columnID)
    {
        var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
        if (isMSIE) {
            if (justHref.value != "" && parent.frames['menu'].document.all(justHref.value))
                parent.frames['menu'].document.all(justHref.value).className = "line";
            parent.frames['menu'].document.all("href" + columnID).className = "sline";
            justHref.value = "href" + columnID;
            parent.frames['cmsright'].location = "companys.jsp?rightid=<%=rightid%>&column=" + columnID;
            return;
        } else {
            if (document.getElementById("justHref").value != "" && window.parent.frames['cmsleft'].document.all("justHref").value)
                window.parent.frames['cmsleft'].document.all("justHref").value.className = "line";
            document.getElementById("justHref").value = "href" + columnID;
            window.parent.frames['cmsright'].location = "companys.jsp?rightid=<%=rightid%>&column=" + columnID;
            return;
        }
    }
</script>
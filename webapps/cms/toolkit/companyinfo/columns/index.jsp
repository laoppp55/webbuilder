<%@ page import="java.sql.*,
                 com.bizwink.cms.toolkit.companyinfo.*,
                 com.bizwink.cms.audit.*,
                 java.util.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK"%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    ICompanyinfoManager companyManager = CompanyinfoPeer.getInstance();
    companyClass companyType = new companyClass();
    Tree colTree = TreeManager.getInstance().getComapnyTypeTree(siteid);;

    final String TREE_ROOT = "<img src=../images/home.gif align=bottom border=0>";
    final String TREE_LINE = "<img src=../images/t1.gif align=top border=0>";
    final String TREE_FORK = "<img src=../images/t3.gif align=top border=0>";
    final String TREE_NODE = "<img src=../images/node.gif align=bottom border=0>&nbsp";
    final String COLUMN_DEL = "<img src=../images/del.gif align=bottom border=0 alt=\"删除栏目\">";
    final String COLUMN_EDIT = "<img src=../images/edit.gif align=middle border=0 alt=\"修改栏目\">";
    final String COLUMN_ADD = "<img src=../images/new.gif align=middle border=0 alt=\"新建栏目\">";
    final String TYPE_ADD = "<img src=../images/new.gif align=middle border=0 alt=\"添加分类\">";

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
            companyType = companyManager.getCompanyClass(currentID);
            if (companyType.getParentid() > 0) {
                level = colTree.getNodeDepth(colTree, currentID) - 1;
                childCount = colTree.getChildCount(colTree, currentID);

                ID = companyType.getId();
                String CName = StringUtil.gb2iso4View(companyType.getCname());
                String EName = companyType.getEname();
                int orderid = companyType.getOrderid();
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

                buf.append("<a href='editcolumn.jsp?ID=").append(companyType.getId());
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
                buf.append("<a href='javascript:editcompanyclass(\"").append(ID).append("\")'");
                buf.append("'>");
                buf.append(COLUMN_EDIT);
                buf.append("</a></td>").append("\n");

                buf.append("<td width='5%' align=center>");
                //buf.append("<a href='createcolumn.jsp?parentID=").append(ID);
                buf.append("<a href='javascript:createnewcompanyclass(\"").append(ID).append("\")'>");
                //buf.append("'>");
                //buf.append("&nbsp;");
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

                buf.append("</td>").append("\n");
                buf.append("</tr>\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        currentID = pid[nodenum];
    } while (nodenum > 0);
    //直到pid数组中没有待处理的节点为止

    int msgno = ParamUtil.getIntParameter(request, "msgno", -1);
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type="text/css" href="../css/global.css">
    <script language="javascript">
        function movein(which){
            which.style.background = '#CECEFF';
        }

        function moveout(which, color){
            which.style.background = color;
        }

        function createnewcompanyclass(troot){
            var winstr = "createcolumn.jsp?parentID=" + troot;
            window.open(winstr,"createnewcompanyclass","height=350,width=600,scrollbars=yes,titlebar=no,toolbar=no,location=no,menubar=no,directions=no,dependent=no,resizable=yes,channelmode=no");
        }

        function editcompanyclass(troot){
            var winstr = "editcolumn.jsp?ID=" + troot;
            window.open(winstr,"editcompanyclass","height=350,width=600,scrollbars=yes,titlebar=no,toolbar=no,location=no,menubar=no,directions=no,dependent=no,resizable=yes,channelmode=no");
        }
    </script>
</head>
<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<center>
    <%
        String[][] titlebars = {
                {"公司分类管理", ""}
        };
        String[][] operations = null;
        operations = new String[][] {
                //{"新建分类信息", "createcolumn.jsp?parentID=" + treeRoot}
                {"新建分类信息", "javascript:createnewcompanyclass('" + treeRoot + "')"}
        };
    %>
    <%@ include file="../inc/titlebar.jsp" %>
    <%
        if (msgno == 0)
            out.println("<span class=cur>公司分类信息创建成功</span>");
        else if (msgno == -1)
            out.println("<span class=cur></span>");
        else
            out.println("<span class=cur>公司分类信息创建失败</span>");
    %>
    <table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width='100%'>
        <tr class=tine bgcolor="#dddddd" height=20>
            <td width='52%'>&nbsp;<%=TREE_ROOT%>&nbsp;<%=treeNodes[0].getEnName()%></td>
            <td align=center width='16%'>目录名</td>
            <td align=center width='8%'>栏目排序</td>
            <td align=center width='8%'>修改</td>
            <td align=center width='8%'>新建</td>
            <td align=center width='8%'>删除</td>
        </tr>
        <%
            out.println(buf.toString());
        %>
    </table>
</center>
</BODY>
</html>

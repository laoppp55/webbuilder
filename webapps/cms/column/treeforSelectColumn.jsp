<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.register.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk" %>
<%

    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteID = authToken.getSiteID();
    String userid = authToken.getUserID();
    
    int ID = ParamUtil.getIntParameter(request, "ID", 0);
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int sitetype = authToken.getSitetype();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    Tree colTree = null;
    if (sitetype == 0 || sitetype == 2) {                             //0表示自己创建的网站，2表示完整拷贝模板网站
        if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
            //普通用户
            List clist = new ArrayList();
            Iterator iter1 = authToken.getPermissionSet().elements();
            while (iter1.hasNext()) {
                Permission permission = (Permission) iter1.next();
                if (rightid == permission.getRightID()) {
                    clist = permission.getColumnListOnRight();
                    break;
                }
            }
            //System.out.println("up=" + clist.size());
            colTree = TreeManager.getInstance().getUserTree(userid, siteid, clist,rightid);
        } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
            //站点管理员
            colTree = TreeManager.getInstance().getSiteTree(siteid);
        }
    } else {
        if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
            //普通用户
            List clist = new ArrayList();
            Iterator iter1 = authToken.getPermissionSet().elements();
            while (iter1.hasNext()) {
                Permission permission = (Permission) iter1.next();
                if (rightid == permission.getRightID()) {
                    clist = permission.getColumnListOnRight();
                    //System.out.println("down" + clist.size());
                    break;
                }
            }
            colTree = TreeManager.getInstance().getUserTreeIncludeSampleSiteColumn(userid, siteid,samsiteid,clist);
        } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
            //站点管理员
            colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
        }
    }

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
                //String cname = new String(treeNodes[i].getChName().getBytes("ISO8859-1"), "GBK");
                String cname = StringUtil.gb2iso4View(treeNodes[i].getChName());
                String name = "<font class=line>" + cname + "</font>";
                buf.append(menuName).append(".MTMAddItem(new MTMenuItem(\"");
                buf.append(name).append("\",\"javascript:parent.frames['cmsleft'].abc('" + ID + "','" + treeNodes[i].getId() + "','" + StringUtil.replace(cname, "'", "`") + "')\"" + ", \"cmsright\"));");
                buf.append("\n");
            }
        }

        //关联当前菜单和他的父菜单
        if (subflag != 0 && currentID != 0)
            buf.append(parentMenu + ".items[" + orderNumber + "].MTMakeSubmenu(" + menuName + ");\r\n");

    } while (nodenum >= 1);
    //直到pid数组中没有待处理的节点为止
%>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <LINK href="../style/global.css" type=text/css rel=stylesheet>
    <script type="text/javascript">
        function abc(par, par1, par2)
        {
            par2 = par2.replace("`", "'");
            var el = parent.frames['cmsright'].form1.columnList;
            var oOption = document.createElement("OPTION");
            oOption.text = par2;
            oOption.value = par1;
            optionLen = el.options.length;
            i = 0;
            var endFlag = true;

            if (par == par1)
            {
                endFlag = false;
                alert("不能引用自身栏目的文章！");
            }

            while (( i < optionLen) && endFlag)
            {
                if (el.options(i).value == oOption.value)
                {
                    endFlag = false;
                    alert("请不要重复选择栏目！");
                }
                i++;
            }

            if (endFlag) {
                el.add(oOption);
                parent.frames['cmsright'].form1.columnSubmit.disabled = false;
                parent.frames['cmsright'].form1.delColumn.disabled = false;
            }
        }
    </script>
</head>
<script type="text/javascript" src="../js/mtmcode.js"></script>
<script type="text/javascript">
    var needDrag = 0;
    <%=buf.toString()%>
</script>

<BODY onload="MTMStartMenu(true)" leftMargin=0 topMargin=0 MARGINHEIGHT="0" MARGINWIDTH="0">
</BODY>
</html>
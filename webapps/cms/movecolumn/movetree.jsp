<%@ page import="com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String userid = authToken.getUserID();
  int siteid = authToken.getSiteID();
  Tree colTree = null;

  if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken,54))    //站点管理员
  {
    colTree = TreeManager.getInstance().getSiteTree(siteid);
  }
  else if (userid.equalsIgnoreCase("admin"))                                             //系统管理员
  {
    colTree = TreeManager.getInstance().getTree();
  }

  node[] treeNodes = colTree.getAllNodes();                     //获取该树的所有节点
  int pid[] = new int[colTree.getNodeNum()];                    //遍历树所需要的节点数组，存储当前未处理的节点
  String parentMenuName[] = new String[colTree.getNodeNum()];   //存储某个节点的所有子节点的父菜单名称
  String parentMenu="menu";                                     //存储当前节点的父菜单名称
  String menuName = "menu";                                     //存储当前节点的菜单名称
  int currentID = 0;                                            //当前正在处理的节点
  int i=0;                                                      //循环变量
  int[] ordernum = new int[colTree.getNodeNum()];               //当前节点所有子节点的顺序号
  int orderNumber = 0;                                          //当前节点在同级节点的顺序号
  int nodenum = 1;                                              //当前被处理节点的初始值
  int subflag = 1;                                              //判断当前节点是否有子节点
  StringBuffer buf = new StringBuffer();                        //存储生成的菜单树

  do
  {
    currentID = pid[nodenum];

    //设置当前处理节点的初始值
    if(currentID != 0)
    {
      parentMenu = parentMenuName[nodenum];
      orderNumber = ordernum[nodenum];
      menuName = "menu"+currentID;
    }

    //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
    //设置所有子节点的父菜单名称，设置所有子节点的序列号，把所有的子节点存入pid数组中
    subflag = 0;
    nodenum = nodenum - 1;
    for (i=0;i<colTree.getNodeNum();i++)
    {
      if (treeNodes[i].getLinkPointer() == currentID)
      {
        nodenum = nodenum + 1;
        pid[nodenum] = treeNodes[i].getId();
        parentMenuName[nodenum] = menuName;
        ordernum[nodenum] = subflag;
        subflag = subflag + 1;
      }
    }

    //如果当前节点有子节点，生成当前节点的菜单
    if (subflag != 0)
    {
      buf.append("var "+menuName+" = null;\n");
      buf.append(menuName+" = new MTMenu();\n");
    }
    for(i=0;i<colTree.getNodeNum();i++)
    {
      if(treeNodes[i].getLinkPointer() == currentID)
      {
        String name = StringUtil.gb2iso4View(treeNodes[i].getChName());
        buf.append(menuName).append(".MTMAddItem(new MTMenuItem(\"");
        buf.append("<font class=line>" + name + "</font>").append("\",");
        buf.append(" \"columns.jsp|" + treeNodes[i].getId() + "|" + name + "\", \"\"));");
        buf.append("\n");
      }
    }

    //关联当前菜单和他的父菜单
    if(subflag != 0 && currentID != 0)
      buf.append(parentMenu+".items["+orderNumber+"].MTMakeSubmenu(" + menuName + ");\r\n");
  }while(nodenum >= 1);
  //直到pid数组中没有待处理的节点为止
%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title></title>
  <link rel=stylesheet type="text/css" href="../style/global.css">
  <script language="javascript">
      function Drag(columnID, Cname)
      {
          var objXml = new ActiveXObject("Microsoft.XMLHTTP");
          objXml.open("POST", "getColumnInfo.jsp?column="+columnID, false);
          objXml.Send();
          var content = objXml.responseText;
          var arr = content.split(",");

          parent.frames['cmsright'].cname.innerText = Cname;
          parent.frames['cmsright'].columnID.innerText = columnID;
          parent.frames['cmsright'].ename.innerText = arr[0].substring(4);
          parent.frames['cmsright'].dirname.innerText = arr[1];
          parent.frames['cmsright'].sitename.innerText = arr[2];
          parent.frames['cmsright'].siteID.innerText = arr[3];
          parent.frames['cmsright'].articlenum.innerText = arr[4];
          parent.frames['cmsright'].movecolumn.objectID.value = columnID;
      }
      var needDrag = 1;
  </script>
</head>
<script type="text/javascript" src="../js/mtmcode.js"></script>
<script type="text/javascript">
    <%=buf.toString()%>
</script>
<BODY onload="MTMStartMenu(true)" leftMargin=0 topMargin=0 MARGINHEIGHT="0" MARGINWIDTH="0">
</BODY></html>
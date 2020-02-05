<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int columnID  = ParamUtil.getIntParameter(request, "column", 0);
  String sitename = authToken.getSitename();
  String baseDir = application.getRealPath("/");
  String rootDir = baseDir + "sites" + java.io.File.separator + sitename + java.io.File.separator + "_templates" + java.io.File.separator;

  Tree dirTree = TreeManager.getInstance().getDirTree(rootDir);         //获取在服务器端生成的一棵树
  node[] treeNodes = dirTree.getAllNodes();                             //获取该树的所有节点
  String parentMenuName[] = new String[dirTree.getNodeNum()];           //存储某个节点的所有子节点的父菜单名称
  String parentMenu="menu";                                             //存储当前节点的父菜单名称
  String menuName = "menu";                                             //存储当前节点的菜单名称
  int currentID = 0;                                                    //当前正在处理的节点
  int i=0,j=0;                                                          //循环变量
  int[] ordernum = new int[dirTree.getNodeNum()];                       //当前节点所有子节点的顺序号
  int orderNumber = 0;                                                  //当前节点在同级节点的顺序号
  int subflag = 1;                                                      //判断当前节点是否有子节点
  StringBuffer buf = new StringBuffer();                                //存储生成的菜单树

  for (i=0; i< dirTree.getNodeNum(); i++)
  {
    currentID = i;

    //设置当前处理节点的初始值
    if(currentID != 0){
      parentMenu = parentMenuName[i];
      orderNumber = ordernum[i];
      menuName = "menu"+currentID;
    }

    //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
    //设置所有子节点的父菜单名称，设置所有子节点的序列号，把所有的子节点存入pid数组中
    subflag = 0;
    for(j=0; j<dirTree.getNodeNum(); j++) {
      if(treeNodes[j].getLinkPointer() == currentID) {
        parentMenuName[j] = menuName;
        ordernum[j] = subflag;
        subflag = subflag + 1;
      }
    }

    //如果当前节点有子节点，生成当前节点的菜单
    if (subflag != 0) {
      buf.append("var "+menuName+" = null;\n");
      buf.append(menuName+" = new MTMenu();\n");
    }
    for(j=0;j<dirTree.getNodeNum();j++) {
      if(treeNodes[j].getLinkPointer() == currentID) {
        int id = treeNodes[j].getId();
        String path = dirTree.getFileSystemDirName(dirTree,id);
        String name  = "<font class=line>"+treeNodes[j].getEnName() + "</font>";
        buf.append(menuName).append(".MTMAddItem(new MTMenuItem(\"");
        buf.append(name).append("\",\"listfile.jsp?path=" + path +  "&column=" + columnID + "\"" + "));");
        buf.append("\n");
      }
    }

    //关联当前菜单和他的父菜单
    if(subflag != 0 && currentID != 0)
      buf.append(parentMenu+".items["+orderNumber+"].MTMakeSubmenu(" + menuName + ");\r\n");
  }
%>
<html>
<head>
<title></title>
<link rel=stylesheet type="text/css" href="../style/global.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<script type="text/javascript" src="../js/mtmcode.js"></script>
<script type="text/javascript">
var needDrag = 0;
<%
  if (buf.length() == 0)
  {
    buf.append("var menu = null;\n");
    buf.append("menu = new MTMenu();\n");
  }
  out.println(buf.toString());
%>
</script>
<BODY onload="MTMStartMenu(true)" leftMargin=0 topMargin=0 MARGINHEIGHT="0" MARGINWIDTH="0">
</BODY></html>
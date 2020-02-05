<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%@ page import="com.bizwink.cms.tree.node" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page import="com.bizwink.upload.MultipartFormHandle" %>
<%@ page import="com.bizwink.cms.sitesetting.FtpSetting" %>
<%@ page import="com.bizwink.cms.sitesetting.IFtpSetManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.upload.uploaderrormsg" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-6-6
  Time: 上午10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int siteid = authToken.getSiteID();
  String username = authToken.getUserID();
  int samsiteid = authToken.getSamSiteid();
  int sitetype = authToken.getSitetype();
  String sitename = authToken.getSitename();
  int imgflag = authToken.getImgSaveFlag();
  int cssjsdir = authToken.getCssJsDir();
  int tcflag = authToken.getPublishFlag();
  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int languageType = ParamUtil.getIntParameter(request, "language", 0);

  Tree colTree = null;
  if (sitetype == 0 || sitetype == 2) {                             //0表示自己创建的网站，2表示完整拷贝模板网站
    colTree = TreeManager.getInstance().getSiteTree(siteid);
  } else {                                                           //1表示共享模板网站的栏目和模板
    colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
  }

  //构建栏目树的JSON数据
  StringBuffer buf = new StringBuffer();                        //存储生成的菜单树
  if (colTree.getNodeNum() > 1) {
    node[] treeNodes = colTree.getAllNodes();                     //获取该树的所有节点
    List<Integer> pid = new ArrayList<Integer>();
    int currentID = 0;                                            //当前正在处理的节点
    int i = 0;                                                      //循环变量
    int[] ordernum = new int[colTree.getNodeNum()];               //当前节点所有子节点的顺序号
    int nodenum = 1;                                              //当前被处理节点的初始值
    int subnodenum = 1;                                              //判断当前节点是否有子节点
    int subnodenumOfParentNode = 0;

    pid.add(0);
    buf.append("[");
    while(pid.size()>0) {
      //处理当前节点
      currentID = pid.get(0);
      pid.remove(0);
      int currentNodeIndex = 0;
      int currentNode_ParentNodeIndex = 0;
      //获取当前节点
      for (i = 0; i < colTree.getNodeNum(); i++) {
        if (treeNodes[i].getId() == currentID) {
          currentNodeIndex = i;
          break;
        }
      }

      //获取当前节点的父节点
      for (i = 0; i < colTree.getNodeNum(); i++) {
        if (treeNodes[i].getId() == treeNodes[currentNodeIndex].getLinkPointer()) {
          currentNode_ParentNodeIndex = i;
          break;
        }
      }

      //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
      //设置所有子节点的父菜单名称，设置所有子节点的序列号，把所有的子节点存入pid数组中
      subnodenum = 0;
      nodenum = nodenum - 1;
      for (i = colTree.getNodeNum() - 1; i >= 0; i--) {
        if (treeNodes[i].getLinkPointer() == currentID) {
          nodenum = nodenum + 1;
          pid.add(0, treeNodes[i].getId());
          ordernum[nodenum] = subnodenum;
          subnodenum = subnodenum + 1;
        }
      }

      //处理当前节点
      if (currentID != 0) {
        //当前节点的父节点的子节点数量减1
        subnodenumOfParentNode = treeNodes[currentNode_ParentNodeIndex].getSubnodes() - 1;
        //修改当前节点父节点未处理的子节点的数量
        treeNodes[currentNode_ParentNodeIndex].setSubnodes(subnodenumOfParentNode);
        //设置当前节点子节点的数量
        treeNodes[currentNodeIndex].setSubnodes(subnodenum);
        //处理当前节点没有子节点的情况
        if (subnodenum == 0) {
          //当前节点是父节点的最后一个节点,循环向树形结构的上层进行查找，一直找到子节点数尚不是零的节点，从不是零的节点向下写节点的结束部分
          if (subnodenumOfParentNode == 0) {
            buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + "}\r");
            //寻找当前节点向上的路径上子节点数不是零的节点
            int tnum = 0;
            int level = 0;
            while (tnum == 0 && treeNodes[currentNodeIndex].getLinkPointer() > 0) {
              for (i = 0; i < colTree.getNodeNum(); i++) {
                if (treeNodes[i].getId() == treeNodes[currentNodeIndex].getLinkPointer()) {
                  tnum = treeNodes[i].getSubnodes();
                  if (tnum == 0) {
                    level = level + 1;
                    currentNodeIndex = i;
                    break;
                  }
                }
              }
            }

            //从当前节点到根节点的路径上查找到第一个未处理子节点数不为零的节点，输出相应深度个数的“]}”字符串
            for (i = 0; i < level-1; i++) buf.append("]}\r");

            buf.append("]},\r");
          } else {                         //当前节点不是父节点的最后一个节点
            buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + "},\r");
          }
        } else {
          if (treeNodes[currentNodeIndex].getLinkPointer()==0)
            buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + ",spread: true," + "children:[\r");
          else
            buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + "," + "children:[\r");
        }
      }
    }

    //去掉字符串最后一个多余的“,”字符，增加json的关闭字符“];”
    int posi = buf.lastIndexOf(",");
    if (posi>-1) {
      buf.delete(posi,buf.length());
      buf.append("\r]");
    }
  }

  String fileDir = "";
  String dir = "";
  String columnname = null;
  Column column = null;
  String Message = "";
  IColumnManager columnMgr = ColumnPeer.getInstance();
  if (columnID > 0) {
    column = columnMgr.getColumn(columnID);
    columnname = column.getCName();
    columnname = StringUtil.gb2iso4View(columnname);
    if (columnID == colTree.getTreeRoot())
      fileDir = java.io.File.separator;
    else
      fileDir = colTree.getDirName(colTree, columnID);
  } else {
    columnname = "程序模板页面";
    fileDir = java.io.File.separator + "program" + java.io.File.separator;
  }

  String baseDir = application.getRealPath("/");
  String tempDir = StringUtil.replace(fileDir, "/", java.io.File.separator);

  //获得所有发布主机列表
  IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
  List siteList = ftpsetMgr.getOtherFtpInfos(siteid);

  uploaderrormsg errormsg = (uploaderrormsg)request.getAttribute("UploadMsg");
  if (errormsg != null) {
    if (errormsg.getErrorCode() == 0)
      Message = "文件上传成功，并被发布到了WEB服务器！！！";
    else{
      Message = (errormsg.getErrorMsg()!=null)?errormsg.getErrorMsg():"";
    }

    for (int i=0; i<errormsg.getErrorPics().size(); i++) {
      String picname = (String)errormsg.getErrorPics().get(i);
    }
  }
%>
<!DOCTYPE html>
<html>
<head>
  <title>网站内容管理系统--模板管理--上传模板</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel=stylesheet type="text/css" href="../style/global.css">
  <link rel="stylesheet" href="../css/cms_css.css">
  <link rel="stylesheet" href="../design/css/layui.css">
  <link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css">
  <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
  <script type="text/javascript" src="../js/json2.js"></script>
  <script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
  <script type="text/javascript" src="../design/layui.js"></script>
  <SCRIPT type="text/javascript">
      <!--
      var setting = {
          view: {
              selectedMulti: true,
              showLine:false,
              fontCss:setFontCss
          },
          check: {
              enable: true
          },
          data: {
              simpleData: {
                  enable: true
              }
          },
          callback: {
              onClick:onClick
          }
      };

      var zNodes =<%=buf.toString()%>;

      function setFontCss(treeId, node) {
          //如果节点id==0则是红色字体（这里自己写自己的判断逻辑就可以了）
          //if(node.level==0){
          //    return {"color":"red"};
          //}else{
          return {"color":"white"};
          //}
      }

      $(document).ready(function(){
          layui.use('element', function(){
              var element = layui.element;
          });

          $.fn.zTree.init($("#treeId"), setting, zNodes);
          //setCheck();
          var treeObj = $.fn.zTree.getZTreeObj("treeId");
          var nodes = treeObj.getNodes();
          if (nodes.length>0) {
              for (var i = 0; i < nodes.length; i++) {
                  treeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
              }
          }
      });


      function onClick(e, treeId, treeNode) {
          uploadfile.uploadcolumnname.value = treeNode.name;
          uploadfile.uploadimagescolumnid.value = treeNode.id;
      }

      function closethepage() {
          <%
              request.removeAttribute("UploadMsg");
          %>
          top.close();
      }
      //-->
  </SCRIPT>
</head>
<body class="layui-layout-body">
<form method="post" action="<%=request.getContextPath()%>/multipartformserv?siteid=<%=siteid%>&sitename=<%=sitename%>" name=uploadfile enctype="multipart/form-data" onSubmit="return validate();">
  <div class="layui-layout layui-layout-admin">
    <input type="hidden" name="tcolumn" id="tcid" value="<%=columnID%>">
    <div class="layui-side layui-bg-black" id="sidetreeid" style="top: 0px;">
      <div id="coltreeid">
        <ul id="treeId" class="ztree"></ul>
      </div>
    </div>

    <div class="layui-body" style="top:0px;">
      <!-- 内容主体区域 -->
      <div id="contentid">
        <%=Message%>
        <input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>" value="/upload/modelupload.jsp">
        <input type="hidden" name="column" value="<%=columnID%>">
        <input type="hidden" name="language" value="<%=languageType%>">
        <input type="hidden" name="fileDir" value="<%=tempDir%>">
        <input type="hidden" name="username" value="<%=username%>">
        <input type="hidden" name="imgflag" value="<%=imgflag%>">
        <input type="hidden" name="cssjsdir" value="<%=cssjsdir%>">
        <input type="hidden" name="tcflag" value="<%=tcflag%>">
        <input type="hidden" name="fromflag" value="model">

        <TABLE CELLSPACING=10 align=center width="100%">
          <tr>
            <td>
              <li><b>支持可以上传的文件扩展名可以是.html、.htm、.shtml、.shtm</b><br>
              <li><b>支持的压缩文件包为ZIP格式，其他压缩格式不支持</b><br>
              <li><b>支持的图象格式包括.bmp、.jpg、.jpeg、.gif、.png</b><br>
                  <%if(imgflag == 0){%>
              <li><b>本站点设置的模板图象存储位置为“站点根目录”</b>
                  <%}else{%>
              <li><b>本站点设置的模板图象存储位置为“各级栏目目录”，请选择对应栏目后，上传模板图像</b>
                  <%}%>
            </td>
          </tr>
          <tr>
            <td>模板HTML文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=file ID=htmlfilename name=htmlfilename size=25></td>
          </tr>
          <tr>
            <td>CSS文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=file ID=cssfilename name=cssfilename size=25></td>
          </tr>
          <tr>
            <td>脚本文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=file ID=scriptfilename name=scriptfilename size=25></td>
          </tr>
          <tr>
            <td>模板简体图象包/图象：<INPUT type=file ID=sfilename name=sfilename size=25></td>
          </tr>
          <%if(tcflag == 1){%>
          <tr>
            <td>模板繁体图象包/图象：<INPUT type=file ID=tfilename name=tfilename size=25></td>
          </tr>
          <%}%>
          <tr>
            <td>选择图片所在目录：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="uploadcolumnname" size="35" readonly value="<%=columnname%>" class="sear_txt_1">
              <input type=hidden name="uploadimagescolumnid" size="20" value="<%=columnID%>" readonly></td>
          </tr>
          <tr>
            <td>选择主机：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <select name="pubsite" size=1 style="width:190px;font-size:9pt">
                <option value=<%=sitename + "_" + siteid%> selected><%=StringUtil.replace(sitename, "_", ".")%>
                </option>
                <%
                  for (int i = 0; i < siteList.size(); i++) {
                    SiteInfo siteinfo = (SiteInfo) siteList.get(i);
                    if (siteinfo.getBindFlag() == 1 && siteinfo.getSiteid() != siteid) {
                      String siteName = siteinfo.getDomainName();
                      if (siteName == null || siteName.length() == 0) siteName = sitename;
                %>
                <option value="<%=StringUtil.replace(siteName,".","_") + "_" + siteinfo.getSiteid()%>"><%=siteName%>
                </option>
                <%
                    }
                  }
                %>
              </select>

            </td>
          </tr>
          <TR height=20>
            <TD align=center>&nbsp;&nbsp;
            </TD>
          </TR>
          <TR height=30>
            <TD align=center>
              <input type="submit" name=ok value="  确定  " class=tine>&nbsp;&nbsp;
              <input type="button" name=Cancel onclick="closethepage();" value="  取消  " class=tine>
            </TD>
          </TR>
        </TABLE>
      </div>
    </div>
  </div>
</FORM>
<script>
    layui.use(['element', 'tree', 'laydate'], function(){
        var $ = layui.jquery;
        var laydate = layui.laydate;
    });
</script>
</body>
</html>
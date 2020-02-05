<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%@ page import="com.bizwink.cms.tree.node" %>
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
  int samsiteid = authToken.getSamSiteid();
  int sitetype = authToken.getSitetype();
  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int flag = ParamUtil.getIntParameter(request, "flag", 0);
  int pageno = ParamUtil.getIntParameter(request, "page", 0);
  int range = ParamUtil.getIntParameter(request, "range", 20);
  int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
  boolean doSearchFlag = ParamUtil.getBooleanParameter(request, "searchFlag");
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
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>网站内容管理系统--文章管理</title>
  <link rel="stylesheet" href="../css/cms_css.css">
  <link rel="stylesheet" href="../design/css/layui.css">
  <link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css">
  <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
  <script type="text/javascript" src="../js/json2.js"></script>
  <script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
  <!--script type="text/javascript" src="../js/jquery.ztree.excheck-3.5.min.js"></script-->
  <script type="text/javascript" src="../design/layui.js"></script>
  <script type="text/javascript" src="../js/referArticle.js"></script>
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
          articlesInfoList(treeNode.id,1,false,4,0);
      }

      function doReferArticle(range, currentPage,doSearchFlag,flag,ascdesc) {
          var selArticleIds = "";
          $('input:checkbox[name=article]:checked').each(function(i) {
              selArticleIds = selArticleIds + $(this).val() + ",";
          });
          if (selArticleIds.length>0) selArticleIds = selArticleIds.substring(0,selArticleIds.length-1);
          var useArticleType = $('input[name="useArticleType"]:checked ').val();
          var tcolumnid = $("input:hidden[id='tcid']").val();
          htmlobj=$.ajax({
              url:"referArticleRight.jsp",
              type:"POST",
              data:{
                  articles:selArticleIds,
                  column:tcolumnid,
                  useArticleType:useArticleType,
                  range:range,
                  start:currentPage,
                  searchFlag:doSearchFlag,
                  flag:flag,
                  ascdesc:ascdesc
              },
              dataType:'json',
              async:false,
              success:function(jsondata){
                  if (jsondata.ERRCODE == 0) {
                      alert("文章引用成功！！！");
                      window.opener.articlesInfoList(jsondata.COLUMNID,jsondata.START,jsondata.SEARCHFLAG,jsondata.FLAG,jsondata.ASCDESC,null,null,null,null);
                      window.close();
                  }
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

      function CA() {
          for (var i = 0; i < document.form1.elements.length; i++) {
              var e = document.form1.elements[i];
              if (e.name != 'allbox' && e.type == 'checkbox') {
                  e.checked = document.form1.allbox.checked;
              }
          }
      }
      //-->
  </SCRIPT>
</head>
<body class="layui-layout-body">
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
    </div>
  </div>
</div>
<script>
    layui.use(['element', 'tree', 'laydate'], function(){
        var $ = layui.jquery;
        var laydate = layui.laydate;
    });
</script>
</body>
</html>
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
    <title>网站内容管理系统--模板管理--引用模板</title>
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
            templatesInfoList(treeNode.id);
        }

        function templatesInfoList(columnid) {
            var siteid = <%=siteid%>;
            htmlobj=$.ajax({
                url:"../template/templatesnew.jsp",
                data:{
                    column:columnid
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    var modelRight = jsondata.MODELRIGHT;
                    var adminRight = jsondata.ADMINRIGHT;
                    var publishRight = jsondata.PUBLISHRIGHT;
                    var htmldata = "<table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n";
                    htmldata = htmldata + "    <tr>\n" +
                        "        <td align=\"left\" valign=\"top\">\n" +
                        "            <table id=\"modelDataGrid\" width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#CCCCCC\" class=\"tab_list\">\n" +
                        "                <tr>\n" +
                        "                    <td width=\"150\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">中文名称</td>\n" +
                        "                    <td width=\"150\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">英文名称</td>\n" +
                        "                    <td width=\"150\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">模板类型</td>\n" +
                        "                    <td width=\"150\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">修改时间</td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">创建者</td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">预览</td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">引用</td>\n" +
                        "                </tr>\n";

                    for(var ii=0;ii<jsondata.DATA.length;ii++) {
                        var modelid = jsondata.DATA[ii].ID;
                        var chname = (typeof(jsondata.DATA[ii].CHNAME)!="undefined")?jsondata.DATA[ii].CHNAME:"";
                        var enname = (typeof(jsondata.DATA[ii].TEMPLATENAME)!="undefined")?jsondata.DATA[ii].TEMPLATENAME:"";
                        var templateType = jsondata.DATA[ii].ISARTICLE;
                        var templateTypeName = "";
                        if (jsondata.DATA[ii].ISARTICLE == 2)
                            templateTypeName = "首页模板";
                        else if (jsondata.DATA[ii].ISARTICLE == 5)
                            templateTypeName = "手机首页模板";
                        else if (jsondata.DATA[ii].ISARTICLE == 3)
                            templateTypeName = "专题模板";
                        else if (jsondata.DATA[ii].ISARTICLE == 6)
                            templateTypeName = "手机专题模板";
                        else if (jsondata.DATA[ii].ISARTICLE == 0)
                            templateTypeName = "栏目模板";
                        else if (jsondata.DATA[ii].ISARTICLE == 4)
                            templateTypeName = "手机栏目模板";
                        else if (jsondata.DATA[ii].ISARTICLE == 1)
                            templateTypeName = "文章模板";
                        var referModelID = jsondata.DATA[ii].REFERMODELID;
                        if (referModelID > 0) templateTypeName = templateTypeName + "(引用)";

                        var editor = (typeof(jsondata.DATA[ii].EDITOR)!="undefined")?jsondata.DATA[ii].EDITOR:"";
                        var createdate = (typeof(jsondata.DATA[ii].CREATEDATE)!="undefined")?jsondata.DATA[ii].CREATEDATE:"";
                        var defaultModel = jsondata.DATA[ii].DEFAULTTEMPLATE;
                        var siteidOfModel = jsondata.DATA[ii].SITEID;
                        var lockflag = jsondata.DATA[ii].LOCKSTATUS;

                        htmldata = htmldata + "                <tr bgcolor=\"#FFFFFF\" id=\"row\"" + modelid + ">\n";
                        htmldata = htmldata + "<td>" + chname + "</td>";
                        htmldata = htmldata + "<td>" + enname + "</td>";
                        htmldata = htmldata + "<td align=\"center\">" + templateTypeName + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + createdate + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\" >" + editor + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:Preview(" + columnid + "," + modelid + "," + templateType + ")\"><img src=\"../images/view.png\" width=\"23\" height=\"23\" alt=\"预览模板\" /></a></td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:ReferModel(" + modelid + ",'" + templateTypeName + "')\"><img src=\"../images/button/edit.gif\" width=\"23\" height=\"23\" /></a></td>";
                        htmldata = htmldata + "</tr>";
                        //alert(jsondata[ii].USERID + jsondata.NICKNAME + jsondata.EMAIL + jsondata.MPHONE + jsondata.SEX + jsondata.COMPANYID + jsondata.DEPARTMENT);
                    }
                    htmldata = htmldata + "            </table>\n";
                    htmldata = htmldata + "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";

                    $("#contentid").html(htmldata);
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

        function ReferModel(modelid,templateTypeName) {
            var columnid = $("#tcid").val();
            htmlobj=$.ajax({
                url:"referTemplateRight.jsp",
                type:"POST",
                data:{
                    template:modelid,
                    column:columnid,
                    doCreate:true
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.result == "true") {
                        alert("引用模板：" + templateTypeName + "成功！！！");
                        window.opener.templatesInfoList(columnid);
                        window.close();
                    } else {
                        alert("引用模板：" + templateTypeName + "失败！！！");
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
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.service.ColumnService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.security.SecurityCheck" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.security.PermissionSet" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.bizwink.cms.security.Permission" %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%@ page import="com.bizwink.cms.tree.node" %>
<%@ page import="com.bizwink.cms.util.FileUtil" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-6-6
  Time: 上午10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int samsiteid = authToken.getSamSiteid();
    String userid = authToken.getUserID();
    int sitetype = authToken.getSitetype();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    String tbuf = null;
    Tree colTree = null;

    Column rootColumn = null;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        ColumnService columnService = (ColumnService)appContext.getBean("columnService");
        rootColumn = columnService.getRootColumnBySiteid(BigDecimal.valueOf(siteid));
    }
    int rootColumnID = rootColumn.getID().intValue();
    if (sitetype == 0 || sitetype == 2) {                             //0表示自己创建的网站，2表示完整拷贝模板网站
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
        }
    } else {                                                           //1表示共享模板网站的栏目和模板
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
            colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
        }
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
                        buf.append("{title:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + "}\r");
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
                        buf.append("{title:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + "},\r");
                    }
                } else {
                    if (treeNodes[currentNodeIndex].getLinkPointer()==0)
                        buf.append("{title:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + ",spread: true," + "children:[\r");
                    else
                        buf.append("{title:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + "," + "children:[\r");
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
    <title>网站内容管理系统--模板管理</title>
    <link rel="stylesheet" href="../design/css/layui.css">
    <link rel="stylesheet" href="../css/mycss.css">
    <link rel="stylesheet" href="../css/cms_css.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <!--引用xtree-->
    <script type="text/javascript" src="../design/layui-xtree.js"></script>
    <script>
        $(document).ready(function(){
            layui.use('element', function(){
                var element = layui.element;
            });

            var rootColumnId = <%=rootColumn.getID()%>;
            templatesInfoList(rootColumnId);
        });

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
                    var column_name = jsondata.COLUMNNAME;
                    var htmldata = "<table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n";
                    htmldata = htmldata + "    <tr>\n" +
                        "        <td>\n" +
                        "            <div class=\"location\">\n" +
                        "                <div class=\"location_1\">模板管理>" + column_name + "</div>\n" +
                        "                <div class=\"button_top\">\n" +
                        "                    <ul>\n" +
                        "                        <li><input name=\"\" type=\"button\" value=\"文件夹管理\" class=\"button_bg_3\" onclick='javascript:WebEdit(" + columnid + ");' /></li>\n" +
                        "                        <li><input name=\"\" type=\"button\" value=\"装入模板\" class=\"button_bg_3\" onclick='javascript:CreateModel(" + columnid + ");' /></li>\n" +
                        "                        <li><input name=\"\" type=\"button\" value=\"上传模板/图像\" class=\"button_bg_3\" onclick='javascript:UploadModel(" + columnid + ",1);' /></li>\n" +
                        "                        <li><input name=\"\" type=\"button\" value=\"引用模板\" class=\"button_bg_3\" onclick='javascript:ReferModel(" + columnid + ");' /></li>\n" +
                        "                    </ul>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </td>\n" +
                        "    </tr>\n";
                    htmldata = htmldata + "    <tr>\n" +
                        "        <td align=\"left\" valign=\"top\">\n" +
                        "            <table id=\"modelDataGrid\" width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#CCCCCC\" class=\"tab_list\">\n" +
                        "                <tr>\n" +
                        "                    <td width=\"150\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">中文名称</td>\n" +
                        "                    <td width=\"150\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">英文名称</td>\n" +
                        "                    <td width=\"150\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">模板类型</td>\n" +
                        "                    <td width=\"150\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">修改时间</td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">创建者</td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">设置默认</td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">修改</td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">删除</td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">预览</td>\n" +
                        "                    <td width=\"80\" align=\"center\" bgcolor=\"#f4f4f4\">发布</td>\n" +
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
                        if(templateType != 3) {
                            if (defaultModel == 1)
                                htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:setDefaulModel(" + modelid + "," + columnid + "," + templateType + ")\"><img src=\"../images/button/moren.gif\" width=\"23\" height=\"23\" /></a></td>";
                            else
                                htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:setDefaulModel(" + modelid + "," + columnid + "," + templateType + ")\"><img src=\"../images/button/morenyuan.gif\" width=\"23\" height=\"23\" /></a></td>";
                        } else{
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\">&nbsp;</td>";
                        }
                        if (siteid === siteidOfModel) {
                            if (lockflag == 0)
                                htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:updateTemplate(" + columnid + "," + modelid + "," + templateType + "," + referModelID + ");\"><img src=\"../images/edit.png\" width=\"24\" height=\"23\" alt=\"修改模板\" /></a></td>";
                            else if (modelRight || adminRight)
                                htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:updateTemplate(" + columnid + "," + modelid + "," + templateType + "," + referModelID + ");\"><img src=\"../images/button/lock.gif\" width=\"24\" height=\"23\" alt=\"模板锁定\" /></a></td>";
                            else
                                htmldata = htmldata + "<td align=center><img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"模板锁定\">";

                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:removeModel(" + columnid + "," + modelid + "," + templateType  + ",'" + templateTypeName + "')\"><img src=\"../images/del.png\" width=\"23\" height=\"23\" alt=\"删除模板\" /></a></td>";
                        } else {
                            htmldata = htmldata + "<td align=center valign=\"middle\"><img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"模板锁定\"></td>"
                            htmldata = htmldata + "<td align=center valign=\"middle\"><a href='javascript:opencopytemplate("+modelid+")'>查看模版</a></td>"
                        }
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:Preview(" + columnid + "," + modelid + "," + templateType + ")\"><img src=\"../images/view.png\" width=\"23\" height=\"23\" alt=\"预览模板\" /></a></td>";
                        if (templateType != 1 && (publishRight || adminRight))
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:publishModel(" + columnid + "," + modelid + "," + templateType + ",'" + templateTypeName + "')\"><img src=\"../images/public.png\" width=\"23\" height=\"23\" /></a></td>";
                        else
                            htmldata = htmldata + "<td align=center>&nbsp;</td>";
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

        function setDefaulModel(templateID,columnid,modeltype) {
            htmlobj=$.ajax({
                url:"setupDefaultTemplate.jsp",
                type:"POST",
                data:{
                    column:columnid,
                    template:templateID,
                    isArticle:modeltype
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.RESULT===true) {
                        alert("设置默认模板成功！！！");
                        templatesInfoList(columnid);
                    } else {
                        alert("设置默认模板失败！！！");
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

        function publishModel(columnid,templateID,modelType,modelname) {
            htmlobj=$.ajax({
                url:"../publish/publishnew.jsp",
                data:{
                    column:columnid,
                    template:templateID,
                    modelType:modelType,
                    source:2
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.result == "true") {
                        alert('模板:' + modelname + '发布成功！！！')
                    } else {
                        alert('模板:' + modelname + '发布失败！！！');
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

        function removeModel(columnid,templateID,modeltype,modelname) {
            htmlobj=$.ajax({
                url:"removetemplate.jsp",
                data:{
                    template:templateID,
                    doDelete:true,
                    isArticle:modeltype,
                    column:columnid
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.result == "true") {
                        alert('模板:' + modelname + '删除成功！！！');
                        templatesInfoList(columnid);
                    } else {
                        alert('模板:' + modelname + '删除失败！！！');
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

        function Preview(columnid,templateID,isArticle) {
            var iWidth=window.screen.availWidth;                                                 //弹出窗口的宽度;
            var iHeight=window.screen.availHeight;                                                //弹出窗口的高度;
            var iTop = 0;        //获得窗口的垂直位置;
            var iLeft = 0;         //获得窗口的水平位置;
            window.open("previewTemplate.jsp?column=" + columnid + "&template=" + templateID + "&isArticle=" + isArticle, "PreviewTemplate", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + "scrollbars");
        }

        function updateTemplate(columnid,templateID,isArticle, referModelID) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            if (referModelID > 0)
                window.open("editReferTemplate.jsp?template=" + templateID + "&column=" + columnid + "&from=c", "", "width=400,height=200,left=300,top=200,status");
            else
                window.open("edittemplate.jsp?template=" + templateID + "&column=" + columnid + "&isArticle=" + isArticle + "&rightid=<%=rightid%>&from=c", "", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + "status=yes,resizable=yes,scrollbars=yes");
        }

        function ReferModel(columnid) {
            var iWidth=1200;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("refertemplate.jsp?column=" + columnid + "&rightid=<%=rightid%>", "referModelWin", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status");
        }

        function opencopytemplate(columnid,templateID) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;

            if(templateID>0) {
                window.open("copytemplate.jsp?template=" + templateID + "&column=" + columnid, "", "width=956,height=600,left=5,top=5,status");
            }
        }

        function WebEdit(columnid) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("../webedit/index.jsp?column=" + columnid + "&right=4", "WebEdit", "width=850,height=600,left=5,top=5,scrollbars");
        }

        function CreateModel(columnid) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("createtemplate.jsp?column=" + columnid, "", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=yes,scrollbars=yes");
        }

        function UploadModel(columnid,cssjsdir) {
            var iWidth=1200;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            if (cssjsdir == 1)
                window.open("../upload/modelupload.jsp?column=" + columnid, "ModelUploadWin", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status");
            else
                window.open("../upload/modelupload2.jsp?column=" + columnid, "ModelUploadWin", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop);
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><a href="../index1.jsp" class="layui-logo">CMS-模板管理</a></div>
        <jsp:include page="../include/menu.jsp"></jsp:include>
    </div>

    <div class="layui-side layui-bg-black" id="sidetreeid" style="top: 60px">
        <div id="treebox" style="overflow-x:auto;">
            <ul id="mytree"></ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div id="contentid">

        </div>
    </div>
    <jsp:include page="../include/tail.jsp"></jsp:include>
</div>
<script>
    layui.use(['element', 'tree', 'layer'], function(){
        var layer = layui.layer,$ = layui.jquery;
        //添加选中颜色显示
        $("body").on("mousedown", ".layui-tree a", function() {
            if(!$(this).siblings('ul').length) {
                $(".layui-tree a").removeClass('active');
                $(this).find('cite').parent().addClass('active');
            }
        });

        var tree = layui.tree;
        var inst1 = tree.render({
            elem: '#mytree', //指定元素
            showLine:true,
            onlyIconControl:true,
            //skin: 'sidebar',
            skin: 'dx',
            target: '_blank', //是否新选项卡打开（比如节点返回href才有效）
            click: function(item){ //点击节点回调
                templatesInfoList(item.data.id);
                //layer.msg('当前节名称：'+ item.data.title + "==" + item.data.id + '<br>全部参数：'+ JSON.stringify(item));
                //console.log(item);
            },
            data:<%=buf.toString()%>
        });

        $("#treebox").css("height",document.documentElement.clientHeight-60);
    });
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/30
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.bizwink.cms.news.Column,
                 java.util.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String userid = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int sitetype = authToken.getSitetype();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    Tree colTree = null;
    String tbuf = null;

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
            node[] treeNodes = colTree.getAllNodes();
            if (colTree.getNodeNum()> 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<colTree.getNodeNum();ii++){
                    if (ii<colTree.getNodeNum()-1) {
                        if (treeNodes[ii].getLinkPointer()==0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\"},\r\n";
                    }else {
                        if (treeNodes[ii].getLinkPointer() == 0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\",url:\"#\"" + ",open:\"true\"" + "}\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
        } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
            //站点管理员
            colTree = TreeManager.getInstance().getSiteTree(siteid);
            node[] treeNodes = colTree.getAllNodes();
            if (colTree.getNodeNum()> 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<colTree.getNodeNum();ii++){
                    if (ii<colTree.getNodeNum()-1) {
                        if (treeNodes[ii].getLinkPointer()==0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\"},\r\n";
                    }else {
                        if (treeNodes[ii].getLinkPointer() == 0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\",url:\"#\"" + ",open:\"true\"" + "}\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
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
            if (clist.size() > 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<clist.size();ii++){
                    Column column = (Column)clist.get(ii);
                    if (ii<clist.size()-1) {
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"},\r\n";
                    }else{
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"#\"" + ",open:\"true\"" +"}\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
        } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
            //站点管理员
            colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
            node[] treeNodes = colTree.getAllNodes();
            if (colTree.getNodeNum()> 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<colTree.getNodeNum();ii++){
                    if (ii<colTree.getNodeNum()-1) {
                        if (treeNodes[ii].getLinkPointer()==0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\"},\r\n";
                    }else {
                        if (treeNodes[ii].getLinkPointer() == 0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\",url:\"#\"" + ",open:\"true\"" + "}\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
        }
    }
%>
<!DOCTYPE html>
<HTML>
<HEAD>
    <TITLE> ZTREE DEMO - checkbox</TITLE>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../css/zTree.css" type="text/css">
    <link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="../js/jquery.ztree.excheck-3.5.min.js"></script>
    <!--
    <script type="text/javascript" src="../../../js/jquery.ztree.exedit.js"></script>
    -->
    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        var zNodes = <%=tbuf%>;
        var code;

        function setCheck() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                py = $("#py").attr("checked")? "p":"",
                sy = $("#sy").attr("checked")? "s":"",
                pn = $("#pn").attr("checked")? "p":"",
                sn = $("#sn").attr("checked")? "s":"",
                type = { "Y":py + sy, "N":pn + sn};
            zTree.setting.check.chkboxType = type;
            showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
        }
        function showCode(str) {
            if (!code) code = $("#code");
            code.empty();
            code.append("<li>"+str+"</li>");
        }

        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            setCheck();
            $("#py").bind("change", setCheck);
            $("#sy").bind("change", setCheck);
            $("#pn").bind("change", setCheck);
            $("#sn").bind("change", setCheck);
        });

        function save() {
            alert("hello word");
            window.close();
        }
        //-->
    </SCRIPT>
</HEAD>

<BODY>
<h1>请选择发布的目标栏目</h1>
<div class="content_wrap">
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>

        <div align="center" style="margin-top: 20px;">
            <input type="button" name="save" value="确认" onclick="javascript:save();"/>
            <input type="button" name="cancel" value="返回" onclick="javascript:window.close();"/>
        </div>
    </div>
    <div class="right">
        <ul class="info">
            <li class="title">
                <ul class="list">
                    <li>
                        <p>
                            <input type="checkbox" id="py" class="checkbox first" checked /><!--span>关联父</span-->
                            <input type="checkbox" id="sy" class="checkbox first" checked /><!--span>关联子</span><br/-->
                            <input type="checkbox" id="pn" class="checkbox first" checked /><!--span>关联父</span-->
                            <input type="checkbox" id="sn" class="checkbox first" checked /><!--span>关联子</span><br/-->
                        </p>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
</BODY>
</HTML>
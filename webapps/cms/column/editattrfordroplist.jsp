<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int rowno = ParamUtil.getIntParameter(request, "rowno", 0);
    int type = ParamUtil.getIntParameter(request, "type", 0);
    int datatype = ParamUtil.getIntParameter(request, "datatype", 0);
    int columnid = ParamUtil.getIntParameter(request, "column", 0);
    String act = ParamUtil.getParameter(request, "act");
    int from = ParamUtil.getIntParameter(request, "from", 0);   //from=1来自首页

    System.out.println("rowno===" + rowno);
%>
<!DOCTYPE html>
<HTML>
<HEAD>
    <TITLE> 栏目信息分类属性定义 </TITLE>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <!--link rel="stylesheet" href="../css/demo.css" type="text/css"-->
    <link rel="stylesheet" href="../css/zTree.css" type="text/css">
    <link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
    <!--link rel="stylesheet" href="../css/bootstrapztree.css" type="text/css"-->

    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script type="text/javascript" src="../js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="../js/jquery.ztree.exedit-3.5.js"></script>
    <script type="text/javascript" src="../js/jquery.ztree.excheck-3.5.min.js"></script>
    <SCRIPT LANGUAGE="JavaScript">
        var zTreeObj;
        // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
        //var setting = {};
        var setting = {
            view: {
                addHoverDom: addHoverDom,            //当鼠标移动到节点上时，显示用户自定义控件
                removeHoverDom: removeHoverDom      //离开节点时的操作
            },
            edit: {
                enable: true, //单独设置为true时，可加载修改、删除图标
                editNameSelectAll: true,
                showRemoveBtn: true,
                showRenameBtn: true
            },
            check: {
                enable: true
            },
            edit: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true,
                    id : "id",       // 结点的id,对应到Json中的id
                    pId : "pId"      // 结点的pId,对应到Json中的parentId
                },
                key : {
                    name : "name" // 结点显示的name属性，对应到Json中的departName
                }
            },
            callback: {                              /**回调函数的设置，随便写了两个**/
                beforeEditName: beforeEditName,     //点击编辑时触发，用来判断该节点是否能编辑
                beforeRemove:beforeRemove,              //点击删除时触发，用来提示用户是否确定删除（可以根据返回值 true|false 确定是否可以删除）
                onRemove:onRemove,                   //(beforeRemove返回true之后可以进行onRemove)删除节点后触发，用户后台操作
                beforeRename:beforeRename,          //编辑结束时触发，用来验证输入的数据是否符合要求(也是根据返回值 true|false 确定是否可以编辑完成)
                onRename:onRename,                   //编辑后触发，用于操作后台
                beforeClick: beforeClick,
                onCheck: onCheck,
                //onNodeCreated:onNodeCreated,
                beforeEditName: beforeEditName,
                onClick: onClick
            }
        };

        // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
        $(document).ready(function(){
            var rowno = document.attrdroplist.rowno.value;
            var actval = document.attrdroplist.act.value;
            var cname = window.opener.attr.cname.value;
            var ename = window.opener.attr.ename.value;
            /*nodes1 = [
             {id:1, pId:0, name:'食品分类|1',level:0},{id:11, pId:1,
             name:'水果|11',level:1},{id:12, pId:1,
             name:'蔬菜|22',level:1}
             ];*/

            if (actval=="update") {
                var defval = window.opener.attr.droplist<%=rowno%>.value;
                if (defval!=='null' && defval!==null && defval!=='') {
                    //alert(defval);
                    var nodes = eval('(' + defval + ')');
                    if (nodes.type == 0) {     //单选下拉列表
                        $("input[name='listtype']:eq(0)").attr("checked",'checked');
                    } else {                   //多选下拉列表
                        $("input[name='listtype']:eq(1)").attr("checked",'checked');
                    }
                    zTreeObj = $.fn.zTree.init($("#zztree"), setting, nodes.data);
                } else {
                    ename = ename.substring(1);
                    nodes = [{id:1,pId:0,name:cname+"|" + ename}];
                    zTreeObj = $.fn.zTree.init($("#zztree"), setting, nodes);
                }
            } else {
                nodes = [{id:1,pId:0,name:cname+"|" + ename}];
                zTreeObj = $.fn.zTree.init($("#zztree"), setting, nodes);
            }
        });

        function beforeClick(treeId, treeNode) {
            //alert(treeNode.id);
            return true;
        }

        function onCheck(e, treeId, treeNode) {
            alert("onCheck");
        }

        function onClick(event,treeId,treeNode) {
            //alert(treeNode.id);
        }

        function beforeEditName(event,treeId,treeNode) {
            alert(treeNode.id + "before edit");
        }

        function beforeEditName(treeId,treeNode){
            /* if(treeNode.isParent){
             alert("不准编辑非叶子节点！");
             return false;
             } */
            return true;
        }

        function beforeRemove(e,treeId,treeNode){
            return confirm("你确定要删除吗？");
        }
        function onRemove(e,treeId,treeNode){
            if(treeNode.isParent){
                var childNodes = zTree.removeChildNodes(treeNode);
                var paramsArray = new Array();
                for(var i = 0; i < childNodes.length; i++){
                    paramsArray.push(childNodes[i].id);
                }
                alert("删除父节点的id为："+treeNode.id+"\r\n他的孩子节点有："+paramsArray.join(","));
                return;
            }
            alert("你点击要删除的节点的名称为："+treeNode.name+"\r\n"+"节点id为："+treeNode.id);
        }


        function beforeRename(treeId,treeNode,newName,isCancel){
            if(newName.length < 3){
                alert("名称不能少于3个字符！");
                return false;
            }
            return true;
        }

        function onRename(e,treeId,treeNode,isCancel){
            alert("修改节点的id为："+treeNode.id+"\n修改后的名称为："+treeNode.name);
        }

        function addHoverDom(treeId, treeNode) {
            var aObj = $("#" + treeNode.tId + "_a");
            if ($("#diyBtn_"+treeNode.id).length>0) return;
            var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' > </span>"
                    + "<button type='button' class='diyBtn1' id='diyBtn_" + treeNode.id
                    + "' title='"+treeNode.name+"' onfocus='this.blur();'></button>";
            aObj.append(editStr);
            var btn = $("#diyBtn_"+treeNode.id);
            if (btn) btn.bind("click", function(){
                alert("diy Button for " + treeNode.name);
            });
        };

        function removeHoverDom(treeId, treeNode) {
            $("#diyBtn_"+treeNode.id).unbind().remove();
            $("#diyBtn_space_" +treeNode.id).unbind().remove();
        };

        /**
         * 判断是否null
         * @param data
         */
        function isNull(data){
            return (data == "" || data == undefined || data == null)?1:0;
        }

        function SaveKeyVal() {
            var text = document.attrdroplist.key.value;
            var value = document.attrdroplist.val.value;
            //获取ztree对象
            var zTree = $.fn.zTree.getZTreeObj("zztree");
            //获取对象树的根节点
            var zNode = zTree.getNodes();
            //获取对象树的所有节点
            var nodes = zTree.transformToArray(zNode);
            var max_nodeid = 0;
            for (var ii=0;ii<nodes.length;ii++) {
                if (parseInt(nodes[ii].id,10) > parseInt(max_nodeid,10)) {
                    max_nodeid = nodes[ii].id;
                }
            }
            var newID = parseInt(max_nodeid,10) + parseInt(1);
            var parentNodes = zTree.getSelectedNodes();
            zTree.addNodes(parentNodes[0], {id:newID, pId:parentNodes[0].id, name:text + "|" + value}); //页面上添加节点
            var node = zTree.getNodeByParam("id", newID, null); //根据新的id找到新添加的节点
            //zTree.selectNode(node); //让新添加的节点处于选中状态
            document.attrdroplist.key.value = "";
            document.attrdroplist.val.value = "";
        }

        function Save() {
            var cname = window.opener.attr.cname.value;
            var ename = window.opener.attr.ename.value;
            var actval = document.attrdroplist.act.value;
            var listtypeval = $('input[name="listtype"]:checked').val();
            //获取ztree对象
            var zTree = $.fn.zTree.getZTreeObj("zztree");
            //获取对象树的根节点
            var zNode = zTree.getNodes();
            //获取对象树的所有节点
            var nodes = zTree.transformToArray(zNode);
            var treebuf = "{type:" + listtypeval + ",'data':[";
            for (var ii=0;ii<nodes.length;ii++) {
                if (nodes.length == 1)
                    treebuf = treebuf + "{id:" + nodes[ii].id  + ",pId:0,name:'" + nodes[ii].name + "',level:" + nodes[ii].level + "}\r\n";
                else if (parseInt(ii,10)<(parseInt(nodes.length,10)-parseInt(1,10)))
                    if (nodes[ii].pId == null)
                        treebuf = treebuf + "{id:" + nodes[ii].id + ",pId:0,name:'" + nodes[ii].name + "',level:" + nodes[ii].level + "},\r\n";
                    else
                        treebuf = treebuf + "{id:" + nodes[ii].id + ",pId:" + nodes[ii].pId + ",name:'" + nodes[ii].name + "',level:" + nodes[ii].level + "},\r\n";
                else
                    treebuf = treebuf + "{id:" + nodes[ii].id + ",pId:" + nodes[ii].pId + ",name:'" + nodes[ii].name + "',level:" + nodes[ii].level + "}\r\n";
            }
            treebuf = treebuf + "]}";
            if (actval=="update") {
                window.opener.attr.droplist<%=rowno%>.value= treebuf;
                var theDroplist= window.opener.document.getElementById("selid<%=rowno%>");
                theDroplist.options.length=0;
                for(var ii=0; ii<nodes.length; ii++){
                    var op=window.opener.document.createElement("option");                       // 新建OPTION (op)
                    op.setAttribute("value",nodes[ii].id);                                         // 设置OPTION的 VALUE
                    op.appendChild(window.opener.document.createTextNode(nodes[ii].name));        // 设置OPTION的 TEXT
                    theDroplist.appendChild(op);                                                    // 为SELECT 新建一 OPTION(op)
                }
                theDroplist.focus();
            } else {
                window.opener.attr.droplist.value=treebuf;
            }
            window.close();
        }
    </SCRIPT>
</HEAD>
<BODY>
<form name="attrdroplist">
    <input type="hidden" name="act" value="<%=act%>">
    <input type="hidden" name="columnid" value="<%=columnid%>">
    <input type="hidden" name="rowno" value="<%=rowno%>">
    <table border="0" width="100%">
        <tr height=40>
            <td align=center>&nbsp;</td>
        </tr>
        <tr>
            <td align="center">
                名称：<input type="text" name="key" value="">
                数值：<input type="text" name="val" value="">
                <input type="button" value="增加" name="add" class=tine onclick="SaveKeyVal();">
            </td>
        </tr>
        <tr>
            <td align="center">
                列表类型：<input type="radio" name="listtype" value="0" checked>单选&nbsp;&nbsp;&nbsp;&nbsp;
                          <input type="radio" name="listtype" value="1">多选
            </td>
        </tr>
        <tr>
            <td align="center">
                <!--select name="maincls" id="mainclsid" style="width:300px;height:300px;" multiple ondblclick="javascript:addSubclasses();"></select-->
                <div style="width: 500px;">
                    <ul id="zztree" class="ztree" style="width: 450px;"></ul>
                </div>
            </td>
        </tr>
        <tr height=40>
            <td align=center>
                <input type="button" value="   保存   " name="save" class=tine onclick="Save();">&nbsp;&nbsp;
                <input type="button" value="   关闭   " name="close" class=tine onclick="window.close();">
            </td>
        </tr>
    </table>
</form>

</BODY>
</HTML>
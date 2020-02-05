<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.tree.*"
        contentType="text/html;charset=gbk"
        %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>

<%
    ITree treeMgr = TreeManager.getInstance();
    Tree colTree = treeMgr.getSiteTree(28);

    StringBuffer buf = new StringBuffer();                        //存储生成的菜单树
    if (colTree.getNodeNum() > 1) {
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

        buf.append("        var zNodes =[\r\n");

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
            int current_nodeid = 0;
            for (i = 0; i < colTree.getNodeNum(); i++) {
                if (treeNodes[i].getLinkPointer() == currentID) {
                    nodenum = nodenum + 1;
                    pid[nodenum] = treeNodes[i].getId();
                    parentMenuName[nodenum] = menuName;
                    ordernum[nodenum] = subflag;
                    subflag = subflag + 1;
                }

                if (treeNodes[i].getId() == currentID) current_nodeid = i;
            }

            //如果当前节点有子节点，生成当前节点的菜单
            if (subflag != 0) {
                buf.append("{ name:\"" + treeNodes[current_nodeid].getChName() + " - 展开\", open:true,\r\n");
                buf.append("children: [\r\n");
            } else {
                buf.append("{ name:\"" + treeNodes[current_nodeid].getChName() + "\"},\r\n");
            }

            //for (i = 0; i < colTree.getNodeNum(); i++) {
            //    if (treeNodes[i].getLinkPointer() == currentID) {
            //        String name = "<span class=line id=href" + treeNodes[i].getId() + ">" + StringUtil.gb2iso4View(treeNodes[i].getChName()) + "</span>";
            //        buf.append(menuName).append(".MTMAddItem(new MTMenuItem(\"");
            //        buf.append(name).append("\",\"javascript:go(" + treeNodes[i].getId() + "); oncontextmenu=showmenuie5(" + treeNodes[i].getId() + ");\",\"cmsleft\"));");
            //        buf.append("\n");
            //    }
            //}

            //关联当前菜单和他的父菜单
            //if (subflag != 0 && currentID != 0)
            //    buf.append(parentMenu + ".items[" + orderNumber + "].MTMakeSubmenu(" + menuName + ");\r\n");
        } while (nodenum >= 1);
        //直到pid数组中没有待处理的节点为止

        System.out.println(buf.toString());
    }
%>

<!DOCTYPE html>
<HTML>
<HEAD>
    <TITLE> ZTREE DEMO - Standard Data </TITLE>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="style/zTree.css" type="text/css">
    <link rel="stylesheet" href="style/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="js/jquery.ztree.core-3.5.js"></script>
    <!--  <----script type="text/javascript" src="js/jquery.ztree.excheck-3.5.js"></script>
       <script type="text/javascript" src="js/jquery.ztree.exedit-3.5.js"></script>-->
    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            data: {
                key: {
                    title:"t"
                },
                simpleData: {
                    enable: true
                }
            },
            callback: {
                beforeClick: beforeClick,
                onClick: onClick
            }
        };

        /*var zNodes =[
            { name:"父节点1 - 展开", open:true,
                children: [
                    { name:"父节点11 - 折叠",
                        children: [
                            { name:"叶子节点111"},
                            { name:"叶子节点112"},
                            { name:"叶子节点113"},
                            { name:"叶子节点114"}
                        ]},
                    { name:"父节点12 - 折叠",
                        children: [
                            { name:"叶子节点121"},
                            { name:"叶子节点122"},
                            { name:"叶子节点123"},
                            { name:"叶子节点124"}
                        ]},
                    { name:"父节点13 - 没有子节点", isParent:true}
                ]},
            { name:"父节点2 - 折叠",
                children: [
                    { name:"父节点21 - 展开", open:true,
                        children: [
                            { name:"叶子节点211"},
                            { name:"叶子节点212"},
                            { name:"叶子节点213"},
                            { name:"叶子节点214"}
                        ]},
                    { name:"父节点22 - 折叠",
                        children: [
                            { name:"叶子节点221"},
                            { name:"叶子节点222"},
                            { name:"叶子节点223"},
                            { name:"叶子节点224"}
                        ]},
                    { name:"父节点23 - 折叠",
                        children: [
                            { name:"叶子节点231",t:"唉，随便点我吧"},
                            { name:"叶子节点232",t:"唉，随便点我吧"},
                            { name:"叶子节点233",t:"唉，随便点我吧"},
                            { name:"叶子节点234",t:"唉，随便点我吧"}
                        ]}
                ]},
            { name:"父节点3 - 没有子节点", isParent:true}

        ];*/


        var zNodes =[
            { name:"petersong.coosite.com - 展开", open:true,
                 children: [
                     { name:"hello"},
                     { name:"互动专区"},
                     { name:"ok关于注采井距对油井受放的影响"},
                     { name:"箱包天地"},
                     { name:"世界杯花絮"},
                     { name:"电影简介"},
                     { name:"体育"},
                     { name:"企业网站建设"},
                     { name:"新闻"},
                     { name:"网站推广"},
                     { name:"人物"},
                     { name:"风景"},
                     { name:"我的收藏 - 展开", open:true,
                         children: [
                           { name:"iphone开发"},
                           { name:"电子邮件"},
                           { name:"工作流"},
                           { name:"物流系统"},
                           { name:"fckeditor"},
                           { name:"加密算法"},
                           { name:"vb.net编程"},
                           { name:"moss2007"},
                           { name:"msoffice"},
                           { name:"mfc"},
                           { name:"flash技术"},
                           { name:"协议规范"},
                           { name:"WebService"},
                           { name:"Office文档处理"},
                           { name:"linux技术"},
                           { name:"系统安全"},
                           { name:"图象处理"},
                           { name:"java调优"},
                           { name:"Cache技术"},
                           { name:"内容管理系统"},
                           { name:"mysql"},
                           { name:"oracle技术"},
                           { name:"关于多媒体文件处理"},
                           { name:"生活点滴"}
                     ]}
        ]}
        ];

        var log, className = "dark";
        function beforeClick(treeId, treeNode, clickFlag) {
            className = (className === "dark" ? "":"dark");
            showLog("[ "+getTime()+" beforeClick ]&nbsp;&nbsp;" + treeNode.name );
            return (treeNode.click != false);
        }

        function onClick(event, treeId, treeNode, clickFlag) {
            alert("[ "+getTime()+" beforeClick ]&nbsp;&nbsp;" + treeNode.name);
            //showLog("[ "+getTime()+" onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");
        }

        function showLog(str) {
            if (!log) log = $("#log");
            log.append("<li class='"+className+"'>"+str+"</li>");
            if(log.children("li").length > 8) {
                log.get(0).removeChild(log.children("li")[0]);
            }
        }

        function getTime() {
            var now= new Date(),
                    h=now.getHours(),
                    m=now.getMinutes(),
                    s=now.getSeconds();
            return (h+":"+m+":"+s);
        }

        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        });
        //-->
    </SCRIPT>
</HEAD>

<BODY>
<h1>最简单的树 -- 标准 JSON 数据</h1>
<h6>[ 文件路径: core/standardData.html ]</h6>
<div class="content_wrap">
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
    <div class="right">
        <ul class="info">
            <li class="title"><h2>1、setting 配置信息说明</h2>
                <ul class="list">
                    <li class="highlight_red">普通使用，无必须设置的参数</li>
                    <li>与显示相关的内容请参考 API 文档中 setting.view 内的配置信息</li>
                    <li>name、children、title 等属性定义更改请参考 API 文档中 setting.data.key 内的配置信息</li>
                </ul>
            </li>
            <li class="title"><h2>2、treeNode 节点数据说明</h2>
                <ul class="list">
                    <li class="highlight_red">标准的 JSON 数据需要嵌套表示节点的父子包含关系
                        <div><pre xmlns=""><code>例如：
                            var nodes = [
                            {name: "父节点1", children: [
                            {name: "子节点1"},
                            {name: "子节点2"}
                            ]}
                            ];</code></pre></div>
                    </li>
                    <li>默认展开的节点，请设置 treeNode.open 属性</li>
                    <li>无子节点的父节点，请设置 treeNode.isParent 属性</li>
                    <li>其他属性说明请参考 API 文档中 "treeNode 节点数据详解"</li>
                </ul>
            </li>
        </ul>
    </div>
</div>
</BODY>
</HTML>
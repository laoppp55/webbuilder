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
    String sitename = authToken.getSitename();
    int samsiteid = authToken.getSamSiteid();
    String userid = authToken.getUserID();
    int sitetype = authToken.getSitetype();
    int columnID = ParamUtil.getIntParameter(request,"column",0);
    int currentPage = ParamUtil.getIntParameter(request,"start",1);
    int range = ParamUtil.getIntParameter(request,"range",0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");
    int flag = ParamUtil.getIntParameter(request,"flag",0);
    int ascdesc = ParamUtil.getIntParameter(request,"ascdesc",0);
    String from = ParamUtil.getParameter(request,"from");
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
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
    <title>网站内容管理系统--文章管理</title>
    <link rel="stylesheet" href="../css/cms_css.css">
    <link rel="stylesheet" href="../design/css/layui.css">
    <link rel="stylesheet" href="../css/jquery.msgbox.css" type="text/css" />
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/jquery.msgbox.min.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>

    <!--引用xtree-->
    <script type="text/javascript" src="../design/layui-xtree.js"></script>
    <script>
        $(document).ready(function(){
            var columnid = <%=columnID%>;
            var currentPage = <%=currentPage%>;
            var doSearchFlag = <%=doSearchFlag%>;
            var flag = <%=flag%>;
            var ascdesc = <%=ascdesc%>;
            if (columnid>0) articlesInfoList(columnid,currentPage,doSearchFlag,flag,ascdesc,null,null,null,null);
        });

        function goSearchPage(columnid,doSearchFlag,flag,ascdesc,auditflag,pubflag,mediatype,arttypeval) {
            var currentPage = form.turnPage.value;
            articlesInfoList(columnid,currentPage,doSearchFlag,flag,ascdesc,auditflag,pubflag,mediatype,arttypeval);
            form.turnPage.value = currentPage;
        }

        function Pagination(totalArticleNum,columnid,totalpages,curentpage,pageNumLength,doSearchFlag,flag,ascdesc,auditflag,pubflag,mediatype,arttypeval) {
            var PaginationDiv = "";
            if (totalpages <= 1)
                PaginationDiv = "<div class=\"page\" style=\"display: none\">";
            else
                PaginationDiv = "<div class=\"page\" style=\"display: block\">";

            if (curentpage<=1)
                PaginationDiv = PaginationDiv + "<span class=\"txtl\">当前栏目的文章总数：" + totalArticleNum + "</span><span>第1页</span>";
            else {
                PaginationDiv = PaginationDiv + "<span class=\"txtl\">当前栏目的文章总数：" + totalArticleNum + "</span><a href=\"javascript:articlesInfoList(" + columnid + ",1," + flag + "," + ascdesc + ","+ auditflag + "," + pubflag + "," + mediatype + "," + arttypeval + ");\">第1页</a>";
            }

            PaginationDiv = PaginationDiv + "<span>共" + totalpages + "页</span>";

            if (curentpage<=1)
                PaginationDiv = PaginationDiv + "<span>上一页</span>";
            else {
                PaginationDiv = PaginationDiv + "<a href=\"javascript:articlesInfoList(" + columnid + "," + (curentpage - 1) + ","+ doSearchFlag + "," + flag + "," + ascdesc + ","+ auditflag + "," + pubflag + "," + mediatype + "," + arttypeval + ");\">上一页</a>";
            }

            var half_pageNumLength = parseInt(pageNumLength/2);
            var startPageNum=0;
            var endPageNum = 0;
            if (curentpage<=half_pageNumLength)
                startPageNum=0;
            else {
                startPageNum = parseInt(curentpage) - parseInt(half_pageNumLength);
                if (startPageNum<=0) startPageNum = 0;
            }

            if (curentpage<half_pageNumLength) {
                if (totalpages <= pageNumLength)
                    endPageNum = totalpages;
                else
                    endPageNum = pageNumLength;
            } else {
                if (totalpages <= pageNumLength)
                    endPageNum = totalpages;
                else {
                    endPageNum = parseInt(curentpage) + parseInt(half_pageNumLength);
                    if (endPageNum>totalpages) endPageNum = totalpages;
                }
            }

            for(var ii=startPageNum;ii<endPageNum;ii++) {
                if ((ii + 1) === curentpage)
                    PaginationDiv = PaginationDiv + "<span class=\"cur\">" + curentpage + "</span>";
                else {
                    PaginationDiv = PaginationDiv + "<a href=\"javascript:articlesInfoList(" + columnid + "," + (ii + 1) + "," + doSearchFlag + "," + flag + "," + ascdesc + ","+ auditflag + "," + pubflag + "," + mediatype + "," + arttypeval + ");\">" + (ii + 1) + "</a>";
                }
            }

            if (curentpage<totalpages)
                PaginationDiv = PaginationDiv + "<a href=\"javascript:articlesInfoList(" + columnid + "," + (curentpage+1) + "," + doSearchFlag + "," + flag + "," + ascdesc + ","+ auditflag + "," + pubflag + "," + mediatype + "," + arttypeval + ");\">下一页</a>";
            else
                PaginationDiv = PaginationDiv + "<span>下一页</span>";

            PaginationDiv = PaginationDiv + "<span class=\"txtl\">转到第</span>";
            PaginationDiv = PaginationDiv + "<span class=\"select-pager\">";
            PaginationDiv = PaginationDiv + "<form name=\"form\">";

            PaginationDiv = PaginationDiv + "<select name=\"turnPage\" size=\"1\" onchange=\"javascript:goSearchPage(" + columnid + ",'" + doSearchFlag + "'," + flag + "," + ascdesc + ","+ auditflag + "," + pubflag + "," + mediatype + "," + arttypeval + ");\">";

            for(var ii=0;ii<totalpages;ii++) {
                if ((ii+1) === curentpage)
                    PaginationDiv = PaginationDiv + "<option value=\"" + (ii+1) + "\" selected>" + (ii+1) +  "</option>";
                else
                    PaginationDiv = PaginationDiv + "<option value=\"" + (ii+1) + "\">" + (ii+1) + "</option>";
            }

            PaginationDiv = PaginationDiv + "</select>";
            PaginationDiv = PaginationDiv + "</form>";
            PaginationDiv = PaginationDiv + "</span>";
            PaginationDiv = PaginationDiv + "<span class=\"txtr\">页</span>";
            PaginationDiv = PaginationDiv + "</div>";

            return PaginationDiv;
        }

        //columnid:获取该栏目的文章列表
        //currentPage:当前页号
        //doSearchFlag：是否执行检索功能
        //flag:设置排序字段
        //ascdesc：升序排列还是降序排列，0升序排列，1降序排列
        //auditflag：文章审核状态
        //pubflag：文章是否被发布
        //mediatype：多媒体文件
        //arttypeval：获取那些状态的文章，0：任何状态，1：新高，2：归档，3：等待多媒体文件转换，4：商品促销，5：新品商品，6：活动促销
        function articlesInfoList(columnid,currentPage,doSearchFlag,flag,ascdesc,auditflag,pubflag,mediatype,arttypeval) {
            var range = 20;
            var title = "";
            var articleid = "";
            var vicetitle = "";
            var author = "";
            var source = "";
            var spubdate = "";
            var epubdate = "";

            if (doSearchFlag) {
                title = searchForm.title.value;
                articleid = searchForm.articleid.value;
                vicetitle = searchForm.vicetitle.value;
                author = searchForm.author.value;
                source = searchForm.source.value;
                spubdate = searchForm.spubdate.value;
                epubdate = searchForm.epubdate.value;
                arttypeval = searchForm.arttype.value;
                columnid = searchForm.column.value;
            }

            var rightid=searchForm.right.value;
            htmlobj=$.ajax({
                url:"../article/articlesnew.jsp",
                data:{
                    doSearch:doSearchFlag,
                    page:currentPage,
                    title:encodeURIComponent(title),
                    articleid:articleid,
                    vicetitle:encodeURIComponent(vicetitle),
                    author:encodeURIComponent(author),
                    source:encodeURIComponent(source),
                    spubdate:encodeURIComponent(spubdate),
                    epubdate:encodeURIComponent(epubdate),
                    arttype:arttypeval,
                    column:columnid,
                    range:range,
                    flag:flag,
                    ascdesc:ascdesc,
                    audit:auditflag,            //查询待审稿件和退回的稿件
                    pubflag:pubflag,
                    mediatype:mediatype,
                    right:rightid
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    var writeRight = jsondata.WRITERIGHT;
                    var publishRight = jsondata.PUBLISHRIGHT;
                    var publishMyRight = jsondata.PUBLISHMYRIGHT;
                    var adminRight = jsondata.ADMINRIGHT;
                    var deptWriteRight = jsondata.DEPTWRITERIGHT;
                    var deptReadRight = jsondata.DEPTREADRIGHT;
                    var deptDeleteRight = jsondata.DEPTDELETERIGHT;
                    var deptPublishRight = jsondata.DEPTPULISHRIGHT;
                    var searchflag = jsondata.DOSEARCH;
                    var articleIds = "";
                    range = jsondata.RANGE;
                    var extra = jsondata.TOTALCOUNT % range;
                    var totalPage = 0;
                    if (extra>0)
                        totalPage = parseInt(jsondata.TOTALCOUNT/range) + 1;
                    else
                        totalPage = jsondata.TOTALCOUNT/range;
                    var ascDescFlag = 0;
                    if (jsondata.ASCDESC === 0)
                        ascDescFlag = 1;                    //升序排列
                    else
                        ascDescFlag = 0;                    //降序排列

                    if (jsondata.ARTICLEID == 0)            //按文章ID排序
                        articleid="";
                    else
                        articleid=jsondata.ARTICLEID;

                    if (typeof(jsondata.TITLE) == "undefined")    //按文章标题排序
                        title = "";
                    else
                        title = jsondata.TITLE;

                    if (typeof(jsondata.VICETITLE) == "undefined")     //按文章副标题排序
                        vicetitle = "";
                    else
                        vicetitle = jsondata.VICETITLE;

                    if (typeof(jsondata.AUTHOR) == "undefined")      //按文章作者排序
                        author = "";
                    else
                        author = jsondata.AUTHOR;

                    if (typeof(jsondata.SOURCE) == "undefined")       //按文章来源排序
                        source = "";
                    else
                        source = jsondata.SOURCE;

                    $("#arttypeid option").remove();
                    //var arttypeval = $("#arttypeid").val();
                    var arttypeval = jsondata.ARTTYPE;
                    $("#arttypeid").val(arttypeval);
                    if (arttypeval===100) {
                        $("#arttypeid").append("<option value=-1>请选择</option>");
                        $("#arttypeid").append("<option value=100 selected>新稿</option>");
                        $("#arttypeid").append("<option value=101>已发布</option>");
                        $("#arttypeid").append("<option value=102>发布中</option>");
                        $("#arttypeid").append("<option value=103>未用</option>");
                        $("#arttypeid").append("<option value=104>归档</option>");
                        $("#arttypeid").append("<option value=105>待转换</option>");
                        $("#arttypeid").append("<option value=106>在审</option>");
                        $("#arttypeid").append("<option value=107>退稿</option>");
                    } else if (arttypeval===101) {
                        $("#arttypeid").append("<option value=-1>请选择</option>");
                        $("#arttypeid").append("<option value=100>新稿</option>");
                        $("#arttypeid").append("<option value=101 selected>已发布</option>");
                        $("#arttypeid").append("<option value=102>发布中</option>");
                        $("#arttypeid").append("<option value=103>未用</option>");
                        $("#arttypeid").append("<option value=104>归档</option>");
                        $("#arttypeid").append("<option value=105>待转换</option>");
                        $("#arttypeid").append("<option value=106>在审</option>");
                        $("#arttypeid").append("<option value=107>退稿</option>");
                    } else if (arttypeval===102) {
                        $("#arttypeid").append("<option value=-1>请选择</option>");
                        $("#arttypeid").append("<option value=100>新稿</option>");
                        $("#arttypeid").append("<option value=101>已发布</option>");
                        $("#arttypeid").append("<option value=102 selected>发布中</option>");
                        $("#arttypeid").append("<option value=103>未用</option>");
                        $("#arttypeid").append("<option value=104>归档</option>");
                        $("#arttypeid").append("<option value=105>待转换</option>");
                        $("#arttypeid").append("<option value=106>在审</option>");
                        $("#arttypeid").append("<option value=107>退稿</option>");
                    } else if (arttypeval===103) {
                        $("#arttypeid").append("<option value=-1>请选择</option>");
                        $("#arttypeid").append("<option value=100>新稿</option>");
                        $("#arttypeid").append("<option value=101>已发布</option>");
                        $("#arttypeid").append("<option value=102>发布中</option>");
                        $("#arttypeid").append("<option value=103 selected>未用</option>");
                        $("#arttypeid").append("<option value=104>归档</option>");
                        $("#arttypeid").append("<option value=105>待转换</option>");
                        $("#arttypeid").append("<option value=106>在审</option>");
                        $("#arttypeid").append("<option value=107>退稿</option>");
                    } else if (arttypeval===104) {
                        $("#arttypeid").append("<option value=-1>请选择</option>");
                        $("#arttypeid").append("<option value=100>新稿</option>");
                        $("#arttypeid").append("<option value=101>已发布</option>");
                        $("#arttypeid").append("<option value=102>发布中</option>");
                        $("#arttypeid").append("<option value=103>未用</option>");
                        $("#arttypeid").append("<option value=104 selected>归档</option>");
                        $("#arttypeid").append("<option value=105>待转换</option>");
                        $("#arttypeid").append("<option value=106>在审</option>");
                        $("#arttypeid").append("<option value=107>退稿</option>");
                    } else if (arttypeval===105) {
                        $("#arttypeid").append("<option value=-1>请选择</option>");
                        $("#arttypeid").append("<option value=100>新稿</option>");
                        $("#arttypeid").append("<option value=101>已发布</option>");
                        $("#arttypeid").append("<option value=102>发布中</option>");
                        $("#arttypeid").append("<option value=103>未用</option>");
                        $("#arttypeid").append("<option value=104>归档</option>");
                        $("#arttypeid").append("<option value=105 selected>待转换</option>");
                        $("#arttypeid").append("<option value=106>在审</option>");
                        $("#arttypeid").append("<option value=107>退稿</option>");
                    } else if (arttypeval===106) {
                        $("#arttypeid").append("<option value=-1>请选择</option>");
                        $("#arttypeid").append("<option value=100>新稿</option>");
                        $("#arttypeid").append("<option value=101>已发布</option>");
                        $("#arttypeid").append("<option value=102>发布中</option>");
                        $("#arttypeid").append("<option value=103>未用</option>");
                        $("#arttypeid").append("<option value=104>归档</option>");
                        $("#arttypeid").append("<option value=105>待转换</option>");
                        $("#arttypeid").append("<option value=106 selected>在审</option>");
                        $("#arttypeid").append("<option value=107>退稿</option>");
                    } else if (arttypeval===107) {
                        $("#arttypeid").append("<option value=-1>请选择</option>");
                        $("#arttypeid").append("<option value=100>新稿</option>");
                        $("#arttypeid").append("<option value=101>已发布</option>");
                        $("#arttypeid").append("<option value=102>发布中</option>");
                        $("#arttypeid").append("<option value=103>未用</option>");
                        $("#arttypeid").append("<option value=104>归档</option>");
                        $("#arttypeid").append("<option value=105>待转换</option>");
                        $("#arttypeid").append("<option value=106>在审</option>");
                        $("#arttypeid").append("<option value=107 selected>退稿</option>");
                    } else{
                        $("#arttypeid").append("<option value=-1 selected>请选择</option>");
                        $("#arttypeid").append("<option value=100>新稿</option>");
                        $("#arttypeid").append("<option value=101>已发布</option>");
                        $("#arttypeid").append("<option value=102>发布中</option>");
                        $("#arttypeid").append("<option value=103>未用</option>");
                        $("#arttypeid").append("<option value=104>归档</option>");
                        $("#arttypeid").append("<option value=105>待转换</option>");
                        $("#arttypeid").append("<option value=106>在审</option>");
                        $("#arttypeid").append("<option value=107>退稿</option>");
                    }

                    var column_name = jsondata.COLUMNNAME;
                    $("#column_nameid").html("文章管理 >" + column_name);

                    var htmldata = "<table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n";
                    htmldata = htmldata + "    <tr>\n" +
                        "        <td align=\"left\" valign=\"top\">\n" +
                        "          <form name=form1>" +
                        "            <table id=\"articleDataGrid\" width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#CCCCCC\" class=\"tab_list\">\n" +
                        "                <tr>\n" +
                        "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><input type=\"checkbox\" name=\"allbox\" id=\"allboxid\" onclick='javascript:CA();' />选择</td>\n" +
                        "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">状态</td>\n" +
                        "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">RSS</td>\n" +
                        "                    <td width=\"270\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag +  ",1," + ascDescFlag + ",null,null,null,null);\">文章标题</a></td>\n" +
                        "                    <td width=\"120\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",4," + ascDescFlag + ",null,null,null,null);\">发布时间</a></td>\n" +
                        "                    <td width=\"120\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",5," + ascDescFlag + ",null,null,null,null);\">修改时间</a></td>\n" +
                        "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",2," + ascDescFlag + ",null,null,null,null);\">主权重</a></td>\n" +
                        "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",3," + ascDescFlag + ",null,null,null,null);\">次权重</a></td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">文章编辑</td>\n" +
                        "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">预览</td>\n" +
                        "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">修改</td>\n" +
                        "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">删除</td>\n" +
                        //"                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">头条</td>\n" +
                        "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">推送</td>\n" +
                        "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">发布</td>\n" +
                        "                </tr>\n";
                    for(var ii=0;ii<jsondata.ARTICLES.length;ii++) {
                        var maintitle = jsondata.ARTICLES[ii].MAINTITLE;
                        var articleColumnID = jsondata.ARTICLES[ii].COLUMNID;
                        var editor = (typeof(jsondata.ARTICLES[ii].EDITOR)!="undefined")?jsondata.ARTICLES[ii].EDITOR:"";
                        var createdate = (typeof(jsondata.ARTICLES[ii].CREATEDATE)!="undefined")?jsondata.ARTICLES[ii].CREATEDATE:"";
                        var publishtime = (typeof(jsondata.ARTICLES[ii].PUBLISHTIME)!="undefined")?jsondata.ARTICLES[ii].PUBLISHTIME:"";
                        var lastupdate = (typeof(jsondata.ARTICLES[ii].LASTUPDATED)!="undefined")?jsondata.ARTICLES[ii].LASTUPDATED:"";
                        var main_weight = jsondata.ARTICLES[ii].DOCLEVEL;
                        var vice_weight = jsondata.ARTICLES[ii].VICEDOCLEVEL;
                        var emptyflag = jsondata.ARTICLES[ii].EMPTYCONTENTFLAG;
                        var status = jsondata.ARTICLES[ii].STATUS;
                        var lockflag = jsondata.ARTICLES[ii].LOCKSTATUS;
                        var pubflag = jsondata.ARTICLES[ii].PUBFLAG;
                        var auditflag = jsondata.ARTICLES[ii].AUDITFLAG;
                        var mediatype = jsondata.ARTICLES[ii].MULTIMEDIATYPE;
                        var isrssflag = jsondata.ARTICLES[ii].ISJOINRSS;
                        var isown = jsondata.ARTICLES[ii].isown;
                        var referID = jsondata.ARTICLES[ii].REFERID;
                        var refersFrom = jsondata.ARTICLES[ii].RefersFrom;
                        var refersTo = jsondata.ARTICLES[ii].RefersTo;
                        var useArtType = jsondata.ARTICLES[ii].useArticleType;
                        var status_str = null;
                        var rownum = ii;

                        articleIds = articleIds + jsondata.ARTICLES[ii].ID + ",";

                        var filename = jsondata.ARTICLES[ii].FILENAME;
                        var ext = null;
                        if (filename != null) {
                            var posi = filename.indexOf(".");
                            if(posi>-1) {
                                ext = filename.substring(posi+1);
                                ext = ext.toLowerCase();
                            }
                        }
                        if (ext != null) {
                            if (ext=="pdf")
                                ext = "pdf.gif";
                            else if (ext=="doc")
                                ext = "word.gif";
                            else if (ext=="xls")
                                ext = "excel.gif";
                            else if (ext=="zip" || ext=="rar" || ext=="tar")
                                ext = "zip.gif";
                            else if (ext=="gif")
                                ext = "gif.gif";
                            else if (ext=="jpg" || ext=="jpeg")
                                ext = "jpg.gif";
                            else if (ext=="html" || ext=="htm" || ext=="shtml" || ext=="jsp" || ext=="asp" || ext=="php" || ext=="aspx")
                                ext = "htm.gif";
                            else if (ext=="ppt")
                                ext = "ppt.gif";
                            else if (ext=="png" || ext=="bmp")
                                ext = "tu.gif";
                            else
                                ext = "wen.gif";
                        } else {
                            ext = "wen.gif";
                        }

                        if (emptyflag == 0) {
                            if (status == 0) {
                                status_str = "<td align=center><img src=\"../images/button/wei.gif\" alt=\"未使用文章\"></td>";
                            } else if (status == 1 || status == 5 || status == 4 || status == 6) {
                                if (auditflag == 0)
                                    switch (pubflag) {
                                        case 0:
                                            status_str = "<td align=center><img src=\"../images/button/yi.gif\" border=0 alt=\"已发布\"></td>";
                                            break;
                                        case 1:
                                            if(mediatype == 1){
                                                status_str = "<td align=center><img src=\"../images/button/dmt.gif\" border=0 alt=\"多媒体\"></td>";
                                            }else{
                                                status_str = "<td align=center><img src=\"../images/button/xin.gif\" border=0 alt=\"新稿\"></td>";
                                            }
                                            break;
                                        case 2:
                                            status_str = "<td align=center><font color=red>发布中</font></td>";
                                            break;
                                        default:
                                            break;
                                    }
                                else if (auditflag == 1)
                                    status_str = "<td align=center><img src=\"../images/button/shen.gif\" border=0 alt=\"在审\"></td>";
                                else
                                    status_str = "<td align=center><img src=\"../images/button/tui.gif\" border=0 alt=\"退稿\"></td>";
                            } else if(status == 3){      //多媒体文件等待转换状态
                                status_str = "<td align=center><img src=\"../images/button/dai.gif\" border=0 alt=\"等待\"></td>";
                            }  else {                                  //2是归档状态
                                status_str = "<td align=center><img src=\"../images/button/dang.gif\" border=0 alt=\"存档\"></td>";
                            }
                        } else {
                            status_str = "<td align=center><img src=\"../images/button/" + ext + "\" border=0 alt=\"上传文件\"></td>";
                        }

                        var rss_val = "";
                        if (isrssflag===1) rss_val = "R";

                        if (emptyflag == 0) {
                            if ((referID > 0) || (!isown)) {
                                if((refersFrom == null)||(refersFrom.equals(""))||(refersFrom.length()==0)) refersFrom = "";
                                maintitle = "<a title='" + refersFrom + "'>" + maintitle + "</a>"
                            } else{
                                if((refersTo == null)||(refersTo.equals(""))||(refersTo.length()==0)) refersTo = "";
                                if(writeRight || adminRight || editor=='<%=userid%>') {
                                    maintitle = "<a href=javascript:editArticle("+ rownum +  "," + jsondata.ARTICLES[ii].ID + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + "," + "'c');" + " title='" + refersTo + "'>" + maintitle + "</a>";
                                }
                            }
                        } else {
                            maintitle = "<a href=javascript:edituploadfile(" + jsondata.ARTICLES[ii].ID + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + "," + "'c')>" + maintitle + "</a>"
                        }
                        if ((referID > 0) || (!isown)){
                            if(useArtType==0)
                                maintitle = maintitle + "<font color=red style='font-size:9px'>(地址引用)</font>";
                            else
                                maintitle = maintitle + "<font color=red style='font-size:9px'>(内容引用)</font>";
                        }

                        var updateAction = "";
                        if(writeRight || adminRight || deptWriteRight || editor=='<%=userid%>') {
                            if (lockflag == 0) {              //没有锁定
                                if (emptyflag == 0) {        //非上传文件，内容不为空
                                    if ((referID > 0) || (!isown))
                                        updateAction = "&nbsp;";
                                    else {
                                        if (auditflag == 0 || auditflag == 2)   //2是退稿状态   0--不需要审核
                                            updateAction = "<a href=javascript:editArticle(" + rownum +  "," + jsondata.ARTICLES[ii].ID + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + ",'c');><img src=\"../images/edit.png\" width=\"24\" height=\"23\" alt=\"编辑文章\"></a>";
                                        else
                                            updateAction = "&nbsp;";
                                    }
                                } else {                     //上传文件，内容为空
                                    if (auditflag == 0 || auditflag == 2)     //2是退稿状态  0--不需要审核
                                        updateAction = "<a href=javascript:edituploadfile(" + jsondata.ARTICLES[ii].ID + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + "," + "'c')><img src=\"../images/edit.png\" width=\"24\" height=\"23\" alt=\"编辑文章\"></a>"
                                    else
                                        updateAction = "&nbsp;";
                                }
                            } else {
                                if((referID > 0) || !isown)
                                    updateAction = "<img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"已被锁定\">";
                                else
                                    updateAction = "<a href=javascript:editArticle(" + rownum + "," + jsondata.ARTICLES[ii].ID + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + ",'c');><img src=\"../images/edit.png\" width=\"24\" height=\"23\" alt=\"编辑文章\"></a>";
                            }
                        } else {
                            if (lockflag!=0) {   //没有写权限，也并不是网站管理员，文章被其他用户锁定
                                updateAction = "<img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"已被锁定\">";
                            } else
                                updateAction = "&nbsp;";
                        }

                        var deleteAction = "";
                        if(writeRight || adminRight || deptDeleteRight || editor=='<%=userid%>')
                            if (auditflag == 0) {
                                if (!isown)
                                    deleteAction = "<a href=\"javascript:removeRefersArticle(" + rownum + "," + jsondata.ARTICLES[ii].ID +"," + currentPage + "," + flag + "," + ascdesc + "," + range +  ",'a');\"><img src=\"../images/del.png\" width=\"23\" height=\"23\" alt=\"删除文章\"/></a>";
                                else
                                    deleteAction = "<a href=\"javascript:removeArticle(" + rownum + "," + jsondata.ARTICLES[ii].ID +"," + currentPage + "," + flag + "," + ascdesc + "," + range +  ",'a');\"><img src=\"../images/del.png\" width=\"23\" height=\"23\" alt=\"删除文章\"/></a>";
                            } else {
                                deleteAction = "&nbsp;";
                            }
                        else
                            deleteAction = "&nbsp;";

                        var headlineAction = "<a href=\"javascript:headlineArticle(" + jsondata.ARTICLES[ii].ID + "," + currentPage + "," + flag + "," + ascdesc + "," + range + ",'a');\"><img src=\"../images/give.png\" width=\"23\" height=\"23\" /></a>";

                        var pushAction = "";
                        if(writeRight || adminRight || deptWriteRight || editor=='<%=userid%>') {
                            if (auditflag == 0) {
                                if ((referID > 0) || (!isown))
                                    pushAction = "&nbsp;";
                                else
                                    pushAction = "<a href=\"javascript:pushArticle(" + jsondata.ARTICLES[ii].ID + "," + currentPage + "," + flag + "," + ascdesc + "," + range + ",'a');\"><img src=\"../images/give.png\" width=\"23\" height=\"23\" /></a>";
                            } else
                                pushAction = "&nbsp;";
                        } else
                            pushAction = "&nbsp;";

                        var publishAction = "";
                        if ((status == 1 || status == 4 || status == 5 || status == 6) && auditflag == 0 && emptyflag == 0)
                            if (referID > 0)
                                publishAction = "&nbsp;";
                            else{
                                if(!isown) {
                                    if(useArtType == 0)
                                        publishAction = "&nbsp;";
                                    else {
                                        if (publishRight || adminRight || deptPublishRight)     //用户是否有发布所有文章的权限
                                            publishAction = "<a href=javascript:publish(" + rownum + "," + columnid + "," + jsondata.ARTICLES[ii].ID + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + ",3," + isown +  ");><img src=\"../images/public.png\" width=\"24\" height=\"23\" alt=\"文章发布\"></a>";
                                        else
                                            publishAction = "&nbsp;";
                                    }
                                } else if(mediatype == 1){
                                    publishAction = "&nbsp;";
                                } else {
                                    if (publishRight || adminRight  || deptPublishRight)     //用户是否有文章发布的权限
                                        publishAction = "<a href=javascript:publish(" + rownum + "," + columnid + "," + jsondata.ARTICLES[ii].ID + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + ",3," +isown +  ");><img src=\"../images/public.png\" width=\"24\" height=\"23\" alt=\"文章发布\"></a>";
                                    else if (editor=='<%=userid%>')
                                        publishAction = "<a href=javascript:publish(" + rownum + "," + columnid + "," + jsondata.ARTICLES[ii].ID + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + ",3," +isown +  ");><img src=\"../images/public.png\" width=\"24\" height=\"23\" alt=\"文章发布\"></a>";
                                    else
                                        publishAction = "&nbsp;";
                                }
                            }
                        else
                            publishAction = "&nbsp;";

                        htmldata = htmldata + "                <tr bgcolor=\"#FFFFFF\" id=\"row" + jsondata.ARTICLES[ii].ID + "\">\n";
                        htmldata = htmldata + "<td align=\"center\"><input type=\"checkbox\" name=\"article\" id=\"ckarticle\"" + jsondata.ARTICLES[ii].ID + " value=\"" + jsondata.ARTICLES[ii].ID  + "\"></td>";
                        htmldata = htmldata + status_str;
                        htmldata = htmldata + "<td>" + rss_val + "</td>";
                        htmldata = htmldata + "<td>" + maintitle + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + publishtime + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + lastupdate + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + main_weight + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + vice_weight + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\" >" + editor + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:Preview("+jsondata.ARTICLES[ii].ID + "," + columnid + ");\"><img src=\"../images/view.png\" width=\"23\" height=\"23\" /></a></td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + updateAction + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + deleteAction + "</td>";
                        //htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + headlineAction + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + pushAction + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + publishAction + "</td>";
                        htmldata = htmldata + "</tr>";
                    }
                    if (articleIds.length>0) articleIds = articleIds.substring(0,articleIds.length-1);
                    htmldata = htmldata + "            </table>\n";
                    htmldata = htmldata + "           <input type=\"hidden\" name=\"allArticleIds\" value=\"" + articleIds + "\">";
                    htmldata = htmldata + "         </form>";
                    htmldata = htmldata + "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";

                    htmldata = htmldata + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
                    htmldata = htmldata + "    <tr>";
                    htmldata = htmldata + "    <td align='right'>" + Pagination(jsondata.TOTALCOUNT,columnid,totalPage,currentPage,10,searchflag,jsondata.FLAG,jsondata.ASCDESC,auditflag,pubflag,mediatype,arttypeval)+ "</td>";
                    htmldata = htmldata + "    </tr>";
                    htmldata = htmldata + "</table>";

                    searchForm.column.value=columnid;
                    searchForm.searchFlag.value=doSearchFlag;
                    searchForm.flag.value=flag;
                    searchForm.ascdesc.value=ascdesc;
                    searchForm.pageno.value=currentPage;

                    $("#contentid").html(htmldata);
                    //$("#listtail").css('display','block');
                    //$("#totalid").html(jsondata.TOTALCOUNT);
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

        function headlineArticle() {
            alert("这篇文章置顶");
        }

        function editArticle(rownum,articleID, range, currentPage,doSearchFlag,flag,ascdesc,from) {
            var arttypeval = searchForm.arttype.value;

            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            //articleID:当前编辑的文章ID
            //range：分页列表每页列出文章条数
            //start：被编辑文章所处的页号
            //searchFlag：是否为检索结果页面
            //flag：排序标识，标识使用哪个字段排序
            //ascdesc:排序方式，是升序还是降序
            window.open("editarticle.jsp?article=" + articleID + "&range=" + range + "&start=" + currentPage + "&searchFlag=" + doSearchFlag + "&flag=" + flag + "&ascdesc=" + ascdesc + "&rownum=" + rownum + "&fromflag=" + from + "&arttype=" + arttypeval, "", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function removeArticle(rownum,articleID, currentPage,flag,ascdesc,range,from) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var arttypeval = searchForm.arttype.value;

            htmlobj=$.ajax({
                url:"removearticle.jsp",
                data:{
                    article:articleID,
                    start:currentPage,
                    flag:flag,
                    ascdesc:ascdesc,
                    arttype:arttypeval,
                    range:range
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.result == "true") {
                        //var tr = document.getElementById('row' + articleID);
                        //tr.parentNode.removeChild(tr);
                        //tb.deleteRow(rownum+1);                //table对象根据行号删除表格中的行
                        articlesInfoList(columnID,currentPage,doSearchFlag,flag,ascdesc,null,null,null,arttypeval);
                        layer.alert('文章删除成功！！！')
                    } else {
                        layer.alert('文章删除失败！！！');
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

        function removeRefersArticle(rownum,articleID, currentPage,flag,ascdesc,range,from) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var arttypeval = searchForm.arttype.value;

            htmlobj=$.ajax({
                url:"removeRefersArticle.jsp",
                data:{
                    doDelete:true,
                    column:columnID,
                    article:articleID,
                    searchFlag:doSearchFlag,
                    range:range,
                    pageno:currentPage,
                    flag:flag,
                    arttype:arttypeval,
                    ascdesc:ascdesc
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.ERRCODE == 0) {
                        articlesInfoList(columnID,currentPage,doSearchFlag,flag,ascdesc,null,null,null,arttypeval);
                        layer.alert('引用文章删除成功！！！')
                    } else {
                        layer.alert('引用文章删除失败！！！');
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

        function pushArticle(articleID, currentPage,flag,ascdesc,range,from) {
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("addRelateListnew.jsp?articleid=" + articleID + "&start=" + currentPage + "&range=" + range + "&fromflag=a", "", "width=" + iWidth + ",height=" + iHeight + ",top=" + iTop + ", left=" + iLeft + ", toolbar=no, menubar=no, resizable=no,location=no, status=no");
        }

        function edituploadfile(articleID, range, currentPage,doSearchFlag,flag,ascdesc,from) {
            var columnID = searchForm.column.value;
            var arttypeval = searchForm.arttype.value;

            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("../upload/edituploadfile.jsp?article=" + articleID + "&range=" + range + "&start=" + currentPage + "&searchFlag=" + doSearchFlag + "&flag=" + flag + "&ascdesc=" + ascdesc  + "&fromflag=" + from + "&arttype=" + arttypeval, "", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function publish(rownum,columnID,articleID, range, start,doSearchFlag,flag,ascdesc,source,isown) {
            htmlobj=$.ajax({
                url:"../publish/publishnew.jsp",
                timeout:0,
                data:{
                    column:columnID,
                    article:articleID,
                    range:range,
                    start:start,
                    searchFlag:doSearchFlag,
                    flag:flag,
                    ascdesc:ascdesc,
                    source:source,
                    isown:isown
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.result == "true") {
                        var tb = document.getElementById('articleDataGrid');
                        tb.rows[rownum + 1].cells[1].innerHTML = "<img src=\"../images/button/yi.gif\" border=0 alt=\"已发布\">";
                        layer.alert('文章发布成功！！！')
                    } else {
                        layer.alert('文章发布失败！！！');
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    if(textStatus=="timeout"){
                        alert("加载超时，请重试");
                    }else{
                        alert(textStatus);
                    }
                    alert(jqXHR.responseText);
                    alert(jqXHR.status);
                    alert(jqXHR.readyState);
                    alert(jqXHR.statusText);
                    alert(textStatus);
                    alert(errorThrown);
                },
                complete: function (XMLHttpRequest,status) {
                    if(status == 'timeout') {
                        alert("hello word");
                    }
                }
            });
        }

        function Preview(articleID, columnId) {
            var columnId = searchForm.column.value;
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("preview.jsp?article=" + articleID + "&column=" + columnId, "Preview", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function CA() {
            for (var i = 0; i < document.form1.elements.length; i++) {
                var e = document.form1.elements[i];
                if (e.name != 'allbox' && e.type == 'checkbox') {
                    e.checked = document.form1.allbox.checked;
                }
            }
        }

        function createArticle(columnID,range, currentPage,doSearchFlag,flag,ascdesc) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            flag = searchForm.flag.value;
            ascdesc = searchForm.ascdesc.value;
            currentPage = searchForm.pageno.value;

            var arttypeval = searchForm.arttype.value;
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("createarticle.jsp?column=" + columnID + "&range=" + range + "&start=" + currentPage + "&searchFlag=" + doSearchFlag + "&flag=" + flag +"&ascdesc=" + ascdesc + "arttype=" + arttypeval, "CreateWin", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function createuploadfile(columnID,range, currentPage,doSearchFlag,flag,ascdesc) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value
            var arttypeval = searchForm.arttype.value;

            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("../upload/createuploadfile.jsp?column=" + columnID+ "&range=" + range + "&start=" + currentPage + "&searchFlag=" + doSearchFlag + "&flag=" + flag +"&ascdesc=" + ascdesc + "&arttype=" + arttypeval, "UpdateWin", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function referArticle(columnID,range, currentPage,doSearchFlag,flag,ascdesc) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value
            var arttypeval = searchForm.arttype.value;

            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("referarticle.jsp?column=" + columnID + "&range=" + range + "&start="+currentPage + "&searchFlag=" + doSearchFlag + "&flag="+flag + "&ascdesc="+ascdesc, "", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function updateRSS() {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value
            var arttypeval = searchForm.arttype.value;

            var allArticleIds = form1.allArticleIds.value;
            var selArticleIds = "";
            $.each($('input:checkbox:checked'),function(){
                selArticleIds = selArticleIds + $(this).val() + ",";
            });

            if (selArticleIds.length>0) selArticleIds = selArticleIds.substring(0,selArticleIds.length-1);
            if (selArticleIds.length==0) {
                layer.alert('请选择需要设置RSS的文章！！！')
            } else
                htmlobj=$.ajax({
                    url:"doArticleRSS.jsp",
                    data:{
                        allArticleIds:allArticleIds,
                        article:selArticleIds
                    },
                    dataType:'json',
                    async:false,
                    success:function(jsondata){
                        var tb = document.getElementById('articleDataGrid');
                        if (jsondata.ERRCODE == 0) {
                            var articleids= new Array();      //定义一数组
                            articleids=selArticleIds.split(",");        //字符分割
                            for(var ii=0; ii<articleids.length; ii++) {
                                var tr = document.getElementById('row' + articleids[ii]);
                                tr.cells[2].innerHTML = "R";
                            }
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

        function batchDel(columnID,range, currentPage,doSearchFlag,flag,ascdesc) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value
            var arttypeval = searchForm.arttype.value;

            var selArticleIds = "";
            $('input:checkbox[name=article]:checked').each(function(i) {
                selArticleIds = selArticleIds + $(this).val() + ",";
            });

            if (selArticleIds.length>0) selArticleIds = selArticleIds.substring(0,selArticleIds.length-1);

            if (selArticleIds.length==0) {
                layer.alert('请选择需要删除的文章！！！')
            } else
                htmlobj=$.ajax({
                    url:"batchDel.jsp",
                    data:{
                        article:selArticleIds
                    },
                    dataType:'json',
                    async:false,
                    success:function(jsondata){
                        var tb = document.getElementById('articleDataGrid');
                        if (jsondata.ERRCODE == 0) {
                            //var articleids= new Array();      //定义一数组
                            //articleids=selArticleIds.split(",");        //字符分割
                            //for(var ii=0; ii<articleids.length; ii++) {
                            //    var tr = document.getElementById('row' + articleids[ii]);
                            //    tr.parentNode.removeChild(tr);
                                //tb.deleteRow(rownum+1);                //table对象根据行号删除表格中的行
                            //}
                            layer.alert('文章删除成功！！！')
                            articlesInfoList(columnID,currentPage,doSearchFlag,flag,ascdesc,null,null,null,arttypeval);
                        } else {
                            layer.alert('文章删除失败！！！')
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

        function doAchive(columnID,range, currentPage,doSearchFlag,flag,ascdesc) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value
            var arttypeval = searchForm.arttype.value;

            var selArticleIds = "";
            $('input:checkbox[name=article]:checked').each(function(i) {
                selArticleIds = selArticleIds + $(this).val() + ",";
            });

            if (selArticleIds.length>0) selArticleIds = selArticleIds.substring(0,selArticleIds.length-1);
            if (selArticleIds.length==0) {
                layer.alert('请选择需要归档的文章！！！')
            } else
                htmlobj=$.ajax({
                    url:"batchAchive.jsp",
                    data:{
                        column:columnID,
                        articles:selArticleIds
                    },
                    dataType:'json',
                    async:false,
                    success:function(jsondata){
                        var tb = document.getElementById('articleDataGrid');
                        if (jsondata.ERRCODE == 0) {
                            var articleids= new Array();      //定义一数组
                            articleids=selArticleIds.split(",");        //字符分割
                            for(var ii=0; ii<articleids.length; ii++) {
                                var tr = document.getElementById('row' + articleids[ii]);
                                tr.cells[1].innerHTML = "<img src=\"../images/button/dang.gif\" border=0 alt=\"归档\">";
                            }
                            layer.alert('文章归档成功！！！')
                        } else {
                            layer.alert('文章归档失败！！！')
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

        function doUnuse(columnID,range, currentPage,doSearchFlag,flag,ascdesc) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value
            var arttypeval = searchForm.arttype.value;

            var selArticleIds = "";
            $('input:checkbox[name=article]:checked').each(function(i) {
                selArticleIds = selArticleIds + $(this).val() + ",";
            });

            if (selArticleIds.length>0) selArticleIds = selArticleIds.substring(0,selArticleIds.length-1);
            if (selArticleIds.length==0) {
                layer.alert('请选择需要设置未用的文章！！！')
            } else
                htmlobj=$.ajax({
                    url:"batchUnuse.jsp",
                    data:{
                        column:columnID,
                        articles:selArticleIds
                    },
                    dataType:'json',
                    async:false,
                    success:function(jsondata){
                        var tb = document.getElementById('articleDataGrid');
                        if (jsondata.ERRCODE == 0) {
                            var articleids= new Array();      //定义一数组
                            articleids=selArticleIds.split(",");        //字符分割
                            for(var ii=0; ii<articleids.length; ii++) {
                                var tr = document.getElementById('row' + articleids[ii]);
                                tr.cells[1].innerHTML = "<img src=\"../images/button/wei.gif\" border=0 alt=\"归档\">";
                            }
                            layer.alert('文章设置未用成功！！！')
                        } else {
                            layer.alert('文章设置未用失败！！！')
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

        //columnid:获取该栏目的文章列表
        //currentPage:当前页号
        //doSearchFlag：是否执行检索功能
        //flag:设置排序字段
        //ascdesc：升序排列还是降序排列，0升序排列，1降序排列
        //auditflag：文章审核状态
        //pubflag：文章是否被发布
        //mediatype：多媒体文件
        //status：获取那些状态的文章，0：任何状态，1：新高，2：归档，3：等待多媒体文件转换，4：商品促销，5：新品商品，6：活动促销
        function auditArticles(columnID,range, currentPage,doSearchFlag,flag,ascdesc,auditflag) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value
            var arttypeval = searchForm.arttype.value;

            articlesInfoList(columnID,1,false,4,0,auditflag,null,null,null);
        }

        //columnid:获取该栏目的文章列表
        //currentPage:当前页号
        //doSearchFlag：是否执行检索功能
        //flag:设置排序字段
        //ascdesc：升序排列还是降序排列，0升序排列，1降序排列
        //auditflag：文章审核状态
        //pubflag：文章是否被发布
        //mediatype：多媒体文件
        //status：获取那些状态的文章，0：任何状态，1：新高，2：归档，3：等待多媒体文件转换，4：商品促销，5：新品商品，6：活动促销
        function returnArticles(columnID,range, currentPage,doSearchFlag,flag,ascdesc,auditflag) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value
            var arttypeval = searchForm.arttype.value;

            articlesInfoList(columnID,1,false,4,0,auditflag,null,null,null);
        }

        function WebEdit(columnID,range, currentPage,doSearchFlag,flag,ascdesc) {
            var columnID = searchForm.column.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value
            var arttypeval = searchForm.arttype.value;

            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("../webedit/index.jsp?column=" + columnID + "&right=4" + "&range=" + range + "&start=" + currentPage + "&searchFlag=" + doSearchFlag + "&flag=" + flag +"&ascdesc=" + ascdesc, "WebEdit", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><a href="../index1.jsp" class="layui-logo">CMS-文章管理</a></div>
        <jsp:include page="../include/menu.jsp"></jsp:include>
    </div>

    <div class="layui-side layui-bg-black" id="sidetreeid" style="top: 60px;">
        <div id="treebox" style="overflow-x:auto;">
            <ul id="mytree"></ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div id="maincontent" class="location" style="padding-bottom: 5px;display: none;">
            <div id="column_nameid">文章管理 ></div>
            <div style="padding-top: 10px;padding-bottom: 10px;">
                <form name="searchForm">
                    <input type="hidden" name="column" value="<%=columnID%>">
                    <input type="hidden" name="pageno" value="<%=currentPage%>">
                    <input type="hidden" name="flag" value="<%=flag%>">
                    <input type="hidden" name="ascdesc" value="<%=ascdesc%>">
                    <input type="hidden" name="searchFlag" value="<%=doSearchFlag%>">
                    <input type="hidden" name="right" value="<%=rightid%>">
                    标题：<input type="text" name="title" id="titleid" value=""  class="sear_txt_1" />
                    副标题：<input type="text" name="vicetitle" id="vicetitleid" value=""  class="sear_txt_1" />
                    作者：<input type="text" name="author" id="authorid" value=""  class="sear_txt_1" />
                    来源：<input type="text" name="source" id="sourceid" value=""  class="sear_txt_1" />
                    发布日期：<input type="text" name="spubdate" id="sdate" class="datetime_txt_1" />到
                    <input type="text" name="epubdate" id="edate" class="datetime_txt_1" />
                    文章ID：<input type="text" name="articleid" id="artid" value=""  class="sear_txt_1" />
                    状态：<select name="arttype" id="arttypeid" class="sear_txt_1"></select>
                    <input type="button" name="search" id="searchid" value="搜索" onclick="javascript:articlesInfoList(<%=columnID%>,<%=currentPage%>,true,<%=flag%>,<%=ascdesc%>,null,null,null,null);" class="sear_btn"/>
                </form>
            </div>
            <div class="button_top">
                <ul>
                    <li><input name="webedit" type="button" value="文件夹管理" class="button_bg_2" onclick='javascript:WebEdit(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>);'/></li>
                    <!--li><input name="auditfile" type="button" value="在审" class="button_bg_1" onclick='javascript:auditArticles(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>,1);'/></li>
                    <li><input name="returnfile" type="button" value="退稿" class="button_bg_1" onclick='javascript:returnArticles(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>,2);'/></li-->
                    <li><input name="updateRss" type="button" value="更新RSS" class="button_bg_3" onclick='javascript:updateRSS();'/></li>
                    <li><input name="batchAchive" type="button" value="批量归档" class="button_bg_3" onclick='javascript:doAchive(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>);'/></li>
                    <li><input name="batchUnuse" type="button" value="批量未用" class="button_bg_3" onclick='javascript:doUnuse(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>);'/></li>
                    <li><input name="batchDel" type="button" value="批量删除" class="button_bg_3" onclick='javascript:batchDel(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>);'/></li>
                    <li><input name="refer" type="button" value="引用" class="button_bg_1" onclick='javascript:referArticle(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>)'/></li>
                    <li><input name="uploadFile" type="button" value="上传文件" class="button_bg_3" onclick='javascript:createuploadfile(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>);'/></li>
                    <li><input name="newFile" type="button" value="新建" class="button_bg_1" onclick='javascript:createArticle(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>);'/></li>
                </ul>
            </div>
        </div>
        <div id="contentid"></div>
    </div>
    <jsp:include page="../include/tail.jsp"></jsp:include>
</div>
<script>
    layui.use(['element', 'tree', 'laydate'], function(){
        var $ = layui.jquery;
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
                //columnid:获取该栏目的文章列表
                //currentPage:当前页号
                //doSearchFlag：是否执行检索功能
                //flag:设置排序字段
                //ascdesc：升序排列还是降序排列，0升序排列，1降序排列
                //auditflag：文章审核状态
                //pubflag：文章是否被发布
                //mediatype：多媒体文件
                //status：获取那些状态的文章，0：任何状态，1：新高，2：归档，3：等待多媒体文件转换，4：商品促销，5：新品商品，6：活动促销
                articlesInfoList(item.data.id,1,false,4,0,null,null,null,0);
                $("#maincontent").css("display","block");
                //layer.msg('当前节名称：'+ item.data.title + "==" + item.data.id + '<br>全部参数：'+ JSON.stringify(item));
                //console.log(item);
            },
            data:<%=buf.toString()%>
        });
        $("#treebox").css("height",document.documentElement.clientHeight-60);

        var laydate = layui.laydate;
        laydate.render({
            elem: '#sdate', //指定元素
            type:"datetime"
        });

        laydate.render({
            elem: '#edate', //指定元素
            type:"datetime"
        });
    });
</script>
</body>
</html>

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
<%@ page import="com.bizwink.cms.util.FileUtil" %><%--
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
    String userid = authToken.getUserID();
    int sitetype = authToken.getSitetype();
    int columnID = ParamUtil.getIntParameter(request,"column",0);
    int currentPage = ParamUtil.getIntParameter(request,"start",1);
    int range = ParamUtil.getIntParameter(request,"range",0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");
    int flag = ParamUtil.getIntParameter(request,"flag",0);
    int ascdesc = ParamUtil.getIntParameter(request,"ascdesc",0);
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
    <title>网站内容管理系统--文章迁移</title>
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
            var columnid = <%=columnID%>;
            var currentPage = <%=currentPage%>;
            var doSearchFlag = <%=doSearchFlag%>;
            var flag = <%=flag%>;
            var ascdesc = <%=ascdesc%>;
            if (columnid>0) articlesInfoList(columnid,currentPage,doSearchFlag,flag,ascdesc,null,null,null,null);
        });

        function goSearchPage(columnid,doSearchFlag,flag,ascdesc,auditflag,pubflag,mediatype,status) {
            var currentPage = form.turnPage.value;
            articlesInfoList(columnid,currentPage,doSearchFlag,flag,ascdesc,auditflag,pubflag,mediatype,status);
            form.turnPage.value = currentPage;
        }

        function Pagination(totalArticleNum,columnid,totalpages,curentpage,pageNumLength,doSearchFlag,flag,ascdesc,auditflag,pubflag,mediatype,status) {
            var PaginationDiv = "";
            if (totalpages <= 1)
                PaginationDiv = "<div class=\"page\" style=\"display: none\">";
            else
                PaginationDiv = "<div class=\"page\" style=\"display: block\">";

            if (curentpage<=1)
                PaginationDiv = PaginationDiv + "<span class=\"txtl\">当前栏目的文章总数：" + totalArticleNum + "</span><span>第1页</span>";
            else {
                PaginationDiv = PaginationDiv + "<span class=\"txtl\">当前栏目的文章总数：" + totalArticleNum + "<a href=\"javascript:articlesInfoList(" + columnid + ",1," + flag + "," + ascdesc + ","+ auditflag + "," + pubflag + "," + mediatype + "," + status + ");\">第1页</a>";
            }

            PaginationDiv = PaginationDiv + "<span>共" + totalpages + "页</span>";

            if (curentpage<=1)
                PaginationDiv = PaginationDiv + "<span>上一页</span>";
            else {
                PaginationDiv = PaginationDiv + "<a href=\"javascript:articlesInfoList(" + columnid + "," + (curentpage - 1) + ","+ doSearchFlag + "," + flag + "," + ascdesc + ","+ auditflag + "," + pubflag + "," + mediatype + "," + status + ");\">上一页</a>";
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
                    PaginationDiv = PaginationDiv + "<a href=\"javascript:articlesInfoList(" + columnid + "," + (ii + 1) + "," + doSearchFlag + "," + flag + "," + ascdesc + ","+ auditflag + "," + pubflag + "," + mediatype + "," + status + ");\">" + (ii + 1) + "</a>";
                }
            }

            if (curentpage<totalpages)
                PaginationDiv = PaginationDiv + "<a href=\"javascript:articlesInfoList(" + columnid + "," + (curentpage+1) + "," + doSearchFlag + "," + flag + "," + ascdesc + ","+ auditflag + "," + pubflag + "," + mediatype + "," + status + ");\">下一页</a>";
            else
                PaginationDiv = PaginationDiv + "<span>下一页</span>";

            PaginationDiv = PaginationDiv + "<span class=\"txtl\">转到第</span>";
            PaginationDiv = PaginationDiv + "<span class=\"select-pager\">";
            PaginationDiv = PaginationDiv + "<form name=\"form\">";

            PaginationDiv = PaginationDiv + "<select name=\"turnPage\" size=\"1\" onchange=\"javascript:goSearchPage(" + columnid + ",'" + doSearchFlag + "'," + flag + "," + ascdesc + ","+ auditflag + "," + pubflag + "," + mediatype + "," + status + ");\">";

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
        //status：获取那些状态的文章，0：任何状态，1：新高，2：归档，3：等待多媒体文件转换，4：商品促销，5：新品商品，6：活动促销
        function articlesInfoList(columnid,currentPage,doSearchFlag,flag,ascdesc,auditflag,pubflag,mediatype,status) {
            var range = 20;
            var title = "";
            var articleid = "";
            var vicetitle = "";
            var author = "";
            var source = "";
            var spubdate = "";
            var epubdate = "";
            var status = "";

            if (doSearchFlag) {
                title = searchForm.title.value;
                articleid = searchForm.articleid.value;
                vicetitle = searchForm.vicetitle.value;
                author = searchForm.author.value;
                source = searchForm.source.value;
                spubdate = searchForm.spubdate.value;
                epubdate = searchForm.epubdate.value;
                status = searchForm.status.value;
            }

            htmlobj=$.ajax({
                url:"../articlemove/listarticle.jsp",
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
                    status:status,
                    column:columnid,
                    range:range,
                    flag:flag,
                    ascdesc:ascdesc,
                    audit:auditflag,            //查询待审稿件和退回的稿件
                    pubflag:pubflag,
                    mediatype:mediatype
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
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

                    status = parseInt(jsondata.STATUS);                  //按文章状态排序
                    $("#statusid option").remove();
                    if (status == 1) {
                        $("#statusid").append("<option value=0>请选择</option>");
                        $("#statusid").append("<option value=1 selected>新稿</option>");
                        $("#statusid").append("<option value=2>已发布</option>");
                        $("#statusid").append("<option value=3>未用</option>");
                        $("#statusid").append("<option value=4>在审</option>");
                        $("#statusid").append("<option value=5>退稿</option>");
                    } else if (status == 2) {
                        $("#statusid").append("<option value=0>请选择</option>");
                        $("#statusid").append("<option value=1>新稿</option>");
                        $("#statusid").append("<option value=2 selected>已发布</option>");
                        $("#statusid").append("<option value=3>未用</option>");
                        $("#statusid").append("<option value=4>在审</option>");
                        $("#statusid").append("<option value=5>退稿</option>");
                    } else if (status == 3) {
                        $("#statusid").append("<option value=0>请选择</option>");
                        $("#statusid").append("<option value=1>新稿</option>");
                        $("#statusid").append("<option value=2>已发布</option>");
                        $("#statusid").append("<option value=3 selected>未用</option>");
                        $("#statusid").append("<option value=4>在审</option>");
                        $("#statusid").append("<option value=5>退稿</option>");
                    } else if (status == 4) {
                        $("#statusid").append("<option value=0>请选择</option>");
                        $("#statusid").append("<option value=1>新稿</option>");
                        $("#statusid").append("<option value=2>已发布</option>");
                        $("#statusid").append("<option value=3>未用</option>");
                        $("#statusid").append("<option value=4 selected>在审</option>");
                        $("#statusid").append("<option value=5>退稿</option>");
                    } else if (status == 5) {
                        $("#statusid").append("<option value=0>请选择</option>");
                        $("#statusid").append("<option value=1>新稿</option>");
                        $("#statusid").append("<option value=2>已发布</option>");
                        $("#statusid").append("<option value=3>未用</option>");
                        $("#statusid").append("<option value=4>在审</option>");
                        $("#statusid").append("<option value=5 selected>退稿</option>");
                    } else {
                        $("#statusid").append("<option value=0>请选择</option>");
                        $("#statusid").append("<option value=1>新稿</option>");
                        $("#statusid").append("<option value=2>已发布</option>");
                        $("#statusid").append("<option value=3>未用</option>");
                        $("#statusid").append("<option value=4>在审</option>");
                        $("#statusid").append("<option value=5>退稿</option>");
                    }

                    var column_name = jsondata.COLUMNNAME;
                    $("#column_nameid").html("文章迁移管理 >" + column_name);

                    var htmldata = "<table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n";

                    htmldata = htmldata + "    <tr>\n" +
                        "        <td align=\"left\" valign=\"top\">\n" +
                        "          <form name=form1 id=\"form1id\">" +
                        "            <table id=\"articleDataGrid\" width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#CCCCCC\" class=\"tab_list\">\n" +
                        "                <tr>\n" +
                        "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><input type=\"checkbox\" name=\"allbox\" id=\"allboxid\" onclick='javascript:CA();' />选择</td>\n" +
                        "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">状态</td>\n" +
                        "                    <td width=\"300\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag +  ",1," + ascDescFlag + ",null,null,null,null);\">文章标题</a></td>\n" +
                        "                    <td width=\"120\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",4," + ascDescFlag + ",null,null,null,null);\">发布时间</a></td>\n" +
                        "                    <td width=\"120\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",5," + ascDescFlag + ",null,null,null,null);\">修改时间</a></td>\n" +
                        "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",2," + ascDescFlag + ",null,null,null,null);\">主权重</a></td>\n" +
                        "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",3," + ascDescFlag + ",null,null,null,null);\">次权重</a></td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">文章编辑</td>\n" +
                        "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">预览</td>\n" +
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
                        var isown = true;
                        var referID = jsondata.ARTICLES[ii].REFERID;
                        var refersFrom = jsondata.ARTICLES[ii].RefersFrom;
                        var refersTo = jsondata.ARTICLES[ii].RefersTo;
                        var useArtType = jsondata.ARTICLES[ii].useArticleType;
                        var status_str = null;
                        var rownum = ii;

                        if (articleColumnID!=columnid) isown = false;

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

                        htmldata = htmldata + "                <tr bgcolor=\"#FFFFFF\" id=\"row" + jsondata.ARTICLES[ii].ID + "\">\n";
                        htmldata = htmldata + "<td align=\"center\"><input type=\"checkbox\" name=\"article\" id=\"ckarticle\"" + jsondata.ARTICLES[ii].ID + " value=\"" + jsondata.ARTICLES[ii].ID  + "\"></td>";
                        htmldata = htmldata + status_str;
                        htmldata = htmldata + "<td>" + maintitle + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + publishtime + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + lastupdate + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + main_weight + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + vice_weight + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\" >" + editor + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:Preview("+jsondata.ARTICLES[ii].ID + "," + columnid + ");\"><img src=\"../images/view.png\" width=\"23\" height=\"23\" /></a></td>";
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
                    htmldata = htmldata + "    <td align='right'>" + Pagination(jsondata.TOTALCOUNT,columnid,totalPage,currentPage,10,searchflag,jsondata.FLAG,jsondata.ASCDESC,auditflag,pubflag,mediatype,status)+ "</td>";
                    htmldata = htmldata + "    </tr>";
                    htmldata = htmldata + "</table>";


                    searchForm.column.value=columnid;
                    searchForm.range.value=range;
                    searchForm.pageno.value=currentPage;
                    searchForm.searchflag.value=doSearchFlag;
                    searchForm.flag.value=flag;
                    searchForm.ascdesc.value=ascdesc;
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

        function selectTColumn() {
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open("selectColumnTree.jsp", "ArticleMoveWinForTargetColumn", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function setupTargetColumn(columnid,columnname) {
            $("#tcolumnid").val(columnid);
            $("#tcolumnnameid").val(columnname)
        }

        function CA() {
            for (var i = 0; i < document.form1.elements.length; i++) {
                var e = document.form1.elements[i];
                if (e.name != 'allbox' && e.type == 'checkbox') {
                    e.checked = document.form1.allbox.checked;
                }
            }
        }

        function checkwhat() {
            var tcolumnname = $("#tcolumnnameid").val();
            var tcolumnid = $("#tcolumnid").val();
            var scolumnid = searchForm.column.value;

            if (tcolumnname == "" || tcolumnname==null) {
                alert("目的栏目不能为空，请选择文章需要移到的栏目名称")
                return false;
            }

            if (tcolumnid == scolumnid) {
                alert("目的栏目不能和源栏目相同")
                return false;
            }
            return true;
        }

        function moveArticles() {
            if (checkwhat()) {
                var tcolumnid = $("#tcolumnid").val();                            //文章移动到的目标栏目ID
                var scopeval = $("input[name='selarticles']:checked").val();    //0 移动或者拷贝选择的文章   1 移动或者拷贝整个栏目的文章
                var forcedo = $('input[name="execute"]:checked').val();          //1 原栏目属性与目标栏目属性不一至强制执行文章移动，否则只移动原与目的栏目属性一至的文章
                var columnid = searchForm.column.value;
                var range = searchForm.range.value;
                var currentpage = searchForm.pageno.value;
                var doSearchFlag = searchForm.searchflag.value;
                var flag = searchForm.flag.value;
                var ascdesc = searchForm.ascdesc.value;

                var selected_artids = "";
                if (scopeval==0) {
                    var $chks = $("#form1id :checkbox:checked");
                    $chks.each(function () {
                        selected_artids = selected_artids  + $(this).attr("value") + ",";
                    });
                    if (selected_artids.length>0) selected_artids = selected_artids.substring(0,selected_artids.length-1);
                    if (selected_artids.length === 0) {
                        alert("请选择要移动的文章");
                        return;
                    }
                }

                htmlobj=$.ajax({
                    url:"moveok.jsp",
                    type:"POST",
                    data:{
                        scope:scopeval,
                        articles:selected_artids,
                        column:columnid,
                        tcolumn:tcolumnid,
                        execute:forcedo,
                        range:range,
                        start:currentpage,
                        searchFlag:doSearchFlag,
                        flag:flag,
                        ascdesc:ascdesc
                    },
                    dataType:'json',
                    async:false,
                    success:function(jsondata){
                        var errcode = jsondata.ERRCODE;
                        columnid = jsondata.COLUMNID;
                        range = jsondata.RANGE;
                        currentpage = jsondata.PAGENO;
                        doSearchFlag = jsondata.SEARCHFLAG;
                        flag = jsondata.FLAG;
                        ascdesc = jsondata.ASCDESC;
                        if (errcode === 2)
                            alert("文章所在原始栏目与目的栏目的属性定义不同，不能执行文章迁移操作！！！");
                        else if (errcode === -1)
                            alert("文章迁移操作失败！！！");
                        else if (errcode === -3)
                            alert("向目的栏目拷贝图片报错！！！");
                        else {
                            alert("文章迁移操作成功！！！");
                            articlesInfoList(columnid,currentpage,doSearchFlag,flag,ascdesc,null,null,null,-1);
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
                return true;
            } else {
                return false;
            }
        }

        function copyArticles() {
            if (checkwhat()) {
                var tcolumnid = $("#tcolumnid").val();                             //文章移动到的目标栏目ID
                var scopeval = $("input[name='selarticles']:checked").val();    //0 移动或者拷贝选择的文章   1 移动或者拷贝整个栏目的文章
                var forcedo = $('input[name="execute"]:checked').val();          //1 原栏目属性与目标栏目属性不一至强制执行文章移动，否则只移动原与目的栏目属性一至的文章
                var columnid = searchForm.column.value;
                var range = searchForm.range.value;
                var currentpage = searchForm.pageno.value;
                var doSearchFlag = searchForm.searchflag.value;
                var flag = searchForm.flag.value;
                var ascdesc = searchForm.ascdesc.value;
                var selected_artids = "";
                if (scopeval==0) {
                    var $chks = $("#form1id :checkbox:checked");
                    $chks.each(function () {
                        selected_artids = selected_artids  + $(this).attr("value") + ",";
                    });
                    if (selected_artids.length>0) selected_artids = selected_artids.substring(0,selected_artids.length-1);
                    if (selected_artids.length === 0) {
                        alert("请选择要移动的文章");
                        return;
                    }
                }

                htmlobj=$.ajax({
                    url:"copyok.jsp",
                    type:"POST",
                    data:{
                        scope:scopeval,
                        articles:selected_artids,
                        column:columnid,
                        tcolumn:tcolumnid,
                        execute:forcedo,
                        range:range,
                        start:currentpage,
                        searchFlag:doSearchFlag,
                        flag:flag,
                        ascdesc:ascdesc
                    },
                    dataType:'json',
                    async:false,
                    success:function(jsondata){
                        var errcode = jsondata.ERRCODE;
                        columnid = jsondata.COLUMNID;
                        range = jsondata.RANGE;
                        currentpage = jsondata.PAGENO;
                        doSearchFlag = jsondata.SEARCHFLAG;
                        flag = jsondata.FLAG;
                        ascdesc = jsondata.ASCDESC;
                        if (errcode === 2)
                            alert("文章所在原始栏目与目的栏目的属性定义不同，不能执行文章拷贝操作！！！");
                        else if (errcode === -1)
                            alert("文章拷贝操作失败！！！");
                        else if (errcode === -3)
                            alert("向目的栏目拷贝图片报错！！！");
                        else {
                            alert("文章拷贝操作成功！！！");
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
                return true;
            } else {
                return false;
            }
        }

        function Preview(articleID, columnId) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("../article/preview.jsp?article=" + articleID + "&column=" + columnId, "Preview", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><a href="../index1.jsp" class="layui-logo">CMS-文章管理</a></div>
        <jsp:include page="../include/menu.jsp"></jsp:include>
    </div>

    <div class="layui-side layui-bg-black" id="sidetreeid" style="top: 60px">
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
                    <input type="hidden" name="range" value="<%=range%>">
                    <input type="hidden" name="pageno" value="<%=currentPage%>">
                    <input type="hidden" name="searchflag" value="<%=doSearchFlag%>">
                    <input type="hidden" name="flag" value="<%=flag%>">
                    <input type="hidden" name="ascdesc" value="<%=ascdesc%>">
                    文章ID：<input type="text" name="articleid" id="artid" value=""  class="sear_txt_1" />
                    标题：<input type="text" name="title" id="titleid" value=""  class="sear_txt_1" />
                    副标题：<input type="text" name="vicetitle" id="vicetitleid" value=""  class="sear_txt_1" />
                    作者：<input type="text" name="author" id="authorid" value=""  class="sear_txt_1" />
                    来源：<input type="text" name="source" id="sourceid" value=""  class="sear_txt_1" />
                    发布日期：<input type="text" name="spubdate" id="sdate" class="datetime_txt_1" />到
                    <input type="text" name="epubdate" id="edate" class="datetime_txt_1" />
                    状态：<select name="status" id="statusid" class="sear_txt_1"></select>
                    <input type="button" name="search" id="searchid" value="搜索" onclick="javascript:articlesInfoList(<%=columnID%>,<%=currentPage%>,true,<%=flag%>,<%=ascdesc%>,null,null,null,null);" class="sear_btn"/>
                </form>
            </div>
            <div class="button_top">
                <ul>
                    <li><input name="uploadFile" type="button" value="拷贝" class="button_bg_3" onclick='javascript:copyArticles(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>);'/></li>
                    <li><input name="newFile" type="button" value="移动" class="button_bg_1" onclick='javascript:moveArticles(<%=columnID%>,<%=range%>,<%=currentPage%>,<%=doSearchFlag%>,<%=flag%>,<%=ascdesc%>);'/></li>
                </ul>
            </div>
            <div id="targetid" style="float: right;padding-right: 50px;">
                目的栏目名称：<input class="sear_txt_1" id="tcolumnnameid" name="columnname" size="22" placeholder="请双击输入框选择栏目" readonly ondblclick="selectTColumn();">
                <input type=hidden id="tcolumnid" name="column" size="5" readonly>
                <input type="radio" name="selarticles" value="0" checked>被选择的文章&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="selarticles" value="1">栏目下所有文章
                <input type="checkbox" name=execute value=0 checked>目的与原栏目属性不同时不执行操作&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
        </div>
        <div id="contentid">

        </div>
    </div>
    <jsp:include page="../include/tail.jsp"></jsp:include>
</div>
<script>
    layui.use(['element', 'tree', 'laydate'], function(){
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

<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.orderArticleListManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.server.CmsServer" contentType="text/html;charset=utf-8" %>
<%@ page import="com.bizwink.cms.util.Map" %>
<%@ page import="com.bizwink.cms.refers.IRefersManager" %>
<%@ page import="com.bizwink.cms.refers.RefersPeer" %>
<%@ page import="com.bizwink.cms.tree.*" %>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int msgno = ParamUtil.getIntParameter(request, "msgno", 1);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearch = ParamUtil.getBooleanParameter(request, "doSearch");
    int siteid=authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int sitetype = authToken.getSitetype();         //获取网站的类型，如果值为零，是普通网站，值为1是从父网站派生的子网站
    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);

    int parentColumnID = column.getParentID();

    String selectColumns = "";
    int useArticleType = column.getUseArticleType();
    IOrderArticleListManager orderArticleMgr = orderArticleListPeer.getInstance();
    List articleList = new ArrayList();
    int total = 0;
    if (SecurityCheck.hasPermission(authToken, 54) || SecurityCheck.hasPermission(authToken, 50)) {
        username = "";
    }

    String href = null;
    if (doSearch) {
        String value = "";
        String item = ParamUtil.getParameter(request, "item");
        href = "articles.jsp?doSearch=true&flag=" + flag + "&column=" + columnID + "&item=" + item;

        if (item.equals("publishtime")) {
            String publishtime1 = ParamUtil.getParameter(request, "publishtime1");
            String publishtime2 = ParamUtil.getParameter(request, "publishtime2");
            if (publishtime1 == null) publishtime1 = "";
            if (publishtime2 == null) publishtime2 = "";
            if (publishtime1 != "" || publishtime2 != "") value = publishtime1 + "," + publishtime2;
            href += "&publishtime1=" + publishtime1 + "&publishtime2=" + publishtime2;
        } else if (item.equals("all")) {
            String maintitle = ParamUtil.getParameter(request, "maintitle");
            String keyword = ParamUtil.getParameter(request, "keyword");
            String vicetitle = ParamUtil.getParameter(request, "vicetitle");
            String author = ParamUtil.getParameter(request, "author");
            String source = ParamUtil.getParameter(request, "source");
            String publishtime1 = ParamUtil.getParameter(request, "publishtime1");
            String publishtime2 = ParamUtil.getParameter(request, "publishtime2");
            int status = ParamUtil.getIntParameter(request, "status", 0);

            if (maintitle != null && maintitle.trim().length() > 0)
                value += "maintitle like '%" + maintitle + "%' and ";
            if (keyword != null && keyword.trim().length() > 0)
                value += "keyword like '%" + keyword + "%' and ";
            if (vicetitle != null && vicetitle.trim().length() > 0)
                value += "vicetitle like '%" + vicetitle + "%' and ";
            if (author != null && author.trim().length() > 0)
                value += "author like '%" + author + "%' and ";
            if (source != null && source.trim().length() > 0)
                value += "source like '%" + source + "%' and ";
            if (status > 0) {
                if (status == 1) value += "status=1 and auditflag=0 and pubflag=1 and ";  //新稿
                if (status == 2) value += "status=1 and auditflag=0 and pubflag=0 and ";  //已发布
                if (status == 3) value += "status=0 and auditflag=0 and ";                //未用
                if (status == 4) value += "status=1 and auditflag=1 and ";                //在审
                if (status == 5) value += "auditflag=2 and ";                             //退稿
            }

            if (CmsServer.getInstance().getDBtype().equals("oracle")) {
                if (publishtime1 != null && publishtime1.trim().length() > 0)
                    value += "publishtime >= TO_DATE('" + publishtime1 + "','YYYY-MM-DD') and ";
                if (publishtime2 != null && publishtime2.trim().length() > 0)
                    value += "publishtime <= TO_DATE('" + publishtime2 + "','YYYY-MM-DD') and ";
            } else {
                if (publishtime1 != null && publishtime1.trim().length() > 0)
                    value += "publishtime >= '" + publishtime1 + "' and ";
                if (publishtime2 != null && publishtime2.trim().length() > 0)
                    value += "publishtime <= '" + publishtime2 + "' and ";
            }

            if (value.length() > 0) value = value.substring(0, value.length() - 4);

            if (maintitle == null) maintitle = "";
            if (keyword == null) keyword = "";
            if (vicetitle == null) vicetitle = "";
            if (author == null) author = "";
            if (source == null) source = "";
            if (publishtime1 == null) publishtime1 = "";
            if (publishtime2 == null) publishtime2 = "";
            href += "&maintitle=" + maintitle + "&keyword=" + keyword + "&vicetitle=" + vicetitle + "&author=" + author + "&source=" + source + "&publishtime1=" + publishtime1 + "&publishtime2=" + publishtime2 + "&status=" + status;
        } else {
            value = ParamUtil.getParameter(request, item);
            if (value == null) value = "";
            href += "&" + item + "=" + value;
        }

        if (value != null && value.length() > 0) {
            try {
                if (SecurityCheck.hasPermission(authToken, 54)) {
                    articleList = orderArticleMgr.searchArticles(columnID, item, value, "*", start, range,siteid,flag,ascdesc);
                    total = orderArticleMgr.searchArticlesCount(columnID, item, value, "*",siteid);
                    System.out.println("total==" + total);
                } else {
                    articleList = orderArticleMgr.searchArticles(columnID, item, value, username, start, range,siteid,flag,ascdesc);
                    total = orderArticleMgr.searchArticlesCount(columnID, item, value, username,siteid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        href = StringUtil.replace(href, " ", "%20");
        href = StringUtil.replace(href, "\"", "&quot;");
    } else {
        try {
            articleList = orderArticleMgr.getArticlesByPage1(columnID, start, range, flag, username, selectColumns,siteid,ascdesc);
            total = orderArticleMgr.getArticleNum(columnID, username, selectColumns,siteid);
        } catch (Exception e) {
        }
    }

    int articleCount = articleList.size();
    //int allArticleNum = orderArticleMgr.getAllArticleNum(columnID, username,selectColumns,siteid);       //包括子栏目所有的文章
    int totalpages = 0;
    if (total > 0) {
        if (total % 20 == 0) {
            totalpages = total / 20;
        } else {
            totalpages = total / 20 + 1;
        }
    }

    Tree colTree = null;
    if (samsiteid == 0)
        colTree = TreeManager.getInstance().getSiteTree(siteid);
    else
        colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
    sitename = StringUtil.replace(sitename,"_",".");
    String cname = colTree.getChineseDirForArticle(colTree,columnID,sitename);
%>

<html>
<head>
    <title>所有文章</title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache" />
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script type="text/javascript" src="../js/jquery-1.4.4.min.js"></script>
    <script language="javascript" src="../js/setday.js"></script>
    <script language="javascript">
        function createArticle(columnID) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("createarticle.jsp?column=" + columnID, "", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function referArticle(columnID) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("referarticle.jsp?column=" + columnID, "", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function editArticle(columnID, articleID, range, start, from) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("editarticle.jsp?article=" + articleID + "&range=" + range + "&start=" + start + "&fromflag=" + from, "", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function Preview(articleID, columnId) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("preview.jsp?article=" + articleID + "&column=" + columnId, "Preview", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function checkNum(str) {
            var success = true;
            for (var i = 0; i < str.length; i++) {
                if (str.substring(i, i + 1) < '0' || str.substring(i, i + 1) > '9') {
                    success = false;
                    break;
                }
            }
            return success;
        }

        function search(item) {
            if (item == 'doclevel') {
                if (!checkNum(searchForm.doclevel1.value) || !checkNum(searchForm.doclevel2.value)) {
                    alert("权重应为整数！");
                    return;
                }
            }

            searchForm.action = "articles.jsp?item=" + item;
            searchForm.submit();
        }

        function moreSearch() {
            if (searchDiv.style.display == "")
                searchDiv.style.display = "none";
            else
                searchDiv.style.display = "";
        }

        function CA() {
            for (var i = 0; i < document.form1.elements.length; i++) {
                var e = document.form1.elements[i];
                if (e.name != 'allbox' && e.type == 'checkbox') {
                    e.checked = document.form1.allbox.checked;
                }
            }
        }

        function WebEdit() {
            var iWidth=1200;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("../webedit/index.jsp?column=<%=columnID%>&right=4", "WebEdit", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function uploadpics() {
            var iWidth=1200;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("../upload/uploadpics.jsp?column=<%=columnID%>", "UploadPics", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function golist(r, id, type) {
            var bor = (r - 1) * 20;
            if (isNumber(r)) {
                window.location = "articles.jsp?start=" + bor + "&column=" + id + "&flag=" + type;
            }
        }
        function isNumber(num) {
            var strRef = "1234567890";
            for (i = 0; i < num.length; i++) {
                tempChar = num.substring(i, i + 1);
                if (strRef.indexOf(tempChar, 0) == -1) {
                    alert("输入页码不正确！");
                    return false;
                }
            }
            return true;
        }

        function pushArticle(articleID, start, range) {
            var iWidth=650;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            window.open("addRelateListnew.jsp?articleid=" + articleID + "&start=" + start + "&range=" + range + "&fromflag=a", "", "width=" + iWidth + ",height=" + iHeight + ",top=0, left=0, toolbar=no, menubar=no, resizable=no,location=no, status=no");
        }

        function doaction(theform) {
            var articl_ids = '';
            $('input:checkbox[name=article]:checked').each(function(k){
                if(k == 0){
                    articl_ids = $(this).val();
                }else{
                    articl_ids += ','+$(this).val();
                }
            })
            var src=document.activeElement;
            if (src.name=='batchdel' && articl_ids!='') {
                var val = confirm("是否删除选择的文件？");
                if (val)
                    return true;
                else
                    return false;
            } else {
                var val = confirm("是否将选择的文件加入RSS？");
                if (val)
                    return true;
                else
                    return false;
            }
        }
    </script>
</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
            {"文章管理", ""},
            {StringUtil.gb2iso4View(cname), ""}
    };

    String[][] operations = {
            {"<a href=javascript:createArticle(" + columnID + ")>新建</a>&nbsp;", ""},
            {"<a href=javascript:uploadpics(" + columnID + ")>批量上传图片</a>", ""},
            {"上传文件", "../upload/createuploadfile.jsp?column=" + columnID},
            {"退稿", "returnarticle.jsp?column=" + columnID},
            {"在审", "auditarticle.jsp?column=" + columnID},
            {"未用", "unusedarticle.jsp?column=" + columnID},
            {"归档", "archivearticle.jsp?column=" + columnID},
            {"<a href=javascript:referArticle(" + columnID + ")>引用</a>&nbsp;", ""},
            {"<a href=javascript:WebEdit();>文件夹管理</a>", ""}
    };
%>
<%@ include file="../inc/titlebarforarticle.jsp" %>
<table cellpadding=1 cellspacing=1 width=100% border=0>
    <tr>
        <td align=center class=cur>
            <%
                if (msgno == 0)
                    out.println("页面发布成功！");
                else if (msgno == -1)
                    out.println("某栏目没有设置索引页模板！");
                else if (msgno == -2)
                    out.println("写文件时出现错误！");
                else if (msgno == -3)
                    out.println("生成文章列表文件时出现错误！");
                else if (msgno == -4)
                    out.println("某栏目模板的文件名为空！");
                else if (msgno == 2)
                    out.println("更新RSS成功！");
                else if (msgno == 3)
                    out.println("批量删除文件成功！");
            %>
        </td>
    </tr>
</table>
<%
    if (articleCount > 0) {
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% align=left class=line>");
        if (start - range >= 0) {
            if (href == null)
                out.println("<a href=articles.jsp?ascdesc=" + ascdesc + "&flag=" + flag + "&column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
            else     //非空，执行检索操作
                out.println("<a href=" + href + "&range=" + range + "&ascdesc=" + ascdesc + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        }
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            if (href == null)
                out.println(remain + "<a href=articles.jsp?ascdesc=" + ascdesc + "&flag=" + flag + "&column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
            else   //非空，执行检索操作
                out.println(remain + "<a href=" + href + "&range=" + range + "&ascdesc=" + ascdesc + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
<form action="doArticleRSS.jsp" method=Post name=form1 onsubmit="return doaction(this)">
<input type=hidden name=column value="<%=columnID%>">
<input type=hidden name=start value="<%=start%>">
<input type=hidden name=range value="<%=range%>">
<tr class=itm bgcolor="#dddddd" height=20>
    <td align=center width="4%">选择</td>
    <td align=center width="4%">状态</td>
    <td align=center width="4%">RSS</td>
    <td align=center width="25%"><a href="<%=(href==null)?"articles.jsp?flag=1&column="+columnID:href%>&ascdesc=<%=(ascdesc==0)?1:0%>">标题</a></td>
    <td align=center width="12%"><a href="<%=(href==null)?"articles.jsp?flag=4&column="+columnID:href%>&ascdesc=<%=(ascdesc==0)?1:0%>">发布时间</a></td>
    <td align=center width="12%"><a href="<%=(href==null)?"articles.jsp?flag=5&column="+columnID:href%>&ascdesc=<%=(ascdesc==0)?1:0%>">修改时间</a></td>
    <td align=center width="6%"><a href="<%=(href==null)?"articles.jsp?flag=2&column="+columnID:href%>&ascdesc=<%=(ascdesc==0)?1:0%>">主权重</a></td>
    <td align=center width="6%"><a href="<%=(href==null)?"articles.jsp?flag=3&column="+columnID:href%>&ascdesc=<%=(ascdesc==0)?1:0%>">次权重</a></td>
    <td align=center width="7%">编辑</td>
    <!--td align=center width="5%">附件</td-->
    <td align=center width="4%">预览</td>
    <td align=center width="4%">修改</td>
    <td align=center width="4%">删除</td>
    <td align=center width="4%">推送</td>
    <td align=center width="4%">发布</td>
</tr>
    <%

  String articleIds = "";
  for (int i = 0; i < articleCount; i++) {
    Article article = (Article) articleList.get(i);

    int multimediatype = article.getMultimediatype();
    int articleID = article.getID();
    int articleCID = article.getColumnID();
    boolean isown = true;
    if(articleCID != columnID) isown = false;
    int lockflag = article.getLockStatus();

    int pubflag = 0;
    IRefersManager refersMgr = RefersPeer.getInstance();

    if(isown)
        pubflag = article.getPubFlag();
    else{
        try {
            pubflag = refersMgr.getRefersArticlePubFlag(articleID, columnID,siteid);
        } catch (orderArticleException e) {
            e.printStackTrace();
        }
    }

    int docLevel = article.getDocLevel();
    int viceDocLevel = article.getViceDocLevel();
    int referID = article.getReferArticleID();
    int emptyflag = article.getNullContent();
    articleIds = articleIds + articleID + ",";

    String lockeditor = article.getLockEditor();
    String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
    String editor = article.getEditor();
    String publishtime = "&nbsp;";
    String lastupdated = "&nbsp;";

    String filename = article.getFileName();
    String ext = ".txt";
    if ((filename != null) && (filename.indexOf(".") != -1)) {
      ext = filename.substring(filename.lastIndexOf(".") + 1);
    }
    Map map = new Map();
    ext = map.getFileType(ext);

    if (article.getPublishTime() != null) publishtime = article.getPublishTime().toString().substring(0, 19);
    if (article.getLastUpdated() != null) lastupdated = article.getLastUpdated().toString().substring(0, 19);

    //是否正在审核中,为空,则表示不在审核中或已审核完毕
    String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
    out.println("<tr bgcolor=" + bgcolor + " class=itm onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "';\" height=25>" +
     "<td align=center>");
    out.print("<input type=checkbox name=article value=" + articleID + ">");
    out.println("</td>");

    if (emptyflag == 0) {
      if (article.getStatus() == 0) {
        out.println("<td align=center><img src=\"../images/button/wei.gif\" alt=\"未使用文章\"></td>");
      } else if (article.getStatus() == 1 || article.getStatus() == 5 || article.getStatus() == 4 || article.getStatus() == 6) {
        if (article.getAuditFlag() == 0)
          switch (pubflag) {
            case 0:
              out.println("<td align=center><img src=\"../images/button/yi.gif\" border=0 alt=\"已发布\"></td>");
              break;
            case 1:
                if(multimediatype == 1){
                   out.println("<td align=center><img src=\"../images/button/dmt.gif\" border=0 alt=\"多媒体\"></td>");
                }else{
                   out.println("<td align=center><img src=\"../images/button/xin.gif\" border=0 alt=\"新稿\"></td>");
                }
              break;
            case 2:
              out.println("<td align=center><font color=red>发布中</font></td>");
              break;
            default:
              break;
          }
        else if (article.getAuditFlag() == 1)
          out.println("<td align=center><img src=\"../images/button/shen.gif\" border=0 alt=\"在审\"></td>");
        else
          out.println("<td align=center><img src=\"../images/button/tui.gif\" border=0 alt=\"退稿\"></td>");
      } else if(article.getStatus() == 3){      //多媒体文件等待转换状态
        out.println("<td align=center><img src=\"../images/button/dai.gif\" border=0 alt=\"等待\"></td>");
      }  else {                                  //2是归档状态
        out.println("<td align=center><img src=\"../images/button/dang.gif\" border=0 alt=\"存档\"></td>");
      }
    } else {
      out.println("<td align=center><img src=\"../images/button/" + ext + "\" border=0 alt=\"上传文件\"></td>");
    }

     out.print("<td align=center>");
    if (article.getJoinRSS() == 1)
        out.print("R");
    else
        out.print("&nbsp;");
    out.println("</td>");

    if (emptyflag == 0){
        if ((referID > 0) || (!isown)) {
            String refersFrom = refersMgr.getRefersFrom(articleCID, articleID, columnID);
            if((refersFrom == null)||(refersFrom.equals(""))||(refersFrom.length()==0)) refersFrom = "";
            out.println("<td><a title='" + refersFrom + "'>" + maintitle + "</a>");
        } else {
            String refersTo = refersMgr.getRefersTo(articleID, columnID);
            if((refersTo == null)||(refersTo.equals(""))||(refersTo.length()==0)) refersTo = "";
            if (SecurityCheck.hasPermission(authToken, 51) || SecurityCheck.hasPermission(authToken, 54) || editor.equalsIgnoreCase(authToken.getUserID())){
                out.println("<td><a href=javascript:editArticle(" + columnID + "," + articleID + "," + range + "," + start + ",'c'); title='"+refersTo+"'>" + maintitle + "</a>");
            } else {
                out.println("<td>" + maintitle + "</td>");
            }
        }
    }else
        out.println("<td><a href=../upload/edituploadfile.jsp?article=" + articleID + ">" + maintitle + "</a>");

    if ((referID > 0) || (!isown)){
        useArticleType = refersMgr.getRefersArticleUseType(articleID, columnID);
        if(useArticleType==0)
            out.println("<font color=red style='font-size:9px'>(地址引用)</font>");
        else
            out.println("<font color=red style='font-size:9px'>(内容引用)</font>");
    }


    out.println("</td>");
    out.println("<td align=center>" + publishtime + "</td>");
    out.println("<td align=center>" + lastupdated + "</td>");
    out.println("<td align=center>" + docLevel + "</td>");
    out.println("<td align=center>" + viceDocLevel + "</td>");
    out.println("<td>" + editor + "</td>");
    //out.println("<td><a href=javascript:Preview(" + articleID + "," + columnID + ");>" + "附件" + "</a></td>");
    out.println("<td align=center><a href=javascript:Preview(" + articleID + "," + columnID + ");><img src=\"../images/button/view.gif\" border=0 alt=\"预览文章\"></a></td>");

    out.println("<td align=center>");
    if (SecurityCheck.hasPermission(authToken, 51) || SecurityCheck.hasPermission(authToken, 54) || editor.equalsIgnoreCase(authToken.getUserID())){
      if (lockflag == 0)            //没有锁定
        if (emptyflag == 0){        //非上传文件，内容不为空
          if ((referID > 0) || (!isown))
            out.println("&nbsp;");
          else
            if (article.getAuditFlag() == 0 || article.getAuditFlag() == 2)   //2是退稿状态   0--不需要审核
               out.println("<a href=javascript:editArticle(" + columnID + "," + articleID + "," + range + "," + start + ",'c');><img src=\"../images/button/edit.gif\" alt=\"编辑文章\" align=bottom border=0></a>");
            else
               out.println("&nbsp;");
        } else {                     //上传文件，内容为空
          if (article.getAuditFlag() == 0 || article.getAuditFlag() == 2)     //2是退稿状态  0--不需要审核
              out.println("<a href=../upload/edituploadfile.jsp?article=" + articleID + "><img src=\"../images/button/edit.gif\" align=bottom border=0 alt=\"编辑文章\"></a>");
          else
             out.println("&nbsp;");
         }
      else
      if (authToken.getUserID().equalsIgnoreCase(lockeditor) || SecurityCheck.hasPermission(authToken, 54)) {  //被当前用户锁定或当前用户为Webmaster
        if((referID > 0) || !isown)
            out.println("<img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"已被锁定\">");
        else
            out.println("<a href=javascript:editArticle(" + columnID + "," + articleID + "," + range + "," + start + ",'c');><img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"已被锁定\"></a>");
      } else {                  //被其他用户锁定
        if((referID > 0) || !isown)
            out.println("<img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"已被锁定\">");
        else
            out.println("<img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"已被锁定\">");
      }
      out.println("</td>");
    } else {
      out.println("&nbsp;");
    }

    //删除文章
    out.println("<td align=center>");
    if (SecurityCheck.hasPermission(authToken, 52) || SecurityCheck.hasPermission(authToken, 54) || editor.equalsIgnoreCase(authToken.getUserID()))
      if (article.getAuditFlag() == 0) {
           if (!isown)
              out.println("<a href=\"removeRefersArticle.jsp?flag=" + flag + "&ascdesc=" + ascdesc + "&article=" + articleID + "&start=" + start + "&range=" + range + "&column=" + columnID + "\"><img src=\"../images/button/del.gif\" align=bottom border=0 alt=\"删除文章\"></a>");
           else
              out.println("<a href=\"removearticle.jsp?flag=" + flag + "&ascdesc=" + ascdesc + "&article=" + articleID + "&start=" + start + "&range=" + range + "&fromflag=a\"><img src=\"../images/button/del.gif\" align=bottom border=0 alt=\"删除文章\"></a>");
      } else {
           out.println("&nbsp;");
      }
    else
      out.println("&nbsp;");
    out.println("</td>");

    //文章推送
    out.println("<td align=center>");
    if (SecurityCheck.hasPermission(authToken, 52) || SecurityCheck.hasPermission(authToken, 54) || editor.equalsIgnoreCase(authToken.getUserID()))
      if (article.getAuditFlag() == 0) {
          if ((referID > 0) || (!isown))
              out.println("&nbsp;");
          else
              out.println("<a href=\"javascript:pushArticle("+articleID+","+start+","+range+");\"><img src=\"../images/button/pub.gif\" align=bottom border=0 alt=\"文章推送\"></a>");
      } else {
          out.println("&nbsp;");
      }
    else
      out.println("&nbsp;");
    out.println("</td>");


    //文章发布
    if ((article.getStatus() == 1 || article.getStatus() == 4 || article.getStatus() == 5 || article.getStatus() == 6) && article.getAuditFlag() == 0 && emptyflag == 0)
      if (referID > 0)
        out.println("<td align=center>&nbsp;</td>");
      else{
        if(!isown) {
            if(useArticleType == 0)
                out.println("<td align=center>&nbsp;</td>");
            else
            if (SecurityCheck.hasPermission(authToken, 6)  || SecurityCheck.hasPermission(authToken, 54))     //用户是否有文章发布的权限
                out.println("<td align=center><a href=\"../publish/publish.jsp?flag=" + flag + "&ascdesc=" + ascdesc + "&column=" + columnID + "&article=" + articleID + "&start=" + start + "&range=" + range + "&source=3&isown="+isown+"\"><img src=../images/button/2scrollbox2.bmp border=0 alt=\"发布文章\"></a></td>");
            else
                out.println("<td align=center>&nbsp;</td>");
        } else if(multimediatype == 1){
            out.println("<td align=center>&nbsp;</td>");
        } else {
            if (SecurityCheck.hasPermission(authToken, 6)  || SecurityCheck.hasPermission(authToken, 54))     //用户是否有文章发布的权限
                out.println("<td align=center><a href=\"../publish/publish.jsp?flag=" + flag + "&ascdesc=" + ascdesc + "&column=" + columnID + "&article=" + articleID + "&start=" + start + "&range=" + range + "&source=3&isown="+isown+"\"><img src=../images/button/2scrollbox2.bmp border=0 alt=\"发布文章\"></a></td>");
            else
                out.println("<td align=center>&nbsp;</td>");
        }
      }
    else
      out.println("<td align=center>&nbsp;</td>");
    out.println("</tr>");
  }

  if((articleIds != null)&&(articleIds.indexOf(",") != -1)) articleIds = articleIds.substring(0, articleIds.length()-1);
%>
</table>
<%if (articleCount > 0) {%>
<table border="0" width="98%" cellspacing=0 cellpadding=0 align=center>
    <tr>
        <td>
            <input type=checkbox name=allbox value="CheckAll" onClick="CA();">全部选中
            &nbsp;&nbsp;<input type=submit name="updatecss" value="更新RSS" class=tine>
            &nbsp;&nbsp;<input type=submit name="batchdel" value="批量删除" class=tine>
        </td>
    </tr>
</table>
<input type="hidden" name="allArticleIds" value="<%=articleIds%>">
<%}%>
</form>
<form action="" method=Post name=searchForm>
    <input type=hidden name=doSearch value=true>
    <input type=hidden name=column value="<%=columnID%>">
    <table border="0" width="98%" cellspacing=0 cellpadding=0 align=center>
        <tr>
            <td width="40%">标&nbsp;&nbsp;题：&nbsp;&nbsp;<input name=maintitle size=30></td>
            <td width="20%"><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                   onclick="search('maintitle');">&nbsp;&nbsp;&nbsp;&nbsp;<a
                    href="javascript:moreSearch();">高级搜索</a>
            </td>
            <td width="40%">当前栏目的文章总数为：<%=total%>&nbsp;&nbsp;
                <%if (totalpages >= 1) {%>总<%=totalpages%>页&nbsp;第<%=(start + range) / 20%>页&nbsp;&nbsp;
                到第<input type="text" name="jump" value="<%=(start+range)/20%>" size="2">
                <a href="javascript:golist(document.all('jump').value,<%=columnID%>,<%=flag%>);">GO</a><%}%>
            </td>
        </tr>
        <tr>
            <td colspan=3></td>
        </tr>
    </table>
    <div id=searchDiv style="display:none">
        <table border="0" width="98%" cellspacing=0 cellpadding=0 align=center>
            <tr>
                <td width="40%">文章ID：&nbsp;&nbsp;<input name=id size=30></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('id');"></td>
            </tr>
            <tr>
                <td width="40%">关键字：&nbsp;&nbsp;<input name=keyword size=30></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('keyword');"></td>
            </tr>
            <tr>
                <td width="40%">副标题：&nbsp;&nbsp;<input name=vicetitle size=30></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('vicetitle');"></td>
            </tr>
            <tr>
                <td width="40%">作&nbsp;&nbsp;者：&nbsp;&nbsp;<input name=author size=30></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('author');"></td>
            </tr>
            <tr>
                <td width="40%">来&nbsp;&nbsp;源：&nbsp;&nbsp;<input name=source size=30></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('source');"></td>
            </tr>
            <tr>
                <td width="40%">发布日期：<input name=publishtime1 size=12 onfocus="setday(this)">&nbsp;到&nbsp;&nbsp;<input
                        name=publishtime2 size=12 onfocus="setday(this)"></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('publishtime');"></td>
            </tr>
            <tr>
                <td width="40%">文章状态：<input type=radio name=status value=1>新稿
                    <input type=radio name=status value=2>已发布
                    <input type=radio name=status value=3>未用
                    <input type=radio name=status value=4>在审
                    <input type=radio name=status value=5>退稿
                </td>
                <td width="10%"><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 " onclick="search('status');"></td>
                <td width="50%"><input class=tine type=button value=" 组合搜索 " onclick="search('all');"></td>
            </tr>
        </table>
    </div>
</form>

</BODY>
</html>

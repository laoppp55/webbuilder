<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.orderArticleListManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.server.CmsServer" contentType="text/html;charset=GBK" %>
<%@ page import="com.bizwink.cms.util.Map" %>
<%@ page import="com.bizwink.cms.refers.IRefersManager" %>
<%@ page import="com.bizwink.cms.refers.RefersPeer" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int msgno = ParamUtil.getIntParameter(request, "msgno", 1);
    boolean doSearch = ParamUtil.getBooleanParameter(request, "doSearch");
    int siteid=authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);

    String selectColumns = "";
    /*List selectColumnsList = columnMgr.getRefersColumnIds(columnID);
    for (int i = 0; i < selectColumnsList.size(); i++) {
        Column scolumn = (Column) selectColumnsList.get(i);
        selectColumns = selectColumns + scolumn.getScid() + ",";
    }
    if ((selectColumns != null) && (selectColumns.lastIndexOf(",") > 0))
        selectColumns = selectColumns.substring(0, selectColumns.length() - 1);
    */

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
        } else if (item.equals("doclevel")) {
            String doclevel1 = ParamUtil.getParameter(request, "doclevel1");
            String doclevel2 = ParamUtil.getParameter(request, "doclevel2");
            if (doclevel1 == null) doclevel1 = "";
            if (doclevel2 == null) doclevel2 = "";
            if (doclevel1 != "" || doclevel2 != "") value = doclevel1 + "," + doclevel2;
            href += "&doclevel1=" + doclevel1 + "&doclevel2=" + doclevel2;
        } else if (item.equals("all")) {
            String maintitle = ParamUtil.getParameter(request, "maintitle");
            String keyword = ParamUtil.getParameter(request, "keyword");
            String vicetitle = ParamUtil.getParameter(request, "vicetitle");
            String author = ParamUtil.getParameter(request, "author");
            String source = ParamUtil.getParameter(request, "source");
            String publishtime1 = ParamUtil.getParameter(request, "publishtime1");
            String publishtime2 = ParamUtil.getParameter(request, "publishtime2");
            int doclevel1 = ParamUtil.getIntParameter(request, "doclevel1", 0);
            int doclevel2 = ParamUtil.getIntParameter(request, "doclevel2", 0);
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

            if (doclevel1 > 0)
                value += "doclevel >= " + doclevel1 + " and ";
            if (doclevel2 > 0)
                value += "doclevel <= " + doclevel2 + " and ";

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
            href += "&maintitle=" + maintitle + "&keyword=" + keyword + "&vicetitle=" + vicetitle + "&author=" + author + "&source=" + source + "&publishtime1=" + publishtime1 + "&publishtime2=" + publishtime2 + "&doclevel1=" + doclevel1 + "&doclevel2=" + doclevel2 + "&status=" + status;
        } else {
            value = ParamUtil.getParameter(request, item);
            if (value == null) value = "";
            href += "&" + item + "=" + value;
        }
        if (value != null && value.length() > 0) {
            try {
                articleList = orderArticleMgr.searchArticles(columnID, item, value, username, start, range,siteid);
                total = orderArticleMgr.searchArticlesCount(columnID, item, value, username,siteid);
            } catch (Exception e) {
            }
        }
        href = StringUtil.replace(href, " ", "%20");
        href = StringUtil.replace(href, "\"", "&quot;");
    } else {
        try {
            System.out.println(System.currentTimeMillis());
            articleList = orderArticleMgr.getArticlesByPage(columnID, start, range, flag, username, selectColumns,siteid);
            total = orderArticleMgr.getArticleNum(columnID, username, selectColumns,siteid);
            System.out.println(System.currentTimeMillis());
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
    String cname = column.getCName();
%>

<html>
<head>
<title>所有文章</title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../style/global.css">
<script language="javascript" src="../js/setday.js"></script>
<script language="javascript">
function createArticle(columnID)
{
    window.open("createarticle.jsp?column=" + columnID, "", "width=935,height=650,left=5,top=5,status,scrollbars");
}

function referArticle(columnID)
{
    window.open("referarticle.jsp?column=" + columnID, "", "width=935,height=650,left=5,top=5,status,scrollbars");
}

function editArticle(columnID, articleID, range, start, from)
{
    window.open("editarticle.jsp?article=" + articleID + "&range=" + range + "&start=" + start + "&fromflag=" + from, "", "width=935,height=650,left=5,top=5,status,scrollbars");
}

function Preview(articleID, columnId)
{
    window.open("preview.jsp?article=" + articleID + "&column=" + columnId, "Preview", "width=800,height=600,left=5,top=5,scrollbars");
}

function checkNum(str)
{
    var success = true;
    for (var i = 0; i < str.length; i++)
    {
        if (str.substring(i, i + 1) < '0' || str.substring(i, i + 1) > '9')
        {
            success = false;
            break;
        }
    }
    return success;
}

function search(item)
{
    if (item == 'doclevel')
    {
        if (!checkNum(searchForm.doclevel1.value) || !checkNum(searchForm.doclevel2.value))
        {
            alert("权重应为整数！");
            return;
        }
    }

    searchForm.action = "articles.jsp?item=" + item;
    searchForm.submit();
}

function moreSearch()
{
    if (searchDiv.style.display == "")
        searchDiv.style.display = "none";
    else
        searchDiv.style.display = "";
}

function CA()
{
    for (var i = 0; i < document.form1.elements.length; i++)
    {
        var e = document.form1.elements[i];
        if (e.name != 'allbox' && e.type == 'checkbox')
        {
            e.checked = document.form1.allbox.checked;
        }
    }
}

function WebEdit()
{
    window.open("../webedit/index.jsp?column=<%=columnID%>&right=4", "WebEdit", "width=850,height=600,left=5,top=5,scrollbars");
}

function uploadpics()
{
    window.open("../upload/uploadpics.jsp?column=<%=columnID%>", "UploadPics", "width=350,height=200,left=200,top=200,scrollbars");
}

function golist(r, id, type) {
    var bor = (r - 1) * 20;
    if (isNumber(r)) {
        window.location = "articles.jsp?start=" + bor + "&column=" + id + "&flag=" + type;
    }
}
function isNumber(num) {
    var strRef = "1234567890";
    for (i = 0; i < num.length; i++)
    {
        tempChar = num.substring(i, i + 1);
        if (strRef.indexOf(tempChar, 0) == -1) {
            alert("输入页码不正确！");
            return false;
        }
    }
    return true;
}
function pushArticle(articleID, start, range) {
    window.open("addRelateListnew.jsp?articleid=" + articleID + "&start=" + start + "&range=" + range + "&fromflag=a", "", "width=650,height=400,top=0, left=0, toolbar=no, menubar=no, resizable=no,location=no, status=no");
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
    };
%>
<%@ include file="../inc/titlebar.jsp" %>
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
                out.println("<a href=articles.jsp?flag=" + flag + "&column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
            else
                out.println("<a href=" + href + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        }
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            if (href == null)
                out.println(remain + "<a href=articles.jsp?flag=" + flag + "&column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
            else
                out.println(remain + "<a href=" + href + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
<form action="doArticleRSS.jsp" method=Post name=form1>
<input type=hidden name=column value="<%=columnID%>">
<input type=hidden name=start value="<%=start%>">
<input type=hidden name=range value="<%=range%>">
<tr class=itm bgcolor="#dddddd" height=20>
    <td align=center width="4%">RSS</td>
    <td align=center width="4%">状态</td>
    <td align=center width="33%"><a href="articles.jsp?flag=1&column=<%=columnID%>">标题</a></td>
    <td align=center width="11%"><a href="articles.jsp?flag=0&column=<%=columnID%>">发布时间</a></td>
    <td align=center width="6%"><a href="articles.jsp?flag=2&column=<%=columnID%>">主权重</a></td>
    <td align=center width="6%"><a href="articles.jsp?flag=3&column=<%=columnID%>">次权重</a></td>
    <td align=center width="10%">编辑</td>
    <td align=center width="5%">附件</td>
    <td align=center width="4%">预览</td>
    <td align=center width="4%">修改</td>
    <td align=center width="4%">删除</td>
 
    <td align=center width="4%">&nbsp;</td>
</tr>
<%

  String articleIds = "";
  for (int i = 0; i < articleCount; i++) {
    Article article = (Article) articleList.get(i);

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
            //System.out.println("articleID=" + articleID);
            //System.out.println("columnID=" + columnID);
            //System.out.println("siteid=" + siteid);
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

    String filename = article.getFileName();
    String ext = ".txt";
    if ((filename != null) && (filename.indexOf(".") != -1)) {
      ext = filename.substring(filename.lastIndexOf(".") + 1);
    }
    Map map = new Map();
    ext = map.getFileType(ext);

    if (article.getPublishTime() != null) publishtime = article.getPublishTime().toString().substring(0, 16);

    //是否正在审核中,为空,则表示不在审核中或已审核完毕
    String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
    out.println("<tr bgcolor=" + bgcolor + " class=itm onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "';\" height=25><td align=center>");
    if (article.getJoinRSS() == 1)
        out.print("<input checked type=checkbox name=article value=" + articleID + ">");
    else
        out.print("<input type=checkbox name=article value=" + articleID + ">");
    out.println("</td>");

    //System.out.println("emptyflag=" + emptyflag);
    //System.out.println("article.getStatus()=" + article.getStatus());
    //System.out.println("article.getAuditFlag()=" + article.getAuditFlag());
    //System.out.println("pubflag=" + pubflag);
    if (emptyflag == 0) {
      if (article.getStatus() == 0) {
        out.println("<td align=center><font color=gray>未用</font></td>");
      } else if (article.getStatus() == 1) {
        if (article.getAuditFlag() == 0)
          switch (pubflag) {
            case 0:
              out.println("<td align=center><img src=\"../images/button/yi.gif\" border=0 alt=\"已发布\"></td>");
              break;
            case 1:
              out.println("<td align=center><img src=\"../images/button/xin.gif\" border=0 alt=\"新稿\"></td>");
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
      } else {
        out.println("<td align=center><img src=\"../images/button/dang.gif\" border=0 alt=\"存档\"></td>");
      }
    } else {
      out.println("<td align=center><img src=\"../images/button/" + ext + "\" border=0 alt=\"上传文件\"></td>");
    }

    if (emptyflag == 0){
        if ((referID > 0) || (!isown)) {
            String refersFrom = refersMgr.getRefersFrom(articleCID, articleID, columnID);
            if((refersFrom == null)||(refersFrom.equals(""))||(refersFrom.length()==0)) refersFrom = "";
            out.println("<td><a title='" + refersFrom + "'>" + maintitle + "</a>");
        } else {
            String refersTo = refersMgr.getRefersTo(articleID, columnID);
            if((refersTo == null)||(refersTo.equals(""))||(refersTo.length()==0)) refersTo = "";
            out.println("<td><a href=javascript:editArticle(" + columnID + "," + articleID + "," + range + "," + start + ",'c'); title='"+refersTo+"'>" + maintitle + "</a>");
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
    out.println("<td align=center>" + docLevel + "</td>");
    out.println("<td align=center>" + viceDocLevel + "</td>");
    out.println("<td>" + editor + "</td>");
    out.println("<td><a href=javascript:Preview(" + articleID + "," + columnID + ");>" + "附件" + "</a></td>");
    out.println("<td align=center><a href=javascript:Preview(" + articleID + "," + columnID + ");><img src=\"../images/button/view.gif\" border=0 alt=\"预览文章\"></a></td>");

    out.println("<td align=center>");
    if (SecurityCheck.hasPermission(authToken, 51) || SecurityCheck.hasPermission(authToken, 54) || editor.equalsIgnoreCase(authToken.getUserID()))
    {
      if (lockflag == 0)     //没有锁定
        if (emptyflag == 0)
          if ((referID > 0) || (!isown))
            out.println("&nbsp;");
          else
            out.println("<a href=javascript:editArticle(" + columnID + "," + articleID + "," + range + "," + start + ",'c');><img src=\"../images/button/edit.gif\" alt=\"编辑文章\" align=bottom border=0></a>");
        else
          out.println("<a href=../upload/edituploadfile.jsp?article=" + articleID + "><img src=\"../images/button/edit.gif\" align=bottom border=0 alt=\"编辑文章\"></a>");
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
    out.println("<td align=center>");
    if (SecurityCheck.hasPermission(authToken, 52) || SecurityCheck.hasPermission(authToken, 54) || editor.equalsIgnoreCase(authToken.getUserID()))
      if (!isown)
        out.println("<a href=\"removeRefersArticle.jsp?article=" + articleID + "&start=" + start + "&range=" + range + "&column=" + columnID + "\"><img src=\"../images/button/del.gif\" align=bottom border=0 alt=\"删除文章\"></a>");
      else
        out.println("<a href=\"removearticle.jsp?article=" + articleID + "&start=" + start + "&range=" + range + "&fromflag=a\"><img src=\"../images/button/del.gif\" align=bottom border=0 alt=\"删除文章\"></a>");
    else
      out.println("&nbsp;");
    out.println("</td>");

   
    if (article.getStatus() == 1 && article.getAuditFlag() == 0 && emptyflag == 0)
      if (referID > 0)
        out.println("<td align=center>&nbsp;</td>");
      else{
        if(!isown) {
            if(useArticleType == 0)
                out.println("<td align=center>&nbsp;</td>");
            else
                out.println("<td align=center>&nbsp;</td>");
        } else
            out.println("<td align=center>&nbsp;</td>");
      }
    else
      out.println("<td align=center>&nbsp;</td>");
    out.println("</tr>");
  }

  if((articleIds != null)&&(articleIds.indexOf(",") != -1))
    articleIds = articleIds.substring(0, articleIds.length()-1);

%>
</table>
<%if (articleCount > 0) {%>
<table border="0" width="98%" cellspacing=0 cellpadding=0 align=center>
    <tr>
        <td><input type=checkbox name=allbox value="CheckAll" onClick="CA();">全部选中
            &nbsp;&nbsp;<input type=submit value=" 更新RSS " class=tine>
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
                <td width="40%">权重范围：<input name=doclevel1 size=12>&nbsp;到&nbsp;&nbsp;<input name=doclevel2 size=12>
                </td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('doclevel');"></td>
            </tr>
            <tr>
                <td width="40%">文章状态：<input type=radio name=status value=1>新稿
                    <input type=radio name=status value=2>已发布
                    <input type=radio name=status value=3>未用
                    <input type=radio name=status value=4>在审
                    <input type=radio name=status value=5>退稿
                </td>
                <td width="10%"><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                       onclick="search('status');"></td>
                <td width="50%"><input class=tine type=button value=" 组合搜索 " onclick="search('all');"></td>
            </tr>
        </table>
    </div>
</form>

</BODY>
</html>

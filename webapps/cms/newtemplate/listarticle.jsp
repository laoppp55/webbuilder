<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int modeltype = ParamUtil.getIntParameter(request, "modeltype", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String msg = ParamUtil.getParameter(request, "msg");
    String content = ParamUtil.getParameter(request, "content");
    String extname = null;
    String CName = null;
    String columnURL = null;

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);

    if (column !=null) {
        extname = column.getExtname();
        CName = StringUtil.gb2iso4View(column.getCName());
        columnURL = column.getDirName();
    } else{
        CName = "程序模板";
        columnURL = "/_prog/";
    }

    if (modeltype == 4 || modeltype == 5)
        extname = "wml";

    IArticleManager articleMgr = ArticlePeer.getInstance();
    int total = articleMgr.getLinkArticlesNum(columnID, content, modeltype);
    List articleList = articleMgr.getLinkArticles(columnID, start, range, content, modeltype);
    int articleCount = articleList.size();
%>

<html>
<head>
    <title>Articles</title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
</head>
<script language="JavaScript">
    var linkType;
    var DHTMLSafe;

    function linkit(flag)
    {
        if(flag == 1){
            window.returnValue = linkform.oHref.value;
            window.parent.close();
        }else if(flag == 2){
            window.returnValue = "";
            window.parent.close();
        }
    }

    function PreviewArticle(articleID)
    {
        window.open("../article/preview.jsp?article=" + articleID, "Preview", "width=800,height=600,left=0,top=0,scrollbars");
    }

    function PreviewTemplate(templateID, isArticle, columnID)
    {
        window.open("previewTemplate.jsp?column=" + columnID + "&template=" + templateID + "&isArticle=" + isArticle, "Preview", "width=800,height=600,left=0,top=0,scrollbars");
    }

    function selectthis(para)
    {
        linkform.oHref.value = para.value;
    }

    function setlinkrules(columnID)
    {
        window.open("addlinkrules.jsp?column=" + columnID, "setLinkRules", "width=800,height=600,left=0,top=0,scrollbars");
    }
</script>

<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<%
    if (msg != null) out.println("<span class=cur>" + msg + "</span>");
    if (articleCount > 0) {
        if (content == null) content = "";
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% align=left class=line>");
        if (start - range >= 0)
            out.println("<a href=listarticle.jsp?column=" + columnID + "&range=" + range + "&start=" + (start - range) + "&content=" + content + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            out.println(remain + "<a href=listarticle.jsp?column=" + columnID + "&range=" + range + "&start=" + (start + range) + "&content=" + content + "><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
    <tr>
        <td colspan=5>当前所在栏目-->><font color=red><%=CName%>
        </font></td>
    </tr>
    <tr class=itm bgcolor="#dddddd">
        <td align=center width="15%">选中该链接</td>
        <td align=center width="50%">标题</td>
        <td align=center width="15%">修改时间</td>
        <td align=center width="10%">编辑</td>
        <td align=center width="10%">预览</td>
    </tr>
    <%
        for (int i = 0; i < articleCount; i++) {
            Article article = (Article) articleList.get(i);

            int articleID = article.getID();       //文章ID或模板ID
            String editor = article.getEditor();   //文章或模板的编辑者
            String bgcolor = (i % 2 == 0) ? "#ffffcc" : "#eeeeee";
            String lastUpdated = article.getLastUpdated().toString().substring(0, 10); //文章或模板的最后修改时间

            columnID = article.getColumnID();      //文章或模板所属栏目ID

            //获得文章创建时间，生成发布路径中的一部分
            String createdate_path = "";
            if(article.getCreateDate() != null){
                createdate_path = article.getCreateDate().toString();
                createdate_path = createdate_path.substring(0, 10).replaceAll("-","") + "/";
            }

            column = columnManager.getColumn(columnID);

            if (modeltype == 4 || modeltype == 5)
                extname = "wml";
            else {
                extname = column.getExtname();
                if (extname == null) extname = "html";
            }

            int isArticleTemplate = 0;             //是文章模板还是栏目模板或首页模板
            String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
            boolean isTemplate = article.getIsTemplate();    //是文章还是模板
            if (isTemplate) {
                int status = article.getStatus();      //是否为默认模板
                maintitle = article.getMainTitle();    //模板中文名称
                if (maintitle == null) maintitle = "";
                maintitle = StringUtil.gb2iso4View(maintitle);
                isArticleTemplate = article.getIsArticleTemplate();

                if (isArticleTemplate == 0) {
                    if (status == 1)
                        maintitle = "<font color=red>" + maintitle + "(默认栏目模板)</font>";
                    else
                        maintitle = "<font color=red>" + maintitle + "(栏目模板)</font>";
                } else {
                    if (status == 1)
                        maintitle = "<font color=red>" + maintitle + "(默认首页模板)</font>";
                    else
                        maintitle = "<font color=red>" + maintitle + "(首页模板)</font>";
                }
            }

            out.println("<tr bgcolor=" + bgcolor + "class=itm>");
            if (isTemplate) {
                out.println("<td align=center><input type=radio name=selectedLink onclick=selectthis(this) value=" + columnURL + article.getFileName() + "." + extname + "></td>");
            } else {
                if (article.getNullContent() == 0)
                    out.println("<td align=center><input type=radio name=selectedLink onclick=selectthis(this) value=" + columnURL + createdate_path + articleID + "." + extname + "></td>");
                else
                    out.println("<td align=center><input type=radio name=selectedLink onclick=selectthis(this) value=" + columnURL + createdate_path + "download/" + article.getFileName() + "></td>");
            }
            out.println("<td>" + maintitle + "</td>");
            out.println("<td>" + lastUpdated + "</td>");
            out.println("<td>" + editor + "</td>");
            out.println("<td align=center>");
            if (isTemplate) {
                out.println("<a href=javascript:PreviewTemplate(" + articleID + "," + isArticleTemplate + "," + columnID + ");><img src=../images/button/view.gif border=0></a>");
            } else {
                if (article.getContent() != null)
                    out.println("<a href=javascript:PreviewArticle(" + articleID + ");><img src=../images/button/view.gif border=0></a>");
                else
                    out.println("<img src=../images/button/view.gif border=0>");
            }
            out.println("</td></tr>");
        }
    %>
    <tr>
        <td align=center><input type="radio" name=selectedLink onclick=selectthis(this)
                                value="<%=columnURL%>"></td>
        <td colspan=4><font color=blue>选择当前栏目所在链接</font></td>
    </tr>
</table>

<table cellpadding="1" cellspacing="1" border="0">
    <form name="linkform" method=post action="listarticle.jsp?column=<%=columnID%>&modeltype=<%=modeltype%>">
        <input type=hidden name=doSearch value=true>
        <tr>
            <td colspan=2>输入标题:<input name=content size=35>&nbsp;<input type=submit value=" 搜索 "></td>
        </tr>
        <tr>
            <td>连接地址:</td>
            <td><input name="oHref" size="35" value="/" ondblclick="javascript:setlinkrules(<%=columnID%>)"></td>
        </tr>
    </form>
</table>

<input type=button value="  确定  " onclick="linkit(1);">&nbsp;&nbsp;
<input type=button value="  取消  " onclick="linkit(2);">

</BODY>
</html>
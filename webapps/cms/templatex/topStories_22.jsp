<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid=authToken.getSiteID();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 10);

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = null;
    String CName = null;
    if (columnID > 0) {
        column = columnManager.getColumn(columnID);
        CName = StringUtil.gb2iso4View(column.getCName());
    } else {
        CName = "程序模板";
    }
    IArticleManager articleMgr = ArticlePeer.getInstance();
    // int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int sitetype = authToken.getSitetype();
    List articleList=new ArrayList();
    int total=0;
    if(sitetype==0){
        articleList = articleMgr.sharegetTopStoriesArticles(columnID, start, range,siteid,samsiteid);
        total = articleMgr.sharegetTopStoriesArticlesNum(columnID,siteid,samsiteid);
    }else{
        articleList = articleMgr.getTopStoriesArticles(columnID, start, range,siteid);
        total = articleMgr.getTopStoriesArticlesNum(columnID,siteid);
    }
    int articleCount = articleList.size();
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="style.css">
    <script language="javascript">
        function Preview(articleID)
        {
            window.open("../article/preview.jsp?article=" + articleID, "Preview", "width=800,height=600,left=5,top=5,scrollbars");
        }

        function selects(articleID, maintitle)
        {
            var el = window.parent.frames['main2'].document.getElementById('selectedArticle');
            var optionLen = el.options.length;

            i = 0;
            var endFlag = true;
            while (i < optionLen && endFlag)
            {
                if (el.options[i].value == articleID)
                    endFlag = false;
                i++;
            }

            if (endFlag)
                window.parent.frames['main2'].document.getElementById('selectedArticle').options.add(new Option(maintitle, articleID, false, false));
        }
    </script>
</head>
<body topmargin=0 leftmargin=4>
<span class="tabstyleb"><b>选择具体热点文章</b></span>
<%
    if (articleCount >= 0) {
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% align=left class=line>");
        if (start - range >= 0)
            out.println("<a href=topStories_22.jsp?column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            out.println(remain + "<a href=topStories_22.jsp?column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>
<table width="100%" border=0 cellpadding=2 cellspacing=1 bgcolor="#CCCCCC">
    <tr bgcolor="#F5F5F5">
        <td colspan=4>当前所在栏目：<font color=red><%=CName%>
        </font></td>
    </tr>
    <tr bgcolor="#F5F5F5">
        <td align=center width="10%">选中</td>
        <td align=center width="60%">标题</td>
        <td align=center width="20%">发布时间</td>
        <td align=center width="10%">预览</td>
    </tr>
    <%
        for (int i = 0; i < articleList.size(); i++) {
            Article article = (Article) articleList.get(i);
            int articleID = article.getID();
            String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
            String createdate = article.getPublishTime().toString().substring(0, 10);
            String bgcolor = (i % 2 == 0) ? "#ffffcc" : "#eeeeee";
            maintitle = StringUtil.replace(maintitle, "'", "`");
            maintitle = StringUtil.replace(maintitle, "\"", "&quot;");

            out.println("<tr bgcolor=" + bgcolor + ">");
            out.println("<td align=center><input type=button class=tine value=add onclick=\"selects(" + articleID + ",'" + maintitle + "');\"></td>");
            out.println("<td>" + maintitle + "</td>");
            out.println("<td align=center>" + createdate + "</td>");
            out.println("<td align=center>");
            if (article.getNullContent() == 0)
                out.println("<a href=\"javascript:Preview(" + articleID + ");\"><img src='../images/preview.gif' border=0></a>");
            else
                out.println("&nbsp;");
            out.println("</td></tr>");
        }
    %>
</table>
</body>
</html>
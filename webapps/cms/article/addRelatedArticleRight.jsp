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
    if(columnID==-1)
    {
        columnID=0;
    }
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String msg = ParamUtil.getParameter(request, "msg");
    int from = ParamUtil.getIntParameter(request, "from", 0);
    int param = ParamUtil.getIntParameter(request, "param", -1);

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String CName = StringUtil.gb2iso4View(column.getCName());

    IArticleManager articleMgr = ArticlePeer.getInstance();
    int total = articleMgr.getRelatedArticlesNum(columnID,siteid);
    List articleList = articleMgr.getRelatedArticles(columnID, start, range,siteid);
    int articleCount = articleList.size();

%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
</head>
<script language="JavaScript">
    function PreviewArticle(articleID) {
        window.open("../article/preview.jsp?article=" + articleID, "Preview", "width=800,height=600,left=0,top=0,scrollbars");
    }

    <%if(from == 0){%>
    function SelectArticle(len) {
        var aid = document.forms["myform"].articleID;
        if (len == 1) {
            var retval = aid.value;
            window.parent.opener.top.frames["cmsright"].document.getElementById("articleID").innerHTML = retval.substring(0, retval.indexOf(","));
            window.parent.opener.top.frames["cmsright"].document.getElementById("cname").innerHTML = retval.substring(retval.indexOf(",") + 1, retval.lastIndexOf(","));
            window.parent.opener.top.frames["cmsright"].document.getElementById("maintitle").innerHTML = retval.substring(retval.lastIndexOf(",") + 1);
            window.parent.opener.top.frames["cmsright"].document.getElementById("article").value = retval.substring(0, retval.indexOf(","));
        } else if (len > 1) {
            var i = 0;
            var isSelected = false;
            for (i = 0; i < len; i++) {
                if (aid[i].checked) {
                    isSelected = true;
                    break;
                }
            }
            if (!isSelected)
                return;
            else {
                var retval = aid[i].value;
                window.parent.opener.top.frames["cmsright"].document.getElementById("articleID").innerHTML = retval.substring(0, retval.indexOf(","));
                window.parent.opener.top.frames["cmsright"].document.getElementById("cname").innerHTML = retval.substring(retval.indexOf(",") + 1, retval.lastIndexOf(","));
                window.parent.opener.top.frames["cmsright"].document.getElementById("maintitle").innerHTML = retval.substring(retval.lastIndexOf(",") + 1);
                window.parent.opener.top.frames["cmsright"].document.getElementById("article").value = retval.substring(0, retval.indexOf(","));
            }
        }
        top.close();
    }
    <%}else{%>
    function SelectArticle(len) {
        var aid;
        if (len == 1) {
            if (!document.getElementById('articleID').checked)
                return;
            else
                aid = document.getElementById('articleID').value;
        } else if (len > 1) {
            var i = 0;
            var isSelected = false;

            for(var i=0;i<len;i++){
                if(document.getElementsByName('articleID')[i].checked){
                    aid = document.getElementsByName('articleID')[i].value;
                    isSelected = true;
                    break;
                }
            }
            if (!isSelected) return;
        }

        var param = <%=param%>;
        alert(aid);
        if(param == 0)
            window.parent.opener.document.getElementById('docLevel').value = aid.substring(0, aid.indexOf(","));
        else if(param == 1)
            window.parent.opener.document.getElementById('viceDocLevel').value = aid.substring(0, aid.indexOf(","));
        else if(param == 2)
            window.parent.opener.document.getElementById('relatedID').value = aid.substring(0, aid.indexOf(","));

        top.close();
    }
    <%}%>
    function cal() {
        top.close();
    }
</script>

<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<%
    if (msg != null) out.println("<span class=cur>" + msg + "</span>");
    if (articleCount > 0) {
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% align=left class=line>");
        if (start - range >= 0)
            out.println("<a href=addRelatedArticleRight.jsp?column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            out.println(remain + "<a href=addRelatedArticleRight.jsp?column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
    <form action="" name="myform">
        <tr>
            <td colspan=5>当前所在栏目-->><font color=red><%=CName%>
            </font></td>
        </tr>
        <tr class=itm bgcolor="#dddddd">
            <td align=center width="7%">选中</td>
            <td align=center width="8%">序号</td>
            <td align=center width="60%">标题</td>
            <td align=center width="15%">修改时间</td>
            <td align=center width="10%">预览</td>
        </tr>
        <%
            for (int i = 0; i < articleCount; i++) {
                Article article = (Article) articleList.get(i);

                int articleID = article.getID();       //文章ID或模板ID
                String bgcolor = (i % 2 == 0) ? "#ffffcc" : "#eeeeee";
                String lastUpdated = article.getPublishTime().toString().substring(0, 10); //文章或模板的最后修改时间
                String maintitle = StringUtil.gb2iso4View(article.getMainTitle());

                out.println("<tr bgcolor=" + bgcolor + "class=itm>");
                out.println("<td align=center><input type=radio id=articleID name=articleID value='" + articleID + "," + CName + "," + maintitle + "'></td>");
                out.println("<td>" + articleID + "</td>");
                out.println("<td>" + maintitle + "</td>");
                out.println("<td>" + lastUpdated + "</td>");
                out.println("<td align=center>");
                if (article.getNullContent() == 0)
                    out.println("<a href=javascript:PreviewArticle(" + articleID + ");><img src=../images/preview.gif border=0></a>");
                else
                    out.println("<img src=../images/preview.gif border=0>");
                out.println("</td></tr>");
            }
        %>
    </form>
</table>

<p align=center>
    <input type=button value="  确定  " onclick="SelectArticle(<%=articleCount%>);" <%if(articleCount==0){%>disabled<%}%>>&nbsp;&nbsp;
    <input type=button value="  取消  " onclick="cal();">
</p>
</BODY>
</html>
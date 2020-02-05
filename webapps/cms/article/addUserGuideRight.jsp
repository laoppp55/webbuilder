<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String msg = ParamUtil.getParameter(request, "msg");

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
    <link rel=stylesheet type=text/css href="../style/global.css"></head>
<script language="JavaScript">
    function PreviewArticle(articleID)
    {
        window.open("../article/preview.jsp?article="+articleID,"Preview","width=800,height=600,left=0,top=0,scrollbars");
    }

    function NewSelectArticle(len)
    {
        var msgstr = "";
        var result = "";
        for(var e=0; e<selname.articleID.length; e++) {
            if(selname.elements[e].checked == true) {
                msgstr = selname.elements[e].value;
                posi = msgstr.indexOf(",");
                value = msgstr.substring(0,posi);
                text = msgstr.substring(posi+1);
                result = result + "a" + value + "-" + text + "\r\n";
            }
        }

        var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
        if (isMSIE) {
            window.returnValue = result;
            window.parent.close();
        }else{
            if (result != "" && result != undefined)
            {
                alert(result);
                result = result.substring(0, result.length-2);
                posi = result.indexOf("-");
                value = result.substring(0,posi);
                text = result.substring(posi+1);
                window.parent.opener.document.createForm.notesArticle.value = value;
                window.parent.opener.document.createForm.userguide.value = text;
            }
            top.close();
        }
    }
</script>

<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<%
    if (msg!=null) out.println("<span class=cur>" + msg + "</span>");
    if (articleCount > 0)
    {
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% align=left class=line>");
        if (start - range >= 0)
            out.println("<a href=addUserGuideRight.jsp?column="+columnID+"&range="+range+"&start="+(start-range)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+start);
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total)
        {
            int remain = ((start+range-total)<range)?(total-start-range):range;
            out.println(remain+"<a href=addUserGuideRight.jsp?column="+columnID+"&range="+range+"&start="+(start+range)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
    <form name=selname>
        <tr>
            <td colspan=5>当前所在栏目-->><font color=red><%=CName%></font></td>
        </tr>
        <tr class=itm bgcolor="#dddddd">
            <td align=center width="15%">选中</td>
            <td align=center width="60%">标题</td>
            <td align=center width="15%">修改时间</td>
            <td align=center width="10%">预览</td>
        </tr>
            <%
  for (int i=0; i<articleCount; i++)
  {
    Article article = (Article)articleList.get(i);

    int articleID = article.getID();       //文章ID或模板ID
    String bgcolor = (i%2==0)?"#ffffcc":"#eeeeee";
    String lastUpdated = article.getPublishTime().toString().substring(0,10); //文章或模板的最后修改时间
    String maintitle = StringUtil.gb2iso4View(article.getMainTitle());

    out.println("<tr bgcolor=" + bgcolor + "class=itm>");
    out.println("<td align=center><input type=radio name=articleID" + " value='"+articleID+","+maintitle+"'></td>");
    out.println("<td>" + maintitle + "</td>");
    out.println("<td>" + lastUpdated + "</td>");
    out.println("<td align=center>");
    if (article.getNullContent() == 0)
      out.println("<a href=javascript:PreviewArticle("+articleID+");><img src=../images/preview.gif border=0></a>");
    else
      out.println("<img src=../images/preview.gif border=0>");
    out.println("</td></tr>");
  }
%>
        <!--tr>
          <td>关联栏目：</td>
          <td><select ID="columnname" name="colnames" style="width:300" TITLE="相关栏目"></select></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr-->
</table>
<p align=center>
<form name="attechform" action="" method="post" enctype="multipart/form-data">
    <table border="0" cellspace="0">
        <tr>
            <td>附件名称：</td>
            <td><input type="text" name="name" value=""></td>
        </tr>
        <tr>
            <td>附件介绍：</td>
            <td><input type="text" name="brief" value=""></td>
        </tr>
        <tr>
            <td>附    件：</td>
            <td><input type="file" name="attfile" value=""></td>
        </tr>
        <tr>
            <td><input type=button value="  确定  "></td>
            <td>    <input type=button value="  取消  " onclick="top.close();">            </td>
        </tr>
    </table>
</form>
</p>
</BODY>
</html>
<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
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

  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int start = ParamUtil.getIntParameter(request, "start", 0);
  int range = ParamUtil.getIntParameter(request, "range", 20);
  String msg = ParamUtil.getParameter(request, "msg");

  IColumnManager columnManager = ColumnPeer.getInstance();
  Column column = columnManager.getColumn(columnID);
  String extname = column.getExtname();
  String CName = StringUtil.gb2iso4View(column.getCName());
  String columnURL = column.getDirName();

  int noContent = 3;   //表示取出所有的文章，不管是否有内容
  IArticleManager articleMgr = ArticlePeer.getInstance();
  int total = articleMgr.getArticleNum(columnID, noContent);
  List articleList = articleMgr.getLinkArticles(columnID, start, range);
  int articleCount = articleList.size();
%>

<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../style/global.css"></head>
<script language="JavaScript">
function PreviewArticle(articleID)
{
  window.open("../article/preview.jsp?article="+articleID,"Preview","width=800,height=600,left=0,top=0,scrollbars");
}

function PreviewTemplate(templateID, isArticle, columnID)
{
  window.open("previewTemplate.jsp?column="+columnID+"&template="+templateID+"&isArticle="+isArticle,"Preview","width=800,height=600,left=0,top=0,scrollbars");
}

function SelectThis(para, url)
{
  Title.value = para.value;
  URL.value = url;
}

function SelectTitle()
{
  if (Title.value == "" || URL.value == "")
    return;

  var retstr = "<a href='" + URL.value + "'";
  if (oTarget.value != "")
    retstr = retstr + " target=" + oTarget.value;
  if (oClass.value != "")
    retstr = retstr + " class=" + oClass.value;
  retstr = retstr + ">" + Title.value + "</a>";

  window.returnValue = retstr;
  window.parent.close();
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
      out.println("<a href=listarticle.jsp?column="+columnID+"&range="+range+"&start="+(start-range)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+start);
    out.println("</td><td width=50% align=right class=line>");
    if (start + range < total)
    {
      int remain = ((start+range-total)<range)?(total-start-range):range;
      out.println(remain+"<a href=listarticle.jsp?column="+columnID+"&range="+range+"&start="+(start+range)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
    }
    out.println("</td></tr></table>");
  }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
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
        String lastUpdated = article.getLastUpdated().toString().substring(0,10); //文章或模板的最后修改时间

        columnID = article.getColumnID();      //文章或模板所属栏目ID
        column = columnManager.getColumn(columnID);
        extname = column.getExtname();
        if (extname == null) extname="html";

        int isArticleTemplate = 0;             //是文章模板还是栏目模板或首页模板
        String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
        boolean isTemplate = article.getIsTemplate();    //是文章还是模板
	       String tempTitle = "";
        if (isTemplate)
        {
          int status = article.getStatus();      //是否为默认模板
          maintitle = article.getMainTitle();    //模板中文名称
          if (maintitle == null)  maintitle = "";
          maintitle = StringUtil.gb2iso4View(maintitle);
	         tempTitle = maintitle;
          isArticleTemplate = article.getIsArticleTemplate();
          if (isArticleTemplate == 3)
          {
            maintitle = "<font color=red>" + maintitle + "(专题模板)</font>";
          }
          else
          {
            if (status == 1)
              maintitle = "<font color=red>" + maintitle + "(默认模板)</font>";
            else
              maintitle = "<font color=red>" + maintitle + "(模板)</font>";
          }
        }

        out.println("<tr bgcolor=" + bgcolor + "class=itm>");
        if (isTemplate)
        {
          out.println("<td align=center><input type=radio name=selectedLink onclick=SelectThis(this,'"+columnURL+article.getFileName()+"."+extname+"') value='"+tempTitle+"'></td>");
        }
        else
        {
          if (article.getNullContent() == 0)
            out.println("<td align=center><input type=radio name=selectedLink onclick=SelectThis(this,'"+columnURL+articleID+"."+extname+"') value='"+maintitle+"'></td>");
          else
            out.println("<td align=center><input type=radio name=selectedLink onclick=SelectThis(this,'"+columnURL+article.getFileName()+"') value='"+maintitle+"'></td>");
        }
        out.println("<td>" + maintitle + "</td>");
        out.println("<td>" + lastUpdated + "</td>");
        out.println("<td align=center>");
        if (isTemplate)
        {
          out.println("<a href=javascript:PreviewTemplate("+articleID+","+isArticleTemplate+","+columnID+");><img src=../images/preview.gif border=0></a>");
        }
        else
        {
          if (article.getContent() != null)
            out.println("<a href=javascript:PreviewArticle("+articleID+");><img src=../images/preview.gif border=0></a>");
          else
            out.println("<img src=../images/preview.gif border=0>");
        }
        out.println("</td></tr>");
    }
%>
    <tr>
      <td align=center><input type="radio" name=selectedLink onclick=SelectThis(this,'<%=columnURL+"index."+extname%>') value="<%=CName%>"></td>
      <td colspan=4><font color=blue>选择当前栏目名称</font></td>
    </tr>
</table>

<table cellpadding="1" cellspacing="2" border="0">
  <tr>
    <td class="styled"><span style="font-size:10pt">标题:</span></td>
    <td><input type="text" name="Title" size="35"><input type="hidden" name="URL"></td>
  </tr>
  <tr>
    <td class="styled"><span style="font-size:10pt">窗口形式:</span></td>
    <td><select name="oTarget"><option value="">原始窗口<option value="_blank">弹出窗口</select></td>
  </tr>
  <tr>
    <td class="styled"><span style="font-size:10pt">标识符:</span></td>
    <td><input type="text" name="oClass" size="8"></td>
  </tr>
</table>
<p align=center>
<input type=button value="  确定  " onclick="SelectTitle();" <%if(columnID==0){%>disabled<%}%>>&nbsp;&nbsp;
<input type=button value="  取消  " ONCLICK="window.parent.close();">
</p>
</BODY>
</html>
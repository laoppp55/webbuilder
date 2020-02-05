<%@ page import="com.bizwink.search.SearchFilesServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>

<%
    String sitename = request.getServerName();  //site name
    sitename = StringUtil.replace(sitename,".","_");
    int pages = ParamUtil.getIntParameter(request,"currpage",1);

    int range = 20;
    String content = ParamUtil.getParameter(request,"searchcontent");

    //去掉输入内容中的<script>之间的内容
    List list = new ArrayList();
    int num = 0;

    if (content != null) {
        Pattern p = Pattern.compile("<script[^>]*>[\\d\\D]*?</script>", Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = p.matcher(content);
        String matchStr=null;
        String tbuf = content;
        while (matcher.find()) {
            matchStr = tbuf.substring(matcher.start(), matcher.end());
            content = content.replace(matchStr,"");
        }
        content = content.replace(">","");
        content = content.replace("<","");
        content = content.replace("%","");
        content = content.replace("'","");
        content = content.replace("\"","");

        SearchFilesServlet search = new SearchFilesServlet();
        num = search.getArticlesNum(sitename,content); //总的记录数
        list = search.getArticles(sitename,content,(pages-1) * range, pages * range);
    }

//    List list = search.getFiles(content, pages);
//    int num = search.getNums(content);
    int totalPages = 0;  //总页数
    if (num % range == 0) {
        totalPages = num / range;
    } else {
        totalPages = num / range + 1;
    }
    sitename = request.getServerName();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head xmlns="">
    <title>北京城建集团</title>
    <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
    <link href="/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body xmlns="">
<div><%@ include file="/inc/head.shtml" %></div>
<table cellspacing="1" cellpadding="1" width="1000" align="center" border="0">
    <tbody>
    <tr>
        <td align="center" height="40" style="font-size: 14px; line-height: 30px"><strong>搜 索 结 果</strong></td>
    </tr>
    <tr>
        <td></td>
    </tr>
    <tr>
        <td class="group_js" align="left">    <%
            for (int i = 0; i < list.size(); i++) {
                Document doc = (Document) list.get(i);
                String maintitle = doc.get("maintitle");
                maintitle = StringUtil.replace(maintitle,content,"<font color=red>"+content+"</font>");
                String vicetitle = doc.get("vicetitle");
                vicetitle = StringUtil.replace(vicetitle,content,"<font color=red>"+content+"</font>");
                String id = doc.get("id");
                String dirname = doc.get("dirname");
                String summary = doc.get("summary");
                summary = StringUtil.replace(summary,content,"<font color=red>"+content+"</font>");
                String saleprice = doc.get("saleprice");
                String marketprice = doc.get("marketprice");
                String vipprice = doc.get("vipprice");
                String stocknum = doc.get("stock");
                String smallpic = doc.get("smallpic");
                String largepic = doc.get("largepic");
                String specpic = doc.get("specpic");
                String shoppingcarurl = doc.get("shoppingcarurl");
                String createdate = doc.get("createdate");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sitename=sitename.replaceAll("_","\\.");
                String publishtime = null;
                try {
                    publishtime = sdf.format(sdf.parse(doc.get("publishtime")));
                } catch (ParseException e) {
                }
                String filename = doc.get("filename");
                String url = "";
                if ((filename != null) && (!filename.equals("")) && (filename.indexOf(".html") == -1) && (filename.indexOf(".shtml") == -1))
                    url = "http://" + sitename + dirname + createdate + "/download/" + filename;
                else
                    url = "http://" + sitename + dirname + createdate + "/" + id + ".shtml";
        %>
            <ul style="margin: 5px; padding: 0px">

                <li class="list_ejlb"><a href=<%=url%>  target="_blank"><%=maintitle%></a></li>
                <li class="date"><<%=publishtime%>></li>

            </ul>
            <%}%>
        </td>
    </tr>
    <tr>
        <td class="group_js" style="text-align: center"><table width="98%" border="0" cellspacing="8" cellpadding="0">
            <tr>
                <td height="25" align="center" class="fy"><A href=search.jsp?currpage=1&searchcontent=<%=content%>>第一页</A>  <A href=search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>>上一页</A>  <A href=search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>>下一页</A>  <A href=search.jsp?currpage=<%=totalPages%>&searchcontent=<%=content%>>最后页</A>
                </td>
            </tr>
        </table></td>
    </tr>
    </tbody>
</table>
<div><%@ include file="/inc/tail.shtml" %></div>
</body>
</html>
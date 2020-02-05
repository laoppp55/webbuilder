<%@page contentType="text/html;charset=utf-8"%>
<%@ page import="com.bizwink.search.SearchFilesServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="java.net.URLEncoder" %>

<%
    //request.setCharacterEncoding("utf-8");
	String sitename = "www_chemicals_sinopec_com";
    String content = ParamUtil.getParameter(request,"keyword");   //request.getParameter("keyword");
    //System.out.println("content=" + content);
    String pagesstr = request.getParameter("currpage");
	int pages = 1;  //当前页
    if (pagesstr == null) {
        pages = 1;
    } else {
        pages = Integer.parseInt(pagesstr);
    }
    int range = 20;

	SearchFilesServlet search = new SearchFilesServlet();
    int num = search.getArticlesNum(sitename,content); //总的记录数
    List list = search.getArticles(sitename,content,(pages-1) * range, pages * range);
//    List list = search.getFiles(content, pages);
//    int num = search.getNums(content);
    int totalPages = 0;  //总页数
    if (num % range == 0) {
        totalPages = num / range;
    } else {
        totalPages = num / range + 1;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="">
<title>中国石化化工销售有限公司</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="IE=8" http-equiv="X-UA-Compatible" />
<link rel="stylesheet" type="text/css" href="/css/main.css" />
</head>
<body xmlns="">
<div class="head"><%@include file="/inc/head.shtml" %></div>
<div class="menu_out"><%@include file="/inc/menu.shtml" %></div>
<div class="main_out">
<div class="main">
<div class="news_title">搜索结果</div>
<div class="news_list">
<ul>
    <%
        for (int i = 0; i < list.size(); i++) {
            Document doc = (Document) list.get(i);
            String maintitle = doc.get("maintitle");
            maintitle = StringUtil.replace(maintitle,content,"<font color=red>"+content+"</font>");
            String id = doc.get("id");
            String dirname = doc.get("dirname");
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
<li><a href="<%=url%>"><%=maintitle%></a><%=publishtime%></li>
    <%}%>
</ul>
</div>
<div class="page"><a href='/_prog/search1.jsp?type=1&keyword=<%=URLEncoder.encode(content,"GBK")%>&currpage=1'>首页</a> | <a href='/_prog/search1.jsp?keyword=<%=URLEncoder.encode(content,"GBK")%>&currpage=<%=pages-1%>'>上一页</a> |
    <a href='/_prog/search1.jsp?keyword=<%=URLEncoder.encode(content,"GBK")%>&currpage=<%=pages+1%>'>下一页</a> |
    <a href='/_prog/search1.jsp?&keyword=<%=URLEncoder.encode(content,"GBK")%>&currpage=<%=totalPages%>'>尾页</a> 第<%=pages%>页 / 共<%=totalPages%>页</div>
<div class="clear"></div>
</div>
</div>
<div><%@include file="/inc/foot.shtml" %></div>
</body>
</html>
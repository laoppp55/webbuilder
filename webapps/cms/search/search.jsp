<%@ page import="com.bizwink.search.SearchFilesServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.util.filter" %>
<%@ page language="java" contentType="text/html;charset=utf-8" %>

<%
    String sitename = request.getServerName();  //site name
    sitename = StringUtil.replace(sitename,".","_");
    String pagesstr = filter.excludeHTMLCode(request.getParameter("currpage"));
    int pages = 1;  //当前页
    if (pagesstr == null) {
        pages = 1;
    } else {
        if (StringUtil.isNumeric(pagesstr))
            pages = Integer.parseInt(pagesstr);
        else
            pages = 0;
    }
    int range = 20;
    String content = filter.excludeHTMLCode(request.getParameter("searchcontent"));
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
<title>北京的民族与宗教</title>
<meta content="text/css; charset=utf-8" http-equiv="Content-Type" />
<link rel="stylesheet" type="text/css" href="/css/index.css" />
<meta content="IE=EmulateIE7" http-equiv="X-UA-Compatible" />
</head>
<body xmlns="">
<!--首图-->
<div class="mzzj-container">
<%@include file="/sites/www_bjethnic_gov_cn/inc/top.shtml" %>
<div class="mzzj-erji-container"><!--search begin-->
<div class="search_bg">
<div class="search_tit">搜索结果--记录总数：<%=num%>条</div>
<%
        for (int i = 0; i < list.size(); i++) {
            Document doc = (Document) list.get(i);
            String maintitle = doc.get("maintitle");
            String id = doc.get("id");
            String dirname = doc.get("dirname");
            String summary = doc.get("summary");
			String resultcontent = doc.get("content");
			if(resultcontent != null&& resultcontent.length() >100){
				resultcontent = resultcontent.substring(0,100)+"...";
			}
            String createdate = doc.get("createdate");
            //createdate = createdate.substring(0, 10);
            //createdate = createdate.replaceAll("-", "");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String publishtime = null;
            try {
                publishtime = sdf.format(sdf.parse(doc.get("publishtime")));
            } catch (ParseException e) {
            }
            String filename = doc.get("filename");
            String url = "";
            if ((filename != null) && (!filename.equals("")) && (filename.indexOf(".html") == -1) && (filename.indexOf(".shtml") == -1))
                url = "http://www.bjethnic.gov.cn" + dirname + createdate + "/download/" + filename;
            else
                url = "http://www.bjethnic.gov.cn" + dirname + createdate + "/" + id + ".shtml";
    %>
    
<div class="ss_bk">
<h3><a target="_blank" href="<%=url%>"><%=maintitle%></a></h3>
<p><%=(summary==null)?"":summary%><a target="_blank" href="<%=url%>">&hellip;[更多详情&gt;&gt;]</a></p>
</div>
<%}%>

<div id="changpage" class="search_page">
<td align="right">共找到<%=num%>条相关内容 共<%=totalPages%>页 <%if (totalPages > 1) {%><a
                href="search.jsp?currpage=1&searchcontent=<%=content%>">首页</a><%}%><%if (pages > 1) {%>&nbsp;&nbsp;<a
                href="search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>">上一页</a><%}%><%if (totalPages > pages) {%>
            &nbsp;&nbsp;<a href="search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>">下一页</a><%}%>&nbsp;&nbsp;<a
                href="search.jsp?currpage=<%=totalPages%>&searchcontent=<%=content%>">末页</a></td>
</div>
</div>
<!--search end--></div>
</div>
<p>
<%@include file="/sites/www_bjethnic_gov_cn/inc/bottom.shtml" %>
</p>
</body>
</html>
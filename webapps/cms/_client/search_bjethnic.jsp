﻿<%@ page import="com.bizwink.search.SearchFilesServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page language="java" contentType="text/html;charset=utf-8" %>

<%
    //String sitename = request.getServerName();  //site name
    String sitename = "www.bjethnic.gov.cn";
    sitename = StringUtil.replace(sitename,".","_");
    String pagesstr = filter.excludeHTMLCode(request.getParameter("currpage"));
    int pages = 1;  //当前页
    if (pagesstr == null) {
        pages = 1;
    } else {
        pages = Integer.parseInt(pagesstr);
    }
    int range = 20;
    String content = filter.excludeHTMLCode(request.getParameter("searchcontent"));
    content = "朝阳区";
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
<%@include file="../sites/www_bjethnic_gov_cn/inc/top.shtml" %>
<div class="mzzj-erji-container">

<!--search begin-->

<div class="search_bg">
<div class="search_tit">搜索结果--记录总数：<%=list.size()%>条</div>
    <%
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
	<div class="ss_bk">
		<h3><a href="<%=url%>" target="_blank"><%=maintitle%></a></h3>
		<p><%=(summary!=null)?summary:""%><a href="<%=url%>" target="_blank">…[更多详情&gt;&gt;]</a></p>
		</div>
	<!--div class="ss_bk">
		<h3><a href="/xwqss/zonghe/20161128/5081587.shtml" target="_blank">强化责任   抓实教育  进一步增强党风廉政意识</a></h3>
		<p>本网讯（通讯员韩文丽）今年以来，区档案局党组认真贯彻区委“从严治党主体责任深化年”部署要求，牢固树立党风廉政建设“生命线”意识，围绕<a href="/xwqss/zonghe/20161128/5081587.shtml" target="_blank">…[更多详情&gt;&gt;]</a></p>
		</div-->
    <%}%>

<div id="changpage" class="search_page">
    <a href="search.jsp?currpage=1&searchcontent=<%=content%>">首页</a> | <a href="search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>">上一页</a> |
    <a href="search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>">下一页 </a>| <a href="search.jsp?currpage=<%=totalPages%>&searchcontent=<%=content%>">尾页</a>  共<%=totalPages%>页 | 当前为第<%=pages%>页
    <!--a href="###" onclick="upPage1(1)">首页</a>
    <a href="###" style="color:red;font-weight:700;" onclick="upPage1(1)">1</a>
    <a href="###" onclick="upPage1(2)">2</a>
    <a href="###" onclick="upPage1(3)">3</a>
    <a href="###" onclick="upPage1(4)">4</a>
    <a href="###" onclick="upPage1(5)">5</a>
    <a href="###" onclick="upPage1(196)">尾页</a>  1/196页  共9796条</div-->
</div>

<!--search end-->
</div>
</div>
<p>
<%@include file="../sites/www_bjethnic_gov_cn/inc/bottom.shtml" %>
</p>
</body>
</html>
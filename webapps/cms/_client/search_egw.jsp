<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.search.SearchFilesServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="java.util.ArrayList" %>

<%
    String sitename = request.getServerName();  //site name
    sitename="www.egreatwall.com";
    sitename = StringUtil.replace(sitename,".","_");
    String pagesstr = request.getParameter("currpage");
    int pages = 1;  //当前页
    if (pagesstr == null) {
        pages = 1;
    } else {
        pages = Integer.parseInt(pagesstr);
    }
    int range = 9;
    String tbuf = request.getParameter("searchcontent");
    String content = com.bizwink.cms.util.filter.excludeHTMLCode(tbuf);

    SearchFilesServlet search = new SearchFilesServlet();
	List list = new ArrayList();
    int num = 0;
    if (content != null && content != "") {
        num = search.getArticlesNum(sitename,content,"c*"); //总的记录数
        list = search.getArticles(sitename,content,"c*",(pages-1) * range, pages * range);
    }
    int totalPages = 0;  //总页数
    int extranum = num % range;
    if (extranum == 0) {
        totalPages = num / range;
    } else {
        totalPages = num / range + 1;
    }

    int startpage=ParamUtil.getIntParameter(request,"startp",1);               //底部导航条的启示页号
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
    <title>长城电商</title>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/js/pptBox.js"></script>
    <!--[if IE 6]>
    <script src="/js/iepng.js" type="text/javascript"></script>
    <script type="text/javascript"  src="/js/index_png.js" ></script>
    <![endif]-->
    <link href="/css/2014column.css" rel="stylesheet" type="text/css" />
</head>
<body xmlns="">
<div class="head_out"><%@include file="/inc/head1.shtml" %> </div>
<div id="top_box">
    <div class="menu"><%@include file="/inc/menu2.shtml" %></div>

<div class="banner"><img src="/images/2014banner_cggl.jpg" width="960" height="163" /></div>
<div class="right_tit"><h1>搜索结果</h1></div>
<div class="right_con">
    <div class="sousuo_list">
        <ul>
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
            <li>
                <A href="<%=url%>" class="ss_list_top"><%=maintitle%></A>
                <A href="<%=url%>" class="ss_list_bottom"><%=(summary!=null)?summary:""%>......</A>
            </li>
            <%}%>
        </ul>
    </div>

    <div class="page" style="width:880px;"><a href="search.jsp?currpage=1&searchcontent=<%=content%>">首页</a> | <a href="search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>">上一页</a> |
        <a href="search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>">下一页 </a>| <a href="search.jsp?currpage=<%=totalPages%>&searchcontent=<%=content%>">尾页</a>  共<%=totalPages%>页 | 当前为第<%=pages%>页</div>
</div>

<div style="clear:both"></div>
</div>
<div class="tail_out"><%@include file="/inc/foot1.shtml" %></div>

</body></html>


<%@ page import="com.bizwink.search.SearchFilesServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.log.ILogManager" %>
<%@ page import="com.bizwink.log.LogPeer" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>

<%
    String sitename = request.getServerName();  //site name
    sitename = "www.cbcsd.org.cn";
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
    SearchFilesServlet search = new SearchFilesServlet();

    List list = new ArrayList();
    int num = 0;

    if (content != null) {
        if (!content.isEmpty()) {
            String ip = request.getHeader("x-forwarded-for");
            if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            ILogManager logManager = LogPeer.getInstance();
            int retcode = logManager.LogSearchKeyword(1,ip,content);
            num = search.getArticlesNum(sitename,content,"c*"); //总的记录数
            list = search.getArticles(sitename,content,"c*",(pages-1) * range, pages * range);
        }
    }

    int totalPages = 0;  //总页数
    if (num % range == 0) {
        totalPages = num / range;
    } else {
        totalPages = num / range + 1;
    }
    sitename = request.getServerName();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html dir="ltr">
<head>
    <title>CBCSD信息检索系统</title>
    <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
    <link rel="stylesheet" type="text/css" href="/css/main.css" />
    <link rel="stylesheet" type="text/css" href="/css/login.css" />
    <script type="text/javascript"  src="/js/jquery.min.js" ></script>
    <script type="text/javascript"  src="/js/jquery.jcarousel.pack.js" ></script>
    <script type="text/javascript"  src="/js/menu.js" ></script>
</head>
<body>
<div class="main">
    <div class="box"><%@include file="/inc/head.shtml" %><div class="banner"><img alt="" src="/images/banner61.jpg" /></div>
        <div class="con">
            <div class="path"><span>当前位置：</span><a href="/">首页</a></div>
            <div class="bigmain">
                <div class="bigmain_1">搜索结果</div>
                <div class="bigmain_4">
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
                    <ul>
                        <li class="title"><a href="<%=url%>"><%=maintitle%></a></li>
                        <li class="txt">&nbsp;&nbsp;&nbsp;&nbsp;<%=(summary!=null)?summary:""%></li>
                    </ul>
                    <%}%>
                    <!--ul>
                        <li class="title"><a href="#">大众与绿色和平共同强调控制新车型二氧化碳排放</a></li>
                        <li class="txt">大众汽车集团3月初首次公布了一项意义深远的决定，在原有的节油目标基础上进一步提高新车的节油指标。大众汽车集团管理董事会主席文德恩教授3月21日与国际环保组织提高新车的节油指......</li>
                    </ul>
                    <ul>
                        <li class="title"><a href="#">大众与绿色和平共同强调控制新车型二氧化碳排放</a></li>
                        <li class="txt">大众汽车集团3月初首次公布了一项意义深远的决定，在原有的节油目标基础上进一步提高新车的节油指标。大众汽车集团管理董事会主席文德恩教授3月21日与国际环保组织提高新车的节油指......</li>
                    </ul-->
                    <div class="daohang"><!--a href="/xmhhd/shehui/news/index.shtml">首页</a> | <a href="/xmhhd/shehui/news/index.shtml">上一页</a> | <a href="/xmhhd/shehui/news/index1.shtml">下一页</a> | <a href="/xmhhd/shehui/news/index29.shtml">尾页</a>&nbsp;&nbsp;&nbsp;共30页 | 当前第1页-->
                        有 <span><%=num%></span> 项符合 <span><%=content%></span> 的查询结果 共<span><%=totalPages%></span>页 <%if (totalPages > 1) {%><a
                                href="search.jsp?currpage=1&searchcontent=<%=content%>"><span>首页</span></a><%}%><%if (pages > 1) {%>
                        &nbsp;&nbsp;<a href="search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>"><span>上一页</span></a><%}%><%if (totalPages > pages) {%>
                        &nbsp;&nbsp;<a href="search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>"><span>下一页</span></a><%}%>
                        &nbsp;&nbsp;<a href="search.jsp?currpage=<%=totalPages%>&searchcontent=<%=content%>"><span>末页</span></a>
                    </div>
                </div>
                <div class="bigmain_3"></div>
            </div>
        </div><%@include file="/inc/tail.shtml" %><div class="clear"></div>
    </div>
</div>
<div class="bg-bottom"></div>
</body>
</html>
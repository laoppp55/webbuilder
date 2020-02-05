<%@ page import="com.bizwink.search.SearchFilesServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>

<%
    String sitename = request.getServerName();  //site name
    sitename = StringUtil.replace(sitename,".","_");
    String pagesstr = request.getParameter("currpage");
    int pages = 1;  //当前页
    if (pagesstr == null) {
        pages = 1;
    } else {
        pages = Integer.parseInt(pagesstr);
    }
    int range = 20;
    String content = request.getParameter("searchcontent");
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
sitename = request.getServerName();  
%>
<html>
    <head>
        <title>EZHENG</title><script src="/_sys_js/tanchuceng.js" type="text/javascript"></script>
        <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
        <link href="/css/css.css" type="text/css" rel="stylesheet" /><script src="/js/AC_RunActiveContent.js" type="text/javascript"></script>
    <style type="text/css">
<!--.biz_table{ border:1 dashed null;
 } 
.biz_table td{ font-size:12px; color:#000000; font-family:宋体 ; text-align:left;
}
.biz_table input{ font-size:12px;  size:18px;

}
biz_table img{ border:0;
}
-->
</style></head>
    <body bgcolor="#ffffff" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <%@include file="/www_ezheng_com_cn/inc/head.shtml" %>
        <table cellspacing="0" cellpadding="0" width="960" align="center" background="/images/content_bg.gif" border="0">
            <tbody>
                <tr>
                    <td valign="top" width="180"><%@include file="/www_ezheng_com_cn/inc/left.shtml" %></td>
                    <td valign="top">
                    <table cellspacing="0" cellpadding="0" width="780" border="0">
                        <tbody>
                            <tr>
                                <td valign="top"><br />
                                <table class="news_txt2" cellspacing="0" cellpadding="0" width="740" align="center" border="0">
                                    <tbody>
                                        <tr>
                                            <td>
                                            <div align="right">信息检索</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td bgcolor="#cccccc" height="1"><img height="1" alt="" width="1" src="/images/space.gif" /></td>
                                        </tr>
                                        <tr>
                                            <td height="50">
                                            <table cellspacing="0" cellpadding="0" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td width="6" bgcolor="#666666" height="25"><img height="1" alt="" width="1" src="/images/space.gif" /></td>
                                                        <td width="80">
                                                        <div class="bt_txt" align="center">检索结果</div>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td bgcolor="#cccccc" height="1"><img height="1" alt="" width="1" src="/images/space.gif" /></td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <table cellspacing="0" cellpadding="0" width="740" align="center" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="cint_txt01" valign="top"><table cellspacing="0" cellpadding="4" width="100%" border="0" class="biz_table">
   <tr>
     <td align="right">有 <span><%=num%></span> 项符合 <span><%=content%></span> 的查询结果 共<span><%=totalPages%></span>页 <%if (totalPages > 1) {%><a
                href="search.jsp?currpage=1&searchcontent=<%=content%>"><span>首页</span></a><%}%><%if (pages > 1) {%>
            &nbsp;&nbsp;<a href="search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>"><span>上一页</span></a><%}%><%if (totalPages > pages) {%>
            &nbsp;&nbsp;<a href="search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>"><span>下一页</span></a><%}%>
            &nbsp;&nbsp;<a href="search.jsp?currpage=<%=totalPages%>&searchcontent=<%=content%>"><span>末页</span></a>
        </td>
    </tr>
    <tr>
    <%
        for (int i = 0; i < list.size(); i++) {
            Document doc = (Document) list.get(i);
            String maintitle = doc.get("maintitle");
            maintitle = StringUtil.replace(maintitle,content,"<font color=red>"+content+"</font>");
            String id = doc.get("id");
            String dirname = doc.get("dirname");
            String summary = doc.get("summary");
            summary = StringUtil.replace(summary,content,"<font color=red>"+content+"</font>");
            String createdate = doc.get("createdate");
            //createdate = createdate.substring(0, 10);
            //createdate = createdate.replaceAll("-", "");
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
    <td align="left"><a href="<%=url%>" target="_blank"><%=maintitle%></a>&nbsp;&nbsp;&nbsp;&nbsp;<label><%=(publishtime==null)?"":publishtime%></label></td>
    </tr>
    <tr><td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=(summary==null)?"":summary%></td></tr>
    <%}%>
    <tr>
        <td align="right">有 <span><%=num%></span> 项符合 <span><%=content%></span> 的查询结果 共<span><%=totalPages%></span>页 <%if (totalPages > 1) {%><a
                href="search.jsp?currpage=1&searchcontent=<%=content%>"><span>首页</span></a><%}%><%if (pages > 1) {%>&nbsp;&nbsp;<a
                href="search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>"><span>上一页</span></a><%}%><%if (totalPages > pages) {%>
            &nbsp;&nbsp;<a href="search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>"><span>下一页</span></a><%}%>&nbsp;&nbsp;<a
               href="search.jsp?currpage=<%=totalPages%>&searchcontent=&lt%=content%>"><span>末页</span></a></td>
    </tr>
</table>
</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <br />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                </tr>
            </tbody>
        </table>
        <%@include file="/www_ezheng_com_cn/inc/tail.shtml" %>
    </body>
</html>
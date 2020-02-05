<%@ page import="com.bizwink.search.SearchFilesServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.log.LogPeer" %>
<%@ page import="com.bizwink.log.ILogManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.filter" %>
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
<html xmlns="http://www.w3.org/1999/xhtml">
    <head xmlns="">
        <title>大屯街道亚运新新家园社区</title>
        <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
        <link href="/css/css.css" type="text/css" rel="stylesheet" /><style type="text/css">
<!--
.STYLE1 {color: #FFFFFF}
--></style>
    </head>
    <body xmlns="">
        <div><%@ include file="/www_xxjysq166_com/inc/4p1x3o4m.shtml" %></div>
        <table cellspacing="0" cellpadding="0" width="1000" align="center" border="0">
            <tbody>
                <tr>
                    <td width="496"><img height="5" alt="" width="1" src="/images/space.gif" /></td>
                    <td width="504"><img height="5" alt="" width="1" src="/images/space.gif" /></td>
                </tr>
                <tr>
                    <td valign="top" align="left" colspan="2"><%@ include file="/www_xxjysq166_com/inc/hfm821ev.shtml" %></td>
                </tr>
                <tr>
                    <td><img height="5" alt="" width="1" src="/images/space.gif" /></td>
                    <td><img height="5" alt="" width="1" src="/images/space.gif" /></td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" width="1000" align="center" border="0">
            <tbody>
                <tr>
                    <td valign="top" align="left" width="737">
                    <table cellspacing="0" cellpadding="0" width="737" border="0">
                        <tbody>
                            <tr>
                                <td valign="bottom" align="left" style="background: url(/images/xx20101119_22.gif) no-repeat 50% top">
                                <table cellspacing="0" cellpadding="0" width="737" border="0">
                                    <tbody>
                                        <tr>
                                            <td class="blueposition" valign="bottom" align="left" height="40">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前位置：<a class="bluelink" href="/">首页</a> --&gt; 搜索结果</td>
                                        </tr>
                                        <tr>
                                            <td valign="bottom" align="center" height="35">
                                            <table cellspacing="0" cellpadding="0" width="708" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td class="fonttitle" valign="middle" align="left" background="/images/xx20101119_25.gif">&nbsp;&nbsp;&nbsp; 搜索结果</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td valign="top" align="center" height="32" style="background: url(/images/xx20101119_23.gif) repeat-y">
                                            <table cellspacing="0" cellpadding="0" width="671" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td style="padding-top: 5px"><table cellspacing="0" cellpadding="4" width="100%" border="0">
   <tr>
     <td align="right">有 <span><%=num%></span> 项符合 <span><%=content%></span> 的查询结果 共<span><%=totalPages%></span>页 <%if (totalPages > 1) {%><a
                href="search.jsp?currpage=1&searchcontent=<%=content%>"><span>首页</span></a><%}%><%if (pages > 1) {%>
            &nbsp;&nbsp;<a href="search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>"><span>上一页</span></a><%}%><%if (totalPages > pages) {%>
            &nbsp;&nbsp;<a href="search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>"><span>下一页</span></a><%}%>
            &nbsp;&nbsp;<a href="search.jsp?currpage=<%=totalPages%>&searchcontent=<%=content%>"><span>末页</span></a>
        </td>
    </tr>
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
<p><a href="<%=url%>" target=_blank><%=maintitle%></a></p>
    <%}%>;
    <tr>
        <td align="right">有 <span><%=num%></span> 项符合 <span><%=content%></span> 的查询结果 共<span><%=totalPages%></span>页 <%if (totalPages > 1) {%><a
                href="search.jsp?currpage=1&searchcontent=<%=content%>"><span>首页</span></a><%}%><%if (pages > 1) {%>&nbsp;&nbsp;<a
                href="search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>"><span>上一页</span></a><%}%><%if (totalPages > pages) {%>
            &nbsp;&nbsp;<a href="search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>"><span>下一页</span></a><%}%>&nbsp;&nbsp;<a
               href="search.jsp?currpage=<%=totalPages%>&searchcontent=<%=content%>"><span>末页</span></a></td>
    </tr>
</table>
</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <table cellspacing="0" cellpadding="0" width="737" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td valign="bottom" align="center" height="30"></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                </td>
                            </tr>
                            <tr>
                                <td><img height="12" alt="" width="737" src="/images/xx20101119_24.gif" /></td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                    <td>&nbsp;</td>
                    <td valign="top" align="left" width="244"><%@ include file="/www_xxjysq166_com/inc/j52nfu4t.shtml" %></td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" width="1000" border="0">
            <tbody>
                <tr>
                    <td><img height="10" alt="" width="1" src="/images/space.gif" /></td>
                </tr>
            </tbody>
        </table>
        <div><%@ include file="/www_xxjysq166_com/inc/1p83113g.shtml" %></div>
    </body>
</html>
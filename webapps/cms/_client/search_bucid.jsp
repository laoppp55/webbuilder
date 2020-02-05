<%@ page import="com.bizwink.search.SearchFilesServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page import="com.bizwink.log.LogPeer" %>
<%@ page import="com.bizwink.log.ILogManager" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>

<%
    String sitename = request.getServerName();  //site name
    sitename = StringUtil.replace(sitename,".","_");
    sitename = "www_bucid_com";
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
    
    int yhds = 0;       //页号段数，按每页排列20页计算
    int extra = totalPages % 20;
    if(extra == 0)
       yhds = totalPages/20;
    else
       yhds = totalPages/20 + 1;
    sitename = request.getServerName();  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head xmlns="">
        <title>无标题文档</title>
        <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
        <link href="/css/style.css" type="text/css" rel="stylesheet" />
    </head>
    <body xmlns="">
        <p><%@ include file="/www_bucid_com/include/32v82uv1.html" %></p>
        <table cellspacing="0" cellpadding="0" width="947" align="center" border="0">
            <tbody>
                <tr>
                    <td valign="top" width="236">
                    <table cellspacing="0" cellpadding="0" width="234" border="0">
                        <tbody>
                            <tr>
                                <td><%@ include file="/www_bucid_com/include/4511k4p6.html" %></td>
                            </tr>
                            <tr>
                                <td>
                                <table class="left_bg01" cellspacing="0" cellpadding="0" width="234" border="0">
                                    <tbody>
                                        <tr>
                                            <td valign="top" align="center"></td>
                                        </tr>
                                    </tbody>
                                </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                    <td width="14"><img alt="" src="/images/space.gif" /></td>
                    <td valign="top" align="left" width="688">
                    <table cellspacing="0" cellpadding="0" width="688" border="0" style="margin: 12px 0px 0px">
                        <tbody>
                            <tr>
                                <td class="weizi" align="left">    <%
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
<table width="90%"><tr><td><a href="<%=url%>" target="_blank"><b><font size="2"><%=maintitle%></font></b></a></td></tr>
<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<%=(summary!=null)?summary:""%></td></tr></table>
    <%}%>
<table width="98%" border="0" cellspacing="8" cellpadding="0">
 <tr>
    <td height="25" align="center"><A href=search.jsp?currpage=1&searchcontent=<%=content%>>第一页</A>  <A href=search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>>上一页</A>  <A href=search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>>下一页</A>  <A href=search.jsp?currpage=<%=totalPages%>&searchcontent=<%=content%>>最后页</A>
</td>
  </tr>
</table></td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                    <td width="9"><img alt="" src="/images/space.gif" /></td>
                </tr>
            </tbody>
        </table>
        <p><%@ include file="/www_bucid_com/include/footer.html" %></p>
    </body>
</html>
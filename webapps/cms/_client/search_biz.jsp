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
    String pagesstr = request.getParameter("currpage");
    int pages = 1;  //当前页
    if (pagesstr == null) {
        pages = 1;
    } else {
        pages = Integer.parseInt(pagesstr);
    }
    int range = 20;

    //String content = request.getParameter("searchcontent");
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
    <title>北京潮虹伟业科技有限公司</title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
    <link rel="stylesheet" type="text/css" href="/css/main.css" />
    <link rel="stylesheet" type="text/css" href="/css/css.css" /><script type="text/javascript" src="/js/lrtk.js"></script>
</head>
<body xmlns="">
<div class="box"><%@ include file="/bizwink/include/head.shtml" %><div class="con1">
    <div class="con1_1">
        <div class="con1_bt">企业宣传网站建设</div>
        <div class="con1_wb">通过网站宣传企业形象，提升品牌价值，推广公司业务及产品，获得潜在客户。</div>
    </div>
    <div class="con1_2">
        <div class="con1_bt">网络品牌营销</div>
        <div class="con1_wb">通过用户体验、网络口碑传播、关键词推广等把企业形象、产品、服务推广出去。</div>
    </div>
    <div class="con1_3">
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
                    </table>
    </div>
    <div class="clear"></div>
</div>
    <div class="clear"></div>
</div>
<!-- lxwm  start -->
<div class="box">
    <div class="con2">
        <div class="con2_1">
            <div class="con2_bt">关于我们</div>
            <div class="con2_wb">北京潮虹伟业科技有限公司是国内互联网应用技术和设计的最早的拓展者之一。为企业提供包括域名注册、虚拟主机、服务器托管租用、企业邮箱、网站建设、网站维护、多媒体演示、电子商务网站开发、企业及行业门户网站建设、企业内部系统建设全方位互联网解决方案。</div>
        </div>
        <div class="con2_2">
            <div class="con2_bt">联系我们</div>
            <div class="con2_wb1">传真：84833752-804　　　电话：84833752-801、802、803</div>
            <div class="con2_wb1">地址：北京市朝阳区九台2000家园3号楼407</div>
        </div>
    </div>
</div>
<!-- lxwm  end -->
<p><%@ include file="/bizwink/include/foot.shtml" %></p>
</body>
</html>
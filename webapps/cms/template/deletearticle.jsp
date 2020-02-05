<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.IArticleManager" %>
<%@ page import="com.bizwink.cms.news.ArticlePeer" %>
<%@ page import="com.bizwink.cms.news.ArticleException" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    int columnid = ParamUtil.getIntParameter(request, "column", -1);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 10);
    int articleid = ParamUtil.getIntParameter(request, "articleID", 0);
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    if (startflag == 1) {
        IArticleManager articleMgr = ArticlePeer.getInstance();
        try {
            articleMgr.deleteCommendArticle(articleid, columnid);
        } catch (ArticleException e) {
            e.printStackTrace();
        }
        response.sendRedirect("commendarticle_2.jsp?column=" + columnid + "&start=" + start + "&range=" + range + "&mark=" + markID);
    }
%>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.news.*" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="java.io.File" %>
<%@ page import="com.bizwink.cms.publish.IPublishManager" %>
<%@ page import="com.bizwink.cms.publish.PublishPeer" %>
<%@ page import="com.bizwink.cms.publish.PublishException" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    int siteId = authToken.getSiteID();
    String siteName = authToken.getSitename();
    String realpath = request.getRealPath("/");
    int columnId = ParamUtil.getIntParameter(request, "column", -1);

    IColumnManager columnMgr = ColumnPeer.getInstance();
    IArticleKeywordManager keywordMgr = ArticleKeywordPeer.getInstance();
    Column column = columnMgr.getColumn(columnId);
    String dirname = column.getDirName();
    int languageType = column.getLanguageType();
    String xmlFile = realpath + "sites" + java.io.File.separator + siteName + StringUtil.replace(dirname, "/", File.separator);
    xmlFile = xmlFile + "keylink.xml";
    String jsFile = realpath + "js" + java.io.File.separator + "key.js";

    File file = new File(xmlFile);
    if (file.exists())
        file.delete();

    List list = new ArrayList();
    try {
        list = keywordMgr.getAllListKeyLink(siteId, columnId);
    } catch (ArticleException e) {
        e.printStackTrace();
    }

    for (int i = 0; i < list.size(); i++) {
        ArticleKeyword articlekeyword = (ArticleKeyword) list.get(i);
        keywordMgr.createKeyLinkXML(siteName, articlekeyword, realpath, dirname);
    }

    IPublishManager publishMgr = PublishPeer.getInstance();
    int createflag = -1;
    try {
        int pubkeyxml = publishMgr.publish(username, xmlFile, siteId, dirname, languageType);
        int pubkeyjs = publishMgr.publish(username, jsFile, siteId, dirname, languageType);
        if ((pubkeyxml >= 0) && (pubkeyjs >= 0))
            createflag = 1;
        else
            createflag = 0;
    } catch (PublishException e) {
        e.printStackTrace();
    }

    response.sendRedirect("keywordRight.jsp?column=" + columnId + "&createflag=" + createflag);
%>
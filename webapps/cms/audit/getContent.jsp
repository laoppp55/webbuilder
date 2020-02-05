<%@ page import="com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    IArticleManager articleMgr = ArticlePeer.getInstance();
    String content = articleMgr.getArticle(articleID).getContent().trim();
    String baseURL = "<base href=http://" + request.getHeader("Host") + "/webbuilder/>";
    content = StringUtil.replace(content, "<head>", "<head>\r\n" + baseURL);
    content = StringUtil.replace(content, "<HEAD>", "<HEAD>\r\n" + baseURL);
    content = StringUtil.gb2iso4View(content);
    out.println(content);
%>
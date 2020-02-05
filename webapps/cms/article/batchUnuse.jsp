<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.ArticlePeer" %>
<%@ page import="com.bizwink.cms.news.IArticleManager" %>
<%@ page import="com.bizwink.cms.news.ArticleException" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int currentPage = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 40);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request, "searchFlag");
    String articleIDs = ParamUtil.getParameter(request, "articles");
    int columnid = ParamUtil.getIntParameter(request, "column", 0);
    String[] tt = articleIDs.split(",");
    List<Integer> articleList =new ArrayList();
    for(int ii=0;ii<tt.length;ii++) {
        articleList.add(Integer.parseInt(tt[ii]));
    }

    int errcode = 0;
    IArticleManager articleMgr = ArticlePeer.getInstance();
    try {
        errcode = articleMgr.batchUnuse(columnid,articleIDs);
    } catch (ArticleException e) {
        errcode = -1;
        e.printStackTrace();
    }

    Map data = new HashMap();
    data.put("DATA",articleList);
    data.put("ERRCODE",errcode);
    data.put("START",currentPage);
    data.put("RANGE",range);
    data.put("FLAG",flag);
    data.put("ASCDESC",ascdesc);
    data.put("SEARCHFLAG",doSearchFlag);
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="com.bizwink.cms.refers.IRefersManager" %>
<%@ page import="com.bizwink.cms.refers.RefersPeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.*" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.orderArticleListManager.orderArticleException" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.util.JSON" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int errcode = 0;
    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    boolean doDelete = ParamUtil.getBooleanParameter(request, "doDelete");
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int pageno = ParamUtil.getIntParameter(request, "pageno", 0);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request, "searchFlag");

    if (doDelete) {
        try {
            IRefersManager refersManager = RefersPeer.getInstance();
            refersManager.remove(articleID, columnID);
        } catch (orderArticleException e) {
            errcode = -1;
            e.printStackTrace();
        }
    }

    Map data = new HashMap<>();
    data.put("ERRCODE",errcode);
    data.put("RANGE",range);
    data.put("COLUMN",columnID);
    data.put("FLAG",flag);
    data.put("PAGENO",pageno);
    data.put("ASCDESC",ascdesc);
    data.put("SEARCHFLAG",doSearchFlag);

    Gson gson = new Gson();
    String jsondata = gson.toJson(data);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>
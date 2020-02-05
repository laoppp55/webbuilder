<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.log.LogPeer" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.log.ILogManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.log.LogException" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=utf-8" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp");
        return;
    }

    int siteID = ParamUtil.getIntParameter(request, "siteid", 0);
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int currentpage = ParamUtil.getIntParameter(request, "pageno", 0);
    String spubdate = ParamUtil.getParameter(request,"spubdate");
    if (spubdate!=null && spubdate!="") spubdate = filter.excludeHTMLCode(URLDecoder.decode(spubdate,"utf-8"));
    String epubdate = ParamUtil.getParameter(request,"epubdate");
    if (epubdate!=null && epubdate!="") epubdate = filter.excludeHTMLCode(URLDecoder.decode(epubdate,"utf-8"));
    int operationType = ParamUtil.getIntParameter(request, "op", 0);
    String editor = filter.excludeHTMLCode(ParamUtil.getParameter(request,"editor"));
    String columns = filter.excludeHTMLCode(ParamUtil.getParameter(request,"columns"));
    String depts = filter.excludeHTMLCode(ParamUtil.getParameter(request,"depts"));
    String title = filter.excludeHTMLCode(ParamUtil.getParameter(request,"title"));

    System.out.println("spubdate==" + spubdate);
    System.out.println("epubdate==" + epubdate);

    List logInfoList = new ArrayList();
    int totalRow = 0;
    if (siteID > 0) {
        ILogManager logMgr = LogPeer.getInstance();
        try {
            //int siteid,List<Integer> columnids,List<Integer> deptids,List<String> editors,String sdatetime,String edatetime,String keyword,int optype
            List<Integer> columnids = null;
            List<Integer> deptids = null;
            List<String> editors = null;
            logInfoList = logMgr.getLoginfoInPage(range,currentpage,siteID,columnids,deptids,editors,spubdate,epubdate,title,operationType);
            totalRow = logMgr.getCountLoginfo(siteID,columnids,deptids,editors,spubdate,epubdate,title,operationType);
        } catch (LogException e) {
            e.printStackTrace();
        }
    }

    Map data = new HashMap<>();
    data.put("PAGENO",currentpage);
    data.put("RANGE",range);
    data.put("COUNT",totalRow);
    data.put("DATA",logInfoList);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>
<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    int resultnum = ParamUtil.getIntParameter(request, "resultnum", 20);
    int pageno = ParamUtil.getIntParameter(request, "pageno", 0);
    String keyword = ParamUtil.getParameter(request, "search");
    int searchflag=ParamUtil.getIntParameter(request,"searchflag",-1);
    int doSearchFlag=ParamUtil.getIntParameter(request,"doSearch",-1);

    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
    List siteList = new ArrayList();
    int rows = 0;
    int startnum = 0 ;
    startnum = (pageno-1)*resultnum;

    if(searchflag == -1){
        rows = siteMgr.getAllSiteInfoNum();
        siteList = siteMgr.getAllSiteInfo(resultnum,startnum);
    }else{
        rows = siteMgr.getAllSearchSiteInfoNum(keyword);
        siteList = siteMgr.getAllSearchSiteInfo(resultnum,startnum,keyword);
    }

    Map data = new HashMap<>();
    data.put("DOSEARCH",doSearchFlag);
    data.put("COUNT",rows);
    data.put("DATA",siteList);
    data.put("PAGENO",pageno);
    data.put("RANGE",resultnum);
    Gson gson = new GsonBuilder().serializeNulls().create();
    //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    //System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>
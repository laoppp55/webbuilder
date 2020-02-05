<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.collectionmgr.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int id = ParamUtil.getIntParameter(request, "id", 0);
    int proxyflag = ParamUtil.getIntParameter(request, "proxyflag", -1);
    String proxyServer = ParamUtil.getParameter(request, "proxyServer");
    int interval = ParamUtil.getIntParameter(request, "interval", 12);
    String proxyPort = ParamUtil.getParameter(request, "proxyPort");
    int proxyloginflag = ParamUtil.getIntParameter(request, "proxyloginflag", 0);
    String proxyloginuser = ParamUtil.getParameter(request, "proxyloginuser");
    String proxyloginpass = ParamUtil.getParameter(request, "proxyloginpass"); 
    String startTimeYear = ParamUtil.getParameter(request, "selectStartTimeYear");
    String startTimeMoon = ParamUtil.getParameter(request, "selectStartTimeMoon");
    String startTimeDay = ParamUtil.getParameter(request, "selectStartTimeDay");
    String startTimeHours = ParamUtil.getParameter(request, "selectStartTimeHours");
    String startTimeMinutes = ParamUtil.getParameter(request, "selectStartTimeMinutes");
    String Time = startTimeYear + "-" + startTimeMoon + "-" + startTimeDay + " " + startTimeHours + ":" + startTimeMinutes + ":00.000";
    Timestamp starttime = Timestamp.valueOf(Time);
    int keywordflag=ParamUtil.getIntParameter(request,"isneed",0);
    String tkeyword=ParamUtil.getParameter(request,"titlekeyword");
    String bkeyword=ParamUtil.getParameter(request,"bodykeyword");
    int tbrelation=ParamUtil.getIntParameter(request,"tbrelation",0);

    IBasic_AttributesManager ibaMgr = Basic_AttributesPeer.getInstance();
    GlobalConfig gCfg = new GlobalConfig();
    gCfg.setProxyflag(proxyflag);
    gCfg.setProxyName(proxyServer);
    gCfg.setProxyPort(proxyPort);
    gCfg.setProxyloginflag(proxyloginflag);
    gCfg.setProxyloginuser(proxyloginuser);
    gCfg.setProxyloginpass(proxyloginpass);
    gCfg.setStartTime(starttime);
    gCfg.setInterval(interval);
    gCfg.setKeywordflag(keywordflag);
    gCfg.setTkeyword(tkeyword);
    gCfg.setBkeyword(bkeyword);
    gCfg.setTbrelation(tbrelation);
    gCfg.setId(id);
    
    ibaMgr.update_GlobalConfig(siteid,gCfg);

    response.sendRedirect("sysConfig.jsp");
%>
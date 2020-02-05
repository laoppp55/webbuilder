<%@ page import="java.util.*,
  		 com.bizwink.cms.sitesetting.*,
  		 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
%>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.util.JSON" %>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null) {
    response.sendRedirect("../login.jsp");
    return;
  }

  String sitename = StringUtil.iso2gb(ParamUtil.getParameter(request, "sitename"));
  int siteid = 0;
  try {
    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
    siteid = siteMgr.getSiteID(sitename);
  } catch (Exception e) {
    e.printStackTrace();
  }

  String jsondata = "{\"SITEID\":" + siteid + "}";
  JSON.setPrintWriter(response,jsondata,"utf-8");
%>
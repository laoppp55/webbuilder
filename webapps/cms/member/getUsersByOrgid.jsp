<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
%>
<%@ page import="com.google.gson.Gson" %>
<%@ include file="../include/auth.jsp"%>
<%
    int siteid = authToken.getSiteID();
    int orgid = ParamUtil.getIntParameter(request,"org",0);

    IUserManager userMgr = UserPeer.getInstance();
    List userList = userMgr.getUsersByOrgid(siteid,orgid);

    Gson  gson = new Gson();
    out.print(gson.toJson(userList));
    out.flush();
%>
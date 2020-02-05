<%@ page import="java.util.*,
		             com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*"
		 contentType="text/html;charset=gbk"
%>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.Map" %>

<%
	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if (authToken == null) {
		response.sendRedirect( "../login.jsp?url=member/removeMember.jsp" );
		return;
	}

	if (!SecurityCheck.hasPermission(authToken, 54)) {
		request.setAttribute("message","无管理用户的权限");
		response.sendRedirect("../error.jsp?message=无管理用户的权限");
		return;
	}

	int siteid = authToken.getSiteID();
	String username = authToken.getUserID();
	String userIDs = ParamUtil.getParameter(request, "userids");
	String unloginflagval = ParamUtil.getParameter(request,"unloginflag");
	boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
	int errcode = 1;

	if (doUpdate) {
		IUserManager userMgr = UserPeer.getInstance();
		try {
			errcode = userMgr.batchUnloginUsers(siteid,userIDs,username,unloginflagval);
		} catch (CmsException ue) {
		    errcode = -2;
		}
	}

	Map data = new HashMap();
	data.put("ERRCODE",errcode);
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	String jsondata = gson.toJson(data);
	System.out.println(jsondata);
	JSON.setPrintWriter(response,jsondata,"utf-8");
%>
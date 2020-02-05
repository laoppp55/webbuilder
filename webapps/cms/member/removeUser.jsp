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

	String userID = ParamUtil.getParameter(request, "userid");
	boolean doDelete = ParamUtil.getBooleanParameter(request, "doDelete");
	boolean error = (userID == null);
	int errcode = 1;
	if (userID.equals("admin")) errcode=-2;

	if (doDelete && !error && errcode==1) {
		IUserManager userMgr = UserPeer.getInstance();
		try {
			User user = userMgr.getUser(userID);
			errcode = userMgr.remove(user,authToken.getUserID());
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
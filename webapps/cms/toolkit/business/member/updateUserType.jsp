<%@ page import="com.bizwink.cms.util.*,
	               com.bizwink.cms.security.*"
         contentType="text/html;charset=gbk"%>
<%@ include file="../../../include/auth.jsp"%>

<%
    int errcode = 0;
    User user = null;
    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    if (!SecurityCheck.hasPermission(authToken, 54)) errcode = -2;

    int uid = ParamUtil.getIntParameter(request, "uid",0);
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    IUserManager userManager = UserPeer.getInstance();
    if (doUpdate && errcode == 0) {
        try {
            user = userManager.getUserByUID(uid,siteid);
            if (user!=null) errcode = userManager.updateUserType(siteid, 6, user.getUserID(), username);
        } catch (CmsException ue) {
            ue.printStackTrace();
        }
    }

    String jsondata = "";
    if(user!=null && errcode==0) {
        jsondata = "{\"msg\":\"SUCESS\",\"errcode\":" + errcode + "}";
        out.print(jsondata);
    } else {
        jsondata = "{\"msg\":\"FAILED\",\"errcode\":" + errcode + "}";
        out.print(jsondata);
    }
    out.flush();
%>
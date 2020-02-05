<%@ page import="java.util.*,
		 com.bizwink.cms.server.*,
		 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if( authToken == null ) {
            response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
            return;
        }

        int siteid = authToken.getSiteID();
        int rightid = ParamUtil.getIntParameter(request,"rightid",0);
%>

<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title></head>
<frameset cols=160,*>
<frameset cols=* rows=0,*>
<frame src=columntree.jsp?rightid=<%=rightid%> name=cmsleft>
<frame src=menu.html name=menu>
</frameset>
<frame src=articlesmain.jsp name=cmsright>
</frameset></html>

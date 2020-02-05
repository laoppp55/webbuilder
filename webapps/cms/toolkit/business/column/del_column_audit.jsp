<%@ page import = "java.io.*,
		   java.util.*,
		   com.bizwink.cms.util.*,
		   com.bizwink.cms.server.*,
		   com.bizwink.cms.security.*,
		   com.bizwink.cms.audit.*"
		   contentType = "text/html;charset=gbk"
%>

<%
   Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
   if(authToken == null)
   {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
	return;
   }

   int ID = ParamUtil.getIntParameter(request,"ID",0);

   IAuditManager auditManager = AuditPeer.getInstance();

   //删除该条记录
   if (ID != 0)
   {
      try
      {
   	auditManager.Del_Column_AuditRules(ID);
      }
      catch (CmsException c)
      {
      }
   }
%>

<html>
<head>
<script language=javascript>
function window_close()
{
    window.close();
    opener.close();
    opener.opener.history.go(0);
}
</script>
</head>
<body onload="javascript:window_close();">
</body>
</html>
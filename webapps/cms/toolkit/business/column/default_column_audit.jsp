<%@ page import = "java.io.*,
		   java.util.*,
		   java.sql.Timestamp,
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

  int ID = ParamUtil.getIntParameter(request, "ID", 0);
  int columnID = ParamUtil.getIntParameter(request, "columnID", -1);
  Timestamp lastUpdated = new Timestamp(System.currentTimeMillis());
  String editor = authToken.getUserID();

  IAuditManager auditManager = AuditPeer.getInstance();

  //设置默认规则
  if (ID != 0 && columnID != -1)
  {
    try
    {
      //先清除库中已有的默认规则
      auditManager.reset_Default_AuditRules(columnID);

      //再添加新的默认规则,同时更新LastUpdated,Editor
      auditManager.setup_Default_AuditRules(ID,lastUpdated,editor);
    }
    catch (CmsException c)
    {
      c.printStackTrace();
    }
  }
%>

<html>
<body onload="javascript:window.close();">
</body>
</html>

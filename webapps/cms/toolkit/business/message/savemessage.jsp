<%@page import="java.util.*,
                java.io.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.business.Message.*"
                contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int siteid      = authToken.getSiteID();

  int id     = ParamUtil.getIntParameter(request,"id",0);
  int userid = ParamUtil.getIntParameter(request,"userid",0);

  IMessageManager messageMgr = messagePeer.getInstance();

  messageMgr.newSaveMessage(id,userid,siteid);


%>

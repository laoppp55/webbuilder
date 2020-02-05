<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.business.Message.*" contentType="text/html;charset=gbk"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int siteid = authToken.getSiteID();

  String[] messages = request.getParameterValues("delMessage");
  int flag = ParamUtil.getIntParameter(request,"flag",-1);
  IMessageManager messageMgr = messagePeer.getInstance();
if(flag != -1){
  int id = 0;
  for(int i=0;i<messages.length;i++){
    id = Integer.parseInt(messages[i]);
    messageMgr.updateDeleteFlag(id,flag,siteid);
  }
  if(flag==1)
    response.sendRedirect("index.jsp");
  if(flag==2)
    response.sendRedirect("index2.jsp");
}else{
  response.sendRedirect("index.jsp");
}
%>
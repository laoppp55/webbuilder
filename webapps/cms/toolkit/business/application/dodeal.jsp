<%@page import="java.util.*,
                java.io.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.business.Other.*"
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

  int id = ParamUtil.getIntParameter(request,"id",0);
  int kind = ParamUtil.getIntParameter(request,"kind",0);
  int status = ParamUtil.getIntParameter(request,"status",0);
  int userid = ParamUtil.getIntParameter(request,"userid",0);
  int startrow = ParamUtil.getIntParameter(request,"startrow",0);
  int range = ParamUtil.getIntParameter(request,"range",50);
  String username = ParamUtil.getParameter(request,"username");
  String backstr = ParamUtil.getParameter(request,"backstr");

  IOtherManager otherMgr = otherPeer.getInstance();
  otherMgr.makeDealed(id,kind,status,siteid);
  backstr = backstr + "?userid="+String.valueOf(userid)+
            "&username="+username+"&startrow="+String.valueOf(startrow)+"&range="+String.valueOf(range);
  response.sendRedirect(backstr);
%>

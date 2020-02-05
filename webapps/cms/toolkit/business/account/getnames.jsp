<%@page import="java.util.*,
                java.io.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.business.Users.*"
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

  String userid     = ParamUtil.getParameter(request,"userid");
  BUser buser = new BUser();
  IBUserManager buserMgr = buserPeer.getInstance();
  buser = buserMgr.getAUsers(userid,siteid);
  String username = StringUtil.gb2iso4View(buser.getUserName());
  username = username==null?"":username;
  String content = "_USERNAME_" + userid + "_UOK_" + "_REALNAME_" + username + "_ROK_";

  out.print(content.trim());
%>

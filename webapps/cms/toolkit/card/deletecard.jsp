<%@ page import="com.bizwink.cms.util.ParamUtil"%>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager"%>
<%@ page import="com.bizwink.cms.business.Order.orderPeer"%>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
      int siteid = authToken.getSiteID();
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    int startrow = ParamUtil.getIntParameter(request,"startrow",0);
    int id = ParamUtil.getIntParameter(request,"id",0);
    if(startflag == 1){
        IOrderManager oMgr = orderPeer.getInstance();
        int errcode = oMgr.deleteACard(id);
        if(errcode == 0){
            out.println("<script language=javascript>alert(\"删除成功！\");window.location=\"index.jsp?startrow="+startrow+"\";</script>");
        }else{
            out.println("<script language=javascript>alert(\"删除失败！\");window.history.go(-1);</script>");
        }
    }
%>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.IYuDingManager" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.YuDingPeer" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.YuDing" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@page contentType="text/html;charset=gbk"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
    String shperson = authToken.getUserID();
    int id = ParamUtil.getIntParameter(request, "id", -1);
    int flag = ParamUtil.getIntParameter(request, "flag", -1);
    IYuDingManager yMgr = YuDingPeer.getInstance();
    YuDing yd = new YuDing();

    if(flag == 1){
        flag = 2;
        yd.setFlag(flag);
        yMgr.updateshenheYuDing(yd,id);
    }else{
        flag = 1;
        yd.setFlag(flag);
        yd.setShperson(shperson);
        yd.setShdate(new Timestamp(System.currentTimeMillis()));
        yMgr.updateshenheYuDing(yd,id);
    }
    response.sendRedirect("index.jsp");
%>

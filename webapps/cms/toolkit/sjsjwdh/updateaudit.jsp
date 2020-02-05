<%@page import="com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.sjswsbs.*" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    int auditflag = ParamUtil.getIntParameter(request,"auditflag",0);
    if(auditflag == 0)
        auditflag=1;
    else
        auditflag=0;
    IWsbsManager wsbsMgr = WsbsPeer.getInstance();
    wsbsMgr.updatejwdh(id,auditflag);
    response.sendRedirect("index.jsp");
%>
<%@page import="com.bizwink.cms.util.ParamUtil," contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.cms.toolkit.csinfo.ICsInfoManager" %>
<%@ page import="com.bizwink.cms.toolkit.csinfo.CsInfoPeer" %>

<%
   int id = ParamUtil.getIntParameter(request, "id", 0);
    ICsInfoManager csInfoMgr = CsInfoPeer.getInstance();
    csInfoMgr.deleteCsInfo(id);
    csInfoMgr.deleteCsInfoPic(id);
    response.sendRedirect("index.jsp");
%>
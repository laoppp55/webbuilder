<%@page import="com.bizwink.cms.kings.changemaster.ChangeMasterPeer,
                com.bizwink.cms.kings.changemaster.IChangeMasterManager,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    String changeid = ParamUtil.getParameter(request,"changeid");
    //System.out.println("changeid = " + changeid);
    //System.out.println("aa = " + id);
    IChangeMasterManager chaMgr = ChangeMasterPeer.getInstance();
    chaMgr.deleteChangeMaster(id,changeid);
    response.sendRedirect("changemaster.jsp");
%>
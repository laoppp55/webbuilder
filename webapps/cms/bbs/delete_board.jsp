<%@page import="com.bizwink.cms.util.*,
                com.bizwink.bbs.bbs.*" contentType="text/html;charset=gbk"
        %>
<%
    if ((session.getValue("UserClass")==null)||(!session.getValue("UserClass").equals("系统管理员")))
    {
        response.sendRedirect("/bbs/index.jsp");
    }

    int modflag        = ParamUtil.getIntParameter(request, "modflag", -1);
    int modifyid       = ParamUtil.getIntParameter(request, "modifyid", -1);

    if((modflag == 1)&&(modifyid != -1)){
        IBBSManager bbsMgr = BBSPeer.getInstance();
        bbsMgr.deleteForum(modifyid);
        response.sendRedirect("board_manager.jsp");
    }
%>


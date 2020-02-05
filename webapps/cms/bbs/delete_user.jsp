<%@page import="com.bizwink.cms.util.*,
                com.bizwink.bbs.bbs.*" contentType="text/html;charset=gbk"
        %>
<%
    if ((session.getValue("UserClass")==null)||(!session.getValue("UserClass").equals("系统管理员")))
    {
        response.sendRedirect("/bbs/index.jsp");
    }

    int id           = ParamUtil.getIntParameter(request, "id", -1);
    int startflag    = ParamUtil.getIntParameter(request, "startflag", -1);

    if(startflag == 1){
        IBBSManager bbsMgr = BBSPeer.getInstance();
        bbsMgr.deleteUser(id) ;
        response.sendRedirect("user_manager.jsp");
    }
%>


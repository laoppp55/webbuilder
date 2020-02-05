<%@page import="com.bizwink.cms.util.*,
                com.bizwink.bbs.bbs.*" contentType="text/html;charset=gbk"
%>
<%
    if ((session.getValue("UserClass")==null)||(!session.getValue("UserClass").equals("系统管理员")))
    {
        response.sendRedirect("/bbs/index.jsp");
    }

    String title = ParamUtil.getParameter(request, "title");
    String mgr = ParamUtil.getParameter(request, "manager");
    int modflag = ParamUtil.getIntParameter(request, "modflag", -1);

    if(modflag == 1){
        IBBSManager bbsMgr = BBSPeer.getInstance();
        BBS bbs = new BBS();

        int id = bbsMgr.getMaxForumID()+1;
        bbs.setID(id);
        bbs.setBBSName(title);
        bbs.setManager(mgr);
        bbsMgr.insertForum(bbs);
        response.sendRedirect("board_manager.jsp");
    }
%>


<%@ page import="com.bizwink.cms.news.Turnpic" %>
<%@ page import="com.bizwink.cms.news.ArticlePeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.IArticleManager" %>
<%@page contentType="text/html;charset=utf-8" %>
<%
    int editflag = ParamUtil.getIntParameter(request, "editflag", 0);
    int articlePicID = ParamUtil.getIntParameter(request, "article", 0);
    String notes = ParamUtil.getParameter(request,"notes");

    if (editflag == 1) {
        IArticleManager aMgr = ArticlePeer.getInstance();
        Turnpic tpic1 = new Turnpic();
        tpic1 = aMgr.getAArticleTurnPic(articlePicID);
        Turnpic tpic = new Turnpic();
        tpic.setId(articlePicID);
        tpic.setNotes(notes);
        tpic.setPicname(tpic1.getPicname());

        aMgr.updataTurnPicInfo(tpic);
        out.println("<script language=\"javascript\">");
        out.println("opener.history.go(0);");
        out.println("window.close();");
        out.println("</script>");
        return;
    }
%>
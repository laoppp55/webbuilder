<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.feedback.FeedBack" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    int siteid = 39;

    IFeedbackManager feedMgr = FeedbackPeer.getInstance();
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    String sitename = request.getServerName();  //site name
    System.out.println("sitename=" + sitename);
    siteid = feedMgr.getSiteID(sitename);     //get siteid
    System.out.println("siteid=" + siteid);

    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    if(startflag == 1){
        int siteids = ParamUtil.getIntParameter(request,"siteid",0);
        String title = ParamUtil.getParameter(request,"title");
        String content = ParamUtil.getParameter(request,"content");
        String email = ParamUtil.getParameter(request,"email");
        String phone = ParamUtil.getParameter(request,"phone");
        String company = ParamUtil.getParameter(request,"company");
        String linkman = ParamUtil.getParameter(request,"linkman");
        String links = ParamUtil.getParameter(request,"links");
        String zip = ParamUtil.getParameter(request,"zip");
        String ip = request.getRemoteHost();

        Word word = new Word();
        word.setSiteid(siteids);
        word.setTitle(title);
        word.setContent(content);
        word.setEmail(email);
        word.setPhone(phone);
        word.setCompany(company);
        word.setLinkman(linkman);
        word.setLinks(links);
        word.setZip(zip);
        word.setUserid(ip);
        wordMgr.insertWord(word);
        out.println("<script   lanugage=\"javascript\">alert(\"您的建议已经被接收,感谢您的建议，我们将做适当的处理！\");window.location=\"/index.shtml\";</script>");
        //response.sendRedirect("/index.shtml");
    }
%>
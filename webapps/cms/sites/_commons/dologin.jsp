<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>

<%
    String UserName = (String)request.getParameter("username");
    String Pass = (String)request.getParameter("password");
    String target_url = ParamUtil.getParameter(request,"refererurl");

    IUregisterManager regMgr = UregisterPeer.getInstance();
    int siteid = ParamUtil.getIntParameter(request,"siteid",0);
    //int Siteid = siteid;
    String fromurl = "";
    if (target_url == null || target_url == "") {
        fromurl = request.getHeader("REFERER") ;
    } else {
        fromurl = target_url;
    }

    //System.out.println("fromurl=" + fromurl);
    //System.out.println("UserName=" + UserName);
    //System.out.println("Pass=" + Pass);
    //System.out.println("siteid=" + siteid);

    Uregister ug = regMgr.login(UserName,Pass,siteid);
    if(ug.getMemberid() != null){
        session.setAttribute("UserLogin",ug);
        session.setMaxInactiveInterval(1000*10);
        if(fromurl.equals("undefined"))
        {
            fromurl="/";
        }
        if(fromurl.indexOf("register.jsp")!=-1)
        {
            fromurl="/";
        }
        if(fromurl.indexOf("login.jsp")!=-1)
        {
            fromurl="/";
        }
        response.sendRedirect(fromurl + "?name="+ug.getMemberid());
    }else{
        out.print("<script language=\"javascript\">alert(\"ÓÃ»§ÃûÃÜÂë´íÎó£¡\");window.location=\""+fromurl+"\";</script>");
    }
%>
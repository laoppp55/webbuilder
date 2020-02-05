<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    String fromurl = request.getHeader("REFERER");

    String title = ParamUtil.getParameter(request,"title");
    String content = ParamUtil.getParameter(request,"content");
    String company = ParamUtil.getParameter(request,"company");
    String linkman = ParamUtil.getParameter(request,"linkman");
    String links = ParamUtil.getParameter(request,"links");
    String zip = ParamUtil.getParameter(request,"zip");
    String email = ParamUtil.getParameter(request,"email");
    String phone = ParamUtil.getParameter(request,"phone");
    int startflag = ParamUtil.getIntParameter(request,"startflag",0);
    int siteid = ParamUtil.getIntParameter(request,"siteid",0);
    int formid = ParamUtil.getIntParameter(request,"formid",0);
    if(startflag == 1){
        Word word = new Word();
        word.setCompany(company);
        word.setContent(content);
        word.setEmail(email);
        word.setLinkman(linkman);
        word.setLinks(links);
        word.setPhone(phone);
        word.setSiteid(siteid);
        word.setFormid(formid);
        word.setTitle(title);
        word.setUserid(request.getRemoteHost());
        word.setZip(zip);
        IWordManager wMgr =  LeaveWordPeer.getInstance();
        wMgr.insertWord(word);
        out.write("<script language=\"javascript\">alert(\"提交成功！\");window.location=\""+fromurl+"\";</script>");
    }else{
        response.sendRedirect(fromurl);
    }
%>
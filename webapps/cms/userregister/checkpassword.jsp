<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%
    String Password = ParamUtil.getParameter(request,"pass").trim();
    String Name = ParamUtil.getParameter(request,"uid").trim();
    int siteid =  ParamUtil.getIntParameter(request,"siteid",0);
    IUregisterManager regMgr = UregisterPeer.getInstance();
    int flag= regMgr.getPassword(Name,Password,siteid);
    if(flag==1){
        out.write(1+"-¾ÉÃÜÂëÕýÈ·!");
    }else{
        out.write(0+"-¾ÉÃÜÂë´íÎó!");
    }

%>
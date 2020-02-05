<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>

<%
    String email = ParamUtil.getParameter(request,"email");
    int siteid =  ParamUtil.getIntParameter(request,"siteid",0);
    IUregisterManager regMgr = UregisterPeer.getInstance();

    int flag= regMgr.checkUserEmailExist(email,siteid);
    if(flag==1){
        out.write(1+"-不可以使用!");
    }else{
        out.write(0+"-可以使用!");
    }
%>
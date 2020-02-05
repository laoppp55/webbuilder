<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.cms.register.*" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%
  	String email = ParamUtil.getParameter(request,"email");
    IRegisterManager regMgr = RegisterPeer.getInstance();

    int flag= regMgr.checkSystemUserEmailExist(email);
    if(flag==1 || flag == 2){
		out.write(1+"-电子邮件地址存在");
	}else{
		out.write(0+"-电子邮件地址不存在，可以使用");
	}
  %>
 
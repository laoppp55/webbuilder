<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.cms.register.*" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%
  	String name = ParamUtil.getParameter(request,"username");
    IRegisterManager regMgr = RegisterPeer.getInstance();

    System.out.println("name=" + name);
    int flag= regMgr.checkSystemUserExist(name);
    if(flag==1 || flag == 2){
		out.write(1+"-用户已经存在");
	}else{
		out.write(0+"-用户不存在，可以使用");
	}
  %>
 
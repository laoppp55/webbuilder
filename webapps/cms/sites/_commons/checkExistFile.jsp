<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.xml.*" %>

<%
    String filename = ParamUtil.getParameter(request,"filename");
    IFormManager formMgr = FormPeer.getInstance();

    System.out.println("filename=" + filename);

    int flag= formMgr.existFilename(filename) ;

    System.out.println("flag=" + flag);

    if(flag==1){
        out.write(1);
    }else{
        out.write(0);
    }
%>
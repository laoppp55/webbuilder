<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.xml.*" %>
<%@ page import="com.bizwink.cms.security.*"%>
<%@ page import="com.bizwink.cms.util.*"%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    String sitename = authToken.getSitename();
    String baseDir = application.getRealPath("/");
    baseDir = baseDir + "sites"+java.io.File.separator+sitename+java.io.File.separator;
    String filename = ParamUtil.getParameter(request,"filename");
    int posi = filename.lastIndexOf(java.io.File.separator);
    if (posi >-1) filename =baseDir +  filename.substring(posi+1);
    IFormManager formMgr = FormPeer.getInstance();

    System.out.println("ggfilename=" + filename);

    int flag= formMgr.existFilename(filename) ;

    System.out.println("flag=" + flag);

    if(flag==1){
        out.write(1);
    }else{
        out.write(0);
    }
%>
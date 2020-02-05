<%@ page language="java" contentType="text/html;charset=GBK" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.*" %>
                                                                            
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    String Path_XML = application.getRealPath("/") + "sites" + java.io.File.separator + authToken.getSitename() + java.io.File.separator + "_prog"+ java.io.File.separator;


    ICompanyinfoManager IComMgr = CompanyinfoPeer.getInstance();
    int i = 0;
    i = IComMgr.CreateXML(siteid,Path_XML);
    if( i != 0 ){
          out.write("<script type=\"text/javascript\">alert('发布失败，请重新发布');window.location='index.jsp'</script>");
    }else{
          out.write("<script type=\"text/javascript\">alert('发布成功！');window.location='index.jsp'</script>");
    }
    
   

%>
<html>
  <head><title>Simple jsp page</title></head>
  <body>Place your content here</body>
</html>
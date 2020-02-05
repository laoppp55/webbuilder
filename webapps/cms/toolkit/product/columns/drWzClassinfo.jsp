<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="java.math.BigDecimal" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-5-21
  Time: 上午10:21
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteID = authToken.getSiteID();
    String baseDir = application.getRealPath("/");
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");

    if (doCreate) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.setCharset("gbk");
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();
        com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
        com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
        String filename = tempFile.getFileName();
        String uploadPath = baseDir + "sites" + java.io.File.separator + sitename + "_temp" + java.io.File.separator;
        java.io.File file = new java.io.File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        mySmartUpload.save(uploadPath);
        mySmartUpload.stop();

        ApplicationContext appContext = SpringInit.getApplicationContext();
        String json_data = null;
        if (appContext!=null) {
            drwzclassinfo drwzclassinfo = (drwzclassinfo)appContext.getBean("drwzclassinfo");
            json_data = drwzclassinfo.DrWzClassinfo(BigDecimal.valueOf(0),BigDecimal.valueOf(siteID),uploadPath + filename);
        }

        if (json_data != null){
            out.print(json_data);
            out.flush();
        } else {
            out.print("nodata");
            out.flush();
        }

        return;
    }
%>
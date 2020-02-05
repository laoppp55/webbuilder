<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page import="com.bizwink.service.ColumnService" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-5-29
  Time: 上午11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    BigDecimal siteid = BigDecimal.valueOf(authToken.getSiteID());
    String sitename = authToken.getSitename();
    String operation = null;
    ApplicationContext appContext = SpringInit.getApplicationContext();

    if (appContext!=null) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.setCharset("utf-8");
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();

        //获取上传的普通字段信息
        operation = mySmartUpload.getRequest().getParameter("doCreate");
        String wzname = mySmartUpload.getRequest().getParameter("wzname");
        String wzclass = mySmartUpload.getRequest().getParameter("wztype");
        String wzdesc = mySmartUpload.getRequest().getParameter("wzdesc");

        //清除不允许用户输入的信息
        if (wzname!=null) wzname= filter.excludeHTMLCode(wzname);
        if (wzclass!=null) wzclass = filter.excludeHTMLCode(wzclass);
        if (wzdesc!=null) wzdesc = filter.excludeHTMLCode(wzdesc);

        ColumnService columnService = (ColumnService)appContext.getBean("columnService");

        //设置上传文件的保存路径
        String uploadPath = application.getRealPath("/") + "sites" + java.io.File.separator + sitename +
                StringUtil.replace(dirName, "/", java.io.File.separator) + "download" + java.io.File.separator;
        java.io.File file = new java.io.File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        //获取上传的文件信息，包括图片文件、介绍资料等信息
        com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
        for(int ii=0;ii<uploadFiles.getCount();ii++) {
            com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
            String filename = tempFile.getFileName();
            mySmartUpload.save(uploadPath);
        }

        //清除上传文件占用的内存信息
        mySmartUpload.stop();

        System.out.println("wzname===" + wzname);
        System.out.println("wzclass===" + wzclass);
        System.out.println("wzdesc===" + wzdesc);
    }

    String content = "hello word" + "===" + operation;
    out.write(content);
    out.flush();
%>

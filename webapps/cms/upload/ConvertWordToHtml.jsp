<%@ page import="com.bizwink.ConvertToHtml.WordToHtml" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.ConvertToHtml.DocxToHtml" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-5-11
  Time: 下午3:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
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

    IColumnManager columnMgr = ColumnPeer.getInstance();

    if (doCreate) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.setCharset("gbk");
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();

        String columnID = mySmartUpload.getRequest().getParameter("column");
        Column column = columnMgr.getColumn(Integer.parseInt(columnID));
        String dirName = column.getDirName();

        com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
        com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
        String filename = tempFile.getFileName();
        String uploadPath = application.getRealPath("/") + "sites" + java.io.File.separator + sitename +
                StringUtil.replace(dirName, "/", java.io.File.separator) + "download" + java.io.File.separator;

        java.io.File file = new java.io.File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        mySmartUpload.save(uploadPath);
        mySmartUpload.stop();

        //转换WORD文件内容为HTML
        if (filename.toLowerCase().endsWith(".doc")) {
            WordToHtml wordToHtml = new WordToHtml();
            String content = wordToHtml.convert2Html(username,sitename,baseDir,dirName,filename,siteID,0);
            out.write(content);
            out.flush();
        } if (filename.toLowerCase().endsWith(".docx")) {
            DocxToHtml docxToHtml = new DocxToHtml();
            String content = docxToHtml.docx2Html(username,sitename,siteID,baseDir,dirName,filename,0);
            out.write(content);
            out.flush();
        } else {
            String content="上传的文件类型错误";
            out.write(content);
            out.flush();
        }
        return;
    }
%>

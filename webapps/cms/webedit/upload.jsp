<%@ page import="com.bizwink.cms.security.*,
                 com.jspsmart.upload.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null) {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  boolean success = false;
  int siteID = authToken.getSiteID();
  String username = authToken.getUserID();
  String siteName = authToken.getSitename();
  String oldFilePath = "";

  SmartUpload mySmartUpload = new SmartUpload();
  mySmartUpload.initialize(this.getServletConfig(), request, response);

  try {
    mySmartUpload.setCharset("utf-8");
    mySmartUpload.upload();
    String path = mySmartUpload.getRequest().getParameter("path");
    oldFilePath = path;

    System.out.println("old file path==" + oldFilePath);

    File tempFile = mySmartUpload.getFiles().getFile(0);
    if (tempFile == null) {
      response.sendRedirect("webmain.jsp?filePath=" + oldFilePath + "&msg=文件上传失败，请重试！");
      return;
    }

    String dirName = path.substring(path.indexOf(siteName)+siteName.length(),path.lastIndexOf("/")+1);
    String unzip = mySmartUpload.getRequest().getParameter("unzip");
    path = StringUtil.replace(path, "/", java.io.File.separator);
    java.io.File file = new java.io.File(path);
    if (!file.exists()) file.mkdirs();
    String fileName = tempFile.getFileName();

    //保存文件
    mySmartUpload.save(path);
    mySmartUpload = null;

    //如果是压缩包并且需要解压
    String extName = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();

    System.out.println("extname==" + extName);

    if (unzip.equals("1") && (extName.equals("zip"))) {
      UnZip unzipHandle = new UnZip();
      unzipHandle.UnZipAnywhere(path + fileName, path, siteName, siteID, 0);
      System.out.println("path==" + (path + fileName));
      file = new java.io.File(path + fileName);
      file.delete();
    } else{    //发布到WEB服务器
      IPublishManager publishMgr = PublishPeer.getInstance();
      publishMgr.publish(username, path+fileName, siteID, dirName, 0);
    }
    success = true;
  } catch (Exception e) {
    e.printStackTrace();
  }

  if (success)
    response.sendRedirect("webmain.jsp?filePath=" + oldFilePath + "&msg=文件上传成功！");
  else
    response.sendRedirect("webmain.jsp?filePath=" + oldFilePath + "&msg=文件上传失败，请重试！");
%>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.images.resizeImage" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.bizwink.cms.sitesetting.*" %>
<%@ page import="com.bizwink.cms.security.IUserManager" %>
<%@ page import="com.bizwink.cms.security.UserPeer" %>
<%@ page import="com.bizwink.cms.security.User" %>
<%@ page contentType="text/html;charset=gbk" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-5-11
  Time: 下午3:25
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../../../include/auth.jsp"%>
<%
    String username = authToken.getUserID();
    String appPath = application.getRealPath("/");
    int siteid = authToken.getSiteID();
    String sitename = authToken.getSitename();
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int uid = ParamUtil.getIntParameter(request, "uid",0);
    ErrorMessage errorMessage = new ErrorMessage();
    String img_url = null;
    Map result = new HashMap();
    User user = null;
    IFtpSetManager ftpSetManager = FtpSetting.getInstance();
    IUserManager userManager = UserPeer.getInstance();
    List<FtpInfo> ftpInfos = ftpSetManager.getFtpInfos(siteid);
    if (username!=null && doCreate) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.setCharset("utf-8");
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();
        if (ftpInfos!=null) {
            com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
            com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
            String filename = tempFile.getFileName();
            if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg") ||filename.toLowerCase().endsWith(".gif") || filename.toLowerCase().endsWith(".png")) {
                String uploadPath = appPath + "sites" + File.separator + sitename + File.separator + "images" + File.separator;
                java.io.File file = new java.io.File(uploadPath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                String extname = filename.substring(filename.indexOf("."));
                String filePathname = uploadPath + uuid + extname;
                tempFile.saveAs(filePathname);
                mySmartUpload.save(uploadPath);
                mySmartUpload.stop();

                resizeImage resizeImage = new resizeImage();
                String targetFilename = resizeImage.createThumbnailBy_jmagick(siteid,username,"/",filePathname,"67X67");
                if (targetFilename!=null) {
                    String yuan_fielname = resizeImage.transferImgForRoundImgage(targetFilename,120);
                    if (yuan_fielname!=null) {
                        img_url = "/images/" + yuan_fielname.substring(yuan_fielname.lastIndexOf(File.separator) + 1);
                        user = userManager.getUserByUID(uid,siteid);
                        if (user!=null) {
                            userManager.updateUserHeadImg(siteid, img_url, user.getUserID(), username);
                        }
                    } else {
                        errorMessage.setErrcode(-11);
                        errorMessage.setErrmsg(ConstantsField.UPLOAD_HEADIMAGE_ZOOM_ERROR);
                        errorMessage.setModelname(ConstantsField.UPLOAD_HEADIMAGE_PROGRAM);
                    }
                    File sfile = new File(uploadPath + filename);
                    if (sfile.exists()) sfile.delete();
                    File uuidfile = new File(filePathname);
                    if (uuidfile.exists()) uuidfile.delete();
                } else {
                    errorMessage.setErrcode(-12);
                    errorMessage.setErrmsg(ConstantsField.UPLOAD_HEADIMAGE_ZOOM_ERROR);
                    errorMessage.setModelname(ConstantsField.UPLOAD_HEADIMAGE_PROGRAM);
                }
            } else {
                errorMessage.setErrcode(-14);
                errorMessage.setErrmsg(ConstantsField.UPLOAD_HEADIMAGE_FILETYPE_ERROR);
                errorMessage.setModelname(ConstantsField.UPLOAD_HEADIMAGE_PROGRAM);
            }
        } else {
            errorMessage.setErrcode(-11);
            errorMessage.setErrmsg(ConstantsField.UPLOAD_HEADIMAGE_UPLOADPATH_ERROR);
            errorMessage.setModelname(ConstantsField.UPLOAD_HEADIMAGE_PROGRAM);
        }
    } else {
        if (username==null) {
            errorMessage.setErrcode(-10);
            errorMessage.setErrmsg(ConstantsField.UPLOAD_HEADIMAGE_NOLOGIN_ERROR);
            errorMessage.setModelname(ConstantsField.UPLOAD_HEADIMAGE_PROGRAM);
        }
    }

    response.setHeader("Content-Type", "application/json;charset=UTF-8");                //注意加上这一句
   if (user!=null) {
       result.put("url", img_url);
       result.put("error", errorMessage);
   } else {
       result.put("url", "");
       result.put("error", errorMessage);
   }
    Gson gson = new Gson();
    String jsondata = gson.toJson(result);
    System.out.println(jsondata);
    out.print(jsondata);
    out.flush();
%>

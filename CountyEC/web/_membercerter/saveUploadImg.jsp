<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.SiteipinfoService" %>
<%@ page import="com.bizwink.po.Siteipinfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.io.File" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.images.resizeImage" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=gbk" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-5-11
  Time: 下午3:25
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username = null;

    //入股SESSION失效，试试COOKIE是否可以读出来
    Cookie[] cookies = request.getCookies();
    String cookie_value = null;
    for(Cookie c :cookies ){
        if (c.getName().equalsIgnoreCase("AuthInfo_cookie")) {
            SecurityUtil securityUtil = new SecurityUtil();
            cookie_value = securityUtil.detrypt(c.getValue(),null);
            break;
        }
    }

    if (cookie_value != null) {
        int posi = cookie_value.indexOf("-");
        username = cookie_value.substring(0, posi);
    } else {                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
        if (authToken != null) {
            SessionUtil.removeUserAuthorization(response, session);
        }
    }

    int errcode = 0;
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    ErrorMessage errorMessage = new ErrorMessage();
    String destFilename = null;
    String img_url = null;
    Map result = new HashMap();
    if (username!=null && doCreate) {
        SmartUpload mySmartUpload = new SmartUpload();
        //mySmartUpload.setCharset("gbk");
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();

        int siteid = Integer.parseInt(mySmartUpload.getRequest().getParameter("siteid"));
        String imgtype = mySmartUpload.getRequest().getParameter("imgtype");
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            List<Siteipinfo> siteipinfos = null;
            SiteipinfoService siteipinfoService = (SiteipinfoService)appContext.getBean("SiteipinfoService");
            UsersService usersService = (UsersService)appContext.getBean("UsersService");
            Users user = usersService.selectByPrimaryKey(username);
            if(siteid > 0) siteipinfos = siteipinfoService.getSiteipinfoBySiteid(BigDecimal.valueOf(siteid));

            if (siteipinfos!=null) {
                Siteipinfo siteipinfo = siteipinfos.get(0);
                com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
                com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
                String filename = tempFile.getFileName();

                if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg") ||filename.toLowerCase().endsWith(".gif") || filename.toLowerCase().endsWith(".png")) {
                    String uploadPath = null;
                    if (siteipinfo.getDOCPATH().endsWith(File.separator)) {
                        uploadPath = siteipinfo.getDOCPATH() + "images" + java.io.File.separator;
                    }else {
                        uploadPath = siteipinfo.getDOCPATH() + File.separator + "images" + java.io.File.separator;
                    }
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

                    if (imgtype.equalsIgnoreCase("himg")) {                     //获取用户头像的缩略图，并将图片URL保存到tbl_members表
                        resizeImage resizeImage = new resizeImage();
                        destFilename = uploadPath + uuid + "67x67" + extname;
                        errcode = resizeImage.zoomImage(67,67,filePathname,destFilename);
                        if (errcode == 0) {
                            img_url = "http://" + siteipinfo.getSITENAME() + "/images/" + uuid + "67x67" + extname;
                            user.setMYIMAGE(img_url);
                            errcode = usersService.updateUserInfo(user);
                            if (errcode > 0) {
                                errorMessage.setErrcode(0);
                                errorMessage.setErrmsg(ConstantsField.UPLOAD_HEADIMAGE_SUCESS);
                                errorMessage.setModelname(ConstantsField.UPLOAD_HEADIMAGE_PROGRAM);
                            } else {
                                errorMessage.setErrcode(-13);
                                errorMessage.setErrmsg(ConstantsField.UPLOAD_HEADIMAGE_SAVEDB_ERROR);
                                errorMessage.setModelname(ConstantsField.UPLOAD_HEADIMAGE_PROGRAM);
                            }
                        } else {
                            errorMessage.setErrcode(-12);
                            errorMessage.setErrmsg(ConstantsField.UPLOAD_HEADIMAGE_ZOOM_ERROR);
                            errorMessage.setModelname(ConstantsField.UPLOAD_HEADIMAGE_PROGRAM);
                        }
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
            errorMessage.setErrcode(-1);
            errorMessage.setErrmsg(ConstantsField.UPLOAD_HEADIMAGE_FILETYPE_ERROR);
            errorMessage.setModelname(ConstantsField.UPLOAD_HEADIMAGE_PROGRAM);
        }
    } else {
        if (username==null) {
            errorMessage.setErrcode(-10);
            errorMessage.setErrmsg(ConstantsField.UPLOAD_HEADIMAGE_NOLOGIN_ERROR);
            errorMessage.setModelname(ConstantsField.UPLOAD_HEADIMAGE_PROGRAM);
        }
    }

    result.put("url",img_url);
    result.put("error",errorMessage);
    Gson gson = new Gson();
    String jsondata = gson.toJson(result);
    System.out.println(jsondata);
    out.print(jsondata);
    out.flush();
%>

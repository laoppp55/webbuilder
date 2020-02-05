<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="java.io.File" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bizwink.upload.vo.Error" %>
<%@ page import="com.bizwink.upload.vo.CkeditorUploadJson" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-5-11
  Time: 下午3:25
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteid = authToken.getSiteID();
    String appPath = request.getSession().getServletContext().getRealPath("");
    String targetfilename = "abc.jpg";
    //Uploadimage uploadimage = null;
    //ErrorMessage errorMessage = new ErrorMessage();
    //Map result = new HashMap();
    //Map imgtypes = new HashMap();
    IColumnManager columnManager = ColumnPeer.getInstance();
    int columnid = ParamUtil.getIntParameter(request,"column",0);
    System.out.println("columnid==" + columnid);
    if (username!=null) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.setCharset("utf-8");
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();
        String brief = mySmartUpload.getRequest().getParameter("notes");
        Column column = columnManager.getColumn(columnid);
        String filedir = column.getDirName().replace("/",File.separator);

        com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
        com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
        String filename = tempFile.getFileName();

        if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg") ||filename.toLowerCase().endsWith(".gif") || filename.toLowerCase().endsWith(".png")) {
            String uploadPath = null;
            uploadPath = appPath + "sites" + File.separator + sitename.replace(".","_") + filedir + "images" + java.io.File.separator;
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
        }
    }

    String fileUrl = "http://29e5534ea20a8.cdn.sohucs.com/c_zoom,h_103/c_cut,x_0,y_0,w_1196,h_797/os/news/e8d2d07e66938cdfc32d0f713b7722b7.jpg";
    Error error = new Error();
    error.setCode(0);
    error.setMessage("hello");
    CkeditorUploadJson ckeditorUploadJson = new CkeditorUploadJson();
    ckeditorUploadJson.setUploaded(1);
    ckeditorUploadJson.setFileName("e8d2d07e66938cdfc32d0f713b7722b7.jpg");
    ckeditorUploadJson.setUrl(fileUrl);
    ckeditorUploadJson.setError(error);
    Gson gson = new Gson();
    String jsondata = gson.toJson(ckeditorUploadJson);
    System.out.print(jsondata);
    out.println(jsondata);
    out.flush();
    out.close();
%>

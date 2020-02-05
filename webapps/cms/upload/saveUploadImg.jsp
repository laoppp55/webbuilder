<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.io.File" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.images.resizeImage" %>
<%@ page import="com.bizwink.images.Uploadimage" %>
<%@ page import="java.util.*" %>
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

    Uploadimage uploadimage = null;
    ErrorMessage errorMessage = new ErrorMessage();
    Map result = new HashMap();
    Map imgtypes = new HashMap();
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    IColumnManager columnManager = ColumnPeer.getInstance();

    if (username!=null && doCreate) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.setCharset("utf-8");
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();
        int columnid = Integer.parseInt(mySmartUpload.getRequest().getParameter("column"));
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

            //生成不同尺寸的小图片
            resizeImage resizeImage = new resizeImage();
            Iterator<Map.Entry<String, String>> entries = imgtypes.entrySet().iterator();
            uploadimage = new Uploadimage();
            uploadimage.setBrief(brief);
            uploadimage.setFilepath(column.getDirName() + "images/"  + uuid + extname );
            List<Map> simages = new ArrayList<Map>();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                String picsize = entry.getValue();
                String picname = entry.getKey();
                String targetfilename = resizeImage.createThumbnailBy_jmagick(siteid,username,filedir,filePathname,picsize);
                int posi = targetfilename.lastIndexOf(File.separator);
                if (posi>-1) targetfilename = targetfilename.substring(posi+1);
                Map theimage = new HashMap();
                theimage.put("smallpic",picname + "-" + targetfilename);
                simages.add(theimage);
            }
            uploadimage.setSmallimages(simages);
            errorMessage.setErrmsg("文章录入模块附图上传成功");
            errorMessage.setErrcode(0);
            errorMessage.setModelname("文章录入模块附图上传");
        } else {
            errorMessage.setErrmsg("图片类型不是系统要求的图片类型");
            errorMessage.setErrcode(-1);
            errorMessage.setModelname("文章录入模块附图上传");
        }
    }

    result.put("data",uploadimage);
    result.put("error",errorMessage);
    Gson gson = new Gson();
    System.out.println(gson.toJson(result));
    out.print(gson.toJson(result));
    out.flush();
%>

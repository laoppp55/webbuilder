<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.publish.IPublishManager" %>
<%@ page import="com.bizwink.cms.publish.PublishPeer" %>
<%@ page import="com.bizwink.cms.multimedia.Attechment" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.error.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=utf-8" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteID = authToken.getSiteID();
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doUpload");
    IColumnManager columnMgr = ColumnPeer.getInstance();
    Attechment attechment = null;

    if (doCreate) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.setCharset("gbk");
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();

        //获取FORM表单的普通参数
        String cname = mySmartUpload.getRequest().getParameter("cname");
        String brief = mySmartUpload.getRequest().getParameter("brief");
        String s_column = mySmartUpload.getRequest().getParameter("column");
        String s_article = mySmartUpload.getRequest().getParameter("article");

        int columnID = 0;
        int articleID = 0;
        if (s_column != null) columnID = Integer.parseInt(s_column);
        if (s_article != null) articleID = Integer.parseInt(s_article);
        Column column = columnMgr.getColumn(columnID);
        String dirName = column.getDirName();

        //获取FORM表单的上传文件信息
        com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
        com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
        String filename = tempFile.getFileName();
        String ext_name = null;

        int dot_posi = filename.lastIndexOf(".");
        if (dot_posi>-1) {
            ext_name = filename.substring(dot_posi+1);
            filename = filename.substring(0,dot_posi);
        }

        filename = filename+ "_" + System.currentTimeMillis();
        if (ext_name != null) filename = filename + "." + ext_name;

        String uploadPath = application.getRealPath("/") + "sites" + java.io.File.separator + sitename +
                StringUtil.replace(dirName,"/",java.io.File.separator) + "download" + java.io.File.separator;

        java.io.File file = new java.io.File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        if (ext_name.equalsIgnoreCase("pdf") || ext_name.equalsIgnoreCase("doc") || ext_name.equalsIgnoreCase("docx") || ext_name.equalsIgnoreCase("xls")
                || ext_name.equalsIgnoreCase("xlsx") || ext_name.equalsIgnoreCase("ppt")|| ext_name.equalsIgnoreCase("pptx")|| ext_name.equalsIgnoreCase("txt")) {

            tempFile.saveAs(uploadPath + filename);
            //mySmartUpload.save(uploadPath);
            mySmartUpload.stop();

            //发布到WEB服务器
            String objDir = dirName + "download/";
            IPublishManager publishMgr = PublishPeer.getInstance();

            int retcode = 0;
            retcode = publishMgr.publish(username, uploadPath + filename, siteID, objDir, 0);

            if (retcode != 0) {
                System.out.println("向WEB服务器发布文件失败！");
            } else {
                attechment = new Attechment();
                SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                attechment.setId(0);
                attechment.setPagetype(0);
                attechment.setContenttype(0);
                attechment.setPageid(articleID);
                attechment.setCname(cname);
                attechment.setSummary(brief);
                attechment.setDirname(objDir + filename);
                attechment.setFilename(filename);
                attechment.setEditor(username);
                attechment.setContent("");
                attechment.setCreatedate(myFmt2.format(new Timestamp(System.currentTimeMillis())));
                attechment.setLastupdate(myFmt2.format(new Timestamp(System.currentTimeMillis())));
            }
        }

        Gson gson = new Gson();
        String jsondata=null;
        if (attechment != null){
            jsondata = gson.toJson(attechment);
            out.print(jsondata);
            out.flush();
        } else {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setErrcode(-1);
            errorMessage.setErrmsg("向WEB服务器发布文件失败！");
            errorMessage.setModelname("uplodAttechment页面模块");
            jsondata = gson.toJson(errorMessage);
            out.print(jsondata);
            out.flush();
        }
    }

    return;
%>
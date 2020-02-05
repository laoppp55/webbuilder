<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.service.ArticleService" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.service.ColumnService" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.po.Article" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.cms.publish.PublishPeer" %>
<%@ page import="com.bizwink.cms.publish.IPublishManager" %>
<%@ page import="com.bizwink.images.resizeImage" %>
<%@ page import="java.io.File" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-6-11
  Time: 上午11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String sitename = authToken.getSitename();
    int siteid = authToken.getSiteID();
    String editor = authToken.getUserID();
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int columnid = 0;
    Column column = null;
    Article article = null;
    ErrorMessage errorMessage = new ErrorMessage();

    //获取50836产品栏目的所有子栏目
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        ArticleService articleService = (ArticleService)appContext.getBean("articleService");
        ColumnService columnService = (ColumnService)appContext.getBean("columnService");
        if (doCreate) {
            SmartUpload mySmartUpload = new SmartUpload();
            mySmartUpload.setCharset("utf-8");
            mySmartUpload.initialize(this.getServletConfig(), request, response);
            mySmartUpload.upload();

            //获取FORM表单的普通参数
            String prodname = filter.excludeHTMLCode(mySmartUpload.getRequest().getParameter("prodname"));
            String brief = filter.excludeHTMLCode(mySmartUpload.getRequest().getParameter("brief"));
            String s_column = filter.excludeHTMLCode(mySmartUpload.getRequest().getParameter("prodclass"));
            if (s_column!=null && s_column!="") {
                columnid = Integer.parseInt(s_column);
                column = columnService.getColumn(BigDecimal.valueOf(columnid));
            } else {
                errorMessage.setErrcode(-1);
                errorMessage.setErrmsg("分类选择出现错误，或者没有选择信息分类");
            }

            //获取FORM表单的上传文件信息
            if(column!=null) {
                com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
                com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
                String filename = tempFile.getFileName();
                UUID uuid = UUID.randomUUID();
                String ext_name = null;

                int dot_posi = filename.lastIndexOf(".");
                if (dot_posi>-1) {
                    ext_name = filename.substring(dot_posi+1);
                    filename = filename.substring(0,dot_posi);
                }

                if (ext_name!=null) {
                    if (ext_name.equalsIgnoreCase("jpg") || ext_name.equalsIgnoreCase("jpeg") || ext_name.equalsIgnoreCase("png") || ext_name.equalsIgnoreCase("bmp") || ext_name.equalsIgnoreCase("gif")) {
                        String dirName = column.getDIRNAME();
                        String uploadPath = application.getRealPath("/") + "sites" + java.io.File.separator + sitename +
                                StringUtil.replace(dirName, "/", java.io.File.separator) + "images" + java.io.File.separator;

                        java.io.File file = new java.io.File(uploadPath);
                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        tempFile.saveAs(uploadPath + uuid + "." + ext_name);

                        //获取缩放后的小图片
                        resizeImage resizeImage = new resizeImage();
                        String small_pic = resizeImage.createSmallPictureBy_jmagick(uploadPath,uploadPath,uploadPath + uuid + "." + ext_name,"300x200");

                        //发布到WEB服务器
                        String objDir = dirName + "images/";
                        IPublishManager publishMgr = PublishPeer.getInstance();
                        //上传原始图片到WWW服务器
                        int retcode = publishMgr.publish(editor,uploadPath + uuid + "." + ext_name,siteid,objDir, 0);
                        //上传缩放后的小图片到WWW服务器
                        retcode = publishMgr.publish(editor,small_pic,siteid,objDir, 0);

                        if (retcode == 0) {
                            BigDecimal articleid = articleService.getMainKey();
                            article = new Article();
                            article.setID(articleid);
                            article.setMAINTITLE(prodname);
                            article.setSUMMARY(brief);
                            article.setDIRNAME(dirName);
                            article.setSOURCE("物资所属公司");
                            article.setSTATUS((short) 1);
                            article.setPUBFLAG((short) 1);
                            article.setMULTIMEDIATYPE((short) 0);
                            article.setAUDITFLAG((short) 0);
                            article.setEMPTYCONTENTFLAG((short) 0);
                            article.setDOCLEVEL(BigDecimal.valueOf(0));
                            article.setSITEID(BigDecimal.valueOf(siteid));
                            article.setCOLUMNID(BigDecimal.valueOf(columnid));
                            article.setSUBSCRIBER((short) 0);
                            article.setLOCKSTATUS((short) 0);
                            article.setISPUBLISHED((short) 1);
                            article.setINDEXFLAG((short) 0);
                            article.setDEPTID("长城");
                            article.setISJOINRSS((short) 0);
                            article.setCLICKNUM(BigDecimal.valueOf(0));
                            article.setREFERID(BigDecimal.valueOf(0));
                            article.setMODELID(BigDecimal.valueOf(0));
                            article.setURLTYPE((short) 0);
                            article.setWORDSNUM(BigDecimal.valueOf(0));
                            article.setEDITOR(editor);
                            article.setBIGPIC(uuid + "." + ext_name);
                            article.setPIC(small_pic.substring(small_pic.lastIndexOf(File.separator)+1));
                            article.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                            article.setLASTUPDATED(new Timestamp(System.currentTimeMillis()));
                            article.setPUBLISHTIME(new Timestamp(System.currentTimeMillis()));
                            article.setCREATOR(editor);
                            int errcode = articleService.saveArticleNoContent(article);
                            if (errcode == 0) {
                                errorMessage.setErrcode(-5);
                                errorMessage.setErrmsg("向数据库保存数据出现错误");
                            }
                        } else {
                            errorMessage.setErrcode(-4);
                            errorMessage.setErrmsg("图片上传WWW服务器出现错误");
                        }
                    } else {
                        errorMessage.setErrcode(-2);
                        errorMessage.setErrmsg("上传文件的扩展名无效，上传文件必须是jpeg、jpg、png类型文件");
                    }
                } else {
                    errorMessage.setErrcode(-3);
                    errorMessage.setErrmsg("上传文件的扩展名为空");
                }
            } else {
                errorMessage.setErrcode(-1);
                errorMessage.setErrmsg("分类选择出现错误，或者没有选择信息分类");
            }
            mySmartUpload.stop();
        }
    } else {
        errorMessage.setErrcode(-10);
        errorMessage.setErrmsg("环境初始化出现错误");
    }

    Gson gson = new Gson();
    String jsondata=null;
    if (article!=null) {
        jsondata = gson.toJson(article);
        out.print(jsondata);
        out.flush();
    } else {
        jsondata = gson.toJson(errorMessage);
        out.print(jsondata);
        out.flush();
    }
%>

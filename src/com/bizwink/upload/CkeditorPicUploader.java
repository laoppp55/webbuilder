package com.bizwink.upload;

import com.bizwink.cms.news.Column;
import com.bizwink.cms.news.ColumnException;
import com.bizwink.cms.news.ColumnPeer;
import com.bizwink.cms.news.IColumnManager;
import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishException;
import com.bizwink.cms.publish.PublishPeer;
import com.bizwink.cms.security.Auth;
import com.bizwink.cms.sitesetting.ISiteInfoManager;
import com.bizwink.cms.sitesetting.SiteInfo;
import com.bizwink.cms.sitesetting.SiteInfoException;
import com.bizwink.cms.sitesetting.SiteInfoPeer;
import com.bizwink.cms.util.ParamUtil;
import com.bizwink.images.resizeImage;
import com.bizwink.upload.vo.CkeditorUploadJson;
import com.bizwink.upload.vo.Error;
import com.google.gson.Gson;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import magick.MagickException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

public class CkeditorPicUploader  extends HttpServlet {
    /**
     * Servlet初始化方法
     */
    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String username = null;
        String sitename = null;
        int siteID = 0;
        String fileUrl = null;
        String filename = null;
        String picsize = null;
        Error error = new Error();
        error.setCode(0);
        error.setMessage("图片上传成功！");
        String appPath = request.getSession().getServletContext().getRealPath("/");
        int columnid = ParamUtil.getIntParameter(request,"column",0);
        IColumnManager columnManager = ColumnPeer.getInstance();
        IPublishManager publishMgr = PublishPeer.getInstance();
        ISiteInfoManager siteInfoManager = SiteInfoPeer.getInstance();

        Object obj = request.getSession().getAttribute("CmsAdmin");
        Auth authToken = null;
        if (obj instanceof Auth) authToken = (Auth)obj;
        if (authToken!=null) {
            username = authToken.getUserID();
            sitename = authToken.getSitename();
            siteID = authToken.getSiteID();
            if (username != null) {
                try {
                    SmartUpload mySmartUpload = new SmartUpload();
                    mySmartUpload.setCharset("utf-8");
                    mySmartUpload.initialize(this.getServletConfig(), request, response);
                    mySmartUpload.upload();
                    String brief = mySmartUpload.getRequest().getParameter("notes");

                    SiteInfo siteInfo = siteInfoManager.getSiteInfo(siteID);
                    Column column = columnManager.getColumn(columnid);
                    String dirName = column.getDirName();
                    picsize = columnManager.getPicsizeNotNullColumnForUp(columnid,"content");
                    if (picsize == null) picsize =  siteInfo.getContentpic();
                    String filedir = column.getDirName().replace("/", File.separator);

                    com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
                    com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
                    filename = tempFile.getFileName();
                    System.out.print(filename);

                    if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg") || filename.toLowerCase().endsWith(".gif") || filename.toLowerCase().endsWith(".png")) {
                        String uploadPath = null;
                        uploadPath = appPath + "sites" + File.separator + sitename.replace(".", "_") + filedir + "images" + java.io.File.separator;
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

                        //按照系统设置的尺寸对图片进行缩放处理
                        resizeImage resizeImage = new resizeImage();
                        String targetfilename = resizeImage.createThumbnailBy_jmagick(siteID,username,filedir,filePathname,picsize);
                        int errcode = publishMgr.publish(username, targetfilename, siteID, dirName + "images" + File.separator, 0);
                        filename = targetfilename.substring(targetfilename.lastIndexOf(File.separator)+1);
                        fileUrl = "/webbuilder/sites/" + sitename + dirName + "images/" + filename;
                    } else if (filename.toLowerCase().endsWith(".swf")) {
                        String uploadPath = null;
                        uploadPath = appPath + "sites" + File.separator + sitename.replace(".", "_") + filedir + "images" + java.io.File.separator;
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

                        int errcode = publishMgr.publish(username, filePathname, siteID, dirName + "images" + File.separator, 0);
                        fileUrl = "/webbuilder/sites/" + sitename + dirName + "images/" + uuid + extname;
                        filename = uuid + extname;
                    } else if (filename.toLowerCase().endsWith(".doc") || filename.toLowerCase().endsWith(".docx") || filename.toLowerCase().endsWith(".ppt") || filename.toLowerCase().endsWith(".pptx") ||
                            filename.toLowerCase().endsWith(".xls") || filename.toLowerCase().endsWith(".xlsx") || filename.toLowerCase().endsWith(".pdf") || filename.toLowerCase().endsWith(".rar")  || filename.toLowerCase().endsWith(".zip")) {
                        String uploadPath = null;
                        uploadPath = appPath + "sites" + File.separator + sitename.replace(".", "_") + filedir + "download" + java.io.File.separator;
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

                        int errcode = publishMgr.publish(username, filePathname, siteID, dirName + "download" + File.separator, 0);
                        fileUrl = "/webbuilder/sites/" + sitename + dirName + "download/" + uuid + extname;
                        filename = uuid + extname;
                    } else {
                        error.setCode(-3);
                        error.setMessage("文件类型错误，不能上传该类型文件");
                    }
                } catch (SmartUploadException exp) {
                    error.setCode(-4);
                    error.setMessage("图片或者FLASH文件上传失败");
                    exp.printStackTrace();
                } catch (ColumnException colexp) {
                    error.setCode(-5);
                    error.setMessage("获取栏目路径信息失败");
                    colexp.printStackTrace();
                } catch (PublishException pubexp) {
                    error.setCode(-6);
                    error.setMessage("发布上传文件到web服务器失败");
                    pubexp.printStackTrace();
                } catch (SiteInfoException siteexp) {
                    error.setCode(-7);
                    error.setMessage("获取站点信息失败");
                    siteexp.printStackTrace();
                } catch (MagickException imgexp) {
                    error.setCode(-8);
                    error.setMessage("缩放图片失败");
                    imgexp.printStackTrace();
                }
            } else{
                error.setCode(-2);
                error.setMessage("登录用户名为空，不能上传图片");
            }
        } else {
            error.setCode(-1);
            error.setMessage("用户没有登录，不能上传图片");
        }

        CkeditorUploadJson ckeditorUploadJson = new CkeditorUploadJson();
        ckeditorUploadJson.setUploaded(1);
        ckeditorUploadJson.setFileName(filename);
        ckeditorUploadJson.setUrl(fileUrl);
        ckeditorUploadJson.setError(error);
        Gson gson = new Gson();
        String jsondata = gson.toJson(ckeditorUploadJson);
        out.println(jsondata);
        out.flush();
        out.close();
    }
}

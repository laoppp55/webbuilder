package com.bizwink.upload;

import java.net.URLEncoder;
import java.util.*;
import java.util.List;
import java.io.*;
import java.sql.*;
import java.sql.Date;
import javax.servlet.http.*;

import com.bizwink.cms.publish.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.extendAttr.*;
import com.bizwink.cms.audit.*;
import com.bizwink.cms.pic.PicPeer;
import com.bizwink.cms.pic.IPicManager;
import com.bizwink.cms.pic.Pic;
import com.bizwink.images.*;
import com.bizwink.cms.sitesetting.ISiteInfoManager;
import com.bizwink.cms.sitesetting.SiteInfoPeer;
import com.bizwink.cms.sitesetting.SiteInfo;
import com.bizwink.cms.sitesetting.SiteInfoException;
import magick.MagickException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class MultipartFormHandle {
    public static final String FORWARDNAME = "forward";
    public static final String MAXWIDTHPARAMNAME = "maxwidth";
    public static final String MAXHEIGHTPARAMNAME = "maxheight";
    public static final String UPDIR = "uploaddir";
    public static final int SIZE = 500;

    private Vector hashimg = null;
    private Hashtable hashimg2 = null;
    private Hashtable hashpara = null;

    private String uploaddir;
    private String sitename;
    private int siteid;
    private String baseDir;

    private uploaderrormsg errormsg = new uploaderrormsg();
    private Upload upload = new Upload();

    public  uploaderrormsg getErrorMsg() {
        return errormsg;
    }

    public String getUploaddir() {
        return uploaddir;
    }

    public void setUploaddir(String uploaddir) {
        this.uploaddir = uploaddir;
    }

    public void init(HttpServletRequest request,String baseDir,String sitename,int siteid) throws Exception {
        this.sitename = sitename;
        this.siteid = siteid;
        this.baseDir = baseDir;
        this.uploaddir = baseDir + "sites" + File.separator + sitename;
        this.upload.setUploaddir(uploaddir);

        this.errormsg.setErrorCode(0);
        this.errormsg.setErrorMsg(null);
        this.errormsg.setParas(null);
        this.errormsg.ClearErrorPics();

        try {
            upload.startUpload(request);
            hashimg = upload.dealAllUploadImg();
            hashimg2 = upload.dealAllUploadImg2();
            hashpara = upload.dealAllPara();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            upload.clear();
        }
    }

    public String getForwardProgram() {
        String forword = "";
        if (hashpara.get(FORWARDNAME) != null) {
            forword = (String) hashpara.get(FORWARDNAME);
        }
        return forword;
    }

    public String getForwardProgramParam() throws Exception {
        String picname = "";
        String username = "";
        int hostID = 0;
        String fileDir = "";
        String fromflag = "";
        int imgflag = 0;
        int cssjsdir = 0;
        StringBuffer buff = new StringBuffer();
        int errcode = 0;
        String pubsite = "";
        int pubsiteid = 0;
        int isdefineattr = 0;

        String maintitle = "";
        String vicetitle = "";
        String summary = "";
        String keyword = "";
        String source = "";
        String status = "";
        int doclevel = 0;
        int using = 0;
        int subscriber = 0;
        int sortid = 0;
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int columnID = 0;
        int upimagescid = 0;                                //图片上传的栏目ID
        int articleID = 0;
        int tcflag = 0;
        int smallpicflag = 0;
        int addWaterMark = 0;
        String notes = null;
        String title_pic = null;
        String vtitle_pic = null;
        String content_pic = null;
        String special_pic = null;
        String author_pic = null;
        String source_pic = null;
        String prod_large_pic = null;
        String prod_small_pic = null;
        String attrname = null;
        int range=0;
        int currentPage=0;
        boolean doSearchFlag;
        int flag=0;
        int ascdesc=0;
        List attrList = null;

        for (Enumeration e = hashpara.keys(); e.hasMoreElements();) {
            String name = (String) e.nextElement();
            String value = (String) hashpara.get(name);
            if (name.equals("picname")) {
                picname = value;
            }
            if (name.equals("username")) {
                username = value;
            }
            if (name.equals("fileDir")) {
                fileDir = value;
            }
            if (name.equals("fromflag")) {
                fromflag = value;
            }
            if (name.equals("attr")) {
                attrname = value;
            }
            if (name.equals("hostID")) {
                hostID = Integer.parseInt(value);
            }
            if (name.equals("imgflag")) {
                imgflag = Integer.parseInt(value);
            }
            if (name.equals("cssjsdir")) {
                cssjsdir = Integer.parseInt(value);
            }
            if (name.equals("maintitle")) {
                maintitle = value;
            }
            if (name.equals("vicetitle")) {
                vicetitle = value;
            }
            if (name.equals("summary")) {
                summary = value;
            }
            if (name.equals("keyword")) {
                keyword = value;
            }
            if (name.equals("source")) {
                source = value;
            }
            if (name.equals("title_pic")) {
                title_pic = value;
            }
            if (name.equals("vtitle_pic")) {
                vtitle_pic = value;
            }
            if (name.equals("author_pic")) {
                author_pic = value;
            }
            if (name.equals("source_pic")) {
                source_pic = value;
            }
            if (name.equals("content_pic")) {
                content_pic = value;
            }
            if (name.equals("special_pic")) {
                special_pic = value;
            }
            if (name.equals("prod_large_pic")) {
                prod_large_pic = value;
            }
            if (name.equals("prod_small_pic")) {
                prod_small_pic = value;
            }
            if (name.equals("doclevel")) {
                doclevel = Integer.parseInt(value);
            }
            if (name.equals("using")) {
                using = Integer.parseInt(value);
            }
            if (name.equals("spic")) {
                smallpicflag = Integer.parseInt(value);
            }
            if (name.equals("subscriber")) {
                subscriber = Integer.parseInt(value);
            }
            if (name.equals("sortid")) {
                sortid = Integer.parseInt(value);
            }
            if (name.equals("year")) {
                year = Integer.parseInt(value);
            }
            if (name.equals("month")) {
                month = Integer.parseInt(value);
            }
            if (name.equals("day")) {
                day = Integer.parseInt(value);
            }
            if (name.equals("hour")) {
                hour = Integer.parseInt(value);
            }
            if (name.equals("minute")) {
                minute = Integer.parseInt(value);
            }
            if (name.equals("column")) {
                columnID = Integer.parseInt(value);
            }
            if (name.equals("uploadimagescolumnid")) {
                upimagescid = Integer.parseInt(value);
            }
            if (name.equals("article")) {
                articleID = Integer.parseInt(value);
            }
            if (name.equals("status")) {
                status = value;
            }
            if (name.equals("tcflag")) {
                tcflag = Integer.parseInt(value);
            }
            if (name.equals("notes")) {
                notes = value;
            }
            //range, currentPage, doSearchFlag, flag, ascdesc
            if (name.equals("range")) {
                range = Integer.parseInt(value);
            }
            if (name.equals("start")) {
                currentPage = Integer.parseInt(value);
            }
            if (name.equals("doSearchFlag")) {
                doSearchFlag = Boolean.parseBoolean(value);
            }
            if (name.equals("flag")) {
                flag = Integer.parseInt(value);
            }
            if (name.equals("ascdesc")) {
                ascdesc = Integer.parseInt(value);
            }
            if (name.equals("addWaterMark")) {
                String addWaterMarks = value;
                if (ColorUtil.isNumber(addWaterMarks))
                    addWaterMark = Integer.parseInt(addWaterMarks);
            }
            if (name.equals("pubsite")) {
                pubsite = value.substring(0, value.lastIndexOf("_"));
                pubsiteid = Integer.parseInt(value.substring(value.lastIndexOf("_") + 1));
            }

            if (name.equalsIgnoreCase("isDefine")) {
                isdefineattr = Integer.parseInt(value);
            }

            if (!name.equals(FORWARDNAME) && !name.equals("dir") && !name.equals("baseDir") && !name.equals("imgflag") &&
                    !name.equals("sitename") && !name.equals("uploadaurl") && !name.equals("username") && !name.equals("notes") &&
                    !name.equals("submit") && !name.equals("title_pic") && !name.equals("vtitle_pic") &&
                    !name.equals("special_pic") && !name.equals("prod_large_pic") && !name.equals("prod_small_pic")&&!name.equals("content_pic") &&
                    !name.equals("author_pic") && !name.equals("source_pic")) {
                    buff.append(name).append("=").append(value).append("&");
            }
        }

        //获取文章的扩展属性值
        if (isdefineattr == 1) {
            attrList = new ArrayList();
            IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
            String xmlTemplate = extendMgr.getXMLTemplate(columnID);
            if (xmlTemplate != null && xmlTemplate.trim().length() > 0) {
                SAXBuilder builder = new SAXBuilder();
                Reader in = new StringReader(xmlTemplate);
                Document doc = builder.build(in);
                List nodeList = doc.getRootElement().getChildren();
                for (Enumeration e = hashpara.keys(); e.hasMoreElements();) {
                    String name = (String) e.nextElement();
                    String value = (String) hashpara.get(name);
                    for (int i = 0; i < nodeList.size(); i++) {
                        Element ext_element = (Element) nodeList.get(i);
                        String ext_ename = ext_element.getName();
                        String ext_value = null;
                        if (name.equals(ext_ename)) {
                            ext_value = value;
                        }

                        if (ext_value != null) ext_value = ext_value.replaceAll("&apos;", "\'");

                        if (ext_value != null && ext_value.trim().length() > 0) {
                            ExtendAttr extend = new ExtendAttr();
                            int dataType = Integer.parseInt(ext_element.getAttributeValue("datatype"));
                            int controltype = Integer.parseInt(ext_element.getAttributeValue("type"));

                            extend.setEName(ext_ename);
                            extend.setDataType(dataType);
                            extend.setControlType(controltype);
                            if (dataType == 1)
                                extend.setStringValue(ext_value);
                            else if (dataType == 2)
                                extend.setNumericValue(Integer.parseInt(ext_value));
                            else if (dataType == 3)
                                extend.setTextValue(ext_value);
                            else if (dataType == 4)
                                extend.setFloatValue(Float.parseFloat(ext_value));
                            attrList.add(extend);
                            break;
                        }
                    }
                }
            }
        }

        ISiteInfoManager siteInfoMgr = SiteInfoPeer.getInstance();
        SiteInfo siteinfo = siteInfoMgr.getSiteInfo(siteid);
        tcflag = siteinfo.getTcFlag();
        String mvdir = "";
        String filename = "";
        //为文章录入页面的文章属性字段上传图片
        //或者是为文章体的内容、模板体的内容上传图片
        if (fromflag.equals("picture") || fromflag.equals("article")) {
            resizeImage resize_image = new resizeImage();
            if (hashimg.size() == 1) {       //简体和繁体图片相同
                filename = (String) hashimg.get(0);
                int posi = -1;
                posi = filename.lastIndexOf(".");
                String ext = filename.substring(posi + 1);
                List fileList = new ArrayList();

                if ("zip".equalsIgnoreCase(ext)) {
                    String zipFile  = this.uploaddir + File.separator + filename;
                    UnZip uzip = new UnZip();
                    uzip.hostID = hostID;
                    uzip.UnZip(zipFile, this.uploaddir, sitename, siteid, 0);
                    fileList = uzip.getFileNameList();
                    mvdir = this.uploaddir + fileDir + "images" + File.separator;
                    buff.append("picname1=");
                    int tmpLen = this.uploaddir.length()+(File.separator).length();
                    for (int i = 0; i < fileList.size(); i++) {
                        filename = fileList.get(i).toString();
                        if (filename.lastIndexOf(",") > 0) filename = filename.substring(tmpLen, filename.lastIndexOf(","));
                        picname = filename.substring(0,filename.lastIndexOf("."));
                        //生成图片名称的随机数部分
                        RandomStrg rstr = new RandomStrg();
                        rstr.setCharset("a-z0-9");
                        rstr.setLength(8);
                        rstr.generateRandomObject();
                        picname = picname + rstr.getRandom();
                        buff.append(picname + filename.substring(filename.lastIndexOf("."))).append(",");
                        errcode = mvPicToUserUploaddir(smallpicflag, picname, filename, mvdir, username, siteid, hostID, fileDir, 0, addWaterMark,notes);
                        FileDeal.deleteFile(this.uploaddir + File.separator + filename);
                    }
                    FileDeal.deleteFile(this.uploaddir + File.separator + (String) hashimg.get(0));
                } else {
                    buff.append("picname1=").append(picname + filename.substring(filename.lastIndexOf(".")));
                    mvdir = this.uploaddir + fileDir + "images" + File.separator;
                    errcode = mvPicToUserUploaddir(smallpicflag,picname, filename, mvdir, username, siteid, hostID, fileDir, 0, addWaterMark,notes);

                    if (tcflag == 1) {
                        errcode = mvPicToUserUploaddir(smallpicflag,picname, filename, mvdir, username, siteid, hostID, File.separator + "big5" + fileDir, 1, addWaterMark,notes);
                    }

                    FileDeal.deleteFile(this.uploaddir + File.separator + (String) hashimg.get(0));

                    //如果标题图片大小、副标题图片大小、文章特效图片大小、商品大图大小、商品小图大小不空
                    //则生成相应的小图片
                    if (errcode == 0 && resize_image.isJpg(filename)) {
                        filename = picname + filename.substring(filename.lastIndexOf("."));
                        if (content_pic != null) {
                            content_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,content_pic);
                            buff.append("&content_pic=").append(content_pic.substring(content_pic.lastIndexOf(File.separator) + 1));
                        }
                        if (title_pic != null) {
                            title_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,title_pic);
                            buff.append("&title_pic=").append(title_pic.substring(title_pic.lastIndexOf(File.separator)+1));
                        }
                        if (vtitle_pic != null) {
                            vtitle_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,vtitle_pic);
                            buff.append("&vtitle_pic=").append(vtitle_pic.substring(vtitle_pic.lastIndexOf(File.separator) + 1));
                        }
                        if (author_pic != null)  {
                            author_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,author_pic);
                            buff.append("&author_pic=").append(author_pic.substring(author_pic.lastIndexOf(File.separator) + 1));
                        }
                        if (source_pic != null)  {
                            source_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,source_pic);
                            buff.append("&souecr_pic=").append(source_pic.substring(source_pic.lastIndexOf(File.separator) + 1));
                        }
                        if (special_pic != null)  {
                            special_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,special_pic);
                            buff.append("&special_pic=").append(special_pic.substring(special_pic.lastIndexOf(File.separator) + 1));
                        }
                        if (prod_large_pic != null)  {
                            prod_large_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,prod_large_pic);
                            buff.append("&prod_large_pic=").append(prod_large_pic.substring(prod_large_pic.lastIndexOf(File.separator) + 1));
                        }
                        if (prod_small_pic != null){
                            prod_small_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,prod_small_pic);
                            buff.append("&prod_small_pic=").append(prod_small_pic.substring(prod_small_pic.lastIndexOf(File.separator) + 1));
                        }
                    }
                }
            } else {                         //简体和繁体图片不相同
                filename = (String) hashimg.get(0);
                buff.append("picname1=").append(picname + filename.substring(filename.lastIndexOf(".")));
                mvdir = this.uploaddir + fileDir + "images" + File.separator;
                errcode = mvPicToUserUploaddir(smallpicflag,picname, filename, mvdir, username, siteid, hostID, fileDir, 0, addWaterMark,notes);

                //如果标题图片大小、副标题图片大小、文章特效图片大小、商品大图大小、商品小图大小不空
                //则生成相应的小图片
                if (errcode == 0  && resize_image.isJpg(filename)) {
                    filename = picname + filename.substring(filename.lastIndexOf("."));
                    if (content_pic != null) {
                        content_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,content_pic);
                        buff.append("&content_pic=").append(content_pic.substring(content_pic.lastIndexOf(File.separator) + 1));
                    }
                    if (title_pic!=null) {
                        title_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,title_pic);
                        buff.append("&title_pic=").append(title_pic.substring(title_pic.lastIndexOf(File.separator)+1));
                    }
                    if (vtitle_pic!=null) {
                        vtitle_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,vtitle_pic);
                        buff.append("&vtitle_pic=").append(vtitle_pic.substring(vtitle_pic.lastIndexOf(File.separator) + 1));
                    }
                    if (author_pic != null)  {
                        author_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,author_pic);
                        buff.append("&author_pic=").append(author_pic.substring(author_pic.lastIndexOf(File.separator) + 1));
                    }
                    if (source_pic != null)  {
                        source_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,source_pic);
                        buff.append("&souecr_pic=").append(source_pic.substring(source_pic.lastIndexOf(File.separator) + 1));
                    }
                    if (special_pic!=null) {
                        special_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,special_pic);
                        buff.append("&special_pic=").append(special_pic.substring(special_pic.lastIndexOf(File.separator) + 1));
                    }
                    if (prod_large_pic != null) {
                        prod_large_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,prod_large_pic);
                        buff.append("&prod_large_pic=").append(prod_large_pic.substring(prod_large_pic.lastIndexOf(File.separator) + 1));
                    }
                    if (prod_small_pic != null) {
                        prod_small_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,prod_small_pic);
                        buff.append("&prod_small_pic=").append(prod_small_pic.substring(prod_small_pic.lastIndexOf(File.separator) + 1));
                    }
                    FileDeal.deleteFile(this.uploaddir + File.separator + (String) hashimg.get(0));
                }

                if (tcflag == 1) {
                    filename = (String) hashimg.get(1);
                    mvdir = this.uploaddir + File.separator + "big5" + fileDir + "images" + File.separator;
                    errcode = mvPicToUserUploaddir(smallpicflag,picname, filename, mvdir, username, siteid, hostID, File.separator + "big5" + fileDir, 1, addWaterMark,notes);
                    //如果标题图片大小、副标题图片大小、文章特效图片大小、商品大图大小、商品小图大小不空
                    //则生成相应的繁体小图片
                    if (errcode == 0  && resize_image.isJpg(filename)) {
                        filename = picname + filename.substring(filename.lastIndexOf("."));
                        if (content_pic != null) {
                            if (content_pic!=null) {
                                content_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,content_pic);
                                buff.append("&content_pic=").append(content_pic.substring(content_pic.lastIndexOf(File.separator) + 1));
                            }
                        }
                        if (title_pic!=null) {
                            title_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,title_pic);
                            buff.append("&title_pic=").append(title_pic.substring(title_pic.lastIndexOf(File.separator)+1));
                        }
                        if (vtitle_pic!=null) {
                            vtitle_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,vtitle_pic);
                            buff.append("&vtitle_pic=").append(vtitle_pic.substring(vtitle_pic.lastIndexOf(File.separator) + 1));
                        }
                        if (author_pic != null)  {
                            author_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,author_pic);
                            buff.append("&author_pic=").append(author_pic.substring(author_pic.lastIndexOf(File.separator) + 1));
                        }
                        if (source_pic != null)  {
                            source_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,source_pic);
                            buff.append("&souecr_pic=").append(source_pic.substring(source_pic.lastIndexOf(File.separator) + 1));
                        }
                        if (special_pic!=null) {
                            special_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,special_pic);
                            buff.append("&special_pic=").append(special_pic.substring(special_pic.lastIndexOf(File.separator) + 1));
                        }
                        if(prod_large_pic != null) {
                            prod_large_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,prod_large_pic);
                            buff.append("&prod_large_pic=").append(prod_large_pic.substring(prod_large_pic.lastIndexOf(File.separator) + 1));
                        }
                        if (prod_small_pic != null) {
                            prod_small_pic = resize_image.createThumbnailBy_jmagick(siteid,username,fileDir,mvdir + filename,prod_small_pic);
                            buff.append("&prod_small_pic=").append(prod_small_pic.substring(prod_small_pic.lastIndexOf(File.separator) + 1));
                        }
                        FileDeal.deleteFile(this.uploaddir + File.separator + (String) hashimg.get(1));
                    }
                }
            }
            errormsg.setErrorCode(errcode);
            switch (errcode) {
                case -1:
                    errormsg.setErrorMsg("不是可识别的文件名");
                case -2:
                    errormsg.setErrorMsg("向用户目录拷贝文件时出错");
                case -21:
                    errormsg.setErrorMsg("不是可识别的FTP传输类型");
                case -22:
                    errormsg.setErrorMsg("不是可识别的FTP传输模式");
                case -23:
                    errormsg.setErrorMsg("FTP I/O读写出现错误");
                case -24:
                    errormsg.setErrorMsg("不能向发布主机建立FTP连接");
                case -25:
                    errormsg.setErrorMsg("FTP连接关闭时出现错误");
                case -26:
                    errormsg.setErrorMsg("建立远程主机的FTP目录出现错误");
                case -31:
                    errormsg.setErrorMsg("不能获取发布站点的定义信息");
                case -40:
                    errormsg.setErrorMsg("FTP I/O读写出现错误");
                default:
                    errormsg.setErrorMsg("不可识别的错误");
            }
        } else if (fromflag.equals("model")) {
            //图片，css，js分别存储
            if (cssjsdir == 1) {
                String ssfilename = null;             //简体图象文件或简体图象包
                String ttfilename = null;             //繁体图象文件或繁体图象包
                String htmlfilename = null;           //繁体图象文件或繁体图象包
                String cssfilename = null;            //繁体图象文件或繁体图象包
                String scriptfilename = null;         //繁体图象文件或繁体图象包

                for (Enumeration e = hashimg2.keys(); e.hasMoreElements();) {
                    String t_name = (String) e.nextElement();
                    String t_value = (String) hashimg2.get(t_name);

                    if (t_name.equalsIgnoreCase("sfilename"))
                        ssfilename = t_value;
                    if (t_name.equalsIgnoreCase("tfilename"))
                        ttfilename = t_value;
                    if (t_name.equalsIgnoreCase("htmlfilename"))
                        htmlfilename = t_value;
                    if (t_name.equalsIgnoreCase("cssfilename"))
                        cssfilename = t_value;
                    if (t_name.equalsIgnoreCase("scriptfilename"))
                        scriptfilename = t_value;
                }


                //上传简体图象或图象包
                String extname = "";
                if (ssfilename != null && ssfilename != "") {
                    if (imgflag == 0) fileDir = File.separator;
                    mvdir = this.uploaddir;
                    if (tcflag == 1) {            //包括繁体网站和简体网站
                        if (siteid == pubsiteid) {
                            errcode = mvModelToUserUploaddir2(ssfilename, mvdir, username, siteid, sitename, columnID, fileDir, imgflag, 0);
                        } else {
                            errcode = mvModelToUserUploaddir2(ssfilename, mvdir, username, pubsiteid, pubsite, columnID, fileDir, imgflag, 0);
                        }
                    } else {                    //只包括简体网站
                        if (siteid == pubsiteid) {
                            errcode = mvModelToUserUploaddir2(ssfilename, mvdir, username, siteid, sitename, columnID, fileDir, imgflag, 1);
                        } else {
                            errcode = mvModelToUserUploaddir2(ssfilename, mvdir, username, pubsiteid, pubsite, columnID, fileDir, imgflag, 1);
                        }
                    }
                    buff.append("picname=").append(ssfilename);
                }

                //上传繁体图象或图象包
                if (tcflag == 1) {
                    if (imgflag == 0) fileDir = File.separator;
                    if (ttfilename != null && ttfilename != "") {   //存在单独繁体图象文件
                        extname = ttfilename.substring(ttfilename.lastIndexOf(".") + 1);
                        mvdir = this.uploaddir;
                        if (siteid == pubsiteid) {
                            errcode = mvModelToUserUploaddir2(ttfilename, mvdir, username, siteid, sitename, columnID, File.separator + "big5" + fileDir, imgflag, 1);
                        } else {
                            errcode = mvModelToUserUploaddir2(ttfilename, mvdir, username, pubsiteid, pubsite, columnID, File.separator + "big5" + fileDir, imgflag, 1);
                        }
                    } else {                                       //没有单独的繁体图象文件
                        if (ssfilename != null && ssfilename != "") {
                            extname = ssfilename.substring(ssfilename.lastIndexOf(".") + 1);
                            mvdir = this.uploaddir;
                            if (siteid == pubsiteid) {
                                errcode = mvModelToUserUploaddir2(ssfilename, mvdir, username, siteid, sitename, columnID, File.separator + "big5" + fileDir, imgflag, 1);
                            } else {
                                errcode = mvModelToUserUploaddir2(ssfilename, mvdir, username, pubsiteid, pubsite, columnID, File.separator + "big5" + fileDir, imgflag, 1);
                            }
                        }
                    }
                }

                //上传HTML文件
                if (htmlfilename != null && htmlfilename != "") {
                    extname = htmlfilename.substring(htmlfilename.lastIndexOf(".") + 1);
                    mvdir = this.uploaddir;
                    //是符合规定的模板文件类型并且模板文件中的各种相关文件没有重名，格式化该模板。
                    if (("html".equalsIgnoreCase(extname)) || ("htm".equalsIgnoreCase(extname)) || ("shtml".equalsIgnoreCase(extname)) || ("shtm".equalsIgnoreCase(extname))) {
                        File uploadedfile = new File(getUploaddir(), htmlfilename);
                        String newfilename = mvdir + File.separator + "_templates" + fileDir + new String (htmlfilename.getBytes("iso8859_1"), "GBK");
                        FileDeal.copy(uploadedfile.toString(), newfilename, 0);
                        errormsg.setTemplatename(newfilename);
                    }  else {
                        errormsg.setErrorMsg("文件类型不满足要求，必须是HTML、HTM、SHTML或者STM");
                    }
                }

                //上传CSS文件
                if (cssfilename != null && cssfilename != "") {
                    mvdir = this.uploaddir;
                    if (siteid == pubsiteid) {
                        errcode = mvModelToUserUploaddir2(cssfilename, mvdir, username, siteid, sitename, columnID, fileDir, imgflag, 0);
                    } else {
                        errcode = mvModelToUserUploaddir2(cssfilename, mvdir, username, pubsiteid, pubsite, columnID, fileDir, imgflag, 0);
                    }
                    if (tcflag == 1) {
                        mvdir = this.uploaddir + File.separator + "big5";
                        if (siteid == pubsiteid) {
                            errcode = mvModelToUserUploaddir2(cssfilename, mvdir, username, siteid, sitename, columnID, fileDir, imgflag, 1);
                        } else {
                            errcode = mvModelToUserUploaddir2(cssfilename, mvdir, username, pubsiteid, pubsite, columnID, fileDir, imgflag, 1);
                        }
                    }
                    buff.append("picname=").append(cssfilename);
                }

                //上传脚本文件
                if (scriptfilename != null && scriptfilename != "") {
                    mvdir = this.uploaddir;
                    if (siteid == pubsiteid) {
                        errcode = mvModelToUserUploaddir2(scriptfilename, mvdir, username, siteid, sitename, columnID, fileDir, imgflag, 0);
                    } else {
                        errcode = mvModelToUserUploaddir2(scriptfilename, mvdir, username, pubsiteid, pubsite, columnID, fileDir, imgflag, 0);
                    }

                    if (tcflag == 1) {
                        mvdir = this.uploaddir + File.separator + "big5";
                        if (siteid == pubsiteid) {
                            errcode = mvModelToUserUploaddir2(scriptfilename, mvdir, username, siteid, sitename, columnID, fileDir, imgflag, 1);
                        } else {
                            errcode = mvModelToUserUploaddir2(scriptfilename, mvdir, username, pubsiteid, pubsite, columnID, fileDir, imgflag, 1);
                        }
                    }
                    buff.append("picname=").append(scriptfilename);
                }
            } else {//css，js，图片都存放在images文件夹下
                if (imgflag == 0) fileDir = File.separator;
                if (hashimg.size() == 1)            //简体模板同繁体模板文件
                {
                    filename = (String) hashimg.get(0);
                    mvdir = this.uploaddir;
                    errcode = mvModelToUserUploaddir(filename, mvdir, username, siteid, sitename, hostID, fileDir, imgflag, 0);

                    if (tcflag == 1) {
                        mvdir = this.uploaddir + File.separator + "big5";
                        errcode = mvModelToUserUploaddir(filename, mvdir, username, siteid, sitename, hostID, fileDir, imgflag, 1);
                    }
                } else {                             //简体模板与繁体模板文件不相同
                    filename = (String) hashimg.get(0);
                    mvdir = this.uploaddir;
                    errcode = mvModelToUserUploaddir(filename, mvdir, username, siteid, sitename, hostID, fileDir, imgflag, 0);

                    if (tcflag == 1) {
                        filename = (String) hashimg.get(1);
                        mvdir = this.uploaddir + File.separator + "big5";
                        errcode = mvModelToUserUploaddir(filename, mvdir, username, siteid, sitename, hostID, fileDir, imgflag, 1);
                    }
                }
            }
        } else if (fromflag.equals("file") && status.equals("save")) {
            String ssfilename = null;             //简体文件
            String downsfilename = null;          //简体文件相应的下载文件
            String ttfilename = null;             //繁体文件
            String downtfilename = null;          //繁体文件相应的下载文件

            for (Enumeration e = hashimg2.keys(); e.hasMoreElements();) {
                String t_name = (String) e.nextElement();
                String t_value = (String) hashimg2.get(t_name);

                if (t_name.equalsIgnoreCase("sfilename"))
                    ssfilename = t_value;
                if (t_name.equalsIgnoreCase("tfilename"))
                    ttfilename = t_value;
                if (t_name.equalsIgnoreCase("downsfilename"))
                    downsfilename = t_value;
                if (t_name.equalsIgnoreCase("downtfilename"))
                    downtfilename = t_value;
            }
            errcode = saveUploadFile(ssfilename, ttfilename, downsfilename, downtfilename, attrList,tcflag, mvdir, username, siteid,
                    fileDir, maintitle, vicetitle, summary, keyword, source, doclevel, using, subscriber, sortid, year,
                    month, day, hour, minute, columnID, 0);
        } else if (fromflag.equals("file") && status.equals("update")) {
            String ssfilename = null;             //简体文件
            String downsfilename = null;          //简体文件相应的下载文件
            String ttfilename = null;             //繁体文件
            String downtfilename = null;          //繁体文件相应的下载文件

            for (Enumeration e = hashimg2.keys(); e.hasMoreElements();) {
                String t_name = (String) e.nextElement();
                String t_value = (String) hashimg2.get(t_name);

                if (t_name.equalsIgnoreCase("sfilename"))
                    ssfilename = t_value;
                if (t_name.equalsIgnoreCase("tfilename"))
                    ttfilename = t_value;
                if (t_name.equalsIgnoreCase("downsfilename"))
                    downsfilename = t_value;
                if (t_name.equalsIgnoreCase("downtfilename"))
                    downtfilename = t_value;
            }

            if (ssfilename == null)
                errcode = updateUploadFile(false, ssfilename, ttfilename, downsfilename, downtfilename,attrList, tcflag, mvdir, username, siteid, fileDir, articleID, maintitle, vicetitle, summary, keyword, source, doclevel, using, subscriber, sortid, year, month, day, hour, minute, columnID, 0);
            else
                errcode = updateUploadFile(true, ssfilename, ttfilename, downsfilename, downtfilename,attrList, tcflag, mvdir, username, siteid, fileDir, articleID, maintitle, vicetitle, summary, keyword, source, doclevel, using, subscriber, sortid, year, month, day, hour, minute, columnID, 0);
        }

        buff.append("&articleid").append("=").append(errcode);
        return buff.toString();
    }

    public String savePicture(String filename, String newlocationDir, int siteid, int columnID, String fileDir, int imgflag) {
        String newfilename = newlocationDir + fileDir + filename;

        IPicManager pictureMgr = PicPeer.getInstance();
        String imgurl = null;
        int height = 0;
        int width = 0;

        UploadImg uploadimg = new UploadImg();
        try {
            boolean errcode = uploadimg.getImagWidthHeight(newfilename);
            if (errcode == false) {
                height = uploadimg.height;
                width = uploadimg.width;
            }
        } catch (Exception ex) {
        }

        ISiteInfoManager siteInfoMgr = SiteInfoPeer.getInstance();
        SiteInfo siteInfo;
        String sitename = "";
        try {
            siteInfo = siteInfoMgr.getSiteInfo(siteid);
            sitename = siteInfo.getDomainName();
            sitename = StringUtil.replace(sitename, ".", "_");
        } catch (SiteInfoException e) {
            e.printStackTrace();
        }

        Pic picture = new Pic();
        File f1 = new File(newfilename);
        int imagesize = (int) f1.length();
        imgurl = "/webbuilder/sites/" + sitename + "/images/" + filename;
        try {
            boolean exist = pictureMgr.existThePicture(filename, siteid);
            if (filename.toLowerCase().lastIndexOf(".zip") == -1 && !exist) {
                picture.setSiteid(siteid);
                picture.setColumnid(columnID);
                picture.setWidth(width);
                picture.setHeight(height);
                picture.setPicsize(imagesize);
                picture.setPicname(filename);
                picture.setImgurl(imgurl);
                picture.setCreatedate(new Timestamp(System.currentTimeMillis()));
                newfilename = pictureMgr.saveOnePicture(picture);
            } else {
                newfilename = filename;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newfilename;
    }

    public int mvPicToUserUploaddir(int smallpic,String picname, String filename, String newlocationDir, String username, int siteid,
                                    int hostID, String fileDir, int delflag, int addWaterMark,String notes) throws Exception {
        int errcode = 0;
        String newlocation = "";
        File uploadedfile = new File(getUploaddir(), filename);

        if (!uploadedfile.isFile()) {
            errcode = -2;
            return errcode;
        }

        try {
            filename = picname + filename.substring(filename.lastIndexOf("."));
            newlocation = newlocationDir + filename;
            FileDeal.copy(uploadedfile.toString(), newlocation, delflag);
        }
        catch (Exception ex) {
            errcode = -1;
            throw new Exception("can't move upload pic to user dirtectory!" + ex.getMessage());
        }

        //给上传的图片添加水印
        if (addWaterMark == 1) {
            PicPeer picPeer = new PicPeer();
            String returnmsg = picPeer.AddWaterMarkToImg(newlocation,notes);
            if ((returnmsg != null) && (returnmsg.length() > 0)) {
                System.out.println("Add watermark failed in AddWaterMarkToImg method of com.bizwink.cms.pic.PicPeer");
            }
        }

        IPublishManager publishManager = PublishPeer.getInstance();
        fileDir = fileDir + "images" + File.separator;
        if (hostID == 0)
            errcode = publishManager.publish(username, newlocation, siteid, fileDir, 0);          //发布到默认主机上
        else
            errcode = publishManager.publish(username, newlocation, siteid, fileDir, 0, hostID);   //发布到其它主机上

        return errcode;
    }

    /**
     * 将用户上传的图片或模板文件移动到对应的位置。本方法适用于css,js,images都存放在images文件夹下
     */
    public int mvModelToUserUploaddir(String filename, String newlocationDir, String username, int siteid, String sitename, int hostID, String fileDir, int imgflag, int delflag) throws Exception {
        int errcode = 0;
        String ext = "unknow";
        if (filename.lastIndexOf(".") > 0) ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        String newfilename = newlocationDir + fileDir + filename;
        String targetDir = "";
        IPublishManager publishManager = PublishPeer.getInstance();
        File uploadedfile = new File(getUploaddir(), filename);

        if (!uploadedfile.isFile()) {
            return errcode;
        }

        try {
            if (CmsServer.getInstance().getOStype().equalsIgnoreCase("win2000")) filename = StringUtil.gb2iso4View(filename);

            if ("js".equals(ext) || "css".equals(ext) || "swf".equals(ext) || "jpeg".equals(ext) || "jpg".equals(ext) || "gif".equals(ext) || "png".equals(ext)) {
                if (imgflag == 0) {
                    newfilename = newlocationDir + File.separator + "images" + File.separator + filename;
                    fileDir = File.separator + "images" + File.separator;
                } else {
                    newfilename = newlocationDir + fileDir + "images" + File.separator + filename;
                    fileDir = fileDir + "images" + File.separator;
                }

                FileDeal.copy(uploadedfile.toString(), newfilename, delflag);
                if (hostID == 0)
                    errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);          //发布到默认主机上
                else
                    errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0, hostID);  //发布到其它主机上
            } else if (("html".equals(ext)) || ("htm".equals(ext)) || ("shtml".equals(ext)) || ("shtm".equals(ext)) || ("jsp".equals(ext)) || ("asp".equals(ext))) {
                fileDir = File.separator;
                newfilename = newlocationDir + File.separator + "_templates" + fileDir + filename;
                FileDeal.copy(uploadedfile.toString(), newfilename, 1);
            } else if ("zip".equals(ext)) {
                if (imgflag == 0) {
                    newfilename = newlocationDir + File.separator + "images" + File.separator + filename;
                    targetDir = newlocationDir + File.separator + "images" + File.separator;
                } else {
                    newfilename = newlocationDir + fileDir + "images" + File.separator + filename;
                    targetDir = newlocationDir + fileDir + "images" + File.separator;
                }

                FileDeal.copy(uploadedfile.toString(), newfilename, delflag);
                UnZip uzip = new UnZip();
                uzip.hostID = hostID;
                uzip.UnZip(newfilename, targetDir, sitename, siteid, 0);
            } else {
                errcode = -2;
            }
        } catch (Exception ex) {
            errcode = -1;
            ex.printStackTrace();
        } finally {
            if (filename.toLowerCase().endsWith(".zip")) {
                File sfile = new File(uploadedfile.toString());
                if (sfile.exists()) sfile.delete();
                sfile = new File(newfilename);
                if (sfile.exists()) sfile.delete();
            } else {
                File sfile = new File(uploadedfile.toString());
                if (sfile.exists()) sfile.delete();
            }
        }

        return errcode;
    }

    /**
     * 将用户上传的图片或模板文件移动到对应的位置。本方法适用于css,js,images分别存储
     */
    public int mvModelToUserUploaddir2(String filename, String newlocationDir, String username, int siteid, String sitename, int columnID, String fileDir, int imgflag, int delflag) throws Exception {
        int errcode = 0;
        int posi = -1;
        posi = filename.lastIndexOf(".");
        String ext = filename.substring(posi + 1);
        String targetDir = "";
        String newfilename = "";
        IPublishManager publishManager = PublishPeer.getInstance();
        File uploadedfile = new File(getUploaddir(), filename);

        if (!uploadedfile.isFile()) {
            return errcode;
        }

        try {
            if ("swf".equalsIgnoreCase(ext) || "jpeg".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext) ||
                    "gif".equalsIgnoreCase(ext) || "png".equalsIgnoreCase(ext) || "bmp".equalsIgnoreCase(ext)) {
                fileDir = fileDir + "images" + File.separator;
                newfilename = newlocationDir + fileDir + filename;
                File thefile = new File(newfilename);
                if (!thefile.exists()) {
                    //拷贝文件到CMS的相应目录之下
                    FileDeal.copy(uploadedfile.toString(), newfilename, delflag);
                    //从CMS相应目录之下将文件发布到WEB服务器
                    errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                    //保存文件属性信息到数据库中
                    savePicture(newfilename, newlocationDir, siteid, columnID, fileDir, imgflag);
                }  else {
                    if (delflag == 0) errormsg.setErrorPics(newfilename);
                }
            } else if ("css".equalsIgnoreCase(ext)) {
                fileDir = File.separator + "css" + File.separator;
                newfilename = newlocationDir + File.separator + "css" + File.separator + filename;
                File thefile = new File(newfilename);
                if (!thefile.exists()) {
                    FileDeal.copy(uploadedfile.toString(), newfilename, delflag);
                    errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                    savePicture(newfilename, newlocationDir, siteid, columnID, fileDir, imgflag);
                } else {
                    if (delflag == 0) errormsg.setErrorPics(newfilename);
                }
            } else if ("js".equalsIgnoreCase(ext) || "vbs".equalsIgnoreCase(ext)) {
                fileDir = File.separator + "js" + File.separator;
                newfilename = newlocationDir + File.separator + "js" + File.separator + filename;
                File thefile = new File(newfilename);
                if (!thefile.exists()) {
                    FileDeal.copy(uploadedfile.toString(), newfilename, delflag);
                    errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                } else {
                    if (delflag == 0) errormsg.setErrorPics(newfilename);
                }
            } else if ("zip".equalsIgnoreCase(ext)) {
                targetDir = newlocationDir + fileDir + "images" + File.separator;
                UnZip uzip = new UnZip();
                fileDir = fileDir + "images" + File.separator;
                List existFileList = uzip.UnZipTemplatePictures(newlocationDir  + File.separator + filename, targetDir, sitename, siteid, fileDir, columnID, delflag);
                if (delflag == 0) {
                    for (int kk=0; kk<existFileList.size(); kk++) {
                        String fname = (String)existFileList.get(kk);
                        errormsg.setErrorPics("sites/" + sitename + fileDir +"images/" + fname);
                    }
                }
            } else {
                errcode = -2;
            }
        } catch (Exception ex) {
            errcode = -1;
            ex.printStackTrace();
        } finally {
            if (uploadedfile.exists()) uploadedfile.delete();
            System.out.println("uploadedfile===" + uploadedfile.getAbsolutePath());
        }

        return errcode;
    }

    private int saveUploadFile(String ssfilename, String ttfilename, String downsfilename, String downtfilename, List extAttrList,int tcflag,
                               String newlocationDir, String username, int siteid, String fileDir, String maintitle,
                               String vicetitle, String summary, String keyword, String source, int doclevel, int using,
                               int subscriber, int sortid, int year, int month, int day, int hour, int minute, int columnID, int saveflag) throws Exception {
        int errcode = 0;
        String filename = null;
        String newfilename = null;
        File uploadedfile = null;
        IPublishManager publishManager = PublishPeer.getInstance();
        String ymd = String.valueOf(new Date(System.currentTimeMillis()));
        ymd = ymd.replaceAll("-", "");
        fileDir = fileDir + ymd + File.separator + "download" + File.separator;
        String initFileDir = getUploaddir() + File.separator;

        if (tcflag == 0) {
            uploadedfile = new File(getUploaddir(), ssfilename);
            if (!uploadedfile.isFile()) {
                errcode = -1;
                return errcode;
            }

            if (downsfilename != null) {
                uploadedfile = new File(getUploaddir(), downsfilename);
                if (!uploadedfile.isFile()) {
                    errcode = -1;
                    return errcode;
                }
            }

            try {
                newlocationDir = this.uploaddir + fileDir;
                if (!CmsServer.getInstance().getOStype().equals("unix")) {
                    if (downsfilename == null) {
                        filename = ssfilename;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                    } else {
                        filename = ssfilename;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        filename = downsfilename;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                    }
                } else {  //windows操作系统
                    if (downsfilename == null) {
                        filename = ssfilename;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                    } else {
                        filename = ssfilename;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        filename = downsfilename;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                    }
                }
            }
            catch (Exception ex) {
                errcode = -1;
                ex.printStackTrace();
                throw new Exception("can't move upload pic to user dirtectory!" + ex.getMessage());
            }
        } else {  //上传繁体文件
            uploadedfile = new File(getUploaddir(), ssfilename);
            if (!uploadedfile.isFile()) {
                errcode = -1;
                return errcode;
            }

            if (downsfilename != null) {
                uploadedfile = new File(getUploaddir(), downsfilename);
                if (!uploadedfile.isFile()) {
                    errcode = -1;
                    return errcode;
                }
            }

            if (ttfilename != null) {
                uploadedfile = new File(getUploaddir(), ttfilename);
                if (!uploadedfile.isFile()) {
                    errcode = -1;
                    return errcode;
                }
            }

            if (downtfilename != null) {
                uploadedfile = new File(getUploaddir(), downtfilename);
                if (!uploadedfile.isFile()) {
                    errcode = -1;
                    return errcode;
                }
            }

            try {
                if (!CmsServer.getInstance().getOStype().equals("unix")) {
                    if (downsfilename == null && ttfilename == null && downtfilename == null) {
                        //上传简体
                        filename =ssfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 0);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                    } else
                    if (downtfilename == null && ttfilename == null && ssfilename != null && downsfilename != null) {
                        //上传简体文件
                        filename = ssfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 0);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体文件
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);

                        //上传简体下载文件
                        filename = downsfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + downsfilename, newfilename, 0);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体下载文件
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                    } else
                    if (ttfilename != null && ssfilename != null && downsfilename == null && downtfilename == null) {
                        //上传简体
                        filename = ssfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体--修改成为与简体文件同名
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ttfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                    } else
                    if (ttfilename != null && ssfilename != null && downsfilename != null && downtfilename != null) {
                        //上传简体文件
                        filename = ssfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体文件--与上传的简体文件同名
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ttfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);

                        //上传简体下载文件
                        filename = downsfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体下载文件----与下载简体文件同名
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + downtfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                    } else {
                        errcode = -2;
                        return errcode;
                    }
                } else {    //windows操作系统
                    if (downsfilename == null && ttfilename == null && downtfilename == null) {
                        //上传简体
                        filename = ssfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 0);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                    } else
                    if (downtfilename == null && ttfilename == null && ssfilename != null && downsfilename != null) {
                        //上传简体文件
                        filename = ssfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 0);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体文件
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);

                        //上传简体下载文件
                        filename = downsfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + downsfilename, newfilename, 0);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体下载文件
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                    } else
                    if (ttfilename != null && ssfilename != null && downsfilename == null && downtfilename == null) {
                        //上传简体
                        filename = ssfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体--修改成为与简体文件同名
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ttfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                    } else
                    if (ttfilename != null && ssfilename != null && downsfilename != null && downtfilename != null) {
                        //上传简体文件
                        filename = ssfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体文件
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + ttfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);

                        //上传简体下载文件
                        filename = downsfilename;
                        newlocationDir = this.uploaddir + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                        //上传繁体下载文件
                        newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                        newfilename = newlocationDir + filename;
                        FileDeal.copy(initFileDir + downtfilename, newfilename, 1);
                        errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                    } else {
                        errcode = -2;
                        return errcode;
                    }
                }
            }
            catch (Exception ex) {
                errcode = -1;
                throw new Exception("can't move upload pic to user dirtectory!" + ex.getMessage());
            }
        }

        IColumnManager columnMgr = ColumnPeer.getInstance();
        IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();

        int auditFlag = columnMgr.getColumn(columnID).getIsAudited();
        Timestamp publishtime = new Timestamp(year - 1900, month - 1, day, hour, minute, 0, 0);
        if (errcode == 0) {
            try {
                Article article = new Article();
                article.setMainTitle(maintitle);
                article.setViceTitle(vicetitle);
                article.setSummary(summary);
                article.setKeyword(keyword);
                article.setSource(source);
                article.setPublishTime(publishtime);
                article.setDocLevel(doclevel);
                article.setSortID(sortid);
                article.setFileName(ssfilename);
                article.setDownfilename(downsfilename);
                article.setColumnID(columnID);
                article.setEditor(username);
                article.setCreator(username);
                article.setCreateDate(new Timestamp(System.currentTimeMillis()));
                article.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                article.setAuditFlag(auditFlag);
                article.setPubFlag(0);
                article.setStatus(using);
                article.setSubscriber(subscriber);
                article.setContent("");
                article.setNullContent(1);
                article.setDirName(fileDir);
                article.setSiteID(siteid);
                article.setReferArticleID(0);
                article.setJoinRSS(0);
                article.setClickNum(0);
                article.setViceDocLevel(0);
                article.setT1(0);
                article.setT2(0);
                article.setT3(0);
                article.setT4(0);
                article.setT5(0);
                //attrList,attechmentList,article,pubcolumns,recommends,uploadimages,auditlist,mmfiles
                errcode = extendMgr.create(extAttrList, null, article, null,null,null,null,null);
            }
            catch (ExtendAttrException e) {
                errcode = -3;
                System.out.println(e.getMessage());
            }
        }
        return errcode;
    }

    public int updateUploadFile(boolean upload, String ssfilename, String ttfilename, String downsfilename, String downtfilename,List extAttrList,int tcflag,
                                String newlocationDir, String username, int siteid, String fileDir, int articleid, String maintitle, String vicetitle,
                                String summary, String keyword, String source, int doclevel, int using, int subscriber, int sortid,
                                int year, int month, int day, int hour, int minute, int columnID, int saveflag) throws Exception {

        int errcode = 0;
        IArticleManager articleMgr = ArticlePeer.getInstance();
        Article article = articleMgr.getArticle(articleid);
        int auditFlag = article.getAuditFlag();
        String filename = null;
        String newfilename = null;
        File uploadedfile = null;
        IPublishManager publishManager = PublishPeer.getInstance();
        //java.text.SimpleDateFormat ymd_format = new java.text.SimpleDateFormat("yyyyMMdd");
        //String ymd = ymd_format.format(article.getCreateDate());
        String ymd = String.valueOf(article.getCreateDate());
        ymd = ymd.substring(0, ymd.indexOf(" "));
        ymd = ymd.replaceAll("-", "");
        fileDir = fileDir + ymd + File.separator + "download" + File.separator;
        String initFileDir = getUploaddir() + File.separator;

        if (upload) {
            if (tcflag == 0) {
                uploadedfile = new File(getUploaddir(), ssfilename);
                if (!uploadedfile.isFile()) {
                    errcode = -1;
                    return errcode;
                }

                if (downsfilename != null) {
                    uploadedfile = new File(getUploaddir(), downsfilename);
                    if (!uploadedfile.isFile()) {
                        errcode = -1;
                        return errcode;
                    }
                }

                try {
                    newlocationDir = this.uploaddir + fileDir;
                    if (!CmsServer.getInstance().getOStype().equals("unix")) {
                        if (downsfilename == null) {
                            filename = ssfilename;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                        } else {
                            filename = ssfilename;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            filename = ssfilename;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                        }
                    } else {
                        if (downsfilename == null) {
                            filename = StringUtil.iso2gbindb(ssfilename);
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                        } else {
                            filename = StringUtil.iso2gbindb(ssfilename);
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            filename = StringUtil.iso2gbindb(downsfilename);
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);
                        }
                    }
                }
                catch (Exception ex) {
                    errcode = -1;
                    ex.printStackTrace();
                    //throw new Exception("can't move upload pic to user dirtectory!" + ex.getMessage());
                }
            } else {  //上传繁体文件
                uploadedfile = new File(getUploaddir(), ssfilename);
                if (!uploadedfile.isFile()) {
                    errcode = -1;
                    return errcode;
                }

                if (downsfilename != null) {
                    uploadedfile = new File(getUploaddir(), downsfilename);
                    if (!uploadedfile.isFile()) {
                        errcode = -1;
                        return errcode;
                    }
                }

                if (ttfilename != null) {
                    uploadedfile = new File(getUploaddir(), ttfilename);
                    if (!uploadedfile.isFile()) {
                        errcode = -1;
                        return errcode;
                    }
                }

                if (downtfilename != null) {
                    uploadedfile = new File(getUploaddir(), downtfilename);
                    if (!uploadedfile.isFile()) {
                        errcode = -1;
                        return errcode;
                    }
                }
                try {
                    if (!CmsServer.getInstance().getOStype().equals("unix")) {
                        if (downsfilename == null && ttfilename == null && downtfilename == null) {
                            //上传简体
                            filename = new String(ssfilename.getBytes("iso8859_1"), "GBK");
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 0);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                        } else
                        if (downtfilename == null && ttfilename == null && ssfilename != null && downsfilename != null) {
                            //上传简体文件
                            filename = new String(ssfilename.getBytes("iso8859_1"), "GBK");
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 0);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体文件
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);

                            //上传简体下载文件
                            filename = new String(downsfilename.getBytes("iso8859_1"), "GBK");
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + downsfilename, newfilename, 0);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体下载文件
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                        } else
                        if (ttfilename != null && ssfilename != null && downsfilename == null && downtfilename == null) {
                            //上传简体
                            filename = new String(ssfilename.getBytes("iso8859_1"), "GBK");
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体--修改成为与简体文件同名
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ttfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                        } else
                        if (ttfilename != null && ssfilename != null && downsfilename != null && downtfilename != null) {
                            //上传简体文件
                            filename = new String(ssfilename.getBytes("iso8859_1"), "GBK");
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体文件--与上传的简体文件同名
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ttfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);

                            //上传简体下载文件
                            filename = new String(downsfilename.getBytes("iso8859_1"), "GBK");
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体下载文件----与下载简体文件同名
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + downtfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                        } else {
                            errcode = -2;
                            return errcode;
                        }
                    } else {    //windows操作系统
                        if (downsfilename == null && ttfilename == null && downtfilename == null) {
                            //上传简体
                            filename = StringUtil.iso2gbindb(ssfilename);
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 0);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                        } else
                        if (downtfilename == null && ttfilename == null && ssfilename != null && downsfilename != null) {
                            //上传简体文件
                            filename = StringUtil.iso2gbindb(ssfilename);
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 0);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体文件
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);

                            //上传简体下载文件
                            filename = StringUtil.iso2gbindb(downsfilename);
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + downsfilename, newfilename, 0);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体下载文件
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                        } else
                        if (ttfilename != null && ssfilename != null && downsfilename == null && downtfilename == null) {
                            //上传简体
                            filename = StringUtil.iso2gbindb(ssfilename);
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体--修改成为与简体文件同名
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ttfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                        } else
                        if (ttfilename != null && ssfilename != null && downsfilename != null && downtfilename != null) {
                            //上传简体文件
                            filename = StringUtil.iso2gbindb(ssfilename);
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ssfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体文件
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + ttfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);

                            //上传简体下载文件
                            filename = StringUtil.iso2gbindb(downsfilename);
                            newlocationDir = this.uploaddir + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + downsfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

                            //上传繁体下载文件
                            newlocationDir = this.uploaddir + File.separator + "big5" + fileDir;
                            newfilename = newlocationDir + filename;
                            FileDeal.copy(initFileDir + downtfilename, newfilename, 1);
                            errcode = publishManager.publish(username, newfilename, siteid, File.separator + "big5" + fileDir, 0);
                        } else {
                            errcode = -2;
                            return errcode;
                        }
                    }
                }
                catch (Exception ex) {
                    errcode = -1;
                    throw new Exception("can't move upload pic to user dirtectory!" + ex.getMessage());
                }
            }
        } else {
            ssfilename = article.getFileName();
        }

        //maintitle = StringUtil.iso2gbindb(maintitle);
        //vicetitle = StringUtil.iso2gbindb(vicetitle);
        //summary = StringUtil.iso2gbindb(summary);
        //keyword = StringUtil.iso2gbindb(keyword);
        //source = StringUtil.iso2gbindb(source);
        //filename = StringUtil.iso2gbindb(filename);
        //username = StringUtil.iso2gbindb(username);

        Timestamp publishtime = new Timestamp(year - 1900, month - 1, day, hour, minute, 0, 0);
        if (errcode == 0) {
            IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();

            try {
                article = new Article();
                article.setMainTitle(maintitle);
                article.setViceTitle(vicetitle);
                article.setSummary(summary);
                article.setKeyword(keyword);
                article.setSource(source);
                article.setDocLevel(doclevel);
                article.setSortID(sortid);
                //article.setFileName(StringUtil.iso2gbindb(ssfilename));
                article.setFileName(ssfilename);
                //article.setDownfilename(StringUtil.iso2gbindb(downsfilename));
                article.setDownfilename(downsfilename);
                article.setEditor(username);
                article.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                article.setAuditFlag(0);
                article.setPubFlag(0);
                article.setPublishTime(publishtime);
                article.setAuditFlag(auditFlag);
                article.setPubFlag(0);
                article.setStatus(using);
                article.setSubscriber(subscriber);
                article.setContent("");
                article.setNullContent(1);
                article.setDirName(fileDir);
                article.setSiteID(siteid);
                article.setID(articleid);
                article.setCreateDate(new Timestamp(System.currentTimeMillis()));
                article.setT1(0);
                article.setT2(0);
                article.setT3(0);
                article.setT4(0);
                article.setT5(0);

                //如果该篇文章为退稿文章，则将以前的审核信息更新为过期信息
                if (auditFlag == 2) {
                    IAuditManager auditMgr = AuditPeer.getInstance();
                    auditMgr.updateAudit_Info("", articleid, "", 0,0);
                }

                errcode = extendMgr.update(extAttrList, null, article, null, null,null,null,null);
            }
            catch (ExtendAttrException e) {
                errcode = -3;
                System.out.println(e.getMessage());
            }
        }

        return errcode;
    }

    public int saveUploadBig5File(String filename, String sfilename, String newlocationDir, String username, int siteid, String fileDir, int saveflag) throws Exception {
        int errcode = 0;
        File uploadedfile = new File(getUploaddir(), sfilename);
        String newfilename = "";

        if (!uploadedfile.isFile()) {
            errcode = -1;
            return errcode;
        }

        try {
            newfilename = newlocationDir + filename;
            FileDeal.copy(uploadedfile.toString(), newfilename, saveflag);
        } catch (Exception ex) {
            errcode = -1;
            throw new Exception("can't move upload pic to user dirtectory!" + ex.getMessage());
        }

        IPublishManager publishManager = PublishPeer.getInstance();
        Date nowdate = new Date(System.currentTimeMillis());
        fileDir = fileDir + nowdate + File.separator + "download" + File.separator;
        errcode = publishManager.publish(username, newfilename, siteid, fileDir, 0);

        return errcode;
    }

    public void clear() {
        this.hashimg = null;
        this.hashimg2 = null;
        this.hashpara = null;
        //this.duplicateFileList = null;
    }

    private MultipartFormHandle() {

    }

    private static MultipartFormHandle mf = new MultipartFormHandle();

    public static MultipartFormHandle getInstance() {
        return mf;
    }
}

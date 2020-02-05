<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.*" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.publish.*" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.io.*"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.images.resizeImage" %>
<%@ page import="java.util.Iterator" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    sitename = StringUtil.replace(sitename,".","_");
    String baseDir = request.getRealPath("/");
    String dir = baseDir + "sites" + java.io.File.separator + sitename + java.io.File.separator + "_company" + java.io.File.separator;
    int columnid = ParamUtil.getIntParameter(request,"column",0);
    int companyid = ParamUtil.getIntParameter(request,"id",0);
    ICompanyinfoManager companyManager = CompanyinfoPeer.getInstance();
    IPublishManager publishMgr = PublishPeer.getInstance();

    try {
        int startflag = ParamUtil.getIntParameter(request,"startflag",0);
        if (startflag == 1) {
            DiskFileUpload upload=new DiskFileUpload();
            List uploadlist=upload.parseRequest(request);
            Iterator iter=uploadlist.iterator();

            List picitems = new ArrayList();
            List mediaitems = new ArrayList();
            while (iter.hasNext()){
                FileItem  item=(FileItem)iter.next();
                if(!item.isFormField()){
                    String filename=item.getName();
                    int posi = filename.lastIndexOf(".");
                    String type = null;
                    if (posi > -1) {
                        type = filename.substring(posi).toLowerCase();
                        if (type.equals(".avi") || type.equals(".mpg") || type.equals(".wmv") || type.equals(".3gp") || type.equals(".mov") || type.equals(".mp4") || type.equals(".asf") || type.equals(".mpeg") || type.equals(".mpe")
                                || type.equals(".wmv9") || type.equals(".rm") || type.equals(".rmvb") || type.equals(".asx")) {
                            mediaitems.add(item);
                        } else if (type.equals(".jpg") || type.equals(".jpeg") || type.equals(".png") || type.equals(".bmp") || type.equals(".gif")){
                            picitems.add(item);
                        }
                    }
                } else {
                    String name = item.getFieldName();
                    if (name.equalsIgnoreCase("column")) columnid = Integer.parseInt(item.getString());
                    if (name.equalsIgnoreCase("company")) companyid = Integer.parseInt(item.getString());
                }
            }

            //保存图片信息和多媒体文件信息到数据库中
            retCompany retc = companyManager.savePicAndMedias(siteid,columnid,companyid,picitems,mediaitems,dir);

            //保存多媒体文件
            List fname = retc.getList();
            for(int i=0; i<mediaitems.size(); i++){
                FileItem  item=(FileItem)mediaitems.get(i);
                String filename=item.getName();
                if(!filename.equals("")) {
                    filename=FilenameUtils.getName(filename);
                    int posi = filename.indexOf(".");
                    String ext = null;
                    if (posi>-1) ext = filename.substring(posi+1);
                    String newfilename = (String)fname.get(i);

                    String savefilename=dir + companyid + java.io.File.separator + "images" + java.io.File.separator + newfilename;
                    java.io.File file = new java.io.File(dir + companyid + java.io.File.separator + "images" + java.io.File.separator);

                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    java.io.File saveFilepath=new java.io.File(savefilename);
                    item.write(saveFilepath);
                }
            }

            //保存图片文件
            resizeImage resize_image = new resizeImage();
            String dirName = "/_company/" + companyid + java.io.File.separator;
            for(int i=0; i<picitems.size(); i++) {
                FileItem item = (FileItem)picitems.get(i);
                String filename = item.getName();
                if (!filename.equals("")) {
                    filename=FilenameUtils.getName(filename);
                    String savefilename=dir + companyid + java.io.File.separator + "images" + java.io.File.separator + filename;
                    java.io.File file = new java.io.File(dir + companyid + java.io.File.separator + "images" + java.io.File.separator);

                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    java.io.File saveFilepath=new java.io.File(savefilename);
                    item.write(saveFilepath);

                    String s_filename = resize_image.createThumbnailBy_jmagick(siteid,username,"/_company/" + companyid + "/",dir + java.io.File.separator + companyid + java.io.File.separator + "images"  + java.io.File.separator + filename,"180X180","s");
                    publishMgr.publish(username, dir + java.io.File.separator + companyid + java.io.File.separator + "images"  + java.io.File.separator + filename, siteid, "/_company/" + companyid + "/images/", 0);
                }
            }
            response.sendRedirect(response.encodeRedirectURL("../companys/closewin.jsp?column=" + columnid + "&siteid=" + siteid + "&fromflag=c"));
            return;
        }
    }catch ( Exception e ) {
        e.printStackTrace();
    }
%>
<html>
<head>
    <title>公司添加页面</title>
    <style type="text/css">
        td {
            font-size: 12px
        }
    </style>
</head>
<body>
<center>
    <table>
        <form name="addForm" action="upload.jsp?startflag=1" method="post" enctype="multipart/form-data">
            <input type="hidden" name="startflag" value="1">
            <input type="hidden" name="column" value="<%=columnid%>">
            <input type="hidden" name="company" value="<%=companyid%>">
            <tr>
                <td>图片或多媒体文件:</td>
                <td><input type="file" name="companypic1" size="30"></td>
            </tr>
            <tr>
                <td>图片或多媒体文件:</td>
                <td><input type="file" name="companypic2" size="30"></td>
            </tr>
            <tr>
                <td>图片或多媒体文件:</td>
                <td><input type="file" name="companypic3" size="30"></td>
            </tr>
            <tr>
                <td>图片或多媒体文件:</td>
                <td><input type="file" name="companypic4" size="30"></td>
            </tr>
            <tr>
                <td align="left"><input type="button" value="返回" onclick="javascript:window.close();"></td>
                <td align="center"><input type="submit" value="提交"></td>
            </tr>
        </form>
    </table>
</center>
</body>
</html>
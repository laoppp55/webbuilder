<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.io.File" %>
<%@ page import="com.bizwink.cms.sitesetting.IFtpSetManager" %>
<%@ page import="com.bizwink.cms.sitesetting.FtpSetting" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.upload.RandomStrg" %>
<%@ page import="com.bizwink.cms.toolkit.csinfo.CsInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sun.mail.iap.Response" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
        Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
        if (authToken == null) {
            response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
            return;
        }
        String sitename = authToken.getSitename();
        int siteid = authToken.getSiteID();
        String baseDir = application.getRealPath("/");
        IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
        List siteList = ftpsetMgr.getFtpInfos(siteid);
       // FtpInfo ftpInfo = ftpsetMgr.getFtpInfo(1);
        //String docPath = ftpInfo.getDocpath();
        List piclist = new ArrayList();

        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();
        String ext;//文件扩展名
        long file_size_max = 4000000;


        for (int i = 0; i < mySmartUpload.getFiles().getCount(); i++) {
            com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(i);
            if (myFile.isMissing()) {
                out.print("<script type=\"text/javascript\">");
                out.print("window.alert(\"请先选择要上传的文件\");");
                out.print("window.location=window.location");
                out.print("</script>");
            } else {
                String myFileName = myFile.getFileName();//取得上载的文件的文件名
                ext = myFile.getFileExt();//取得后缀名
                if (!(ext.length() > 0)) {
                    out.println("**************myFileName的名称是：" + myFileName);
                }

                int file_size = myFile.getSize();//取得文件的大小

                if (file_size < file_size_max) {
                    //更改文件名
                    RandomStrg rstr = new RandomStrg();
                    rstr.setCharset("a-z0-9");
                    rstr.setLength(8);
                    rstr.generateRandomObject();
                    String filename = "pic" + rstr.getRandom() + "." + ext;
                    String saveUrl = baseDir + "sites/" + sitename + "/images/";//文件保存路径
                    File file = new File(saveUrl);
                    //如果文件夹不存在则创建
                    if (!file.exists() && !file.isDirectory()) {
                        System.out.println("//不存在");
                        file.mkdirs();
                    }
                    saveUrl += filename;
                    myFile.saveAs(saveUrl, mySmartUpload.SAVE_PHYSICAL);

                   /* for (int j = 0; j < siteList.size(); j++) {
                        FtpInfo ftpInfo = (FtpInfo)siteList.get(j);
                        String docPath = ftpInfo.getDocpath();
                        String dirUrl = docPath + "/images/"; //文件发布路径
                        File file1 = new File(dirUrl);
                        //如果文件夹不存在则创建
                        if (!file1.exists() && !file1.isDirectory()) {
                            System.out.println("//不存在");
                            file1.mkdirs();
                        }
                        dirUrl += filename;
                        myFile.saveAs(dirUrl, mySmartUpload.SAVE_PHYSICAL);
                    }*/


                    CsInfo csInfo_pic = new CsInfo();
                    csInfo_pic.setRoom_pic_url(filename);
                    piclist.add(csInfo_pic);


                }
            }
        }


%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>files upload</title>
    <!--script src="jquery-1.7.1.min.js"></script-->
    <script src="http://libs.baidu.com/jquery/1.7.2/jquery.min.js"></script>
    <script src="file_upload_plug-in.js"></script>
    <script>
        $(function () {
            //var btn = $("#Button1");

            var btn = $("#Button1").uploadFile({
                url: "test.jsp",
                fileSuffixs: ["jpg", "png", "gif", "txt"],
                maximumFilesUpload: 10,//最大文件上传数
                onComplete: function (msg) {
                    $("#testdiv").append(msg + "<br/>");
                },
                onAllComplete: function () {
                    alert("全部上传完成");
                },
                isGetFileSize: true,//是否获取上传文件大小，设置此项为true时，将在onChosen回调中返回文件fileSize和获取大小时的错误提示文本errorText
                onChosen: function (file, obj, fileSize, errorText) {
                    if (!errorText) {
                        $("#file_size").text(file + "文件大小为：" + fileSize + "KB");
                    } else {
                        alert(errorText);
                        return false;
                    }
                    return true;//返回false将取消当前选择的文件
                },
                perviewElementId: "fileList", //设置预览图片的元素id
                perviewImgStyle: { width: '100px', height: '100px', border: '1px solid #ebebeb' }//设置预览图片的样式
            });

            var upload = btn.data("uploadFileData");

            $("#files").click(function () {
                upload.submitUpload();
            });
        });
    </script>

</head>
<body>
<div id="file_size" style="width: 400px; border-bottom: 1px solid #C0C0C0;"></div>
<div style="width: 400px; height: 300px;">
    <div style="font-size: 13px; font-weight:bold;color: #808080;font-family:'微软雅黑','黑体','华文细黑';">对于上传按钮和选择文件按钮，你可以使用其他任何形式的元素，可以是图片或一切你能想到的东西，都是可以的</div>
    <input id="Button1" type="button" value="选择文件" />
    <input id="files" type="button" value="上传" />
    <!-- <div style="width: 420px; height: 180px; overflow:auto;border:1px solid #C0C0C0;">-->
    <div id="fileList" style="margin-top: 10px; padding-top:10px; font-size: 13px; width:400px">

    </div>
    <!-- </div>-->
</div>
<br/>
<div id="testdiv"></div>
</body>
</html>

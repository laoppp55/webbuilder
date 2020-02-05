<%@ page import="com.jspsmart.upload.SmartUploadException" %>
<%@ page import="java.io.File" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.security.*," %>
<%@page contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    if (startflag ==1) {
        //上传图片
        com.jspsmart.upload.SmartUpload mySmartUpload = new com.jspsmart.upload.SmartUpload();
        mySmartUpload.initialize(pageContext);
        mySmartUpload.setMaxFileSize(5 * 1024 * 1024);

        try {
            //上载文件
            mySmartUpload.upload();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String myFileName = "";

        //取得所有上载的文件
        if (mySmartUpload.getFiles().getCount() > 0) {
            //取得上载的文件
            com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(0);
            if (!myFile.isMissing()) {
                long createtime = System.currentTimeMillis();
                //取得后缀名
                String ext = myFile.getFileExt();
                myFileName = String.valueOf(createtime) + "." + ext;

                if ((ext.equalsIgnoreCase("gif")) || (ext.equalsIgnoreCase("jpg")) || (ext.equalsIgnoreCase("jpeg")) || (ext.equalsIgnoreCase("bmp"))) {
                    //保存路径
                    String path = application.getRealPath("/");
                    String trace = path + File.separator + "sites/images/buttons/" + myFileName;

                    //将文件保存在服务器端
                    try {
                        myFile.saveAs(trace);
                    } catch (SmartUploadException e) {
                        e.printStackTrace();
                    }
                }
                mySmartUpload.stop();
                mySmartUpload = null;
                out.print("<script language=javascript>window.returnValue = '" + myFileName + "';top.close();</script>");
            }
        }
    }


%>
<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <meta http-equiv="Pragma" content="no-cache">
    <style type="text/css">
        TABLE {
            FONT-SIZE: 12px;
            word-break: break-all
        }

        BODY {
            FONT-SIZE: 12px;
            margin-top: 0px;
            margin-bottom: 0px;
            line-height: 20px;
        }

        .TITLE {
            FONT-SIZE: 16px;
            text-align: center;
            color: #FF0000;
            font-weight: bold;
            line-height: 30px;
        }

        .FONT01 {
            FONT-SIZE: 12px;
            color: #FFFFFF;
            line-height: 20px;
        }

        .FONT02 {
            FONT-SIZE: 12px;
            color: #D04407;
            font-weight: bold;
            line-height: 20px;
        }

        .FONT03 {
            FONT-SIZE: 14px;
            color: #000000;
            line-height: 25px;
        }

        A:link {
            text-decoration: none;
            line-height: 20px;
        }

        A:visited {
            text-decoration: none;
            line-height: 20px;
        }

        A:active {
            text-decoration: none;
            line-height: 20px;
            font-weight: bold;
        }

        A:hover {
            text-decoration: none;
            line-height: 20px;
        }

        .pad {
            padding-left: 4px;
            padding-right: 4px;
            padding-top: 2px;
            padding-bottom: 2px;
            line-height: 20px;
        }

        .form {
            border-bottom: #000000 1px solid;
            background-color: #FFFFFF;
            border-left: #000000 1px solid;
            border-right: #000000 1px solid;
            border-top: #000000 1px solid;
            font-size: 9pt;
            font-family: "宋体";
        }

        .botton {
            border-bottom: #000000 1px solid;
            background-color: #F1F1F1;
            border-left: #FFFFFF 1px solid;
            border-right: #333333 1px solid;
            border-top: #FFFFFF 1px solid;
            font-size: 9pt;
            font-family: "宋体";
            height: 20px;
            color: #000000;
            padding-bottom: 1px;
            padding-left: 1px;
            padding-right: 1px;
            padding-top: 1px;
            border-style: ridge
        }
    </style>
    <script language="javascript">
        function checkSelect() {
            if ((uploadform.pic.value == null) || (uploadform.pic.value == "")) {
                alert("请选择要上传的图片");
                return false;
            }

            return true;
        }

        function subOK(flag) {
            if(flag == 1) {
                window.returnValue = 'abc';
                window.close();
            }
        }
    </script>
</head>
<body>
<center>
    <table width="80%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
        <tr>
            <td>
                <table width="100%" border="0" cellpadding="0">
                    <tr bgcolor="#F4F4F4" align="center">
                        <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">按钮小图片</td>
                    </tr>
                    <tr bgcolor="#d4d4d4" align="right">
                        <td>
                            <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                <form name="uploadform" enctype="multipart/form-data" method="post" action="uploadIcon.jsp?startflag=1" onsubmit="javascript:return checkSelect();">
                                    <tr bgcolor="#FFFFFF" class="css_001">
                                        <td>请选择图片：<input type="file" name="pic"></td>
                                    </tr>
                                    <tr bgcolor="#FFFFFF" class="css_001">
                                        <td><input type="submit" name="sub" value="上传"></td>
                                    </tr>
                                </form>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</center>
</body>
</html>
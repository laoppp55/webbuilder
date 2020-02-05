<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.upload.*,
                 com.jspsmart.upload.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int type = ParamUtil.getIntParameter(request, "type", 0);

    String imgPath = application.getRealPath("/") + "sites" + java.io.File.separator +
            sitename + java.io.File.separator + "_sys_ListImages" + java.io.File.separator;
    java.io.File file = new java.io.File(imgPath);
    if (!file.exists()) {
        file.mkdirs();
    }

    boolean doUpload = ParamUtil.getBooleanParameter(request, "doUpload");
    if (doUpload) {
        SmartUpload upload = new SmartUpload();
        upload.initialize(pageContext);
        upload.upload();

        RandomStrg random = new RandomStrg();
        random.setCharset("0-9");
        random.setLength(6);
        random.generateRandomObject();
        String filename = random.getRandom();

        File uploadFile = upload.getFiles().getFile(0);
        String ext = uploadFile.getFileExt();
        String filePath = imgPath + filename + "." + ext;
        uploadFile.saveAs(filePath);

        IPublishManager publishMgr = PublishPeer.getInstance();
        publishMgr.publish(username, filePath, siteID, "/_sys_ListImages/", 0);

        String viewer = request.getHeader("user-agent");
        out.println("<script language=javascript>");
        if (viewer.toLowerCase().indexOf("gecko") == -1) {
            out.println("window.returnValue = \"<IMG src=/webbuilder/sites/<" + "%%sitename%%" + ">/_sys_ListImages/" + filename + "." + ext + " border=0>\";");
            out.println("window.close();");
        } else {
            out.println("var returnvalue = \"<IMG src=/webbuilder/sites/<" + "%%sitename%%" + ">/_sys_ListImages/" + filename + "."
                    + ext + " border=0>\";" +
                    "window.parent.opener.top.frames[\"cmsright\"].storeCaret(window.parent.opener.top.frames[\"cmsright\"]." +
                    "document.getElementById('content'));window.parent.opener.top.frames[\"cmsright\"].insertAtCaret(window." +
                    "parent.opener.top.frames[\"cmsright\"].document.getElementById('content'),returnvalue);top.close();");
            out.println("top.close();");
        }
        out.println("</script>");
        return;
    }
%>

<html>
<head>
    <title>列表图标</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language=javascript>
        function check()
        {
            var filename = document.uploadForm.filename.value;
            if (filename == "")
            {
                return false;
            }
            else
            {
                var ext = filename.substring(filename.lastIndexOf(".") + 1);
                if (ext != "gif" && ext != "jpg" && ext != "jpeg" && ext != "bmp" && ext != "png")
                {
                    alert("图片文件扩展名应为.gif .jpg .jpeg .bmp .png！");
                    return false;
                }
            }
            return true;
        }

        function SelectPic(filename)
        {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = "<IMG src=/webbuilder/sites/<" + "%%sitename%%" + ">/_sys_ListImages/" + filename + " border=0>";
                window.close();
            } else {
                <%if(type == 0){%>
                    var returnvalue = "<IMG src=/webbuilder/sites/<" + "%%sitename%%" + ">/_sys_ListImages/" + filename + " border=0>";                    
                    window.parent.opener.top.frames["cmsright"].storeCaret(window.parent.opener.top.frames["cmsright"].document.getElementById('content'));
                    window.parent.opener.top.frames["cmsright"].insertAtCaret(window.parent.opener.top.frames["cmsright"].document.getElementById('content'), returnvalue);
                    top.close();
                <%}else if(type == 6){%>
                    var returnvalue = "<IMG src=/webbuilder/sites/<" + "%%sitename%%" + ">/_sys_ListImages/" + filename + " border=0>";
                    window.parent.opener.storeCaret(window.parent.opener.document.getElementById('content'));
                    window.parent.opener.insertAtCaret(window.parent.opener.document.getElementById('content'), returnvalue);
                    top.close();
                <%}%>
            }
        }
    </script>
</head>
<body bgcolor="#CCCCCC">
<table border="1" width="98%" align=center borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=2 cellSpacing=0>
    <tr height=50>
        <td align=center><br>

            <form action="styleImageRight.jsp?doUpload=true" method="POST" name="uploadForm"
                  enctype="multipart/form-data" onsubmit="return check();">
                <input type="file" name="filename" size="20">&nbsp;&nbsp;
                <input type=submit value=" 上传 ">&nbsp;&nbsp;
                <input type=button value=" 取消 " onclick="top.close();">
            </form>
        </td>
    </tr>
    <tr>
        <td>
            <%
                int j = 1;
                java.io.File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        String filename = files[i].getName();
                        if (filename.indexOf(".") > -1) {
                            String extname = filename.substring(filename.indexOf(".") + 1, filename.length()).toLowerCase();
                            if (extname.equals("gif") || extname.equals("jpg") || extname.equals("jpeg") || extname.equals("bmp") || extname.equals("pcx"))
                                out.println("<a href=# onclick=SelectPic('" + filename + "');><img src=/webbuilder/sites/" + sitename + "/_sys_ListImages/" + filename + " border=1 width=20 height=20></a>&nbsp;");
                            if (j % 12 == 0) out.println("<br>");
                            j++;
                        }
                    }
                }
            %>
        </td>
    </tr>
</table>
</body>
</html>
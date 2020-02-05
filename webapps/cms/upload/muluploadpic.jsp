<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.cms.sitesetting.*,
                 com.jspsmart.upload.*,
                 com.bizwink.cms.server.CmsServer"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="org.apache.tools.zip.*" %>
<%@ page import="com.bizwink.cms.pic.*" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteID = authToken.getSiteID();
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    if (columnID == 0) {
        String idstr = (String) session.getAttribute("createtemplate_columnid");
        if ((idstr != null) && (!idstr.equals("null")) && (!idstr.equals("")))
            columnID = Integer.parseInt(idstr);
    }

    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);
    String dirName = column.getDirName();

    String domain = StringUtil.replace(sitename, "_", ".");
    if (CmsServer.getInstance().getCustomer().equalsIgnoreCase("linktone")) domain = "download.linktone.com";

    if (doCreate) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();
        int hostID = Integer.parseInt(mySmartUpload.getRequest().getParameter("hostID"));
        String alttext = mySmartUpload.getRequest().getParameter("alttext");
        /*if (alttext != null) {
            alttext = new String(alttext.getBytes(), "utf-8");
            alttext = StringUtil.iso2gbindb(alttext);
        }*/

        com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
        com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
        String filename = tempFile.getFileName();
        String returnvalue = "";
        String uploadPath = application.getRealPath("/") + "sites" + java.io.File.separator + sitename + dirName + "images" + java.io.File.separator;

        uploadPath = StringUtil.replace(uploadPath, "/", java.io.File.separator);
        java.io.File file = new java.io.File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        mySmartUpload.save(uploadPath);

        //发布到WEB服务器
        String objDir = dirName + "images/";
        IPublishManager publishMgr = PublishPeer.getInstance();

        int retcode = 0;
        if (hostID > 0)
            retcode = publishMgr.publish(username, uploadPath + filename, siteID, objDir, 0, hostID);
        else
            retcode = publishMgr.publish(username, uploadPath + filename, siteID, objDir, 0);
        if (retcode != 0) {
            out.println("向WEB服务器发布文件失败！");
            return;
        } else {

            List picList = new ArrayList();
            String zipDir = uploadPath + filename;
            UnZip uzip = new UnZip();
            uzip.hostID = hostID;
            uzip.UnZipAnywhere(zipDir, uploadPath, sitename, siteID, 0);
            Enumeration enum1 = null;
            ZipFile zf = new ZipFile(zipDir);
            enum1 = zf.getEntries();
            while (enum1.hasMoreElements()) {
                ZipEntry target = (ZipEntry) enum1.nextElement();
                String picurl = "/webbuilder/sites/" + sitename + dirName + "images/" + target.getName();
                returnvalue = returnvalue + "<IMG SRC=" + picurl + " border=0 alt=" + alttext + ">";
                Pic pic = new Pic();
                pic.setSiteid(siteID);
                pic.setColumnid(columnID);
                pic.setWidth(0);
                pic.setHeight(0);
                pic.setPicsize(Integer.parseInt(String.valueOf(target.getSize())));
                pic.setImgurl(picurl);
                pic.setPicname(target.getName());
                picList.add(pic);
            }
            zf.close();
            //图片信息入库
            IPicManager picMgr = PicPeer.getInstance();
            picMgr.createPic(picList);
        }
        out.println("<script language=javascript>");
        out.println("window.opener.top.FCKeditorAPI.GetInstance(\"content\").InsertHtml(\"" + returnvalue + "\");");
        out.println("top.close();");
        out.println("</script>");
        return;
    }

    //读出当前站点所有发布主机
    IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
    List siteIPList = ftpsetMgr.getOtherFtpInfos(siteID);
%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
    <title>文件上传</title>
    <script language=javascript>
        function check()
        {
            if (document.uploadForm.filename.value == "")
            {
                alert("请选择上传文件！");
                return false;
            }
            else if (document.uploadForm.siteIP.value == "")
            {
                alert("请至少选择一个发布主机！");
                return false;
            }
            else
            {
                return true;
            }
        }

        function displayURL()
        {
            var filename = document.uploadForm.filename.value;
            filename = filename.substring(filename.lastIndexOf("\\") + 1);
            document.uploadForm.url.value = "http://<%=domain+dirName%>download/" + filename;
        }
    </script>
</head>

<body bgcolor="#cccccc">
<FORM METHOD=POST ACTION="muluploadpic.jsp?column=<%=columnID%>&doCreate=true" NAME="uploadForm"
      onsubmit="return check();" enctype="multipart/form-data">
    <table width="100%" align=center border=0>
        <tr><td colspan="2">&nbsp;&nbsp;&nbsp;目前系统只支持ZIP压缩文件，请选择图片压缩文件</td></tr>
        <tr><td>&nbsp;</td></tr>
        <tr height=20>
            <td width="20%" align=right>上传文件：</td>
            <td width="80%"><input type=file name="filename" size=30></td>
        </tr>
        <tr height=80>
            <td align=right>发布主机：</td>
            <td><select name=hostID style="width:240" size=1>
                <option value="0">默认发布主机</option>
                <%
                    for (int i = 0; i < siteIPList.size(); i++) {
                        FtpInfo ftpinfo = (FtpInfo) siteIPList.get(i);
                        if (ftpinfo.getStatus() == 0) {
                            String siteName = ftpinfo.getSiteName();
                            if (siteName == null || siteName.length() == 0) siteName = sitename;
                %>
                <option value="<%=ftpinfo.getID()%>" selected><%=siteName%>
                </option>
                <%
                        }
                    }
                %>
            </select>
            </td>
        </tr>
        <tr>
            <td align=right>替换文字：</td>
            <td><input name="alttext" size="12"></td>
        </tr>

        <tr height=40>
            <td align=center colspan=2>
                <input type=submit value="  上传  ">&nbsp;&nbsp;&nbsp;&nbsp;<input type=button value="  取消  "
                                                                                 onclick="window.close();">
            </td>
        </tr>
    </table>
</FORM>

</body>
</html>

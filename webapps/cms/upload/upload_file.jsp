<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.publish.IPublishManager" %>
<%@ page import="com.bizwink.cms.sitesetting.IFtpSetManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.publish.PublishPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.FtpSetting" %>
<%@ page import="com.bizwink.cms.sitesetting.FtpInfo" %>
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

    if (doCreate) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.setCharset("gbk");
        mySmartUpload.upload();
        int hostID = Integer.parseInt(mySmartUpload.getRequest().getParameter("hostID"));
        String showword = mySmartUpload.getRequest().getParameter("showword");

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

        tempFile.saveAs(uploadPath + filename);
        mySmartUpload.save(uploadPath);

        //发布到WEB服务器
        String objDir = dirName + "download/";
        IPublishManager publishMgr = PublishPeer.getInstance();

        int retcode = 0;
        if (hostID > 0)
            retcode = publishMgr.publish(username, uploadPath + filename, siteID, objDir, 0, hostID);
        else
            retcode = publishMgr.publish(username, uploadPath + filename, siteID, objDir, 0);

        if (retcode != 0) {
            out.println("向WEB服务器发布文件失败！");
            return;
        }

        String returnvalue = "<a href='" + dirName + "download/" + filename + "'>" + showword + "</a>";
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
            document.uploadForm.url.value = "<%=dirName%>download/" + filename;
        }
    </script>
</head>
<body bgcolor="#cccccc">
<FORM METHOD=POST ACTION="upload_file.jsp?column=<%=columnID%>&doCreate=true" NAME="uploadForm"
      onsubmit="return check();" enctype="multipart/form-data">
    <table width="100%" align=center border=0>
        <tr height=20>
            <td width="20%" align=right>上传文件：</td>
            <td width="80%"><input type=file name="filename" size=30 onchange="displayURL();"></td>
        </tr>
        <tr height=80>
            <td align=right>发布主机：</td>
            <td><select name=hostID style="width:240" size=1>
                <option value="0" selected>默认发布主机</option>
                <%
                    for (int i = 0; i < siteIPList.size(); i++) {
                        FtpInfo ftpInfo = (FtpInfo) siteIPList.get(i);
                %>
                <option value="<%=ftpInfo.getID()%>"><%=ftpInfo.getIp()%>
                </option>
                <%}%>
            </select>
            </td>
        </tr>
        <tr height=20>
            <td align=right>连接地址：</td>
            <td><input type=text name=url size=40></td>
        </tr>
        <tr height=20>
            <td align=right>显示文字：</td>
            <td><input type=text name=showword size=40 value="下载附件"></td>
        </tr>
        <tr height=40>
            <td align=center colspan=2>
                <input type=submit value="  上传  ">&nbsp;&nbsp;&nbsp;&nbsp;<input type=button value="  取消  "
                                                                                 onclick="top.close();">
            </td>
        </tr>
    </table>
</FORM>

</body>
</html>

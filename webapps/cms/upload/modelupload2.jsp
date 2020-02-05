<%@ page import="java.util.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.upload.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    int cssjsdir = authToken.getCssJsDir();
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int imgflag = authToken.getImgSaveFlag();
    int tcflag = authToken.getPublishFlag();
    String fileDir = "";
    String dir = "";

    Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    if (columnID == colTree.getTreeRoot())
        fileDir = java.io.File.separator;
    else
        fileDir = colTree.getDirName(colTree, columnID);

    String baseDir = application.getRealPath("/");
    String tempDir = StringUtil.replace(fileDir, "/", java.io.File.separator);

    //去掉返回目录中的域名
    String sitenameBuf = StringUtil.replace(sitename, "_", ".");
    int posi = tempDir.indexOf(sitenameBuf);
    if (posi != -1) tempDir = tempDir.substring(posi + sitenameBuf.length());
    dir = baseDir + "sites" + java.io.File.separator + sitename;

    //获得所有发布主机列表
    IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
    List siteList = ftpsetMgr.getOtherFtpInfos(siteid);
%>

<HTML>
<HEAD>
    <TITLE>上传新模板/图像</TITLE>
    <link rel=stylesheet type="text/css" href="/webbuilder/style/global.css">
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <SCRIPT LANGUAGE="JavaScript">
        function validate()
        {
            if (uploadfile.sfilename.value == "" || uploadfile.sfilename.value == null)
            {
                alert("请选择要上传文件");
                uploadfile.sfilename.focus();
                return false;
            }
            return true;
        }
    </SCRIPT>
</HEAD>

<BODY bgcolor="#cccccc">
<form method="post" action="<%=request.getContextPath()%>/multipartformserv?dir=<%=dir%>" name=uploadfile
      enctype="multipart/form-data" onSubmit="return validate();">
    <input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>" value="/upload/modelupload2.jsp">
    <input type="hidden" name="column" value="<%=columnID%>">
    <input type="hidden" name="baseDir" value="<%=baseDir%>">
    <input type="hidden" name="fileDir" value="<%=tempDir%>">
    <input type="hidden" name="sitename" value="<%=sitename%>">
    <input type="hidden" name="siteid" value="<%=siteid%>">
    <input type="hidden" name="username" value="<%=username%>">
    <input type="hidden" name="imgflag" value="<%=imgflag%>">
    <input type="hidden" name="fromflag" value="model">
    <input type="hidden" name="cssjsdir" value="<%=cssjsdir%>">
    <input type="hidden" name="tcflag" value="<%=tcflag%>">
    <TABLE CELLSPACING=10 align=center width="100%">
        <tr>
            <td>简体文件：<INPUT type=file ID=sfilename name=sfilename size=30></td>
        </tr>
        <%if(tcflag == 1){%>
        <tr>
            <td>繁体文件：<INPUT type=file ID=tfilename name=tfilename size=30></td>
        </tr>
        <%}%>
        <tr>
            <td>选择主机：<select name="hostID" size=1 style="width:185;font-size:9pt">
                <option value="0" selected>默认发布主机</option>
                <%
                    for (int i = 0; i < siteList.size(); i++) {
                        FtpInfo ftpinfo = (FtpInfo) siteList.get(i);
                        if (ftpinfo.getStatus() == 0) {
                            String siteName = ftpinfo.getSiteName();
                            if (siteName == null || siteName.length() == 0) siteName = sitename;
                %>
                <option value="<%=ftpinfo.getID()%>"><%=siteName%>
                </option>
                <%
                        }
                    }
                %></select>
            </td>
        </tr>
        <TR height=40>
            <TD align=center>
                <input type="submit" name=ok value="  确定  " class=tine>&nbsp;&nbsp;
                <input type="button" name=Cancel onclick="window.close();" value="  取消  " class=tine>
            </TD>
        </TR>
    </TABLE>
</FORM>

</BODY>
</HTML>
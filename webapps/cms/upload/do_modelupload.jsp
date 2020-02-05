<%@ page import="java.util.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.upload.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int imgflag = authToken.getImgSaveFlag();
    int cssjsdir = authToken.getCssJsDir();
    int tcflag = authToken.getPublishFlag();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int languageType = ParamUtil.getIntParameter(request, "language", 0);

    System.out.println("do model upload cssjsdir==" + cssjsdir);

    String fileDir = "";
    String dir = "";
    String columnname = null;
    Column column = null;
    String Message = "";
    String userid = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int sitetype = authToken.getSitetype();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    Tree colTree = null;
    if (sitetype == 0 || sitetype == 2)  {                            //0表示自己创建的网站，2表示完整拷贝模板网站
        if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
            //普通用户
            List clist = new ArrayList();
            Iterator iter1 = authToken.getPermissionSet().elements();
            while (iter1.hasNext()) {
                Permission permission = (Permission) iter1.next();
                if (rightid == permission.getRightID()) {
                    clist = permission.getColumnListOnRight();
                    break;
                }
            }
            colTree = TreeManager.getInstance().getUserTree(userid, siteid, clist,rightid);
        } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
            //站点管理员
            colTree = TreeManager.getInstance().getSiteTree(siteid);
        }
    } else {
        if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
            //普通用户
            List clist = new ArrayList();
            Iterator iter1 = authToken.getPermissionSet().elements();
            while (iter1.hasNext()) {
                Permission permission = (Permission) iter1.next();
                if (rightid == permission.getRightID()) {
                    clist = permission.getColumnListOnRight();
                    break;
                }
            }
            colTree = TreeManager.getInstance().getUserTreeIncludeSampleSiteColumn(userid, siteid,samsiteid,clist);
        } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
            //站点管理员
            colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
        }
    }

    IColumnManager columnMgr = ColumnPeer.getInstance();
    if (columnID > 0) {
        column = columnMgr.getColumn(columnID);
        columnname = column.getCName();
        columnname = StringUtil.gb2iso4View(columnname);
        if (columnID == colTree.getTreeRoot())
            fileDir = java.io.File.separator;
        else
            fileDir = colTree.getDirName(colTree, columnID);
    } else {
        columnname = "程序模板页面";
        fileDir = java.io.File.separator + "program" + java.io.File.separator;
    }

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

    uploaderrormsg errormsg = (uploaderrormsg)request.getAttribute("UploadMsg");
    if (errormsg != null) {
        if (errormsg.getErrorCode() == 0)
            Message = "文件上传成功，并被发布到了WEB服务器！！！";
        else{
            Message = (errormsg.getErrorMsg()!=null)?errormsg.getErrorMsg():"";
        }

        for (int i=0; i<errormsg.getErrorPics().size(); i++) {
            String picname = (String)errormsg.getErrorPics().get(i);
        }
    }
%>

<HTML>
<HEAD>
    <TITLE>上传新模板</TITLE>
    <link rel=stylesheet type="text/css" href="/webbuilder/style/global.css">
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <script type="text/javascript">
        function validate()
        {
            if ((uploadfile.sfilename.value == "" || uploadfile.sfilename.value == null) &&
                (uploadfile.tfilename.value == "" || uploadfile.tfilename.value == null) &&
                (uploadfile.htmlfilename.value == "" || uploadfile.htmlfilename.value == null) &&
                (uploadfile.cssfilename.value == "" || uploadfile.cssfilename.value == null) &&
                (uploadfile.scriptfilename.value == "" || uploadfile.scriptfilename.value == null))
            {
                alert("请选择要上传文件");
                uploadfile.htmlfilename.focus();
                return false;
            }
            return true;
        }

        function openwin(filename) {
            window.open(filename);
        }

        function closethepage() {
        <%
            request.removeAttribute("UploadMsg");
        %>
            top.close();
        }
    </SCRIPT>
</HEAD>
<BODY>
<%=Message%>
<form method="post" action="<%=request.getContextPath()%>/multipartformserv?siteid=<%=siteid%>&sitename=<%=sitename%>" name=uploadfile
      enctype="multipart/form-data" onSubmit="return validate();">
    <input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>" value="/upload/do_modelupload.jsp">
    <input type="hidden" name="column" value="<%=columnID%>">
    <input type="hidden" name="language" value="<%=languageType%>">
    <input type="hidden" name="fileDir" value="<%=tempDir%>">
    <input type="hidden" name="username" value="<%=username%>">
    <input type="hidden" name="imgflag" value="<%=imgflag%>">
    <input type="hidden" name="cssjsdir" value="<%=cssjsdir%>">
    <input type="hidden" name="tcflag" value="<%=tcflag%>">
    <input type="hidden" name="fromflag" value="model">

    <TABLE CELLSPACING=10 align=center width="100%">
        <tr>
            <td>
                <li><b>支持可以上传的文件扩展名可以是.html、.htm、.shtml、.shtm</b><br>
                <li><b>支持的压缩文件包为ZIP格式，其他压缩格式不支持</b><br>
                <li><b>支持的图象格式包括.bmp、.jpg、.jpeg、.gif、.png</b><br>
                    <%if(imgflag == 0){%>
                <li><b>本站点设置的模板图象存储位置为“站点根目录”</b>
                    <%}else{%>
                <li><b>本站点设置的模板图象存储位置为“各级栏目目录”，请选择对应栏目后，上传模板图像</b>
                    <%}%>
            </td>
        </tr>
        <tr>
            <td>模板HTML文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=file ID=htmlfilename name=htmlfilename
                                                                          size=25></td>
        </tr>
        <tr>
            <td>CSS文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=file ID=cssfilename name=cssfilename
                                                                             size=25></td>
        </tr>
        <tr>
            <td>脚本文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=file ID=scriptfilename name=scriptfilename
                                                                      size=25></td>
        </tr>
        <tr>
            <td>模板简体图象包/图象：<INPUT type=file ID=sfilename name=sfilename size=25></td>
        </tr>
        <%if(tcflag == 1){%>
        <tr>
            <td>模板繁体图象包/图象：<INPUT type=file ID=tfilename name=tfilename size=25></td>
        </tr>
        <%}%>
        <tr>
            <td>选择图片所在目录：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="uploadcolumnname" size="35" readonly value="<%=columnname%>">
                <input type=hidden name="uploadimagescolumnid" size="20" value="<%=columnID%>" readonly></td>
        </tr>
        <tr>
            <td>选择主机：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <select name="pubsite" size=1 style="width:190;font-size:9pt">
                    <option value=<%=sitename + "_" + siteid%> selected><%=StringUtil.replace(sitename, "_", ".")%>
                    </option>
                    <%
                        for (int i = 0; i < siteList.size(); i++) {
                            SiteInfo siteinfo = (SiteInfo) siteList.get(i);
                            if (siteinfo.getBindFlag() == 1 && siteinfo.getSiteid() != siteid) {
                                String siteName = siteinfo.getDomainName();
                                if (siteName == null || siteName.length() == 0) siteName = sitename;
                    %>
                    <option value="<%=StringUtil.replace(siteName,".","_") + "_" + siteinfo.getSiteid()%>"><%=siteName%>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>

            </td>
        </tr>
        <TR height=20>
            <TD align=center>&nbsp;&nbsp;
            </TD>
        </TR>
        <TR height=30>
            <TD align=center>
                <input type="submit" name=ok value="  确定  " class=tine>&nbsp;&nbsp;
                <input type="button" name=Cancel onclick="closethepage();" value="  取消  " class=tine>
            </TD>
        </TR>
    </TABLE>
</FORM>

</BODY>
</HTML>
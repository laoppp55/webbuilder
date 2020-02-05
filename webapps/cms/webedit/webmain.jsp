<%@ page import="java.util.*,
                 java.io.*,
                 com.bizwink.cms.webedit.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.news.*"
         contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    String siteName = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    String filePath = ParamUtil.getParameter(request, "filePath");
    boolean doDelete = ParamUtil.getBooleanParameter(request, "doDelete");
    IWebEditManager webeditMgr = WebEditPeer.getInstance();

    if (doDelete)
    {
        boolean delete = ParamUtil.getBooleanParameter(request, "delete");
        webeditMgr.DeleteFile(filePath,siteName,siteID,delete);
        filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        response.sendRedirect("webmain.jsp?filePath=" + filePath);
        return;
    }

    String dirName = "";
    if (columnID > 0)
    {
        IColumnManager columnMgr = ColumnPeer.getInstance();
        Column column = columnMgr.getColumn(columnID);
        dirName = column.getDirName();
        String rootPath = application.getRealPath("/") + "sites" + File.separator + siteName;
        filePath = rootPath + StringUtil.replace(dirName, "/", File.separator);
    } else {
        dirName = filePath.substring(filePath.indexOf(siteName) + siteName.length());
        filePath = StringUtil.replace(filePath, "/", File.separator);
    }

    List filesList = webeditMgr.getFiles(filePath);
    filePath = StringUtil.replace(filePath, File.separator, "/");
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel=stylesheet type="text/css" href="../style/global.css">
    <script language="javascript">
        function Edit(filename)
        {
            window.open("editfile.jsp?filePath=<%=filePath%>" + filename,"","width=600,height=400,left=100,top=100");
        }

        function Rename(filename)
        {
            window.open("rename.jsp?filePath=<%=filePath%>" + filename,"","width=300,height=160,left=200,top=200");
        }

        function Delete(filename)
        {
            if (confirm("真的要删除吗？"))
            {
                if (confirm("要删除WEB服务器上的该文件吗？"))
                    window.location = "webmain.jsp?doDelete=true&delete=true&filePath=<%=filePath%>" + filename;
                else
                    window.location = "webmain.jsp?doDelete=true&delete=false&filePath=<%=filePath%>" + filename;
            }
        }

        function OpenIt(isDir,filename)
        {
            if (isDir)
                window.location = "webmain.jsp?filePath=<%=filePath%>" + filename + "/";
            else
                window.open("/webbuilder/sites/<%=siteName+dirName%>" + filename,"","");
        }

        function check()
        {
            var filename = uploadForm.filename.value;
            if (filename == "") {
                return false;
            } else {
                if (filename.indexOf(".") > -1) {
                    var ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
                    if (ext == "zip" || ext == "rar") {
                        if (confirm("是否需要解压该压缩包？"))
                            uploadForm.unzip.value = "1";
                    }
                }
                return true;
            }
        }

        function movein(which)
        {
            which.style.background = "#092568";
            which.style.color = "white";
        }

        function moveout(which)
        {
            which.style.background = "white";
            which.style.color = "black";
        }
    </script>
</head>

<BODY>
<table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="100%">
    <tr height=22><td colspan=7>当前文件夹为：<b><%=dirName%></b></td></tr>
    <tr height=22 class=itm bgcolor="#dddddd">
        <td align=center width="37%">文件名称</td>
        <td align=center width="14%">文件大小</td>
        <td align=center width="18%">修改时间</td>
        <td align=center width="10%">文件属性</td>
        <td align=center width="7%">编辑</td>
        <td align=center width="7%">改名</td>
        <td align=center width="7%">删除</td>
    </tr>
</table>
<table border=0 cellPadding=0 cellSpacing=0 width="100%">
    <%
        for (int i=0; i<filesList.size(); i++) {
            WebEdit webedit = (WebEdit)filesList.get(i);
            String filename = webedit.getFileName();

            String ext = "";
            if (filename.indexOf(".") > -1)
                ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

            long size = webedit.getFileSize();
            if (size > 0)
            {
                size = (size / 1000 == 0) ? size / 1000: size / 1000 + 1;
                if (size == 0) size = 1;
            }

            String icon = "menu_link_default.gif";
            if (webedit.getIsDirectory())
                icon = "menu_folder_closed.gif";
            //else if (ext.equals("htm") || ext.equals("html") || ext.equals("shtm") || ext.equals("shtml"))
            //icon = "menu_link_ie.gif";
            //else if (ext.equals("gif") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("bmp") || ext.equals("png"))
            //icon = "image.gif";
    %>
    <tr height=24 onmouseover="movein(this)" onmouseout="moveout(this)">
        <td width="37%">&nbsp;<img src="../menu-images/<%=icon%>" align=absmiddle><span onclick="OpenIt(<%=webedit.getIsDirectory()%>,'<%=filename%>')" style="cursor:hand"><%=filename%></span></td>
        <td width="14%" align=right><%=(size>0)?String.valueOf(size)+" KB":""%>&nbsp;</td>
        <td width="18%" align=center><%=webedit.getFileDate().toString().substring(0,16)%></td>
        <td width="10%" align=center><%=(webedit.getCanWrite())?"可写":"只读"%></td>
        <%if (SecurityCheck.hasPermission(authToken,54) || SecurityCheck.hasPermission(authToken,55)){%>
        <td width="7%" align=center>
            <%if (!webedit.getIsDirectory() && (ext.equals("htm") || ext.equals("html") || ext.equals("shtm") || ext.equals("shtml") || ext.equals("jsp") || ext.equals("asp") || ext.equals("php") || ext.equals("txt"))){%>
            <a href="javascript:Edit('<%=filename%>');"><img src="../images/edit.gif" align="bottom" border=0></a>
            <%}%>
        </td>
        <td width="7%" align=center><a href="javascript:Rename('<%=filename%>');"><img src="../images/edit.gif" align="bottom" border=0></a></td>
        <%}else{%>
        <td width="7%" align=center></td>
        <td width="7%" align=center></td>
        <%}%>
        <td width="7%" align=center>
            <%if (!webedit.getIsDirectory() && (SecurityCheck.hasPermission(authToken,54) || SecurityCheck.hasPermission(authToken,56))){%>
            <a href="javascript:Delete('<%=filename%>');"><img src="../images/del.gif" align="bottom" border=0></a>
            <%}%>
        </td>
    </tr>
    <%}%>
</table>
<br>
<form name=uploadForm action="upload.jsp" method="post" enctype="multipart/form-data" onsubmit="javascript:return check();">
    <input type=hidden name=unzip value="0">
    <input type=hidden name=path value="<%=filePath%>">
    <table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="100%">
        <tr height=22><td align=center><b>文件上传</b></td></tr>
        <tr height=30 class=itm bgcolor="#dddddd">
            <td align=center>
                选择文件：<input type=file name=filename size=30 class=tine>&nbsp;&nbsp;
                <input class=tine type=submit value="  上传  ">
            </td>
        </tr>
    </table>
</form>
<br>
</BODY>
</html>
<%@ page import="com.jspsmart.upload.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.upload.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"
%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int doupload = ParamUtil.getIntParameter(request,"doupload",0);

    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request,"column",0);

    String baseDir = application.getRealPath("/");
    baseDir = baseDir + java.io.File.separator + "sites"+java.io.File.separator+sitename;

    if(doupload==1){
        SmartUpload upload=new SmartUpload();
        try{
            upload.initialize(this.getServletConfig(), request, response);
            upload.upload();
            columnID = Integer.parseInt(upload.getRequest().getParameter("column"));
        }catch(Exception e){
            response.sendRedirect("../article/articles.jsp?column="+columnID);
        }
        IColumnManager columnManager = ColumnPeer.getInstance();
        Column column = columnManager.getColumn(columnID);
        String dirname = column.getDirName();
        com.jspsmart.upload.Files uploadfiles = upload.getFiles();
        if(uploadfiles.getCount()>0){
            com.jspsmart.upload.File tempfile = uploadfiles.getFile(0);
            if(!tempfile.isMissing()){
                java.io.File tfile = new java.io.File(baseDir+java.io.File.separator+"_TEMPUPFILE");
                if(!tfile.exists())tfile.mkdirs();
                String saveit = tfile.getPath()+java.io.File.separator+tempfile.getFileName();
                tempfile.saveAs(saveit);
                UploadPicture upic = new UploadPicture();
                upic.doUploadPics(saveit,baseDir+dirname,columnID,siteid,authToken.getUserID());

                out.println("<script language=javascript>");
                out.println("window.close();");
                out.println("opener.history.go(0);");
                out.println("</script>");
            }
        }
    }
%>

<html>
<head>
    <title></title>
    <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <SCRIPT LANGUAGE=JavaScript>
        function check()
        {
            var fname = uploadfile.sfilename.value;
            if (fname == "")
            {
                alert("请选择文件！");
                return false;
            }
            else
            {
                if (fname.toLowerCase().lastIndexOf(".zip") == -1) {
                    alert("必须上传ZIP压缩的文件包！");
                    return false;
                } else
                    return true;
            }
        }
    </SCRIPT>
</head>

<body bgcolor="#cccccc">


<%
    String[][] titlebars = {
            { "文件管理", "index.jsp" },
            { "批量图片上传", "" }
    };
    String[][] operations = {
    };
%>
<%@ include file="../inc/titlebar.jsp" %>
<form method="post" action="uploadpics.jsp?doupload=1" name=uploadfile enctype="multipart/form-data" onsubmit="javascript:return check();">
    <input type="hidden" name=column value="<%=columnID%>">
    <input type="hidden" name="status" value="save">
    <input type="hidden" name="baseDir" value="<%=baseDir%>">
    <input type="hidden" name="sitename" value="<%=sitename%>">
    <input type="hidden" name="siteid" value="<%=siteid%>">
    <input type="hidden" name="username" value="<%=username%>">
    <input type="hidden" name="fromflag" value="file">

    <table width="100%" border="0" align="center">
        <tr>
            <td colspan="2" height="36">图片文件：<input type=file id="sfilename" size=30 name="picfile"></td>
        </tr>
        <tr><td>注:请上传图片的 <font color=red><b>ZIP</b></font> 压缩包文件</td></tr>
        <tr>
            <td colspan="2" align="center" height=60>
                <input type="submit" value="  上传  " class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="  取消  " class=tine onclick="window.close();">
            </td>
        </tr>
    </table>
</form>

</body>
</html>

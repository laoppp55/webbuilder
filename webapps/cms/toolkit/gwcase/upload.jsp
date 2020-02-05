<%@ page import="java.io.*,
                 java.util.*,
                 java.text.*,
                 com.jspsmart.upload.*,
                 com.xml.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.upload.*,
                 com.bizwink.calendar.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk"
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

    String baseDir = application.getRealPath("/");
    baseDir = baseDir + java.io.File.separator + "sites"+java.io.File.separator+sitename;
    IFormManager formMgr = FormPeer.getInstance();
    if(doupload==1){
        SmartUpload upload=null;
        try{
            upload = new SmartUpload();
            upload.initialize(this.getServletConfig(), request, response);
            upload.upload();
            com.jspsmart.upload.Files uploadfiles = upload.getFiles();
            if(uploadfiles.getCount()>0){
                com.jspsmart.upload.File tempfile = uploadfiles.getFile(0);
                if(!tempfile.isMissing()){
                    java.io.File tfile = new java.io.File(baseDir+java.io.File.separator);
                    if(!tfile.exists())tfile.mkdirs();
                    System.out.println("filename=" + tempfile.getFileName());
                    String saveit = tfile.getPath()+java.io.File.separator+tempfile.getFileName();
                    tempfile.saveAs(saveit);
                    formMgr.transferBjrabData(saveit,"","",username);
                    out.println("<script language=javascript>");
                    out.println("window.close();");
                    out.println("opener.history.go(0);");
                    out.println("</script>");
                }
            }
        }catch(Exception e){
        }
    }
%>

<html>
<head>
    <title></title>
    <link REL="stylesheet" TYPE="text/css" HREF="../../style/global.css">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <SCRIPT LANGUAGE=JavaScript>
        function check()
        {
            var fname = uploadfile.sfilename.value;
            if (fname.toLowerCase().lastIndexOf(".mdb") == -1) {
                alert("上传文件必须以MDB后缀名结尾的ACCESS文件！");
                return false;
            }
            else if (fname == "")
            {
                alert("请选择文件！");
                return false;
            }
            else
            {
                var objXmlc;
                if (window.ActiveXObject){
                    objXmlc = new ActiveXObject("Microsoft.XMLHTTP");
                }else if (window.XMLHttpRequest){
                    objXmlc = new XMLHttpRequest();
                }
                objXmlc.open("POST", "checkExistFile.jsp?filename="+fname, false);
                objXmlc.send(null);
                var res = objXmlc.responseText;
                if(res==0){
                    return true;
                } else {
                    alert("选择上传的文件名已经存在，请修改上传文件的文件名！！！");
                    return false;
                }
            }
        }
    </SCRIPT>
</head>

<body bgcolor="#cccccc">


<%
    String[][] titlebars = {
            { "上传ACCESS文件", "" }
    };
    String[][] operations = {
    };
%>
<%@ include file="../../inc/titlebar.jsp" %>
<form method="post" action="upload.jsp?doupload=1" name=uploadfile enctype="multipart/form-data" onsubmit="javascript:return check();">
    <input type="hidden" name="status" value="save">
    <input type="hidden" name="baseDir" value="<%=baseDir%>">
    <input type="hidden" name="sitename" value="<%=sitename%>">
    <input type="hidden" name="siteid" value="<%=siteid%>">
    <input type="hidden" name="username" value="<%=username%>">
    <input type="hidden" name="fromflag" value="file">

    <table width="100%" border="0" align="center">
        <tr>
            <td colspan="2" height="36">请选择ACCESS文件：<input type=file id="sfilename" size=30 name="picfile"></td>
        </tr>
        <tr>
            <td colspan="2" height="36">请输入文件描述&nbsp;&nbsp;：<input type="text" id="summary" size=30 name="brief"></td>
        </tr>
        <tr><td>注:请上传的文件必须是 <font color=red><b>ACCESS文件</b></font> </td></tr>
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

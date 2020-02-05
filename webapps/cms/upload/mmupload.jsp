<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);
    String dirName = column.getDirName();
%>
<html>
<head>
    <title>上传视频文件</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
    <link rel="stylesheet" type="text/css" href="uploadify/uploadify.css">
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script src="uploadify/jquery.uploadify.js" type="text/javascript"></script>

    <style>
        body { padding: 30px }
        form { display: block; margin: 20px auto; background: #eee; border-radius: 10px; padding: 15px }
        .progress { position:relative; width:400px; border: 1px solid #ddd; padding: 1px; border-radius: 3px; }
        .bar { background-color: #B4F5B4; width:0%; height:20px; border-radius: 3px; }
        .percent { position:absolute; display:inline-block; top:3px; left:48%; }
    </style>
</head>

<body bgcolor="#cccccc">
<table width="100%" align=center border=0>
    <tr><td colspan="2">&nbsp;&nbsp;&nbsp;请选择多媒体文件</td></tr>
    <tr><td>&nbsp;</td></tr>
    <tr height=20>
        <td width="20%" align=right></td>
        <td width="80%">
            <div id="queue"></div>
            <input id="file_upload" name="file_upload" type="file" multiple="true">
        </td>
    </tr>
</table>
</body>
<script language=javascript>
    $(function() {
        $('#file_upload').uploadify({
            'swf'          : 'uploadify/uploadify.swf',
            'uploader'    : 'saveVideo.jsp?doCreate=true',
            //'buttonImg'   : 'uploadify/upload.png',      //浏览按钮的图片的路径 。
            'buttonText'  : ' 选择文件',
            'wmode'       : 'transparent',                 //设置该项为transparent 可以使浏览按钮的flash背景文件透明，并且flash文件会被置为页面的最高层。
            'cancelImg': 'uploadify/cancel.png',
            'fileDataName':'file_upload',
            'height'      : 40,                              //设置浏览按钮的高度
            'width'       : 80,                               //设置浏览按钮的宽度
            //'onUploadComplete' : function(file) {
            //alert('The file ' + file.name + ' finished processing.');
            //window.opener.top.document.createForm.mediaid.value='hello';
            //window.opener.top.document.createForm.mediafilename.value=window.opener.top.document.createForm.mediafilename.value + '" + newname + ":" + filename + "|';"
            //top.close();
            //},
            'onUploadSuccess':function(file, data, response) {
                //alert('The file ' + file.name + ' finished processing.' + "==" + data);
                var posi = data.indexOf(":");
                if (posi>-1) {
                    var flv_filename = data.substring(0, posi);
                    window.opener.top.document.createForm.mediaid.value = flv_filename;
                    window.opener.top.document.createForm.mediafilename.value = window.opener.top.document.createForm.mediafilename.value + data + "|";
                    window.opener.top.CreateElementForEditor("object","<%=sitename%>","<%=dirName%>",flv_filename);
                }
                top.close();
            }
        });
    });
</script>
</html>

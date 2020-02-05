<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page contentType="text/html;charset=utf-8" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);
    String dirName = column.getDirName();
%>

<html>
<head>
    <title>文件上传</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/jquery.form.js"></script>
    <script language=javascript>
        $(function () {
            $("#btnsubmit").click(function () {
                var filename = $("#fileToUpload").val();
                if (filename == "") {
                    alert("请选择上传文件！");
                    return false;
                }

                if (filename.indexOf(".doc") == -1 && filename.indexOf(".docx")==-1) {
                    alert("上传文件必须是WORD文件！");
                    return false;
                }

                var myData = {
                    "column": "<%=columnID%>"
                };

                var getvalflag = false;
                var retval = "";
                var ajaxFormOption = {
                    type: "post",                                                     //提交方式
                    dataType: "html",                                                //数据类型
                    data: myData,                                                      //自定义数据参数，视情况添加
                    url: "../upload/ConvertWordToHtml.jsp?doCreate=true",       //请求url
                    success: function (data) {                                         //提交成功的回调函数
                        var oEditor = window.parent.opener.top.SetHTMLToEditor(data);
                        window.close();
                    }
                };

                //不需要submit按钮，可以是任何元素的click事件
                $("#form1").ajaxSubmit(ajaxFormOption);

                /*if (getvalflag == false)
                    return false;
                else {
                    window.returnValue=retval;
                    window.close();
                }*/
            });
        });
    </script>
</head>
<body bgcolor="#cccccc">
<form enctype="multipart/form-data" id="form1">
    <table width="100%" align=center border=0>
        <tr height=20>
            <td width="20%" align=right>上传文件：</td>
            <td width="80%"><input type=file id="fileToUpload" name="file1" size=30></td>
        </tr>
        <tr height=40>
            <td align=center colspan=2>
                <input type="button" id="btnsubmit" value="  上传  ">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="btncancel" value="  取消  "   onclick="top.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>

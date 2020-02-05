<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    int siteid = ParamUtil.getIntParameter(request, "site", 0);
    int currentpage = ParamUtil.getIntParameter(request, "pageno", 0);
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int doSearchFlag = ParamUtil.getIntParameter(request, "searchflag", 0);
%>
<html>
<head>
    <title>上传网站首页文件</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="Javascript">
        function f(theimg) {
            var val = theimg.value;
            var ext = val.substring(val.lastIndexOf(".")).toLowerCase();
            var validate = false;

            if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp") {
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = theimg.value;
                d.style.width = d.offsetWidth;
                d.style.height = d.offsetHeight;
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = 'scale';
                validate = true;
            }
            else if (ext == ".swf")
            {
                validate = true;
            }
            else
            {
                if (!validate)
                {
                    alert("只能上传图像及FLASH文件！");
                }
            }
        }

        function validate()
        {
            if (document.form1.filename.value == "")
            {
                alert("请选择文件！");
                return false;
            }
            return true;
        }

        function cal() {
            top.close();
        }
    </script>
</head>

<body bgcolor="#cccccc">
<form name="form1" action="do_uploadsitepic.jsp" method="post" enctype="multipart/form-data" onsubmit="return validate();">
    <input type="hidden" name="doUpload" value="1">
    <input type="hidden" name="site" value="<%=siteid%>">
    <input type="hidden" name="pageno" value="<%=currentpage%>">
    <input type="hidden" name="range" value="<%=range%>">
    <input type="hidden" name="searchflag" value="<%=doSearchFlag%>">

    <table align="center" width="90%" border=0>
        <tr height=30>
            <td>选择文件：<input type=file name="filename" size=31 onpropertychange="f(this)"></td>
        </tr>
        <tr height=30>
            <td>图片描述：<input type=text name="notes" size=31></td>
        </tr>
        <tr height=35>
            <td align=left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="submit" value="  上传  " class=tine>&nbsp;&nbsp;
                <input type="button" value="  取消  " class=tine onClick="cal();">
            </td>
        </tr>
        <tr>
            <td valign="top" bgcolor="#ffffff">
                <!--<iframe src="showImage.html" width="400" marginwidth="0" height="280" marginheight="0" scrolling="auto" frameborder="1" name=frm></iframe>-->
                <h1 id=d
                    style="border:0px solid black;filter : progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);WIDTH: 400px; HEIGHT: 280px">
                </h1>
            </td>
        </tr>
    </table>

</form>

</body>
</html>

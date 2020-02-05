<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    ISiteInfoManager  siteinfo = SiteInfoPeer.getInstance();
   
    String imgstr = ParamUtil.getParameter(request, "imgstr");
    String filedname = ParamUtil.getParameter(request, "fname");
    imgstr = StringUtil.replace(imgstr, "'", "\"");
    String imgsize = ParamUtil.getParameter(request,"imagesize");
    if (imgsize != null) {
        out.println("<script>");
        out.println("window.returnValue='" + imgsize + "';");
        out.println("top.close();");
        out.println("</script>");
        return;
    }
%>

<html>
<head>
    <title>选择图片的大小</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript">
        function dosubmit(form) {
            form.action = "selectimagesize.jsp";
            form.submit();
        }
    </script>
</head>

<body>
<form action="selectimagesize.jsp" method="post" name="sizeform">
    <input type="hidden" name="fname" value="<%=filedname%>">
    <table boder=0>
        <tr>
            <td>
                请选择图片的大小：
                <select name="imagesize" onchange="javascript:dosubmit(sizeform);">
                    <option value="" selected>请选择</option>
                    <option value="0X0">原图</option>
                    <option value="150X120">150X120</option>
                    <option value="300X240">300X240</option>
                    <option value="450X360">450X360</option>
                    <option value="600X480">600X480</option>
                    <option value="750X600">750X600</option>
                    <option value="900X750">900X750</option>
                    <option value="1050X900">1050X900</option>
                    <option value="1200X960">1200X960</option>
                </select>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
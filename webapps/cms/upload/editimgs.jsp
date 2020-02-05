<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String src = ParamUtil.getParameter(request, "src");
    String width = ParamUtil.getParameter(request, "width");
    String height = ParamUtil.getParameter(request, "height");
    String vspace = ParamUtil.getParameter(request, "vspace");
    String hspace = ParamUtil.getParameter(request, "hspace");
    String border = ParamUtil.getParameter(request, "border");
    String align = ParamUtil.getParameter(request, "align");
    String alt = ParamUtil.getParameter(request, "alt");
%>

<html>
<head>
    <title>图片修改</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>
<table boder=0 width="600">
    <tr>
        <td>
            <iframe src='editimg.jsp?src=<%=src%>&width=<%=width%>&height=<%=height%>&vspace=<%=vspace%>&hspace=<%=hspace%>&border=<%=border%>&align=<%=align%>&alt=<%=alt%>'
                    width="600" marginwidth="0" height="380"
                    marginheight="0" scrolling="no" frameborder="0"></iframe>
        </td>
    </tr>
</table>
</body>
</html>
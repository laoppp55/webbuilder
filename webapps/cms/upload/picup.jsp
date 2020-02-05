<%@page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String columnIDstr = (String)session.getAttribute("createtemplate_columnid");

    int columnID = 0;
    if((columnIDstr != null)&&(!columnIDstr.equals("null"))&&(!columnIDstr.equals("")))
        columnID = Integer.parseInt(columnIDstr);
    int template_or_article_flag = ParamUtil.getIntParameter(request, "template_or_article", 0);
%>

<html>
<head>
    <title>上载图片及flash</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<frameset border=0 frameborder=0 framespacing=0>
    <frame src="/webbuilder/upload/picupload.jsp?column=<%=columnID%>&template_or_article=<%=template_or_article_flag%>" name=cmsright scrolling=auto marginheight=0 marginwidth=5 noresize>
</frameset>
</html>
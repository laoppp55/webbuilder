<%@ page import="java.sql.*,
          java.io.*,
          com.bizwink.cms.modelManager.*,
          com.bizwink.cms.server.*,
          com.bizwink.cms.security.*,
          com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if( authToken == null ) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int id = ParamUtil.getIntParameter(request, "id", 0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int rightid  = ParamUtil.getIntParameter(request, "rightid", 0);

    IModelManager  modelManager = ModelPeer.getInstance();
    modelManager.updatecancle(0, id);
%>

<html>
<head>
    <script language=javascript>
        function closeme(columnid) {
            //alert(columnid);
            if (columnid > 0)
                opener.location.href = "../newtemplate/templates.jsp?column=<%=columnID%>&rightid=<%=rightid%>";
            else
                opener.location.href = "../newtemplate/templatesforprogram.jsp?column=<%=columnID%>&rightid=<%=rightid%>";
            window.close();
        }
    </script>
</head>
<Body onload="javascript:closeme(<%=columnID%>)">
</body>
</html>
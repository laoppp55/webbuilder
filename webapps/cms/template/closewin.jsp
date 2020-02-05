<%@ page import="java.sql.*,
          com.bizwink.cms.modelManager.*,
          com.bizwink.cms.security.*,
          com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
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
            if (columnid > 0) {
                window.opener.templatesInfoList(columnid);
            }else
                window.opener.templatesInfoList(columnid);
                //opener.location.href = "../template/templatesforprogram.jsp?column=<%=columnID%>&rightid=<%=rightid%>";
            window.close();
        }
    </script>
</head>
<Body onload="javascript:closeme(<%=columnID%>)">
</body>
</html>
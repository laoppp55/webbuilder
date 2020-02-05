<%@ page import = "java.util.*,
                   com.bizwink.cms.util.*,
                   com.bizwink.cms.audit.*,
                   com.bizwink.cms.security.*"
         contentType="text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    IAuditManager auditMgr = AuditPeer.getInstance();
    String userID = authToken.getUserID();
    int siteID = authToken.getSiteID();

    int backCount = 0;
    int auditCount = 0;
    if (!userID.equalsIgnoreCase("admin"))
    {
        //退稿
        List backList = auditMgr.getBack_Articles(userID);
        backCount = backList.size();

        //审核
        String user_ID = "[" + userID + "]";
        List auditList = auditMgr.getArticles_NeedAudit(user_ID, siteID);
        auditCount = auditList.size();
    }
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href=style/global.css>

    <script language="JavaScript">
        <!--
        /*function window_open()
        {
            //1:有退稿
            //2:有审核
            //3:都有
            //0:都无
            var param = 0;

        if (<%=backCount%> != 0) {
            if (<%=auditCount%> != 0)
            param = 3;
        else
            param = 1;
        }
        else
            {
                if (<%=auditCount%> != 0)
                param = 2;
            }

            if (param != 0)
            {
                window.open("inc/note.jsp?param="+param,"note","width=400,height=400,left=200,top=150,scrollbars=yes");
            }
        } */

        function MM_openBrWindow(theURL,winName,features) { //v2.0
            window.open(theURL,winName,features);
        }

        function post() {
            //window.open("openemail.jsp","openemail","width=800,height=600,left=20,top=10,scrollbars=yes resize=yes");
        }
        //-->
    </script>

</head>

<BODY LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8 onload="javascript:post();">
<!--body link=#000099 alink=#cc0000 vlink=#000099 TOMARGIN=8-->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="#000000">
        <td height="2"></td>
    </tr>
</table>

<%
    String[][] titlebars = {
            { "首页", "" }
    };
    String[][] operations=null;
%>
<%@ include file="inc/titlebar.jsp" %>
</body>
</html>
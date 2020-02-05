<%@ page import="com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String itemText = ParamUtil.getParameter(request,"item");
    int row = ParamUtil.getIntParameter(request,"row",0);
    String cname = itemText;
    int p = 0;
    if ((p=itemText.indexOf("-getAllSubArticle")) > -1) {
        cname = itemText.substring(0, p);
    }
%>

<html>
<head>
    <title>定义文章列表属性</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type="text/css" href="../style/global.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <SCRIPT LANGUAGE=JavaScript>
        function doSave() {
            var attrVal = "<%=itemText%>";
            var row = <%=row%>;
            var val = 0;

            //是否显示子栏目列表  0--显示  1--不显示
            $('input:radio:checked').each(function(){
                val = $(this).val();
            });

            if (val == 0 && attrVal.indexOf("-getAllSubArticle") == -1)
                attrVal = attrVal + "-getAllSubArticle";
            else if (val == 1 && attrVal.indexOf("-getAllSubArticle") > -1)
                attrVal = "<%=cname%>";

            window.opener.setupTheItem(row,attrVal);
            window.close();
        }
    </SCRIPT>
</head>

<BODY bgcolor="#cccccc">
<table width="100%" border="0" align="center">
    <tr height="20">
        <td>栏目名称：<%=cname%></td>
    </tr>
    <tr height="25">
        <td>是否获取所有子栏目的文章：
            <input type="radio" name="subCList" id="selid" value="0">是&nbsp;&nbsp;
            <input type="radio" name="subCList" id="selid" value="1">否
        </td>
    </tr>
    <tr height="30">
        <td align="center">
            <input type="button" name="Ok" value=" 确定 " class=tine onclick="javascript:doSave();">&nbsp;&nbsp;
            <input type="button" name="Cancel" value=" 取消 " class=tine onclick="window.close();">
        </td>
    </tr>
</table>

</BODY>
</html>

<SCRIPT LANGUAGE=JavaScript>
    var attrVal = "<%=itemText%>";
    if (attrVal.indexOf("-getAllSubArticle") > -1)
        subCList[0].checked = true;
    else
        subCList[1].checked = true;
</SCRIPT>
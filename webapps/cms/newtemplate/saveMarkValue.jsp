<%@ page import="com.bizwink.cms.util.*,
         com.bizwink.cms.security.*"
         contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.server.CmsServer" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteID = authToken.getSiteID();

    int columnID = ParamUtil.getIntParameter(request, "column",0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    if (doCreate == true) {
        String content = ParamUtil.getParameter(request, "content");
        System.out.println("columnId=" + columnID + "====siteID=" + siteID);
        int posi = -1;
        while ((posi = content.toLowerCase().indexOf("<cmstextarea")) > -1)
            content = content.substring(0, posi) + "<textarea" + content.substring(posi + 12);
        while ((posi = content.toLowerCase().indexOf("</cmstextarea>")) > -1)
            content = content.substring(0, posi) + "</textarea>" + content.substring(posi + 14);
        String cname = ParamUtil.getParameter(request, "cname");
        String notes = ParamUtil.getParameter(request, "notes");
        IMarkManager markMgr = markPeer.getInstance();
        mark mark = new mark();
        mark.setColumnID(columnID);
        mark.setSiteID(siteID);
        mark.setContent(content);
        mark.setChinesename(cname);
        mark.setNotes(notes);
        mark.setInnerHTMLFlag(1);
        mark.setFormatFileNum(0);
        mark.setRelatedColumnID("");
        mark.setMarkType(7);
        int markid = markMgr.Create(mark);
        out.println("<script type=\"text/javascript\">");
        out.println("opener.writeHtmlcodeMark(" + markid + ");");
        out.println("window.close()");
        out.println("</script>");
        return;
    }
%>
<html>
<head>
<title>生成HTML代码标记</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type="text/css" href="../style/editor.css">
<script type="text/javascript" src="../toolbars/btnclick<%=CmsServer.lang%>.js"></script>
<script type="text/javascript" src="../toolbars/dhtmled.js"></script>
<script type="text/javascript" src="../fckeditor/fckeditor.js"></script>
<script type="text/javascript">
function tijiao()
{
    var cname = document.getElementById("cname").value;
    if (cname == "")
    {
        alert("标记中文名称不能为空");
        return false;
    }
    return true;
}
</script>
</head>
<body>
<table width="100%" border=0 cellspacing=0 cellpadding=0>
    <form name=htmlmark method="post" action="saveMarkValue.jsp"  onsubmit="return tijiao();">
        <tr>
            <td>
                中文名称：<input name="cname" size=25 value="">
                &nbsp;&nbsp;&nbsp;&nbsp;注释：<input name="notes" size=50 value="">
            </td>
            <td>
                <input type=submit name=save value=提交>
                &nbsp;&nbsp;&nbsp;&nbsp;<input type=button name=cancel value=关闭窗口 onclick="javascript:window.close()">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="hidden" name="doCreate" value="true">
                <input type="hidden" name="column" value="<%=columnID%>">
                <textarea rows=30 cols=120 name=content wrap=virtual   size=60></textarea>
                <script type="text/javascript">
                    htmlmark.content.value = opener.parent.window.document.createForm.hcode.value;
                    opener.parent.window.document.createForm.hcode.value="";
                    var oFCKeditor = new FCKeditor('content') ;
                    oFCKeditor.BasePath = "../fckeditor/";
                    oFCKeditor.Height = 530;
                    oFCKeditor.ToolbarSet = "Default";
                    oFCKeditor.ReplaceTextarea();
                </script>
            </td>
        </tr>
    </form>
</table>
</body>
</html>

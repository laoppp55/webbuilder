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

    IMarkManager markMgr = markPeer.getInstance();
    int columnID = ParamUtil.getIntParameter(request, "column",0);
    int markID = ParamUtil.getIntParameter(request, "mark",0);
    String content = null;
    String cname = null;
    String notes = null;
    int posi = -1;
    if (markID > 0) {
        mark mymark = markMgr.getAMark(markID);
        cname = mymark.getChineseName();
        notes = mymark.getNotes();
        content = mymark.getContent();
        while ((posi = content.toLowerCase().indexOf("<textarea")) > -1)
            content = content.substring(0, posi) + "<cmstextarea" + content.substring(posi + 9);
        while ((posi = content.toLowerCase().indexOf("</textarea>")) > -1)
            content = content.substring(0, posi) + "</cmstextarea>" + content.substring(posi + 11);
        content = StringUtil.gb2iso4View(content);
    }

    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    if (doUpdate == true) {
        content = ParamUtil.getParameter(request, "content");
        System.out.println("columnId=" + columnID + "====siteID=" + siteID);
        posi = -1;
        while ((posi = content.toLowerCase().indexOf("<cmstextarea")) > -1)
            content = content.substring(0, posi) + "<textarea" + content.substring(posi + 12);
        while ((posi = content.toLowerCase().indexOf("</cmstextarea>")) > -1)
            content = content.substring(0, posi) + "</textarea>" + content.substring(posi + 14);
        cname = ParamUtil.getParameter(request, "cname");
        notes = ParamUtil.getParameter(request, "notes");
        mark mark = new mark();
        mark.setID(markID);
        mark.setColumnID(columnID);
        mark.setSiteID(siteID);
        mark.setContent(content);
        mark.setChinesename(cname);
        mark.setNotes(notes);
        mark.setInnerHTMLFlag(1);
        mark.setFormatFileNum(0);
        mark.setRelatedColumnID("");
        mark.setMarkType(7);
        markMgr.Update(mark);
        out.println("<script type=\"text/javascript\">");
        out.println("window.close()");
        out.println("</script>");
        return;
    }
%>
<html>
<head>
    <title>编辑HTML代码标记</title>
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
    <form name=htmlmark method="post" action="editHtmlcodeMark.jsp"  onsubmit="return tijiao();">
        <tr>
            <td>
                中文名称：<input name="cname" size=25 value="<%=(cname==null)?"":cname%>">
                &nbsp;&nbsp;&nbsp;&nbsp;注释：<input name="notes" size=50 value="<%=(notes==null)?"":notes%>">
            </td>
            <td>
                <input type=submit name=save value=提交>
                &nbsp;&nbsp;&nbsp;&nbsp;<input type=button name=cancel value=关闭窗口 onclick="javascript:window.close()">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="hidden" name="doUpdate" value="true">
                <input type="hidden" name="column" value="<%=columnID%>">
                <input type="hidden" name="mark" value="<%=markID%>">
                <textarea rows=30 cols=120 name=content wrap=virtual   size=60><%=content%></textarea>
                <script type="text/javascript">
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

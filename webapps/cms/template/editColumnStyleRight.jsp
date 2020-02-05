<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.viewFileManager.IViewFileManager" %>
<%@ page import="com.bizwink.cms.viewFileManager.ViewFile" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.viewFileManager.viewFilePeer" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.extendAttr.ExtendAttrPeer" %>
<%@ page import="com.bizwink.cms.extendAttr.IExtendAttrManager" %>
<%@ page contentType="text/html;charset=utf-8" %>

<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String cname = "列表样式文件";
    String headstr = "";
    String content = "";
    String tailstr = "";

    int siteID = authToken.getSiteID();
    int styleID = ParamUtil.getIntParameter(request, "ID", 0);
    int type = ParamUtil.getIntParameter(request, "type", 0);
    String from = ParamUtil.getParameter(request,"from");
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    ViewFile viewfile = new ViewFile();
    boolean actionFlag = false;
    int newID = 0;

    if (doCreate) {
        cname = ParamUtil.getParameter(request, "cname");
        content = ParamUtil.getParameter(request, "content");
        headstr = ParamUtil.getParameter(request, "headstr");
        tailstr = ParamUtil.getParameter(request, "tailstr");
        if (headstr == null) headstr = "";
        if (tailstr == null) tailstr = "";

        viewfile.setSiteID(siteID);
        viewfile.setType(type);
        viewfile.setChineseName(cname);
        viewfile.setEditor(authToken.getUserID());
        viewfile.setNotes("");
        viewfile.setContent(headstr.trim() + "<!--ROW-->" + content.trim() + "<!--ROW-->" + tailstr.trim());
        viewfile.setID(styleID);

        if (styleID > 0)
            viewfileMgr.update(viewfile);
        else
            newID = viewfileMgr.create(viewfile);

        actionFlag = true;

        /*String viewer = request.getHeader("user-agent");
        out.println("<script language=javascript>");

        if (viewer.toLowerCase().indexOf("gecko") == -1) {
            out.println("parent.window.returnValue = \"" + newID + "," + cname + "\";");
            out.println("parent.window.close();");
        } else {
            out.println("window.parent.opener.document.getElementById(\"listType\").options.add(new Option('" + cname + "'," + newID + "));");
            out.println("window.parent.opener.document.getElementById(\"listType\").options[window.parent.opener.document.getElementById(\"listType\").length-1].selected=true;");
            out.println("top.close();");
        }
        out.println("</script>");
        return;
        */

    }

    if (styleID > 0) {
        viewfile = viewfileMgr.getAViewFile(styleID);
        cname = viewfile.getChineseName();
        String str = viewfile.getContent();
        cname = StringUtil.gb2iso4View(cname);
        str = StringUtil.gb2iso4View(str);

        headstr = str.substring(0, str.indexOf("<!--ROW-->")).trim();
        content = str.substring(str.indexOf("<!--ROW-->") + 10, str.lastIndexOf("<!--ROW-->")).trim();
        tailstr = str.substring(str.lastIndexOf("<!--ROW-->") + 10).trim();
    }

    String marks = "";
    if (type == 6) {
        marks = marks + "<option value='<A HREF=<" + "%%COLUMNURL%%" + "> target=_blank><" + "%%COLUMNNAME%%" + "></A>'>栏目名称</option>";
        marks = marks + "<option value='<" + "%%COLUMNDESC%%" + ">'>栏目描述</option>";
        marks = marks + "<option value='<" + "%%COLUMNID%%" + ">'>栏目ID</option>";
        marks = marks + "<option value='<" + "%%sitename%%" + ">'>站点名称</option>";
        marks = marks + "<option value='<!--BEGIN-->'>分列开始标记</option>";
        marks = marks + "<option value='<!--END-->'>分列结束标记</option>";
    } else {
        IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
        List attrList = extendMgr.getAttrForStyle(0);
        for (int i = 0; i < attrList.size(); i++) {
            String temp = (String) attrList.get(i);
            String ename = temp.substring(0, temp.indexOf(","));
            String cname2 = temp.substring(temp.indexOf(",") + 1);
            marks = marks + "<option value='" + ename + "'>" + cname2 + "</option>";
        }
    }
%>

<html>
<head>
    <title>选择列表样式</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script language=javascript>
        $(document).ready(function() {
            var actionflag = <%=actionFlag%>;
            var cname = '<%=cname%>';
            var newID = <%=newID%>;
            var styleID = <%=styleID%>;
            var type = <%=type%>;
            var from = "<%=from%>"
            if (actionflag == true) {
                if (from === 'system') {                       //对样式文件的修改来自于系统设置的“样式文件”
                    //window.parent.opener.location = "../member/listStyle.jsp?type="+type;
                    window.parent.opener.styleInfoList(type);
                    top.close();
                } else {
                    if (styleID === 0)
                        window.parent.opener.addNewOption(type, cname, newID);
                    else
                        window.parent.opener.updateStyleOption(type, cname, styleID);
                    top.close();
                }
            }
        })

        function SelectMarkName()
        {
            var i = document.editForm.markname.selectedIndex;
            var str = document.editForm.markname.options[i].value;
            if (str != "" && str != "1")
                insertAtCaret(document.editForm.content, str);
            document.editForm.markname.options[0].selected = true;
        }

        function storeCaret(textEl) {
            if (textEl.createTextRange)
                textEl.caretPos = document.selection.createRange().duplicate();
        }

        function insertAtCaret(textObj, textFeildValue) {
            if (document.all) {
                if (textObj.createTextRange && textObj.caretPos) {
                    var caretPos = textObj.caretPos;
                    caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == '   '   ?textFeildValue + '   '   :   textFeildValue;
                } else {
                    textObj.value = textFeildValue;
                }
            } else {
                if (textObj.setSelectionRange) {
                    var rangeStart = textObj.selectionStart;
                    var rangeEnd = textObj.selectionEnd;
                    var tempStr1 = textObj.value.substring(0, rangeStart);
                    var tempStr2 = textObj.value.substring(rangeEnd);
                    textObj.value = tempStr1 + textFeildValue + tempStr2;
                } else {
                    alert("This   version   of   Mozilla   based   browser   does   not   support   setSelectionRange");
                }
            }
        }

        function save() {
            if (document.editForm.cname.value == "") {
                alert("请输入样式文件中文名称！");
                document.editForm.cname.focus();
                return false;
            }
            else if (document.editForm.content.value == "") {
                alert("请输入样式文件内容！");
                document.editForm.content.focus();
                return false;
            }
            return true;
        }

        function insertpic() {
            var iWidth = 400;
            var iHeight = 200;
            var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
            var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
            wins = window.open("styleImage.jsp?type=<%=type%>", "StyleWinInsertPic", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
            wins.focus();
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<form method="POST" action="editColumnStyleRight.jsp" name="editForm" onsubmit="javascript:return save();">
    <input type="hidden" name="doCreate" value="true">
    <input type="hidden" name="ID" value="<%=styleID%>">
    <input type="hidden" name="type" value="<%=type%>">
    <input type="hidden" name="from" value="<%=from%>">
    <table border="0" width="100%">
        <tr align="center">
            <td>
                中文名称：<input size=16 name="cname" value="<%=cname%>" class=tine>
                列表标记：<select size="1" name="markname" style="width:120" onchange="SelectMarkName();" class=tine>
                <option value="">选择列表标记</option>
                <%=marks%>
            </select>
                <input type="button" value="图片" style="font-size:9pt;height:18" onclick="insertpic();">
            </td>
        </tr>
        <tr align="center">
            <td>
                <textarea rows="3" name="headstr" cols="100" style="font-size: 8pt; font-family: Georgia"><%=headstr%></textarea><br>
            </td>
        </tr>
        <tr align="center">
            <td>
                <textarea rows="20" id="content" name="content" cols="100" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"
                          style="font-size: 8pt; font-family: Georgia"><%=content%></textarea><br>
            </td>
        </tr>
        <tr align="center">
            <td>
                <textarea rows="3" name="tailstr" cols="100" style="font-size: 8pt; font-family: Georgia"><%=tailstr%></textarea>
            </td>
        </tr>
        <tr align="center">
            <td height="50">
                <input type="submit" value="  保存  " class=tine>&nbsp;&nbsp;<input type="button" value="  取消  " onclick="top.close();" class=tine>
            </td>
        </tr>
    </table>
</form>

</body>
</html>
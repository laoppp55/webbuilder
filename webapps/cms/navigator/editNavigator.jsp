<%@ page import="java.util.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.viewFileManager.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp" );
        return;
    }

    int id = ParamUtil.getIntParameter(request, "id", 0);
    System.out.println("id=" + id);
    boolean doCreate     = ParamUtil.getBooleanParameter(request,"doCreate");
    boolean success = false;

    IViewFileManager vfManager = viewFilePeer.getInstance();
    navigator nv = new navigator();
    nv = vfManager.getNavigator(id);

    String cname = nv.getName();
    String notes = nv.getNotes();
    String content = nv.getContent();
    String fcontent = nv.getfContent();

    if (doCreate)
    {
        notes       = ParamUtil.getParameter(request, "notes");
        content     =  ParamUtil.getParameter(request, "content");
        fcontent    =  ParamUtil.getParameter(request, "fcontent");
        cname       =  ParamUtil.getParameter(request, "cname");
        try {
            nv.setID(id);
            nv.setName(cname);
            nv.setContent(content);
            nv.setNotes(notes);
            nv.setfContent(fcontent);

            vfManager.update(nv);
            success = true;
        } catch (viewFileException vfe) {
            success = false;
            throw new viewFileException("" + vfe.getMessage());
        }
    }

    if (success)
    {
        out.println("<script language=javascript>");
        out.println("opener.history.go(0);");
        out.println("window.close();");
        out.println("</script>");
        return;
    }
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>修改样式文件</title>
    <link rel=stylesheet type=text/css href=../style/global.css>
    <script language="javascript">
        function SelectMarkName()
        {
            var i = document.editview.markname.selectedIndex;
            var str = document.editview.markname.options[i].value;
            if (str != "" && str != "1")
            {
                insertAtCaret(document.editview.content, str);
            }
            else if (str == "1")       //列表图标
            {
                var retVal = showModalDialog("../member/styleImage.jsp", "SelectStyleImage",
                        "font-family:Verdana;font-size:12;dialogWidth:35em;dialogHeight:25em;status:no");
                if (retVal != "")
                {
                    var type = retVal.substring(0, 1);
                    var str = retVal.substring(1, retVal.length);
                    if (type == "1") str = "<IMG src=/_sys_ListImages/" + str + ">";
                    insertAtCaret(document.editview.content, str);
                }
            }
            document.editview.markname.options[0].selected = true;
        }

        function storeCaret(textEl)
        {
            if (textEl.createTextRange)
            {
                textEl.caretPos = document.selection.createRange().duplicate();
            }
        }

        function insertAtCaret(textEl, text)
        {
            if (textEl.createTextRange && textEl.caretPos)
            {
                var caretPos = textEl.caretPos;
                caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == ' ' ?text + ' ' : text;
            }
            else
            {
                textEl.value = text;
            }
        }

        function check(){
            if((editview.cname.value == null)||(editview.cname.value == "")){
                alert("请输入网站导航的名称！");
                return false;
            }
            if((editview.content.value == null)||(editview.content.value == "")){
                alert("请输入网站导航的内容");
                return false;
            }
            return true;
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<form action="editNavigator.jsp" method="post" name="editview">
<input type=hidden name=doCreate value="true">
<input type=hidden name=id value="<%=id%>">
<table border="1" width="100%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00 height="446" align=center>
<tr>
    <td width="15%" align="right" height="20"><b>导航条名称：</b></td>
    <td width="85%" height="20">&nbsp;<input type="text" name="cname" value="<%=((cname==null)||(cname.equals("null")))?"":cname%>"></td>
</tr>
<tr>
    <td width="15%" bgcolor="#EFEFEF" align="right" height="20"><b>注释：</b></td>
    <td width="85%" bgcolor="#EFEFEF" height="20">&nbsp;<input type="text" name="notes" size="50" value="<%=((notes==null)||(notes.equals("null")))?"":notes%>"></td>
</tr>
        <div id="h1" style="VISIBILITY: hidden; POSITION: absolute">
        <tr>
        <td width="15%" align="right" height="20"><b>主导航内容：</b></td>
        <td width="85%" height="80">&nbsp;<textarea name="content" cols=120 rows=15 WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);" style="font-size: 8pt; font-family: Georgia"><%=content%></textarea></td>
        </tr>
        </div>
        <tr>
        <td width="15%" align="right" height="20"><b>辅助导航内容：</b></td>
        <td width="85%" height="230">&nbsp;<textarea name="fcontent" cols=120 rows=15 WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);" style="font-size: 8pt; font-family: Georgia"><%=fcontent%></textarea></td>
        </tr>
        </table>
        <p align=center>
        <input type="submit" name="edit" value="修改" onclick="javascript:return check();">&nbsp;&nbsp;
<input type="button" name="cancel" value="取消" onclick="javascript:window.close()">
        </p>
        </form>
        </body>
        </html>

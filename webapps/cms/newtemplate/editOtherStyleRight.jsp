<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.viewFileManager.*"
         contentType="text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    int ID = ParamUtil.getIntParameter(request, "ID", 0);
    int type = ParamUtil.getIntParameter(request, "type", 0);
    int fromflag = ParamUtil.getIntParameter(request, "fromflag", 0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    ViewFile viewFile = new ViewFile();

    String cname = "样式文件";
    String content = "";

    if (doCreate) {
        cname = ParamUtil.getParameter(request, "filename");
        content = ParamUtil.getParameter(request, "content");
        type = ParamUtil.getIntParameter(request, "type", 0);

        viewFile.setChineseName(cname);
        viewFile.setContent(content);
        viewFile.setSiteID(siteID);
        viewFile.setID(ID);
        viewFile.setType(type);
        viewFile.setEditor(authToken.getUserID());
        viewFile.setNotes("");

        int newID = ID;
        if (ID > 0)
            viewfileMgr.update(viewFile);
        else
            newID = viewfileMgr.create(viewFile);

        String viewer = request.getHeader("user-agent");
        out.println("<script language=javascript>");
        if (viewer.toLowerCase().indexOf("gecko") == -1) {
            out.println("window.returnValue = \"" + newID + "," + cname + "\";");
            out.println("window.close();");
        } else {
            if(type == 5){
                out.println("window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"sellink\").options.add(new Option('" + cname + "'," + newID + "));");
                out.println("window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"sellink\").options[window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"sellink\").length-1].selected=true;");
                out.println("top.close();");
            }else if(type == 7){
                out.println("window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"seldays\").options.add(new Option('" + cname + "'," + newID + "));");
                out.println("window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"seldays\").options[window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"seldays\").length-1].selected=true;");
                out.println("top.close();");
            }else if(type == 2){
                if(fromflag == 0){
                    out.println("window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"navbar\").options.add(new Option('" + cname + "'," + newID + "));");
                    out.println("window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"navbar\").options[window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"navbar\").length-1].selected=true;");
                    out.println("top.close();");
                }else if(fromflag == 1){
                    out.println("window.parent.opener.document.getElementById(\"navbar\").options.add(new Option('" + cname + "'," + newID + "));");
                    out.println("window.parent.opener.document.getElementById(\"navbar\").options[window.parent.opener.document.getElementById(\"navbar\").length-1].selected=true;");
                    out.println("top.close();");
                }
            }else if(type == 8){
                out.println("window.parent.opener.document.getElementById(\"listType\").options.add(new Option('" + cname + "'," + newID + "));");
                out.println("window.parent.opener.document.getElementById(\"listType\").options[window.parent.opener.document.getElementById(\"listType\").length-1].selected=true;");
                out.println("top.close();");
            }else if(type == 10){
                out.println("window.parent.opener.document.getElementById(\"nextarticle\").options.add(new Option('" + cname + "'," + newID + "));");
                out.println("window.parent.opener.document.getElementById(\"nextarticle\").options[window.parent.opener.document.getElementById(\"nextarticle\").length-1].selected=true;");
                out.println("top.close();");
            }
        }
        out.println("</script>");
        return;
    }

    if (ID > 0)              //update
    {
        viewFile = viewfileMgr.getAViewFile(ID);
        cname = viewFile.getChineseName();
        content = viewFile.getContent();

        if (cname == null) cname = "";
        if (content == null) content = "";
        cname = StringUtil.gb2iso4View(cname);
        content = StringUtil.gb2iso4View(content);
    }

    //导航条标记
    String marks = "";
    if (type == 2)        //navbar
    {
        marks = "<option value='共<" + "%%NUM%%" + ">条'>总条数</option>" +
                "<option value='共<" + "%%PAGENUM%%" + ">页'>总页数</option>" +
                "<option value='<A href=<" + "%%HEAD%%" + ">>第一页</A>'>第一页</option>" +
                "<option value='<A href=<" + "%%PREVIOUS%%" + ">>上一页</A>'>上一页</option>" +
                "<option value='<A href=<" + "%%NEXT%%" + ">>下一页</A>'>下一页</option>" +
                "<option value='<A href=<" + "%%BOTTOM%%" + ">>最后页</A>'>最后页</option>" +
                "<option value='第<" + "%%CURRENTPAGE%%" + ">页'>当前页</option>" +
                "<option value='选择：<" + "%%SELECT%%" + ">'>下拉跳转</option>" +
                "<option value='<form>跳转到<input name=cmspage size=4>\n<input type=button value=确定 onclick=<" + "%%GOTO%%" + ">></form>'>填写页数跳转</option>" +
                "<option value='页码：<" + "%%NUMBER%%" + ">'>页码排列</option>";
    }
    if (type == 5)        //readed article title
    {
        content = "<" + "%%DATA%%" + ">";
        marks = "<option value='<" + "%%DATA%%" + ">'>文章标题</option>";
    }
    if (type == 8)        //chinese or english path
    {
        marks = "<option value='<A HREF=<" + "%%URL%%" + ">><" + "%%CHINESE_PATH%%" + "></A>'>中文路径</option>" +
                "<option value='<A HREF=<" + "%%URL%%" + ">><" + "%%ENGLISH_PATH%%" + "></A>'>英文路径</option>" +
                "<option value='$<A HREF=<" + "%%URL%%" + ">><" + "%%LAST_ITEM%%" + "></A>'>最后项格式</option>" +
                "<option value='$>>'>分隔符</option>";
    }
    if (type == 9)        //标题样式
    {
        marks = "<option value='<" + "%%DATA%%" + ">'>文章主标题</option>" +
                "<option value='<" + "%%COLUMNNAME%%" + ">'>当前栏目名称</option>" +
                "<option value='<" + "%%COLUMNNAME1%%" + ">'>一级栏目名称</option>" +
                "<option value='<" + "%%COLUMNNAME2%%" + ">'>二级栏目名称</option>" +
                "<option value='<" + "%%COLUMNNAME3%%" + ">'>三级栏目名称</option>";
    }
    if (type == 10)        //next article
    {
        marks = "<option value='<A HREF=<"+"%%URL%%"+">><"+"%%DATA%%"+"></A>'>文章标题</option>";
    }
%>

<html>
<head>
    <title>碎片管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script language=javascript>
        function SelectMarkName()
        {
            var i = document.form1.markname.selectedIndex;
            var str = document.form1.markname.options[i].value;
            insertAtCaret(document.form1.content, str);
            document.form1.markname.options[0].selected = true;
        }

        function storeCaret(textEl)
        {
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

        function check()
        {
            if (document.form1.filename.value == "")
            {
                alert("请输入样式文件中文名称！");
                return false;
            }
            if (document.form1.content.value == "")
            {
                alert("样式文件内容不能为空！");
                return false;
            }
            if (<%=type%> == 5 && document.form1.content.value.indexOf("<" + "%%DATA%%" + ">") == -1)
            {
                alert("样式文件中必须有<" + "%%DATA%%" + ">！");
                return false;
            }
            return true;
        }

        function insertpic()
        {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if(isMSIE){
                var retstr = showModalDialog("styleImage.jsp", "SelectStyleImage", "font-family:Verdana;font-size:12;dialogWidth:36em;dialogHeight:28em;status:no");
                if (retstr != "" && retstr != undefined)
                {
                    insertAtCaret(document.form1.content, retstr);
                }
            }else{
                wins = window.open("styleImage.jsp", "", "width=400,height=200");
                wins.focus();
            }
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<form method="POST" action="editOtherStyleRight.jsp" name=form1 onsubmit="return check();">
    <input type=hidden name=doCreate value=true>
    <input type=hidden name="ID" value="<%=ID%>">
    <input type=hidden name="type" value="<%=type%>">
    <input type=hidden name="fromflag" value="<%=fromflag%>">
    <table border="0" width="96%" align=center>
        <tr height=30>
            <td width="50%">
                中文名称：<input class=tine name="filename" id="filename" size="20" value="<%=cname%>">
            </td>
            <td width="50%">
                选择标记：
                <select size="1" name="markname" style="width:140" onchange="SelectMarkName();" class=tine>
                    <option value="">选择列表标记</option>
                    <%=marks%>
                </select>
                <input type="button" value="图片" class=tine onclick="insertpic();">
            </td>
        </tr>
        <tr>
            <td colspan=2 height=30>
                文件内容：<br><textarea class=tine rows="17" id="content" name="content" cols="82" WRAP="soft"
                                   ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);"
                                   ONKEYUP="storeCaret(this);"><%=content%></textarea>
            </td>
        </tr>
        <tr height=45>
            <td width="100%" colspan="2" align=center>
                <input type="submit" value="  确定  " class=tine>&nbsp;&nbsp;&nbsp;
                <input type="button" value="  取消  " onclick="top.close();" class=tine>
            </td>
        </tr>
    </table>
</form>

</body>
</html>
<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.markManager.*,
                com.bizwink.cms.server.CmsServer,
                com.bizwink.cms.xml.*,"
        contentType="text/html;charset=utf-8"
%>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    //     修改编辑属性取值
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    IMarkManager markMgr = markPeer.getInstance();

    int siteID = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);

    int listType = 0;
    int innerFlag = 0;
    String notes = "";
    String cname = "用户登录";

    String submits = "";
    String submitsimage = "";

    String register = "";
    String regimage = "";

    String findpwd = "";
    String findpwdimage = "";

    int type = ParamUtil.getIntParameter(request, "type", 0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");


    if (doCreate) {
        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");

        String login = ParamUtil.getParameter(request,"login");
        int textsize = ParamUtil.getIntParameter(request,"textsize",0);
        //登录按钮
        submits = ParamUtil.getParameter(request,"submits");
        submitsimage = ParamUtil.getParameter(request,"submitsimage");
        //注册按钮
        register = ParamUtil.getParameter(request,"submits");
        regimage = ParamUtil.getParameter(request,"submitsimage");
        //找回密码按钮
        findpwd = ParamUtil.getParameter(request,"submits");
        findpwdimage = ParamUtil.getParameter(request,"submitsimage");

        String content ="[TAG][LOGINFORM][SITEID]"+siteID+"[/SITEID][SITENAME]" + sitename + "[/SITENAME]"+
                "[CHINESENAME]"+cname+"[/CHINESENAME][NOTES]"+notes+"[/NOTES]" +
                "[LOGININFO]<!{^CDATA{^" + login + ">^}^}>[/LOGININFO]"+
                "[REGISTER]" + register + "[/REGISTER]"+
                "[REGIMAGE]" + regimage + "[/REGIMAGE]"+
                "[FINDPWD]" + findpwd + "[/FINDPWD]"+
                "[FINDPWDIMAGE]" + findpwdimage + "[/FINDPWDIMAGE]"+
                "[SUBMITS]" + submits + "[/SUBMITS]"+
                "[SUBMITISMAGE]" + submitsimage + "[/SUBMITISMAGE]"+
                "[TEXTSIZE]" + textsize + "[/TEXTSIZE]"+
                "[/LOGINFORM]"+ "[/TAG]";
        boolean saveas = ParamUtil.getBooleanParameter(request, "saveas");
        String relatedCID = "(0)";

        mark mark = new mark();
        mark.setID(markID);
        mark.setColumnID(columnID);
        mark.setSiteID(siteID);
        mark.setContent(content);
        mark.setChinesename(cname);
        mark.setNotes(notes);
        mark.setInnerHTMLFlag(innerFlag);
        mark.setFormatFileNum(listType);
        mark.setRelatedColumnID(relatedCID);
        mark.setMarkType(1);
        int orgmarkID = markID;
        if (orgmarkID > 0 && !saveas)
            markMgr.Update(mark);
        else
            markID = markMgr.Create(mark);

        String markname = "用户登录";

        if (orgmarkID > 0 && !saveas) {
            out.println("<script>top.close();</script>");
        } else {
            String returnvalue = "[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]";
            out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                    "window.parent.opener.document.createForm.cname.value='" + markname + "';" +
                    "window.parent.opener.document.createForm.modelname.value='login';" +
                    "window.parent.opener.InsertHTML(returnvalue);top.close();</script>");
        }
        return;
    }

    int textsize = 0;
    String logininfo = "";

    if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");
        System.out.println("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + str);
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + str);

        cname = properties.getProperty(properties.getName().concat(".CHINESENAME"));
        logininfo = properties.getProperty(properties.getName().concat(".LOGININFO"));
        if (logininfo != null) logininfo = logininfo.substring(0,logininfo.length() -1);


        if(cname == null) cname = "用户登录";
        notes = properties.getProperty(properties.getName().concat(".NOTES"));
        if (notes == null || notes.equalsIgnoreCase("null")) notes = "";

        submits = properties.getProperty(properties.getName().concat(".SUBMITS"));
        if(submits == null || submits.equalsIgnoreCase("null")) submits = "";

        submitsimage = properties.getProperty(properties.getName().concat(".SUBMITISMAGE"));
        if(submitsimage == null || submitsimage.equalsIgnoreCase("null")) submitsimage = "";

        register = properties.getProperty(properties.getName().concat(".REGISTER"));
        if(register == null || register.equalsIgnoreCase("null")) register = "";

        regimage = properties.getProperty(properties.getName().concat(".REGIMAGE"));
        if(regimage == null || regimage.equalsIgnoreCase("null")) regimage = "";

        findpwd = properties.getProperty(properties.getName().concat(".FINDPWD"));
        if(findpwd == null || findpwd.equalsIgnoreCase("null")) findpwd = "";

        findpwdimage = properties.getProperty(properties.getName().concat(".FINDPWDIMAGE"));
        if(findpwdimage == null || findpwdimage.equalsIgnoreCase("null")) findpwdimage = "";

        String text = properties.getProperty(properties.getName().concat(".TEXTSIZE"));
        if(text != null && !text.equals("") && !text.equalsIgnoreCase("null")) textsize = Integer.parseInt(text);

    }

%>

<html>
<head>
    <base target="_self" >
    <title>用户登录页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="javascript" src="../js/color.js"></script>
    <meta http-equiv="Pragma" content="no-cache">
    <script type="text/javascript">
        function cal() {
            top.close();
        }

        function doit() {
            alert("hello word");
            var logininfo =  document.markform.login.value;
            if(logininfo == "")
            {
                alert("请输入登录表单样式");
                return false;
            }
            markform.action = "addUserLoginForm.jsp";
            markform.method = "post";
            markform.target = "_self"
            markform.submit();
        }

        function selpic(type)
        {
            winStr = "selectIcon.jsp";
            returnvalue = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:40em; status:no");

            var loginimg = document.getElementById("loginimg");
            var regimg = document.getElementById("regimg");
            var mimaimg = document.getElementById("mimaimg");
            if (type == 1) {
                markform.login_img.value=returnvalue;
                if (returnvalue != null && returnvalue != "") {
                    loginimg.style.display = "";
                    document.getElementById("loginsrc_img").src="/_sys_images/buttons/" + returnvalue;
                }
            }
            else if (type == 2) {
                markform.reg_img.value = returnvalue;
                if (returnvalue != null && returnvalue != "") {
                    regimg.style.display = "";
                    document.getElementById("regsrc_img").src="/_sys_images/buttons/" + returnvalue;
                }
            }
            else {
                markform.mima_img.value = returnvalue;
                if (returnvalue != null && returnvalue != "") {
                    mimaimg.style.display = "";
                    document.getElementById("mimasrc_img").src="http://www.coosite.com/webbuilder/sites/images/" + returnvalue;
                }
            }
        }
        function SelectMarkName()
        {
            var i = document.markform.fields.selectedIndex;
            //var str = "<" + "%%" + document.markform.fields.options[i].value + "%%" + ">";
            //alert(i);
            var str = "";
            if (i==1)
                str = "<input type=\"text\" id=\"username\" name=\"username\" value=\"\" size=\"\">";
            else if (i==2)
                str = "<input type=\"password\" id=\"password\" name=\"password\" value=\"\" size=\"\">";
            else if (i==3)
                str = "<" + "%%" + document.markform.fields.options[i].value + "%%" + ">";
            else if (i==4){
                str = "<a href=\"/_prog/register,jsp\" target=\"_blank\"><" + "%%" + document.markform.fields.options[i].value + "%%" + "></a>";
            }
            else if (i==5) {
                str = "<a href=\"/_prog/findpwd.jsp\" target=\"_blank\"><" + "%%" + document.markform.fields.options[i].value + "%%" + "></a>";
            }

            if (str != "" && str != "1" && document.markform.fields.options[i].value != "-1")
                insertAtCaret(document.markform.login, str);
            document.markform.fields.options[0].selected = true;
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
        function storeCaret(textEl)
        {
            if (textEl.createTextRange)
                textEl.caretPos = document.selection.createRange().duplicate();
        }
        function selectpic(type){
            //alert("hello word");
            var returnval = window.showModalDialog("selectsubmitframe.jsp","","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
            if(type==0){
                document.getElementById("okimage").value = returnval;
            }else if(type==1){
                document.getElementById("cancelimage").value = returnval;
            }else if(type==2){
                document.getElementById("addressimage").value = returnval;
            }else if(type==6){
                document.getElementById("submitsimage").value = returnval;
            }else if(type==3){
                document.getElementById("sendwayimage").value = returnval;
            }else if(type==4){
                document.getElementById("paywayimage").value = returnval;
            }else if(type==5){
                document.getElementById("orderimage").value = returnval;
            }else if(type==7){
                document.getElementById("editsendwayimage").value = returnval;
            }else if(type==8){
                document.getElementById("editpaywayimage").value = returnval;
            }else if(type==9){
                document.getElementById("invoiceimage").value = returnval;
            }else if(type==10){
                document.getElementById("editinvoiceimage").value = returnval;
            }
            //window.open("selectsubmitpic.jsp?type="+type, "", "height=500, width=800, toolbar=yes, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
        }
        function upload(type)
        {
            //alert("hello word");
            var returnval = window.showModalDialog("uploadsubmitpicframe.jsp","","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
            if(type==0){
                document.getElementById("okimage").value = returnval;
            }else if(type==1){
                document.getElementById("cancelimage").value = returnval;
            }else if(type==2){
                document.getElementById("addressimage").value = returnval;
            }else if(type==6){
                document.getElementById("submitsimage").value = returnval;
            }else if(type==3){
                document.getElementById("sendwayimage").value = returnval;
            }else if(type==4){
                document.getElementById("paywayimage").value = returnval;
            }else if(type==5){
                document.getElementById("orderimage").value = returnval;
            }else if(type==7){
                document.getElementById("editsendwayimage").value = returnval;
            }else if(type==8){
                document.getElementById("editpaywayimage").value = returnval;
            }else if(type==9){
                document.getElementById("invoiceimage").value = returnval;
            }else if(type==10){
                document.getElementById("editinvoiceimage").value = returnval;
            }
            //window.open("uploadsubmitpic.jsp?type="+type, "", "height=200, width=600 toolbar=yes, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<table width="80%" border="0" align="center">
    <form name="markform">
        <input type=hidden name=doCreate value=true>
        <input type=hidden name=saveas value=false>
        <input type=hidden name=column value="<%=columnID%>">
        <input type=hidden name=type value=19>
        <input type="hidden" name="gjcontent" id="gjcontent">
        <input type="hidden" name="mark" value="<%=markID%>">
        <tr height="30">
            <td width="15%">请选择送登录标记：</td>
            <td width="70%" align="left"><select id="fieldname" name="fields" onchange="SelectMarkName();">
                <option value ="-1">请选择送登录标记</option>
                <option value ="username">用户名</option>
                <option value ="passwd">密码</option>
                <option value ="submits">登录按钮</option>
                <option value ="register">注册按钮</option>
                <option value ="findpwd">找回密码按钮</option>
            </select>
            </td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="15%">
                登录表单样式：</td><td align="left" valign="top" width="70%"><textarea name="login" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=logininfo%></textarea></td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                登录按钮类型：<select name="submits">
                <option value="submit" <%if(submits != null && submits.equals("submit")){%>selected<%}%>>提交</option>
                <option value ="images" <%if(submits != null && submits.equals("images")){%>selected<%}%>>图片</option>
                <option value ="links" <%if(submits != null && submits.equals("links")){%>selected<%}%>>文字连接</option>
            </select>
            </td>
            <td align="left" valign="top" width="75%">
                选择图片：<input id="submitsimage" name="submitsimage" type="text" value="<%=submitsimage%>" readonly ><a href="#" onclick="javascript:selectpic(6);">选择已有图片</a>  <a href="#" onclick="javascript:upload(6);">上传新图片</a>
            </td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                注册按钮类型：<select name="register">
                <option value="no">请选择</option>
                <option value="submit" <%if(register != null && register.equals("submit")){%>selected<%}%>>提交</option>
                <option value ="images" <%if(register != null && register.equals("images")){%>selected<%}%>>图片</option>
                <option value ="links" <%if(register != null && register.equals("links")){%>selected<%}%>>文字连接</option>
            </select>
            </td>
            <td align="left" valign="top" width="75%">
                选择图片：<input id="regimageid" name="regimage" type="text" value="<%=regimage%>" readonly ><a href="#" onclick="javascript:selectpic(6);">选择已有图片</a>  <a href="#" onclick="javascript:upload(6);">上传新图片</a>
            </td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                找回密码按钮类型：<select name="findpwd">
                <option value="no">请选择</option>
                <option value="submit" <%if(findpwd != null && findpwd.equals("submit")){%>selected<%}%>>提交</option>
                <option value ="images" <%if(findpwd != null && findpwd.equals("images")){%>selected<%}%>>图片</option>
                <option value ="links" <%if(findpwd != null && findpwd.equals("links")){%>selected<%}%>>文字连接</option>
            </select>
            </td>
            <td align="left" valign="top" width="75%">
                选择图片：<input id="findpwdid" name="findpwdimage" type="text" value="<%=findpwdimage%>" readonly ><a href="#" onclick="javascript:selectpic(6);">选择已有图片</a>  <a href="#" onclick="javascript:upload(6);">上传新图片</a>
            </td>
        </tr>

        <tr height="1">
            <td height="1" width="100%" colspan="2">
                _____________________________________________________________________________________________________________________________________________
            </td>
        </tr>
        <tr height=24>
            <td colspan="2">用户名或密码文本长度：<input name=textsize size=20 value="<%=textsize%>" class=tine></td>
        </tr>
        <tr height=24>
            <td colspan="2">标记中文名称：<input name=chineseName size=20 value="<%=cname%>" class=tine></td>
        </tr>
        <tr height=80>
            <td colspan="2">标记描述：<br><textarea rows="3" id="notes" cols="38" class=tine><%=notes%>
            </textarea></td>
        </tr>
        <tr height="50">
            <td align=center colspan="2">
                <input type="button" value=" 确定 " onClick = "doit();" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value=" 取消 " onClick = "javascript:cal();" class=tine>
            </td>
        </tr>
    </form>
</table>

</body>
</html>
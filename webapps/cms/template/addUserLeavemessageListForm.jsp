<%@page import="java.util.*,
                com.bizwink.cms.viewFileManager.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.xml.*"
        contentType="text/html;charset=gbk"
        %>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    //     修改编辑属性取值
    int markID = ParamUtil.getIntParameter(request, "mark", 0);

    int siteID = authToken.getSiteID();
    IMarkManager markMgr = markPeer.getInstance();
    mark vmark=null;
    if(markID>0) vmark=markMgr.getAMark(markID);
    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);

    //获取系统定义的分页列条式和本站点自己定义的分页条样式 ,如果修改系统定义的分页条样式，系统将自动保存为本站点拥有的自定义分页条样式
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    List navList = viewfileMgr.getViewFileC(siteID, 2);
    ViewFile viewfile = new ViewFile();

    //获取留言板表单的标记定义
    List marks = markMgr.getMarksByType(siteID,21);
    String notes = "";
    String content = "";
    String cname = "用户留言列表";
    String lwforms = "";                  //与留言列表相关联的表单ID
    int lwforms_n = 0;                    //与留言列表相关联的表单ID的数字表示
    int auditflag = 0;                    //0显示所有的用户留言信息  1只显示通过审核的留言信息
    int fenyestyle = -1;                  //分页导航格式ID

    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");

    if (doCreate) {
        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");
        content = ParamUtil.getParameter(request, "content");
        lwforms = ParamUtil.getParameter(request, "lwforms");
        auditflag = ParamUtil.getIntParameter(request, "audit",0);
        String relatedCID = "()";
        int ranges = ParamUtil.getIntParameter(request,"ranges",0);
        fenyestyle = ParamUtil.getIntParameter(request,"fenyelist",0);
        String xmlstr = "[TAG][LEAVEMESSAGELIST][SITEID]"+siteID+"[/SITEID][FORMID]" + lwforms + "[/FORMID][RANGE]"+ranges+"[/RANGE]" +
                "[CHINESENAME]"+cname+"[/CHINESENAME][NOTES]"+notes+"[/NOTES][AUDIT]" + auditflag + "[/AUDIT][PAGES]" + fenyestyle +
                "[/PAGES][LISTSTYLE]<!{^CDATA{^" + content + ">^}^}>[/LISTSTYLE][/LEAVEMESSAGELIST]"+ "[/TAG]";

        mark mark = new mark();
        mark.setID(markID);
        mark.setColumnID(columnID);
        mark.setSiteID(siteID);
        mark.setContent(xmlstr);
        mark.setMarkType(22);
        mark.setChinesename(cname);
        mark.setNotes(notes);
        mark.setInnerHTMLFlag(0);
        mark.setRelatedColumnID(relatedCID);

        int orgmarkid = markID;
        if (markID > 0)
            markMgr.Update(mark);
        else
            markID = markMgr.Create(mark);

        String viewer = request.getHeader("user-agent");
        if (viewer.toLowerCase().indexOf("gecko") == -1)
            out.println("<script>window.returnValue=\"[TAG][MARKID]" + markID+"_"+columnID + "[/MARKID][/TAG]\";window.close();</script>");
        else {
            if(orgmarkid > 0){
                out.println("<script>top.close();</script>");
            }else{
                String returnvalue = "[TAG][MARKID]" + markID + "[/MARKID][/TAG]";
                out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + cname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                        "window.parent.parent.opener.InsertHTML('content',returnvalue);top.close();</script>");
            }
        }
        return;
    }

    int range = 10;
    String listStyle = null;
    if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");

        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        range = Integer.parseInt(properties.getProperty(properties.getName().concat(".RANGE")));

        fenyestyle = Integer.parseInt(properties.getProperty(properties.getName().concat(".PAGES")));//分页样式
        cname = properties.getProperty(properties.getName().concat(".CHINESENAME"));
        listStyle = properties.getProperty(properties.getName().concat(".LISTSTYLE"));
        if (listStyle != null) listStyle = listStyle.substring(0,listStyle.length() -1);
        lwforms = properties.getProperty(properties.getName().concat(".FORMID"));
        if (lwforms != null) lwforms_n=Integer.parseInt(lwforms);
        if(cname == null) cname = "用户留言列表";
        notes = properties.getProperty(properties.getName().concat(".NOTES"));
        if (notes == null) notes = "";
    }
%>

<html>
<head>
    <base target="_self" >
    <title>定义留言列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="javascript" src="../js/mark.js"></script>
    <meta http-equiv="Pragma" content="no-cache">
    <script type="text/javascript">
        function createStyle(type, columnID){
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            winstrs = "../template/editOtherStyle.jsp?type=" + type;
            if (isMSIE)
                retstr = showModalDialog(winstrs, "createStyle", "font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
            else {
                artwin = window.open(winstrs, "createStyle", "font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
                artwin.focus();
            }

            if (retstr != "" && retstr != undefined) {
                var newoption = document.createElement("OPTION");
                newoption.value = retstr.substring(0, retstr.indexOf(","));
                newoption.text = retstr.substring(retstr.indexOf(",") + 1);

                document.all("fenyelist").add(newoption);
                document.all("fenyelist").options[document.all("fenyelist").length - 1].selected = true;
            }
        }

        function updateStyle(type, styleID, columnID){
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                var retstr = showModalDialog("../template/editOtherStyle.jsp?type=" + type + "&ID=" + styleID, "updateStyle", "font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
            } else {
                wins = window.open("../template/editOtherStyle.jsp?type=" + type + "&ID=" + styleID, "updateStyle", "font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
                wins.focus();
            }
        }

        function previewStyle(type, styleID)
        {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                if (styleID > 0) {
                    showModalDialog("../template/getviewfile.jsp?id=" + styleID + "&type=" + type, "", "font-family:Verdana;font-size:12;dialogWidth:40em;dialogHeight:16em;status:no");
                }
            } else {
                if (styleID > 0) {
                    wins = window.open("../template/getviewfile.jsp?id=" + styleID + "&type=" + type, "updateStyle", "font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
                    wins.focus();
                }
            }
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

        function SelectMarkName()
        {
            var i = document.markform.fields.selectedIndex;
            var str = "";
            if (document.markform.fields.options[i].text!="显示留言")
                str = "<" + "%%" + document.markform.fields.options[i].value + "%%" + ">";
            else
                str = "<a href=\"/_commons/displeavemessage.jsp?lid=<" + "%%" + document.markform.fields.options[i].value + "%%" + ">\" target=\"_blank\"><"+"%%title%%" + "></a>";

            if (str != "" && str != "1")
                insertAtCaret(document.markform.content, str);
            document.markform.fields.options[0].selected = true;
        }

        function storeCaret(textEl)
        {
            if (textEl.createTextRange)
                textEl.caretPos = document.selection.createRange().duplicate();
        }

        function getFormFileds(p1,p2) {
            var objXml;
            if (window.ActiveXObject) {
                objXml = new ActiveXObject("Microsoft.XMLHTTP");
            } else if (window.XMLHttpRequest) {
                objXml = new XMLHttpRequest("");
            }

            if (p1 != 0) {                           //p1=0 表示是创建一个新的留言列表，没有与任何留言表单相关联 p1=1表示编辑一个已经存在的留言列表
                if (p2 == 0)                         //p2=0表示改变与该留言列表相关联的表单 p2=1表示进入编辑页面
                    objXml.open("POST", "../sites/_commons/getFormFields.jsp?markid="+markform.lwforms.value, false);
                else
                    objXml.open("POST", "../sites/_commons/getFormFields.jsp?markid=<%=lwforms%>", false);
                objXml.send(null);
                var retstr = objXml.responseText;
                if (retstr != null && retstr.length > 0) {
                    var arrdata = retstr.split("~~!");
                    document.getElementById("fieldname").options.length=0;
                    document.getElementById("fieldname").options.add(new Option("请选择",""));
                    document.getElementById("fieldname").options.add(new Option("留言ID","id"));
                    document.getElementById("fieldname").options.add(new Option("留言用户名称","username"));
                    document.getElementById("fieldname").options.add(new Option("显示留言","id"));
                    for (var i = 0; i < arrdata.length; i++)
                    {
                        //标题 内容 公司  电子邮件  姓名  联系地址  邮政编码  联系电话 回复

                        if (arrdata[i].indexOf("信件标题")>-1)
                            document.getElementById("fieldname").options.add(new Option("信件标题","title"));

                        if (arrdata[i] == "信件内容")
                            document.getElementById("fieldname").options.add(new Option("信件内容","content"));

                        if (arrdata[i] == "发信公司")
                            document.getElementById("fieldname").options.add(new Option("发信公司","company"));

                        if (arrdata[i] == "电子邮件")
                            document.getElementById("fieldname").options.add(new Option("电子邮件","email"));

                        if (arrdata[i] == "姓    名")
                            document.getElementById("fieldname").options.add(new Option("姓    名","contactor"));

                        if (arrdata[i] == "联系地址")
                            document.getElementById("fieldname").options.add(new Option("联系地址","contactway"));

                        if (arrdata[i] == "邮政编码")
                            document.getElementById("fieldname").options.add(new Option("邮政编码","postcode"));

                        if (arrdata[i] == "联系电话")
                            document.getElementById("fieldname").options.add(new Option("联系电话","telephone"));
                    }
                    document.getElementById("fieldname").options.add(new Option("留言时间","writedate"));
                    document.getElementById("fieldname").options.add(new Option("回复内容","return"));
                    document.getElementById("fieldname").options.add(new Option("回复时间","rettime"));
                    document.getElementById("fieldname").options.add(new Option("循环开始","begin"));
                    document.getElementById("fieldname").options.add(new Option("循环结束","end"));
                }
            }
        }


        function cal() {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = "";
                top.close();
            } else {
                top.close();
            }
        }

        function doit()
        {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            var relatedformid = markform.lwforms.value;
            if (relatedformid == 0) {
                alert("必须选择与留言列表相关联的留言表单，如果没有留言表单，请先定义留言表单");
                return false;
            }
            if (isMSIE) {
                markform.action = "addUserLeavemessageListForm.jsp";
                markform.method = "post";
                markform.target = "_self"
                markform.submit();
            }
        }
    </script>
</head>

<body bgcolor="#CCCCCC" onload="getFormFileds(<%=lwforms_n%>,1)">
<table width="100%" border="0" align="center">
    <form name="markform">
        <input type=hidden name=doCreate value=true>
        <input type=hidden name=saveas value=false>
        <input type=hidden name=column value="<%=columnID%>">
        <input type=hidden name=type value=23>
        <input type="hidden" name="mark" value="<%=markID%>">

        <input type="hidden" name="contents" id="contents">
        <tr>
            <td style="height: 31px" colspan="4"><strong>定义留言列表样式：</strong></td>
        </tr>
        <tr>
            <td align="left" style="width: 120px; height: 24px;">每页显示留言的条数：</td>
            <td align="left" style="height: 24px; width: 16px;"><input name=ranges size=6 value="<%=range%>" class=tine></td>
            <td align="left" style="width: 120px; height: 24px;">分页导航样式：</td>
            <td align="left" style="height: 24px;">
                <SELECT NAME="fenyelist" id="fenyelistid" style="width: 163px">
                    <option value="0">选择分页条样式</option>
                    <%
                        for (int i = 0; i < navList.size(); i++) {
                            viewfile = (ViewFile) navList.get(i);
                    %>
                    <option value="<%=viewfile.getID()%>"
                            <%if(fenyestyle==viewfile.getID()){%>selected<%}%>><%=StringUtil.gb2iso4View(viewfile.getChineseName())%>
                    </option>
                    <%}%>
                </SELECT>&nbsp;&nbsp;
                <input name="Button1" type="button" onclick="createStyle(2,0)" value="创建">
                <input name="Button2" type="button" onclick="updateStyle(2,fenyelist.options[fenyelist.selectedIndex].value,0)" value="修改">
                <input name="Button3" type="button" onclick="previewStyle(2,fenyelist.options[fenyelist.selectedIndex].value)" value="预览"></td>
        </tr>
        <tr>
            <td align="left" valign="middle" style="height: 24px">选择关联留言表单:</td>
            <td align="left" valign="middle" style="height: 24px; width: 16px;">
                <select name="lwforms" style="width: 169px" onchange="javascript:getFormFileds(1,0)">
                    <option value="0">请选择留言表单</option>
                    <%
                        mark mk = null;
                        for (int i=0; i<marks.size(); i++) {
                            mk = (mark)marks.get(i);
                            if (lwforms_n == mk.getID())
                                out.println("<option value=\"" + mk.getID() +"\" selected>" + mk.getChineseName() + "</option>");
                            else
                                out.println("<option value=\"" + mk.getID() +"\">" + mk.getChineseName() + "</option>");
                        }
                    %>
                </select></td>
            <td align="left" valign="middle" style="height: 24px">选择留言字段名称：</td>
            <td align="left" valign="middle" style="height: 24px"><select ID="fieldname" name="fields" style="width: 159px" onchange="SelectMarkName();">
                <option value ="">请选择字段</option>
                <!-- 标题 内容 公司  电子邮件  联系人  联系方式  邮政编码  电话 回复-->
                <option value ="id">留言ID</option>
                <option value ="username">留言用户名称</option>
                <option value ="title">信件标题</option>
                <option value ="content">信件内容</option>
                <option value ="company">发信公司</option>
                <option value ="email">电子邮件</option>
                <option value ="contactor">姓    名</option>
                <option value ="contactway">联系地址</option>
                <option value ="postcode">邮政编码</option>
                <option value ="telephone">联系电话</option>
                <option value ="return">回复内容</option>
                <option value ="rettime">回复时间</option>
            </select></td>
        </tr>

        <tr height=24>
            <td colspan="4" align="left">
                <input name="audit" value="0" type="radio" checked>在列表中显示所有信息&nbsp;&nbsp;&nbsp;
                <input name="audit" value="1" type="radio">在列表中只显示审核通过的信息</td>
        </tr>

        <tr height=24>
            <td colspan="4" align="left">
                <textarea name="content" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=(listStyle!=null)?listStyle:""%></textarea></td>
        </tr>

        <tr height=24>
            <td colspan="4" align="left">标记中文名称：<input name=chineseName size=20 value="<%=cname%>" class=tine></td>
        </tr>

        <tr height=80>
            <td colspan="4" align="left">标记描述：<br><textarea name="notes" rows="3" id="notes" cols="38" class=tine><%=(notes!=null)?notes:""%></textarea>
            </td>
        </tr>
        <tr height="50">
            <td align=center colspan="4" align="left">
                <input type="button" value=" 确定 " onClick = "doit();" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value=" 取消 " onClick = "javascript:cal();" class=tine>
            </td>
        </tr>
    </form>
</table>

</body>
</html>
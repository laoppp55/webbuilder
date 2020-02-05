<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.xml.*,
                com.bizwink.cms.viewFileManager.*"
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
    //System.out.println("markID="+markID);
    IMarkManager markMgr = markPeer.getInstance();
    mark vmark=null;




    int siteID = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
         //获取系统定义的分页列条式和本站点自己定义的分页条样式 ,如果修改系统定义的分页条样式，系统将自动保存为本站点拥有的自定义分页条样式
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    List navList = viewfileMgr.getViewFileC(siteID, 2);
    ViewFile viewfile = new ViewFile();
    int fenyestyle = -1;                  //分页导航格式ID
    int listType = 0;
    int innerFlag = 0;
    String notes = "";
    String cname = "订单查询";

    int type = ParamUtil.getIntParameter(request, "type", 0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
   int ranges = 10;

    if (doCreate) {
        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");
        String content  = ParamUtil.getParameter(request, "content");
        ranges = ParamUtil.getIntParameter(request,"ranges",0);
        fenyestyle = ParamUtil.getIntParameter(request,"fenyelist",0);
        String xmlstr = "[TAG][ORDERSEARCH_RESULT][SITEID]"+siteID+"[/SITEID]" +
                        "[CHINESENAME]"+cname+"[/CHINESENAME][NOTES]"+notes+"[/NOTES]" +  "[RANGE]"+ranges+"[/RANGE][PAGE]"+fenyestyle+"[/PAGE]" +
                        "[LISTSTYLE]<!{^CDATA{^" + content + ">^}^}>[/LISTSTYLE][/ORDERSEARCH_RESULT]"+ "[/TAG]";
        boolean saveas = ParamUtil.getBooleanParameter(request, "saveas");
        String relatedCID = "(0)";

        mark mark = new mark();
        mark.setID(markID);
        mark.setColumnID(columnID);
        mark.setSiteID(siteID);
        mark.setContent(xmlstr);
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

        String viewer = request.getHeader("user-agent");
        String markname = "订单查询";

        if (viewer.toLowerCase().indexOf("gecko") == -1)
            out.println("<script>window.returnValue=\"[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]\";top.close();</script>");
        else {
            if (orgmarkID > 0 && !saveas) {
                out.println("<script>top.close();</script>");
            } else {
                String returnvalue = "[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]";
                out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                        "window.parent.opener.InsertHTML('content',returnvalue);top.close();</script>");
            }
        }
        return;
    }
     String listStyle = null;
    if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");

        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);


        cname = properties.getProperty(properties.getName().concat(".CHINESENAME"));
        listStyle = properties.getProperty(properties.getName().concat(".LISTSTYLE"));
        if (listStyle != null) listStyle = listStyle.substring(0,listStyle.length() -1);

        if(cname == null) cname = "订单查询";
        notes = properties.getProperty(properties.getName().concat(".NOTES"));
        if (notes == null || notes.equalsIgnoreCase("null")) notes = "";

        String rangestr = properties.getProperty(properties.getName().concat(".RANGE"));
        if(rangestr != null && !rangestr.equals("") && !rangestr.equalsIgnoreCase("null"))
            ranges = Integer.parseInt(rangestr) ;
        String fenyestyles = properties.getProperty(properties.getName().concat(".PAGE"));
        if(fenyestyles != null && !fenyestyles.equals("") && !fenyestyles.equalsIgnoreCase("null"))
            fenyestyle = Integer.parseInt(fenyestyles);
    }
%>

<html>
<head>
    <base target="_self" >
    <title>订单查询</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="javascript" src="../js/color.js"></script>
    <meta http-equiv="Pragma" content="no-cache">
    <script type="text/javascript">
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
            if (isMSIE) {
                markform.action = "addOrderSearch.jsp";
                markform.method = "post";
                markform.target = "_self"
                markform.submit();
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
            var str = "<" + "%%" + document.markform.fields.options[i].value + "%%" + ">";

            if (str != "" && str != "1" && document.markform.fields.options[i].value != "-1")
                insertAtCaret(document.markform.content, str);
            document.markform.fields.options[0].selected = true;
        }

        function storeCaret(textEl)
        {
            if (textEl.createTextRange)
                textEl.caretPos = document.selection.createRange().duplicate();
        }

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
    </script>
</head>

<body bgcolor="#CCCCCC">
<table width="100%" border="0" align="center">
    <form name="markform">
        <input type=hidden name=doCreate value=true>
        <input type=hidden name=saveas value=false>
        <input type=hidden name=column value="<%=columnID%>">
        <input type=hidden name=type value=12>
        <input type="hidden" name="mark" value="<%=markID%>">
        <tr height="30">
            <td width="20%">每页显示条数：<input name=ranges size=6 value="<%=ranges%>" class=tine></td>
            <td width="80%" align="left">分页导航样式：<SELECT NAME="fenyelist" id="fenyelistid" style="width: 163px">
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
                <input name="Button3" type="button" onclick="previewStyle(2,fenyelist.options[fenyelist.selectedIndex].value)" value="预览">
            </td>
        </tr>
        <tr height="30">
            <td width="20%">请选择订单标记：</td>
            <td width="80%" align="left"><select id="fieldname" name="fields" onchange="SelectMarkName();">
                <option value ="-1">请选择订单标记</option>
                <option value ="orderid">订单号</option>
                <option value ="ordertype">订单类型</option>
                <option value ="name">收件人姓名</option>
                <option value ="address">收件人地址</option>
                <option value ="postcode">收件人邮编</option>
                <option value ="phone">收件人电话</option>
                <option value ="payway">付款方式</option>
                <option value ="sendway">送货方式</option>
                <option value ="status">订单状态</option>
                <option value ="totalfee">总费用(含邮费)</option>
                <option value ="sendfee">邮寄费用</option>
                <option value ="scores">使用积分</option>
                <option value ="payflag">支付状态</option>
                <option value ="createdate">创建日期</option>
                <option value ="cancelorder">订单取消</option>
                <option value ="orderdetail">查看详细</option>
                <option value ="begin">循环开始</option>
                <option value ="end">循环结束</option>
                <option value ="navbar">分页导航位置</option>
            </select>
            </td>
        </tr>

        <tr height=24>
            <td colspan="2" align="left">
                <textarea name="content" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=(listStyle!=null)?listStyle:""%></textarea></td>
        </tr>
            <td colspan="2">标记中文名称：<input name=chineseName size=20 value="<%=cname%>" class=tine></td>
        </tr>
        <tr height=80>
            <td colspan="2">标记描述：<br><textarea rows="3" id="notes" name="notes" cols="38" class=tine><%=notes%>
            </textarea></td>
        </tr>
        <tr height="50">
            <td align=center>
                <input type="button" value=" 确定 " onClick = "doit();" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value=" 取消 " onClick = "javascript:cal();" class=tine>
            </td>
        </tr>
    </form>
</table>

</body>
</html>
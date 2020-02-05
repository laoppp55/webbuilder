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
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.Fee" %>
<%@ page import="com.bizwink.cms.business.Order.SendWay" %>

<%
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
       //获得站点送货方式
    IOrderManager oMgr = orderPeer.getInstance();
    List sendwaylist = new ArrayList();
    sendwaylist = oMgr.getAllFeeInfo(siteID);
    //获得支付方式
    List paylist = new ArrayList();
    paylist = oMgr.getAllSendWayInfo(siteID);



    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);

    int listType = 0;
    int innerFlag = 0;
    String notes = "";
    String cname = "订单生成";

    int type = ParamUtil.getIntParameter(request, "type", 0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");


    if (doCreate) {
        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");

        String sendinfo = ParamUtil.getParameter(request,"sendinfo");
        String sendwayinfo = ParamUtil.getParameter(request,"sendwaylist");
        String paywayinfo = ParamUtil.getParameter(request,"paywaylist");
        String productinfo = ParamUtil.getParameter(request,"productlist");
        String editaddress = ParamUtil.getParameter(request,"editaddress");
        String addressimages = ParamUtil.getParameter(request,"addressimage");
        String sendwaysubmit = ParamUtil.getParameter(request,"sendwaysubmit");
        String sendwayimages = ParamUtil.getParameter(request,"sendwayimage");
        String paywaysubmit = ParamUtil.getParameter(request,"paywaysubmit");
        String paywayimage = ParamUtil.getParameter(request,"paywayimage");
        String ordersubmit = ParamUtil.getParameter(request,"ordersubmit");
        String orderimage = ParamUtil.getParameter(request,"orderimage");
        int usescore = ParamUtil.getIntParameter(request,"usescore",0);
        String sendwaydisplaylist = ParamUtil.getParameter(request,"sendwaydisplaylist");
        String editsendway = ParamUtil.getParameter(request,"editsendway");
        String editsendwayimage  = ParamUtil.getParameter(request,"editsendwayimage");
         String paywaydisplaylist = ParamUtil.getParameter(request,"paywaydisplaylist");
        String editspayway = ParamUtil.getParameter(request,"editpayway");
        String editpaywayimage  = ParamUtil.getParameter(request,"editpaywayimage");

        String invoiceinfo = ParamUtil.getParameter(request,"invoicelist");
        String invoicesubmit = ParamUtil.getParameter(request,"invoicesubmit");
        String invoiceimage = ParamUtil.getParameter(request,"invoiceimage");
        String invoicedisplaylist = ParamUtil.getParameter(request,"invoicedisplaylist");
        String editinvoice = ParamUtil.getParameter(request,"editinvoice");
        String editinvoiceimage = ParamUtil.getParameter(request,"editinvoiceimage");
        String content ="[TAG][ORDER_RESULT][SITEID]"+siteID+"[/SITEID][SITENAME]" + sitename + "[/SITENAME]"+
                        "[CHINESENAME]"+cname+"[/CHINESENAME][NOTES]"+notes+"[/NOTES]" +
                        "[ADDRESSINFO]<!{^CDATA{^" + sendinfo + ">^}^}>[/ADDRESSINFO][SENDWAY]<!{^CDATA{^" + sendwayinfo + ">^}^}>[/SENDWAY]"+
                "[PAYWAY]<!{^CDATA{^" + paywayinfo + ">^}^}>[/PAYWAY]" +
                "[SENDWAYDISPLAY]<!{^CDATA{^" + sendwaydisplaylist + ">^}^}>[/SENDWAYDISPLAY]"+
                "[PAYWAYDISPLAY]<!{^CDATA{^" + paywaydisplaylist + ">^}^}>[/PAYWAYDISPLAY]"+
                "[PRODUCTINFO]<!{^CDATA{^" + productinfo + ">^}^}>[/PRODUCTINFO]"+
                "[INVOICEINFO]<!{^CDATA{^" + invoiceinfo + ">^}^}>[/INVOICEINFO]"+
                "[INVOICEDISPLAYINFO]<!{^CDATA{^" + invoicedisplaylist + ">^}^}>[/INVOICEDISPLAYINFO]"+
                "[ADDRESSSUBMIT]" + editaddress + "[/ADDRESSSUBMIT]"+
                "[ADDRESSIMAGE]" + addressimages + "[/ADDRESSIMAGE]"+
                "[SENDWAYSUBMIT]" + sendwaysubmit + "[/SENDWAYSUBMIT]"+
                "[SENDWAYIMAGE]" + sendwayimages + "[/SENDWAYIMAGE]"+
                "[PAYWAYSUBMIT]" + paywaysubmit + "[/PAYWAYSUBMIT]"+
                "[PAYWAYIMAGE]" + paywayimage + "[/PAYWAYIMAGE]"+
                "[ORDERSUBMIT]" + ordersubmit + "[/ORDERSUBMIT]"+
                "[ORDERIMAGE]" + orderimage + "[/ORDERIMAGE]"+
                "[SENDWAYDISPLAYSUBMIT]" + editsendway + "[/SENDWAYDISPLAYSUBMIT]"+
                "[SENDWAYDISPLAYIMAGE]" + editsendwayimage + "[/SENDWAYDISPLAYIMAGE]"+
                "[PAYWAYDISPLAYSUBMIT]" + editspayway + "[/PAYWAYDISPLAYSUBMIT]"+
                "[PAYWAYDISPLAYIMAGE]" + editpaywayimage + "[/PAYWAYDISPLAYIMAGE]"+
                "[INVOICESUBMIT]" + invoicesubmit + "[/INVOICESUBMIT]"+
                "[INVOICEIMAGE]" + invoiceimage + "[/INVOICEIMAGE]"+
                "[EDITINVOICE]" + editinvoice + "[/EDITINVOICE]"+
                "[EDITINVOICEIMAGE]" + editinvoiceimage + "[/EDITINVOICEIMAGE]"+
                "[/ORDER_RESULT]"+ "[/TAG]";
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

        String viewer = request.getHeader("user-agent");
        String markname = "订单生成";

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
    String addressinfo = "";
    String sendway = "";
    String paywayinfo = "";
    String productinfo = "";
    String addresssubmit = "";
    String addressimage = "";
    String sendwaysubmit = "";
    String sendwayimage = "";
    String paywaysubmit = "";
    String paywayimage = "";
    String ordersubmit = "";
    String orderimage = "";
    String sendwaydisplaylist = "";
    String sendwaydisplaysubmit = "";
    String sendwaydisplayimage = "";
    String paywaydisplaylist = "";
    String paywaydisplaysubmit = "";
    String paywaydisplayimage = "";
    String invoiceinfo = "";
    String invoicesubmit = "";
    String invoicesubmitimage = "";
    String invoicedisplaylist = "";
    String invoicedisplaysubmit = "";
    String invoicedisplayimage = "";
     if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");

        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);


        cname = properties.getProperty(properties.getName().concat(".CHINESENAME"));
        addressinfo = properties.getProperty(properties.getName().concat(".ADDRESSINFO"));
        if (addressinfo != null) addressinfo = addressinfo.substring(0,addressinfo.length() -1);
        sendway = properties.getProperty(properties.getName().concat(".SENDWAY"));
        if (sendway != null) sendway = sendway.substring(0,sendway.length() -1);
        paywayinfo = properties.getProperty(properties.getName().concat(".PAYWAY"));
        if (paywayinfo != null) paywayinfo = paywayinfo.substring(0,paywayinfo.length() -1);
         productinfo = properties.getProperty(properties.getName().concat(".PRODUCTINFO"));
        if (productinfo != null) productinfo = productinfo.substring(0,productinfo.length() -1);
         sendwaydisplaylist = properties.getProperty(properties.getName().concat(".SENDWAYDISPLAY"));
        if (sendwaydisplaylist != null) sendwaydisplaylist = sendwaydisplaylist.substring(0,sendwaydisplaylist.length() -1);
         paywaydisplaylist = properties.getProperty(properties.getName().concat(".PAYWAYDISPLAY"));
        if (paywaydisplaylist != null) paywaydisplaylist = paywaydisplaylist.substring(0,paywaydisplaylist.length() -1);

          invoiceinfo = properties.getProperty(properties.getName().concat(".INVOICEINFO"));
        if (invoiceinfo != null) invoiceinfo = invoiceinfo.substring(0,invoiceinfo.length() -1);

         invoicedisplaylist = properties.getProperty(properties.getName().concat(".INVOICEDISPLAYINFO"));
        if (invoicedisplaylist != null) invoicedisplaylist = invoicedisplaylist.substring(0,invoicedisplaylist.length() -1);

        if(cname == null) cname = "订单生成";
        notes = properties.getProperty(properties.getName().concat(".NOTES"));
        if (notes == null || notes.equalsIgnoreCase("null")) notes = "";

         addresssubmit = properties.getProperty(properties.getName().concat(".ADDRESSSUBMIT"));
         if(addresssubmit == null || addresssubmit.equalsIgnoreCase("null")) addresssubmit = "";

         addressimage = properties.getProperty(properties.getName().concat(".ADDRESSIMAGE"));
         if(addressimage == null || addressimage.equalsIgnoreCase("null")) addressimage = "";

         sendwaysubmit = properties.getProperty(properties.getName().concat(".SENDWAYSUBMIT"));
         if(sendwaysubmit == null || sendwaysubmit.equalsIgnoreCase("null")) sendwaysubmit = "";

         sendwayimage = properties.getProperty(properties.getName().concat(".SENDWAYIMAGE"));
         if(sendwayimage == null || sendwayimage.equalsIgnoreCase("null")) sendwayimage = "";

         paywaysubmit = properties.getProperty(properties.getName().concat(".PAYWAYSUBMIT"));
         if(paywaysubmit == null || paywaysubmit.equalsIgnoreCase("null")) paywaysubmit = "";

         paywayimage = properties.getProperty(properties.getName().concat(".PAYWAYIMAGE"));
         if(paywayimage == null || paywayimage.equalsIgnoreCase("null")) paywayimage = "";

         ordersubmit = properties.getProperty(properties.getName().concat(".ORDERSUBMIT"));
         if(ordersubmit == null || ordersubmit.equalsIgnoreCase("null")) ordersubmit = "";

         orderimage = properties.getProperty(properties.getName().concat(".ORDERIMAGE"));
         if(orderimage == null || orderimage.equalsIgnoreCase("null")) orderimage = "";

         sendwaydisplaysubmit = properties.getProperty(properties.getName().concat(".SENDWAYDISPLAYSUBMIT"));
         if(sendwaydisplaysubmit == null || sendwaydisplaysubmit.equalsIgnoreCase("null")) sendwaydisplaysubmit = "";

         sendwaydisplayimage = properties.getProperty(properties.getName().concat(".SENDWAYDISPLAYIMAGE"));
         if(sendwaydisplayimage == null || sendwaydisplayimage.equalsIgnoreCase("null")) sendwaydisplayimage = "";

         paywaydisplaysubmit = properties.getProperty(properties.getName().concat(".PAYWAYDISPLAYSUBMIT"));
         if(paywaydisplaysubmit == null || paywaydisplaysubmit.equalsIgnoreCase("null")) paywaydisplaysubmit = "";

         paywaydisplayimage = properties.getProperty(properties.getName().concat(".PAYWAYDISPLAYIMAGE"));
         if(paywaydisplayimage == null || paywaydisplayimage.equalsIgnoreCase("null")) paywaydisplayimage = "";

         invoicesubmitimage = properties.getProperty(properties.getName().concat(".INVOICEIMAGE"));
         if(invoicesubmitimage == null || invoicesubmitimage.equalsIgnoreCase("null")) invoicesubmitimage = "";

         invoicesubmit = properties.getProperty(properties.getName().concat(".INVOICESUBMIT"));
         if(invoicesubmit == null || invoicesubmit.equalsIgnoreCase("null")) invoicesubmit = "";

         invoicedisplaysubmit = properties.getProperty(properties.getName().concat(".EDITINVOICE"));
         if(invoicedisplaysubmit == null || invoicedisplaysubmit.equalsIgnoreCase("null")) invoicedisplaysubmit = "";

         invoicedisplayimage = properties.getProperty(properties.getName().concat(".EDITINVOICEIMAGE"));
         if(invoicedisplayimage == null || invoicedisplayimage.equalsIgnoreCase("null")) invoicedisplayimage = "";
    }
%>

<html>
<head>
    <base target="_self" >
    <title>订单生成</title>
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
            var comminfo =  document.markform.sendinfo.value;
            var sendwayinfo = document.markform.sendwaylist.value;
            var paywayinfo = document.markform.paywaylist.value;
            var productinfo = document.markform.productlist.value;
            var sendwaydisplay = document.markform.sendwaydisplaylist.value;
            var paywaydisplay = document.markform.paywaydisplaylist.value;
            if(comminfo == "")
            {
                alert("请输入送货地址样式！");

                return false;
            }
            if(sendwayinfo == "")
            {
                alert("请输入送货方式样式！");

                return false;
            }
            if(sendwaydisplay == "")
            {
                alert("请输入送货方式回显样式！");

                return false;
            }
            if(paywayinfo == "")
            {
                alert("请输入支付方式样式！");
                
                return false;
            }
            if(paywaydisplay == "")
            {
                alert("请输入支付方式回显样式！");

                return false;
            }
            if(productinfo == "")
            {
                alert("请输入商品列表样式！");

                return false;
            }
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                markform.action = "addOrderGenerate.jsp";
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
                insertAtCaret(document.markform.sendinfo, str);
            document.markform.fields.options[0].selected = true;
        }
        function SelectProductMark()
        {
            var i = document.markform.product.selectedIndex;
            var str = "<" + "%%" + document.markform.product.options[i].value + "%%" + ">";

            if (str != "" && str != "1" && document.markform.product.options[i].value != "-1")
                insertAtCaret(document.markform.productlist, str);
            document.markform.product.options[0].selected = true;
        }
        function SelectSendwayMarkName()
        {
            var i = document.markform.sendway.selectedIndex;
            var values = document.markform.sendway.options[i].value;
            var str = "";
            if(values == "submitbutton"){
                str = "<" + "%%" + values + "%%" + ">";
            }else{
                var names = values.substring(0,values.lastIndexOf("_"));
                var ids = values.substring(values.lastIndexOf("_")+1,values.length);
                str = "<!--"+names+"--><" + "%%" + ids + "%%" + ">";
            }

            if (str != "" && str != "1" && document.markform.sendway.options[i].value != "-1")
                insertAtCaret(document.markform.sendwaylist, str);
            document.markform.sendway.options[0].selected = true;
        }
        function SelectDisplaySendwayMarkName()
        {
            var i = document.markform.sendwaydisplay.selectedIndex;
            var values = document.markform.sendwaydisplay.options[i].value;
            //var names = values.substring(0,values.lastIndexOf("_"));
            //var ids = values.substring(values.lastIndexOf("_")+1,values.length);
            var str = "<" + "%%" + values + "%%" + ">";

            if (str != "" && str != "1" && document.markform.sendwaydisplay.options[i].value != "-1")
                insertAtCaret(document.markform.sendwaydisplaylist, str);
            document.markform.sendwaydisplay.options[0].selected = true;
        }
        function SelectPaywayMarkName()
        {
            var i = document.markform.payway.selectedIndex;
            var values = document.markform.payway.options[i].value;
            var str = "";
            if(values == "submitbutton"){
                str = "<" + "%%" + values + "%%" + ">";
            }else{
                var names = values.substring(0,values.lastIndexOf("_"));
                var ids = values.substring(values.lastIndexOf("_")+1,values.length);
                str = "<!--"+names+"--><" + "%%" + ids + "%%" + ">";
            }
            /*var names = values.substring(0,values.lastIndexOf("_"));
            var ids = values.substring(values.lastIndexOf("_")+1,values.length);
            var str = "<!--"+names+"--><" + "%%" + ids + "%%" + ">";*/
            if (str != "" && str != "1" && document.markform.payway.options[i].value != "-1")
                insertAtCaret(document.markform.paywaylist, str);
            document.markform.payway.options[0].selected = true;
        }
        function SelectDisplayPaywayMarkName()
        {
            var i = document.markform.paywaydisplay.selectedIndex;
            var values = document.markform.paywaydisplay.options[i].value;
            var str = "<" + "%%" + values + "%%" + ">";
            if (str != "" && str != "1" && document.markform.paywaydisplay.options[i].value != "-1")
                insertAtCaret(document.markform.paywaydisplaylist, str);
            document.markform.paywaydisplay.options[0].selected = true;
        }
        function SelectDisplaInvoiceMarkName()
        {
            var i = document.markform.invoicedisplay.selectedIndex;
            var values = document.markform.invoicedisplay.options[i].value;
            var str = "<" + "%%" + values + "%%" + ">";
            if (str != "" && str != "1" && document.markform.invoicedisplay.options[i].value != "-1")
                insertAtCaret(document.markform.invoicedisplaylist, str);
            document.markform.invoicedisplay.options[0].selected = true;
        }
        function SelectInvoiceMarkName()
        {
            var i = document.markform.invoice.selectedIndex;
            var values = document.markform.invoice.options[i].value;
            var str = "";

            str = "<" + "%%" + values + "%%" + ">";

            if (str != "" && str != "1" && document.markform.invoice.options[i].value != "-1")
                insertAtCaret(document.markform.invoicelist, str);
            document.markform.invoice.options[0].selected = true;
        }
        function storeCaret(textEl)
        {
            if (textEl.createTextRange)
                textEl.caretPos = document.selection.createRange().duplicate();
        }
        /*function selectpic(type){
            //alert("hello word");
            window.open("selectsubmitpic.jsp?type="+type, "", "height=500, width=800, toolbar=yes, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
        }
        function upload(type)
        {
            //alert("hello word");
            window.open("uploadsubmitpic.jsp?type="+type, "", "height=200, width=600 toolbar=yes, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
        }*/
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
<table width="100%" border="0" align="center">
    <form name="markform">
        <input type=hidden name=doCreate value=true>
        <input type=hidden name=saveas value=false>
        <input type=hidden name=column value="<%=columnID%>">
        <input type=hidden name=type value=13>
       <input type="hidden" name="mark" value="<%=markID%>">
        <tr height="30">
            <td width="15%">请选择送货地址标记：</td>
            <td width="85%" align="left"><select id="fieldname" name="fields" onchange="SelectMarkName();">
                <option value ="-1">请选择送货地址标记</option>
                 <option value ="connname">收货人</option>
                <option value ="province">省份</option>
                <option value ="city">市/县</option>
                <option value ="zone">地区</option>
                <option value ="address">详细地址</option>
                <option value ="zip">邮政编码</option>
                <option value ="phone">电话</option>                
                <option value ="editsubmit">修改按钮</option>
            </select>
            </td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="15%">
                送货地址样式：</td><td align="left" valign="top" width="85%"><textarea name="sendinfo" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=addressinfo==null?"":addressinfo%></textarea></td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                修改送货地址按钮类型：<select name="editaddress">
                <option value="submit" <%if(addresssubmit != null && addresssubmit.equals("submit")){%>selected<%}%>>提交</option>
                <option value ="images" <%if(addresssubmit != null && addresssubmit.equals("images")){%>selected<%}%>>图片</option>
                <option value ="links" <%if(addresssubmit != null && addresssubmit.equals("links")){%>selected<%}%>>文字连接</option>
                </select>
            </td>
            <td align="left" valign="top" width="75%">
             选择图片：<input id="addressimage" name="addressimage" type="text" value="<%=addressimage%>" readonly ><a href="#" onclick="javascript:selectpic(2);">选择已有图片</a>  <a href="#" onclick="javascript:upload(2);">上传新图片</a>   
            </td>
        </tr>
        <tr height="1">
            <td height="1" width="100%" colspan="2">
                _____________________________________________________________________________________________________________________________________________
            </td>
        </tr>
        <tr height="30">
            <td width="15%">请选择送货方式标记：</td>
            <td width="85%" align="left"><select id="sendway" name="sendway" onchange="SelectSendwayMarkName();">
                <option value ="-1">请选择送货标记</option>
                <option value ="submitbutton">确认按钮</option>
                <option value ="linktime">送货时间</option>
                <%
                    if(sendwaylist.size() > 0){
                    for(int i = 0; i < sendwaylist.size(); i ++)
                    {
                        Fee fee = (Fee)sendwaylist.get(i);
                        String name = fee.getCname()==null?"": StringUtil.gb2iso4View(fee.getCname());
                        String sendwaynotes = fee.getNotes()==null?"":"("+StringUtil.gb2iso4View(fee.getNotes())+")";
                        String feestr = "";
                        if(fee.getFee()==0){
                            feestr = "免费送货";
                        }
                        else{
                            feestr = String.valueOf(fee.getFee());
                        }
                        String values = name + "_"+fee.getId();
                        String displays = name+":"+feestr+sendwaynotes;
                %>
                <option value="<%=values%>"><%=displays%></option>
                <%}}else{%>
                <option value="同城送货(免费)_0">同城送货(免费)_0</option>
                <%}%>
            </select>（一个标记相当于一个单选按钮）
            </td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="15%">
                送货方式样式：</td><td align="left" valign="top" width="85%"><textarea name="sendwaylist" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=sendway==null?"":sendway%></textarea></td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                确认送货方式按钮类型：<select name="sendwaysubmit">
                <option value="submit" <%if(sendwaysubmit != null && sendwaysubmit.equals("submit")){%>selected<%}%>>提交</option>
                                <option value ="images" <%if(sendwaysubmit != null && sendwaysubmit.equals("images")){%>selected<%}%>>图片</option>
            <option value ="links" <%if(sendwaysubmit != null && sendwaysubmit.equals("links")){%>selected<%}%>>文字连接</option>
            </select>
            </td>
            <td align="left" valign="top" width="75%">
             选择图片：<input id="sendwayimage" name="sendwayimage" type="text" value="<%=sendwayimage%>" readonly ><a href="#" onclick="javascript:selectpic(3);">选择已有图片</a>  <a href="#" onclick="javascript:upload(3);">上传新图片</a>
            </td>
        </tr>
        <tr height="30">
            <td width="15%">请选择送货方式回显标记：</td>
            <td width="85%" align="left"><select id="sendwaydisplay" name="sendwaydisplay" onchange="SelectDisplaySendwayMarkName();">
                <option value ="-1">请选择送货方式回显标记</option>
                <option value ="editsubmit">修改按钮</option>
                <option value ="sendwayname">送货方式名称</option>
                <option value ="linktime">送货时间</option>
            </select>
            </td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="15%">
                送货方式回显样式：</td><td align="left" valign="top" width="85%"><textarea name="sendwaydisplaylist" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=sendwaydisplaylist==null?"":sendwaydisplaylist%></textarea></td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                修改送货方式按钮类型：<select name="editsendway">
                <option value="submit" <%if(sendwaydisplaysubmit != null && sendwaydisplaysubmit.equals("submit")){%>selected<%}%>>提交</option>
                                <option value ="images" <%if(sendwaydisplaysubmit != null && sendwaydisplaysubmit.equals("images")){%>selected<%}%>>图片</option>
            <option value ="links" <%if(sendwaydisplaysubmit != null && sendwaydisplaysubmit.equals("links")){%>selected<%}%>>文字连接</option>
            </select>
            </td>
            <td align="left" valign="top" width="75%">
             选择图片：<input id="editsendwayimage" name="editsendwayimage" type="text" value="<%=sendwaydisplayimage%>" readonly ><a href="#" onclick="javascript:selectpic(7);">选择已有图片</a>  <a href="#" onclick="javascript:upload(7);">上传新图片</a>
            </td>
        </tr>
        <tr height="1">
            <td height="1" width="100%" colspan="2">
                _____________________________________________________________________________________________________________________________________________
            </td>
        </tr>
        <tr height="30">
            <td width="15%">请选择支付方式标记：</td>
            <td width="85%" align="left"><select id="payway" name="payway" onchange="SelectPaywayMarkName();">
                <option value ="-1">请选择支付方式标记</option>
                <option value ="submitbutton">确认按钮</option>
                <%
                    for(int i = 0; i < paylist.size();i ++){
                         SendWay payway = (SendWay)paylist.get(i);
                        String name = payway.getCname()==null?"": StringUtil.gb2iso4View(payway.getCname());
                        String paynotes = payway.getNotes()==null?"":"("+StringUtil.gb2iso4View(payway.getNotes())+")";
                %>
                <option value="<%=name+"_"+String.valueOf(payway.getId())%>"><%=name+paynotes%></option>
                <%}%>
            </select>（一个标记相当于一个单选按钮）
            </td>
        </tr>
        <tr height=24>
            <td  align="left"  valign="top" width="15%">
                支付方式样式：</td><td align="left" valign="top" width="85%"><textarea name="paywaylist" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=paywayinfo==null?"":paywayinfo%></textarea></td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                确认支付方式按钮类型：<select name="paywaysubmit">
                <option value="submit" <%if(paywaysubmit != null && paywaysubmit.equals("submit")){%>selected<%}%>>提交</option>
                <option value ="images" <%if(paywaysubmit != null && paywaysubmit.equals("images")){%>selected<%}%>>图片</option>
                <option value ="links" <%if(paywaysubmit != null && paywaysubmit.equals("links")){%>selected<%}%>>文字连接</option>
                </select>
            </td>
            <td align="left" valign="top" width="75%">
             选择图片：<input id="paywayimage" name="paywayimage" type="text" value="<%=paywayimage%>" readonly ><a href="#" onclick="javascript:selectpic(4);">选择已有图片</a>  <a href="#" onclick="javascript:upload(4);">上传新图片</a>
            </td>
        </tr>
        <tr height="30">
            <td width="15%">请选择支付方式回显标记：</td>
            <td width="85%" align="left"><select id="paywaydisplay" name="paywaydisplay" onchange="SelectDisplayPaywayMarkName();">
                <option value ="-1">请选择支付方式回显标记</option>
                <option value ="editsubmit">修改按钮</option>
                <option value ="paywayname">支付方式名称</option>
            </select>
            </td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="15%">
                支付方式回显样式：</td><td align="left" valign="top" width="85%"><textarea name="paywaydisplaylist" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=paywaydisplaylist==null?"":paywaydisplaylist%></textarea></td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                修改支付方式按钮类型：<select name="editpayway">
                <option value="submit" <%if(paywaydisplaysubmit != null && paywaydisplaysubmit.equals("submit")){%>selected<%}%>>提交</option>
                                <option value ="images" <%if(paywaydisplaysubmit != null && paywaydisplaysubmit.equals("images")){%>selected<%}%>>图片</option>
            <option value ="links" <%if(paywaydisplaysubmit != null && paywaydisplaysubmit.equals("links")){%>selected<%}%>>文字连接</option>
            </select>
            </td>
            <td align="left" valign="top" width="75%">
             选择图片：<input id="editpaywayimage" name="editpaywayimage" type="text" value="<%=paywaydisplayimage%>" readonly ><a href="#" onclick="javascript:selectpic(8);">选择已有图片</a>  <a href="#" onclick="javascript:upload(8);">上传新图片</a>
            </td>
        </tr>
        <tr height="1">
            <td height="1" width="100%" colspan="2">
                _____________________________________________________________________________________________________________________________________________
            </td>
        </tr>
        <tr height="30">
            <td width="15%">请选择发票信息标记：</td>
            <td width="85%" align="left"><select id="invoice" name="invoice" onchange="SelectInvoiceMarkName();">
                <option value ="-1">请选择发票信息标记</option>
                <option value ="submitbutton">确认按钮</option>
                <option value ="invoicetype">发票类型</option>
                <option value ="title">发票抬头</option>
                <option value ="companyname">单位名称</option>
                <option value ="content">发票内容</option>
                <option value ="identification">纳税人识别号</option>
                <option value ="registeraddress">注册地址</option>
                <option value ="phone">注册电话</option>
                <option value ="bankname">开户银行</option>
                <option value ="bankaccount">银行帐号</option>
            </select>
            </td>
        </tr>
        <tr height=24>
            <td  align="left"  valign="top" width="15%">
                发票信息样式：</td><td align="left" valign="top" width="85%"><textarea name="invoicelist" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=invoiceinfo==null?"":invoiceinfo%></textarea></td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                确认发票信息按钮类型：<select name="invoicesubmit">
                <option value="submit" <%if(invoicesubmit != null && invoicesubmit.equals("submit")){%>selected<%}%>>提交</option>
                <option value ="images" <%if(invoicesubmit != null && invoicesubmit.equals("images")){%>selected<%}%>>图片</option>
                <option value ="links" <%if(invoicesubmit != null && invoicesubmit.equals("links")){%>selected<%}%>>文字连接</option>
                </select>
            </td>
            <td align="left" valign="top" width="75%">
             选择图片：<input id="invoiceimage" name="invoiceimage" type="text" value="<%=invoicesubmitimage%>" readonly ><a href="#" onclick="javascript:selectpic(9);">选择已有图片</a>  <a href="#" onclick="javascript:upload(9);">上传新图片</a>
            </td>
        </tr>
        <tr height="30">
            <td width="15%">请选择发票信息回显标记：</td>
            <td width="85%" align="left"><select id="invoicedisplay" name="invoicedisplay" onchange="SelectDisplaInvoiceMarkName();">
                <option value ="-1">请选择发票信息回显标记</option>
                <option value ="editsubmit">修改按钮</option>
                <option value ="invoicetype">发票类型</option>
                <option value ="title">发票抬头</option>
                <option value ="content">发票内容</option>
            </select>
            </td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="15%">
                发票信息回显样式：</td><td align="left" valign="top" width="85%"><textarea name="invoicedisplaylist" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=invoicedisplaylist==null?"":invoicedisplaylist%></textarea></td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                修改发票信息按钮类型：<select name="editinvoice">
                <option value="submit" <%if(invoicedisplaysubmit != null && invoicedisplaysubmit.equals("submit")){%>selected<%}%>>提交</option>
                                <option value ="images" <%if(invoicedisplaysubmit != null && invoicedisplaysubmit.equals("images")){%>selected<%}%>>图片</option>
            <option value ="links" <%if(invoicedisplaysubmit != null && invoicedisplaysubmit.equals("links")){%>selected<%}%>>文字连接</option>
            </select>
            </td>
            <td align="left" valign="top" width="75%">
             选择图片：<input id="editinvoiceimage" name="editinvoiceimage" type="text" value="<%=invoicedisplayimage%>" readonly ><a href="#" onclick="javascript:selectpic(10);">选择已有图片</a>  <a href="#" onclick="javascript:upload(10);">上传新图片</a>
            </td>
        </tr>
        <tr height="1">
            <td height="1" width="100%" colspan="2">
                _____________________________________________________________________________________________________________________________________________
            </td>
        </tr>
         <tr height="30">
            <td width="15%">请选择商品属性标记：</td>
            <td width="85%" align="left"><select id="product" name="product" onchange="SelectProductMark();">
                <option value ="-1">请选择商品属性标记</option>
                <option value ="productno">商品编号</option>
                <option value ="productname">商品名称</option>
                <option value ="productnameurl">商品名称（URL）</option>
                <option value ="saleprice">商品售价</option>
                <option value ="marketprice">商品市场价</option>
                <option value ="productnum">商品数量</option>
                <option value ="score">商品积分</option>

                <option value ="totalscore">购物车商品总积分</option>
                <option value ="historyscore">历史总积分</option>
                <option value ="nowscore">当前总积分</option>
                <option value ="producttotal">商品价格小计</option>
                <option value ="totalprice">商品价格总计</option>
                <option value ="submitorder">提交订单按钮</option>
                <option value ="usescores">使用积分消费</option>
                <option value ="fee">运费</option>
                <option value ="usecard">使用购物券消费</option>
                <option value ="scoresfee">积分抵扣</option>
                <option value ="cardfee">购物券抵扣</option>
                <option value ="begin">循环开始</option>
                <option value ="end">循环结束</option>
            </select>
            </td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="15%">
                商品列表样式：</td><td align="left" valign="top" width="85%"><textarea name="productlist" style="width: 700px; height: 217px" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);"><%=productinfo == null?"":productinfo%></textarea></td>
        </tr>
        <tr height=24>
            <td align="left" valign="top" width="25%">
                提交订单按钮类型：<select name="ordersubmit">
                <option value="submit" <%if(ordersubmit != null && ordersubmit.equals("submit")){%>selected<%}%>>提交</option>
                                <option value ="images" <%if(ordersubmit != null && ordersubmit.equals("images")){%>selected<%}%>>图片</option>
            <option value ="links" <%if(ordersubmit != null && ordersubmit.equals("links")){%>selected<%}%>>文字连接</option>                    
            </select>
            </td>
            <td align="left" valign="top" width="75%">
             选择图片：<input id="orderimage" name="orderimage" type="text" value="<%=orderimage%>" readonly ><a href="#" onclick="javascript:selectpic(5);">选择已有图片</a>  <a href="#" onclick="javascript:upload(5);">上传新图片</a>   
            </td>
        </tr>
        <tr height="1">
            <td height="1" width="100%" colspan="2">
                _____________________________________________________________________________________________________________________________________________
            </td>
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
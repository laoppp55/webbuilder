<%@page import="com.bizwink.cms.kings.deliverymaster.DeliveryMaster,
                com.bizwink.cms.kings.deliverymaster.DeliveryMasterPeer,
                com.bizwink.cms.kings.deliverymaster.IDeliveryMasterManager,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil,
                com.bizwink.cms.util.SessionUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bizwink.cms.kings.deliverydetail.*" %>
<script language="JavaScript" src="../images/setday.js"></script>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    String id = ParamUtil.getParameter(request, "deliveryid");
    String deliverydate1 = ParamUtil.getParameter(request, "deliverydate");
    String customerid = ParamUtil.getParameter(request, "customerid");
    String salesmanid = ParamUtil.getParameter(request, "salesmanid");
    String deliveryproperty = ParamUtil.getParameter(request, "deliveryproperty");
    String deliveryaddress = ParamUtil.getParameter(request, "deliveryaddress");
    String invoiceno = ParamUtil.getParameter(request, "invoiceno");
    String customerorderno = ParamUtil.getParameter(request, "customerorderno");
    int subtotal = ParamUtil.getIntParameter(request, "subtotal", 0);
    int valueaddtax = ParamUtil.getIntParameter(request, "valueaddtax", 0);
    int amount = ParamUtil.getIntParameter(request, "amount", 0);
    int accountreceivable = ParamUtil.getIntParameter(request, "accountreceivable", 0);
    int received = ParamUtil.getIntParameter(request, "received", 0);
    String limitdate1 = ParamUtil.getParameter(request, "limitdate");
    DeliveryMaster dm = new DeliveryMaster();
    IDeliveryMasterManager delMgr = DeliveryMasterPeer.getInstance();

    IDeliveryDetailManager ddMgr = DeliveryDetailPeer.getInstance();
    DeliveryDetail dd = new DeliveryDetail();
    List list1 = new ArrayList();
    List listdd = new ArrayList();
    list1 = ddMgr.getByIdDeliveryDetails(id);

    if (startflag == 1) {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp deliverydate = new Timestamp(sf.parse(deliverydate1).getTime());
        Timestamp limitdate = new Timestamp(sf.parse(limitdate1).getTime());
        dm.setSiteid(siteid);
        dm.setDeliveryDate(deliverydate);
        dm.setCustomerID(customerid);
        dm.setSalesManID(salesmanid);
        dm.setDeliveryProperty(deliveryproperty);
        dm.setDeliveryAddress(deliveryaddress);
        dm.setInvoiceNo(invoiceno);
        dm.setCustomerOrderNo(customerorderno);
        dm.setSubTotal(subtotal);
        dm.setValueAddTax(valueaddtax);
        dm.setAmount(amount);
        dm.setAccountReceivable(accountreceivable);
        dm.setReceived(received);
        dm.setLimitDate(limitdate);
        if(list1!=null){
            for(int i=0;i<list1.size();i++){
                dd = new DeliveryDetail();
                int pdid = ParamUtil.getIntParameter(request,"pdid" + i,0);
                int productid = ParamUtil.getIntParameter(request,"a0" + i,0);
                int salesquantity = ParamUtil.getIntParameter(request,"a1" + i,0);
                int salesunitprice = ParamUtil.getIntParameter(request,"a2" + i,0);
                int salesamount = ParamUtil.getIntParameter(request,"a3" + i,0);
                dd.setId(pdid);
                dd.setProductID(String.valueOf(productid));
                dd.setSalesQuantity(salesquantity);
                dd.setSalesUnitPrice(salesunitprice);
                dd.setSalesAmount(salesamount);
                listdd.add(dd);
            }
        }        
        delMgr.updateDeliveryMasterhe(dm, id,listdd);
        response.sendRedirect("deliverymaster.jsp");
    }
    dm = delMgr.getByIdDeliveryMaster(id);
%>

<HTML>
<HEAD><TITLE>存货变动信息录入</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
    <SCRIPT language=javascript>
        function check()
        {
            if (form1.deliveryid.value == "")
            {
                alert("请输入出货单单号！");
                return false;
            }
            if (form1.deliverydate.value == "") {
                alert("请输入出货日期");
                return false;
            }
            if (form1.customerid.value == "") {
                alert("请输入客户编号");
                return false;
            }
            if (form1.salesmanid.value == "") {
                alert("请输入业务员编号");
                return false;
            }
            if (form1.deliveryproperty.value != "1" && form1.deliveryproperty.value != "2") {
                alert("出货单属性只能输入1或2");
                return false;
            }
            return true;
        }

        function msg1() {
            for (var i = 0; i < form1.deliverypropertys.length; i++)
            {
                if (form1.deliverypropertys[i].selected)
                    form1.deliveryproperty.value = form1.deliverypropertys[i].value;
            }
        }

        function goto()
        {
            form1.action = "deliverymaster.jsp";
            form1.submit();
        }
        function chenk(type){
            //window.open("../product/index.jsp","","");
            str = window.showModalDialog('../product/index.jsp?id='+<%=1%>,'example05','dialogWidth:1000px;dialogHeight:600px.dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes')
            if(str!=undefined){
                //alert(str);

                var types = 'a0';
                types += type;
                //form1.types.value = str;
                document.getElementById(types).value = str;
            }
        }          
    </SCRIPT>

    <META content="MSHTML 6.00.2800.1479" name=GENERATOR>
</HEAD>
<BODY bgColor=#ffffff>
<FORM name=form1 action=edit.jsp method=post>
    <INPUT type=hidden value=1 name=startflag>
    <CENTER>
        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80%
               borderColorLight=#008000 border=1>
            <TBODY>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>修改出货单信息</P></TD>
            </TR>
            <TR height=32>
                <TD align=right>出货单单号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=deliveryid value="<%=id%>">
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>出货日期：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=deliverydate
                                            value="<%=dm.getDeliveryDate().toString().substring(0,10)%>"
                                            onfocus="setday(this)">
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>客户编号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=customerid value="<%=dm.getCustomerID()%>">
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>业务员编号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=salesmanid value="<%=dm.getSalesManID()%>">
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>出货单属性：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=deliveryproperty value="<%=dm.getDeliveryProperty()%>"
                                            readonly>
                    <select name="deliverypropertys" onchange="msg1()">
                        <option value="1">出货</option>
                        <option value="2">出货退出</option>
                    </select>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>送货地址：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=deliveryaddress value="<%=dm.getDeliveryAddress()==null?"":dm.getDeliveryAddress()%>">
            </TR>
            <TR height=32>
                <TD align=right>发票号码：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=invoiceno value="<%=dm.getInvoiceNo()==null?"":dm.getInvoiceNo()%>">
            </TR>
            <TR height=32>
                <TD align=right>订单号码：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=customerorderno value="<%=dm.getCustomerOrderNo()==null?"":dm.getCustomerOrderNo()%>">
            </TR>
            <TR height=32>
                <TD align=right>合计金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=subtotal value="<%=dm.getSubTotal()%>">
            </TR>
            <TR height=32>
                <TD align=right>营业税：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=valueaddtax value="<%=dm.getValueAddTax()%>">
            </TR>
            <TR height=32>
                <TD align=right>总计金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=amount value="<%=dm.getAmount()%>">
            </TR>
            <TR height=32>
                <TD align=right>应收金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=accountreceivable value="<%=dm.getAccountReceivable()%>">
            </TR>
            <TR height=32>
                <TD align=right>已收金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=received value="<%=dm.getReceived()%>">
            </TR>
            <TR height=32>
                <TD align=right>收款截止日：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=limitdate
                                            value="<%=dm.getLimitDate().toString().substring(0,10)%>"
                                            onfocus="setday(this)">
            </TR>
            <TR height=32>
                <TD colSpan=2><FONT
                        color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：带有*的项为必填项</FONT></TD>
            </TR>
        <TR>
            <TD align=center height=32 colspan="2">
                <table cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=100% borderColorLight=#008000 border=1>
                    <tr><td bgColor=#33ccff height="32" colspan="4" align="center">出库单明细</td></tr>
                    <tr><TD align=center width=40% height=32>商品名称</TD>
                    <TD align=center width=20% height=32>数量</TD>
                    <TD align=center width=20% height=32>单价</TD>
                    <TD align=center width=20% height=32>金额</TD>
                    </tr><%
                        if(list1 != null){
                            for(int i=0;i<list1.size();i++){
                                dd = (DeliveryDetail)list1.get(i);
                                String productid = dd.getProductID();
                                String salesquantity = Integer.toString(dd.getSalesQuantity());
                                String salesunitprice = Integer.toString(dd.getSalesUnitPrice());
                                String salesamount = Integer.toString(dd.getSalesAmount());
                    %><input type="hidden" name="pdid<%=i%>" value="<%=dd.getId()%>">
                    <tr><TD align=center width=40% height=32>&nbsp;<input size="30" name="a0<%=i%>" id="a0<%=i%>" onclick="chenk(<%=i%>);" value="<%=productid == null ? "" : productid%>" readonly></TD>
                    <TD align=center width=20% height=32>&nbsp;<input size="16" name="a1<%=i%>" id="a1<%=i%>" value="<%=salesquantity == null ? "" : salesquantity%>" ></TD>
                    <TD align=center width=20% height=32>&nbsp;<input size="16" name="a2<%=i%>" id="a2<%=i%>" value="<%=salesunitprice == null ? "" : salesunitprice%>" ></TD>
                    <TD align=center width=20% height=32>&nbsp;<input size="16" name="a3<%=i%>" id="a3<%=i%>" value="<%=salesamount == null ? "" : salesamount%>" ></TD>
                    </tr>
                    <%}}%>
                </table>
            </TD>
        </TR>              
            </TBODY>
        </TABLE>
        <P align=center><INPUT onclick="javascript:return check();" type=submit value=" 确 认 " name=Ok>&nbsp;&nbsp;
            <INPUT onclick=javascript:goto(); type=button value=返回列表 name=golist>
        </P>
    </CENTER>
</FORM>
</BODY>
</HTML>

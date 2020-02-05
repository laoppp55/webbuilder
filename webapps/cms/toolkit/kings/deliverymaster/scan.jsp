<%@page import="com.bizwink.cms.kings.deliverymaster.DeliveryMaster,
                com.bizwink.cms.kings.deliverymaster.DeliveryMasterPeer,
                com.bizwink.cms.kings.deliverymaster.IDeliveryMasterManager,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bizwink.cms.kings.deliverydetail.*" %>
<%
    String id = ParamUtil.getParameter(request, "id");
    DeliveryMaster dm = new DeliveryMaster();
    IDeliveryMasterManager delMgr = DeliveryMasterPeer.getInstance();
    dm = delMgr.getByIdDeliveryMaster(id);
    IDeliveryDetailManager ddMgr = DeliveryDetailPeer.getInstance();
    DeliveryDetail dd = new DeliveryDetail();
    List list = new ArrayList();
    list = ddMgr.getByIdDeliveryDetails(id);

    Timestamp deliverydate = dm.getDeliveryDate();
    String customerid = dm.getCustomerID();
    String salesmanid = dm.getSalesManID();
    String deliveryproperty = dm.getDeliveryProperty();
    String deliveryaddress = dm.getDeliveryAddress();
    String invoiceno = dm.getInvoiceNo();
    String customerorderno = dm.getCustomerOrderNo();
    String subtotal = Integer.toString(dm.getSubTotal());
    String valueaddtax = Integer.toString(dm.getValueAddTax());
    String amount = Integer.toString(dm.getAmount());
    String accountreceivable = Integer.toString(dm.getAccountReceivable());
    String received = Integer.toString(dm.getReceived());
    Timestamp limitdate = dm.getLimitDate();
%>
<HTML>
<HEAD><TITLE>浏览出货单信息</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
</HEAD>
<BODY>
<CENTER>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80% borderColorLight=#008000 border=1>
        <TBODY>
        <TR>
            <TD bgColor=#33ccff colSpan=2 height=32>
                <P align=center>出货单信息</P></TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>出货单单号：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=id%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>出货日期：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=deliverydate.toString().substring(0, 10)%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>客户编号：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=customerid == null ? "--" : customerid%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>业务员编号：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=salesmanid == null ? "--" : salesmanid%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>出货单属性：</TD>
            <TD align=left width=70% height=32>&nbsp;<%if(deliveryproperty != null){if(deliveryproperty.equals("1")){
                %>出货<%}else{%>出货退出<%}}%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>送货地址：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=deliveryaddress == null ? "--" : deliveryaddress%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>发票号码：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=invoiceno == null ? "--" : invoiceno%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>订单号码：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=customerorderno == null ? "--" : customerorderno%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>合计金额：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=subtotal == null ? "--" : subtotal%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>营业税：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=valueaddtax == null ? "--" : valueaddtax%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>总计金额：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=amount == null ? "--" : amount%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>应收金额：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=accountreceivable == null ? "--" : accountreceivable%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>已收金额：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=received == null ? "--" : received%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>收款截止日：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=limitdate.toString().substring(0, 10)%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32 colspan="2">
                <table cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=100% borderColorLight=#008000 border=1>
                    <tr><td bgColor=#33ccff height="32" colspan="4" align="center">出货单明细</td></tr>
                    <tr><TD align=center width=40% height=32>商品名称</TD>
                    <TD align=center width=20% height=32>数量</TD>
                    <TD align=center width=20% height=32>单价</TD>
                    <TD align=center width=20% height=32>金额</TD>
                    </tr><%
                           if(list != null){
                               for(int i = 0; i < list.size(); i++){
                                   dd = (DeliveryDetail)list.get(i);
                                   String productid = dd.getProductID();
                                   String salesquantity = Integer.toString(dd.getSalesQuantity());
                                   String salesunitprice = Integer.toString(dd.getSalesUnitPrice());
                                   String salesamount = Integer.toString(dd.getSalesAmount());

                    %>
                    <tr><TD align=center width=40% height=32>&nbsp;<%=productid == null ? "--" : productid%></TD>
                    <TD align=center width=20% height=32>&nbsp;<%=salesquantity == null ? "--" : salesquantity%></TD>
                    <TD align=center width=20% height=32>&nbsp;<%=salesunitprice == null ? "--" : salesunitprice%></TD>
                    <TD align=center width=20% height=32>&nbsp;<%=salesamount == null ? "--" : salesamount%></TD>
                    </tr>
                    <%} }%>
                </table>                
            </TD>
        </TR>
        </TBODY>
    </TABLE>
</CENTER>
<BR><BR>
<CENTER><INPUT onclick=javascript:history.go(-1); type=button value=" 返 回 ">
</CENTER>
</BODY>
</HTML>

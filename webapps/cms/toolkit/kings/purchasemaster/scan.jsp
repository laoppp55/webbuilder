<%@page import="com.bizwink.cms.kings.purchasemaster.IPurchaseMasterManager,
                com.bizwink.cms.kings.purchasemaster.PurchaseMaster,
                com.bizwink.cms.kings.purchasemaster.PurchaseMasterPeer,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.cms.kings.purchasedetail.*" %>
<%@ page import="java.util.*" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    PurchaseMaster purchasemaster = new PurchaseMaster();
    IPurchaseMasterManager pumMgr = PurchaseMasterPeer.getInstance();
    purchasemaster = pumMgr.getByIdpurchase(id);
    IPurchaseDetailManager pdMgr = PurchaseDetailPeer.getInstance();
    PurchaseDetail pd = new PurchaseDetail();
    List list = new ArrayList();
    list = pdMgr.getByIdPurchaseDetails(id);

    //String purchaseid = Integer.toString(purchasemaster.getPurchaseID());
    Timestamp purchasedate = purchasemaster.getPurchaseDate();
    String purchaseid = purchasemaster.getSupplierID();
    String supplierid = purchasemaster.getSupplierID();
    String purchaseproperty = Integer.toString(purchasemaster.getPurchaseProperty());
    String invoiceno = Integer.toString(purchasemaster.getInvoiceNo());
    String subtotal = Integer.toString(purchasemaster.getSubTotal());
    String valueaddtax = Integer.toString(purchasemaster.getValutAddTax());
    String amount = Integer.toString(purchasemaster.getAmount());
    String accountpayable = Integer.toString(purchasemaster.getAccountPayable());
    String paid = Integer.toString(purchasemaster.getPaid());
    Timestamp limitdate = purchasemaster.getLimitDate();


%>
<HTML>
<HEAD><TITLE>liu lan</TITLE>
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
                <P align=center>进货单信息</P></TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>进货单单号：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=purchaseid == null?"":purchaseid%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>进货日期：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=purchasedate.toString().substring(0, 10)%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>供应商编号：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=supplierid == null ? "--" : supplierid%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>进货单属性：</TD>
            <TD align=left width=70% height=32>&nbsp;<%if(purchaseproperty != null){if(purchaseproperty.equals("1")){
                %>进货<%}else{%>进货退出<%}}%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>发票号码：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=invoiceno==null?"--":invoiceno%>
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
            <TD align=right width=30% height=32>应付金额：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=accountpayable == null ? "--" : accountpayable%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>已付账款：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=paid == null ? "--" : paid%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=30% height=32>付款截止日：</TD>
            <TD align=left width=70% height=32>&nbsp;<%=limitdate.toString().substring(0, 10)%>
            </TD>
        </TR>
        <TR>
            <TD align=center height=32 colspan="2">
                <table cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=100% borderColorLight=#008000 border=1>
                    <tr><td bgColor=#33ccff height="32" colspan="4" align="center">进货单明细</td></tr>
                    <tr><TD align=center width=40% height=32>商品名称</TD>
                    <TD align=center width=20% height=32>数量</TD>
                    <TD align=center width=20% height=32>单价</TD>
                    <TD align=center width=20% height=32>金额</TD>
                    </tr><%
                        if(list != null){
                            for(int i=0;i<list.size();i++){
                                pd = (PurchaseDetail)list.get(i);
                                String productid = pd.getProductID();
                                String purchasequantity = Integer.toString(pd.getPurchaseQuantity());
                                String purchaseunitprice = Integer.toString(pd.getPurchaseUnitPrice());
                                String purchaseamount = Integer.toString(pd.getPurchaseAmount());
                    %>
                    <tr><TD align=center width=40% height=32>&nbsp;<%=productid == null ? "--" : productid%></TD>
                    <TD align=center width=20% height=32>&nbsp;<%=purchasequantity == null ? "--" : purchasequantity%></TD>
                    <TD align=center width=20% height=32>&nbsp;<%=purchaseunitprice == null ? "--" : purchaseunitprice%></TD>
                    <TD align=center width=20% height=32>&nbsp;<%=purchaseamount == null ? "--" : purchaseamount%></TD>
                    </tr>
                    <%}}%>
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

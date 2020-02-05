<%@page import="com.bizwink.cms.kings.supplier.ISupplierSuManager,
                com.bizwink.cms.kings.supplier.SupplierSu,
                com.bizwink.cms.kings.supplier.SupplierSuPeer,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="java.sql.Timestamp" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    SupplierSu suppliersu = new SupplierSu();
    ISupplierSuManager suppliersuMgr = SupplierSuPeer.getInstance();
    suppliersu = suppliersuMgr.getByIdsupplier(id);

    String supplierattribname = suppliersu.getSupplierattribname();
    String suppliername = suppliersu.getSuppliername();
    String invoiceno = Integer.toString(suppliersu.getInvoiceno());
    String owner = suppliersu.getOwner();
    String rocid = Integer.toString(suppliersu.getRocid());
    String phone1 = Integer.toString(suppliersu.getPhone1());
    String phone2 = Integer.toString(suppliersu.getPhone2());
    String fax = Integer.toString(suppliersu.getFax());
    String contactname1 = suppliersu.getContactname1();
    String contactname2 = suppliersu.getContactname2();
    String companyaddress = suppliersu.getCompanyaddress();
    String deliveryaddress = suppliersu.getDeliveryaddress();
    String invoiceaddress = suppliersu.getInvoiceaddress();
    Timestamp lastpurchasedate = suppliersu.getLastpurchasedate();
    //System.out.println("---"+lastpurchasedate);
    String paydays = Integer.toString(suppliersu.getPaydays());
    String prepaid = Integer.toString(suppliersu.getPrepaid());
%>
<HTML>
<HEAD><TITLE>New Page 1</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
</HEAD>
<BODY>
<CENTER>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=497
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR>
            <TD width=493 bgColor=#33ccff colSpan=2 height=32>
                <P align=center>供应商信息</P></TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>供应商编号：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=id%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>供应商简称：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=supplierattribname == null ? "--" : supplierattribname%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>供应商名称：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=suppliername == null ? "--" : suppliername%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>统一编号：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=invoiceno == null ? "--" : invoiceno%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>负责人：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=owner == null ? "--" : owner%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>身份证号码：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=rocid == null ? "--" : rocid%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>联络电话1：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=phone1 == null ? "--" : phone1%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>联络电话2：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=phone2 == null ? "--" : phone2%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>传真号码：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=fax == null ? "--" : fax%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>联络人1：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=contactname1 == null ? "--" : contactname1%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>联络人2：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=contactname2 == null ? "--" : contactname2%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>公司地址：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=companyaddress == null ? "--" : companyaddress%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>工厂地址：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=deliveryaddress == null ? "--" : deliveryaddress%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>发票地址：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=invoiceaddress == null ? "--" : invoiceaddress%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>最近进货日期：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=lastpurchasedate.toString().substring(0, 10)%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>付款日数：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=paydays == null ? "--" : paydays%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>暂付款：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=prepaid == null ? "--" : prepaid%>
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

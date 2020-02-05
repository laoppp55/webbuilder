<%@page import="com.bizwink.cms.kings.deliverydetail.DeliveryDetail,
                com.bizwink.cms.kings.deliverydetail.DeliveryDetailPeer,
                com.bizwink.cms.kings.deliverydetail.IDeliveryDetailManager,
                com.bizwink.cms.kings.deliverymaster.DeliveryMaster,
                com.bizwink.cms.kings.deliverymaster.DeliveryMasterPeer,
                com.bizwink.cms.kings.deliverymaster.IDeliveryMasterManager" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.kings.product.IProductSuManager" %>
<%@ page import="com.bizwink.cms.kings.product.ProductSu" %>
<%@ page import="com.bizwink.cms.kings.product.ProductSuPeer" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<script language="JavaScript" src="../images/setday.js"></script>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    IDeliveryMasterManager delMgr = DeliveryMasterPeer.getInstance();
    List list = new ArrayList();
    list = delMgr.getAllDeliveryMaster(siteid);

    IProductSuManager suMgr = ProductSuPeer.getInstance();
    List list1 = new ArrayList();
    list1 = suMgr.getAllProduct(siteid);

    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    String deliveryid = ParamUtil.getParameter(request, "deliveryid");
    int productid = ParamUtil.getIntParameter(request, "productid", 0);
    int salesquantity = ParamUtil.getIntParameter(request, "salesquantity", 0);
    int salesunitprice = ParamUtil.getIntParameter(request, "salesunitprice", 0);
    int salesamount = ParamUtil.getIntParameter(request, "salesamount", 0);
    DeliveryDetail dd = new DeliveryDetail();
    IDeliveryDetailManager delsMgr = DeliveryDetailPeer.getInstance();

    if (startflag == 1) {
        ProductSu ps = new ProductSu();

        dd.setSiteid(siteid);
        dd.setDeliveryID(deliveryid);
        dd.setProductID(productid);
        dd.setSalesQuantity(salesquantity);
        dd.setSalesUnitPrice(salesunitprice);
        dd.setSalesAmount(salesamount);
        ps.setQuantity(salesquantity);
        delsMgr.updateDeliveryDetail(dd, deliveryid, ps);
        response.sendRedirect("deliverydetails.jsp");
    }
    dd = delsMgr.getByIdDeliveryDetail(deliveryid);
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
            if (form1.productid.value == "") {
                alert("请输入商品编号");
                return false;
            }
            return true;
        }
        function msg1() {
            for (var i = 0; i < form1.deliveryids.length; i++)
            {
                if (form1.deliveryids[i].selected)
                    form1.deliveryid.value = form1.deliveryids[i].value;
            }
        }
        function msg2() {
            for (var i = 0; i < form1.productids.length; i++)
            {
                if (form1.productids[i].selected)
                //alert(form1.supplierids[i].value);
                    form1.productid.value = form1.productids[i].value;
            }
        }

        function goto()
        {
            form1.action = "deliverydetails.jsp";
            form1.submit();
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
                    <P align=center>修改出货单明细信息</P></TD>
            </TR>
            <TR height=32>
                <TD align=right>变动单单号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=deliveryid value="<%=deliveryid%>" readonly>
                    <SELECT NAME="deliveryids" onchange="msg1()">

                        <%
                            for (int i = 0; i < list.size(); i++) {
                                DeliveryMaster dm = (DeliveryMaster) list.get(i);
                        %>

                        <OPTION VALUE=<%=i%>><%=dm.getDeliveryID()%>
                        </OPTION>

                        <% } %></SELECT>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>商品编号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=productid value="<%=dd.getProductID()%>" readonly>
                    <SELECT NAME="productids" onchange="msg2()">

                        <%
                            for (int i = 0; i < list1.size(); i++) {
                                ProductSu ps = (ProductSu) list1.get(i);
                        %>

                        <OPTION VALUE=<%=i%>><%=ps.getProductID()%>
                        </OPTION>

                        <% } %></SELECT>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>数量：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=salesquantity value="<%=dd.getSalesQuantity()%>">
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>单价：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=salesunitprice value="<%=dd.getSalesUnitPrice()%>">
            </TR>
            <TR height=32>
                <TD align=right>金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=salesamount value="<%=dd.getSalesAmount()%>">
            </TR>
            <TR height=32>
                <TD colSpan=2><FONT
                        color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：带有*的项为必填项</FONT></TD>
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

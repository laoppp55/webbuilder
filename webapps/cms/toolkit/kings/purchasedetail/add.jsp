<%@page import="com.bizwink.cms.kings.product.IProductSuManager,
                com.bizwink.cms.kings.product.ProductSu,
                com.bizwink.cms.kings.product.ProductSuPeer,
                com.bizwink.cms.kings.purchasedetail.IPurchaseDetailManager,
                com.bizwink.cms.kings.purchasedetail.PurchaseDetail,
                com.bizwink.cms.kings.purchasedetail.PurchaseDetailPeer" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.kings.purchasemaster.IPurchaseMasterManager" %>
<%@ page import="com.bizwink.cms.kings.purchasemaster.PurchaseMaster" %>
<%@ page import="com.bizwink.cms.kings.purchasemaster.PurchaseMasterPeer" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
    IPurchaseMasterManager pumMgr = PurchaseMasterPeer.getInstance();
    List list = new ArrayList();
    list = pumMgr.getAllPurchaseMaster(siteid);

    IProductSuManager psMgr = ProductSuPeer.getInstance();
    List list1 = new ArrayList();
    list1 = psMgr.getAllProduct(siteid);


    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    int purchaseid = ParamUtil.getIntParameter(request, "purchaseid", 0);
    int productid = ParamUtil.getIntParameter(request, "productid", 0);
    int purchasequantity = ParamUtil.getIntParameter(request, "purchasequantity", 0);
    int purchaseunitprice = ParamUtil.getIntParameter(request, "purchaseunitprice", 0);
    int purchaseamount = ParamUtil.getIntParameter(request, "purchaseamount", 0);
    String lastpurchasedate1 = ParamUtil.getParameter(request, "lastpurchasedate");

    if (startflag == 1) {
        PurchaseDetail pd = new PurchaseDetail();
        ProductSu ps = new ProductSu();
        IPurchaseDetailManager pudMgr = PurchaseDetailPeer.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp lastpurchasedate = new Timestamp(sf.parse(lastpurchasedate1).getTime());


        pd.setPurchaseID(purchaseid);
        pd.setProductID(productid);
        pd.setPurchaseQuantity(purchasequantity);
        pd.setPurchaseUnitPrice(purchaseunitprice);
        pd.setPurchaseAmount(purchaseamount);
        pd.setSiteid(siteid);
        ps.setLastPurchaseDate(lastpurchasedate);
        ps.setQuantity(purchasequantity);
        pudMgr.insertPurchaseDetail(pd, ps);
        response.sendRedirect("purchasedetail.jsp");
    }
%>
<%
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

    java.util.Date currentTime = new java.util.Date();//得到当前系统时间

    String str_date1 = formatter.format(currentTime); //将日期时间格式化
/*String str_date2 = currentTime.toString(); //将Date型日期时间转换成字符串形式
    System.out.println("aaa = "+str_date2);
    System.out.println("bbb = "+str_date1);*/
%>

<HTML>
<HEAD><TITLE>进货单信息录入</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
    <SCRIPT language=javascript>
        function check()
        {
            if (form1.purchaseid.value == "")
            {
                alert("请输入进货单单号！");
                return false;
            }
            if (form1.productid.value == "") {
                alert("请输入商品编号");
                return false;
            }
            return true;
        }
        function msg1() {
            for (var i = 0; i < form1.purchaseids.length; i++)
            {
                if (form1.purchaseids[i].selected)
                //alert(form1.supplierids[i].value);
                    form1.purchaseid.value = form1.purchaseids[i].innerText;
            }
        }
        function msg2() {
            for (var i = 0; i < form1.productids.length; i++)
            {
                if (form1.productids[i].selected)
                //alert(form1.supplierids[i].value);
                    form1.productid.value = form1.productids[i].innerText;
            }
        }

        function goto()
        {
            form1.action = "purchasedetail.jsp";
            form1.submit();
        }
    </SCRIPT>

    <META content="MSHTML 6.00.2800.1479" name=GENERATOR>
</HEAD>
<BODY bgColor=#ffffff onload="msg1(),msg2()">
<FORM name=form1 action=add.jsp method=post>
    <INPUT type=hidden value=1 name=startflag>
    <INPUT type=hidden value=<%=str_date1%> name=lastpurchasedate>
    <CENTER>
        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80%
               borderColorLight=#008000 border=1>
            <TBODY>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>添加进货单明细信息</P></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>进货单单号：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=16 name=purchaseid readonly>
                    <SELECT NAME="purchaseids" onchange="msg1()">

                        <%
                            for (int i = 0; i < list.size(); i++) {
                                PurchaseMaster pm = (PurchaseMaster) list.get(i);
                        %>

                        <OPTION VALUE=<%=i%>><%=pm.getPurchaseID()%>
                        </OPTION>

                        <% } %></SELECT>
                    <FONT
                            color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>商品编号：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=16 name=productid readonly>
                    <SELECT NAME="productids" onchange="msg2()">

                        <%
                            for (int i = 0; i < list1.size(); i++) {
                                ProductSu ps = (ProductSu) list1.get(i);
                        %>

                        <OPTION VALUE=<%=i%>><%=ps.getProductID()%>
                        </OPTION>

                        <% } %></SELECT>
                    <FONT
                            color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>数量：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=purchasequantity>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>单价：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=purchaseunitprice>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=purchaseamount>
                    <FONT color=red>*</FONT></TD>
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

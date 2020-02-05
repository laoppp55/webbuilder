<%@page import="com.bizwink.cms.kings.purchasemaster.IPurchaseMasterManager,
                com.bizwink.cms.kings.purchasemaster.PurchaseMaster,
                com.bizwink.cms.kings.purchasemaster.PurchaseMasterPeer,
                com.bizwink.cms.kings.supplier.ISupplierSuManager,
                com.bizwink.cms.kings.supplier.SupplierSu,
                com.bizwink.cms.kings.supplier.SupplierSuPeer,
                com.bizwink.cms.security.Auth" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.kings.purchasedetail.*" %>
<script language="JavaScript" src="../images/setday.js"></script>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    ISupplierSuManager supplierMgr = SupplierSuPeer.getInstance();
    List list = new ArrayList();
    list = supplierMgr.getAllSupplier(siteid);

    IPurchaseDetailManager pdMgr = PurchaseDetailPeer.getInstance();
    PurchaseDetail pd = new PurchaseDetail();    

    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    int id = ParamUtil.getIntParameter(request, "id", 0);
    String purchasedate1 = ParamUtil.getParameter(request, "purchasedate");
    String supplierid = ParamUtil.getParameter(request, "supplierid");
    int purchaseproperty = ParamUtil.getIntParameter(request, "purchaseproperty", 0);
    int invoiceno = ParamUtil.getIntParameter(request, "invoiceno", 0);
    int subtotal = ParamUtil.getIntParameter(request, "subtotal", 0);
    int valueaddtax = ParamUtil.getIntParameter(request, "valueasstax", 0);
    int amount = ParamUtil.getIntParameter(request, "amount", 0);
    int accountpayable = ParamUtil.getIntParameter(request, "accountpayable", 0);
    int paid = ParamUtil.getIntParameter(request, "paid", 0);
    String limitdate1 = ParamUtil.getParameter(request, "limitdate");
    PurchaseMaster pm = new PurchaseMaster();
    IPurchaseMasterManager pumMgr = PurchaseMasterPeer.getInstance();
    List list1 = new ArrayList();
    List listpd = new ArrayList();
    list1 = pdMgr.getByIdPurchaseDetails(id);
    pm = pumMgr.getByIdpurchase(id);    
    if (startflag == 1) {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp purchasedate = new Timestamp(sf.parse(purchasedate1).getTime());
        Timestamp limitdate = new Timestamp(sf.parse(limitdate1).getTime());
        pm.setPurchaseDate(purchasedate);
        pm.setSupplierID(supplierid);
        pm.setPurchaseProperty(purchaseproperty);
        pm.setInvoiceNo(invoiceno);
        pm.setSubTotal(subtotal);
        pm.setValutAddTax(valueaddtax);
        pm.setAmount(amount);
        pm.setAccountPayable(accountpayable);
        pm.setPaid(paid);
        pm.setLimitDate(limitdate);
        pm.setSiteid(siteid);
        if(list1!=null){
            for(int i=0;i<list1.size();i++){
                pd = new PurchaseDetail();
                int pdid = ParamUtil.getIntParameter(request,"pdid" + i,0);
                int productid = ParamUtil.getIntParameter(request,"a0" + i,0);
                int purchasequantity = ParamUtil.getIntParameter(request,"a1" + i,0);
                int purchaseunitprice = ParamUtil.getIntParameter(request,"a2" + i,0);
                int purchaseamount = ParamUtil.getIntParameter(request,"a3" + i,0);
                pd.setId(pdid);
                pd.setProductID(String.valueOf(productid));
                pd.setPurchaseQuantity(purchasequantity);
                pd.setPurchaseUnitPrice(purchaseunitprice);
                pd.setPurchaseAmount(purchaseamount);
                listpd.add(pd);
            }
        }
        pumMgr.updatePurchaseMasterhe(pm, id,listpd);
        response.sendRedirect("purchasemaster.jsp");
    }


%>
<HTML>
<HEAD><TITLE>修改进货单信息</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
    <SCRIPT language=javascript>
        function check()
        {
            if (form1.supplierid.value == "")
            {
                alert("请输入供应商编号！");
                return false;
            }
            if (form1.purchaseproperty.value != "1" && form1.purchaseproperty.value != "2")
            {
                alert("进货单属性只能输入1或者2");
                return false
            }
            return true;
        }

        function gong(){
            //window.frames["example05"];
            str = window.showModalDialog('gongFrame.jsp','example05','dialogWidth:500px;dialogHeight:300px.dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes')
            if(str!=undefined){
                form1.supplierid.value = str;
            }
        }

        function msg() {
            for (var i = 0; i < form1.supplierids.length; i++)
            {
                if (form1.supplierids[i].selected)
                //alert(form1.supplierids[i].value);
                    form1.supplierid.value = form1.supplierids[i].innerText;
            }
        }

        function msg1() {
            for (var i = 0; i < form1.purchasepropertys.length; i++)
            {
                if (form1.purchasepropertys[i].selected)
                    form1.purchaseproperty.value = form1.purchasepropertys[i].value;
            }
        }

        function goto()
        {
            form1.action = "purchasemaster.jsp";
            form1.submit();
        }
        function chenk(type){
            //str = window.open("../product/index.jsp","","");
            str = window.showModalDialog('../product/index.jsp?id='+<%=1%>,'example05','dialogWidth:1000px;dialogHeight:600px.dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes')
            //alert("str = " + str)
            if(str!=undefined){
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
    <input type=hidden name="id" value="<%=id%>">

    <CENTER>
        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80%
               borderColorLight=#008000 border=1>
            <TBODY>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>修改进货单信息</P></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>供应商编号：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=16 name=supplierid value="<%=pm.getSupplierID()%>" readonly onclick="gong();">
                    <%--<SELECT NAME="supplierids" onchange="msg()">
                        <%
                            for (int i = 0; i < list.size(); i++) {
                                SupplierSu ss = (SupplierSu) list.get(i);
                        %>
                        <OPTION VALUE=<%=i%>><%=ss.getSupplierid()%>
                        </OPTION>

                        <% } %></SELECT>--%>
                    <FONT
                            color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>进货日期：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=purchasedate
                                            value="<%=pm.getPurchaseDate().toString().substring(0,10)%>"
                                            onfocus="setday(this)">
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>进货单属性：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=purchaseproperty value="<%=pm.getPurchaseProperty()%>">
                    <select name="purchasepropertys" onchange="msg1()">
                        <option value="1">进货</option>
                        <option value="2">进货退出</option>
                    </select>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>发票号码：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=invoiceno value="<%=pm.getInvoiceNo()%>">
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>合计金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=subtotal value="<%=pm.getSubTotal()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>营业税：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=valueaddtax value="<%=pm.getValutAddTax()%>">
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>总计金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=amount value="<%=pm.getAmount()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>应付金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=accountpayable value="<%=pm.getAccountPayable()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>已付账款：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=paid
                                            value="<%=pm.getPaid()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>付款截止日：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=limitdate
                                            value="<%=pm.getLimitDate().toString().substring(0,10)%>"
                                            onfocus="setday(this)"></TD>
            </TR>
            <TR height=32>
                <TD colSpan=2><FONT
                        color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：带有*的项为必填项</FONT></TD>
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
                        if(list1 != null){
                            for(int i=0;i<list1.size();i++){
                                pd = (PurchaseDetail)list1.get(i);
                                String productid = pd.getProductID();
                                String purchasequantity = Integer.toString(pd.getPurchaseQuantity());
                                String purchaseunitprice = Integer.toString(pd.getPurchaseUnitPrice());
                                String purchaseamount = Integer.toString(pd.getPurchaseAmount());
                    %><input type="hidden" name="pdid<%=i%>" value="<%=pd.getId()%>">
                    <tr><TD align=center width=40% height=32>&nbsp;<input size="30" name="a0<%=i%>" id="a0<%=i%>" onclick="chenk(<%=i%>);" value="<%=productid == null ? "" : productid%>" readonly></TD>
                    <TD align=center width=20% height=32>&nbsp;<input size="16" name="a1<%=i%>" id="a1<%=i%>" value="<%=purchasequantity == null ? "" : purchasequantity%>" ></TD>
                    <TD align=center width=20% height=32>&nbsp;<input size="16" name="a2<%=i%>" id="a2<%=i%>" value="<%=purchaseunitprice == null ? "" : purchaseunitprice%>" ></TD>
                    <TD align=center width=20% height=32>&nbsp;<input size="16" name="a3<%=i%>" id="a3<%=i%>" value="<%=purchaseamount == null ? "" : purchaseamount%>" ></TD>
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

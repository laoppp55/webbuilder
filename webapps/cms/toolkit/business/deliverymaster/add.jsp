<%@page import="com.bizwink.cms.kings.deliverymaster.DeliveryMaster,
                com.bizwink.cms.kings.deliverymaster.DeliveryMasterPeer,
                com.bizwink.cms.kings.deliverymaster.IDeliveryMasterManager,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil,
                com.bizwink.cms.util.SessionUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.bizwink.cms.kings.deliverydetail.DeliveryDetail" %>
<%@ page import="java.util.*" %>
<script language="JavaScript" src="../images/setday.js"></script>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    String deliveryid = ParamUtil.getParameter(request, "deliveryid");
    List listdd = new ArrayList();

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
    String productid1 = ParamUtil.getParameter(request, "productid");
    String salesquantity1 = ParamUtil.getParameter(request, "salesquantity");
    String salesunitprice1 = ParamUtil.getParameter(request, "salesunitprice");
    String salesamount1 = ParamUtil.getParameter(request, "salesamount");    


    if (startflag == 1) {
        DeliveryMaster dm = new DeliveryMaster();
        DeliveryDetail dd = new DeliveryDetail();
        IDeliveryMasterManager delMgr = DeliveryMasterPeer.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String deliverydate1 = ParamUtil.getParameter(request, "deliverydate");
        String limitdate1 = ParamUtil.getParameter(request, "limitdate");
        Timestamp deliverydate = new Timestamp(sf.parse(deliverydate1).getTime());
        Timestamp limitdate = new Timestamp(System.currentTimeMillis());
        if(limitdate1 != null)
             limitdate = new Timestamp(sf.parse(limitdate1).getTime());
        //System.out.println("limitdate = " + limitdate);
        dm.setDeliveryID(deliveryid);
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
        String[] productid = productid1.split(",");
        String[] salesquantity = salesquantity1.split(",");
        String[] salesunitprice = salesunitprice1.split(",");
        String[] salesamount = salesamount1.split(",");
        for(int i = 0; i < productid.length; i++){
            dd = new DeliveryDetail();
            if(productid[i] != null && productid[i] != ""){
                dd.setProductID(productid[i]);
            }
            if(salesquantity[i] != null && salesquantity[i] != ""){
                dd.setSalesQuantity(Integer.parseInt(salesquantity[i]));
            }
            if(salesunitprice[i] != null && salesunitprice[i] != ""){
                dd.setSalesUnitPrice(Integer.parseInt(salesunitprice[i]));
            }
            if(salesamount[i] != null && salesamount[i] != ""){
                dd.setSalesAmount(Integer.parseInt(salesamount[i]));
            }
            listdd.add(dd);
        }        
        delMgr.insertDeliveryMasterhe(dm,listdd);
        response.sendRedirect("deliverymaster.jsp");
    }
%>

<HTML>
<HEAD><TITLE>出货单信息录入</TITLE>
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
            var tableobj=document.getElementById("table1");
            var rows = tableobj.rows.length-2;            

            var productid ="";
            var salesquantity ="";
            var salesunitprice ="";
            var salesamount ="";
            for(i=0;i<=rows;i++){
            var   obj=   document.getElementById("a0"+i);
            var   obj1=   document.getElementById("a1"+i);
            var   obj2=   document.getElementById("a2"+i);
            var   obj3=   document.getElementById("a3"+i);                
                if(obj.value != "" && obj.value != null){
                    productid += obj.value +",";
                }else{
                    productid += 0 + ",";
                }
                if(obj1.value != "" && obj1.value != null){
                    salesquantity += obj1.value +",";
                }else{
                    salesquantity += 0 + ",";
                }
                if(obj2.value != "" && obj2.value != null){
                    salesunitprice += obj2.value +",";
                }else{
                    salesunitprice += 0 + ",";
                }
                if(obj3.value != "" && obj3.value != null){
                    salesamount += obj3.value +",";
                }else{
                    salesamount += 0 + ",";
                }                
            }
                document.form1.productid.value = productid;
                document.form1.salesquantity.value = salesquantity;
                document.form1.salesunitprice.value = salesunitprice;
                document.form1.salesamount.value = salesamount;
            return true;
        }

        function msg1() {
            for (var i = 0; i < form1.deliverypropertys.length; i++)
            {
                if (form1.deliverypropertys[i].selected)
                    form1.deliveryproperty.value = form1.deliverypropertys[i].value;
            }
        }

		function butt(){
            var tableobj=document.getElementById("table1");
            var rowobj=tableobj.insertRow(tableobj.rows.length);

            var cell1=rowobj.insertCell(rowobj.cells.length);
            var cell2=rowobj.insertCell(rowobj.cells.length);
            var cell3=rowobj.insertCell(rowobj.cells.length);
            var cell4=rowobj.insertCell(rowobj.cells.length);
            var p = tableobj.rows.length -2;
            var name = "a0" + p;
            var name1 = "a1" + p;
            var name2 = "a2" + p;
            var name3 = "a3" + p;
            //alert(name);
            //cell1.innerHTML=document.getElementById("name").value;
            cell1.innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT size=34 name='"+name+"' id='"+name+"' onclick=\"chenk('"+ p +"');\" readonly>";
            cell2.innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT size=27 name='"+name1+"' id='"+name1+"'>";
            cell3.innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;<INPUT size=14 name='"+name2+"' id='"+name2+"'>";
            cell4.innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;<INPUT size=14 name='"+name3+"' id='"+name3+"'>";

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
		function   initTblCell(cell){

		var   lastCell   =   document.getElementById("table1").rows[0].cells[cell.cellIndex];

		cell.innerHTML   =   lastCell.innerHTML;

		//alert(cell.innerHTML);

		if   (cell.children   !=   null   &&   cell.children.length   >   0)   {

		for(childIndex   =   0;   childIndex   <   cell.children.length;   childIndex++)   {

		var   child   =   cell.children[childIndex];

		switch(child.type)   {

		case   "text":

		child.value   =   "";

		break;

		case   "checkbox":

		child.value   =   "";

		child.checked   =   false;

		break;

		}

		}

		}

		cell.className   =   lastCell.className;

		cell.align   =   lastCell.align;

		cell.height   =   lastCell.height;

		}

        function   buttonFun(){
        var   obj=   document.getElementsByName("a00");
        var   obj1=   document.getElementsByName("a10");
        var   obj2=   document.getElementsByName("a20");
        var   obj3=   document.getElementsByName("a30");
        var purchaseid ="";
        var productid ="";
        var purchasequantity ="";
        var purchaseunitprice ="";
        var salesamount ="";
        for(i=1;i<obj.length;i++){
            purchaseid += obj[i].value +",";
            productid += obj1[i].value +",";
            purchasequantity += obj2[i].value +",";
            purchaseunitprice += obj3[i].value +",";
            salesamount += obj4[i].value +",";
        }
            document.form1.purchaseid.value = purchaseid;
            document.form1.productid.value = productid;
            document.form1.purchasequantity.value = purchasequantity;
            document.form1.purchaseunitprice.value = purchaseunitprice;
            document.form1.salesamount.value = salesamount;
        }

        function   dellRow(){

        var   obj=   document.getElementById("table1");

        var   objRow=obj.rows.length-1;

        //alert(objRow);

        if(objRow   !=   0){

        obj.deleteRow(objRow);

        }

        }        

        function goto()
        {
            form1.action = "deliverymaster.jsp";
            form1.submit();
        }
    </SCRIPT>

    <META content="MSHTML 6.00.2800.1479" name=GENERATOR>
</HEAD>
<BODY bgColor=#ffffff onload="msg1()">
<FORM name=form1 action=add.jsp method=post>
    <INPUT type=hidden value=1 name=startflag>
    <INPUT type=hidden value=1 name=productid>
    <INPUT type=hidden value=1 name=salesquantity>
    <INPUT type=hidden value=1 name=salesunitprice>
    <INPUT type=hidden value=1 name=salesamount>
    <CENTER>
        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80%
               borderColorLight=#008000 border=1>
            <TBODY>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>添加出货单信息</P></TD>
            </TR>
            <TR height=32>
                <TD align=right>出货单单号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=deliveryid>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>出货日期：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=deliverydate onfocus="setday(this)">
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>客户编号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=customerid>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>业务员编号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=salesmanid>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>出货单属性：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=deliveryproperty readonly>
                    <select name="deliverypropertys" onchange="msg1()">
                        <option value="1">出货</option>
                        <option value="2">出货退出</option>
                    </select>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>送货地址：</TD>
                <TD align=left>&nbsp;<INPUT size=32 name=deliveryaddress>
            </TR>
            <TR height=32>
                <TD align=right>发票号码：</TD>
                <TD align=left>&nbsp;<INPUT size=32 name=invoiceno>
            </TR>
            <TR height=32>
                <TD align=right>订单号码：</TD>
                <TD align=left>&nbsp;<INPUT size=32 name=customerorderno>
            </TR>
            <TR height=32>
                <TD align=right>合计金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=subtotal>
            </TR>
            <TR height=32>
                <TD align=right>营业税：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=valueaddtax>
            </TR>
            <TR height=32>
                <TD align=right>总计金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=amount>
            </TR>
            <TR height=32>
                <TD align=right>应收金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=amountreceivable>
            </TR>
            <TR height=32>
                <TD align=right>已收金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=received>
            </TR>
            <TR height=32>
                <TD align=right>收款截止日：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=limitdate onfocus="setday(this)">
            </TR>
            <TR height=32>
                <TD colSpan=2><FONT
                        color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：带有*的项为必填项</FONT></TD>
            </TR>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>进货单明细&nbsp;&nbsp;&nbsp;<%--添加
                        <select name="xian" onchange="xianshi();">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                        </select>--%><input type=button value=" 添 加 " onclick="butt();">
                        <input type=button value=" 减 少 " onclick="dellRow();">
                        <%--<input type=button value=" 累 计 " onclick="buttonFun();">--%>
                    </P></TD>
            </TR>
            <TR height=32>
                <TD colspan="2"><TABLE id="table1" cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=100% borderColorLight=#008000 border=1>
                        <TR height=32 >
                            <TD align=center>商品名称</TD>
                            <TD align=center>数量</TD>
                            <TD align=center>单价</TD>
                            <TD align=center>金额</TD>
                        </TR>
                        <TR height=32>
                            <TD align=center>&nbsp;<INPUT size=34 name=a00 id="a00" onclick="chenk(0);" readonly></TD>
                            <TD align=center>&nbsp;<INPUT size=27 name=a10 id="a10"></TD>
                            <TD align=center>&nbsp;<INPUT size=14 name=a20 id="a20"></TD>
                            <TD align=center>&nbsp;<INPUT size=14 name=a30 id="a30"></TD>
                        </TR>
                        <TR height=32>
                            <TD align=center>&nbsp;<INPUT size=34 name=a01 id="a01" onclick="chenk(1);" readonly></TD>
                            <TD align=center>&nbsp;<INPUT size=27 name=a11 id="a11"></TD>
                            <TD align=center>&nbsp;<INPUT size=14 name=a21 id="a21"></TD>
                            <TD align=center>&nbsp;<INPUT size=14 name=a31 id="a31"></TD>
                        </TR>
                        <TR height=32>
                            <TD align=center>&nbsp;<INPUT size=34 name=a02 id="a02" onclick="chenk(2);" readonly></TD>
                            <TD align=center>&nbsp;<INPUT size=27 name=a12 id="a12"></TD>
                            <TD align=center>&nbsp;<INPUT size=14 name=a22 id="a22"></TD>
                            <TD align=center>&nbsp;<INPUT size=14 name=a32 id="a32"></TD>
                        </TR>
                        <TR height=32>
                            <TD align=center>&nbsp;<INPUT size=34 name=a03 id="a03" onclick="chenk(3);" readonly></TD>
                            <TD align=center>&nbsp;<INPUT size=27 name=a13 id="a13"></TD>
                            <TD align=center>&nbsp;<INPUT size=14 name=a23 id="a23"></TD>
                            <TD align=center>&nbsp;<INPUT size=14 name=a33 id="a33"></TD>
                        </TR>
                </TABLE></TD>
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

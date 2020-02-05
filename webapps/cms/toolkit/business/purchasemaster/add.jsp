<%@page import="com.bizwink.cms.kings.purchasemaster.IPurchaseMasterManager,
                com.bizwink.cms.kings.purchasemaster.PurchaseMaster,
                com.bizwink.cms.kings.purchasemaster.PurchaseMasterPeer,
                com.bizwink.cms.kings.supplier.ISupplierSuManager,
                com.bizwink.cms.kings.supplier.SupplierSu,
                com.bizwink.cms.kings.supplier.SupplierSuPeer" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.kings.purchasedetail.PurchaseDetail" %>
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
    List listpd = new ArrayList();
    list = supplierMgr.getAllSupplier(siteid);

    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    String purchasedate1 = ParamUtil.getParameter(request, "purchasedate");
    String supplierid = ParamUtil.getParameter(request, "supplierid");
    int purchaseproperty = ParamUtil.getIntParameter(request, "purchaseproperty", 0);
    int invoiceno = ParamUtil.getIntParameter(request, "invoiceno", 0);
    int subtotal = ParamUtil.getIntParameter(request, "subtotal", 0);
    int valueaddtax = ParamUtil.getIntParameter(request, "valueaddtax", 0);
    int amount = ParamUtil.getIntParameter(request, "amount", 0);
    int accountpayable = ParamUtil.getIntParameter(request, "accountpayable", 0);
    int paid = ParamUtil.getIntParameter(request, "paid", 0);
    String limitdate1 = ParamUtil.getParameter(request, "limitdate");
    String purchaseid = ParamUtil.getParameter(request, "purchaseid");
    String productid1 = ParamUtil.getParameter(request, "productid");
    String purchasequantity1 = ParamUtil.getParameter(request, "purchasequantity");
    String purchaseunitprice1 = ParamUtil.getParameter(request, "purchaseunitprice");
    String purchaseamount1 = ParamUtil.getParameter(request, "purchaseamount");

    if (startflag == 1) {
        PurchaseMaster pm = new PurchaseMaster();
        PurchaseDetail pd = new PurchaseDetail();
        IPurchaseMasterManager pumMgr = PurchaseMasterPeer.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp purchasedate = new Timestamp(sf.parse(purchasedate1).getTime());
        Timestamp limitdate = new Timestamp(System.currentTimeMillis());
        if(limitdate1 != null){
        limitdate = new Timestamp(sf.parse(limitdate1).getTime());
            }
        pm.setPurchaseID(purchaseid);
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
        String[] productid = productid1.split(",");
        String[] purchasequantity = purchasequantity1.split(",");
        String[] purchaseunitprice = purchaseunitprice1.split(",");
        String[] purchaseamount = purchaseamount1.split(",");
        //System.out.println("purchasequantity = "+purchasequantity.length);
        for(int i = 0; i < productid.length; i++){

            pd = new PurchaseDetail();
            if(productid[i] != null && productid[i] != ""){
                pd.setProductID(productid[i]);
            }
            if(purchasequantity[i] != null && purchasequantity[i] != ""){
                pd.setPurchaseQuantity(Integer.parseInt(purchasequantity[i]));
            }
            if(purchaseunitprice[i] != null && purchaseunitprice[i] != ""){
                pd.setPurchaseUnitPrice(Integer.parseInt(purchaseunitprice[i]));
            }
            if(purchaseamount[i] != null && purchaseamount[i] != ""){
                pd.setPurchaseAmount(Integer.parseInt(purchaseamount[i]));
            }
            listpd.add(pd);
        }
        pumMgr.insertPurchaseMasterhe(pm,listpd);
        response.sendRedirect("purchasemaster.jsp");
    }
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
            if (form1.supplierid.value == "")
            {
                alert("请输入供应商编号！");
                return false;
            }
            if (form1.purchasedate.value == "")
            {
                alert("请输入进货日期！");
                return false;
            }
            if (form1.invoiceno.value == "")
            {
                alert("请输入发票号码！");
                return false;
            }
            if (form1.purchaseproperty.value != "1" && form1.purchaseproperty.value != "2")
            {
                alert("进货单属性只能输入1或者2");
                return false
            }
            var tableobj=document.getElementById("table1");
            var rows = tableobj.rows.length-2;
            //alert(rows);

            var productid ="";
            var purchasequantity ="";
            var purchaseunitprice ="";
            var purchaseamount ="";

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
                    purchasequantity += obj1.value +",";
                }else{
                    purchasequantity += 0 + ",";
                }
                if(obj2.value != "" && obj2.value != null){
                    purchaseunitprice += obj2.value +",";
                }else{
                    purchaseunitprice += 0 + ",";
                }
                if(obj3.value != "" && obj3.value != null){
                    purchaseamount += obj3.value +",";
                }else{
                    purchaseamount += 0 + ",";
                }
            }
                document.form1.productid.value = productid;
                document.form1.purchasequantity.value = purchasequantity;
                document.form1.purchaseunitprice.value = purchaseunitprice;
                document.form1.purchaseamount.value = purchaseamount;
            return true;
        }
        /*function msg() {
            for (var i = 0; i < form1.supplierids.length; i++)
            {
                if (form1.supplierids[i].selected)
                    form1.supplierid.value = form1.supplierids[i].innerText;
            }
        }*/

        function msg1() {
            for (var i = 0; i < form1.purchasepropertys.length; i++)
            {
                if (form1.purchasepropertys[i].selected)
                    form1.purchaseproperty.value = form1.purchasepropertys[i].value;
            }
        }

        function gong(){
            //window.frames["example05"];
            str = window.showModalDialog('gongFrame.jsp','example05','dialogWidth:500px;dialogHeight:300px.dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes')
            if(str!=undefined){
                form1.supplierid.value = str;
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

            /*document.getElementById("name").value="";
            document.getElementById("pass").value="";*/
			/*var   tblObj   =   document.getElementById("table1");

*//*			if(tblObj.rows){

			alert(tblObj.rows.length);

			}else{

			alert('aaa');

			}*//*

			//追加行

			var   newRow   =   tblObj.insertRow();

			newRow.style.display   =   "";

			var   cellNum   =   tblObj.rows[0].cells.length;

			//追加列

			for   (colIndex   =   0;   colIndex   <   cellNum;   colIndex++)   {

			var   newCell   =   newRow.insertCell();

			initTblCell(newCell);

			}*/

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
            var tableobj=document.getElementById("table1");
            var rows = tableobj.rows.length-2;
            alert(rows);

            var productid ="";
            var purchasequantity ="";
            var purchaseunitprice ="";
            var purchaseamount ="";
            
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
                    purchasequantity += obj1.value +",";
                }else{
                    purchasequantity += 0 + ",";
                }
                if(obj2.value != "" && obj2.value != null){
                    purchaseunitprice += obj2.value +",";
                }else{
                    purchaseunitprice += 0 + ",";
                }
                if(obj3.value != "" && obj3.value != null){
                    purchaseamount += obj3.value +",";
                }else{
                    purchaseamount += 0 + ",";
                }
            }
                document.form1.productid.value = productid;
                document.form1.purchasequantity.value = purchasequantity;
                document.form1.purchaseunitprice.value = purchaseunitprice;
                document.form1.purchaseamount.value = purchaseamount;
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
            form1.action = "purchasemaster.jsp";
            form1.submit();
        }
        function chenk(type){
            //str = window.open("../product/index.jsp?id=<%=1%>","","");
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
<BODY bgColor=#ffffff onload="msg1()">
<FORM name=form1 action=add.jsp method=post>
    <INPUT type=hidden value=1 name=startflag>
    <INPUT type=hidden value=1 name=productid>
    <INPUT type=hidden value=1 name=purchasequantity>
    <INPUT type=hidden value=1 name=purchaseunitprice>
    <INPUT type=hidden value=1 name=purchaseamount>
    <CENTER>
        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80% borderColorLight=#008000 border=1>
            <TBODY>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>添加进货单信息</P></TD>
            </TR>
            <TR height=32>
                <TD align=right>进货单单号：</TD>
                <TD align=left>&nbsp;<INPUT name=purchaseid> <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>供应商编号：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=16 name=supplierid readonly onclick="gong();">
                    <%--<SELECT NAME="supplierids" onchange="msg()">

                        <%
                            for (int i = 0; i < list.size(); i++) {
                                SupplierSu ss = (SupplierSu) list.get(i);
                        %>

                        <OPTION VALUE=<%=i%>><%=ss.getSupplierid()%>
                        </OPTION>

                        <% } %></SELECT>--%>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>进货日期：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=purchasedate onfocus="setday(this)">
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>进货单属性：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=purchaseproperty readonly>
                    <select name="purchasepropertys" onchange="msg1()">
                        <option value="1">进货</option>
                        <option value="2">进货退出</option>
                    </select>
                    <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>发票号码：</TD>
                <TD align=left>&nbsp;<INPUT name=invoiceno> <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>合计金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=subtotal>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>营业税：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=valueaddtax></TD>
            </TR>
            <TR height=32>
                <TD align=right>总计金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=amount></TD>
            </TR>
            <TR height=32>
                <TD align=right>应付金额：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=accountpayable></TD>
            </TR>
            <TR height=32>
                <TD align=right>已付账款：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=paid></TD>
            </TR>
            <TR height=32>
                <TD align=right>付款截止日：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=limitdate onfocus="setday(this)"></TD>
            </TR>
            <TR height=32>
                <TD colSpan=2><FONT color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：带有*的项为必填项</FONT>
                </TD>
            </TR>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>进货单明细&nbsp;&nbsp;&nbsp;<input type=button value=" 添 加 " onclick="butt();">
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

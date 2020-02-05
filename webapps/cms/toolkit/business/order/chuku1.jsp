<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Order.*,
                 java.text.*,
                 com.bizwink.cms.business.Product.*"
         contentType="text/html;charset=gbk" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ include file="../../../include/auth.jsp"%>
<%
    long orderid = ParamUtil.getLongParameter(request, "orderid", 0);
    int the_siteid = authToken.getSiteID();
    int nextflag = ParamUtil.getIntParameter(request, "nextflag", 0);
    int dealflag = ParamUtil.getIntParameter(request, "dealflag", 0);
    boolean chaidanflag = false;
    int order_status = ParamUtil.getIntParameter(request, "order_status", -1);

    Order order = new Order();
    List list = new ArrayList();
    IOrderManager orderMgr = orderPeer.getInstance();
    IProductManager productMgr = productPeer.getInstance();
    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
    Product product = new Product();

    order = orderMgr.getAOrder(orderid);
    String createdate = String.valueOf(order.getCreateDate());
    createdate = createdate.substring(0, 19);

    if (nextflag == 1) {
        int oldstatus = -1;
        if (order_status >= 0) {
            oldstatus = orderMgr.getStatus(orderid);
            orderMgr.updateStatus(orderid, order_status, "");
            response.sendRedirect("chuku1.jsp?orderid=" + orderid);
        }
    }

    DecimalFormat df = new DecimalFormat();
    df.applyPattern("0.00");
    DecimalFormat df2 = new DecimalFormat();
    df2.applyPattern("0");
    Fee fee = new Fee();
    fee = orderMgr.getAFeeInfo(order.getSendWay());
    SendWay payway = orderMgr.getASendWayInfo(order.getPayWay());
    Invoice invoice = orderMgr.getInvoiceInfoForOrder(orderid);
%>
<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <meta http-equiv="Pragma" content="no-cache">
    <script language="javascript">
        function closewin() {
            window.close();
        }

        function dofun(obj) {
            /*if (confirm("确认拆分该订单吗？")) {
                alert("拆分");
            } else {
                alert("不拆分");
            }*/

            alert("hello word");
            var winStr = "chaifen_orders.jsp?orderid=<%=orderid%>";
            var returnvalue = "";
            returnvalue = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
        }
    </script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<%
    String[][] titlebars = {
            {"首页", ""},
            {"订单管理", ""}
    };

    String[][] operations = {
            {"处理中", "index.jsp"}, {"", ""},
            {"已发货", "receive.jsp"}, {"", ""},
            {"已完成", "end.jsp"}, {"", ""},
            {"退货订单", "putback.jsp"}, {"", ""},
            {"拒收订单", "refuse.jsp"}, {"", ""},
            {"客户取消", "qorders.jsp"}, {"", ""},
    };
%>
<%@ include file="../inc/titlebar.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
<td>
<table width="100%" border="0" cellpadding="0">
<tr bgcolor="#F4F4F4" align="center">
    <td class="moduleTitle"><font color="#48758C">订单<font color=red><%=orderid%>
    </font>详细信息(创建日期<%=createdate%>)  <a href="peisong.jsp?orderid=<%=orderid%>" target=_blank>打印订单</a></font></td>
</tr>
<tr bgcolor="#d4d4d4" align="right">
<td>
<table width="100%" border="0" cellpadding="2" cellspacing="1">
<%
    list = orderMgr.getDetailList(orderid);
    Order orderex = new Order();
    String name = "";
    String price = "";
    String typestr = "";
    String P_sitename = "";
    String P_company = "";
    String phone = "";
    if ((order.getPhone() != null) && (!"".equals(order.getPhone()))) {
        phone = order.getPhone();
        phone = StringUtil.gb2iso4View(phone);
    } else {
        phone = orderMgr.getPhone(orderid);
        phone = StringUtil.gb2iso4View(phone);
    }
%>
<tr>
    <td align="center"><%=StringUtil.gb2iso4View(order.getName())%></td>
    <td align="center" colspan="2"><!--%=StringUtil.gb2iso4View(order.getAddress())%--></td>
    <td align="center"><!--%=StringUtil.gb2iso4View(order.getPostcode())%--></td>
    <td align="center"><%=phone%></td>
    <td align="center"><%if (payway == null) {%>
        货到付款<%} else {%><%=payway.getCname() == null ? "--" : StringUtil.gb2iso4View(payway.getCname())%><%}%>
    </td>
    <td align="center"><%if (fee == null) {%>同城送货<%}else{%><%=fee.getCname()==null?"--":StringUtil.gb2iso4View(fee.getCname())%>
        <%}%></td>
    <td>
        <font color="red">
            <%if (order.getNouse() == 0) {%>客户取消<%
        } else {
            if (order.getStatus() == 1) {
        %>处理中<%
        } else {
            if (order.getStatus() == 2) {
        %>发货<%
        } else {
            if (order.getStatus() == 3) {
        %>退货<%
        } else {
            if (order.getStatus() == 4) {
        %>完成<%
        } else {
            if (order.getStatus() == 5) {
        %>拒收<%
        } else {
            if (order.getStatus() == 6) {
        %>退货订单<%
        } else {
            if (order.getStatus() == 7) {
        %><font color="#FF0000">缺货</font><%
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        %></font></td>
    <td>&nbsp;</td>
</tr>
<tr bgcolor="#FFFFFF">
    <td align="center">产品名称</td>
    <td align="center">售价</td>
    <td align="center">订购数</td>
    <td align="center">站点名称</td>
    <td align="center">供货商</td>
    <!--td align="center">收件人姓名</td>
   <td align="center">收件人地址</td>
   <td align="center">收件人邮编</td>
   <td align="center">收件人电话</td>
   <td align="center">付款方式</td>
   <td align="center">地区</td>
   <td align="center">取货方式</td>
   <!--td align="center">订单状态</td-->
    <td align="center">使用积分</td>
    <td align="center">使用购物券</td>
    <td align="center">总费用</td>
    <td align="center">邮寄费用</td>
    <td align="center">送货时间</td>
</tr>
<%
    for (int i = 0; i < list.size(); i++) {
        orderex = (Order) list.get(i);
        product = productMgr.getAProduct(orderex.getProductid());
        SiteInfo si = siteMgr.getSiteInfo(product.getSiteID());
        if (si != null) P_sitename = si.getDomainName();
        if (product.getSuppliername() != null) P_company = product.getSuppliername();
        name = StringUtil.gb2iso4View(orderex.getProductname());
        price = String.valueOf(product.getSalePrice());
        //购物券信息
        Card card = orderMgr.getACardInfo(orderex.getCardid());
        if (product.getSiteID() != the_siteid) chaidanflag = true;
        String linktime = "";
        if(order.getLinktime() == null){
            linktime = "工作日、双休日与假日均可送货";
        }
        else{
            if(linktime.equals("0")){

            }
            else if(linktime.equals("1")){
                  linktime = "只工作日送货(双休日、假日不用送)";
            }
            else{
                linktime = "只双休日、假日送货(工作日不用送)";
            }
        }
%>
<tr bgcolor="#FFFFFF">
    <td align="center"><%=name == null ? "--" : name%></td>
    <td align="center"><%=orderex.getSaleprice()%></td>
    <td align="center"><%=orderex.getOrderNum()%></td>
    <td align="center"><%=P_sitename%></td>
    <td align="center"><%=P_company%></td>
    <td align="center"><%=df.format(order.getUserscores())%></td>
    <td align="center"><%=order.getUsecard()%></td>
    <td align="center"><%=df.format(order.getPayfee())%></td>
    <td align="center"><%=df2.format(order.getDeliveryfee())%></td>
    <td align="center"><%=linktime%></td>
</tr>
<%}%>
</table>
</td>
</tr>
    <tr bgcolor="#d4d4d4">
<td align="left">

    </td>
    </tr>
</table>
</td>
</tr>
</table>
<table width="30%" border="0" cellpadding="2" cellspacing="1"  bgcolor="#F4F4F4" align="left">
                                <tr bgcolor="#FFFFFF">
                                    <td width="100%" colspan="2">发票信息：</td>
                                </tr>
        <%if(invoice != null){
             int content = invoice.getContent();
            Invoice c = orderMgr.getInvoiceConenteById(content);
            String cname = "";
            if(c == null){
                cname = "明细";
            }
            else{
                cname = c.getContentinfo() == null?"明细":StringUtil.gb2iso4View(c.getContentinfo());
            }
        %>
        <tr bgcolor="#FFFFFF">
                                    <td align="right">发票类型：</td>
                                    <td align="left"><%if(invoice.getInvoicetype() == 0){out.print("普通发票");}else{out.print("增值税发票");}%></td>
                                </tr>
                                <tr bgcolor="#FFFFFF">
                                    <td align="right">发票抬头：</td>
                                    <td align="left"><%if(invoice.getTitle() == 0){out.print("个人");}else{out.print("单位");}%></td>
                                </tr>
                                 <tr bgcolor="#FFFFFF">
                                    <td align="right">单位名称：</td>
                                    <td align="left"><%=invoice.getCompanyname() == null?"":StringUtil.gb2iso4View(invoice.getCompanyname())%></td>
                                </tr>
                                <tr bgcolor="#FFFFFF">
                                    <td align="right">发票内容：</td>
                                    <td align="left"><%=cname%></td>
                                </tr>
                                <tr bgcolor="#FFFFFF">
                                    <td align="right">纳税人识别号：</td>
                                    <td align="left"><%=invoice.getIdentification() == null?"":StringUtil.gb2iso4View(invoice.getIdentification())%></td>
                                </tr>
                                <tr bgcolor="#FFFFFF">
                                    <td align="right">注册地址：</td>
                                    <td align="left"><%=invoice.getRegisteraddress()== null?"":StringUtil.gb2iso4View(invoice.getRegisteraddress() )%></td>
                                </tr>
                                <tr bgcolor="#FFFFFF">
                                    <td align="right">注册电话：</td>
                                    <td align="left"><%=invoice.getPhone() == null?"":StringUtil.gb2iso4View(invoice.getPhone() )%></td>
                                </tr>
                                <tr bgcolor="#FFFFFF">
                                    <td align="right">开户银行：</td>
                                    <td align="left"><%=invoice.getBankname() == null?"":StringUtil.gb2iso4View(invoice.getBankname() )%></td>
                                </tr>
                                <tr bgcolor="#FFFFFF">
                                    <td align="right">银行帐号：</td>
                                    <td  align="left"><%=invoice.getBankaccount() == null?"":StringUtil.gb2iso4View(invoice.getBankaccount() )%></td>
                                </tr>
        <%}%>
                            </table>
<br>
<%--<input type="button" value="关闭" onclick="window.close();">--%>
<form action="chuku1.jsp" method="post" name="chuku">
    <input type="hidden" name="nextflag" class="input" value="1">
    <table border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
        <tr>
            <td>
                <table width="100%" border="0" cellpadding="0">
                    <tr bgcolor="#F4F4F4" align="center">
                        <td class="moduleTitle"><font color="#48758C">修改订单状态</font></td>
                    </tr>
                    <tr bgcolor="#d4d4d4" align="right">
                        <td>
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr bgcolor="#FFFFFF">
                                    <td width="146">订单编号：</td>
                                    <td width="222"><input readonly="true" class="input" name="orderid" type="text"
                                                           size="20" value="<%=orderid%>">
                                    </td>
                                </tr>
                                <tr bgcolor="#FFFFFF">
                                    <td>订单状态：</td>
                                    <td>
                                        <select name="order_status">
                                            <option value="-1">请选择</option>
                                            <option value="0">客户取消</option>
                                            <option value="1">处理中</option>
                                            <option value="7">到款</option>
                                            <option value="2">发货</option>
                                            <option value="3">退货</option>
                                            <option value="4">完成</option>
                                            <option value="5">拒收</option>
                                            <option value="6">缺货</option>
                                        </select>
                                        <font color="#FF0000">&nbsp; </font></td>
                                </tr>
                            </table>
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                    <tr align="center">
                        <td>
                            <%if (chaidanflag == true) {%>
                            <input type="button" name="tijiao" value="拆单" onclick="javascript:dofun(this)">&nbsp;&nbsp;
                            <%}%>
                            <input type="submit" name="xiugai" value="修改">&nbsp;&nbsp;
                            <input type="button" name="close" value="关闭" onclick="javascript:closewin();">
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</center>
</body>
</html>

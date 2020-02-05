<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Order.*,
                 java.text.*"
         contentType="text/html;charset=gbk" %>
<%@ include file="../../../include/auth.jsp"%>
<%
    long orderid = ParamUtil.getLongParameter(request, "orderid", 0);
    int siteid = authToken.getSiteID();
    Order theorder = new Order();
    List list = new ArrayList();
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatDateAndTime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    IOrderManager orderMgr = orderPeer.getInstance();
    theorder = orderMgr.getAOrder(orderid);
    list = orderMgr.getDetailList(orderid);

    AddressInfo addressInfo = orderMgr.getAAddresInfoForOrder(orderid);
    Invoice invoice = orderMgr.getInvoiceInfoForOrder(orderid);

%>
<html>
<head>
    <title>客户订单详情</title>
</head>
<body>
<table width="1200" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#e2e2e2">
    <tr>
        <td bgcolor="#f6f4f4" style="padding-left:30px;"><strong>请确认订单信息：</strong></td>
    </tr>
    <%
        for(int ii=0; ii<list.size(); ii++) {
            Order order = (Order)list.get(ii);
            String prodname = order.getProductname();
            Timestamp createdate = order.getCreateDate();

        /*    int bznum = order.getOrderNum();
            int subscribe = order.getSubscribe();
            int subscribenum = order.getSubscribenum();
            Timestamp userinstartdate = null;
            Timestamp userinenddate = null;
            Timestamp servicestartdate = null;
            Timestamp serviceenddate = null;

            if (order.getUserinstarttime()!=null)
                userinstartdate = new Timestamp(order.getUserinstarttime().getTime());
            if (order.getUserinendtime()!=null)
                userinenddate = new Timestamp(order.getUserinendtime().getTime());
            if (order.getServicestarttime()!=null)
                servicestartdate = new Timestamp(order.getServicestarttime().getTime());
            if (order.getServiceendtime()!=null)
                serviceenddate = new Timestamp(order.getServiceendtime().getTime());*/
    %>
    <tr>
        <td bgcolor="#ffffff" style="padding-left:30px;">
            <p>
                订单编号：<%=orderid%>（订单创建时间：<%=format.format(createdate)%>）<Br/>
                课程名称：<span class="red"><%=(prodname!=null)?prodname:""%></span><Br/>
                <%--报纸份数：<%=bznum%><Br/>--%>
                课程金额：<%=order.getSaleprice()%>元<Br/>
                报名时间：<%=formatDateAndTime.format(order.getCreateDate())%><Br/>
            </p>
            <%--<p>
                订阅方式<br/>
                <%
                    if(subscribe==1) {
                        out.println("年订：根据系统设置的订阅规则计算出的订阅时间为" + format.format(servicestartdate)+"---" + format.format(serviceenddate));
                        //out.println("<br />您的订阅价格：" + orderAndOrderdetail.getPAYFEE());
                    } else if (subscribe==2) {
                        out.println("半年订：根据系统设置的订阅规则计算出的订阅时间为" + format.format(servicestartdate)+"---" + format.format(serviceenddate));
                        //out.println("<br />您的订阅价格：" + orderAndOrderdetail.getPAYFEE());
                    } else if (subscribe==3) {
                        out.println("季订：您订阅了" + subscribenum + "个季度，根据系统设置的订阅规则计算出的订阅时间为" + format.format(servicestartdate)+"---" + format.format(serviceenddate));
                        //out.println("<br />您的订阅价格：" + orderAndOrderdetail.getPAYFEE());
                    } else if (subscribe==4) {
                        out.println("月订：您订阅了" + subscribenum + "个月，根据系统设置的订阅规则计算出的订阅时间为" + format.format(servicestartdate)+"---" + format.format(serviceenddate));
                        //out.println("<br />您的订阅价格：" + orderAndOrderdetail.getPAYFEE());
                    }
                %>
            </p>--%>
            <%}%>
          <%--  <p>
                配送信息<Br/>
                收报人姓名（单位）：<%=(addressInfo!=null)?addressInfo.getName():""%><Br/>
                座机：<%=(addressInfo!=null)?(addressInfo.getPhone()==null)?"":addressInfo.getPhone():""%><Br/>
                手机：<%=(addressInfo!=null)?(addressInfo.getMobile()==null)?"":addressInfo.getMobile():""%><Br/>
                地址：<%=(addressInfo!=null)?addressInfo.getAddress():""%><Br/>
                邮编：<%=(addressInfo!=null)?addressInfo.getZip():""%><Br/>
                备注：<%=(addressInfo!=null)?(addressInfo.getNotes()==null)?"":addressInfo.getNotes():""%><Br/>
            </p>
            <%if (invoice!=null) {%>
            <p>
                发票信息<Br/>
                <%if (theorder.getNees_invoice()==0) {%>
                是否要发票：不要发票<Br/>
                <%} else {%>
                是否要发票：要发票<Br/>
                <%}%>
                <%if (theorder.getNees_invoice()==1) {%>
                发票类型：电子专项发票<Br/>
                <%} else if (theorder.getNees_invoice()==2){%>
                发票类型：电子普通发票<Br/>
                <%} else {%>
                发票类型：未知发票类型<Br/>
                <%}%>
                发票单位：<%=invoice.getCompanyname()%><Br/>
                税号：<%=invoice.getIdentification()%><Br/>
                地址：<%=(invoice.getRegisteraddress()==null)?"":invoice.getRegisteraddress()%><Br/>
                联系电话：<%=(invoice.getPhone()==null)?"":invoice.getPhone()%><Br/>
                开户银行：<%=invoice.getBankname()%><Br/>
                银行账号：<%=invoice.getBankaccount()%><Br/>
                发票内容：<%=invoice.getContentinfo()%><Br/>
                &lt;%&ndash;电子邮件：<%=(invoice.getEmail()==null)?"":invoice.getEmail()%><Br/>&ndash;%&gt;
            </p>
            <%}%>--%>
        </td>
    </tr>
</table>
</body>
</html>

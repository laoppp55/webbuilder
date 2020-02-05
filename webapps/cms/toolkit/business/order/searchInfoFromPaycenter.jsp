<%@page language="java" contentType="text/html;charset=gbk"%>
<%@page import="com.yeepay.PaymentForOnlineService,com.yeepay.QueryResult"%>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.wxpay.WXPayService" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.bizwink.cms.business.Order.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%!
    String formatString(String text){
        if(text == null) return "";  return text;
    }
%>
<%@ include file="../../../include/auth.jsp"%>
<%
    IOrderManager orderManager = orderPeer.getInstance();

    int payway = ParamUtil.getIntParameter(request,"payway",0);
    int startrow = ParamUtil.getIntParameter(request,"start",0);
    int doUpdate = ParamUtil.getIntParameter(request,"startflag",0);
    String p2_Order   = formatString(request.getParameter("orderid"));
    List list = orderManager.getDetailList(Long.parseLong(p2_Order));
    int status = orderManager.getStatus(Long.parseLong(p2_Order));
    Map<String, String> qr = null;
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
    SimpleDateFormat disp_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String payresult = "";                                 //订单支付结果，成功为“SUCCESS”
    String paylsh = null;                                    //支付中心返回的交易流水号
    String mch_id = null;                                    //商户ID
    String jy_type = null;                                   //交易类型，银行支付为bank，微信扫码支付为NATIVE
    String paydatetime = null;                               //支付完成的时间
    int retcode = -1;                                        //支付中心已经完成了支付，但是支付状态没有返回，手工修改支付状态的返回码，正常返回为0
    if (doUpdate == 0) {
        try {
            if (payway == 2) {                                                      //银行支付查询
                qr = PaymentForOnlineService.queryByOrder(p2_Order);                // 调用后台外理查询方法
                if (qr.get("rb_PayStatus").equals("SUCCESS")) {
                    payresult = qr.get("rb_PayStatus");
                    paylsh = qr.get("r2_TrxId");
                    mch_id = qr.get("mch_id");
                    jy_type = "bank";
                    paydatetime = qr.get("ry_FinshTime");
                    //out.println("业务类型:" + qr.get("r0_Cmd") + "<br>");
                    //out.println("查询结果:" + qr.get("r1_Code") + "<br>");
                    String prodname = "";
                    for (int ii = 0; ii < list.size(); ii++) {
                        Order order = (Order) list.get(ii);
                        prodname = order.getProductname();
                    }
                    out.println("商品名称:" + prodname + "<br>");
                    out.println("订单号:" + qr.get("r6_Order") + "<br>");
                    out.println("支付交易流水号:" + qr.get("r2_TrxId") + "<br>");
                    out.println("支付金额:" + qr.get("r3_Amt") + "<br>");
                    out.println("交易币种:" + qr.get("r4_Cur") + "<br>");
                    out.println("支付状态:" + qr.get("rb_PayStatus") + "<br>");
                    //out.println("支付创建时间:" +  qr.get("rx_CreateTime") + "]<br>");
                    out.println("支付完成时间:" + qr.get("ry_FinshTime") + "<br>");
                    if (qr.get("hmacError") != null)
                        out.println("验证信息错误:" + qr.get("hmacError") + "<br>");
                    else
                        out.println("验证信息错误:" + "<br>");
                    if (qr.get("errorMsg") != null)
                        out.println("错误信息:" + qr.get("errorMsg") + "<br>");
                    else
                        out.println("错误信息:" + "<br>");

                    //out.println("商户扩展信息:" +  qr.get("r8_MP") + "<br>");
                    //out.println("已退款次数 [rc_RefundCount:" + qr.get("rc_RefundCount") + "]<br>");
                    //out.println("已退款金额 [rd_RefundAmt:" + qr.get("rd_RefundAmt") + "]<br>");
                    //out.println("[rw_RefundRequestID:" +  qr.get("rw_RefundRequestID") + "]<br>");
                    //out.println("[rz_RefundAmount:" +  qr.get("rz_RefundAmount") + "]<br>");
                } else {
                    out.println("订单" + p2_Order + "未支付<br>");
                }
            } else if (payway == 0) {
                //查询微信付款信息
                WXPayService wxPayService = WXPayService.getInstance();
                qr = wxPayService.tradeQuery(p2_Order);
                System.out.println("查询微信订单信息" + qr.get("result_code"));
                if (qr.get("result_code").equals("SUCCESS") && qr.get("trade_state_desc").equals("支付成功")) {
                    payresult = qr.get("result_code");
                    paylsh = qr.get("transaction_id");
                    mch_id = qr.get("mch_id");
                    jy_type = qr.get("trade_type");
                    paydatetime = qr.get("time_end");
                    Date date = format.parse(qr.get("time_end"));
                    out.println("支付交易流水号" + qr.get("transaction_id") + "<br>");
                    out.println("订单号：" + qr.get("out_trade_no") + "<br>");
                    String prodname = "";
                    for (int ii = 0; ii < list.size(); ii++) {
                        Order order = (Order) list.get(ii);
                        prodname = order.getProductname();
                    }
                    out.println("订阅报纸：" + prodname + "<br>");
                    out.println("支付金额：" + Float.parseFloat(qr.get("total_fee")) / 100 + "<br>");
                    out.println("支付币种：" + qr.get("fee_type") + "<br>");
                    out.println("支付结果描述：" + qr.get("trade_state_desc") + "<br>");
                    out.println("支付完成时间：" + disp_format.format(date) + "<br>");

                    //out.println(qr.get("body"));
                    //out.println(qr.get("product_id"));
                    //out.println(qr.get("return_code"));
                    //out.println(qr.get("result_code"));
                    //out.println(qr.get("cash_fee"));
                    //out.println(qr.get("attach"));
                } else {
                    out.println("订单" + p2_Order + "未支付<br>");
                }
            }
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    } else if (status!=8){
        String orderid = ParamUtil.getParameter(request,"orderid");
        payresult = ParamUtil.getParameter(request,"payresult");
        payway = ParamUtil.getIntParameter(request,"payway",0);
        paylsh = ParamUtil.getParameter(request,"lsh");
        mch_id = ParamUtil.getParameter(request,"mchid");
        jy_type = ParamUtil.getParameter(request,"jytype");
        paydatetime = ParamUtil.getParameter(request,"paydatetime");
        format = new SimpleDateFormat("yyyyMMddhhmmss");

        Order order = new Order();
        order.setOrderid(Long.parseLong(orderid));                                            //订单号
        order.setR2TrxId(paylsh);                                                //支付方返回的交易流水号
        order.setZfmemberid(mch_id);                                               //商户ID
        order.setR2type(jy_type);                                                             //交易类型：扫码支付
        order.setPayresult(payresult);                                         //交易返回结果
        order.setPaydate(new Timestamp(format.parse(paydatetime).getTime()));   //交易完成时间
        order.setPayWay(payway);                                                                   //0微信支付，1支付宝，2银行支付
        order.setStatus(8);
        //8表示正常订单，已经完成支付
        retcode = orderManager.updateOrderinfoByZhifuResult(order);
    }
%>
<html>
<head>
    <title>修改订单状态</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" href="../../images/pt9.css">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <script type="text/javascript" src="../../../js/jquery-1.11.1.min.js"></script>
    <script language="javascript">
        var errcode = <%=retcode%>;
        $(document).ready(function() {
            if (errcode == 0) {
                alert("订单支付状态已经修改");
                opener.location.href = "index.jsp?startrow=" + searchForm.start.value;
                window.close();
            }
        });

        function closewin(){
            window.close();
        }
    </script>
</head>

<body bgcolor="#FFFFFF">
<%if (status!=8 && payresult.equals("SUCCESS")) {  //订单支付状态不等于8并且订单查询结果是支付成功的状态下提示修改订单的支付状态%>
<center><br>
    <form name="searchForm" action="searchInfoFromPaycenter.jsp" method="post">
        <input type="hidden" name="startflag" value="1">
        <input type="hidden" name="start" value="<%=startrow%>">
        <input type="hidden" name="orderid" value="<%=p2_Order%>">
        <input type="hidden" name="payresult" value="<%=payresult%>">
        <input type="hidden" name="payway" value="<%=payway%>">
        <input type="hidden" name="lsh" value="<%=paylsh%>">
        <input type="hidden" name="mchid" value="<%=mch_id%>">
        <input type="hidden" name="jytype" value="<%=jy_type%>">
        <input type="hidden" name="paydatetime" value="<%=paydatetime%>">
        <p>支付中心显示用户已经完成了支付，但是支付中心的信息由于网络原因未能同步该信息到网站，请点击“确认修改”按钮修改订单支付状态</p>
        <p align="center"><input type="submit" name="Ok" value="确认修改">&nbsp;&nbsp;<input type="button" name="close" value="关闭" onclick="javascript:closewin();"></p>
    </form>
</center>
<%}%>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-9-23
  Time: 下午7:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="net.sf.json.JSON" %>
<%@ page import="net.sf.json.xml.XMLSerializer" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.wxpay.WXPayService" %>
<%@ page import="com.bizwink.cms.news.UnifiedOrderRespose" %>
<%@ page import="com.bizwink.mysql.service.MEcService" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.cms.news.RefundResponse" %>
<%
    //logger.info("****************************************wxReturnPay微信的回调函数被调用******************************");
    String inputLine;
    String notityXml = "";
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    response.setHeader("Access-Control-Allow-Origin", "*");
    // 微信给返回的东西
    try {
        while ((inputLine = request.getReader().readLine()) != null) {
            notityXml += inputLine;
        }
        request.getReader().close();
    } catch (Exception e) {
        e.printStackTrace();
        //logger.info("xml获取失败");
        response.getWriter().write("xml获取失败");
        return;
    }
    if (StringUtils.isEmpty(notityXml)) {
        //logger.info("xml为空");
        response.getWriter().write( "xml为空");
        return;
    }

    WXPayService wxService = WXPayService.getInstance();
    if(!wxService.checkSign(notityXml)) {
        response.getWriter().write( "验签失败");
    }
    //logger.info("xml的值为：" + notityXml);
    XMLSerializer xmlSerializer = new XMLSerializer();
    JSON json = xmlSerializer.read(notityXml);
    //logger.info(json.toString());
    JSONObject jsonObject=JSONObject.fromObject(json.toString());
    RefundResponse reFundReturninfo = (RefundResponse) JSONObject.toBean(jsonObject, RefundResponse.class);
    //logger.info(("转换后的实体bean为："+returnPay.toString()));
    //logger.info(("订单号："+returnPay.getOut_trade_no()+"价格："+returnPay.getTotal_fee()));
    if (reFundReturninfo.getReturn_code().equals("SUCCESS") && reFundReturninfo.getOut_trade_no() != null && !reFundReturninfo.getOut_trade_no().isEmpty()) {
        double fee = (reFundReturninfo.getTotal_fee())/100.0;
        //logger.info("微信的支付状态为SUCCESS");
        //在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
        ApplicationContext appContext = SpringInit.getApplicationContext();
        MEcService mEcService = null;
        if (appContext!=null) {
            mEcService = (MEcService)appContext.getBean("MEcService");
            mEcService.UpdatePayflag(Long.parseLong(reFundReturninfo.getOut_trade_no()),reFundReturninfo.getTransaction_id(),reFundReturninfo.getMch_id(),"退款",reFundReturninfo.getReturn_code(),1);
            out.println("<script   lanugage=\"javascript\">alert(\"退款成功！\");window.close();</script>");
            return;
        }else {
            response.sendRedirect("/error.jsp?errcode=-1");
            return;
        }
    }
%>

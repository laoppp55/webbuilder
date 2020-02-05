<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.business.Product.Product" %>
<%@ page import="com.bizwink.cms.business.Product.productPeer" %>
<%@ page import="com.bizwink.cms.business.Product.IProductManager" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.bizwink.cms.business.Order.*" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    long orderid = ParamUtil.getLongParameter(request, "orderid", 0);

    IOrderManager oMgr = orderPeer.getInstance();
    List list = new ArrayList();
    list = oMgr.getDetailList(orderid);

    Order order = new Order();
    order = oMgr.getAOrder(orderid);

    DecimalFormat df = new DecimalFormat();
    df.applyPattern("0.00");
    DecimalFormat df2 = new DecimalFormat();
    df2.applyPattern("0");
    Fee fee = new Fee();
    fee = oMgr.getAFeeInfo(order.getSendWay());
    SendWay payway = oMgr.getASendWayInfo(order.getPayWay());
    String paywaystr = "";
    if (payway == null) {
        paywaystr = "货到付款";
    } else {
        paywaystr = payway.getCname() == null ? "--" : StringUtil.gb2iso4View(payway.getCname());
    }
    String feestr = "";
    if (fee == null) {
        feestr = "同城送货";
    } else {
        feestr = fee.getCname() == null ? "--" : StringUtil.gb2iso4View(fee.getCname());
    }
    String status = "";
    if (order.getStatus() == 0) {
        status = "客户取消";
    }
    if (order.getStatus() == 1) {
        status = "处理中";
    }
    if (order.getStatus() == 2) {
        status = "发货";
    }
    if (order.getStatus() == 3) {
        status = "退货";
    }
    if (order.getStatus() == 4) {
        status = "完成";
    }
    if (order.getStatus() == 5) {
        status = "拒收";
    }
    if (order.getStatus() == 6) {
        status = "缺货";
    }
    if (order.getStatus() == 7) {
        status = "等待付款";
    }
    String outstr = "<table width=\"100%\" border=\"0\" cellpadding=\"0\" class=\"biz_table\">";

    outstr = outstr + "<td class=\"moduleTitle\"><font color=\"#48758C\">订单<font color=red>" + orderid + "</font>详细信息(创建日期" + order.getCreateDate().toString().substring(0, 10)
            + ")</font></td>\n" +
            "</tr>\n" +
            "<tr bgcolor=\"#d4d4d4\" align=\"right\">\n" +
            "<td>\n" +
            "<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\n" +
            "    <tr bgcolor=\"#FFFFFF\">\n" +
            "        <td align=\"center\">产品名称</td>\n" +
            "        <td align=\"center\">售价</td>\n" +
            "        <td align=\"center\">订购数</td>\n" +
            "        <td align=\"center\">收件人姓名</td>\n" +
            "        <td align=\"center\">收件人地址</td>\n" +
            "        <td align=\"center\">收件人邮编</td>\n" +
            "        <td align=\"center\">收件人电话</td>\n" +
            "        <td align=\"center\">付款方式</td>\n" +
            "        <!--<td align=\"center\">地区</td>-->\n" +
            "        <td align=\"center\">取货方式</td>\n" +
            "        <td align=\"center\">订单状态</td>\n" +
            "        <td align=\"center\">总费用</td>\n" +
            "        <td align=\"center\">邮寄费用</td>\n" +
            "    </tr>";
    IProductManager productMgr = productPeer.getInstance();
    Product product = new Product();
    for (int i = 0; i < list.size(); i++) {
        Order orderex = (Order) list.get(i);
        product = productMgr.getAProduct(orderex.getProductid());
        String phone = "";
        if ((order.getPhone() != null) && (!"".equals(order.getPhone()))) {
            phone = order.getPhone();
            phone = StringUtil.gb2iso4View(phone);
        } else {
            phone = oMgr.getPhone(orderid);
            phone = StringUtil.gb2iso4View(phone);
        }
        String name = StringUtil.gb2iso4View(product.getMainTitle());
        String price = String.valueOf(product.getSalePrice());
        outstr = outstr + " <tr bgcolor=\"#FFFFFF\">\n" +
                "        <td align=\"center\">" + name + "</td>\n" +
                "        <td align=\"center\">" + orderex.getSaleprice() +
                "</td>\n" +
                "        <td align=\"center\">" + orderex.getOrderNum() +
                "</td>\n" +
                "        <td align=\"center\">" + StringUtil.gb2iso4View(order.getName()) +
                "</td><td align=\"center\">" + StringUtil.gb2iso4View(order.getAddress()) +
                "</td><td align=\"center\">" + StringUtil.gb2iso4View(order.getPostcode())+
                "</td><td align=\"center\">" + phone + "</td><td align=\"center\">" + paywaystr +
                "</td>\n" +
                "        <td align=\"center\">" + feestr +
                "</td>\n" +
                "        <td>\n" +
                "            <font color=\"red\">" + status +
                "</font></td>\n" +
                "        <td align=\"center\">" + df.format(order.getPayfee()) +
                "</td>\n" +
                "        <td align=\"center\">" + df2.format(order.getDeliveryfee()) +
                "</td>\n" +
                "    </tr>";
    }
    outstr = outstr + "</table>";


    out.write(outstr);
%>
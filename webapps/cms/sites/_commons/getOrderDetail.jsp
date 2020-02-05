<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.business.Product.Product" %>
<%@ page import="com.bizwink.cms.business.Product.productPeer" %>
<%@ page import="com.bizwink.cms.business.Product.IProductManager" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.bizwink.cms.business.Order.*" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    long orderid = ParamUtil.getLongParameter(request, "orderid", 0);
    int markID = ParamUtil.getIntParameter(request, "markid", 0);

    IOrderManager oMgr = orderPeer.getInstance();
    List list = new ArrayList();
    list = oMgr.getDetailList(orderid);

    Order order = new Order();
    order = oMgr.getAOrder(orderid);

    IMarkManager markMgr = markPeer.getInstance();
    String listStyle = "";
    int siteid = 0;
    if (markID > 0) {

        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");

        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        listStyle = properties.getProperty(properties.getName().concat(".LISTSTYLE"));
        String siteids = properties.getProperty(properties.getName().concat(".SITEID"));
        if (siteids != null && !siteids.equalsIgnoreCase("null") && !siteids.equals("")) {
            siteid = Integer.parseInt(siteids);
        }

    }
    String outstr = "";
    String head = "";
    String tail = "";
    if (listStyle != null) {

        listStyle = listStyle.substring(0, listStyle.length() - 1);
        int posi = listStyle.indexOf("<" + "%%begin%%" + ">");
        if (posi > -1) {
            head = listStyle.substring(0, posi);
            listStyle = listStyle.substring(posi + ("<" + "%%begin%%" + ">").length());
        }
        posi = listStyle.indexOf("<" + "%%end%%" + ">");
        if (posi > -1) {
            tail = listStyle.substring(posi + ("<" + "%%end%%" + ">").length());
            listStyle = listStyle.substring(0, posi);
        }
    }
    //处理循环之前部分  订单信息
    if (order != null) {
        String line = head;
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
        String name = order.getName() == null ? "--" : StringUtil.gb2iso4View(order.getName());
        String address = order.getAddress() == null ? "--" : StringUtil.gb2iso4View(order.getAddress());
        String postcode = order.getPostcode() == null ? "--" : StringUtil.gb2iso4View(order.getPostcode());
        String phone = order.getPhone() == null ? "--" : StringUtil.gb2iso4View(order.getPhone());
        int payway = order.getPayWay();
        SendWay paywayinfo = new SendWay();
        paywayinfo = oMgr.getASendWayInfo(payway);
        String paywayname = "--";
        if (paywayinfo != null) {
            paywayname = paywayinfo.getCname() == null ? "--" : StringUtil.gb2iso4View(paywayinfo.getCname());
        }
        int sendway = order.getSendWay();
        Fee fee = new Fee();
        fee = oMgr.getAFeeInfo(sendway);
        String sendwayname = "--";
        if (fee != null) {
            sendwayname = fee.getCname() == null ? "--" : StringUtil.gb2iso4View(fee.getCname());
        }
        float totalfee = order.getPayfee();
        int payflag = order.getPayflag();
        String payflags = "<a href=\"/_commons/dopay.jsp?orderid="+order.getOrderid()+"\">未支付</a>";
        if (payflag == 1) {
            payflags = "已支付";
        }
        float sendfee = order.getDeliveryfee();
        int scores = order.getUserscores();
        Timestamp create = order.getCreateDate();
        String createdate = create.toString().substring(0, 16);

        line = StringUtil.replace(line, "<" + "%%orderid%%" + ">", String.valueOf(order.getOrderid()));
        line = StringUtil.replace(line, "<" + "%%name%%" + ">", name);
        line = StringUtil.replace(line, "<" + "%%address%%" + ">", address);
        line = StringUtil.replace(line, "<" + "%%postcode%%" + ">", postcode);
        line = StringUtil.replace(line, "<" + "%%phone%%" + ">", phone);
        line = StringUtil.replace(line, "<" + "%%payway%%" + ">", paywayname);
        line = StringUtil.replace(line, "<" + "%%sendway%%" + ">", sendwayname);
        line = StringUtil.replace(line, "<" + "%%status%%" + ">", status);
        line = StringUtil.replace(line, "<" + "%%totalfee%%" + ">", String.valueOf(totalfee));
        line = StringUtil.replace(line, "<" + "%%sendfee%%" + ">", String.valueOf(sendfee));
        line = StringUtil.replace(line, "<" + "%%scores%%" + ">", String.valueOf(scores));
        line = StringUtil.replace(line, "<" + "%%payflag%%" + ">", payflags);
        line = StringUtil.replace(line, "<" + "%%createdate%%" + ">", createdate);
        outstr = outstr + line;
        //处理商品列表
        float allprice = 0;
        IProductManager productMgr = productPeer.getInstance();
        IColumnManager columnManager = ColumnPeer.getInstance();
        for (int i = 0; i < list.size(); i++) {
            String lines = listStyle;
            Order orderx = (Order) list.get(i);
            Product product = productMgr.getAProduct(orderx.getProductid());
            if (product != null) {
                String productname = product.getMainTitle();
                float saleprice = product.getSalePrice();
                float marketprice = product.getMarketPrice();
                int buynum = orderx.getOrderNum();
                float totalprice = buynum * saleprice;
                allprice = allprice + totalprice;
                Column column = columnManager.getColumn(product.getColumnID());
                String extfilename = null;
                if (column != null) {
                    extfilename = column.getExtname();
                }
                String createdates = product.getCreateDate().toString().substring(0, 10).replaceAll("-", "");
                lines = StringUtil.replace(lines, "<" + "%%productname%%" + ">", StringUtil.gb2iso4View(productname));
                lines = StringUtil.replace(lines, "<" + "%%productnameurl%%" + ">", "<a href=\"" + product.getDirName() + createdates + "/" + orderx.getProductid() + "." + extfilename + "\">" + StringUtil.gb2iso4View(productname) + "</a>");
                lines = StringUtil.replace(lines, "<" + "%%saleprice%%" + ">", String.valueOf(saleprice));
                lines = StringUtil.replace(lines, "<" + "%%marketprice%%" + ">", String.valueOf(marketprice));
                lines = StringUtil.replace(lines, "<" + "%%score%%" + ">", String.valueOf(product.getScores()));
                lines = StringUtil.replace(lines, "<" + "%%producttotal%%" + ">", String.valueOf(totalprice));
                lines = StringUtil.replace(lines, "<" + "%%productnum%%" + ">", String.valueOf(buynum));
            }else{
                lines = "";
            }
            outstr = outstr + lines;
        }
        //处理商品列表之后的数据
        tail = StringUtil.replace(tail, "<" + "%%orderid%%" + ">", String.valueOf(order.getOrderid()));
        tail = StringUtil.replace(tail, "<" + "%%name%%" + ">", name);
        tail = StringUtil.replace(tail, "<" + "%%address%%" + ">", address);
        tail = StringUtil.replace(tail, "<" + "%%postcode%%" + ">", postcode);
        tail = StringUtil.replace(tail, "<" + "%%phone%%" + ">", phone);
        tail = StringUtil.replace(tail, "<" + "%%payway%%" + ">", paywayname);
        tail = StringUtil.replace(tail, "<" + "%%sendway%%" + ">", sendwayname);
        tail = StringUtil.replace(tail, "<" + "%%status%%" + ">", status);
        tail = StringUtil.replace(tail, "<" + "%%totalfee%%" + ">", String.valueOf(totalfee));
        tail = StringUtil.replace(tail, "<" + "%%sendfee%%" + ">", String.valueOf(sendfee));
        tail = StringUtil.replace(tail, "<" + "%%scores%%" + ">", String.valueOf(scores));
        tail = StringUtil.replace(tail, "<" + "%%payflag%%" + ">", payflags);
        tail = StringUtil.replace(tail, "<" + "%%createdate%%" + ">", createdate);
        tail = StringUtil.replace(tail, "<" + "%%totalprice%%" + ">", String.valueOf(totalfee));
        tail = StringUtil.replace(tail, "<" + "%%card%%" + ">", String.valueOf(order.getUsecard()));
        outstr = outstr + tail;
        out.write(outstr);
    } else {
        out.write("");
    }

%>
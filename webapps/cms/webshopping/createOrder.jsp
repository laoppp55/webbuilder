<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.lang.Math" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.business.Product.IProductManager" %>
<%@ page import="com.bizwink.cms.business.Product.Product" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.cms.business.Order.*" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Product.productPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%

    List list = new ArrayList();
    list = (List) session.getAttribute("en_list");
    if (list == null) {
        List glist = new ArrayList();
        list = glist;
        session.setAttribute("en_list", glist);
    }
    System.out.println("LIST = "+list.size());
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    //通过session获得用户id
    int userid = 0;

    IOrderManager orderMgr = orderPeer.getInstance();
    String sendwaystr = (String) session.getAttribute("sendway");
    int sendway = 0;
    if (sendwaystr != null) {
        sendway = Integer.parseInt(sendwaystr);
    }
    float totalprice = 0;

    String paystr = (String) session.getAttribute("payway");
    int payway = -1;
    if (paystr != null) {
        payway = Integer.parseInt(paystr);
    }
    String cidstr = (String) session.getAttribute("info");
    int cid = -1;
    if (cidstr != null) {
        cid = Integer.parseInt(cidstr);
    }
    int errcode = -1;
    String outstr = "false";
    if (cid > 0 && payway > 0 && sendwaystr != null) {
        //获得运费
        int type = sendway;
        Fee fee = new Fee();
        fee = orderMgr.getAFeeInfo(type);
        float sendfee = 0;
        if (fee != null) {
            sendfee = fee.getFee();
        }
        //获得收货人信息
        AddressInfo addressinfo = new AddressInfo();
        addressinfo = orderMgr.getAAddresInfo(cid);
        String address = addressinfo.getAddress() == null ? "" : StringUtil.gb2iso4View(addressinfo.getAddress());
        String zip = addressinfo.getZip();
        String phone = addressinfo.getPhone() + "," + addressinfo.getMobile();
        String city = addressinfo.getCity() == null ? "" : StringUtil.gb2iso4View(addressinfo.getCity());
        String province = addressinfo.getProvinces() == null ? "" : StringUtil.gb2iso4View(addressinfo.getProvinces());
        String zone = addressinfo.getZone() == null ? "" : StringUtil.gb2iso4View(addressinfo.getZone());
        String name = addressinfo.getName() == null ? "" : StringUtil.gb2iso4View(addressinfo.getName());
        //生成定单号
        long oredertime = System.currentTimeMillis();
        String str = String.valueOf(oredertime);
        str = str.substring(6, 13);
        if (str.length() < 7) {
            for (int i = 0; i < (7 - str.length()); i++) {
                str = "1" + str;
            }
        }

        String random = String.valueOf(Math.random());
        random = random.substring(random.indexOf(".") + 1, random.indexOf(".") + 4);
        str = str + random;
        if (str != null) {
            if (str.length() < 10) {
                for (int i = 0; i < (10 - str.length()); i++) {
                    str = "1" + str;
                }
            }
        }
        long orderid = Long.parseLong(str);
        str = String.valueOf(orderid);
        if (str.length() < 10) {
            for (int i = 0; i < (10 - str.length()); i++) {
                str = "1" + str;
            }
        }
        orderid = Long.parseLong(str);


        String buystr = "";
        String pid = "";
        String buynumstr = "";
        int productid = 0;
        int buynum = 0;
        float price = 0;
        String pname = "";

        IProductManager productMgr = productPeer.getInstance();
        Product product = new Product();
        String hostname = "";
        for (int i = 0; i < list.size(); i++) {
            buystr = (String) list.get(i);

            if ((buystr != null) && (!buystr.equals("")) && (!buystr.equals("null"))) {
                buystr = buystr.trim();
                //产品ID
                pid = buystr.substring(0, buystr.indexOf("_"));
                productid = Integer.parseInt(pid);
                //购买数量
                buynumstr = buystr.substring(buystr.indexOf("_") + 1, buystr.lastIndexOf("_"));
                buynum = Integer.parseInt(buynumstr);
                hostname = buystr.substring(buystr.lastIndexOf("_") + 1, buystr.length());

                product = productMgr.getAProduct(productid);
                price = product.getSalePrice();
                totalprice = totalprice + buynum * price;
            }
        }

        //获得站点id
        IFeedbackManager feedbackMgr = FeedbackPeer.getInstance();
        int siteid = 0;
        siteid = feedbackMgr.getSiteID(hostname);
        if ((startflag == 1) && (list.size() > 0)) {
            //创建订单（数据入En_orders表）
            Order order = new Order();
            order.setOrderid(orderid);
            order.setUserid(userid);
            order.setName(name);
            order.setAddress(address);
            order.setPostcode(zip);
            order.setPhone(phone);
            order.setTotalfee(totalprice);   //商品总金额
            order.setDeliveryfee(sendfee);    //运费
            order.setPayfee(totalprice + sendfee);  //用户应付总额
            order.setCreateDate(new Timestamp(System.currentTimeMillis()));
            order.setCity(city);
            order.setProvince(province);
            order.setSendWay(sendway);     //送货方式
            order.setPayWay(payway);        //支付方式
            order.setZone(zone);
            order.setSiteid(siteid);

            List order_detail = new ArrayList();
            for (int j = 0; j < list.size(); j++) {
                buystr = (String) list.get(j);

                if ((buystr != null) && (!buystr.equals("")) && (!buystr.equals("null"))) {
                    buystr = buystr.trim();
                    //产品ID
                    pid = buystr.substring(0, buystr.indexOf("_"));
                    productid = Integer.parseInt(pid);
                    //产品名称
                    product = productMgr.getAProduct(productid);
                    pname = product.getMainTitle() == null ? "" : StringUtil.gb2iso4View(product.getMainTitle());
                    //购买数量
                    buynumstr = buystr.substring(buystr.indexOf("_") + 1, buystr.lastIndexOf("_"));
                    buynum = Integer.parseInt(buynumstr);
                    //产品价格
                    price = product.getSalePrice();
                    //数据入En_orders_deail表

                    Order orders = new Order();
                    orders.setOrderid(orderid);
                    orders.setProductid(productid);
                    orders.setOrderNum(buynum);
                    orders.setSaleprice(price);
                    orders.setProductname(pname);
                    order_detail.add(orders);

                    //检查当前商品是否有附属商品，如果有附属商品，将附属商品插入订单详细信息表

                    //更改库存,如果需要修改库存,则在此处调用方法.

                }


            }
            errcode = orderMgr.createOrderInfo(order, order_detail);
        }

        //入库完成后，清空session
        List buylist = new ArrayList();
        session.setAttribute("en_list", buylist);
        session.removeAttribute("en_sell_list");
        session.setAttribute("en_totalprice", "0.00");
        session.removeAttribute("sendway");
        session.removeAttribute("payway");
        session.removeAttribute("info");
        if(errcode == 0) {
             outstr = "<orderid>"+String.valueOf(orderid)+"</orderid>";
        }
    }
    out.write(outstr);

%>
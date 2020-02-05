<%@ page import="java.sql.*,
                 java.util.*,
                 java.io.*,
                 com.bizwink.cms.business.Order.*" contentType="text/html;charset=gbk" %>
<%@ page import="com.bizwink.cms.extendAttr.ExtendAttrPeer" %>
<%@ page import="com.bizwink.cms.extendAttr.IExtendAttrManager" %>

<%
    //生成文件名
    String path = request.getRealPath("");
    Timestamp today = new Timestamp(System.currentTimeMillis());
    String todaytime = String.valueOf(today);
    todaytime = todaytime.substring(0, todaytime.indexOf(" "));
    todaytime = todaytime.replaceAll("-", "");
    File f = new File(path, todaytime + "orderhist.txt");

    //在当前目录下建立文件
    if (f.exists()) {
        f.delete();
    } else {
        f.createNewFile();
    }
    FileWriter fw = new FileWriter(f);

    //获取数据信息
    IOrderManager orderMgr = orderPeer.getInstance();
    List list = new ArrayList();
    try {
        list = orderMgr.getOrderInfo();
    } catch (OrderException e) {
        e.printStackTrace();
    }
    int userid = 0;
    long orderid;
    int sendway;
    int payway;
    int invoice;
    Timestamp createdate;
    float fee = 0;
    Order order;
    for (int i = 0; i < list.size(); i++) {
        order = (Order) list.get(i);
        userid = order.getUserid();
        orderid = order.getOrderid();
        String sorderid = String.valueOf(orderid);
        sendway = order.getSendWay();
        payway = order.getPayWay();
        invoice = order.getNees_invoice();
        createdate = order.getCreateDate();
        try {
            fee = orderMgr.getFee(sendway);
        } catch (OrderException e) {
            e.printStackTrace();
        }
        if (invoice == 0) {
            fw.write(userid + "," + sorderid + "," + sendway + "," + payway + ",否," + createdate + "," + fee + "\r\n");
        }
        if (invoice == 1) {
            fw.write(userid + "," + sorderid + "," + sendway + "," + payway + ",是," + createdate + "," + fee + "\r\n");
        }
    }
    fw.close();

    //for detail
    int productid;
    float saleprice;
    int buynum;
    int presentid;
    int scores = 0;
    float t_saleprice;

    File f1 = new File(path, todaytime + "orderdet.txt");
    if (f1.exists()) {
        f1.delete();
    } else {
        f1.createNewFile();
    }
    FileWriter fw1 = new FileWriter(f1);

    List d_list = new ArrayList();
    for (int j = 0; j < list.size(); j++) {
        order = (Order) list.get(j);
        orderid = order.getOrderid();
        String porderid = String.valueOf(orderid);
        try {
            d_list = orderMgr.getDetailList(orderid);
            scores = orderMgr.getScores(orderid);
        } catch (OrderException e) {
            e.printStackTrace();
        }
        String _scores = String.valueOf(scores);
        float fscores = Float.parseFloat(_scores);

        for (int n = 0; n < d_list.size(); n++) {
            Order d_order = (Order) d_list.get(n);
            productid = d_order.getProductid();
            saleprice = d_order.getSaleprice();
            buynum = d_order.getOrderNum();
            t_saleprice = saleprice * buynum;
            //获取商品编号
            IExtendAttrManager eMgr = ExtendAttrPeer.getInstance();
            String pnum = eMgr.getProductNum(productid, "_num");
            //输出商品信息
            if (t_saleprice > fscores) {
                fw1.write(porderid + "," + pnum + "," + saleprice + "," + buynum + ",1," + scores + "\r\n");
            } else {
                fw1.write(porderid + "," + pnum + "," + saleprice + "," + buynum + ",1,0" + "\r\n");
            }
            // 获取本商品的赠品id
            List z_list = new ArrayList();
            try {
                z_list = orderMgr.getPresent(productid);
            } catch (OrderException e) {
                e.printStackTrace();
            }
            for (int m = 0; m < z_list.size(); m++) {
                Order zorder = (Order) z_list.get(m);
                presentid = zorder.getPresentid();
                String pnum1 = eMgr.getProductNum(presentid,"_num");
                float psaleprice = orderMgr.getZprice(presentid);
                fw1.write(porderid + "," + pnum1 + "," + psaleprice + "," + "1" + ",0,0" + "\r\n");
            }
        }
    }
    fw1.close();

    //for userinfo
    String username;
    String gender;
    String birthday = "";
    int province;
    String address;
    String wedding;
    String email;
    String phone;
    String mobile;
    int gender1 = -1;
    String pphone1 = "";
    String pphone2 = "";
    String pphone3 = "";
    int userid1;
    Uregister ureg = new Uregister();

    File f2 = new File(path, todaytime + "contact.txt");
    if (f2.exists()) {
        f2.delete();
    } else {
        f2.createNewFile();
    }
    FileWriter fw2 = new FileWriter(f2);

    File f3 = new File(path, todaytime + "phone.txt");
    if (f3.exists()) {
        f3.delete();
    } else {
        f3.createNewFile();
    }
    FileWriter fw3 = new FileWriter(f3);

    for (int l = 0; l < list.size(); l++) {
        order = (Order) list.get(l);
        userid1 = order.getUserid();

        try {
            ureg = orderMgr.getUserInfo(userid1);
        } catch (OrderException e) {
            e.printStackTrace();
        }
        username = ureg.getUsername();
        username = username == null ? "" : username.trim();
        gender = ureg.getGender();
        if (gender != null && gender.equals("男")) {
            gender1 = 1;
        }
        if (gender != null && gender.equalsIgnoreCase("女")) {
            gender1 = 2;
        }
        province = ureg.getProvience();
        address = ureg.getAddress();
        address = address == null ? "" : address.trim();
        wedding = ureg.getWedding();
        email = ureg.getEmail();
        email = email == null ? "" : email.trim();
        phone = ureg.getPhone();
        phone = phone == null ? "" : phone.trim();
        mobile = ureg.getMobile();
        mobile = mobile == null ? "" : mobile.trim();
        if (wedding == null || (wedding.equals("null")) || (wedding.equals("null-null"))) {
            fw2.write(userid1 + "," + username + "," + gender1 + "," + birthday + ",,,," + province + "," + address + ",,,否," + email + "\r\n");
        } else {
            fw2.write(userid1 + "," + username + "," + gender1 + "," + birthday + ",,,," + province + "," + address + ",,,是," + email + "\r\n");
        }
        if (phone != null) {
            String[] p = phone.split("-");
            if (p != null) {
                if (p.length == 3) {
                    pphone1 = p[0];
                    pphone2 = p[1];
                    pphone3 = p[2];
                } else if (p.length == 2) {
                    pphone1 = p[0];
                    pphone2 = p[1];
                } else {
                    pphone2 = p[0];
                }
            }
        }
        pphone1 = pphone1 == null ? "" : pphone1.trim();
        pphone2 = pphone2 == null ? "" : pphone2.trim();
        pphone3 = pphone3 == null ? "" : pphone3.trim();
        mobile = mobile == null ? "" : mobile.trim();
        pphone1 = pphone1.equals("null") ? "" : pphone1;
        pphone2 = pphone2.equals("null") ? "" : pphone2;
        pphone3 = pphone3.equals("null") ? "" : pphone3;
        mobile = mobile.equals("null") ? "" : mobile;
        if (phone != null) {
            fw3.write(userid1 + "," + pphone1 + "," + pphone2 + "," + pphone3 + ",1" + "\r\n");
        }
        if (mobile != null) {
            fw3.write(userid1 + ",," + mobile + ",,4" + "\r\n");
        }
    }
    fw2.close();
    fw3.close();

    //更新数据
    for (int a = 0; a < list.size(); a++) {
        Order forder = (Order) list.get(a);
        orderid = forder.getOrderid();
        try {
            orderMgr.updateFlag(orderid);
        } catch (OrderException e) {
            e.printStackTrace();
        }
    }

%>
<a href="download1.jsp">点击下载定单信息文件</a>&nbsp;&nbsp;
<a href="download2.jsp">点击下载定单详细信息文件</a>&nbsp;&nbsp;
<a href="download3.jsp">点击下载用户信息文件</a>&nbsp;&nbsp;
<a href="download4.jsp">点击下载用户电话信息文件</a>
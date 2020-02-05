<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page import="com.bizwink.cms.business.Order.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    int markID = ParamUtil.getIntParameter(request, "markid", 0);
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);

    //通过session 获得用户id
    Uregister username = (Uregister) session.getAttribute("UserLogin");
    if (username == null) {
        out.write("");
    } else {
        IMarkManager markMgr = markPeer.getInstance();
        String listStyle = "";
        int range = 0;
        int fenyestyle = 0;
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
            String rangestr = properties.getProperty(properties.getName().concat(".RANGE"));
            if (rangestr != null && !rangestr.equals("") && !rangestr.equalsIgnoreCase("null"))
                range = Integer.parseInt(rangestr);
            String fenyestyles = properties.getProperty(properties.getName().concat(".PAGE"));
            if (fenyestyles != null && !fenyestyles.equals("") && !fenyestyles.equalsIgnoreCase("null"))
                fenyestyle = Integer.parseInt(fenyestyles);
        }
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        SiteInfo siteinfo = siteMgr.getSiteInfo(siteid);
        String sitename = "";
        if (siteinfo != null) {
            sitename = siteinfo.getDomainName();
        }
        if (sitename != null) {
            sitename = StringUtil.replace(sitename, ".", "_");
        }
        int userid = username.getId();
        List list = new ArrayList();

        IOrderManager oMgr = orderPeer.getInstance();
        list = oMgr.getUserOrderList(userid, range, startrow);
        int num = oMgr.getUserOrderNum(userid);
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
        String outstr = head;
        if (list.size() > 0) {


            for (int i = 0; i < list.size(); i++) {
                Order order = (Order) list.get(i);
                String line = listStyle;
                String status = "";

                if (order.getNouse() == 0) {
                    status = "客户取消";
                } else {
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
                }
                String ordertype = "";
                if (order.getUserid() <= 0) {
                    ordertype = "快速订购";
                } else {
                    ordertype = "会员订单";
                }
                String cancelorder = "";
                if(order.getNouse() == 1 && order.getStatus() == 1)
                {
                    //只有处理中的订单能取消
                    cancelorder = "<a href=\"/_commons/cancelorder.jsp?orderid="+order.getOrderid()+"\">取消订单</a>";
                }
                else{
                    cancelorder = "订单不能取消";
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
                String detail = "<a href=\"/" + sitename + "/_prog/orderdetailsearch.jsp?orderid=" + order.getOrderid() + "\">查看详情</a>";
                line = StringUtil.replace(line, "<" + "%%orderid%%" + ">", String.valueOf(order.getOrderid()));
                line = StringUtil.replace(line, "<" + "%%ordertype%%" + ">", ordertype);
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
                line = StringUtil.replace(line, "<" + "%%orderdetail%%" + ">", detail);
                line = StringUtil.replace(line, "<" + "%%cancelorder%%" + ">", cancelorder);
                outstr = outstr + line;
            }
            //分页
            String navbar = "";
            if (fenyestyle > 0) {
                int pages = num / range;
                int current_page = startrow / range;
                navbar = StringUtil.generateNavBar(markID, fenyestyle, pages, current_page, current_page, range, "javascript:getordersearchresult");
                // System.out.println("nav = "+navbar);
            }
            tail = StringUtil.replace(tail, "<" + "%%navbar%%" + ">", navbar);
            outstr = outstr + tail;
            out.write(outstr);
        } else {
            //分页
            String navbar = "";
            if (fenyestyle > 0) {
                int pages = num / range;
                int current_page = startrow / range;
                navbar = StringUtil.generateNavBar(markID, fenyestyle, pages, current_page, current_page, range, "javascript:getordersearchresult");
            }
            tail = StringUtil.replace(tail, "<" + "%%navbar%%" + ">", navbar);
            outstr = outstr + tail;
            out.write(outstr);
        }
    }
%>
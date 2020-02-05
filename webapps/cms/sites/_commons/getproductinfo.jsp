<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.business.Product.IProductManager" %>
<%@ page import="com.bizwink.cms.business.Product.productPeer" %>
<%@ page import="com.bizwink.cms.business.Product.Product" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.cms.business.Order.*" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Uregister ug = (Uregister) session.getAttribute("UserLogin");
    List list = new ArrayList();
    list = (List) session.getAttribute("en_list");
    if (list == null) {
        List glist = new ArrayList();
        list = glist;
        session.setAttribute("en_list", glist);
    }


    int markID = ParamUtil.getIntParameter(request, "markid", 0);
    int userid = 0; //获得用户id
    if (ug != null) {
        userid = ug.getId();
    }
    IUregisterManager regMgr = UregisterPeer.getInstance();
    int scores = regMgr.getUserScores(userid);//获得用户积分

    String userscores = (String) session.getAttribute("scores");//获得用户本次使用的积分
    int uscores = 0;
    if (userscores != null && !userscores.equals("") && !userscores.equalsIgnoreCase("null")) {
        uscores = Integer.parseInt(userscores);
    }
    String scoresstr = "";
    if ((scores - uscores) <= 0) {
        scoresstr = "disabled";
    }
    IOrderManager orderMgr = orderPeer.getInstance();
    //获得用户本次使用的购物券号
    String usecards = (String) session.getAttribute("card");
    String cardnum = "";
    if (usecards != null && !usecards.equals("") && !usecards.equalsIgnoreCase("null")) {
        cardnum = usecards;
    }
    int cardmoney = 0;
    Card cardinfo = null;
    if (usecards != null && !usecards.equals("") && !usecards.equalsIgnoreCase("null")) {
        cardinfo = orderMgr.getACardInfo(usecards); //获得购物券信息
    }
    Timestamp dates = new Timestamp(System.currentTimeMillis());
    String nowdate = "";
    int year = dates.getYear() + 1900;
    int month = dates.getMonth() + 1;
    int day = dates.getDay() + 19;
    nowdate = String.valueOf(year);
    if (month < 10) {
        nowdate += "-0" + String.valueOf(month);
    } else {
        nowdate += "-" + String.valueOf(month);
    }
    if (day < 10) {
        nowdate += "-0" + String.valueOf(day);
    } else {
        nowdate += "-" + String.valueOf(day);
    }
    int cardstatus = 0;//购物券的使用情况 0--用户没有使用购物券  1-购物券已经被使用过2-购物券可以使用3-输入信息错误4过期
    if (cardinfo != null) {
        if (cardinfo.getIscheck() == 1) {
            cardstatus = 1;
        } else {
            if (cardinfo.getBegintime().compareTo(nowdate) <= 0 && cardinfo.getEndtime().compareTo(nowdate) >= 0) {
                cardstatus = 2;
                cardmoney = cardinfo.getDenomination();
            } else {
                cardstatus = 4;
            }

        }
    }
    if (cardinfo == null && usecards != null && !usecards.equalsIgnoreCase("null")) {
        cardstatus = 3;
    }

    String sendway = (String) session.getAttribute("sendway");
    int type = 0;
    if (sendway != null) {
        type = Integer.parseInt(sendway);
    }

    /*Fee fee = new Fee();
    fee = orderMgr.getAFeeInfo(type);
    float sendfee = 0;
    if(fee != null){
        sendfee = fee.getFee();
    }*/
    //获得标记信息
    IMarkManager markMgr = markPeer.getInstance();
    String listStyle = "";
    int siteid = 0;
    String submit = "";//确认按钮
    String submitimage = "";//确认按钮图片
    if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");

        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        listStyle = properties.getProperty(properties.getName().concat(".PRODUCTINFO"));
        String siteids = properties.getProperty(properties.getName().concat(".SITEID"));
        if (siteids != null && !siteids.equalsIgnoreCase("null") && !siteids.equals("")) {
            siteid = Integer.parseInt(siteids);
        }
        submit = properties.getProperty(properties.getName().concat(".ORDERSUBMIT"));
        submitimage = properties.getProperty(properties.getName().concat(".ORDERIMAGE"));
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
    //获得积分兑换规则

    int rules = regMgr.getScoresForMoney(sitename);     //get scores rule 用户在工具箱中定义
    int scoresvalue = 0;
    if (rules != 0)
        scoresvalue = uscores / rules;
    else
        scoresvalue = uscores / 10;//积分抵扣值   默认为10个积分=1元 RMB
    double result = 0;
    String head = "";
    String tail = "";
    if (listStyle != null) {
        //处理提交订单按钮
        String submits = "";
        if (submit.equals("submit")) {
            submits = "                    <input type=\"button\" id=\"newtj\" name=\"button1\" value=\"确认\" onclick=\"javascript:getval('" + sitename + "');\">&nbsp;\n";
        } else if (submit.equals("images")) {
            submits = "<a href=\"#\" onclick=\"javascript:getval('" + sitename + "');\"><img id=\"newtj\" src=\"/_sys_images/buttons/" + submitimage + "\" border=0></a>";
        } else {
            submits = "<a id=\"newtj\" href=\"#\" onclick=\"javascript:getval('" + sitename + "');\">确认</a>";
        }
        listStyle = StringUtil.replace(listStyle, "<" + "%%submitorder%%" + ">", submits);
        //处理积分消费按钮
        String usescorestr = "您的帐户可用积分：" + (scores - uscores) + "&nbsp;<input type=\"text\" name=\"scores\" size=\"6\" value=\"" + uscores + "\"><input type=\"button\" name=\"button\" value=\"使用积分\" onclick=\"javascript:usescore(document.confirmform.scores.value," + markID + "," + siteid + ");\" " + scoresstr + ">&nbsp;&nbsp;<font color=\"red\">-" + scoresvalue + "元</font>";
        listStyle = StringUtil.replace(listStyle, "<" + "%%usescores%%" + ">", usescorestr);
        //使用购物券消费按钮
        String usecard = "";
        if (cardstatus == 2) {
            usecard = "请输入您的购物券号码：<input type=\"text\" name=\"usecards\" size=\"10\" value=\"" + cardnum + "\"  onBlur=\"javascript:usecard(this.value," + markID + ");\">&nbsp;&nbsp;<font color=\"red\">-" + cardmoney + "元</font>";
        } else if (cardstatus == 1) {
            usecard = "请输入您的购物券号码：<input type=\"text\" name=\"usecards\" size=\"10\" value=\"" + cardnum + "\"  onBlur=\"javascript:usecard(this.value," + markID + ");\">&nbsp;&nbsp;<font color=\"red\">-0元   购物券已经使用过</font>";
        } else if (cardstatus == 3) {
            usecard = "请输入您的购物券号码：<input type=\"text\" name=\"usecards\" size=\"10\" value=\"" + cardnum + "\"  onBlur=\"javascript:usecard(this.value," + markID + ");\">&nbsp;&nbsp;<font color=\"red\">-0元   购物券信息错误</font>";
        } else if (cardstatus == 4) {
            usecard = "请输入您的购物券号码：<input type=\"text\" name=\"usecards\" size=\"10\" value=\"" + cardnum + "\"  onBlur=\"javascript:usecard(this.value," + markID + ");\">&nbsp;&nbsp;<font color=\"red\">-0元   购物券已经使用</font>";
        } else {
            usecard = "请输入您的购物券号码：<input type=\"text\" name=\"usecards\" size=\"10\" value=\"" + cardnum + "\"  onBlur=\"javascript:usecard(this.value," + markID + ");\">&nbsp;&nbsp;<font color=\"red\">-0元</font>";
        }
        listStyle = StringUtil.replace(listStyle, "<" + "%%usecard%%" + ">", usecard);
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
    String buystr = "";
    String pid = "";
    String buynumstr = "";
    int productid = 0;
    int buynum = 0;

    String productname = "";
    float saleprice = 0;
    float totalprice = 0;
    float allprice = 0;
    float marketprice = 0;


    String outstr = head;

    IProductManager productMgr = productPeer.getInstance();
    Product product = new Product();
    IColumnManager columnManager = ColumnPeer.getInstance();
    String productids = "";
    int totalscore_in_shoppingcar = 0;
    int totalproductnum_in_shoppingcar = 0;
    if (list.size() > 0) {
        for (int i = 0; i < list.size(); i++) {
            String line = listStyle;
            buystr = (String) list.get(i);
            if ((buystr != null) && (!buystr.equals("")) && (!buystr.equals("null"))) {
                buystr = buystr.trim();
                pid = buystr.substring(0, buystr.indexOf("_"));
                buynumstr = buystr.substring(buystr.indexOf("_") + 1, buystr.lastIndexOf("_"));
                productid = Integer.parseInt(pid);
                buynum = Integer.parseInt(buynumstr);
                product = productMgr.getAProduct(productid);
                productname = product.getMainTitle();
                saleprice = product.getSalePrice();
                marketprice = product.getMarketPrice();
                totalprice = buynum * saleprice;
                allprice = allprice + totalprice;
                totalscore_in_shoppingcar = totalscore_in_shoppingcar + product.getScores()*buynum;
                totalproductnum_in_shoppingcar = totalproductnum_in_shoppingcar + buynum;
                Column column = columnManager.getColumn(product.getColumnID());
                String extfilename = null;
                if (column != null) {
                    extfilename = column.getExtname();
                }
                String createdate = product.getCreateDate().toString().substring(0, 10).replaceAll("-", "");
                if (extfilename == null) extfilename = "";
                line = StringUtil.replace(line, "<" + "%%productno%%" + ">", pid);
                line = StringUtil.replace(line, "<" + "%%productname%%" + ">", StringUtil.gb2iso4View(productname));
                line = StringUtil.replace(line, "<" + "%%productnameurl%%" + ">", "<a href=\"" + product.getDirName() + createdate + "/" + pid + "." + extfilename + "\">" + StringUtil.gb2iso4View(productname) + "</a>");
                line = StringUtil.replace(line, "<" + "%%saleprice%%" + ">", String.valueOf(saleprice));
                line = StringUtil.replace(line, "<" + "%%marketprice%%" + ">", String.valueOf(marketprice));
                line = StringUtil.replace(line, "<" + "%%score%%" + ">", String.valueOf(product.getScores()));
                line = StringUtil.replace(line, "<" + "%%producttotal%%" + ">", String.valueOf(totalprice));
                line = StringUtil.replace(line, "<" + "%%productnum%%" + ">", String.valueOf(buynum));
                outstr = outstr + line;
                if(i == 0){
                    productids += pid;
                }else if(i == list.size()-1){
                    productids += pid;
                }else{
                    productids += pid + ",";
                }
            }
        }


        //获得运费
        String paystr = (String) session.getAttribute("payway");
        int payway = -1;
        if (paystr != null) {
            payway = Integer.parseInt(paystr);
        }
        //送货信息
        String cidstr = (String) session.getAttribute("info");
        int cid = -1;
        if (cidstr != null) {
            cid = Integer.parseInt(cidstr);
        }
        AddressInfo addressinfo = new AddressInfo();
        addressinfo = orderMgr.getAAddresInfo(cid);
        String cityname = "";
        if(addressinfo != null){
            cityname = addressinfo.getCity() == null ? "" : StringUtil.gb2iso4View(addressinfo.getCity());
        }
        int cityid = orderMgr.getCityId(cityname);
        float sendfee = 0;
        sendfee = orderMgr.getShoppingFee(type,payway,cityid,productids,nowdate,allprice  - scoresvalue - cardmoney,siteid);
        result = allprice + sendfee - scoresvalue - cardmoney;

        //session.setAttribute("en_allprice", String.valueOf(result));
        session.setAttribute("en_allprice", String.valueOf(result));
        tail = StringUtil.replace(tail, "<" + "%%totalprice%%" + ">", String.valueOf(result));
        tail = StringUtil.replace(tail, "<" + "%%totalproductnum%%" + ">", String.valueOf(totalproductnum_in_shoppingcar));
        tail = StringUtil.replace(tail, "<" + "%%totalscore%%" + ">", String.valueOf(totalscore_in_shoppingcar));
        outstr = outstr + tail;
        outstr = StringUtil.replace(outstr, "<" + "%%totalprice%%" + ">", String.valueOf(result));
        outstr = StringUtil.replace(outstr, "<" + "%%fee%%" + ">", String.valueOf(sendfee));
        outstr = StringUtil.replace(outstr, "<" + "%%scoresfee%%" + ">", String.valueOf(scoresvalue));
        outstr = StringUtil.replace(outstr, "<" + "%%cardfee%%" + ">", String.valueOf(cardmoney));
    } else {
        outstr = "您还没有购买任何商品！";
    }
    out.write(outstr);
%>
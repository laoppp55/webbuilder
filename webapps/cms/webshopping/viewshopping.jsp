<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.extendAttr.ExtendAttrPeer" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.bizwink.cms.extendAttr.IExtendAttrManager" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.bizwink.cms.business.Product.IProductManager" %>
<%@ page import="com.bizwink.cms.business.Product.productPeer" %>
<%@ page import="com.bizwink.cms.business.Product.Product" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    List list = new ArrayList();
    list = (List) session.getAttribute("en_list");
    if (list == null) {
        List glist = new ArrayList();
        list = glist;
        session.setAttribute("en_list", glist);
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

    IExtendAttrManager extendAttrMgr = ExtendAttrPeer.getInstance();
    NumberFormat usFormat = NumberFormat.getIntegerInstance(Locale.US);


    String outstr = "";
    outstr = outstr + "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#CCCCCC\" style=\"font-size:12px\">\n" +
            "  <tr>\n" +
            "    <td height=\"30\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\" >商品名称</td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\">市场价</td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\">会员价</td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\">数量</td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\">总价</td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\">修改</td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\">删除 </td>\n" +
            "  </tr>";

    IProductManager productMgr = productPeer.getInstance();
    Product product = new Product();
    if(list.size() >0){
    for (int i = 0; i < list.size(); i++) {
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
            outstr = outstr +
            "  <tr>\n" +
            "    <td height=\"30\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\" >"+ StringUtil.gb2iso4View(productname)+"</td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\">"+marketprice+"</td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\">"+saleprice+"</td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\"><input name=quantity"+productid+" type=\"text\" value=\""+buynum+"\" size=\"3\"/></td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\">"+totalprice+"</td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\"><input type=\"button\" name=\"Submit\" value=\"修改\" onclick='javascript:updateCart("+productid+",document.all(\"quantity"+productid+"\").value)'/></td>\n" +
            "    <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\"><input type=\"button\" name=\"Submit22\" value=\"删除\" onClick=\"javascript:delete_shoppingcart("+productid+");\"/> </td>" +
            "  </tr>";
        }
    }

    outstr = outstr +
            "  <tr>\n" +
            "    <td height=\"30\" align=\"right\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\" colspan=\"4\">购物车总价</td>\n" +
            "    <td align=\"left\" bgcolor=\"#FFFFFF\" style=\"font-weight:600\" colspan=\"3\">"+allprice+"</td>\n" +
            "  </tr><tr><td bgcolor=\"#FFFFFF\" align=right colspan=7><input type=button name=button value=结算 onClick='javascrip:gobuy();'></td></tr></table>";
    }else{
        outstr = "false";
    }
    out.write(outstr);
%>
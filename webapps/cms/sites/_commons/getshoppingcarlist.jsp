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
<%@ page contentType="text/html;charset=GBK" %>
<%
    List list = new ArrayList();
    list = (List) session.getAttribute("en_list");
    if (list == null) {
        List glist = new ArrayList();
        list = glist;
        session.setAttribute("en_list", glist);
    }
    String login_to_url=request.getHeader("REFERER");
    int markID = ParamUtil.getIntParameter(request, "markid", 0);
    //获得标记信息
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
        String siteids  = properties.getProperty(properties.getName().concat(".SITEID"));
        if(siteids != null && !siteids.equalsIgnoreCase("null") && !siteids.equals(""))
        {
            siteid = Integer.parseInt(siteids);
        }
    }
    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
    SiteInfo siteinfo = siteMgr.getSiteInfo(siteid);
     String sitename = "";
    if(siteinfo != null)
    {
        sitename = siteinfo.getDomainName();
    }
    if(sitename != null){
        sitename = StringUtil.replace(sitename,".","_");
    }
    String head = "";
    String tail = "";
    if (listStyle != null) {
        listStyle = StringUtil.replace(listStyle, "<" + "%%orderurl%%" + ">", "/" + sitename + "/_prog/ordergenerate.jsp?loginurl="+login_to_url);
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
                totalscore_in_shoppingcar = totalscore_in_shoppingcar + product.getScores()*buynum;
                totalproductnum_in_shoppingcar = totalproductnum_in_shoppingcar + buynum;
                totalprice = buynum * saleprice;
                allprice = allprice + totalprice;
                Column column = columnManager.getColumn(product.getColumnID());
                String extfilename = null;
                if (column != null) {
                    extfilename = column.getExtname();
                }
                String createdate = product.getCreateDate().toString().substring(0,10).replaceAll("-","");
                if (extfilename == null) extfilename = "";
                line = StringUtil.replace(line, "<" + "%%productno%%" + ">", pid);
                line = StringUtil.replace(line, "<" + "%%productname%%" + ">", StringUtil.gb2iso4View(productname));
                line = StringUtil.replace(line, "<" + "%%productnameurl%%" + ">", "<a href=\"" + product.getDirName() +createdate+"/"+ pid + "." + extfilename + "\">" + StringUtil.gb2iso4View(productname) + "</a>");
                line = StringUtil.replace(line, "<" + "%%saleprice%%" + ">", String.valueOf(saleprice));
                line = StringUtil.replace(line, "<" + "%%marketprice%%" + ">", String.valueOf(marketprice));
                line = StringUtil.replace(line, "<" + "%%score%%" + ">", String.valueOf(product.getScores()));
                line = StringUtil.replace(line, "<" + "%%producttotal%%" + ">", String.valueOf(totalprice));
                line = StringUtil.replace(line, "<" + "%%productnum%%" + ">","<input name=quantity" + productid + " type=\"text\" value=\"" + buynum + "\" size=\"2\"/><a href=\"#\" onclick='javascript:updateCart(" + productid +",document.all(\"quantity"+productid+"\").value,\""+sitename+"\")' />修改</a>");
                line = StringUtil.replace(line, "<" + "%%deleteproducturl%%" + ">", "/_commons/deleteShoppingcart.jsp?pid="+pid+"&sitename="+sitename);
                outstr = outstr + line;
            }
        }
        tail = StringUtil.replace(tail, "<" + "%%totalproductnum%%" + ">", String.valueOf(totalproductnum_in_shoppingcar));
        tail = StringUtil.replace(tail, "<" + "%%totalscore%%" + ">", String.valueOf(totalscore_in_shoppingcar));        
        tail  = StringUtil.replace(tail, "<" + "%%totalprice%%" + ">", String.valueOf(allprice));
        outstr = outstr + tail;
    } else {
        outstr = "您还没有购买任何商品！";
    }
    out.write(outstr);
%>
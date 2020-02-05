<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    List list = new ArrayList();
    list = (List) session.getAttribute("en_list");
    int allnum = 0;
    if(list != null){
    for (int i = 0; i < list.size(); i++) {
        String buystr = (String) list.get(i);
        if ((buystr != null) && (!buystr.equals("")) && (!buystr.equals("null"))) {
            buystr = buystr.trim();

            String buynumstr = buystr.substring(buystr.indexOf("_") + 1, buystr.lastIndexOf("_"));
            int buynum = Integer.parseInt(buynumstr);
            allnum += buynum;
        }
    }
    }
    String outstr = "<a href=\"/www_sinopecbtc_com/_prog/shoppingcar.jsp\">购物车（"+allnum+"）件商品</a>";
    out.write(outstr);
%>
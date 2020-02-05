<%@ page import="java.io.*,
                 java.util.*" contentType="text/html;charset=gbk"
        %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Product.IProductManager" %>
<%@ page import="com.bizwink.cms.business.Product.productPeer" %>
<%@ page import="com.bizwink.cms.business.Product.Product" %>

<%
    List oldlist = (List) session.getAttribute("en_list");
    if (oldlist == null) {
        List list = new ArrayList();
        session.setAttribute("en_list", list);
        oldlist = (List) session.getAttribute("en_list");
    }
    int pid = ParamUtil.getIntParameter(request, "pid", 0);         //商品id
    int num = ParamUtil.getIntParameter(request, "num", 0);         //商品数量
    String type = ParamUtil.getParameter(request, "sitename");      //站点id

    IProductManager productMgr = productPeer.getInstance();
    int status = productMgr.checkStatus(pid);

    if (status == 1) {
        if ((pid != 0) && (num != 0)) {
            String insertstr = String.valueOf(pid) + "_" + String.valueOf(num) + "_" + type;
            if (oldlist.size() > 0) {
                String str = "";
                String proidstr = "";
                String pronumstr = "";
                String typestr = "";
                int proid = 0;
                int pronum = 0;
                int sort = 0;
                boolean existflag = false;

                for (int i = 0; i < oldlist.size(); i++) {
                    str = (String) oldlist.get(i);
                    proidstr = str.substring(0, str.indexOf("_"));
                    proid = Integer.parseInt(proidstr);

                    if (pid == proid) {
                        existflag = true;
                        pronumstr = str.substring(str.indexOf("_") + 1, str.lastIndexOf("_"));
                        pronum = Integer.parseInt(pronumstr);
                        typestr = str.substring(str.lastIndexOf("_") + 1, str.length());
                        sort = i;
                        break;
                    }
                }

                if (existflag) {
                    pronum = pronum + num;
                    insertstr = String.valueOf(proid) + "_" + String.valueOf(pronum) + "_" + typestr;
                    oldlist.set(sort, insertstr);
                } else {
                    oldlist.add(insertstr);
                }
            } else {
                oldlist.add(insertstr);
            }

            float allprice = 0;
            for (int i = 0; i < oldlist.size(); i++) {
                String buystr = "";
                String buynumstr = "";
                int buynum = 0;
                float price = 0;
                float totalprice = 0;
                String proidstr = "";
                int proid = 0;

                buystr = (String) oldlist.get(i);

                if ((buystr != null) && (!buystr.equals("")) && (!buystr.equals("null"))) {
                    buystr = buystr.trim();
                    proidstr = buystr.substring(0, buystr.indexOf("_"));
                    buynumstr = buystr.substring(buystr.indexOf("_") + 1, buystr.lastIndexOf("_"));
                    proid = Integer.parseInt(proidstr);
                    buynum = Integer.parseInt(buynumstr);

                    Product product = productMgr.getAProduct(proid);
                    price = product.getSalePrice();
                    totalprice = buynum * price;
                    allprice = allprice + totalprice;
                }
            }
            session.setAttribute("en_totalprice", String.valueOf(allprice));

        }
        out.print("购买成功！请查看购物车！");
    } else {
        out.print("品已经下架，不能添加进购物车！");
    }
%>
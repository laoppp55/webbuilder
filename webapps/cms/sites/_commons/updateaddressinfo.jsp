<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.AddressInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    String name = ParamUtil.getParameter(request, "connname");
    String address = ParamUtil.getParameter(request, "address");
    String prov = ParamUtil.getParameter(request, "prov");
    String city = ParamUtil.getParameter(request, "city");
    String zone = ParamUtil.getParameter(request, "zone");
    String mobile = ParamUtil.getParameter(request, "mobile");
    String phone = ParamUtil.getParameter(request, "phone");
    String zip = ParamUtil.getParameter(request, "zip");
    int markID = ParamUtil.getIntParameter(request, "markid", 0);
    //int userid = 0;
    Uregister username = (Uregister) session.getAttribute("UserLogin");
    IOrderManager oMgr = orderPeer.getInstance();
    AddressInfo addressinfo = new AddressInfo();
    int errcode = 0;
    addressinfo.setAddress(address);
    addressinfo.setCity(city);
    addressinfo.setId(id);
    addressinfo.setMobile(mobile);
    addressinfo.setName(name);
    addressinfo.setPhone(phone);
    addressinfo.setProvinces(prov);
    addressinfo.setUserid(username.getId());
    addressinfo.setZip(zip);
    addressinfo.setZone(zone);;
    if (id == 0)
        errcode = oMgr.createAddressInfo(addressinfo);
    else
        errcode = oMgr.updateAddressinfo(addressinfo);
    

    if (errcode == 0) {
        List list = new ArrayList();
        list = oMgr.getAllAddressInfo(username.getId());
        if (list.size() > 0) {
            //获得标记信息
            IMarkManager markMgr = markPeer.getInstance();
            String listStyle = "";
            String submit = "";//确认按钮
            String submitimage = "";//确认按钮图片
            int siteid = 0;
            if (markID > 0) {
                String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
                str = StringUtil.replace(str, "[", "<");
                str = StringUtil.replace(str, "]", ">");
                str = StringUtil.replace(str, "{^", "[");
                str = StringUtil.replace(str, "^}", "]");

                XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
                listStyle = properties.getProperty(properties.getName().concat(".ADDRESSINFO"));//样式

                String siteids = properties.getProperty(properties.getName().concat(".SITEID"));
                if (siteids != null && !siteids.equalsIgnoreCase("null") && !siteids.equals("")) {
                    siteid = Integer.parseInt(siteids);
                }
                submit = properties.getProperty(properties.getName().concat(".ADDRESSSUBMIT"));
                submitimage = properties.getProperty(properties.getName().concat(".ADDRESSIMAGE"));
            }
            if (listStyle != null) {
                listStyle = listStyle.substring(0, listStyle.length() - 1);
            }

            AddressInfo cons1 = new AddressInfo();
            int id1 = 0;

            cons1 = (AddressInfo) list.get(0);
            id1 = cons1.getId();
            String provname = cons1.getProvinces() == null ? "" : StringUtil.gb2iso4View(cons1.getProvinces());
            String cityname = cons1.getCity() == null ? "" : StringUtil.gb2iso4View(cons1.getCity());
            String phone1 = "";
            if (cons1.getMobile() != null) {
                phone1 = cons1.getMobile();
            } else if (cons1.getPhone() != null) {
                phone1 = cons1.getPhone();
            }
            String zones = cons1.getZone() == null ? "" : StringUtil.gb2iso4View(cons1.getZone());

            session.setAttribute("info", String.valueOf(id1));//设置送货地址session
            listStyle = StringUtil.replace(listStyle, "<" + "%%connname%%" + ">", StringUtil.gb2iso4View(cons1.getName()));
            listStyle = StringUtil.replace(listStyle, "<" + "%%province%%" + ">", provname);
            listStyle = StringUtil.replace(listStyle, "<" + "%%city%%" + ">", cityname);
            listStyle = StringUtil.replace(listStyle, "<" + "%%zone%%" + ">", zones);
            listStyle = StringUtil.replace(listStyle, "<" + "%%address%%" + ">", StringUtil.gb2iso4View(cons1.getAddress()));
            listStyle = StringUtil.replace(listStyle, "<" + "%%zip%%" + ">", StringUtil.gb2iso4View(cons1.getZip()));
            listStyle = StringUtil.replace(listStyle, "<" + "%%phone%%" + ">", phone1);

            String submits = "";
            if (submit.equals("submit")) {
                submits = "                    <input type='button' name='button1' value='修改' onclick='javascript:editaddress(" + id1 + ");'>&nbsp;\n";
            } else if (submit.equals("images")) {
                submits = "<a href=\"#\" onclick='editaddress(" + id1 + ");'><img src=\"/images/" + submitimage + "\" border=0></a>";
            } else {
                submits = "<a href=\"#\" onclick='editaddress(" + id1 + ");'>修改</a>";
            }
            listStyle  = StringUtil.replace(listStyle, "<" + "%%editsubmit%%" + ">", submits);
            out.write(listStyle);
        }
    } else {
        out.print("");
    }
%>
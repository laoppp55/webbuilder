<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.AddressInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    int id = ParamUtil.getIntParameter(request,"id",0);
    String name = ParamUtil.getParameter(request, "connname");
    String address = ParamUtil.getParameter(request, "address");
    String prov = ParamUtil.getParameter(request, "prov");
    String city = ParamUtil.getParameter(request, "city");
    String zone = ParamUtil.getParameter(request, "zone");
    String mobile = ParamUtil.getParameter(request, "mobile");
    String phone = ParamUtil.getParameter(request, "phone");
    String zip = ParamUtil.getParameter(request, "zip");

    //int userid = 0;
    Uregister username=(Uregister)session.getAttribute("UserLogin");
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
    addressinfo.setZone(zone);
    if(id == 0)
        errcode = oMgr.createAddressInfo(addressinfo);
    else
         errcode = oMgr.updateAddressinfo(addressinfo);
    int checkid = 0;
    String checkids = (String)session.getAttribute("info");
        if(checkids != null){
            checkid = Integer.parseInt(checkids);
        }
    String outstr =
            "            <table width='90%' border='0' cellpadding='0' cellspacing='0' style=\"font-size:12px\">\n" +
            "                <!--DWLayoutTable-->\n" +
            "                <tr>\n" +
            "                  <td height='41' valign='middle' style='background:#F1F1F1; color:#3F3F3F; padding-left:20px;'><!--DWLayoutEmptyCell-->&nbsp;</td>\n" +
            "                  <td height='41' valign='middle' style='background:#F1F1F1; color:#3F3F3F; padding-left:20px;'>姓名</td>\n" +
            "                  <td height='41' valign='middle' style='background:#F1F1F1; color:#3F3F3F; padding-left:20px;'>省份</td>\n" +
            "                  <td width='56' align='center' valign='middle' style='background:#F1F1F1; color:#3F3F3F; '>区域</td>\n" +
            "                  <td width='170' align='center' valign='middle' style='background:#F1F1F1; color:#3F3F3F;'>地址</td>\n" +
            "                  <td width='63' align='center'  valign='middle' style='background:#F1F1F1; color:#3F3F3F;;'>邮编</td>\n" +
            "                  <td width='66' align='center'  valign='middle' style='background:#F1F1F1; color:#3F3F3F;;'>电话</td>\n" +
            "                  <td width='109' align='center'  valign='middle' style='background:#F1F1F1; color:#3F3F3F;;'><!--DWLayoutEmptyCell-->&nbsp;</td>\n" +
            "                </tr>";
    if (errcode == 0) {
        List list = new ArrayList();
        list = oMgr.getAllAddressInfo(username.getId());
        AddressInfo cons1 = new AddressInfo();
        int id1 = 0;
        for (int i = 0; i < list.size() && i < 3; i++) {
            cons1 = (AddressInfo) list.get(i);
            id1 = cons1.getId();
            String provname = cons1.getProvinces()==null?"":StringUtil.gb2iso4View(cons1.getProvinces());
            String cityname = cons1.getCity()==null?"":StringUtil.gb2iso4View(cons1.getCity());
            String phone1 = "";
            if (cons1.getMobile() != null) {
                phone1 = cons1.getMobile();
            } else if (cons1.getPhone() != null)
            {
                phone1 = cons1.getPhone();
            }
            String uname = "";
            if(cons1.getName() != null){
                uname = StringUtil.gb2iso4View(cons1.getName());
            }
            String checkstr = ">\n";
            if(id1==checkid){
                checkstr = " checked>\n";
            }
            if(list.size()==1){
                checkstr = " checked>\n";
            }
            outstr = outstr + "<tr>\n" +
                    "                  <td height='35' valign='middle' style='border-bottom:#BCBCBC 1px dotted;color:#61749F; padding-left:20px;'>\n" +
                    "                    <input type='radio' name='info' value='"+id1+"' onclick='javascript:selectadd("+id1+");'" + checkstr+
                    "                  </td>\n" +
                    "                  <td height='35' align='center' valign='middle' style='border-bottom:#BCBCBC 1px dotted;color:#61749F; '>"+uname+"</td>\n" +
                    "                  <td height='35' valign='middle' style='border-bottom:#BCBCBC 1px dotted;color:#61749F; padding-left:20px;'>"+provname+"</td>\n" +
                    "                  <td align='center' valign='middle' class='dot' style='color:#FD7E01;border-bottom:#BCBCBC 1px dotted;color:#61749F;'>"+cityname+"</td>\n" +
                    "                  <td align='center'  valign='middle' class='dot' style='color:#333333;border-bottom:#BCBCBC 1px dotted;color:#61749F;'>"+cons1.getAddress()+"</td>\n" +
                    "                  <td align='center' style='border-bottom:#BCBCBC 1px dotted; '>"+cons1.getZip()+"</td>\n" +
                    "                  <td align='center' style='color:#333333;border-bottom:#BCBCBC 1px dotted;color:#61749F;'>"+phone1+"</td>\n" +
                    "                  <td align='center' style='color:#333333;border-bottom:#BCBCBC 1px dotted;'><span style='color:#333333;border-bottom:#AFAFAF 1px solid;'>\n" +
                    "                    <input type='button' name='button1' value='修改' onclick='javascript:edit("+id1+");'>&nbsp;\n" +
                    "                  </span><span style='color:#333333;border-bottom:#AFAFAF 1px solid;'>\n" +
                    "                  <input type='button' name='button' value='删除' onclick='javascript:del("+id1+");'>\n" +
                    "                  </span></td>\n" +
                    "                </tr>";
            session.setAttribute("info",String.valueOf(id1));
        }
        outstr = outstr + "<tr>\n" +
                "                  <td height='37' colspan='8' align='center' valign='middle' style='border-bottom:#AFAFAF 1px solid;color:#61749F; padding-left:20px;'><span style='color:#333333;border-bottom:#AFAFAF 1px solid;'>\n" +
                "                    <input type='button' name='button3' value='添加新的收货地址' onclick='javascript:add1();' />\n" +
                "                  </span></td>\n" +
                "                  </tr>\n" +
                "                <!--DWLayoutTable-->\n" +
                "              </table>";
    }
    if(errcode == 0){
        out.print(outstr);
    }
    else{
        out.print("");
    }
%>
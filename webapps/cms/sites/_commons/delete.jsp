<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.business.Order.AddressInfo" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    int id = ParamUtil.getIntParameter(request, "id", 0);
    if (startflag == 1) {
        IOrderManager oMgr = orderPeer.getInstance();
        int errcode = oMgr.deleteAddressInfo(id);
        if (errcode == 0) {
            //删除成功
            //通过session获得用户id
            Uregister username = (Uregister) session.getAttribute("UserLogin");
            int userid = username.getId();
            int checkid = 0;
            String checkids = (String) session.getAttribute("info");
            if (checkids != null) {
                checkid = Integer.parseInt(checkids);
            }
            List list = new ArrayList();
            list = oMgr.getAllAddressInfo(userid);
            if (list.size() > 0) {
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

                AddressInfo cons1 = new AddressInfo();
                int id1 = 0;
                for (int i = 0; i < list.size() && i < 3; i++) {
                    cons1 = (AddressInfo) list.get(i);
                    id1 = cons1.getId();
                    String provname = cons1.getProvinces() == null ? "" : StringUtil.gb2iso4View(cons1.getProvinces());
                    String cityname = cons1.getCity() == null ? "" : StringUtil.gb2iso4View(cons1.getCity());
                    String phone1 = "";
                    if (cons1.getMobile() != null) {
                        phone1 = cons1.getMobile();
                    } else if (cons1.getPhone() != null) {
                        phone1 = cons1.getPhone();
                    }
                    String uname = "";
                    if (cons1.getName() != null) {
                        uname = StringUtil.gb2iso4View(cons1.getName());
                    }
                    String checkstr = ">\n";
                    if (id1 == checkid) {
                        checkstr = " checked>\n";
                    }
                    outstr = outstr + "<tr>\n" +
                            "                  <td height='35' valign='middle' style='border-bottom:#BCBCBC 1px dotted;color:#61749F; padding-left:20px;'>\n" +
                            "                    <input type='radio' name='info' value='" + id1 + "' onclick='javascript:selectadd(" + id1 + ");'" + checkstr +
                            "                  </td>\n" +
                            "                  <td height='35' align='center' valign='middle' style='border-bottom:#BCBCBC 1px dotted;color:#61749F; '>" + uname + "</td>\n" +
                            "                  <td height='35' valign='middle' style='border-bottom:#BCBCBC 1px dotted;color:#61749F; padding-left:20px;'>" + provname + "</td>\n" +
                            "                  <td align='center' valign='middle' class='dot' style='color:#FD7E01;border-bottom:#BCBCBC 1px dotted;color:#61749F;'>" + cityname + "</td>\n" +
                            "                  <td align='center'  valign='middle' class='dot' style='color:#333333;border-bottom:#BCBCBC 1px dotted;color:#61749F;'>" + cons1.getAddress() + "</td>\n" +
                            "                  <td align='center' style='border-bottom:#BCBCBC 1px dotted; '>" + cons1.getZip() + "</td>\n" +
                            "                  <td align='center' style='color:#333333;border-bottom:#BCBCBC 1px dotted;color:#61749F;'>" + phone1 + "</td>\n" +
                            "                  <td align='center' style='color:#333333;border-bottom:#BCBCBC 1px dotted;'><span style='color:#333333;border-bottom:#AFAFAF 1px solid;'>\n" +
                            "                    <input type='button' name='button1' value='修改' onclick='javascript:edit(" + id1 + ");'>&nbsp;\n" +
                            "                  </span><span style='color:#333333;border-bottom:#AFAFAF 1px solid;'>\n" +
                            "                  <input type='button' name='button' value='删除' onclick='javascript:del(" + id1 + ");'>\n" +
                            "                  </span></td>\n" +
                            "                </tr>";
                    session.setAttribute("info", String.valueOf(id1));
                }
                outstr = outstr + "<tr>\n" +
                        "                  <td height='37' colspan='8' align='center' valign='middle' style='border-bottom:#AFAFAF 1px solid;color:#61749F; padding-left:20px;'><span style='color:#333333;border-bottom:#AFAFAF 1px solid;'>\n" +
                        "                    <input type='button' name='button3' value='添加新的收货地址' onclick='javascript:add1();' />\n" +
                        "                  </span></td>\n" +
                        "                  </tr>\n" +
                        "                <!--DWLayoutTable-->\n" +
                        "              </table>";
                out.write(outstr);
            } else {
                out.write("true");
            }
        } else {
            out.write("false");
        }
    } else {
        out.write("false");
    }
%>
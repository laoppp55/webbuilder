<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.Fee" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.business.Order.SendWay" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    int payway = ParamUtil.getIntParameter(request,"payway",-1);
    session.setAttribute("payway",String.valueOf(payway));

    IOrderManager oMgr = orderPeer.getInstance();
    SendWay paywayinfo = new SendWay();
    paywayinfo = oMgr.getASendWayInfo(payway);

    String trstr = paywayinfo.getCname()==null?"": StringUtil.gb2iso4View(paywayinfo.getCname());

    String outstr = "";
    outstr =  "<table width='100%' border='0' cellpadding='0' cellspacing='0' style=\"font-size:12px\">\n" +
            "          <!--DWLayoutTable-->\n" +
            "          <tr>\n" +
            "                <td valign='top' align='center'>支付方式:" +trstr+"</td>\n" +
            "              </tr>\n"+
            "          <tr>\n" +
            "            <td height='52' align='center' valign='top'><input type='button' value='修改' onclick='javascript:editPayway("+payway+");'/></td>\n" +
            "              </tr>\n" +
            "          </table>";
    out.print(outstr);
%>
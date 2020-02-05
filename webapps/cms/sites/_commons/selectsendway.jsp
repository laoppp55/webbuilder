
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.Fee" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    int sendway = ParamUtil.getIntParameter(request,"sendway",-1);
    session.setAttribute("sendway",String.valueOf(sendway));

    IOrderManager oMgr = orderPeer.getInstance();
    Fee fee = new Fee();
    fee= oMgr.getAFeeInfo(sendway);

    String trstr = fee.getCname()==null?"": StringUtil.gb2iso4View(fee.getCname());

    String outstr = "";
    outstr =  "<table width='100%' border='0' cellpadding='0' cellspacing='0' style=\"font-size:12px\">\n" +
            "          <!--DWLayoutTable-->\n" +
            "          <tr>\n" +
            "                <td valign='top' align='center'>送货方式:" +trstr+"</td>\n" +
            "              </tr>\n"+
            "          <tr>\n" +
            "            <td height='52' align='center' valign='top'><input type='button' value='修改' onclick='javascript:editSendway("+sendway+");'/></td>\n" +
            "              </tr>\n" +
            "          </table>";
    out.print(outstr);
%>
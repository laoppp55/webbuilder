<%@ page import="com.bizwink.stockinfo.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<link href="/images/sinopec.css" type="text/css" rel="stylesheet" />
<%
    String stockid = ParamUtil.getParameter(request,"stockcode");
    ISpiderManager stoMgr = SpiderPeer.getInstance();
    Spider spider = stoMgr.getSpiderInfoBycode("600266.SS");

    String d = (ParamUtil.getParameter(request,"d",false) == null)? "":ParamUtil.getParameter(request,"d",false);
    java.util.Date sysDate = new java.util.Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String thedate = spider.getTrade_time().toString();
    int posi = thedate.indexOf(" ");
    thedate = thedate.substring(0,posi);
    out.write("<table class=\"right_bg01\" cellspacing=\"0\" cellpadding=\"0\" width=\"243\" border=\"0\">\r\n");
    out.write("<tbody>\r\n");
    out.write("<tr>\r\n");
    out.write("<td width=\"18\"><img alt=\"\" src=\"/images/space.gif\" /></td>\r\n");
    out.write("<td width=\"100\" height=\"30\">收盘价:" + spider.getPrev_Close() + "</td>\r\n");
    out.write("<td width=\"100\" height=\"30\">开盘价:" + spider.getOpen_money() + "</td>\r\n");
    out.write("</tr>\r\n");
    out.write("<tr>\r\n");
    out.write("<td width=\"18\"><img alt=\"\" src=\"/images/space.gif\" /></td>\r\n");
    out.write("<td height=\"25\">当前价：" + spider.getLast_trade() + "</td>\r\n");
    out.write("<td>范围：" + spider.getDay_range() + "</td>\r\n");
    out.write("</tr>\r\n");
    out.write("<tr>\r\n");
    out.write("<td width=\"18\"><img alt=\"\" src=\"/images/space.gif\" /></td>\r\n");
    out.write("<td colspan=\"2\" height=\"25\">变化量：" + spider.getChange() + "</td>\r\n");
    out.write("</tr>\r\n");
    out.write("</tbody>\r\n");
    out.write("</table>\r\n");
    out.write("<table cellspacing=\"0\" cellpadding=\"0\" width=\"243\" border=\"0\">\r\n");
    out.write("<tbody>\r\n");
    out.write("<tr>\r\n");
    out.write("<td><img alt=\"\" src=\"/images/20110114_45.gif\" /></td>\r\n");
    out.write("</tr>\r\n");
    out.write("</tbody>\r\n");
    out.write("</table>\r\n");
%>

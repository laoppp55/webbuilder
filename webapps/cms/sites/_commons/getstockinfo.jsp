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
    StockInfo spider = stoMgr.getSpiderInfoBycode("600266.SS");

    String d = (ParamUtil.getParameter(request,"d",false) == null)? "":ParamUtil.getParameter(request,"d",false);
    java.util.Date sysDate = new java.util.Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String thedate = spider.getTrade_time().toString();
    int posi = thedate.indexOf(" ");
    thedate = thedate.substring(0,posi);
    out.write("<table>");
    out.write("<tr><td>日&nbsp;&nbsp;期：" + thedate + "</td></tr>");
    out.write("<tr><td>前收盘：" + spider.getPrev_close() + "</td></tr>");
    out.write("<tr><td>开盘价：" + spider.getOpen_money() + "</td></tr>");
    out.write("<tr><td>当前价：" + spider.getLast_trade() + "</td></tr>");
    out.write("<tr><td>范&nbsp;&nbsp;围：" + spider.getDay_range_low() + "-" + spider.getDay_range_high() + "</td></tr>");
    out.write("<tr><td>变化量：" + spider.getThechange() + "</td></tr>");
    out.write("</table>");
%>

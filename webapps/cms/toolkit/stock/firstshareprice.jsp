<%@ page import="com.sinopec.stock.IStockManager" %>
<%@ page import="com.sinopec.stock.StockPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sinopec.stock.Stock" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<link href="/images/sinopec.css" type="text/css" rel="stylesheet" />
<%
    IStockManager stoMgr = StockPeer.getInstance();
    List shanghaiList = stoMgr.getStockNameInfo(1);
    List xianggangList = stoMgr.getStockNameInfo(2);
    List niuyueList = stoMgr.getStockNameInfo(3);
//    System.out.println(sto.getLast_trade() + "hhhh");
%>
<html>
<head>
<META http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate">
<META http-equiv="Pragma" content="no-cache">
</head>
<body topMargin=0 leftMargin=0>

                                            <table cellspacing="0" cellpadding="0" width="100%" border="0" class="stock-text">
                                                <tbody>
                                                    <tr>
<%
                if((shanghaiList != null)&&(shanghaiList.size() > 0)){
                for (int i = 0; i < 1; i++) {
                    Stock sto = (Stock) shanghaiList.get(i);
            %>
                                                        <td align="center">上海 <%=sto.getLast_trade() == null ? "" : sto.getLast_trade()%> <%=sto.getChange() == null ? "" : sto.getChange().substring(0, sto.getChange().indexOf(">")+1)%></td>
<%}}%>
<%
                if((xianggangList != null)&&(xianggangList.size() > 0)){
                for (int i = 0; i < 1; i++) {
                    Stock sto = (Stock) xianggangList.get(i);
            %>
                                                    <td align="center">香港 <%=sto.getLast_trade() == null ? "" : sto.getLast_trade()%> <%=sto.getChange() == null ? "" : sto.getChange().substring(0, sto.getChange().indexOf(">")+1)%></td>
<%}}%>
<%
                if((niuyueList != null)&&(niuyueList.size() > 0)){
                for (int i = 0; i < 1; i++) {
                    Stock sto = (Stock) niuyueList.get(i);
            %>
                                                        <td align="center">纽约 <%=sto.getLast_trade() == null ? "" : sto.getLast_trade()%> <%=sto.getChange() == null ? "" : sto.getChange().substring(0, sto.getChange().indexOf(">")+1)%></td>
<%}}%>
<%
                if((niuyueList != null)&&(niuyueList.size() > 0)){
                for (int i = 0; i < 1; i++) {
                    Stock sto = (Stock) niuyueList.get(i);
            %>             
                                           <td align="center">伦敦 <%=sto.getLast_trade() == null ? "" : sto.getLast_trade()%> <%=sto.getChange() == null ? "" : sto.getChange().substring(0, sto.getChange().indexOf(">")+1)%></td>
<%}}%>            
                                        </tr>
                                                </tbody>
                                            </table>
   </body>
</html> 

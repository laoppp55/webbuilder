<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.booyee.bookincome.*,
                 com.booyee.order.*"
                 contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String userid = authToken.getUserID();
  int siteid = 1;
  int msgflag = ParamUtil.getIntParameter(request, "msgflag", 0);

  IOrderManager orderMgr = OrderPeer.getInstance();
  Order order = new Order();
  List list = new ArrayList();
%>


<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<meta http-equiv="Pragma" content="no-cache">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
      String[][] titlebars = {
              { "首页", "" },
              { "图书管理", "" },
              { "库存图书", "" }
          };

      String[][] operations = {
        { "库存图书", "book.jsp" },{"    ",""},
        { "图书批量入库", "piliang.jsp" },{"    ",""},
       // { "图书出库", "chuku.jsp" },{"    ",""},
        { "图书退货", "tuihuo.jsp" },{"    ",""},
        { "图书上架", "shangjia.jsp" },{"    ",""},
        { "预定图书", "yuding.jsp" }
          };
%>
<%@ include file="../inc/titlebar.jsp" %>

<form action="chuku.jsp" method="post" name="chuku">
<center>
<%
  if(msgflag == 1){
%>
<font size="2" color="red">图书库存不足，无法发货！</font>
<br>
<%}%>
<table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">订单信息(只显示进入收到货款流程的订单)</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                  <td>订单号</td>
                  <td>用户ID </td>
                  <td>收件人姓名</td>
                  <td>收件人地址</td>
                  <td>收件人邮编</td>
                  <td>收件人电话</td>
                  <td>付款方式</td>

                  <td>地区</td>
                  <td>取货方式</td>
                  <td>订单状态</td>
                  <td>总费用</td>
                  <td>邮寄费用</td>
                  <td>需付费用</td>
                  <td>创建日期</td>
                </tr>
                  <%
                     list = orderMgr.order_list(3);
                     for(int i=0 ;i < list.size() ;i++){
                      order = (Order)list.get(i);
                      String createdate = String.valueOf(order.getCreateDate());
                      createdate = createdate.substring(0, 19);
                  %>
                <tr  bgcolor="#FFFFFF">
                  <%
                  //判断是否允许发货    status=2允许发货；否则不允许发货
                  int status = order.getStatus();
                  if(status == 3){
                  %>
                  <td><a href="chuku1.jsp?orderid=<%=order.getOrderid()%>&userid=<%=order.getUserid()%>&name=<%=order.getName()%>&receive=<%=order.getReceive()%>&dealflag=1">
                      发货</a>
                  </td>
                  <%}else{%>
                  <td>
                      发货
                  </td>
                  <%}%>
                  <td><%=order.getOrderid()%></td>
                  <td><%=order.getUserid()==null?"":new String(order.getUserid().getBytes("iso8859_1"),"GBK")%></td>
                  <td><%=order.getName()==null?"":new String(order.getName().getBytes("iso8859_1"),"GBK")%></td>
                  <td><%=order.getAddress()==null?"":new String(order.getAddress().getBytes("iso8859_1"),"GBK")%></td>
                  <td><%=order.getPostcode()%></td>
                  <td><%=order.getPhone()==null?"":new String(order.getPhone().getBytes("iso8859_1"),"GBK")%></td>
                  <td><%if(order.getBillingway() == 0){%>邮局汇款<%}else{if(order.getBillingway() == 1){%>银行汇款<%}else{if(order.getBillingway() == 2){%>其他方式<%}}}%></td>

                  <td><%if(order.getArea() == 0){%>中国大陆<%}else{if(order.getArea() == 1){%>港、澳、台地区<%}else{if(order.getArea() == 2){%>国外<%}}}%></td>
                  <td><%if(order.getReceive() == 0){%>普通邮寄<%}
                   else{if(order.getReceive() == 1){%>EMS特快专递<%}
                   else{if(order.getReceive() == 2){%>用户自取<%}}}%></td>
                  <td><%if(order.getStatus() == 0){%>新订单<%}
                   else{if(order.getStatus() == 1){%>正在处理<%}
                   else{if(order.getStatus() == 3){%>收到货款<%}
                   else{if(order.getStatus() == 2){%>送货流程<%}
                   else{if(order.getStatus() == 4){%>处理完毕<%}
                   else{if(order.getStatus() == 5){%>进入归档<%}
                   else{if(order.getStatus() == 6){%><font color="#FF0000">缺货</font><%}}}}}}}%></td>
                  <td><%=order.getTotalfee()%></td>
                  <td><%=order.getDeliveryfee()%></td>
                  <td><%=order.getPayfee()%></td>
                  <td><%=createdate%></td>
                </tr>
                <%}%>
               </table>
            </td>
          </tr>
        </table>
      </td>
</tr>
</table>
</center>
</form>
</center>
</body>
</html>
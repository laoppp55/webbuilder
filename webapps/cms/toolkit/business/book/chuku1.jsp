<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.booyee.order.*"
                 contentType="text/html;charset=gbk"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String userid = authToken.getUserID();
  int siteid = 1;
%>
<%
  int orderid           = ParamUtil.getIntParameter(request, "orderid", 0);
  String userid1        = ParamUtil.getParameter(request, "userid");
  String shippername    = ParamUtil.getParameter(request, "shippername");
  String receivername   = ParamUtil.getParameter(request, "name");
  String jing_ban_ren   = ParamUtil.getParameter(request, "jing_ban_ren");
  int shippingway       = ParamUtil.getIntParameter(request, "receive", 0);
  int nextflag          = ParamUtil.getIntParameter(request, "nextflag", 0);
  int dealflag          = ParamUtil.getIntParameter(request, "dealflag", 0);

  Order order = new Order();
  List list = new ArrayList();
  IOrderManager orderMgr = OrderPeer.getInstance();

  if(nextflag == 1){

    //入表tbl_booksend
    Order order2 = new Order();
    order2.setOrderid(orderid);
    order2.setUserid(userid1);
    order2.setShippingway(shippingway);
    orderMgr.insertBookSend(order2);

    //图书出库修改库存
    list = orderMgr.order_detail_list(orderid);
    boolean doflag = false;
    boolean flag = true;
    for(int j=0;j<list.size();j++){
      order = (Order)list.get(j);
      long bookid = order.getBookid();
      int num = order.getOrdernum();
      int booknum = orderMgr.getBookNum(bookid);
      orderMgr.updateBookNum(bookid, booknum, num);

      if((booknum-num)>0){
        order.setOrderid(orderid);
        order.setUserid(userid1);
        order.setShippername(shippername);
        order.setReceivername(receivername);
        order.setJing_ban_ren(jing_ban_ren);
        order.setShippingway(shippingway);
        //入出库表
        orderMgr.chuku_info(order);

        //入出库detail表
        order = orderMgr.getAOrderDetail(orderid,bookid);
        order.setBookname(new String(order.getBookname().getBytes("iso8859_1"),"GBK"));
        orderMgr.chuku_detail(order);

        //图书出库修改这个订单的状态为处理完毕
        orderMgr.updateOrderStatus(4, orderid);
      }else{
        flag = false;
      }
    }

    int msgflag = 0;
    if(!flag){
      msgflag = 1;
    }
    response.sendRedirect("chuku.jsp?msgflag="+msgflag);
  }
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<meta http-equiv="Pragma" content="no-cache">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
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
  <table width="100%" border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
    <tr>
      <td> <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">图书订单详细信息</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td> <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td>订单号</td>
                  <td>图书编号 </td>
                  <td>图书名称</td>
                  <td>图书价格</td>
                  <td>订购数目</td>
                  <td>折扣价格</td>
                  <td>图书类型</td>
                  <td>图书重量</td>
                  <td>图书宽度</td>
                  <td>图书高度</td>
                  <td>创建日期</td>
                </tr>
                <% list = orderMgr.order_detail_list(orderid);
                  for(int i=0 ;i < list.size() ;i++){
                    order = (Order)list.get(i);
                    String bookname = order.getBookname();
                    bookname = new String(bookname.getBytes("iso8859_1"),"GBK");
                    String createdate = String.valueOf(order.getCreateDate());
                    createdate = createdate.substring(0,19);
              %>
                <tr  bgcolor="#FFFFFF">
                  <td><%=order.getOrderid()%></a></td>
                  <td><%=order.getBookid()%></td>
                  <td><%=bookname%></td>
                  <td><%=order.getBookprice()%></td>
                  <td><%=order.getOrdernum()%></td>
                  <td><%=order.getDiscountprice()%></td>
                  <td><%=order.getBook_type()%></td>
                  <td><%=order.getBookweight()%></td>
                  <td><%=order.getBook_width()%></td>
                  <td><%=order.getBook_height()%></td>
                  <td><%=createdate%></td>
                </tr>
                <%}%>
              </table></td>
          </tr>
        </table></td>
    </tr>
  </table><br>
  <input type="button" value="返回" onclick="javascript:history.go(-1);">
  <% if(dealflag == 1){%>
  <form action="chuku1.jsp" method="post" name="chuku">
    <input type="hidden" name="nextflag" class="input" value="1">

    <table border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF"><tr><td>
      <table width="100%" border="0" cellpadding="0">
        <tr bgcolor="#F4F4F4" align="center">
          <td class="moduleTitle"><font color="#48758C">图书出库信息</font></td>
        </tr>
        <tr bgcolor="#d4d4d4" align="right">
          <td>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr bgcolor="#FFFFFF">
                <td width="146">订单编号：</td>
                <td width="222"> <input readonly = "true" class="input" name="orderid" type="text" size="20" value="<%=orderid%>">
                </td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td>订购者ID：</td>
                <td> <input readonly = "true" class="input" name="userid" type="text" size="20" value="<%=userid1%>">
                  <font color="#FF0000">&nbsp; </font> </td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td>发货人姓名：</td>
                <td> <input class="input" name="shippername" type="text" size="20">
                </td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td>收货人姓名：</td>
                <td> <input readonly = "true" class="input" name="name" type="text" size="20" value="<%=receivername%>">
                </td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td>经办人：</td>
                <td> <input class="input" name="jing_ban_ren" type="text" size="20">
                </td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td>送货方式：</td>
                <td><select name="receive">
                    <option value="1"<%if(shippingway == 1){%> selected<%}%>>普通邮寄</option>
                    <option value="2"<%if(shippingway == 2){%> selected<%}%>>EMS特快专递</option>
                    <option value="3"<%if(shippingway == 3){%> selected<%}%>>用户自取</option>
                  </select> </td>
              </tr>
            </table>
            <tr><td>&nbsp;</td></tr>
        <tr align="center">
          <td><input type="submit" name="Submit" value="提交"> &nbsp;&nbsp;&nbsp;
            <input name="Reset" type="reset"  value="重置"></td>
        </tr></td></tr>
      </table></td></tr>
    </table>
  </form>
  <%}%>
</center>
</body>
</html>

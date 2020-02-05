<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Other.*,
                 com.bizwink.cms.business.Order.*"
                 contentType="text/html;charset=gbk"%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int siteid = authToken.getSiteID();

  int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
  int orderid = ParamUtil.getIntParameter(request, "orderid", 0);
  int editflag = ParamUtil.getIntParameter(request, "editflag", 0);

  IOrderManager orderMgr= orderPeer.getInstance();
  Order order = new Order();
  IOtherManager otherMgr = otherPeer.getInstance();
  ReceiveMoney remoney = new ReceiveMoney();

  String payer            = ParamUtil.getParameter(request, "nameofpayer");
  String jingbanren       = ParamUtil.getParameter(request, "jingbanren");
  int payway              = ParamUtil.getIntParameter(request, "payway", 0);
  float number            = ParamUtil.getFloatParameter(request, "number", 0);
  String unit             = ParamUtil.getParameter(request, "unit");
  String desc             = ParamUtil.getParameter(request, "desc");
  String username = "";
  String numberstr = "";
  if((orderid != 0)&&(editflag == 1))
  {
    remoney = otherMgr.getAReceiveMoney(orderid);
    payer = remoney.getPayer();
    jingbanren = remoney.getJingBanRen();
    payway = remoney.getPayway();
    number = remoney.getNumber();
    numberstr = String.valueOf(number);
    unit = remoney.getUnit();
    order = orderMgr.getAOrder(orderid);
    username = order.getName();
    desc = remoney.getDescribe();
  }else if(startflag == 0)
  {
    remoney = otherMgr.getAReceiveMoney(orderid);
    order = orderMgr.getAOrder(orderid);
    username = order.getName();
    payer="";
    jingbanren=authToken.getUserID();
    unit= "人民币";
    payway=0;
  }

  if(startflag == 1){
    payer            = ParamUtil.getParameter(request, "payer");
    jingbanren       = ParamUtil.getParameter(request, "jingbanren");
    payway           = ParamUtil.getIntParameter(request, "payway", 0);
    number           = ParamUtil.getFloatParameter(request, "number", 0);
    unit             = ParamUtil.getParameter(request, "unit");
    desc             = ParamUtil.getParameter(request, "desc");

    remoney.setDescribe(desc);
    remoney.setJingBanRen(jingbanren);
    remoney.setNumber(number);
    remoney.setPayer(payer);
    remoney.setPayway(payway);
    remoney.setUnit(unit);
    remoney.setSiteID(siteid);
    remoney.setOrderid(orderid);

    /*if(editflag == 0)
    {
      otherMgr.newReceiveMoney(remoney);
      out.println("<script language=\"javascript\">");
      out.println("alert(\"收款信息添加成功\")");
      out.println("opener.history.go(0);");
      out.println("window.close();");
      out.println("</script>");
    }else
    {*/
    boolean payflag = otherMgr.checkPayMoney(orderid);
    if(!payflag){
      orderMgr.addReceiveMoney(orderid, authToken.getUserID());
      out.println("<script language=\"javascript\">");
      out.println("opener.history.go(0);");
      out.println("window.close();");
      out.println("</script>");
    }else{
      otherMgr.updateReceiveMoney(remoney);
      out.println("<script language=\"javascript\">");
      out.println("opener.history.go(0);");
      out.println("window.close();");
      out.println("</script>");
    }
  }

%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<script language="JavaScript" src="../include/setday.js" ></script>
<meta http-equiv="Pragma" content="no-cache">
<script language="javascript">
function check(frm){
  if((receive.userid.value == "")||(receive.userid.value == null)){
    alert("请输入用户编号！");
    return false;
  }

  if((receive.payer.value == "")||(receive.payer.value == null)){
    alert("请输入付款人姓名！");
    return false;
  }

  if((receive.jingbanren.value == "")||(receive.jingbanren.value == null)){
    alert("请输入经办人姓名！");
    return false;
  }

  if((receive.payway.value == "")||(receive.payway.value == null)){
    alert("请选择付款方式！");
    return false;
  }

  if((receive.number.value == "")||(receive.number.value == null)){
    alert("请输入金额！");
    return false;
  }

  if((receive.unit.value == "")||(receive.unit.value == null)){
    alert("请输入货币单位！");
    return false;
  }

  return true;
}

function getname()
{
  var userid = receive.userid.value;
  if(userid=="")
  {
    alert('请输入用户编号');
    return false;
  }
  if(isNaN(parseInt(userid)))
  {
    alert('请正确输入用户编号，由数字组成');
    return false;
  }
  var objXml = new ActiveXObject("Microsoft.XMLHTTP");
  objXml.open("POST", "getnames.jsp?userid="+userid, false);
  objXml.Send();
  var content = objXml.responseText;
  var idx11 = content.indexOf('_USERNAME_');
  var idx12 = content.indexOf('_UOK_');
  var idx21 = content.indexOf('_REALNAME_');
  var idx22 = content.indexOf('_ROK_');
  if(idx11 != -1 && idx12 != -1)
  {
    var username = content.substring(idx11+10,idx12);
    receive.username.value = username;
  }
  if(idx21 != -1 && idx22 != -1)
  {
    var realname = content.substring(idx21+10,idx22);
    receive.realname.value = realname;
  }
}
</script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<%
  String[][] titlebars = {
          { "首页", "" },
          { "帐务管理", "" }
      };

  String[][] operations = {};
%>
<%@ include file="../inc/titlebar.jsp" %>

<form action="receive.jsp" method="post" name="receive">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="orderid" value="<%=orderid%>">
<center>
<table width="770" border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">收款信息</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
                <table width="100%" border="0" cellpadding="2" cellspacing="1">
                  <tr bgcolor="#FFFFFF">
                    <td>客户真名：</td>
                    <td colspan=2> <input type="text" name="username" size="20" value="<%=username==null?"":StringUtil.gb2iso4View(username)%>" readonly>
                         <font color="#FF0000"> *</font>
                    </td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>付款人姓名：</td>
                    <td colspan=2> <input name="payer" type="text" size="20" value="<%=username==null?"":StringUtil.gb2iso4View(username)%>">
                      <font color="#FF0000">* </font> </td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>经办人：</td>
                    <td colspan=2> <input name="jingbanren" type="text" size="20" value="<%=jingbanren==null?"":StringUtil.gb2iso4View(jingbanren)%>">
                      <font color="#FF0000"> * </font> </td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>付款方式：</td>
                    <td colspan=2>
                    <select name="payway">
                    <option>请选择</option>
                    <option value="0" <%if(payway==0){%>selected<%}%>>邮局汇款</option>
                    <option value="1" <%if(payway==1){%>selected<%}%>>银行汇款</option>
                    <option value="2" <%if(payway==2){%>selected<%}%>>其他方式</option>
                    </select><font color="#FF0000"> * </font>
                    </td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>金额：</td>
                    <td colspan=2> <input name="number" type="text" size="20" value="<%=numberstr%>">
                      <font color="#FF0000"> * </font> </td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>货币单位：</td>
                    <td colspan=2> <input name="unit" type="text" size="20" value="<%=StringUtil.gb2iso4View(unit)%>">
                      <font color="#FF0000"> * </font> </td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>描述：</td>
                    <td colspan=2>
                    <textarea rows="5" cols="80" name="desc"><%=desc==null?"":StringUtil.gb2iso4View(desc)%></textarea><font color="#FF0000">&nbsp;</font>
                    </td>
                  </tr>
                </table>
            </td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr align="center">
            <td><input type="submit" name="Submit" value="提交" onclick="javascript:return check(this.form);">
                &nbsp;&nbsp;&nbsp;
                <input name="Reset" type="reset" id="Reset" value="重置"></td>
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

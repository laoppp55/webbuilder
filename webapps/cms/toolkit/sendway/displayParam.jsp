<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.cms.business.Order.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%
       int startflag = ParamUtil.getIntParameter(request,"startflag",-1);


         int paywayid = 0;
    InterfaceParam iParam = null;
   IOrderManager imr = orderPeer.getInstance();
    if(ParamUtil.getParameter(request,"paywayid") != null )  {
        paywayid=Integer.parseInt(ParamUtil.getParameter(request,"paywayid"));
         iParam =imr.getInterfaceParam(paywayid);
        if(iParam == null){
            out.println("<script>alert('没有要修改的信息!');</script>");
            response.sendRedirect("index.jsp");
        }
    }

    if(startflag == 1){
          iParam = new InterfaceParam();
          String accountnumber=ParamUtil.getParameter(request,"accountnumber");
          String paywaykey=ParamUtil.getParameter(request,"paywaykey");
          String alipayaccount=ParamUtil.getParameter(request,"alipayaccount");
         
          iParam.setAccountNumber(accountnumber);
          iParam.setPaywayKey(paywaykey);
          iParam.setPaywayId(paywayid);
          iParam.setAlipayAccount(alipayaccount);
          int t = imr.updateInterfaceParam(iParam);
        if(t == 0){
            out.println("<script>alert('修改失败');</script>");
        }
        else{
            response.sendRedirect("index.jsp");
        }
    }
%>
<html>
  <head>
  <title>支付方式管理</title>
      <meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache">
  <style type="text/css">
TABLE {FONT-SIZE: 12px;word-break:break-all}
BODY {FONT-SIZE: 12px;margin-top: 0px;margin-bottom: 0px; line-height:20px;}
.TITLE {FONT-SIZE:16px; text-align:center; color:#FF0000; font-weight:bold; line-height:30px;}
.FONT01 {FONT-SIZE: 12px; color:#FFFFFF; line-height:20px;}
.FONT02 {FONT-SIZE: 12px; color:#D04407; font-weight:bold; line-height:20px;}
.FONT03 {FONT-SIZE: 14px; color:#000000; line-height:25px;}
A:link {text-decoration:none;line-height:20px;}
A:visited {text-decoration:none;line-height:20px;}
A:active {text-decoration:none;line-height:20px; font-weight:bold;}
A:hover {text-decoration:none;line-height:20px;}
.pad {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; line-height:20px;}
.form{border-bottom:#000000 1px solid; background-color:#FFFFFF; border-left:#000000 1px solid; border-right:#000000 1px solid; border-top:#000000 1px solid; font-size: 9pt; font-family:"宋体";}
.botton{border-bottom:#000000 1px solid; background-color:#F1F1F1; border-left:#FFFFFF 1px solid; border-right:#333333 1px solid; border-top:#FFFFFF 1px solid; font-size: 9pt; font-family:"宋体"; height:20px; color: #000000; padding-bottom: 1px; padding-left: 1px; padding-right: 1px; padding-top: 1px; border-style: ridge}
</style>
      <script type="text/javascript">
          function chkSub(){
              var bessNo = document.getElementById("accountnumber").value;
               var key = document.getElementById("paywaykey").value;
               var sellerAcc = document.getElementById("alipayaccount").value;
              if(bessNo == null || bessNo == ""){
                  alert('请填写商户号!');
                  return false;
              }
              else if(key == null || key == ""){
                  alert('请填写密钥!');
                  return false;
              }
              else if(sellerAcc == null || sellerAcc == ""){
                  alert('请填写卖家支付宝账户!');
                  return false;
              }
              else{
                  return true;
              }
          }
          function bakurl(){
              window.location="index.jsp";
          }
      </script>
  </head>
  <body>
  <form action="displayParam.jsp" method="post">
   <input type="hidden" name="paywayid" value="<%=paywayid%>">
    <input type="hidden" name="startflag" value="1">
 <table width="50%" align="center" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                  <tr  bgcolor="#FFFFFF" class="css_001">
                    <td align="center">商户号:</td>
                   <td><input type="text" name="accountnumber" id="accountnumber" value="<%=iParam.getAccountNumber()%>"></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                    <td align="center">密钥:</td>
                   <td><input type="text" name="paywaykey" id="paywaykey" value="<%=iParam.getPaywayKey()%>"></td>
                </tr>
                  <tr  bgcolor="#FFFFFF" class="css_001">
                    <td align="center">卖家支付宝账户:</td>
                    <td><input type="text" name="alipayaccount" id="alipayaccount" value="<%=iParam.getAlipayAccount()%>"></td>
                  </tr>
                  <tr  bgcolor="#FFFFFF" class="css_001">
                    <td colspan="2" align="center"><input type="submit" name="sub" onclick="return chkSub();" value="修改">
                    <input type="button" value="返回" onclick="bakurl();">
                    </td>

                 </tr>
               </table>
            </td>
          </tr>
            </tr>
        </table>
      </td>
</tr>
</table>
</form>
</body>
</html>
<%@ page import="com.bizwink.cms.util.ParamUtil"%>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager"%>
<%@ page import="com.bizwink.cms.business.Order.orderPeer"%>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
      int siteid = authToken.getSiteID();
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);

    if(startflag == 1){
        IOrderManager orderMgr = orderPeer.getInstance();
        int cardnum = ParamUtil.getIntParameter(request,"cardnum",0);
        int denomination = ParamUtil.getIntParameter(request,"denomination",-1);
        String begintime = ParamUtil.getParameter(request,"begintime");
        String endtime = ParamUtil.getParameter(request,"endtime");
        orderMgr.autoCreateCard(siteid,begintime,endtime,denomination,cardnum);
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
<script language="JavaScript" src="include/setday.js" ></script>
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
.form{border-bottom:#000000 1px solid; background-color:#FFFFFF; border-left:#000000 1px solid; border-right:#000000 1px solid; border-top:#000000 1px solid; font-size: 9pt; font-family:"??ì?";}
.botton{border-bottom:#000000 1px solid; background-color:#F1F1F1; border-left:#FFFFFF 1px solid; border-right:#333333 1px solid; border-top:#FFFFFF 1px solid; font-size: 9pt; font-family:"??ì?"; height:20px; color: #000000; padding-bottom: 1px; padding-left: 1px; padding-right: 1px; padding-top: 1px; border-style: ridge}
</style>
</head>
<script type="text/javascript">
function check(){
    if(createForm.cardnum.value == ""){
        alert("请输入购物券的个数！");
        createForm.cardnum.focus();
        return false;
    }
    if(!isNumber(createForm.cardnum.value)){
        alert("您输入的购物券个数不正确！");
        createForm.cardnum.focus();
        return false;
    }
    if(createForm.denomination.value == ""){
        alert("请输入面额！");
        createForm.denomination.focus();
        return false;
    }
    return true;
}
function isNumber(num) {
    strRef = "1234567890";
    for (i = 0; i < num.length; i++)
    {
      tempChar = num.substring(i, i + 1);
      if (strRef.indexOf(tempChar, 0) == -1) {
        //alert("您输入的购买数量不正确！");
        return false;
      }
    }
    return true;
  }
</script>
<body>
<center>
<table width="50%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<form name="createForm" method="post" action="autocreatecard.jsp" onsubmit="return check();">
<input type="hidden" name="startflag" value="1">
<tr>
  <td>
    <table width="100%" border="0" cellpadding="0">
      <tr bgcolor="#F4F4F4" align="center">
        <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">自动生成离线购物卷</td>
      </tr>
      <tr bgcolor="#d4d4d4" align="right">
        <td>
          <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
            <tr  bgcolor="#FFFFFF" class="css_001">
              <td width="8%" align="center" bgcolor="#FFFFFF">生成购物券的个数</td>
              <td align="center" width="48%"><input name="cardnum" type="text" id="relation"></td>
            </tr>
              <tr  bgcolor="#FFFFFF" class="css_001">
              <td width="8%" align="center" bgcolor="#FFFFFF">面额</td>
              <td align="center" width="48%"><input type="text" name="denomination"></td>
            </tr>
              <tr bgcolor="#FFFFFF">
                <td valign="bottom" class="txt">日期</td>
                <td bgcolor="#FFFFFF" class="txt"> 从(开始日期)
                <input type="text" size="10" name="begintime" onfocus="setday(this)" readonly>
                 到(结束日期)
                  <input type="text" size="10" name="endtime" onfocus="setday(this)" readonly>
                </td>
             </tr>
           </table>
        </td>
      </tr>
    </table>
  </td>
</tr>
<tr>
<td align=center><input type="submit" value="确定"></td>
</tr>
</form>
</table>
</center>
</body>
</html>
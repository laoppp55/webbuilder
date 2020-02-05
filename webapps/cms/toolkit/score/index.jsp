<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
      int siteid = authToken.getSiteID();
    IOrderManager orderMgr = orderPeer.getInstance();
    int updateflag = ParamUtil.getIntParameter(request,"updateflag",-1);
    if(updateflag == 1) {
        int score = ParamUtil.getIntParameter(request,"scores",0);
        orderMgr.createScoresRule(siteid,score);
    }
    int scores = orderMgr.getScoresRuleForSite(siteid);

%>
<html>
<head>
<title>积分抵扣管理</title>
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
    <script language="javascript">

     function check(){
         if(document.form.scores.value == null || document.form.scores.value == ""){
             alert("请输入积分！");
             document.form.scores.focus();
             return false;
         }
         if(!isNumber(document.form.scores.value)){
             alert("请输入一个整数！");
             document.form.scores.focus();
             return false;
         }
         return true;
     }
         function isNumber(num) {
              var strRef = "1234567890";
              for (i = 0; i < num.length; i++)
              {
                var tempChar = num.substring(i, i + 1);
                if (strRef.indexOf(tempChar, 0) == -1) {
                  return false;
                }
              }
               return true;
            }
  </script>
</head>
<body>
<center>
<table width="80%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">积分抵扣管理</td>
          </tr>
            <form name="form" action="index.jsp" method="post" onsubmit="return check();">
                <input type="hidden" name="updateflag" value="1">
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="40%" align="right">积分抵扣</td>
                    <td align="left" width="30%"><input name="scores" type="text" size="10" value="<%=scores%>">积分=1元RMB </td>
                    <td align="left" width="30%"><input type="submit" name="sub" value="提交"> </td>
                </tr>

               </table>
            </td>
          </tr>
                </form>
            <tr>
                <td align="right">
                    <a href="/webbuilder/toolkit/index.jsp">返回</a>
                </td>
            </tr>
        </table>
      </td>
</tr>
</table>

</center>
</body>
</html>
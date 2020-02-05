<%@ page import="com.bizwink.cms.util.ParamUtil"%>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager"%>
<%@ page import="com.bizwink.cms.business.Order.orderPeer"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.bizwink.cms.business.Order.Card" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.webapps.survey.answer.IAnswerManager" %>
<%@ page import="com.bizwink.webapps.survey.answer.AnswerPeer" %>
<%@ page import="com.bizwink.webapps.survey.answer.Answer" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
      int siteid = authToken.getSiteID();
    int sid = ParamUtil.getIntParameter(request,"sid",0);
    IAnswerManager aMgr = AnswerPeer.getInstance();
    List list = aMgr.getUserinfo(sid);
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<script language="JavaScript" src="include/setday.js" ></script>
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
    function golist(r,str){
      window.location = "index.jsp?startrow="+r+str;
    }

    function del(){
      var val;
      val = confirm("你确定要删除吗？");
      if(val == 1){
        window.location = "deleteAllCard.jsp?startflag=1";
      }
    }

     function delacard(id,startrow){
      var val;
      val = confirm("你确定要删除吗？");
      if(val == 1){
        window.location = "deletecard.jsp?startflag=1&startrow="+startrow+"&id="+id;
      }
     }
     function update(id,startrow,ischeck){
          var val;
          val = confirm("你确定要修改吗？");
          if(val == 1){
            window.location = "update.jsp?startflag=1&startrow="+startrow+"&id="+id+"&ischeck="+ischeck;
          }
    }
  </script>
</head>
<body>
<input type="hidden" name="updateflag" value="1">
<center>
<table width="80%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">调查用户信息</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="20%" align="center" bgcolor="#FFFFFF">用户名</td>
                  <td align="center" width="15%">电话</td>
                  <td align="center" width="15%">年龄</td>
                  <td align="center" width="15%">email</td>
                  <td align="center" width="15%">日期</td>
                </tr>
                  <%
                      for(int i = 0; i < list.size();i++){
                          Answer a = (Answer)list.get(i);
                  %>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td align="center"><%=a.getUsername()==null?"": StringUtil.gb2iso4View(a.getUsername())%></td>
                    <td align="center"><%=a.getPhone()==null?"":StringUtil.gb2iso4View(a.getPhone())%></td>                    
                    <td align="center"><%=a.getAge()==null?"":StringUtil.gb2iso4View(a.getAge())%></td>
                    <td align="center"><%=a.getEmail()==null?"":StringUtil.gb2iso4View(a.getEmail())%></td>
                     <td align="center"><%=a.getDatetime().toString().substring(0,16)%></td>
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
</body>
</html>
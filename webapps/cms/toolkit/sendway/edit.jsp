<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.Fee" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.business.Order.SendWay" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
      int siteid = authToken.getSiteID();
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    int id = ParamUtil.getIntParameter(request,"id",0);
    IOrderManager oMgr = orderPeer.getInstance();

    if(startflag == 1){
        String cname = ParamUtil.getParameter(request,"cname");
        String notes = ParamUtil.getParameter(request,"notes");
        SendWay send = new SendWay();

        send.setCname(cname);
        send.setNotes(notes);
        send.setId(id);

        int errcode = oMgr.updateASendWayInfo(send);
        if(errcode == 0){
            out.print("<script language=javascript>alert(\"修改成功！\");window.location='index.jsp';</script>");
            return;
        }
        else{
            out.print("<script language=javascript>alert(\"修改失败！\");window.location='index.jsp';</script>");
            return;
        }
    }

    SendWay osend = oMgr.getASendWayInfo(id);
    String cnames = StringUtil.gb2iso4View(osend.getCname());
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
<script language="javascript">
function checkFloat(str)
{
    var retstr = false;
    var regstr = /[^0-9.]/gi;
    if (regstr.exec(str) == null)
    {
        retstr = true;
    }
    return retstr;
}
    function check(){
      if(form.cname.value==""||form.cname.value==null){
          alert("请填写支付方式名称！");
          form.cname.focus();
          return false;
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
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">支付方式</td>
          </tr>
            <form action="edit.jsp" name="form" onsubmit="return check();">
                <input type="hidden" name="startflag" value="1">
                <input type="hidden" name="id" value="<%=id%>">
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">支付方式名称</td>
                    <td align="left" width="50%">
                        <select name="cname">
                        <option value ="货到付款" <%if(cnames.equals("货到付款")){%>selected<%}%>>货到付款</option>
                        <option value ="邮政汇款" <%if(cnames.equals("邮政汇款")){%>selected<%}%>>邮政汇款</option>
                        <option value ="支付宝" <%if(cnames.equals("支付宝")){%>selected<%}%>>支付宝</option>
                        <option value ="财付通" <%if(cnames.equals("财付通")){%>selected<%}%>>财付通</option>
                        <option value ="快钱" <%if(cnames.equals("快钱")){%>selected<%}%>>快钱</option>
                    </select></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">支付方式描述</td>
                    <td align="left" width="50%"><textarea rows="5" cols="20" name="notes"><%=osend.getNotes()==null?"":StringUtil.gb2iso4View(osend.getNotes())%></textarea> </td>
                </tr>

               </table>
            </td>
          </tr>
            <tr>
                <td align="center">
                    <input type="submit" name="sub" value="修改">
                </td>
            </tr>
                </form>
        </table>
      </td>
</tr>
</table>

</center>
</body>
</html>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.Fee" %>
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
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    if(startflag == 1){
        String cname = ParamUtil.getParameter(request,"cname");
        String notes = ParamUtil.getParameter(request,"notes");
        float fee = ParamUtil.getFloatParameter(request,"fee",0);
        Fee feeinfo = new Fee();
        IOrderManager oMgr = orderPeer.getInstance();
        feeinfo.setCname(cname);
        feeinfo.setFee(fee);
        feeinfo.setNotes(notes);
        feeinfo.setSiteid(siteid);

        int errcode = oMgr.createFee(feeinfo);
        if(errcode == 0){
            out.print("<script language=javascript>alert(\"添加成功！\");window.location='index.jsp';</script>");
            return;
        }
        else{
            out.print("<script language=javascript>alert(\"添加失败！\");window.location='index.jsp';</script>");
            return;
        }
    }

%>
<html>
<head>
<title>送货方式管理</title>
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
          alert("请填写送货方式名称！");
          form.cname.focus();
          return false;
      }
        if(form.fee.value==""||!checkFloat(form.fee.value)){
            alert("运费应为小数型!");
            form.fee.focus();
            return false;
        }
        return true;
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
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">送货方式</td>
          </tr>
            <form action="create.jsp" name="form" onsubmit="return check();">
                <input type="hidden" name="startflag" value="1">
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">送货方式名称</td>
                    <td align="left" width="50%"><input type="text" name="cname" value=""></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">送货方式描述</td>
                    <td align="left" width="50%"><textarea rows="5" cols="20" name="notes"></textarea> </td>
                </tr>
                  <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">运费(如果免费请填写“0”)</td>
                    <td align="left" width="50%"><input type="text" name="fee" value="0"> </td>
                </tr>
               </table>
            </td>
          </tr>
            <tr>
                <td align="center">
                    <input type="submit" name="sub" value="提交">
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
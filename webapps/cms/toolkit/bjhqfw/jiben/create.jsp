<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.bjhqfw.shetuan.SheTuan" %>
<%@ page import="com.bizwink.cms.bjhqfw.jiben.IJiBenManager" %>
<%@ page import="com.bizwink.cms.bjhqfw.jiben.JiBenPeer" %>
<%@ page import="com.bizwink.cms.bjhqfw.jiben.JiBen" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
    int startflag = ParamUtil.getIntParameter(request,"startflag",0);
    String meetname = ParamUtil.getParameter(request,"meetname");
    String meetmax = ParamUtil.getParameter(request,"meetmax");
    String meetroot = ParamUtil.getParameter(request,"meetroot");

    if(startflag == 1){
        IJiBenManager jMgr = JiBenPeer.getInstance();
        JiBen jb = new JiBen();
        jb.setMeetname(meetname);
        jb.setMeetmax(meetmax);
        jb.setMeetroot(meetroot);
        jMgr.createJiBen(jb);
        response.sendRedirect("index.jsp");
    }

%>
<html>
<head>
<title>添加会议室基本信息</title>
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
            if(document.form.meetname.value == ""){
                alert("请输入会议室名称！");
                return false;
            }
            if(document.form.meetmax.value == ""){
                alert("请输入会议室容量！");
                return false;
            }
            if(document.form.meetroot.value == ""){
                alert("请输入会议室位置！");
                return false;
            }
            document.form.action = "create.jsp";
            document.form.submit();
        }
  </script>
</head>
<body>
<form name="form" action="" method="post">
<input type="hidden" name="startflag" value="1">
<center>
<table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td align="center">
        <table width="70%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">增加会议室基本信息</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="center">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">会议室名称：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;<input type="text" name="meetname" value=""></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">会议室容量：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;<input type="text" name="meetmax" value=""></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">会议室位置：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;<input type="text" name="meetroot" value=""></td>
                </tr>
               </table>
            </td>
          </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td align="center">
                    <input type="button" name="ok" value=" 添 加 " onclick="check()">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" name="ok" value=" 返 回 " onclick=javascript:history.go(-1);>
                </td>
            </tr>
        </table>
      </td>
</tr>
</table>

</center>
    </form>
</body>
</html>
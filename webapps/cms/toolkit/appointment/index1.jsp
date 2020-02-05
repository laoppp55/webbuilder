<%@ page import="com.bizwink.cms.util.ParamUtil"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.bizwink.cms.business.Order.Card" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.webapps.appointment.IAppointmentManager" %>
<%@ page import="com.bizwink.webapps.appointment.AppointmentPeer" %>
<%@ page import="com.bizwink.webapps.appointment.Appointment" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.bizwink.calendar.SmartDate" %>
<%@ page import="com.bizwink.calendar.SmartDateImpl" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
      int siteid = authToken.getSiteID();
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 50);
    int searchflag = ParamUtil.getIntParameter(request,"searchflag",-1);

    String jumpstr = "";
    List list = new ArrayList();
    int rows = 0;
     String sql = "select * from tbl_userappointment where siteid = "+ siteid ;
    String sqlnum = "select count(*) from tbl_userappointment where siteid = "+ siteid ;
    if(searchflag == -1){
         sql += " order by id desc";
    }else
    {

        String cname = ParamUtil.getParameter(request,"cname");
        if(cname != null && !cname.equals("") && !cname.equals("null"))
        {
            jumpstr = "&searchflag=1&cname="+cname;
             sql = "select * from tbl_userappointment where siteid = "+ siteid + " and appointmentcode = '"+cname+"'";
             sqlnum = "select count(*) from tbl_userappointment where siteid = "+ siteid + " and appointmentcode = '"+cname+"'";

        }
         sql += " order by id desc";
    }
    IAppointmentManager aMgr = AppointmentPeer.getInstance();
    list = aMgr.getUserAppointmentInfo(sql,startrow,range) ;
    rows = aMgr.getUserAppointmentInfoNum(sqlnum) ;

    int totalpages = 0;
    int currentpage = 0;
    if(rows < range){
        totalpages = 1;
        currentpage = 1;
    }else{
        if(rows%range == 0)
          totalpages = rows/range;
        else
          totalpages = rows/range + 1;
        currentpage = startrow/range + 1;
    }
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
      window.location = "index1.jsp?startrow="+r+str;
    }

   function check()
   {
       if(searchFom.cname.value == "")
       {
           alert("请输入预约号");
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
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">业务预定信息管理</td>
          </tr>

          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                    <td align="center" width="15%">序号</td>
                  <td width="20%" align="center" bgcolor="#FFFFFF">业务代码</td>
                  <td align="center" width="15%">业务名称</td>
                    <td align="center" width="15%">预约用户</td>
                    <td align="center" width="20%">预约日期</td>
                  <td align="center" width="15%">预约码</td>
                </tr>
                  <%
                      for(int i = 0; i < list.size();i++){
                          Appointment a = (Appointment)list.get(i);
                          Appointment b = aMgr.getCodeInfoByAppointmentCode(a.getAppointmentcode(), siteid);
                  %>
                <tr  bgcolor="#FFFFFF" class="css_001">
                    <td align="center"><%=a.getId()%></td>
                  <td align="center"><%=b.getCodeinfo() == null?"": StringUtil.gb2iso4View(b.getCodeinfo() )%></td>
                    <td align="center"><%=b.getCodename() == null?"": StringUtil.gb2iso4View(b.getCodename() )%></td>
                    <td align="center"><%=a.getUserid() == null?"":StringUtil.gb2iso4View(a.getUserid())%></td> 
                     <td align="center"><%=a.getAppointmentdate()== null?"": StringUtil.gb2iso4View(a.getAppointmentdate() )%></td>
                     <td align="center"><%=a.getAppointmentcode()== null?"": StringUtil.gb2iso4View(a.getAppointmentcode() )%></td>
                </tr>
                  <%}%>
               </table>
            </td>
          </tr>
        </table>
      </td>
</tr>
</table>
<table width="70%" class="css_002">
<tr valign="bottom" width=100%>
<td>
 总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
</td>
<td>
</td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td class="css_002">
<%
    if((startrow-range)>=0){
%>
[<a href="index1.jsp?startrow=<%=startrow-range%><%=jumpstr%>" class="css_002">上一页</a>]
<%}
  if((startrow+range)<rows){
%>
[<a href="index1.jsp?startrow=<%=startrow+range%><%=jumpstr%>" class="css_002">下一页</a>]
<%}

  if(totalpages>1){%>
  &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
  <a href="#" class="css_002" onclick="golist((document.all('jump').value-1) * <%=range%>,'<%=jumpstr%>');">GO</a>
  <%}%>
</td>
<td align="right">&nbsp;</td>
<td align="right">&nbsp;</td>
</tr>
</table>
<table width="50%" class="css_002">
    <tr valign="bottom" width=100%>
        <td colspan=6>
           &nbsp;
        </td>
    </tr>
    <tr valign="bottom" width=100%>
        <td colspan=6>
           &nbsp;
        </td>
    </tr>
</table>
<table width="50%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<form action="index1.jsp" name="searchFom" onsubmit="return check();">
<input type="hidden" name="searchflag" value="1">
<tr>
  <td>
    <table width="100%" border="0" cellpadding="0">
      <tr bgcolor="#F4F4F4" align="center">
        <td height="30" valign="left" bgcolor="#F4F4F4" class="css_003">查询：</td>
      </tr>
      <tr bgcolor="#d4d4d4" align="right">
        <td>
          <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
            <tr  bgcolor="#FFFFFF" class="css_001">
              <td width="8%" align="center" bgcolor="#FFFFFF">预约号：</td>
              <td align="center" width="48%"><input name="cname" type="text"></td>
            </tr>

           </table>
        </td>
      </tr>
    </table>
  </td>
</tr>
<tr>
<td align=center><input type="submit" value="查询"></td>
</tr>
</form>
</table>
</center>
</body>
</html>
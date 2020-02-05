<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.IYuDingManager" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.YuDingPeer" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.YuDing" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.bizwink.cms.bjhqfw.jiben.IJiBenManager" %>
<%@ page import="com.bizwink.cms.bjhqfw.jiben.JiBenPeer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
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
    String ydperson = ParamUtil.getParameter(request, "ydperson");
    String jbxinxiid = ParamUtil.getParameter(request,"jbxinxiid");
    String year = ParamUtil.getParameter(request, "year");
    String month = ParamUtil.getParameter(request, "month");
    String day = ParamUtil.getParameter(request, "day");
    String hour = ParamUtil.getParameter(request, "hour");
    String minute = ParamUtil.getParameter(request, "minute");

    String year1 = ParamUtil.getParameter(request, "year1");
    String month1 = ParamUtil.getParameter(request, "month1");
    String day1 = ParamUtil.getParameter(request, "day1");
    String hour1 = ParamUtil.getParameter(request, "hour1");
    String minute1 = ParamUtil.getParameter(request, "minute1");
    String khdate1 = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
    String jsdate1 = year1 + "-" + month1 + "-" + day1 + " " + hour1 + ":" + minute1 + ":00";
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    IJiBenManager jMgr = JiBenPeer.getInstance();
    List list = new ArrayList();
    list = jMgr.getAllListJiBen();

    if(startflag == 1){
        Timestamp khdate = new Timestamp(sf.parse(khdate1).getTime());
        Timestamp jsdate = new Timestamp(sf.parse(jsdate1).getTime());
        IYuDingManager yMgr = YuDingPeer.getInstance();
        YuDing yd = new YuDing();
        yd.setYdperson(ydperson);
        yd.setJbxinxiid(jbxinxiid);
        yd.setKhdate(khdate);
        yd.setJsdate(jsdate);
        yMgr.createYuDing(yd);
        response.sendRedirect("index.jsp");
    }

    Calendar today = Calendar.getInstance();
    int m_year = today.get(Calendar.YEAR);
    int m_day = today.get(Calendar.DAY_OF_MONTH);
    int m_month = today.get(Calendar.MONTH) + 1;
    int m_hour = today.get(Calendar.HOUR_OF_DAY);
    int m_minute = today.get(Calendar.MINUTE);

%>
<html>
<head>
<title>会议室预定</title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache">
<script language="JavaScript" src="../setday.js"></script>
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
            if(document.form.ydperson.value == ""){
                alert("请输入预定人！");
                return false;
            }
             var i = document.form.jbxinxiid.selectedIndex;   //获得下拉列表的value
            var val = document.form.jbxinxiid.options[i].value;
            if(val == "-1"){
                alert("请选择预定会议室ID！");
                return false;
            }
            /*if(document.form.khdate.value == ""){
                alert("请选择开会时间！");
                return false;
            }
            if(document.form.jsdate.value == ""){
                alert("请选择结束时间！");
                return false;
            }*/
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
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">会议室预定</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="center">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">预定人：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;&nbsp;<input type="text" name="ydperson" value=""></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">预定会议室ID：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;
                      <select name="jbxinxiid">
                        <option value="-1" selected>请选择
                        <%for(int i = 0; i < list.size(); i++){
                            JiBen jb = (JiBen)list.get(i);
                         %>
                        <option value="<%=jb.getId()%>"><%=jb.getMeetname()%>
                        <% }%>
                      </select>
                    </td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">开会时间：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;
                <input class=tine type=text size=3 maxlength=4 name=year
                                                               value=<%=m_year%>>年
                <select name=month size=1 class=tine>
                    <%for (int i = 1; i < 13; i++) {%>
                    <option value=<%=i%> <%=(m_month == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>月
                <select name=day size=1 class=tine>
                    <%for (int i = 1; i < 32; i++) {%>
                    <option value=<%=i%> <%=(m_day == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>日
                <select name=hour size=1 class=tine>
                    <%for (int i = 1; i < 24; i++) {%>
                    <option value=<%=i%> <%=(m_hour == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>时
                <select name=minute size=1 class=tine>
                    <%for (int i = 1; i < 61; i++) {%>
                    <option value=<%=i%> <%=(m_minute == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>分</td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">结束时间：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;
                <input class=tine type=text size=3 maxlength=4 name=year1
                                                               value=<%=m_year%>>年
                <select name=month1 size=1 class=tine>
                    <%for (int i = 1; i < 13; i++) {%>
                    <option value=<%=i%> <%=(m_month == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>月
                <select name=day1 size=1 class=tine>
                    <%for (int i = 1; i < 32; i++) {%>
                    <option value=<%=i%> <%=(m_day == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>日
                <select name=hour1 size=1 class=tine>
                    <%for (int i = 1; i < 24; i++) {%>
                    <option value=<%=i%> <%=(m_hour == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>时
                <select name=minute1 size=1 class=tine>
                    <%for (int i = 1; i < 61; i++) {%>
                    <option value=<%=i%> <%=(m_minute == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>分</td>
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
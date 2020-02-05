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
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    String jbxinxiid = ParamUtil.getParameter(request,"jbxinxiid");
    String huiyi = ParamUtil.getParameter(request,"huiyi");
    String riqi = ParamUtil.getParameter(request,"riqi");
    List currentlist = new ArrayList();
    IYuDingManager yMgr = YuDingPeer.getInstance();
    String sqlstr = "";
    if(jbxinxiid != null && jbxinxiid !=""){
        sqlstr = "select * from tbl_yuding where jbxinxiid = '" + jbxinxiid + "' and khdate >= to_date('" + riqi + " 00:00:00' ,'yyyy-mm-dd hh24:mi:ss') and khdate <= to_date('" + riqi + " 23:59:59' ,'yyyy-mm-dd hh24:mi:ss')";
    }
    //System.out.println("sqlstr = " + sqlstr);
    currentlist = yMgr.getAllYuDing(sqlstr,start,range);

    IJiBenManager jMgr = JiBenPeer.getInstance();
    List list = new ArrayList();
    list = jMgr.getAllListJiBen();


%>
<html>
<head>
<title>查询会议室预定</title>
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
        function searchcheck() {
             var i = document.form.jbxinxiid.selectedIndex;   //获得下拉列表的value
            var val = document.form.jbxinxiid.options[i].value;
            document.form.huiyi.value = document.form.jbxinxiid.options[i].innerText;
            if(val == "-1"){
                alert("请选择预定会议室ID！");
                return false;
            }
            if((form.riqi.value == null) || (form.riqi.value == "")){
                alert("请选择日期！")
                return false;
            }
            form.submit();
            return true;
        }
  </script>
</head>
<body>
<form name="form" action="ydjilu.jsp" method="post">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="huiyi" value="">
<center>
<table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td align="center">
        <table width="70%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">查询一天会议预定记录</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="center">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="20%" align="center">预定会议室ID&nbsp;</td>
                  <td width="40%" align="center">开始时间&nbsp;</td>
                  <td width="40%" align="center">结束时间&nbsp;</td>
                </tr>
                  <%
                        for(int i = 0; i < currentlist.size(); i++){
                            YuDing yd = (YuDing)currentlist.get(i);
                  %>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="20%" align="center"><%=huiyi%></td>
                  <td width="40%" align="center"><%=yd.getKhdate()%></td>
                  <td width="40%" align="center"><%=yd.getJsdate()%></td>
                </tr>
                  <%}%>
               </table>
            </td>
          </tr>
            <tr><td>&nbsp;</td></tr>
            <tr><td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="20%" align="right">预定会议室ID：&nbsp;</td>
                    <td align="left" width="20%">&nbsp;
                      <select name="jbxinxiid">
                        <option value="-1" selected>请选择
                        <%for(int i = 0; i < list.size(); i++){
                            JiBen jb = (JiBen)list.get(i);
                         %>
                        <option value="<%=jb.getId()%>"><%=jb.getMeetname()%>
                        <% }%>
                      </select>
                    </td>
                  <td width="20%" align="right">请选择日期：&nbsp;</td>
                  <td width="40%" align="left"><INPUT size=16 name=riqi onfocus="setday(this)"></td>
                </tr>
               </table>
            </td></tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td align="center">
                    <input type="button" name="ok" value=" 查 询 " onclick="searchcheck()">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" name="close" value=" 关 闭 " onclick="window.close();">
                </td>
            </tr>
        </table><br>
      </td>
</tr>
</table>

</center>
    </form>
</body>
</html>
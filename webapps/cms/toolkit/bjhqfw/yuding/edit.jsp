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
    int id = ParamUtil.getIntParameter(request,"id",0);
    int m_year = 0;
    int m_day = 0;
    int m_month = 0;
    int m_hour = 0;
    int m_minute = 0;

    int m_year1 = 0;
    int m_day1 = 0;
    int m_month1 = 0;
    int m_hour1 = 0;
    int m_minute1 = 0;

    IJiBenManager jMgr = JiBenPeer.getInstance();
    List list = new ArrayList();
    list = jMgr.getAllListJiBen();
    IYuDingManager yMgr = YuDingPeer.getInstance();
    YuDing yd = new YuDing();

    if(startflag == 1){
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

        Timestamp khdate = new Timestamp(sf.parse(khdate1).getTime());
        Timestamp jsdate = new Timestamp(sf.parse(jsdate1).getTime());
        yMgr = YuDingPeer.getInstance();
        yd = new YuDing();
        yd.setYdperson(ydperson);
        yd.setJbxinxiid(jbxinxiid);
        yd.setKhdate(khdate);
        yd.setJsdate(jsdate);
        yMgr.updateYuDing(yd,id);
        response.sendRedirect("index.jsp");
    } else {
        yd = yMgr.getByIdYuDing(id);
        Timestamp khdate = yd.getKhdate();
        Timestamp jsdate = yd.getJsdate();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(khdate.getTime());
        m_year = cal.get(Calendar.YEAR);
        m_day = cal.get(Calendar.DAY_OF_MONTH);
        m_month = cal.get(Calendar.MONTH)+1;
        m_hour = cal.get(Calendar.HOUR);
        m_minute = cal.get(Calendar.MINUTE);

        cal.setTimeInMillis(jsdate.getTime());
        m_year1 = cal.get(Calendar.YEAR);
        m_day1 = cal.get(Calendar.DAY_OF_MONTH);
        m_month1 = cal.get(Calendar.MONTH)+1;
        m_hour1 = cal.get(Calendar.HOUR);
        m_minute1 = cal.get(Calendar.MINUTE);
    }
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
        A:link {text-decoration:none;line-height:20px;}
        A:visited {text-decoration:none;line-height:20px;}
        A:active {text-decoration:none;line-height:20px; font-weight:bold;}
        A:hover {text-decoration:none;line-height:20px;}
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
            document.form.action = "edit.jsp";
            document.form.submit();
        }
    </script>
</head>
<body>
<form name="form" action="" method="post">
    <input type="hidden" name="startflag" value="1">
    <input type="hidden" name="id" value="<%=id%>">
    <center>
        <table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
            <tr>
                <td align="center">
                    <table width="100%" border="0" cellpadding="0">
                        <tr bgcolor="#F4F4F4" align="center">
                            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">会议室预定</td>
                        </tr>
                        <tr bgcolor="#d4d4d4" align="center">
                            <td>
                                <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="50%" align="right">预定人：&nbsp;</td>
                                        <td align="left" width="50%">&nbsp;&nbsp;<input type="text" name="ydperson" value="<%=yd.getYdperson()%>" readonly="true"></td>
                                    </tr>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="50%" align="right">预定会议室ID：&nbsp;</td>
                                        <td align="left" width="50%">&nbsp;
                                            <select name="jbxinxiid">
                                                <option value="-1">请选择
                                                        <%for(int i = 0; i < list.size(); i++){
                            JiBen jb = (JiBen)list.get(i);
                         %>
                                                <option value="<%=jb.getId()%>"<%if(yd.getJbxinxiid().equals(String.valueOf(jb.getId()))){%>selected<%}%>><%=jb.getMeetname()%>
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
                                                   value=<%=m_year1%>>年
                                            <select name=month1 size=1 class=tine>
                                                <%for (int i = 1; i < 13; i++) {%>
                                                <option value=<%=i%> <%=(m_month1 == i) ? "selected" : ""%>><%=i%>
                                                </option>
                                                <%}%>
                                            </select>月
                                            <select name=day1 size=1 class=tine>
                                                <%for (int i = 1; i < 32; i++) {%>
                                                <option value=<%=i%> <%=(m_day1 == i) ? "selected" : ""%>><%=i%>
                                                </option>
                                                <%}%>
                                            </select>日
                                            <select name=hour1 size=1 class=tine>
                                                <%for (int i = 1; i < 24; i++) {%>
                                                <option value=<%=i%> <%=(m_hour1 == i) ? "selected" : ""%>><%=i%>
                                                </option>
                                                <%}%>
                                            </select>时
                                            <select name=minute1 size=1 class=tine>
                                                <%for (int i = 1; i < 61; i++) {%>
                                                <option value=<%=i%> <%=(m_minute1 == i) ? "selected" : ""%>><%=i%>
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
                                <input type="button" name="ok" value=" 修 改 " onclick="check()">
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
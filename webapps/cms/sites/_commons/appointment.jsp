<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.webapps.appointment.IAppointmentManager" %>
<%@ page import="com.bizwink.webapps.appointment.AppointmentPeer" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.webapps.appointment.Appointment" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    IUregisterManager regMgr = UregisterPeer.getInstance();
    String sitename = request.getServerName();
    String slash_sitename = StringUtil.replace(sitename, ".", "_");
    int Siteid = regMgr.getSiteid(sitename);
    Uregister ug = (Uregister) session.getAttribute("UserLogin");
    if (ug == null) {
        out.print("<script language=\"javascript\">alert(\"您还没有登录，请先登录！\");window.close();</script>");
        return;
    }
    String userid = ug.getMemberid();
    //String userid = "feixiang";
    //int Siteid = 1711;
    String code = ParamUtil.getParameter(request, "code");//业务代码
    IAppointmentManager aMgr = AppointmentPeer.getInstance();
    
    String cname = aMgr.getAppointmentNameByAppointmentCode(code, Siteid);
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    if(startflag == 1){
        String year = ParamUtil.getParameter(request,"year");
        String month = ParamUtil.getParameter(request,"month");
        String day = ParamUtil.getParameter(request,"day");
        String hour = ParamUtil.getParameter(request,"hour");
        String minutes = ParamUtil.getParameter(request,"minutes");
        String appointmentcode = ParamUtil.getParameter(request,"appointmentcode");
        Appointment a = new Appointment();
        a.setAppointmentcode(appointmentcode);
        a.setAppointmentdate(year+"-"+month+"-"+day+" "+hour+":"+minutes);
        a.setUserid(userid);
        a.setSiteid(Siteid);
        int errcode = aMgr.createUserAppointmentInfo(a);
        if(errcode == 0){
             out.print("<script language=\"javascript\">alert(\"您已预约成功，您的预约号为："+appointmentcode+"\");window.close();</script>");
        }else{
             out.print("<script language=\"javascript\">alert(\"预约失败，请重新预约\");window.close();</script>");
        }
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>北京市无线电管理局</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <style type="text/css"></style>
    <link href="http://www.bjcz.gov.cn/images/common.css" rel="stylesheet" type="text/css">
</head>
<script language="javascript">
    function selectdate()
    {
        var year = form.year.value;
        var month = form.month.value;
        var day = form.day.value;
        var objXml = new ActiveXObject("Microsoft.XMLHTTP");
        objXml.open("POST", "getappointmentcode.jsp?siteid=<%=Siteid%>&code=<%=code%>&date="+year+"-"+month+"-"+day, false);
        objXml.Send();

        var retstr = objXml.responseText;
       if(retstr != null && retstr.indexOf("false")>-1)
       {
           document.getElementById("nocode").innerHTML = "<font color=\"red\">该时间没有预约号，请重新选择时间！</font>";
           form.appointmentcode.value = "";
           document.getElementById("tijiao").disabled = true;
       }
        else{
            form.appointmentcode.value = retstr;
           document.getElementById("tijiao").disabled = false;
       }
    }
</script>
<body>
<center>
    <form action="appointment.jsp" method="post" name="form">
        <input type="hidden" name="startflag" value="1">
        <table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="16%">&nbsp;</td>
                <td width="84%">&nbsp;</td>
            </tr>
            <tr>
                <td height="40" align="right" valign="middle" class="inputtitle">业务代码：</td>
                <td align="left" valign="middle"><label>
                    <%=code%>
                </label></td>
            </tr>
            <tr>
                <td height="40" align="right" valign="middle" class="inputtitle">业务名称：</td>
                <td align="left" valign="middle"><%=cname == null ? "" : StringUtil.gb2iso4View(cname)%>
                </td>
            </tr>
            <tr>
                <td height="40" align="right" valign="middle" class="inputtitle">预约时间：</td>
                <td align="left" valign="middle"><label>
                    <label>
                        <select name="year" onchange="selectdate();">
                            <%
                                Timestamp currentdate = new Timestamp(System.currentTimeMillis());
                                int year = currentdate.getYear() + 1900;
                                int month = currentdate.getMonth() + 1;
                                int day = currentdate.getDay();
                                for (int i = 2010; i <= 2050; i++) {
                            %>
                            <option value="<%=i%>"<%if (i == year) {%> selected <%}%>><%=i%>
                            </option>
                            <%}%>
                        </select>
                        年
                        <select name="month" onchange="selectdate();">
                            <%
                                for (int i = 1; i <= 12; i++) {
                                    String dates = "";
                                    if (i < 10) {
                                        dates = "0" + String.valueOf(i);
                                    } else {
                                        dates = String.valueOf(i);
                                    }
                            %>
                            <option value="<%=dates%>"<%if (i == month) {%> selected <%}%>><%=i%>
                            </option>
                            <%}%>
                        </select>
                        月
                        <select name="day" onchange="selectdate();">
                            <%
                                for (int i = 1; i <= 31; i++) {
                                    String dates = "";
                                    if (i < 10) {
                                        dates = "0" + String.valueOf(i);
                                    } else {
                                        dates = String.valueOf(i);
                                    }
                            %>
                            <option value="<%=dates%>"<%if (i == day) {%> selected <%}%>><%=i%>
                            </option>
                            <%}%>
                        </select>
                        日
                        <select name="hour">
                            <%
                                for (int i = 0; i <= 24; i++) {
                                    String dates = "";
                                    if (i < 10) {
                                        dates = "0" + String.valueOf(i);
                                    } else {
                                        dates = String.valueOf(i);
                                    }
                            %>
                            <option value="<%=dates%>"><%=i%>
                            </option>
                            <%}%>
                        </select>
                        时
                        <select name="minutes">
                            <%
                                for (int i = 0; i <= 59; i++) {
                                    String dates = "";
                                    if (i < 10) {
                                        dates = "0" + String.valueOf(i);
                                    } else {
                                        dates = String.valueOf(i);
                                    }
                            %>
                            <option value="<%=dates%>"><%=i%>
                            </option>
                            <%}%>
                        </select>
                        分
                    </label>
                </label></td>
            </tr>
            <tr>
                <td height="40" align="right" valign="middle" class="inputtitle">预约号：</td>
                <td align="left" valign="middle"><label>
                    <input name="appointmentcode" type="text" class="txtinput" readonly>
                </label></td>
            </tr>
            <tr>
                <td height="40" align="right" valign="middle" class="inputtitle">&nbsp</td>
                <td align="left" valign="middle"><div id="nocode"></div></td>
            </tr>
            <tr>
                <td height="40" align="right" valign="middle" class="inputtitle">&nbsp;</td>
                <td align="left" valign="middle"><input type="submit" id="tijiao" name="tijiao" value="提交" disabled="true"> </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </form>
</center>
</body>
</html>
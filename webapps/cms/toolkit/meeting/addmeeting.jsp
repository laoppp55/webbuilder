<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.ICompanyinfoManager" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.CompanyinfoPeer" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.Meettings" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);


    if (startflag == 1) {
        String meetingname = ParamUtil.getParameter(request, "meetingname");
        String meetingdatetime = ParamUtil.getParameter(request, "meetingdatetime");
        String address = ParamUtil.getParameter(request, "address");
        String editor=ParamUtil.getParameter(request, "editor");

        try {
            ICompanyinfoManager CompanyinfoMgr = CompanyinfoPeer.getInstance();
            Meettings meettings = new Meettings();
            meettings.setMeetingname(meetingname);
            meettings.setMeetingdatetime(Timestamp.valueOf(meetingdatetime+" 00:00:00"));
            meettings.setAddress(address);
            meettings.setEditor(editor);
            CompanyinfoMgr.addMeetings(meettings);
            response.sendRedirect("index.jsp?success=1");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>添加新培训会</title>
    <style type="text/css">
        <!--
        body {
            margin-top: 0px;
            margin-bottom: 0px;
        }

        -->
    </style>
    <link href="images/css.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/WdatePicker.js" ></script>
    <script type="text/javascript">
        function checkSurvey(){
            if((addForm.meetingname.value == null)||(addForm.meetingname.value == "")){
                alert("请输入培训会的名称");
                return false;
            }
            if((addForm.meetingdatetime.value == null)||(addForm.meetingdatetime.value == "")){
                alert("请输入培训会的时间");
                return false;
            }
            if((addForm.meetingdatetime.value == null)||(addForm.meetingdatetime.value == "")){
                alert("请输入培训会的地址");
                return false;
            }
            return true;
        }
    </script>
</head>

<body>
<center>
    <table width="900" border="0" cellpadding="0" cellspacing="0" class="bian">
        <tr>
            <td height="360" valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <form method="POST" action="addmeeting.jsp" name="addForm" onsubmit="javascript:return checkSurvey();">
                        <input type="hidden" name="startflag" value="1">
                        <input type="hidden" name="editor" value="<%=authToken.getUserID()%>">
                        <tr>
                            <td>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="50" height="40" align="center"><img src="images/qian_02.jpg" width="30" height="30"/>
                                        </td>
                                        <td width="150" class="black12c">添加新培训会</td>
                                        <td width="697"></td>
                                    </tr>

                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="300" height="1" bgcolor="#898898"></td>
                                        <td width="1" height="1" bgcolor="#898898"></td>
                                        <td height="1" bgcolor="#898898"></td>
                                    </tr>
                                    <tr>
                                        <td height="40" align="center" bgcolor="#F6F5F0" class="black12c">培训会名称：</td>
                                        <td width="1" bgcolor="#898898"></td>
                                        <td>
                                            &nbsp;&nbsp;<input type="text" name="meetingname" size="50">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="1" bgcolor="#898898"></td>
                                        <td width="1" height="1" bgcolor="#898898"></td>
                                        <td height="1" bgcolor="#898898"></td>
                                    </tr>
                                    <tr>
                                        <td height="40" align="center" bgcolor="#F6F5F0" class="black12c">培训会时间：</td>
                                        <td width="1" bgcolor="#898898"></td>
                                        <td>
                                            &nbsp;&nbsp;<input id="beginTime"  value="" type="text" onfocus="WdatePicker({el:'beginTime',dateFmt:'yyyy-MM-dd'});" class="Wdate" name="meetingdatetime" size="50" >
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="1" bgcolor="#898898"></td>
                                        <td width="1" height="1" bgcolor="#898898"></td>
                                        <td height="1" bgcolor="#898898"></td>
                                    </tr>
                                    <tr>
                                        <td height="40" align="center" bgcolor="#F6F5F0" class="black12c">培训会地址：</td>
                                        <td width="1" bgcolor="#898898"></td>
                                        <td>
                                            &nbsp;&nbsp;<input type="text" name="address" size="50">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="1" bgcolor="#898898"></td>
                                        <td width="1" height="1" bgcolor="#898898"></td>
                                        <td height="1" bgcolor="#898898"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td height="50">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="300"></td>
                                        <td width="52"><input type="submit" value="提  交" name="mee_submit"></td>
                                        <td width="70"></td>
                                        <td width="52"><input type="reset" value="重  写" name="survey_reset"></td>
                                        <td width="71"></td>
                                        <td width="52"><input type="button" value="返  回" name="survey_back" onclick="window.location = 'index.jsp'"></td>
                                        <td width="300"></td>
                                    </tr>
                                </table>
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




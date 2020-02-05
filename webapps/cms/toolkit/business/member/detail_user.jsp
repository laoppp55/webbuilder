<%@ page import="java.sql.*,
                 java.text.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Users.*"
         contentType="text/html;charset=gbk"
        %>
<%@ include file="../../../include/auth.jsp"%>
<%
    int siteid = authToken.getSiteID();
    String userid            = ParamUtil.getParameter(request, "userid");
    int startflag            = ParamUtil.getIntParameter(request, "startflag", 0);

    BUser buser = new BUser();
    IBUserManager buserMgr = buserPeer.getInstance();
    buser = buserMgr.getAUsers(userid,siteid);
    String username = buser.getUserName();
    username = StringUtil.gb2iso4View(username);
    int sex = buser.getSex();
    String address = buser.getAddress();
    String postcode = buser.getPostCode();
    String email = buser.getEmail();
    String phone = buser.getPhone();
    String mobilephone = buser.getMobilePhone();
    int birthday_year = buser.getBirthday_year();
    int birthday_month = buser.getBirthday_month();
    int birthday_day = buser.getBirthday_day();
    String occupation = buser.getOccupation();
    Timestamp createdate = buser.getCreateDate();
    int education = buser.getEducation();
    int lockflag = buser.getLockFlag();
    String showdate = String.valueOf(createdate);
    //if(showdate != null && ! "".equals(showdate))
    //  showdate = showdate.substring(0,16);

    System.out.println(username +"=="+phone + "==" + mobilephone);

    if(startflag==1){
        username               = ParamUtil.getParameter(request, "username");
        address                = ParamUtil.getParameter(request, "address");
        postcode               = ParamUtil.getParameter(request, "postcode");
        email                  = ParamUtil.getParameter(request, "email");
        phone                  = ParamUtil.getParameter(request, "phone");
        mobilephone            = ParamUtil.getParameter(request, "mobilephone");
        occupation             = ParamUtil.getParameter(request, "occupation");
        sex                    = ParamUtil.getIntParameter(request, "sex", 0);
        birthday_year          = ParamUtil.getIntParameter(request, "birthday_year", 0);
        birthday_month         = ParamUtil.getIntParameter(request, "birthday_month", 0);
        birthday_day           = ParamUtil.getIntParameter(request, "birthday_day", 0);
        education              = ParamUtil.getIntParameter(request, "education", 0);
        lockflag               = ParamUtil.getIntParameter(request, "lockflag",0);

        buser.setSiteid(siteid);
        buser.setUserID(userid);
        buser.setUserName(username);
        buser.setSex(sex);
        buser.setAddress(address);
        buser.setPostCode(postcode);
        buser.setEmail(email);
        buser.setPhone(phone);
        buser.setMobilePhone(mobilephone);
        buser.setBirthday_year(birthday_year);
        buser.setBirthday_month(birthday_month);
        buser.setBirthday_day(birthday_day);
        buser.setOccupation(occupation);
        buser.setEducation(education);
        buser.setLockFlag(lockflag);

        buserMgr.updateUserInfo(buser);
        response.sendRedirect("detail_user.jsp?userid="+userid);
    }

    DecimalFormat df = new DecimalFormat();
    df.applyPattern("0.00");
%>
<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <meta http-equiv="Pragma" content="no-cache">

</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>

    <form action="detail_user.jsp" method="post" name="detailform">
        <input type="hidden" name="startflag" value="1">
        <input type="hidden" name="userid" value=<%=userid%>>
        <center>
            <table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
                <tr>
                    <td>
                        <table width="100%" border="0" cellpadding="2" cellspacing="1">
                            <tr bgcolor="#d4d4d4" align="center" valign="middle">
                                <td  class="txt">前台客户&nbsp;<%=username%>&nbsp;信息 &nbsp;&nbsp;</td>
                            </tr>

                            <tr bgcolor="#F4F4F4" align="right">
                                <td >
                                    <%@ include file="userinfo.jsp" %>
                                </td>
                            </tr>

                            <!--tr bgcolor="#F4F4F4" align="center">
                                <td >
                                    <input type="submit" value="修改">
                                </td>
                            </tr-->
                        </table>
                    </td>
                </tr>
            </table>
        </center>
    </form>
</center>
</body>
</html>
<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.webapps.register.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"%>
<%@ include file="../../../include/auth.jsp"%>
<%
    if (!SecurityCheck.hasPermission(authToken, 54)) {
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    int siteid = authToken.getSiteID();
    boolean error = false;
    boolean success = false;
    int flag=ParamUtil.getIntParameter(request, "flag",0);

    String errormsg = "";
    if (flag == 1) {
        String memberId = ParamUtil.getParameter(request, "memberid");
        String realName = ParamUtil.getParameter(request, "realname");
        String pass = ParamUtil.getParameter(request, "password");
        String linkMan = ParamUtil.getParameter(request, "linkman");
        String email = ParamUtil.getParameter(request, "email");
        String phone = ParamUtil.getParameter(request, "phone");
        String mobilePhone = ParamUtil.getParameter(request, "mobilephone");
        int userType = ParamUtil.getIntParameter(request, "usertype",0);
        String grade = ParamUtil.getParameter(request, "grade");

        Uregister uf = new Uregister();
        uf.setSiteid(siteid);
        uf.setMemberid(memberId);
        uf.setPassword(pass);
        uf.setLinkman(linkMan);
        uf.setEmail(email);
        uf.setPhoen(phone);
        uf.setGrade(grade);
        uf.setMobilephone(mobilePhone);
        uf.setUsertype(userType);
        uf.setName(realName);

        IUregisterManager uiMgr = UregisterPeer.getInstance();
        boolean bl = uiMgr.userExist(memberId);
        if (!bl) { //验证用户id是否存在，如果不存在则添加新用户
            int rel = uiMgr.insert_Info(uf);
            if (rel != 0) {
                errormsg = "<br><font color=red><b>添加成功!</b></font>";
            } else {
                errormsg = "<br><font color=red><b>添加失败!</b></font>";
            }
        } else {
            errormsg = "<br><font color=red><b>对不起，该用户名已存在!</b></font>";
        }
    }
%>

<html>
<head>
    <title>创建新用户</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">

    <link rel="stylesheet" type="text/css" href="../style/global.css">

    <SCRIPT LANGUAGE=javascript>

        function checkinput()
        {
            var memId=document.getElementById("memberid").value;
            var usertype=document.getElementById("usertype").value;
            if(memId == "" || memId == null){
                alert("用户id不能为空");
                return false;
            } else if(usertype == "-1" || usertype == null){
                alert("用户类型不能为空");
                return false;
            }
            return true;

        }
    </SCRIPT>
</head>

<body bgcolor="#ffffec">
<div align="center">
    <form name="newitem" id="form1" action="frontnewuser.jsp" method="post" >
        <input type="hidden" name="flag" value="1">

        <div style="margin-top:40px; padding-top:40px">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr bgcolor="#d4d4d4">
                    <td valign="top">
                        <table width="100%" border="0" cellpadding="4" cellspacing="1">
                            <tr bgcolor="#FFFFFF">

                                <td valign="top" class="txt" width="50%" colspan="2" align="center">添加新用户信息

                                </td>

                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td class="txt">&nbsp;&nbsp;用户ID：&nbsp;
                                    <input type="text" id="memberid" name="memberid" class="input">
                                </td>
                                <td valign="top" class="txt" width="50%">真实姓名：&nbsp;
                                    <input type="text" id="realname" name="realname" class="input">
                                </td>

                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td class="txt">用户口令：&nbsp;
                                    <input type="password" id="password" name="password" class="input">
                                </td>
                                <td class="txt">电子邮件：&nbsp;
                                    <input type="text" name="email" id="email" class="input">
                                </td>
                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td valign="top" class="txt" width="50%">&nbsp;&nbsp;&nbsp;&nbsp;电话：&nbsp;
                                    <input type="text" name="phone" id="phone" class="input">
                                </td>
                                <td class="txt">移动电话：&nbsp;
                                    <input type="text" name="mobilephone" id="mobilephone" class="input">
                                </td>
                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td valign="top" class="txt" width="50%">&nbsp;&nbsp;联系人：&nbsp;
                                    <input type="text" name="linkman" id="linkman" class="input">
                                </td>
                                <td class="txt">用户级别：&nbsp;
                                    <input type="text" id="grade" name="grade" class="input">
                                </td>
                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td valign="top" class="txt" width="50%" colspan="2">用户类型：&nbsp;
                                    <select name="usertype" id="usertype">
                                        <option value="-1">---请选择---</option>
                                        <option value="0">普通用户</option>
                                        <option value="1">内部用户</option>
                                        <option value="2">企业用户</option>
                                        <option value="3">VIP用户</option>
                                    </select>
                                </td>
                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td valign="top" class="txt" align="center" colspan="2">
                                    <input type="submit" value="添加" onclick="return checkinput();">
                                    <input type="reset" value="重置">
                                </td>

                            </tr>


                        </table>
                    </td>
                </tr>
            </table>
            <div id="msg"><%=errormsg%></div>
        </div>
    </form>
</div>
</body>
</html>

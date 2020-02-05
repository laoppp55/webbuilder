<%@ page import="java.util.*,
		 java.net.URLEncoder,
                 com.bizwink.cms.security.*,
                 org.apache.regexp.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"

%>
<%@ include file="../../../include/auth.jsp"%>
<%
    if (!SecurityCheck.hasPermission(authToken, 54)){
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    boolean error = false;
    boolean success = false;
    String errormsg = "";

    String userid           = ParamUtil.getParameter(request,"userid");
    String nickname         = ParamUtil.getParameter(request,"username");
    String email            = ParamUtil.getParameter(request,"email");
    String phone            = ParamUtil.getParameter(request,"telephone");
    String mobilephone      = ParamUtil.getParameter(request,"mobilphone");
    String password         = ParamUtil.getParameter(request,"passwd");
    boolean doCreate        = ParamUtil.getBooleanParameter(request,"doCreate");
    List clist	            = ParamUtil.getParameterValues(request,"columnList");
    int siteid              = 1;

    if( doCreate )
    {
        if( email == null || userid == null || password == null )
        {
            error = true;
        }
    }

    if( !error && doCreate )
    {
	IUserManager   userMgr  = UserPeer.getInstance();

        try
        {
          boolean IsExit = userMgr.existUser(userid,siteid);
          if (!IsExit)
          {
	    //增加新用户
            User newUser = new User();

            newUser.setID( userid );
            newUser.setSiteid(siteid);
            newUser.setPassword( password );
            newUser.setNickName( nickname);
            newUser.setEmail( email);
            newUser.setPhone( phone);
            newUser.setMobilePhone(mobilephone);
            //newUser.setColumnID(clist);

            userMgr.create(newUser,authToken.getUserID());
          }
          else
          {
             errormsg = "<br><font color=red><b>对不起，该用户名已存在!</b></font>";
             error = true;
             success = true;
          }
        }
        catch(CmsException ue)
        {
            success = false;
            error = true;
        }
    }

%>

<html>
<head>
<title>创建新用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script LANGUAGE="JavaScript" SRC="../js/check.js">
</script>

<SCRIPT LANGUAGE=javascript>

function checkinput(form)
{

  	if (!InputValid(form.userid, 1, "string", 0, 0, 0,"用户标识符"))
			return (false);

        if (!validpassword( form.passwd.value, form.confirm.value ))
			return (false);

  	if (!InputValid(form.email, 1, "email", 0, 0, 0,"电子邮件"))
			return (false);

  	if (!InputValid(form.username, 1, "string", 1, 2, 12,"用户姓名"))
			return (false);

  	if (!InputValid(form.telephone, 0, "fax", 1, 0, 11,"用户电话"))
			return (false);

  	if (!InputValid(form.mobilphone, 0, "fax", 1, 0, 11,"用户手机"))
			return (false);
    return true;
}

function window_close() {
	parent.window.close();
}

function submit_end() {
  <% if( !error && doCreate ) {%>
    parent.window.opener.location.href="index.jsp?msg=用户创建成功";
    parent.window.close();
  <%}%>

}

function delElement()
{
  var el = newitem.columnList;
  for(var i = el.options.length-1; i >=0; i--)
  {
    if(el.options[i].selected == true)
    {
         el.options[i] = null;
    }
  }
}
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" onload="javascript:submit_end();">
<div align="center">
<form name="newitem" method="post"  onsubmit="return checkinput(this);">
<input type="hidden" name="doCreate" value="true">
    <div align="left">
      <table width="100%" border="1" bgcolor="#CCCCCC" height="300">
        <tr>
          <td width="30%" align=right><font size="2">用户标识符：</font></td>
          <td colspan="3">&nbsp;<input type="text" name="userid" value="<%=((userid==null)?"":userid)%>">
          </td>
        </tr>
        <tr>
          <td width="30%" align=right height="25"><font size="2">密码：</font></td>
          <td colspan="3">&nbsp;<input type="password" name="passwd" ></td>
        </tr>
        <tr>
          <td width="30%" align=right><font size="2">确认密码：</font></td>
          <td colspan="3">&nbsp;<input type="password" name="confirm"> </td>
        </tr>
        <tr>
          <td width="30%" align=right><font size="2">电子邮件：</font></td>
          <td colspan="3">&nbsp;<input type="text" name="email"> </td>
        </tr>
        <tr>
          <td width="30%" align=right><font size="2">用户姓名：</font></td>
          <td colspan="3">&nbsp;<input type="text" name="username"> </td>
        </tr>
        <tr>
          <td width="30%" align=right><font size="2">用户电话：</font></td>
          <td colspan="3">&nbsp;<input type="text" name="telephone"> </td>
        </tr>
        <tr>
          <td width="30%" align=right><font size="2">用户手机：</font></td>
          <td colspan="3">&nbsp;<input type="text" name="mobilphone"> </td>
        </tr>
        <tr>
          <td colspan="4" height="40"> <div align="center">
              <input type="submit" name="adduser" value="增加">&nbsp;&nbsp;
              <input type="button" name="cancel" value="取消" onclick="javascript:window_close()">
            </div></td>
        </tr>
      </table>
      <%=errormsg%>
    </div>
  </form>
</div>
</body>
</html>

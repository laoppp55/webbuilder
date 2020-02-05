<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.joincompany.IJoincompanyManager" %>
<%@ page import="com.bizwink.joincompany.JoincompanyPeer" %>
<%@ page import="com.bizwink.joincompany.Joincompany" %><%
   int flag= ParamUtil.getIntParameter(request,"flag",-1);
   if(flag==1)
   {
       String joinid=ParamUtil.getParameter(request,"joinid");
       String password=ParamUtil.getParameter(request,"password");
       IJoincompanyManager jpeer= JoincompanyPeer.getInstance();
       Joincompany join=null;
       join=jpeer.getJoin(joinid,password);
        if (join == null) {
            out.write("<script type=\"text/javascript\">alert('提交失败');window.location='login.jsp'</script>");
        } else {
            session.setAttribute("join",join);
            out.write("<script type=\"text/javascript\">alert('提交成功');window.location='update.jsp'</script>");
        }
       
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>网站超市--合作加盟</title>
<link href="coositecss.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" xml:space="preserve">
        function tijiao()
        {
             var joinid=document.getElementById("joinid").value;
             var password=document.getElementById("password");
             if(joinid=="")
             {
                 alert("加盟商ID不能为空");
                 return;
             }
             if(password=="")
             {
                 alert("密码不能为空")
                 return;
             }
             document.form1.action = "login.jsp";
             document.form1.submit();
        }
    </script>
</head>

<body>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="25">&nbsp;</td>
    <td width="223" align="left" valign="top"><img src="images/logo_331.jpg" width="217" height="84" vspace="10" /></td>
    <td width="261" align="left" valign="top"><img src="images/Preview_331.jpg" width="261" height="152" /></td>
    <td width="491" align="left" valign="top"><table width="465" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="30">&nbsp;</td>
        <td width="435">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td align="right" valign="middle"><a href="#" class="inde"><br />
    设为首页 |</a><a href="#" class="inde"> 加为收藏 &nbsp;</a><a href="#"> </a></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td align="left" valign="top">
            <table>
            <tr><td><a href="index.jsp"><img src="images/coosite_14.gif"  border="0" /></a></td>
                <td><a href="/webbuilder/register/about.html"><img src="images/coosite_15.gif"  border="0"/></a></td>
                <td><a href="/webbuilder/joincompany/login.jsp"><img src="images/coosite_16.gif" border="0"/></a></td>
                <td><a href="/webbuilder/register/webpubindex.jsp"><img src="images/coosite_17.gif" border="0"/></a></td>
                 <td><a href="#"><img src="images/coosite_18.gif" border="0"/></a></td>
                  <td><a href="/webbuilder/register/gettouch.html"><img src="images/coosite_19.gif" border="0"/></a></td>
                </tr>
            </table>
		</td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="688" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left" valign="top"><img src="images/Franchisee_enter_1.gif" width="688" height="79" /></td>
  </tr>
  <tr>
    <td align="left" valign="top" background="images/coosite_login_2.gif">
		<table width="688" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="54">&nbsp;</td>
			<td width="298" align="left" valign="top">
			<form id="form1" name="form1" method="post" action="">
    <input type="hidden" name="flag" value="1">
				<table width="298" border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td width="87" height="20">&nbsp;</td>
						<td width="211">&nbsp;</td>
					  </tr>
				  <tr>
					<td align="right" valign="top" class="grey_font">加盟商ID：</td>
						<td align="left" valign="middle"><label>
						 <input type="text" name="joinid" id="joinid" class="txt_inde" style="vertical-align:middle" /> 
				    </label>					</td>
				  </tr>
					  <tr>
						<td height="19"></td>
						<td></td>
					  </tr>
					  <tr>
						<td align="right" valign="top" class="grey_font">密&nbsp;&nbsp;码：</td>
						<td align="left" valign="top"><label>
					<input type="password" name="password" id="password" class="txt_inde" />	  
						</label></td>
					  </tr>
					  <tr>
						<td height="19"></td>
						<td height="35"><a href="#" class="linktitle">找回密码</a></td>
					  </tr>
					  <tr>
						<td align="left" valign="top">&nbsp;</td>
						<td><img src="images/enter_331.jpg" width="62" height="27" onclick="tijiao()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="register.jsp"><img src="images/login_331.jpg" width="62" height="27" border="0" /></a></td>
					  </tr> 
					  <tr>
						<td align="right" valign="middle" class="grey_font">&nbsp;</td>
						<td align="left" valign="middle"></td>
					  </tr>
		      </table>	</form>		
		    </td>
			<td width="336" align="left" valign="top"><br />
		    <img src="images/coosite_login_4.gif" width="268" height="156" hspace="20" /></td>
		  </tr>
	  </table>
	</td>
  </tr>
  <tr>
    <td align="left" valign="top"><img src="images/coosite_login_3.gif" width="688" height="64" /></td>
  </tr>
</table>
</body>
</html>

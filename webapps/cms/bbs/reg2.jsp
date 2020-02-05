<%@page import="java.io.*,
                java.util.*,
                java.text.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.bucid.bbs.*,
                com.bucid.register.*" contentType="text/html;charset=gbk"
%>
<%
  String userid          = ParamUtil.getParameter(request, "userid");
  String password        = ParamUtil.getParameter(request, "password");
  String email           = ParamUtil.getParameter(request, "email");
  String username        = ParamUtil.getParameter(request, "username");
  int sex                = ParamUtil.getIntParameter(request, "sex", 0);
  int birthday_year      = ParamUtil.getIntParameter(request, "birthday_year", 0);
  int birthday_month     = ParamUtil.getIntParameter(request, "birthday_month", 0);
  int birthday_day       = ParamUtil.getIntParameter(request, "birthday_day", 0);
  String phone           = ParamUtil.getParameter(request, "phone");
  String mobilephone     = ParamUtil.getParameter(request, "mobilephone");
  String address         = ParamUtil.getParameter(request, "address");
  String postcode        = ParamUtil.getParameter(request, "postcode");
  int startflag          = ParamUtil.getIntParameter(request, "startflag", -1);
	int isexist            = ParamUtil.getIntParameter(request, "isexist", -1);
  String idcard          = ParamUtil.getParameter(request, "idcard");

  String ip              = request.getRemoteAddr();
  if(startflag == 1){
    IRegisterManager registerMgr = RegisterPeer.getInstance();
		Register register = new Register();

		int id = registerMgr.getMaxUserID();
    id = id + 1;

		register.setID(id);
		register.setUserID(userid);
		register.setPassword(password);
		register.setEmail(email);
		register.setUserName(username);
		register.setSex(sex);
		register.setBirthday_year(birthday_year);
		register.setBirthday_month(birthday_month);
		register.setBirthday_day(birthday_day);
		register.setPhone(phone);
		register.setMobilePhone(mobilephone);
		register.setAddress(address);
		register.setPostCode(postcode);
		register.setIP(ip);
        register.setIDCard(idcard);

		//检查用户是否存在
		boolean existflag = registerMgr.checkUser(register);
		if(!existflag){
			response.sendRedirect("reg2.jsp?isexist=1");
			return;
		}

		//检查身份证号是否存在
		boolean existipflag = registerMgr.checkIDCard(idcard);
		if(!existipflag){
			response.sendRedirect("reg2.jsp?isexist=2");
			return;
		}

		registerMgr.createUser(register);
		session.setAttribute("UserClass","新手上路");
		session.setAttribute("userid",userid);
		response.sendRedirect("regOk.jsp");
  }
%>

<html>
<head>
<title>欢迎访问北京城建投资发展股份有限公司</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet href="images/chengjian.css" style="text/css">
<script language=javascript>
function check(){
  if((regform.userid.value == "")||(regform.userid.value == null)){
	  alert("请输入您的用户名！");
		return false;
	}

	if((regform.password.value == "")||(regform.password.value == null)){
	  alert("请输入您的密码！");
		return false;
	}

	if((regform.repassword.value == "")||(regform.repassword.value == null)){
	  alert("请输入您的确认密码！");
		return false;
	}

	if(regform.password.value != regform.repassword.value){
	  alert("您输入的密码和确认密码不正确！");
		return false;
	}

	if((regform.email.value == null)||(regform.email.value == "")){
	  alert("请输入您的电子邮件！");
		return false;
	}
	return true;
}
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#FFFFFF">
<center>
<%@include file="head.jsp"%>
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="images/space.gif" width="10" height="10"></td>
</tr>
</table>
<table width="778" border="0" cellspacing="0" cellpadding="0">
<tr>
<td>
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/dotline.gif">
<tr>
<td><img src="images/space.gif" width="3" height="3"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="tittle">
<tr>
<td><b><span class="tittle">您的位置: </span></b><span class="tittle"><a href="/bbs/index.jsp">城建论坛</a>
>> <a href="/bbs/reg1.jsp">用户注册</a></span></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/dotline.gif">
<tr>
<td><img src="images/space.gif" width="3" height="3"></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="txt">
<tr bgcolor="F1F2EC" align="center">
<form  method="post" action="reg2.jsp" name="regform">
<input type="hidden" name="startflag" value="1">
<td><b>注册信息</b> </td>
</tr>
</table>
<table cellpadding=2 width="100%" border=0>
<tbody>
<tr>
<td class=txt>会员基本信息<font
              color=#ff0000>*</font>必填</td>
</tr>
<%
  if(isexist == 1){
%>
<tr>
<td class=txt><font color=red>您注册的用户名已经存在！请重新注册！</font></td>
</tr>
<%}else if(isexist == 2){%>
<tr>
<td class=txt><font color=red>该身份证号已经注册！不能重复注册！</font></td>
</tr>
<%}%>
</tbody>
</table>
<table cellspacing=1 cellpadding=2 width="100%"
              border=0>
<tbody>
<tr bgcolor=#ffffff>
<td class=txt valign=top width="20%" height=1>您的用户名：</td>
<td class=txt valign=top width="30%" height=1>
<input size=15 name=userid>
<font color=#ff0000>* </font></td>
<td class=txt
                  height=1>注册成功后，用户名不能再修改！用户名可以是中、英文、数字，下划线及短横线中的组合。(最大长度１６字节)。
<br>
</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=25>您的密码：</td>
<td class=txt height=25>
<input type=password size=15
                  name=password>
<font color=#ff0000>* </font></td>
<td height=25>&nbsp;</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=25>请再次输入密码以确认：</td>
<td class=txt height=25>
<input type=password size=15
                  name=repassword>
<font color=#ff0000>* </font></td>
<td height=25>&nbsp;</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=25>您的email：</td>
<td class=txt height=25>
<input size=28 name=email>
<font
                  color=#ff0000>* </font></td>
<td height=25>&nbsp;</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=25>姓名：</td>
<td class=txt height=25>
<input size=14 name=username></td>
<td height=25>&nbsp;</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=25>您的性别：</td>
<td class=txt height=25>
<select name=sex>
<option value=0>男</option>
<option value=1>女</option>
</select></td>
<td height=25>&nbsp;</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=25>出生日期：</td>
<td class=txt height=25>
<input maxlength=4 size=4
                  name=birthday_year>
年
<select name=birthday_month>
<option
                    value=0 selected>选择</option>
<option value=1>1</option>
<option value=2>2</option>
<option value=3>3</option>
<option value=4>4</option>
<option value=5>5</option>
<option value=6>6</option>
<option value=7>7</option>
<option value=8>8</option>
<option value=9>9</option>
<option value=10>10</option>
<option value=11>11</option>
<option value=12>12</option>
</select>
月
<select
                  name=birthday_day>
<option value=0 selected>选择</option>
<option value=1>1</option>
<option value=2>2</option>
<option value=3>3</option>
<option value=4>4</option>
<option value=5>5</option>
<option value=6>6</option>
<option value=7>7</option>
<option value=8>8</option>
<option value=9>9</option>
<option value=10>10</option>
<option value=11>11</option>
<option value=12>12</option>
<option value=13>13</option>
<option value=14>14</option>
<option value=15>15</option>
<option value=16>16</option>
<option value=17>17</option>
<option value=18>18</option>
<option value=19>19</option>
<option value=20>20</option>
<option value=21>21</option>
<option value=22>22</option>
<option value=23>23</option>
<option value=24>24</option>
<option value=25>25</option>
<option value=26>26</option>
<option value=27>27</option>
<option value=28>28</option>
<option value=29>29</option>
<option value=30>30</option>
<option value=31>31</option>
</select>
日 </td>
<td height=25>&nbsp;</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=34>身份证号：</td>
<td class=txt height=34>
<input size=28 name=idcard>
</td>
<td class=txt height=34>请输入有效身份证号</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=34>联系电话：</td>
<td class=txt height=34>
<input size=28 name=phone>
</td>
<td class=txt height=34>能联系到您的电话号码，可填写多个，用逗号搁开。</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=25>手机：</td>
<td class=txt height=25>
<input size=18 name=mobilephone>
</td>
<td height=25>&nbsp;</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=34>家庭地址：</td>
<td class=txt height=34>
<input size=28 name=address>
</td>
<td height=34>&nbsp;</td>
</tr>
<tr bgcolor=#ffffff>
<td class=txt height=25>邮政编码：</td>
<td class=txt height=25>
<input size=13 name=postcode>
</td>
<td height=25>&nbsp;</td>
</tr>
</tbody>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="8">
<tr>
<td align="right">
<input type="submit" name="Submit" value="确定" onclick="javascript:return check();">
</td>
<td>
<input type="reset" name="Submit2" value="重填">
</td>
</tr>
</table>
</td>
</tr>
</form>
</table>
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="images/space.gif" width="25" height="25"></td>
</tr>
</table>
<%@include file="tail.jsp"%>
</center>
</body>
</html>


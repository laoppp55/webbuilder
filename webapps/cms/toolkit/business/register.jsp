<%@ page import="java.util.*,
  		 com.bizwink.server.*,
  		 com.bizwink.security.*,
  		 com.bizwink.register.*,
  		 com.bizwink.util.*"
  		 contentType="text/html;charset=gbk"
%>

<%
  boolean error = false;
  String errormsg = "";
  boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");

  String CorpName = null;
  String CorpAddr = null;
  String UserName = null;
  String TelePhone = null;
  String Fax = null;
  String Email = null;
  String Industry = null;
  String Product = null;
  String SiteName = null;
  String Memo = null;
  int pic = -1;
  int article = -1;

  if (doCreate)
  {
    CorpName = StringUtil.iso2gb(ParamUtil.getParameter(request, "CorpName"));
    CorpAddr = StringUtil.iso2gb(ParamUtil.getParameter(request, "CorpAddr"));
    UserName = StringUtil.iso2gb(ParamUtil.getParameter(request, "UserName"));
    TelePhone = StringUtil.iso2gb(ParamUtil.getParameter(request, "TelePhone"));
    Fax = StringUtil.iso2gb(ParamUtil.getParameter(request, "Fax"));
    Email = StringUtil.iso2gb(ParamUtil.getParameter(request, "Email"));
    Industry = StringUtil.iso2gb(ParamUtil.getParameter(request, "Industry"));
    Product = StringUtil.iso2gb(ParamUtil.getParameter(request, "Product"));
    SiteName = StringUtil.iso2gb(ParamUtil.getParameter(request, "SiteName"));
    Memo = StringUtil.iso2gb(ParamUtil.getParameter(request, "Memo"));
    pic = ParamUtil.getIntParameter(request, "pic", -1);
    article = ParamUtil.getIntParameter(request, "article", -1);

    if (CorpName == null || UserName == null || TelePhone == null ||
        SiteName == null || Email == null || pic == -1 || article == -1)
    {
      error = true;
      errormsg = "用户注册失败，请重新注册！";
    }
  }

  if (doCreate && !error)
  {
    try
    {
      IRegisterManager regMgr = RegisterPeer.getInstance();

      //先判断有无相同域名
      boolean isExit = regMgr.QuerySiteName(SiteName.trim());
      if (isExit)
      {
      	  errormsg = "该域名已被注册，请换其它域名！";
%>
	<script language=javascript>
	alert("<%=errormsg%>");
	history.go(-1);
	</script>
<%
      	  return;
      }

      Register register = new Register();

      register.setCorpName(CorpName);
      register.setCorpAddr(CorpAddr);
      register.setUserName(UserName);
      register.setTelePhone(TelePhone);
      register.setFax(Fax);
      register.setEmail(Email);
      register.setIndustry(Industry);
      register.setProduct(Product);
      register.setSiteName(SiteName);
      register.setMemo(Memo);
      register.setImagesDir(pic);
      register.setArticleFlag(article);
      register.setBindFlag(0);

      regMgr.create(register);

      errormsg = "您已成功注册！请等待我们的确认，如成功后，将会发送用户名和密码到您的信箱，请您耐心等候！";
    }
    catch (Exception e)
    {
      e.printStackTrace();
      errormsg = "用户注册失败，请重新注册！";
    }
  }

  if (doCreate)
  {
%>
<script language=javascript>
alert("<%=errormsg%>");
window.location = "index.jsp";
</script>
<%
    return;
  }
%>

<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=style/global.css>
<title>在线注册</title>
<script language=javascript>
function Form_Check()
{
    if (document.RegForm.CorpName.value == "")
    {
    	alert("企业名称不能为空！");
    	return false;
    }
    else if (document.RegForm.UserName.value == "")
    {
    	alert("联系人姓名不能为空！");
    	return false;
    }
    else if (document.RegForm.TelePhone.value == "")
    {
    	alert("联系人电话不能为空！");
    	return false;
    }
    else if (document.RegForm.Email.value == "")
    {
    	alert("电子邮件地址不能为空！");
    	return false;
    }
    else if (document.RegForm.SiteName.value == "")
    {
    	alert("域名不能为空！");
    	return false;
    }
    else if (document.RegForm.pic[0].value == "" && document.RegForm.pic[1].value == "")
    {
    	alert("图像存储方式不能为空！");
    	return false;
    }
    else if (document.RegForm.article[0].value == "" && document.RegForm.article[1].value == "")
    {
    	alert("文章显示方式不能为空！");
    	return false;
    }
    else
    {
    	return true;
    }
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" id="all" leftmargin="10" topmargin="10" marginwidth="10" marginheight="10" link="#000020" vlink="#000020" alink="#000020">
<br><p align="center"><b><font face="黑体" size="4">在&nbsp; 线&nbsp; 注&nbsp;
册</font></b></p>
<div align="center">
  <center>

<form method="POST" action="register.jsp" name="RegForm" onsubmit="javascript:return Form_Check();">
<input type=hidden name="doCreate" value="true">
<table border="1" width="75%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00 height="446">
  <tr>
    <td width="100%" bgcolor="#006699" align="right" height="20" colspan="2">
      <p align="center"><font color="#FFFFFF"><b>带 * 号项必须填写</b></font></td>
  </tr>
<!--
  <tr>
    <td width="21%" bgcolor="#EFEFEF" align="right" height="30"><b>用户名：</b></td>
    <td width="79%" bgcolor="#EFEFEF" height="30">&nbsp;<input type="text" name="userid" size="15" style="border-style: solid; border-width: 1">
      * (用于登录的帐号，由字母和数字组成)</td>
  </tr>
  <tr>
    <td width="21%" align="right" height="30"><b>密&nbsp; 码：</b></td>
    <td width="79%" height="30">&nbsp;<input type="password" name="password1" size="15" style="border-style: solid; border-width: 1">
      * (用于登录的密码，最好6位以上)</td>
  </tr>
  <tr>
    <td width="21%" bgcolor="#EFEFEF" align="right" height="30"><b>确认密码：</b></td>
    <td width="79%" bgcolor="#EFEFEF" height="30">&nbsp;<input type="password" name="password2" size="15" style="border-style: solid; border-width: 1">
      *</td>
  </tr>
-->
  <tr>
    <td width="21%" align="right" height="30"><b>企业名称：</b></td>
    <td width="79%" height="30">&nbsp;<input maxlength="40" type="text" name="CorpName" size="35" style="border-style: solid; border-width: 1">
      *</td>
  </tr>
  <tr>
    <td width="21%" bgcolor="#EFEFEF" align="right" height="30"><b>地&nbsp;
      址：</b></td>
    <td width="79%" bgcolor="#EFEFEF" height="30">&nbsp;<input maxlength="50" type="text" name="CorpAddr" size="50" style="border-style: solid; border-width: 1"></td>
  </tr>
  <tr>
    <td width="21%" align="right" height="30"><b>联系人：</b></td>
    <td width="79%" height="30">&nbsp;<input maxlength="10" type="text" name="UserName" size="15" style="border-style: solid; border-width: 1">
      *</td>
  </tr>
  <tr>
    <td width="21%" bgcolor="#EFEFEF" align="right" height="30"><b>电&nbsp;
      话：</b></td>
    <td width="79%" bgcolor="#EFEFEF" height="30">&nbsp;<input maxlength="20" type="text" name="TelePhone" size="20" style="border-style: solid; border-width: 1">
      *</td>
  </tr>
  <tr>
    <td width="21%" align="right" height="30"><b>传&nbsp; 真：</b></td>
    <td width="79%" height="30">&nbsp;<input maxlength="20" type="text" name="Fax" size="20" style="border-style: solid; border-width: 1"></td>
  </tr>
  <tr>
    <td width="21%" bgcolor="#EFEFEF" align="right" height="30"><b>电子邮件：</b></td>
    <td width="79%" bgcolor="#EFEFEF" height="30">&nbsp;<input maxlength="40" type="text" name="Email" size="35" style="border-style: solid; border-width: 1">
      * <font color=red>(用户名和密码将发送到该邮箱，请正确填写邮件地址)</font></td>
  </tr>
  <tr>
    <td width="21%" align="right" height="30"><b>行&nbsp; 业：</b></td>
    <td width="79%" height="30">&nbsp;<select name="Industry" size="1">
<OPTION value=notselect>--请选择行业---</OPTION>
<OPTION VALUE=信息技术和互联网(计算机软硬件,通讯)>信息技术和互联网(计算机软硬件,通讯)</OPTION>
<OPTION VALUE=电子技术>电子技术</OPTION>
<OPTION VALUE=金融(银行,风险基金)>金融(银行,风险基金)</OPTION>
<OPTION VALUE=贸易>贸易</OPTION>
<OPTION VALUE=快速消费品(食品,饮料,化妆品)>快速消费品(食品,饮料,化妆品)</OPTION>
<OPTION VALUE=耐用消费品(服装,纺织,家具,家电,工艺品)>耐用消费品(服装,纺织,家具,家电,工艺品)</OPTION>
<OPTION VALUE=咨询业>咨询业</OPTION>
<OPTION VALUE=生物/制药/保健/医药>生物/制药/保健/医药</OPTION>
<OPTION VALUE=建筑/设计/装潢>建筑/设计/装潢</OPTION>
<OPTION VALUE=旅游业>旅游业</OPTION>
<OPTION VALUE=酒店/餐饮>酒店/餐饮</OPTION>
<OPTION VALUE=广告业>广告业</OPTION>
<OPTION VALUE=媒体/出版>媒体/出版</OPTION>
<OPTION VALUE=加工/制造(工业自动化,设备,零部件)>加工/制造(工业自动化,设备,零部件)</OPTION>
<OPTION VALUE=印刷/包装>印刷/包装</OPTION>
<OPTION VALUE=采掘业/冶炼>采掘业/冶炼</OPTION>
<OPTION VALUE=娱乐/体育>娱乐/体育</OPTION>
<OPTION VALUE=法律>法律</OPTION>
<OPTION VALUE=化工/能源>化工/能源</OPTION>
<OPTION VALUE=环保>环保</OPTION>
<OPTION VALUE=交通/运输/物流>交通/运输/物流</OPTION>
<OPTION VALUE=批发和零售>批发和零售</OPTION>
<OPTION VALUE=教育/培训>教育/培训</OPTION>
<OPTION VALUE=学术/科研/艺术>学术/科研/艺术</OPTION>
<OPTION VALUE=保险业>保险业</OPTION>
<OPTION VALUE=房地产及中介>房地产及中介</OPTION>
<OPTION VALUE=服务业>服务业</OPTION>
<OPTION VALUE=非盈利机构/政府>非盈利机构/政府</OPTION>
<OPTION VALUE=农业/渔业/林业>农业/渔业/林业</OPTION>
<OPTION VALUE=其他行业>其他行业</OPTION>
</select>
</td>
  </tr>
  <tr>
    <td width="21%" bgcolor="#EFEFEF" align="right" height="30"><b>产&nbsp;
      品：</b></td>
    <td width="79%" bgcolor="#EFEFEF" height="30">&nbsp;<input maxlength="100" type="text" name="Product" size="50" style="border-style: solid; border-width: 1"></td>
  </tr>
  <tr>
    <td width="21%" align="right" height="30"><b>域&nbsp; 名：</b></td>
    <td width="79%" height="30">&nbsp;<input maxlength="80" type="text" name="SiteName" size="50" style="border-style: solid; border-width: 1">
      *</td>
  </tr>
  <tr>
    <td width="21%" align="right" height="80" bgcolor="#EFEFEF"><b>介&nbsp;   绍：</b></td>
    <td width="79%" height="80" bgcolor="#EFEFEF">&nbsp;<textarea rows="4" name="Memo" cols="49" style="border-style: solid; border-width: 1"></textarea>&nbsp;</td>
  </tr>
  <tr>
    <td width="21%" align="right" height="30"><b>图像存储方式：</b></td>
    <td width="79%" height="30"><input type="radio" value="0" name="pic" checked>站点根目录images下&nbsp;
      <input type="radio" value="1" name="pic">各栏目images下 *</td>
  </tr>
  <tr>
    <td width="21%" bgcolor="#EFEFEF" align="right" height="30"><b>文章显示方式：</b></td>
    <td width="79%" bgcolor="#EFEFEF" height="30"><input type="radio" value="0" name="article" checked>不显示子栏目的文章&nbsp;
      <input type="radio" value="1" name="article">显示所有子栏目的文章
      *</td>
  </tr>
</table>
<p><input type="submit" value="注  册" name="Register">&nbsp;&nbsp;&nbsp;
   <input type="button" value="取  消" name="Reset" onclick="history.go(-1)"></p>
</form>

  </center>
</div>

</body>
</html>

<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.ParsePosition" %>
<%
	//获取当前系统时间  用于计算距离回答结束还有多久
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date systemDate = new Date();
	ParsePosition pos1 = new ParsePosition(0);
	ParsePosition pos2 = new ParsePosition(0);
	String SystemDate = sdf.format(systemDate);
	Date systemdate = sdf.parse(SystemDate, pos1);

	User user = (User) session.getAttribute("user");
	int userType = 1;
	int uID = 0;
	if (user == null) {
		userType = 0;
	} else {
		uID = user.getUserid();
	}
	wenbaImpl firstcolumn = null, sc = null;
	int classid = ParamUtil.getIntParameter(request, "cid", 0);
	int locationid = ParamUtil.getIntParameter(request, "localid", 0);

	int id = Integer.parseInt((String) request.getParameter("id"));
	String fenLei = (String) request.getParameter("fenlei");
	
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	iwenba.Pageview(id);//点击率
	wenti wentiflag = iwenba.getQuestion(id);
	List list = iwenba.getCname();
	String Pro = (String) request.getParameter("pro");
	String sousuo = (String) request.getParameter("sss");
	List top8_wenti = iwenba.getTop8QuestionsWenti(classid);
	List top8_wenti_xuanshang = iwenba
			.getTop8QuestionsXuanshang(classid);
	List top10_wenti = iwenba.getTop10Questions(classid, 0);
	List top10_wenti_jiejue = iwenba.getTop10Questions(classid, 1);
	List top10_wenti_0anwser = iwenba.getTop10Questions0Answer(classid);
	wenti wt = new wenti();
	wt = (wenti)iwenba.getQuestion(id);

	String wtdate="0";
	wtdate = wt.getCreatedate().toString();
	System.out.println("null pointer------------------"+wtdate+"-------------------------");
	int wtposi = wtdate.indexOf(" ");
	wtdate = wtdate.substring(0, wtposi);
	//获取提问时间用于计算距离回答还有多少天
	Date cDate = sdf.parse(wtdate, pos2);
	long day = systemdate.getTime() - cDate.getTime();
	String days = String.valueOf((day / (24 * 60 * 60 * 1000)));
	//设置回答期限为30天
	int szts = 30;
	//计算距离回答结束的天数
	int dates = szts - Integer.parseInt(days);
	//当距离回答结束的天数为0是  选取得票数最多的回答为最佳答案

	if (dates <= 0) {
		int result = 0;
		result = iwenba.wentiguoqi(id);//判断问题是否过期
		//System.out.println("------------------------过期判断："+result);
		//必须先判断提问是否有回答 如果有得票最多为最佳答案 如果没有答案 给问题设置为过期、
		//iwenba.changeQuestionStatus(id);
		//if(wt.getAnwsernum()==0){
		//	iwenba.changeanwStatus_wenti(id);
		//}else{
		//	int aid = iwenba.getOneansid(id).getId();
		//	iwenba.changeAnwStatus(aid);//选择得票数最多的答案为最佳
			
		//}
	}
	String qid = String.valueOf(wt.getId());
	answer answer_stat;
	answer_stat = (answer) iwenba.getAnwserCon_zuijia(qid);
	String con = answer_stat.getAnwser();
	
	List dianjilist = iwenba.getTOP8DianJiShu(classid);
    List gradelist = iwenba.getTop8weekgrade();
%>
<%
	String linghuida = "零回答问题";
	linghuida = URLEncoder.encode(linghuida);
	String jiejue = "最新解决问题";
	jiejue = URLEncoder.encode(jiejue);
	String lanmu = "最新提出问题";
	lanmu = URLEncoder.encode(lanmu);
%>
<%
	//问题答案分页
	int intRowCount = 0; //总的记录数
	int intPageCount = 0; //总的页数
	int intPageSize = 5; //每页显示的记录数
	int intPage; //当前显示页 
	int begin_no = 0; //开始的rownum记录号
	int end_no = 0; //结束的rownum记录号
	String strPage = request.getParameter("page"); //取当前显示页码 
	if (strPage == null || strPage.equals(null)) {
		intPage = 1;
	} else {
		intPage = java.lang.Integer.parseInt(strPage);
		if (intPage < 1)
			intPage = 1;
	}
	begin_no = (intPage - 1) * intPageSize + 1;
	end_no = intPage * intPageSize;
	List pagelist = iwenba.getAnwserConnum(id);
	intRowCount = pagelist.size();
	intPageCount = (intRowCount + intPageSize - 1) / intPageSize; //计算总共要分多少页
	if (intPage > intPageCount) {
		intPage = intPageCount; //调整待显示的页码 
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>中国劳动法务网--问吧</title>
<link href="/images/css.css" rel="stylesheet" type="text/css"/>
<link href="/webbuilder/sites/www_fawu_com/images/hr-low.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">
<!--
function vote(){
	//window.open("http://www.hrlaw.com.cn/uermanager/regist/zhuce.jsp");
	alert("您还没有登陆，请登陆。");
}
function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}

function wenti_fenlei(){
	var fenleis = document.getElementById("wentifenlei").value;
	var url = "wenba_fenlei.jsp?sss=" +fenleis;
	window.location.href=url; 
}

function divchange(){
	var skey = document.getElementById("sss").value;
	var FenLei = document.getElementById("fenlei").value;
	var FanWei = document.getElementById("fanwei").value;
	var url = "wenba_fenlei.jsp?cid="+ FenLei + "&sss=" +skey +"&fanwei=" +FanWei;
	if(skey == ""){
		alert("请输入搜索关键字");
		return false;
	}
	if(FenLei == ""){
		alert("请选择分类");
		return false;
	}
	if(FanWei == ""){
		alert("请选择范围")
		return false;
	}
	window.location.href=url; 
}

function selectfenlei(){
	var CID = document.getElementById("fenlei").value;
	var url = "wenba.jsp?cid=" +escape(CID);
	window.location.href=url;
}

function wenti(){
    var types = <%=userType%>
	if(types==0){
		alert("您没有登陆不能回答，请登陆。");
		return ;
	}else{
		window.open("/wenba/wenba_woyaowen.jsp?cid=<%=classid%>");
	}
}
function top(){
	window.history.go();
}

function tijiaohuida(){
	var url = "/wenba/wenba_huida.jsp";
	window.location.href=url;
}

function changNone(){
	document.getElementById("finsh_z").style.display="block";
	document.getElementById("finsh_jie").style.display="none";
	document.getElementById("finsh_0").style.display="none";
	document.getElementById("zx_finsh").background="/images/wenbazuizhongye_r3_c3.jpg";
	document.getElementById("jj_finsh").background="/images/wenbazuizhongye_r3_c5.jpg";
	document.getElementById("0a_finsh").background="/images/wenbazuizhongye_r3_c7.jpg";
}

function changNone1(){
	document.getElementById("finsh_z").style.display="none";
	document.getElementById("finsh_jie").style.display="block";
	document.getElementById("finsh_0").style.display="none";
	document.getElementById("zx_finsh").background="/images/wenbazuizhongye_r3_c3_1.jpg";
	document.getElementById("jj_finsh").background="/images/wenbazuizhongye_r3_c5_1.jpg";
	document.getElementById("0a_finsh").background="/images/wenbazuizhongye_r3_c7.jpg";
}

function changNone2(){
	document.getElementById("finsh_z").style.display="none";
	document.getElementById("finsh_jie").style.display="none";
	document.getElementById("finsh_0").style.display="block";
	document.getElementById("zx_finsh").background="/images/wenbazuizhongye_r3_c3_1.jpg";
	document.getElementById("jj_finsh").background="/images/wenbazuizhongye_r3_c5.jpg";
	document.getElementById("0a_finsh").background="/images/wenbazuizhongye_r3_c7_1.jpg";
}

function checkzhuanjia(){
	var userid = <%=uID%>;
	var questionuid = <%=wentiflag.getUserid_hd()%>;
	if(userid==0){
		alert('您还没有登陆，请先登陆后再进行此操作！');
		return false;
	}
	if(userid==questionuid||questionuid==0){
		document.form1.action = "/wenba/wenba_huida.jsp?id=<%=wt.getId()%>&cid=<%=classid%>&fenlei=<%=URLEncoder.encode(fenLei)%>";
		document.form1.submit();
	}else{
			alert('对不起，您不能回答此问题！');
			return false;
	}
}
function nextquestion(){
	document.form1.action = "doNextQuestion.jsp?id=<%=id%>&fenlei=<%=URLEncoder.encode(fenLei)%>";
	document.form1.submit();
}
//-->
</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
.STYLE2 {
	font-size: medium;
	font-family: "新宋体";
	font-weight: bold;
}
.STYLE3 {color: #FF0066}
-->
</style>
</head>

<body>
<form name="form1" method="post">
<%@ include file="/include/laodongtop.shtml" %>
<%@ include file="/include/wenbacitou.shtml" %>
<table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/images/bai.gif" width="1" height="8" /></td>
  </tr>
</table>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top"><%@ include file="/include/wenbaZJL.jsp"%>
      <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="/images/bai.gif" width="1" height="10" /></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="225" valign="top">
		<form name="sform" method="post"> 
		  	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="huibian">
            <tr>
              <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="22" bgcolor="#BDD6DB" class="blackc12">&nbsp;&nbsp;问答分类</td>
                </tr>
                <tr>
                  <td bgcolor="#F1F5F6"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td><img src="/images/bai.gif" width="1" height="5" /></td>
                    </tr>
                  </table>
                    <table width="190" border="0" align="center" cellpadding="0" cellspacing="0">
                     <%
                     	int extra = list.size() % 2;
                     	for (int i = 0; i < list.size() / 2; i++) {
                     		firstcolumn = (wenbaImpl) list.get(2 * i);
                     		sc = (wenbaImpl) list.get(2 * i + 1);
                     %>
                    <tr>
                      <td width="95" height="25"><img src="/images/wen_ba_r14_c4.jpg" width="3" height="3" align="absmiddle" />&nbsp;&nbsp;<a href="doZXTW.jsp?fenlei=<%=firstcolumn.getID()%>"><%=firstcolumn.getCName()%></a></td>
                      <td width="95"><img src="/images/wen_ba_r14_c4.jpg" width="3" height="3" align="absmiddle" />&nbsp;&nbsp;<a href="doZXTW.jsp?fenlei=<%=sc.getID()%>"><%=sc.getCName()%></a></td>
                    </tr>
                    <%
                    	}
                    %>
                     <%
                     	if (extra > 0) {
                     		firstcolumn = (wenbaImpl) list.get(list.size() - 1);
                     %>
                    <tr>
                      <td width="95" height="25"><img src="/images/wen_ba_r14_c4.jpg" width="3" height="3" align="absmiddle" />&nbsp;&nbsp;<a href="doZXTW.jsp?fenlei=<%=firstcolumn.getID()%>"><%=firstcolumn.getCName()%></a></td>
                      <td width="95">&nbsp;&nbsp;</td>
                    </tr>
                     <%
                     	}
                     %>
                  </table>
                    <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td><img src="/images/bai.gif" width="1" height="5" /></td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="22" bgcolor="#BDD6DB" class="blackc12">&nbsp;&nbsp;问题搜索</td>
                </tr>
                <tr>
                  <td bgcolor="#F1F5F6"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td><img src="/images/bai.gif" width="1" height="5" /></td>
                      </tr>
                    </table>
                      <table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="49" height="25"><img src="/images/wen_ba_r18_c3.jpg" width="49" height="19" align="absmiddle" /></td>
                          <td>&nbsp;<select name="fenlei" id="" style="font-size:12px;color:#000000;width: 130px;border:#d1d1d1 1px solid;">
										<option >--请选择--</option>
										<%
											for (int i = 0; i < list.size(); i++) {
												firstcolumn = (wenbaImpl) list.get(i);
										%>
										<option value="<%=firstcolumn.getID()%>"><%=StringUtil.gb2iso4View(firstcolumn.getCName())%></option>
											<%
												}
											%>
                          			</select></td>
                        </tr>
                        <tr>
                          <td width="49" height="25"><img src="/images/wen_ba_r20_c3.jpg" width="49" height="19" align="absmiddle" /></td>
                          <td>&nbsp;<select name="fanwei" style="font-size:12px;color:#000000;width: 130px;border:#d1d1d1 1px solid;">
						  				<option value="">-请选择范围-</option>
						  				<option value="zuixinwenti">最新提出问题</option>
										<option value="jiejiewenti">最新解决问题</option>
										<option value="0answenti">零回答问题</option>
						  			</select>
                          </td>
                        </tr>
                        <tr>
                          <td width="49" height="25"><img src="/images/wen_ba_r22_c3.jpg" width="49" height="19" align="absmiddle" /></td>
                          <td>&nbsp;<input type="text" name="sss" style="font-size:12px;color:#000000;width: 130px;border:#d1d1d1 1px solid;" /></td>
                        </tr>
                      </table>
                      <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td><img src="/images/bai.gif" width="1" height="5" /></td>
                        </tr>
                    </table></td>
                </tr>
              </table></td>
            </tr>
			<tr>
			<td height="40" align="right" bgcolor="#F1F5F6"><a href="#" onclick="divchange()"><img src="/images/wen_ba_r24_c12.jpg" width="41" height="23" border="0" align="absbottom" /></a>&nbsp;&nbsp;</td>
			</tr>
          </table>
		  </form>   
		  	<table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="/images/bai.gif" width="1" height="13" /></td>
              </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="44" background="/images/wenba_r2_c2.jpg" class="blackc12">&nbsp;&nbsp;<img src="/images/diqiu.gif" width="30" height="31" align="absbottom" />&nbsp;地方问答</td>
              </tr>
              <tr>
                <td valign="top" background="/images/wen_ba_r26_c2.jpg"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><img src="/images/bai.gif" width="1" height="5" /></td>
                  </tr>
                </table>
                  <table width="213" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td valign="top"><img src="/images/wen_ba_r28_c3.jpg" width="213" height="15" /></td>
                    </tr>
                    <tr>
                      <td valign="top" background="/images/wen_ba_r30_c31.jpg"><table width="190" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=北京">北京</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=上海" >上海</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=天津">天津</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=重庆">重庆</a></td>
                        </tr>
						<tr>
                          <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=安徽">安徽</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=福建">福建</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=甘肃">甘肃</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=广东">广东</a></td>
                        </tr>
                        <tr>
                          <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=浙江">浙江</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=云南">云南</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=新疆">新疆</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=河北">河北</a></td>
                        </tr>
                        <tr>
                          <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=河南">河南</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=黑龙江">黑龙江</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=湖北">湖北</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=湖南">湖南</a></td>
                        </tr>
                        <tr>
                          <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=内蒙古">内蒙古</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=宁夏">宁夏</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=青海">青海</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=山东">山东</a></td>
                        </tr>
                        <tr>
                          <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=山西">山西</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=陕西">陕西</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=四川">四川</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=西藏">西藏</a></td>
                        </tr>
						<tr>
                          <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=辽宁">辽宁</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=吉林">吉林</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=江苏">江苏</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=江西">江西</a></td>
                        </tr>
						<tr>
                          <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=贵州">贵州</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=广西">广西</a></td>
                          <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="dodifangZXTW.jsp?proname=海南">海南</a></td>
                          <td>&nbsp;</td>
                        </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td valign="top"><img src="/images/wen_ba_r30_c3.jpg" width="213" height="15" /></td>
                    </tr>
                  </table>
                  <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td><img src="/images/bai.gif" width="1" height="5" /></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td valign="top"><img src="/images/wen_ba_r28_c2.jpg" width="224" height="6" /></td>
              </tr>
            </table>
            <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="/images/bai.gif" width="1" height="10" /></td>
              </tr>
            </table>
          </td>
          <td valign="top" width="8"><img src="/images/bai.gif" width="8" height="1" /></td>
          <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/images/wenba_finsh_1.gif"></td>
      </tr>
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td  height="20" background="/images/wenba_finsh_2.gif" width="3"></td>
            <td background="/images/wenba_finsh_3.gif"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="blackc12c"><img src="/images/wen_ba_r26_c22.jpg" width="7" height="11" align="absmiddle" />&nbsp;<%=fenLei%></td>
                  <td align="right"><a href="javascript:nextquestion()">&nbsp;下一个问题</a></td>
                </tr>
              </table></td>
            <td width="3" background="/images/wenba_finsh_5.gif"></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><img src="/images/wenba_finsh_8.gif"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td background="/images/wenba_finsh_28.gif" width="10">&nbsp;</td>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
      <%
      	String url = wt.getPicpath();
      	int UserID = wt.getUserid();
      	
      %>
        <td height="33" valign="bottom" >&nbsp;&nbsp;&nbsp;&nbsp;<span class="STYLE2"><%=wt.getTitles()%></span></td>
      </tr>
      <tr>
        <td align="right">该问题悬赏：<%=wt.getXuanshang()%>&nbsp;<span class="STYLE1">积分</span>&nbsp;</td>
      </tr>
	  <tr>
        <td valign="top" bgcolor="#FFFFFF"><img src="/images/wen_ba_r39_c21.jpg" width="508" height="1" /></td>
      </tr>
	  <tr>
        <td height="30">&nbsp;&nbsp;&nbsp;&nbsp;问题分类：<%=wt.getCname()%>&nbsp;&nbsp;&nbsp;所属地区：<%=wt.getProvince()%>&nbsp;&nbsp;&nbsp;发布时间：<%=wtdate%>&nbsp;&nbsp;&nbsp;提问人：<%= wt.getUsername()%></td>
      </tr>
	  <tr>
        <td valign="top" bgcolor="#FFFFFF"><img src="/images/wen_ba_r39_c21.jpg" width="508" height="1" /></td>
      </tr>
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="25">&nbsp;</td>
				<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td>&nbsp;</td>
                  </tr>
                  <%
                  	if (url == null || url.equals("")) {
                  %>
				  <tr>
                    <td><%=wt.getQuestion()%></td>
                  </tr>
                  <%
                  	} else {
                  %>
                  <tr>
                    <td align="center" valign="middle"><img src="<%=url%>" height="180" width="220" /></td>
                  </tr>
				  <tr>
                    <td><%=wt.getQuestion()%></td>
                  </tr>
                  <%
                  	}
                  %>
                  <tr>
                    <td>&nbsp;</td>
                  </tr>
                </table></td>
				<td width="25">&nbsp;</td>
			  </tr>
			</table>
	   </td>
      </tr>
	  <tr>
        <td height="20">&nbsp;</td>
      </tr>
      <tr>
        <td>
		  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		    <td width="10"></td>
			<td height="24" align="right" background="/images/wenba_finsh_19.gif">
			  <%=wtdate%>&nbsp;&nbsp;
			  <%
			  	if (con != null) {
			  %>
			  该问题已解决不再接受回答！
			  <%
			  	}
			  	if(dates <= 0&&con == null){
			  %>
			  该问题已过期不再接受回答！
			 
			  <%
			  	}
			  	if(con == null&&dates > 0){
			  %>
			  距离回答结束还有<span class="STYLE3">&nbsp;<%=dates%>&nbsp;</span>天
			  <%
			  	}
			  %>
			</td>
			<td width="10"></td>
		  </table>
		</td>
      </tr>
	  <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <%
          	if (user == null) {
          		if (con != null) {
          %>
          <tr>
	            <td width="150" height="50" align="center" class="blackc12c">问&nbsp;题&nbsp;已&nbsp;解&nbsp;决</td>
	            <td>&nbsp;&nbsp;所有回答是：<%=wt.getAnwsernum()%>个</td>
	            <td width="180" align="right" valign="top">
	            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  </tr>
					</table>
				</td>
	      </tr>
	      <%
	      		}
	      		if( dates <= 0&&con==null){
	      %>
	       <tr>
	           <td width="150" height="50" align="center" class="blackc12c">问&nbsp;题&nbsp;已&nbsp;过&nbsp;期</td>
	           <td>&nbsp;&nbsp;所有回答是：<%=wt.getAnwsernum()%>个</td>
	           <td width="180" align="right" valign="top">
	             <table width="100%" border="0" cellspacing="0" cellpadding="0">
					 <tr>
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  </tr>
				 </table>
				</td>
	        </tr>
	      
	      <%
	      		} 
				if(con==null&&dates > 0) {
	      %>
          <tr>
            <td width="150" height="50" align="right" class="blackc12c">
			<img src="/images/wenba_finsh_022.gif" width="110" height="31" border="0" align="absmiddle" onclick="checkzhuanjia()" style="cursor:hand"/>
			</td>
            <td>&nbsp;&nbsp;目前回答是：<%=wt.getAnwsernum()%>个</td>
            <td width="180" align="right" valign="top">
            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  </tr>
				</table>
			</td>
          </tr>
          <%
          	}
          }else{
          		String username = user.getUsername().toString();
          		String UserName = wt.getUsername();
          		if (username == UserName || username.equals(UserName)) {
          %>
	          <tr>
	            <td width="150" height="50" align="center" class="blackc12c">您&nbsp; 的&nbsp; 提&nbsp; 问</td>
	            <td>&nbsp;&nbsp;目前回答是：<%=wt.getAnwsernum()%>个</td>
	            <td width="180" align="right" valign="top">
	            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  </tr>
					</table>
				</td>
	          </tr>
	          <%
	          	} else {
	          		if(con==null&&dates > 0){
	          %>
    
          			<tr>
			            <td width="150" height="50" align="right">
			            
			            <img src="/images/wenba_finsh_022.gif" width="110" height="31" border="0" align="absmiddle" onclick="checkzhuanjia()" style="cursor:hand"/></td>
			            <td>&nbsp;&nbsp;目前回答是：<%=wt.getAnwsernum()%>个</td>
			            <td width="180" align="right" valign="top">
			            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  <tr>
							    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							  </tr>
							</table>
						</td>
			         </tr>
          			<%}else{%>
          					
          			 <tr>
			            <td width="150" height="50" align="center" class="blackc12c">问&nbsp;题&nbsp;已&nbsp;解&nbsp;决</td>
			            <td>&nbsp;&nbsp;所有回答是：<%=wt.getAnwsernum()%>个</td>
			            <td width="180" align="right" valign="top">
			            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  <tr>
							    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							  </tr>
							</table>
						</td>
			      	  </tr>		
          				<%} %>	
          			<% 
          				}
          			%>
        
          
          			<%
                    }
                    %>  
        </table></td>
      </tr>
    </table></td>
    <td background="/images/wenba_finsh_30.gif" width="10">&nbsp;</td>
  </tr>
</table>
</td>
  </tr>
  <tr>
    <td><img src="/images/wenba_finsh_032.gif" width="530" height="12"/></td>
  </tr>
</table>
<table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="/images/bai.gif" width="1" height="13" /></td>
              </tr>
            </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" bgcolor="#E9E9E9"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><img src="/images/bai.gif" width="1" height="10" /></td>
                  </tr>
                </table>
                  <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td valign="top" bgcolor="#FFFFFF">
                     
					  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  	 <%
					  	 	if (con != null) {
					  	 %>
			                  <tr>
								<td height="25" valign="middle">&nbsp;&nbsp;<strong>最佳回答</strong></td>
							  </tr>
							  <tr>
								<td valign="top" bgcolor="#FFFFFF"><img src="/images/wen_ba_r39_c21.jpg" width="508" height="1" /></td>
							  </tr>
							  
		                      <tr>
								<td height="20" valign="middle">&nbsp;&nbsp;</td>
							  </tr>
							  <tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="15">&nbsp;</td>
											<td><%=con%></td>
											<td width="120" align="right" valign="top"><img src="/images/finsh_3.gif" width="81" height="48" border="0" align="absmiddle" />&nbsp;&nbsp;</td>
									    </tr>
								    </table>
							    </td>
							  </tr> 
							  <tr>
							    <td>&nbsp;</td>
							  </tr>
							  <tr>
								<td height="20" valign="middle">&nbsp;&nbsp;&nbsp;回答时间:<%=answer_stat.getCreatedate()%>&nbsp;&nbsp;&nbsp;回答者:<%= answer_stat.getUsername()%></td>
							  </tr>
							  <tr>
								<td valign="top" bgcolor="#FFFFFF"><img src="/images/wen_ba_r39_c21.jpg" width="508" height="1" /></td>
							  </tr> 
						  <%}%>    
						  <%if (con != null) {%>
						  <tr>
							<td height="30" valign="middle">&nbsp;&nbsp;<strong>其他回答</strong>(<%=wt.getAnwsernum() - 1%>)</td>
						  </tr>
						  <%} else { %>
						  <tr>
							<td height="25" valign="middle">&nbsp;&nbsp;<strong>目前回答</strong>(<%=wt.getAnwsernum()%>)</td>
						  </tr>
						  <%}%>
						  <tr>
							<td valign="top" bgcolor="#FFFFFF"><img src="/images/wen_ba_r39_c21.jpg" width="508" height="1" /></td>
						  </tr>
						  <%
						  	List answerlist = new ArrayList();
						  	answer ans = null;
						  	answerlist = iwenba.getAnwserCon(end_no, begin_no, qid);
						  	if (wt.getAnwsernum() == 0) {
						  %>
						  <tr>
						  	<td height="30" valign="middle">&nbsp;&nbsp;目前没有回答!</td>
						  </tr>
						  <%
						  	} else {
						  %>
						  <%
						  	for (int i = 0; i < answerlist.size(); i++) {
						  			ans = (answer) answerlist.get(i);
						  			ans.getUsername();
						  			String url_ans = ans.getPicpath();
						  %>
						  <tr>
							<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
								  <tr>
									<td height="10"></td>
								  </tr>
								  <tr>
									<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
									  <tr>
                                        <td width="15">&nbsp;</td>
                                        <%if (url_ans == null || url_ans.equals("")) {%>
                                        <td align="center" valign="middle">&nbsp;</td>
										<%}else{ %> 
										<td align="center" valign="middle"><img src="<%=url_ans%>" height="120" width="160" /></td>
										<%}%>   
										<td width="120" align="right" valign="top"></td>
                                      </tr>                                                                           
                                      <tr>
                                        <td width="15">&nbsp;</td>
                                        <td><%=ans.getAnwser()%></td>
                                        <%
                                        	if (con == null || con.equals("")) {
                                        		if(user == null){
                                        %>
                                        <td width="120" align="right" valign="top"><a href="#"  onclick="vote()"><img src="/images/wenba_finsh1_03.gif" width="81" height="48" border="0" align="absmiddle" /></a>&nbsp;&nbsp;<br/>
												<%}else{
													String username = "";
													String UserName = wt.getUsername();
													username = user.getUsername().toString();
													if (username == UserName|| username.equals(UserName)) {
												%>
									    <td width="120" align="right" valign="top"><a href="/wenba/wenba_status.jsp?id=<%=ans.getId()%>&cid=<%=classid%>&fenlei=<%=URLEncoder.encode(fenLei)%>&qid=<%=ans.getQid()%>&uid=<%=ans.getUserid()%>"><img src="/images/wenba_vote2.gif" width="81" height="48" border="0" align="absmiddle" /></a>&nbsp;&nbsp;</td>
									          		<%
									          			}else{
									          				if(username.equals(ans.getUsername())){
									          		%>
									    <td width="120" align="right" valign="top"><img src="/images/finsh_4.gif" width="81" height="48" border="0" align="absmiddle" />&nbsp;&nbsp;</td> 
									    			<%}else{ %>     		
									    <td width="120" align="right" valign="top"><a href="/wenba/wenba_vote.jsp?id=<%=ans.getId()%>&cid=<%=classid%>&fenlei=<%=URLEncoder.encode(fenLei)%>&qid=<%=ans.getQid()%>&uid=<%= uID%>"><img src="/images/1.gif" width="81" height="48" border="0" align="absmiddle" /></a>&nbsp;&nbsp;<br/></td>
													<%}}}} else {%>
										<td >&nbsp;&nbsp;</td>
														<%}%>
                                      </tr>  
                                    </table>
                                    </td>
								  </tr>
								  <tr>
									<td>
									  <table width="100%" border="0" cellpadding="0" cellspacing="0">
									  	<tr>
									  	  <td>&nbsp;</td>
									  	  <td>参考资料：<%=ans.getCahkaoziliao()==null?"":ans.getCahkaoziliao() %></td>
									  	</tr>
										<tr>
											<td width="15">&nbsp;</td>
											<td height="20" valign="middle">回答时间:<%=ans.getCreatedate()%>&nbsp;&nbsp;&nbsp;回答者:<%= ans.getUsername() %>:</td>
										</tr>
										  <tr>
											<td colspan="2" valign="top" bgcolor="#FFFFFF"><img src="/images/wen_ba_r39_c21.jpg" width="508" height="1" /></td>
						  				</tr> 
									  </table>
									</td>
								  </tr>
								</table>
							</td>
						  </tr>
						  <tr>
							<td height="10"  bgcolor="#FFFFFF"></td>
						  </tr>
						  <%
						  	}
						  %>
						  <tr>
							<td align="right" bgcolor="#FFFFFF">
								<%
									if (intRowCount > intPageSize) {
								%>
								<!-- 共<%=intPageCount%>页 -->
								<%
									if (intPage > 1) {
								%>
								<a href="wenba_finsh.jsp?page=<%=intPage - 1%>&cid=<%=classid%>&id=<%=id%>&fenlei=<%=fenLei%>"><img src="/images/wenba_woyaoda_r9_c12.jpg" width="4" height="7" border="0" align="absmiddle" /></a>
	                    		<%
	                    			}
	                    		%>
	                    		<a href="wenba_finsh.jsp?page=1&cid=<%=classid%>&id=<%=id%>&fenlei=<%=fenLei%>">首页</a>
	                    		<%
	                    			if (intPage > 1) {
	                    		%>
	                    		<a href="wenba_finsh.jsp?page=<%=intPage - 1%>&cid=<%=classid%>&id=<%=id%>&fenlei=<%=fenLei%>">上一页</a><%
	                    			}
	                    		%>
	                    		<%
	                    			if (intPage < intPageCount) {
	                    		%>
	                    		<a href="wenba_finsh.jsp?page=<%=intPage + 1%>&cid=<%=classid%>&id=<%=id%>&fenlei=<%=fenLei%>">下一页</a><%
	                    			}
	                    		%>
	                            <a href="wenba_finsh.jsp?page=<%=intPageCount%>&cid=<%=classid%>&id=<%=id%>&fenlei=<%=fenLei%>">尾页</a>
	                            <%
	                            	if (intPage < intPageCount) {
	                            %>
								<a href="wenba_finsh.jsp?page=<%=intPage + 1%>&cid=<%=classid%>&id=<%=id%>&fenlei=<%=fenLei%>"><img src="/images/wenba_woyaoda_r9_c14.jpg" width="4" height="7" border="0" align="absmiddle" /></a><%
									}
								%>
								<%
									}
								%>							&nbsp;&nbsp;&nbsp;&nbsp;</td>
						  </tr>
						  <%
						  	}
						  %>
						</table>
					  </td>
                    </tr>
                    <tr>
                      <td valign="top" bgcolor="#FFFFFF">
					  

					  </td>
                    </tr>
                  </table>
                  <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td><img src="/images/bai.gif" width="1" height="10" /></td>
                    </tr>
                  </table></td>
              </tr>
            </table> 
			<table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="/images/bai.gif" width="1" height="13" /></td>
              </tr>
            </table>
</td>
          </tr>
      </table>
    </td>
    <td width="10" valign="top"><img src="/images/bai.gif" width="10" height="1" /></td>
    <td width="210" valign="top">
	<%@ include file="/include/liulei.shtml" %>
      <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="/images/bai.gif" width="1" height="10" /></td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="huibian1">
        <tr>
          <td valign="top" background="/images/wen_ba_r18_c33.jpg"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="/images/bai.gif" width="1" height="5" /></td>
            </tr>
          </table>
            <table width="180" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="25"><img src="/images/wen_ba_r19_c34.jpg" width="15" height="14" align="absmiddle" />&nbsp;<a href="#">如何提问？</a></td>
              </tr>
              <tr>
                <td height="25"><img src="/images/wen_ba_r21_c35.jpg" width="15" height="14" align="absmiddle" />&nbsp;<a href="#">如何回答？</a></td>
              </tr>
              <tr>
                <td height="25"><img src="/images/wen_ba_r23_c35.jpg" width="15" height="14" align="absmiddle" />&nbsp;<a href="#">什么是专家解疑？</a></td>
              </tr>
              <tr>
                <td height="25"><img src="/images/wen_ba_r25_c35.jpg" width="15" height="14" align="absmiddle" />&nbsp;<a href="#">规范/积分规则是什么？</a></td>
              </tr>
            </table>
            <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="/images/bai.gif" width="1" height="5" /></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="/images/bai.gif" width="1" height="10" /></td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="huibian1">
        <tr>
          <td height="24" background="/images/wen_ba_r30_c34.jpg"><a href="#" class="blackc12">&nbsp;本周问题排行榜</a></td>
        </tr>
        <tr>
          <td height="210" valign="top" background="/images/wen_ba_r18_c33.jpg"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="/images/bai.gif" width="1" height="5" /></td>
            </tr>
          </table>
            <table width="185" border="0" align="center" cellpadding="0" cellspacing="0">
                    <%
                    	String names = "本周问题排行榜";
                    	int wt_idx = 0;
                        String str = "";
                        for(int i=0; i<dianjilist.size(); i++) {
                            WenbaBean wb = (WenbaBean)dianjilist.get(i);
                            String title = wb.getTitle();
							if(title.length()>10){
								str = title.substring(0,10) + "...";
							}else{
								str = title ;
							}     
                                                                        
                     %>
              <tr>
                <td width="15" height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" align="absmiddle" /></td>
                <td><a href="/wenba/wenba_finsh.jsp?id=<%= wb.getId()%>&fenlei=<%= names%>"><%= str%></a></td>
                <td width="30" align="center" class="black12"><%= wb.getFenshu() %></td>
              </tr>
              
			 <%} %>
            </table>
            <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="/images/bai.gif" width="1" height="5" /></td>
              </tr>
          </table></td>
        </tr>
      </table>
      <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="/images/bai.gif" width="1" height="10" /></td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="huibian1">
        <tr class="blackc12">
          <td height="24" background="/images/wen_ba_r30_c34.jpg"><a href="#" class="blackc12">&nbsp;</a><a href="#">本周积分排行榜</a></td>
        </tr>
        <tr>
          <td height="210" valign="top" background="/images/wen_ba_r18_c33.jpg"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="/images/bai.gif" width="1" height="5" /></td>
              </tr>
            </table>
              <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			  		<%
			  			String namex = "本周积分排行榜";
                        for(int i=0; i<gradelist.size(); i++) {
                            WenbaBean wb = (WenbaBean)gradelist.get(i);                   
                     %>
                <tr>
                  <td width="15" height="25" align="center"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" align="absmiddle" /></td>
                  <td class="black12"><a href="dopersonQW.jsp?personid=<%=wb.getUserid() %>"><%=wb.getUsername() %></a></td>
                  <td width="30" align="center" class="black12"><%=wb.getWeekgrade() %></td>
                </tr>
                <%} %>
              </table>
              <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img src="/images/bai.gif" width="1" height="5" /></td>
                </tr>
          </table></td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/images/bai.gif" width="1" height="13" /></td>
  </tr>
</table>
<%@ include file="/include/low.shtml"%>
</form>
</body>
</html>


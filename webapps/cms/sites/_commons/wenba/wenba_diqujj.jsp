<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*,java.text.*" %>
<%
	User user = (User)session.getAttribute("user");
	int userType = 1;
	int P_jifeng = 0;
	String userName = "";
	String userID ="";
	if(user==null){
		userType = 0;
	}else{
		P_jifeng = user.getUsergrade();
		userName = (String)user.getUsername();
		userID = String.valueOf(user.getUserid());
	}
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	wenbaImpl firstcolumn = null;
	List list = iwenba.getCname();
	int classid = ParamUtil.getIntParameter(request,"cid",0);
	String CID = (String)request.getParameter("cid");
	String Pro = (String)request.getParameter("pro");
	String key = (String)request.getParameter("keys");
	//List top8_wenti = iwenba.getTop8QuestionsWenti(classid);
	//List top8_wenti_xuanshang = iwenba.getTop8QuestionsXuanshang(classid);
	//List top10_wenti = iwenba.getTop10Questions(classid,0);
    //List top10_wenti_jiejue= iwenba.getTop10Questions(classid,1);
    //List top10_wenti_0anwser = iwenba.getTop10Questions0Answer(classid);
	String types = "diqu_jiejue";
%>
<%
	String linghuida = "零回答问题";
	linghuida = URLEncoder.encode(linghuida);
	String jiejue = "最新解决问题";
	jiejue = URLEncoder.encode(jiejue);
	String lanmu = "最新提出问题";
	lanmu = URLEncoder.encode(lanmu);
	
	String thispage = (String)session.getAttribute("difangyijjthispage")==null?"0":(String)session.getAttribute("difangyijjthispage");
	String allpages = (String)session.getAttribute("difangyijjallpages")==null?"0":(String)session.getAttribute("difangyijjallpages");
	String perpagenum = session.getAttribute("difangyijjpagenum")==null?"0":(String)session.getAttribute("difangyijjpagenum");
	List pagelist = (List)session.getAttribute("difangyijjpagelist");
	if(pagelist==null){
		response.sendRedirect("dodifangZXTW.jsp");
	}
	String str3 = (String)session.getAttribute("str3");
	
	session.setAttribute("str3",null);
	session.setAttribute("difangyijjthispage",null);
	session.setAttribute("difangyijjallpages",null);
	session.setAttribute("difangyijjpagenum",null);
	session.setAttribute("difangyijjpagelist",null);
	String provincename = "";
	String columnid = "0";
	String keyword = "";
	if(str3!=null){
	String[] strs = str3.split(",");
	
	
		if(strs.length>2){
			provincename = strs[0];
			columnid = strs[1];
			keyword = strs[2];
		}else{
			provincename = strs[0];
			columnid = strs[1];
		}
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	List prolist = iwenba.getProvince();
	List dianjilist = iwenba.getTOP8DianJiShu(classid);
    List gradelist = iwenba.getTop8weekgrade();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>地区问答</title>
<link href="/images/css.css" rel="stylesheet" type="text/css"/>
<link href="/webbuilder/sites/www_fawu_com/images/hr-low.css" rel="stylesheet" type="text/css" />

<script type="text/JavaScript">
function gonum(){
    	var gopagenum = document.form1.gopagenum.value;
    		
    	if(gopagenum==""){
    		return false;
    	}else{
    		document.form1.thispage.value = gopagenum;
    		document.form1.action = "dodifangYJJ.jsp?proname=<%=provincename %>&cid=<%=columnid %>&keys=<%=keyword %>";
    		document.form1.submit();
    	}
    }

function wenti(){
    var types = <%= userType%>
	if(types==0){
		alert("您没有登陆不能回答，请登陆。");
		return false;
	}else{
		window.open("/wenba/wenba_woyaowen.jsp?cid=<%= classid%>");
	}
}

function selectfenlei(){
	var CID = document.getElementById("fenlei").value;
	var url = "wenba.jsp?cid=" +escape(CID);
	window.location.href=url;
}

function divchange(){
	var skey = document.getElementById("sss").value;
	var url = "wenba_sousuo.jsp?cid=<%= classid%>&sss=" +skey;
	
	if(skey == ""){
		alert("请输入搜索关键字");
	}else{
			window.location.href=url; 
	}
}

function wenti_change(){
	var url = "wenba_diqu.jsp?cid=<%=classid%>&pro=<%= Pro%>&keys=<%= key%>";
	window.location.href=url;
}

//function wenti_jiejue_change(){
//	var url = "wenba_diqujj.jsp?cid=<%=classid%>&pro=<%= Pro%>&keys=<%= key%>";
//	window.location.href=url;
//}
function wenti_ans_change(){
	var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=<%= Pro%>&keys=<%= key%>";
	window.location.href=url;
}
function wenti(){
	window.open("/wenba/wenba_woyaowen.jsp?cid=<%= classid%>");
}

function difang(prov){
	switch(prov)
	{
		case '北京' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '上海' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '天津' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '重庆' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '安徽' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '福建' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '甘肃' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '广东' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '广西' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '贵州' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '海南' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '河南' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '河北' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '黑龙江' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '湖北' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '湖南' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '吉林' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '江苏' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '江西' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '辽宁' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '内蒙古' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '宁夏' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '青海' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '山东' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '山西' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '陕西' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '四川' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '云南' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '新疆' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '西藏' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '浙江' :
		var url = "dodifangYJJ.jsp?proname=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
	}
}

function sousuo_fenlei(){
	var fenleiID = document.getElementById("select_fenlei").value; 
	var keys = document.getElementById("sousuo_key").value;
	var url ="dodifangYJJ.jsp?proname=<%= provincename%>&cid=" +fenleiID +"&keys=" + keys;
	if(fenleiID==""){
		alert("请选择分类");
		return ;
	}
	window.location.href=url;
}

function shuaxin(){
	document.all.cod.src="valid.jsp";
}

function check(){
	var types = <%= userType%>
	if(types==0){
		alert("您没有登陆，请登陆。");
		return ;
	}
	if(document.all.titles.value==""){
		alert("请输入问题标题");
		return ;
	}
	if(document.all.Question.value==""){
		alert("请输入问题回答");
		return ;
	}
	if(document.all.tplFile.value!=""){
		var filename = document.all.tplFile.value;		
		var pos = filename.lastIndexOf(".");	
		var lastname = filename.substring(pos+1,filename.length);
		//alert(lastname);
		if(lastname.toLowerCase()!="jpg"&&lastname.toLowerCase()!="gif"){
			alert("图片类型不对!图片格式为JPG或GIF格式");
			return ;
		}
	}
	if(document.all.tplFile1.value!=""){
		var filename = document.all.tplFile1.value;		
		var pos = filename.lastIndexOf(".");		
		var lastname = filename.substring(pos+1,filename.length);
		//alert(lastname);
		if(lastname.toLowerCase()!="txt"&&lastname.toLowerCase()!="doc"){
			alert("文件类型不对!");
			return ;
		}
	}
	if(document.all.Select1.value=="1"){
		alert("请选择地区");
		return ;
	}
	if(document.all.Select2.value=="1"){
		alert("请选择地区");
		return ;
	}
	if(document.all.Select3.value=="1"){
		alert("请选择地区");
		return ;
	}
	if(document.all.Codes.value==""){
		alert("请输入验证码");
		return ;
	}
	if(document.all.Emailnotify.checked){
		if(document.all.emails.value == ""){
			alert("请输入邮箱地址");
			return ;
		}
	}
	document.all.form1.action = "tiwen_do.jsp";
	document.all.form1.submit();
}

function tiwenleixing(){
	var userid = document.getElementById("userId").value;
	var objXml1;
    	if (window.ActiveXObject)
        {
        	objXml1 = new ActiveXObject("Microsoft.XMLHTTP");
        }
        else if (window.XMLHttpRequest)
        {
            objXml1 = new XMLHttpRequest();
        }
        objXml1.open("POST", "/wenba/jifenyanzheng.jsp?userid="+userid, false);
        objXml1.send(null);
		var retstrs = objXml1.responseText;
		//alert(retstrs);
		if(retstrs<20){
    		document.getElementById("jifenyazheng").innerHTML = "<font color=red>"+"积分少于20无法使用悬赏提问！"+"</font>";
    	}else{
    		document.getElementById("twxs").style.display="block"; 
    	}	
}

function pttw(){
	document.getElementById("twxs").style.display="none"; 
}
</script>

<script language="javascript">
function LoadXML()
{
	var xmlDoc = new ActiveXObject("Microsoft.XMLDOM"); 
	xmlDoc.async = "false";
	xmlDoc.load("XML\\Area.xml");
	return xmlDoc;
}

function InitArea()
{
	/*获取，引用三个下拉框 */
	var dropElement1=document.getElementById("Select1"); 
    var dropElement2=document.getElementById("Select2"); 
    var dropElement3=document.getElementById("Select3"); 
	/*自定义一个方法 把传进来的对象清除	这里清除了三个下拉所有框的选项*/  
    RemoveDropDownList(dropElement1);
    RemoveDropDownList(dropElement2);
    RemoveDropDownList(dropElement3);
	
	var pOption = document.createElement("option");
	pOption.value = "";
	pOption.text = "--请选择--";
	dropElement1.add(pOption);
	
	var cOption = document.createElement("option");
	cOption.value = "";
	cOption.text = "--请选择--";
	dropElement2.add(cOption);
	
	var aOption = document.createElement("option");
	aOption.value = " ";
	aOption.text = "--请选择--";
	dropElement3.add(aOption);
	
	var xmlDoc= LoadXML();
	//var provinceNodes = xmlDoc.documentElement.childNodes[1].getAttribute("name");
	/*获取全市节点*/
	var provinceNodes = xmlDoc.documentElement.childNodes;
	//var  TopnodeList=xmlDoc.selectSingleNode("Root").childNodes;
	//j = provinceNodes.length;
	//alert(j)
	if(provinceNodes.length > 0) //provinceNodes.length = 31
	{
		var province;
		var city;
		var area;
		
		for(var i=0; i<provinceNodes.length; i++)
		{
			province = provinceNodes[i];
			var pOption = document.createElement("option");
			pOption.value = province.getAttribute("name");
			pOption.text = province.getAttribute("name");
			dropElement1.add(pOption);
		}
	}
}

function selectProvince()
{
	var dropElement1=document.getElementById("Select1"); 
    var provinceName=dropElement1.options[dropElement1.selectedIndex].text;
	var xmlDoc= LoadXML();
	var provinceNode = xmlDoc.selectSingleNode("//root/province[@name='"+provinceName+"']");  //alert(provinceNode.getAttribute("name"));
	
	/*XmlNode node = doc.SelectSingleNode("//AllNode/Node[@ID = ’aaa’]");
   在整个Xml中查找AllNode节点下的节点名为Node的节点，该子节点的ID属性值为aaa*/
   
	var dropElement2=document.getElementById("Select2"); 
    var dropElement3=document.getElementById("Select3");
	
	RemoveDropDownList(dropElement2);
    RemoveDropDownList(dropElement3);
	
	var cOption = document.createElement("option");
	cOption.value = "0";
	cOption.text = "--请选择--";
	dropElement2.add(cOption);
	
	var aOption = document.createElement("option");
	aOption.value = "0";
	aOption.text = "--请选择--";
	dropElement3.add(aOption);
	
	for(var i=0; i<provinceNode.childNodes.length; i++)
		{
			city = provinceNode.childNodes[i];
			var cOption = document.createElement("option");
			cOption.value = city.getAttribute("name");
			cOption.text = city.getAttribute("name");
			dropElement2.add(cOption);
		}


}

function selectArea()
{
	var dropElement2=document.getElementById("Select2"); 
    var cityName=dropElement2.options[dropElement2.selectedIndex].text;
	var xmlDoc= LoadXML();
	var cityNode = xmlDoc.selectSingleNode("//root/province/city[@name='"+cityName+"']");
	
    var dropElement3=document.getElementById("Select3");
    RemoveDropDownList(dropElement3);
	var aOption = document.createElement("option");
	aOption.value = "0";
	aOption.text = "--请选择--";
	dropElement3.add(aOption);
	
	for(var i=0; i<cityNode.childNodes.length; i++)
		{
			city = cityNode.childNodes[i];
			var aOption = document.createElement("option");
			aOption.value = city.getAttribute("name");
			aOption.text = city.getAttribute("name");
			dropElement3.add(aOption);
		}
}

function RemoveDropDownList(obj)
{
	if(obj)//如果参数obj不为空 则
	{
		var length=obj.options.length;
		if(length > 0)
		{
			for(var i=length; i>=0;i--)
			{
				obj.remove(i);
			}
		}
	}
}
window.onload=InitArea;
</script>
</head>

<body>
<form name="form1" method="post">
<input type="hidden" name="thispage" value="<%=thispage %>"/>
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
          <td height="30" class="black14">&nbsp;地方问答 --<%=provincename %></td>
        </tr>
        <tr>
          <td height="25" align="center" valign="bottom" bgcolor="#BDD6DB" class="blackc12c">省级行政区列表</td>
        </tr>
        <tr>
          <td valign="top" class="bian1"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="/images/bai.gif" width="1" height="10" /></td>
            </tr>
          </table>
            <table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('北京')">北京</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('上海')">上海</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('天津')">天津</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('重庆')">重庆</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('安徽')">安徽</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('福建')">福建</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('甘肃')">甘肃</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('广东')">广东</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('广西')">广西</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('贵州')">贵州</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('海南')">海南</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('河北')">河北</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('河南')">河南</a></td>
            </tr>
			<tr>
              <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('黑龙江')">黑龙江</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('湖北')">湖北</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('湖南')">湖南</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('吉林')">吉林</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('江苏')">江苏</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('江西')">江西</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('辽宁')">辽宁</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('内蒙古')">内蒙古</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('宁夏')">宁夏</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('青海')">青海</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('山东')">山东</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('山西')">山西</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('陕西')">陕西</a></td>
            </tr>
            <tr>
              <td height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('四川')">四川</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('西藏')">西藏</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('新疆')">新疆</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('云南')">云南</a></td>
              <td><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" />&nbsp;<a href="#" onclick="difang('浙江')">浙江</a></td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
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
          <td><img src="/images/bai.gif" width="1" height="10" /></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/bai.gif" width="1" height="1" /></td>
        </tr>
        <tr>
          <td height="39" background="/images/wenba_woyaoda_r2_c2.jpg">&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="select_fenlei" style="font-size:12px;color:#000000;width: 170px;border:#d1d1d1 1px solid;" >
            	<option >--请选择--</option>
			<%
				for(int i=0;i<list.size();i++){
				firstcolumn = (wenbaImpl)list.get(i);
			%>
				<option value="<%= firstcolumn.getID()%>"><%=StringUtil.gb2iso4View(firstcolumn.getCName()) %></option>
				<%}%>
            </select>&nbsp;&nbsp;<span class="unnamed">
                    <input name="sousuo_key" type="text" value="" style="font-size:12px;color:#000000;width: 170px;border:#d1d1d1 1px solid;"  />
&nbsp;                    <a href="javascript:sousuo_fenlei()" ><img src="/images/wenba_woyaoda_r3_c5.jpg" width="58" height="19" border="0" align="absmiddle" /></a></span></td>
        </tr>
      </table>
      <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="/images/bai.gif" width="1" height="10" /></td>
        </tr>
      </table>
      <table width="761" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td valign="top"><img src="/images/wenba_woyaoda_r6_c2.jpg" width="761" height="8" /></td>
        </tr>
        <tr>
          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="7" valign="top" background="/images/wenba_woyaoda_r7_c2.jpg"><img src="/images/bai.gif" width="7" height="1" /></td>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="248" height="30" align="center" background="/images/wenba_woyaoda_r7_c7.jpg" class="blackc12c"><a href="dodifangZXTW.jsp?proname=<%=provincename %>" >最新提出问题</a></td>
                            <td align="center"><img src="/images/bai.gif" width="1" height="1" /></td>
                            <td width="248" align="center" background="/images/wenba_woyaoda_r7_c5.jpg" class="blackc12c"><a href="dodifangYJJ.jsp?proname=<%=provincename %>">已解决的问题</a></td>
                            <td align="center"><img src="/images/bai.gif" width="1" height="1" /></td>
                            <td width="247" align="center" background="/images/wenba_woyaoda_r7_c10.jpg" class="blackc12c"><a href="dodifangLHD.jsp?proname=<%=provincename %>" >零回答的问题</a></td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td valign="top" background="/images/wenba_woyaoda_r8_c5.jpg"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td valign="top"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                                <tr>
                                  <td width="120" height="25" align="center" bgcolor="#ECEDEF">分类</td>
                                  <td align="center" bgcolor="#ECEDEF">标题</td>
                                  <td width="80" align="center" bgcolor="#ECEDEF">地区</td>
                                  <td width="80" align="center" bgcolor="#ECEDEF">悬赏</td>
                                  <td width="80" align="center" bgcolor="#ECEDEF">回答</td>
                                  <td width="80" align="center" bgcolor="#ECEDEF">时间</td>
                                </tr>
								<%
									
									String color1 = "#ECEDEF";
									String color2 = "#FFFFFF"; 
									String color = "";
									if(pagelist!=null){
									for(int i=0;i<pagelist.size();i++){
										WenbaBean wq = (WenbaBean)pagelist.get(i);
									                    
								%>
                                <tr>
                                  <td height="25" align="left" bgcolor="<%= color%>">&nbsp;&nbsp;[<%= wq.getCname()%>]</td>
                                  <td align="left" bgcolor="<%= color%>">
                                  	<a href="/wenba/wenba_finsh.jsp?cid=<%=wq.getColumnid()%>&id=<%=wq.getId()%>&fenlei=<%= jiejue%> ">&nbsp;&nbsp;<%= wq.getTitle()%></a>&nbsp;&nbsp;
                                  </td>         
                                  <td align="center" bgcolor="<%= color%>"><%=wq.getProvince()%></td>
                                  <td align="center" bgcolor="<%= color%>"><%=wq.getXuanshang()%></td>
                                  <td align="center" bgcolor="<%= color%>"><%=wq.getAnswernum()%></td>
                                  <td align="center" bgcolor="<%= color%>">[<%=sdf.format(wq.getCreatedate())%>]</td>
                                </tr>
                                <%
                                	}}else{
                                %>
                                <tr>
		                          <td height="25" colspan="5" align="center">对不起！没有您想找的搜索结果，您可以更换搜索条件重新搜索。</td>
		                          <td align="center"></td>
		                        </tr>
		                        <%} %>
                            </table></td>
                          </tr>
                          <tr>
        				<td width="1036" height="50" align="center" valign="bottom" class="font">
        				<%
							if (Integer.parseInt(thispage) >1){
						%>
                		<a href="dodifangYJJ.jsp?thispage=1&proname=<%=provincename %>&cid=<%=columnid %>&keys=<%=keyword %>">首页</a>&nbsp;&nbsp;&nbsp;<a href="dodifangYJJ.jsp?thispage=<%=Integer.parseInt(thispage)-1 %>&proname=<%=provincename %>&cid=<%=columnid %>&keys=<%=keyword %>">上一页</a>
                		<%
							}
							if (Integer.parseInt(allpages) > Integer.parseInt(thispage)){
							%>
              				&nbsp;&nbsp;<a href="dodifangYJJ.jsp?thispage=<%=Integer.parseInt(thispage)+1 %>&proname=<%=provincename %>&cid=<%=columnid %>&keys=<%=keyword %>">下一页</a>&nbsp;&nbsp; &nbsp;<a href="dodifangYJJ.jsp?thispage=<%=Integer.parseInt(allpages)%>&proname=<%=provincename %>&cid=<%=columnid %>&keys=<%=keyword %>">末页</a>
              				<%
							}
							%>
              				&nbsp;当前是第<%=thispage%>页  &nbsp;&nbsp;共<%=allpages%>页&nbsp;
              				到
              				<input name="gopagenum" type="text" class="txtgo" value="" size="2" />
              				页
            				<input name="gobutton" type="button" class="madgo" onClick="gonum()" value="GO" /></td>
      				</tr>
                      </table></td>
                    </tr>
                </table></td>
                <td width="9" valign="top" background="/images/wenba_woyaoda_r7_c8.jpg"><img src="/images/bai.gif" width="9" height="1" /></td>
              </tr>
          </table></td>
        </tr>
        <tr>
          <td valign="top"><img src="/images/wenba_woyaoda_r8_c2.jpg" width="761" height="18" /></td>
        </tr>
      </table>
      <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="/images/bai.gif" width="1" height="10" /></td>
        </tr>
      </table>
      <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="/images/bai.gif" width="1" height="10" /></td>
        </tr>
      </table></td>
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
          <td height="24" background="/images/wen_ba_r30_c34.jpg">&nbsp;<a href="#" class="blackc12c">本周问题排行榜</a></td>
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
          <td height="24" background="/images/wen_ba_r30_c34.jpg"><a href="#" class="blackc12">&nbsp;</a><a href="#" class="blackc12c">本周积分排行榜</a></td>
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
      </table></td>
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



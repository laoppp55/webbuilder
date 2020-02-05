<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>
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
	int classid = ParamUtil.getIntParameter(request,"cid",24);
	String CID = (String)request.getParameter("cid");
	String Pro = (String)request.getParameter("pro");
	String key = (String)request.getParameter("keys");
	List top8_wenti = iwenba.getTop8QuestionsWenti(classid);
	List top8_wenti_xuanshang = iwenba.getTop8QuestionsXuanshang(classid);
	List top10_wenti = iwenba.getTop10Questions(classid,0);
    List top10_wenti_jiejue= iwenba.getTop10Questions(classid,1);
    List top10_wenti_0anwser = iwenba.getTop10Questions0Answer(classid);
	String types = "diqu_oans";
%>
<%
	String linghuida = "零回答问题";
	linghuida = URLEncoder.encode(linghuida);
	String jiejue = "最新解决问题";
	jiejue = URLEncoder.encode(jiejue);
	String lanmu = "最新提出问题";
	lanmu = URLEncoder.encode(lanmu);
%>
<% //最新提出问提分页
		int intRowCount=0;  //总的记录数
		int intPageCount=0; //总的页数
		int intPageSize=10; //每页显示的记录数
		int intPage; //当前显示页 
		int begin_no=0; //开始的rownum记录号
		int end_no=0;  //结束的rownum记录号
		String strPage = request.getParameter("page"); //取当前显示页码 
		if(strPage==null||strPage.equals(null)){
			intPage = 1; 
		} 
		else{
			intPage = java.lang.Integer.parseInt(strPage); 
			if(intPage<1) 
				intPage = 1; 
		}
		begin_no=(intPage-1) * intPageSize + 1; 
	    end_no = intPage * intPageSize;
	    List pagelist = iwenba.getProQuestionsPagenum(Pro);
	    intRowCount = pagelist.size();
	    intPageCount = (intRowCount+intPageSize-1) / intPageSize; //计算总共要分多少页
			if(intPage>intPageCount) {
				intPage = intPageCount; //调整待显示的页码 
			}
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>地区问答</title>
<link href="/images/css.css" rel="stylesheet" type="text/css"/>
<link href="/webbuilder/sites/www_fawu_com/images/hr-low.css" rel="stylesheet" type="text/css" />

<script type="text/JavaScript">
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

function wenti_jiejue_change(){
	var url = "wenba_diqujj.jsp?cid=<%=classid%>&pro=<%= Pro%>&keys=<%= key%>";
	window.location.href=url;
}
//function wenti_ans_change(){
//	var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=<%= Pro%>";
//	window.location.href=url;
//}
function wenti(){
	window.open("/wenba/wenba_woyaowen.jsp?cid=<%= classid%>");
}

function difang(prov){
	switch(prov)
	{
		case '北京' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '上海' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '天津' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '重庆' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '安徽' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '福建' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '甘肃' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '广东' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '广西' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '贵州' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '海南' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '河南' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '河北' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '黑龙江' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '湖北' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '湖南' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '吉林' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '江苏' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '江西' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '辽宁' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '内蒙古' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '宁夏' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '青海' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '山东' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '山西' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '陕西' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '四川' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '云南' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '新疆' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '西藏' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
		case '浙江' :
		var url = "wenba_diqu0ans.jsp?cid=<%=classid%>&pro=" +prov +"&keys=" +"";
		window.location.href=url;
		break;
	}
}

function sousuo_fenlei(){
	var fenleiID = document.getElementById("select_fenlei").value; 
	var keys = document.getElementById("sousuo_key").value;
	var url ="wenba_diqu0ans.jsp?pro=<%= Pro%>&cid=" +fenleiID +"&keys=" + keys;
	if(fenleiID==""){
		alert("请选择分类");
		return ;
	}
	if(keys==""){
		alert("请输入搜索关键字");
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
<%@ include file="/include/laodongtop.shtml" %>
<%@ include file="/include/wenbacitou.shtml" %>
<table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/images/bai.gif" width="1" height="8" /></td>
  </tr>
</table>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="88" valign="top" background="/images/wenba_r11_c3.jpg" class="bian"><table width="753" border="0" align="right" cellpadding="0" cellspacing="0">
          <tr>
            <td width="224" height="70" valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top"><img src="/images/wen_ba_r3_c2.jpg" width="224" height="9" /></td>
              </tr>
              <tr>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="6" valign="top"><img src="/images/wen_ba_r4_c2.jpg" width="6" height="70" /></td>
                    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="25" class="blackc12c">&nbsp;<a href="javascript:wenti()">我要问</a></td>
                      </tr>
                      <tr>
                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="38"><a href="#" onclick="wenti()"><img src="/images/wen_ba_r2_c2.jpg" width="38" height="38" border="0" /></a></td>
                            <td><table width="95%" border="0" align="right" cellpadding="0" cellspacing="0">
                              	<%
								wenti wt2 = null;
								if(top10_wenti_jiejue.size()!=0){
								int wt_id2 = Integer.parseInt((String)top10_wenti_jiejue.get(0));
								wt2 = iwenba.getQuestion(wt_id2);
								String titles = wt2.getTitles().trim();  
		                    	String str;
		                    	if(titles.length()>12) {
		                    		str = titles.substring(0,12) + "...";
		                    	}else{
		                    		str = titles;
		                   		}                     
							%>
                              <tr>
                                <td class="black12"><a href="/wenba/wenba_finsh.jsp?id=<%= wt2.getId()%>&cid=<%= wt2.getColumnid()%>&fenlei=<%= jiejue%>"><%= str%></a></td>
                              </tr>
                              <%} %>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                    <td width="10" valign="top"><img src="/images/wen_ba_r4_c6.jpg" width="20" height="70" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td valign="top"><img src="/images/wen_ba_r8_c2.jpg" width="224" height="9" /></td>
              </tr>
            </table></td>
            <td width="265" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top"><img src="/images/wen_ba_r3_c7.jpg" width="264" height="9" /></td>
              </tr>
              <tr>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="6" valign="top"><img src="/images/wen_ba_r4_c2.jpg" width="6" height="70" /></td>
                      <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td height="25" class="blackc12c">&nbsp;<a href="#">我来答</a></td>
                        </tr>
                        <tr>
                          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="60"><img src="/images/wen_ba_r5_c8.jpg" width="60" height="38" /></td>
                                <td><table width="95%" border="0" align="right" cellpadding="0" cellspacing="0">
                                     <%
										wenti wt0 = null;
										if(top10_wenti_0anwser.size()!=0){
										int wt_id0 = Integer.parseInt((String)top10_wenti_0anwser.get(0));
										wt0 = iwenba.getQuestion(wt_id0);
										String titles = wt0.getTitles().trim();  
				                    	String str;
				                    	if(titles.length()>12) {
				                    		str = titles.substring(0,12) + "...";
				                    	}else{
				                    		str = titles;
				                   		}                     
									%>
                                    <tr>
                                      <td class="black12"><a href="/wenba/wenba_finsh.jsp?id=<%= wt0.getId()%>&cid=<%= wt0.getColumnid()%>&fenlei=<%= linghuida%>"><%= str%></a></td>
                                    </tr>
                                    <%} %>
                                </table></td>
                              </tr>
                          </table></td>
                        </tr>
                      </table></td>
                      <td width="10" valign="top"><img src="/images/wen_ba_r4_c9.jpg" width="20" height="70" /></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td valign="top"><img src="/images/wen_ba_r8_c7.jpg" width="264" height="9" /></td>
              </tr>
            </table></td>
            <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top"><img src="/images/wen_ba_r3_c10.jpg" width="265" height="9" /></td>
              </tr>
              <tr>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="6" valign="top"><img src="/images/wen_ba_r4_c2.jpg" width="6" height="70" /></td>
                      <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="25" class="blackc12c">&nbsp;<a href="/wenba/wenba_zhuanjia.jsp?cid=<%= classid%>">专家解疑</a></td>
                          </tr>
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="45"><img src="/images/wen_ba_r6_c13.jpg" width="45" height="38" /></td>
                                  <td><table width="95%" border="0" align="right" cellpadding="0" cellspacing="0">
                                       <%
										wenti wt1 = null;
										if(top10_wenti.size()!=0){
										int wt_id1 = Integer.parseInt((String)top10_wenti.get(0));
										wt1 = iwenba.getQuestion(wt_id1);
										String titles = wt1.getTitles().trim();  
				                    	String str;
				                    	if(titles.length()>12) {
				                    		str = titles.substring(0,12) + "...";
				                    	}else{
				                    		str = titles;
				                   		}                     
									   %>
                                      <tr>
                                        <td class="black12"><a href="/wenba/wenba_finsh.jsp?id=<%= wt1.getId()%>&cid=<%= wt1.getColumnid()%>&fenlei=<%= lanmu%>"><%= str%></a></td>
                                      </tr>
                                      <%} %>
                                  </table></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                      <td width="10" valign="top"><img src="/images/wen_ba_r4_c11.jpg" width="20" height="70" /></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td valign="top"><img src="/images/wen_ba_r8_c10.jpg" width="265" height="9" /></td>
              </tr>
            </table></td>
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
          <td height="30" class="black14">&nbsp;地方问答</td>
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
                    <input name="sousuo_key" type="text" style="font-size:12px;color:#000000;width: 170px;border:#d1d1d1 1px solid;" / >
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
                            <td width="248" height="30" align="center" background="/images/wenba_woyaoda_r7_c7.jpg" class="blackc12c"><a href="#" onclick="wenti_change()">最新提出问题</a></td>
                            <td align="center"><img src="/images/bai.gif" width="1" height="1" /></td>
                            <td width="248" align="center" background="/images/wenba_woyaoda_r7_c10.jpg" class="blackc12c"><a href="#" onclick="wenti_jiejue_change()">已解决的问题</a></td>
                            <td align="center"><img src="/images/bai.gif" width="1" height="1" /></td>
                            <td width="247" align="center" background="/images/wenba_woyaoda_r7_c5.jpg" class="blackc12c"><a href="#">零回答的问题</a></td>
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
									//String lanmu ="零回答问题";
									wenti wt_zuixin = null;
									String date_zuixin = null;
									String color1 = "#ECEDEF";
									String color2 = "#FFFFFF"; 
									String color = "";
									List wenti_zuixin = iwenba.getProQuestion0ans(end_no,begin_no,Pro,key,classid);
									for(int i=0;i<wenti_zuixin.size();i++){
										if(i%2==0){
											color = color2;
										}else{
											color = color1;
										}
										wt_zuixin = (wenti)wenti_zuixin.get(i);
										date_zuixin = wt_zuixin.getCreatedate().toString();
                    					int posi = date_zuixin.indexOf(" ");
                    					date_zuixin = date_zuixin.substring(0, posi);
                    					String titles = wt_zuixin.getTitles().trim();  
				                    	String str;
				                    	if(titles.length()>18) {
				                    		str = titles.substring(0,18) + "...";
				                    	}else{
				                    		str = titles;
				                   		}                     
								%>
                                <tr>
                                  <td height="25" align="left" bgcolor="<%= color%>">&nbsp;&nbsp;[<%= wt_zuixin.getCname()%>]</td>
                                  <td align="left" bgcolor="<%= color%>">
                                  	<a href="/wenba/wenba_finsh.jsp?cid=<%= wt_zuixin.getColumnid()%>&id=<%= wt_zuixin.getId()%>&fenlei=<%= linghuida%> ">&nbsp;&nbsp;<%= str%></a>&nbsp;&nbsp;
                                  	<%if(wt_zuixin.getFilepath()!=null){ %>
                        			<a href="javascript:xiazai()">下载</a>
                        			<%} %>
                                  </td>
                                  <script type="text/JavaScript">
			                      	function xiazai(){
			                      		var types = <%= userType%>;
			                      		var xuanshang = <%= P_jifeng%>;
			                      		//var name = <%=userName%>;
											if(types==0){
												alert("只有登陆用户才能下载相关资料！");
												return ;
											}
				                      		if(xuanshang<30){
				                      			alert("您的积分不足，不能下载相关资料！");
				                      			return;
				                      		}else{
				                      			var con = confirm("下载该资料将扣除您10积分。");
					                      		if(con==true){
					                      			var url = "/wenba/download.jsp?filepath=<%= wt_zuixin.getFilepath()%>&qid=<%=wt_zuixin.getId()%>&username=<%= userName%>";
					                      			window.location.href=url; 
					                      		}else{}          
				                      		}
				                     }
				                  </script>          
                                  <td align="center" bgcolor="<%= color%>"><%= wt_zuixin.getProvince()%></td>
                                  <td align="center" bgcolor="<%= color%>"><%= wt_zuixin.getXuanshang()%></td>
                                  <td align="center" bgcolor="<%= color%>"><%= wt_zuixin.getAnwsernum()%></td>
                                  <td align="center" bgcolor="<%= color%>">[<%= date_zuixin%>]</td>
                                </tr>
                                <%
                                	}if(wt_zuixin==null){
                                %>
                                <tr>
		                          <td height="25" colspan="5" align="center">对不起！没有您想找的搜索结果，您可以更换搜索条件重新搜索。</td>
		                          <td align="center"></td>
		                        </tr>
		                        <%} %>
                            </table></td>
                          </tr>
                          <tr>
                            <td height="40" align="right" valign="bottom">
                              <%if(intPageCount>1){ %>	
                            	<!-- 共<%= intPageCount%>页 -->
                            	<%if (intPage > 1) {%>
                            	<a href="wenba_diqu0ans.jsp?page=<%=intPage - 1%>&cid=<%=classid%>&pro=<%= Pro%>"><img src="/images/wenba_woyaoda_r9_c12.jpg" width="4" height="7" border="0" align="absmiddle" /></a>&nbsp;
                            	<%} %>
	                    		<a href="wenba_diqu0ans.jsp?page=1&cid=<%=classid%>&pro=<%= Pro%>">首页</a>
	                    		<%if (intPage > 1) {%>
	                    		<a href="wenba_diqu0ans.jsp?page=<%=intPage - 1%>&cid=<%=classid%>&pro=<%= Pro%>">上一页</a><%}%>
	                    		<%if (intPage < intPageCount) {%>
	                    		<a href="wenba_diqu0ans.jsp?page=<%=intPage + 1%>&cid=<%=classid%>&pro=<%= Pro%>">下一页</a><%}%>
	                            <a href="wenba_diqu0ans.jsp?page=<%=intPageCount%>&cid=<%=classid%>&pro=<%= Pro%>">尾页</a>&nbsp;
								<%if (intPage < intPageCount) {%>
								<a href="wenba_diqu0ans.jsp?page=<%=intPage + 1%>&cid=<%=classid%>&pro=<%= Pro%>"><img src="/images/wenba_woyaoda_r9_c14.jpg" width="4" height="7" border="0" align="absmiddle" /></a>&nbsp;&nbsp;
								<%} %>
							  <%} %>
							</td>
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
      <table width="761" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="45" background="/images/wenba_woyaowen_r2_c2.jpg"><table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td class="blackc12c"><img src="/images/wen_ba_r26_c22.jpg" width="7" height="11" align="absmiddle" />&nbsp;您的提问</td>
                <td align="right"><a href="#">&gt;&gt;&nbsp;更多</a></td>
              </tr>
          </table></td>
        </tr>
        <tr>
          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="8" valign="top" background="/images/wenba_woyaowen_r3_c2.jpg"><img src="/images/bai.gif" width="8" height="1" /></td>
                <td valign="top"><form name="form1" method="post" enctype="multipart/form-data">
			  <input type="hidden" name="types" value="<%= types%>" />
			  <input type="hidden" name="CLLUMNIDS" value="<%= CID%>"/>
			  <input type="hidden" name="Xuanshang" value="10"/>
			  <input type="hidden" name="userName" value="<%= userName%>" />
			  <input type="hidden" name="userId" value="<%= userID%>" />
			  <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="90" height="25">标&nbsp;&nbsp;题：</td>
                  <td><input type="text" name="titles" style="font-size:12px;color:#000000;width: 450px;border:#d1d1d1 1px solid;" value="控制在6－30个字之间，提问越详细，回答越准确。" onFocus="if (value =='控制在6－30个字之间，提问越详细，回答越准确。'){value =''}"/></td>
                </tr>
                <tr>
                  <td valign="top">内&nbsp;&nbsp;容：</td>
                  <td class="unnamed1"><textarea name="Question" rows="8" style="font-size:12px;color:#000000;width: 450px;border:#d1d1d1 1px solid;"></textarea><br/>提问控制在2000字以内，提问越详细，回答越准确。 <br/>
                    <input type="file" id="tplFile" name="tplFile"  style="font-size:12px;color:#000000;width: 200px;border:#d1d1d1 1px solid;" /> 图片必须小于1M,JPG或GIF格式</td>
                </tr>
                <tr>
                  <td valign="top"></td>
                  <td class="unnamed1">
                    <input type="file" id="tplFile1" name="tplFile1"  style="font-size:12px;color:#000000;width: 200px;border:#d1d1d1 1px solid;" /> 上传文件是txt或doc格式
                  </td>
                </tr>
                <tr>
                  <td height="25">问题分类：</td>
                  <td><select name="fenlei" style="font-size:12px;color:#000000;width: 120px;border:#d1d1d1 1px solid;" >
                  		<option value="">--请选择分类--</option>
                  	  	<%
							for(int i=0;i<list.size();i++){
							firstcolumn = (wenbaImpl)list.get(i);
							StringBuffer values = new StringBuffer();
							values.append(firstcolumn.getCName().toString()).append(",").append(String.valueOf(firstcolumn.getID()));
					  	%>
						<option value="<%= values%>"><%=StringUtil.gb2iso4View(firstcolumn.getCName())%></option>
						<%
							}
						%>
					  </select>				  </td>
                </tr>
                <tr>
                  <td height="25">所属地区：</td>
                  <td><select id="Select1" name="Select1"  onchange="selectProvince()" style="font-size:12px;color:#000000;width: 120px;border:#d1d1d1 1px solid;" >
                  </select>
                    省
                    <select id="Select2" name="Select2" onChange="selectArea()" style="font-size:12px;color:#000000;width: 120px;border:#d1d1d1 1px solid;" >
                    </select>
                    市
                    <select id="Select3" name="Select3" style="font-size:12px;color:#000000;width: 120px;border:#d1d1d1 1px solid;" >
                    </select>
                    区</td>
                </tr>
                <tr>
                  <td valign="top">邮箱地址：</td>
                  <td class="unnamed">
                    <input type="text" name="emails" style="font-size:12px;color:#000000;width: 120px;border:#d1d1d1 1px solid;" />
                    <br />
                    <span class="black12a">请正确输入邮箱地址，回答将发送至该邮箱，200字以内</span><br />
                    <input type="checkbox" name="Emailnotify" onClick="checkemail()"/>
                    <span class="unnamed1">当有人回答问题时，通过邮箱获得通知。</span><br/></td>
                </tr>
				
                <tr>
                  <td height="30" colspan="2" valign="top">
				    <table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td width="90" height="30">提问类型：</td>
						<td align="left" valign="middle" class="unnamed1">
						  <input type="radio" name="xstw" value="radiobutton" onClick="pttw()"/>普通提问
						  <input type="radio" name="xstw" value="radiobutton" onClick="tiwenleixing()" />悬赏提问
						  </td>
					  </tr>
					</table>				  </td>
                </tr>
                <tr>
                  <td colspan="2" valign="top">
				    <div id="twxs" style="display:none;">
				    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
						  <td width="90" valign="middle">悬 赏 分：</td>
						  <td class="unnamed1"><select name="xsjf">
								<option value="0">请选择</option>
								<option value="5">+5</option>
								<option value="10">+10</option>
								<option value="15">+15</option>
								<option value="20">+20</option>
							  </select> 
						  + 系统默认悬赏分10分。您的悬赏分越高获得回答的几率越大。
						  </td>
						  <td></td>
					  </tr>
                    </table>
					</div>
				  </td>
                </tr>
                <tr>
                  <td height="40" valign="middle">验 证 码：</td>
                  <td valign="bottom" class="unnamed"><span class="unnamed1">
                    <input name="Codes" type="text" style="font-size:12px;color:#000000;width: 120px;border:#d1d1d1 1px solid;" />
                    <img name="cod" src="valid.jsp" width="100" height="21" align="absmiddle" /></span><a href="#" onClick="shuaxin()">看不清楚？再换一张</a><br/>
                    <span class="black12a">请输入上图中的验证码，字符不区分大小写。</span></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td height="40">
                  <a href="javascript:check()"><img src="/images/wenba_woyaowen_r6_c4.jpg" width="60" height="19" border="0" align="absmiddle" /></a></td>
                </tr>
              </table>
			  </form></td>
                <td width="10" valign="top" background="/images/wenba_woyaowen_r3_c4.jpg"><img src="/images/bai.gif" width="10" height="1" /></td>
              </tr>
          </table></td>
        </tr>
        <tr>
          <td valign="top"><img src="/images/wenba_woyaowen_r4_c2.jpg" width="761" height="30" /></td>
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
          <td valign="top" background="/images/wen_ba_r18_c33.jpg"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="/images/bai.gif" width="1" height="5" /></td>
            </tr>
          </table>
            <table width="185" border="0" align="center" cellpadding="0" cellspacing="0">
             <%
                    String names = "本周问题排行榜";
                    wenti wtw = null;
                    int wt_idw = 0;
                    for(int i=0; i<top8_wenti.size(); i++) {
                        wtw = new wenti();
                        wt_idw=Integer.parseInt((String)top8_wenti.get(i).toString());
                        wtw = iwenba.getQuestion(wt_idw); 
                        String titles = wtw.getTitles().trim();  
                    	String str;
                    	if(titles.length()>10) {
                    		str = titles.substring(0,10) + "...";
                    	}else{
                    		str = titles;
                   		}                     
                                                    
              %>
			  <tr>
                <td width="15" height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" align="absmiddle" /></td>
                <td><a href="/wenba/wenba_finsh.jsp?id=<%= wtw.getId()%>&fenlei=<%= names%>"><%= str%></a></td>
                <td width="30" align="center" class="black12"><%= wtw.getXuanshang()%></td>
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
          <td valign="top" background="/images/wen_ba_r18_c33.jpg"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="/images/bai.gif" width="1" height="5" /></td>
              </tr>
            </table>
              <table width="185" border="0" align="center" cellpadding="0" cellspacing="0">
                <%
			  		String namex = "本周积分排行榜";
                    wenti wtx = null;
                    int wt_idx = 0;
                    for(int i=0; i<top8_wenti_xuanshang.size(); i++) {
                        wtx = new wenti();
                        wt_idx=Integer.parseInt((String)top8_wenti_xuanshang.get(i).toString());
                        wtx = iwenba.getQuestion(wt_idx);  
                        String titles = wtx.getTitles().trim();  
                    	String str;
                    	if(titles.length()>10) {
                    		str = titles.substring(0,10) + "...";
                    	}else{
                    		str = titles;
                   		}                                             
                %>
                <tr>
                  <td width="15" height="25"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" align="absmiddle" /></td>
                  <td><a href="/wenba/wenba_finsh.jsp?id=<%= wtx.getId()%>&fenlei=<%= namex%>"><%= str%></a></td>
                  <td width="30" align="center" class="black12"><%= wtx.getXuanshang()%></td>
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
</body>
</html>



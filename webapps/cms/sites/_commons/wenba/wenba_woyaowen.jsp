<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>
<%
	User user = (User)session.getAttribute("user");
	String userName = "";
	String userID ="";
	int userType = 1;
	if(user!=null){
		userName = (String)user.getUsername();
		userID = String.valueOf(user.getUserid());
		userType = user.getUsertype();
	}else{
		response.sendRedirect("/login.jsp");
	}
	int classid = ParamUtil.getIntParameter(request,"cid",0);
	//String CID = (String)request.getParameter("cid");
	String USERID_HD = request.getParameter("USERID")==null?"0":request.getParameter("USERID"); //USERID 回答该问题的专家的user_id
	//String cCNAME = (String)request.getParameter("Cname");
	//String pID = (String)request.getParameter("Pid");
	HttpSession sessions = request.getSession();
	String sRand=(String)sessions.getAttribute("randnum");
%>
<%
	wenbaImpl firstcolumn = null;
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	List list = iwenba.getCname();
	List top8_wenti = iwenba.getTop8QuestionsWenti(classid);
	List top8_wenti_xuanshang = iwenba.getTop8QuestionsXuanshang(classid);
	List top10_wenti = iwenba.getTop10Questions(classid,0);
    List top10_wenti_jiejue= iwenba.getTop10Questions(classid,1);
    List top10_wenti_0anwser = iwenba.getTop10Questions0Answer(classid);
    
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
	String types = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>提问</title>
<link href="/images/css.css" rel="stylesheet" type="text/css"/>
<link href="/webbuilder/sites/www_fawu_com/images/hr-low.css" rel="stylesheet" type="text/css" />
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
	pOption.value = "1";
	pOption.text = "--请选择--";
	dropElement1.add(pOption);
	
	var cOption = document.createElement("option");
	cOption.value = "1";
	cOption.text = "--请选择--";
	dropElement2.add(cOption);
	
	var aOption = document.createElement("option");
	aOption.value = "1";
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
	cOption.value = "1";
	cOption.text = "--请选择--";
	dropElement2.add(cOption);
	
	var aOption = document.createElement("option");
	aOption.value = "1";
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
	aOption.value = "1";
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

<script language="JavaScript" type="text/javascript">
function shuaxin(){
	//window.history.go();
	//document.all.cod.src="/validateServlet";
	document.all.cod.src="valid.jsp";
}

function biaoti(){
	document.all.titles.value=="";
}

function check(){
	if(document.all.titles.value==""){
		alert("请输入问题标题");
		return ;
	}
	var title = document.getElementById("titles").value;
	title = title.replace(/\s/ig,'');
	if(title.length<6){
		alert("标题过短，最少是六个汉字！");
		return;
	}
	if(document.getElementById("titles").value.length>30){
		alert("标题过长，最多不超过30个汉字！");
		return;
	}
	if(document.all.Question.value==""){
		alert("请输入问题内容！");
		return ;
	}
	if(document.getElementById("Question").value.length>2000){
		alert("内容最多不超过2000个汉字！");
		return;
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
	
	if(document.form1.fenlei.value==""){
		alert('请选择问题类别');
		return ;
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
	if(document.form1.emailflag.value==1){
		var emailPattern = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		if (emailPattern.test(document.getElementById("emails").value)==false){
			alert("email:"+document.getElementById("emails").value);
			alert("非法的Email地址！")
			return false;
		}
	}
	
	if(document.getElementById("xsh").checked){
		if(document.getElementById("xsjf").value>document.form1.personjifen.value){
		alert('对不起，您的积分不够，不能使用悬赏积分');
		return ;
		}
	}
	
	
	
	if(document.all.Codes.value==""){
		alert("请输入验证码");
		return ;
	}else{
		var verify = document.all.Codes.value;
		var objXmlc;
	    if (window.ActiveXObject){
	    	objXmlc = new ActiveXObject("Microsoft.XMLHTTP");
		}else if (window.XMLHttpRequest){
	    	objXmlc = new XMLHttpRequest();
		}
		objXmlc.open("POST", "CheceVerify.jsp?Verify="+verify, false);
		objXmlc.send(null);       
		var res = objXmlc.responseText; 
		//alert(res);
		var re = res.split('-'); 
		var retstrs = re[0];
		if(retstrs==0){
			alert("验证码错误！请重新输入。");		
			document.all.Codes.value ="";
			shuaxin();
			return ;
		}
	}
	if(document.all.Emailnotify.checked){
		if(document.all.emails.value == ""){
			alert("请输入邮箱地址");
			return ;
		}
	}
	document.all.form1.action = "tiwen_do.jsp?emailflag="+document.form1.emailflag.value;
	document.all.form1.submit();
}

function tiwenleixing(){
	document.getElementById("twxs").style.display="block";	
}

function checkxuanshang(){
	var userid = document.getElementById("userId").value;
	var xshjf = document.getElementById("xsjf").value;
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
		document.form1.personjifen.value = retstrs;
		if(retstrs<xshjf){
			alert('对不起，您的积分不够');
    		//document.getElementById("jifenyazheng").innerHTML = "<font color=red>"+"积分少于20无法使用悬赏提问！"+"</font>";
    	}	
}

function pttw(){
	document.getElementById("twxs").style.display="none"; 
}
function wenti(){
    var types = <%=userType%>
	if(types==0){
		alert("您没有登陆不能回答，请登陆。");
		return false;
	}else{
		window.open("/wenba/wenba_woyaowen.jsp?cid=<%=classid%>");
	}
}

function checkeflag(){
	if(document.form1.Emailnotify.checked){
		if(document.getElementById("emails").value==""){
			alert('请填写您的邮箱地址！');
			return false;
		}else{
			alert('请确保您的邮箱可用');
		}
		
		document.form1.emailflag.value=1;
	}else{
		document.form1.emailflag.value=0;
	}
}
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
    <td valign="top"><%@ include file="/include/wenbaZJL.jsp"%>
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
          <td valign="top" height="598" background="/images/2009630-1tiwen.jpg" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top">
			  <form name="form1" method="post" enctype="multipart/form-data">
			  <input type="hidden" name="USERID_HD" value="<%= USERID_HD%>"/>
			  <input type="hidden" name="types" value="<%= types%>" />
			  <input type="hidden" name="CLLUMNIDS" value="<%= classid%>"/>
			  <input type="hidden" name="Xuanshang" value="10"/>
			  <input type="hidden" name="userName" value="<%= userName%>" />
			  <input type="hidden" name="userId" id="userId" value="<%= userID%>" />
			  <input type="hidden" name="emailflag" value="0">
			  <input type="hidden" name="personjifen" value="0">
			  <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="90" height="25">标&nbsp;&nbsp;题：</td>
                  <td><input type="text" name="titles" id="titles" style="font-size:12px;color:#000000;width: 450px;border:#d1d1d1 1px solid;" value=""  /></td>
                </tr>
                <tr>
                  <td valign="top">内&nbsp;&nbsp;容：</td>
                  <td class="unnamed1"><textarea name="Question" id="Question" rows="8" style="font-size:12px;color:#000000;width: 450px;border:#d1d1d1 1px solid;"></textarea><br/>提问控制在2000字以内，提问越详细，回答越准确。<br/>
                    <input type="file" id="tplFile" name="tplFile"  style="font-size:12px;color:#000000;width: 200px;border:#d1d1d1 1px solid;" /> 图片必须小于1M,JPG或GIF格式</td>
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
                    <input type="text" id="emails" name="emails" style="font-size:12px;color:#000000;width: 120px;border:#d1d1d1 1px solid;"/>
                    <br />
                    <span class="black12a">请正确输入邮箱地址，回答将发送至该邮箱，200字以内</span><br />
                    <input type="checkbox" name="Emailnotify" onClick="checkeflag()"/>
                    <span class="unnamed1">当有人回答问题时，通过邮箱获得通知。</span><br/></td>
                </tr>
				
                <tr>
                  <td height="30" colspan="2" valign="top">
				    <table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td width="90" height="30">提问类型：</td>
						<td width="174" align="left" valign="middle" class="unnamed1">
						  <input type="radio" name="xstw" id="pt" value="0" onClick="pttw()" checked/>普通提问
						  <input type="radio" name="xstw" id="xsh" value="1" onClick="tiwenleixing()" />悬赏提问						  </td>
					    <td width="296" align="left" valign="baseline" class="unnamed1"><div id="jifenyazheng"></div></td>
					  </tr>
					</table>				  </td>
                </tr>
                <tr>
                  <td colspan="2" valign="top">
				    <div id="twxs" style="display:none;">
				    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
						  <td width="90" valign="middle">悬 赏 分：</td>
						  <td class="unnamed1"><select name="xsjf" id="xsjf" onchange="checkxuanshang()">
								<option value="5">+5</option>
								<option value="10">+10</option>
								<option value="15">+15</option>
								<option value="20">+20</option>
							  </select> 
						  您的悬赏分越高获得回答的几率越大，但也会扣除您相应的积分。						  </td>
						  <td></td>
					  </tr>
                    </table>
					</div>				  </td>
                </tr>
                <tr>
                  <td height="40" valign="middle">验 证 码：</td>
                  <td valign="bottom" class="unnamed"><span class="unnamed1">
                    <input name="Codes" type="text" style="font-size:12px;color:#000000;width: 120px;border:#d1d1d1 1px solid;" />
                    <img name="cod" src="valid.jsp" width="100" height="21" align="absmiddle" /></span><a href="#" onClick="javascript:shuaxin()">看不清楚？再换一张</a><br/>
                    <span class="black12a">请输入上图中的验证码，字符不区分大小写。</span></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td height="40">
                  <a href="javascript:check()" ><img src="/images/wenba_woyaowen_r6_c4.jpg" width="60" height="19" border="0" align="absmiddle" /></a></td>
                </tr>
              </table>
			  </form>			  </td>
              </tr>
          </table></td>
        </tr>
        <tr>
          <td valign="top"><img src="/images/wenba_woyaowen_r4_c2.jpg" width="761" height="30" /></td>
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
            </table>
            
          </td>
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
            </table>
          </td>
            
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

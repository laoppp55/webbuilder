<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@page import="java.net.*" %>
<%
	User user = (User)session.getAttribute("user");
	int userType = 1;
	if(user==null){
		userType = 0;
	}
	String userName="";
	int userID = 0;
	if(user!=null){
		userID = Integer.parseInt(String.valueOf(user.getUserid()));
		userName = user.getUsername().toString();
		userType = user.getUsertype();
	}
	
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	int classid = Integer.parseInt((String)request.getParameter("cid")==null?"0":request.getParameter("cid"));
	int Qid =Integer.parseInt((String)request.getParameter("id"));
	String fenLei = (String)request.getParameter("fenlei");
	wenti wt = iwenba.getQuestion(Qid);
	List top8_wenti = iwenba.getTop8QuestionsWenti(classid);
	List top8_wenti_xuanshang = iwenba.getTop8QuestionsXuanshang(classid);
	
	List dianjilist = iwenba.getTOP8DianJiShu(classid);
    List gradelist = iwenba.getTop8weekgrade();
%>
<%
	List top10_wenti = iwenba.getTop10Questions(classid,0);
    List top10_wenti_jiejue= iwenba.getTop10Questions(classid,1);
    List top10_wenti_0anwser = iwenba.getTop10Questions0Answer(classid);
	String linghuida = "零回答问题";
	String jiejue = "最新解决问题";
	String lanmu = "最新提出问题";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>我来答</title>
<link href="/images/css.css" rel="stylesheet" type="text/css"/>
<link href="/webbuilder/sites/www_fawu_com/images/hr-low.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript">
function shuaxin(){
	document.all.cod.src="valid.jsp";
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
function check(){
	var userflag = "<%=userName%>";
	if(userflag==""){
		alert('您还没有登陆，请登陆后继续操作');
		return;
	}
	if(document.all.textArea.value==""){
		alert("请输入问题回答");
		return false;
	}
	if(document.getElementById("textArea").value.length>2000){
		alert("回答内容最多不超过2000个汉字！");
		return;
	}
	if(document.getElementById("canKao").value.length>100){
		alert("参考资料字数不能多于100！");
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
	document.all.Form.action = "answer_do.jsp?useremail="+document.getElementById("useremail1").value+"&emailflag="+document.all.sendemail.value+"&questionname="+document.getElementById("questionname").value;
	document.all.Form.submit();
}
function shuaxin(){
	//window.history.go();
	//document.all.cod.src="/validateServlet";
	document.all.cod.src="valid.jsp";
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
              <td class="blackc12c"><img src="/images/wen_ba_r26_c22.jpg" width="7" height="11" align="absmiddle" />&nbsp;您的回答</td>
              <td align="right"><a href="#">&gt;&gt;&nbsp;更多</a></td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="8" valign="top" background="/images/wenba_woyaowen_r3_c2.jpg"><img src="/images/bai.gif" width="8" height="1" /></td>
              <td valign="top">
              <form name="Form" method="post" enctype="multipart/form-data">
              <input type="hidden" name="UserName" value="<%= userName%>"/>
              <input type="hidden" name="UserID" value="<%= userID%>" />
    		  <input type="hidden" name="QID" value="<%= Qid%>" />
    		  <input type="hidden" name="CID" value="<%= classid%>" />
    		  <input type="hidden" name="fff" value="<%= fenLei%>" />
    		  <input type="hidden" name="sendemail" value="<%=wt.getEmailnotify() %>">
    		  <input type="hidden" id="useremail1" name="useremail1" value="<%=wt.getEmail() %>">
    		  <input type="hidden" name="questionname" id="questionname" value="<%=wt.getTitles() %>">
              <table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="100" height="25">正在回答的问题：</td>
                  <td><%= wt.getTitles()%></td>
                </tr>
                <tr>
                	<td valign="top">问题内容</td>
                	<td class="unnamed1"><textarea name="textArea1" id="anwsercontent" rows="5" style="font-size:12px;color:#000000;width: 450px;border:#d1d1d1 1px solid;"><%=wt.getQuestion() %></textarea></td>
                </tr>
                <tr>
                  <td valign="top">您的回答：</td>
                  <td class="unnamed1"><textarea name="textArea" id="textArea" rows="8" style="font-size:12px;color:#000000;width: 450px;border:#d1d1d1 1px solid;"></textarea><br/>                    
                  回答控制在2000字以内，回答越详细越准确，越容易被选为最佳答案。<br/>
                    <input type="file" id="tplFile" name="tplFile" style="font-size:12px;color:#000000;width: 200px;border:#d1d1d1 1px solid;" />
                    
                    如文字不能准确表达，还可上传图片。图片必须小于1M，JPG或GIF格式</td>
                </tr>
                
                
                <tr>
                  <td valign="top">参考资料：</td>
                  <td class="unnamed">
                    <input name="canKao" id="canKao" type="text" style="font-size:12px;color:#000000;width: 300px;border:#d1d1d1 1px solid;" />
                    <br />
                    <span class="black12a">如果回答内容参照了相关资料，如网址、书名、文献...等，请在此注明，100字以内</span></td>
                </tr>
                <tr>
                  <td valign="top">验 证 码：</td>
                  <td class="unnamed"><span class="unnamed1">
                    <input name="Codes" type="text" style="font-size:12px;color:#000000;width: 120px;border:#d1d1d1 1px solid;" />
                    <img name="cod" src="valid.jsp" width="83" height="21" align="absmiddle" /></span><a href="#" onClick="shuaxin()">看不清楚？再换一张</a><br/>
                    <span class="black12a">请输入上图中的验证码，字符不区分大小写。</span></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td height="40"><img src="/images/wenba_woyaowen_r6_c4.jpg" width="60" height="19" border="0" align="absmiddle" onclick="check()" style="cursor:hand"/></a></td>
                </tr>
              </table>
              </form>
              </td>
              <td width="10" valign="top" background="/images/wenba_woyaowen_r3_c4.jpg"><img src="/images/bai.gif" width="10" height="1" /></td>
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
          <td height="24" background="/images/wen_ba_r30_c34.jpg"><a href="#" class="blackc12c">&nbsp;本周问题排行榜</a></td>
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
</body>
</html>

<%@ page  import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.ParsePosition" %>
<%
	wenbaImpl firstcolumn = null,sc=null;
    int classid = ParamUtil.getIntParameter(request,"cid",0);
    int locationid = ParamUtil.getIntParameter(request,"localid",0);
    IWenbaManager iwenba = wenbaManagerImpl.getInstance();
%>
<%
	//次程序用于判断到期问题的状态（是否过期  是否有最佳答案(是否 已解决)）
	List alllist = iwenba.getAllQuestions0(classid,0);
	for(int l=0;l<alllist.size();l++){
		//获取当前系统时间  用于计算距离回答结束还有多久
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date systemDate = new Date();
		ParsePosition pos1 = new ParsePosition(0);
		ParsePosition pos2 = new ParsePosition(0);
		String SystemDate = sdf.format(systemDate);
		Date systemdate = sdf.parse(SystemDate, pos1);
		
		wenti wt_status = new wenti();
		int id = Integer.parseInt((String)alllist.get(l));
		wt_status = iwenba.getQuestion(id);
		
		String wtdate = wt_status.getCreatedate().toString();
		int wtposi = wtdate.indexOf(" ");
		wtdate = wtdate.substring(0, wtposi);
		//获取提问时间用于计算距离回答还有多少天
		Date cDate = sdf.parse(wtdate, pos2);
		long day = systemdate.getTime() - cDate.getTime();
		String days = String.valueOf((day / (24 * 60 * 60 * 1000)));
		int szts = 15;
		//计算距离回答结束的天数
		int dates = szts - Integer.parseInt(days);
		if(dates==0){
			iwenba.changeQuestionStatus(id);
			if(wt_status.getAnwsernum()==0){
				iwenba.changeanwStatus_wenti(id);//设置问题过期
			}else{
				int aid = iwenba.getOneansid(id).getId();
				iwenba.changeAnwStatus(aid);//选择得票数最多的答案为最佳
				
			}
		}
	
	
	}
	
	
%>

<%
	User user = (User)session.getAttribute("user");
	String username = null;
	int P_jifeng = 0;
	int userType = 1;
	if(user==null){
		//response.sendRedirect("/login.jsp");
		userType = 0;
	}else{
		P_jifeng = user.getUsergrade();
		username = user.getUsername();
	}
   
    List list = iwenba.getCname();
	String Pro = (String)request.getParameter("pro");
	String sousuo = (String)request.getParameter("sss");
	//String sousuo = "培训";
	List top10_wenti_sousuo = null;
	List top10_wenti_0anwser_sousuo = null;
	List top10_wenti_sousuo_jiejue =null;
	if(sousuo!=null||!"".equals(sousuo)){
		top10_wenti_sousuo = iwenba.getTop10QuestionsSousuo(classid,0,sousuo);
		top10_wenti_sousuo_jiejue = iwenba.getTop10QuestionsSousuo(classid,1,sousuo);
		top10_wenti_0anwser_sousuo = iwenba.getTop10Questions0AnswerSousuo(classid,sousuo);
	}	
	//List top10_wentip = iwenba.getTop10Questions(classid,0,Pro);
	List top8_wenti = iwenba.getTop8QuestionsWenti(classid);
	List top8_wenti_xuanshang = iwenba.getTop8QuestionsXuanshang(classid);
    List top10_wenti = iwenba.getTop10Questions(classid,0);
    List top10_wenti_jiejue= iwenba.getTop10Questions(classid,1);
    //List top10_wenti_jiejuep= iwenba.getTop10Questions(classid,1,Pro);
    List top10_wenti_0anwser = iwenba.getTop10Questions0Answer(classid); 
    List top10_wenti_0anwserp = iwenba.getTop10Questions0Answer(classid,Pro);
    
    
    List dianjilist = iwenba.getTOP8DianJiShu(classid);
    List gradelist = iwenba.getTop8weekgrade();
    List zhuanjialist = iwenba.getzhuangjian();
    
%>
<%
	String linghuida = "零回答问题";
	linghuida = URLEncoder.encode(linghuida);
	String jiejue = "最新解决问题";
	jiejue = URLEncoder.encode(jiejue);
	String lanmu = "最新提出问题";
	lanmu = URLEncoder.encode(lanmu);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>中国劳动法务网 > 问吧</title>
<link href="/images/css.css" rel="stylesheet" type="text/css"/>
<link href="/webbuilder/sites/www_fawu_com/images/hr-low.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">

<!--
function wenti(){
    var types = <%= userType%>;
	if(types==0){
		alert("您没有登陆不能提问，请登陆。");
		return ;
	}else{
		window.open("/wenba/wenba_woyaowen.jsp?cid=<%= classid%>");
	}
}
function divchange(){
	var skey = document.getElementById("sss").value;
	var FenLei = document.getElementById("fenlei").value;
	var FanWei = document.getElementById("fanwei").value;
	var url = "";
	if(FenLei == ""){
		alert("请选择分类");
		return ;
	}
	if(FanWei == ""){
		alert("请选择范围")
		return ;
	}
	if(FanWei=="zuixinwenti")
		url = "doZXTW.jsp?fenlei="+FenLei+"&keyword="+skey;
	else if(FanWei=="jiejiewenti")
		url = "doYJJ.jsp?fenlei="+FenLei+"&keyword="+skey;
	else if(FanWei=="0answenti")
		url = "doLHD.jsp?fenlei="+FenLei+"&keyword="+skey;
	window.location.href=url; 
}

function wenti_fenlei(){
	var fenleis = document.getElementById("wentifenlei").value;
	var url = "wenba_fenlei.jsp?cid=<%= classid%>&sss=" +fenleis;
	window.location.href=url; 
}


//-->
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
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="225" valign="top">
		<!-- <form name="sform" method="post">   -->
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
                         System.out.println("extra=" + extra);
                         for(int i=0;i<list.size()/2; i++) {
                             firstcolumn = (wenbaImpl)list.get(2*i);
                             sc = (wenbaImpl)list.get(2*i+1);
                     %>
                    <tr>
                      <td width="95" height="25"><img src="/images/wen_ba_r14_c4.jpg" width="3" height="3" align="absmiddle" />&nbsp;&nbsp;<a href="doZXTW.jsp?fenlei=<%=firstcolumn.getID()%>"><%=firstcolumn.getCName()%></a></td>
                      <td width="95"><img src="/images/wen_ba_r14_c4.jpg" width="3" height="3" align="absmiddle" />&nbsp;&nbsp;<a href="doZXTW.jsp?fenlei=<%=sc.getID()%>"><%=sc.getCName()%></a></td>
                    </tr>
                    <%}%>
                     <% if (extra > 0) {
                        firstcolumn = (wenbaImpl)list.get(list.size()-1);
                     %>
                    <tr>
                      <td width="95" height="25"><img src="/images/wen_ba_r14_c4.jpg" width="3" height="3" align="absmiddle" />&nbsp;&nbsp;<a href="doZXTW.jsp?fenlei=<%=firstcolumn.getID()%>"><%=firstcolumn.getCName()%></a></td>
                      <td width="95">&nbsp;&nbsp;</td>
                    </tr>
                     <%}%>
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
                          <td>&nbsp;<select name="fenlei" id=""style="font-size:12px;color:#000000;width: 130px;border:#d1d1d1 1px solid;">
										<option >--请选择--</option>
										<%
										    for(int i=0;i<list.size();i++){
										    firstcolumn = (wenbaImpl)list.get(i);
					  		  			%>
										<option value="<%= firstcolumn.getID()%>"><%=StringUtil.gb2iso4View(firstcolumn.getCName()) %></option>
											<%}%>
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
			<td height="40" align="right" bgcolor="#F1F5F6"><a href="javascript:divchange()"><img src="/images/wen_ba_r24_c12.jpg" width="41" height="23" border="0" align="absbottom" /></a>&nbsp;&nbsp;</td>
			</tr>
          </table>
		 <!-- </form>  -->   
		  	
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
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="24" valign="bottom" background="/images/wenba_r6_c2.jpg" class="blackc12">&nbsp;&nbsp;专家介绍</td>
              </tr>
              <tr>
                <td height="195" align="left" valign="top" background="/images/wenba_r7_c2.jpg"><table width="205" border="0" align="center" cellpadding="0" cellspacing="0">
                  
                  <%
                  if(zhuanjialist!=null){
                  	for(int i=0;i<zhuanjialist.size();i++){ 
                  		if(i>=2) break;
                  		User zhuanjia = (User)zhuanjialist.get(i);
                  		String introduct = zhuanjia.getZhuanjiajianjie();
                  %>
                  <tr>
                    <td width="56" align="left" valign="top"><a href="zhuanjia_info.jsp?userid=<%=zhuanjia.getUserid() %>&imgname=<%=zhuanjia.getImgname() %>"><img src="/wenba/images/<%=zhuanjia.getImgname() %>" width="56" height="78" border="0"/></a></td>
                    <td align="left" valign="top"><table width="95%" border="0" align="right" cellpadding="0" cellspacing="0">
                      <tr>
                        <td><%=zhuanjia.getUsername()%><br><%if(introduct!=null){ if(introduct.length()>=10) out.write(introduct.substring(0,10)+".."); else{ out.write(introduct);}}else{out.write("中国劳动法务网资深会员竭诚为您服务！");}%></td>
                      </tr>
                    </table></td>
                  </tr>  		
                  <%} }%>
                </table></td>
              </tr>
              <tr>
                <td><img src="/images/wenba_r9_c2.jpg" width="224" height="5" /></td>
              </tr>
            </table></td>
          <td valign="top" width="8"><img src="/images/bai.gif" width="8" height="1" /></td>
          <td valign="top">
		  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="45" background="/images/wenba_r2_c7.jpg"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="blackc12"><img src="/images/wen_ba_r26_c22.jpg" width="7" height="11" align="absmiddle" />&nbsp;最新提出问题</td>
                  <td align="right"><a href="/wenba/doZXTW.jsp">&gt;&gt;&nbsp;更多</a></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10" valign="top" background="/images/wenba_r3_c7.jpg"><img src="/images/bai.gif" width="10" height="1" /></td>
                  <td height="215" valign="top">
				  <div id="lanmu_tichu">
				  	<table width="480" border="0" align="center" cellpadding="0" cellspacing="0">
                    <%
                    	
                        wenti wtz1 = null;
                        int wt_id1 = 0;
                        String date_str = null;
                        for(int i=0; i<top10_wenti.size(); i++) {
                            wtz1 = new wenti();
                            wt_id1=Integer.parseInt((String)top10_wenti.get(i));
                            wtz1 = iwenba.getQuestion(wt_id1);
                            date_str = wtz1.getCreatedate().toString();
                            int posi = date_str.indexOf(" ");
                            date_str = date_str.substring(0,posi);
                            String title = wtz1.getTitles().trim();
							String str = null;
							if(title.length()>22){
								str = title.substring(0,22) + "...";
							}else{
								str = title ;
							}
                            
                
                        %>
                    <tr>
                      <td height="20">
                        <img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" align="absmiddle" />&nbsp;&nbsp;
                        <a href="/wenba/wenba_finsh.jsp?id=<%= wtz1.getId()%>&cid=<%= wtz1.getColumnid()%>&fenlei=<%= lanmu%>"><%=str%></a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <%if(wtz1.getFilepath()!=null){ %>
                        <a href="javascript:xiazai()">下载</a>
                        <%} %>
                      </td>
                      <script type="text/JavaScript">
                      	function xiazai(){
                      		var types = <%= userType%>;
                      		var xuanshang = <%= P_jifeng%>;
                      		//var name = <%=username%>;
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
		                      			var url = "/wenba/download.jsp?filepath=<%= wtz1.getFilepath()%>&qid=<%=wtz1.getId()%>&username=<%= username%>";
		                      			window.location.href=url; 
		                      		}else{}
	                      		}
                      	}
                      </script>
                      <td width="80" align="center" class="black12">[<%=date_str%>]</td>
                    </tr>
                    <%}%>
                  </table>
				  </div>
				  </td>
                  <td width="10" valign="top" background="/images/wenba_r3_c9.jpg"><img src="/images/bai.gif" width="10" height="1"  /></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td valign="top"><img src="/images/wenba_r4_c7.jpg" width="530" height="20" /></td>
            </tr>
          </table>
            <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><img src="/images/bai.gif" width="1" height="13" /></td>
              </tr>
            </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="huibian">
              <tr>
                <td height="34" background="/images/wenba_r8_c10.jpg"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="blackc12"><img src="/images/wen_ba_r26_c22.jpg" width="7" height="11" align="absmiddle" />&nbsp;最新解决问题</td>
                    <td align="right"><a href="/wenba/doYJJ.jsp">&gt;&gt;&nbsp;更多</a></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td valign="top"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><img src="/images/bai.gif" width="1" height="5" /></td>
                  </tr>
                </table>
                  <div id="jiejue">
				  	<table width="480" border="0" align="center" cellpadding="0" cellspacing="0">
                      <%
                         wenti wtj = null;
                         int wt_id2 = 0;
                          for(int i=0; i<top10_wenti_jiejue.size(); i++) {
                              wtj = new wenti();
                              wt_id2=Integer.parseInt((String)top10_wenti_jiejue.get(i));
                              wtj = iwenba.getQuestion(wt_id2);
                              date_str = wtj.getCreatedate().toString();
                              int posi = date_str.indexOf(" ");
                              date_str = date_str.substring(0,posi);
                              String title = wtj.getTitles().trim();
							  String str = null;
							  if(title.length()>22){
							  	str = title.substring(0,22) + "...";
							  }else{
								str = title ;
							  }
                              
                          %>
                      <tr>
                      <td width="15" height="20"><img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" align="absmiddle" /></td>
                      <td>
                      	<a href="/wenba/wenba_finsh.jsp?id=<%= wtj.getId()%>&cid=<%= wtj.getColumnid()%>&fenlei=<%= jiejue%>"><%=str%></a>&nbsp;&nbsp;&nbsp;&nbsp;
                      	<%if(wtj.getFilepath()!=null){ %>
                        <a href="javascript:xiazai1()">下载</a>
                        <%} %>
                      </td>
                      <script type="text/JavaScript">
                      	function xiazai1(){
                      		var types = <%= userType%>;
                      		var xuanshang = <%= P_jifeng%>;
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
		                      			var url = "/wenba/download.jsp?filepath=<%= wtj.getFilepath()%>&qid=<%=wtj.getId()%>&username=<%= username%>";
		                      			window.location.href=url; 
		                      		}else{}
	                      		}
                      	}
                      </script>
                      <td width="80" align="center" class="black12">[<%=date_str%>]</td>
                    </tr>
                    <%}%>
                  </table>
				  </div>
				  
                  <table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td><img src="/images/bai.gif" width="1" height="5" /></td>
                    </tr>
                  </table></td>
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
                      <td valign="top" bgcolor="#FFFFFF"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td height="25" class="blackc12"><img src="/images/wen_ba_r26_c22.jpg" width="7" height="11" align="absmiddle" />&nbsp;零回答问题</td>
                          <td align="right"><a href="/wenba/doLHD.jsp">&gt;&gt;&nbsp;更多</a></td>
                        </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td valign="top" bgcolor="#FFFFFF"><img src="/images/wen_ba_r39_c21.jpg" width="508" height="1" /></td>
                    </tr>
                    <tr>
                      <td valign="top" bgcolor="#FFFFFF"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                          <td><img src="/images/bai.gif" width="1" height="10" /></td>
                        </tr>
                      </table>
                        <div id="0ans">
							<table width="480" border="0" align="center" cellpadding="0" cellspacing="0">
                            <%
                            
                               wenti  wta = null;
                               int wt_id3 = 0;
                                for(int i=0; i<top10_wenti_0anwser.size(); i++) {
                                    wta = new wenti();
                                    wt_id3=Integer.parseInt((String)top10_wenti_0anwser.get(i));
                                    wta = iwenba.getQuestion(wt_id3);
                                    date_str = wta.getCreatedate().toString();
                                    int posi = date_str.indexOf(" ");
                                    date_str = date_str.substring(0,posi);
                                    String title = wta.getTitles().trim();
									String str = null;
									if(title.length()>22){
										str = title.substring(0,22) + "...";
									}else{
										str = title ;
									}
                                %>
                        <tr>
                          <td height="20">
                          	<img src="/images/wen_ba_r30_c6.jpg" width="3" height="3" align="absmiddle" />&nbsp;&nbsp;
                          	<a href="/wenba/wenba_finsh.jsp?id=<%= wta.getId()%>&cid=<%= wta.getColumnid()%>&fenlei=<%= linghuida%>"><%=str%></a>&nbsp;&nbsp;&nbsp;&nbsp;
                          	<%if(wta.getFilepath()!=null){ %>
                        	<a href="javascript:xiazai2()">下载</a>
                        	<%} %>
                          </td>
                          <script type="text/JavaScript">
	                      	function xiazai2(){
	                      		var types = <%= userType%>;
	                      		var xuanshang = <%= P_jifeng%>;
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
		                      				var url = "/wenba/download.jsp?filepath=<%= wta.getFilepath()%>&qid=<%=wta.getId()%>&username=<%= username%>";
		                      				window.location.href=url; 
		                      			}else{}
		                      			
		                      		}
	                      	}
	                      </script>
                          <td width="80" align="center" class="black12">[<%=date_str%>]</td>
                        </tr>
                       <%}%>
                      </table>
						</div>
					  
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
                  </table></td>
              </tr>
            </table>
        </tr>
      </table>

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
          <td valign="top" background="/images/2009630-1bg.jpg" height="209"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="/images/bai.gif" width="1" height="5" /></td>
            </tr>
          </table>
            <table width="180" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="25"><img src="/images/wen_ba_r19_c34.jpg" width="15" height="14" align="absmiddle" />&nbsp;<a href="/wenba/wenba_rhtw.jsp">如何提问？</a></td>
              </tr>
              <tr>
                <td height="25"><img src="/images/wen_ba_r21_c35.jpg" width="15" height="14" align="absmiddle" />&nbsp;<a href="/wenba/wenba_rhhd.jsp">如何回答？</a></td>
              </tr>
              <tr>
                <td height="25"><img src="/images/wen_ba_r23_c35.jpg" width="15" height="14" align="absmiddle" />&nbsp;<a href="/wenba/wenba_zjjy.jsp">什么是专家解疑？</a></td>
              </tr>
              <tr>
                <td height="25"><img src="/images/wen_ba_r25_c35.jpg" width="15" height="14" align="absmiddle" />&nbsp;<a href="#"><a href="/wenba/wenba_guifan.jsp">规范</a><a href="/wenba/wenba_jifen.jsp">/积分规则是什么？</a></td>
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
          <td><img src="/images/bai.gif" width="1" height="12" /></td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="huibian1">
        <tr>
          <td height="24" background="/images/wen_ba_r30_c34.jpg"><a href="#" class="blackc12">&nbsp;本周问题排行榜</a></td>
        </tr>
        <tr>
          <td height="220" valign="top" background="/images/wen_ba_r18_c33.jpg"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
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
          <td><img src="/images/bai.gif" width="1" height="12" /></td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="huibian1">
        <tr class="blackc12">
          <td height="24" background="/images/wen_ba_r30_c34.jpg"><a href="#" class="blackc12">&nbsp;</a><a href="#">本周积分排行榜</a></td>
        </tr>
        <tr>
          <td height="241" valign="top" background="/images/wen_ba_r18_c33.jpg"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
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

</table>
<table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/images/bai.gif" width="1" height="13" /></td>
  </tr>
</table>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/images/zhuce1_r19_c2.jpg" width="1000" height="3" /></td>
  </tr>
  <tr>
    <td height="25" align="center" class="black12"><a href="#" class="lan">关于我们&nbsp;&nbsp;</a>-<a href="#" class="lan">&nbsp;&nbsp;联系我们</a><a href="#" class="lan">&nbsp;&nbsp;</a>-<a href="#" class="lan">&nbsp;&nbsp;</a><a href="#" class="lan">为您服务&nbsp;&nbsp;</a>-<a href="#" class="lan">&nbsp;&nbsp;</a><a href="#" class="lan">HR&nbsp;外&nbsp;包&nbsp;&nbsp;</a>-<a href="#" class="lan">&nbsp;&nbsp;</a><a href="#" class="lan">劳务派遣&nbsp;&nbsp;</a>-<a href="#" class="lan">&nbsp;&nbsp;</a><a href="#" class="lan">广告投放&nbsp;&nbsp;</a>-<a href="#" class="lan">&nbsp;&nbsp;</a><a href="#" class="lan">在线咨询&nbsp;&nbsp;</a>-<a href="#" class="lan">&nbsp;&nbsp;</a><a href="#" class="lan">网站地图</a><a href="#" class="lan">&nbsp;&nbsp;</a>-<a href="#" class="lan">&nbsp;&nbsp;</a><a href="#" class="lan">免责声明</a></td>
  </tr>
  <tr>
    <td><img src="/images/bai.gif" width="1" height="10" /></td>
  </tr>
  <tr>
    <td height="20" align="center">中国劳动法务网版权所有<a href="#" class="lan">&nbsp;&nbsp;</a><a href="#" class="lan">&nbsp;&nbsp;</a>京ICP备000000号</td>
  </tr>
  <tr>
    <td height="20" align="center">Copyright @ 2008-2010 Dreamstime.all right reserved. </td>
  </tr>
</table>
</body>
</html>

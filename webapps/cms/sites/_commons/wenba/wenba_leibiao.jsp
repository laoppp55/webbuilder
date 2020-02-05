<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>
<%
	User user = (User)session.getAttribute("user");
	String username = null;
	int P_jifeng = 0;
	int userType = 1;
	if(user==null){
		userType = 0;
		//
	}else{
		P_jifeng = user.getUsergrade();
		username = user.getUsername();
	}
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	wenbaImpl firstcolumn = null;
	List lists = iwenba.getCname();
	int classid = ParamUtil.getIntParameter(request,"cid",24);
	List top10_wenti = iwenba.getTop10Questions(classid,0);
    List top10_wenti_jiejue= iwenba.getTop10Questions(classid,1);
    List top10_wenti_0anwser = iwenba.getTop10Questions0Answer(classid); 
	String skey = (String)request.getParameter("keys");
	String Pro = (String)request.getParameter("pro");
	List top8_wenti = iwenba.getTop8QuestionsWenti(classid);
	List top8_wenti_xuanshang = iwenba.getTop8QuestionsXuanshang(classid);
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
		int intPageSize=15; //每页显示的记录数
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
	    List pagelist = iwenba.getSousuoQuestionsPidnum(classid,0,skey,Pro);
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
<title>中国劳动法务网--地区搜索</title>
<link href="/images/css.css" rel="stylesheet" type="text/css"/>
<link href="/webbuilder/sites/www_fawu_com/images/hr-low.css" rel="stylesheet" type="text/css" />
</head>
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
function change_wenti(){
	var url = "wenba_leibiao1.jsp?cid=<%=classid%>&keys=<%= skey%>";
	window.location.href=url;
}
function change_oans(){
	var url = "wenba_leibiao0a.jsp?cid=<%=classid%>&keys=<%= skey%>";
	window.location.href=url;
}

function sousuo_fenlei(){
	var fenleiID = document.getElementById("select_fenlei").value; 
	var keys = document.getElementById("sousuo_key").value;
	var url ="wenba_leibiao.jsp?cid=" +fenleiID +"&keys=" + keys;
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
</script>
<body>
<%@ include file="/include/laodongtop.shtml" %>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="225"><img src="/images/wenba_r4_c2.jpg" width="225" height="27" /></td>
    <td align="center" background="/images/wenba_r4_c3.jpg"><a href="/wenba/wenba_difang.jsp?cid=<%= classid%>&pro=北京" class="bai">地方问答</a></td>
    <td width="1" align="center" background="/images/wenba_r4_c3.jpg"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center" background="/images/wenba_r4_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/wenba/wenba_leibiao.jsp?cid=<%= classid%>&keys="" " class="bai">最新提出问题</a></td>
    <td width="1" align="center" background="/images/wenba_r4_c3.jpg"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center" background="/images/wenba_r4_c3.jpg"><a href="/wenba/wenba_leibiao1.jsp?cid=<%= classid%>&keys="" " class="bai">最新解决问题</a></td>
    <td width="1" align="center" background="/images/wenba_r4_c3.jpg"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center" background="/images/wenba_r4_c3.jpg"><a href="/wenba/wenba_leibiao0a.jsp?cid=<%= classid%>&keys="" " class="bai">零回答问题</a></td>
    <td width="1" align="center" background="/images/wenba_r4_c3.jpg"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center" background="/images/wenba_r4_c3.jpg"><a href="/wenba/wenba_zhuanjia.jsp?cid=<%= classid%>" class="bai">答人团</a></td>
    <td width="192"><img src="/images/wenba_r4_c6.jpg" width="192" height="27" /></td>
  </tr>
</table>
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
                        <td height="25" class="blackc12c">&nbsp;<a href="JavaScript:wenti()">我要问</a></td>
                      </tr>
                      <tr>
                        <td><input type="image" src="/images/wen_ba_r2_c2.jpg" width="38" height="38"/></td>
                        <td>11111111111</td>
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
                          <td><img src="/images/wen_ba_r5_c8.jpg" width="60" height="38" border="0" align="absmiddle" /></td>
                          <td>2222222222</td>
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
                            <td><img src="/images/wen_ba_r6_c13.jpg" width="45" height="38" /></td>
                            <td>333333333</td>
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
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/bai.gif" width="1" height="1" /></td>
        </tr>
        <tr>
          <td height="39" background="/images/wenba_woyaoda_r2_c2.jpg">&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="select_fenlei" style="font-size:12px;color:#000000;width: 170px;border:#d1d1d1 1px solid;" >
             	<option >--请选择--</option>
			<%
				for(int i=0;i<lists.size();i++){
				firstcolumn = (wenbaImpl)lists.get(i);
			%>
				<option value="<%= firstcolumn.getID()%>"><%=StringUtil.gb2iso4View(firstcolumn.getCName()) %></option>
				<%}%>
            </select>&nbsp;&nbsp;<span class="unnamed">
            <input name="sousuo_key" type="text" style="font-size:12px;color:#000000;width: 170px;border:#d1d1d1 1px solid;"  />
&nbsp;      <a href="javascript:sousuo_fenlei()"><img src="/images/wenba_woyaoda_r3_c5.jpg" width="58" height="19" border="0" align="absmiddle" /></a></span></td>
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
                      <td width="248" height="30" align="center" background="/images/wenba_woyaoda_r7_c5.jpg" class="blackc12c"><a href="#">最新提出问题</a></td>
                      <td align="center"><img src="/images/bai.gif" width="1" height="1" /></td>
                      <td width="248" align="center" background="/images/wenba_woyaoda_r7_c7.jpg" class="blackc12c"><a href="#" onclick="change_wenti()">已解决的问题</a></td>
                      <td align="center"><img src="/images/bai.gif" width="1" height="1" /></td>
                      <td width="247" align="center" background="/images/wenba_woyaoda_r7_c10.jpg" class="blackc12c"><a href="#" onclick="change_oans()">零回答的问题</a></td>
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
                        	wenti wt = null;
                        	String date_pro = null;
                        	String color1 = "#ECEDEF";
							String color2 = "#FFFFFF"; 
							String color = "";
                        	List list = iwenba.getSousuoQuestionsPid(end_no,begin_no,classid,0,skey,Pro);
                        	for(int i=0;i<list.size();i++){
                        		if(i%2==0){
									color = color2;
								}else{
									color = color1;
								}
                        		wt = (wenti)list.get(i);
                        		date_pro = wt.getCreatedate().toString();
                    			int posi = date_pro.indexOf(" ");
                    			date_pro = date_pro.substring(0, posi);
                    			String titles = wt.getTitles().trim();  
                           		String str;
                            	if(titles.length()>18) {
                            		str = titles.substring(0,18) + "...";
                            	}else{
                            		str = titles;
                            	}             
                        %>
                        <tr>
                          <td height="25" align="left" bgcolor="<%= color%>">&nbsp;&nbsp;[<%= wt.getCname()%>]</td>
                          <td align="left" bgcolor="<%= color%>">&nbsp;&nbsp;
                          	<a href="/wenba/wenba_finsh.jsp?id=<%= wt.getId()%>&cid=<%= wt.getColumnid()%>&fenlei=<%= linghuida%>"><%=str%></a>&nbsp;&nbsp;
                          	<%if(wt.getFilepath()!=null){ %>
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
		                      			var url = "/wenba/download.jsp?filepath=<%= wt.getFilepath()%>&qid=<%=wt.getId()%>&username=<%= username%>";
		                      			window.location.href=url; 
		                      		}else{}
	                      		}
                      	}
                      </script>
                          <td align="center" bgcolor="<%= color%>"><%= wt.getProvince()%></td>
                          <td align="center" bgcolor="<%= color%>"><%= wt.getXuanshang()%></td>
                          <td align="center" bgcolor="<%= color%>"><%= wt.getAnwsernum()%></td>
                          <td align="center" bgcolor="<%= color%>">[<%= date_pro%>]</td>
                        </tr>
                        <%
                        	}if(wt==null){
                        %>
                        <tr>
                          <td height="25" colspan="5" align="center">对不起！没有您想找的搜索结果，您可以更换搜索条件重新搜索。</td>
                          <td align="center"></td>
                        </tr>
                        <% }%>
                      </table></td>
                    </tr>
                    <tr>
                      <td height="30" align="right" valign="bottom">
                      	<%if(intPageCount>1){ %>
                      		<%if (intPage > 1) {%>
                      		<a href="wenba_leibiao.jsp?page=<%=intPage - 1%>&cid=<%=classid%>&keys=<%= skey%>"><img src="/images/wenba_woyaoda_r9_c12.jpg" width="4" height="7" border="0" align="absmiddle" /></a>&nbsp;
                            <%} %>
                            <!-- 共<%= intPageCount%>页 --> 
	                    	<a href="wenba_leibiao.jsp?page=1&cid=<%=classid%>&keys=<%= skey%>">首页</a>
	                    	<%if (intPage > 1) {%>
	                    	<a href="wenba_leibiao.jsp?page=<%=intPage - 1%>&cid=<%=classid%>&keys=<%= skey%>">上一页</a><%}%>
	                    	<%if (intPage < intPageCount) {%>
	                    	<a href="wenba_leibiao.jsp?page=<%=intPage + 1%>&cid=<%=classid%>&keys=<%= skey%>">下一页</a><%}%>
	                        <a href="wenba_leibiao.jsp?page=<%=intPageCount%>&cid=<%=classid%>&keys=<%= skey%>">尾页</a>&nbsp;
	                        <%if (intPage < intPageCount) {%>
	                    	<a href="wenba_leibiao.jsp?page=<%=intPage + 1%>&cid=<%=classid%>&keys=<%= skey%>"><img src="/images/wenba_woyaoda_r9_c14.jpg" width="4" height="7" border="0" align="absmiddle" /></a>
	                    	<%} %>&nbsp;&nbsp;&nbsp;&nbsp;
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

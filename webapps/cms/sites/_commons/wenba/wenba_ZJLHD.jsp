<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>
<%@ page  import="java.text.*"%>
<%
User user = (User)session.getAttribute("user");
	String username = null;
	int userType = 1;
	if(user==null){
		userType = 0;
		//
	}else{
		username = user.getUsername();
	}
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	wenbaImpl firstcolumn = null;
	List lists = iwenba.getCname();
	String cid = session.getAttribute("zjlhdcid")==null?"0":(String)session.getAttribute("zjlhdcid");
	session.setAttribute("zjlhdcid",null);
	int classid = Integer.parseInt(cid);
	//List top10_wenti = iwenba.getTop10Questions(classid,0);
   // List top10_wenti_jiejue= iwenba.getTop10Questions(classid,1);
    //List top10_wenti_0anwser = iwenba.getTop10Questions0Answer(classid); 
	//String skey = (String)request.getParameter("keys");
	//String Pro = (String)request.getParameter("pro");
	//List top8_wenti = iwenba.getTop8QuestionsWenti(classid);
	//List top8_wenti_xuanshang = iwenba.getTop8QuestionsXuanshang(classid);
	String linghuida = "零回答问题";
	linghuida = URLEncoder.encode(linghuida);
	String jiejue = "最新解决问题";
	jiejue = URLEncoder.encode(jiejue);
	String lanmu = "最新提出问题";
	lanmu = URLEncoder.encode(lanmu);
	
	String thispage = (String)session.getAttribute("LHDthispage")==null?"0":(String)session.getAttribute("LHDthispage");
	String allpages = (String)session.getAttribute("LHDallpages")==null?"0":(String)session.getAttribute("LHDallpages");
	String perpagenum = session.getAttribute("LHDpagenum")==null?"0":(String)session.getAttribute("LHDpagenum");
	List pagelist = (List)session.getAttribute("LHDpagelist");
	if(pagelist==null){
		response.sendRedirect("dolinghuida.jsp");
	}
	String str3 = (String)session.getAttribute("str3");
	
	session.setAttribute("str3",null);
	String provincename = "";
	String columnid = "0";
	String keyword = "";
	if(str3!=null){
	String[] strs = str3.split(",");
	
	
	if(!strs[0].equals("-@"))
		provincename = strs[0];
	if(!strs[1].equals("-@"))
		columnid = strs[1];
	if(!strs[2].equals("-@"))
		keyword = strs[2];
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
<title>中国劳动法务网--地区搜索</title>
<link href="/images/css.css" rel="stylesheet" type="text/css"/>
<link href="/webbuilder/sites/www_fawu_com/images/hr-low.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/JavaScript">
function gonum(){
    	var gopagenum = document.form1.gopagenum.value;
    		
    	if(gopagenum==""){
    		return false;
    	}else{
    		document.form1.thispage.value = gopagenum;
    		document.form1.action = "dolinghuida.jsp?province=<%=provincename %>&fenlei=<%=columnid %>&keyword=<%=keyword %>";
    		document.form1.submit();
    	}
    }
function wenti(){
    var types = <%=userType%>
	if(types==0){
		alert("您没有登陆不能回答，请登陆。");
		return;
	}else{
		window.open("/wenba/wenba_woyaowen.jsp?cid=<%=classid%>");
	}
}

function sousuo(){
	var fenlei = "";
	var province = "";
	var keyword = "";
	fenlei = document.form1.fenlei.value;
	province = document.form1.proname.value;
	keyword = document.form1.sss.value;
	document.form1.action = "dolinghuida.jsp?fenlei="+fenlei+"&province="+province+"&keyword="+keyword;
    document.form1.submit();
}
</script>
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
      
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/bai.gif" width="1" height="1" /></td>
        </tr>
        <tr>
          <td><img src="/images/wen_ba_r18_c3.jpg" align="absmiddle"/>&nbsp;<select name="fenlei" id="">
				<option value="0">--请选择--</option>
				<%
					for(int i=0;i<lists.size();i++){
					firstcolumn = (wenbaImpl)lists.get(i);
				%>
				<option value="<%= firstcolumn.getID()%>"><%=StringUtil.gb2iso4View(firstcolumn.getCName()) %></option>
				<%}%>
          </select></td>
          
         <td><img src="/images/wen_ba_r20_c3.jpg" align="absmiddle" />&nbsp;<select name="proname" style="font-size:12px;color:#000000;width: 130px;border:#d1d1d1 1px solid;">
				<option value="">-请选择城市-</option>
				<%for(int i=0;i<prolist.size();i++){ 
					WenbaBean wb = (WenbaBean)prolist.get(i);
					
				%>
				<option value="<%=wb.getProvince() %>"><%=wb.getProvince() %></option>
				<%} %>
		</select></td>
		
		<td><img src="/images/wen_ba_r22_c3.jpg" align="absmiddle" />&nbsp;<input type="text" name="sss" style="font-size:12px;color:#000000;width: 130px;border:#d1d1d1 1px solid;" value=""/></td>
       	<td><img src="/images/wen_ba_r24_c12.jpg" width="41" height="23" border="0" style="cursor:hand" align="absbottom" onclick="sousuo()"/></td>
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
                      <td width="248" height="30" align="center" background="/images/wenba_woyaoda_r7_c5.jpg" class="blackc12c"><a href="dozuixintiwen.jsp">最新提出问题</a></td>
                      <td align="center"><img src="/images/bai.gif" width="1" height="1" /></td>
                      <td width="248" align="center" background="/images/wenba_woyaoda_r7_c7.jpg" class="blackc12c"><a href="doyijiejue.jsp">已解决的问题</a></td>
                      <td align="center"><img src="/images/bai.gif" width="1" height="1" /></td>
                      <td width="247" align="center" background="/images/wenba_woyaoda_r7_c10.jpg" class="blackc12c">零回答的问题</td>
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
                        	 if(pagelist!=null){
                        	 	for(int i=0;i<pagelist.size();i++){
                        	 		WenbaBean wb = (WenbaBean)pagelist.get(i);
                        %>
                        <tr>
                          <td height="25" align="left">&nbsp;&nbsp;[<%= wb.getCname()%>]</td>
                          <td align="left">&nbsp;&nbsp;
                          	<a href="/wenba/wenba_finsh.jsp?id=<%= wb.getId()%>&cid=<%= wb.getColumnid()%>&fenlei=<%= lanmu%>"><%=wb.getTitle()%></a>&nbsp;&nbsp;
                      </td>
                          <td align="center"><%= wb.getProvince()%></td>
                          <td align="center"><%= wb.getXuanshang()%></td>
                          <td align="center"><%= wb.getAnswernum()%></td>
                          <td align="center">[<%= sdf.format(wb.getCreatedate())%>]</td>
                        </tr>
                        <%
                        	}
                        	}else{
                        %>
                        <tr>
                          <td height="25" colspan="5" align="center">对不起！没有您想找的搜索结果，您可以更换搜索条件重新搜索。</td>
                          <td align="center"></td>
                        </tr>
                        <% }%>
                      </table></td>
                    </tr>
                    
                    <tr>
        				<td width="1036" height="50" align="center" valign="bottom" class="font">
        				<%
							if (Integer.parseInt(thispage) >1){
						%>
                		<a href="dolinghuida.jsp?thispage=1&province=<%=provincename %>&fenlei=<%=columnid %>&keyword=<%=keyword %>">首页</a>&nbsp;&nbsp;&nbsp;<a href="dolinghuida.jsp?thispage=<%=Integer.parseInt(thispage)-1 %>&province=<%=provincename %>&fenlei=<%=columnid %>&keyword=<%=keyword %>">上一页</a>
                		<%
							}
							if (Integer.parseInt(allpages) > Integer.parseInt(thispage)){
							%>
              				&nbsp;&nbsp;<a href="dolinghuida.jsp?thispage=<%=Integer.parseInt(thispage)+1 %>&province=<%=provincename %>&fenlei=<%=columnid %>&keyword=<%=keyword %>">下一页</a>&nbsp;&nbsp; &nbsp;<a href="dolinghuida.jsp?thispage=<%=Integer.parseInt(allpages)%>&province=<%=provincename %>&fenlei=<%=columnid %>&keyword=<%=keyword %>">末页</a>
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
          <td valign="top" background="/images/wen_ba_r18_c33.jpg"><table width="1" border="0" align="center" cellpadding="0" cellspacing="0">
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

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
	List list = iwenba.getCname();
	int classid = ParamUtil.getIntParameter(request,"cid",0);
	//String CID = (String)request.getParameter("cid");
	List top8_wenti = iwenba.getTop8QuestionsWenti(classid);
	List top8_wenti_xuanshang = iwenba.getTop8QuestionsXuanshang(classid);
    
    //List zhuanjialist = iwenba.getzhuangjian();
    List zhuanjialist = (List)session.getAttribute("zhuanjialist");
    if(zhuanjialist==null){
    	response.sendRedirect("dozhuanjia.jsp");
    }
    String thispage = (String)session.getAttribute("zhuanjiathispage")==null?"0":(String)session.getAttribute("zhuanjiathispage");
	String allpages = (String)session.getAttribute("zhuanjiaallpages")==null?"0":(String)session.getAttribute("zhuanjiaallpages");
	String perpagenum = session.getAttribute("zhuanjiapagenum")==null?"0":(String)session.getAttribute("zhuanjiapagenum");
    List ZXTWlist = iwenba.getZuiXinTiWen();
    List LHDlist = iwenba.getLingHuiDa();
    List YJJlist = iwenba.getyijiejue();
    
    List dianjilist = iwenba.getTOP8DianJiShu(classid);
    List gradelist = iwenba.getTop8weekgrade();
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>专&nbsp;&nbsp; 家</title>
<link href="/images/css.css" rel="stylesheet" type="text/css" />
<link href="/webbuilder/sites/www_fawu_com/images/hr-low.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">
function checkZXTW(){
	document.getElementById("ZXTWflag").background = "/images/wenba_woyaoda_r7_c5.jpg";
	document.getElementById("YJJflag").background = "/images/wenba_woyaoda_r7_c7.jpg";
	document.getElementById("LHDflag").background = "/images/wenba_woyaoda_r7_c7.jpg";
	document.getElementById("ZXTW").style.display = "";
	document.getElementById("YJJ").style.display = "none";
	document.getElementById("LHD").style.display = "none";
}
function checkYJJ(){
	document.getElementById("ZXTWflag").background = "/images/wenba_woyaoda_r7_c7.jpg";
	document.getElementById("YJJflag").background = "/images/wenba_woyaoda_r7_c5.jpg";
	document.getElementById("LHDflag").background = "/images/wenba_woyaoda_r7_c7.jpg";
	document.getElementById("ZXTW").style.display = "none";
	document.getElementById("YJJ").style.display = "";
	document.getElementById("LHD").style.display = "none";
}
function checkLHD(){
	document.getElementById("ZXTWflag").background = "/images/wenba_woyaoda_r7_c7.jpg";
	document.getElementById("YJJflag").background = "/images/wenba_woyaoda_r7_c7.jpg";
	document.getElementById("LHDflag").background = "/images/wenba_woyaoda_r7_c5.jpg";
	document.getElementById("ZXTW").style.display = "none";
	document.getElementById("YJJ").style.display = "none";
	document.getElementById("LHD").style.display = "";
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
function gonum(){
    	var gopagenum = document.form1.gopagenum.value;
    		
    	if(gopagenum==""){
    		return false;
    	}else{
    		document.form1.thispage.value = gopagenum;
    		document.form1.action = "dozhuanjia.jsp";
    		document.form1.submit();
    	}
    }
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
          <td valign="top"><img src="/images/wenba_wentifenlei_r2_c2.jpg" width="763" height="6" /></td>
        </tr>
        <tr><td>专家团</td></tr>
        <tr>
        	<td>
        		<table>
        			<%if(zhuanjialist.size()>0){ 
        				for(int i=0;i<zhuanjialist.size();i++){
        					User u = (User)zhuanjialist.get(i);
        					int meilizhi = u.getMeilizhi();
        					String touxian = "";
        					if(meilizhi>1&&meilizhi<=300){
								touxian = "HR学员";
							}else if(meilizhi>300&&meilizhi<=600){
							    touxian = "HR助理";
							}else if(meilizhi>600&&meilizhi<=1000){
							    touxian = "HR专员";
							}else if(meilizhi>1000&&meilizhi<=1500){
							    touxian = "HR主管";
							}else if(meilizhi>1500&&meilizhi<=2000){
							    touxian = "HR经理";
							}else if(meilizhi>2000&&meilizhi<=2500){
							    touxian = "HR高级经理";
							}else if(meilizhi>2500){
							    touxian = "HR总监";
							}
							String introduct = u.getZhuanjiajianjie()==null?"中国劳动法务网资深会员竭诚为您服务！":u.getZhuanjiajianjie();
							if(introduct.length()>50){
								introduct = introduct.substring(0,50);
							}
        			%>
        			<tr>
        				<td>
        					<img src="http://www.hrlaw.com.cn/wenba/images/<%=u.getImgname() %>" width="66" height="88" hspace="2" vspace="2" border="0" />
        				</td>
        				<td>
        					<table>
        						<tr><td><font size="3" color="black"><%=u.getUsername() %></font></td></tr>
        						<tr><td>头衔：<%=touxian %></td></tr>
        						<tr><td>积分：<%=u.getUsergrade() %></td></tr>
        						<tr><td>魅力值：<%=u.getMeilizhi() %></td></tr>
        						<tr><td><a href="/wenba/zhuanjia_info.jsp?userid=<%= u.getUserid()%>&cid=<%= classid%>&imgname=<%=u.getImgname() %>" class="hei">【查看详情】</a></td></tr>
        						<tr><td><a href="wenba_woyaowen.jsp?cid=<%=classid %>&USERID=<%=u.getUserid() %>"><img src="/images/zhuanjiatiwen.gif" border="0"></a></td></tr>
        					</table>
        				</td>
        				<td>
        				<table>
        					<tr><td>
        					<textarea name="textarea1" id="textarea1" rows="8" style="font-size:12px;color:#000000;width: 450px;border:#d1d1d1 1px solid;" readOnly="true"><%=introduct==null?"暂无简介":introduct%></textarea>
        					</td></tr>
        				</table>
        				</td>
        			</tr>
        			
        			<%} }%>
        			<tr>
        				<td width="1036" height="50" align="center" valign="bottom" class="font" colspan="3">
        				<%
							if (Integer.parseInt(thispage) >1){
						%>
                		<a href="dozhuanjia.jsp?thispage=1">首页</a>&nbsp;&nbsp;&nbsp;<a href="dozhuanjia.jsp?thispage=<%=Integer.parseInt(thispage)-1 %>">上一页</a>
                		<%
							}
							if (Integer.parseInt(allpages) > Integer.parseInt(thispage)){
							%>
              				&nbsp;&nbsp;<a href="dozhuanjia.jsp?thispage=<%=Integer.parseInt(thispage)+1 %>">下一页</a>&nbsp;&nbsp; &nbsp;<a href="dozhuanjia.jsp?thispage=<%=Integer.parseInt(allpages)%>">末页</a>
              				<%
							}
							%>
              				&nbsp;当前是第<%=thispage%>页  &nbsp;&nbsp;共<%=allpages%>页&nbsp;
              				到
              				<input name="gopagenum" type="text" class="txtgo" value="" size="2" />
              				页
            				<input name="gobutton" type="button" class="madgo" onClick="gonum()" value="GO" /></td>
      				</tr>
        		</table>
        	</td>
        </tr>
        <tr>
          <td valign="top"><img src="/images/wenba_wentifenlei_r4_c2.jpg" width="763" height="7" /></td>
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
                            <td width="248" height="30" align="center" id="ZXTWflag" background="/images/wenba_woyaoda_r7_c5.jpg" class="blackc12c" onMouseOver="checkZXTW()"><a href="dozuixintiwen.jsp">最新提出问题</a></td>
                            <td align="center"><img src="/images/bai.gif" width="1" height="1" /></td>
                            <td width="248" align="center" id="YJJflag" background="/images/wenba_woyaoda_r7_c7.jpg" class="blackc12c" onMouseOver="checkYJJ()"><a href="doyijiejue.jsp">已解决的问题</a></td>
                            <td align="center"><img src="/images/bai.gif" width="1" height="1" /></td>
                            <td width="247" align="center" id="LHDflag" background="/images/wenba_woyaoda_r7_c10.jpg" class="blackc12c" onMouseOver="checkLHD()"><a href="dolinghuida.jsp">零回答的问题</a></td>
                            
                          </tr>
                      </table></td>
                    </tr>
                    <tr id="ZXTW" style="display:">
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
                                String name_zuixin = "最新提出问题";
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		                    	if(ZXTWlist.size()>0){
		                    		if(ZXTWlist.size()>5){
		                    			for(int i=0;i<5;i++){
	                    					WenbaBean wb = (WenbaBean)ZXTWlist.get(i);
	                    		%>
		                        <tr>
		                          <td height="25" align="left" >&nbsp;&nbsp;[<%= wb.getCname()%>]</td>
		                          <td align="left" >&nbsp;&nbsp;<a href="/wenba/wenba_finsh.jsp?id=<%= wb.getId()%>&cid=<%= wb.getColumnid()%>&fenlei=<%= name_zuixin%>"><%=wb.getTitle()%></a></td>
		                          <td align="center" ><%= wb.getProvince()%></td>
		                          <td align="center" ><%= wb.getXuanshang()%>分</td>
		                          <td align="center" ><%= wb.getAnswernum()%></td>
		                          <td align="center" >[<%= sdf.format(wb.getCreatedate())%>]</td>
		                        </tr>
		                        <%}}else{
		                        		for(int i=0;i<ZXTWlist.size();i++){
		                        			WenbaBean wb = (WenbaBean)ZXTWlist.get(i);
		                        %>
		                        <tr>
		                          <td height="25" align="left" >&nbsp;&nbsp;[<%= wb.getCname()%>]</td>
		                          <td align="left" >&nbsp;&nbsp;<a href="/wenba/wenba_finsh.jsp?id=<%= wb.getId()%>&cid=<%= wb.getColumnid()%>&fenlei=<%= name_zuixin%>"><%=wb.getTitle()%></a></td>
		                          <td align="center" ><%= wb.getProvince()%></td>
		                          <td align="center" ><%= wb.getXuanshang()%>分</td>
		                          <td align="center" ><%= wb.getAnswernum()%></td>
		                          <td align="center" >[<%= sdf.format(wb.getCreatedate())%>]</td>
		                        </tr>
		                        <%}}}%>
		                        <tr><td align="right" colspan="6"><a href="dozuixintiwen.jsp">更多</a></td></tr>
                            </table></td>
                          </tr>
                          </table></td>
                    </tr>
                      <tr id="YJJ" style="display:none">
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
                                String name_yijiejue = "已解决问题";
		                    	if(YJJlist.size()>0){
		                    		if(YJJlist.size()>5){
		                    			for(int i=0;i<5;i++){
	                    					WenbaBean wb = (WenbaBean)YJJlist.get(i);
	                    		%>
		                        <tr>
		                          <td height="25" align="left" >&nbsp;&nbsp;[<%= wb.getCname()%>]</td>
		                          <td align="left" >&nbsp;&nbsp;<a href="/wenba/wenba_finsh.jsp?id=<%= wb.getId()%>&cid=<%= wb.getColumnid()%>&fenlei=<%= name_yijiejue%>"><%=wb.getTitle()%></a></td>
		                          <td align="center" ><%= wb.getProvince()%></td>
		                          <td align="center" ><%= wb.getXuanshang()%>分</td>
		                          <td align="center" ><%= wb.getAnswernum()%></td>
		                          <td align="center" >[<%= sdf.format(wb.getCreatedate())%>]</td>
		                        </tr>
		                        <%}}else{
		                        		for(int i=0;i<YJJlist.size();i++){
		                        			WenbaBean wb = (WenbaBean)YJJlist.get(i);
		                        %>
		                        <tr>
		                          <td height="25" align="left" >&nbsp;&nbsp;[<%= wb.getCname()%>]</td>
		                          <td align="left" >&nbsp;&nbsp;<a href="/wenba/wenba_finsh.jsp?id=<%= wb.getId()%>&cid=<%= wb.getColumnid()%>&fenlei=<%= name_yijiejue%>"><%=wb.getTitle()%></a></td>
		                          <td align="center" ><%= wb.getProvince()%></td>
		                          <td align="center" ><%= wb.getXuanshang()%>分</td>
		                          <td align="center" ><%= wb.getAnswernum()%></td>
		                          <td align="center" >[<%= sdf.format(wb.getCreatedate())%>]</td>
		                        </tr>
		                        <%}}}%>
		                        <tr><td align="right" colspan="6"><a href="doyijiejue.jsp">更多</a></td></tr>
                            </table></td>
                          </tr>
                          
                          
                      </table></td>
                    </tr>
                    
                    <tr id="LHD" style="display:none">
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
                                String name_linghuida = "零回答问题";
		                    	if(LHDlist.size()>0){
		                    		if(LHDlist.size()>5){
		                    			for(int i=0;i<5;i++){
	                    					WenbaBean wb = (WenbaBean)LHDlist.get(i);
	                    		%>
		                        <tr>
		                          <td height="25" align="left" >&nbsp;&nbsp;[<%= wb.getCname()%>]</td>
		                          <td align="left" >&nbsp;&nbsp;<a href="/wenba/wenba_finsh.jsp?id=<%= wb.getId()%>&cid=<%= wb.getColumnid()%>&fenlei=<%= name_linghuida%>"><%=wb.getTitle()%></a></td>
		                          <td align="center" ><%= wb.getProvince()%></td>
		                          <td align="center" ><%= wb.getXuanshang()%>分</td>
		                          <td align="center" ><%= wb.getAnswernum()%></td>
		                          <td align="center" >[<%= sdf.format(wb.getCreatedate())%>]</td>
		                        </tr>
		                        <%}}else{
		                        		for(int i=0;i<LHDlist.size();i++){
		                        			WenbaBean wb = (WenbaBean)LHDlist.get(i);
		                        %>
		                        <tr>
		                          <td height="25" align="left" >&nbsp;&nbsp;[<%= wb.getCname()%>]</td>
		                          <td align="left" >&nbsp;&nbsp;<a href="/wenba/wenba_finsh.jsp?id=<%= wb.getId()%>&cid=<%= wb.getColumnid()%>&fenlei=<%= name_linghuida%>"><%=wb.getTitle()%></a></td>
		                          <td align="center" ><%= wb.getProvince()%></td>
		                          <td align="center" ><%= wb.getXuanshang()%>分</td>
		                          <td align="center" ><%= wb.getAnswernum()%></td>
		                          <td align="center" >[<%= sdf.format(wb.getCreatedate())%>]</td>
		                        </tr>
		                        <%}}}%>
		                        <tr><td align="right" colspan="6"><a href="dolinghuida.jsp">更多</a></td></tr>
                            </table></td>
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

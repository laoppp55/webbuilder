<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>
<%@ page  import="java.text.*"%>
<%
User user = (User)session.getAttribute("user");
	String username = null;
	int userType = 1;
	if(user==null){
		response.sendRedirect("/login.jsp");
		userType = 0;
		//
	}else{
		username = user.getUsername();
	}
int classid = ParamUtil.getIntParameter(request,"cid",0);
String pagenum = session.getAttribute("personpagenum")==null?"0":(String)session.getAttribute("personpagenum");
String allpages = session.getAttribute("personallpages")==null?"0":(String)session.getAttribute("personallpages");
String thispage = session.getAttribute("personthispage")==null?"0":(String)session.getAttribute("personthispage");
String personid = session.getAttribute("personuserid")==null?"0":(String)session.getAttribute("personuserid");
String qwflag = session.getAttribute("qwflag")==null?"0":(String)session.getAttribute("qwflag");
session.setAttribute("personpagenum",null);
session.setAttribute("personallpages",null);
session.setAttribute("personallpages",null);
session.setAttribute("personuserid",null);
session.setAttribute("qwflag",null);
List personlist = (List)session.getAttribute("personpagelist");
IWenbaManager iwenba = wenbaManagerImpl.getInstance();
IUserManager userMgr = UserPeer.getInstance();
List dianjilist = iwenba.getTOP8DianJiShu(classid);
List gradelist = iwenba.getTop8weekgrade();
User peruser = userMgr.getUserinfobyid(Integer.parseInt(personid));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="/images/hr-low.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
}
-->
</style>
<link href="/images/css.css" rel="stylesheet" type="text/css" />

<script type="text/JavaScript">
function wenti(){
    var types = <%=userType%>
	if(types==0){
		alert("您没有登陆不能回答，请登陆。");
		return false;
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
    		document.form1.action = "dopersonQW.jsp?personid=<%=personid%>&qwflag=<%=qwflag%>";
    		document.form1.submit();
    	}
    }
</script>
</head>

<body>
<form name="form1" method="post">
<input type="hidden" name="thispage" value="<%=thispage %>"/>
<%@ include file="/include/laodongtop.shtml" %>
<TABLE cellSpacing=0 cellPadding=0 width=982 align=center border=0>
  <TBODY>
  <TR>
    <TD width=225><IMG height=27  src="/images/wenba_r4_c2.jpg" 
      width=225></TD>
    <TD align=middle background="/images/wenba_r4_c3.jpg"><A 
      class=bai 
      href="http://www.hrlaw.com.cn/wenba/wenba_difang.jsp?cid=0&amp;pro=北京">地方问答</A></TD>
    <TD align=middle width=1 
      background="/images/wenba_r4_c3.jpg"><IMG height=12 
      src="中国劳动法务网%20%20问吧_files/wenba_r5_c5.jpg" width=1 align=absMiddle></TD>
    <TD align=middle background="/images/wenba_r4_c3.jpg">&nbsp;<A 
      class=bai href="http://www.hrlaw.com.cn/wenba/doZXTW.jsp">最新提出问题</A></TD>
    <TD align=middle width=1 
      background="/images/wenba_r4_c3.jpg"><IMG height=12 
      src="中国劳动法务网%20%20问吧_files/wenba_r5_c5.jpg" width=1 align=absMiddle></TD>
    <TD align=middle background="/images/wenba_r4_c3.jpg"><A 
      class=bai href="http://www.hrlaw.com.cn/wenba/doYJJ.jsp">最新解决问题</A></TD>
    <TD align=middle width=1 
      background=中国劳动法务网%20%20问吧_files/wenba_r4_c3.jpg><IMG height=12 
      src="中国劳动法务网%20%20问吧_files/wenba_r5_c5.jpg" width=1 align=absMiddle></TD>
    <TD align=middle background="/images/wenba_r4_c3.jpg"><A 
      class=bai href="http://www.hrlaw.com.cn/wenba/doLHD.jsp">零回答问题</A></TD>
    <TD align=middle width=1 
      background="/images/wenba_r4_c3.jpg"><IMG height=12 
      src="中国劳动法务网%20%20问吧_files/wenba_r5_c5.jpg" width=1 align=absMiddle></TD>
    <TD align=middle background="/images/wenba_r4_c3.jpg"><A 
      class=bai href="http://www.hrlaw.com.cn/wenba/dozhuanjia.jsp">答人团</A></TD>
    <TD width=192><IMG height=27  src="/images/wenba_r4_c6.jpg"
      width=192></TD></TR></TBODY></TABLE>
	  <TABLE cellSpacing=0 cellPadding=0 width=1 align=center border=0>
  <TBODY>
  <TR>
    <TD><IMG height=8 src="中国劳动法务网%20%20问吧_files/bai.gif" 
  width=1></TD></TR></TBODY></TABLE>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="765" align="left" valign="top"><%@ include file="/include/wenbaZJL.jsp"%>
	<TABLE cellSpacing=0 cellPadding=0 width=1 align=center border=0>
        <TBODY>
        <TR>
          <TD><IMG height=10 src="/images/bai.gif" 
        width=1></TD></TR></TBODY></TABLE>
      <table width="765" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left" valign="top"><img src="/images/2009-7-1user-2.jpg" width="765" height="37" /></td>
        </tr>
        <tr>
          <td height="270" align="left" valign="top" background="/images/2009-7-1user-4.jpg">
			  <table width="765" border="0" cellspacing="4" cellpadding="0">
			    <tr>
				    <td><IMG height=10 src="/images/bai.gif" 
        width=1></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
				  <td width="118" align="center" valign="middle">
				  <%if(peruser.getMeilizhi()>1&&peruser.getMeilizhi()<=300)
						  		out.print("<img src=\"images/0.jpg\" width=\"88\" height=\"118\" />");
						  	else if(peruser.getMeilizhi()>300&&peruser.getMeilizhi()<=600)
						  		out.print("<img src=\"images/0.jpg\" width=\"88\" height=\"118\" />");
						  	else if(peruser.getMeilizhi()>600&&peruser.getMeilizhi()<=1000)
						  		out.print("<img src=\"images/0.jpg\" width=\"88\" height=\"118\" />");
						  	else if(peruser.getMeilizhi()>1000&&peruser.getMeilizhi()<=1500)
						  		out.print("<img src=\"images/0.jpg\" width=\"88\" height=\"118\" />");
						  	else if(peruser.getMeilizhi()>1500&&peruser.getMeilizhi()<=2000)
						  		out.print("<img src=\"images/0.jpg\" width=\"88\" height=\"118\" />");
						  	else if(peruser.getMeilizhi()>2000&&peruser.getMeilizhi()<=2500)
						  		out.print("<img src=\"images/0.jpg\" width=\"88\" height=\"118\" />");
						  	else if(peruser.getMeilizhi()>2500)
						  		out.print("<img src=\"images/0.jpg\" width=\"88\" height=\"118\" />");
						   %>
				  
				  </td>
				  <td width="129" align="left" valign="top">
						  <TABLE>
						<TBODY>
						<TR>
						  <TD><FONT color=black size=3><%=peruser.getUsername() %></FONT></TD></TR>
						<TR>
						  <TD>头衔：
						  <%if(peruser.getMeilizhi()>1&&peruser.getMeilizhi()<=300)
						  		out.print("HR学员");
						  	else if(peruser.getMeilizhi()>300&&peruser.getMeilizhi()<=600)
						  		out.print("HR助理");
						  	else if(peruser.getMeilizhi()>600&&peruser.getMeilizhi()<=1000)
						  		out.print("HR专员");
						  	else if(peruser.getMeilizhi()>1000&&peruser.getMeilizhi()<=1500)
						  		out.print("HR主管");
						  	else if(peruser.getMeilizhi()>1500&&peruser.getMeilizhi()<=2000)
						  		out.print("HR经理");
						  	else if(peruser.getMeilizhi()>2000&&peruser.getMeilizhi()<=2500)
						  		out.print("高级HR经理");
						  	else if(peruser.getMeilizhi()>2500)
						  		out.print("HR总监");
						   %>
							</TD></TR>
						<TR>
						  <TD>积分：<%=peruser.getUsergrade() %></TD></TR>
						<TR>
					  <TD>魅力值：<%=peruser.getMeilizhi()%></TD></TR></TBODY></TABLE>				  </td>
				  <td width="502" align="left" valign="top">
				  <textarea id=textarea1 style="BORDER-RIGHT: #d1d1d1 1px solid; BORDER-TOP: #d1d1d1 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #d1d1d1 1px solid; WIDTH: 480px; COLOR: #000000; BORDER-BOTTOM: #d1d1d1 1px solid" name=textarea1 rows=8 readonly>中国劳动法务网，如果您回答其他人的问题采纳，那么您就可能进入我们的周积分排行榜。
				</textarea></td>
				</tr>
			  </table>
		  </td>
        </tr>
        <tr>
          <td align="left" valign="top"><img src="/images/2009-7-1user-3.jpg" width="765" height="8" /></td>
        </tr>
      </table>
	  	<TABLE cellSpacing=0 cellPadding=0 width=1 align=center border=0>
        <TBODY>
        <TR>
          <TD><IMG height=10 src="/images/bai.gif" 
        width=1></TD></TR></TBODY></TABLE>
			<table width="765" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td height="40" align="left" valign="top" background="/images/2009-7-1user-6.jpg">
				<table width="765" border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td width="7"><IMG height=10 src="/images/bai.gif" 
			width=7></td>
					<td width="374"></td>
					<td width="2"><IMG height=10 src="/images/bai.gif" 
			width=2></td>
					<td width="374"></td>
					<td width="8"><IMG height=10 src="/images/bai.gif" 
			width=8></td>
				  </tr>
				  <tr>
				  <%if(qwflag.equals("0")) {%>
					<td></td>
					<td height="30" align="center" valign="bottom" background="/images/2009-7-1user-9.jpg" class="blackc12c"><a href="dopersonQW.jsp?qwflag=0&personid=<%=personid %>">他的提问</a></td>
					<td></td>
					<td align="center" valign="bottom" background="/images/2009-7-1user-12.jpg" class="blackc12c"><a href="dopersonQW.jsp?qwflag=1&personid=<%=personid %>">他的回答</a></td>
					<td></td>
					<%}else{ %>
					<td></td>
					<td height="30" align="center" valign="bottom" background="/images/2009-7-1user-12.jpg" class="blackc12c"><a href="dopersonQW.jsp?qwflag=0&personid=<%=personid %>">他的提问</a></td>
					<td></td>
					<td align="center" valign="bottom" background="/images/2009-7-1user-9.jpg" class="blackc12c"><a href="dopersonQW.jsp?qwflag=1&personid=<%=personid %>">他的回答</a></td>
					<td></td>
					<%} %>
				  </tr>
				</table>
				
			</td>
		  </tr>
		  <tr>
			<td align="left" valign="top" background="/images/2009-7-1user-7.jpg">
				<table width="765" border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td width="7"></td>
					<td>
					<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR id=ZXTW>
                      <TD height="298" vAlign=top>
                        <TABLE cellSpacing=0 cellPadding=0 width="99%" 
                        align=center border=0>
                          <TBODY>
                          <TR>
                            <TD vAlign=top>
                              <TABLE cellSpacing=2 cellPadding=0 width="100%" 
                              border=0>
                                <TBODY>
                                <TR>
                                <TD align=middle width=60 bgColor=#ecedef height=25>分类</TD>
                                <TD align=middle bgColor=#ecedef>标题</TD>
                                <TD align=middle width=120 bgColor=#ecedef>地区</TD>
                                <TD align=middle width=80 bgColor=#ecedef>悬赏</TD>
                                <TD align=middle width=80 bgColor=#ecedef>回答</TD>
                                <TD align=middle width=100 bgColor=#ecedef>时间</TD></TR>
                                <%if(personlist!=null){
                                	for(int i=0;i<personlist.size();i++){
                                		WenbaBean wb = (WenbaBean)personlist.get(i);
                                		
                                 %>
                                <TR>
                                <TD align=left height=22>&nbsp;&nbsp;[<%=wb.getCname()==null?"":wb.getCname() %>]</TD>
                                <TD align=left>&nbsp;&nbsp;<A 
                                href="http://www.hrlaw.com.cn/wenba/wenba_finsh.jsp?id=2941&amp;cid=0&amp;fenlei=最新提出问题"><%=wb.getTitle()==null?"&nbsp;":wb.getTitle() %>
                                </A></TD>
                                <TD align=middle><%=wb.getProvince()%></TD>
                                <TD align=middle><%=wb.getXuanshang()%></TD>
                                <TD align=middle><%=wb.getAnswernum()%></TD>
                                <TD align=middle>[<%=wb.getCreatedate()%>]</TD></TR>
                                <TR>
                                <%}} %>
                                <tr>
        				<td height="24" align="right" valign="bottom" class="font" colspan="6">
        				<%
							if (Integer.parseInt(thispage) >1){
						%>
                		<a href="dopersonQW.jsp?thispage=1&personid=<%=personid %>&qwflag=<%=qwflag %>">首页</a>&nbsp;&nbsp;&nbsp;<a href="dopersonQW.jsp?thispage=<%=Integer.parseInt(thispage)-1 %>&personid=<%=personid %>&qwflag=<%=qwflag %>">上一页</a>
                		<%
							}
							if (Integer.parseInt(allpages) > Integer.parseInt(thispage)){
							%>
              				&nbsp;&nbsp;<a href="dopersonQW.jsp?thispage=<%=Integer.parseInt(thispage)+1 %>&personid=<%=personid %>&qwflag=<%=qwflag %>">下一页</a>&nbsp;&nbsp; &nbsp;<a href="dopersonQW.jsp?thispage=<%=Integer.parseInt(allpages)%>&personid=<%=personid %>&qwflag=<%=qwflag %>">末页</a>
              				<%
							}
							%>
              				&nbsp;当前是第<%=thispage%>页  &nbsp;&nbsp;共<%=allpages%>页&nbsp;
              				到
              				<input name="gopagenum" type="text" class="txtgo" value="" size="2" />
              				页
            				<input name="gobutton" type="button" class="madgo" onClick="gonum()" value="GO" /></td>
      				</tr>
                      </TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
                    </TR></TBODY></TABLE>
					
					</td>
					<td width="7">
					</td>
				  </tr>
				</table>
			</td>
		  </tr>
		  <tr>
			<td><img src="/images/2009-7-1user-8.jpg" /></td>
		  </tr>
	  </table>

    </td>
    <TD vAlign=top width=10><IMG height=1 
      src="/images/bai.gif" width=10></TD>
    <TD vAlign=top width=210>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD background=/images/bg-4.gif height=159>
            <TABLE cellSpacing=0 cellPadding=0 width=190 align=center 
              border=0><TBODY>
              <TR>
                <TD align=middle width=63 height=45><A 
                  href="http://www.hrlaw.com.cn/yuyinzixun/"><IMG height=44 
                  src="/images/icon-1.gif" width=48 
                border=0></A></TD>
                <TD align=middle width=63><A 
                  href="http://www.hrlaw.com.cn/mobile/sjindex.jsp"><IMG 
                  height=43 src="/images/icon-2.gif" width=49 
                  border=0></A></TD>
                <TD align=middle><A 
                  href="http://www.hrlaw.com.cn/wenba/wenba.jsp"><IMG height=36 
                  src="/images/icon-3.gif" width=35 
                border=0></A></TD></TR>
              <TR>
                <TD class=blackc12 align=middle height=20><A 
                  href="http://www.hrlaw.com.cn/yuyinzixun/">语音咨询</A></TD>
                <TD class=blackc12 align=middle><A 
                  href="http://www.hrlaw.com.cn/mobile/sjindex.jsp">手机咨询</A></TD>
                <TD class=blackc12 align=middle><A 
                  href="http://www.hrlaw.com.cn/wenba/wenba.jsp">问 吧</A> </TD></TR>
              <TR>
                <TD vAlign=top align=middle><IMG height=5 
                  src="/images/bai.gif" width=1></TD>
                <TD vAlign=top align=middle><IMG height=5 
                  src="/images/bai.gif" width=1></TD>
                <TD vAlign=top align=middle><IMG height=5 
                  src="/images/bai.gif" width=1></TD></TR>
              <TR>
                <TD align=middle height=45><IMG height=38 
                  src="/images/icon-4.gif" width=32 border=0></TD>
                <TD align=middle><IMG height=39 
                  src="/images/icon-5.gif" width=40 border=0></TD>
                <TD align=middle><A 
                  href="http://www.hrlaw.com.cn/bookstore/"><IMG height=40 
                  src="/images/icon-6.gif" width=37 
                border=0></A></TD></TR>
              <TR>
                <TD class=blackc12 align=middle height=20><A 
                  href="http://www.hrlaw.com.cn/faguichaxun/">专业搜索</A></TD>
                <TD class=blackc12 align=middle>为您服务</TD>
                <TD class=blackc12 align=middle><A 
                  href="http://www.hrlaw.com.cn/bookstore/">精品图书</A></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width=1 align=center border=0>
        <TBODY>
        <TR>
          <TD><IMG height=10 src="/images/bai.gif" 
        width=1></TD></TR></TBODY></TABLE>
      <TABLE class=huibian1 cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD vAlign=top 
            background=/images/wen_ba_r18_c33.jpg><TABLE 
            cellSpacing=0 cellPadding=0 width=1 align=center border=0>
              <TBODY>
              <TR>
                <TD><IMG height=5 src="/images/bai.gif" 
                  width=1></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=180 align=center 
              border=0><TBODY>
              <TR>
                <TD height=25><IMG height=14 
                  src="/images/wen_ba_r19_c34.jpg" width=15 
                  align=absMiddle>&nbsp;<A 
                  href="http://www.hrlaw.com.cn/wenba/wenba_zhuanjia.jsp#">如何提问？</A></TD></TR>
              <TR>
                <TD height=25><IMG height=14 
                  src="/images/wen_ba_r21_c35.jpg" width=15 
                  align=absMiddle>&nbsp;<A 
                  href="http://www.hrlaw.com.cn/wenba/wenba_zhuanjia.jsp#">如何回答？</A></TD></TR>
              <TR>
                <TD height=25><IMG height=14 
                  src="/images/wen_ba_r23_c35.jpg" width=15 
                  align=absMiddle>&nbsp;<A 
                  href="http://www.hrlaw.com.cn/wenba/wenba_zhuanjia.jsp#">什么是专家解疑？</A></TD></TR>
              <TR>
                <TD height=25><IMG height=14 
                  src="/images/wen_ba_r25_c35.jpg" width=15 
                  align=absMiddle>&nbsp;<A 
                  href="http://www.hrlaw.com.cn/wenba/wenba_zhuanjia.jsp#">规范/积分规则是什么？</A></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=1 align=center border=0>
              <TBODY>
              <TR>
                <TD><IMG height=5 src="/images/bai.gif" 
                  width=1></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width=1 align=center border=0>
        <TBODY>
        <TR>
          <TD><IMG height=10 src="/images/bai.gif" 
        width=1></TD></TR></TBODY></TABLE>
      <TABLE class=huibian1 cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD background=/images/wen_ba_r30_c34.jpg 
            height=24>&nbsp;<A class=blackc12c 
            href="http://www.hrlaw.com.cn/wenba/wenba_zhuanjia.jsp#">本周问题排行榜</A></TD></TR>
        <TR>
          <TD height="210" vAlign=top 
            background=/images/wen_ba_r18_c33.jpg><TABLE 
            cellSpacing=0 cellPadding=0 width=1 align=center border=0>
              <TBODY>
              <TR>
                <TD><IMG height=5 src="/images/bai.gif" 
                  width=1></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=185 align=center 
              border=0><TBODY>
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
                
                </TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=1 align=center border=0>
              <TBODY>
              <TR>
        <TD><IMG height=5 src="/images/bai.gif" 
                  width=1></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width=1 align=center border=0>
        <TBODY>
        <TR>
          <TD><IMG height=10 src="/images/bai.gif" 
        width=1></TD></TR></TBODY></TABLE>
      <TABLE class=huibian1 cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR class=blackc12>
          <TD background=/images/wen_ba_r30_c34.jpg 
            height=24><A class=blackc12 
            href="http://www.hrlaw.com.cn/wenba/wenba_zhuanjia.jsp#">&nbsp;</A><A 
            class=blackc12c 
            href="http://www.hrlaw.com.cn/wenba/wenba_zhuanjia.jsp#">本周积分排行榜</A></TD></TR>
        <TR>
          <TD height="213" vAlign=top 
            background=/images/wen_ba_r18_c33.jpg><TABLE 
            cellSpacing=0 cellPadding=0 width=1 align=center border=0>
              <TBODY>
              <TR>
                <TD><IMG height=5 src="/images/bai.gif" 
                  width=1></TD></TR></TBODY></TABLE>
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
            <TABLE cellSpacing=0 cellPadding=0 width=1 align=center border=0>
              <TBODY>
              <TR>
    <TD><IMG height=5 src="/images/bai.gif" 
                  width=1></TD></TR></TBODY></TABLE></TD>
        </TR></TBODY></TABLE></TD>
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

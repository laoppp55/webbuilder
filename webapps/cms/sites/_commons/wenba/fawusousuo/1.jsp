<%@ page language="java" import="java.util.*,java.sql.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.wenba.*" %>
<html>
<head>
<link href="images/css.css" rel="stylesheet" type="text/css" />
<link href="/images/hr-low.css" rel="stylesheet" type="text/css"/>

<%
	//法规、案例-498、知识问答-481、论文-497
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	String keys = (String)request.getParameter("key_w");
	String FenLei_t = (String)request.getParameter("fa_ss");
	String FenLei = (String)request.getParameter("fa_fl");////1 劳动合同 2工资 3社会保险 4招聘培训 5劳动保护 6仲裁监察
	int fa_fl = Integer.parseInt(FenLei);
	List list =null;
	Sousuo ss =null;
	int columnid = 0;
	if(FenLei.equals("1")&&FenLei_t.equals("597")){
		columnid = 598;
	}
	if(FenLei.equals("1")&&FenLei_t.equals("489")){
		columnid = 490;
	}
	if(FenLei.equals("1")&&FenLei_t.equals("481")){
		columnid = 482;
	}
	if(FenLei.equals("1")&&FenLei_t.equals("497")){
		columnid = 506;
	}if(FenLei.equals("2")&&FenLei_t.equals("597")){
		columnid = 599;
	}if(FenLei.equals("2")&&FenLei_t.equals("489")){
		columnid = 491;
	}if(FenLei.equals("2")&&FenLei_t.equals("481")){
		columnid = 483;
	}if(FenLei.equals("2")&&FenLei_t.equals("497")){
		columnid = 507;
	}if(FenLei.equals("3")&&FenLei_t.equals("597")){
		columnid = 600;
	}if(FenLei.equals("3")&&FenLei_t.equals("489")){
		columnid = 495;
	}if(FenLei.equals("3")&&FenLei_t.equals("481")){
		columnid = 487;
	}if(FenLei.equals("3")&&FenLei_t.equals("497")){
		columnid = 511;
	}if(FenLei.equals("4")&&FenLei_t.equals("597")){
		columnid = 602;
		int columnid1= 603;
	}if(FenLei.equals("4")&&FenLei_t.equals("489")){
		columnid = 511;
	}if(FenLei.equals("4")&&FenLei_t.equals("481")){
		columnid = 511;
	}if(FenLei.equals("4")&&FenLei_t.equals("497")){
		columnid = 511;
	}if(FenLei.equals("5")&&FenLei_t.equals("597")){
		columnid = 601;
	}if(FenLei.equals("5")&&FenLei_t.equals("489")){
		columnid = 492;
	}if(FenLei.equals("5")&&FenLei_t.equals("481")){
		columnid = 484;
	}if(FenLei.equals("5")&&FenLei_t.equals("497")){
		columnid = 508;
	}
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
	    List list_z = iwenba.Cms_sousuo(keys,columnid);
	    intRowCount = list_z.size();
	    intPageCount = (intRowCount+intPageSize-1) / intPageSize; //计算总共要分多少页
			if(intPage>intPageCount) {
				intPage = intPageCount; //调整待显示的页码 
			}
%>
</head>
<body>
<%@ include file="/include/laodongtop.shtml"%>
<%if(fa_fl==1){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/laodonghetong_r2_c2.jpg" width="225" height="29" /></td>
    <td align="center" background="/images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">法规</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">案例</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">知道问答</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">论文</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">问吧</a></td>
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
<%if(fa_fl==2){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/gongzi_r2_c2.jpg" width="225" height="29" /></td>
   <td align="center" background="/images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">法规</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">案例</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">知道问答</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">论文</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">问吧</a></td>
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
<%if(fa_fl==3){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/shehuibaoxian_r2_c2.jpg" width="225" height="29" /></td>
   <td align="center" background="images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">法规</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">案例</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">知道问答</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">论文</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">问吧</a></td>
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
<%if(fa_fl==4){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/zhaopingpeixun_r2_c2.jpg" width="226" height="29" /></td>
    <td align="center" background="images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">法规</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">案例</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">知道问答</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">论文</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">问吧</a></td>
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
<%if(fa_fl==5){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/laodongbaohu_r2_c2.jpg" width="225" height="29" /></td>
    <td align="center" background="images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">法规</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">案例</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">知道问答</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">论文</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">问吧</a></td>
  
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
<%if(fa_fl==6){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/zhongcaijiancha_r2_c2.jpg" width="225" height="29" /></td>
  <td align="center" background="/images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">法规</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">案例</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">知道问答</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">论文</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">问吧</a></td>
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td height="33" background="/images/3_faluchaxun_r3_c4.jpg" class="black12b">&nbsp;&nbsp;
    		<img src="/images/3_faluchaxun_r4_c6.jpg" width="7" height="11" align="absmiddle"/>&nbsp;劳动合同案例
        </td>
    </tr>
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<%
				list = iwenba.Cms_select(end_no,begin_no,keys,columnid); 
				String color1 = "#f3f1f2";
               	String color2 = "#FFFFFF";
                String color = "";
            	for(int i=0;i<list.size();i++){
                	ss=(Sousuo)list.get(i);
                	if(i%2==0){
						color = color1;
					}else{
						color = color2;
					}
					String date = ss.getCreatedate().toString();
					int posi = date.indexOf(" "); 
					date = date.substring(0,posi);
					String date_url = date.replace("-","");
					String url = ss.getDirname() +date_url +"/"+ss.getId()+".shtml";
             %>
				<tr>
					<td bgcolor="<%= color%>">
						<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td  width="15" height="25">
                                	<img src="/images/3_faluchaxun_r9_c6.jpg" width="3" height="3" align="absmiddle"/>
                            	</td>
                            	<td><a href="<%= url%>"><%= ss.getMaintitle()%></a></td>
                            	<td width="80" align="center"><%= date%></td>
                        	</tr>
                    	</table>
                	 </td>
				</tr>
				<%}%>
			</table>
		</td>
	</tr>
	<tr>
		<td height="25" bgcolor="#F3F1F2">&nbsp;</td>
	</tr>
	<tr>
		<td height="35" align="right">
		总页数:<%= intPageCount%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 当前页：<%= intPage%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="1.jsp?page=1&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>&fa_fl=<%= FenLei%>">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%if (intPage > 1) {%>
		<a href="1.jsp?page=<%=intPage - 1%>&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>&fa_fl=<%= FenLei%>">上一页</a>&nbsp;&nbsp;&nbsp;
		<%}%>
		<%if (intPage < intPageCount) {%>
		<a href="1.jsp?page=<%=intPage + 1%>&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>&fa_fl=<%= FenLei%>">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%}%>
		<a href="1.jsp?page=<%=intPageCount%>&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>&fa_fl=<%= FenLei%>">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
</table>															
</body>
</html>
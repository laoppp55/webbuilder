<%@ page language="java" import="java.util.*,java.sql.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.wenba.*" %>
<%@ page import="java.net.*" %>
<html>
<head>
<link href="/images/css.css" rel="stylesheet" type="text/css" />
<link href="/images/hr-low.css" rel="stylesheet" type="text/css"/>

<%
	//���桢����-498��֪ʶ�ʴ�-481������-497
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	String keys = (String)request.getParameter("key_w");
	String FenLei_t = (String)request.getParameter("fa_ss");
	String FenLei = (String)request.getParameter("fa_fl");////1 �Ͷ���ͬ 2���� 3��ᱣ�� 4��Ƹ��ѵ 5�Ͷ����� 6�ٲü��
	int fa_fl = Integer.parseInt(FenLei);
	
	String biaoti = null;
	if(FenLei.equals("1")){
		biaoti = "�Ͷ���ͬ";
	}if(FenLei.equals("2")){
		biaoti = "����";
	}if(FenLei.equals("3")){
		biaoti = "��ᱣ��";
	}if(FenLei.equals("4")){
		biaoti = "��Ƹ/��ѵ";
	}if(FenLei.equals("5")){
		biaoti = "�Ͷ�����";
	}if(FenLei.equals("6")){
		biaoti = "�ٲ�/���";
	}
	
	List list =null;
	Sousuo ss_wenba = null;
	Sousuo ss =null;
	String columnid = "";
	if(FenLei.equals("1")&&FenLei_t.equals("597")){
		columnid = " (columnid=598 or columnid=550) ";
	}
	if(FenLei.equals("1")&&FenLei_t.equals("489")){
		columnid = " columnid=490 ";
	}
	if(FenLei.equals("1")&&FenLei_t.equals("481")){
		columnid = " columnid=482 ";
	}
	if(FenLei.equals("1")&&FenLei_t.equals("497")){
		columnid = " columnid=506 ";
	}if(FenLei.equals("2")&&FenLei_t.equals("597")){
		columnid = " (columnid=599 or columnid=551) ";
	}if(FenLei.equals("2")&&FenLei_t.equals("489")){
		columnid = " columnid=491 ";
	}if(FenLei.equals("2")&&FenLei_t.equals("481")){
		columnid = " columnid=483 ";
	}if(FenLei.equals("2")&&FenLei_t.equals("497")){
		columnid = " columnid=507 ";
	}if(FenLei.equals("3")&&FenLei_t.equals("597")){
		columnid = " (columnid=600 or columnid=557) ";
	}if(FenLei.equals("3")&&FenLei_t.equals("489")){
		columnid = " columnid=495 ";
	}if(FenLei.equals("3")&&FenLei_t.equals("481")){
		columnid = " columnid=487 ";
	}if(FenLei.equals("3")&&FenLei_t.equals("497")){
		columnid = " columnid=511 ";
	}if(FenLei.equals("4")&&FenLei_t.equals("597")){
		columnid = " (columnid=602 or columnid=553 or columnid=554) ";
	}if(FenLei.equals("4")&&FenLei_t.equals("489")){//����
		columnid = " (columnid=582 or columnid=583) ";
	}if(FenLei.equals("4")&&FenLei_t.equals("481")){
		columnid = " (columnid=578 or  columnid=579) ";
	}if(FenLei.equals("4")&&FenLei_t.equals("497")){
		columnid = " columnid=509 ";
	}if(FenLei.equals("5")&&FenLei_t.equals("597")){
		columnid = " (columnid=601 or columnid=552) ";
	}if(FenLei.equals("5")&&FenLei_t.equals("489")){
		columnid = " columnid=492 ";
	}if(FenLei.equals("5")&&FenLei_t.equals("481")){
		columnid = " columnid=484 ";
	}if(FenLei.equals("5")&&FenLei_t.equals("497")){
		columnid = " columnid=508 ";
	}
	if(FenLei.equals("6")&&FenLei_t.equals("597")){
		columnid = " (columnid=604 or columnid=605 or columnid=555 or columnid=556) ";
	}if(FenLei.equals("6")&&FenLei_t.equals("489")){
		columnid = " (columnid=584 columnid=585)";
	}if(FenLei.equals("6")&&FenLei_t.equals("481")){
		columnid = " (columnid=580 or  columnid=581) ";
	}if(FenLei.equals("6")&&FenLei_t.equals("497")){
		columnid = " columnid=510 ";
	}
%>
<% 
		int intRowCount=0;  //�ܵļ�¼��
		int intPageCount=0; //�ܵ�ҳ��
		int intPageSize=10; //ÿҳ��ʾ�ļ�¼��
		int intPage; //��ǰ��ʾҳ   
		int begin_no=0; //��ʼ��rownum��¼��
		int end_no=0;  //������rownum��¼��
		String strPage = request.getParameter("page"); //ȡ��ǰ��ʾҳ�� 
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
	    List list_z = null;
	    if(FenLei_t.equals("498")){
	    	list_z = iwenba.Cms_con_wenba(keys);
	    }else{
	    	list_z=iwenba.Cms_con(keys,columnid); 
	    }
	    
	    intRowCount = list_z.size();
	    intPageCount = (intRowCount+intPageSize-1) / intPageSize; //�����ܹ�Ҫ�ֶ���ҳ
			if(intPage>intPageCount) {
				intPage = intPageCount; //��������ʾ��ҳ�� 
			}
%>
</head>
<body>
<%@ include file="/include/laodongtop.shtml"%>
<%if(fa_fl==1){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/laodonghetong_r2_c2.jpg" width="225" height="29" /></td>
    <td align="center" background="/images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">֪���ʴ�</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">�ʰ�</a></td>
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
<%if(fa_fl==2){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/gongzi_r2_c2.jpg" width="225" height="29" /></td>
   <td align="center" background="/images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">֪���ʴ�</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">�ʰ�</a></td>
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
<%if(fa_fl==3){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/shehuibaoxian_r2_c2.jpg" width="225" height="29" /></td>
   <td align="center" background="images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">֪���ʴ�</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">�ʰ�</a></td>
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
<%if(fa_fl==4){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/zhaopingpeixun_r2_c2.jpg" width="226" height="29" /></td>
    <td align="center" background="images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">֪���ʴ�</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">�ʰ�</a></td>
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
<%if(fa_fl==5){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/laodongbaohu_r2_c2.jpg" width="225" height="29" /></td>
    <td align="center" background="images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">֪���ʴ�</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">�ʰ�</a></td>
  
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
<%if(fa_fl==6){%>
<table width="982" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/laodonghetong_r2_c3.jpg">
  <tr>
    <td width="225"><img src="/images/zhongcaijiancha_r2_c2.jpg" width="225" height="29" /></td>
  <td align="center" background="/images/laodonghetong_r2_c3.jpg"><img src="/images/wenba_r6_c5.jpg" width="4" height="7" align="absmiddle" />&nbsp;<a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=1" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=2" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=3" class="bai">֪���ʴ�</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="/gedifagui.jsp?flag=<%=fa_fl%>&ci=4" class="bai">����</a></td>
    <td width="1" align="center"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center"><a href="#" class="bai">�ʰ�</a></td>
    <td width="6"><img src="/images/menu_right_lj.jpg" width="6" height="29" /></td>
  </tr>
</table>
<%}%>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td height="33" background="/images/3_faluchaxun_r3_c4.jpg" class="black12b">&nbsp;&nbsp;
    		<img src="/images/3_faluchaxun_r4_c6.jpg" width="7" height="11" align="absmiddle"/>&nbsp;<%= biaoti%>
        </td>
    </tr>
    <%
		if(FenLei_t.equals("498")){
			list = iwenba.Cms_select_wenba(end_no,begin_no,keys); 
			String color_1 = "#f3f1f2";
            String color_2 = "#FFFFFF";
            String color_3 = "";
			for(int t=0;t<list.size();t++){
			if(t%2==0){
						color_3 = color_1;
					}else{
						color_3 = color_2;
					}
			ss_wenba=(Sousuo)list.get(t);
			String date = ss_wenba.getCreatedate().toString();
			int posi = date.indexOf(" "); 
			date = date.substring(0,posi);
			String fenlei ;
			String jiejue = URLEncoder.encode("���½������");
			String lanmu = URLEncoder.encode("�����������");
			if(ss_wenba.getStatus()==0){
				fenlei = jiejue;
			}else{
				fenlei= lanmu;
			}
			
	%> 
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td bgcolor="<%= color_3%>">
						<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td  width="15" height="25">
                                	<img src="/images/3_faluchaxun_r9_c6.jpg" width="3" height="3" align="absmiddle"/>
                            	</td>
                            	<td><a href="/wenba/wenba_finsh.jsp?id=<%= ss_wenba.getId()%>&cid=<%= ss_wenba.getCid()%>&fenlei=<%= fenlei%>"><%= ss_wenba.getMaintitle()%></a></td>
                            	<td width="80" align="center"><%= date%></td>
                        	</tr>
                    	</table>
                	 </td>
				</tr>
				
			</table>
		</td>
	</tr>
	<%}
		}else{
	%>
	<%
				list = iwenba.Cms_select_to(end_no,begin_no,keys,columnid); 
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
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
			
			</table>
		</td>
	</tr>
	<%}}%>
	<tr>
		<td height="25" bgcolor="#F3F1F2">&nbsp;</td>
	</tr>
	<%if(ss==null&&ss_wenba==null){%>
	<tr>
		<td bgcolor="#f3f1f2">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		û����Ҫ�����Ľ����
		</td>
	</tr>
	<%}else{%>
	<tr>
		<td height="35" align="right">
		��ҳ��:<%= intPageCount%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ��ǰҳ��<%= intPage%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="2.jsp?page=1&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>&fa_fl=<%= FenLei%>">��ҳ</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%if (intPage > 1) {%>
		<a href="2.jsp?page=<%=intPage - 1%>&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>&fa_fl=<%= FenLei%>">��һҳ</a>&nbsp;&nbsp;&nbsp;
		<%}%>
		<%if (intPage < intPageCount) {%>
		<a href="2.jsp?page=<%=intPage + 1%>&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>&fa_fl=<%= FenLei%>">��һҳ</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%}%>
		<a href="2.jsp?page=<%=intPageCount%>&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>&fa_fl=<%= FenLei%>">βҳ</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<%} %>
</table>	
<%@ include file="/include/low.shtml"%>											
</body>
</html>
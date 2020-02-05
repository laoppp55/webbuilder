<%@ page language="java" import="java.util.*,java.sql.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.wenba.*" %>
<%@ page import="java.net.*" %>
<html>
<head>
<link href="/images/css.css" rel="stylesheet" type="text/css" />
<link href="/images/hr-low.css" rel="stylesheet" type="text/css"/>

<%
	//法规、案例-498、知识问答-481、论文-497
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	String keys = (String)request.getParameter("key_w");
	String FenLei_t = (String)request.getParameter("fa_ss");
	int cid = Integer.parseInt(FenLei_t);
	List list =null;
	Sousuo ss =null;
	String biaoti = null;
	if(FenLei_t.equals("513")){
		biaoti = "劳动合同";
	}if(FenLei_t.equals("514")){
		biaoti = "工资";
	}if(FenLei_t.equals("515")){
		biaoti = "劳动保护";
	}if(FenLei_t.equals("516")){
		biaoti = "招聘与就业";
	}if(FenLei_t.equals("517")){
		biaoti = "仲裁与监察";
	}if(FenLei_t.equals("518")){
		biaoti = "养老保险";
	}if(FenLei_t.equals("519")){
		biaoti = "医疗保险";
	}if(FenLei_t.equals("520")){
		biaoti = "工伤保险";
	}if(FenLei_t.equals("521")){
		biaoti = "失业保险";
	}if(FenLei_t.equals("522")){
		biaoti = "生育保险";
	}
%>
<% 
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
	    List list_z = null;
	    list_z = iwenba.Cms_yanwen_con(keys,cid);
	    intRowCount = list_z.size();
	    intPageCount = (intRowCount+intPageSize-1) / intPageSize; //计算总共要分多少页
			if(intPage>intPageCount) {
				intPage = intPageCount; //调整待显示的页码 
			}
%>
</head>
<body>
<%@ include file="/include/laodongtop.shtml"%>
<%@ include file="/include/yaowencibaohanwenjian.shtml" %>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td height="33" background="/images/3_faluchaxun_r3_c4.jpg" class="black12b">&nbsp;&nbsp;
    		<img src="/images/3_faluchaxun_r4_c6.jpg" width="7" height="11" align="absmiddle"/>&nbsp;&nbsp;<%= biaoti%>
        </td>
    </tr>
	<%
				list = iwenba.Cms_yawen_select(end_no,begin_no,keys,cid); 
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
	<%}%>
	<tr>
		<td height="25" bgcolor="#F3F1F2">&nbsp;</td>
	</tr>
	<%if(ss==null){%>
	<tr>
		<td bgcolor="#f3f1f2">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		没有您要搜索的结果！
		</td>
	</tr>
	<%}else{%>
	<tr>
		<td height="35" align="right">
		总页数:<%= intPageCount%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 当前页：<%= intPage%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="yawen_show.jsp?page=1&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%if (intPage > 1) {%>
		<a href="yawen_show.jsp?page=<%=intPage - 1%>&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>">上一页</a>&nbsp;&nbsp;&nbsp;
		<%}%>
		<%if (intPage < intPageCount) {%>
		<a href="yawen_show.jsp?page=<%=intPage + 1%>&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%}%>
		<a href="yawen_show.jsp?page=<%=intPageCount%>&key_w=<%=keys%>&fa_ss=<%= FenLei_t%>">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<%} %>
</table>	
<%@ include file="/include/low.shtml"%>											
</body>
</html>
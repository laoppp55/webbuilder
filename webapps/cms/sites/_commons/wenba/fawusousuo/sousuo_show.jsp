<%@ page language="java" import="java.util.*,java.sql.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.wenba.*" %>
<html>
<head>
<%
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	String keys = (String)request.getParameter("key_w");
	String FenLei_t = (String)request.getParameter("fa_ss");
	String FenLei = (String)request.getParameter("fa_fl");////1 劳动合同 2工资 3社会保险 4招聘培训 5劳动保护 6仲裁监察
	out.print(keys+" ");
	out.print(FenLei_t+" ");
	out.print(FenLei+" ");
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
	}if(FenLei.equals("5")&&FenLei_t.equals("597")){
		columnid = 600;
	}if(FenLei.equals("5")&&FenLei_t.equals("489")){
		columnid = 495;
	}if(FenLei.equals("5")&&FenLei_t.equals("481")){
		columnid = 487;
	}if(FenLei.equals("5")&&FenLei_t.equals("497")){
		columnid = 511;
	}
	out.print(columnid);
	list = iwenba.Cms_sousuo(keys,columnid);
	
	
%>
</head>
<body>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
    	<TR>
        	<TD vAlign=top>
            	<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              		<TBODY>
              			<TR>
                			<TD class=black12b  background=/images/3_faluchaxun_r3_c4.jpg height=33>&nbsp;&nbsp;<IMG height=11 src="/images/3_faluchaxun_r4_c6.jpg" width=7 align=absMiddle>&nbsp;劳动合同案例</TD>
                		</TR>
              			<TR>
                			<TD>
                				<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    				<TBODY>
                    				<%
                    					for(int i=0;i<list.size();i++){
                    						ss=(Sousuo)list.get(i);
                    						String color1 = "#f3f1f2";
                    						String color2 = "#FFFFFF";
                    						String color = "";
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
                    					<TR>
                      						<TD bgColor="<%= color%>">
                        						<TABLE cellSpacing=0 cellPadding=0 width="95%" align=center border=0>
										        	<TBODY>
										            	<TR>
												        	<TD width=15 height=25><IMG height=3 src="/images/3_faluchaxun_r9_c6.jpg" width=3 align=absMiddle></TD>
												            <TD><a href="<%= url%>"><%= ss.getMaintitle()%></a></TD>
												        	<TD align=middle width=80><%= date%></TD>
												    	</TR>
												   	</TBODY>
												</TABLE>
											</TD>
										</TR>  
										<%} %>         
									</TBODY>
								</TABLE>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>																
</body>
</html>
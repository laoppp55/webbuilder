<%@ page language="java" import="java.util.*,com.bizwink.wenba.*" contentType="text/html; charset=GBK"%>
<%@ page import="java.net.*" %>
<%
String id = request.getParameter("id")==null?"0":request.getParameter("id");
String fenlei = request.getParameter("fenlei")==null?"":request.getParameter("fenlei");
int sendid = 0;
String sendfenlei = "";
List list = new ArrayList();
IWenbaManager iwenba = wenbaManagerImpl.getInstance();
list = iwenba.getZXTW(0,"","");
int flag = -1;
if(list!=null){
	for(int i=0;i<list.size();i++){
		WenbaBean wb = (WenbaBean)list.get(i);
		if(wb.getId()==Integer.parseInt(id)){
			flag = i;
			break;
		}
	}
	if(flag==list.size()-1){
		flag = -1;
	}
	for(int i=0;i<list.size();i++){
		if(i==flag+1){
			WenbaBean wb = (WenbaBean)list.get(i);
			sendid = wb.getId();
			sendfenlei = wb.getCname();
			break;
		}else{
			continue;
		}
	}
	response.sendRedirect("wenba_finsh.jsp?id="+sendid+"&fenlei="+URLEncoder.encode(sendfenlei));
}else{
	response.sendRedirect("wenba_finsh.jsp?id="+id+"&fenlei="+URLEncoder.encode(fenlei));
}
%>
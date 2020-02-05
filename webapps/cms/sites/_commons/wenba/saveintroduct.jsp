<%@ page language="java" import="java.util.*,com.bizwink.wenba.*" contentType="text/html; charset=GBK"%>
<%
String userid = request.getParameter("userid")==null?"0":request.getParameter("userid");
String cid = request.getParameter("cid")==null?"0":request.getParameter("cid");
String content = request.getParameter("introduct")==null?"":request.getParameter("introduct");
String imgname = request.getParameter("imgname")==null?"0":request.getParameter("imgname");
if(!userid.equals("0")){
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	int result = iwenba.setintroduct(Integer.parseInt(userid),content);
	if(result==0){
		System.out.println("插入成功");
	}else{
		System.out.println("插入失败");
	}
}
response.sendRedirect("zhuanjia_info.jsp?cid="+cid+"&userid="+userid+"&imgname="+imgname);

%>
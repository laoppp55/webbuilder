<%@ page contentType="text/html;charset=GBK" import="com.jspsmart.upload.*" %>
<%@ page import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*" %>
<%
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	String FilePath = (String)request.getParameter("filepath");
	String id = (String)request.getParameter("qid");
	int ID = Integer.parseInt(id);
	String UserName = (String)request.getParameter("username");
	//wenti wen = new wenti();
	//wen = iwenba.getQuestion(ID);
	//int xuanshi = wen.getXuanshang();
	//if(xuanshi<300){
		//System.out.print("");
	//}else{
		SmartUpload su = new SmartUpload();  
		su.initialize(pageContext); 
		// 设定contentDisposition为null以禁止浏览器自动打开文件， 
		//保证点击链接后是下载文件。若不设定，则下载的文件扩展名为 
		//doc时，浏览器将自动用word打开它。扩展名为pdf时
		//浏览器将用acrobat打开。 
		su.setContentDisposition(null); 
		// 下载文件
		su.downloadFile(FilePath);
	//}
	
%>

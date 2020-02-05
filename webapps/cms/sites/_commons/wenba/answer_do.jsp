<%@ page import="java.util.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.wenba.*" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.InetAddress"%>
<%@ page import="java.net.*" %>
<%@ page import="com.bizwink.util.*" %>
<%
	String email = request.getParameter("useremail")==null?"":request.getParameter("useremail");
	String emailflag = request.getParameter("emailflag")==null?"0":request.getParameter("emailflag");
	String questionname = request.getParameter("questionname")==null?"":request.getParameter("questionname");

	com.jspsmart.upload.SmartUpload uploader = new com.jspsmart.upload.SmartUpload();
	uploader.initialize(pageContext);
  	uploader.upload();
  	com.jspsmart.upload.File uploadedFile = uploader.getFiles().getFile(0);
  	//int file_size = uploader.getFiles().getFile(0).getSize();
  	String file_name =  uploader.getFiles().getFile(0).getFileName();
  	String pathUrl1;
  	if(file_name==null||file_name.equals("")){
  		pathUrl1 = null;
  	}else{
	  	String rName = String.valueOf(Math.abs(Math.round(Math.random()*10)*(new Date().getTime())*10)+new Date().getTime())+"."+uploader.getFiles().getFile(0).getFileExt();
	  	pathUrl1 = "/wenba/uploadImage/wenti/" +rName;
	  	uploadedFile.saveAs(pathUrl1);
  	}
  	
  	com.jspsmart.upload.Request myRequest = uploader.getRequest();
%>
<%
	answer ansers = new answer();
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
%>
<%
	String UserName = myRequest.getParameter("UserName");
	int userID = Integer.parseInt(myRequest.getParameter("UserID"));
	int QIDs = Integer.parseInt(myRequest.getParameter("QID"));
	int CID = Integer.parseInt(myRequest.getParameter("CID"));
	String textAreas = (String) myRequest.getParameter("textArea");
	String canKaos = (String) myRequest.getParameter("canKao");
	String fenLei = (String) myRequest.getParameter("fff").toString();
	String Ipaddress = request.getRemoteAddr();
	int votenum = 0;
	
	ansers.setQid(QIDs);
	ansers.setAnwser(textAreas);
	ansers.setCahkaoziliao(canKaos);
	ansers.setIpaddress(Ipaddress);
	ansers.setVotenum(votenum);
	ansers.setPicpath(pathUrl1);
	ansers.setUsername(UserName);
	ansers.setUserid(userID);
	
	if (!ansers.equals("")||ansers!=null) {
		//添加答案
		iwenba.addanswer(ansers);
		//System.out.println("添加答案完成");
		//iwenba.changeUseranwnum(userID,1);
		//回答问题加5分，分别加到grade和weekgrade
		int graderesult = iwenba.answaddgrade(userID,5);
		//System.out.println("回答问题加积分完成");
		//System.out.println("----------------grade+5------------------:"+graderesult);
		int weekresult = iwenba.weekgrade(userID,5);
		//System.out.println("周积分变化完成");
		//System.out.println("----------------weekgrade+5------------------:"+weekresult);
		int statu = 1;
		//回答问题数加1
		iwenba.changeQuestion(QIDs);
		//System.out.println("回答数加一完成");
		if(emailflag.equals("1")){
		System.out.println("emaile发送问题");
			SendMail sendemail = new SendMail();
			String content = "<table><tr><td align=left>网友解答:"+textAreas+"</td></tr></table></center>";
			content = content + "<br /><br />" +"<table><tr><td align=center>谢谢您的关注，请您及时选出最佳答案！(<a href=http://www.hrlaw.com.cn/wenba/wenba_finsh.jsp?id="+QIDs+"&fenlei="+URLEncoder.encode(fenLei)+" target=_bank>选择最佳答案</a>)</td></tr></table>";
			String title = "中国劳动法务网问题回复";
			String tit = "<ceter><table align=center border=0><tr><td align=center><a href=http://www.hrlaw.com.cn target=_bank><img src=\"http://www.hrlaw.com.cn/images/logo.gif\" height=89 width=181></a></td></tr></table>";
       	 	tit= tit + "<table><tr><td align=center>您的问题："+questionname+"</td></tr></table></center>";
       	 	//System.out.println(tit);
        	sendemail.sendmail(email,tit+"<br />"+content,title);
		}
		response.sendRedirect("/wenba/wenba_finsh.jsp?id=" +QIDs+"&fenlei=" +URLEncoder.encode(fenLei));
	}
%>

<html>
  <head>
  </head>
  <body>
  </body>
</html>

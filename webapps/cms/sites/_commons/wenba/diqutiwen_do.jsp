<%@ page import="java.util.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.wenba.*" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.InetAddress"%>
<%@ page import="java.net.*" %>
  <%
  	wenti wen = new wenti();
  	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
  %>
  
<%
	com.jspsmart.upload.SmartUpload uploader = new com.jspsmart.upload.SmartUpload();
	uploader.initialize(pageContext);
  	uploader.upload();
  	com.jspsmart.upload.File uploadedFile = uploader.getFiles().getFile(0);
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
	int emailflag = 0;
	boolean EMAILFLAG = ParamUtil.getCheckboxParameter(request,"Emailnotify");//邮件通知
 		if (EMAILFLAG == true) {
 			emailflag = 1;
  		}
  	String Title = (String) myRequest.getParameter("titles");               //问题标题
  	String QUESTION = (String) myRequest.getParameter("Question");         //问题内容
  	//判断问题分类是否更改
  	int COLUMNID; 
  	String cname = null;                                                         //问题所属的分类ID
  	String Cname = (String)myRequest.getParameter("fenlei");               //问题所属分类中文名称、
  	if(Cname==null||Cname.equals("")){
  		COLUMNID = Integer.parseInt((String)myRequest.getParameter("CLLUMNIDS"));
  		wenbaImpl wenbai = null;
  		wenbai= iwenba.getCnameId(COLUMNID); 
  		cname = wenbai.getCName();
  	}else{
  		String[] value = Cname.split(",");
  		COLUMNID = Integer.parseInt(value[1]);
  		cname = value[0];
  	}
  	
  	String Ipaddress = request.getRemoteAddr();                          //IP地址
  	String PROVINCE = (String) myRequest.getParameter("Select1");          //省份
  	String CITY = (String) myRequest.getParameter("Select2");              //城市
 	String AREA = (String) myRequest.getParameter("Select3");              //区
 	String Email = (String) myRequest.getParameter("emails");                //邮箱地址
 	int xuanshang = 10;
 	String Types = (String)myRequest.getParameter("types");
 	wen.setTitles(Title);
 	wen.setQuestion(StringUtil.gb2iso4View(QUESTION));
 	wen.setColumnid(COLUMNID);
 	wen.setCname(StringUtil.gb2iso4View(cname));
 	wen.setIpaddress(Ipaddress);
 	wen.setProvince(StringUtil.gb2iso4View(PROVINCE));
 	wen.setCity(StringUtil.gb2iso4View(CITY));
 	wen.setArea(StringUtil.gb2iso4View(AREA));
 	wen.setXuanshang(xuanshang);
 	wen.setEmail(Email);
 	wen.setEmailnotify(emailflag);
 	wen.setPicpath(pathUrl1);
 	iwenba.addWenti(wen);
 	
 	if(Types=="zhuanjia"||Types.equals("zhuanjia")){
 		response.sendRedirect("/wenba/wenba_zhuanjia.jsp?cid="+COLUMNID);
 	}
 	if(Types=="diqu"||Types.equals("diqu"))
 	{
 		response.sendRedirect("/wenba/wenba_diqu.jsp?cid=" +COLUMNID+ "&pro=" + URLEncoder.encode(PROVINCE));
 	}
 	if(Types=="diqu_jiejue"||Types.equals("diqu_jiejue"))
 	{
 		response.sendRedirect("/wenba/wenba_diqu.jsp?cid=" +COLUMNID+ "&pro=" + URLEncoder.encode(PROVINCE));
 	}
 	if(Types=="diqu_oans"||Types.equals("diqu_oans"))
 	{
 		response.sendRedirect("/wenba/wenba_diqu0ans.jsp?cid=" +COLUMNID+ "&pro=" + URLEncoder.encode(PROVINCE));
 	}
%>

<html>
<head></head>
<body>
<table>
<tr>
	<td><%= Title%></td>
</tr>
<tr>
	<td><%= QUESTION%></td>
</tr>
<tr>
	<td><%= COLUMNID%></td>
</tr>
</table>
</body>
</html>
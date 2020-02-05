<%@ page language="java" import="java.util.*,java.sql.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.wenba.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<LINK href="sites/www_gdmoa_org/images/ziti.css" type=text/css rel=stylesheet>
<title>无标题文档</title>
<script type="text/JavaScript">
function select_fw(){
	if(document.getElementById("fa_ss").value=="0"){
		alert("请选择分类！");
		return false;
	}
	if(document.getElementById("key_w").value==""){
		alert("请输入搜索关键字！");
		return false;
	}else{
		var key_w = document.getElementById("key_w").value;//搜索关键字
		var fa_ss = document.getElementById("fa_ss").value;
		var url = "/wenba/fawusousuo/yawen_show.jsp?key_w="+key_w+"&fa_ss=" +fa_ss;
		window.location.href = url;
	}
}

</script>
</head>
<BODY>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
       <TD bgColor=#2b8087 height=30>&nbsp;       
        <SELECT id="fa_ss" style="BORDER-RIGHT: #ffffff 1px solid; BORDER-TOP: #ffffff 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #ffffff 1px solid; WIDTH: 80px; COLOR: #000000; BORDER-BOTTOM: #ffffff 1px solid"  name="fa_ss"> 
	    	<OPTION value="0">搜索分类</OPTION>
	        <OPTION value="2">劳动合同</OPTION> 
	        <OPTION value="3">工资</OPTION>
	        <OPTION value="4">劳动保护</OPTION> 
	  		<OPTION value="5">招聘与就业</OPTION>
	  		<OPTION value="6">仲裁与监察</OPTION>
	  		<OPTION value="7">养老保险</OPTION>
	  		<OPTION value="8">医疗保险</OPTION>
	  		<OPTION value="9">工伤保险</OPTION>
	  		<OPTION value="10">失业保险</OPTION>
	  		<OPTION value="11">生育保险</OPTION> 		
  		</SELECT> 
  		<INPUT id = "key_w"style="BORDER-RIGHT: #ffffff 1px solid; BORDER-TOP: #ffffff 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #ffffff 1px solid; WIDTH: 120px; COLOR: #000000; BORDER-BOTTOM: #ffffff 1px solid" name="key_w"/>&nbsp;&nbsp;   
  		<INPUT id="" style="FONT-SIZE: 12px; HEIGHT: 18px" type=submit value=搜索 name=button  onclick="javascript:select_fw()"/>&nbsp;&nbsp;
       </TD>         
	</TR>
</TABLE>   
</BODY>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="register.*" %>
<%@ page import="javax.servlet.http.HttpSession"%>

  <%
  	String Verify = (String)request.getParameter("Verify");
    String sessionvalues = (String)session.getAttribute("rand"); 
  	if(Verify==sessionvalues||Verify.equals(sessionvalues)){
  		out.write(1+"-验证码正确!" +"=="+Verify+"=="+sessionvalues);
  	}else{
  		out.write(0+"-验证码不正确!"+"=="+Verify+"=="+sessionvalues);
  	}
  %>
 
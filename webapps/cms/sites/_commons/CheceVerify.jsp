<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<%
    String Verify = (String)request.getParameter("Verify");
    String sessionvalues = (String)session.getAttribute("rand");
    int sposi = -1;
    int eposi = -1;
    sposi = Verify.toLowerCase().indexOf("<script>");
    eposi = Verify.toLowerCase().indexOf("</script>");
    String sVerify = "";
    if (sposi>-1) {
        sVerify = Verify.substring(0,sposi);
    }

    if (eposi>-1) {
        Verify = sVerify + Verify.substring(sposi + "</script>".length());
    }
        
    if(Verify==sessionvalues||Verify.equals(sessionvalues)){
        out.write(1+"-验证码正确!" +"=="+Verify+"=="+sessionvalues);
    }else{
        out.write(0+"-验证码不正确!"+"=="+Verify+"=="+sessionvalues);
    }
%>
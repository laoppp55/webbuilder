<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Enumeration<String> paraNames = request.getParameterNames();
    String grant_code = null;
    while(paraNames.hasMoreElements()){
        String paraKey = paraNames.nextElement();
        String paraValue  = request.getParameter(paraKey);
        System.out.println(paraKey + "=" + paraValue);
        if (paraKey.equals("code")) {
            grant_code = paraValue;
            break;
        }
    }
    session.setAttribute("grantCode",grant_code);
    System.out.println("login OK!!!!");
    response.sendRedirect("/index.shtml");
%>

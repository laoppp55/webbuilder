<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/20
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Enumeration enu=request.getParameterNames();
    while(enu.hasMoreElements()){
        String paraName=(String)enu.nextElement();
        System.out.println(paraName+": "+request.getParameter(paraName));
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>

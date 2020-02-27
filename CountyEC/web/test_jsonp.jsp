<%@ page import="java.sql.Connection" %>
<%@ page import="com.bizwink.util.DBUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DBUtil dbUtil = new DBUtil();
    Connection conn= dbUtil.getConn();

    if (conn != null) System.out.println("连接被成功创建");
%>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
    <script type="text/javascript">
        /*
         $(document).ready(function(){
         $.ajax({
         url:'http://192.168.9.5/jsonp_test1.jsp',
         dataType:"jsonp",
         jsonp:"jsonpcallback",
         success:function(data){
         var $ul = $("<ul></ul>");
         $.each(data,function(i,v){
         $("<li/>").text(v["id"] + " " + v["name"]).appendTo($ul)
         });
         $("#res").append($ul);
         }
         });
         });
         */
    </script>
</head>
<body>
<div id="res"></div>
</body>
</html>

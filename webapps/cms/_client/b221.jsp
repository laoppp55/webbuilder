<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-3-25
  Time: 下午9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script src="js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script>
        alert("hello word");
        var sjhIframe = '<iframe height="26" src="http://petersong.coosite.com/petersong/p.jsp" frameborder="0" width="100%" height="0" scrolling="no"></iframe>';
        $('#iframeid').append(sjhIframe);
    </script>
</head>
<body>
<iframe height="26" src="http://petersong.coosite.com/petersong/p.jsp" frameborder="0" width="100%" height="0" scrolling="no"></iframe>
<div id="iframeid"></div>
<a href="http://petersong.coosite.com/petersong/a221.jsp" target="_blank">p2</a>
</body>
</html>

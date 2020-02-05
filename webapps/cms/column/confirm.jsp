<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-1-14
  Time: 下午2:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>确认提示框</title>
    <script language=javascript type="text/javascript">
        function F(str){
            window.opener.setupAttr(str);
            window.close();
        }
    </script>
</head>
<body style="background: menu">
<div style="margin-top: 15%; margin-left:5%">
    <input id="Button1" type="button" value="继承" style="width:80px;height:25px" onclick="F('jj');"/>
    <input id="Button2" type="button" value="自定义" style="width:80px;height:25px" onclick="F('self');"/>
    <input id="Button3" type="button" value="关闭" style="width:80px;height:25px" onclick="F('cancel');" />
</div>
</body>
</html>
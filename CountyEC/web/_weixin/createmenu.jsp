<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.bizwink.weixin.HttpUtility" %>
<%@ page import="com.bizwink.weixin.AccessTokenResult" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/14
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    AccessTokenResult accessTokenResult = HttpUtility.getToken("wx5c345dba1ac0184c","274bb2545802a0ac31db9e7c4df03209");
    String access_token = accessTokenResult.getAccess_token();
    String user_define_menu = "{\"button\":[{\"type\":\"click\",\"name\":\"项目管理\",\"key\":\"20_PROMANAGE\"},{\"type\":\"click\",\"name\":\"机构运作\",\"key\":\"30_ORGANIZATION\"},{\"name\":\"日常工作\",\"sub_button\":[{\"type\":\"click\",\"name\":\"待办工单\",\"key\":\"01_WAITING\"},{\"type\":\"click\",\"name\":\"已办工单\",\"key\":\"02_FINISH\"},{\"type\":\"click\",\"name\":\"我的工单\",\"key\":\"03_MYJOB\"},{\"type\":\"click\",\"name\":\"公告消息箱\",\"key\":\"04_MESSAGEBOX\"},{\"type\":\"click\",\"name\":\"签到\",\"key\":\"05_SIGN\"}]}]}";

%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>

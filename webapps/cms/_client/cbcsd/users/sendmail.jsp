<%@ page import="com.bizwink.util.SendMail" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-1-7
  Time: 下午9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String result = "发送成功";
    String mailbody = "<meta http-equiv=Content-Type content=text/html;charset=gb2312>" +
            "<div align=center><a href=http://www.bizwink.com.cn>" +
            "北京潮虹伟业科技有限公司</a></div>";

    //SendMail themail = new SendMail("smtp.163.net");
    SendMail themail = new SendMail("116.90.87.233");
    //themail.setNeedAuth(true);
    themail.setNeedAuth(false);

    if(themail.setSubject("标题") == false) return;
    if(themail.setBody(mailbody) == false) return;
    if(themail.setTo("bizwink@sohu.com") == false) return;
    if(themail.setFrom("petersong@bizwink.com.cn") == false) return;
    if(themail.addFileAffix("/data/a/index.shtml") == false) return;
    //themail.setNamePass("bet","7zhang8");

    if(themail.sendout() == true) {
        System.out.println("ok");
        result = "发送成功";
    } else {
        result = "发送失败";
    }
%>
<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-6-19
  Time: 下午10:05
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username = null;

    //入股SESSION失效，试试COOKIE是否可以读出来
    Cookie[] cookies = request.getCookies();
    String cookie_value = null;
    for(Cookie c :cookies ){
        if (c.getName().equalsIgnoreCase("AuthInfo_cookie")) {
            SecurityUtil securityUtil = new SecurityUtil();
            cookie_value = securityUtil.detrypt(c.getValue(),null);
            break;
        }
    }

    if (cookie_value != null) {
        int posi = cookie_value.indexOf("-");
        username = cookie_value.substring(0, posi);
    } else {                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
        if (authToken != null) {
            SessionUtil.removeUserAuthorization(response, session);
        }
    }

    if (username != null) {

    } else {
        response.sendRedirect(response.encodeRedirectURL("error.jsp"));
    }

%>
<html>
<head>
    <title></title>
</head>
<body>
 <div class=\"grzx_1\">"+
    "<div class=\"grzx_1_bt\">全部留言</div>"+
    "<div class=\"grzx_1_sub\">&nbsp;</div>"+
    "<div class=\"clear\"></div>"+
    "</div>"+
"<div class=\"grzx_222\">"+
    "        <div class=\"grzx_lylb\">"+
        "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
        "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
        "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">司马台古北水镇龙祥人家农家院周边有什么采摘园吗？是否有停车位？我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
        "<div class=\"clear\"></div>"+
        "</div>"+
    "<div class=\"grzx_lylb\">"+
        "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
        "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
        "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">是否有停车位？我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
        "<div class=\"clear\"></div>"+
        "</div>"+
    "<div class=\"grzx_lylb\">"+
        "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
        "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
        "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
        "<div class=\"clear\"></div>"+
        "</div>"+
    "<div class=\"grzx_lylb\">"+
        "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
        "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
        "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
        "<div class=\"clear\"></div>"+
        "</div>"+
    "<div class=\"grzx_lylb\">"+
        "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
        "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
        "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
        "<div class=\"clear\"></div>"+
        "</div>"+
    "<div class=\"clear\"></div>"+
    "</div>"+
"<div class=\"grzx_2\">"+
    "        <div class=\"grzx_bottom_1\"><input name=\"\" type=\"checkbox\" value=\"\" /></div>"+
    "<div class=\"grzx_bottom_2\">全选</div>"+
    "<div class=\"grzx_bottom_3\"><input name=\"\" class=\"plsc_btn\" type=\"button\" /></div>"+
    "<div class=\"grzx_bottom_4\">"+
        "        <div class=\"page_1\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_6.jpg\" /></a></div>"+
        "<div class=\"page_2\">"+
            "        <ul>"+
                "        <li class=\"page_ts\"><a href=\"#\">1</a></li>"+
                "        <li><a href=\"#\">2</a></li>"+
                "        <li><a href=\"#\">…</a></li>"+
                "</ul>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
        "<div class=\"page_3\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_7.jpg\" /></a></div>"+
        "<div class=\"page_4\">共11页，</div>"+
        "<div class=\"page_5\">到第<input value=\"10\" name=\"\" type=\"text\" class=\"grzx_btn1\" />页</div>"+
        "<div class=\"page_6\"><input name=\"\" type=\"button\" class=\"grzx_btn2\" /></div>"+
        "</div>"+
    "<div class=\"clear\"></div>"+
    </div>
</body>
</html>

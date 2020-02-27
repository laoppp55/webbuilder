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
<div class="grzx_1">
    <div class="grzx_1_bt">全部订单</div>
    <div class="grzx_1_sub">&nbsp;</div>
   <div class="clear"></div>
    </div>
<div class="grzx_2">
            <div class="fjfx">房间房型</div>
    <div class="fjfx_btn">
                <select style="width:200px;">
                    <option value="单人间">单人间</option>
            <option value="双人间">双人间</option>
            <option value="多人间">多人间</option>
            </select>
        </div>
    <div class="clear"></div>
    </div>
<div class="grzx_2">
            <div class="fjfx">订单状态</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 全部</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 待处理</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 待付款</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 已经取消</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 已成交</div>
    <div class="clear"></div>
    </div>
<div class="grzx_2">
            <div class="fjfx">下单时间</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 全部</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 1个月</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 2个月</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 3个月</div>
    <div class="clear"></div>
    </div>
<div class="grzx_2">
            <div class="fjfx">入住时间</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 全部</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 今日入住</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 明日入住</div>
    <div class="ddzt_1"><input name="" type="radio" value="" /> 7天入住</div>
    <div class="clear"></div>
    </div>
<div class="grzx_2">
            <div class="khmc">客户名称：<input name="" type="text" class="khmc_btn" value="请输入客户名称" /></div>
    <div class="khmc">客户手机：<input name="" type="text" class="khmc_btn" value="请输入客户手机号码" /></div>
    <div class="khcx"><input name="" type="button" class="khcx_btn" /><input name="" type="button" class="khcx_btn1" /></div>
    <div class="clear"></div>
    </div>
<div class="grzx_2">
            <div class="ddlb_ly">
                <div class="ddlb_1">下单时间</div>
        <div class="ddlb_1">房间房型</div>
        <div class="ddlb_1">订单金额</div>
        <div class="ddlb_1">客户名称</div>
        <div class="ddlb_1">入住人数</div>
        <div class="ddlb_1" style=" border:none;">订单状态</div>
    </div>
<div class="dd_list">
            <div class="ddlb_2">2016-02-29</div>
    <div class="ddlb_2">多人间</div>
    <div class="ddlb_2">￥128</div>
    <div class="ddlb_2">张三</div>
    <div class="ddlb_2">5</div>
    <div class="ddlb_2" style=" border:none;">已提交</div>
</div>
<div class="dd_list">
            <div class="ddlb_2">2016-02-29</div>
    <div class="ddlb_2">多人间</div>
    <div class="ddlb_2">￥128</div>
    <div class="ddlb_2">张三</div>
    <div class="ddlb_2">5</div>
    <div class="ddlb_2" style=" border:none;">已提交</div>
</div>
<div class="dd_list">
            <div class="ddlb_2">2016-02-29</div>
    <div class="ddlb_2">多人间</div>
    <div class="ddlb_2">￥128</div>
    <div class="ddlb_2">张三</div>
    <div class="ddlb_2">5</div>
    <div class="ddlb_2" style=" border:none;">已提交</div>
</div>
<div class="dd_list">
            <div class="ddlb_2">2016-02-29</div>
    <div class="ddlb_2">多人间</div>
    <div class="ddlb_2">￥128</div>
    <div class="ddlb_2">张三</div>
    <div class="ddlb_2">5</div>
    <div class="ddlb_2" style=" border:none;">已提交</div>
</div>
<div class="dd_list">
            <div class="ddlb_2">2016-02-29</div>
    <div class="ddlb_2">多人间</div>
    <div class="ddlb_2">￥128</div>
    <div class="ddlb_2">张三</div>
    <div class="ddlb_2">5</div>
    <div class="ddlb_2" style=" border:none;">已提交</div>
</div>
<div class="clear"></div>
</div>
<div class="grzx_2">
            <div class="grzx_bottom_1">&nbsp;</div>
    <div class="grzx_bottom_2">&nbsp;</div>
    <div class="grzx_bottom_3">&nbsp;</div>
    <div class="grzx_bottom_4">
                <div class="page_1"><a href="#"><img src="http://www.gugulx.com/images/20160120_6.jpg" /></a></div>
        <div class="page_2">
                    <ul>
                        <li class="page_ts"><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">…</a></li>
                </ul>
            <div class="clear"></div>
            </div>
        <div class="page_3"><a href="#"><img src="http://www.gugulx.com/images/20160120_7.jpg" /></a></div>
        <div class="page_4">共11页，</div>
        <div class="page_5">到第<input value="10" name="" type="text" class="grzx_btn1" />页</div>
        <div class="page_6"><input name="" type="button" class="grzx_btn2" /></div>
        </div>
    <div class="clear"></div>
    </div>
</body>
</html>

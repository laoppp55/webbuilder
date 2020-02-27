<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
<link href="http://www.gugulx.com/css/lyb.css" rel="stylesheet" type="text/css" />
<script src="http://www.gugulx.com/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" src="http://www.gugulx.com/js/zDrag.js"></script>
<script type="text/javascript" src="http://www.gugulx.com/js/zDialog.js"></script>
<script src="http://www.gugulx.com/js/validate/jquery.validate.js" type="text/javascript"></script>
<script src="http://www.gugulx.com/js/validate/messages_zh.js" type="text/javascript"></script>
<script src="http://www.gugulx.com/js/validate/additional-methods.js" type="text/javascript"></script>
<script src="http://www.gugulx.com/js/validate/validatePlugin.js" type="text/javascript"></script>
<script charset="utf-8" language="javascript" type="text/javascript" src="http://www.gugulx.com/js/message.js"></script>
<%
    String ip = request.getHeader("x-forwarded-for");
    if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
    }
    if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("X-Real-IP");
    }
    if(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
    }


    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username=null;
    //如果SESSION失效，试试COOKIE是否可以读出来
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
    if(username == null ){
        username = ip;
    }

%>

<form id="formInsertLeaveWord" method="post" accept-charset="utf-8">
    <input id="siteid" type="hidden" name="siteid" value="">
    <input id="userid" type="hidden" name="userid" value="<%=username%>">
    <div class="article_1225">
        <div class="title_1225">
            <!-- <span>留言板</span><a href="#" onclick=javascript:divwinclose()><img border="0" alt="" src="http://njy01.coosite.com/images/close_1225.jpg" /></a>
            -->
        </div>
        <div class="con_1225">
            <div class="con_bd">
                <div class="con_bd_1">标　　题：</div>
                <div class="con_bd_2">
                    <input type="text" name="title" size="30" maxlength="50" required/></div>
                <div class="clear"></div>
            </div>
            <div class="con_bd">
                <div class="con_bd_1">联系电话：</div>
                <div class="con_bd_2">
                    <input type="text" name="phone" size="30" maxlength="50" required/></div>
                <div class="clear"></div>
            </div>
            <div class="con_bd">
                <div class="con_bd_1">电子邮箱：</div>
                <div class="con_bd_2"><input type="email" name="email" size="30" maxlength="50" required/></div>
                <div class="clear"></div>
            </div>
            <div class="con_bd">
                <div class="con_bd_1">留言信息：</div>
                <div class="con_bd_2"><textarea name="content" cols="50" rows="10" required></textarea></div>
                <div class="clear"></div>
            </div>
            <div class="con_bd">
                <div class="con_bd_1"></div>
                <div class="con_bd_2">
                    <input name="ok" type="submit" class="buttom" value="提交" />
                    <input name="reset" type="reset" class="buttom" value="重置" />
                    <input name="cancel" type="button" class="buttom" value="取消" onclick=javascript:divwinclose('_messages') /></div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</form>
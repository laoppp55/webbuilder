<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="com.bizwink.dubboservice.service.SiteinfoService" %>
<%@ page import="com.bizwink.dubboservice.service.ColumnService" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.dubboservice.service.ArticleService" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="com.bizwink.util.*" %>
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

    Column column = null;
    String htmlFromExtattrs = null;
    if (username != null) {
        int columnid = ParamUtil.getIntParameter(request, "column", 0);
        int siteid =  ParamUtil.getIntParameter(request, "siteid", 0);
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            ArticleService articleService = (ArticleService)appContext.getBean("ArticleService");
            UsersService usersService = (UsersService)appContext.getBean("UsersService");
            ColumnService columnService = (ColumnService)appContext.getBean("ColumnService");
            SiteinfoService siteinfoService = (SiteinfoService)appContext.getBean("SiteinfoService");
            Users user = usersService.getByUserId(username);
            column = columnService.getColumnByID(BigDecimal.valueOf(columnid));
            String columnattr = column.getXMLTEMPLATE();
            htmlFromExtattrs = createExtendattrInputHtml.builderHtmlFromExtendattr(columnattr);
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="http://www.gugulx.com/css/fdzx_css.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://www.gugulx.com/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="http://www.gugulx.com/js/jquery.form.js"></script>
    <script type="text/javascript" language="javascript">
        function savehouseinfo() {
            alert("hello word");
        }
    </script>
</head>
<body>
<div>
    <form action="createhouse.jsp" method="post" enctype="multipart/form-data" name="add">
        房间名称： <input type="text" name="housename" value="" size="100" maxlength="300" /></br>
        房间图片1：<input type="file" name="pic1" value="" size="100" maxlength="300"></br>
        房间图片2：<input type="file" name="pic1" value="" size="100" maxlength="300"></br>
        房间图片3：<input type="file" name="pic1" value="" size="100" maxlength="300"></br>
        房间图片4：<input type="file" name="pic1" value="" size="100" maxlength="300"></br>
        房间图片5：<input type="file" name="pic1" value="" size="100" maxlength="300"></br>
        房间描述：<textarea name="brief" maxlength="600" rows="10" cols="60"></textarea></br>
        <%=htmlFromExtattrs%>
        <input type="button" name="addhouseinfo" value="保存" onclick="javascript:savehouseinfo();">
        <input type="button" name="reback" value="取消" onclick="javascript:window.close();">
    </form>
</div>
</body>
</html>
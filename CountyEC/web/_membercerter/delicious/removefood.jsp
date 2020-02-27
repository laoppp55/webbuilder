<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.dubboservice.service.ArticleService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page import="com.google.gson.Gson" %>
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

    int retcode = 0;
    if (username != null) {
        int articleid = ParamUtil.getIntParameter(request, "article", 0);
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            ArticleService articleService = (ArticleService)appContext.getBean("ArticleService");
            retcode = articleService.RemoveArticle(BigDecimal.valueOf(articleid));
        }
    }

    ErrorMessage errorMessage = new ErrorMessage();
    Gson gson = new Gson();
    String jsondata=null;
    if (retcode == 0) {
        errorMessage.setErrcode(-1);
        errorMessage.setErrmsg("删除数据失败");
        errorMessage.setModelname("删除美食信息模块");
        jsondata = gson.toJson(errorMessage);
        out.print(jsondata);
        out.flush();
    } else {
        errorMessage.setErrcode(0);
        errorMessage.setErrmsg("删除数据成功");
        errorMessage.setModelname("删除美食信息模块");
        jsondata = gson.toJson(errorMessage);
        out.print(jsondata);
        out.flush();
    }
%>

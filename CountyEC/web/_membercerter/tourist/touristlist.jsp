<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.po.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.dubboservice.service.ArticleService" %>
<%@ page import="com.bizwink.dubboservice.service.SiteinfoService" %>
<%@ page import="com.bizwink.dubboservice.service.ColumnService" %>
<%@ page import="com.bizwink.po.Siteinfo" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="java.text.SimpleDateFormat" %>
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

    List<Article> articles = null;
    Column column = null;
    Siteinfo siteinfo = null;
    //SimpleDateFormat sdf = new SimpleDateFormat("yyyyyyyy-MM-dd HH(hh):mm:ss S E D F w W a k K z");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if (username != null) {
        int columnid = ParamUtil.getIntParameter(request, "colid", 0);
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            ArticleService articleService = (ArticleService)appContext.getBean("ArticleService");
            UsersService usersService = (UsersService)appContext.getBean("UsersService");
            ColumnService columnService = (ColumnService)appContext.getBean("ColumnService");
            SiteinfoService siteinfoService = (SiteinfoService)appContext.getBean("SiteinfoService");
            Users user = usersService.getByUserId(username);
            int siteid = user.getSITEID().intValue();
            siteinfo = siteinfoService.getSiteinfoByID(BigDecimal.valueOf(siteid));
            column = columnService.getColumnByID(BigDecimal.valueOf(columnid));
            articles = articleService.getArticles(BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnid));
        }
    } else {
        response.sendRedirect(response.encodeRedirectURL("error.jsp"));
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="http://www.gugulx.com/css/fdzx_css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="grzx_1">
    <div class="grzx_1_bt">客人留影</div>
    <div class="grzx_1_sub">&nbsp;</div>
    <div class="clear"></div>
</div>
<form name="lyform"><div class="grzx_2">
    <div class="fjfx_1"><span>*</span> 留影描述：</div>
    <div class="fjfx_2"><textarea name="" cols="56" rows="5"></textarea></div>
    <div class="clear"></div>
</div>
    <div class="grzx_2">
        <div class="fjfx_1"><span>*</span> 留影图片：</div>
        <div class="fjfx_2"><input name="" type="text" class="fjfx_txt" /></div>
        <div class="fjfx_3"><input name="" type="button" value="选择图片" /></div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2">
        <div class="fjfx_1"><input name="" type="button" class="njy_btn" /></div>
        <div class="clear"></div>
    </div></form>
<div class="grzx_2">
    <div class="ddlb_ly_1">
        <div class="fjfx_pic">缩图</div>
        <div class="fjfx_pic1">客人留影描述</div>
        <div class="fjfx_pic2">发布日期</div>
        <div class="fjfx_pic3">操作</div>
    </div>
    <div class="dd_list_fx">
        <%for(int ii=0; ii<articles.size(); ii++) {
            Article article = articles.get(ii);
        %>
        <div class="fjfx_pic_1"><img src="http://<%=siteinfo.getSITENAME()%>/<%=column.getDIRNAME()%>/images/<%=article.getPIC()%>"  width="160" height="101"/></div>
        <div class="fjfx_pic_11"><%=(article.getSUMMARY()==null)?"":article.getSUMMARY()%></div>
        <div class="fjfx_pic_12"><%=(article.getCREATEDATE()==null)?"":sdf.format(article.getCREATEDATE())%></div>
        <div class="fjfx_pic_13"><input name="delaction" type="button" value="删除" onclick="javascript:delarticle(<%=article.getCOLUMNID()%>,<%=article.getID()%>)"/></div>
        <div class="clear"></div>
        <%}%>
    </div>
    <div class="clear"></div>
</div>
<div class="grzx_2">
    <div class="grzx_bottom_1">&nbsp;</div>
    <div class="grzx_bottom_2">&nbsp;</div>
    <div style="width:180px; float:left;">&nbsp;</div>
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
<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.dubboservice.service.ArticleService" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.po.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.dubboservice.service.ColumnService" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="com.bizwink.dubboservice.service.SiteinfoService" %>
<%@ page import="com.bizwink.po.Siteinfo" %>
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
    int siteid = 0;
    int columnid = ParamUtil.getIntParameter(request, "colid", 0);
    int pageno = ParamUtil.getIntParameter(request, "page", 1);
    int pagesize = 3;
    int totalrow = 0;
    int totalpages = 0;

    if (username != null) {
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            ArticleService articleService = (ArticleService)appContext.getBean("ArticleService");
            UsersService usersService = (UsersService)appContext.getBean("UsersService");
            ColumnService columnService = (ColumnService)appContext.getBean("ColumnService");
            SiteinfoService siteinfoService = (SiteinfoService)appContext.getBean("SiteinfoService");
            Users user = usersService.getByUserId(username);
            siteid = user.getSITEID().intValue();
            siteinfo = siteinfoService.getSiteinfoByID(BigDecimal.valueOf(siteid));
            column = columnService.getColumnByID(BigDecimal.valueOf(columnid));
            System.out.println("pageno===" + pageno);
            articles = articleService.getArticlesByPage(BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnid),(pageno-1)*pagesize,pagesize);
            totalrow = articleService.countArticles(BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnid));
            if (totalrow % pagesize == 0)
                totalpages = totalrow/pagesize;
            else
                totalpages = totalrow/pagesize + 1;
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
    <style>
        #exec_target{display:none;width:0;height:0;}
        #feedback{width:1200px;margin:0 auto;}
        #feedback img{float:left;width:300px;height:300px;}
    </style>
</head>
<body>
<div class="grzx_1">
    <div class="grzx_1_bt">特色美食</div>
    <div class="grzx_1_sub">&nbsp;</div>
    <div class="clear"></div>
</div>
<form id="msformid" name="msform" method="post" action="/_membercerter/delicious/createfood.jsp" target="exec_target" enctype="multipart/form-data">
    <input type="hidden" name="colid" value="<%=columnid%>" />
    <div class="grzx_2">
        <div class="fjfx_1"><span>*</span> 美食描述：</div>
        <div class="fjfx_2"><textarea name="brief" cols="56" rows="5"></textarea></div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2">
        <div class="fjfx_1"><span>*</span> 美食图片：</div>
        <div class="fjfx_3"><input name="msimg" id="upload_file" type="file"  class="fjfx_txt" value="选择图片" /></div><br/>
        <div class="fjfx_2"><span>*</span> 价格：<input name="price" type="text" /></div>
        <div class="clear"></div>
    </div>
    <div class="grzx_2">
        <div class="fjfx_1"><input name="save" type="button" onclick="javascript:createArticle(<%=columnid%>);" class="njy_btn" /></div>
        <div class="clear"></div>
    </div>
</form>
<iframe id="exec_target" name="exec_target"></iframe>    <!-- 提交表单处理iframe框架 -->
<div id="feedback"></div>    <!-- 响应返回数据容器 -->
<div class="grzx_2">
    <div class="ddlb_ly_1">
        <div class="fjfx_pic">缩图</div>
        <div class="fjfx_pic1">特色美食描述</div>
        <div class="fjfx_pic2">发布日期</div>
        <div class="fjfx_pic3">操作</div>
    </div>
    <div class="dd_list_fx">
        <%for(int ii=0; ii<articles.size(); ii++) {
            Article article = articles.get(ii);
        %>
        <div class="fjfx_pic_1"><img src="http://<%=siteinfo.getSITENAME()%>/<%=column.getDIRNAME()%>/images/<%=article.getPIC()%>" width="160" height="101"/></div>
        <div class="fjfx_pic_11"><%=(article.getSUMMARY()==null)?"":article.getSUMMARY()%></div>
        <div class="fjfx_pic_12"><%=(article.getCREATEDATE()==null)?"":sdf.format(article.getCREATEDATE())%></div>
        <div class="fjfx_pic_13"><input name="delaction" type="button" value="删除" onclick="javascript:delarticle(<%=pageno%>,<%=article.getCOLUMNID().intValue()%>,<%=article.getID().intValue()%>)"/></div>
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
        <div class="page_1"><a href="javascript:void(0);" onclick="gotopage(1,<%=columnid%>)"><img src="http://www.gugulx.com/images/20160120_6.jpg" /></a></div>
        <div class="page_2">
            <ul>
                <%if (totalpages<=5) {
                    for(int ii=1; ii<=totalpages; ii++) {
                        if (pageno == ii)
                            out.println("<li class=\"page_ts\">"+ ii+"</li>");
                        else
                            out.println("<li><a href=\"javascript:void(0);\" onclick=gotopage(" + ii + "," +columnid + ")>"+ ii +"</a></li>");
                    }
                } else {
                    if (pageno<3){
                        for(int ii=1; ii<=totalpages; ii++) {
                            if (pageno == ii)
                                out.println("<li class=\"page_ts\">"+ ii + "</li>");
                            else
                                out.println("<li><a href=\"javascript:void(0);\" onclick=gotopage(" + ii + "," + columnid + ")>"+ ii +"</a></li>");
                        }
                    } else {
                        for(int ii=pageno-2;ii<=pageno+2; ii++) {
                            if (ii>totalpages)
                                break;
                            else {
                                if (pageno == ii)
                                    out.println("<li class=\"page_ts\">"+ ii + "</li>");
                                else
                                    out.println("<li><a href=\"javascript:void(0);\" onclick=javascript:gotopage(" + ii  + "," + columnid + ")>"+ ii +"</a></li>");
                            }
                        }
                    }
                }%>
            </ul>
            <div class="clear"></div>
        </div>
        <div class="page_3"><a href="javascript:void(0);" onclick="gotopage(<%=(pageno+1)%>,<%=columnid%>)"><img src="http://www.gugulx.com/images/20160120_7.jpg" /></a></div>
        <div class="page_4">共<%=totalpages%>页，</div>
        <div class="page_5">到第<input value=<%=pageno%> name="page" type="text" class="grzx_btn1" />页</div>
        <div class="page_6"><input name="pageok" type="button" class="grzx_btn2" onclick="gotopage(page.value,<%=columnid%>)"/></div>
    </div>
    <div class="clear"></div>
</div>
</body>
</html>

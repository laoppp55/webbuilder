<%@ page import="java.net.URLEncoder" contentType="text/html;charset=gbk" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.IArticleManager" %>
<%@ page import="com.bizwink.cms.news.ArticlePeer" %>
<%@ page import="com.bizwink.cms.news.Article" %>
<%
    int articleid = ParamUtil.getIntParameter(request, "articleid", -1);
    String sitename = request.getServerName();
    IArticleManager articlepeer = ArticlePeer.getInstance();
    Article article = articlepeer.getArticle(articleid);

    //追加id
    Cookie[] cookies = request.getCookies();
    boolean flag = true;
    if (cookies != null) {
        System.out.println("cookies=" + cookies.length);
        for (int i = 0; i < cookies.length; i++) {
            System.out.println("name=" + cookies[i].getName());
            if (cookies[i].getName().equals(sitename)) {
                String oldValue = cookies[i].getValue();
                System.out.println("value=" + cookies[i].getValue());
                String newValue="";
                if(oldValue==null||oldValue.equals("null")){
                    oldValue="";
                }
                if(oldValue.indexOf(articleid+"")==-1){
                    newValue = oldValue + "_" + articleid;
                } else {
                    newValue=oldValue;
                }
                cookies[i].setValue(newValue);
                cookies[i].setPath("/");
                cookies[i].setMaxAge(365 * 24 * 60 * 60);
                response.addCookie(cookies[i]);
                flag=false;
                break;
            }
        }
    }


    if(flag){
        Cookie cookie = new Cookie(sitename, "_"+articleid);
        cookie.setPath("/");//这个要设置
        // cookie.setDomain(".wangwz.com");//这个也要设置才能实现上面的两个网站共用
        cookie.setMaxAge(365 * 24 * 60 * 60);//不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
        response.addCookie(cookie);

        //cookie = new Cookie("nick", URLEncoder.encode("王伟宗","UTF-8"));
        // cookie.setPath("/");
        //cookie.setDomain(".wangwz.com");
        //cookie.setMaxAge(365*24*60*60);
        //response.addCookie(cookie);
    }
%>
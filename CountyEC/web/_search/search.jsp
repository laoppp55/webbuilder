<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page import="com.bizwink.search.LuceneSearchService" %>
<%@ page import="com.bizwink.cms.SearchResults" %>
<%@ page import="java.util.Random" %>
<%@ page import="com.bizwink.util.pagination" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-4-14
  Time: 下午8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int pagesize = 12;
    int items_per_row = 3;
    int pageno = ParamUtil.getIntParameter(request,"pageno",0);
    String keyword = ParamUtil.getParameter(request,"keyword");
    String  s_infotype = ParamUtil.getParameter(request,"infotype");
    System.out.println("keyword==" + keyword);
    System.out.println("infotype==" + s_infotype);
    List<SearchResults> results = LuceneSearchService.getSearchResultByClassid(Integer.parseInt(s_infotype),null,keyword);
    System.out.println("results size===" + results.size());
%>
<html>
<head>
    <title>周边旅游行网--信息检索</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="IE=8" http-equiv="X-UA-Compatible" />

    <link rel="stylesheet" type="text/css" href="css_home.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="IE=8" http-equiv="X-UA-Compatible" />
    <link rel="stylesheet" type="text/css" href="/css/css_home2018.css"/>
    <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/js/181.js"></script>
    <script type="text/javascript" src="/js/jquery.slides.min.js"></script>
    <script type="text/javascript" src="/js/change_tab.js"></script>
    <script type="text/javascript" src="/js/json2.js"></script>
    <script type="text/javascript" src="/js/json_parse.js"></script>
    <script type="text/javascript" src="/js/List.js"></script>

    <script>
        var $top=jQuery.noConflict();
        $top(function() {
            $top('#slides').slidesjs({
                play:{
                    active: false,
                    effect: "fade",
                    auto: true,
                    interval: 2000
                },
                effect: {
                    fade: {
                        speed: 1500,
                        crossfade: true
                    }
                },
                pagination: {
                    active: true
                },
                navigation:{
                    active: false
                }
            });
        });

        function doSearch() {
            searchform.target="_blank";
            searchform.submit();
        }

    </script>
</head>
<body>
<!--#include virtual="/inc/top.shtml"-->
<!--#include virtual="/inc/menu_home.shtml"-->
<div class="banner">
    <div class="slide-bg">
        <div class="slide-wp">
            <div id="slides" class="slides">
                <div><img src="http://www.gugulx.com/images/bigpic2008_1.jpg" galleryimg="no"></div>
                <div><img src="http://www.gugulx.com/images/bigpic2008_2.jpg" galleryimg="no" width="1920"></div>
                <div><img src="http://www.gugulx.com/images/bigpic2008_3.jpg" galleryimg="no"></div>
            </div><!--end slides-->
        </div>
        <!--end slide-wp-->
    </div>
</div>
<div class="box">
    <div class="search">
        <form name="searchform" action="/_search/search.jsp" method="post">
            <div class="search_in">
                <input type="text"   name="keyword" class="txt" />
                <input type="button" name="dosearch" class="btn" onclick="javascript:doSearch();"/><br/>
                <input type="radio"  name="infotype" value="2991">农家乐
                <input type="radio"  name="infotype" value="4592" checked>美丽乡村
                <input type="radio"  name="infotype" value="2992">采摘园
                <input type="radio"  name="infotype" value="0">其他
            </div>
        </form>
    </div>
    <div class="list_in">
        <div class="title"><span><%
            if (s_infotype.equalsIgnoreCase("2991"))
                out.println("农家乐：" + results.size());
            else if (s_infotype.equalsIgnoreCase("4592"))
                out.println("美丽乡村："+ results.size());
            else if (s_infotype.equalsIgnoreCase("2992"))
                out.println("采摘园："+ results.size());
            else
                out.println("其他："+results.size());
        %></span></div>
        <div class="njl_txt" id="result">
            <%
                if (results!=null) {
                    int max=20;
                    int min=1;
                    Random random = new Random();

                    int rownum = results.size() / items_per_row;
                    int extra = results.size() % items_per_row;
                    out.println("<ul>");
                    if (extra==0) {
                        if (results.size()<=pagesize) {
                            for(int kk=0;kk<rownum;kk++) {
                                for(int ii=0;ii<items_per_row;ii++) {
                                    SearchResults companyinfo = results.get(kk*items_per_row+ii);
                                    int s = random.nextInt(max)%(max-min+1) + min;
                                    out.println("<li>");
                                    out.println("<a href=\"javascript:siteopen('" + companyinfo.getSITEID() + "');\" class=\"pic\"><img src=\"/images/pic/listpic_20" + s + ".jpg\" width=\"316\" height=\"203\" /></a>");
                                    out.println("<a href=\"javascript:siteopen('" + companyinfo.getSITEID() + "');\" class=\"txt\">" + companyinfo.getCOMPANYNAME() + "</a>");
                                    out.println("</li>");
                                }
                                out.println("<br />");
                            }
                        } else {
                            for(int kk=pagesize*pageno/items_per_row;kk<(pageno+1)*pagesize/items_per_row;kk++) {
                                for(int ii=0;ii<items_per_row;ii++) {
                                    SearchResults companyinfo = results.get(kk*items_per_row+ii);
                                    int s = random.nextInt(max)%(max-min+1) + min;
                                    out.println("<li>");
                                    out.println("<a href=\"javascript:siteopen('" + companyinfo.getSITEID() + "');\" class=\"pic\"><img src=\"/images/pic/listpic_20" + s + ".jpg\" width=\"316\" height=\"203\" /></a>");
                                    out.println("<a href=\"javascript:siteopen('" + companyinfo.getSITEID() + "');\" class=\"txt\">" + companyinfo.getCOMPANYNAME() + "</a>");
                                    out.println("</li>");
                                }
                                out.println("<br />");
                            }
                        }
                    } else {
                        if ((pageno+1)*pagesize<results.size()) {
                            //写完整页的信息
                            for(int kk=pagesize*pageno/items_per_row;kk<(pageno+1)*pagesize/items_per_row;kk++) {
                                for(int ii=0;ii<items_per_row;ii++) {
                                    SearchResults companyinfo = results.get(kk*items_per_row+ii);
                                    int s = random.nextInt(max)%(max-min+1) + min;
                                    out.println("<li>");
                                    out.println("<a href=\"javascript:siteopen('" + companyinfo.getSITEID() + "');\" class=\"pic\"><img src=\"/images/pic/listpic_20" + s + ".jpg\" width=\"316\" height=\"203\" /></a>");
                                    out.println("<a href=\"javascript:siteopen('" + companyinfo.getSITEID() + "');\" class=\"txt\">" + companyinfo.getCOMPANYNAME() + "</a>");
                                    out.println("</li>");
                                }
                                out.println("<br />");
                            }
                        } else {
                            //写整行的信息
                            for(int kk=pagesize*pageno/items_per_row;kk<rownum;kk++) {
                                for(int ii=0;ii<items_per_row;ii++) {
                                    SearchResults companyinfo = results.get(kk*items_per_row+ii);
                                    int s = random.nextInt(max)%(max-min+1) + min;
                                    out.println("<li>");
                                    out.println("<a href=\"javascript:siteopen('" + companyinfo.getSITEID() + "');\" class=\"pic\"><img src=\"/images/pic/listpic_20" + s + ".jpg\" width=\"316\" height=\"203\" /></a>");
                                    out.println("<a href=\"javascript:siteopen('" + companyinfo.getSITEID() + "');\" class=\"txt\">" + companyinfo.getCOMPANYNAME() + "</a>");
                                    out.println("</li>");
                                }
                                out.println("<br />");
                            }
                            //判断是否到达最后一页，如果到达最后一页写最后一行信息
                            out.println("<br />");
                            for(int ii=rownum*items_per_row;ii<results.size();ii++) {
                                SearchResults companyinfo = results.get(ii);
                                int s = random.nextInt(max)%(max-min+1) + min;
                                out.println("<li>");
                                out.println("<a href=\"javascript:siteopen('" + companyinfo.getSITEID() + "');\" class=\"pic\"><img src=\"/images/pic/listpic_20" + s + ".jpg\" width=\"316\" height=\"203\" /></a>");
                                out.println("<a href=\"javascript:siteopen('" + companyinfo.getSITEID() + "');\" class=\"txt\">" + companyinfo.getCOMPANYNAME() + "</a>");
                                out.println("</li>");
                            }
                        }
                    }
                    out.println("</ul>");
                }
            %>
        </div>
        <div class="list_lable">
            <div style="float:left;" id="paging">
                <%=pagination.makePagingForSearch(s_infotype,pagesize,pageno,results.size(),keyword,"/_search/search.jsp")%>
            </div>
        </div>
    </div>
    <div class="item"><a href="#"><img src="http://www.gugulx.com/images/item2018-1.jpg" width="250" height="107" /></a><a href="#"><img src="http://www.gugulx.com/images/item2018-2.jpg" width="250" height="107" /></a><a href="#"><img src="http://www.gugulx.com/images/item2018-3.jpg" width="250" height="107" /></a><a href="#"><img src="http://www.gugulx.com/images/item2018-4.jpg" width="250" height="107" /></a></div>
</div>
<div class="clear"></div>
<div class="bottom">
    <div class="bottom_in">
        <div class="cont"><a href="#">联系我们</a>   |   <a href="#">法律声明</a>   |   <a href="#">网站地图</a>   |   <a href="#">关于我们</a></div>
        <div class="bqs">版权信息归周边游旅行网所有</div>
    </div>
</div>
</body>
</html>

<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.service.ColumnService" %>
<%@ page import="com.bizwink.service.ArticleService" %>
<%@ page import="com.bizwink.po.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    request.setCharacterEncoding("utf-8");
    int siteid = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int pageno = ParamUtil.getIntParameter(request,"pageno",0);
    boolean doDelete = ParamUtil.getBooleanParameter(request,"doDelete");
    boolean doSearch = ParamUtil.getBooleanParameter(request,"doSearch");
    String keyword = ParamUtil.getParameter(request,"keyword");
    int status = ParamUtil.getIntParameter(request,"prodstatus",0);
    int pubflag = 0;                  //信息已经发布

    if (status == 1)
        pubflag = 0;                   //信息已经发布
    else
        pubflag = 1;                   //信息未发布

    //获取50836产品栏目的所有子栏目
    ApplicationContext appContext = SpringInit.getApplicationContext();
    List<Article> articleList = null;
    ColumnService columnService = null;
    ArticleService articleService = null;
    int articlecount = 0;
    int pagesize=5;
    int total_pages = 0;
    if (appContext!=null) {
        if (pageno>=1) pageno = pageno-1;
        BigDecimal startno = BigDecimal.valueOf(pageno*pagesize);
        BigDecimal endno = BigDecimal.valueOf((pageno+1)*pagesize);
        articleService = (ArticleService)appContext.getBean("articleService");
        columnService = (ColumnService)appContext.getBean("columnService");
        if(doSearch) {
            if (doDelete) {           //删除操作，先删除商品信息，然后在分页列出商品信息
                String del_artids = ParamUtil.getParameter(request,"artids");
                int delnum = articleService.deleteArticleByAidList(BigDecimal.valueOf(siteid), del_artids);
            }
            articlecount = articleService.countArticlebyDeptidAndKeyword("长城",keyword,BigDecimal.valueOf(siteid),BigDecimal.valueOf(status),BigDecimal.valueOf(pubflag)).intValue();
            total_pages = articlecount/pagesize;
            if (articlecount % pagesize > 0) total_pages = total_pages + 1;
            articleList = articleService.searchArticleByDeptAndKeyword("长城",keyword,BigDecimal.valueOf(status),BigDecimal.valueOf(pubflag),BigDecimal.valueOf(siteid),startno,endno);
        }
    }
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <meta name="renderer" content="webkit">
    <meta name="description" content="长城云采 - 专注企业采购信息化服务。采购商 - 多家大型企业入驻，累计成交超万亿。商机惠 - 汇集能源行业采购资源，海量优质信息尽在商机惠。" />
    <title>长城电商-商品信息维护</title>
    <link rel="stylesheet" href="css/public/base.css"/>
    <link rel="stylesheet" href="css/public/header.css"/>
    <link rel="stylesheet" href="css/public/lefter.css"/>
    <link rel="stylesheet" href="css/public/popup.css"/>
    <link rel="stylesheet" href="page-link/paging.css"/>
    <link rel="stylesheet" href="css/commodity-control/maintenance-info.css"/>
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-placeholder.js"></script>
    <script type="text/javascript" src="page-link/query.js"></script>
    <script type="text/javascript" src="page-link/paging.js"></script>
    <script>
        $(function () {
            var page = <%=pageno%>;
            /*调用placeholder方法  处理ie8不识别palceholder的问题*/
            $('input, textarea').placeholder();
            /*左边导航栏点击显示隐藏*/
            $(".side-nav-a").on("click", function () {
                $(this).toggleClass("select-a")
            })

            $(".side-nav-a li").on("click", function (event) {
                $(".side-nav-a li").removeClass("select-b");
                $(this).addClass("select-b");
                /*   $(".info-box").removeClass("info-select").eq($(this).index()).addClass("info-select");*/
                event.stopPropagation();
            })

            /*调用分页链接方法   渲染相应分页数据*/
            /*$('.tcdPageCode').Paging({
             pagesize: <%=pagesize%>, count: <%=articlecount%>, toolbar: true, callback: function (page, size, count) {
             //console.log(arguments)
             alert('当前第 ' + page + '页,每页 ' + size + '条,总页数：' + count + '页')

             }
             });*/

            $('.search-btn').on("click",function() {
                var pageno = prodform.pageno.value;
                prodform.method="post";
                prodform.doSearch.value = true;
                prodform.action = "searchProducts.jsp?pageno=" + pageno;
                prodform.submit();
            })

            $('.del-btn').on("click",function() {
                var chk_value =[];
                $('input[name="ckdel"]:checked').each(function(){
                    chk_value.push($(this).val());
                });
                var pageno = prodform.pageno.value;
                if (pageno>=0 && pageno<=<%=total_pages%>) {
                    if(confirm("确定要删除数据吗？")){
                        prodform.method="post";
                        prodform.doDelete.value = true;
                        prodform.doSerch.value = true;
                        prodform.artids.value = chk_value;
                        prodform.action = "searchProducts.jsp?pageno=" + pageno;
                        prodform.submit();
                    }
                } else {
                    alert("输入的页号超出可能的范围值，请重新输入页号");
                }
            })
        })

        function jumptopage() {
            var pageno = prodform.jumppage.value;
            if (pageno>=0 && pageno<=<%=total_pages%>) {
                prodform.method="post";
                prodform.action = "searchProducts.jsp?pageno=" + pageno;
                prodform.submit();
            } else
                alert("输入的页号超出可能的范围值，请重新输入页号");
        }
    </script>
</head>
<body>

<!--顶部导航-->
<div class="header">
    <div class="content-auto">
        <div class="logo left">
            <a href="javascript:void(0)">长城云采</a>
        </div>
        <div class="greeting">
            Hi, 某某某，下午好！
        </div>
        <ul class="nav-bar">
            <li class="nav-li"><a href="javascript:void(0)">首页</a></li>
            <li class="nav-li"><a href="javascript:void(0)">我的云采</a></li>
            <li class="nav-li"><a href="javascript:void(0)">我的业务平台</a></li>
            <li class="nav-li service-center">
                <a href="javascript:void(0)">客服中心</a>
                <i class="down-arrow"></i>
                <i class="up-arrow"></i>
                <ul>
                    <li><a href="javascript:void(0)">新手入门</a></li>
                    <li><a href="javascript:void(0)">买家帮助</a></li>
                    <li><a href="javascript:void(0)">卖家帮助</a></li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<div class="center content-auto clearfix">
    <!--左侧功能栏-->
    <div class="sidebar">
        <ul class="side-nav">
            <li class="side-nav-a">
                <a href="javascript:void(0)">账户管理</a>
                <ul>
                    <li><a href="../prsonal.html">个人账户信息</a></li>
                    <li><a href="../company.html">公司基本信息</a></li>
                    <li><a href="../authentication/authentication.html">企业资质认证</a></li>
                </ul>
            </li>
            <li class="side-nav-a">
                <a href="javascript:void(0)">供应商服务</a>
                <ul>
                    <li><a href="../tobesupplier.html">推荐成为供应商</a></li>
                </ul>
            </li>
            <li class="side-nav-a select-a">
                <a href="javascript:void(0)">商品管理</a>
                <ul>
                    <li class="select-b"><a href="./index.jsp?pageno=<%=pageno%>">商品信息维护</a></li>
                    <li><a href="./not-on.jsp">未上架的商品</a></li>
                </ul>
            </li>
            <li class="side-nav-a">
                <a href="javascript:void(0)">供应商导入</a>
                <ul>
                    <li><a href="javascript:void(0)">主档案导入</a></li>
                    <li><a href="javascript:void(0)">产品目录导入</a></li>
                    <li><a href="javascript:void(0)">开户银行信息导入</a></li>
                </ul>
            </li>
            <li class="side-nav-a">
                <a href="javascript:void(0)">物资管理</a>
                <ul>
                    <li><a href="javascript:void(0)">分类目录管理</a></li>
                    <li><a href="javascript:void(0)">物资模板管理</a></li>
                    <li><a href="javascript:void(0)">物资明细管理</a></li>
                </ul>
            </li>
        </ul>
        <ul class="contact-way">
            <li class="c-blue">QQ群</li>
            <li>298871343</li>
            <li class="c-blue"> 电 话</li>
            <li>010-64661119</li>
            <li class="c-blue">邮 箱</li>
            <li>service@egreatwall.com</li>
        </ul>
    </div>
    <!--推荐成为供应商-->
    <div class="info-box">
        <div class="info-title">
            <div class="home-icon"></div>
            <span><a href="javascript:void(0)">商品管理</a></span>
            <span>></span>
            <span><a href="javascript:void(0)">商品信息维护</a></span>
        </div>
        <form name="prodform" id="prodformid">
            <input type="hidden" name="doDelete" value="false">
            <input type="hidden" name="doSearch" value="false">
            <input type="hidden" name="pageno" value="<%=pageno%>">
            <input type="hidden" name="artids" value="">

            <div class="info-content clearfix">
                <p><span class="f-yellow">发布商品只需三步：</span>发布商品信息 → 填写商品信息 → 提交信息成功，等待平台审核</p>

                <div class="search-form">
                    商品名称：<input type="text" name="keyword"  value="<%=keyword%>" placeholder="可通过关键词进行搜索"/>
                    状态：
                    <select name="prodstatus" title="状态">
                        <option selected="" value="请选择">请选择</option>
                        <option value="1" <%=(status==1)?"selected":""%>>上架</option>
                        <option value="0"  <%=(status==0)?"selected":""%>>审核中</option>
                    </select>
                    <span class="search-btn">查询</span>
                </div>
                <div class="search-form2">
                    <span class="release-btn"><a href="saveproduct.jsp">发布商品</a></span>
                    <span class="del-btn">删除</span>
                </div>

                <div class="operating">
                    <ul class="operating-title clearfix">
                        <li class="li-a"><input type="checkbox"/></li>
                        <li class="li-b">商品图片</li>
                        <li class="li-c">商品名称</li>
                        <li class="li-d">所属分类</li>
                        <li class="li-e">发布时间</li>
                        <li class="li-f">商品描述</li>
                        <li class="li-g">状态</li>
                        <li class="li-h">操作</li>
                    </ul>

                    <%if (articleList!=null) {
                        Article article = null;
                        SecurityUtil securityUtil = new SecurityUtil();
                        SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        for(int ii=0; ii<articleList.size(); ii++) {
                            article = articleList.get(ii);
                            if (article!=null) {
                                BigDecimal columnid = article.getCOLUMNID();
                                Column column = columnService.getColumn(columnid);
                                int apubflag = article.getPUBFLAG();
                                int astatus = article.getSTATUS();
                                int articleid = article.getID().intValue();
                    %>
                    <ul class="operating-content clearfix">
                        <li class="li-a"><input id="aid<%=article.getID()%>" name="ckdel" value="<%=article.getID()%>" type="checkbox"/></li>
                        <li class="li-b"><img src="<%="../../sites/" + sitename +article.getDIRNAME() + "images/" + article.getPIC()%>" alt=""/></li>
                        <li class="li-c">
                            <p><%=article.getMAINTITLE()%></p>
                        </li>
                        <li class="li-d"><%=column.getCNAME()%></li>
                        <li class="li-e">
                            <p><%=myFmt2.format(article.getCREATEDATE())%></p>
                        </li>
                        <li class="li-f">
                            <p>
                                <%=(article.getSUMMARY()!=null)?article.getSUMMARY():""%>
                            </p>
                        </li>
                        <li class="li-g"><%=(astatus==1 && apubflag==0)?"上架":"待审"%></li>
                        <li class="li-h"><a href="updateproduct.jsp?id=<%=articleid%>&vcode=<%=URLEncoder.encode(securityUtil.encrypt(""+articleid,null))%>">修改</a></li>
                    </ul>
                    <%}}}%>
                </div>

                <!--div class="pagination-link" style="margin:20px 0">
                    <div class="tcdPageCode"></div>
                </div-->

                <% if (articlecount>5) {%>
                <div class="pagination-link" style="margin:20px 0">
                    <div class="tcdPageCode">
                        <a href="searchProducts.jsp?keyword=&prodstatus=&verify=">首页</a><a href="searchProducts.jsp?pageno=<%=(pageno>1)?pageno-1:0%>">上一页</a>
                        <%
                            if (total_pages<5)
                                for(int ii=0;ii<total_pages;ii++) {
                                    out.println("<a href=\"searchProducts.jsp?pageno=" + (ii+1) + "\">" + (ii+1) + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
                                }
                        %>
                        <a href="searchProducts.jsp?pageno=<%=(pageno<total_pages)?pageno+1:total_pages%>">下一页</a><a href="searchProducts.jsp?pageno=<%=total_pages%>">末页</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="text" name="jumppage" value="" size="2">&nbsp;<a href="javascript:void(0);" onclick="jumptopage();">跳转</a>
                    </div>
                </div>
                <%}%>
            </div>
        </form>
    </div>
</div>
</body>
</html>
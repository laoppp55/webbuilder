<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.service.ColumnService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.service.ArticleService" %>
<%@ page import="com.bizwink.po.Article" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int articleid = ParamUtil.getIntParameter(request,"id",0);
    String vcode = ParamUtil.getParameter(request, "vcode");
    SecurityUtil securityUtil = new SecurityUtil();
    String s_artid = securityUtil.detrypt(vcode,null);
    int v_articleid = Integer.parseInt(s_artid);

    //获取50836产品栏目的所有子栏目
    ApplicationContext appContext = SpringInit.getApplicationContext();
    List<Column> columns = null;
    ColumnService columnService = null;
    ArticleService articleService = null;
    Article article = null;
    BigDecimal columnid = null;
    Column column = null;
    if (appContext!=null) {
        articleService = (ArticleService)appContext.getBean("articleService");
        columnService = (ColumnService)appContext.getBean("columnService");
        columns = columnService.getSubColumns(BigDecimal.valueOf(65396));
        if (articleid == v_articleid) {
            article = articleService.getArticleByID(BigDecimal.valueOf(articleid));
            columnid = article.getCOLUMNID();
            column = columnService.getColumn(columnid);
        }
    }
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <meta name="renderer" content="webkit">
    <meta name="description" content="长城云采 - 专注企业采购信息化服务。采购商 - 多家大型企业入驻，累计成交超万亿。商机惠 - 汇集能源行业采购资源，海量优质信息尽在商机惠。"/>
    <title>商品管理-发布商品</title>
    <link rel="stylesheet" href="css/public/base.css"/>
    <link rel="stylesheet" href="css/public/header.css"/>
    <link rel="stylesheet" href="css/commodity-control/release-products.css"/>
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-placeholder.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>

    <script>
        $(function () {
            /*调用placeholder方法  处理ie8不识别palceholder的问题*/
            $('input, textarea').placeholder();
            /*左边导航栏点击显示隐藏*/
            $(".side-nav-a").on("click", function () {
                $(this).toggleClass("select-a")
            })
            $(".side-nav-a li").on("click", function (event) {
                $(".side-nav-a li").removeClass("select-b");
                $(this).addClass("select-b");
                event.stopPropagation();
            })
        })
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
    <div class="info-title">
        <span><a href="javascript:void(0)">发布商品</a></span>
    </div>
    <div class="info-content">
        <form id="releaseprodid" enctype="multipart/form-data">
            <input type="hidden" name="article" value="<%=articleid%>">
            <ul>
                <li>
                    <span class="form-key"><i class="f-yellow">＊</i>商品名称：</span>
                    <input type="text" name="prodname" size="200" value="<%=(article!=null)?article.getMAINTITLE():""%>"/>
                    <span class="text-error">使用字母、数字和符号两种及以上的组</span>
                </li>
                <li>
                    <span class="form-key"><i class="f-yellow">＊</i>所属类别：</span>
                    <select name="prodclass" style="width: 300px;height:30px;border: 1px solid #e3e3e3;">
                        <option value="-1">请选择</option>
                        <%
                            if (columns!=null) {
                                for(int ii=0;ii<columns.size();ii++) {
                                    Column column1 = columns.get(ii);
                                    if (column.getID().compareTo(column1.getID())==0)
                                        out.println("<option value=\"" + column1.getID() + "\"" + " selected=\"selected\">" + column1.getCNAME() + "</option>");
                                    else
                                        out.println("<option value=\"" + column1.getID() + "\"" + ">" + column1.getCNAME() + "</option>");
                                }
                            }
                        %>
                    </select>
                </li>
                <li class="describe-li">
                    <span class="form-key"><i class="f-yellow">＊</i>商品描述：</span>
                    <textarea name="brief" cols="" rows=""><%=(article!=null)?article.getSUMMARY():""%></textarea>
                    <span class="text-error">使用字母、数字和符号两种及以上的组合</span>
                </li>
                <li class="upload-book">
                    <span class="form-key"><i class="f-yellow">＊</i>商品图片：</span>

                    <div class="upload-book-box">
                        <div id="preview">
                            <img id="imghead" src="<%="../../sites/" + sitename +article.getDIRNAME() + "images/" + article.getBIGPIC()%>" alt=""/>
                        </div>

                        <span class="pre-btn">上传图片</span>
                        <input name="prodimg" id="imginput" type="file" onchange="yulan(this)">
                    </div>
                </li>

            </ul>
            <div class="clearfix"></div>
            <div class="submit">
                <a href="javascript:doRelease();">发布商品</a>
            </div>
        </form>
    </div>
</div>
</div>
<script>
    /*图片上传预览*/
    $(function () {
        $(".pre-btn").on("click", function () {
            $('#imginput').trigger("click");
        })
    })

    function doRelease() {
        var ajaxFormOption = {
            type: "post",                                                     //提交方式
            dataType: "json",                                                //数据类型
            url: "doUpdateProduct.jsp?doCreate=true",       //请求url
            success: function (data) {                                         //提交成功的回调函数
                if (data.ID > 0) {
                    alert("商品信息上传成功")
                    window.location.href = "index.jsp";
                }
            }
        };

        //不需要submit按钮，可以是任何元素的click事件
        $("#releaseprodid").ajaxSubmit(ajaxFormOption);
    }

    //图片上传预览    IE是用了滤镜。
    function yulan(file) {
        var imghead = $("#imghead");
        var explorer = navigator.userAgent;
        var imgSrc = $(file)[0].value;
        if (explorer.indexOf('MSIE') >= 0) {
            if (!/\.(jpg|jpeg|png|JPG|PNG|JPEG)$/.test(imgSrc)) {
                imgSrc = "";
                imghead.attr("src", "img/authentication/img-add.jpg");
                alert("格式错误");
                $(".pre-btn").text("上传图片");
                return false;
            } else {
                /*imghead.attr("src", imgSrc);*/
                $(".pre-btn").text("修改图片")
                previewImage(file);
            }
        } else {
            if (!/\.(jpg|jpeg|png|JPG|PNG|JPEG)$/.test(imgSrc)) {
                imgSrc = "";
                imghead.attr("src", "img/authentication/img-add.jpg");
                alert("格式错误");
                $(".pre-btn").text("上传图片");
                return false;
            } else {
                /*   var file = $(this)[0].files[0];
                 var url = URL.createObjectURL(file);
                 imghead.attr("src", url);*/
                $(".pre-btn").text("修改图片")
                previewImage(file)
            }
        }
    }

    function previewImage(file) {
        var MAXWIDTH = 98;
        var MAXHEIGHT = 98;
        var div = document.getElementById('preview');
        if (file.files && file.files[0]) {
            div.innerHTML = '<img id=imghead>';
            var img = document.getElementById('imghead');
            img.onload = function () {
                var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                img.width = rect.width;
                img.height = rect.height;
//                 img.style.marginLeft = rect.left+'px';
                img.style.marginTop = rect.top + 'px';
            }
            var reader = new FileReader();
            reader.onload = function (evt) {
                img.src = evt.target.result;
            }
            reader.readAsDataURL(file.files[0]);
        }
        else //兼容IE
        {
            var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
            file.select();
            var src = document.selection.createRange().text;
            div.innerHTML = '<img id=imghead>';
            var img = document.getElementById('imghead');
            img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
            var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
            status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width + ',' + rect.height);
            div.innerHTML = "<div id=divhead style='width:" + rect.width + "px;height:" + rect.height + "px;margin-top:" + rect.top + "px;" + sFilter + src + "\"'></div>";
        }
    }

    function clacImgZoomParam(maxWidth, maxHeight, width, height) {
        var param = {top: 0, left: 0, width: width, height: height};
        if (width > maxWidth || height > maxHeight) {
            rateWidth = width / maxWidth;
            rateHeight = height / maxHeight;

            if (rateWidth > rateHeight) {
                param.width = maxWidth;
                param.height = Math.round(height / rateWidth);
            } else {
                param.width = Math.round(width / rateHeight);
                param.height = maxHeight;
            }
        }
        param.left = Math.round((maxWidth - param.width) / 2);
        param.top = Math.round((maxHeight - param.height) / 2);
        return param;
    }
</script>
</body>
</html>
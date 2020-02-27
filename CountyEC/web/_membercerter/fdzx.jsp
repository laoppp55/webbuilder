<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.dubboservice.service.*" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.*" %>
<%@ page import="com.bizwink.dao.TemplateManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username = null;
    int samsiteid = 0;

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

    Siteinfo siteinfo = null;
    Users user = null;
    String json_data = null;
    String brief_content = "";
    String notes_content = "";
    String lxfs_content = "";
    String tshd_content = "";
    if (username != null) {
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            ColumnService columnService = (ColumnService)appContext.getBean("ColumnService");
            SiteinfoService siteinfoService = (SiteinfoService)appContext.getBean("SiteinfoService");
            UsersService usersService = (UsersService)appContext.getBean("UsersService");
            TemplateService templateService = (TemplateService)appContext.getBean("TemplateService");
            siteinfo = siteinfoService.selectBySitename(username + ".gugulx.com");

            if (siteinfo==null)
                System.out.println("没有找到相应的站点");
            else {
                samsiteid = siteinfo.getSAMSITEID().intValue();
                json_data = columnService.getColumnJsonsBySiteid(siteinfo.getSITEID(),siteinfo.getSAMSITEID());
                System.out.println(json_data);
            }
            user = usersService.getByUserId(username);
            //TemplateManager templateManager = new TemplateManager();
            brief_content = StringUtil.getBodyFromHTML(templateService.getTemplate(siteinfo.getSITEID().intValue(),52719,3).getCONTENT());
            notes_content = StringUtil.getBodyFromHTML(templateService.getTemplate(siteinfo.getSITEID().intValue(), 52737, 3).getCONTENT());
            lxfs_content = StringUtil.getBodyFromHTML(templateService.getTemplate(siteinfo.getSITEID().intValue(), 52738, 3).getCONTENT());
            tshd_content = StringUtil.getBodyFromHTML(templateService.getTemplate(siteinfo.getSITEID().intValue(), 52758, 3).getCONTENT());
            if (brief_content!= null) {
                brief_content = brief_content.replace("\"","'");
                brief_content = brief_content.replace("\r","");
                brief_content = brief_content.replace("\n","");
            } else {
                brief_content ="";
            }

            if (notes_content!=null) {
                notes_content = notes_content.replace("\"","'");
                notes_content = notes_content.replace("\r","");
                notes_content = notes_content.replace("\n","");
            } else {
                notes_content ="";
            }
            if (lxfs_content!=null) {
                lxfs_content = lxfs_content.replace("\"","'");
                lxfs_content = lxfs_content.replace("\r","");
                lxfs_content = lxfs_content.replace("\n","");
            } else {
                lxfs_content ="";
            }
            if (tshd_content!=null) {
                tshd_content = tshd_content.replace("\"","'");
                tshd_content = tshd_content.replace("\r","");
                tshd_content = tshd_content.replace("\n","");
            } else {
                tshd_content = "";
            }
        }
    } else {
        response.sendRedirect("/_members/login.jsp");
    }

    if (siteinfo == null) response.sendRedirect("/_members/login.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>农家院用户中心</title>
<link href="http://www.gugulx.com/css/fdzx_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="http://www.gugulx.com/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link REL="stylesheet" TYPE="text/css" HREF="http://www.gugulx.com/css/editor.css">
<!--[if IE 6]>
<script src="http://www.gugulx.com/js/iepng.js" type="text/javascript"></script>
<script type="text/javascript"  src="http://www.gugulx.com/js/index_png.js" ></script>
<![endif]-->
<script type="text/javascript" src="http://www.gugulx.com/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="http://www.gugulx.com/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="http://www.gugulx.com/js/jquery.form.js"></script>
<script src="http://www.gugulx.com/ckeditor/ckeditor.js"></script>
<script src="http://www.gugulx.com/js/fdzx.js" type="text/javascript"></script>

<script type="text/javascript" language="javascript">
/**自定义的数据源，ztree支持json,数组，xml等格式的**/
var columnnodes = <%=json_data%>;
/**ztree的参数配置，setting主要是设置一些tree的属性，是本地数据源，还是远程，动画效果，是否含有复选框等等**/
var setting = {
    check: { /**复选框**/
    enable: false,
        chkboxType: {"Y":"", "N":""}
    },
    view: {
        //dblClickExpand: false,
        expandSpeed: 300 //设置树展开的动画速度，IE6下面没效果，
    },
    data: {
        simpleData: {   //简单的数据源，一般开发中都是从数据库里读取，API有介绍，这里只是本地的
            enable: true,
            idKey: "id",  //id和pid，这里不用多说了吧，树的目录级别
            pIdKey: "pId",
            rootPId: 0   //根节点
        }
    },
    callback: {     /**回调函数的设置，随便写了两个**/
    beforeClick: beforeClick,
        onCheck: onCheck
    }
};

function onCheck(e, treeId, treeNode) {
    alert("onCheck");
}

$(document).ready(function(){//初始化ztree对象
    personinfo();
    var zTreeDemo = $.fn.zTree.init($("#columnTree"),setting, columnnodes);
    $("#_ul li").each(function(a,i){
        $(this).click(function(){
            $("#_ul li").each(function(m,n){
                $(this).removeAttr("class");
                $("#_div"+m).css("display","none");
            });
            $(this).attr("class","ts");
            $("#_div"+a).css("display","block");
        });
    });
});

function logoff() {
    if(confirm("您确定要退出吗？")){
        $.post("/_members/logoff.jsp",{},
                function(data) {
                    if (data.indexOf("<logoutflag>") > -1) {
                        //window.opener.location.reload();
                        window.open('','_self','');
                        window.close();
                    }
                }
        )
    }
}

function tijiao(actflag) {
    var siteid=<%=siteinfo.getSITEID()%>;
    var content = "";
    var topicname = "";
    var cname = "";
    if (actflag == "njyjs") {
        var colid = document.njyform.columnid.value;
        topicname = "brief";
        cname = "农家院简介";
        content = CKEDITOR.instances['njyjscontent'].getData();
    } else if (actflag == "zysx") {
        var colid = document.zysxform.columnid.value;
        topicname = "notes";
        cname = "注意事项";
        alert(actflag + "==" + colid);
        content = CKEDITOR.instances['zysxcontent'].getData();
        alert(content);
    } else if (actflag == "lxfs") {
        var colid = form3.columnid.value;
        topicname = "contactus";
        cname = "联系我们";
        content = CKEDITOR.instances['lxfscontent'].getData();
    } else if (actflag == "tshd") {
        var colid = form4.columnid.value;
        topicname = "tesehd";
        cname = "特色活动";
        content = CKEDITOR.instances['tshdcontent'].getData();
    }

    alert("ssss");

    /*$.post("/_membercerter/njytopic.jsp",{
                columnid:colid,
                content:content,
                topicname:topicname,
                cname:cname,
                siteid:siteid
            },
            function(data) {
                var value=data.errcode;
                var text=data.errmsg;
                var modelname = data.modelname;
                alert(value +"===" + text + "==="+modelname);
            },
            "json"
    )*/

    return false;
}

function beforeClick(treeId, treeNode) {
    if (treeNode.id==52719) {                    //农家院介绍
        var buf = "<div class=\"grzx_1\">\r\n" +
                "<div class=\"grzx_1_bt\">农家院介绍</div>\r\n" +
                "<div class=\"grzx_1_sub\">&nbsp;</div>\r\n" +
                "<div class=\"clear\"></div>\r\n" +
                "</div>\r\n" +
                "<div class=\"grzx_2\">\r\n" +
                "<form method=\"post\" action=\"\" name=\"njyform\" onsubmit=\"return tijiao('njyjs');\">\r\n" +
                "<input type=\"hidden\" name=\"columnid\" value=" + treeNode.id + ">\r\n" +
                "<div class=\"njyjs_1\"><textarea name=\"njyjs\"  id=\"njyjscontent\" cols=\"100\" rows=\"30\"></textarea></div>\r\n" +
                "<div class=\"njyjs_1\"><span>农家院描述不得多于500个字符</span></div>\r\n" +
                "<div class=\"njyjs_1\"><input name=\"njyjs\" type=\"image\" src=\"http://www.gugulx.com/images/20160120_8.jpg\" /></div>\r\n" +
                "</form>\r\n" +
                "</div>\r\n" +
                "<script type=\"text/javascript\">\r\n" +
                "CKEDITOR.replace('njyjscontent', {\r\n" +
                "    fullPage: true,\r\n" +
                "    allowedContent:true,\r\n" +
                "    filebrowserBrowseUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52719',\r\n" +
                "    filebrowserImageBrowseUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52719',\r\n" +
                "    filebrowserUploadUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52719',\r\n" +
                "    filebrowserImageUploadUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52719'\r\n" +
                "});\r\n" +
                "CKEDITOR.instances.njyjscontent.setData(\"<%=brief_content%>\");\r\n" +
                "<\/script>\r\n";

        $divs = $('#_div').children('div');
        $divs.each(	function(){	//这里可以对每一个div进行操作
            $(this).css("display","none");
        });

        $("#_div4").css("display","block");
        $("#_div4").html(buf);
    } else if (treeNode.id==52737) {        //注意事项
        var buf = "    <div class=\"grzx_1\">\r\n" +
                "<div class=\"grzx_1_bt\">注意事项</div>\r\n" +
                "        <div class=\"grzx_1_sub\">&nbsp;</div>\r\n" +
                "<div class=\"clear\"></div>\r\n" +
                "        </div>\r\n" +
                "        <div class=\"grzx_2\">\r\n" +
                "<form method=\"post\" action=\"\" name=\"zysxform\" onsubmit=\"return tijiao('zysx');\">\r\n" +
                "<input type=\"hidden\" name=\"columnid\" value=" + treeNode.id + ">\r\n" +
                "        <div class=\"njyjs_1\"><textarea name=\"zysx\"   id=\"zysxcontent\" cols=\"100\" rows=\"30\"></textarea></div>\r\n" +
                "<div class=\"njyjs_1\"><span>注意事项的描述不得多于500个字符</span></div>\r\n" +
                "<div class=\"njyjs_1\"><input name=\"zysx\" type=\"image\" src=\"http://www.gugulx.com/images/20160120_8.jpg\" /></div>\r\n" +
                "</form>\r\n" +
                "</div>\r\n" +
                "<script type=\"text/javascript\">\r\n" +
                "CKEDITOR.replace('zysxcontent', {\r\n" +
                "    fullPage: true,\r\n" +
                "    allowedContent: true,\r\n" +
                "    filebrowserBrowseUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52737',\r\n" +
                "    filebrowserImageBrowseUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52737',\r\n" +
                "    filebrowserUploadUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52737',\r\n" +
                "    filebrowserImageUploadUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52737'\r\n" +
                "});\r\n" +
                "CKEDITOR.instances.zysxcontent.setData(\"<%=notes_content%>\");\r\n" +
                "<\/script>\r\n";

        $divs = $('#_div').children('div');
        $divs.each(	function(){	//这里可以对每一个div进行操作
            $(this).css("display","none");
        });

        $("#_div9").css("display","block");
        $("#_div9").html(buf);
    } else if (treeNode.id==52738) {        //联系方式
        var buf="    <div class=\"grzx_1\">"+
                "<div class=\"grzx_1_bt\">联系方式</div>"+
                "        <div class=\"grzx_1_sub\">&nbsp;</div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"grzx_2\">"+
                "<form method=\"post\" action=\"\" name=\"form3\">\r\n" +
                "<input type=\"hidden\" name=\"columnid\" value=" + treeNode.id + ">\r\n" +
                "        <div class=\"njyjs_1\"><textarea name=\"lxfs\" id=\"lxfscontent\" cols=\"100\" rows=\"30\"></textarea></div>"+
                "<div class=\"njyjs_1\"><span>联系方式的描述不得多于500个字符</span></div>"+
                "<div class=\"njyjs_1\"><input name=\"save\" type=\"button\" onclick=\"javascript:tijiao('lxfs');\" class=\"njy_btn\" /></div>"+
                "</form>\r\n" +
                "</div>\r\n" +
                "<script type=\"text/javascript\">\r\n" +
                "CKEDITOR.replace('lxfscontent', {\r\n" +
                "    fullPage: true,\r\n" +
                "    allowedContent: true,\r\n" +
                "    filebrowserBrowseUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52738',\r\n" +
                "    filebrowserImageBrowseUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52738',\r\n" +
                "    filebrowserUploadUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52738',\r\n" +
                "    filebrowserImageUploadUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52738'\r\n" +
                "});\r\n" +
                "CKEDITOR.instances.lxfscontent.setData(\"<%=lxfs_content%>\");\r\n" +
                "<\/script>\r\n";

        $divs = $('#_div').children('div');
        $divs.each(	function(){	//这里可以对每一个div进行操作
            $(this).css("display","none");
        });

        $("#_div8").css("display","block");
        $("#_div8").html(buf);
    } else if (treeNode.id==52736) {        //房型
        htmlobj=$.ajax({
            url:"/_membercerter/house/houselist.jsp",
            type:'post',
            dataType:'html',
            data:{
                colid:treeNode.id
            },
            async:false,
            cache:false,
            success:function(data){
                $divs = $('#_div').children('div');
                $divs.each(	function(){	//这里可以对每一个div进行操作
                    $(this).css("display","none");
                });

                $("#_div5").css("display","block");
                $("#_div5").html(data);
            }
        });
    } else if (treeNode.id==52739) {        //过客留影
        htmlobj=$.ajax({
            url:"/_membercerter/tourist/touristlist.jsp",
            type:'post',
            dataType:'html',
            data:{
                colid:treeNode.id
            },
            async:false,
            cache:false,
            success:function(data){
                $divs = $('#_div').children('div');
                $divs.each(	function(){	//这里可以对每一个div进行操作
                    $(this).css("display","none");
                });

                $("#_div10").css("display","block");
                $("#_div10").html(data);
            }
        });
    } else if (treeNode.id==52757) {         //特色美食
        htmlobj=$.ajax({
            url:"/_membercerter/delicious/foodslist.jsp",
            type:'post',
            dataType:'html',
            data:{
                colid:treeNode.id
            },
            async:false,
            cache:false,
            success:function(data){
                $divs = $('#_div').children('div');
                $divs.each(	function(){	//这里可以对每一个div进行操作
                    $(this).css("display","none");
                });
                $("#_div6").css("display","block");
                $("#_div6").html(data);
            }
        });
    } else if (treeNode.id==52758) {         //特色活动
        var buf ="    <div class=\"grzx_1\">\r\n"+
                "<div class=\"grzx_1_bt\">特色活动</div>\r\n"+
                "<div class=\"grzx_1_sub\">&nbsp;</div>\r\n"+
                "<div class=\"clear\"></div>\r\n"+
                "</div>\r\n"+
                "<div class=\"grzx_2\">\r\n"+
                "<form method=\"post\" action=\"\" name=\"form4\">\r\n" +
                "<input type=\"hidden\" name=\"columnid\" value=" + treeNode.id + ">\r\n" +
                "        <div class=\"njyjs_1\"><textarea name=\"tshd\" id=\"tshdcontent\" cols=\"100\" rows=\"30\"></textarea></div>\r\n"+
                "<div class=\"njyjs_1\"><span>特色活动的描述不得多于500个字符</span></div>\r\n"+
                "<div class=\"njyjs_1\"><input name=\"tshd\" type=\"button\" onclick=\"javascript:tijiao('tshd');\" class=\"njy_btn\"/></div>\r\n"+
                "</div>\r\n" +
                "</form>\r\n" +
                "<script type=\"text/javascript\">\r\n" +
                "CKEDITOR.replace('tshdcontent', {\r\n" +
                "    fullPage: true,\r\n" +
                "    allowedContent: true,\r\n" +
                "    filebrowserBrowseUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52758',\r\n" +
                "    filebrowserImageBrowseUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52758',\r\n" +
                "    filebrowserUploadUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52758',\r\n" +
                "    filebrowserImageUploadUrl:'http://<%=username%>.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52758'\r\n" +
                "});\r\n" +
                "CKEDITOR.instances.tshdcontent.setData(\"<%=tshd_content%>\");\r\n" +
                "<\/script>\r\n";

        $divs = $('#_div').children('div');
        $divs.each(	function(){	//这里可以对每一个div进行操作
            $(this).css("display","none");
        });

        $("#_div7").css("display","block");
        $("#_div7").html(buf);
    }
}

function gotopage(pageno,columnid) {
    htmlobj=$.ajax({
        url:"/_membercerter/delicious/foodslist.jsp",
        type:'post',
        dataType:'html',
        data:{
            colid:columnid,
            page:pageno
        },
        async:false,
        cache:false,
        success:function(data){
            $divs = $('#_div').children('div');
            $divs.each(	function(){	//这里可以对每一个div进行操作
                $(this).css("display","none");
            });
            $("#_div6").css("display","block");
            $("#_div6").html(data);
        }
    });
}

function delarticle(pageno,columnid,articleid) {
    var url = "";
    var list_url = "";
    if (columnid == 52736) {
        url = "/_membercerter/house/removehouse.jsp";
        list_url = "/_membercerter/house/houselist.jsp";
    } else if (columnid == 52739) {
        url = "/_membercerter/tourist/removetourist.jsp";
        list_url = "/_membercerter/tourist/touristlist.jsp";
    } else if (columnid == 52757) {
        url = "/_membercerter/delicious/removefood.jsp";
        list_url = "/_membercerter/delicious/foodslist.jsp";
    }

    obj1 = $.ajax({
        url:url,
        type:'post',
        dataType:'json',
        data:{
            article:articleid
        },
        async:false,
        cache:false,
        success:function(data){
            if (data.errcode ==0) {
                htmlobj=$.ajax({
                    url:list_url,
                    type:'post',
                    dataType:'html',
                    data:{
                        colid:columnid,
                        page:pageno
                    },
                    async:false,
                    cache:false,
                    success:function(data){
                        $divs = $('#_div').children('div');
                        $divs.each(	function(){	//这里可以对每一个div进行操作
                            $(this).css("display","none");
                        });
                        if (columnid == 52736) {
                            $("#_div5").css("display","block");
                            $("#_div5").html(data);
                        } else if (columnid == 52739) {
                            $("#_div10").css("display","block");
                            $("#_div10").html(data);
                        } else if (columnid == 52757) {
                            $("#_div6").css("display","block");
                            $("#_div6").html(data);
                        }
                    }
                });
            } else {
                alert("删除文章失败！！！");
            }
        }
    })
}

function createArticle(columnid) {
    alert(columnid);
    var url = "";
    var list_url = "";
    if (columnid == 52736) {
        url = "/_membercerter/house/createhouse.jsp";
        list_url = "/_membercerter/house/houselist.jsp";
    } else if (columnid == 52739) {
        url = "/_membercerter/tourist/createtourist.jsp";
        list_url = "/_membercerter/tourist/touristlist.jsp";
    } else if (columnid == 52757) {
        url = "/_membercerter/delicious/createfood.jsp";
        list_url = "/_membercerter/delicious/foodslist.jsp";
    }

    if($("#upload_file").val() != '') $("#msformid").submit();

    //iframe加载响应，初始页面时也有一次，此时data为null。
    $("#exec_target").load(function(){
        var data = $(window.frames['exec_target'].document.body).find("textarea").html();
        //若iframe携带返回数据，则显示在feedback中
        if(data != null){
            $("#feedback").append(data.replace(/&lt;/g,'<').replace(/&gt;/g,'>'));
            $("#upload_file").val('');
        }
    });
}

function updatearticle(columnid,articleid) {
    alert(columnid);
    alert(articleid);
    var url = "";
    if (columnid == 52736) {
        url = "/_membercerter/house/updatehouse.jsp";
    } else if (columnid == 52739) {
        url = "/_membercerter/tourist/updatetourist.jsp";
    } else if (columnid == 52757) {
        url = "/_membercerter/delicious/updatefood.jsp";
    }

    htmlobj=$.ajax({
        url:url,
        type:'post',
        dataType:'html',
        data:{
            colid:treeNode.id
        },
        async:false,
        cache:false,
        success:function(data){
            $divs = $('#_div').children('div');
            $divs.each(	function(){	//这里可以对每一个div进行操作
                $(this).css("display","none");
            });

            if (columnid == 52736) {
                $("#_div5").css("display","block");
                $("#_div5").html(data);
            } else if (columnid == 52739) {
                $("#_div10").css("display","block");
                $("#_div10").html(data);
            } else if (columnid == 52757) {
                $("#_div6").css("display","block");
                $("#_div6").html(data);
            }
        }
    });
}

function orderList() {
    htmlobj=$.ajax({
        url:"/_membercerter/orderList.jsp",
        type:'post',
        dataType:'html',
        data:{
            colid:1234
        },
        async:false,
        cache:false,
        success:function(data){
            $divs = $('#_div').children('div');
            $divs.each(	function(){	//这里可以对每一个div进行操作
                $(this).css("display","none");
            });

            $("#_div1").css("display","block");
            $("#_div1").html(data);
        }
    });
}

function personinfo() {
    htmlobj=$.ajax({
        url:"/_membercerter/personinfo.jsp",
        type:'post',
        dataType:'html',
        data:{
            colid:1234,
            samsiteid:<%=samsiteid%>
        },
        async:false,
        cache:false,
        success:function(data){
            $divs = $('#_div').children('div');
            $divs.each(	function(){	//这里可以对每一个div进行操作
                $(this).css("display","none");
            });

            $("#_div0").css("display","block");
            $("#_div0").html(data);
        }
    });
}
</script>
</head>

<body>
<div class="top_fdzx">
    <div class="top_con">
        <div class="logo_fdzx"><img src="http://www.gugulx.com/images/logo.png" /></div>
        <div class="menu_fdzx"><a href="http://www.gugulx.com"><span>首页</span></a><a href="http://www.gugulx.com/njl.shtml?type=njy">农家乐</a><a href="http://www.gugulx.com/mlxc.shtml?type=mlxc">美丽乡村</a><a href="http://www.gugulx.com/czy.shtml?type=czy">采摘园</a><a href="http://www.gugulx.com/list.shtml?type=jq">景点景区</a></div>
        <div class="top_sub">
            <%if (siteinfo.getSAMSITEID().intValue()==2991) {%>
            <div class="top_sub_1">农家院主：<%=username%></div>
            <%} else if ((siteinfo.getSAMSITEID().intValue()==4592)) {%>
            <div class="top_sub_1">村长：<%=username%></div>
            <%} else if ((siteinfo.getSAMSITEID().intValue()==2992)) {%>
            <div class="top_sub_1">园主：<%=username%></div>
            <%} else if ((siteinfo.getSAMSITEID().intValue()==2993)) {%>
            <div class="top_sub_1">景区经理：<%=username%></div>
            <%} else {%>
            <div class="top_sub_1">游客：<%=username%></div>
            <%}%>
            <div class="top_sub_2"><a href="#" onclick="javascript:logoff()">[退出]</a></div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<!-- top end  -->
<div class="box">
    <div class="tit_2016">
        <div class="tit_2016_1">我的农家院</div>
        <div class="tit_2016_2">&nbsp;</div>
        <div class="tit_2016_3">&nbsp;</div>
    </div>
    <div class="clear"></div>
</div>
<div class="box">
    <div class="neirong">
        <div class="ny_left">
            <div class="biaoti">账户设置</div>
            <div class="list">
                <ul id="_ul">
                    <%if (siteinfo.getSAMSITEID().intValue()==2991) {%>
                    <li class="ts"><a href="#" onclick="javascript:personinfo();">农家院主资料</a></li>
                    <%} else if ((siteinfo.getSAMSITEID().intValue()==4592)) {%>
                    <li class="ts"><a href="#" onclick="javascript:personinfo();">村长资料</a></li>
                    <%} else if ((siteinfo.getSAMSITEID().intValue()==2992)) {%>
                    <li class="ts"><a href="#" onclick="javascript:personinfo();">园主资料</a></li>
                    <%} else if ((siteinfo.getSAMSITEID().intValue()==2993)) {%>
                    <li class="ts"><a href="#" onclick="javascript:personinfo();">景区经理资料</a></li>
                    <%} else {%>
                    <li class="ts"><a href="#" onclick="javascript:personinfo();">游客资料</a></li>
                    <%}%>
                    <li><a href="#" onclick="javascript:orderList();">管理订单</a></li>
                    <li><a href="#" onclick="javascript:leaveWords(1,<%=siteinfo.getSITEID()%>);">查看留言</a></li>
                    <li><a href="#" onclick="javascript:commentList(1,<%=siteinfo.getSITEID()%>);">查看点评</a></li>
                </ul>
            </div>
            <%if (siteinfo.getSAMSITEID().intValue() > 0) {%>
            <div class="biaoti" style="margin-top:20px;">
                <%if (siteinfo.getSAMSITEID().intValue()==2991) {%>
                农家院信息维护
                <%} else if ((siteinfo.getSAMSITEID().intValue()==4592)) {%>
                美丽乡村信息维护
                <%} else if ((siteinfo.getSAMSITEID().intValue()==2992)) {%>
                采摘园信息维护
                <%} else if ((siteinfo.getSAMSITEID().intValue()==2993)) {%>
                景点景区信息维护
                <%}%>
            </div>
            <div class="ztree" id="columnTree">
            </div>
            <%}%>
        </div>
        <div class="ny_right" id="_div">
            <!--  grzl start  -->
            <div style="display:block" id="_div0">
            </div>
            <!--  grzl end  -->
            <!--  ddgl start  -->
            <div style="display:none;" id="_div1">
            </div>
            <!--  ddgl end  -->
            <!--  ckly start  -->
            <div style="display:none;" id="_div2">
            </div>
            <!--  ckly end  -->
            <!--  ckdp start  -->
            <div style="display:none" id="_div3">
            </div>
            <!--  ckdp end  -->
            <!--  njyjs start  -->
            <div style="display:none" id="_div4">
            </div>
            <!--  njyjs end  -->
            <!--  fjfx start  -->
            <div style="display:none" id="_div5">
            </div>
            <!--  fjfx end  -->
            <!--  tsms start  -->
            <div style="display:none" id="_div6">
            </div>
            <!--  tsms end  -->
            <!--  tshd start  -->
            <div style="display:none" id="_div7">
            </div>
            <!--  tshd end  -->
            <!--  lxfs start  -->
            <div style="display:none" id="_div8">
            </div>
            <!--  lxfs end  -->
            <!--  zysx start  -->
            <div style="display:none" id="_div9">
            </div>
            <!--  zysx end  -->
            <!--  krly start  -->
            <div style="display:none" id="_div10">
            </div>
            <!--  krly end  -->
        </div>
        <div class="clear"></div>
    </div>
</div>
<!-- center end  -->
<div class="bg3_fdzx">
    <div class="footer">
        <div class="footer_sub"><a href="#">联系我们</a> | <a href="#">法律声明</a> | <a href="#">网站地图</a> | <a href="#">关于我们</a> | <a href="#">ICP备案信息</a></div>
        <div class="footer_p">版权信息归周边游旅行网所有</div>
    </div>
</div>
<!-- footer end  -->
</body>
</html>
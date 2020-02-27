<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.dubboservice.service.*" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    Siteinfo siteinfo = null;
    Users user = null;
    String json_data = null;
    if (username != null) {
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            ColumnService columnService = (ColumnService)appContext.getBean("ColumnService");
            SiteinfoService siteinfoService = (SiteinfoService)appContext.getBean("SiteinfoService");
            UsersService usersService = (UsersService)appContext.getBean("UsersService");
            siteinfo = siteinfoService.selectBySitename(username + ".coosite.com");
            json_data = columnService.getColumnJsonsBySiteid(siteinfo.getSITEID(),siteinfo.getSAMSITEID());
            user = usersService.getByUserId(username);
        }
    } else {
        response.sendRedirect("/_members/login.jsp");
    }

    if (siteinfo == null) response.sendRedirect("/_members/login.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>景区用户中心</title>
<link href="http://www.gugulx.com/css/fdzx_css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="http://www.gugulx.com/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link REL="stylesheet" TYPE="text/css" HREF="http://www.gugulx.com/css/editor.css">
<!--[if IE 6]>
<script src="http://www.gugulx.com/js/iepng.js" type="text/javascript"></script>
<script type="text/javascript"  src="http://www.gugulx.com/js/index_png.js" ></script>
<![endif]-->
<script type="text/javascript" src="http://www.gugulx.com/jstree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="http://www.gugulx.com/jstree/jquery.ztree.core-3.5.js"></script>
<script src="http://www.gugulx.com/ckeditor/ckeditor.js"></script>

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
                    alert(data);
                    if (data.indexOf("<logoutflag>") > -1) {
                        //window.opener.location.reload();
                        //window.opener = null;
                        window.open('','_self','');
                        window.close();
                    }
                }
        )
    }
}

function tijiao(actflag) {
    alert(actflag);

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
                "<form method=\"post\" action=\"\" name=\"form1\">\r\n" +
                "<input type=\"hidden\" name=\"columnid\" value=" + treeNode.id + ">\r\n" +
                "<div class=\"njyjs_1\"><textarea name=\"njyjs\"  id=\"njyjscontent\" cols=\"100\" rows=\"30\"></textarea></div>\r\n" +
                "<div class=\"njyjs_1\"><span>对于农家院的描述不得少于200个字符</span></div>\r\n" +
                "<div class=\"njyjs_1\"><input name=\"njyjs\" type=\"button\" onclick=\"javascript:tijiao('njyjs');\" class=\"njy_btn\" /></div>\r\n" +
                "</form>\r\n" +
                "</div>\r\n" +
                "<script type=\"text/javascript\">\r\n" +
                "CKEDITOR.replace('njyjscontent', {\r\n" +
                "    fullPage: true,\r\n" +
                "    allowedContent:true,\r\n" +
                "    filebrowserBrowseUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52719',\r\n" +
                "    filebrowserImageBrowseUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52719',\r\n" +
                "    filebrowserUploadUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52719',\r\n" +
                "    filebrowserImageUploadUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52719'\r\n" +
                "});\r\n" +
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
                "<form method=\"post\" action=\"\" name=\"form1\">\r\n" +
                "<input type=\"hidden\" name=\"columnid\" value=" + treeNode.id + ">\r\n" +
                "        <div class=\"njyjs_1\"><textarea name=\"zysx\"   id=\"zysxcontent\" cols=\"100\" rows=\"30\"></textarea></div>\r\n" +
                "<div class=\"njyjs_1\"><span>对于农家院的描述不得少于200个字符</span></div>\r\n" +
                "<div class=\"njyjs_1\"><input name=\"zysx\" type=\"button\" onclick=\"javascript:tijiao('zysx');\" class=\"njy_btn\" /></div>\r\n" +
                "</form>\r\n" +
                "</div>\r\n" +
                "<script type=\"text/javascript\">\r\n" +
                "CKEDITOR.replace('zysxcontent', {\r\n" +
                "    fullPage: true,\r\n" +
                "    allowedContent: true,\r\n" +
                "    filebrowserBrowseUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52737',\r\n" +
                "    filebrowserImageBrowseUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52737',\r\n" +
                "    filebrowserUploadUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52737',\r\n" +
                "    filebrowserImageUploadUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52737'\r\n" +
                "});\r\n" +
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
                "<form method=\"post\" action=\"\" name=\"form1\">\r\n" +
                "<input type=\"hidden\" name=\"columnid\" value=" + treeNode.id + ">\r\n" +
                "        <div class=\"njyjs_1\"><textarea name=\"lxfs\" id=\"lxfscontent\" cols=\"100\" rows=\"30\"></textarea></div>"+
                "<div class=\"njyjs_1\"><span>对于农家院的描述不得少于200个字符</span></div>"+
                "<div class=\"njyjs_1\"><input name=\"\" type=\"button\" onclick=\"javascript:tijiao('lxfs');\" class=\"njy_btn\" /></div>"+
                "</form>\r\n" +
                "</div>\r\n" +
                "<script type=\"text/javascript\">\r\n" +
                "CKEDITOR.replace('lxfscontent', {\r\n" +
                "    fullPage: true,\r\n" +
                "    allowedContent: true,\r\n" +
                "    filebrowserBrowseUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52738',\r\n" +
                "    filebrowserImageBrowseUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52738',\r\n" +
                "    filebrowserUploadUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52738',\r\n" +
                "    filebrowserImageUploadUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52738'\r\n" +
                "});\r\n" +
                "<\/script>\r\n";

        $divs = $('#_div').children('div');
        $divs.each(	function(){	//这里可以对每一个div进行操作
            $(this).css("display","none");
        });

        $("#_div8").css("display","block");
        $("#_div8").html(buf);
    } else if (treeNode.id==52736) {        //房型
        var buf="    <div class=\"grzx_1\">" +
                "<div class=\"grzx_1_bt\">房间房型</div>" +
                "       <div class=\"grzx_1_sub\">&nbsp;</div>" +
                "<div class=\"clear\"></div>" +
                "        </div>" +
                "        <div class=\"grzx_2\">" +
                "        <div class=\"fjfx_1\"><span>*</span> 房间房型：</div>" +
                "<div class=\"fjfx_2\">" +
                "        <select name=\"select\" style=\"width:300px;\">" +
                "        <option value=\"单人间\">单人间</option>" +
                "        <option value=\"双人间\">双人间</option>" +
                "        <option value=\"多人间\">多人间</option>" +
                "        </select>" +
                "        </div>" +
                "       <div class=\"clear\"></div>" +
                "        </div>" +
                "        <div class=\"grzx_2\">" +
                "        <div class=\"fjfx_1\"><span>*</span> 房型描述：</div>"+
                "<div class=\"fjfx_2\"><textarea name=\"\" cols=\"56\" rows=\"5\"></textarea></div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "<div class=\"grzx_2\">"+
                "<div class=\"fjfx_1\"><span>*</span>房型图片：</div>"+
                "<div class=\"fjfx_2\"><input name=\"\" type=\"text\" class=\"fjfx_txt\" /></div>"+
                "<div class=\"fjfx_3\"><input name=\"\" type=\"button\" value=\"选择图片\" /></div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "<div class=\"grzx_2\">"+
                "<div class=\"fjfx_1\"><input name=\"\" type=\"button\" class=\"njy_btn\" /></div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "<div class=\"grzx_2\">"+
                "<div class=\"ddlb_ly_1\">"+
                "<div class=\"fjfx_pic\">缩图</div>"+
                "<div class=\"fjfx_pic1\">房型描述</div>"+
                "<div class=\"fjfx_pic2\">发布日期</div>"+
                "<div class=\"fjfx_pic3\">操作</div>"+
                "       </div>"+
                "<div class=\"dd_list_fx\">"+
                "<div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_4.jpg\" /></div>"+
                "<div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "<div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "<div class=\"dd_list_fx\">"+
                "<div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_4.jpg\" /></div>"+
                "<div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "<div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "<div class=\"dd_list_fx\">"+
                "<div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_4.jpg\" /></div>"+
                "<div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "<div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "<div class=\"dd_list_fx\">"+
                "<div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_4.jpg\" /></div>"+
                "<div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "<div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "<div class=\"dd_list_fx\">"+
                "<div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_4.jpg\" /></div>"+
                "<div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "<div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "<div class=\"grzx_2\">"+
                "<div class=\"grzx_bottom_1\">&nbsp;</div>"+
                "<div class=\"grzx_bottom_2\">&nbsp;</div>"+
                "<div style=\"width:180px; float:left;\">&nbsp;</div>"+
                "<div class=\"grzx_bottom_4\">"+
                "<div class=\"page_1\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_6.jpg\" /></a></div>"+
                "<div class=\"page_2\">"+
                "        <ul>"+
                "<li class=\"page_ts\"><a href=\"#\">1</a></li>"+
                "        <li><a href=\"#\">2</a></li>"+
                "        <li><a href=\"#\">…</a></li>"+
                "        </ul>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "<div class=\"page_3\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_7.jpg\" /></a></div>"+
                "<div class=\"page_4\">共11页，</div>"+
                "<div class=\"page_5\">到第<input value=\"10\" name=\"\" type=\"text\" class=\"grzx_btn1\" />页</div>"+
                "<div class=\"page_6\"><input name=\"\" type=\"button\" class=\"grzx_btn2\" /></div>"+
                "        </div>"+
                "<div class=\"clear\"></div>"+
                "        </div>";
        $divs = $('#_div').children('div');
        $divs.each(	function(){	//这里可以对每一个div进行操作
            $(this).css("display","none");
        });

        $("#_div5").css("display","block");
        $("#_div5").html(buf);
    } else if (treeNode.id==52739) {        //过客留影
        var buf = "    <div class=\"grzx_1\">"+
                "<div class=\"grzx_1_bt\">客人留影</div>"+
                "        <div class=\"grzx_1_sub\">&nbsp;</div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"grzx_2\">"+
                "        <div class=\"fjfx_1\"><span>*</span> 留影描述：</div>"+
                "<div class=\"fjfx_2\"><textarea name=\"\" cols=\"56\" rows=\"5\"></textarea></div>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"grzx_2\">"+
                "        <div class=\"fjfx_1\"><span>*</span> 留影图片：</div>"+
                "<div class=\"fjfx_2\"><input name=\"\" type=\"text\" class=\"fjfx_txt\" /></div>"+
                "        <div class=\"fjfx_3\"><input name=\"\" type=\"button\" value=\"选择图片\" /></div>"+
                "        <div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"grzx_2\">"+
                "        <div class=\"fjfx_1\"><input name=\"\" type=\"button\" class=\"njy_btn\" /></div>"+
                "        <div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"grzx_2\">"+
                "        <div class=\"ddlb_ly_1\">"+
                "        <div class=\"fjfx_pic\">缩图</div>"+
                "        <div class=\"fjfx_pic1\">客人留影描述</div>"+
                "        <div class=\"fjfx_pic2\">发布日期</div>"+
                "        <div class=\"fjfx_pic3\">操作</div>"+
                "        </div>"+
                "        <div class=\"dd_list_fx\">"+
                "        <div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_6.jpg\" /></div>"+
                "        <div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "        <div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "        <div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"dd_list_fx\">"+
                "        <div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_6.jpg\" /></div>"+
                "        <div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "        <div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "        <div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"dd_list_fx\">"+
                "        <div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_6.jpg\" /></div>"+
                "        <div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "        <div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "        <div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"dd_list_fx\">"+
                "        <div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_6.jpg\" /></div>"+
                "        <div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "        <div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "        <div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"dd_list_fx\">"+
                "        <div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_6.jpg\" /></div>"+
                "        <div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "        <div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "        <div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"grzx_2\">"+
                "        <div class=\"grzx_bottom_1\">&nbsp;</div>"+
                "<div class=\"grzx_bottom_2\">&nbsp;</div>"+
                "<div style=\"width:180px; float:left;\">&nbsp;</div>"+
                "<div class=\"grzx_bottom_4\">"+
                "        <div class=\"page_1\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_6.jpg\" /></a></div>"+
                "<div class=\"page_2\">"+
                "        <ul>"+
                "        <li class=\"page_ts\"><a href=\"#\">1</a></li>"+
                "        <li><a href=\"#\">2</a></li>"+
                "        <li><a href=\"#\">…</a></li>"+
                "        </ul>"+
                "<div class=\"clear\"></div>"+
                "        </div>"+
                "        <div class=\"page_3\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_7.jpg\" /></a></div>"+
                "<div class=\"page_4\">共11页，</div>"+
                "<div class=\"page_5\">到第<input value=\"10\" name=\"\" type=\"text\" class=\"grzx_btn1\" />页</div>"+
                "        <div class=\"page_6\"><input name=\"\" type=\"button\" class=\"grzx_btn2\" /></div>"+
                "        </div>"+
                "        <div class=\"clear\"></div>"+
                "</div>";

        $divs = $('#_div').children('div');
        $divs.each(	function(){	//这里可以对每一个div进行操作
            $(this).css("display","none");
        });

        $("#_div10").css("display","block");
        $("#_div10").html(buf);
    } else if (treeNode.id==52757) {         //特色美食
        var buf = "    <div class=\"grzx_1\">"+
                "        <div class=\"grzx_1_bt\">特色美食</div>"+
                "<div class=\"grzx_1_sub\">&nbsp;</div>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"grzx_2\">"+
                "        <div class=\"fjfx_1\"><span>*</span> 美食描述：</div>"+
                "<div class=\"fjfx_2\"><textarea name=\"\" cols=\"56\" rows=\"5\"></textarea></div>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"grzx_2\">"+
                "        <div class=\"fjfx_1\"><span>*</span> 美食图片：</div>"+
                "<div class=\"fjfx_2\"><input name=\"\" type=\"text\" class=\"fjfx_txt\" /></div>"+
                "<div class=\"fjfx_3\"><input name=\"\" type=\"button\" value=\"选择图片\" /></div>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"grzx_2\">"+
                "        <div class=\"fjfx_1\"><input name=\"\" type=\"button\" class=\"njy_btn\" /></div>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"grzx_2\">"+
                "        <div class=\"ddlb_ly_1\">"+
                "        <div class=\"fjfx_pic\">缩图</div>"+
                "<div class=\"fjfx_pic1\">特色美食描述</div>"+
                "<div class=\"fjfx_pic2\">发布日期</div>"+
                "<div class=\"fjfx_pic3\">操作</div>"+
                "</div>"+
                "<div class=\"dd_list_fx\">"+
                "        <div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_5.jpg\" /></div>"+
                "<div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "<div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"dd_list_fx\">"+
                "        <div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_5.jpg\" /></div>"+
                "<div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "<div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"dd_list_fx\">"+
                "        <div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_5.jpg\" /></div>"+
                "<div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "<div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"dd_list_fx\">"+
                "        <div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_5.jpg\" /></div>"+
                "<div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "<div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"dd_list_fx\">"+
                "        <div class=\"fjfx_pic_1\"><img src=\"http://www.gugulx.com/images/20160229_5.jpg\" /></div>"+
                "<div class=\"fjfx_pic_11\">经营面积：经营面积（平方米）：600<br />床位数量：床位：20张</div>"+
                "<div class=\"fjfx_pic_12\">2016-02-18</div>"+
                "<div class=\"fjfx_pic_13\"><input name=\"\" type=\"button\" value=\"删除\" /></div>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"grzx_2\">"+
                "        <div class=\"grzx_bottom_1\">&nbsp;</div>"+
                "<div class=\"grzx_bottom_2\">&nbsp;</div>"+
                "<div style=\"width:180px; float:left;\">&nbsp;</div>"+
                "<div class=\"grzx_bottom_4\">"+
                "        <div class=\"page_1\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_6.jpg\" /></a></div>"+
                "<div class=\"page_2\">"+
                "        <ul>"+
                "        <li class=\"page_ts\"><a href=\"#\">1</a></li>"+
                "        <li><a href=\"#\">2</a></li>"+
                "        <li><a href=\"#\">…</a></li>"+
                "</ul>"+
                "<div class=\"clear\"></div>"+
                "</div>"+
                "<div class=\"page_3\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_7.jpg\" /></a></div>"+
                "<div class=\"page_4\">共11页，</div>"+
                "<div class=\"page_5\">到第<input value=\"10\" name=\"\" type=\"text\" class=\"grzx_btn1\" />页</div>"+
                "<div class=\"page_6\"><input name=\"\" type=\"button\" class=\"grzx_btn2\" /></div>"+
                "</div>"+
                "<div class=\"clear\"></div>"+
                "</div>";
        $divs = $('#_div').children('div');
        $divs.each(	function(){	//这里可以对每一个div进行操作
            $(this).css("display","none");
        });

        $("#_div6").css("display","block");
        $("#_div6").html(buf);
    } else if (treeNode.id==52758) {         //特色活动
        var buf ="    <div class=\"grzx_1\">\r\n"+
                "<div class=\"grzx_1_bt\">特色活动</div>\r\n"+
                "<div class=\"grzx_1_sub\">&nbsp;</div>\r\n"+
                "<div class=\"clear\"></div>\r\n"+
                "</div>\r\n"+
                "<div class=\"grzx_2\">\r\n"+
                "<form method=\"post\" action=\"\" name=\"tshdform\">\r\n" +
                "        <div class=\"njyjs_1\"><textarea name=\"tshd\" id=\"tshdcontent\" cols=\"100\" rows=\"30\"></textarea></div>\r\n"+
                "<div class=\"njyjs_1\"><span>对于农家院的描述不得少于200个字符</span></div>\r\n"+
                "<div class=\"njyjs_1\"><input name=\"tshd\" type=\"button\" onclick=\"javascript:tijiao('tshd');\" class=\"njy_btn\"/></div>\r\n"+
                "</div>\r\n" +
                "</form>\r\n" +
                "<script type=\"text/javascript\">\r\n" +
                "CKEDITOR.replace('tshdcontent', {\r\n" +
                "    fullPage: true,\r\n" +
                "    allowedContent: true,\r\n" +
                "    filebrowserBrowseUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52758',\r\n" +
                "    filebrowserImageBrowseUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52758',\r\n" +
                "    filebrowserUploadUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52758',\r\n" +
                "    filebrowserImageUploadUrl:'http://www.gugulx.com/_upload/uploadimage.jsp?Type=Images&columnid=52758'\r\n" +
                "});\r\n" +
                "<\/script>\r\n";

        $divs = $('#_div').children('div');
        $divs.each(	function(){	//这里可以对每一个div进行操作
            $(this).css("display","none");
        });

        $("#_div7").css("display","block");
        $("#_div7").html(buf);
    }
}

function orderList() {
    var buf="    <div class=\"grzx_1\">"+
            "<div class=\"grzx_1_bt\">全部订单</div>"+
            "<div class=\"grzx_1_sub\">&nbsp;</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"fjfx\">房间房型</div>"+
            "<div class=\"fjfx_btn\">"+
            "        <select style=\"width:200px;\">"+
            "        <option value=\"单人间\">单人间</option>"+
            "<option value=\"双人间\">双人间</option>"+
            "<option value=\"多人间\">多人间</option>"+
            "</select>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"fjfx\">订单状态</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 全部</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 待处理</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 待付款</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 已经取消</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 已成交</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"fjfx\">下单时间</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 全部</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 1个月</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 2个月</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 3个月</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"fjfx\">入住时间</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 全部</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 今日入住</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 明日入住</div>"+
            "<div class=\"ddzt_1\"><input name=\"\" type=\"radio\" value=\"\" /> 7天入住</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"khmc\">客户名称：<input name=\"\" type=\"text\" class=\"khmc_btn\" value=\"请输入客户名称\" /></div>"+
            "<div class=\"khmc\">客户手机：<input name=\"\" type=\"text\" class=\"khmc_btn\" value=\"请输入客户手机号码\" /></div>"+
            "<div class=\"khcx\"><input name=\"\" type=\"button\" class=\"khcx_btn\" /><input name=\"\" type=\"button\" class=\"khcx_btn1\" /></div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"ddlb_ly\">"+
            "        <div class=\"ddlb_1\">下单时间</div>"+
            "<div class=\"ddlb_1\">房间房型</div>"+
            "<div class=\"ddlb_1\">订单金额</div>"+
            "<div class=\"ddlb_1\">客户名称</div>"+
            "<div class=\"ddlb_1\">入住人数</div>"+
            "<div class=\"ddlb_1\" style=\" border:none;\">订单状态</div>"+
            "</div>"+
            "<div class=\"dd_list\">"+
            "        <div class=\"ddlb_2\">2016-02-29</div>"+
            "<div class=\"ddlb_2\">多人间</div>"+
            "<div class=\"ddlb_2\">￥128</div>"+
            "<div class=\"ddlb_2\">张三</div>"+
            "<div class=\"ddlb_2\">5</div>"+
            "<div class=\"ddlb_2\" style=\" border:none;\">已提交</div>"+
            "</div>"+
            "<div class=\"dd_list\">"+
            "        <div class=\"ddlb_2\">2016-02-29</div>"+
            "<div class=\"ddlb_2\">多人间</div>"+
            "<div class=\"ddlb_2\">￥128</div>"+
            "<div class=\"ddlb_2\">张三</div>"+
            "<div class=\"ddlb_2\">5</div>"+
            "<div class=\"ddlb_2\" style=\" border:none;\">已提交</div>"+
            "</div>"+
            "<div class=\"dd_list\">"+
            "        <div class=\"ddlb_2\">2016-02-29</div>"+
            "<div class=\"ddlb_2\">多人间</div>"+
            "<div class=\"ddlb_2\">￥128</div>"+
            "<div class=\"ddlb_2\">张三</div>"+
            "<div class=\"ddlb_2\">5</div>"+
            "<div class=\"ddlb_2\" style=\" border:none;\">已提交</div>"+
            "</div>"+
            "<div class=\"dd_list\">"+
            "        <div class=\"ddlb_2\">2016-02-29</div>"+
            "<div class=\"ddlb_2\">多人间</div>"+
            "<div class=\"ddlb_2\">￥128</div>"+
            "<div class=\"ddlb_2\">张三</div>"+
            "<div class=\"ddlb_2\">5</div>"+
            "<div class=\"ddlb_2\" style=\" border:none;\">已提交</div>"+
            "</div>"+
            "<div class=\"dd_list\">"+
            "        <div class=\"ddlb_2\">2016-02-29</div>"+
            "<div class=\"ddlb_2\">多人间</div>"+
            "<div class=\"ddlb_2\">￥128</div>"+
            "<div class=\"ddlb_2\">张三</div>"+
            "<div class=\"ddlb_2\">5</div>"+
            "<div class=\"ddlb_2\" style=\" border:none;\">已提交</div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"grzx_bottom_1\">&nbsp;</div>"+
            "<div class=\"grzx_bottom_2\">&nbsp;</div>"+
            "<div class=\"grzx_bottom_3\">&nbsp;</div>"+
            "<div class=\"grzx_bottom_4\">"+
            "        <div class=\"page_1\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_6.jpg\" /></a></div>"+
            "<div class=\"page_2\">"+
            "        <ul>"+
            "        <li class=\"page_ts\"><a href=\"#\">1</a></li>"+
            "        <li><a href=\"#\">2</a></li>"+
            "        <li><a href=\"#\">…</a></li>"+
            "</ul>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"page_3\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_7.jpg\" /></a></div>"+
            "<div class=\"page_4\">共11页，</div>"+
            "<div class=\"page_5\">到第<input value=\"10\" name=\"\" type=\"text\" class=\"grzx_btn1\" />页</div>"+
            "<div class=\"page_6\"><input name=\"\" type=\"button\" class=\"grzx_btn2\" /></div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>";

    $divs = $('#_div').children('div');
    $divs.each(	function(){	//这里可以对每一个div进行操作
        $(this).css("display","none");
    });

    $("#_div1").css("display","block");
    $("#_div1").html(buf);
}

function leaveWords() {
    var buf="    <div class=\"grzx_1\">"+
            "<div class=\"grzx_1_bt\">全部留言</div>"+
            "<div class=\"grzx_1_sub\">&nbsp;</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_222\">"+
            "        <div class=\"grzx_lylb\">"+
            "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
            "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
            "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">司马台古北水镇龙祥人家农家院周边有什么采摘园吗？是否有停车位？我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_lylb\">"+
            "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
            "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
            "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">是否有停车位？我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_lylb\">"+
            "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
            "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
            "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_lylb\">"+
            "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
            "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
            "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_lylb\">"+
            "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
            "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
            "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">我想去那边玩儿两天大约开销是多少钱，方便的话能给我一个详细介绍吗？</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"grzx_bottom_1\"><input name=\"\" type=\"checkbox\" value=\"\" /></div>"+
            "<div class=\"grzx_bottom_2\">全选</div>"+
            "<div class=\"grzx_bottom_3\"><input name=\"\" class=\"plsc_btn\" type=\"button\" /></div>"+
            "<div class=\"grzx_bottom_4\">"+
            "        <div class=\"page_1\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_6.jpg\" /></a></div>"+
            "<div class=\"page_2\">"+
            "        <ul>"+
            "        <li class=\"page_ts\"><a href=\"#\">1</a></li>"+
            "        <li><a href=\"#\">2</a></li>"+
            "        <li><a href=\"#\">…</a></li>"+
            "</ul>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"page_3\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_7.jpg\" /></a></div>"+
            "<div class=\"page_4\">共11页，</div>"+
            "<div class=\"page_5\">到第<input value=\"10\" name=\"\" type=\"text\" class=\"grzx_btn1\" />页</div>"+
            "<div class=\"page_6\"><input name=\"\" type=\"button\" class=\"grzx_btn2\" /></div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>";
    $divs = $('#_div').children('div');
    $divs.each(	function(){	//这里可以对每一个div进行操作
        $(this).css("display","none");
    });

    $("#_div2").css("display","block");
    $("#_div2").html(buf);
}

function commentList() {
    var buf="    <div class=\"grzx_1\">"+
            "<div class=\"grzx_1_bt\">全部点评</div>"+
            "<div class=\"grzx_1_sub\">&nbsp;</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_222\">"+
            "        <div class=\"grzx_lylb\">"+
            "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
            "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
            "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">古北水镇真的很好玩，有着千年历史的古北水镇，古朴、典雅、风景如画。古北水镇还有许多家农家院，也非常不错，有机会大家可以去游玩一次。</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_lylb\">"+
            "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
            "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
            "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">古北水镇真的很好玩，有着千年历史的古北水镇，古朴、典雅、风景如画。古北水镇还有许多家农家院，也非常不错，有机会大家可以去游玩一次。</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_lylb\">"+
            "        <div class=\"grzx_lylb_0\"><input name=\"\" type=\"radio\" value=\"\" /></div>"+
            "<div class=\"grzx_lylb_1\"><img src=\"http://www.gugulx.com/images/20150527_9.png\" /><br />34342323</div>"+
            "<div class=\"grzx_lylb_2\">"+
            "        <div class=\"grzx_lylb_nav\">古北水镇真的很好玩，有着千年历史的古北水镇，古朴、典雅、风景如画。古北水镇还有许多家农家院，也非常不错，有机会大家可以去游玩一次。</div>"+
            "<div class=\"grzx_lylb_date\">发布日期：2016-01-20 上午 11:04:12</div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"grzx_bottom_1\"><input name=\"\" type=\"checkbox\" value=\"\" /></div>"+
            "<div class=\"grzx_bottom_2\">全选</div>"+
            "<div class=\"grzx_bottom_3\"><input name=\"\" class=\"plsc_btn\" type=\"button\" /></div>"+
            "<div class=\"grzx_bottom_4\">"+
            "        <div class=\"page_1\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_6.jpg\" /></a></div>"+
            "<div class=\"page_2\">"+
            "        <ul>"+
            "        <li class=\"page_ts\"><a href=\"#\">1</a></li>"+
            "        <li><a href=\"#\">2</a></li>"+
            "        <li><a href=\"#\">…</a></li>"+
            "</ul>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"page_3\"><a href=\"#\"><img src=\"http://www.gugulx.com/images/20160120_7.jpg\" /></a></div>"+
            "<div class=\"page_4\">共11页，</div>"+
            "<div class=\"page_5\">到第<input value=\"10\" name=\"\" type=\"text\" class=\"grzx_btn1\" />页</div>"+
            "<div class=\"page_6\"><input name=\"\" type=\"button\" class=\"grzx_btn2\" /></div>"+
            "</div>"+
            "<div class=\"clear\"></div>"+
            "</div>";

    $divs = $('#_div').children('div');
    $divs.each(	function(){	//这里可以对每一个div进行操作
        $(this).css("display","none");
    });

    $("#_div3").css("display","block");
    $("#_div3").html(buf);
}

function personinfo() {
    var buf="    <div class=\"grzx_1\">"+
            "<div class=\"grzx_1_bt\">房东资料</div>"+
            "<div class=\"grzx_1_sub\">&nbsp;</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"grzx_1_0\">房东头像：</div>"+
            "<div class=\"grzx_1_1\" style=\"padding-top:30px;\">头像：</div>"+
            "<div class=\"grzx_1_2\"><img src=\"http://www.gugulx.com/images/20150527_8.png\" /></div>"+
            "<div class=\"grzx_1_3\" style=\"padding-top:30px;\"><a href=\"#\">修改头像</a></div>"+
            "<div class=\"grzx_1_4\" style=\"padding-top:30px;\">(上传真实头像，增加信任感)</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"grzx_1_0\">联系方式：</div>"+
            "<div class=\"grzx_1_1\"><span>*</span>手机号：</div>"+
            "<div class=\"grzx_1_2\"><input name=\"\" type=\"text\" class=\"grzx_btn3\" value=\"<%=(user.getMPHONE()==null)?"":user.getMPHONE()%>\" /></div>"+
            "<div class=\"grzx_1_3\"><a href=\"#\">修改</a></div>"+
            "<div class=\"grzx_1_4\">&nbsp;</div>"+
            "<div class=\"clear\"></div>"+
            "<div class=\"grzx_1_1\"><span>*</span>邮箱：</div>"+
            "<div class=\"grzx_1_2\"><input name=\"\" type=\"text\" class=\"grzx_btn3\" value=\"<%=(user.getEMAIL()==null)?"":user.getEMAIL()%>\" /></div>"+
            "<div class=\"grzx_1_3\"><a href=\"#\">修改</a></div>"+
            "<div class=\"grzx_1_4\">&nbsp;</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"grzx_1_0\">房东信息：</div>"+
            "<div class=\"grzx_1_1\">昵称：</div>"+
            "<div class=\"grzx_1_2\"><input name=\"\" type=\"text\" class=\"grzx_btn3\" value=\"gaoshanliushui\" /></div>"+
            "<div class=\"grzx_1_3\"><a href=\"#\">修改</a></div>"+
            "<div class=\"grzx_1_4\">（个性昵称）</div>"+
            "<div class=\"clear\"></div>"+
            "<div class=\"grzx_1_1\">会员名：</div>"+
            "<div class=\"grzx_1_2\"><input name=\"\" type=\"text\" class=\"grzx_btn3\" value=\"gaoshanliushui\" /></div>"+
            "<div class=\"grzx_1_3\"><a href=\"#\">修改</a></div>"+
            "<div class=\"grzx_1_4\">（会员名可做登录名）</div>"+
            "<div class=\"grzx_1_1\"><span>*</span>真实姓名：</div>"+
            "<div class=\"grzx_1_2\"><input name=\"\" type=\"text\" class=\"grzx_btn3\" value=\"张三\" /></div>"+
            "<div class=\"grzx_1_3\"><a href=\"#\">修改</a></div>"+
            "<div class=\"grzx_1_4\">（自己的真实姓名）</div>"+
            "<div class=\"clear\"></div>"+
            "<div class=\"grzx_1_1\"><span>*</span>证件号码：</div>"+
            "<div class=\"grzx_1_2\"><input name=\"\" type=\"text\" class=\"grzx_btn3\" value=\"135406198211301628\" /></div>"+
            "<div class=\"grzx_1_3\"><a href=\"#\">修改</a></div>"+
            "<div class=\"grzx_1_4\">（证件号码，下单更加快捷方便）</div>"+
            "<div class=\"grzx_1_1\"><span>*</span>性别：</div>"+
            "<div class=\"grzx_1_2\"><input name=\"\" type=\"text\" class=\"grzx_btn3\" value=\"女\" /></div>"+
            "<div class=\"grzx_1_3\"><a href=\"#\">修改</a></div>"+
            "<div class=\"grzx_1_4\">（修改自己的性别）</div>"+
            "<div class=\"clear\"></div>"+
            "<div class=\"grzx_1_1\"><span>*</span>地址：</div>"+
            "<div class=\"grzx_1_2\"><input name=\"\" type=\"text\" class=\"grzx_btn3\" value=\"马上填写\" /></div>"+
            "<div class=\"grzx_1_3\"><a href=\"#\">修改</a></div>"+
            "<div class=\"grzx_1_4\">（自己的地址，有机会获得更多惊喜）</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\">"+
            "        <div class=\"grzx_1_0\">修改密码：</div>"+
            "<div class=\"grzx_1_1\"><span>*</span>密码：</div>"+
            "<div class=\"grzx_1_2\"><input name=\"\" type=\"text\" class=\"grzx_btn3\" value=\"************\" /></div>"+
            "<div class=\"grzx_1_3\"><a href=\"#\">修改</a></div>"+
            "<div class=\"grzx_1_4\">&nbsp;</div>"+
            "<div class=\"clear\"></div>"+
            "</div>"+
            "<div class=\"grzx_2\"><input class=\"grzx_btn\" name=\"\" type=\"button\" /><input class=\"grzx_btn111\" name=\"\" type=\"button\" /></div>";

    $divs = $('#_div').children('div');
    $divs.each(	function(){	//这里可以对每一个div进行操作
        $(this).css("display","none");
    });

    $("#_div0").css("display","block");
    $("#_div0").html(buf);
}
</script>
</head>

<body>
<div class="top_fdzx">
    <div class="top_con">
        <div class="logo_fdzx"><img src="http://www.gugulx.com/images/logo.png" /></div>
        <div class="menu_fdzx"><a href="http://www.gugulx.com"><span>首页</span></a><a href="http://www.gugulx.com/list.shtml?type=njy">农家乐</a><a href="http://www.gugulx.com/list.shtml?type=mlxc">美丽乡村</a><a href="http://www.gugulx.com/list.shtml?type=czy">采摘园</a><a href="http://www.gugulx.com/list.shtml?type=jq">景点景区</a></div>
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
                    <li><a href="#" onclick="javascript:leaveWords();">查看留言</a></li>
                    <li><a href="#" onclick="javascript:commentList();">查看点评</a></li>
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
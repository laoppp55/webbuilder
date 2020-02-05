<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="java.math.BigDecimal" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-6-6
  Time: 上午10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    ApplicationContext appContext = SpringInit.getApplicationContext();
    Organization organization = null;
    if (appContext!=null) {
        OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
        organization = organizationService.getRootOrganization(BigDecimal.valueOf(siteid));
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>网站内容管理系统</title>
    <link rel="stylesheet" href="../design/css/layui.css">
    <link rel="stylesheet" href="../css/mycss.css">
    <link rel="stylesheet" href="../css/cms_css.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <!--引用xtree-->
    <script type="text/javascript" src="../design/layui-xtree.js"></script>
    <script>
        $(document).ready(function(){
            layui.use('element', function(){
                var element = layui.element;
            });
        });

        function userInfoList(orgid) {
            htmlobj=$.ajax({
                url:"usersManager.jsp",
                data:{
                    id:orgid
                },
                dataType:'text',
                async:false,
                success:function(data){
                    var e_posi = data.indexOf("]");
                    data = data.substring(0,e_posi+1);
                    var jsondata = $.parseJSON(data);                    //字符串转换为json对象方法一
                    //var jsondata = eval('(' + data + ')');              //字符串转换为json对象方法二
                    var htmldata = "<table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n";
                    htmldata = htmldata + "    <tr>\n" +
                        "        <td>\n" +
                        "            <div class=\"location\">\n" +
                        "                <div class=\"location_1\">系统管理 > 用户管理</div>\n" +
                        "                <div class=\"button_top\">\n" +
                        "                    <ul>\n" +
                        "                        <li><input name=\"\" type=\"button\" value=\"新建\" class=\"button_bg_1\"/></li>\n" +
                        "                        <li><input name=\"\" type=\"button\" value=\"批量导入\" class=\"button_bg_2\"/></li>\n" +
                        "                        <li><input name=\"\" type=\"button\" value=\"批量删除\" class=\"button_bg_3\"/></li>\n" +
                        "                    </ul>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </td>\n" +
                        "    </tr>\n";
                    htmldata = htmldata + "    <tr>\n" +
                        "        <td align=\"left\" valign=\"top\">\n" +
                        "            <table width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#CCCCCC\" class=\"tab_list\">\n" +
                        "                <tr>\n" +
                        "                    <td width=\"50\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><input type=\"checkbox\" name=\"selall\" id=\"selallid\" />选择</td>\n" +
                        "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">序号</td>\n" +
                        "                    <td width=\"73\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">用户名</td>\n" +
                        "                    <td width=\"87\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">真实姓名</td>\n" +
                        "                    <td width=\"150\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">电子邮件</td>\n" +
                        "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">手机号码</td>\n" +
                        "                    <td width=\"50\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">性别</td>\n" +
                        "                    <td width=\"200\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">公司</td>\n" +
                        "                    <td width=\"190\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">部门</td>\n" +
                        "                    <td width=\"120\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">创建时间</td>\n" +
                        "                    <td width=\"40\" align=\"center\" bgcolor=\"#f4f4f4\">删除</td>\n" +
                        "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">修改</td>\n" +
                        "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">禁用</td>\n" +
                        "                </tr>\n";
                    for(var ii=0;ii<jsondata.length;ii++) {
                        var nickname = (typeof(jsondata[ii].NICKNAME)!="undefined")?jsondata[ii].NICKNAME:"";
                        var email = (typeof(jsondata[ii].EMAIL)!="undefined")?jsondata[ii].EMAIL:"";
                        var mphone = (typeof(jsondata[ii].MPHONE)!="undefined")?jsondata[ii].MPHONE:"";
                        var sex = (jsondata[ii].SEX=="1")?"女":"男";
                        var company = (typeof(jsondata[ii].COMPANYID)!="undefined")?jsondata[ii].COMPANYID:"";
                        var dept = (typeof(jsondata[ii].DEPARTMENT)!="undefined")?jsondata[ii].DEPARTMENT:"";
                        var createdate = (typeof(jsondata[ii].CREATEDATE)!="undefined")?jsondata[ii].CREATEDATE:"";

                        htmldata = htmldata + "                <tr bgcolor=\"#FFFFFF\" id=\"row\"" + jsondata[ii].ID + ">\n";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><input type=\"checkbox\" name=\"selitem\" id=\"selitem" + jsondata[ii].ID + "\" /></td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + jsondata[ii].ID + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + jsondata[ii].USERID + "</td>";
                        htmldata = htmldata + "<td>" + nickname + "</td>";
                        htmldata = htmldata + "<td>" + email + "</td>";
                        htmldata = htmldata + "<td align=\"center\">" + mphone + "</td>";
                        htmldata = htmldata + "<td align=\"center\" >" + sex + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\" >" + company + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + dept + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + createdate + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"#\"><img src=\"../images/del.png\" width=\"23\" height=\"23\" /></a></td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:updateuser(" + jsondata[ii].ID + ",'" + jsondata[ii].USERID + "','" + nickname + "');\"><img src=\"../images/edit.png\" width=\"24\" height=\"23\" /></a></td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"#\"><img src=\"../images/public.png\" width=\"23\" height=\"23\" /></a></td>";
                        htmldata = htmldata + "</tr>";
                        //alert(jsondata[ii].USERID + jsondata.NICKNAME + jsondata.EMAIL + jsondata.MPHONE + jsondata.SEX + jsondata.COMPANYID + jsondata.DEPARTMENT);
                    }
                    htmldata = htmldata + "            </table>\n";

                    htmldata = htmldata + "            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                        "                <tr>\n" +
                        "                    <td width=\"70%\">用户总数：" + jsondata.length + "&nbsp;&nbsp;&nbsp;&nbsp;总1页&nbsp;&nbsp;&nbsp;第一页 到第<input type=\"text\" name=\"textfield\" id=\"textfield\"  class=\"txt_1\"/> <input type=\"submit\" name=\"button2\" id=\"button2\" value=\"GO\" class=\"btn_go\" /></td>\n" +
                        "\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                        "                <tr>\n" +
                        "                    <td width=\"23%\" height=\"50\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标题：<input type=\"text\" name=\"textfield2\" id=\"textfield2\" class=\"sear_txt_1\"/>&nbsp;<input type=\"submit\" name=\"button3\" id=\"button3\" value=\"搜索\" class=\"sear_btn\"/> <a href=\"#\" class=\"sear_gj\">高级搜索</a></td>\n" +
                        "                </tr>\n" +
                        "            </table>\n";

                    htmldata = htmldata + "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";

                    $("#contentid").html(htmldata);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    alert(jqXHR.status);
                    alert(jqXHR.readyState);
                    alert(jqXHR.statusText);
                    alert(textStatus);
                    alert(errorThrown);
                }
            });
        }

        function usersManager() {
            $("#sidetreeid").css('display','block');
            htmlobj=$.ajax({
                url:"/webbuilder/organization/organizationsForTree.jsp",
                type:'post',
                data:{
                },
                dataType:'text',
                async:false,
                success:function(data){
                    var posi = data.indexOf("[");
                    data = data.substring(posi);
                    layui.use('tree', function(){
                        var tree = layui.tree;
                        //渲染
                        var inst1 = tree.render({
                            elem: '#mytree',  //绑定元素
                            skin: 'sidebar',
                            showLine:true,
                            onlyIconControl:true,
                            target: '_blank', //是否新选项卡打开（比如节点返回href才有效）
                            click: function(item){ //点击节点回调
                                userInfoList(item.data.id);
                                //layer.msg('当前节名称：'+ item.data.title + "==" + item.data.id + '<br>全部参数：'+ JSON.stringify(item));
                                //console.log(item);
                            },
                            data: eval(data)
                        });
                    });
                    $("#treebox").css("height",document.documentElement.clientHeight-60);
                },
                error:function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
            });
        }

        function updateuser(uid,userid,nickname) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("editUsernew.jsp?uid=" + uid + "&userid=" + userid + "&username=" + nickname, "EditUserWin", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + "scrollbars");
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">CMS-系统角色管理</div>
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="../column/indexnew.jsp">栏目管理</a></li>
            <li class="layui-nav-item"><a href="../template/indexnew.jsp">模板管理</a></li>
            <li class="layui-nav-item"><a href="../article/indexnew.jsp">文章管理</a></li>
            <li class="layui-nav-item"><a href="../articlemove/indexnew.jsp">文章迁移</a></li>
            <li class="layui-nav-item"><a href="../audit/indexnew.jsp">文章审核</a></li>
            <li class="layui-nav-item"><a href="../publish/indexnew.jsp">文章发布</a></li>
            <!--li class="layui-nav-item"><a href="javascript:;">系统设置</a></li-->
            <li class="layui-nav-item">
                <a href="javascript:;">系统设置</a>
                <dl class="layui-nav-child">
                    <dd><a href="../member/indexnew.jsp">用户管理</a></dd>
                    <dd><a href="../organization/indexnew.jsp">组织架构管理</a></dd>
                    <dd><a href="../roles/index.jsp">角色管理</a></dd>
                    <dd><a href="../rights/index.jsp">权限管理</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="javascript:;">工具箱</a></li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    贤心
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">退了</a></li>
        </ul>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div id="contentid">
            文章列表区域
        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © layui.com - 底部固定区域
    </div>
</div>
<script>
    /*layui.use(['element', 'tree', 'layer'], function(){
        var layer = layui.layer,$ = layui.jquery;
        //添加选中颜色显示
        $("body").on("mousedown", ".layui-tree a", function() {
            if(!$(this).siblings('ul').length) {
                $(".layui-tree a").removeClass('active');
                $(this).find('cite').parent().addClass('active');
            }
        });

        var tree = layui.tree;
        var inst1 = tree.render({
            elem: '#mytree', //指定元素
            showLine:true,
            onlyIconControl:true,
            //skin: 'sidebar',
            skin: 'dx',
            target: '_blank', //是否新选项卡打开（比如节点返回href才有效）
            click: function(item){ //点击节点回调
                htmlobj=$.ajax({
                    url:"articlesnew.jsp",
                    dataType:'json',
                    async:false,
                    data:{
                        column:item.id,
                        start:0
                    },
                    success:function(data){

                    }
                });
                layer.msg('当前节名称：'+ item.data.title + "==" + item.data.id + '<br>全部参数：'+ JSON.stringify(item));
                console.log(item);
            },
            data:< %=buf.toString()%>
        });

        $("#treebox").css("height",document.documentElement.clientHeight-60);
    });*/
</script>
</body>
</html>

<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
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
%>
<!DOCTYPE html>
<html>
<head>
    <title>网站内容管理系统</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../design/css/layui.css">
    <link rel="stylesheet" href="../css/mycss.css">
    <link rel="stylesheet" href="../css/cms_css.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/icon.css">

    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>

    <!--引用xtree-->
    <script type="text/javascript" src="../design/layui-xtree.js"></script>
    <script>
        $(document).ready(function(){
            layui.use('element', function(){
                var element = layui.element;
            });
            programInfoList();
        });
        function goout() {
            window.location.href = "../exit.jsp";
        }

        function programInfoList() {
            htmlobj=$.ajax({
                url:"pmanager.jsp",
                type:"POST",
                data:{
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    var htmldata = "<table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n";
                    htmldata = htmldata + "    <tr>\n" +
                        "        <td align=\"left\" valign=\"top\">\n" +
                        "          <form name=form1>" +
                        "            <table id=\"articleDataGrid\" width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#CCCCCC\" class=\"tab_list\">\n" +
                        "                <tr>\n" +
                        "                    <td width=\"20%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">功能类型</td>\n" +
                        "                    <td width=\"20%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">语言类型</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">位置</td>\n" +
                        "                    <td width=\"30%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">说明</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">修改</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">删除</td>\n" +
                        "                </tr>\n";
                    for(var ii=0;ii<jsondata.DATA.length;ii++) {
                        var programid = jsondata.DATA[ii].id;
                        var p_type = jsondata.DATA[ii].p_type;
                        var l_type = jsondata.DATA[ii].l_type;
                        var position_type = jsondata.DATA[ii].position;
                        var explain = jsondata.DATA[ii].explain;
                        var notes = jsondata.DATA[ii].notes;

                        var program_name = "";
                        switch (p_type) {
                            case 11:
                                program_name = "信息检索";
                                break;
                            case 12:
                                program_name = "购物车";
                                break;
                            case 13:
                                program_name = "订单生成";
                                break;
                            case 14:
                                program_name = "订单回显";
                                break;
                            case 15:
                                program_name = "订单查询";
                                break;
                            case 16:
                                program_name = "信息反馈";
                                break;
                            case 17:
                                program_name = "用户评论";
                                break;
                            case 18:
                                program_name = "用户注册";
                                break;
                            case 19:
                                program_name = "用户登录";
                                break;
                            case 20:
                                program_name = "订单明细查询";
                                break;
                            case 21:
                                program_name = "用户留言";
                                break;
                            case 22:
                                program_name = "修改注册";
                                break;
                            case 24:
                                program_name = "地图标注";
                                break;
                        }


                        var language = "";
                        switch (l_type) {
                            case 1:
                                language = "Java";
                                break;
                            case 2:
                                language = "Javascript";
                                break;
                        }

                        var position = "";
                        switch (position_type) {
                            case 1:
                                position = "页头";
                                break;
                            case 3:
                                position = "页尾";
                                break;
                        }

                        htmldata = htmldata + "                <tr bgcolor=\"#FFFFFF\" id=\"" + programid + "\">\n";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + program_name + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + language + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + position + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + notes + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><input type=\"radio\" name=\"action\" onclick=\"javascript:editProgram(" + programid + ");\"></td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><input type=\"radio\" name=\"action\" onclick=\"javascript:removeProgram(" + programid + ");\"></td>";
                        htmldata = htmldata + "</tr>";
                    }

                    htmldata = htmldata + "            </table>\n";
                    htmldata = htmldata + "         </form>";
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

        function addProgram() {
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("paddprogram.jsp","增加程序模块","top="+ iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight + ",resizable=yes,scrollbars=yes");
        }

        function editProgram(progid) {
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("pedit.jsp?id=" + progid,"修改程序模块","top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight + ",resizable=yes,scrollbars=yes");
        }

        function removeProgram(progid) {
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("premove.jsp?id=" + progid,"删除程序模块","top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight + ",resizable=yes,scrollbars=yes");
        }

    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">WCMS--站点管理</div>
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="../managesites.jsp">用户管理</a></li>
            <li class="layui-nav-item"><a href="../member/siteManagenew.jsp">站点管理</a></li>
            <li class="layui-nav-item"><a href="../program/index.jsp">程序管理</a></li>
            <li class="layui-nav-item"><a href="../member/listStylenew.jsp">列表样式管理</a></li>
            <!--li class="layui-nav-item"><a href="">文章移动</a></li-->
            <li class="layui-nav-item"><a href="../member/logManagenew.jsp">工作量管理</a></li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="javascript:goout();">退了</a></li>
        </ul>
    </div>

    <div class="layui-body" style="left: 0px;top:65px;">
        <div class="button_top">
            <ul>
                <li><input name="addprogram" type="button" value="增加程序模块" class="button_bg_2" onclick='javascript:addProgram();'/></li>
            </ul>
        </div>

        <!-- 内容主体区域 -->
        <div id="contentid" style="left: 0px;padding-top:30px;"></div>
    </div>

    <div class="layui-footer" style="left: 0px;">
        版权信息： © 2003-2019 北京潮虹伟业科技有限公司，版权所有。
    </div>
</div>
</body>
</html>

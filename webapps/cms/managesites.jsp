<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="com.bizwink.service.UsersService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.po.Users" %>
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
    <link rel="stylesheet" href="design/css/layui.css">
    <link rel="stylesheet" href="css/mycss.css">
    <link rel="stylesheet" href="css/cms_css.css">
    <link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/themes/icon.css">

    <script type="text/javascript" src="js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="js/json2.js"></script>
    <script type="text/javascript" src="design/layui.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>

    <!--引用xtree-->
    <script type="text/javascript" src="design/layui-xtree.js"></script>
    <script>
        $(document).ready(function(){
            layui.use('element', function(){
                var element = layui.element;
            });
            userInfoList(15,1,-1);
        });
        function goout() {
            window.location.href = "exit.jsp";
        }

        function goSearchPage(range,currentpage,doSearchFlag) {
            var currentPage = form.turnPage.value;
            userInfoList(range,currentpage,doSearchFlag);
            form.turnPage.value = currentPage;
        }

        function Pagination(totalNum,totalpages,pageNumLength,range,currentpage,doSearchFlag) {
            var PaginationDiv = "";
            if (totalpages <= 1)
                PaginationDiv = "<div class=\"page\" style=\"display: none\">";
            else
                PaginationDiv = "<div class=\"page\" style=\"display: block\">";

            if (currentpage<=1)
                PaginationDiv = PaginationDiv + "<span class=\"txtl\">用户总数：" + totalNum + "</span><span>第1页</span>";
            else {
                PaginationDiv = PaginationDiv + "<a href=\"javascript:userInfoList(" + range + "," + currentpage + "," + doSearchFlag + ");\">第1页</a>";
            }

            PaginationDiv = PaginationDiv + "<span>共" + totalpages + "页</span>";

            if (currentpage<=1)
                PaginationDiv = PaginationDiv + "<span>上一页</span>";
            else {
                PaginationDiv = PaginationDiv + "<a href=\"javascript:userInfoList(" + range + "," + (currentpage - 1) + "," + doSearchFlag + ");\">上一页</a>";
            }

            var half_pageNumLength = parseInt(pageNumLength/2);
            var startPageNum=0;
            var endPageNum = 0;
            if (currentpage<=half_pageNumLength)
                startPageNum=0;
            else {
                startPageNum = parseInt(currentpage) - parseInt(half_pageNumLength);
                if (startPageNum<=0) startPageNum = 0;
            }

            if (currentpage<half_pageNumLength) {
                if (totalpages <= pageNumLength)
                    endPageNum = totalpages;
                else
                    endPageNum = pageNumLength;
            } else {
                if (totalpages <= pageNumLength)
                    endPageNum = totalpages;
                else {
                    endPageNum = parseInt(currentpage) + parseInt(half_pageNumLength);
                    if (endPageNum>totalpages) endPageNum = totalpages;
                }
            }

            for(var ii=startPageNum;ii<endPageNum;ii++) {
                if ((ii + 1) === currentpage)
                    PaginationDiv = PaginationDiv + "<span class=\"cur\">" + currentpage + "</span>";
                else {
                    PaginationDiv = PaginationDiv + "<a href=\"javascript:userInfoList(" + range + "," + (ii + 1) + "," + doSearchFlag + ");\">" + (ii + 1) +  "</a>";
                }
            }

            if (currentpage<totalpages)
                PaginationDiv = PaginationDiv + "<a href=\"javascript:userInfoList(" + range + "," + (currentpage+1) + "," + doSearchFlag + ");\">下一页</a>";
            else
                PaginationDiv = PaginationDiv + "<span>下一页</span>";

            PaginationDiv = PaginationDiv + "<span class=\"txtl\">转到第</span>";
            PaginationDiv = PaginationDiv + "<span class=\"select-pager\">";
            PaginationDiv = PaginationDiv + "<form name=\"form\">";

            PaginationDiv = PaginationDiv + "<select name=\"turnPage\" size=\"1\" onchange=\"javascript:goSearchPage(" + range + ",'" + currentpage + "," + doSearchFlag +  ");\">";

            for(var ii=0;ii<totalpages;ii++) {
                if ((ii+1) === currentpage)
                    PaginationDiv = PaginationDiv + "<option value=\"" + (ii+1) + "\" selected>" + (ii+1) +  "</option>";
                else
                    PaginationDiv = PaginationDiv + "<option value=\"" + (ii+1) + "\">" + (ii+1) + "</option>";
            }

            PaginationDiv = PaginationDiv + "</select>";
            PaginationDiv = PaginationDiv + "</form>";
            PaginationDiv = PaginationDiv + "</span>";
            PaginationDiv = PaginationDiv + "<span class=\"txtr\">页</span>";
            PaginationDiv = PaginationDiv + "</div>";

            return PaginationDiv;
        }

        function userInfoList(resultnum,currentpage,doSearchFlag) {
            var searchcontent = $("#searchcontentid").val();
            var searchflag = $("input[name='namesearch']:checked").val();
            if (doSearchFlag==-1)
                searchflag = -1;
            else {
                if (typeof(searchflag) == "undefined") alert("请选择查询方式");
                if (searchcontent==null) alert("请输入查询关键字");
            }

            var pageNumLength = 10;   //页码的长度为10
            htmlobj=$.ajax({
                url:"member/admin_index.jsp",
                type:"POST",
                data:{
                    resultnum:resultnum,
                    pageno:currentpage,
                    searchflag:searchflag,                              //对那个字段执行检索操作
                    doSearch:doSearchFlag,                              //是否执行检索操作
                    search:searchcontent
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    var doSearchFlag = jsondata.DOSEARCH;
                    var totalNum = jsondata.COUNT;
                    var extra = jsondata.COUNT % resultnum;
                    var totalPage = 0;
                    if (extra>0)
                        totalPage = parseInt(jsondata.COUNT/resultnum) + 1;
                    else
                        totalPage = parseInt(jsondata.COUNT/resultnum);

                    var htmldata = "<table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n";
                    htmldata = htmldata + "    <tr>\n" +
                        "        <td align=\"left\" valign=\"top\">\n" +
                        "          <form name=form1>" +
                        "            <table id=\"articleDataGrid\" width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#CCCCCC\" class=\"tab_list\">\n" +
                        "                <tr>\n" +
                        "                    <td width=\"20%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">用户名称</td>\n" +
                        "                    <td width=\"20%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">站点名称</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">联系人</td>\n" +
                        "                    <td width=\"20%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">电子邮件</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">电话</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">修改密码</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">删除</td>\n" +
                        "                </tr>\n";
                    for(var ii=0;ii<jsondata.DATA.length;ii++) {
                        var userid = jsondata.DATA[ii].USERID;
                        var sitename = jsondata.DATA[ii].SITENAME;
                        var contactor = jsondata.DATA[ii].NICKNAME;
                        var email = jsondata.DATA[ii].EMAIL;
                        var phone = jsondata.DATA[ii].PHONE;
                        if (typeof(contactor) == "undefined") contactor="&nbsp;";
                        if (typeof(email) == "undefined") email="&nbsp;";
                        if (typeof(phone) == "undefined") phone="&nbsp;";
                        var updatePassAction = "<input type=\"radio\" name=\"action\" onclick='updatePasswd(\"" + userid + "\")'>";
                        var deleteAction = "";

                        if (userid==="admin")
                            deleteAction = "<input type=\"radio\" name=\"action\" disabled>";
                        else
                            deleteAction = "<input type=\"radio\" name=\"action\" onclick='javascript:deleteUser(\"" + userid + "\"," + resultnum + "," + currentpage + "," + doSearchFlag + ")'>";

                        htmldata = htmldata + "                <tr bgcolor=\"#FFFFFF\" id=\"" + userid + "\">\n";
                        htmldata = htmldata + "<td align=\"center\">" + userid +  "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + sitename + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + contactor + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + email + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + phone + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + updatePassAction + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + deleteAction + "</td>";
                        htmldata = htmldata + "</tr>";
                    }

                    htmldata = htmldata + "            </table>\n";
                    htmldata = htmldata + "         </form>";
                    htmldata = htmldata + "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";

                    htmldata = htmldata + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
                    htmldata = htmldata + "    <tr>";
                    htmldata = htmldata + "    <td align='right'>" + Pagination(totalNum,totalPage,pageNumLength,resultnum,currentpage,doSearchFlag)+ "</td>";
                    htmldata = htmldata + "    </tr>";
                    htmldata = htmldata + "</table>";
                    $("#sousuoid").css("display","block");
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

        function updatePasswd(userid) {
            if(confirm("确定要修改用户" + userid + "的口令吗？")) {
                var iWidth=600;                                                 //弹出窗口的宽度;
                var iHeight=400;                                                //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                //iTop = 0;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                window.open("member/editPassword.jsp?type=1&userid="+userid, "UpdatePasswdWin", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
            }
        }

        function deleteUser(userid,range,currentpage,dosearch) {
            if(confirm("确定要删除用户" + userid + "吗？")) {
                var iWidth = window.screen.availWidth - 100;                                                 //弹出窗口的宽度;
                var iHeight = 800;                                                //弹出窗口的高度;
                //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                iTop = 0;
                var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;         //获得窗口的水平位置;
                window.open("member/removeUser.jsp?type=1&userid=" + userid + "&start=" + currentpage + "&range=" + range + "&dosearch=" + dosearch, "deleteUserWin", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
            }
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">WCMS--站点管理</div>
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="javascript:userInfoList(15,1,-1);">用户管理</a></li>
            <li class="layui-nav-item"><a href="member/siteManagenew.jsp">站点管理</a></li>
            <li class="layui-nav-item"><a href="program/index.jsp">程序管理</a></li>
            <li class="layui-nav-item"><a href="member/listStylenew.jsp">列表样式管理</a></li>
            <!--li class="layui-nav-item"><a href="">文章移动</a></li-->
            <li class="layui-nav-item"><a href="member/logManagenew.jsp">工作量管理</a></li>
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

    <div class="layui-side layui-bg-black" id="sidetreeid" style="top: 60px;display: none;">
        <div id="treebox" style="overflow-x:auto;">
            <ul id="mytree"></ul>
        </div>
    </div>

    <div class="layui-body" style="left: 0px;top:80px;">
        <!-- 内容主体区域 -->
        <div id="sousuoid" style="padding-top: 10px;padding-bottom: 10px;display: none;">
            <form name="searchForm">
                <input type="radio" name="namesearch" value="1">用户名：
                <input type="radio" name="namesearch" value="2">站点名称：
                <input type="radio" name="namesearch" value="3">联系人：
                <input type="radio" name="namesearch" value="4">电子邮件：
                <input type="text" name="search" id="searchcontentid" class="sear_txt_1" size="50">
                <input type="button" name="dosearch" id="searchid" value="搜索" onclick="javascript:userInfoList(15,1,0);" class="sear_btn"/>
            </form>
        </div>

        <div id="contentid">
        </div>
    </div>

    <div class="layui-footer" style="left: 0px;">
        版权信息： © 2003-2019 北京潮虹伟业科技有限公司，版权所有。
    </div>
</div>
</body>
</html>

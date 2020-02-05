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
            //programInfoList();
        });
        function goout() {
            window.location.href = "../exit.jsp";
        }

        function goSearchPage(range) {
            var currentPage = form.turnPage.value;
            articlesInfoList(range,currentPage);
            form.turnPage.value = currentPage;
        }

        function Pagination(totalArticleNum,totalpages,curentpage,range,pageNumLength) {
            var PaginationDiv = "";
            if (totalpages <= 1)
                PaginationDiv = "<div class=\"page\" style=\"display: none\">";
            else
                PaginationDiv = "<div class=\"page\" style=\"display: block\">";

            if (curentpage<=1)
                PaginationDiv = PaginationDiv + "<span class=\"txtl\">当前栏目的文章总数：" + totalArticleNum + "</span><span>第1页</span>";
            else {
                PaginationDiv = PaginationDiv + "<a href=\"javascript:logInfoList(" + range + ",1" + ");\">第1页</a>";
            }

            PaginationDiv = PaginationDiv + "<span>共" + totalpages + "页</span>";

            if (curentpage<=1)
                PaginationDiv = PaginationDiv + "<span>上一页</span>";
            else {
                PaginationDiv = PaginationDiv + "<a href=\"javascript:logInfoList(" + range + "," + (curentpage - 1) + ");\">上一页</a>";
            }

            var half_pageNumLength = parseInt(pageNumLength/2);
            var startPageNum=0;
            var endPageNum = 0;
            if (curentpage<=half_pageNumLength)
                startPageNum=0;
            else {
                startPageNum = parseInt(curentpage) - parseInt(half_pageNumLength);
                if (startPageNum<=0) startPageNum = 0;
            }

            if (curentpage<half_pageNumLength) {
                if (totalpages <= pageNumLength)
                    endPageNum = totalpages;
                else
                    endPageNum = pageNumLength;
            } else {
                if (totalpages <= pageNumLength)
                    endPageNum = totalpages;
                else {
                    endPageNum = parseInt(curentpage) + parseInt(half_pageNumLength);
                    if (endPageNum>totalpages) endPageNum = totalpages;
                }
            }

            for(var ii=startPageNum;ii<endPageNum;ii++) {
                if ((ii + 1) === curentpage)
                    PaginationDiv = PaginationDiv + "<span class=\"cur\">" + curentpage + "</span>";
                else {
                    PaginationDiv = PaginationDiv + "<a href=\"javascript:logInfoList(" + range + "," + (ii + 1) + ");\">" + (ii + 1) + "</a>";
                }
            }

            if (curentpage<totalpages)
                PaginationDiv = PaginationDiv + "<a href=\"javascript:logInfoList(" + range + "," + (curentpage+1) + ");\">下一页</a>";
            else
                PaginationDiv = PaginationDiv + "<span>下一页</span>";

            PaginationDiv = PaginationDiv + "<span class=\"txtl\">转到第</span>";
            PaginationDiv = PaginationDiv + "<span class=\"select-pager\">";
            PaginationDiv = PaginationDiv + "<form name=\"form\">";

            PaginationDiv = PaginationDiv + "<select name=\"turnPage\" size=\"1\" onchange=\"javascript:goSearchPage(" + range + ");\">";

            for(var ii=0;ii<totalpages;ii++) {
                if ((ii+1) === curentpage)
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

        function logInfoList(range,currentpage) {
            var siteid = 0;
            var sitename = searchForm.domainname.value;
            if (typeof(sitename) == "undefined" || sitename==null || sitename=="") {
                alert("请输入域名！！！");
                return;
            } else {
                siteid = getsiteid(sitename);
            }
            var title = searchForm.title.value;
            var spubdate = searchForm.spubdate.value;
            var epubdate = searchForm.epubdate.value;
            var operation = searchForm.op.value;                           //1增加文章 2修改文章 3删除文章
            var editor = searchForm.author.value;
            var columns = searchForm.column.value;
            var depts = searchForm.dept.value;

            if (siteid>0) {
                htmlobj = $.ajax({
                    url: "logInfo.jsp",
                    type: "POST",
                    data: {
                        range:range,
                        pageno:currentpage,
                        siteid: siteid,
                        spubdate:encodeURIComponent(spubdate),
                        epubdate:encodeURIComponent(epubdate),
                        op:operation,
                        editor:editor,
                        columns:columns,
                        depts:depts,
                        title:encodeURIComponent(title)
                    },
                    dataType: 'json',
                    async: false,
                    success: function (jsondata) {
                        var range = jsondata.RANGE;
                        var currentpage = jsondata.PAGENO;
                        var extra = jsondata.COUNT % range;
                        var totalPage = 0;
                        if (extra>0)
                            totalPage = parseInt(jsondata.COUNT/range) + 1;
                        else
                            totalPage = jsondata.COUNT/range;

                        var htmldata = "<table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n";
                        htmldata = htmldata + "    <tr>\n" +
                            "        <td align=\"left\" valign=\"top\">\n" +
                            "          <form name=form1>" +
                            "            <table id=\"articleDataGrid\" width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#CCCCCC\" class=\"tab_list\">\n" +
                            "                <tr>\n" +
                            "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">用户账号</td>\n" +
                            "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">用户名</td>\n" +
                            "                    <td width=\"30%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">内容描述</td>\n" +
                            "                    <td width=\"20%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">操作时间</td>\n" +
                            "                    <td width=\"20%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">操作类型</td>\n" +
                            "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">所属部门</td>\n" +
                            "                </tr>\n";
                        for(var ii=0;ii<jsondata.DATA.length;ii++) {
                            var logid = jsondata.DATA[ii].ID;
                            var userid = jsondata.DATA[ii].editor;
                            var nickname = jsondata.DATA[ii].editor;
                            var acttime = jsondata.DATA[ii].actTime;
                            var acttype = jsondata.DATA[ii].actType;
                            var department = jsondata.DATA[ii].deptid;
                            var maintitle = jsondata.DATA[ii].maintitle;

                            var actname = "";
                            switch (acttype) {
                                case 1:
                                    actname = "增加文章";
                                    break;
                                case 2:
                                    actname = "修改文章";
                                    break;
                                case 3:
                                    actname = "删除文章";
                                    break;
                                case 4:
                                    actname = "发布文章";
                                    break;
                                case 5:
                                    actname = "审核文章";
                                    break;
                                case 6:
                                    actname = "增加模板";
                                    break;
                                case 7:
                                    actname = "修改模板";
                                    break;
                                case 8:
                                    actname = "删除模板";
                                    break;
                                case 9:
                                    actname = "发布模板";
                                    break;
                                case 10:
                                    actname = "登录系统";
                                    break;
                                case 11:
                                    actname = "创建用户";
                                    break;
                                case 12:
                                    actname = "修改用户";
                                    break;
                                case 13:
                                    actname = "删除用户";
                                    break;
                                case 14:
                                    actname = "修改登录密码";
                                    break;
                                case 15:
                                    actname = "退出系统";
                                    break;
                            }

                            htmldata = htmldata + "                <tr bgcolor=\"#FFFFFF\" id=\"" + logid + "\">\n";
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + userid + "</td>";
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + nickname + "</td>";
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + maintitle + "</td>";
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + acttime + "</td>";
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + actname + "</td>";
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + "</td>";
                            htmldata = htmldata + "</tr>";
                        }

                        htmldata = htmldata + "            </table>\n";
                        htmldata = htmldata + "         </form>";
                        htmldata = htmldata + "        </td>\n" +
                            "    </tr>\n" +
                            "</table>";

                        htmldata = htmldata + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
                        htmldata = htmldata + "    <tr>";
                        htmldata = htmldata + "    <td align='right'>" + Pagination(jsondata.COUNT,totalPage,currentpage,range,10)+ "</td>";
                        htmldata = htmldata + "    </tr>";
                        htmldata = htmldata + "</table>";

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
        }

        function getColumns() {
            var sitename = searchForm.domainname.value;
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            //window.open("paddprogram.jsp","增加程序模块","top="+ iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight + ",resizable=yes,scrollbars=yes");
        }

        function getDepts() {
            var sitename = searchForm.domainname.value;
            alert("hello depts==" + sitename);
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            //window.open("pedit.jsp?id=" + progid,"修改程序模块","top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight + ",resizable=yes,scrollbars=yes");
        }

        function getEditors() {
            var sitename = searchForm.domainname.value;
            alert("hello editors==" + sitename);
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            //window.open("premove.jsp?id=" + progid,"删除程序模块","top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight + ",resizable=yes,scrollbars=yes");
        }

        function getsiteid(domainname) {
            var siteid = 0;
            htmlobj=$.ajax({
                url:"getSiteidBySitename.jsp",
                type:"POST",
                data:{
                    sitename:domainname
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    siteid = jsondata.SITEID;
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

            return siteid;
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
        <!--div id="maincontent" class="location" style="padding-bottom: 5px;display: none;"-->
        <div id="maincontent" class="location" style="padding-bottom: 5px;">
            <div>工作量统计分析 ></div>
            <div style="padding-top: 10px;padding-bottom: 10px;">
                <form name="searchForm">
                    站点域名：<input type="text" name="domainname" id="domainnameid" value=""  class="sear_txt_1" />
                    站点栏目：<input type="text" name="column" id="columnid" value="" placeholder="双击选择栏目"  class="sear_txt_1" ondblclick="javascript:getColumns();"/>
                    标题：<input type="text" name="title" id="titleid" value=""  class="sear_txt_1" />
                    部门：<input type="text" name="dept" id="deptid" value="" placeholder="双击选择部门" class="sear_txt_1" ondblclick="javascript:getDepts();"/>
                    文章作者：<input type="text" name="author" id="authorid" value="" placeholder="双击选择作者" class="sear_txt_1" ondblclick="javascript:getEditors();"/>
                    发布日期：<input type="text" name="spubdate" id="sdate" class="datetime_txt_1" />到
                    <input type="text" name="epubdate" id="edate" class="datetime_txt_1" />
                    操作：<select name="op" id="opid" class="sear_txt_1">
                    <option value="0">所有</option>
                    <option value="1">增加文章</option>
                    <option value="2">修改文章</option>
                    <option value="3">删除文章</option>
                    <option value="4">发布文章</option>
                    <option value="5">审核文章</option>
                    <option value="6">增加模板</option>
                    <option value="7">修改模板</option>
                    <option value="8">删除模板</option>
                    <option value="9">发布模板</option>
                    <option value="10">登录系统</option>
                    <option value="11">创建用户</option>
                    <option value="12">修改用户</option>
                    <option value="13">删除用户</option>
                    <option value="14">修改登录密码</option>
                </select>
                    <input type="button" name="search" id="searchid" value="搜索" onclick="javascript:logInfoList(20,1);" class="sear_btn"/>
                </form>
            </div>
        </div>

        <!-- 内容主体区域 -->
        <div id="contentid" style="left: 0px;padding-top:30px;"></div>
    </div>

    <div class="layui-footer" style="left: 0px;">
        版权信息： © 2003-2019 北京潮虹伟业科技有限公司，版权所有。
    </div>
</div>
<script>
    layui.use(['element', 'tree', 'laydate'], function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#sdate', //指定元素
            type:"datetime"
        });

        laydate.render({
            elem: '#edate', //指定元素
            type:"datetime"
        });
    });
</script>
</body>
</html>

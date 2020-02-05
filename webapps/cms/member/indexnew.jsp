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
<%@ page import="java.util.logging.SimpleFormatter" %>
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
    int pageno = 1;
    int range = 20;
    int rootOrgID = 0;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    Organization rootOrganization = null;
    List<Users> users = new ArrayList<Users>();
    if (appContext!=null) {
        OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
        rootOrganization = organizationService.getRootOrganization(BigDecimal.valueOf(siteid));
        rootOrgID = rootOrganization.getID().intValue();
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
            var orgid = <%=rootOrgID%>;
            usersManager();
            userInfoList(orgid,1,false,1,0);
            layui.use('element', function(){
                var element = layui.element;
            });
        });

        function goSearchPage(orgid,doSearchFlag,flag,ascdesc) {
            var currentPage = form.turnPage.value;
            userInfoList(orgid,currentPage,doSearchFlag,flag,ascdesc);
            form.turnPage.value = currentPage;
        }

        function Pagination(orgid,totalUserNum,totalpages,curentpage,pageNumLength,doSearchFlag,flag,ascdesc) {
            var PaginationDiv = "";
            if (totalpages <= 1)
                PaginationDiv = "<div class=\"page\" style=\"display: none\">";
            else
                PaginationDiv = "<div class=\"page\" style=\"display: block\">";

            if (curentpage<=1)
                PaginationDiv = PaginationDiv + "<span class=\"txtl\">用户总数：" + totalUserNum + "</span><span>第1页</span>";
            else {
                PaginationDiv = PaginationDiv + "<span class=\"txtl\">用户总数：" + totalUserNum + "<a href=\"javascript:userInfoList(" + orgid + ",1," + flag + "," + ascdesc + ");\">第1页</a>";
            }

            PaginationDiv = PaginationDiv + "<span>共" + totalpages + "页</span>";

            if (curentpage<=1)
                PaginationDiv = PaginationDiv + "<span>上一页</span>";
            else {
                PaginationDiv = PaginationDiv + "<a href=\"javascript:userInfoList(" + orgid + "," + (curentpage - 1) + ","+ doSearchFlag + "," + flag + "," + ascdesc + ");\">上一页</a>";
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
                    PaginationDiv = PaginationDiv + "<a href=\"javascript:userInfoList(" + orgid + "," + (ii + 1) + "," + doSearchFlag + "," + flag + "," + ascdesc + ");\">" + (ii + 1) + "</a>";
                }
            }

            if (curentpage<totalpages)
                PaginationDiv = PaginationDiv + "<a href=\"javascript:userInfoList(" + orgid + "," + (curentpage+1) + "," + doSearchFlag + "," + flag + "," + ascdesc + ");\">下一页</a>";
            else
                PaginationDiv = PaginationDiv + "<span>下一页</span>";

            PaginationDiv = PaginationDiv + "<span class=\"txtl\">转到第</span>";
            PaginationDiv = PaginationDiv + "<span class=\"select-pager\">";
            PaginationDiv = PaginationDiv + "<form name=\"form\">";

            PaginationDiv = PaginationDiv + "<select name=\"turnPage\" size=\"1\" onchange=\"javascript:goSearchPage(" + orgid + ",'" + doSearchFlag + "'," + flag + "," + ascdesc + ");\">";

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

        function userInfoList(orgid,currentPage,doSearchFlag,flag,ascdesc) {
            //alert("orgid:" + orgid);
            //alert("currentPage:" + currentPage);
            //alert("doSearchFlag:" + doSearchFlag);
            //alert("flag:" + flag);
            //alert("ascdesc:" + ascdesc);

            var keyword = searchForm.username.value;

            htmlobj=$.ajax({
                url:"usersManager.jsp",
                data:{
                    orgid:orgid,
                    keyword:keyword,
                    pageno:currentPage,
                    searchFlag:doSearchFlag,
                    flag:flag,
                    ascdesc:ascdesc
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    /*var e_posi = data.indexOf("]");
                    data = data.substring(0,e_posi+1);
                    var jsondata = $.parseJSON(data);                    //字符串转换为json对象方法一
                    //var jsondata = eval('(' + data + ')');              //字符串转换为json对象方法二
                    */
                    var totalUserNum = jsondata.USERCOUNT;
                    var range = jsondata.RANGE;
                    var extra = totalUserNum % range;
                    var totalPage = 0;
                    if (extra>0)
                        totalPage = parseInt(totalUserNum/range) + 1;
                    else
                        totalPage = totalUserNum/range;
                    var curentpage = jsondata.PAGENO;
                    var pageNumLength = 10;
                    var orgid = jsondata.ORGID;
                    var htmldata = "<table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n";
                    htmldata = htmldata + "    <tr>\n" +
                        "        <td align=\"left\" valign=\"top\">\n" +
                        "          <form name=form1 id=\"form1id\">" +
                        "            <table width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#CCCCCC\" class=\"tab_list\">\n" +
                        "                <tr>\n" +
                        "                    <td width=\"5%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><input type=\"checkbox\" name=\"allbox\" id=\"selallid\" onclick='javascript:CA();'/>选择</td>\n" +
                        "                    <td width=\"5%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">序号</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">用户名</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">真实姓名</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">电子邮件</td>\n" +
                        "                    <td width=\"5%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">手机号码</td>\n" +
                        "                    <td width=\"5%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">性别</td>\n" +
                        //"                    <td width=\"200\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">单位</td>\n" +
                        "                    <td width=\"20%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">部门</td>\n" +
                        "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">创建时间</td>\n" +
                        "                    <td width=\"5%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">授权</td>\n" +
                        "                    <td width=\"5%\" align=\"center\" bgcolor=\"#f4f4f4\">删除</td>\n" +
                        "                    <td width=\"5%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">修改</td>\n" +
                        "                    <td width=\"5%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">禁用/许可</td>\n" +
                        "                </tr>\n";
                    for(var ii=0;ii<jsondata.USERS.length;ii++) {
                        var nickname = (typeof(jsondata.USERS[ii].NICKNAME)!="undefined")?jsondata.USERS[ii].NICKNAME:"";
                        var email = (typeof(jsondata.USERS[ii].EMAIL)!="undefined")?jsondata.USERS[ii].EMAIL:"";
                        var mphone = (typeof(jsondata.USERS[ii].MPHONE)!="undefined")?jsondata.USERS[ii].MPHONE:"";
                        var sex = (jsondata.USERS[ii].SEX=="1")?"女":"男";
                        var company = (typeof(jsondata.USERS[ii].COMPANYID)!="undefined")?jsondata.USERS[ii].COMPANYID:"";
                        var dept = (typeof(jsondata.USERS[ii].DEPARTMENT)!="undefined")?jsondata.USERS[ii].DEPARTMENT:"";
                        var createdate = (typeof(jsondata.USERS[ii].CREATEDATE)!="undefined")?jsondata.USERS[ii].CREATEDATE:"";
                        var unlogin = jsondata.USERS[ii].UNLOGIN;

                        htmldata = htmldata + "                <tr bgcolor=\"#FFFFFF\" id=\"row\"" + jsondata.USERS[ii].ID + ">\n";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" +
                            "<input type=\"checkbox\" name=\"selitem\" id=\"selitem" + jsondata.USERS[ii].ID +"\" value=\"" + jsondata.USERS[ii].ID  +  "\" />" +
                            "<input type=\"hidden\" name=\"itemunlogin\" id=\"itemunlogin" + jsondata.USERS[ii].ID +"\" value=\"" + jsondata.USERS[ii].UNLOGIN  +  "\" />" +
                            "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + jsondata.USERS[ii].ID + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + jsondata.USERS[ii].USERID + "</td>";
                        htmldata = htmldata + "<td>" + nickname + "</td>";
                        htmldata = htmldata + "<td>" + email + "</td>";
                        htmldata = htmldata + "<td align=\"center\">" + mphone + "</td>";
                        htmldata = htmldata + "<td align=\"center\" >" + sex + "</td>";
                        //htmldata = htmldata + "<td align=\"center\" valign=\"middle\" >" + company + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + dept + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + createdate + "</td>";
                        htmldata = htmldata + "<td align=\"center\" valign=\"middle\" ><a href=\"javascript:grantuser('" + jsondata.USERS[ii].USERID + "'," + orgid + "," + curentpage + "," + range + "," + doSearchFlag + "," + flag + "," + ascdesc + ");\"><img src=\"../images/grant.png\" width=\"23\" height=\"23\" /></a></td>";
                        if (jsondata.USERS[ii].SITEMANAGERFLAG == 0) {
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:deluser('" + jsondata.USERS[ii].USERID + "'," + orgid + "," + curentpage + "," + range + "," + doSearchFlag + "," + flag + "," + ascdesc + ");\"><img src=\"../images/del.png\" width=\"23\" height=\"23\" /></a></td>";
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:updateuser('" + jsondata.USERS[ii].USERID + "'," + orgid + "," + curentpage + "," + range + "," + doSearchFlag + "," + flag + "," + ascdesc + ");\"><img src=\"../images/edit.png\" width=\"24\" height=\"23\" /></a></td>";
                        } else {
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><img src=\"../images/del.png\" width=\"23\" height=\"23\" /></td>";
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><img src=\"../images/edit.png\" width=\"24\" height=\"23\" /></td>";
                        }
                        if (jsondata.USERS[ii].UNLOGIN === 0)
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:unlogin('" + jsondata.USERS[ii].USERID + "',0," + orgid + "," + curentpage + "," + range + "," + doSearchFlag + "," + flag + "," + ascdesc + ");\"><img src=\"../images/jinzhi.png\" width=\"23\" height=\"23\" /></a></td>";
                        else
                            htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:unlogin('" + jsondata.USERS[ii].USERID + "',1," + orgid + "," + curentpage + "," + range + "," + doSearchFlag + "," + flag + "," + ascdesc + ");\"><img src=\"../images/unjinzhi.png\" width=\"23\" height=\"23\" /></a></td>";
                        htmldata = htmldata + "</tr>";
                        //alert(jsondata[ii].USERID + jsondata.NICKNAME + jsondata.EMAIL + jsondata.MPHONE + jsondata.SEX + jsondata.COMPANYID + jsondata.DEPARTMENT);
                    }

                    htmldata = htmldata + "            </table>\n";
                    htmldata = htmldata + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
                    htmldata = htmldata + "    <tr>";
                    htmldata = htmldata + "    <td align='right'>" + Pagination(orgid,totalUserNum,totalPage,curentpage,pageNumLength,doSearchFlag,flag,ascdesc)+ "</td>";
                    htmldata = htmldata + "    </tr>";
                    htmldata = htmldata + "</table>";
                    htmldata = htmldata + "   </form>";
                    htmldata = htmldata + "  </td>\n" +
                        "    </tr>\n" +
                        "</table>";

                    searchForm.orgid.value=orgid;
                    searchForm.searchFlag.value=doSearchFlag;
                    searchForm.flag.value=flag;
                    searchForm.ascdesc.value=ascdesc;
                    searchForm.pageno.value=currentPage;

                    $("#maincontent").css('display','block');;
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
                url:"../organization/organizationsForTree.jsp",
                type:'post',
                data:{
                },
                dataType:'text',
                async:false,
                success:function(data){
                    var posi = data.indexOf("[");
                    data = data.substring(posi);
                    if (data!="[]") {
                        layui.use('tree', function () {
                            var tree = layui.tree;
                            //渲染
                            var inst1 = tree.render({
                                elem: '#mytree',  //绑定元素
                                skin: 'sidebar',
                                showLine: true,
                                onlyIconControl: true,
                                target: '_blank', //是否新选项卡打开（比如节点返回href才有效）
                                click: function (item) { //点击节点回调
                                    userInfoList(item.data.id,1,false,1,0);
                                    //window.location.href = "indexnew.jsp?id=" + item.data.id;
                                    //layer.msg('当前节名称：'+ item.data.title + "==" + item.data.id + '<br>全部参数：'+ JSON.stringify(item));
                                    //console.log(item);
                                },
                                data: eval(data)
                            });
                        });
                        $("#sidetreeid").css("display","block");
                        $("#treebox").css("height", document.documentElement.clientHeight - 60);
                    } else{
                        $("#sidetreeid").css("display","none");
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
            });
        }

        function deluser(userid,orgid,curentpage,range,doSearchFlag,flag,ascdesc) {
            htmlobj=$.ajax({
                url:"removeUser.jsp",
                type:'post',
                data:{
                    doDelete:true,
                    userid:userid
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.ERRCODE == 0) {
                        layer.alert('用户删除成功！！！');
                        userInfoList(orgid,curentpage,doSearchFlag,flag,ascdesc);
                    } else {
                        layer.alert('用户删除失败！！！')
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
            });
        }

        function updateuser(userid,orgid,curentpage,range,doSearchFlag,flag,ascdesc) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("editUsernew.jsp?userid=" + userid + "&sorgid=" + orgid + "&pageno=" + curentpage + "&range="+range + "&searchFlag="+doSearchFlag + "&flag="+flag + "&ascdesc="+ascdesc, "EditUserWin", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + "scrollbars");
        }

        function grantuser(userid,orgid,curentpage,range,doSearchFlag,flag,ascdesc) {
            var iWidth=window.screen.availWidth-100;                                                 //弹出窗口的宽度;
            var iHeight=800;                                                //弹出窗口的高度;
            //var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            iTop = 0;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("grantRightToUser.jsp?guserid=" + userid + "&sorgid=" + orgid + "&pageno=" + curentpage + "&range="+range + "&searchFlag="+doSearchFlag + "&flag="+flag + "&ascdesc="+ascdesc, "EditUserWin", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + "scrollbars");
        }

        function unlogin(userid,unloginflag,orgid,curentpage,range,doSearchFlag,flag,ascdesc) {
            htmlobj=$.ajax({
                url:"unloginUser.jsp",
                type:'post',
                data:{
                    doUpdate:true,
                    unlogin:unloginflag,
                    userid:userid
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.ERRCODE == 0) {
                        if (unloginflag === 1)
                            layer.alert('禁止用户登录成功！！！');
                        else
                            layer.alert('允许用户登录成功！！！');
                        userInfoList(orgid,curentpage,doSearchFlag,flag,ascdesc);
                    } else {
                        if (unloginflag === 1)
                            layer.alert('禁止用户登录失败！！！');
                        else
                            layer.alert('允许用户登录失败！！！');
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
            });
        }

        function adduser() {
            var orgid = searchForm.orgid.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value;

            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("newuser.jsp?sorgid=" + orgid + "&pageno=" + currentPage + "&searchFlag="+doSearchFlag + "&flag="+flag + "&ascdesc="+ascdesc, "NewUserWin", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + "scrollbars");
        }

        function CA() {
            for (var i = 0; i < document.form1.elements.length; i++) {
                var e = document.form1.elements[i];
                if (e.name != 'allbox' && e.type == 'checkbox') {
                    e.checked = document.form1.allbox.checked;
                }
            }
        }

        function batchdel() {
            var orgid = searchForm.orgid.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value;

            var selUserIds = "";
            $('input:checkbox[name=selitem]:checked').each(function(i) {
                selUserIds = selUserIds + $(this).val() + ",";
            });
            if (selUserIds.length>0) selUserIds = selUserIds.substring(0,selUserIds.length-1);

            htmlobj=$.ajax({
                url:"removeChoicedUser.jsp",
                type:'post',
                data:{
                    doDelete:true,
                    userids:selUserIds
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.ERRCODE == 0) {
                        layer.alert('用户批量删除成功！！！');
                        userInfoList(orgid,currentPage,doSearchFlag,flag,ascdesc);
                    } else {
                        layer.alert('用户批量删除失败！！！')
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
            });
        }

        function batchunlogin() {
            var orgid = searchForm.orgid.value;
            var doSearchFlag = searchForm.searchFlag.value;
            var flag = searchForm.flag.value;
            var ascdesc = searchForm.ascdesc.value;
            var currentPage = searchForm.pageno.value;

            var selUserIds = "";
            var unloginval = "";
            $('input:checkbox[name=selitem]:checked').each(function(i) {
                selUserIds = selUserIds + $(this).val() + ",";
                unloginval = unloginval  + eval('$("#itemunlogin' + $(this).val() + '").val()') + ",";
            });
            if (selUserIds.length>0) selUserIds = selUserIds.substring(0,selUserIds.length-1);
            if (unloginval.length>0) unloginval = unloginval.substring(0,unloginval.length-1);

            htmlobj=$.ajax({
                url:"unloginChoicedUser.jsp",
                type:'post',
                data:{
                    doUpdate:true,
                    userids:selUserIds,
                    unloginflag:unloginval
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.ERRCODE == 0) {
                        layer.alert('批量禁止用户登录成功！！！');
                        userInfoList(orgid,currentPage,doSearchFlag,flag,ascdesc);
                    } else {
                        layer.alert('批量禁止用户登录失败！！！')
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
            });
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><a href="../index1.jsp" class="layui-logo">CMS-用户管理</a></div>
        <jsp:include page="../include/menu.jsp"></jsp:include>
    </div>

    <div class="layui-side layui-bg-black" id="sidetreeid" style="top: 60px;width: 250px;">
        <div id="treebox" style="overflow-x:auto;">
            <ul id="mytree"></ul>
        </div>
    </div>

    <div class="layui-body" style="left:250px;">
        <!-- 内容主体区域 -->
        <div id="maincontent" class="location" style="padding-bottom: 5px;display: none;">
            <div id="column_nameid">系统管理 > 用户管理</div>
            <div style="padding-top: 10px;padding-bottom: 10px;">
            <div style="padding-top: 10px;padding-bottom: 10px;">
                <form name="searchForm">
                    <input type="hidden" name="orgid" value="">
                    <input type="hidden" name="pageno" value="">
                    <input type="hidden" name="flag" value="">
                    <input type="hidden" name="ascdesc" value="">
                    <input type="hidden" name="searchFlag" value="">
                    用户名：<input type="text" name="username" id="userid" value=""  class="sear_txt_1" />
                    <!--创建日期：<input type="text" name="spubdate" id="sdate" class="datetime_txt_1" />到
                    <input type="text" name="epubdate" id="edate" class="datetime_txt_1" />-->
                    <input type="button" name="search" id="searchid" value="搜索" onclick="javascript:userInfoList(<%=rootOrgID%>,1,true,1,0);" class="sear_btn"/>
                </form>
            </div>
            <div class="button_top">
                <ul>
                    <li><input name="impusers" type="button" value="批量删除" class="button_bg_2"  onclick="javascript:batchdel();"/></li>
                    <li><input name="delmore" type="button" value="批量禁用" class="button_bg_3"  onclick="javascript:batchunlogin();"/></li>
                    <li><input name="adduser" type="button" value="新建" class="button_bg_1" onclick="javascript:adduser();"/></li>
                    <!--li><input name="impusers" type="button" value="批量导入" class="button_bg_2"  onclick="javascript:impusers();"/></li>
                    <li><input name="delmore" type="button" value="批量删除" class="button_bg_3"  onclick="javascript:delmore();"/></li-->
                </ul>
            </div>
        </div>

        <div id="contentid"></div>
    </div>
    <jsp:include page="../include/tail.jsp"></jsp:include>
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

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
          $("input[name=styletype]:eq(0)").attr("checked",'checked');
          styleInfoList(1);
      });
      function goout() {
          window.location.href = "../exit.jsp";
      }

      function styleInfoList(type) {
          htmlobj=$.ajax({
              url:"listStyle.jsp",
              type:"POST",
              data:{
                  type:type
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
                      "                    <td width=\"20%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">编号</td>\n" +
                      "                    <td width=\"20%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">中文名称</td>\n" +
                      "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">样式类型</td>\n" +
                      "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">编辑</td>\n" +
                      "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">修改日期</td>\n" +
                      "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">预览</td>\n" +
                      "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">修改</td>\n" +
                      "                    <td width=\"10%\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">删除</td>\n" +
                      "                </tr>\n";

                  for(var ii=0;ii<jsondata.DATA.length;ii++) {
                      var styleid = jsondata.DATA[ii].ID;
                      var cname = jsondata.DATA[ii].chinesename;
                      var styletype = jsondata.DATA[ii].type;
                      var editor = jsondata.DATA[ii].editor;
                      var updatedate = jsondata.DATA[ii].lastUpdated;

                      var viewtypestr = "";
                      if (styletype == 1)
                          viewtypestr = "文章列表";
                      else if (styletype == 2)
                          viewtypestr = "导航条";
                      else if (styletype == 3)
                          viewtypestr = "相关文章";
                      else if (styletype == 4)
                          viewtypestr = "热点文章";
                      else if (styletype == 5)
                          viewtypestr = "已阅读标题";
                      else if (styletype == 6)
                          viewtypestr = "栏目列表";
                      else if (styletype == 7)
                          viewtypestr = "新文章样式";
                      else if (styletype == 8)
                          viewtypestr = "路径样式";
                      else if (styletype == 9)
                          viewtypestr = "页面标题样式";
                      else if (styletype == 10)
                          viewtypestr = "上下篇";

                      htmldata = htmldata + "                <tr bgcolor=\"#FFFFFF\" id=\"" + styleid + "\">\n";
                      htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + styleid + "</td>";
                      htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + cname + "</td>";
                      htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + viewtypestr + "</td>";
                      htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + editor + "</td>";
                      htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + updatedate + "</td>";
                      htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href='javascript:PreviewStyle(" + styletype + "," + styleid + ");'><img src=\"../images/button/view.gif\" border=0></a></td>";
                      htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href='javascript:EditStyle(" + styletype + "," + styleid + ");'><img src=\"../images/button/edit.gif\" border=0></a></td>";
                      htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href='javascript:DeleteStyle(" + styletype + "," + styleid + ");'><img src=\"../images/button/del.gif\" border=0></a></td>";
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

      function EditStyle(type, styleID) {
          var siteID = <%=siteid%>;
          if (siteID > 0){
              if (type == 1 || type == 3 || type == 4) {
                  var iWidth = 1000;
                  var iHeight = 600;
                  var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                  var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                  var win = window.open("../template/editStyle.jsp?ID=" + styleID + "&type=" + type + "&from=system", "updateStyle", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
              }else if (type == 6) {
                  var iWidth = 1000;
                  var iHeight = 600;
                  var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                  var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                  var win = window.open("../template/editColumnStyle.jsp?ID=" + styleID + "&type=" + type + "&from=system", "updateStyle", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
              }else {
                  var iWidth = 1000;
                  var iHeight = 600;
                  var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                  var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                  var win = window.open("../template/editOtherStyle.jsp?ID=" + styleID + "&type=" + type + "&from=system", "updateStyle", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
              }
          }else {
              if (type == 1 || type == 3 || type == 4 || type == 6) {
                  var iWidth = 1000;
                  var iHeight = 600;
                  var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                  var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                  var win = window.open("../template/editColumnStyle.jsp?ID=" + styleID + "&type=" + type + "&from=system", "updateStyle", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
              } else {
                  var iWidth = 1000;
                  var iHeight = 600;
                  var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                  var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                  var win = window.open("../template/editOtherStyle.jsp?ID=" + styleID + "&type=" + type + "&from=system", "updateStyle", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
              }
          }
      }

      function PreviewStyle(type, styleID) {
          window.open("../template/getviewfile.jsp?id="+styleID+"&type="+type,"PreviewStyle","width=500,height=150,left=150,top=225,scrollbars=yes");
      }

      function DeleteStyle(type,styleID) {
          var msg = confirm("真要删除吗？");
          if (msg) {
              htmlobj=$.ajax({
                  url:"deleteStyle.jsp",
                  type:"POST",
                  data:{
                      styleid:styleID,
                      type:type
                  },
                  dataType:'json',
                  async:false,
                  success:function(jsondata){
                      if (jsondata.result == 1) {
                          alert("样式文件删除成功！！！");
                          styleInfoList(type);
                      }else
                          alert("样式文件删除失败！！！");
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

      function CreateStyle() {
          var siteID = <%=siteid%>;
          var type=$("input[name='styletype']:checked").val();
          if (typeof(type) == "undefined") alert("请选择样式文件类型");
          if (type > 0) {
              if (siteID > 0){
                  if (type == 1 || type == 3 || type == 4) {
                      var iWidth = 1000;
                      var iHeight = 600;
                      var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                      var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                      var win = window.open("../template/editStyle.jsp?type=" + type + "&from=system", "createStyle", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
                  }else if (type == 6) {
                      var iWidth = 1000;
                      var iHeight = 600;
                      var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                      var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                      var win = window.open("../template/editColumnStyle.jsp?type=" + type + "&from=system", "createStyle", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
                  }else {
                      var iWidth = 1000;
                      var iHeight = 600;
                      var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                      var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                      var win = window.open("../template/editOtherStyle.jsp?type=" + type + "&from=system", "createStyle", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
                  }
              }else{
                  if (type == 1 || type == 3 || type == 4 || type == 6) {
                      var iWidth = 1000;
                      var iHeight = 600;
                      var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                      var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                      var win = window.open("../template/editColumnStyle.jsp?type=" + type + "&from=system", "createStyle", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
                  }else {
                      var iWidth = 1000;
                      var iHeight = 600;
                      var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                      var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                      var win = window.open("../template/editOtherStyle.jsp?type=" + type + "&from=system", "createStyle", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
                  }
              }
          }
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
      <li class="layui-nav-item"><a href="javascript:void(0);">列表样式管理</a></li>
      <!--li class="layui-nav-item">
        <a href="javascript:;">列表样式管理</a>
        <dl class="layui-nav-child">
          <dd><a href="member/indexnew.jsp">文章列表</a></dd>
          <dd><a href="organization/indexnew.jsp">栏目列表</a></dd>
          <dd><a href="roles/index.jsp">相关文章</a></dd>
          <dd><a href="rights/index.jsp">热点文章</a></dd>
          <dd><a href="rights/index.jsp">导航条</a></dd>
          <dd><a href="rights/index.jsp">中英文路径</a></dd>
          <dd><a href="rights/index.jsp">新文章</a></dd>
          <dd><a href="rights/index.jsp">被读文章</a></dd>
          <dd><a href="rights/index.jsp">标题样式</a></dd>
          <dd><a href="rights/index.jsp">上下篇文章</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="">文章移动</a></li-->
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
    <!-- 内容主体区域 -->
    <div id="addsiteid" style="float: right">
      <input name="batchDel" type="button" value="新建样式" class="button_bg_3" onclick='javascript:CreateStyle();'/>
    </div>
    <div id="sousuoid" style="padding-top: 10px;padding-bottom: 10px;padding-top: 30px;">
      <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%" align=center>
        <tr height=24>
          <td align=center><input type="radio" name="styletype" value="1" onclick="javascript:styleInfoList(1);" /><b><font color=red>文章列表</font></b></td>
          <td align=center><input type="radio" name="styletype" value="6" onclick="javascript:styleInfoList(6);" /><b><font color=red>栏目列表</font></b></td>
          <td align=center><input type="radio" name="styletype" value="3" onclick="javascript:styleInfoList(3);" /><b><font color=red>相关文章</font></b></td>
          <td align=center><input type="radio" name="styletype" value="4" onclick="javascript:styleInfoList(4);" /><b><font color=red>热点文章</font></b></td>
          <td align=center><input type="radio" name="styletype" value="2" onclick="javascript:styleInfoList(2);" /><b><font color=red>导航条</font></b></td>
          <td align=center><input type="radio" name="styletype" value="8" onclick="javascript:styleInfoList(8);" /><b><font color=red>中英文路径</font></b></td>
          <td align=center><input type="radio" name="styletype" value="7" onclick="javascript:styleInfoList(7);" /><b><font color=red>新文章</font></b></td>
          <td align=center><input type="radio" name="styletype" value="5" onclick="javascript:styleInfoList(5);" /><b><font color=red>被读文章</font></b></td>
          <td align=center><input type="radio" name="styletype" value="9" onclick="javascript:styleInfoList(9);" /><b><font color=red>标题样式</font></b></td>
          <td align=center><input type="radio" name="styletype" value="10" onclick="javascript:styleInfoList(10);" /><b><font color=red>上下篇</font></b></td>
        </tr>
      </table>
    </div>
    <div id="contentid"></div>
  </div>

  <div class="layui-footer" style="left: 0px;">
    版权信息： © 2003-2019 北京潮虹伟业科技有限公司，版权所有。
  </div>
</div>
</body>
</html>

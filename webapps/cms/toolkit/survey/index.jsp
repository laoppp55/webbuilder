<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.webapps.survey.define.Define" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page contentType="text/html;charset=GBK" %>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
  int range = ParamUtil.getIntParameter(request, "range", 20);
  int success = ParamUtil.getIntParameter(request, "success", 0);
    int siteid = authToken.getSiteID();

  IDefineManager defineMgr = DefinePeer.getInstance();
  List currentlist = new ArrayList();
  String msg;
  switch (success) {
    case 1:
      msg = "调查添加成功";
      break;
    case 2:
      msg = "调查删除成功";
      break;
    case 3:
      msg = "调查修改成功";
      break;
    default:
      msg = "";
      break;
  }

  int rows = 0;
  int totalpages = 0;
  int currentpage = 0;

  currentlist = defineMgr.getAllDefineSurvey(siteid,startrow, range);
  rows = defineMgr.getAllDefineSurveyNum(siteid);

  if (rows < range) {
    totalpages = 1;
    currentpage = 1;
  } else {
    if (rows % range == 0)
      totalpages = rows / range;
    else
      totalpages = rows / range + 1;

    currentpage = startrow / range + 1;
  }
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
  <title>调查管理</title>
  <style type="text/css">
    <!--
    body {
      margin-top: 0px;
      margin-bottom: 0px;
    }
    -->
  </style>
  <link href="images/css.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript">
    function DeleteSurvey(ID)
    {
      var str = confirm("确认要删除调查吗？如果删除，属于该调查的问题及答案将一并删除！");
      if (str)
        window.location = "deleteSurvey.jsp?sid=" + ID;
    }

    function Pop_Vote(ID)
    {
      window.open("display.jsp?ID=" + ID, "Display", "left=200,top=150,width=400,height=400");
    }
    function golist(r){
      window.location = "index.jsp?startrow="+r;
    }
    function upflag(id,flag){
        var flags;
        if(flag == 0){
            flags = 1;
        }
        if(flag == 1){
            flags = 0;
        }
        var val;
        val = confirm("你确定更改有效标志？");
        if(val == 1){
             window.location = "flag.jsp?startflag=1"+"&flag="+flags+"&id="+id;
        }
    }
  </script>
</head>

<body>
<center>
<table width="900" border="0" cellpadding="0" cellspacing="0" class="bian">
<tr>
<td valign="top">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
  <td>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="50" height="40" align="center"><img src="images/qian_02.jpg" width="30" height="30"/></td>
        <td width="100" class="black12c">调查管理</td>
        <td width="550"></td>
        <td width="30" align="center"><img src="images/hb_01.jpg" width="11" height="7"/></td>
        <td width="100" class="black12c"><a
                href="addSurvey.jsp"><b>添加新调查</b></a></td>
        <td width="30" align="center"><img src="images/lv_01.jpg" width="11" height="7"/></td>
        <td width="37" class="black12c"><a href="../index.jsp">返回</a></td>
      </tr>
    </table>
  </td>
</tr>
<tr>
  <td height="1" bgcolor="#898898"></td>
</tr>
<tr>
  <td>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="50" height="30" align="center" bgcolor="#F6F5F0" class="black12c">编号</td>
        <td width="1" bgcolor="#898898"></td>
        <td width="200" align="center" bgcolor="#F6F5F0" class="black12c">名称</td>
        <td width="1" bgcolor="#898898"></td>
        <td width="200" align="center" bgcolor="#F6F5F0" class="black12c">描述</td>
        <td width="1" bgcolor="#898898"></td>
        <td width="75" align="center" bgcolor="#F6F5F0" class="black12c">创建时间</td>
          <td width="1" bgcolor="#898898"></td>
        <td width="75" align="center" bgcolor="#F6F5F0" class="black12c">是否有效</td>
        <td width="1" bgcolor="#898898"></td>
        <td width="300" align="center" bgcolor="#F6F5F0" class="black12c">管理</td>
      </tr>

    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <%
        if (currentlist != null) {
          for (int i = 0; i < currentlist.size(); i++) {
            Define define = (Define) currentlist.get(i);
            int id = define.getId();
            String surveyname = define.getSurveyname();
            String notes = define.getNotes();
            Timestamp createtime = define.getCreatetime();

      %>
      <tr>
        <td bgcolor="#898898"></td>
        <td bgcolor="#898898"></td>
        <td bgcolor="#898898"></td>
        <td bgcolor="#898898"></td>
        <td height="1" bgcolor="#898898"></td>
        <td bgcolor="#898898"></td>
        <td bgcolor="#898898"></td>
        <td bgcolor="#898898"></td>
        <td bgcolor="#898898"></td>
          <td bgcolor="#898898"></td>
        <td bgcolor="#898898"></td>
      </tr>
      <tr>
        <td width="50" height="30" align="center" class="black12"><%=i + 1%>
        </td>
        <td width="1" bgcolor="#898898"></td>
        <td width="200" align="center" class="black12"><%=StringUtil.iso2gb(surveyname)%>
        </td>
        <td width="1" bgcolor="#898898"></td>
        <td width="200" align="center" class="black12"><%=StringUtil.iso2gb(notes)%>
        </td>
        <td width="1" bgcolor="#898898"></td>
        <td width="75" align="center" class="black12"><%=createtime.toString().substring(0, 19)%>
        </td>
        <td width="1" bgcolor="#898898"></td>
          <td width="75" align="center" class="black12"><a href="javascript:upflag(<%=id%>,<%=define.getUserflag()%>);"><%if(define.getUserflag()==0){%>无效<%}else{%>有效<%}%></a>
        </td>
        <td width="1" bgcolor="#898898"></td>
        <td width="300" align="center" class="black12">
          <a title="对问题的增加、修改、删除操作" href="addQuestion.jsp?sid=<%=id%>">管理</a>
          <a title="删除调查" href="javascript:DeleteSurvey('<%=id%>')">删除</a>
          <a title="修改调查" href="updateSurvey.jsp?sid=<%=id%>">修改</a>
          <a title="横向预览调查" href="viewSurvey.jsp?sid=<%=id%>">横向预览</a>
          <a title="纵向预览调查" href="viewHSurvey.jsp?sid=<%=id%>"
             target=_blank>纵向预览</a><br>
          <a title="查看投票结果" href="viewResult.jsp?sid=<%=id%>"
             target=_blank>投票结果</a>
         <%-- <a title="查询投票人" href="../result/index.jsp?sid=<%=id%>" target=_blank>查询投票人</a>--%>
        </td>
      </tr>
      <%
          }
        }
      %>
    </table>
  </td>
</tr>
<tr>
  <td height="1" bgcolor="#898898"></td>
</tr>
<tr>
  <td height="40" align="right" class="black12">
    共有<%=rows%>条纪录&nbsp;&nbsp;总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
    &nbsp;&nbsp;&nbsp;&nbsp;
    <%
      if ((startrow - range) >= 0) {
    %>
    [<a href="index.jsp?startrow=<%=startrow-range%>">上一页</a>]
    <%}%>
    <%
      if ((startrow + range) < rows) {
    %>
    [<a href="index.jsp?startrow=<%=startrow+range%>">下一页</a>]
    <%
      }
      if (totalpages > 1) {
    %>
    &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
          <a href="###" onclick="golist((document.all('jump').value-1)*<%=range%>);">GO</a>
  <%}%>
</td>
</tr>
</table>
</td>
</tr>
</table>
</center>
</body>
</html>

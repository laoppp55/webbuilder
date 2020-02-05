<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.survey.define.Define" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.webapps.survey.define.DefineException" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int sid = ParamUtil.getIntParameter(request, "sid", -1);
  int qid = ParamUtil.getIntParameter(request, "qid", -1);
  int aid = ParamUtil.getIntParameter(request, "aid", -1);
  int startflag = ParamUtil.getIntParameter(request, "startflag", -1);

  IDefineManager defineMgr = DefinePeer.getInstance();
  Define define = new Define();

  List list = new ArrayList();
  String qname = "";
  String surveyname = "";
  String notes = "";
  String qanswer = "";
  int qtype = 1;
  int qmust = 1;

  if (startflag == 1) {
    qanswer = ParamUtil.getParameter(request, "qanswer");
    if (aid == -1) {
      try {
        define.setSid(sid);
        define.setQid(qid);
        define.setQanswer(qanswer);
        defineMgr.createDefineAnswer(define);

        response.sendRedirect("addAnswer.jsp?success=1&sid=" + sid + "&qid=" + qid);
        return;
      } catch (DefineException e) {
        e.printStackTrace();
      }
    } else {
      try {
        define.setSid(sid);
        define.setQid(qid);
        define.setAid(aid);
        define.setQanswer(qanswer);
        defineMgr.updateDefineAnswer(define);

        response.sendRedirect("addAnswer.jsp?success=2&sid=" + sid + "&qid=" + qid);
        return;
      } catch (DefineException e) {
        e.printStackTrace();
      }
    }
  } else {
    try {
      define = defineMgr.getADefineSurvey(sid);
      surveyname = define.getSurveyname();
      notes = define.getNotes();

      define = defineMgr.getADefineQuestion(qid);
      qname = define.getQname();
      qtype = define.getQtype();
      qmust = define.getQmust();

      list = defineMgr.getAllDefineAnswersByQID(qid);
      if (aid != -1) {
        define = defineMgr.getADefineAnswer(aid);
        qanswer = define.getQanswer();
      }
    } catch (DefineException e) {
      e.printStackTrace();
    }
  }
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
  <title>调查管理-答案管理</title>
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
    function DeleteAnswer(qid, sid, aid)
    {
      var str = confirm("确认要删除答案吗？");
      if (str)
        window.location = "deleteAnswer.jsp?qid=" + qid + "&sid=" + sid + "&aid=" + aid;
    }
  </script>
</head>

<body>
<center>
<table width="900" border="0" cellpadding="0" cellspacing="0" class="bian">
<tr>
<td height="360" align="center" valign="top">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<form method="POST" action="addAnswer.jsp" name=addQuestionForm>
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="sid" value="<%=sid%>">
<input type="hidden" name="qid" value="<%=qid%>">
<input type="hidden" name="aid" value="<%=aid%>">
<tr>
  <td valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="50" height="40" align="center"><img src="images/qian_01.jpg" width="30" height="30"/></td>
        <td align="left"><span class="black12c">调查管理</span><span class="black12">--答案管理</span></td>
        <td width="30" align="center"><img src="images/lv_01.jpg" width="11" height="7"/></td>
        <td width="37" class="black12c"><a href="addQuestion.jsp?sid=<%=sid%>">返回</a></td>
      </tr>

    </table>
  </td>
</tr>
<tr>
  <td height="1" valign="top" bgcolor="#898898"></td>
</tr>
<tr>
  <td valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="160" height="50" align="center" bgcolor="#F6F5F0" class="black12c">调查名称：</td>
        <td width="1" bgcolor="#898898"></td>
        <td align="left" class="black12">&nbsp;&nbsp;<%=StringUtil.iso2gb(surveyname)%></td>
      </tr>
      <tr>
        <td height="1" bgcolor="#898898"></td>
        <td bgcolor="#898898"></td>
        <td bgcolor="#898898"></td>
      </tr>
      <tr>
        <td width="160" height="50" align="center" bgcolor="#F6F5F0" class="black12c">调查描述：</td>
        <td bgcolor="#898898"></td>
        <td align="left" class="black12">&nbsp;&nbsp;<%=StringUtil.iso2gb(notes)%></td>
      </tr>
      <tr>
        <td height="1" bgcolor="#898898"></td>
        <td height="1" bgcolor="#898898"></td>
        <td height="1" bgcolor="#898898"></td>
      </tr>
      <tr>
        <td width="160" height="50" align="center" bgcolor="#F6F5F0" class="black12c">问题名称：</td>
        <td bgcolor="#898898"></td>
        <td align="left" class="black12">&nbsp;&nbsp;<%=StringUtil.iso2gb(qname)%></td>
      </tr>
      <tr>
        <td height="1" bgcolor="#898898"></td>
        <td height="1" bgcolor="#898898"></td>
        <td height="1" bgcolor="#898898"></td>
      </tr>
      <tr>
        <td width="160" height="50" align="center" bgcolor="#F6F5F0" class="black12c">问题类型：</td>
        <td bgcolor="#898898"></td>
        <td align="left" class="black12">&nbsp;&nbsp;<%if (qtype == 1) {%>单选<%} else if (qtype == 2) {%>多选<%}%></td>
      </tr>
      <tr>
        <td height="1" bgcolor="#898898"></td>
        <td height="1" bgcolor="#898898"></td>
        <td height="1" bgcolor="#898898"></td>
      </tr>
      <tr>
        <td width="160" height="50" align="center" bgcolor="#F6F5F0" class="black12c">是否必填：</td>
        <td bgcolor="#898898"></td>
        <td align="left" class="black12">&nbsp;&nbsp;<%if (qmust == 1) {%>选填<%} else if (qmust == 2) {%>必填<%}%></td>
      </tr>
      <tr>
        <td height="1" bgcolor="#898898"></td>
        <td height="1" bgcolor="#898898"></td>
        <td height="1" bgcolor="#898898"></td>
      </tr>
      <tr>
        <td width="160" height="50" align="center" bgcolor="#F6F5F0" class="black12c">答&nbsp;&nbsp;&nbsp;&nbsp;案：</td>
        <td bgcolor="#898898"></td>
        <td align="left" valign="middle">
          &nbsp;<input type="text" name="qanswer" size=80 value="<%=StringUtil.iso2gb(qanswer)%>">
        </td>
      </tr>

    </table>
  </td>
</tr>
<tr>
  <td height="1" valign="top" bgcolor="#898898"></td>
</tr>
<tr>
  <td height="80" align="center" valign="middle">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="310"></td>
        <td width="52"><input type="submit" value="提  交" name="survey_submit"></td>
        <td width="60"></td>
        <td width="52"><input type="reset" value="重  写" name="survey_reset"></td>
        <td width="60"></td>
        <td width="52"><input type="button" value="返  回" name="survey_back"
                     onclick="window.location='addQuestion.jsp?sid=<%=sid%>';"></td>
        <td width="60"></td>
        <td width="95"><input type="button" value="返回管理首页" name="survey_back" onclick="window.location='index.jsp';"></td>
        <td width="310"></td>
      </tr>
    </table>
  </td>
</tr>
</form>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="1" bgcolor="#898898"></td>
          <td bgcolor="#898898"></td>
          <td bgcolor="#898898"></td>
        </tr>
        <tr>
          <td width="600" height="40" align="center" bgcolor="#F6F5F0" class="black12c">问题</td>
          <td width="1" bgcolor="#898898"></td>
          <td align="center" bgcolor="#F6F5F0" class="black12c">管理</td>
        </tr>
        <tr>
          <td height="1" bgcolor="#898898"></td>
          <td height="1" bgcolor="#898898"></td>
          <td height="1" bgcolor="#898898"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <%
                  for (int i = 0; i < list.size(); i++) {
                    define = (Define) list.get(i);
                    aid = define.getAid();
                    qanswer = define.getQanswer();
                %>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="600" height="80" align="center" class="black12"><%=StringUtil.iso2gb(qanswer)%></td>
          <td width="1" bgcolor="#898898"></td>
          <td width="297" align="center" class="black12">
            <a href="javascript:DeleteAnswer(<%=qid%>,<%=sid%>,<%=aid%>)">删除</a>
            <a href="addAnswer.jsp?qid=<%=qid%>&sid=<%=sid%>&aid=<%=aid%>">修改</a></td>
        </tr>
        <tr>
          <td width="1" bgcolor="#898898"></td>
          <td width="1" bgcolor="#898898"></td>
          <td width="1" bgcolor="#898898"></td>
        </tr>
      </table>
      <%
                  }
                %>
    </td>
  </tr>
  <tr>
    <td height="40"></td>
  </tr>
</table>
</td>
</tr>
</table>
</center>
</body>
</html>


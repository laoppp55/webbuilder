<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.survey.define.Define" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="com.bizwink.webapps.survey.define.DefineException" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
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
  int startflag = ParamUtil.getIntParameter(request, "startflag", -1);

  if (startflag == 1) {
    String surveyname = ParamUtil.getParameter(request, "surveyname");
    String notes = ParamUtil.getParameter(request, "notes");

    try {
      IDefineManager defineMgr = DefinePeer.getInstance();
      Define define = new Define();
      define.setSurveyname(surveyname);
      define.setNotes(notes);
        define.setSiteid(authToken.getSiteID());
      defineMgr.createDefineSurvey(define);

      response.sendRedirect("index.jsp?success=1");
      return;
    } catch (DefineException e) {
      e.printStackTrace();
    }
  }
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
  <title>添加新调查</title>
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
    function checkSurvey(){
      if((addSurveyForm.surveyname.value == null)||(addSurveyForm.surveyname.value == "")){
        alert("请输入定义调查的名称");
        return false;
      }
      if((addSurveyForm.notes.value == null)||(addSurveyForm.notes.value == "")){
        alert("请输入定义调查的描述");
        return false;
      }
      return true;
    }
  </script>
</head>

<body>
<center>
<table width="900" border="0" cellpadding="0" cellspacing="0" class="bian">
  <tr>
    <td height="360" valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <form method="POST" action="addSurvey.jsp" name="addSurveyForm" onsubmit="javascript:return checkSurvey();">
          <input type="hidden" name="startflag" value="1">
          <tr>
            <td>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="50" height="40" align="center"><img src="images/qian_02.jpg" width="30" height="30"/>
                  </td>
                  <td width="150" class="black12c">添加新调查</td>
                  <td width="697"></td>
                </tr>

              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="200" height="1" bgcolor="#898898"></td>
                  <td width="1" height="1" bgcolor="#898898"></td>
                  <td height="1" bgcolor="#898898"></td>
                </tr>
                <tr>
                  <td height="40" align="center" bgcolor="#F6F5F0" class="black12c">调查名称：</td>
                  <td width="1" bgcolor="#898898"></td>
                  <td>
                    &nbsp;&nbsp;<input type="text" name="surveyname" size="80">
                  </td>
                </tr>
                <tr>
                  <td height="1" bgcolor="#898898"></td>
                  <td width="1" height="1" bgcolor="#898898"></td>
                  <td height="1" bgcolor="#898898"></td>
                </tr>
                <tr>
                  <td height="100" align="center" bgcolor="#F6F5F0" class="black12c">调查描述：</td>
                  <td width="1" bgcolor="#898898"></td>
                  <td>
                    &nbsp;&nbsp;<textarea rows="6" name="notes" cols="80"></textarea>
                  </td>
                </tr>
                <tr>
                  <td height="1" bgcolor="#898898"></td>
                  <td width="1" height="1" bgcolor="#898898"></td>
                  <td height="1" bgcolor="#898898"></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td height="50">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="300"></td>
                  <td width="52"><input type="submit" value="提  交" name="survey_submit"></td>
                  <td width="70"></td>
                  <td width="52"><input type="reset" value="重  写" name="survey_reset"></td>
                  <td width="71"></td>
                  <td width="52"><input type="button" value="返  回" name="survey_back" onclick="window.location = 'index.jsp'"></td>
                  <td width="300"></td>
                </tr>
              </table>
            </td>
          </tr>
        </form>
      </table>
    </td>
  </tr>
</table>
</center>
</body>
</html>



<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.survey.define.DefineException" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.survey.define.Define" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page contentType="text/html;charset=GBK" %>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null) {
    response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int sid = ParamUtil.getIntParameter(request, "sid", -1);

  IDefineManager defineMgr = DefinePeer.getInstance();
  List questionlist = new ArrayList();
  List answerlist = new ArrayList();

  if (sid != -1) {
    try {
      questionlist = defineMgr.getAllDefineQuestionsBySID(sid);
    } catch (DefineException e) {
      e.printStackTrace();
    }
  }
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
  <title>调查预览</title>
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
      function check() {
          <%
            for(int i=0;i<questionlist.size();i++){
              Define define = (Define)questionlist.get(i);
              int qmust = define.getQmust();
              int qid = define.getQid();

              if(qmust == 2){
          %>
          var mustflag = false;
          for (var i = 0; i < answerForm.answer<%=qid%>.length; i++) {
              if (answerForm.answer<%=qid%>[i].checked) {
                  mustflag = true;
                  break;
              }
          }
          if (!mustflag) {
              alert("请选择问题 <%=i+1%> 的答案！");
              return false;
          }
          <%
              }
            }
          %>
          /*if ((answerForm.username.value == null) || (answerForm.username.value == "")) {
            alert("请输入您的姓名！");
            return false;
          }
          if ((answerForm.phone.value == null) || (answerForm.phone.value == "")) {
            alert("请输入联系电话！");
            return false;
          }
          if ((answerForm.email.value == null) || (answerForm.email.value == "")) {
            alert("请输入电子邮件！");
            return false;
          }*/

          answerForm.submit();
          return true;
      }

      function checkNum(listsize, nother, qid, which, qtype) {
          var otherBtnName = "answerForm.other" + qid;
          var o = eval(otherBtnName);
          if (nother == 1) {
              if (listsize == which) {
                  if (qtype == 1) {
                      o.disabled = 0;
                  } else {
                      if (eval("answerForm.answer" + qid + "[" + listsize + "]").checked) {
                          o.disabled = 0;
                      } else {
                          o.disabled = 1;
                      }
                  }
              } else {
                  o.disabled = 1;
              }
          }
      }

      function viewResult() {
          window.location = "viewResult.jsp?sid=<%=sid%>";
      }
  </script>
</head>

<body>
<center>
  <table width="900" border="0" cellpadding="0" cellspacing="0" class="bian">
    <tr>
      <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <form action="answer.jsp" method="post" name="answerForm">
            <input type="hidden" name="sid" value="<%=sid%>">
            <tr>
              <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="50" height="40" align="center"><img src="images/qian_02.jpg" width="30" height="30"/></td>
                    <td width="150" class="black12c">调查预览</td>
                    <td width="697"></td>
                  </tr>

                </table>
              </td>
            </tr>
            <%
              if (questionlist.size() > 0) {
            %>
            <tr>
              <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <%
                    for (int i = 0; i < questionlist.size(); i++) {
                      Define qdefine = (Define) questionlist.get(i);
                      int qid = qdefine.getQid();
                      String qname = qdefine.getQname();
                      int qtype = qdefine.getQtype();
                      int nother = qdefine.getNother();
                      int atype = qdefine.getAtype();
                  %>
                  <tr>
                    <td height="1" bgcolor="#898898"></td>
                  </tr>
                  <tr>
                    <td height="30" bgcolor="#F6F5F0" class="black12">&nbsp;&nbsp;<%=StringUtil.iso2gb(qname)%>
                    </td>
                  </tr>
                  <tr>
                    <td height="1" bgcolor="#898898"></td>
                  </tr>
                  <%
                    try {
                      answerlist = defineMgr.getAllDefineAnswersByQID(qid);
                    } catch (DefineException e) {
                      e.printStackTrace();
                    }
                  %>
                  <tr>
                    <td height="100" align="left" class="black12">
                      <%
                        if (answerlist != null) {
                          for (int j = 0; j < answerlist.size(); j++) {
                            Define adefine = (Define) answerlist.get(j);
                            String qanswer = adefine.getQanswer();
                            String picurl = adefine.getPicurl();

                            if (atype != 1) {
                              qanswer = picurl;
                            }
                      %>
                      <%if (qtype == 1) {%>
                      <input type="radio" name="answer<%=qid%>" value="<%=StringUtil.iso2gb(qanswer)%>"
                             onClick="checkNum(<%=answerlist.size()-1%>,<%=nother%>,<%=qid%>,<%=j%>,<%=qtype%>);">
                      <%if (atype == 1) {%><%=StringUtil.iso2gb(qanswer)%><%} else {%><a href="../answerspic/<%=picurl%>"
                                                                                         target=_blank><img
                            src="../answerspic/<%=picurl%>"
                            alt="" width=160 border=0></a><%}%>&nbsp;&nbsp;
                      <%} else {%>
                      <input type="checkbox" name="answer<%=qid%>" value="<%=StringUtil.iso2gb(qanswer)%>"
                             onClick="checkNum(<%=answerlist.size()-1%>,<%=nother%>,<%=qid%>,<%=j%>,<%=qtype%>);">
                      <%if (atype == 1) {%><%=StringUtil.iso2gb(qanswer)%><%} else {%><a href="../answerspic/<%=picurl%>"
                                                                                         target=_blank><img
                            src="../answerspic/<%=picurl%>"
                            alt="" width=160 border=0></a><%}%>&nbsp;&nbsp;
                      <%
                          }
                        }
                        if (nother == 1) {
                      %>
                      <input type="text" name="other<%=qid%>">
                      <script type="text/javascript">
                          answerForm.other<%=qid%>.disabled = 1;
                      </script>
                      <%
                          }
                        }
                      %>
                    </td>
                  </tr>
                  <%
                    }
                  %>
                </table>
              </td>
            </tr>
            <tr>
              <td height="50">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align=300></td>
                    <td align="center"><input type="button" name="subbutton" value=" 提交 " onclick="return check();">
                      &nbsp;&nbsp;
                      <input type="button" name="surveybut" value="查看结果" onclick="viewResult();">&nbsp;&nbsp;
                      <input type="button" name="surveybut" value=" 返回 " onclick="javascript:window.location='index.jsp'">
                    </td>
                    <td align=300></td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td height="1" bgcolor="#898898"></td>
            </tr>
            <%
              String requesturl = request.getRequestURI();
            %>
            <tr>
              <td height="30" bgcolor="#F6F5F0" class="black12">&nbsp;&nbsp;前台页面调用URL：<%=requesturl%></td>
            </tr>
            <%
              } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert(\"调查定义不完整，请完整定义后预览调查！\");");
                out.println("window.close();");
                out.println("</script>");
              }
            %>
          </form>
        </table>
      </td>
    </tr>
  </table>
</center>
</body>
</html>




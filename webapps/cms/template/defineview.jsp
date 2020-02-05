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
<%@ page contentType="text/html;charset=utf-8" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    String url = "/sites/" +  authToken.getSitename() +  "/css";

  int sid = ParamUtil.getIntParameter(request, "id", -1);
  String wstytle = ParamUtil.getParameter(request,"wstytle");
    String astytle = ParamUtil.getParameter(request,"astytle");
    int ys = ParamUtil.getIntParameter(request,"yangshi",0);
    if(wstytle.equals("-1"))
    {
        wstytle = "";
    }
    if(astytle.equals("-1")){
        astytle = "";
    }
    if(wstytle.indexOf(".")>-1){
        wstytle = wstytle.substring(wstytle.indexOf("."));
    }
    if(astytle.indexOf(".")>-1){
        astytle = astytle.substring(astytle.indexOf("."));
    }
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
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>调查预览</title>
  <style type="text/css">
    <!--
    body {
      margin-top: 0px;
      margin-bottom: 0px;
    }

    -->
  </style>
  <link href="<%=url%>/css.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<center>
<%if(ys == 0){%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
        <td height="1" ></td>
      </tr>
      <tr>
        <td height="30" class="<%=wstytle%>">&nbsp;&nbsp;<%=StringUtil.iso2gb(qname)%>
        </td>
      </tr>
      <tr>
        <td height="1"></td>
      </tr>
      <%
        try {
          answerlist = defineMgr.getAllDefineAnswersByQID(qid);
        } catch (DefineException e) {
          e.printStackTrace();
        }
      %>
      <tr>
        <td height="100" align="left" class="<%=astytle%>">
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
        <td align="center"><input type="button" name="subbutton" value=" 提交 ">
          &nbsp;&nbsp;
          <input type="button" name="surveybut" value="查看结果">&nbsp;&nbsp;

        </td>
        <td align=300></td>
      </tr>
    </table>
  </td>
</tr>
<%
  } else {
    out.println("<script type=\"text/javascript\">");
    out.println("alert(\"调查定义不完整，请完整定义后预览调查！\");");
    out.println("window.close();");
    out.println("</script>");
  }
%>

</table>
 <%}else{%>
    <table border="0" width="100%" cellspacing="1" cellpadding=3>
<%
  if (questionlist.size() > 0) {
    for (int i = 0; i < questionlist.size(); i++) {
      Define qdefine = (Define) questionlist.get(i);
      int qid = qdefine.getQid();
      String qname = qdefine.getQname();
      int qtype = qdefine.getQtype();
      int nother = qdefine.getNother();
      int atype = qdefine.getAtype();
%>
<tr>
  <td width="5%" align=left class="<%=wstytle%>"><%=StringUtil.iso2gb(qname)%>
  </td>
</tr>
<%
  try {
    answerlist = defineMgr.getAllDefineAnswersByQID(qid);
  } catch (DefineException e) {
    e.printStackTrace();
  }
%>
<tr height=35>
  <td width="5%" align=left>
    <table border=0>
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
      <tr>
        <td class="<%=astytle%>">
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
          %>
        </td>
      </tr>
      <%
        }
      %>
    </table>
  </td>
</tr>
<%
  }
%>
<tr>
  <td align=center height=50><input type="button" name="subbutton" value="提   交">&nbsp;
    <input type="button" name="surveybut" value="查看结果">&nbsp;
  </td>
</tr>
<%
  } else {
    out.println("<script type=\"text/javascript\">");
    out.println("alert(\"调查定义不完整，请完整定义后预览调查！\");");
    out.println("window.close();");
    out.println("</script>");
  }
%>
</table>
    <%}%>
</center>
</body>
</html>




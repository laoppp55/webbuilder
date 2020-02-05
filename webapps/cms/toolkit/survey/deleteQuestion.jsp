<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="com.bizwink.webapps.survey.define.DefineException" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page contentType="text/html;charset=GBK"%>

<%
  int qid = ParamUtil.getIntParameter(request, "qid", -1);
  int sid = ParamUtil.getIntParameter(request, "sid", -1);

  if (qid != -1) {
    IDefineManager defineMgr = DefinePeer.getInstance();

    try {
      defineMgr.deleteDefineQuestion(qid);

      response.sendRedirect("addQuestion.jsp?success=1&sid="+sid);
      return;
    } catch (DefineException e){
      e.printStackTrace();
    }
  }else{
    response.sendRedirect("addQuestion.jsp?success=2&sid="+sid);
  }
%>
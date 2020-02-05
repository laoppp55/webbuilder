<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.survey.define.DefineException" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page contentType="text/html;charset=GBK" %>

<%
  int sid = ParamUtil.getIntParameter(request, "sid", -1);

  if (sid != -1) {
    IDefineManager defineMgr = DefinePeer.getInstance();
    try {
      defineMgr.deleteDefineSurvey(sid);
    } catch (DefineException e) {
      e.printStackTrace();
    }
    response.sendRedirect("index.jsp?success=2");
    return;
  } else {
    response.sendRedirect("index.jsp?success=-1");
    return;
  }
%>
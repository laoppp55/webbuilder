<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.program.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null) {
    response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int id = ParamUtil.getIntParameter(request, "id", -1);
  int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
  int errcode = 0;

  IProgramManager programMgr = ProgramPeer.getInstance();
  Program program = programMgr.getAPrograms(id);
  if(startflag == 1)errcode = programMgr.removeProgram(id);

%>
<html>

<head>
  <title>程序管理</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link href="../css/cms_css.css" rel=stylesheet>
  <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
  <script>
      $(document).ready(function(){
          var errcode = <%=errcode%>;
          if (errcode === 1) {
              alert("程序模块删除成功");
              window.opener.programInfoList();
              window.close();
          } else if (errcode < 0){
              alert("程序模块删除失败");
              window.close();
          }
      });
  </script>
</head>

<body>
<table cellSpacing="0" cellPadding="0" width="770" border="0" align=center>
  <tr><td>
    <p class=line>删除程序模块 <b><%=StringUtil.gb2iso4View(program.getNotes())%></b> ?<p>
    <ul class=cur>警告: 此操作将删除该程序模块，您真的想删除吗?</ul>
    <form name="leavewordForm" method="post" action="premove.jsp">
      <input type="hidden" name="startflag" value="1">
      <input type="hidden" name="id" value="<%=id%>">
      <input type=image src="../images/button_dele.gif">
      &nbsp;&nbsp;&nbsp;&nbsp;
      <a href="javascript:window.close();"><img src="../images/button_cancel.gif" border=0></a>
    </form>
  </td></tr>
</table>
</body>
</html>
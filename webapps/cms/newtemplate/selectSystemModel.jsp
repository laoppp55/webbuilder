<%@ page import="java.util.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.systmeTemplate.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int count = ParamUtil.getIntParameter(request,"count",0);
  int columnID = ParamUtil.getIntParameter(request,"column",0);
  int templateColumnID = ParamUtil.getIntParameter(request,"templateCID",0);
  int retcode   = 0;

  String username = authToken.getUserID();
  String sitename = authToken.getSitename();
  int siteid      = authToken.getSiteID();
  int imgflag     = authToken.getImgSaveFlag();
  int big5flag      = authToken.getPublishFlag();
  String appPath  = request.getRealPath("/");

  ISystemTemplateManager systemTemplateManager = systemTemplatePeer.getInstance();
  SystemTemplate systemTemplate = systemTemplateManager.getSystemTemplateType(templateColumnID);
  String templateSourceDir = systemTemplate.getDirName();
  IColumnManager columnMgr = ColumnPeer.getInstance();
  Column column = columnMgr.getColumn(columnID);
  String templateTargetDir = column.getDirName();

  for (int i=0; i<count; i++)
  {
    int systemModelID = ParamUtil.getIntParameter(request, "selected"+i, 0);
    retcode = systemTemplateManager.selectTemplate(appPath,systemModelID,templateSourceDir,templateTargetDir,sitename,siteid,imgflag,big5flag,username);
  }

%>
<script language="javascript">
  window.parent.close();
  window.open("message.jsp?retcode=<%=retcode%>","message",'width=480,height=360,left=200,top=100,scrollbars=yes,status=yes')
</script>

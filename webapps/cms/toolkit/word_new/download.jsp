<%@ page import="com.jspsmart.upload.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page contentType="text/html;charset=gbk"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    //如果在weblogic底下同样要加上此句
    String userid = authToken.getUserID();
    String sitename = authToken.getSitename();
    String appPath = application.getRealPath("/");
    response.reset();
    String disName = request.getParameter("file");
    SmartUpload su = new SmartUpload();
    su.initialize(pageContext);
    // 设定contentDisposition为null以禁止浏览器自动打开文件，
    //保证点击链接后是下载文件。若不设定，则下载的文件扩展名为
    //doc时，浏览器将自动用word打开它。扩展名为pdf时，
    //浏览器将用acrobat打开。
    exportWriter writer = new exportWriter();
    su.setContentDisposition(null);
    // 下载文件
    String fileName = writer.toUtf8String(disName);
    fileName = appPath + java.io.File.separator +  "sites" + java.io.File.separator + sitename + java.io.File.separator + "downanwser" + java.io.File.separator + userid + java.io.File.separator + fileName;
    try{
        su.downloadFile(fileName);
    } catch(java.io.FileNotFoundException e) {
        e.printStackTrace();
%>
<script Language="javascript">
    alert('服务器上未找到要下载的文件！');
</script>
<%}
%>

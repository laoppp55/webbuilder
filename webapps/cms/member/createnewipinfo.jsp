<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp?url=member/createnewsite.jsp" );
        return;
    }
    if (!SecurityCheck.hasPermission(authToken,54))
    {
        request.setAttribute("message","无系统管理的权限");
        response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
        return;
    }

    int ID = ParamUtil.getIntParameter(request, "ID", 0);
    int siteID = ParamUtil.getIntParameter(request, "siteID", 0);
    int pubway = ParamUtil.getIntParameter(request, "pubway", -1);
    int status = ParamUtil.getIntParameter(request, "status", 0);
    String siteIP = ParamUtil.getParameter(request, "ip");
    String siteName = ParamUtil.getParameter(request, "sitename");
    String docroot = ParamUtil.getParameter(request, "root");
    String ftpuser = ParamUtil.getParameter(request, "ftp_username");
    String ftppasswd = ParamUtil.getParameter(request, "ftp_passwd");
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");

    if (siteIP == null) siteIP = "localhost";
    IFtpSetManager ftpMgr = FtpSetting.getInstance();

    int ftptype = 0;
    if (pubway == 2) {     //pubway=2表示是SFTP发布
        pubway = 0;        //回复pubway=0,表示是采用FTP协议进行信息发布
        ftptype = 1;       //设置FTP协议是信息加密的SFTP协议
    } else if (pubway == 0) {
        pubway = 0;        //回复pubway=0,表示是采用FTP协议进行信息发布
        ftptype = 0;       //设置FTP协议是信息非加密的FTP协议
    } else {
        pubway = 1;        //本地信息发布，ftptype设置的值无影响
    }

    System.out.println("ftptype===" + ftptype);

    if (doCreate) {
        FtpInfo ftpinfo = new FtpInfo();
        ftpinfo.setSiteid(siteID);
        ftpinfo.setSiteName(siteName);
        ftpinfo.setDocpath(docroot);
        ftpinfo.setFtppwd(ftppasswd);
        ftpinfo.setFtpuser(ftpuser);
        ftpinfo.setIp(siteIP);
        ftpinfo.setFtptype(ftptype);
        ftpinfo.setPublishway(pubway);
        ftpinfo.setStatus(status);

        ftpMgr.create(ftpinfo);
        //将文章列表图标发布到WEB服务器
        //regMgr.copyIconToWEB(authToken.getUserID(),siteID,application.getRealPath("/"));
    }

    if (doUpdate) {
        FtpInfo ftpinfo = new FtpInfo();
        ftpinfo.setID(ID);
        ftpinfo.setSiteName(siteName);
        ftpinfo.setIp(siteIP);
        ftpinfo.setDocpath(docroot);
        ftpinfo.setFtpuser(ftpuser);
        ftpinfo.setFtptype(ftptype);
        ftpinfo.setFtppwd(ftppasswd);
        ftpinfo.setStatus(status);
        ftpinfo.setPublishway(pubway);
        ftpMgr.update(ftpinfo);
    }
%>

<script language="javascript">
    opener.history.go(0);
    window.close();
</script>

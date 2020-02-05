<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 17-2-4
  Time: 上午11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.net.ftp.FTPDefaultWorkingDirectory" %>
<%@ page import="com.bizwink.net.sftp.SFTPDefaultWorkingDirectory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp" );
        return;
    }

    String ipaddr = ParamUtil.getParameter(request, "ip");
    String username = ParamUtil.getParameter(request, "username");
    String passwd = ParamUtil.getParameter(request, "passwd");
    int ftptype = ParamUtil.getIntParameter(request, "ftptype",0);

    String ftphomedir = null;
    if (ftptype == 0) {            //FTP
        FTPDefaultWorkingDirectory ftpDefaultWorkingDirectory = new FTPDefaultWorkingDirectory();
        ftphomedir = ftpDefaultWorkingDirectory.getDefaultWorkingDirectory(ipaddr,username,passwd,21);
    } else if (ftptype == 2){     //SFTP
        SFTPDefaultWorkingDirectory sftpDefaultWorkingDirectory = new SFTPDefaultWorkingDirectory();
        ftphomedir = sftpDefaultWorkingDirectory.getDefaultWorkingDirectory(ipaddr,username,passwd,22);
    }

    System.out.println("ftphomedir==" + ftphomedir);

    if (ftphomedir!=null) {
        out.print("dir:" + ftphomedir);
        out.flush();
        out.close();
    }else {
        out.print("nodata");
        out.flush();
        out.close();
    }

    return;
%>
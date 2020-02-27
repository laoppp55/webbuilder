<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="com.bizwink.dubboservice.service.SiteipinfoService" %>
<%@ page import="com.bizwink.po.Siteipinfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.net.sftp.BatchFtpFile" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.dubboservice.service.ColumnService" %>
<%@ page import="com.bizwink.po.Column" %>
<%@ page import="java.math.BigDecimal" %>
<%
    ApplicationContext appContext = SpringInit.getApplicationContext();
    String username = null;
    int usertype = 0;
    int columnid= ParamUtil.getIntParameter(request,"columnid",0);

    System.out.println("columnid=" + columnid);

    //入股SESSION失效，试试COOKIE是否可以读出来
    Cookie[] cookies = request.getCookies();
    String cookie_value = null;
    for(Cookie c :cookies ){
        if (c.getName().equalsIgnoreCase("AuthInfo_cookie")) {
            SecurityUtil securityUtil = new SecurityUtil();
            cookie_value = securityUtil.detrypt(c.getValue(),null);
            break;
        }
    }

    if (appContext!=null) {
        UsersService ds = (UsersService)appContext.getBean("UsersService");
        SiteipinfoService siteipinfoService = (SiteipinfoService)appContext.getBean("SiteipinfoService");
        ColumnService columnService = (ColumnService)appContext.getBean("ColumnService");
        if (cookie_value != null) {
            int posi = cookie_value.indexOf("-");
            username = cookie_value.substring(0,posi);
            usertype = Integer.parseInt(cookie_value.substring(posi));
            Users user = ds.getByUserId(username);
            Column column =columnService.getColumnByID(BigDecimal.valueOf(columnid));

            FileUpload fileUpload = new FileUpload();
            fileUpload.initialize(this.getServletConfig(), request, response);
            String retFilename = fileUpload.upload(column.getDIRNAME());
            System.out.println("ret file name---" + retFilename);
            List<String> localFileNames = null;
            if (retFilename != null) {
                localFileNames = new ArrayList<String>();
                localFileNames.add(retFilename);
            }

            int errcode = 0;
            BatchFtpFile batchFtpFile = new BatchFtpFile();
            List<Siteipinfo> siteipinfos = siteipinfoService.getSiteipinfoBySiteid(user.getSITEID());
            for(int ii=0; ii<siteipinfos.size(); ii++) {
                Siteipinfo siteipinfo = siteipinfos.get(ii);
                String siteIP = siteipinfo.getSITEIP();
                String remoteDocRoot = siteipinfo.getDOCPATH();
                String ftpUser = siteipinfo.getFTPUSER();
                String ftpPasswd = siteipinfo.getFTPPASSWD();

                if (localFileNames != null) {
                    if (siteipinfo.getPUBLISHWAY() == 0) {
                        errcode = batchFtpFile.open(siteIP, ftpUser, ftpPasswd);
                        if (errcode == 0)
                            errcode = batchFtpFile.transferMoreFile(localFileNames, remoteDocRoot);
                        batchFtpFile.close();
                    }

                    if (siteipinfo.getPUBLISHWAY() == 1 || siteipinfo.getPUBLISHWAY() == 2) {
                        Copy copyHandle = new Copy();
                        errcode = copyHandle.copyMoreFiles(localFileNames, remoteDocRoot);
                    }

                    System.out.println("docroot=" + siteipinfo.getDOCPATH());
                    System.out.println("publishway=" + siteipinfo.getPUBLISHWAY());
                    System.out.println("user=" + siteipinfo.getFTPUSER());
                    System.out.println("pwd=" + siteipinfo.getFTPPASSWD());
                    System.out.println("host ip=" + siteipinfo.getSITEIP());
                }
            }
        }
    }

    String params = request.getQueryString();
    System.out.println("upload images===" + params);
    String callbackfunc=request.getParameter("CKEditorFuncNum");
    // 返回"图像"选项卡并显示图片  request.getContextPath()为web项目名
    out.println("<script type=\"text/javascript\">");
    out.println("window.parent.CKEDITOR.tools.callFunction("+ callbackfunc + ",'http://www.gugulx.com/images/20150527_10.jpg'" + ",'');");
    //out.println("window.parent.CKEDITOR.tools.callFunction("+ callbackfunc + ",'" +"img/postImg/"+ fileName + "','')");
    out.println("</script>");
    System.out.println("finished");
%>
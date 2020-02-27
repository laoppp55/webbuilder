<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.dubboservice.service.ArticleService" %>
<%@ page import="java.io.File" %>
<%@ page import="com.bizwink.FileUpload.SingleFileUpload" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-6-19
  Time: 下午10:05
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username = null;

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

    if (cookie_value != null) {
        int posi = cookie_value.indexOf("-");
        username = cookie_value.substring(0, posi);
    } else {                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
        if (authToken != null) {
            SessionUtil.removeUserAuthorization(response, session);
        }
    }

    ErrorMessage errorMessage = new ErrorMessage();
    if (username != null) {
        int columnid = ParamUtil.getIntParameter(request, "colid", 0);
        System.out.println("food create===" + columnid);
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            ArticleService articleService = (ArticleService)appContext.getBean("ArticleService");
            SingleFileUpload upload =new SingleFileUpload();
            upload.parseRequest(request);

            //设置上传文件的存储目录
            File parent =new File("C:\\upload\\");

            try{
                upload.upload(parent);
            } catch(org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException e){
                // 文件大小超出最大值
                errorMessage.setErrcode(-1);
                errorMessage.setErrmsg("上传文件大小超出最大值");
                errorMessage.setModelname("美食图片上传模块");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
%>

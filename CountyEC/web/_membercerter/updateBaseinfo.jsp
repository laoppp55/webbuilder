<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-3-11
  Time: 下午4:37
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

    int errcode = 0;
    Users user = null;
    Users user_init_info = null;
    if (username != null) {
        ApplicationContext appContext = SpringInit.getApplicationContext();
        String infotype = ParamUtil.getParameter(request,"type");
        String info = URLDecoder.decode(ParamUtil.getParameter(request, "info"));
        if (appContext!=null) {
            String pwd = null;
            UsersService usersService = (UsersService)appContext.getBean("UsersService");
            user = usersService.getByUserId(username);
            if (infotype.equalsIgnoreCase("mphone")){
                user_init_info = usersService.selectByMphone(info);               //判断新的手机号是否被注册
                if (user_init_info!=null) errcode = -1;
                user.setMPHONE(info);
            }else if (infotype.equalsIgnoreCase("email")){                      //判断新的电子邮件地址是否被注册
                user_init_info = usersService.selectByEmail(info);
                if (user_init_info!=null) errcode = -1;
                user.setEMAIL(info);
            }else if (infotype.equalsIgnoreCase("njyname"))
                user.setCOMPANY(info);
            else if (infotype.equalsIgnoreCase("contactor"))
                user.setNICKNAME(info);
            else if (infotype.equalsIgnoreCase("address"))
                user.setADDRESS(info);
            else if (infotype.equalsIgnoreCase("pass"))
                user.setUSERPWD(Encrypt.md5(info.getBytes()));

            if (errcode == 0) errcode = usersService.updateBasicInfo(infotype,user);      //如果修改手机号码或者邮件地址，这些信息没有被注册则进行修改
        }
    } else {
        response.sendRedirect("/_members/login.jsp");
    }

    if (errcode > 0)
        out.write("true");
    else
        out.write("false");
    out.flush();
%>
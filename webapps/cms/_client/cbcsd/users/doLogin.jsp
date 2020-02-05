<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.service.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.security.Auth" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%
    boolean doLogin = ParamUtil.getBooleanParameter(request,"doLogin");
    String refer_url = request.getHeader("referer");
    String userid = filter.excludeHTMLCode(ParamUtil.getParameter(request,"userid"));
    String passwd = filter.excludeHTMLCode(ParamUtil.getParameter(request,"pwd"));
    Auth auth = null;
    int errcode = 0;

    if (doLogin) {
        ApplicationContext appContext = SpringInit.getApplicationContext();
        String password = null;
        if (appContext!=null) {
            UsersService usersService = (UsersService)appContext.getBean("usersService");
            //获取用户信息
            Users us= usersService.getUserinfoByUserid(userid);
            if (us==null) {
                us= usersService.getUserinfoByEmail(userid);
                if (us == null)
                    us= usersService.getUserinfoByMphone(userid);
            }

            if (us == null) {
                errcode = -1;
            } else {
                try {
                    password = Encrypt.md5(passwd.getBytes());
                } catch (Exception e) {
                    errcode = -2;
                }
                if (password!=null) {
                    //用户口令错
                    if (!password.equalsIgnoreCase(us.getUSERPWD())) {
                        errcode = -3;
                    } else {
                        errcode = 1;
                        auth = new Auth();
                        auth.setUid(us.getID().intValue());
                        auth.setSiteid(us.getSITEID().intValue());
                        auth.setUserid(us.getUSERID());
                        auth.setUsername(us.getNICKNAME());
                        auth.setUsertype(us.getUSERTYPE().intValue());
                        session.setAttribute("AuthInfo", auth);

                        //设置在gugulx.com的二级域中都可以访问的cookie
                        /*Gson gson = new Gson();
                        String jsondata = gson.toJson(auth);
                        SecurityUtil securityUtil = new SecurityUtil();
                        Cookie loginCookie = new Cookie("AuthInfo_cookie",securityUtil.encrypt(jsondata,null));
                        loginCookie.setDomain("cbcsd.org.cn");
                        loginCookie.setPath("/");
                        loginCookie.setMaxAge(-1);
                        response.addCookie(loginCookie);*/
						Cookie userCookie = new Cookie("username",us.getUSERID());
						userCookie.setPath("/");
						response.addCookie(userCookie);
                    }
                }
            }
        } else {
            System.out.println("环境初始化失败");
            errcode = -4;
        }
    }

    Gson gson = new Gson();
    String jsonData = null;
    if (auth!=null)
        jsonData =  gson.toJson(auth);
    else
        jsonData = "{\"result\":\"false\"}";

    JSON.setPrintWriter(response, jsonData,"utf-8");
%>
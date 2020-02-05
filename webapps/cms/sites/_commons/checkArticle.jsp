<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    //判断文章权限
    int userflag = ParamUtil.getIntParameter(request,"userflag",0);//是否只有登陆用户可浏览
    int userlevel = ParamUtil.getIntParameter(request,"userlevel",0);//用户级别标志
    if(userflag == 1){
        //只有登录用户可看 判断用户是否登录
        Uregister ug = (Uregister)session.getAttribute("UserLogin");
        //用户没有登录
        if(ug == null){
            out.print("nologin");
            return;
        }else if (ug.getErrmsg().equals("ok")){
           if(userlevel >= 0){
               if(ug.getUsertype() != userlevel){
                   out.write("nolevel-" + ug.getErrmsg());
                   return;
               }
           }
        } else if (ug.getErrmsg().equals("用户口令错！！！")) {
            out.print("用户口令错");
            return;
        } else if (ug.getErrmsg().equals("用户被系统管理员锁定，暂时停止登录！！！")) {
            out.print("用户被系统管理员锁定，暂时停止登录");
            return;
        } else if (ug.getErrmsg().equals("用户名错，用户不存在！！！")) {
            out.print("用户名错，用户不存在");
            return;
        }
        out.write("true");
    }
%>
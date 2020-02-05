<%--
  Created by IntelliJ IDEA.
  User: Vincent kang
  Date: 2010-7-20
  Time: 10:54:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.*" %>
<%@ page import="java.util.List" %>

<%
     Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int articleid = 0;
    int columnID = 0;
   //文章已有图片的删除
       
    int picid = ParamUtil.getIntParameter(request,"picid",0);   //数据库中图片唯一id
    articleid = ParamUtil.getIntParameter(request,"articleid",0);
    columnID = ParamUtil.getIntParameter(request, "column", 0);
    IArticleManager articleManager = ArticlePeer.getInstance();
    //删除成功，跳转
    int code = articleManager.deleteArticleTurnpic(picid);
     if( code== 0) {
         response.sendRedirect(response.encodeRedirectURL("upload_turn_pic.jsp?column="  +  columnID+ "&articleid=" + articleid));
     }
     else{
         
     }
  //已上传图片未保存 的删除
   String picname = ParamUtil.getParameter(request,"url");
   List piclist = (List) session.getAttribute("turn_pic");
   if (null != piclist){
     for(int i =0; i< piclist.size();i++){
          Turnpic tpic = (Turnpic)piclist.get(i);
           if(picname.equals(tpic.getPicname())){
               piclist.remove(i);
               break;
           }
     }
      session.setAttribute("turn_pic",piclist);
      response.sendRedirect(response.encodeRedirectURL("upload_turn_pic.jsp?column="  +  columnID+ "&articleid=" + articleid));
   }


%>
<html>
  <head><title>删除图片</title></head>
  <body></body>
</html>
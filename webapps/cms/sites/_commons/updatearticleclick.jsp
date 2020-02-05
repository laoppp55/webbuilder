<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.articleonclick.IArticleOnclickManager" %>
<%@ page import="com.bizwink.webapps.articleonclick.ArticleOnclickPeer" %>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.net.URLDecoder" %>
<%
       int articleid= ParamUtil.getIntParameter(request,"articleid",-1);
       int flag=ParamUtil.getIntParameter(request,"flag",-1);

       if(articleid>-1)
       {   IArticleOnclickManager aomgr= ArticleOnclickPeer.getInstance();
           int count=0;
           if(flag==0){
               //¸üÐÂÐ´COOIKE
               int z=0;
               Cookie[] cookies = request.getCookies();
               for(int i=0;i<cookies.length;i++){
                   if(cookies[i].getName().equals("key")){
                       // System.out.println("cookies="+cookies[i].getName());
                        z=1;
                    }

                   }
              // System.out.println("z="+z);
               if(z==0){
                    int i =aomgr.updateArticleOnclick(articleid);
                   
               }
              // if(cookies.length==0){
                    Cookie cookie = new Cookie("key", "value");
                    cookie.setMaxAge(2880);
                    response.addCookie(cookie);
            //   }
                    count=aomgr.getArticleClicknum(articleid);
                    out.write(count+"");
           }
           if(flag==1)
           {
               count=aomgr.getArticleClicknum(articleid);
               out.write(count+"");
           }
       }
%>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.webcms.IWebArticleManager" %>
<%@ page import="com.webcms.WebArticlePeer" %>
<%@ page import="com.bizwink.cms.news.Article" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
     Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid=authToken.getSiteID();
     String username = authToken.getUserID();
    IColumnManager  colmgr= ColumnPeer.getInstance();
    List list=colmgr.getListIsShare();
    int columnid=ParamUtil.getIntParameter(request,"columnid",-1);
     IWebArticleManager wap= WebArticlePeer.getInstance();

    int count = 0;
    int zongpage = 0;
    int recordcount = 10;
    int ipage=0;

      if(columnid!=-1)
    {
       count =wap.getArticlecount(columnid,siteid) ;

    }


       String dpage=request.getParameter("dpage");
       zongpage = (count + recordcount - 1) / recordcount;
       try{
	     	ipage=Integer.parseInt(dpage);
	   }catch(Exception e){
			ipage=1;
       }
       if (ipage <= 0) ipage = 1;
       if (ipage >= zongpage) ipage = zongpage;
    List articlelist=new ArrayList();
       if(columnid!=-1)
       {
          articlelist=wap.getArticleList(ipage,columnid,siteid) ; 
       }

    
%>
<html>
<head>

    <script type="text/javascript">
        function tijiao(columnID)
        {
           window.open("createarticle.jsp?column=" + columnID, "", "width=935,height=650,left=5,top=5,status,scrollbars");
        }
        function edit(columnID,artilceid)
        {
           window.open("editarticle.jsp?column=" + columnID+"&article="+artilceid, "", "width=935,height=650,left=5,top=5,status,scrollbars");
        }
    </script>
</head>
<body>
     <%for(int i=0;i<list.size();i++)
     {
          Column columns=(Column)list.get(i);
         %>
     <a href="index.jsp?columnid=<%=columns.getID()%>"> <%=columns.getCName()%></a>&nbsp;&nbsp;&nbsp;&nbsp;
      <%if(columnid==columns.getID())
      {
      %>
        <a href="javascript:tijiao(<%=columns.getID()%>)">  新建</a>
     <%
     }
         }
     %>
        <br><br><br><br><br><br>
        <table>
     <%
       for(int i=0;i<articlelist.size();i++)
       {
           Article article=(Article)articlelist.get(i);
     %>
            <tr><td><a href="javascript:tijiao(<%=article.getColumnID()%>)"> <%=article.getMainTitle()%></a></td><td><a href="javascript:edit(<%=article.getColumnID()%>,<%=article.getID()%>)">修改</a></td><td>删除</td></tr>
      <%}%>
       </table>

     
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td height="100" align="center" valign="bottom" class="blues"><%if(zongpage>1)
                        {
                        %>
      总页数:<%=zongpage%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 当前页：<%=ipage+"/"+zongpage%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;               <a href="?dpage=1" class="lian1">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="?dpage=<%=ipage-1%>" class="lian1"  >上一页 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="?dpage=<%=ipage+1%>" class="lian1"  >下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="?dpage=<%=zongpage%>" class="lian1"  >尾页 </a>
                       <%}%></td>
        </tr>
        </table>
</body>
</html>

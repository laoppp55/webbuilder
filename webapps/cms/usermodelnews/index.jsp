<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.tree.node" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
     Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int samsitetype = authToken.getSamSitetype();
    int modeltype = ParamUtil.getIntParameter(request, "modeltype", 0);
    Tree colTree = null;
    if (samsitetype == 0 || samsitetype==1)
        colTree = TreeManager.getInstance().getSiteTree(siteid);
    else
        colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
   
    String username = authToken.getUserID();
    IColumnManager colmgr= ColumnPeer.getInstance();

    System.out.println("siteid="+siteid+" samsiteid="+samsiteid);
    //colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
   
%>
<html>
<head>

    <script type="text/javascript">

    </script>
</head>
<body>
<%
    if (colTree != null){
    node[] treenodes = colTree.getAllNodes();
     Column column=colmgr.getRootparentColumn(siteid);
     int a[] = TreeManager.getInstance().getSubTreeColumnIDList(treenodes,column.getID());
    for(int i=2;i<a.length;i++)                                                           
    {
        Column col=colmgr.getColumn(a[i]);
        if(!col.getCName().equals("包含文件")){
     %>

      <a href="articles.jsp?column=<%=col.getID()%>"><%=col.getCName()%></a>&nbsp;&nbsp;
<%   }
    }
     }
%>
       
</body>
</html>

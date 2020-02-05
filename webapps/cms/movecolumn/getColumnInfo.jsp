<%@page import="com.bizwink.cms.news.*,
                com.bizwink.move.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.sitesetting.*"
        contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int columnID = ParamUtil.getIntParameter(request,"column",0);
  IColumnManager columnMgr = ColumnPeer.getInstance();
  Column column = columnMgr.getColumn(columnID);
  int siteID = column.getSiteID();
  ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();

  IMoveManager moveMgr = MovePeer.getInstance();
  int allArticleNum = moveMgr.getArticlesNum(columnID);       //包括子栏目所有的文章
  out.print(column.getEName()+","+column.getDirName()+","+siteMgr.getSiteInfo(siteID).getDomainName()+","+siteID+","+allArticleNum);
%>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.tree.node" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.chinabuyregister.IChinaBuyRegiterManager" %>
<%@ page import="com.chinabuyregister.ChinaBuyRegiterPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.news.Article" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page import="com.bizwink.cms.register.Register" %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
     int siteid= ParamUtil.getIntParameter(request,"siid",-1);
      ISiteInfoManager sitepeer= SiteInfoPeer.getInstance();
     int samsiteid=ParamUtil.getIntParameter(request,"said",-1);
    if(siteid==-1||samsiteid==-1)
    {
        response.sendRedirect("index.jsp");
    }
    IColumnManager colmgr= ColumnPeer.getInstance();
    Tree colTree = null;
    System.out.println("siteid="+siteid);
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    
    colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>电子商务展区</title>
<link href="images/digtal1css.css" rel="stylesheet" type="text/css" />
</head>

<body>
<%@ include file="/sites/www_chinabuy360_cn/include/top.shtml"%>
<table width="1003" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="13">&nbsp;</td>
    <td width="243" align="left" valign="top"><table width="243" border="0" cellspacing="0" cellpadding="0">

      <tr>
        <td align="left" valign="top"><img src="images/digtal1.1_03.jpg" width="243" height="37" /></td>
      </tr>
      <tr>
        <td align="left" valign="top" background="images/digtal1.1_06.jpg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="13%">&nbsp;</td>
            <td width="87%" height="25" align="left" valign="middle" class="fonttitle"><%
			 if (colTree != null){
    node[] treenodes = colTree.getAllNodes();
     Column column=colmgr.getRootparentColumn(siteid);
                  System.out.println("siteid="+siteid+"   "+column.getID());
     int a[] = TreeManager.getInstance().getSubTreeColumnIDList(treenodes,column.getID());
                 System.out.println("siteid="+siteid+"  a[]"+a.length);
    for(int i=2;i<a.length-1;i++)
    {
        Column col=colmgr.getColumn(a[i]);
        if(col.getCName().equals("商铺介绍")){
			%>
			<%=col.getCName()%>
			<%   }
    }
     }
%></td>
          </tr>

		   <tr>
            <td width="13%">&nbsp;</td>
            <td width="87%" height="25" align="left" valign="middle" class="fonttitle">商品展区</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="left" valign="top">
			<ul>
			<%
			 if (colTree != null){
    node[] treenodes = colTree.getAllNodes();
     Column column=colmgr.getRootparentColumn(siteid);
     int a[] = TreeManager.getInstance().getSubTreeColumnIDList(treenodes,column.getID());
    for(int i=2;i<a.length-1;i++)
    {
        Column col=colmgr.getColumn(a[i]);
        if(!col.getCName().equals("包含文件")&&!col.getCName().equals("商铺介绍")&&!col.getCName().equals("商品展区")){
			%>
			<li><a href="list.jsp?cid=<%=col.getID()%>&siid=<%=siteid%>&said=<%=samsiteid%>" class="lefttitle"><%=col.getCName()%></a></li>

<%   }
    }
     }
%>
			</ul>
			</td>
          </tr>
        </table>
        </td>
      </tr>
      <tr>
        <td align="left" valign="top"><img src="images/digtal1.1_08.jpg" width="243" height="21" /></td>
      </tr>
    </table></td>
    <td width="6">&nbsp;</td>
	<td width="727" align="left" valign="top"><table width="727" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="images/digtal1.6.jpg" width="727" height="305" /></td>
      </tr>
      <tr>
        <td align="left" valign="top"><img src="images/digtal1.1_11.jpg" width="727" height="49" /></td>
      </tr>
      <tr>
        <td align="left" valign="top" background="images/digtal1.1_14.jpg" style="background-repeat:repeat-y;"><p>&nbsp;</p>



            <table width="727" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="5">&nbsp;</td>
        <%
		//手镯
            IChinaBuyRegiterManager chinabuymgr= ChinaBuyRegiterPeer.getInstance();
            Column column =chinabuymgr.getLikeCnameColumn("手镯",siteid);
            if(column!=null)
            {
                List list=chinabuymgr.getChinabuyIndex(column.getID(),8);

                for(int i=0;i<list.size();i++)
                {
                    Article article=(Article)list.get(i);
                     String picdirname=article.getDirName();
                           String picimg=article.getProductPic();
                           System.out.println("picimg="+picimg);
                            String pic="";
                        Register reg=chinabuymgr.getReg(article.getSiteID());
                        String address=reg.getAddress();
                        String company=reg.getCompany();
                        String mphone=reg.getMphone();
                        if(address==null)
                        {
                            address="";
                        }
                        if(mphone==null)
                        {
                            mphone="";
                        }
                       if(company==null)
                        {
                            company="";
                        }
                         if(picimg==null){

                            pic="images/digtal1.1.jpg";
                         }
                         else{
                            SiteInfo site=sitepeer.getSiteInfo(article.getSiteID());
                            pic="http://"+site.getDomainName()+picdirname+"images/"+picimg;
                         }
                    if((i)%4==0)
                    {


        %>
             <td width="5">&nbsp;</td>
            </tr>
          </table>
            <table width="727" border="0" cellspacing="0" cellpadding="0">

            <tr>
                  <%}%>
                       <%
                           
                       %>
             <td width="181" align="center" valign="top"><a href="article.jsp?articleid=<%=article.getID()%>&siid=<%=siteid%>&said=<%=samsiteid%>"><img src="<%=pic%>" width="167" height="111" hspace="5" border="0" /></a><br />
                <br />
                  <a href="article.jsp?articleid=<%=article.getID()%>&siid=<%=siteid%>&said=<%=samsiteid%>" class="fontgrey"><%=article.getMainTitle()%><br />
                价格：</a><span class="fontred">￥<%=article.getSalePrice()%></span>&nbsp;<br>电话:<%=mphone%><br />公司名称:<%=company%><br>地址:<%=address%></td>
              <td width="10">&nbsp;</td>







             <%}}%>
                  <td width="5">&nbsp;</td>
            </tr>
          </table>
        </td>
      </tr>
	  <tr>
        <td align="left" valign="top"><img src="images/digtal1.1_16.jpg" width="727" height="24" /></td>
      </tr>
    </table>
	  <table width="727" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td align="left" valign="top"><img src="images/digtal1.1_11-1.jpg" width="727" height="49" /></td>
        </tr>
        <tr>
          <td align="left" valign="top" background="images/digtal1.1_14.jpg"><p>&nbsp;</p>
               <%
		//手镯
          //  IChinaBuyRegiterManager chinabuymgr= ChinaBuyRegiterPeer.getInstance();
         //   Column column =chinabuymgr.getLikeCnameColumn("电脑",siteid);
            if(column!=null)
            {
                List list=chinabuymgr.getChinabuyIndex(41390,8);

                for(int i=0;i<list.size();i++)
                {
                    Article article=(Article)list.get(i);
                     String picdirname=article.getDirName();
                           String picimg=article.getProductPic();
                           System.out.println("picimg="+picimg);
                            String pic="";
                        Register reg=chinabuymgr.getReg(article.getSiteID());
                        String address=reg.getAddress();
                        String company=reg.getCompany();
                        String mphone=reg.getMphone();
                        if(address==null)
                        {
                            address="";
                        }
                        if(mphone==null)
                        {
                            mphone="";
                        }
                       if(company==null)
                        {
                            company="";
                        }
                         if(picimg==null){

                            pic="images/digtal1.1.jpg";
                         }
                         else{
                            SiteInfo site=sitepeer.getSiteInfo(article.getSiteID());
                            pic="http://"+site.getDomainName()+picdirname+"images/"+picimg;
                         }
                    if((i)%4==0)
                    {


        %>
          <td width="5">&nbsp;</td>
        </tr>
  </table>
            <table width="727" border="0" cellspacing="0" cellpadding="0">

            <tr>
                  <%}%>

             <td width="181" align="center" valign="top"><a href="article.jsp?articleid=<%=article.getID()%>&siid=<%=siteid%>&said=<%=samsiteid%>"><img src="<%=pic%>" width="167" height="111" hspace="5" border="0" /></a><br />
                <br />
                  <a href="article.jsp?articleid=<%=article.getID()%>&siid=<%=siteid%>&said=<%=samsiteid%>" class="fontgrey"><%=article.getMainTitle()%><br />
                价格：</a><span class="fontred">￥<%=article.getSalePrice()%></span>&nbsp;<br>电话:<%=mphone%><br />公司名称:<%=company%><br>地址:<%=address%></td>
              <td width="10">&nbsp;</td>







             <%}}%>
</td>
        </tr>
        <tr>
          <td align="left" valign="top"><img src="images/digtal1.1_16.jpg" width="727" height="24" /></td>
        </tr>
      </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table><%@ include file="/sites/www_chinabuy360_cn/include/low.shtml"%>
</body>
</html>

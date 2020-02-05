<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.tree.node" %>
<%@ page import="com.chinabuyregister.IChinaBuyRegiterManager" %>
<%@ page import="com.chinabuyregister.ChinaBuyRegiterPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page import="com.bizwink.cms.register.Register" %>
<%@ page import="com.bizwink.cms.news.*" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
     int siteid= ParamUtil.getIntParameter(request,"siid",-1);
     int cid=ParamUtil.getIntParameter(request,"cid",-1);
     int articleid=ParamUtil.getIntParameter(request,"articleid",-1);
      int samsiteid=ParamUtil.getIntParameter(request,"said",-1);
    ISiteInfoManager sitepeer= SiteInfoPeer.getInstance();
    if(siteid==-1||samsiteid==-1||articleid==-1)
    {
        response.sendRedirect("index.jsp");
    }
    IColumnManager colmgr= ColumnPeer.getInstance();
    Tree colTree = null;
    colTree =  TreeManager.getInstance().getSiteTree(siteid);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
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
     int a[] = TreeManager.getInstance().getSubTreeColumnIDList(treenodes,column.getID());
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
        <td align="left" valign="top"><img src="images/digtal1.1_11.jpg" width="727" height="49" /></td>
      </tr>
      <tr>
        <td align="left" valign="top" background="images/digtal1.1_14.jpg" style="background-repeat:repeat-y;"><p>&nbsp;</p>



            <table width="727" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="5">&nbsp;<%
                  IChinaBuyRegiterManager chinabuymgr= ChinaBuyRegiterPeer.getInstance();

                 Article article= chinabuymgr.getArticle(articleid);
                   String picdirname=article.getDirName();
                           String picimg=article.getProductPic();
                           System.out.println("picimg="+picimg+" siteid="+article.getSiteID());
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
                  String bigimg=article.getProductBigPic();
                  String big="";
                  if(bigimg==null)
                  {
                    big="images/digtal1.1.jpg";
                  }
                  else{
                      SiteInfo site=sitepeer.getSiteInfo(article.getSiteID());
                            big="http://"+site.getDomainName()+picdirname+"images/"+bigimg; 
                  }
              %></td> </tr></table>
                 <table width="727" border="0" cellspacing="0" cellpadding="0" >

            <tr>
                   <td width="181" align="center" valign="top"><table width="700" >
                     <tr><td width="224"><img src="<%=pic%>" width="167" height="111" hspace="5" /><br /><%=article.getMainTitle()%><br /></td>
              <td ><table align="left"><tr><td align="left"><span class="fontred">价格：￥<%=article.getSalePrice()%></span></td></tr><tr><td align="left">电话:<%=mphone%><br>公司名称:<%=company%></td></tr><tr><td align="left">地址:<%=address%></td></tr></table>
                <br /></td></tr></table>  简介:<br>
                       <%=article.getContent()%><br>
				  <img src="<%=big%>"  hspace="5" />
				<br /></td>
                  
                      </tr>
        
    
        <tr>
        <td align="left" valign="top"><img src="images/digtal1.1_16.jpg" width="727" height="24" /></td>
      </tr>
    </table>
    <td width="13">&nbsp;</td>
  </tr>
</table><%//@ include file="/sites/www_chinabuy360_cn/include/low.shtml"%>
</body>
</html>

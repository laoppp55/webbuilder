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
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page import="com.bizwink.cms.register.Register" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
     int siteid= ParamUtil.getIntParameter(request,"siid",-1);
     int cid=ParamUtil.getIntParameter(request,"cid",-1);
      int samsiteid=ParamUtil.getIntParameter(request,"said",-1);
    ISiteInfoManager sitepeer= SiteInfoPeer.getInstance();
    if(siteid==-1||samsiteid==-1)
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
            int count = 0;
    int zongpage = 0;
    int recordcount = 10;
    int ipage = 0;

    count = chinabuymgr.count(cid);
            System.out.println("count="+count);
    String dpage = request.getParameter("dpage");
    zongpage = (count + recordcount - 1) / recordcount;
    try {
        ipage = Integer.parseInt(dpage);
    } catch (Exception e) {
        ipage = 1;
    }

    if (ipage <= 0) ipage = 1;
    if (ipage >= zongpage) ipage = zongpage;
    //  System.out.println("ipage----------------"+ipage);
    //   System.out.println("..................."+zongpage);
    List list = chinabuymgr.page(ipage, cid);
    // list = iarticle.listPage(ipage,id);
    // Iterator iterator = list.iterator();


             

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







             <%}%>
                  <td width="5">&nbsp;</td>
            </tr>
          </table>
            <%
    if (zongpage > 1) {
%>
总页数:<%=zongpage%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 当前页：<%=ipage + "/" + zongpage%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="?dpage=1&cid=<%=cid%>" class="lian1">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
        href="?dpage=<%=ipage-1%>&cid=<%=cid%>" class="lian1">上一页 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
        href="?dpage=<%=ipage+1%>&cid=<%=cid%>" class="lian1">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="?dpage=<%=zongpage%>&cid=<%=cid%>" class="lian1">尾页 </a>
<%}%>
        </td>
      </tr>
	  <tr>
        <td align="left" valign="top"><img src="images/digtal1.1_16.jpg" width="727" height="24" /></td>
      </tr>
    </table>

    <td width="13">&nbsp;</td>
  </tr>
</table><%@ include file="/sites/www_chinabuy360_cn/include/low.shtml"%>
</body>
</html>

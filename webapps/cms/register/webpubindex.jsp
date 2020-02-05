<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%
    int current_siteid = 0;
    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
    List enterprise_siteList = new ArrayList();
    List ec_siteList = new ArrayList();
    List personal_siteList = new ArrayList();
    try {
        enterprise_siteList = siteMgr.getTop8SiteInfo(0,current_siteid);
        ec_siteList = siteMgr.getTop8SiteInfo(1,current_siteid);
        personal_siteList = siteMgr.getTop8SiteInfo(2,current_siteid);
    } catch (Exception exp) {
        exp.printStackTrace();
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>网站</title>
    <link href="coositecss.css" rel="stylesheet" type="text/css" />
    <script language="Javascript">
        function openwin(sitename)
        {
            window.open("http://" + sitename);
        }
    </script>
</head>

<body>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="25">&nbsp;</td>
        <td width="223" align="left" valign="top"><img src="images/logo_331.jpg" width="217" height="84" vspace="10" /></td>
        <td width="261" align="left" valign="top"><img src="images/Preview_331.jpg" width="261" height="152" /></td>
        <td width="491" align="left" valign="top"><table width="465" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="20" height="30">&nbsp;</td>
                <td width="435">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="right" valign="middle"><a href="#" class="inde"><br />
                    设为首页 |</a><a href="#" class="inde"> 加为收藏 &nbsp;</a><a href="#"> </a></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="left" valign="top">
                    <table>
                    <tr><td><a href="index.jsp"><img src="images/coosite_14.gif"  border="0" /></a></td>
                        <td><a href="/webbuilder/register/about.html"><img src="images/coosite_15.gif"  border="0"/></a></td>
                        <td><a href="/webbuilder/joincompany/login.jsp"><img src="images/coosite_16.gif" border="0"/></a></td>
                        <td><a href="/webbuilder/register/webpubindex.jsp"><img src="images/coosite_17.gif" border="0"/></a></td>
                         <td><a href="#"><img src="images/coosite_18.gif" border="0"/></a></td>
                          <td><a href="/webbuilder/register/gettouch.html"><img src="images/coosite_19.gif" border="0"/></a></td>
                        </tr>
                    </table>
                 </td>
            </tr>
        </table></td>
    </tr>
</table>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
    <td height="10"></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
<td width="25" align="left" valign="top">	</td>
<td width="178" align="left" valign="top">
    <table width="178" border="0" cellpadding="0" cellspacing="0" background="images/product-index_17.gif">
        <tr>
            <td width="178" align="left" valign="middle"><img src="images/product-index_03.gif" width="178" height="34" /></td>
        </tr>
        <tr>
            <td height="34" align="left" valign="middle" background="images/product-index_02.gif">
                <ul>
                    <li class="li_product"><a href="webpublist.jsp?type=0">企业网站</a></li>
                </ul>
            </td>
        </tr>
        <tr>
            <td height="34" align="left" valign="middle" background="images/product-index_02.gif">
                <ul>
                    <li class="li_product"><a href="webpublist.jsp?type=1">电子商务</a></li>
                </ul>
            </td>
        </tr>
        <tr>
            <td height="34" align="left" valign="middle" background="images/product-index_02.gif">
                <ul>
                    <li class="li_product"><a href="webpublist.jsp?type=2">个人网站</a></li>
                </ul>
            </td>
        </tr>
        <tr>
            <td height="100" align="left">
            </td>
        </tr>
    </table>
</td>
<td width="19" align="left" valign="top">	</td>
<td width="761" align="left" valign="top">
<table width="761" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td align="left" valign="top"><img src="images/product-index_05.gif" width="761" height="8" /></td>
</tr>
<tr>
    <td align="left" valign="top" background="images/product-index_20.gif">
        <table width="761" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="10"></td>
                <td width="735"></td>
            </tr>
            <tr>
                <td width="27">&nbsp;</td>
                <td width="735" align="left" valign="middle"><img src="images/product-index_09.gif" width="9" height="11" align="absmiddle" /> <span class="blue_font">企业网站</span></td>
            </tr>
            <tr>
                <td height="16"></td>
                <td width="735"></td>
            </tr>
        </table>
        <table width="761" border="0" cellspacing="0" cellpadding="0">
            <%
                SiteInfo siteInfo = null;
                for (int i=0; i<2; i++)
                {
                    out.println("<tr><td width=\"25\">&nbsp;</td>");
                    for(int j=0;j<4; j++) {
                        siteInfo = (SiteInfo)enterprise_siteList.get(i*4+j);
                        int siteid = siteInfo.getSiteid();
                        String domainname = siteInfo.getDomainName();
                        String sitepic = siteInfo.getDomainPic();
                        //System.out.println("sitepic=" + sitepic);
            %>
            <td width="160" align="center" valign="top"><a href="javascript:openwin('<%=domainname%>')"><img src="../sitespic/<%=(sitepic!=null)?sitepic:"product_331.jpg"%>" width="160" height="123" border="0" /></a><br />
                <br />
                &nbsp;&nbsp; <br><a href="javascript:openwin('<%=domainname%>')"><%=domainname%></a><br /></td>
            <td width="22">&nbsp;</td>
            <%}%>
            <td width="26">&nbsp;</td></tr>
            <tr>
                <td width="25" height="10"></td>
                <td width="160"></td>
                <td width="22"></td>
                <td width="160"></td>
                <td width="22"></td>
                <td width="160"></td>
                <td width="22"></td>
                <td width="160"></td>
                <td width="26"></td>
            </tr>
            <%}%>
        </table>
    </td>
</tr>
<tr>
    <td align="left" valign="top"><img src="images/product-index_22.gif" width="761" height="7" /></td>
</tr>
<% if (ec_siteList.size() > 0) {%>
<tr>
    <td align="left" valign="top" background="images/product-index_20.gif">
        <table width="761" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="10"></td>
                <td width="735"></td>
            </tr>
            <tr>
                <td width="27">&nbsp;</td>
                <td width="735" align="left" valign="middle"><img src="images/product-index_09.gif" width="9" height="11" align="absmiddle" /> <span class="blue_font">电子商务网站</span></td>
            </tr>
            <tr>
                <td height="16"></td>
                <td width="735"></td>
            </tr>
        </table>
        <table width="761" border="0" cellspacing="0" cellpadding="0">
            <%
                siteInfo = null;
                for (int i=0; i<2; i++)
                {
                    out.println("<tr><td width=\"25\">&nbsp;</td>");
                    for(int j=0;j<4; j++) {
                        siteInfo = (SiteInfo)enterprise_siteList.get(i*4+j);
                        int siteid = siteInfo.getSiteid();
                        String domainname = siteInfo.getDomainName();
                        String sitepic = siteInfo.getDomainPic();
                        //System.out.println("sitepic=" + sitepic);
            %>
            <td width="160" align="center" valign="top"><a href="javascript:openwin('<%=domainname%>')"><img src="../sitespic/<%=(sitepic!=null)?sitepic:"product_331.jpg"%>" width="160" height="123" border="0" /></a><br />
                <br />
                &nbsp;&nbsp; <br><a href="javascript:openwin('<%=domainname%>')"><%=domainname%></a><br /></td>
            <td width="22">&nbsp;</td>
            <%}%>
            <td width="26">&nbsp;</td></tr>
            <tr>
                <td width="25" height="10"></td>
                <td width="160"></td>
                <td width="22"></td>
                <td width="160"></td>
                <td width="22"></td>
                <td width="160"></td>
                <td width="22"></td>
                <td width="160"></td>
                <td width="26"></td>
            </tr>
            <%}%>
        </table>
    </td>
</tr>
<tr>
    <td align="left" valign="top"><img src="images/product-index_22.gif" width="761" height="7" /></td>
</tr>
<%}%>
<% if (personal_siteList.size() > 0) {%>
<tr>
    <td align="left" valign="top" background="images/product-index_20.gif">
        <table width="761" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="10"></td>
                <td width="735"></td>
            </tr>
            <tr>
                <td width="27">&nbsp;</td>
                <td width="735" align="left" valign="middle"><img src="images/product-index_09.gif" width="9" height="11" align="absmiddle" /> <span class="blue_font">个人网站</span></td>
            </tr>
            <tr>
                <td height="16"></td>
                <td width="735"></td>
            </tr>
        </table>
        <table width="761" border="0" cellspacing="0" cellpadding="0">
            <%
                siteInfo = null;
                for (int i=0; i<2; i++)
                {
                    out.println("<tr><td width=\"25\">&nbsp;</td>");
                    for(int j=0;j<4; j++) {
                        siteInfo = (SiteInfo)enterprise_siteList.get(i*4+j);
                        int siteid = siteInfo.getSiteid();
                        String domainname = siteInfo.getDomainName();
                        String sitepic = siteInfo.getDomainPic();
                        //System.out.println("sitepic=" + sitepic);
            %>
            <td width="160" align="center" valign="top"><a href="javascript:openwin('<%=domainname%>')"><img src="../sitespic/<%=(sitepic!=null)?sitepic:"product_331.jpg"%>" width="160" height="123" border="0" /></a><br />
                <br />
                &nbsp;&nbsp; <br><a href="javascript:openwin('<%=domainname%>')"><%=domainname%></a><br /></td>
            <td width="22">&nbsp;</td>
            <%}%>
            <td width="26">&nbsp;</td></tr>
            <tr>
                <td width="25" height="10"></td>
                <td width="160"></td>
                <td width="22"></td>
                <td width="160"></td>
                <td width="22"></td>
                <td width="160"></td>
                <td width="22"></td>
                <td width="160"></td>
                <td width="26"></td>
            </tr>
            <%}%>
        </table>
    </td>
</tr>
<%}%>
</table>
</td>
<td width="17" align="left" valign="top"></td>
</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td height="30" align="center" valign="middle"></td>
    </tr>
    <tr>
        <td height="1" bgcolor="#EBEBEB"></td>
    </tr>
    <tr>
        <td height="50" align="center" valign="middle">版权所有  &nbsp;&nbsp;北京盈商动力软件开发有限公司</td>
    </tr>
</table>
</body>
</html>

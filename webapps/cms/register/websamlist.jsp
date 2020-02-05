<%@ page import="com.bizwink.cms.server.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%
    int current_siteid = 0;
    int resultnum = ParamUtil.getIntParameter(request, "resultnum", 12);
    int startnum = ParamUtil.getIntParameter(request, "startnum", 0);
    int searchflag = ParamUtil.getIntParameter(request, "searchflag", -1);
    int sitetype = ParamUtil.getIntParameter(request, "type", 0);
    String search = ParamUtil.getParameter(request, "search");
    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
    List siteList = new ArrayList();
    int totals = 0;
    int extra_iteams = 0;
    int rows_of_page = 0;
    int totalpages = 0;
    int currentpage = 0;

    if(searchflag == -1){
        totals = siteMgr.getAllSiteInfoNum(sitetype,current_siteid);
        siteList = siteMgr.getAllSiteInfo(resultnum,startnum,sitetype,current_siteid);
    }else{
        totals = siteMgr.getAllSearchSiteInfoNum(search,sitetype,current_siteid);
        siteList = siteMgr.getAllSearchSiteInfo(resultnum,startnum,search,sitetype,current_siteid);
    }
    extra_iteams = siteList.size()%4;
    rows_of_page = siteList.size()/4;

    if(totals < resultnum){
        totalpages = 1;
        currentpage = 1;
    }else{
        if(totals%resultnum == 0)
            totalpages = totals/resultnum;
        else
            totalpages = totals/resultnum + 1;

        currentpage = startnum/resultnum + 1;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>选择网站模板</title>
    <link href="coositecss.css" rel="stylesheet" type="text/css" />
    <script language="Javascript">
        function openwin(sitename)
        {
            window.open("http://" + sitename);
        }

        function select_the_template(form,domainname,samsitetype) {
            var value=0;
            for(var i=0; i<form.site_radio.length; i++) {
                if (form.site_radio[i].checked) {
                    value = form.site_radio[i].value;
                    break;
                }
            }

            if (value==0) {
                alert("请选择一套网站模板");
                return false;
            } else {
                opener.document.regform.samsitename.value = domainname;
                opener.document.regform.samsiteid.value = value;
                opener.document.regform.samsitetemplate.value = 0;
                window.close();
            }
        }

        //共享摸版打开选择号码
        function sharetelenum(id)
        {
            window.open("../register/sharetempnum.jsp?id="+id,"");
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
                    <a href="index.jsp"><img src="images/coosite_14.gif" width="48" height="17" border="0" /></a><img src="images/coosite_15.gif" width="80" height="17" /><img src="images/coosite_16.gif" width="80" height="17" /><a href="webpubindex.jsp"><img src="images/coosite_17.gif" border="0" /></a><img src="images/coosite_18.gif" width="81" height="17" /><img src="images/coosite_19.gif" width="78" height="17" />
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
                    <li class="li_product"><%=(sitetype==0)?"企业网站":"<a href=\"websamlist.jsp?type=0\">企业网站</a>"%></li>
                </ul>
            </td>
        </tr>
        <tr>
            <td height="34" align="left" valign="middle" background="images/product-index_02.gif">
                <ul>
                    <li class="li_product"><%=(sitetype==1)?"电子商务":"<a href=\"websamlist.jsp?type=1\">电子商务</a>"%></li>
                </ul>
            </td>
        </tr>
        <tr>
            <td height="34" align="left" valign="middle" background="images/product-index_02.gif">
                <ul>
                    <li class="li_product"><%=(sitetype==2)?"个人网站":"<a href=\"websamlist.jsp?type=2\">个人网站</a>"%></li>
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
<form name="selmodel">
<input type="hidden" value="0" name="sharetemnum">
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
                    <td width="735" align="left" valign="middle"><img src="images/product-index_09.gif" width="9" height="11" align="absmiddle" />
                    <span class="blue_font">
                    <% if (sitetype == 0)
                        out.println("企业网站");
                    else if (sitetype == 1)
                        out.println("电子商务网站");
                    else
                        out.println("个人网站");
                    %>
                    </span></td>
                </tr>
                <tr>
                    <td height="16"></td>
                    <td width="735"></td>
                </tr>
            </table>
            <table width="761" border="0" cellspacing="0" cellpadding="0">
                <%
                    SiteInfo siteInfo = null;
                    int row=0;
                    for (row=0; row<rows_of_page; row++)
                    {
                        out.println("<tr><td width=\"25\">&nbsp;</td>");
                        for(int col=0;col<4; col++) {
                            siteInfo = (SiteInfo)siteList.get(row*4+col);
                            int siteid = siteInfo.getSiteid();
                            String domainname = siteInfo.getDomainName();
                            String sitepic = siteInfo.getDomainPic();
                            int samsitetype = siteInfo.getValidFlag();
                            //System.out.println("sitepic=" + sitepic);
                %>
                <td width="160" align="center" valign="top"><a href="javascript:openwin('<%=domainname%>')"><img src="../sitespic/<%=(sitepic!=null)?sitepic:"product_331.jpg"%>" width="160" height="123" border="0" /></a><br />
                    <br />
                    <%if (samsitetype == 2){%>
                     <input type="radio" name="site_radio" onclick="javascript:select_the_template(selmodel,'<%=domainname%>',<%=samsitetype%>)" value=<%=siteid%> />NO.<%=siteid%>
                    &nbsp;&nbsp; <br><a href="javascript:openwin('<%=domainname%>')">
                    <%}else{%>
                    <input type="radio" name="site_radio" onclick="javascript:select_the_template(selmodel,'<%=domainname%>',<%=samsitetype%>)" value=<%=siteid%> />NO.<%=siteid%>
                    &nbsp;&nbsp; <br><a href="javascript:openwin('<%=domainname%>')">
                    <% }
                        if (samsitetype == 0)
                          out.println("<font color='red'>" + domainname +"</font>");
                        else if (samsitetype == 1)
                          out.println(domainname);
                        else
                          out.println("<font color='yellow'>" + domainname +"(共享)</font>");

                    %></a><br />
                    <!--input type="image" src="images/product-index_15.gif" width="83" height="24" vspace="7" border="0" /--></td>
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
                <!--有一行的站点数目小于4-->
                <% if (extra_iteams>0) {
                    out.println("<tr><td width=\"25\">&nbsp;</td>");
                    for(int col=0;col<extra_iteams; col++) {
                        siteInfo = (SiteInfo)siteList.get(row*4+col);
                        int siteid = siteInfo.getSiteid();
                        String domainname = siteInfo.getDomainName();
                        String sitepic = siteInfo.getDomainPic();
                        //System.out.println("sitepic=" + sitepic);
                %>
                <td width="160" align="center" valign="top"><a href="javascript:openwin('<%=domainname%>')"><img src="../sitespic/<%=(sitepic!=null)?sitepic:"product_331.jpg"%>" width="160" height="123" border="0" /></a><br />
                    <br />
                    <input type="radio" name="site_radio" onclick="javascript:select_the_template(selmodel,'<%=domainname%>')" value=<%=siteid%> />NO.<%=siteid%>
                    &nbsp;&nbsp; <br><a href="javascript:openwin('<%=domainname%>')"><%=domainname%></a><br />
                    <!--input type="image" src="images/product-index_15.gif" width="83" height="24" vspace="7" border="0" /--></td>
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
</form>
</table>
</td>
<td width="17" align="left" valign="top"></td>
</tr>
</table>
<center>
    <TABLE>
        <TBODY>
            <TR>
                <TD>总共<%=totalpages%>页&nbsp;&nbsp; 共<%=totals%>条&nbsp;&nbsp; 当前第<%=currentpage%>页&nbsp;
                    <%
                        if(startnum>0){
                    %>
                    <a href="websamlist.jsp?type=<%=sitetype%>&startnum=0&searchflag=<%=searchflag%>&search=<%=search%>">第一页</a>
                    <%}%>
                    <%if((startnum-resultnum)>=0){%>
                    <a href="websamlist.jsp?type=<%=sitetype%>&startnum=<%=startnum-resultnum%>&searchflag=<%=searchflag%>&search=<%=search%>">上一页</a>
                    <%}%>
                    <%if((startnum+resultnum)<totals){%>
                    <A href="websamlist.jsp?type=<%=sitetype%>&startnum=<%=startnum+resultnum%>&searchflag=<%=searchflag%>&search=<%=search%>">下一页</A>
                    <%}%>
                    <%if(currentpage != totalpages){%>
                    <A href="websamlist.jsp?type=<%=sitetype%>&startnum=<%=(totalpages-1)*resultnum%>&searchflag=<%=searchflag%>&search=<%=search%>">最后一页</A>
                    <%}%>
                </TD>
                <TD>&nbsp;</TD>
            </TR></TBODY></TABLE><br>
    <table>
        <form name=searchform method=post action="weblist.jsp">
            <input type="hidden" name="searchflag" value="1">
            <input type="hidden" name="type" value="<%=sitetype%>">
            <tr>
                <td>
                    站点名：<input type="text" name="search">
                </td>
                <td>
                    <input type="submit" name="sbutton" value="搜索">
                </td>
            </tr>
        </form>
    </table>
</center>
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

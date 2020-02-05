<%@ page contentType="text/html;charset=gbk" import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.joincompany.IJoincompanyManager" %>
<%@ page import="com.bizwink.joincompany.JoincompanyPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.joincompany.Joincompany" %>
<%@ page import="com.bizwink.cms.register.Register" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%
    int flag = ParamUtil.getIntParameter(request, "flag", -1);
    IJoincompanyManager apeer = JoincompanyPeer.getInstance();
     if(flag==2)
     {
         String userid=ParamUtil.getParameter(request,"name");
         apeer.updateMembers(userid);
     }
    int count = 0;
    int zongpage = 0;
    int recordcount = 20;
    int ipage = 0;
    List list=null;
     Joincompany joins=(Joincompany)session.getAttribute("join");
    if(joins==null)
    {
        response.sendRedirect("login.jsp");
        return;
    }
     String joinname=ParamUtil.getParameter(request,"joinname");

        count = apeer.getCMSMembersCount(ipage,joins.getId(),0);
        String dpage = request.getParameter("dpage");
        zongpage = (count + recordcount - 1) / recordcount;

        try {
            ipage = Integer.parseInt(dpage);
        } catch (Exception e) {
            ipage = 1;
        }
    System.out.println(""+count);
        if (ipage <= 0) ipage = 1;
        if (ipage >= zongpage) ipage = zongpage;
         list = apeer.getCMSMembersPage(ipage,joins.getId(),0);
  

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="coositecss.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" xml:space="preserve">
        function updatepass(userid)
        {
            
            window.open("updatepass.jsp?name="+userid,"","width=270,height=260");
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
		<img src="images/coosite_14.gif" width="48" height="17" /><img src="images/coosite_15.gif" width="80" height="17" /><img src="images/coosite_16.gif" width="80" height="17" /><img src="images/coosite_17.gif" /><img src="images/coosite_18.gif" width="81" height="17" /><img src="images/coosite_19.gif" width="78" height="17" />
		</td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="688" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="688" height="79" align="left" valign="top" background="images/webmessages.gif">&nbsp;	</td>
  </tr>
  <tr>
      <td height="15" background="images/coosite_login_2.gif"></td>
  </tr>
  <tr>
    <td align="center" valign="top" background="images/coosite_login_2.gif">
		<table width="620" border="0" cellpadding="0" cellspacing="1" bgcolor="#E0DFDF">
		  <tr>
			<td width="150" height="30" align="center" valign="middle" bgcolor="#FFFFFF"><strong>用户名</strong></td>
		    <td width="134" align="center" valign="middle" bgcolor="#FFFFFF"><strong>域名</strong></td>
		    <td width="143" align="center" valign="middle" bgcolor="#FFFFFF"><strong>电子邮箱</strong></td>
		    <td width="92" align="center" valign="middle" bgcolor="#FFFFFF"><a href="#" class="linktitle"><strong>密码修改</strong></a></td>
		    <td width="95" align="center" valign="middle" bgcolor="#FFFFFF"><a href="#" class="linktitle"><strong>删除</strong></a></td>
		  </tr>
		  <%
   for(int i=0;i<list.size();i++)
   {
      Register register=(Register)list.get(i);
      List sitelist=apeer.getSiteInfo(register.getSiteID());
       String sitename="";
       for(int j=0;j<sitelist.size();j++)
       {
           SiteInfo site=(SiteInfo)sitelist.get(j);
               sitename=site.getDomainName();
       }

%>
		  <tr>
		    <td height="24" align="center" valign="middle" bgcolor="#FFFFFF"><%=register.getUserID()%></td>
		    <td align="center" valign="middle" bgcolor="#FFFFFF"><%=sitename%></td>
		    <td align="center" valign="middle" bgcolor="#FFFFFF"><%=register.getEmail()%></td>
		    <td align="center" valign="middle" bgcolor="#FFFFFF"><a href="javascript:updatepass('<%=register.getUserID()%>')">密码修改</a> </td>
		    <td align="center" valign="middle" bgcolor="#FFFFFF"><a href="sitlist.jsp?name=<%=register.getUserID()%>&flag=2">删除</a></td>
	      </tr>
		  <%}%>
	  </table>
	</td>
  </tr>
  <tr>
    <td align="left" valign="top"><img src="images/coosite_login_3.gif" width="688" height="64" /></td>
  </tr>
   <% if (zongpage > 1) {

                        %>
                        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td height="20" align="center" valign="bottom" class="blues"><a href="?dpage=1"
                                                                                                class="lian1">第一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                        href="?dpage=<%=ipage-1%>" class="lian1">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                        href="?dpage=<%=ipage+1%>" class="lian1">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                        href="?dpage=<%=zongpage%>" class="lian1"> 最后一页</a></td>
                            </tr>
                        </table>
                        <%}%>
</table>
</body>
</html>

<%@ page import="com.bizwink.joincompany.IJoincompanyManager" %>
<%@ page import="com.bizwink.joincompany.JoincompanyPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.register.Register" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
    IJoincompanyManager jpeer = JoincompanyPeer.getInstance();
    int joincompanyid = ParamUtil.getIntParameter(request, "joincompanyid", -1);
    int count = 0;
    int zongpage = 0;
    int recordcount = 20;
    int ipage = 0;
    List list = null;


    count = jpeer.getCMSMembersCount(ipage, joincompanyid);
    String dpage = request.getParameter("dpage");
    zongpage = (count + recordcount - 1) / recordcount;

    try {
        ipage = Integer.parseInt(dpage);
    } catch (Exception e) {
        ipage = 1;
    }

    if (ipage <= 0) ipage = 1;
    if (ipage >= zongpage) ipage = zongpage;
    list = jpeer.getCMSMembersPage(ipage, joincompanyid);


%>
<style type="text/css">
    TABLE {
        FONT-SIZE: 12px;
        word-break: break-all
    }

    BODY {
        FONT-SIZE: 12px;
        margin-top: 0px;
        margin-bottom: 0px;
        line-height: 20px;
    }

    .TITLE {
        FONT-SIZE: 16px;
        text-align: center;
        color: #FF0000;
        font-weight: bold;
        line-height: 30px;
    }

    .FONT01 {
        FONT-SIZE: 12px;
        color: #FFFFFF;
        line-height: 20px;
    }

    .FONT02 {
        FONT-SIZE: 12px;
        color: #D04407;
        font-weight: bold;
        line-height: 20px;
    }

    .FONT03 {
        FONT-SIZE: 14px;
        color: #000000;
        line-height: 25px;
    }

    A:link {
        text-decoration: none;
        line-height: 20px;
    }

    A:visited {
        text-decoration: none;
        line-height: 20px;
    }

    A:active {
        text-decoration: none;
        line-height: 20px;
        font-weight: bold;
    }

    A:hover {
        text-decoration: none;
        line-height: 20px;
    }

    .pad {
        padding-left: 4px;
        padding-right: 4px;
        padding-top: 2px;
        padding-bottom: 2px;
        line-height: 20px;
    }

    .form {
        border-bottom: #000000 1px solid;
        background-color: #FFFFFF;
        border-left: #000000 1px solid;
        border-right: #000000 1px solid;
        border-top: #000000 1px solid;
        font-size: 9pt;
        font-family: "宋体";
    }

    .botton {
        border-bottom: #000000 1px solid;
        background-color: #F1F1F1;
        border-left: #FFFFFF 1px solid;
        border-right: #333333 1px solid;
        border-top: #FFFFFF 1px solid;
        font-size: 9pt;
        font-family: "宋体";
        height: 20px;
        color: #000000;
        padding-bottom: 1px;
        padding-left: 1px;
        padding-right: 1px;
        padding-top: 1px;
        border-style: ridge
    }
</style>
<center>
    <table width="100%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
        <tr>
            <td>
                <table width="100%" border="0" cellpadding="0">
                    <tr bgcolor="#F4F4F4" align="center">
                        <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">加盟商创建的站点</td>
                    </tr>
                    <tr bgcolor="#d4d4d4" align="right">
                        <td>
                            <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                <tr bgcolor="#FFFFFF" class="css_001">
                                    <td width="7%" align="center">用户名</td>
                                    <td align="center" width="10%">域名</td>

                                    <td align="center" width="20%">email</td>
                                     <td align="center" width="20%">是否被加盟商删除</td>
                                      <td align="center" width="10%">创建时间</td>
                                   
                                </tr>
                                <%
                                    for (int i = 0; i < list.size(); i++) {
                                        Register register = (Register) list.get(i);
                                        List sitelist = jpeer.getSiteInfo(register.getSiteID());
                                        String sitename = "";
                                        String createdate="";
                                        for (int j = 0; j < sitelist.size(); j++) {
                                            SiteInfo site = (SiteInfo) sitelist.get(j);
                                            sitename = site.getDomainName();
                                            createdate=site.getCreatedate();
                                        }
                                        int dflag=register.getDflag();
                                        String dflagstr="";
                                        if(dflag==0)
                                        {
                                            dflagstr="加盟商存在站点";
                                        }
                                        else{
                                             dflagstr="已被加盟商删除";
                                        }

                                %>

                                <tr>
                                    <td height="24" align="center" valign="middle"
                                        bgcolor="#FFFFFF"><%=register.getUserID()%>
                                    </td>
                                    <td align="center" valign="middle" bgcolor="#FFFFFF"><%=sitename%>
                                    </td>

                                    <td align="center" valign="middle" bgcolor="#FFFFFF"><%=register.getEmail()%>
                                    </td>
                                    <td align="center" valign="middle" bgcolor="#FFFFFF"><%=dflagstr%>
                                    </td>
                                     <td align="center" valign="middle" bgcolor="#FFFFFF"><%=createdate%>
                                    </td>
                                </tr>
                                <%}%>
                            </table>

                            <%

                            if (zongpage > 1) {



                            %>
                            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td height="20" align="center" valign="bottom" class="blues"><%=ipage%>
                                        /<%=zongpage%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                            href="?dpage=1&joincompanyid=<%=joincompanyid%>"
                                            class="lian1">第一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                            href="?dpage=<%=ipage-1%>&joincompanyid=<%=joincompanyid%>"
                                            class="lian1">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                            href="?dpage=<%=ipage+1%>&joincompanyid=<%=joincompanyid%>"
                                            class="lian1">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                            href="?dpage=<%=zongpage%>&joincompanyid=<%=joincompanyid%>"
                                            class="lian1"> 最后一页</a></td>
                                </tr>
                            </table>
                            <%}%>
                       

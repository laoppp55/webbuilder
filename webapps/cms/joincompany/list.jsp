<%@ page contentType="text/html;charset=gbk" import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.joincompany.IJoincompanyManager" %>
<%@ page import="com.bizwink.joincompany.JoincompanyPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.joincompany.Joincompany" %>
<%
    int flag = ParamUtil.getIntParameter(request, "flag", -1);
    IJoincompanyManager apeer = JoincompanyPeer.getInstance();
    int count = 0;
    int zongpage = 0;
    int recordcount = 20;
    int ipage = 0;
    List list = null;
    String joinname = ParamUtil.getParameter(request, "joinname");
    if (flag == 1) {

        count = apeer.getSearchCountJoin(joinname);
        String dpage = request.getParameter("dpage");
        zongpage = (count + recordcount - 1) / recordcount;

        try {
            ipage = Integer.parseInt(dpage);
        } catch (Exception e) {
            ipage = 1;
        }

        if (ipage <= 0) ipage = 1;
        if (ipage >= zongpage) ipage = zongpage;
        list = apeer.searchJoin(joinname, ipage);
    } else {

        count = apeer.getCountJoin();
        String dpage = request.getParameter("dpage");
        zongpage = (count + recordcount - 1) / recordcount;

        try {
            ipage = Integer.parseInt(dpage);
        } catch (Exception e) {
            ipage = 1;
        }

        if (ipage <= 0) ipage = 1;
        if (ipage >= zongpage) ipage = zongpage;
        list = apeer.getPageJoin(ipage);
    }

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
<script type="text/javascript">
    function updatepass(joincompanyid,name)
    {

        window.open("houupdate.jsp?id="+joincompanyid+"&name="+name,"","width=270,height=260");
    }
</script>
<center>
    <table width="100%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
        <tr>
            <td>
                <table width="100%" border="0" cellpadding="0">
                    <tr bgcolor="#F4F4F4" align="center">
                        <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">文章评论</td>
                    </tr>
                    <tr bgcolor="#d4d4d4" align="right">
                        <td>
                            <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                <tr bgcolor="#FFFFFF" class="css_001">
                                    <td width="7%" align="center">加盟商登陆ID</td>
                                    <td align="center" width="10%">加盟商姓名</td>
                                    <td align="center" width="5%">联系人</td>
                                    <td align="center" width="10%">联系方式</td>

                                    <td align="center" width="10%">传真</td>
                                    <td align="center" width="20%">企业执照</td>
                                    <td align="center" width="18%">email</td>
                                    <td align="center" width="8%">查看建站</td>
                                    <td align="center" width="6%">重置密码
                                    </td>
                                    <td align="center" width="7%">删除</td>
                                </tr>
                                <%
                                    for (int i = 0; i < list.size(); i++) {
                                        Joincompany join = (Joincompany) list.get(i);

                                %>

                                <tr bgcolor="#FFFFFF" class="css_001">
                                    <td width="7%" align="center"><%=join.getJoinid()%>
                                    </td>
                                    <td align="center" width="10%"><%=join.getJoinname()%>
                                    </td>
                                    <td align="center" width="5%"><%=join.getLianxipeople()%>
                                    </td>
                                    <td align="center" width="10%"><%=join.getPhone()%>
                                    </td>
                                    <td align="center" width="10%"><%=join.getFax()%>
                                    </td>
                                    <td align="center" width="20%"><%=join.getZhizhaonumber()%>
                                    </td>
                                    <td align="center" width="18%"><%=join.getEmail()%>
                                    </td>
                                    <td align="center" width="8%"><a href="list1.jsp?joincompanyid=<%=join.getId()%>" target=_blank>查看建站</a>
                                    </td>
                                    <td align="center" width="6%"><a href="javascript:updatepass(<%=join.getId()%>,<%=join.getJoinid()%>)">重置密码</a>
                                    </td>
                                    <td align="center" width="7%"><a href="delete.jsp?id=<%=join.getId()%>&dpage=<%=ipage%>">删除</a>
                                    </td>
                                </tr>
                                <%}%>
                            </table>
                            <script type="text/javascript">
                                function tijiao()
                                {
                                    document.form.action = "list.jsp";
                                    document.form.submit();
                                }
                            </script>
                            <form method="post" name="form">
                                <input type="hidden" name="flag" value="1">
                                <input type="text" name="joinname">
                                <input type="button" onclick="tijiao()">
                            </form>
                            <%
                            if (zongpage > 1) {


                            %>
                            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td height="20" align="center" valign="bottom" class="blues"><%=ipage%>/<%=zongpage%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                            href="?dpage=1&flag=<%=flag%>&joinname=<%=joinname%>"
                                            class="lian1">第一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                            href="?dpage=<%=ipage-1%>&flag=<%=flag%>&joinname=<%=joinname%>"
                                            class="lian1">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                            href="?dpage=<%=ipage+1%>&flag=<%=flag%>&joinname=<%=joinname%>"
                                            class="lian1">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                                            href="?dpage=<%=zongpage%>&flag=<%=flag%>&joinname=<%=joinname%>"
                                            class="lian1"> 最后一页</a></td>
                                </tr>
                            </table>
<%}%>
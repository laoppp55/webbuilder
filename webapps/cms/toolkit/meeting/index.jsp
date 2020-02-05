<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.ICompanyinfoManager" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.CompanyinfoPeer" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.Meettings" %>
<%@ page contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int success = ParamUtil.getIntParameter(request, "success", 0);
    int siteid = authToken.getSiteID();

    ICompanyinfoManager meetingMgr = CompanyinfoPeer.getInstance();
    List currentlist = new ArrayList();
    String msg;
    switch (success) {
        case 1:
            msg = "培训会添加成功";
            break;
        case 2:
            msg = "培训会删除成功";
            break;
        case 3:
            msg = "培训会修改成功";
            break;
        default:
            msg = "";
            break;
    }

    int rows = 0;
    int totalpages = 0;
    int currentpage = 0;

    currentlist = meetingMgr.getAllmeetings(startrow, range);
    rows = meetingMgr.getAllmeetingsNum();

    if (rows < range) {
        totalpages = 1;
        currentpage = 1;
    } else {
        if (rows % range == 0)
            totalpages = rows / range;
        else
            totalpages = rows / range + 1;

        currentpage = startrow / range + 1;
    }
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>培训会管理</title>
    <style type="text/css">
        <!--
        body {
            margin-top: 0px;
            margin-bottom: 0px;
        }
        -->
    </style>
    <link href="images/css.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        function DeleteMeet(ID)
        {
            var str = confirm("确认要删除会议吗？如果删除，报名参加该会议的报名人员将一并删除！");
            if (str)
                window.location = "deleteMeet.jsp?sid=" + ID;
        }
        function golist(r){
            window.location = "index.jsp?startrow="+r;
        }

    </script>
</head>

<body>
<center>
    <table width="900" border="0" cellpadding="0" cellspacing="0" class="bian">
        <tr>
            <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="50" height="40" align="center"><img src="images/qian_02.jpg" width="30" height="30"/></td>
                                    <td width="100" class="black12c">培训会管理</td>
                                    <td width="550"><a href="baoming.jsp" target="_blank">报名页面</a></td>
                                    <td width="30" align="center"><img src="images/hb_01.jpg" width="11" height="7"/></td>
                                    <td width="100" class="black12c"><a
                                            href="addmeeting.jsp"><b>添加新培训会</b></a></td>
                                    <td width="30" align="center"><img src="images/lv_01.jpg" width="11" height="7"/></td>
                                    <td width="37" class="black12c"><a href="../index.jsp">返回</a></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td height="1" bgcolor="#898898"></td>
                    </tr>
                    <tr>
                        <td>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="50" height="30" align="center" bgcolor="#F6F5F0" class="black12c">编号</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" bgcolor="#F6F5F0" class="black12c">培训会名称</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center" bgcolor="#F6F5F0" class="black12c">培训会时间</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" bgcolor="#F6F5F0" class="black12c">培训会地址</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="120" align="center" bgcolor="#F6F5F0" class="black12c">培训会创建人</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center" bgcolor="#F6F5F0" class="black12c">创建时间</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="150" align="center" bgcolor="#F6F5F0" class="black12c">管理</td>
                                </tr>

                            </table>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <%
                                    if (currentlist != null) {
                                        for (int i = 0; i < currentlist.size(); i++) {
                                            Meettings meetting = (Meettings) currentlist.get(i);
                                            int id = meetting.getID();
                                            String meetingname = meetting.getMeetingname();
                                            Timestamp meetingdatetime = meetting.getMeetingdatetime();
                                            String address = meetting.getAddress();
                                            Timestamp createtime = meetting.getCreatedate();
                                            String editor = meetting.getEditor();

                                %>
                                <tr>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td height="1" bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                </tr>
                                <tr>
                                    <td width="50" height="30" align="center"  class="black12c"><%=i + 1%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center"  class="black12c"><%=StringUtil.iso2gb(meetingname)%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center"  class="black12c"><%=meetingdatetime.toString().substring(0, 10)%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center"  class="black12c"><%=StringUtil.iso2gb(address)%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="120" align="center"  class="black12c"><%=editor%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center"  class="black12c"><%=createtime.toString().substring(0, 19)%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="150" align="center" class="black12">
                                        <a title="删除培训会" href="javascript:DeleteMeet('<%=id%>')">删除</a>
                                        <a title="修改培训会" href="updatemeeting.jsp?id=<%=id%>">修改</a>
                                    </td>
                                </tr>
                                <%
                                        }
                                    }
                                %>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td height="1" bgcolor="#898898"></td>
                    </tr>
                    <tr>
                        <td height="40" align="right" class="black12">
                            共有<%=rows%>条纪录&nbsp;&nbsp;总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <%
                                if ((startrow - range) >= 0) {
                            %>
                            [<a href="index.jsp?startrow=<%=startrow-range%>">上一页</a>]
                            <%}%>
                            <%
                                if ((startrow + range) < rows) {
                            %>
                            [<a href="index.jsp?startrow=<%=startrow+range%>">下一页</a>]
                            <%
                                }
                                if (totalpages > 1) {
                            %>
                            &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
                            <a href="###" onclick="golist((document.all('jump').value-1)*<%=range%>);">GO</a>
                            <%}%>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</center>
</body>
</html>

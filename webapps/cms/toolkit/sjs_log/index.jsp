<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.IArticleManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.news.ArticlePeer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.news.Tender" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    //int siteid = authToken.getSiteID();

    IArticleManager artcileMgr = ArticlePeer.getInstance();
    List currentlist = new ArrayList();
    int searchflag = ParamUtil.getIntParameter(request, "searchflag", 0);
    String searchtime1 = "";
    String searchtime2 = "";

    if (searchflag == 1) {
        searchtime1 = ParamUtil.getParameter(request, "searchtime1");
        searchtime2 = ParamUtil.getParameter(request, "searchtime2");
    }
    currentlist = artcileMgr.getSjs_log(searchtime1,searchtime2);

%>
<html>
<head>
    <title>石景山上报信息统计</title>
    <style type="text/css">
        <!--
        body {
            margin-top: 0px;
            margin-bottom: 0px;
        }
        -->
    </style>
    <link href="../../js/common.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" src="../../js/setday.js"></script>
    <script type="text/javascript">
        function gotosearch() {
            searchForm.action = "index.jsp";
            searchForm.submit();
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
                                    <td width="400" class="black12c" colspan="5">“北京石景山”各单位上报信息数量汇总表</td>
                                    <td width="200" class="black12c" colspan="5">
                                        <%=searchtime1==null?"":searchtime1%>至<%=searchtime2==null?"":searchtime2%>
                                    </td>
                                    <td width="37" class="black12c"><a href="../index.jsp">返回</a></td>
                                </tr>
                            </table>
                            <form action="index.jsp" method="post" name="searchForm">
                                <input type="hidden" name="searchflag" value="1">
                                <table width="100%" border="0" cellpadding="3" cellspacing="1">
                                    <tr bgcolor="#FFFFFF">
                                        <td valign="bottom" class="txt">日期</td>
                                        <td colspan="2" bgcolor="#FFFFFF" class="txt"> 从(开始日期)
                                            <input type="text" size="10" name="searchtime1" onfocus="setday(this)" readonly>
                                            到(结束日期)
                                            <input type="text" size="10" name="searchtime2" onfocus="setday(this)" readonly>

                                            <input type=button value="查询"
                                                   onclick="javascript:gotosearch();">
                                        </td>
                                    </tr>

                                </table>
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td height="1" bgcolor="#898898"></td>
                    </tr>
                    <tr>
                        <td>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="600" height="30" align="center" bgcolor="#F6F5F0" class="black12c">单位名称</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td align="center" bgcolor="#F6F5F0" class="black12c">上报数量（条）</td>
                                    <td width="1" bgcolor="#898898"></td>

                                </tr>

                            </table>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <%
                                    if (currentlist != null) {
                                        for (int i = 0; i < currentlist.size(); i++) {
                                            Tender tender = (Tender) currentlist.get(i);
                                            String maintitle = tender.getMaintitle();
                                            int num = tender.getNum();

                                %>
                                <tr>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>
                                    <td bgcolor="#898898"></td>

                                </tr>
                                <tr>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="600" height="30" align="center"  class="black12c"><%=maintitle==null?"":maintitle%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td align="center"  class="black12c"><%=num%></td>
                                    <td width="1" bgcolor="#898898"></td>

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

                </table>
            </td>
        </tr>
    </table>
</center>
</body>
</html>


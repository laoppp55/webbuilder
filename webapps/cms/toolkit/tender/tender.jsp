<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.news.IArticleManager" %>
<%@ page import="com.bizwink.cms.news.ArticlePeer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.news.Tender" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int articleid = ParamUtil.getIntParameter(request,"articleid",0);
    int num = ParamUtil.getIntParameter(request,"num",0);
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);

    IArticleManager artcileMgr = ArticlePeer.getInstance();
    List currentlist = new ArrayList();
    String msg;

    int rows = 0;
    int totalpages = 0;
    int currentpage = 0;

    currentlist = artcileMgr.getTenderbyid(articleid,startrow, range);
    rows = num;

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
    <title></title>
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
                                    <td width="100" class="black12c">文章标题</td>
                                    <td width="550"></td>
                                    <td width="30" align="center"><img src="images/hb_01.jpg" width="11" height="7"/></td>
                                    <td width="100" class="black12c"></td>
                                    <td width="30" align="center"><img src="images/lv_01.jpg" width="11" height="7"/></td>
                                    <td width="37" class="black12c"><a href="index.jsp">返回</a></td>
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
                                    <td width="200" align="center" bgcolor="#F6F5F0" class="black12c">userid</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center" bgcolor="#F6F5F0" class="black12c">名称</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" bgcolor="#F6F5F0" class="black12c">单位</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="120" align="center" bgcolor="#F6F5F0" class="black12c">下载时间</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center" bgcolor="#F6F5F0" class="black12c"></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="150" align="center" bgcolor="#F6F5F0" class="black12c"></td>
                                </tr>

                            </table>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <%
                                    if (currentlist != null) {
                                        for (int i = 0; i < currentlist.size(); i++) {
                                            Tender tender = (Tender) currentlist.get(i);
                                            int id = tender.getId();
                                            int fileid = tender.getFileid();
                                            int userid = tender.getUserid();
                                            String username = tender.getName();
                                            String depttitle = tender.getDepttitle();
                                            Timestamp createdate = tender.getCreateDate();

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
                                    <td width="200" align="center"  class="black12c"><%=userid%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center"  class="black12c"><%=username%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center"  class="black12c"><%=depttitle%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="120" align="center"  class="black12c"><%=createdate.toString().substring(0, 19)%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center"  class="black12c"></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="150" align="center" class="black12">
                                        XXXXXX
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

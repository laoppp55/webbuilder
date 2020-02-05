<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.ICompanyinfoManager" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.CompanyinfoPeer" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.Meetting_sign_part" %>
<%@ page contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    Long orderid = ParamUtil.getLongParameter(request,"orderid",0);
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int success = ParamUtil.getIntParameter(request, "success", 0);

    ICompanyinfoManager meetingMgr = CompanyinfoPeer.getInstance();
    List currentlist = new ArrayList();


    int rows = 0;
    int totalpages = 0;
    int currentpage = 0;

    currentlist = meetingMgr.getmeeting_sign_part(startrow, range, orderid);
    rows = meetingMgr.getmeetingSignpartNum(orderid);

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
    <title>参会人注册信息管理</title>
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
    <table width="1000" border="0" cellpadding="0" cellspacing="0" class="bian">
        <tr>
            <td valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="50" height="40" align="center"><img src="images/qian_02.jpg" width="30" height="30"/></td>
                                    <td width="200" class="black12c">参会人注册信息管理</td>
                                    <td width="450"></td>
                                    <td width="30" align="center"><img src="images/hb_01.jpg" width="11" height="7"/></td>
                                    <td width="100" class="black12c"></td>
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
                                    <td width="100" height="30" align="center" bgcolor="#F6F5F0" class="black12c">订单号</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" bgcolor="#F6F5F0" class="black12c">培训会主表ID</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center" bgcolor="#F6F5F0" class="black12c">培训人姓名</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" bgcolor="#F6F5F0" class="black12c">部门和职务</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="120" align="center" bgcolor="#F6F5F0" class="black12c">手机号码</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center" bgcolor="#F6F5F0" class="black12c">传真号码</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="150" align="center" bgcolor="#F6F5F0" class="black12c">电子邮件</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" bgcolor="#F6F5F0" class="black12c">培训会时间</td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" bgcolor="#F6F5F0" class="black12c">培训会地址</td>
                                </tr>

                            </table>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <%
                                    if (currentlist != null) {
                                        for (int i = 0; i < currentlist.size(); i++) {
                                            Meetting_sign_part meetting_sign_part = (Meetting_sign_part) currentlist.get(i);
                                            int id = meetting_sign_part.getId();
                                            int signid = meetting_sign_part.getSignid();
                                            //Long orderid = meetting_sign.getOrderid();
                                            String name = meetting_sign_part.getName();
                                            String depttitle = meetting_sign_part.getDepttitle();
                                            String mobilephone = meetting_sign_part.getMobilephone();
                                            String fax = meetting_sign_part.getFax();
                                            String email=meetting_sign_part.getEmail();
                                            Timestamp meettingtime = meetting_sign_part.getMeettingtime();
                                            String meetingaddress= meetting_sign_part.getMeetingaddress();

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
                                    <td width="100" height="30" align="center" class="black12c"><%=orderid%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" class="black12c"><%=signid%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center" class="black12c"><%=name==null?"":name%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" class="black12c"><%=depttitle==null?"":depttitle%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="120" align="center"  class="black12c"><%=mobilephone==null?"":mobilephone%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="100" align="center" class="black12c"><%=fax==null?"":fax%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="150" align="center" class="black12c"><%=email==null?"":email%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" class="black12c"><%=meettingtime.toString().substring(0,10)%></td>
                                    <td width="1" bgcolor="#898898"></td>
                                    <td width="200" align="center" class="black12c"><%=meetingaddress==null?"":meetingaddress%></td>
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



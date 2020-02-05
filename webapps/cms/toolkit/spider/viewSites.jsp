<%@ page import="com.bizwink.collectionmgr.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int basicId = ParamUtil.getIntParameter(request, "basicId", -1);

    IBasic_AttributesManager baMgr = Basic_AttributesPeer.getInstance();
    Basic_Attributes ba = baMgr.getBasic_Attributes(basicId);
    int keywordflag = ba.getKeywordflag();

    GlobalConfig gc;
    List getcolumnsList = baMgr.getColumnNames(basicId);
    int loginflag = ba.getLoginflag();
    int proxyflag = ba.getProxyflag();

    String proxyurl = "";
    String proxyport = "";
    if (proxyflag == 1) {
        gc = baMgr.getProxyConfigOfSite(basicId);
        proxyurl = gc.getProxyName();
        proxyport = gc.getProxyPort();
    }

    String tkeyword = "";
    String bkeyword = "";
    int tbrelation = 2;
    Basic_Attributes keyword;
    if (keywordflag == 1) {
        keyword = baMgr.getKeywordsOfSite(basicId);
        if (keyword != null) {
            tkeyword = keyword.getTKeyword();
            bkeyword = keyword.getBKeyword();
            tbrelation = keyword.getTbRelation();
        }
    } else {
        gc = baMgr.getGlobalKeyword();
        if (gc != null) {
            tkeyword = gc.getTkeyword();
            bkeyword = gc.getBkeyword();
            tbrelation = gc.getTbrelation();
        }
    }
    tkeyword= null==tkeyword?"":tkeyword;
    bkeyword= null==bkeyword?"":bkeyword;
%>
<html>
<head>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="css/manager.css" type=text/css rel=stylesheet>
    <META content="MSHTML 6.00.6000.16525" name=GENERATOR>
    <style type="text/css">
        <!--
        .STYLE1 {
            color: #FF0000
        }

        -->
    </style>
</head>

<body>
<center>
    <table class=tableBorder cellSpacing=1 cellPadding=3 width="95%" align=center border=0>
        <tbody>
            <TR>
                <TD class=title colSpan=6>站点配置</TD>
            </TR>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td width="15%">站点名称<span class="STYLE1">*</span></td>
                <td colspan="5"><%=(ba.getSiteName() == null ? "" : ba.getSiteName())%>
                </td>
            </tr>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td width="86">起始URL<span class="STYLE1">*</span></TD>
                <td colspan="5"><%=ba.getStartUrl() == null ? "" : ba.getStartUrl()%>
                </TD>
            </tr>

            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td width="86">关键词来源</TD>
                <td colspan="5"><%if (ba.getKeywordflag() == 1) {%>自定义关键词<%} else {%>使用全局关键词<%}%>
                </TD>
            </tr>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td>标题关键词</td>
                <td colspan=5><%=tkeyword%></td>
            </tr>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td>正文关键词</td>
                <td colspan=5><%=bkeyword%></td>
            </tr>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td height="20">正文和标题的关系</td>
                <td colspan=5><label>
                    <%
                        if (tbrelation == 2) out.print("或");
                        else if (tbrelation == 1) out.print("与");
                    %>
                </label></td>
            </tr>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td>匹配的栏目<span class="STYLE1">*</span></td>
                <td colspan=5>
                    <%
                        String cnames = "|&nbsp;";
                        for (int i = 0; i < getcolumnsList.size(); i++) {
                            Basic_Attributes bas = (Basic_Attributes) getcolumnsList.get(i);
                            String cname = bas.getCname();
//                            cname = new String(cname.getBytes("ISO8859-1"), "GBK");
                            cnames = cnames + "<font color=blue>" + cname + "</font> |&nbsp;";
                        }
                    %>
                    <%=cnames%>
                </td>
            </tr>
            <%if (loginflag == 1) {%>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td width="15%">登录的URL<span class="STYLE1">*</span></td>
                <td><%=ba.getPosturl() == null ? "" : ba.getPosturl()%>
                </td>
            </tr>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td>传递的参数</td>
                <td><%=ba.getPostdata() == null ? "" : ba.getPostdata()%>
                </td>
            </tr>
            <%
                }
                if (proxyflag == 1) {%>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td width="15%">代理服务器地址<span class="STYLE1">*</span></td>
                <td><%=proxyurl == null ? "" : proxyurl%>
                </td>
            </tr>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td>端口<span class="STYLE1">*</span></td>
                <td><%=proxyport == null ? "" : proxyport%>
                </td>
            </tr>
            <%}%>
            <tr>
                <td class=title5 colspan="6" align="right"><a href="index.jsp">点击这里返回</a></td>
            </tr>
        </tbody>
    </table>
</center>
</body>
</html>
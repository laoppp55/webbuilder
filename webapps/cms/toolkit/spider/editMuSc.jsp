<%@ page import="com.bizwink.collectionmgr.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int basicId=ParamUtil.getIntParameter(request,"basicId",-1);
    IMatchUrl_SpecialCodeManager iMuScMgr = MatchUrl_SpecialCodePeer.getInstance();
    
%>
<head>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="css/manager.css" type=text/css rel=stylesheet>
    <META content="MSHTML 6.00.6000.16525" name=GENERATOR>
</head>

<body>
<TABLE class=tableBorder cellSpacing=1 cellPadding=3 width="95%" align=center border=0>
    <tbody>
        <form name="muScForm" method="post" action="doEditMuSc.jsp"onSubmit="return check();">
            <input name="basicId"type="hidden"value="<%=basicId%>">
            <%
                int sp_id = ParamUtil.getIntParameter(request, "sc_id", -1);
                int mu_id = ParamUtil.getIntParameter(request, "mu_id", -1);
                if (sp_id != -1&&sp_id!=0) {
                    MatchUrl_SpecialCode sc;
                    sc = iMuScMgr.getSc(sp_id);
                    String st=sc.getSt().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                    String et=sc.getEt().replaceAll("<","&lt;").replaceAll(">","&gt;");
            %>
            <input type="hidden"name="flag"value="sc">
            <input type="hidden"name=sc_id value="<%=sp_id%>">
            <tr>
                <td class=title colspan="2">特征码修改</td>
            </tr>

            <tr>
                <td align="right"><strong>起始特征码</strong></td>
                <td><input type="text" name=st id=st size=100 value='<%=st%>'></td>
            </tr>
            <tr>
                <td align="right"><strong>结束特征码</strong></td>
                <td><input type="text" name=et id=et size=100 value='<%=et%>'></td>
            </tr>
            <tr>
                <td colspan=2 align="center"><input name="Submit" type=submit id="Submit1" value="保存">
                &nbsp;&nbsp;<input name="Reset" type=reset id="Reset1" value="重置"></td>
            </tr>
            <tr>
                <td class="title5" colspan=2 align="right"><a href="index.jsp">点击这里返回</a></td>
            </tr>
            <%
            } else if (mu_id != -1&&mu_id!=0) {
                MatchUrl_SpecialCode mu;
                mu=iMuScMgr.getMu(mu_id);
            %>
            <input name=flag type="hidden" value="mu">
            <input name=mu_id type="hidden"value="<%=mu.getMu_id()%>">
            <tr>
                <td class=title colspan=2>修改匹配URL</td>
            </tr>
            <tr>
                <td align="right">URL</td>
                <td><input name=matchUrl id=matchUrl size=100 value="<%=mu.getMatchUrl()%>"></td>
            </tr>
            <tr>
                <td colspan=2 align="center"><input name="Submit" type=submit id="Submit" value="保存">
                <input name="Reset" type=reset id="Reset" value="重置"></td>
            </tr>
            <tr>
                <td class=title5 colspan=2 align=right><a href="index.jsp">点击这里返回</a></td>
            </tr>
            <%

            } else {%>
            <tr>
                <td class=title colspan=2><label>&nbsp;</label></td>
            </tr>
            <tr>
                <td colspan=2 align="center"><strong><font color="red">发生错误！</font></strong></td>
            </tr>
            <tr>
                <td colspan=2 class=title5 align="right"><a href="index.jsp">点击这里返回</a></td>
            </tr>
            <%}%>
        </form>
    </tbody>
</table>
<script type="text/javascript">
    function check(){
        if(muScForm.flag.value=="sc"){
            if(muScForm.st.value==""){
                alert("请输入起始URL！");
                muScForm.st.focus();
                return false;
            }
            if(muScForm.et.focus()){
                alert("请输入结束URL！");
                muScForm.et.focus();
                return false;
            }
        }else if(muScForm.flag=="mu"&&muScForm.matchUrl.value==""){
            alert("请输入匹配的URL：");
            muScForm.matchUrl.focus();
            return false;            
        }
        return true;
    }
</script>
</body>
</html>
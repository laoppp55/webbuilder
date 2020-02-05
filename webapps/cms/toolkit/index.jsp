<%@ page import="com.bizwink.cms.security.*,com.bizwink.cms.util.*,com.bizwink.cms.register.*" contentType="text/html;charset=utf-8"%>
<%@ page import="com.xml.IFormManager" %>
<%@ page import="com.xml.FormPeer" %>
<%@ page import="java.util.List" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String userid = authToken.getUserID();
    String deptid = authToken.getDepartment();
    String baseDir= application.getRealPath("/");
    String password = (String)session.getAttribute("AD_Pass");
    IFormManager formpeer= FormPeer.getInstance();
    String sitename=authToken.getSitename();
    List list=formpeer.getFileXML(baseDir+"\\sites\\"+sitename+"\\_prog");
%>

<html>
<head>
    <title>工具箱服务</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <link rel="stylesheet" href="../design/css/layui.css">
    <link rel="stylesheet" href="../css/mycss.css">
    <link rel="stylesheet" href="../css/cms_css.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <!--引用xtree-->
    <script type="text/javascript" src="../design/layui-xtree.js"></script>
    <script>
        $(document).ready(function(){
            layui.use('element', function(){
                var element = layui.element;
            });
        });
    </script>
</head>

<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><a href="../index1.jsp" class="layui-logo">CMS-工具箱<a></div>
        <jsp:include page="../include/menu.jsp"></jsp:include>
    </div>

    <div class="layui-body" style="left: 0px">
        <!-- 内容主体区域 -->
        <div id="contentid">
            <table class=line width="100%" border=0 cellspacing=0 cellpadding=0>
                <tr bgcolor=#003366><td height=2 colspan=2></td></tr>
                <tr>
                    <td width="50%" class=line><a href="index.jsp">工具箱</a></td>
                    <td width="50%" align=right class=line></td>
                </tr>
                <tr bgcolor=#003366><td colspan=2 height=2></td></tr>
            </table>

            <br>
            <br>
            <br>
            <div align="center">
                <center>
                    <table border="0" width="100%" height="178">
                        <tr>
                            <%if (SecurityCheck.hasPermission(authToken, 54)) {%>
                            <td width="15%" align="center" height="54"><img border="0" src="../images/toolkit/say.gif" width="32" height="32"></td>
                            <td width="15%" align="center" height="54"><img border="0" src="../images/toolkit/say.gif" width="32" height="32"></td>
                            <%} else {%>
                            <td width="15%" align="center" height="54"><img border="0" src="../images/toolkit/say.gif" width="32" height="32"></td>
                            <%}%>
                            <td width="15%" align="center" height="34"><img border="0" src="../images/toolkit/survey.gif" width="32" height="32"></td>
                            <td width="15%" align="center" height="34"><img border="0" src="../images/toolkit/say.gif" width="32" height="32"></td>
                            <td width="15%" align="center" height="34"><img border="0" src="../images/toolkit/mail.gif" width="32" height="32"></td>
                            <td width="15%" align="center" height="34"><img border="0" src="../images/toolkit/0079.GIF" width="32" height="32"></td>
                        </tr>
                        <tr>
                            <%if (SecurityCheck.hasPermission(authToken, 54)) {%>
                            <td width="15%" align="center" height="18"><a href="http://localhost:8080/website?depTId=&userType=1" target="_blank">网站浏览量统计分析</a></td>
                            <!--td width="15%" align="center" height="18"><a href="http://localhost:8080/work?depTId=&userType=1" target="_blank">信息维护量统计分析</a></td-->
                            <td width="15%" align="center" height="18"><a href="editorworking/index.jsp" target="_blank">信息维护量统计分析</a></td>
                            <%} else {%>
                            <td width="15%" align="center" height="18"><a href="http://localhost:8080/work?depTId=<%=deptid%>&userType=0" target="_blank">信息维护量统计分析</a></td>
                            <%}%>
                            <td width="15%" align="center" height="18"><a href="HisArchive/index.jsp">档案管理</a></td>
                            <td width="15%" align="center" height="18"><a href="keywords/index.jsp">关键词库管理</a></td>
                            <td width="15%" align="center" height="18"><a href="survey/index.jsp">网上调查</a></td>
                            <td width="15%" align="center" height="18"><a href="comment/index.jsp">评论管理</a></td>
                        </tr>
                        <%if(!userid.equals("扫黑办")&&!userid.equals("zmhd")){%>
                        <tr>
                            <td width="15%" align="center" height="54"><img border="0" src="../images/toolkit/bbs.gif" width="32" height="32"></td>
                            <td width="15%" align="center" height="54"><img border="0" src="../images/toolkit/say.gif" width="32" height="32"></td>
                            <!--td width="15%" align="center" height="54"><img border="0" src="../images/toolkit/say.gif" width="32" height="32"></td-->
                            <td width="15%" align="center" height="54"><img border="0" src="../images/toolkit/say.gif" width="32" height="32"></td>
                            <td width="15%" align="center" height="54"><img border="0" src="../images/toolkit/bbs.gif" width="32" height="32"></td>
                            <td width="15%" align="center" height="54"><img border="0" src="../images/toolkit/say.gif" width="32" height="32"></td>
                            <td width="15%" align="center" height="54"><img border="0" src="../images/toolkit/say.gif" width="32" height="32"></td>
                        </tr>
                        <tr>
                            <td width="15%" align="center" height="18"><a href="survey/index.jsp" target="_blank">在线调查</a></td>
                            <td width="15%" align="center" height="18"><a href="word/index.jsp"  target="_blank">网站留言</a></td>
                            <!--td width="15%" align="center" height="18"><a href="/webbuilder/toolkit/business/member/index2.jsp"  target="_blank">注册用户管理</a></td-->
                            <td width="15%" align="center" height="18"><a href="sjswsbs/index.jsp"  target="_blank">网上办事后台管理功能</a></td>
                            <td width="15%" align="center" height="18"><a href="sjs_log/index.jsp"  target="_blank">石景山网站群文章录入查询</a></td>
                            <td width="15%" align="center" height="18"><a href="sjs_info/index.jsp"  target="_blank">石景山机构信息公开</a></td>
                            <td width="15%" align="center" height="18"><a href="zmhd/main.jsp">政民互动来信管理</a></td>
                        </tr>
                        <%}else if(userid.equals("扫黑办")){%>

                        <tr>
                            <td width="25%" align="center" height="34"><img border="0" src="../images/toolkit/survey.gif" width="32" height="32"></td>
                        </tr>
                        <tr>
                            <td width="25%" align="center" height="18"><a href="sjsdhjb/index.jsp" target="_blank">打黑举报</a></td>
                        </tr>
                        <%}else{%>

                        <tr>
                            <td width="25%" align="center" height="34"><img border="0" src="../images/toolkit/survey.gif" width="32" height="32"></td>
                        </tr>
                        <tr>
                            <td width="25%" align="center" height="18"><a href="zmhd/main.jsp">政民互动来信管理</a></td>
                        </tr>
                        <%}%>
                    </table>
                </center>
            </div>
        </div>
    </div>
    <jsp:include page="../include/homePageTail.jsp"></jsp:include>
</div>
</body>
</html>

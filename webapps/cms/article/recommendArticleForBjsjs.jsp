<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019-01-05
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    IMarkManager markManager = markPeer.getInstance();
    //获取推荐文章列表标记
    List marks = markManager.getMarksByType(siteid,12);
    mark mark = null;

    int columnid = ParamUtil.getIntParameter(request,"column",0);
    int articleid = ParamUtil.getIntParameter(request,"article",0);
%>
<html>
<head>
    <title>推荐文章</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
</head>
<body>
<%
    for(int ii=0; ii<marks.size(); ii++) {
        mark = (mark)marks.get(ii);
%>
<input type="checkbox" name="recommend" id="recommendID" value="<%=mark.getID()%>"><%=mark.getChineseName()%>
<%}%>
<br/><br/>
<input type="hidden" name="column" value="<%=columnid%>" id="colid">
<input type="hidden" name="article" value="<%=articleid%>" id="artid">
<input type="button" name="tijiao" value="保存" id="save">
<input type="button" name="cancel" value="返回" id="close">
</body>
<script language=javascript>
    $("#save").click(function(){
        /*var recommend_select = $("#recommendID", window.opener.document);
        recommend_select.empty();
        var r=document.getElementsByName("recommend");
        for(var i=0;i<r.length;i++){
            if(r[i].checked){
                recommend_select.append("<option value='a" + r[i].value + "-" + r[i].nextSibling.nodeValue + "'>" + r[i].nextSibling.nodeValue + "</option>");
                window.close();
            }
        }*/
    });

    $("#close").click(function(){
        window.close();
    });
</script>
</html>

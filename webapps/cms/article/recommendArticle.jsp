<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.markManager.mark" %><%--
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
<input type="button" name="tijiao" value="保存" id="save">
<input type="button" name="cancel" value="返回" id="close">
</body>
<script language=javascript>
    $(document).ready(function() {
        var check_item_size = <%=marks.size()%>;
        var recommend_select = window.opener.document.createForm.recommendID;
        //var recommend_select = $("#recommendID", window.opener.document.createForm);
        var r=document.getElementsByName("recommend");
        for (var i = 0; i < recommend_select.length; i++) {
            var valueAndText = recommend_select.options[i].value;         // 选中值
            var posi = valueAndText.indexOf("-");
            var value = valueAndText.substring(0,posi);
            value = value.substring(1);
            for (var j=0; j<check_item_size; j++) {
                if (r[j].value == value){
                    r[j].checked=true;
                    break;
                }
            }
        }
    });

    $("#save").click(function(){
        var recommend_select = $("#recommendID", window.opener.document);
        recommend_select.empty();
        var r=document.getElementsByName("recommend");
        for(var i=0;i<r.length;i++){
            if(r[i].checked){
                recommend_select.append("<option value='a" + r[i].value + "-" + r[i].nextSibling.nodeValue + "'>" + r[i].nextSibling.nodeValue + "</option>");
                window.close();
            }
        }
    });

    $("#close").click(function(){
        window.close();
    });
</script>
</html>

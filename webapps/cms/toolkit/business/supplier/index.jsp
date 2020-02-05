<%@ page import="com.bizwink.cms.security.Auth,
                 com.bizwink.cms.util.SessionUtil"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
    /*if (!SecurityCheck.hasPermission(authToken,54))
 {
   request.setAttribute("message","无管理用户的权限");
   response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
   return;
 }

 int siteid = authToken.getSiteID();
 String username = authToken.getUserID();

 IUserManager userMgr = UserPeer.getInstance();
 List userList = userMgr.getUsers(siteid);
 int userCount = userList.size();*/
%>

<html>
<head>
    <title></title>

</head>
<P align=right><FONT color=#0000ff><A href="../../index.jsp">返回</A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT><br>
    <br>
<table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="90%"
       align=center>
    <tr bgcolor="#eeeeee" class=tine>
        <td align="center" colspan="4">欢迎您进入进销存管理系统</td>
    </tr>

    <tr bgcolor="#ffffff" class=line>
        <td align=center><a href="supplier.jsp">供货商数据</a></td>
        <td align=center><a href="../product/product.jsp">商品数据</a></td>
        <td align="center"><a href="../purchasemaster/purchasemaster.jsp">进货单</a></td>
        <td align="center"><a href="../purchasedetail/purchasedetail.jsp">进货单明细</a></td>
    </tr>

    <tr bgcolor="#ffffff" class=line>
        <td align=center><a href="../changemaster/changemaster.jsp">存货变动单主表</a></td>
        <td align=center><a href="../changedetail/changedetail.jsp">存货变动单明细表</a></td>
        <td align="center"><a href="../deliverymaster/deliverymaster.jsp">出货单主表</a></td>
        <td align="center"><a href="../deliverydetail/deliverydetails.jsp">出货单明细表</a></td>
    </tr>

</table>

</body>
</html>
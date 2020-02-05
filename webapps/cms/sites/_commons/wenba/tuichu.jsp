<%@ page import="com.bizwink.user.User" %>
<%@ page import="com.bizwink.user.IUserManager" %>
<%@ page import="com.bizwink.user.UserPeer" %>
<%@ page contentType="text/html; charset=GBK" %> <script type="text/javascript">var a=s.indexOf()</script>
<%
User ureg = (User) session.getAttribute("user");
 if(ureg!=null){
 session.invalidate();
 out.write(ureg.getUsername());
 return ;
 }
%>
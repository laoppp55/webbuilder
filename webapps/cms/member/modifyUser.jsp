<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"
%>
<%
      Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect( "../login.jsp?url=member/createMember.jsp" );
        return;
      }
      if (!SecurityCheck.hasPermission(authToken, 54))
      {
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
      }

      boolean error = false;
      //int siteid = authToken.getSiteID();
      String  userID      = ParamUtil.getParameter(request,"userid");
      int type = ParamUtil.getIntParameter(request, "type", 0);
      boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
      IUserManager UserMgr = UserPeer.getInstance();
      User user = UserMgr.getUser(userID);
      

      if(doUpdate){
          String  userid = ParamUtil.getParameter(request,"userid");
          String  username = ParamUtil.getParameter(request,"username");
          String  mphone = ParamUtil.getParameter(request,"userphone");
          String  address = ParamUtil.getParameter(request,"useraddress");
          String  email = ParamUtil.getParameter(request,"Email");

          User usertemp = new User();
          usertemp.setID(userid);
          usertemp.setNickName(username);
          usertemp.setPhone(mphone);
          usertemp.setAddress(address);
          usertemp.setEmail(email);

          UserMgr.updateUserInfo(usertemp);
          if (type == 1)
			response.sendRedirect("admin_index.jsp");
		 else
			response.sendRedirect("index.jsp");
		 return;
      
      }
%>
<html>
<head><title>修改用户注册信息</title>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <link rel="stylesheet" type="text/css" href="../style/global.css">
  <script type="text/javascript">
   function check() {
       var username = document.getElementById("username").value;
       if(username == null){
           alert("用户联系人不能为空");
           return false;
       }
       var userphone =  document.getElementById("userphone").value;
       if(userphone != ""){
           var filter = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
           flag = filter.test(userphone);
           if(!flag){
               alert("电话号码输入有误，请重新输入");
               return false;
           }
       }
       var email = document.getElementById("email").value;
       if(email != null ){
           var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                flag = reg.test(companyemail);
                if (!flag) {
                    alert("邮箱格式不正确！");
                    document.getElementById("companyemail").focus();
                    return false;
               }
       }
   }
  </script>
  </head>
<%
        String[][] titlebars = {
            { "用户管理", "" },
            { "修改用户信息", "" }
        };
        String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
  <body>
  <center>
 <table>
 <tr><td colspan="2"><p>
&nbsp;&nbsp;&nbsp;<font class=tine>修改用户 <b><%= userID%></b>&nbsp;的信息</font>
<p> </td></tr>
        <form action="modifyUser.jsp" name="updateForm" onsubmit="javascritp:return check();">
            <input type="hidden" name="doUpdate" value="true">
            <input type="hidden" name="userid" value="<%=userID%>">
            <input type="hidden" name="type" value="<%=type%>">
            &nbsp;&nbsp;
        <tr>
            <td class=tine>用户ID：</td>
            <td class=tine align="left"><%=userID%></td>
        </tr>
        <tr>
            <td height="19" align="left"></td>
            <td align="left"></td>
        </tr>
         <tr>
             <td class="tine">联系人：</td>
             <td><input type="text" name="username" id ="username" size="30" value="<%=user.getNickName()==null?"":user.getNickName()%>" ></td>
         </tr>
         <tr>
            <td height="19" align="left"></td>
            <td align="left"></td>
        </tr>
          <tr>
             <td class="tine">联系电话：</td>
             <td><input type="text" name="userphone" id ="userphone" size="30" value="<%=user.getPhone()==null?"":user.getPhone()%>" ></td>
         </tr>
          <tr>
            <td height="19" align="left"></td>
            <td align="left"></td>
        </tr>
          <tr>
             <td class="tine">联系地址：</td>
             <td><input type="text" name="useraddress"  size="30" value="<%=user.getAddress()==null?"":user.getAddress()%>" ></td>
         </tr>
         <tr>
            <td height="19" align="left"></td>
            <td align="left"></td>
        </tr>
          <tr>
             <td class="tine">电子邮件：</td>
             <td><input type="text" name="Email" id="email" size="30" value="<%=user.getEmail()==null?"":user.getEmail()%>" ></td>
         </tr>
         <tr>
            <td height="19" align="left"></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td>
         <input type="submit" value=" 确定 " onclick="">&nbsp;&nbsp;</td>
            <td>
          <%if (type == 1){%>
         <input type="button" value=" 取消 " onclick="location.href='admin_index.jsp';return false;">
          <%}else{%>
        <input type="button" value=" 取消 " onclick="location.href='index.jsp';return false;">
         <%}%></td>
            </tr>
        </form>
</table>
</center>
</body>
</html>
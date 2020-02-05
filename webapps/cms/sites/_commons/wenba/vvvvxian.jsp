<%@ page import="com.bizwink.user.User" %>
<%@ page import="com.bizwink.user.IUserManager" %>
<%@ page import="com.bizwink.user.UserPeer" %>
<%@ page contentType="text/html; charset=GBK" %> <script type="text/javascript">var a=s.indexOf()</script>
<%
User ureg = (User) session.getAttribute("user");
 if(ureg!=null){
 out.write("登陆成功-"+ ureg.getUsername()+","+ureg.getUsergrade());
 return ;
 
 }

IUserManager userMgr = UserPeer.getInstance();
User cuser = null;
  if((request.getParameter("name")!=null&&request.getParameter("pass")!=null))
   {
   cuser =userMgr.getLogin(request.getParameter("name"),request.getParameter("pass"));
}
String fflag ="";
 
if(cuser!=null){
	if(cuser.getUserflag()==0){
        out.print("您还没有被激活，请到您的邮箱中激活您的用户名!");
		return;
    }else{
		if(cuser.getUserstate()==1){
			
            out.print("对不起,您已经被禁用,暂时不能登录，请尽快与管理员联系!");
			return;
        }else{
			User user = new User();
			if(cuser.getUsername().equals(request.getParameter("name"))){
				user = userMgr.getUserinfo("1-"+request.getParameter("name"));
			}else if(cuser.getUseremail().equals(request.getParameter("name"))){
				user = userMgr.getUserinfo("2-"+request.getParameter("name"));
			}
				session.setAttribute("user",user);
				out.write("登陆成功-"+user.getUsername()+","+user.getUsergrade());
               
			
		}
	}
}else{
out.write("用户名或密码错误");

    
}
%>
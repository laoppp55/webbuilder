<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                java.text.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.bizwink.bbs.bbs.*,
				com.bizwink.webapps.register.*" contentType="text/html;charset=gbk"
%>
<%
  String username       = ParamUtil.getParameter(request, "username");
  String password       = ParamUtil.getParameter(request, "password");
	int startflag         = ParamUtil.getIntParameter(request, "startflag", 0);

	IUregisterManager registerMgr = UregisterPeer.getInstance();
	Uregister register = new Uregister();
	IBBSManager bbsMgr = BBSPeer.getInstance();
  int loginflag = 0;
  if(startflag == 1) {
    boolean existflag = false;

    Uregister ureg = registerMgr.login(username,password,0);
    if(ureg.getMemberid()!= null){
	    register = registerMgr.getAUser(username);
	    int id = register.getID();

      //用户的登录成功计数
      int loginnum = registerMgr.getUserLoginNum(username) + 1;
      registerMgr.updateUserLoginNum(id, loginnum);

      //用户发新帖，更新用户在线登录时间
      Timestamp nowtime = new Timestamp(System.currentTimeMillis());
      String ipaddress = request.getRemoteAddr();
			BBS bbs = new BBS();
      bbs.setUserName(username);
      bbs.setLoginTime(nowtime);
      bbs.setIPAddress(ipaddress);

      //删除该用户在在线表中的记录
      bbsMgr.deleteOnline(bbs);

      //用户登录成功，将新的用户登录时间插入用户在线表
      bbsMgr.insertOnline(bbs);

			register = registerMgr.getAUser(username);
			String grade = register.getGrade();
      session.setAttribute("userid",username);
      session.setAttribute("password",password);
			session.setAttribute("UserClass",grade);
      loginflag = 1;
		}
	}
	response.sendRedirect("/bbs/index.jsp?loginflag="+loginflag);
%></html>    
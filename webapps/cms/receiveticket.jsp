<%@ page contentType="text/html; charset=GB2312"%>
<%@ page import="com.bjca.sso.processor.*,com.bjca.sso.bean.*"%>
<%@ page import="com.bizwink.cms.security.*" %>
<%
	//服务器证书
	String BJCA_SERVER_CERT = request.getParameter("BJCA_SERVER_CERT");
	//票据
	String BJCA_TICKET = request.getParameter("BJCA_TICKET");
    System.out.println("BJCA_TICKET=" + BJCA_TICKET);

    //获取departName
    int posi = BJCA_TICKET.indexOf("<departName>");
    String username = BJCA_TICKET.substring(posi + "<departName>".length());
    posi = username.indexOf("</departName>");
    username = username.substring(0,posi);
    System.out.println("cname="+username);

    //获取userid
    posi = BJCA_TICKET.indexOf("<UserID>");
    String userid = BJCA_TICKET.substring(posi + "<UserID>".length());
    posi = userid.indexOf("</UserID>");
    userid = userid.substring(0,posi);

    //获取部门ID
    posi = BJCA_TICKET.indexOf("<DepartCode>");
    String departid = BJCA_TICKET.substring(posi + "<DepartCode>".length());
    posi = departid.indexOf("</DepartCode>");
    departid = departid.substring(0,posi);
    System.out.println("ename="+departid);

	//票据类型
	String BJCA_TICKET_TYPE = request.getParameter("BJCA_TICKET_TYPE");
	
	//out.println("票据："+BJCA_TICKET);
	
	TicketManager ticketmag = new TicketManager();
	//验证签名和解密
	UserTicket userticket = ticketmag.getTicket(BJCA_TICKET, BJCA_TICKET_TYPE, BJCA_SERVER_CERT);
	//处理票据信息

	if (username!=null && userid !=null && departid!=null) {
	//if(userticket != null) {
		//取领导姓名
		//String username = userticket.getUserName();
		//取用户唯一标识
		//String userid = userticket.getUserUniqueID();
		
		//取部门唯一标识
		//String departid = userticket.getUserDepartCode();

		/**取角色信息
		Hashtable roles = userticket.getUserRoles();
		String s_role = "";
		if(roles != null && roles.size() > 0) {
			int index = 1;
			Enumeration e = roles.keys();
			Enumeration e2 = roles.elements();
			for(;e.hasMoreElements();){
				String rolecode = (String)e.nextElement();
				String rolename = (String)e2.nextElement();
				if(rolename.indexOf("?") != -1) {
					rolename = new String(rolename.getBytes("GBK"),"ISO-8859-1");
				}
				if(index == 1){
					s_role = rolecode;
				}else{
					s_role = s_role + "," + rolecode;
				}
				index++;
			}
		}
		*/
		
		//request.getSession().setAttribute("roles",s_role);
		request.getSession().setAttribute("userid",userid);
		request.getSession().setAttribute("departid",departid);

		/*
		此处是应用系统根据用户唯一标识userid，到自己的数据库中进行查询该用户是否存在于系统中，
		如果存在则让此用户登陆成功，反之跳到错误页面
		userid是通过集成的数据同步，插入到应用系统数据库中的
		*/

		//将通过认证后需要跳转的地址写入下面的变量ticketurl中
		String ticketurl = "";
        IAuthManager authMgr = AuthPeer.getInstance();
        try
        {
            Auth authToken = authMgr.getSjsAuth(userid, "123456");
            if (authToken != null) {
                //更新部门名称
                Department dept = new Department();
                dept.setCname(username);
                dept.setEname(departid);
                IUserManager uMgr = UserPeer.getInstance();
                uMgr.update_departbyUK(dept);
                uMgr.update_membersDepart(departid,userid);

                int siteid = authToken.getSiteID();
                session.setAttribute("CmsAdmin", authToken);
                session.setMaxInactiveInterval(60*60*1000);
                int modelnum = authMgr.getTemplateNum(siteid);
                if (modelnum == 0 && !username.equals("admin"))  {                    //转向模板选择页面
%>
<script type="text/javascript">
    var ret = confirm("选择已经存在的模板？");
    if (ret)
        window.location="register/webindex.jsp";
    else
    <%response.sendRedirect("index1.jsp");%>// window.location="index1.jsp";
</script>
<%
                } else                                                                  //转向登录成功页面
                    ticketurl = "index1.jsp";
            } else {
                ticketurl = "sso_errors.jsp";
            }
        }
        catch (Exception e)
        {
            ticketurl = "sso_errors.jsp";
        }
		//response.sendRedirect(ticketurl);
		request.getRequestDispatcher(ticketurl).forward(request,response);//转向业务系统
	}else{
		response.sendRedirect("sso_errors.jsp");//这里是临时的错误页面，可以修改错误页面
	}
%>
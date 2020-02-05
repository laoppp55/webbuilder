<%@ page contentType="text/html; charset=GB2312"%>
<%@ page import="java.util.*,java.net.*,cn.org.bjca.uams.rest.spi.BjcaRestSdk"%>
<%@ page import="com.bizwink.cms.security.*" %>
<%
   /* request.setCharacterEncoding("utf-8");
    response.setContentType("text/html");
    //票据
    String tokenId = request.getParameter("tokenId");
    System.out.println("tokenId="+tokenId);
    //认证服务器URL
    String amUrl = request.getParameter("amUrl");
    System.out.println("amUrl="+amUrl);
    //随机数
   // String random = request.getParameter("random");
   // System.out.println("random="+random);
    //初始化设置认证服务器URL
    //Map map1 = BjcaRestSdk.getInstance().setServerUrl(amUrl);
    //初始化设置认证服务器URL
    // Map map = BjcaRestSdk.getInstance().setServerUrl(amUrl);
    // Map map = BjcaRestSdk.getInstance().setServerUrl("http://60.24.77.102:9088/am");
    Map map = BjcaRestSdk.getInstance().setServerUrl("http://172.25.7.143:80/am");*/
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html");
    String tokenId = request.getParameter("tokenId");
	System.out.println(tokenId);
//        String random = request.getParameter("random");
    String amUrl = request.getParameter("amUrl");
	amUrl="http://172.26.49.143/am";
    System.out.println(amUrl);
    Map map = BjcaRestSdk.getInstance().setServerUrl(amUrl);
    //产生随机数
    Map map2 = BjcaRestSdk.getInstance().generateRandom(tokenId);
    System.out.println(map2);

    //获取随机数
    String random = (String) map2.get("random");


    //状态码
    String status = map.get("status").toString();
    System.out.println("status1="+status);

//判断初始化状态：0--成功 301--服务器 URL 不能为空！ 305--服务器 URL 未知错误，检查服务器 URL
    if("0".equals(status)){
        //用户属性名称
        //String attributeName = "useridcode";
        //获得用户属性值
        //Map map2 = BjcaRestSdk.getInstance().getUserAttribute(tokenId, attributeName, random);
        //获取用户属性：用户基本信息和扩展信息
        //查询用户信息
        Map map4 = BjcaRestSdk.getInstance().getAllUserAttributes(tokenId, random);
		System.out.println(map4);

        //状态码
        status = map4.get("status").toString();
        System.out.println("status="+status);
        if("[0]".equals(status)){
            //获取用户唯一标识
            String userIdCode = ((List<String>) map4.get("useridcode")).get(0);
            System.out.println(userIdCode);
            //String userid=null;
            IUserManager uMgr = UserPeer.getInstance();
            //String userid = uMgr.getUserByIdCode(userIdCode).getUserID();
			String userid=userIdCode;
			//System.out.println("userid="+userid);
            //String userid = (String)map2.get("cn");
          /*  System.out.println("userid="+userid);
            String departId =  (String)map2.get("orgNumber");
            System.out.println("departId="+departId);
            String departName = (String)map2.get("orgName");
            System.out.println("departName="+departName);*/
            //if (departName!=null && userid !=null && departId!=null) {
            if(userid !=null){
                request.getSession().setAttribute("userid",userid);
               // request.getSession().setAttribute("departid",departId);

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
                     /*   Department dept = new Department();
                        dept.setCname(departName);
                        dept.setEname(departId);

                        uMgr.update_departbyUK(dept);
                        uMgr.update_membersDepart(departId,userid);*/

                        int siteid = authToken.getSiteID();
                        session.setAttribute("CmsAdmin", authToken);
                        session.setMaxInactiveInterval(60*60*1000);
                        int modelnum = authMgr.getTemplateNum(siteid);
                        String username ="null"; //departName;
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
                            //response.sendRedirect("register/webindex.jsp");
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


        }else{
            //101--随机数已失效! 301--未知错误！ 102--验证 Token 失败！ 103--获取用户信息失败！
            //304--系统内部错误！ 306--参数编码错误 307--服务连接错误！ 308--用户认证失败！
            //309--用户状态异常！ 310--请求地址不存在
            response.sendRedirect("sso_errors.jsp");
        }
    }else{
        response.sendRedirect("sso_errors.jsp");
    }
%>
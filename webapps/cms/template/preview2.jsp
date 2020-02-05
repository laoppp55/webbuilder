<%@ page import="java.io.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.webedit.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
      response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
      return;
    }

    String dir1 = ParamUtil.getParameter(request, "dir1");
    String dir2 = ParamUtil.getParameter(request, "dir2");
    String name = ParamUtil.getParameter(request, "name");

    String path = request.getRealPath("/") + "cmstemplates" + File.separator;
    if (dir1 != null)
    {
    	path = path + dir1 + File.separator;
    }
    if (dir2 != null)
    {
    	path = path + dir2 + File.separator;
    }
    if (name != null)
    {
    	path = path + name;
    }
    else
    {
    	out.println("文件数据出错！");
    	return;
    }
    path = StringUtil.iso2gb(path);

    BufferedReader br = new BufferedReader(new FileReader(path));
    String content = "";
    String temp = "";
    while ((temp = br.readLine()) != null)
    {
      content = content + temp + "\n";
    }
    br.close();

    content = StringUtil.gb2iso(content);

    String dir = "";
    if (dir2 != null)
    {
      dir = dir1 + "/" + dir2;
    }
    else
    {
      dir = dir1;
    }
    dir = dir + "/images" + name.substring(name.lastIndexOf(".")-1,name.lastIndexOf("."));

    IWebEditManager webPeer = WebEditPeer.getInstance();
    content = webPeer.convertSrc(content, dir);

    out.println(content);
%>
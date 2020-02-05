<%@ page import="java.util.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.security.*,
                 com.booyee.bbs.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk"
%>
<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if( authToken == null ) {
         response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
         return;
    }

    int doflag = ParamUtil.getIntParameter(request, "doflag", 0);

    IBBSManager bbsMgr = BBSPeer.getInstance();
    BBS bbs = new BBS();
    List list = new ArrayList();
    list = bbsMgr.getAllColumn();

    String bbsname = "";
    String bbsdesc = "";
    String manager = "";
    int id = 0;

    if(doflag == 1){
      id = ParamUtil.getIntParameter(request, "id", 0);
      bbsname = ParamUtil.getParameter(request, "bbsname");
      bbsdesc = ParamUtil.getParameter(request, "bbsdesc");
      manager = ParamUtil.getParameter(request, "manager");

      bbs.setBBSName(bbsname);
      bbs.setBBSDesc(bbsdesc);
      bbs.setManager(manager);
      bbs.setID(id);
      bbsMgr.updateAForum(bbs);

      response.sendRedirect("setManager.jsp");
    }
%>
<html>
<head>
<title>论坛管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>

<body>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=95% align="center">
<tr bgcolor="#eeeeee" class=tine>
<td align=center width="180">论坛名</td>
<td align=center width="604">论坛描述</td>
<td align=center width="97">斑竹</td>
</tr>
<%
  for(int i=0;i<list.size();i++){
    bbs = (BBS)list.get(i);
    id = bbs.getID();
    bbsname = bbs.getBBSName();
    bbsdesc = bbs.getBBSDesc();
    manager = bbs.getManager();
%>
<tr bgcolor="#eeeeee" class=tine>
<td align=center width="180"><a href="setManager.jsp?startflag=1&id=<%=id%>"><%=bbsname%></a></td>
<td align=center width="604"><%=bbsdesc==null?" ":new String(bbsdesc.getBytes("iso8859_1"),"GBK")%></td>
<td align=center width="97"><%=manager%></td>
</tr>
<%}%>
</table>
<%
  int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
  id = ParamUtil.getIntParameter(request, "id", 0);
  if(startflag == 1){
    bbs = bbsMgr.getAFORUM(id);
    bbsname = bbs.getBBSName();
    bbsdesc = bbs.getBBSDesc();
    manager = bbs.getManager();
%>
<form method="POST" action="setManager.jsp">
<input type="hidden" name="doflag" value="1">
<input type="hidden" name="id" value="<%=id%>">
<table align="left" width="95%">
  <tr>
    <td>
      论坛名：&nbsp; <input type="text" name="bbsname" value="<%=bbsname%>" size="51">
    </td>
  </tr>
  <tr>
    <td>
      论坛描述：<input type="text" name="bbsdesc" value="<%=bbsdesc==null?"":new String(bbsdesc.getBytes("iso8859_1"),"GBK")%>" size="106">
    </td>
  </tr>
  <tr>
    <td>
      斑竹：&nbsp;&nbsp;&nbsp; <input type="text" name="manager" value="<%=manager%>" size="50">&nbsp;添加多个斑竹以英文的','分隔
    </td>
  </tr>
  <tr>
    <td>
      <p align="center"><input type="submit" value="提交" name="B1"></p>
    </td>
  </tr>
</table>
</form>
<%}%>
</body>

</html>


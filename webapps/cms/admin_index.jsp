<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.register.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp" );
        return;
    }
    if (authToken.getUserID().compareToIgnoreCase("admin") != 0)
    {
        request.setAttribute("message","无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    int resultnum = ParamUtil.getIntParameter(request, "resultnum", 20);
    int startnum = ParamUtil.getIntParameter(request, "startnum", 0);
    int searchflag = ParamUtil.getIntParameter(request, "searchflag", -1);
    String search = ParamUtil.getParameter(request, "search");
    int namesearch=ParamUtil.getIntParameter(request,"namesearch",-1);
    IRegisterManager registerMgr = RegisterPeer.getInstance();
    IAuditManager auditMgr = AuditPeer.getInstance();
    List userList = new ArrayList();
    int userCount = 0;

    if(searchflag == -1){
        userCount = auditMgr.getUsersCount("");
        userList = auditMgr.getUsers(resultnum,startnum,"");
    }else{
        if(namesearch==1) {
            userCount = auditMgr.getUsersCount(search);
            userList = auditMgr.getUsers(resultnum,startnum,search);
        }
        if(namesearch==2) {
            userCount = auditMgr.getSitenameCount(search);
            userList = auditMgr.getSitename(resultnum,startnum,search);
            out.print(userList.size());
        }
        if(namesearch==3) {
            userCount = auditMgr.getNicknameCount(search);
            userList = auditMgr.getNickname(resultnum,startnum,search);
        }
        if(namesearch==4) {
            userCount = auditMgr.getEmailCount(search);
            userList = auditMgr.getEmail(resultnum,startnum,search);
        }
    }

    //int row = 0;
    int rows = 0;
    int totalpages = 0;
    int currentpage = 0;

    //row = userList.size();
    rows = userCount;

    if(rows < resultnum){
        totalpages = 1;
        currentpage = 1;
    }else{
        if(rows%resultnum == 0)
            totalpages = rows/resultnum;
        else
            totalpages = rows/resultnum + 1;

        currentpage = startnum/resultnum + 1;
    }
%>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script language="javascript">
        function popup_window()
        {
            window.open( "addUser.jsp","","top=100,left=100,width=500,height=450,resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no");
        }

        function edit_window(userid)
        {
            window.open( "addUser.jsp?userid="+userid,"","top=100,left=100,width=500,height=450,resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no");
        }

        function grant_window(userid)
        {
            window.open( "grantRightToUser.jsp?userid="+userid,"","top=100,left=100,width=550,height=450,resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no");
        }
    </script>
</head>
<body>
<%
    String[][] titlebars = {
            { "系统设置", "" },
            { "用户管理", "" }
    };
    String[][] operations = {};
%>
<%@ include file="../inc/titlebar.jsp" %>
<font class=line>用户列表</font>
<table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="100%">
    <tr bgcolor="#eeeeee" class=tine>
        <td width="15%" align=center class="listHeader"><b>用户名称</b></td>
        <td width="15%" align=center>站点名称</td>
        <td width="15%" align=center>联系人</td>
        <td width="20%" align=center>电子邮件</td>
        <td width="15%" align=center>电话</td>
        <td align=center width="10%">密码</td>
        <td align=center width="10%">删除</td>
    </tr>
    <%
        if(namesearch==2) {
            for (int i=0; i<userList.size(); i++) {
                String sitename = "&nbsp;";
                String userID = "";
                String nickName = "";
                String email = "";
                String phone = "";

                Register user = (Register)userList.get(i);
                sitename = user.getSiteName();
                int siteID =user.getSiteID();
                if(user!=null) {
                    User users=  auditMgr.getUserOne( siteID);
                    if(users!=null) {
                        userID = users.getID();
                        nickName = StringUtil.gb2iso4View(users.getNickName());
                        email = users.getEmail();
                        phone = users.getPhone();
                    }
                }
    %>
    <tr bgcolor="#ffffff" class=line>
        <td align=center><b><%=userID%></b></td>
        <td>&nbsp;<%=sitename%></td>
        <td>&nbsp;<%=(nickName==null)?"&nbsp;":nickName%></td>
        <td>&nbsp;<%=(email==null)?"&nbsp;":email%></td>
        <td>&nbsp;<%=(phone==null)?"&nbsp;":phone%></td>
        <td align="center"><input type="radio" name="action" onclick="location.href='editPassword.jsp?type=1&userid=<%=userID%>';"></td>
        <td align="center"><input type="radio" name="action" onclick="location.href='removeUser.jsp?type=1&userid=<%=userID%>';" <%if(userID.equals("admin")){%>disabled<%}%>></td>
    </tr>
    <%    }
    }
    else{
        for (int i=0; i<userList.size(); i++) {
            User user = (User)userList.get(i);
            String userID = user.getID();
            String nickName = StringUtil.gb2iso4View(user.getNickName());  //联系人
            String email = user.getEmail();
            String phone = user.getPhone();
            int siteID = user.getSiteID();
            String sitename = "&nbsp;";

            if (!userID.equalsIgnoreCase("admin") ) {
                Register reg = registerMgr.getSite(siteID);
                if(reg != null) sitename = reg.getSiteName();
            }
    %>
    <tr bgcolor="#ffffff" class=line>
        <td align=center><b><%=userID%></b></td>
        <td>&nbsp;<%=sitename%></td>
        <td>&nbsp;<%=(nickName==null)?"&nbsp;":nickName%></td>
        <td>&nbsp;<%=(email==null)?"&nbsp;":email%></td>
        <td>&nbsp;<%=(phone==null)?"&nbsp;":phone%></td>
        <td align="center"><input type="radio" name="action" onclick="location.href='editPassword.jsp?type=1&userid=<%=userID%>';"></td>
        <td align="center"><input type="radio" name="action" onclick="location.href='removeUser.jsp?type=1&userid=<%=userID%>';" <%if(userID.equals("admin")){%>disabled<%}%>></td>
    </tr>
    <%}
    }%>
</table>
<br>
<center>
    <TABLE>
        <TBODY>
        <TR>
            <TD>总共<%=totalpages%>页&nbsp;&nbsp; 共<%=rows%>条&nbsp;&nbsp; 当前第<%=currentpage%>页&nbsp;
                <%
                    if(startnum>0){
                %>
                <a href="admin_index.jsp?startnum=0&searchflag=<%=searchflag%>&search=<%=search%>&namesearch=<%=namesearch%>">第一页</a>
                <%}%>
                <%if((startnum-resultnum)>=0){%>
                <a href="admin_index.jsp?startnum=<%=startnum-resultnum%>&searchflag=<%=searchflag%>&search=<%=search%>&namesearch=<%=namesearch%>">上一页</a>
                <%}%>
                <%if((startnum+resultnum)<rows){%>
                <A href="admin_index.jsp?startnum=<%=startnum+resultnum%>&searchflag=<%=searchflag%>&search=<%=search%>&namesearch=<%=namesearch%>">下一页</A>
                <%}%>
                <%if(currentpage != totalpages){%>
                <A href="admin_index.jsp?startnum=<%=(totalpages-1)*resultnum%>&searchflag=<%=searchflag%>&search=<%=search%>&namesearch=<%=namesearch%>">最后一页</A>
                <%}%>
            </TD>
            <TD>&nbsp;</TD>
        </TR></TBODY></TABLE><br>
    <table>
        <form name=searchform method=post action="admin_index.jsp">
            <input type="hidden" name="searchflag" value="1">
            <tr>

                <td>
                    <input type="radio" name="namesearch" value="1" <% if(namesearch==1) {%> checked="checked" <%}%> > 用户名：
                    <input type="radio" name="namesearch" value="2" <% if(namesearch==2) {%> checked="checked" <%}%> > 站点名称：
                    <input type="radio" name="namesearch" value="3" <% if(namesearch==3) {%> checked="checked" <%}%> >  联系人：
                    <input type="radio" name="namesearch" value="4" <% if(namesearch==4) {%> checked="checked" <%}%> >  电子邮件：
                    <input type="text" name="search">
                </td>
                <td>
                    <input type="submit" name="sbutton" value="搜索">
                </td>
            </tr>
        </form>
    </table>
</center>
</body>
</html>

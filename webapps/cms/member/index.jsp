<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
%>
<%@ include file="../include/auth.jsp"%>
<%
    //注释文件是程序员必须的工作

    if (!SecurityCheck.hasPermission(authToken,54))
    {
        request.setAttribute("message","无管理用户的权限");
        response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
        return;
    }

    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();

    IUserManager userMgr = UserPeer.getInstance();
    List userList = userMgr.getUsers(siteid);
    int userCount = userList.size();
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <link rel="stylesheet" href="../design/css/layui.css">
    <link rel="stylesheet" href="../css/mycss.css">
    <link rel="stylesheet" href="../css/cms_css.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <!--引用xtree-->
    <script type="text/javascript" src="../design/layui-xtree.js"></script>
    <script language="javascript">
        function createUser()
        {
            window.open("newuser.jsp","","top=100,left=100,width=700,height=700");
        }

        function editUser(userid)
        {
            window.open("editUser.jsp?userid="+userid,"","top=0,left=100,width=650,height=700");
        }

        function pubFlag()
        {
            window.open("pubflag.jsp","","top=200,left=200,width=300,height=150");
        }

        function copySiteFromSampleSite()
        {
            window.open("../register/webindex.jsp","selectmodel");
        }

        function grantUser(userid)
        {
            window.open("grantRightToUserOld.jsp?userid="+userid,"","top=100,left=100,width=420,height=360");
        }

        function programs()
        {
            window.open("programsfortoolkit.jsp","","top=100,left=100,width=900,height=600");
        }

        function orgmanager()
        {
            window.open("../organization/index.jsp","organizationwin","top=0,left=0,width=1300,height=800,resizable=yes,scrollbars=yes");
        }

        function createAttachType()
        {
            var bln = confirm("您确定要定义文章附件的分类吗？");
            if (bln) {
                alert("hello world");
                window.open("createnewtype.jsp", "", "width=600,height=400,left=200,right=200,scrollbars,status");
            }
        }
    </script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><a href="../index1.jsp" class="layui-logo">CMS-用户管理</a></div>
        <jsp:include page="../include/menu.jsp"></jsp:include>
    </div>

    <div id="contentid" style="overflow-scrolling: auto">
        <%
            String[][] titlebars = {
                    { "用户管理", "" }
            };
            String[][] operations = {
                    {"<a href=javascript:createUser();>创建用户</a>", ""},
                    {"组管理", "groupManage.jsp"},
                    {"样式文件", "listStyle.jsp"},
                    {"关键字管理", "keyword.jsp"},
                    {"文章归档", "archive.jsp"},
                    {"文章附件类型","attachTypeManager.jsp"},
                    {"组织架构管理", "../organization/index.jsp"},
                    //{"<a href=javascript:programs();>程序库管理</a>",""},
                    {"<a href=javascript:pubFlag();>发布方式</a>", ""}
            };
        %>
        <%@ include file="../inc/titlebar.jsp" %>
        <br>
        <table border=1 cellPadding=0 cellSpacing=0 width="100%" align=center>
            <tr bgcolor="#eeeeee">
                <td width="15%" align=center class="listHeader"><b>用户名</b></td>
                <td width="15%" align=center>用户姓名</td>
                <td width="20%" align=center>单位</td>
                <!--td width="15%" align=center>角色</td-->
                <td width="10%" align=center>授权管理</td>
                <td width="10%" align=center>属性</td>
                <td width="10%" align=center>组别</td>
                <td width="10%" align=center>密码</td>
                <td width="10%" align=center>删除</td>
            </tr>
            <%
                for (int i=0; i<userCount; i++)
                {
                    User user = (User)userList.get(i);
                    String userID = user.getID();
                    String nickName = StringUtil.gb2iso4View(user.getNickName());
                    String dept = user.getDepartment();
                    String company = user.getCompany();
                    Department d = null;
                    String dname = null;
                    if (dept != null) {
                        if (!dept.isEmpty()) {
                            try {
                                d = userMgr.getOneDepartmentInfoById(Integer.parseInt(dept));
                                if(d != null ) {
                                    dname = d.getCname();
                                    dname = StringUtil.gb2iso4View(dname);
                                }
                            } catch(NumberFormatException exp) {
                                dname = dept;
                            }
                        } else if (company!=null) {
                            if (!company.isEmpty()) dname = company;
                        }
                    } else if (company!=null && company!="") {
                        dname = company;
                    }

            %>
            <tr bgcolor="#ffffff">
                <td align=center><b><%=userID%></b></td>
                <td align=center><%=nickName%></td>
                <td align=center><%=(dname!=null)?dname:"&nbsp;&nbsp;"%></td>
                <!--td align="center"><input type="radio" name="action" onclick='grantUser("<%=userID%>");' <%if(userID.equalsIgnoreCase(username)){%>disabled<%}%>></td>
        <td align="center"><input type="radio" name="action" onclick='editUser("<%=userID%>");' <%if(userID.equalsIgnoreCase(username)){%>disabled<%}%>></td-->
                <td align="center"><input type="radio" name="action" onclick='grantUser("<%=userID%>");'></td>
                <td align="center"><input type="radio" name="action" onclick='editUser("<%=userID%>");'></td>
                <td align="center"><input type="radio" name="action" onclick="location.href='editUserGroup.jsp?userid=<%=userID%>';" <%if(userID.equalsIgnoreCase(username)){%>disabled<%}%>></td>
                <td align="center"><input type="radio" name="action" onclick="location.href='editPassword.jsp?userid=<%=userID%>';"></td>
                <td align="center"><input type="radio" name="action" onclick="location.href='removeUser.jsp?userid=<%=userID%>';" <%if(userID.equalsIgnoreCase(username)){%>disabled<%}%>></td>
            </tr>
            <%}%>
        </table>
    </div>
</div>
<jsp:include page="../include/tail.jsp"></jsp:include>
</div>
</body>
</html>
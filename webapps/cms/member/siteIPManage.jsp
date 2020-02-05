<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect( "../login.jsp" );
        return;
    }
    if (!SecurityCheck.hasPermission(authToken,54)) {
        request.setAttribute("message","无系统管理的权限");
        response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
        return;
    }

    int siteID = ParamUtil.getIntParameter(request, "siteid", 0);

    IFtpSetManager ftpMgr = FtpSetting.getInstance();
    List ftpList = ftpMgr.getFtpInfoList(siteID);
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <link rel="stylesheet" href="../css/mycss.css">
    <link rel="stylesheet" href="../css/cms_css.css">

    <script language="javascript">
        function create() {
            var iWidth=600;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("addsiteipinfo.jsp?siteid=<%=siteID%>","","top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
        }

        function edit(ID) {
            var iWidth=600;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("editipinfo.jsp?ID="+ID,"Edit_Site","top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
        }
    </script>
</head>

<body>
<%
    String[][] titlebars = {
            {"主机管理", "" }
    };
    String [][] operations = {
            {"<a href=javascript:create();>增加主机</a>",""},
            {"<a href=javascript:window.close();>返回</a>",""}
    };
%>
<%@ include file="../inc/titlebar.jsp" %>
<br>
    <table id="articleDataGrid" width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#CCCCCC" class="tab_list">
    <tr>
        <td width="15%" align=center bgcolor="#f4f4f4">主机类型</td>
        <td width="20%" align=center bgcolor="#f4f4f4"><b>主机名称</b></td>
        <td width="20%" align=center bgcolor="#f4f4f4">发布地址</td>
        <td width="25%" align=center bgcolor="#f4f4f4">发布目录</td>
        <td width="10%" align=center bgcolor="#f4f4f4">发布方式</td>
        <td width="5%" align=center bgcolor="#f4f4f4">修改</td>
        <td width="5%" align=center bgcolor="#f4f4f4">删除</td>
    </tr>
    <%
        FtpInfo ftpInfo = null;
        for (int i=0; i<ftpList.size(); i++)
        {
            ftpInfo = (FtpInfo)ftpList.get(i);
            String siteIP = ftpInfo.getIp();
            String docpath = ftpInfo.getDocpath();
            int status = ftpInfo.getStatus();
            int pubway = ftpInfo.getPublishway();
            int ftptype = ftpInfo.getFtptype();
    %>
    <tr bgcolor="#ffffff">
        <td align=center>
            <font color=red><b>
            <%if(status==1){%>WEB发布<%}else if(status == 0){%>图片/多媒体<%} else if (status == 2) {%>WAP发布<%}%></b>
            </font>
        </td>
        <td align=center><b><%=ftpInfo.getSiteName()%></b></td>
        <td align=center><%=siteIP%></td>
        <td><%=docpath%></td>
        <td align=center>
            <%if(pubway==0){
               if (ftptype == 0)
                   out.print("FTP发布");
               else
                   out.print("SFTP发布");
            } else {
                out.print("本地发布");
            }%>
        </td>
        <td align="center"><input type="radio" name="action" onclick="edit(<%=ftpInfo.getID()%>);"></td>
        <td align="center"><input type="radio" name="action" onclick="location.href='removeipinfo.jsp?ID=<%=ftpInfo.getID()%>';"></td>
    </tr>
    <%}%>
</table>

</body>
</html>
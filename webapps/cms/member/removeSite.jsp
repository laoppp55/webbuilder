<%@ page import="java.util.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.security.*" contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "��ϵͳ����Ա��Ȩ��");
        response.sendRedirect("../index.jsp");
        return;
    }

    int siteid = ParamUtil.getIntParameter(request, "siteid", -1);
    String dname = ParamUtil.getParameter(request, "dname");
    boolean doDelete = ParamUtil.getBooleanParameter(request, "doDelete");
    int currentpage = ParamUtil.getIntParameter(request, "pageno", 0);
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int doSearchFlag = ParamUtil.getIntParameter(request, "searchflag", 0);
    int errcode = 0;

    System.out.println("siteid==" + siteid);

    if (doDelete) {
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        try {
            errcode = siteMgr.remove(siteid);
        } catch (SiteInfoException ue) {
            errcode = -1;
        }
    }
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script src="../js/jquery-1.12.4.js" type="text/javascript"></script>
    <script language="javascript">
        $(document).ready(function(){
            var errcode=<%=errcode%>;
            if (errcode === 1) {
                alert("վ��ɾ���ɹ���");
                window.opener.userInfoList(<%=range%>,<%=currentpage%>,<%=doSearchFlag%>);
                window.close();
            } else if (errcode==-1) {
                alert("վ��ɾ��ʧ�ܣ�");
                window.close();
            }
        });
    </script>
</head>
<body>
<%
    //վ���б�
    String[][] titlebars = {
            {"", "siteManage.jsp"},
            {"ɾ��վ��", ""}
    };
    String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p>
    &nbsp;&nbsp;<font color="red" size="2">ȷ��Ҫɾ��վ�� <b><%=dname%></b> ?
</p>
<p>
    &nbsp;&nbsp;�ò�����ɾ�������й�վ�����Ϣ�������£�ģ�壬�û���������Ϣ��<b>�����ؿ���!!!</b></font>
</p>

<form action="removeSite.jsp" name="deleteForm" method="post">
    <input type="hidden" name="doDelete" value="true">
    <input type="hidden" name="siteid" value="<%=siteid%>">
    <input type="hidden" name="dname" value="<%=dname%>">
    <input type="hidden" name="range" value="<%=range%>">
    <input type="hidden" name="pageno" value="<%=currentpage%>">
    <input type="hidden" name="searchflag" value="<%=doSearchFlag%>">
    &nbsp;&nbsp;
    <input type="submit" value=" ȷ�� ">
    &nbsp;&nbsp;&nbsp;
    <input type="button" name="cancel" value=" ȡ�� " style="font-weight:bold;" onclick="javascript:window.close();">
</form>
</body>
</html>

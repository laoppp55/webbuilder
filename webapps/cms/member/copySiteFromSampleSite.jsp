<%@ page import="java.util.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.security.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="com.bizwink.cms.register.IRegisterManager" %>
<%@ page import="com.bizwink.cms.register.RegisterPeer" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp?url=member/removeMember.jsp" );
        return;
    }

    String userID = authToken.getUserID();
    int siteid = authToken.getSiteID();
    String sitename = authToken.getSitename();
    String apppath = request.getRealPath("/");
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int samsiteid  = 0;
    boolean error = false;
    String pass = null;

    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
    List siteList = siteMgr.getAllSiteInfo();

    if (doUpdate)
    {
        pass = ParamUtil.getParameter(request, "pass1");
        samsiteid = ParamUtil.getIntParameter(request,"samsite",0);
        //System.out.println("samsiteid  =" + samsiteid);
        if (samsiteid != 0) {
            IRegisterManager regMgr = RegisterPeer.getInstance();
            boolean retcode = regMgr.copySamSite(samsiteid,siteid,userID,sitename,apppath);
        }
    }
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
</head>

<body>
<%
    String[][] titlebars = {
    };
    String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p class=tine>&nbsp;&nbsp;&nbsp;修改用户 <b><%=userID%></b>&nbsp;的密码<p>

<form action="copySiteFromSampleSite.jsp" name="updateForm" onsubmit="javascript:return CheckPass();">
    <input type="hidden" name="doUpdate" value="true">
    <input type="hidden" name="userid" value="<%=userID%>">
    <font class=tine>
        请选择被拷贝的网站：
        <select name="samsite" size="9" style="width:200;font-size:9pt">
            <%
                for (int i=0; i<siteList.size(); i++) {
                    int sample_siteid = ((SiteInfo)(siteList.get(i))).getSiteid();
                    String sample_sitename =  StringUtil.gb2iso4View(((SiteInfo)(siteList.get(i))).getDomainName());
                    if (sample_siteid != siteid)
                        out.println("<option value=\"" + sample_siteid + "\">" + sample_sitename + "</option>");
                }
            %>
        </select>
        <input type="submit" value="  确定  ">&nbsp;&nbsp;
        <input type="button" name="cancel" value="  取消  " onclick="javascript:window.close()">
    </font>
</form>
<script language="JavaScript">
    function CheckPass()
    {
        if (updateForm.pass1.value == "" || updateForm.pass2.value == "")
        {
            alert("密码不能为空！");
            return false;
        }
        else if (updateForm.pass1.value != updateForm.pass2.value)
        {
            alert("两次输入的密码不相同！");
            return false;
        }
        else
        {
            return true;
        }
    }
</script>

</body>
</html>

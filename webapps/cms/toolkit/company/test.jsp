<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.CompanyinfoPeer" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.ICompanyinfoManager" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.CompanyColumn" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    int siteid = ParamUtil.getIntParameter(request,"siteid", 0);

    ICompanyinfoManager comMgr = CompanyinfoPeer.getInstance();
    List list = new ArrayList();
    list = comMgr.getComColumn(siteid);

%>
<html>
<head>
 <style type="text/css">
        td {
            font-size: 12px;
        }
    </style>
</head>
<script type="text/javascript">
    function go(columnID)
    {
        var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
        if (isMSIE) {
           /* if (justHref.value != "" && parent.frames['menu'].document.all(justHref.value))
                parent.frames['menu'].document.all(justHref.value).className = "line";
            parent.frames['menu'].document.all("href" + columnID).className = "sline";
            justHref.value = "href" + columnID;*/
            parent.frames['cmsright'].location = "Companymain.jsp?id=" + columnID;
            return;
        } else {
            /*if (document.getElementById("justHref").value != "" && window.parent.frames['cmsleft'].document.all("justHref").value)
                window.parent.frames['cmsleft'].document.all("justHref").value.className = "line";
            document.getElementById("justHref").value = "href" + columnID;*/
            window.parent.frames['cmsright'].location = "Companymain.jsp?id=" + columnID;
            return;
        }
    }
</script>
<body>
<table>
    <%
        for(int i = 0 ; i < list.size(); i++){
             CompanyColumn ccolumn = (CompanyColumn)list.get(i);

    %>
    <tr><td><a href="javascript:go(<%=ccolumn.getID()%>);"><%=ccolumn.getCName()%></a></td></tr>
    <%
        }
    %>
</table>
</body>
</html>
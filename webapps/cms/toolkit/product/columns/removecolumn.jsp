<%@ page import="java.sql.*,
    		 com.bizwink.cms.toolkit.companyinfo.*,
    		 com.bizwink.cms.modelManager.*,
    		 com.bizwink.cms.security.*,
		 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if( authToken == null ) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    // get parameters
    int ID   = ParamUtil.getIntParameter(request,"ID", 0);
    int siteid = authToken.getSiteID();
    boolean doDelete = ParamUtil.getBooleanParameter(request,"doDelete");

    boolean noColumnSpecified = (ID == 0);
    boolean errors = (noColumnSpecified);

    ICompanyinfoManager companyManager = CompanyinfoPeer.getInstance();
    companyClass companyType = new companyClass();

    // delete user if specified
    if( doDelete && !errors )
    {
        String message = "";
        try
        {
            //第三步再删除该栏目(将删除该栏目的审核规则及所属文章审核规则)
            companyManager.remove(ID,siteid);

            message = "删除栏目完毕!";
        }
        catch( Exception e )
        {
            System.err.println( e );
            message = "栏目未找到";
        }


        request.setAttribute("message",message);
        response.sendRedirect("index.jsp");
        return;
    }
    companyType = companyManager.getCompanyClass(ID);

%>
<html><head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../css/global.css">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
            { "删除分类信息", "" }
    };
    String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p class=line>
    删除分类信息 <b><%=StringUtil.gb2iso4View(companyType.getCname())%> </b>?
<p>
<ul class=cur>警告: 此操作将永久性的删除该分类及该分类下所属的企业信息，您真的想删除吗?</ul>
<form action="removecolumn.jsp" name="deleteForm">
    <input type=hidden name=doDelete value="true">
    <input type=hidden name=ID value="<%= ID %>">
    <input type=image src=../images/button_dele.gif onclick="document.all.deleteForm.submit()">
    &nbsp;
    <input type=image src=../images/button_cancel.gif onclick="location.href='index.jsp';return false;">
</form>
</body></html>
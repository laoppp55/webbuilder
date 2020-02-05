<%@ page import="java.sql.*,
    com.bizwink.cms.news.*,
    com.bizwink.cms.modelManager.*,
    com.bizwink.cms.security.*,
    com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if( authToken == null ) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=ϵͳ��ʱ�������µ�½!"));
        return;
    }

    // get parameters
    int siteid    = authToken.getSiteID();
    int rightid   = ParamUtil.getIntParameter(request,"rightid", 0);
    int ID        = ParamUtil.getIntParameter(request,"template", 0);
    int columnID  = ParamUtil.getIntParameter(request,"column", 0);
    int modelType = ParamUtil.getIntParameter(request,"isArticle", 0);
    boolean doDelete = ParamUtil.getBooleanParameter(request,"doDelete");

    boolean noTemplateSpecified = (ID == 0);
    boolean errors = (noTemplateSpecified);

    IModelManager  modelManager= ModelPeer.getInstance();
    Model model = modelManager.getModel(ID);
    // delete user if specified
    if( doDelete) {
        try {
            modelManager.Remove(ID,siteid);
        }
        catch( ModelException e ) {
            System.out.println( e.getMessage() );
        }
        //response.sendRedirect(
        //    response.encodeRedirectURL("templates.jsp?column=" + columnID + "&rightid=" + rightid + "&msg=ģ�屻ɾ��!")
        //);
        //System.out.println("../template/templates.jsp?column=" + columnID + "&rightid=" + rightid + "&msg=ģ�屻ɾ��!");
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/template/templates.jsp?column=" + columnID + "&rightid=" + rightid + "&msgno=-5"));
        return;
    }

    columnID = model.getColumnID();
    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String columnName = StringUtil.gb2iso4View(column.getCName());
%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
String[][] titlebars = {
        { "ģ�����", "templatesmain.jsp" },
        { columnName, "templates.jsp?column="+columnID },
        { "ɾ��ģ��", "" }
    };
    String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p class=line>ɾ��ģ��?<p>
<ul class=cur>����: �����������Ե�ɾ����ģ��,�������ɾ����?</ul>
<form action=removetemplate.jsp name="deleteForm">
<input type=hidden name=doDelete value="true">
<input type=hidden name=column value="<%=columnID%>">
<input type=hidden name=column value="<%=rightid%>">
<input type=hidden name=template value="<%= ID %>">
<input type=image src=../images/button_dele.gif onclick="document.all.deleteForm.submit()">
&nbsp;
<input type=image src=../images/button_cancel.gif onclick="location.href='templates.jsp?column=<%=columnID%>';return false;">
</form>
</body>
</html>
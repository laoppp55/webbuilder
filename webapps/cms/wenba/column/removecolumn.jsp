<%@ page import="java.sql.*,
    		 com.bizwink.cms.news.*,
    		 com.bizwink.cms.modelManager.*,
    		 com.bizwink.cms.security.*,
		 com.bizwink.cms.util.*"
		 contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.wenba.IWenbaManager" %>
<%@ page import="com.bizwink.wenba.wenbaManagerImpl" %>
<%@ page import="com.bizwink.wenba.wenbaImpl" %>
<%
        Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
        if( authToken == null ) {
            response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=ϵͳ��ʱ�������µ�½!"));
            return;
        }

	// get parameters
	int ID   = ParamUtil.getIntParameter(request,"ID", 0);
        int siteid = authToken.getSiteID();
	boolean doDelete = ParamUtil.getBooleanParameter(request,"doDelete");

	// global error variables
	String errorMessage = "";

	boolean noColumnSpecified = (ID == 0);
	boolean errors = (noColumnSpecified);
     IWenbaManager columnManager = wenbaManagerImpl.getInstance();
	//

    // IColumnManager columnManager = ColumnPeer.getInstance();
  //	IArticleManager articleManager = ArticlePeer.getInstance();
  //	IModelManager modelManager = ModelPeer.getInstance();

	// delete user if specified
	if( doDelete && !errors )
	{
      	    String message = "";
      	    try
      	    {
/*
		//��һ����ɾ������Ŀ�µ���������
		articleManager.removeArticlesInColumn(ID,siteid);

		//�ڶ�����ɾ������Ŀ�µ�����ģ��
		modelManager.removeModelsInColumn(ID,siteid);
*/
		//��������ɾ������Ŀ(��ɾ������Ŀ����˹�������������˹���)
		columnManager.remove(ID,siteid);

		message = "ɾ����Ŀ���!";
	    }
	    catch( Exception e )
	    {
		System.err.println( e );
		message = "��Ŀδ�ҵ�";
	    }


	    request.setAttribute("message",message);
	    response.sendRedirect("index.jsp");
	    return;
	}
    	wenbaImpl column = columnManager.getColumn(ID);

%>
<html><head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../style/global.css">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
	String[][] titlebars = {
	        { "��Ŀ����", "index.jsp" },
	        { "ɾ����Ŀ", "" }
	};
	String[][] operations = null;
%>
<%@ include file="../../inc/titlebar.jsp" %>
<p class=line>
ɾ����Ŀ <b><%=StringUtil.gb2iso4View(column.getCName())%> </b>?
<p>
<ul class=cur>����: �˲����������Ե�ɾ������Ŀ�����������¡�ģ�弰��˹����������ɾ����?</ul>
<form action="removecolumn.jsp" name="deleteForm">
<input type=hidden name=doDelete value="true">
<input type=hidden name=ID value="<%= ID %>">
<input type=image src=../../images/button_dele.gif onclick="document.all.deleteForm.submit()">
&nbsp;
<input type=image src=../../images/button_cancel.gif onclick="location.href='index.jsp';return false;">
</form>
</body></html>
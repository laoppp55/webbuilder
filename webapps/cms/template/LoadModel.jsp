<%@page import="java.sql.*,
    java.util.*,
    com.bizwink.cms.news.*,
    com.bizwink.cms.modelManager.*,
    com.bizwink.cms.security.*,
    com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
    // get parameters
    String columnName  = ParamUtil.getParameter(request,"columnName");
    int columnID = ParamUtil.getIntParameter(request,"columnCode",0);
    boolean doLoad = ParamUtil.getBooleanParameter(request,"doLoad");
    int isArticle = ParamUtil.getIntParameter(request,"isArticle",0);
    String userid = ParamUtil.getParameter(request,"editor");

  //  Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
  //  if(authToken != null) {
  //      userid = authToken.getUserID();
  //  }

    out.println(userid);

    IModelManager modelManager = ModelPeer.getInstance();
    Model model = null;

    if (doLoad) {
    response.sendRedirect(
            response.encodeRedirectURL("templates.jsp?column="+columnID+"&msg=模板修改成功")
        );
        return;

      // model.setColumnID(columnID);
      // model.setCreatedate(new Timestamp(System.currentTimeMillis()));
      // model.setCreator(userid);
      // model.setEditor(userid);
      // model.setIsArticle(isArticle);
      // model.setLastupdated(new Timestamp(System.currentTimeMillis()));

      // modelManager.Create(model);
    }
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript>

<!--
function doSubmit() {
    LoadTemplate.action="/cms/template/LoadModel.jsp";
    LoadTemplate.submit();

    return true;
}

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF">
<form method="post" action="LoadModel.jsp" name="loadTemplate">
  <p>栏目名称：
    <input type=text name=columnName value=<%=columnName%>>
    <input type=hidden name=columnCode value=<%=columnID%>>
    <input type=hidden name=editor value=<%=userid%>>
    <input type=hidden name=doLoad value=true>
  </p>
  <p>模板类型：
    <input type="radio" name="isArticle" value="1" checked>
    文章模板 &nbsp;&nbsp;&nbsp;&nbsp;
    <input type="radio" name="isArticle" value="0">
    栏目模板</p>
  <p> 模板文件：
    <input type="file" name="modelFileName" enctype="multipart/form-data" value="浏览">
  </p>
  <p>
    <!--input type="button" name="LoadModle" onclick="doSubmit()" value="提交"-->
    <input type="submit" name="LoadModle" value="提交">
    <input type="button" name="cancel" onclick="javascript:window.close();" value="取消">
  </p>
</form>
</body>
</html>

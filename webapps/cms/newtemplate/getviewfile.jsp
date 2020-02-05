<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.viewFileManager.*,
                com.bizwink.cms.util.*"
                contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String sitename = authToken.getSitename();
  int ID = ParamUtil.getIntParameter(request, "id", 0);
  int type = ParamUtil.getIntParameter(request, "type", 0);

  IViewFileManager viewfileMgr = viewFilePeer.getInstance();
  String content = viewfileMgr.getAViewFile(ID).getContent();
  if (content == null)  content = "";
  content = StringUtil.gb2iso4View(content);

  content = StringUtil.replace(content, "_blank", "_self");
  content = StringUtil.replace(content, "<"+"%%DATA%%"+">","文章标题");
  content = StringUtil.replace(content, "<"+"%%URL%%"+">", "#");
  content = StringUtil.replace(content, "<"+"%%COLUMNNAME%%"+">","栏目名称");
  content = StringUtil.replace(content, "<"+"%%COLUMNNAME1%%"+">","一级栏目名称");
  content = StringUtil.replace(content, "<"+"%%COLUMNNAME2%%"+">","二级栏目名称");
  content = StringUtil.replace(content, "<"+"%%COLUMNNAME3%%"+">","三级栏目名称");
  content = StringUtil.replace(content, "<"+"%%COLUMNURL%%"+">", "#");
  content = StringUtil.replace(content, "<"+"%%sitename%%"+">",sitename);
  //content = StringUtil.replace(content, "/webbuilder/sites/"+sitename,"/webbuilder/sites/"+sitename);

  if (type == 1 || type == 3 || type == 4)     //文章列表,相关文章,热点文章
  {
    content = StringUtil.replace(content, "<"+"%%VICETITLE%%"+">","副标题");
    content = StringUtil.replace(content, "<"+"%%ASUMMARY%%"+">","摘要");
    content = StringUtil.replace(content, "<"+"%%AUTHOR%%"+">","作者");
    content = StringUtil.replace(content, "<"+"%%ASOURCE%%"+">","来源");
    content = StringUtil.replace(content, "<"+"%%NEW%%"+">","(新)");
    content = StringUtil.replace(content, "<"+"%%PT%%"+">",  "2004-11-23");
  }
  if (type == 2)     //导航条
  {
    content = StringUtil.replace(content, "<"+"%%NUM%%"+">",      "23");
    content = StringUtil.replace(content, "<"+"%%PAGENUM%%"+">",  "5");
    content = StringUtil.replace(content, "<"+"%%HEAD%%"+">",     "#");
    content = StringUtil.replace(content, "<"+"%%PREVIOUS%%"+">", "#");
    content = StringUtil.replace(content, "<"+"%%NEXT%%"+">",     "#");
    content = StringUtil.replace(content, "<"+"%%BOTTOM%%"+">",   "#");
    content = StringUtil.replace(content, "<"+"%%CURRENTPAGE%%"+">",   "3");
    content = StringUtil.replace(content, "<"+"%%SELECT%%"+">",   "<select><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select>");
    content = StringUtil.replace(content, "<"+"%%NUMBER%%"+">",   "<a href=#>1</a>&nbsp;<a href=#>2</a>&nbsp;<a href=#>3</a>&nbsp;<a href=#>4</a>&nbsp;<a href=#>5</a>");
  }
  if (type == 6)     //栏目列表
  {
    content = StringUtil.replace(content, "<"+"%%COLUMNDESC%%"+">","栏目描述");
  }
  if (type == 7)     //中英文路径
  {
    String str = content;
    String temp = StringUtil.replace(str,"<"+"%%CHINESE_PATH%%"+">","首页");
    temp = StringUtil.replace(temp,"<"+"%%URL%%"+">","#");
    content = content + temp;
    temp = StringUtil.replace(str,"<"+"%%CHINESE_PATH%%"+">","新闻");
    temp = StringUtil.replace(temp,"<"+"%%URL%%"+">","#");
    content = content + temp;
    temp = StringUtil.replace(str,"<"+"%%CHINESE_PATH%%"+">","国际新闻");
    temp = StringUtil.replace(temp,"<"+"%%URL%%"+">","#");
    content = content + temp;
  }
%>
<html>
<head>
<title>样式文件预览</title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../style/global.css">
</head>
<body>
<table border=0 width="100%">
<tr>
<td>
<%=content%>
</td>
</tr>
</table>
</body>
</html>
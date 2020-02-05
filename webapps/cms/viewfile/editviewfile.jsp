<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.viewFileManager.*"
         contentType="text/html;charset=utf-8"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect( "../login.jsp" );
    return;
  }

  int id = ParamUtil.getIntParameter(request, "id", 0);
  boolean doCreate     = ParamUtil.getBooleanParameter(request,"doCreate");
  int viewtype = 0;
  boolean error = false;
  boolean success = false;

  IViewFileManager vfManager = viewFilePeer.getInstance();
  ViewFile vf = new ViewFile();

  StringBuffer notes = new StringBuffer();
  StringBuffer content = new StringBuffer();
  String hcontent = new String();
  String tcontent = new String();
  String allcontent = new String();
  String cname = null;
  vf = vfManager.getAViewFile(id);
  viewtype = vf.getType();
  String viewname = vf.getChineseName();
  String viewdesc = vf.getNotes();
  String viewcontent = vf.getContent();
  if(viewcontent.indexOf("<!--ROWBEGIN-->") != -1){
    hcontent = viewcontent.substring(0, viewcontent.indexOf("<!--ROWBEGIN-->"));
  }
  if(viewcontent.indexOf("<!--ROWEND-->") != -1){
    tcontent = viewcontent.substring(viewcontent.indexOf("<!--ROWEND-->")+13,viewcontent.length());
  }
  if((viewcontent.indexOf("<!--ROWBEGIN-->") != -1)&&(viewcontent.indexOf("<!--ROWEND-->") != -1)){
    viewcontent = viewcontent.substring(viewcontent.indexOf("<!--ROWBEGIN-->")+15,viewcontent.indexOf("<!--ROWEND-->"));
  }

  if (doCreate)
  {
    notes.append(ParamUtil.getParameter(request, "notes"));
    content.append(ParamUtil.getParameter(request, "content"));
    hcontent    =  ParamUtil.getParameter(request, "hcontent");
    tcontent    =  ParamUtil.getParameter(request, "tcontent");
    viewtype    =  ParamUtil.getIntParameter(request, "viewtype", 1);
    cname       =  ParamUtil.getParameter(request, "cname");

    if(hcontent == null)
      hcontent = "";
    if(tcontent == null)
      tcontent = "";

    if (content == null || cname == null)
      error = true;
    else
      allcontent = hcontent + "<!--ROWBEGIN-->" + content.toString() + "<!--ROWEND-->" + tcontent;

    allcontent = StringUtil.replace(allcontent, "\"+\"%", "%");
    allcontent = StringUtil.replace(allcontent, "%\"+\"", "%");
  }

  if (doCreate && !error) {
    try {
      vf.setID(id);
      vf.setChineseName(cname);
      vf.setContent(allcontent);
      vf.setNotes(notes.toString());
      vf.setType(viewtype);

      vfManager.update(vf);
      success = true;
    } catch (viewFileException vfe) {
      success = false;
      throw new viewFileException("" + vfe.getMessage());
    }
  }

  if (success)
  {
    out.println("<script language=javascript>");
    out.println("opener.history.go(0);");
    out.println("window.close();");
    out.println("</script>");
    return;
  }
%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>修改样式文件</title>
  <link rel=stylesheet type=text/css href=../style/global.css>
  <script language="javascript">
      function changelist(val){
          if(val == 1){
              document.editview.markname.options.length = 0;
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[0].text= "选择列表标记";
              document.editview.markname.options[0].value= "";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[1].text= "文章标题";
              document.editview.markname.options[1].value= "<A href=<"+"%%TITLEURL%%"+"> target=_blank><"+"%%DATA%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[2].text= "文章副标题";
              document.editview.markname.options[2].value= "<A href=<"+"%%VICETITLEURL%%"+"> target=_blank><"+"%%VICETITLE%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[3].text= "文章概要";
              document.editview.markname.options[3].value= "<A href=<"+"%%SUMMARYURL%%"+"> target=_blank><"+"%%ASUMMARY%%"+"></A>'>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[4].text= "文章内容";
              document.editview.markname.options[4].value= "<A href=<"+"%%CONTENTURL%%"+"> target=_blank><"+"%%CONTENT%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[5].text= "文章作者";
              document.editview.markname.options[5].value= "<A href=<"+"%%AUTHORURL%%"+"> target=_blank><"+"%%AUTHOR%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[6].text= "文章来源";
              document.editview.markname.options[6].value= "<A href=<"+"%%SOURCEURL%%"+"> target=_blank><"+"%%ASOURCE%%"+"></A>'>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[7].text= "文章日期";
              document.editview.markname.options[7].value= "<"+"%%PT%%"+">";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[8].text= "站点名称";
              document.editview.markname.options[8].value= "<"+"%%sitename%%"+">";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[9].text= "文章链接";
              document.editview.markname.options[9].value= "<A href=<"+"%%URL%%"+"> target=_blank></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[10].text= "栏目名称";
              document.editview.markname.options[10].value= "<A href=<"+"%%COLUMNNAMEURL%%"+"> target=_blank><"+"%%COLUMNNAME%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[11].text= "栏目链接";
              document.editview.markname.options[11].value= "<A href=<"+"%%COLUMNURL%%"+"> target=_blank></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[12].text= "列表图标";
              document.editview.markname.options[12].value= "1";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[13].text= "列开始标记";
              document.editview.markname.options[13].value= "<!--BEGIN-->";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[14].text= "列结束标记";
              document.editview.markname.options[14].value= "<!--END-->";
          }else if(val == 2){
              document.editview.markname.options.length = 0;
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[0].text= "选择列表标记";
              document.editview.markname.options[0].value= "";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[1].text= "总条数";
              document.editview.markname.options[1].value= "共<"+"%%NUM%%"+">条";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[2].text= "总页数";
              document.editview.markname.options[2].value= "共<"+"%%PAGENUM%%"+">页";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[3].text= "第一页";
              document.editview.markname.options[3].value= "<A href=<"+"%%HEAD%%"+">>第一页</A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[4].text= "上一页";
              document.editview.markname.options[4].value= "<A href=<"+"%%PREVIOUS%%"+">>上一页</A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[5].text= "下一页";
              document.editview.markname.options[5].value= "<A href=<"+"%%NEXT%%"+">>下一页</A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[6].text= "最后页";
              document.editview.markname.options[6].value= "<A href=<"+"%%BOTTOM%%"+">>最后页</A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[7].text= "当前页";
              document.editview.markname.options[7].value= "第<"+"%%CURRENTPAGE%%"+">页";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[8].text= "下拉跳转";
              document.editview.markname.options[8].value= "选择：<"+"%%SELECT%%"+">";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[9].text= "填写页数跳转";
              document.editview.markname.options[9].value= "<form>跳转到<input name=cmspage size=4>\n<input type=button value=确定 onclick=<"+"%%GOTO%%"+">></form>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[10].text= "页码排列";
              document.editview.markname.options[10].value= "页码：<"+"%%NUMBER%%"+">";
          }else if(val == 3){
              document.editview.markname.options.length = 0;
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[0].text= "选择列表标记";
              document.editview.markname.options[0].value= "";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[1].text= "中文路径";
              document.editview.markname.options[1].value= "<A HREF=<"+"%%URL%%"+">><"+"%%CHINESE_PATH%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[2].text= "英文路径";
              document.editview.markname.options[2].value= "<A HREF=<"+"%%URL%%"+">><"+"%%ENGLISH_PATH%%"+"></A>";
          }else if(val == 4){
              document.editview.markname.options.length = 0;
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[0].text= "选择列表标记";
              document.editview.markname.options[0].value= "";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[1].text= "文章标题";
              document.editview.markname.options[1].value= "<A href=<"+"%%URL%%"+">><"+"%%MAINTITLE%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[2].text= "链接地址";
              document.editview.markname.options[2].value= "<option value='<A href=<"+"%%URL%%"+">></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[3].text= "文章概述";
              document.editview.markname.options[3].value= "<"+"%%SUMMARY%%"+">";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[4].text= "文章来源";
              document.editview.markname.options[4].value= "<"+"%%SOURCE%%"+">";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[5].text= "无链接标题";
              document.editview.markname.options[5].value= "<"+"%%MAINTITLE%%"+">";
          }
      }

      function SelectMarkName()
      {
          var i = document.editview.markname.selectedIndex;
          var str = document.editview.markname.options[i].value;
          if (str != "" && str != "1")
          {
              insertAtCaret(document.editview.content, str);
          }
          else if (str == "1")       //列表图标
          {
              var retVal = showModalDialog("../member/styleImage.jsp", "SelectStyleImage",
                  "font-family:Verdana;font-size:12;dialogWidth:35em;dialogHeight:25em;status:no");
              if (retVal != "")
              {
                  var type = retVal.substring(0, 1);
                  var str = retVal.substring(1, retVal.length);
                  if (type == "1") str = "<IMG src=/_sys_ListImages/" + str + ">";
                  insertAtCaret(document.editview.content, str);
              }
          }
          document.editview.markname.options[0].selected = true;
      }

      function storeCaret(textEl)
      {
          if (textEl.createTextRange)
          {
              textEl.caretPos = document.selection.createRange().duplicate();
          }
      }

      function insertAtCaret(textEl, text)
      {
          if (textEl.createTextRange && textEl.caretPos)
          {
              var caretPos = textEl.caretPos;
              caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == ' ' ?text + ' ' : text;
          }
          else
          {
              textEl.value = text;
          }
      }

      function check(){
          if((editview.cname.value == null)||(editview.cname.value == "")){
              alert("请输入样式文件的名称！");
              return false;
          }
          if((editview.content.value == null)||(editview.content.value == "")){
              alert("请输入样式文件的内容");
              return false;
          }
          return true;
      }
  </script>
</head>

<body bgcolor="#CCCCCC">
<form action="editviewfile.jsp" method="post" name="editview">
  <input type=hidden name=doCreate value="true">
  <input type=hidden name=id value="<%=id%>">
  <table border="1" width="95%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00 height="446" align=center>
    <tr>
      <td width="21%" bgcolor="#EFEFEF" align="right" height="20"><b>样式文件类型：</b></td>
      <td width="79%" bgcolor="#EFEFEF" height="20">&nbsp;
        <select name="viewtype" style="width:80" onchange="javascript:changelist(editview.viewtype.value);">
          <option value=1 <%if(viewtype == 1){%>selected<%}%>>列表类型</option>
          <option value=2 <%if(viewtype == 2){%>selected<%}%>>导航条</option>
          <option value=3 <%if(viewtype == 3){%>selected<%}%>>路径格式</option>
          <option value=4 <%if(viewtype == 4){%>selected<%}%>>热点文章</option>
        </select></td>
    </tr>
    <tr>
      <td width="21%" align="right" height="20"><b>样式文件名称：</b></td>
      <td width="79%" height="20">&nbsp;<input type="text" name="cname" value="<%=((viewname==null)||(viewname.equals("null")))?"":viewname%>"></td>
    </tr>
    <tr>
      <td width="21%" bgcolor="#EFEFEF" align="right" height="20"><b>样式文件注释：</b></td>
      <td width="79%" bgcolor="#EFEFEF" height="20">&nbsp;<input type="text" name="notes" size="50" value="<%=((viewdesc==null)||(viewdesc.equals("null")))?"":viewdesc%>"></td>
    </tr>
    <tr>
      <td width="21%" bgcolor="#EFEFEF" align="right" height="20"><b>选择标记：</b></td>
      <td width="79%" bgcolor="#EFEFEF" height="20">&nbsp;
        <select size="1" name="markname" style="width:140" onchange="SelectMarkName();">
          <script language="javascript">
              <%if(viewtype == 1){%>
              document.editview.markname.options.length = 0;
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[0].text= "选择列表标记";
              document.editview.markname.options[0].value= "";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[1].text= "文章标题";
              document.editview.markname.options[1].value= "<A href=<"+"%%TITLEURL%%"+"> target=_blank><"+"%%DATA%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[2].text= "文章副标题";
              document.editview.markname.options[2].value= "<A href=<"+"%%VICETITLEURL%%"+"> target=_blank><"+"%%VICETITLE%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[3].text= "文章概要";
              document.editview.markname.options[3].value= "<A href=<"+"%%SUMMARYURL%%"+"> target=_blank><"+"%%ASUMMARY%%"+"></A>'>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[4].text= "文章内容";
              document.editview.markname.options[4].value= "<A href=<"+"%%CONTENTURL%%"+"> target=_blank><"+"%%CONTENT%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[5].text= "文章作者";
              document.editview.markname.options[5].value= "<A href=<"+"%%AUTHORURL%%"+"> target=_blank><"+"%%AUTHOR%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[6].text= "文章来源";
              document.editview.markname.options[6].value= "<A href=<"+"%%SOURCEURL%%"+"> target=_blank><"+"%%ASOURCE%%"+"></A>'>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[7].text= "文章日期";
              document.editview.markname.options[7].value= "<"+"%%PT%%"+">";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[8].text= "站点名称";
              document.editview.markname.options[8].value= "<"+"%%sitename%%"+">";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[9].text= "文章链接";
              document.editview.markname.options[9].value= "<A href=<"+"%%URL%%"+"> target=_blank></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[10].text= "栏目名称";
              document.editview.markname.options[10].value= "<A href=<"+"%%COLUMNNAMEURL%%"+"> target=_blank><"+"%%COLUMNNAME%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[11].text= "栏目链接";
              document.editview.markname.options[11].value= "<A href=<"+"%%COLUMNURL%%"+"> target=_blank></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[12].text= "列表图标";
              document.editview.markname.options[12].value= "1";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[13].text= "列开始标记";
              document.editview.markname.options[13].value= "<!--BEGIN-->";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[14].text= "列结束标记";
              document.editview.markname.options[14].value= "<!--END-->";
              <%}else if(viewtype == 2){%>
              document.editview.markname.options.length = 0;
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[0].text= "选择列表标记";
              document.editview.markname.options[0].value= "";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[1].text= "总条数";
              document.editview.markname.options[1].value= "共<"+"%%NUM%%"+">条";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[2].text= "总页数";
              document.editview.markname.options[2].value= "共<"+"%%PAGENUM%%"+">页";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[3].text= "第一页";
              document.editview.markname.options[3].value= "<A href=<"+"%%HEAD%%"+">>第一页</A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[4].text= "上一页";
              document.editview.markname.options[4].value= "<A href=<"+"%%PREVIOUS%%"+">>上一页</A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[5].text= "下一页";
              document.editview.markname.options[5].value= "<A href=<"+"%%NEXT%%"+">>下一页</A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[6].text= "最后页";
              document.editview.markname.options[6].value= "<A href=<"+"%%BOTTOM%%"+">>最后页</A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[7].text= "当前页";
              document.editview.markname.options[7].value= "第<"+"%%CURRENTPAGE%%"+">页";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[8].text= "下拉跳转";
              document.editview.markname.options[8].value= "选择：<"+"%%SELECT%%"+">";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[9].text= "填写页数跳转";
              document.editview.markname.options[9].value= "<form>跳转到<input name=cmspage size=4>\n<input type=button value=确定 onclick=<"+"%%GOTO%%"+">></form>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[10].text= "页码排列";
              document.editview.markname.options[10].value= "页码：<"+"%%NUMBER%%"+">";
              <%}else if(viewtype == 3){%>
              document.editview.markname.options.length = 0;
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[0].text= "选择列表标记";
              document.editview.markname.options[0].value= "";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[1].text= "中文路径";
              document.editview.markname.options[1].value= "<A HREF=<"+"%%URL%%"+">><"+"%%CHINESE_PATH%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[2].text= "英文路径";
              document.editview.markname.options[2].value= "<A HREF=<"+"%%URL%%"+">><"+"%%ENGLISH_PATH%%"+"></A>";
              <%}else if(viewtype == 4){%>
              document.editview.markname.options.length = 0;
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[0].text= "选择列表标记";
              document.editview.markname.options[0].value= "";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[1].text= "文章标题";
              document.editview.markname.options[1].value= "<A href=<"+"%%URL%%"+">><"+"%%MAINTITLE%%"+"></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[2].text= "链接地址";
              document.editview.markname.options[2].value= "<option value='<A href=<"+"%%URL%%"+">></A>";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[3].text= "文章概述";
              document.editview.markname.options[3].value= "<"+"%%SUMMARY%%"+">";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[4].text= "文章来源";
              document.editview.markname.options[4].value= "<"+"%%SOURCE%%"+">";
              document.editview.markname.add(document.createElement("OPTION"));
              document.editview.markname.options[5].text= "无链接标题";
              document.editview.markname.options[5].value= "<"+"%%MAINTITLE%%"+">";
              <%}%>
              </script>
              </select>
              </td>
              </tr>
              <div id="h1" style="VISIBILITY: hidden; POSITION: absolute">
                  <tr>
                  <td width="21%" align="right" height="20"><b>样式文件头：</b></td>
              <td width="79%" height="80">&nbsp;<textarea name="hcontent" cols=66 rows=5 WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);" style="font-size: 8pt; font-family: Georgia"><%=hcontent%></textarea></td>
              </tr>
              </div>
              <tr>
              <td width="21%" align="right" height="20"><b>样式文件内容：</b></td>
              <td width="79%" height="230">&nbsp;<textarea name="content" cols=66 rows=23 WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);" style="font-size: 8pt; font-family: Georgia"><%=viewcontent%></textarea></td>
              </tr>
              <div id="h2" style="VISIBILITY: hidden; POSITION: absolute">
                  <tr>
                  <td width="21%" align="right" height="20"><b>样式文件尾：</b></td>
              <td width="79%" height="80">&nbsp;<textarea name="tcontent" cols=66 rows=5 WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);" style="font-size: 8pt; font-family: Georgia"><%=tcontent%></textarea></td>
              </tr>
              </div>
              </table>
              <p align=center>
                  <input type="submit" name="edit" value="修改" onclick="javascript:return check();">&nbsp;&nbsp;
              <input type="button" name="cancel" value="取消" onclick="javascript:window.close()">
                  </p>
                  </form>
                  <%if(viewtype == 3){%>
                  <script language="javascript">
              document.editview.hcontent.editable = false;
              </script>
              <%}%>
              </body>
              </html>

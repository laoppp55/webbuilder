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

  boolean doCreate     = ParamUtil.getBooleanParameter(request,"doCreate");
  int viewtype = 0;
  boolean error = false;
  boolean success = false;

  IViewFileManager vfManager = viewFilePeer.getInstance();

  StringBuffer notes = new StringBuffer();
  StringBuffer content = new StringBuffer();
  String hcontent = new String();
  String tcontent = new String();
  String allcontent = new String();
  String cname = null;
  ViewFile vf = new ViewFile();

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
      vf.setSiteID(-1);
      vf.setChineseName(cname);
      vf.setContent(allcontent);
      vf.setNotes(notes.toString());
      vf.setType(viewtype);

      vfManager.create(vf);
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
  <title>增加样式文件</title>
  <link rel=stylesheet type=text/css href=../style/global.css>
  <script language="javascript">
      function changelist(val){
          if(val == 1){
              addview.markname.options.length = 0;
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[0].text= "选择列表标记";
              document.addview.markname.options[0].value= "";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[1].text= "文章标题";
              document.addview.markname.options[1].value= "<A href=<"+"%%TITLEURL%%"+"> target=_blank><"+"%%DATA%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[2].text= "文章副标题";
              document.addview.markname.options[2].value= "<A href=<"+"%%VICETITLEURL%%"+"> target=_blank><"+"%%VICETITLE%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[3].text= "文章概要";
              document.addview.markname.options[3].value= "<A href=<"+"%%SUMMARYURL%%"+"> target=_blank><"+"%%ASUMMARY%%"+"></A>'>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[4].text= "文章内容";
              document.addview.markname.options[4].value= "<A href=<"+"%%CONTENTURL%%"+"> target=_blank><"+"%%CONTENT%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[5].text= "文章作者";
              document.addview.markname.options[5].value= "<A href=<"+"%%AUTHORURL%%"+"> target=_blank><"+"%%AUTHOR%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[6].text= "文章来源";
              document.addview.markname.options[6].value= "<A href=<"+"%%SOURCEURL%%"+"> target=_blank><"+"%%ASOURCE%%"+"></A>'>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[7].text= "文章日期";
              document.addview.markname.options[7].value= "<"+"%%PT%%"+">";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[8].text= "站点名称";
              document.addview.markname.options[8].value= "<"+"%%sitename%%"+">";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[9].text= "文章链接";
              document.addview.markname.options[9].value= "<A href=<"+"%%URL%%"+"> target=_blank></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[10].text= "栏目名称";
              document.addview.markname.options[10].value= "<A href=<"+"%%COLUMNNAMEURL%%"+"> target=_blank><"+"%%COLUMNNAME%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[11].text= "栏目链接";
              document.addview.markname.options[11].value= "<A href=<"+"%%COLUMNURL%%"+"> target=_blank></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[12].text= "列表图标";
              document.addview.markname.options[12].value= "1";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[13].text= "列开始标记";
              document.addview.markname.options[13].value= "<!--BEGIN-->";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[14].text= "列结束标记";
              document.addview.markname.options[14].value= "<!--END-->";
          }else if(val == 2){
              addview.markname.options.length = 0;
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[0].text= "选择列表标记";
              document.addview.markname.options[0].value= "";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[1].text= "总条数";
              document.addview.markname.options[1].value= "共<"+"%%NUM%%"+">条";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[2].text= "总页数";
              document.addview.markname.options[2].value= "共<"+"%%PAGENUM%%"+">页";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[3].text= "第一页";
              document.addview.markname.options[3].value= "<A href=<"+"%%HEAD%%"+">>第一页</A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[4].text= "上一页";
              document.addview.markname.options[4].value= "<A href=<"+"%%PREVIOUS%%"+">>上一页</A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[5].text= "下一页";
              document.addview.markname.options[5].value= "<A href=<"+"%%NEXT%%"+">>下一页</A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[6].text= "最后页";
              document.addview.markname.options[6].value= "<A href=<"+"%%BOTTOM%%"+">>最后页</A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[7].text= "当前页";
              document.addview.markname.options[7].value= "第<"+"%%CURRENTPAGE%%"+">页";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[8].text= "下拉跳转";
              document.addview.markname.options[8].value= "选择：<"+"%%SELECT%%"+">";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[9].text= "填写页数跳转";
              document.addview.markname.options[9].value= "<form>跳转到<input name=cmspage size=4>\n<input type=button value=确定 onclick=<"+"%%GOTO%%"+">></form>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[10].text= "页码排列";
              document.addview.markname.options[10].value= "页码：<"+"%%NUMBER%%"+">";
          }else if(val == 3){
              addview.markname.options.length = 0;
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[0].text= "选择列表标记";
              document.addview.markname.options[0].value= "";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[1].text= "中文路径";
              document.addview.markname.options[1].value= "<A HREF=<"+"%%URL%%"+">><"+"%%CHINESE_PATH%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[2].text= "英文路径";
              document.addview.markname.options[2].value= "<A HREF=<"+"%%URL%%"+">><"+"%%ENGLISH_PATH%%"+"></A>";
          }else if(val == 4){
              addview.markname.options.length = 0;
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[0].text= "选择列表标记";
              document.addview.markname.options[0].value= "";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[1].text= "文章标题";
              document.addview.markname.options[1].value= "<A href=<"+"%%URL%%"+">><"+"%%MAINTITLE%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[2].text= "链接地址";
              document.addview.markname.options[2].value= "<option value='<A href=<"+"%%URL%%"+">></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[3].text= "文章概述";
              document.addview.markname.options[3].value= "<"+"%%SUMMARY%%"+">";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[4].text= "文章来源";
              document.addview.markname.options[4].value= "<"+"%%SOURCE%%"+">";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[5].text= "无链接标题";
              document.addview.markname.options[5].value= "<"+"%%MAINTITLE%%"+">";
          }else if(val == 6){
              addview.markname.options.length = 0;
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[0].text= "选择列表标记";
              document.addview.markname.options[0].value= "";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[1].text= "文章标题";
              document.addview.markname.options[1].value= "<A HREF=<"+"%%URL%%"+"> target=_blank><"+"%%DATA%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[2].text= "文章副标题";
              document.addview.markname.options[2].value= "<A href=<"+"%%URL%%"+"> target=_blank><"+"%%VICETITLE%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[3].text= "文章概要";
              document.addview.markname.options[3].value= "<A href=<"+"%%URL%%"+"> target=_blank><"+"%%ASUMMARY%%"+"></A>'>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[4].text= "文章内容";
              document.addview.markname.options[4].value= "<"+"%%CONTENT%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[5].text= "文章作者";
              document.addview.markname.options[5].value= "<A href=<"+"%%URL%%"+"> target=_blank><"+"%%AUTHOR%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[6].text= "文章来源";
              document.addview.markname.options[6].value= "<A href=<"+"%%URL%%"+"> target=_blank><"+"%%ASOURCE%%"+"></A>'>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[7].text= "文章日期";
              document.addview.markname.options[7].value= "<"+"%%PT%%"+">";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[8].text= "站点名称";
              document.addview.markname.options[8].value= "<"+"%%sitename%%"+">";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[9].text= "文章URL";
              document.addview.markname.options[9].value= "<A href=<"+"%%URL%%"+"> target=_blank></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[10].text= "栏目名称";
              document.addview.markname.options[10].value= "<A href=<"+"%%COLUMNNAMEURL%%"+"> target=_blank><"+"%%COLUMNNAME%%"+"></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[11].text= "栏目URL";
              document.addview.markname.options[11].value= "<A href=<"+"%%COLUMNURL%%"+"> target=_blank></A>";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[13].text= "分栏开始标记";
              document.addview.markname.options[13].value= "<!--BEGIN-->";
              document.addview.markname.add(document.createElement("OPTION"));
              document.addview.markname.options[14].text= "分栏结束标记";
              document.addview.markname.options[14].value= "<!--END-->";
          }
      }

      function SelectMarkName()
      {
          var i = document.addview.markname.selectedIndex;
          var str = document.addview.markname.options[i].value;
          if (str != "" && str != "1")
          {
              insertAtCaret(document.addview.content, str);
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
                  insertAtCaret(document.addview.content, str);
              }
          }
          document.addview.markname.options[0].selected = true;
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
          if((addview.cname.value == null)||(addview.cname.value == "")){
              alert("请输入样式文件的名称！");
              return false;
          }
          if((addview.content.value == null)||(addview.content.value == "")){
              alert("请输入样式文件的内容");
              return false;
          }
          return true;
      }
  </script>
</head>

<body bgcolor="#CCCCCC">
<form action="addviewfile.jsp" method="post" name="addview">
  <input type=hidden name=doCreate value="true">
  <table border="1" width="95%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00 height="446" align=center>
    <tr>
      <td width="21%" bgcolor="#EFEFEF" align="right" height="20"><b>样式文件类型：</b></td>
      <td width="79%" bgcolor="#EFEFEF" height="20">&nbsp;
        <select name="viewtype" style="width:100" onchange="javascript:changelist(addview.viewtype.value);">
          <option value=1>列表类型</option>
          <option value=2>导航条</option>
          <option value=3>路径格式</option>
          <option value=4>热点文章</option>
          <option value=5>已阅读连接样式</option>
          <option value=6>栏目列表样式</option>
          <option value=7>新文章样式</option>
        </select></td>
    </tr>
    <tr>
      <td width="21%" align="right" height="20"><b>样式文件名称：</b></td>
      <td width="79%" height="20">&nbsp;<input type="text" name="cname"></td>
    </tr>
    <tr>
      <td width="21%" bgcolor="#EFEFEF" align="right" height="20"><b>样式文件注释：</b></td>
      <td width="79%" bgcolor="#EFEFEF" height="20">&nbsp;<input type="text" name="notes" size="50"></td>
    </tr>
    <tr>
      <td width="21%" bgcolor="#EFEFEF" align="right" height="20"><b>选择标记：</b></td>
      <td width="79%" bgcolor="#EFEFEF" height="20">&nbsp;
        <select size="1" name="markname" style="width:140" onchange="SelectMarkName();">
          <option value="">选择列表标记</option>
          <option value='<A href=<"+"%%TITLEURL%%"+"> target=_blank><"+"%%DATA%%"+"></A>'>文章标题</option>
          <option value='<A href=<"+"%%VICETITLEURL%%"+"> target=_blank><"+"%%VICETITLE%%"+"></A>'>文章副标题</option>
          <option value='<A href=<"+"%%SUMMARYURL%%"+"> target=_blank><"+"%%ASUMMARY%%"+"></A>'>文章概要</option>
          <option value='<A href=<"+"%%CONTENTURL%%"+"> target=_blank><"+"%%CONTENT%%"+"></A>'>文章内容</option>
          <option value='<A href=<"+"%%AUTHORURL%%"+"> target=_blank><"+"%%AUTHOR%%"+"></A>'>文章作者</option>
          <option value='<A href=<"+"%%SOURCEURL%%"+"> target=_blank><"+"%%ASOURCE%%"+"></A>'>文章来源</option>
          <option value='<"+"%%PT%%"+">'>文章日期</option>
          <option value='<"+"%%sitename%%"+">'>站点名称</option>
          <option value='<A href=<"+"%%URL%%"+"> target=_blank></A>'>文章链接</option>
          <option value='<A href=<"+"%%COLUMNNAMEURL%%"+"> target=_blank><"+"%%COLUMNNAME%%"+"></A>'>栏目名称</option>
          <option value='<A href=<"+"%%COLUMNURL%%"+"> target=_blank></A>'>栏目链接</option>
          <option value='1'>列表图标</option>
          <option value='<!--BEGIN-->'>列开始标记</option>
          <option value='<!--END-->'>列结束标记</option>
        </select>
      </td>
    </tr>
    <tr>
      <td width="21%" align="right" height="20"><b>样式文件头：</b></td>
      <td width="79%" height="80">&nbsp;<textarea name="hcontent" cols=66 rows=5 WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);" style="font-size: 8pt; font-family: Georgia"></textarea></td>
    </tr>
    <tr>
      <td width="21%" align="right" height="20"><b>样式文件内容：</b></td>
      <td width="79%" height="230">&nbsp;<textarea name="content" cols=66 rows=23 WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);" style="font-size: 8pt; font-family: Georgia"></textarea></td>
    </tr>
    <tr>
      <td width="21%" align="right" height="20"><b>样式文件尾：</b></td>
      <td width="79%" height="80">&nbsp;<textarea name="tcontent" cols=66 rows=5 WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);" style="font-size: 8pt; font-family: Georgia"></textarea></td>
    </tr>
  </table>
  <p align=center>
    <input type="submit" name="add" value="增加" onclick="javascript:return check();">&nbsp;&nbsp;
    <input type="button" name="cancel" value="取消" onclick="javascript:window.close()">
  </p>
</form>

</body>
</html>

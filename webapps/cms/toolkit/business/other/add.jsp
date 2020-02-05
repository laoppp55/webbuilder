<%@ page import="java.io.*,
                 java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.booyee.other.*"
                 contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String userid = authToken.getUserID();
  int siteid = 1;
  int startflag = ParamUtil.getIntParameter(request, "startflag", -1);

  IOtherManager otherMgr = OtherPeer.getInstance();

  if(startflag == 1){
    String title = ParamUtil.getParameter(request, "title");
    String content = ParamUtil.getParameter(request, "content");
    otherMgr.insertZhuanTi(title,content);
    //response.sendRedirect("index.jsp");
    out.println("<script language=\"javascript\">");
    out.println("opener.history.go(0);");
    out.println("window.close();");
    out.println("</script>");
  }
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<meta http-equiv="Pragma" content="no-cache">
<script language="javascript">
function check(frm){
  if((annoForm.title.value == "")||(annoForm.title.value == null)){
    alert("请输入公告标题！");
    return false;
  }

  if((annoForm.content.value == "")||(annoForm.content.value == null)){
    alert("请输入公告内容！");
    return false;
  }
  return true;
}

function upload()
{
  window.open("uploadfiles2.jsp","上传图片","width=347,height=175,toolbar=no,top=200,left=200,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no");
}
</script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<%
      String[][] titlebars = {
              { "首页", "" },
              { "图书专题特惠管理", "index.jsp" },
              { "添加新专题", "" }
          };

      String[][] operations = {
        { "", "" },{"    ",""}
      };
%>
<%@ include file="../inc/titlebar.jsp" %>
<form action="add.jsp" method="post" name="annoForm">
<input type="hidden" name="startflag" value="1">
<center>
<table width="770" border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">添加新专题</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
                <table width="100%" border="0" cellpadding="2" cellspacing="1">
                  <tr bgcolor="#FFFFFF">
                    <td>专题名称：</td>
                    <td> <input type="text" name="title" size="80"> <font color="#FF0000">&nbsp;
                      </font> </td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td name="title">专题描述：</td>
                    <td>
                      <textarea rows="18" cols="90" name="content"></textarea>
                    </td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>上传图片：</td>
                    <td><input type="button" value="上传图片" onclick="javascript:upload()"></td>
                  </tr>
                </table>
            </td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr align="center">
            <td><input type="submit" name="Submit" value="提交" onclick="javascript:return check(this.form);">
            </td>
          </tr>
        </table>
      </td>
</tr>
</table>
</center>
</form>
</center>
</body>
</html>

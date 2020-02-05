<%@ page import="java.io.*,
                 java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.booyee.announce.*"
                 contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int startrow            = ParamUtil.getIntParameter(request, "startrow", 0);
  int range               = ParamUtil.getIntParameter(request, "range", 100);
  String userid = authToken.getUserID();
  int siteid = 1;

  IAnnounceManager announceMgr = AnnouncePeer.getInstance();
  List list = new ArrayList();
  List currentlist = new ArrayList();
  int currentrows = 0;
  int totalrows = 0;
  list = announceMgr.getAllAnnounce();
  currentlist = announceMgr.getCurrentAllAnnounce(startrow,range);

  int row = 0;
  int rows = 0;
  int totalpages = 0;
  int currentpage = 0;
  row = currentlist.size();
  rows = list.size();

  if(rows < range){
    totalpages = 1;
    currentpage = 1;
  }else{
    if(rows%range == 0)
      totalpages = rows/range;
    else
      totalpages = rows/range + 1;

    currentpage = startrow/range + 1;
  }
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<meta http-equiv="Pragma" content="no-cache">
<script language="javascript">
function CheckAll(form){
   for (var i=0;i<form.elements.length;i++){
      var e = form.elements[i];
      if (e.name != 'chkAll')
        e.checked = form.chkAll.checked;
    }
}

function check(form){
  var flag = false;
  for (var i=0;i<form.elements.length;i++){
    if(form.elements[i].checked){
      flag = true;
    }
  }
  if(!flag){
    alert("请您选择要删除的公告！");
    return false;
  }else{
    var val;
    val = confirm("您确定要删除这些公告吗？");
    if(val)
      return true;
    else
      return false;
  }
}
</script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<%
      String[][] titlebars = {
              { "首页", "" },
              { "公告管理", "" }
          };

      String[][] operations = {
        { "添加新公告", "addnew.jsp" },{"    ",""}
      };
%>
<%@ include file="../inc/titlebar.jsp" %>
<form method="post" action="deleteAnno.jsp">
  <table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF" width="90%">
    <tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">公告管理</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td width="5%" align="center">&nbsp;</td>
                  <td width="5%" align="center">公告号</td>
                  <td width="15%" align="center">标题</td>
                  <td width="60%" align="center">内容</td>
                  <td width="15%" align="center">创建时间</td>
                </tr>
                <%
                Announce announce = new Announce();
                String title = "";
                String content = "";
                for(int i=0;i<currentlist.size();i++){
                  announce = (Announce)currentlist.get(i);
                  title = announce.getTitle();
                  content = announce.getContent();
                  title = new String(title.getBytes("iso8859_1"),"GBK");
                  content = new String(content.getBytes("iso8859_1"),"GBK");
                %>
                <tr  bgcolor="#FFFFFF">
                  <td align="center"><input type="checkbox" name="delAnnounce" value="<%=announce.getID()%>">
                  <td align="center"><%=announce.getID()%></td>
                  <td><%=title%></td>
                  <td><%=content%></td>
                  <td><%=announce.getCreateDate()%></td>
                </tr>
                <%}%>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="chkAll" value="on"  onclick="javascript:CheckAll(this.form);">全部选中</td></tr>
  </table>
  <input type="submit" value="删除" onclick="javascript:return check(this.form);">
</form>
<table>
<tr>
<td>
<%
  if((startrow-range)>=0){
%>
[<a href="index.jsp?startrow=<%=startrow-range%>">上一页</a>]
<%}%>
<%
  if((startrow+range)<rows){
%>
[<a href="index.jsp?startrow=<%=startrow+range%>">下一页</a>]
<%}%>
</td>
</tr>
</table>
</center>
</body>
</html>

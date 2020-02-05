<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Message.*"
                 contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int siteid = authToken.getSiteID();

  int startrow            = ParamUtil.getIntParameter(request, "startrow", 0);
  int range               = ParamUtil.getIntParameter(request, "range", 30);
  String username         = ParamUtil.getParameter(request,"username");


  IMessageManager messageMgr = messagePeer.getInstance();
  Message message = new Message();
  List list = new ArrayList();
  List currentlist = new ArrayList();
  int currentrows = 0;
  int totalrows = 0;
  list = messageMgr.getMessageList(1,-1,1,username,siteid,startrow,0);
  currentlist = messageMgr.getMessageList(1,-1,1,username,siteid,startrow,range);

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
    alert("请您选择要删除的信息！");
    return false;
  }else{
    var val;
    val = confirm("您确定要删除这些信息吗？");
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
              { "消息管理", "" }
          };

      String[][] operations = {
              { "发送消息", "sendmessage.jsp" },
              { "发件箱", "index2.jsp" },
              { "收件箱", "index.jsp" },
              { "收藏夹", "important.jsp" }
          };
%>
<%@ include file="../inc/titlebar.jsp" %>
<form method="post" action="deleteMess.jsp">
<input type="hidden" name="username" value="<%=username%>">
<input type="hidden" name="flag" value="2">
<input type="hidden" name="startrow" value="<%=startrow%>">
<input type="hidden" name="range" value="<%=range%>">
  <table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF" width="100%">
    <tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">消息列表</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td width="5%" align="center">&nbsp;</td>

                  <td width="15%" align="center">接收者</td>
                  <td width="65%" align="center">消息内容</td>
                  <td width="15%" align="center">发送时间</td>
                </tr>
                <%
                int id = 0;
                String receive_user = "";
                String content = "";
                Timestamp send_date = null;
                String senddate = "";
                for(int i=0;i<currentlist.size();i++){
                  message = (Message)currentlist.get(i);

                  id = message.getID();
                  receive_user = StringUtil.gb2iso4View(message.getReceiverName());
                  content = StringUtil.gb2iso4View(message.getMessage());
                  send_date = message.getSendDate();
                  senddate = String.valueOf(send_date);
                  senddate = senddate.equals("null")?"":senddate.substring(0,16);
                %>
                <tr  bgcolor="#FFFFFF">
                  <td align="center"><input type="checkbox" name="delMessage" value="<%=id%>"></td>

                  <td align="center">
                      <table width="100%"><tr><td align="center" >
                       <%if(message.getReceive_User()!=null && !message.getReceive_User().equals("")){%>
                       <a href="../member/detail_user.jsp?userid=<%=message.getReceive_User()%>" target=_blank><%=receive_user%></a>
                       <%}else{%>
                       <font color=blue>所有用户</font>
                       <%}%>
                      </td>
                      <td class="txt" width=36>
                       <a href="sendmessage.jsp?userid=<%=message.getReceive_User()%>" target=_blank>
                       <font color="#99B8D5">新信息</font>
                       </a>
                      </td></tr></table>
                  </td>
                  <td align="center"><%=content%></a></td>
                  <td align="center"><%=senddate%></td>
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
[<a href="index2.jsp?startrow=<%=startrow-range%>">上一页</a>]
<%}%>
<%
  if((startrow+range)<rows){
%>
[<a href="index2.jsp?startrow=<%=startrow+range%>">下一页</a>]
<%}%>
</td>
</tr>
</table>
</center>
<script language="javascript">
function searchLeaveWord()
{
  if((searchLeaveWordForm.username.value == null))
  {
    alert("请输入您要查询的用户名！");
    return false;
  }
  searchLeaveWordForm.submit();
}
</script>
<form name="searchLeaveWordForm" method="post" action="index2.jsp">
<input type="hidden" name="searchflag" value=1>
<table align="center">
<tr><td>
查询留言：用户ID&nbsp;<input type="text" name="username">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="查询" onclick="javascript:return searchLeaveWord();">
</td></tr>
</table>
</form>
</body>
</html>

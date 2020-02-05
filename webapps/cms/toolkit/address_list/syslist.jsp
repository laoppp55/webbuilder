<%@page import="com.bizwink.cms.toolkit.addresslist.*,
                java.util.*,
                com.bizwink.cms.util.ParamUtil,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.SessionUtil,
                java.sql.Timestamp" contentType="text/html;charset=GBK"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int siteid = authToken.getSiteID();
  String memberid = authToken.getUserID();
  int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
  int range = ParamUtil.getIntParameter(request, "range", 20);
  if(startrow<0){
    startrow = 0;
  }

  IAddressListManager addresslistMgr = AddressListPeer.getInstance();
  List list = new ArrayList();
  List currentlist = new ArrayList();

  list = addresslistMgr.getSysAddressList(siteid);
  currentlist = addresslistMgr.getCurrentSysAddressList(startrow,range,siteid);

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
<HTML><HEAD><TITLE>New Page 1</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<LINK href="images/common.css" type=text/css rel=stylesheet>
<LINK href="images/forum.css" type=text/css rel=stylesheet>
<SCRIPT language=javascript>
function searchcheck(){
  if((form1.searchstr.value ==null)||(form1.searchstr.value == "")){
    alert("请输入要查询的内容！");
    return false;
  }
  form1.submit();
  return true;
}

function popwin(id,name,uid){
  window.open("send.jsp?id="+id+"&name="+name+"&userid="+uid,"","width=550,height=300,top=100,left=200");
}
</SCRIPT>
</HEAD>
<BODY>
<P align=right>
<%
  Timestamp messagedate = addresslistMgr.getNewMessageDate(memberid);
  Timestamp readdate = addresslistMgr.getNewReadDate(memberid);

  out.println("<table>");
  out.println("<tr>");
  if(readdate != null){
    if(messagedate.compareTo(readdate)>0){
      out.println("<td align=center>");
      out.println("<a href='messagelist.jsp?receiver='"+memberid+"><b><font color=red>您有新消息！</font></b></a>");
      out.println("</td>");
    }
  }
  out.println("<td><a href=\"messagelist.jsp\">已接收消息</a></td>");
  out.println("<td>&nbsp;</td>");
  out.println("<td><a href=\"sendmsg.jsp\">已发送消息</a></td>");
%>

<FORM name=form1 action=search.jsp method=post>
<TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
borderColorLight=#008000 border=1>
  <TBODY>
  <TR height=35>
    <TD align=middle width="5%">编号</TD>
    <TD align=middle width="8%">姓名</TD>
    <td align=middle width="8%">登录名</td>
    <TD align=middle width="10%">手机</TD>
    <TD align=middle width="9%">电话</TD>
    <TD align=middle width="10%">电子邮件</TD>
    <TD align=middle width="5%">发送消息</TD></TR>
<%
  int id = 0;
  String username = "";
  String userid = "";
  String mobilephone = "";
  String phone = "";
  String email = "";
  for(int i=0;i<row;i++){
    AddressList addresslist = (AddressList)currentlist.get(i);
    id = addresslist.getID();
    username = addresslist.getName()==null?"":addresslist.getName();
    userid = addresslist.getMemberID();
    mobilephone = addresslist.getMobilephone();
    phone = addresslist.getPhone();
    email = addresslist.getEmail();

    if(addresslist.getMemberID().equalsIgnoreCase(memberid)){
      continue;
    }
%>
  <TR height=35>
    <TD align=middle width="5%"><%=(currentpage-1)*range+i+1%></TD>
    <TD align=middle width="8%"><A href="javascript:popwin('<%=id%>','<%=username%>','<%=userid%>');"><%=username%></a> </TD>
    <TD align=middle width="8%"><%=userid%></td>
    <TD align=middle width="10%"><%=mobilephone==null?"--":mobilephone%> </TD>
    <TD align=middle width="9%"><%=phone==null?"--":phone%> </TD>
    <TD align=middle width="10%"><%=email==null?"--":email%> </TD>
    <TD align=middle width="5%"><A href="javascript:popwin('<%=id%>','<%=username%>','<%=userid%>');"><IMG src="images/send_1.gif" border=0></A></TD>
</TR>
<%}%>
</TBODY></TABLE></p><BR>
<p align=center>
<TABLE>
  <TBODY>
  <TR>
    <TD>总共<%=totalpages%>页&nbsp;&nbsp;  当前第<%=currentpage%>页&nbsp;
    <%
      if(startrow>0){
    %>
    <a href="list.jsp?startrow=0">第一页</a>
    <%}%>
    <%if((startrow-range)>=0){%>
    <a href="list.jsp?startrow=<%=startrow-range%>">上一页</a>
    <%}%>
    <%if((startrow+range)<list.size()){%>
    <A href="list.jsp?startrow=<%=startrow+range%>">下一页</A>
    <%}%>
    <%if(currentpage != totalpages){%>
    <A href="list.jsp?startrow=<%=(totalpages-1)*range%>">最后一页</A>
    <%}%>
    </TD>
    <TD>&nbsp;</TD>
</TR></TBODY></TABLE></p><BR><BR>
</FORM></BODY></HTML>

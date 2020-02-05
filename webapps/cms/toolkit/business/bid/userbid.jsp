<%@ page import="java.io.*,
                 java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.booyee.bid.*,
                 com.booyee.search.*"
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

  String jumpstr = "";
  userid                   = ParamUtil.getParameter(request,"userid");
  int startrow            = ParamUtil.getIntParameter(request, "startrow", 0);
  int range               = ParamUtil.getIntParameter(request, "range", 10);
  String bookname  = "";


  float user_money = 0;
  String buytime = "";

  IBidManager bidMgr = BidPeer.getInstance();
  Bid bid = new Bid();

  List list = new ArrayList();
  List currentlist = new ArrayList();
  list = bidMgr.listUserBidBookname(0,0,userid);
  currentlist = bidMgr.listUserBidBookname(startrow,range,userid);

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

  DecimalFormat df = new DecimalFormat();
  df.applyPattern("0.00");
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<meta http-equiv="Pragma" content="no-cache">
<script language="javascript">
  function golist(r){
    detailform.action = "userbid.jsp?userid=<%=userid%>&startrow="+r;
    detailform.submit();
  }

</script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<%
      String[][] titlebars = {
              { "首页", "" },
              { "竞标管理", "index.jsp" }
          };

      String[][] operations = {
        { "正在竞标", "index.jsp" },{"    ",""},
        { "添加竞标", "add.jsp" },{"    ",""},
        { "竞标结束", "manager.jsp" },{"    ",""},
        { "历史纪录", "history-list.jsp" },{"    ",""}
      };
%>
<%@ include file="../inc/titlebar.jsp" %>
<form action="userbid.jsp" method="post" name="detailform">
<center>
<table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="2" cellspacing="1">

          <tr bgcolor="#F4F4F4" align="center" >
           <td class="moduleTitle" colspan="2">
             用户ID:"<%=userid%>"&nbsp;竞标出价详单</font>
           </td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td colspan="2">
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td align="center" class="txt">竞标图书名称</td>
                  <td align="center" class="txt">用户出价</td>
                  <td align="center" class="txt">出价时间</td>
                </tr>

              <%
                for(int i=0;i<currentlist.size();i++){
                  bid = (Bid)currentlist.get(i);
                  bookname = bid.getBookName();
                  bookname = new String(bookname.getBytes("iso8859_1"),"GBK");
                  user_money = bid.getUser_Money();
                  buytime = String.valueOf(bid.getBuytime());
                  buytime = buytime.substring(0,16);
              %>
                <tr  bgcolor="#FFFFFF">
                  <td align="center" class="txt">
                     <%=bookname%>
                  </td>
                  <td align="center" class="txt">
                   <%=df.format(user_money)%>
                  </td>
                  <td align="center" class="txt">
                   <%=buytime%>
                  </td>
                </tr>
              <%
                }
            %>

               </table>
            </td>
          </tr>
        </table>
      </td>
</tr>
</table>
<table>
<tr valign="bottom">
<td>
 总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
</td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td>
<%
    if((startrow-range)>=0){
%>
[<a href="userbid.jsp?startrow=<%=startrow-range%>userid=<%=userid%>">上一页</a>]
<%}
  if((startrow+range)<rows){
%>
[<a href="userbid.jsp?startrow=<%=startrow+range%>userid=<%=userid%>">下一页</a>]
<%}

  if(totalpages>1){%>
  &nbsp;&nbsp;第<input type="text" name="jump" value=<%=currentpage%> size="3">页&nbsp;
  <a href="#" onclick="golist((document.all('jump').value-1) * <%=range%>);">GO</a>
  <%}%>

</td>
</tr>
</table>
</center>
</form>
</center>
</body>
</html>
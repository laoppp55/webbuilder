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
  int startflag            = ParamUtil.getIntParameter(request, "startflag", 0);
  int startrow            = ParamUtil.getIntParameter(request, "startrow", 0);
  int range               = ParamUtil.getIntParameter(request, "range", 2);
  int bidid        = ParamUtil.getIntParameter(request,"bidid",-1);
  String bookname  = ParamUtil.getParameter(request,"bookname");
  String what    = ParamUtil.getParameter(request,"what");
  if((what=="")||(what==null))
    what ="";

  float user_money = 0;
  String buytime = "";

  IBidManager bidMgr = BidPeer.getInstance();
  Bid bid = new Bid();

  List list = new ArrayList();
  List currentlist = new ArrayList();
  list = bidMgr.listDetailBid(0,0,bidid,what);
  currentlist = bidMgr.listDetailBid(startrow,range,bidid,what);

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

</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>

<form action="#" method="post" name="detailform">
<input type="hidden" name="startflag" value="1">
<center>
<table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="2" cellspacing="1">
          <tr bgcolor="#d4d4d4" align="center" valign="middle">
           <td  class="txt">出价人ID查询 &nbsp;&nbsp;

                     <input type="text" name="what" size="15">

           </td>
           <td >
           <input type="submit" value="查询">
           </td>

          </tr>
          <tr bgcolor="#F4F4F4" align="center" >
           <td class="moduleTitle" colspan="2">
          <%if((startflag==1)){%>
            (
            查询
             <%if(startflag==1){%>
               &nbsp;&nbsp;用户ID:"<%=what%>"
             <%}%>
            )
          <%}%>
           <br>
            <font color="#48758C">“<%=bookname%>”&nbsp;竞标出价详单</font>
           </td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td colspan="2">
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td align="center" class="txt">用户ID</td>
                  <td align="center" class="txt">用户出价</td>
                  <td align="center" class="txt">出价时间</td>
                  <td></td>
                </tr>

              <%
                for(int i=0;i<currentlist.size();i++){
                  bid = (Bid)currentlist.get(i);
                  userid = bid.getUserID();
                  userid = userid==null?"":new String(userid.getBytes("iso8859_1"),"GBK");
                  user_money = bid.getUser_Money();
                  buytime = String.valueOf(bid.getBuytime());
                  buytime = buytime.substring(0,16);
              %>
                <tr  bgcolor="#FFFFFF">
                  <td align="center" class="txt">
                     <%=userid%>
                  </td>
                  <td align="center" class="txt">
                   <%=df.format(user_money)%>
                  </td>
                  <td align="center" class="txt">
                   <%=buytime%>
                  </td>
                  <td align="center" class="txt">
                  <a href="detail_user.jsp?userid=<%=userid%>" target=_blank>用户信息</a>
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
</center>
</form>
</center>
</body>
</html>
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

  int startrow            = ParamUtil.getIntParameter(request, "startrow", 0);
  int range               = ParamUtil.getIntParameter(request, "range", 100);
  int searchflag          = ParamUtil.getIntParameter(request, "searchflag", 0);

  IBidManager bidMgr = BidPeer.getInstance();
  Bid bid = new Bid();
  ISearchManager searchMgr = SearchPeer.getInstance();
  Search search = new Search();

  List list = new ArrayList();
  List currentlist = new ArrayList();

  String booknames = "";
  int newflags = -1;
  String jumpstr = "";

  int currentrows = 0;
  int totalrows = 0;
  int row = 0;
  int rows = 0;
  int totalpages = 0;
  int currentpage = 0;

    list = bidMgr.listBidBooks(0,0,"0");
    currentlist = bidMgr.listBidBooks(startrow,range,"0");

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


  if(searchflag==1){
    booknames     = ParamUtil.getParameter(request,"bookname");
    newflags      = ParamUtil.getIntParameter(request,"newflag",-1);
    String sqlstr = "select * from tbl_bid where bidflag=0 ";
    if((booknames!="")&&(booknames!=null)){
       sqlstr = sqlstr + " and bookid in "+
                "(select bookid from tbl_bookinfo where bookname like '%"+booknames+"%')";
       jumpstr = jumpstr + "&bookname="+booknames;
    }
    if(newflags!=-1){
       sqlstr = sqlstr + " and bookid in " +
                "(select bookid from tbl_bookinfo where newflag = "+newflags+")";
       jumpstr = jumpstr + "&newflag=" + newflags;
    }

    sqlstr = sqlstr.replaceAll("@","%");
    list = bidMgr.listBidBooks(0,0,sqlstr);
    currentlist = bidMgr.listBidBooks(startrow,range,sqlstr);

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
    alert("请您选择要结束的竞标单！");
    return false;
  }else{
    var val;
    val = confirm("您确定要提前结束这些竞标吗？");
    if(val){
      bidform.action="end.jsp";
      bidform.submit();
      return true;
    }
    else
      return false;
  }
}

  function gotoSearch(r){
    SearchForm.action = "index.jsp?searchflag=1";
    SearchForm.submit();
  }

  function jumppage(r,str){
    window.location = "index.jsp?startrow="+r+"&searchflag=1"+str;
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
<form name="bidform" method="post" action="index.jsp">
  <table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF" width="90%">
    <tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">竞标管理(竞标中图书)</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td align="center">修改结束标志</td>
                  <td align="center">图书名称</td>
                  <td align="center">图书类型</td>
                  <td align="center">起拍价</td>
                  <td align="center">最高竞标</td>
                  <td align="center">出价人</td>
                  <td align="center">浏览次数</td>
                  <td align="center">出价次数</td>
                  <td align="center">剩余时间</td>
                  <td></td>
                </tr>
                <%
                  for(int i=0;i<currentlist.size();i++){
                    bid = (Bid)currentlist.get(i);
                    search = searchMgr.getABook(bid.getBookID());
                    int browsenum = bid.getBrowseNum();
                    int buynum = bid.getBuynum();

                    int newflag = search.getNewFlag();
                    String booktype = "";
                    if(newflag == 0){
                      booktype = "新书";
                    }else if(newflag == 1){
                      booktype = "旧书";
                    }else if(newflag == 2){
                      booktype = "古籍";
                    }else if(newflag == 3){
                      booktype = "民国书";
                    }
                    Timestamp nowtime = new Timestamp(System.currentTimeMillis());
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.util.Date now = df.parse(nowtime.toString());
                    java.util.Date date=df.parse(bid.getEndtime().toString());
                    long l=date.getTime()-now.getTime();
                    long day=l/(24*60*60*1000);
                    long hour=(l/(60*60*1000)-day*24);
                    long min=((l/(60*1000))-day*24*60-hour*60);
                    long s=(l/1000-day*24*60*60-hour*60*60-min*60);

                    boolean saleflag = true;;
                    if(day < 0){
                      day = 0;
                      saleflag = false;
                    }
                    if(hour < 0){
                      hour = 0;
                      saleflag = false;
                    }
                    if(min < 0){
                      min = 0;
                      saleflag = false;
                    }
                    if(s < 0){
                      s = 0;
                      saleflag = false;
                    }

                    //判断延迟时间
                    boolean delayflag = false;
                    long ll = date.getTime() - now.getTime() + 300000 ;
                    long minl = ll/60000;
                    long sl = (ll%60000)/1000;

                    if(minl < 0){
                      minl = 0;
                    }
                    if(sl < 0){
                      sl = 0;
                    }

                    if((minl==0)&&(sl==0)){
                      saleflag = false;
                      delayflag = false;
                    }else{
                      saleflag = true;
                      delayflag = true;
                    }

                    String lefttime = "";
                    if((day ==0)&&(hour == 0)&&(min == 0)&&(s == 0)){
                      if((minl==0)&&(sl==0)){
                        lefttime = "竞标结束";
                      }else{
                        lefttime = minl + "分" + sl + "秒(延时)";
                      }
                    }else{
                      lefttime = day + "天" + hour + "小时" + min + "分" + s + "秒";
                    }

                    String begintime = "";
                    begintime = String.valueOf(bid.getBegintime());
                    begintime = begintime.substring(0,16);
                    /*Timestamp thistime = new Timestamp(System.currentTimeMillis());
                    if(thistime.before(bid.getBegintime()))
                      lefttime = "竞标启始时间："+ begintime;*/
                    String bookname = "";
                    bookname = new String(search.getBookName().getBytes("iso8859_1"),"GBK");
                %>
                <tr  bgcolor="#FFFFFF">
                  <td align="center" class="txt"><input type="checkbox" name="end" value="<%=bid.getID()%>"></td>
                  <td align="center" class="txt">
                    <%=bookname%>
                  </td>
                  <td align="center" class="txt"><%=booktype%></td>
                  <td align="center" class="txt"><%=bid.getBegin_Money()%>元</td>
                  <td align="center" class="txt"><%=bid.getHigh_Money()%>元</td>
                  <td align="center" class="txt">
                  <a href="userbid.jsp?userid=<%=bid.getUserID()==null?"":new String(bid.getUserID().getBytes("iso8859_1"),"GBK")%>" target=_blank>
                   <%=bid.getUserID()==null?"":new String(bid.getUserID().getBytes("iso8859_1"),"GBK")%>
                  </a>
                  </td>
                  <td align="center" class="txt"><%=browsenum%></td>
                  <td align="center" class="txt"><%=buynum%>
                   <a href="detail_bid.jsp?bidid=<%=bid.getID()%>&bookname=<%=bookname%>" target=_blank>
                   查看详细
                   </a>
                  </td>
                  <td align="center" class="txt"><%=lefttime%></td>
                  <td align="center" class="txt">
                   <a href="modify.jsp?id=<%=bid.getID()%>" target=_blank>修改</a>
                  </td>
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

<table>
<tr valign="bottom">
<td>
共有<%=rows%>项竞标&nbsp;&nbsp;总<%=totalpages%>页 第<%=currentpage%>页
</td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td>
<%
  if((startrow-range)>=0){
%>
[<a href="index.jsp?startrow=<%=startrow-range%>&searchflag=1<%=jumpstr%>">上一页</a>]
<%}%>
<%
  if((startrow+range)<rows){
%>
[<a href="index.jsp?startrow=<%=startrow+range%>&searchflag=1<%=jumpstr%>">下一页</a>]
<%}
  if(totalpages>1){
  %>
    &nbsp;&nbsp;第<input type="text" name="jump" value=<%=currentpage%> size="3">页&nbsp;
    <a href="###" onclick="jumppage((document.all('jump').value-1) * <%=range%>,'<%=jumpstr%>');">GO</a>
 <%}%>
</td>
</tr>
</table>
  <input type="submit" value="结束竟拍" onclick="javascript:return check(this.form);">
</form>


<table width="770" border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<form method="post" action="book.jsp" name="SearchForm">
<input type="hidden" name="searchflag" value="1">
<tr>
<td align="center" valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="8">
          <tr bgcolor="#F1F2EC">
            <td class="txt"><strong><font color="6F4A06">图书搜索：</font></strong></td>
          </tr>
        </table>
        <br>
        <table width="95%" border="0" cellspacing="0" cellpadding="0">
          <tr bgcolor="#d4d4d4">
            <td valign="top">
              <table width="100%" border="0" cellpadding="4" cellspacing="1">
                <tr bgcolor="#FFFFFF">
                  <td  valign="top" class="txt" width="50%">书名：&nbsp;
                    <input type="text" name="bookname" class="input">
                  </td>
                  <td class="txt">图书类型：&nbsp;
                    <select name="newflag">
                      <option>请选择...</option>
                      <option value="0">新书</option>
                      <option value="1">旧书</option>
                      <option value="3">民国书</option>
                      <option value="2">古籍</option>
                    </select>
                  </td>
                </tr>
              </table>
            </td>
</tr>
</table>
<table width="95%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="images/space.gif" width="1" height="1"></td>
</tr>
</table>
        <p><input type=button value="搜索" onclick="javascript:gotoSearch();"><br>
          <br>
        </p>
</td>
</tr>
</form>
</table>
</center>
</body>
</html>

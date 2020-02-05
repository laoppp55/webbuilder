<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.bid.*,
                com.booyee.search.*" contentType="text/html;charset=gbk"
%>
<%
  int id = ParamUtil.getIntParameter(request, "id", 0);
  int startflag = ParamUtil.getIntParameter(request, "startflag", 0);

  ISearchManager searchMgr = SearchPeer.getInstance();
  Search search = new Search();
  IBidManager bidMgr = BidPeer.getInstance();
  Bid bid = new Bid();
  bid = bidMgr.getABidBook(id);

  long bookid = 0;
  bookid = bid.getBookID();
  search = searchMgr.getABook(bookid);
  String bookname = search.getBookName();
  bookname = new String(bookname.getBytes("iso8859_1"),"GBK");


  String begintime_year = "";
  String begintime_month = "";
  String begintime_day = "";
  String endtime_year = "";
  String endtime_month = "";
  String endtime_day = "";
  String begintime_hour = "";
  String begintime_minute = "";
  String begintime_second = "";
  String endtime_hour = "";
  String endtime_minute = "";
  String endtime_second = "";

  String begintime = "";
  String endtime = "";

  begintime = String.valueOf(bid.getBegintime());
  endtime = String.valueOf(bid.getEndtime());

  begintime_year = begintime.substring(0,4);
  begintime_month = begintime.substring(5,7);
  begintime_day = begintime.substring(8,10);
  begintime_hour = begintime.substring(11,13);
  begintime_minute = begintime.substring(14,16);
  begintime_second = begintime.substring(17,19);

  endtime_year = endtime.substring(0,4);
  endtime_month = endtime.substring(5,7);
  endtime_day = endtime.substring(8,10);
  endtime_hour = endtime.substring(11,13);
  endtime_minute = endtime.substring(14,16);
  endtime_second = endtime.substring(17,19);

  if(startflag == 1){
    bookname                  = ParamUtil.getParameter(request, "bookname");
    String desc                      = ParamUtil.getParameter(request, "desc");
    float begin_money         = ParamUtil.getFloatParameter(request, "begin_money",0);
    float add_money           = ParamUtil.getFloatParameter(request, "add_money",0);
    begintime_year            = ParamUtil.getParameter(request, "begintime_year");
    begintime_month           = ParamUtil.getParameter(request, "begintime_month");
    begintime_day             = ParamUtil.getParameter(request, "begintime_day");
    endtime_year              = ParamUtil.getParameter(request, "endtime_year");
    endtime_month             = ParamUtil.getParameter(request, "endtime_month");
    endtime_day               = ParamUtil.getParameter(request, "endtime_day");
    begintime_hour            = ParamUtil.getParameter(request, "begintime_hour");
    begintime_minute          = ParamUtil.getParameter(request, "begintime_minute");
    begintime_second          = ParamUtil.getParameter(request, "begintime_second");
    endtime_hour              = ParamUtil.getParameter(request, "endtime_hour");
    endtime_minute            = ParamUtil.getParameter(request, "endtime_minute");
    endtime_second            = ParamUtil.getParameter(request, "endtime_second");


    begintime = begintime_year +"-"+ begintime_month +"-"+ begintime_day;
    endtime = endtime_year +"-"+ endtime_month +"-"+ endtime_day;
    begintime = begintime + " " + begintime_hour + ":" + begintime_minute + ":" + begintime_second;
    endtime = endtime + " " + endtime_hour + ":" + endtime_minute + ":" + endtime_second;

    //bookname = new String(bookname.getBytes("iso8859_1"),"GBK");
    //desc = new String(desc.getBytes("iso8859_1"),"GBK");

    bid.setID(id);
    //bid.setBookNama(bookname);
    bid.setBegin_Money(begin_money);
    bid.setAdd_Money(add_money);
    bidMgr.updateBid(bid,begintime,endtime);
    bidMgr.updateDesc(bookid,desc,bookname);
    response.sendRedirect("index.jsp");
  }
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<meta http-equiv="Pragma" content="no-cache">

<script language="javascript">
  function isCharsInBag (s, bag)
  {
     var i;
    for (i = 0; i < s.length; i++)
    {
     var c = s.charAt(i);
     if (bag.indexOf(c) == -1) return false;
    }
    return true;
  }
function update()
{
  if((modifyForm.begin_money.value == "")||(modifyForm.begin_money.value == null)){
     alert("请输入起拍价！");
     return false;
  }
  if(!isCharsInBag (modifyForm.begin_money.value, "0123456789.")){
    alert("请正确输入起拍价！");
    return false;
  }
  if((modifyForm.add_money.value == "")||(modifyForm.add_money.value == null)){
     alert("请输入每次加价！");
     return false;
  }
  if(!isCharsInBag (modifyForm.add_money.value, "0123456789.")){
    alert("请正确输入每次加价！");
    return false;
  }
  if((modifyForm.begintime_year.value == "")||(modifyForm.begintime_year.value == null)){
     alert("请输入开始时间(年)！");
     return false;
  }
  if(!isCharsInBag(modifyForm.begintime_year.value, "0123456789")){
    alert("请正确输入开始时间(年)！");
    return false;
  }
  if((modifyForm.begintime_month.value == "")||(modifyForm.begintime_month.value == null)){
     alert("请输入开始时间(月)！");
     return false;
  }
  if(!isCharsInBag(modifyForm.begintime_month.value, "0123456789")){
    alert("请正确输入开始时间(月)！");
    return false;
  }
  if((modifyForm.begintime_day.value == "")||(modifyForm.begintime_day.value == null)){
     alert("请输入开始时间(日)！");
     return false;
  }
  if(!isCharsInBag(modifyForm.begintime_day.value, "0123456789")){
    alert("请正确输入开始时间(日)！");
    return false;
  }
  if((modifyForm.endtime_year.value == "")||(modifyForm.endtime_year.value == null)){
     alert("请输入结束时间(年)！");
     return false;
  }
  if(!isCharsInBag(modifyForm.endtime_year.value, "0123456789")){
    alert("请正确输入结束时间(年)！");
    return false;
  }
  if((modifyForm.endtime_month.value == "")||(modifyForm.endtime_month.value == null)){
     alert("请输入结束时间(月)！");
     return false;
  }
  if(!isCharsInBag(modifyForm.endtime_month.value, "0123456789")){
    alert("请正确输入结束时间(月)！");
    return false;
  }
  if((modifyForm.endtime_day.value == "")||(modifyForm.endtime_day.value == null)){
     alert("请输入结束时间(日)！");
     return false;
  }
  if(!isCharsInBag(modifyForm.endtime_day.value, "0123456789")){
    alert("请正确输入结束时间(日)！");
    return false;
  }
  return true;
}
</script>
</head>
<body>
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
<form action="modify.jsp" method="post" name="modifyForm">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="id" value="<%=id%>">
<table width="770" border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">添加竞标信息</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
                <table width="100%" border="0" cellpadding="2" cellspacing="1">
                  <!--DWLayoutTable-->
                  <tr bgcolor="#FFFFFF">
                    <td width="20%">图书名称：</td>
                    <td width="60%"> <input type="text" value="<%=bookname%>" size="50" name="bookname">
                    </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td width="20%">标品描述：</td>
                    <%
                      String desc = "";
                      desc = search.getDesc();
                      if((desc != "")&&(desc != null)){
                        desc = new String(desc.getBytes("iso8859_1"),"GBK");
                      }else{
                        desc = "";
                      }
                    %>
                    <td width="60%"> <input type="text" value="<%=desc%>" size="50" name="desc">
                    </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>起拍价：</td>
                    <td> <input type="text" name="begin_money" size="10" value=<%=bid.getBegin_Money()%>> 元<font color="#FF0000">&nbsp;
                      </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>每次加价：</td>
                    <td> <input name="add_money" type="text" id="desc" size="10" value=<%=bid.getAdd_Money()%>> 元
                      <font color="#FF0000">&nbsp; </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>开始时间：</td>
                    <td> <input name="begintime_year" type="text" id="begintime_year" size="4" maxlength="4" value=<%=begintime_year%>>
                      年
                      <input name="begintime_month" type="text" size="3" maxlength=2 value=<%=begintime_month%>>
                      月
                      <input name="begintime_day" type="text" size="3" maxlength=2 value=<%=begintime_day%>>
                      日&nbsp;

                    <input name="begintime_hour" type="text" id="begintime_hour" size="2" maxlength="2" value="<%=begintime_hour%>">
                       时
                      <input name="begintime_minute" type="text" id="begintime_minute" size="2" maxlength="2" value="<%=begintime_minute%>">
                       分
                      <input name="begintime_second" type="text" id="begintime_second" size="2" maxlength="2" value="<%=begintime_second%>">
                       秒
                    </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>结束时间：</td>
                    <td> <input name="endtime_year" type="text" id="endtime_year" size="4" maxlength="4" value=<%=endtime_year%>>
                      年
                      <input name="endtime_month" type="text" size="3" maxlength=2 value=<%=endtime_month%>>
                      月
                      <input name="endtime_day" type="text" size="3" maxlength=2 value=<%=endtime_day%>>
                      日&nbsp;
                    <input name="endtime_hour" type="text" id="endtime_hour" size="2" maxlength="2" value=<%=endtime_hour%>>
                       时
                      <input name="endtime_minute" type="text" id="endtime_minute" size="2" maxlength="2" value=<%=endtime_minute%>>
                       分
                      <input name="endtime_second" type="text" id="endtime_second" size="2" maxlength="2" value=<%=endtime_second%>>
                       秒
                    </td>
                    <td>&nbsp;</td>
                  </tr>
                </table>
            </td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr align="center">
            <td><input type="submit" name="Submit" value="确定" onclick="javascript:return update();">
            <input type="button" name="button" value="返回" onclick="javascript:history.go(-1);"></td>
          </tr>
        </table>
      </td>
</tr>
</table>
  </form>
</center>
</body>
</html>

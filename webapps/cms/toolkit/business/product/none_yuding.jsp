<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.news.*,
                com.bizwink.cms.server.*,
                com.bizwink.cms.tree.*,
                com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                com.booyee.search.*" contentType="text/html;charset=gbk"
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
  String userid = "";
  ISearchManager searchMgr = SearchPeer.getInstance();


  String yd_bookname = ParamUtil.getParameter(request,"yd_bookname");
  String yd_author = ParamUtil.getParameter(request,"yd_author");
  String yd_publisher = ParamUtil.getParameter(request,"yd_publisher");
  String yd_wanter = ParamUtil.getParameter(request,"yd_wanter");
  int searchflag = ParamUtil.getIntParameter(request,"searchflag",-1);


  Search search = new Search();
  List list = new ArrayList();
  List currentlist = new ArrayList();
  int currentrows = 0;
  int totalrows = 0;

  if(searchflag==1){
   list = searchMgr.searchYuDing(yd_bookname,yd_author,yd_publisher,yd_wanter);
   currentlist = searchMgr.searchcurrentYuDing(yd_bookname,yd_author,yd_publisher,yd_wanter,startrow,range);
  }else{
   list = searchMgr.listWantedBooks(0,0);
   currentlist = searchMgr.listWantedBooks(startrow,range);
  }

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
    val = confirm("您确定要删除选定信息吗？");
    if(val){
      payform.action="delwanted.jsp";
      payform.submit();
      return true;
    }else{
      return false;
    }
  }
}

function search(){
  payform.action="yuding.jsp";
  payform.submit();
}
</script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<%
      String[][] titlebars = {
              { "首页", "" },
              { "图书管理", "" },
              { "库存图书", "" }
          };

      String[][] operations = {
        { "库存图书", "book.jsp" },{"    ",""},
        { "图书批量入库", "piliang.jsp" },{"    ",""},
       // { "图书出库", "chuku.jsp" },{"    ",""},
        { "图书退货", "tuihuo.jsp" },{"    ",""},
        { "图书上/下架", "shangjia.jsp" },{"    ",""},
        { "预定图书", "yuding.jsp" }
          };
%>
<%@ include file="../inc/titlebar.jsp" %>

<form action="#" method="post" name="payform">
<center>
<table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF" width="90%">
 <tr>
  <td>
    <input type="hidden" name="searchflag" value="1">
    <table width="100%" border="0" cellpadding="0">
      <tr bgcolor="#F4F4F4" align="center">
       <td class="txt">预定图书名称
        <input type="text" name="yd_bookname" >
       </td>
       <td class="txt">预定图书著作人
        <input type="text" name="yd_author" >
       </td>
       <td class="txt">预定图书出版人
        <input type="text" name="yd_publisher" >
       </td>
       <td class="txt">预定用户ID
        <input type="text" name="yd_wanter" >
       </td>
       <td class="txt">
         <input type="button" value="搜索" onclick="search();">
       </td>
      </tr>
    </table>
  </td>
 </tr>
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">图书预定管理</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td>&nbsp;&nbsp;</td>
                  <td>图书名称</td>
                  <td>著作人</td>
                  <td>出版人</td>
                  <td>求书用户</td>
                </tr>
                  <%
                     String bookname;
                     String author;
                     String publisher;
                     String wanter;
                     int wantedid;
                 for(int i=0 ;i < currentlist.size() ;i++){
                  search = (Search)currentlist.get(i);
                  bookname = search.getwant_bookname();
                  bookname = new String(bookname.getBytes("iso8859_1"),"GBK");
                  author = search.getwant_author();
                  if((author!="")&&(author!=null)){
                    author = new String(author.getBytes("iso8859_1"),"GBK");
                  }
                  publisher = search.getwant_publisher();
                  if((publisher!="")&&(publisher!=null)){
                    publisher = new String(publisher.getBytes("iso8859_1"),"GBK");
                  }
                  wanter = search.getwant_wanter();
                  if((wanter!="")&&(wanter!=null)){
                    wanter = new String(wanter.getBytes("iso8859_1"),"GBK");
                  }
                  wantedid = search.getwant_id();

                  %>
                <tr  bgcolor="#FFFFFF">
                  <td > <input type="checkbox" name="delwanted" value="<%=wantedid%>"></td>
                  <td><%=bookname==null?"":bookname%></td>
                  <td><%=author==null?"":author%></td>
                  <td><%=publisher==null?"":publisher%></td>
                  <td><%=wanter==null?"":wanter%></td>
                </tr>
                <%}%>
               </table>
            </td>
          </tr>
        </table>
      </td>
</tr>
<tr><td align="left">&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="chkAll" value="on"  onclick="javascript:CheckAll(this.form);">全部选中</td></tr>
</table>
<table>
<tr>
<td>
共有<%=rows%>本书&nbsp;&nbsp;总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
</td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td>
<%
  if((startrow-range)>=0){
    if(searchflag!=1){
%>
[<a href="yuding.jsp?startrow=<%=startrow-range%>">上一页</a>]
<%}
if(searchflag==1){%>
[<a href="yuding.jsp?startrow=<%=startrow-range%>&searchflag==1&yd_bookname=<%=yd_bookname%>&yd_author=<%=yd_author%>&yd_publisher=<%=yd_publisher%>&yd_wanter=<%=yd_wanter%>">上一页</a>]
<%}
}%>
<%
  if((startrow+range)<rows){
    if(searchflag!=1){
%>
[<a href="yuding.jsp?startrow=<%=startrow+range%>">下一页</a>]
<%}
if(searchflag==1){%>
[<a href="yuding.jsp?startrow=<%=startrow+range%>&searchflag==1&yd_bookname=<%=yd_bookname%>&yd_author=<%=yd_author%>&yd_publisher=<%=yd_publisher%>&yd_wanter=<%=yd_wanter%>">上一页</a>]
<%}
}%>
</td>
</tr>
</table>
<input type="button" value="删除" onclick="javascript:return check(this.form);">
</center>
</form>
</center>
</body>
</html>
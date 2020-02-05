<%@ page import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Other.*"
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
  int range               = ParamUtil.getIntParameter(request, "range", 50);
  int searchflag          = ParamUtil.getIntParameter(request, "searchflag", 0);
  int userid              = ParamUtil.getIntParameter(request,"userid",0);
  String username         = ParamUtil.getParameter(request,"username");

  IOtherManager otherMgr = otherPeer.getInstance();
  Application appli = new Application();

  List currentlist = new ArrayList();
  int currentrows = 0;
  int totalrows = 0;
  int row = 0;
  int rows = 0;
  int totalpages = 0;
  int currentpage = 0;
  int kind=4;
  int status=2;
  currentlist = otherMgr.getApplicationList(kind,userid,username,startrow,range,siteid);
  row =0;
  rows = otherMgr.getApplicationNum(kind,status,userid,username,siteid);
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
<script language="JavaScript" src="../include/setday.js" ></script>
<meta http-equiv="Pragma" content="no-cache">
<script language="javascript">
  function golist(r){
    window.location = "dealed.jsp?startrow="+r;

  }

  function gotosearch(){
    searchForm.action = "dealed.jsp";
    searchForm.submit();
  }

  function jumppage(r,str){
    window.location = "dealed.jsp?startrow="+r+"&"+str;
  }

  function dodeal(id,kind,v)
  {
    window.location = "dodeal.jsp?id="+id+"&kind="+kind+"&status="+v+"&userid=<%=userid%>&username=<%=username%>&startrow=<%=startrow%>&range=<%=range%>&backstr=dealed.jsp";
  }
</script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<%
      String[][] titlebars = {
              { "首页", "" },
              { "订单管理", "" }
          };

      String[][] operations = {
              { "保修申请", "index.jsp" },{"",""},
              { "更换申请", "change.jsp" },{"",""},
              { "退货申请", "back.jsp" },{"",""},
              { "以处理申请", "dealed.jsp" },{"",""}
          };
%>
<%@ include file="../inc/titlebar.jsp" %>
<center>
<form name="searchForm" method="post" action="dealed.jsp">
<input type="hidden" name="searchflag" value="1">
<input type="hidden" name="range" value=<%=range%>>

  <table width="100%" border="0" cellpadding="3" cellspacing="1">
    <tr bgcolor="#FFFFFF">
      <td valign="bottom" class="txt">用户ID：
        <input type="text" name="username" size="20">
        <input type=button value="查询" onclick="javascript:gotosearch();" style="height:19;font-size:9pt;">
      </td>
    </tr>
  </table>
</form>
</center>

<form action="dealed.jsp" method="post" name="repairform">
<input type="hidden" name="updateflag" value="1">
<center>
<table border="0" width="100%"  cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">已处理完毕申请</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td align="center" width="10%">用户ID</td>
                  <td align="center" width="6%">订单编号</td>
                  <td align="center" width="17%">产品名称(编号)</td>
                  <td align="center" width="44%">详细说明</td>
                  <td align="center" width="9%">申请日期</td>
                  <td align="center" width="8%">事由</td>
                  <td align="center" width="6%">&nbsp;</td>
                </tr>
                  <%
                     String createdate = "";
                     String notes = "";
                     String productname = "";
                     int tmpkind=0;
                     for(int i=0 ;i < currentlist.size() ;i++){
                       appli = (Application)currentlist.get(i);
                       createdate = appli.getCreatedate()==null?"":String.valueOf(appli.getCreatedate()).substring(0,10);
                       notes = appli.getNotes()==null?null:appli.getNotes();
                       notes = notes==null?"":StringUtil.gb2iso4View(notes);
                       username = appli.getUserName()==null?"":StringUtil.gb2iso4View(appli.getUserName());
                       productname = appli.getProductName()==null?"":StringUtil.gb2iso4View(appli.getProductName());

                  %>
                <tr  bgcolor="#FFFFFF">
                  <td align="center">
                   <a href="dealed.jsp?userid=<%=appli.getUserID()%>" target=_blank><%=username%></a>
                  </td>
                  <td align="center">
                   <a href="../order/chuku1.jsp?orderid=<%=appli.getOrderID()%>" target=_blank><%=appli.getOrderID()%></a>
                  </td>
                  <td align="center">
                   <%=productname%>(<%=appli.getProductID()%>)
                  </td>
                  <td align="center"><%=notes%></td>
                  <td align="center"><%=createdate%></td>
                  <td align="center">
                   <%if(appli.getRepairFlag()==2){%>
                    <font color=red>批准</font><font color=blue>保修</font>
                   <%tmpkind=1;}%>
                   <%if(appli.getRepairFlag()==3){%>
                    <font color=red>无法</font><font color=blue>保修</font>
                   <%tmpkind=1;}%>
                   <%if(appli.getExchangeFlag()==2){%>
                    <font color=red>批准</font><font color=blue>更换</font>
                   <%tmpkind=2;}%>
                   <%if(appli.getExchangeFlag()==3){%>
                    <font color=red>无法</font><font color=blue>更换</font>
                   <%tmpkind=2;}%>
                   <%if(appli.getBackFlag()==2){%>
                    <font color=red>批准</font><font color=blue>退货</font>
                   <%tmpkind=3;}%>
                   <%if(appli.getBackFlag()==3){%>
                    <font color=red>无法</font><font color=blue>退货</font>
                   <%tmpkind=3;}%>
                  </td>
                  <td align="center"><button style="width:50;height:19;font-size:9pt" onclick="dodeal(<%=appli.getID()%>,<%=tmpkind%>,1)">无效</button></td>
                </tr>
                <%}%>
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
  if(searchflag == 0){
    if((startrow-range)>=0){
%>
[<a href="dealed.jsp?startrow=<%=startrow-range%>">上一页</a>]
<%}
  if((startrow+range)<rows){
%>
[<a href="dealed.jsp?startrow=<%=startrow+range%>">下一页</a>]
<%}

  if(totalpages>1){%>
  &nbsp;&nbsp;第<input type="text" name="jump" value=<%=currentpage%> size="3">页&nbsp;
  <a href="#" onclick="golist((document.all('jump').value-1) * <%=range%>);">GO</a>
  <%}

  }else if(searchflag != 0){
    if((startrow-range)>=0){
%>
  [<a href="dealed.jsp?userid=<%=userid%>&username=<%=username%>&startrow=<%=startrow-range%>">上一页</a>]
  <%}%>
  <%
    if((startrow+range)<rows){
  %>
  [<a href="dealed.jsp?userid=<%=userid%>&username=<%=username%>&startrow=<%=startrow+range%>">下一页</a>]
 <%}
   if(totalpages>1){%>
   &nbsp;&nbsp;第<input type="text" name="jump" value=<%=currentpage%> size="3">页&nbsp;
   <a href="###" onclick="jumppage((document.all('jump').value-1) * <%=range%>,'userid=<%=userid%>&username=<%=username%>');">GO</a>
<%}
}%>
</td>
</tr>
</table>
</center>
</form>


</center>
</body>
</html>
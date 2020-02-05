<%@ page import="java.sql.*,
                 java.util.*,
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
  int range               = ParamUtil.getIntParameter(request, "range", 100);
  int searchflag          = ParamUtil.getIntParameter(request,"searchflag",-1);
  String jumpstr = "&searchflag=1";

  IOtherManager otherMgr = otherPeer.getInstance();
  Other other = new Other();
  List list = new ArrayList();
  List currentlist = new ArrayList();
  int currentrows = 0;
  int totalrows = 0;
  list = otherMgr.getGHList(startrow,0,"");
  currentlist = otherMgr.getGHList(startrow,range,"");

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
  String kind;
  String name;
  String lianxiren;
  String address;
  String postcode;
  String telphone;
  String email;
  String notes;

  if(searchflag==1)
  {
    lianxiren = ParamUtil.getParameter(request,"lianxiren");
    name      = ParamUtil.getParameter(request,"name");
    kind      = ParamUtil.getParameter(request,"kind");
    String sqlstr ="select * from en_gonghuo where 1=1 ";
    if(lianxiren != null && !"".equals(lianxiren))
    {
      sqlstr = sqlstr + " and lianxiren like '@"+lianxiren+"@'";
      jumpstr = jumpstr + "&lianxiren="+lianxiren;
    }

    if(name != null && !"".equals(name))
    {
      sqlstr = sqlstr + " and name like '@"+name+"@'";
      jumpstr = jumpstr + "&name="+name;
    }

    if(kind != null && !"".equals(kind))
    {
      sqlstr = sqlstr + " and kind like '@"+kind+"@'";
      jumpstr = jumpstr + "&kind="+kind;
    }

    sqlstr = sqlstr.replaceAll("@","%");
    list = otherMgr.getGHList(startrow,range,sqlstr);
    currentlist = otherMgr.getGHList(startrow,range,sqlstr);
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
  function delgh(id){
    var val;
    val = confirm("你确定要删除这条供货商信息？");
    if(val){
      gonghuoform.action = "delgh.jsp?gh_id="+id;
      gonghuoform.submit();
      return true;
    }
    return false;
  }

  function gotosearch(){
    searchForm.action = "index.jsp";
    searchForm.submit();
  }
</script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<%
      String[][] titlebars = {
              { "首页", "" },
              { "供货商管理", "" }
          };

      String[][] operations = {
              { "添加供货商", "addnew.jsp" },{"",""}
          };
%>
<%@ include file="../inc/titlebar.jsp" %>

<form action="#" method="post" name="gonghuoform">
<center>
<table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">供货商信息</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td>供货商名称</td>
                  <td>供货类型</td>
                  <td>联系人</td>
                  <td>地址</td>
                  <td>邮编</td>
                  <td>电话</td>
                  <td>电子邮件</td>
                  <td>备注</td>
                  <td>&nbsp;&nbsp;&nbsp;</td>
                 </tr>
                  <%

                     for(int i=0 ;i < currentlist.size() ;i++){
                      other      = (Other)currentlist.get(i);
                      kind       = StringUtil.gb2iso4View(other.getGH_Kind());
                      name       = StringUtil.gb2iso4View(other.getGH_Name());
                      lianxiren  = StringUtil.gb2iso4View(other.getGH_Lianxiren());
                      address    = StringUtil.gb2iso4View(other.getGH_Address());
                      postcode   = StringUtil.gb2iso4View(other.getGH_Postcode());
                      telphone   = StringUtil.gb2iso4View(other.getGH_Phone());
                      email      = StringUtil.gb2iso4View(other.getGH_Email());
                      notes      = StringUtil.gb2iso4View(other.getGH_Notes());
                  %>
                <tr  bgcolor="#FFFFFF">
                  <td><%=name==null?"":name%></td>
                  <td><%=kind==null?"":kind%></td>
                  <td><%=lianxiren==null?"":lianxiren%></td>
                  <td><%=address==null?"":address%></td>
                  <td><%=postcode==null?"":address%></td>
                  <td><%=telphone==null?"":telphone%></td>
                  <td><%=email==null?"":email%></td>
                  <td><%=notes==null?"":notes%></td>
                  <td><a href="#" onclick="javascript:return delgh(<%=other.getGH_ID()%>);">删除</a></td>
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
<tr>
<td>
<td>
<%
  if(searchflag == 0){
    if((startrow-range)>=0){
%>
[<a href="index.jsp?startrow=<%=startrow-range%>">上一页</a>]
<%}
  if((startrow+range)<rows){
%>
[<a href="index.jsp?startrow=<%=startrow+range%>">下一页</a>]
<%}}else if(searchflag != 0){
    if((startrow-range)>=0){
%>
  [<a href="index.jsp?startrow=<%=startrow-range%><%=jumpstr%>">上一页</a>]
  <%}%>
  <%
    if((startrow+range)<rows){
  %>
  [<a href="index.jsp?startrow=<%=startrow+range%><%=jumpstr%>">下一页</a>]
 <%}}%>
</td>
</tr>
</table>
</center>
</form>
<center>
<table width="60%" border="0" cellpadding="0">
<tr>
<td background="images/dot-line.gif"></td>
</tr>
<tr bgcolor="#d4d4d4" align="right">
<td bgcolor="#DCE4E7">
<form name="searchForm" method="post" action="index.jsp">
<input type="hidden" name="searchflag" value="1">
<input type="hidden" name="range" value=<%=range%>>
<table width="100%"  border="0" cellpadding="2">
<tr>
<td class="txt"><font color="#59697B"><strong>供货商查询</strong></font> </td>
</tr>
</table>
  <table width="100%" border="0" cellpadding="3" cellspacing="1">
    <tr bgcolor="#FFFFFF">
      <td width="30%" valign="bottom" class="txt">供货商名称：</td>
      <td width="70%" valign="top" class="txt">
        <input type="text" name="name" size="20">
        <font color="#FF0000">&nbsp; </font></td>
    </tr>
    <tr bgcolor="#FFFFFF">
      <td valign="bottom" class="txt">供货类型：</td>
      <td colspan="1" class="txt">
        <input type="text" name="kind" size="20">
      </td>
    </tr>
    <tr bgcolor="#FFFFFF">
      <td valign="bottom" class="txt">联系人：</td>
      <td colspan="1" class="txt">
        <input type="text" name="lianxiren" size="20">
      </td>
    </tr>

    <tr><td colspan=2 align="center" valign="center"><input type=button value="查询" onclick="javascript:gotosearch();"></td></tr>
  </table>
</form>
</td></tr></table>
</center>

</center>
</body>
</html>
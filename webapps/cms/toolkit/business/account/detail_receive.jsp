<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Other.*,
                 com.bizwink.cms.business.Order.*"
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

  int startflag =        ParamUtil.getIntParameter(request,"startflag",0);
  int kind      =        ParamUtil.getIntParameter(request,"kind",1);
  String what1 = ParamUtil.getParameter(request,"what1");
  String what2 = ParamUtil.getParameter(request,"what2");
  String userid = ParamUtil.getParameter(request,"userid");
  int startrow = ParamUtil.getIntParameter(request,"startrow",0);
  int range    = ParamUtil.getIntParameter(request,"range",20);
  String username = ParamUtil.getParameter(request,"username");

    if((what1=="")||(what1==null))
      what1 = "";
    if((what2=="")||(what2==null))
      what2 = "";

  String jingbanren = "";
  float money = 0;
  String desc = "";
  String createdate = "";
  String payer = "";
  String payway = "";
  String unit = "";

  String realname = "";

  IOtherManager otherMgr = otherPeer.getInstance();
  List list = new ArrayList();
  ReceiveMoney remoney = new ReceiveMoney();

  String beginy = "";
  String beginm = "";
  String begind = "";
  String endy = "";
  String endm = "";
  String endd = "";

  if(kind==1)
  {
    beginy = ParamUtil.getParameter(request,"beginy");
    beginm = ParamUtil.getParameter(request,"beginm");
    begind = ParamUtil.getParameter(request,"begind");
    endy = ParamUtil.getParameter(request,"endy");
    endm = ParamUtil.getParameter(request,"endm");
    endd = ParamUtil.getParameter(request,"endd");
    if((beginy==null)||(beginy=="")) beginy = "1900";
    if((beginm==null)||(beginm=="")) beginm = "01";
    if((begind==null)||(begind=="")) begind = "01";
    if((endy==null)||(endy=="")) endy = "9999";
    if((endm==null)||(endm=="")) endm = "01";
    if((endd==null)||(endd=="")) endd = "01";
    if(Integer.parseInt(beginy)<1900) beginy = "1900";
    if(Integer.parseInt(beginm)>12) beginm = "01";
    if(Integer.parseInt(begind)>31) begind = "01";
    if(Integer.parseInt(endy)<1900) endy = "9999";
    if(Integer.parseInt(endm)>12) endm = "01";
    if(Integer.parseInt(endd)>31) endd = "01";

    what1 = beginy + "-"  + beginm + "-"  + begind + " 00:00:01";
    what2 = endy + "-" + endm + "-"  + endd + " 23:59:59";
  }

%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<meta http-equiv="Pragma" content="no-cache">
<script  language="JavaScript" type="text/JavaScript">
 function change(value){
   if(value==1){
     document.all('what1').disabled=true;
     get();
   }
   if(value==2){
     document.all('what1').disabled=false;
     get();
   }
   if(value==3){
     document.all('what1').disabled=false;
    get2();
   }
   if(value==4){
     document.all('what1').disabled=false;
     get();
   }
   if(value==5){
     document.all('what1').disabled=false;
     get();
   }
   if(value==6){
     document.all('what1').disabled=false;
     get();
   }
 }
 function get(){
    document.all('ww').style.visibility='hidden';
 }
 function get2(){
    document.all('ww').style.visibility='visible'
 }
</script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>

<%
  String[][] titlebars = {
          { "首页", "" },
          { "帐务管理", "" }
      };

  String[][] operations = {
          };
%>
<%@ include file="../inc/titlebar.jsp" %>

<form action="detail_receive.jsp" method="post" name="qudaoform">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="userid" value="<%=userid%>">
<input type="hidden" name="username" value="<%=username%>">
<center>
<table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="2" cellspacing="1">
          <tr bgcolor="#d4d4d4" align="center" valign="middle">
           <td colspan="3">
             &nbsp;查询自
             <input name="beginy" type="text" size="4" maxlength="4">年
             <input name="beginm" type="text" size="2" maxlength="2">月
             <input name="begind" type="text" size="2" maxlength="2">日
             &nbsp;&nbsp; 至&nbsp;&nbsp;
             <input name="endy" type="text" size="4" maxlength="4">年
             <input name="endm" type="text" size="2" maxlength="2">月
             <input name="endd" type="text" size="2" maxlength="2">日
           </td>
          </tr>

          <tr bgcolor="#d4d4d4" align="center" valign="middle">
          <td>
          <table><tr>
           <td  class="txt">查询方式 &nbsp;&nbsp;
             <select name="kind" onChange="javascript:change(document.all('kind').value);">
              <option value="1" <%if(kind==1){%>selected<%}%>>时间查询</option>
              <option value="2" <%if(kind==2){%>selected<%}%>>经办人</option>
              <option value="3" <%if(kind==3){%>selected<%}%>>金额</option>
              <option value="4" <%if(kind==4){%>selected<%}%>>付款人</option>
              <option value="5" <%if(kind==5){%>selected<%}%>>用户名</option>
              <option value="6" <%if(kind==6){%>selected<%}%>>用户真名</option>
             </select>&nbsp;&nbsp;为&nbsp;&nbsp;
             <input type="text" name="what1" size="15">
           </td>
           <td class="txt">
            <div id="ww" style="visibility:hidden;">
             至(可不输)<input type="text" name="what2" size="15">
            </div>
           </td>
            <%if(kind==1){%>
               <script language="javascript">
                 document.all('what1').disabled = true
               </script>
            <%}
              if(kind==3){%>
               <script language="javascript">
                 get2();
               </script>
            <%}%>
           <td >
           <input type="submit" value="查询">
           </td>
          </tr></table>
          </td>
          </tr>
          <tr bgcolor="#F4F4F4" align="center" >
           <td class="moduleTitle" colspan="3">

            (
             <%if(!userid.equals("")){%>&nbsp;用户帐号：<%=userid%>&nbsp;<%}%>
             <%if(kind==1){%>
               自<%=beginy%>-<%=beginm%>-<%=begind%>至<%=endy%>-<%=endm%>-<%=endd%>止
             <%}
               if(kind==2){%>
               &nbsp;&nbsp;查询经办人:"<%=what1%>"
             <%}
               if(kind==3){
              if(what1==what2){%>
               &nbsp;&nbsp;查询金额:"<%=what1%>"
             <%}else{%>
               &nbsp;&nbsp;查询金额:"<%=what1%>-<%=what2%>"
             <%}}
              if(kind==4){%>
               &nbsp;&nbsp;付款人:"<%=what1%>"
             <%}
              if(kind==5){%>
               &nbsp;&nbsp;查询用户名:"<%=what1%>"
             <%}
              if(kind==6){%>
               &nbsp;&nbsp;查询用户真名:"<%=what1%>"
             <%}%>
            )

           <br>
            <font color="#48758C">收款列表</font>
           </td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td colspan="3">
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td align="center" class="txt">金额</td>
                  <td align="center" class="txt">币种</td>
                  <td align="center" class="txt">付款人</td>
                  <td align="center" class="txt">经办人</td>
                  <td align="center" class="txt">用户名</td>
                  <td align="center" class="txt">付款方式</td>
                  <td align="center" class="txt">描述</td>
                  <td align="center" class="txt">记录时间</td>
                  <td align="center" class="txt"></td>
                </tr>

              <%
               IOrderManager orderMgr = orderPeer.getInstance();
               list = otherMgr.getReceiveMoneyList(kind,what1,what2,userid,startrow,range);
               for(int i=0; i<list.size();i++)
               {
                remoney = (ReceiveMoney)list.get(i);
                jingbanren = StringUtil.gb2iso4View(remoney.getJingBanRen());
                userid = remoney.getUserID();
                username = StringUtil.gb2iso4View(remoney.getUserName());
                realname = StringUtil.gb2iso4View(remoney.getRealName());
                money = remoney.getNumber();
                desc = StringUtil.gb2iso4View(remoney.getDescribe());
                payer = StringUtil.gb2iso4View(remoney.getPayer());
                payway = orderMgr.getPayWay(remoney.getPayway());
                unit = StringUtil.gb2iso4View(remoney.getUnit());
                createdate = String.valueOf(remoney.getCreatedate());
                createdate = createdate==null?"":createdate.substring(0,16);
              %>
                <tr  bgcolor="#FFFFFF">
                  <td align="center" class="txt">
                   <font color="blue"><%=money%></font>
                  </td>
                  <td align="center" class="txt">
                   <%=unit%>
                  </td>
                  <td align="center" class="txt">
                   <%=payer%>
                  </td>
                  <td align="center" class="txt">
                   <%=jingbanren%>
                  </td>
                  <td align="center" class="txt">
                   <%=username%>
                  </td>
                  <td align="center" class="txt">
                   <%=payway%>
                  </td>
                  <td align="center" class="txt">
                   <%=desc%>
                  </td>
                  <td align="center" class="txt">
                   <%=createdate%>
                  </td>
                  <td align="center" class="txt">
                   <a href="receive.jsp?id=<%=remoney.getID()%>" target=_blank>修改
                   </a>
                  </td>
                </tr>
              <%}%>

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
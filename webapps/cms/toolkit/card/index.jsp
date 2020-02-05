<%@ page import="com.bizwink.cms.util.ParamUtil"%>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager"%>
<%@ page import="com.bizwink.cms.business.Order.orderPeer"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.bizwink.cms.business.Order.Card" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
      int siteid = authToken.getSiteID();
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    int searchflag = ParamUtil.getIntParameter(request,"searchflag",-1);
    String jumpstr = "";
    IOrderManager orderMgr = orderPeer.getInstance();
    List list = new ArrayList();
    int rows = 0;
    if(searchflag == -1){
        list = orderMgr.getCardForOutLine(startrow,range,"select * from tbl_shoppingcard where siteid = "+siteid);
        rows = orderMgr.getCardNum("select count(*) from tbl_shoppingcard where siteid = "+siteid);
    }
    int denomination = 0;
    String begintime = "";
    String endtime = "";
    if(searchflag == 1){

        denomination = ParamUtil.getIntParameter(request,"denomination",0);
        begintime = ParamUtil.getParameter(request,"begintime");
        endtime = ParamUtil.getParameter(request,"endtime");
    }
    if(searchflag != -1){
        if ((endtime != "") && (endtime != null)) endtime = endtime + " 23:59:59";
        if ((begintime != "") && (begintime != null)) begintime = begintime + " 00:00:00";
        jumpstr = "&searchflag=1";
        String searchnum = "select count(*) from tbl_shoppingcard where siteid = "+siteid+" and 1=1";
        String searchsql = "select * from tbl_shoppingcard where siteid = "+siteid+" and 1=1";
        if(denomination > 0){
            searchsql = searchsql + " and denomination = " + denomination;
            searchnum = searchnum + " and denomination = " + denomination;
            jumpstr = jumpstr + "&denomination=" + denomination;
        }
        if(begintime != null && !begintime.equals("")){
            searchsql = searchsql + " and createtime >= TO_DATE('"+begintime+"', 'YYYY-MM-DD HH24:MI:SS')";
            searchnum = searchnum + " and createtime >= TO_DATE('"+begintime+"', 'YYYY-MM-DD HH24:MI:SS')";
            jumpstr = jumpstr + "&begintime=" + begintime;
        }
        if(endtime != null && !endtime.equals("")){
            searchsql = searchsql + " and createtime <= TO_DATE('"+endtime+"', 'YYYY-MM-DD HH24:MI:SS')";
            searchnum = searchnum + " and createtime <= TO_DATE('"+endtime+"', 'YYYY-MM-DD HH24:MI:SS')";
            jumpstr = jumpstr + "&endtime="+endtime;
        }

        searchsql = searchsql + " order by createtime desc";

        list = orderMgr.getCardForOutLine(startrow,range,searchsql);
        rows = orderMgr.getCardNum(searchnum);
    }

    int totalpages = 0;
    int currentpage = 0;
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

    int ischeckcardnum = orderMgr.getIsCheckCardNum(siteid);
    //int activationnum = orderMgr.getActivationCardNum(siteid);
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<script language="JavaScript" src="include/setday.js" ></script>
<meta http-equiv="Pragma" content="no-cache">
<style type="text/css">
TABLE {FONT-SIZE: 12px;word-break:break-all}
BODY {FONT-SIZE: 12px;margin-top: 0px;margin-bottom: 0px; line-height:20px;}
.TITLE {FONT-SIZE:16px; text-align:center; color:#FF0000; font-weight:bold; line-height:30px;}
.FONT01 {FONT-SIZE: 12px; color:#FFFFFF; line-height:20px;}
.FONT02 {FONT-SIZE: 12px; color:#D04407; font-weight:bold; line-height:20px;}
.FONT03 {FONT-SIZE: 14px; color:#000000; line-height:25px;}
A:link {text-decoration:none;line-height:20px;}
A:visited {text-decoration:none;line-height:20px;}
A:active {text-decoration:none;line-height:20px; font-weight:bold;}
A:hover {text-decoration:none;line-height:20px;}
.pad {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; line-height:20px;}
.form{border-bottom:#000000 1px solid; background-color:#FFFFFF; border-left:#000000 1px solid; border-right:#000000 1px solid; border-top:#000000 1px solid; font-size: 9pt; font-family:"宋体";}
.botton{border-bottom:#000000 1px solid; background-color:#F1F1F1; border-left:#FFFFFF 1px solid; border-right:#333333 1px solid; border-top:#FFFFFF 1px solid; font-size: 9pt; font-family:"宋体"; height:20px; color: #000000; padding-bottom: 1px; padding-left: 1px; padding-right: 1px; padding-top: 1px; border-style: ridge}
</style>
    <script language="javascript">
    function golist(r,str){
      window.location = "index.jsp?startrow="+r+str;
    }

    function del(){
      var val;
      val = confirm("你确定要删除吗？");
      if(val == 1){
        window.location = "deleteAllCard.jsp?startflag=1";
      }
    }

     function delacard(id,startrow){
      var val;
      val = confirm("你确定要删除吗？");
      if(val == 1){
        window.location = "deletecard.jsp?startflag=1&startrow="+startrow+"&id="+id;
      }
     }
     function update(id,startrow,ischeck){
          var val;
          val = confirm("你确定要修改吗？");
          if(val == 1){
            window.location = "update.jsp?startflag=1&startrow="+startrow+"&id="+id+"&ischeck="+ischeck;
          }
    }
  </script>
</head>
<body>
<input type="hidden" name="updateflag" value="1">
<center>
<table width="80%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">离线购物卷管理</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="20%" align="center" bgcolor="#FFFFFF">序列号</td>
                  <td align="center" width="15%">激活码</td>
                    <td align="center" width="5%">面额</td>
                  <td align="center" width="15%">起始日期</td>
                  <td align="center" width="15%">结束日期</td>
                    <td align="center" width="15%">发放日期</td>
                    <td align="center" width="10%">使用标志</td>
                    <td align="center" width="5%">删除</td>
                </tr>
                  <%
                      for(int i = 0; i < list.size();i++){
                          Card card = (Card)list.get(i);
                  %>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td align="center"><%=card.getCardnum()==null?"":card.getCardnum()%></td>
                    <td align="center"><%=card.getCode()==null?"":card.getCode()%></td>
                    <td align="center"><%=card.getDenomination()%></td>
                  <td align="center"><%=card.getBegintime()==null?"":card.getBegintime()%></td>
                  <td align="center"><%=card.getEndtime()==null?"":card.getEndtime()%></td>
                    <td align="center"><%=card.getCreatetime().toString().substring(0,10)%></td>
                    <td align="center"><%if(card.getIscheck()==0){%>未使用<%}else{%><font color="red">已使用</font><%}%></td>
                    <td align="center"><a href="javascript:delacard(<%=card.getId()%>,<%=startrow%>);">删除</a></td>
                </tr>
                  <%}%>
               </table>
            </td>
          </tr>
        </table>
      </td>
</tr>
</table>
<table width="70%" class="css_002">
<tr valign="bottom" width=100%>
<td>
 总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
</td>
<td>
</td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td class="css_002">
<%
    if((startrow-range)>=0){
%>
[<a href="index.jsp?startrow=<%=startrow-range%><%=jumpstr%>" class="css_002">上一页</a>]
<%}
  if((startrow+range)<rows){
%>
[<a href="index.jsp?startrow=<%=startrow+range%><%=jumpstr%>" class="css_002">下一页</a>]
<%}

  if(totalpages>1){%>
  &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
  <a href="#" class="css_002" onclick="golist((document.all('jump').value-1) * <%=range%>,'<%=jumpstr%>');">GO</a>
  <%}%>
</td>
<td align="right">&nbsp;&nbsp;<a href="javascript:del();">删除全部离线购物卷</a>&nbsp;&nbsp;</td>
<td align="right"><a href="create.jsp">添加离线购物卷</a>&nbsp;&nbsp;<a href="autocreatecard.jsp">自动添加购物券</a></td>
</tr>
</table>
<table width="50%" class="css_002">
    <tr valign="bottom" width=100%>
        <td colspan=6>
           &nbsp;
        </td>
    </tr>
    <tr valign="bottom" width=100%>
        <td colspan=6>
           &nbsp;
        </td>
    </tr>
    <tr valign="bottom" width=100%>
        <td colspan=6>
           已使用<font color=red><%=ischeckcardnum%></font>张购物券&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
    </tr>
</table>
<table width="50%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<form action="index.jsp" name="searchFom">
<input type="hidden" name="searchflag" value="1">
<tr>
  <td>
    <table width="100%" border="0" cellpadding="0">
      <tr bgcolor="#F4F4F4" align="center">
        <td height="30" valign="left" bgcolor="#F4F4F4" class="css_003">购物卷查询：</td>
      </tr>
      <tr bgcolor="#d4d4d4" align="right">
        <td>
          <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
            <tr  bgcolor="#FFFFFF" class="css_001">
              <td width="8%" align="center" bgcolor="#FFFFFF">面额：</td>
              <td align="center" width="48%"><input name="denomination" type="text"></td>
            </tr>
              <tr bgcolor="#FFFFFF">
                <td align="center" class="txt">日期：</td>
                <td bgcolor="#FFFFFF" class="txt"> 从(开始日期)
                <input type="text" size="10" name="begintime" onfocus="setday(this)" readonly>
                 到(结束日期)
                  <input type="text" size="10" name="endtime" onfocus="setday(this)" readonly>
                </td>
             </tr>
           </table>
        </td>
      </tr>
    </table>
  </td>
</tr>
<tr>
<td align=center><input type="submit" value="查询"></td>
</tr>
</form>
</table>
</center>
</body>
</html>
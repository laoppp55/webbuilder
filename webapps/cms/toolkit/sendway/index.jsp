<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.Fee" %>
<%@ page import="com.bizwink.cms.business.Order.SendWay" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
      int siteid = authToken.getSiteID();
    IOrderManager orderMgr = orderPeer.getInstance();
    List list = new ArrayList();
    list = orderMgr.getAllSendWayInfo(siteid);
%>
<html>
<head>
<title>支付方式管理</title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
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

     function del(id){
      var val;
      val = confirm("你确定要删除吗？");
      if(val == 1){
        window.location = "delete.jsp?startflag=1"+"&id="+id;
      }
    }

    function edit(id){
      window.location = "edit.jsp?id="+id;
  }
        function interfaceParam(id){
            window.location="interfaceParam.jsp?paywayid="+id;
        }
        function interfaceDetails(id){
            window.location="displayParam.jsp?paywayid="+id;
        }
  </script>
</head>
<body>
<input type="hidden" name="updateflag" value="1">
<center>
<table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">支付方式管理</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="15%" align="center">支付方式名称</td>
                    <td align="center" width="25%">支付方式描述</td>
                    <td align="center" width="12%">创建时间</td>
                    <td align="center" width="10%">修改</td>
                    <td align="center" width="10%">删除</td>
                    <td align="center" width="13%">添加接口参数</td>
                    <td align="center" width="15%">接口详细信息</td>
                </tr>
                  <%
                      for(int i = 0; i < list.size();i++){
                          SendWay send = (SendWay)list.get(i);
                          

                  %>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="15%" align="center"><%=send.getCname()==null?"": StringUtil.gb2iso4View(send.getCname())%></td>
                    <td align="center" width="25%"><%=send.getNotes()==null?"": StringUtil.gb2iso4View(send.getNotes())%></td>
                    <td align="center" width="12%"><%=send.getCreatedate().toString().substring(0,10)%></td>
                    <td align="center" width="10%"><a href="javascript:edit(<%=send.getId()%>)">修改</a></td>
                    <td align="center" width="10%"><a href="javascript:del(<%=send.getId()%>)">删除</a></td>
                    <td align="center" width="13%"><a href="javascript:interfaceParam(<%=send.getId()%>)">添加接口参数</a></td>
                    <td align="center" width="15%"><a href="javascript:interfaceDetails(<%=send.getId()%>)">接口详细信息</a></td>
                </tr>
                  <%}%>
               </table>
            </td>
          </tr>
            <tr>
                <td align="right">
                    <a href="create.jsp">添加支付方式</a>
                </td>
            </tr>
        </table>
      </td>
</tr>
</table>

</center>
</body>
</html>
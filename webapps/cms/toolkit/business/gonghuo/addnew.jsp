<%@ page import="java.io.*,
                 java.sql.*,
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
%>

<%
  boolean success = false;
  int startflag = ParamUtil.getIntParameter(request, "startflag", 0);

  if(startflag == 1)
  {
    String kind      = ParamUtil.getParameter(request,"kind");
    String name      = ParamUtil.getParameter(request,"name");
    String lianxiren = ParamUtil.getParameter(request,"lianxiren");
    String address   = ParamUtil.getParameter(request,"address");
    String postcode  = ParamUtil.getParameter(request,"postcode");
    String telphone  = ParamUtil.getParameter(request,"telphone");
    String email     = ParamUtil.getParameter(request,"email");
 //   String fahuo     = ParamUtil.getParameter(request,"fahuo");
 //   String jiesuan   = ParamUtil.getParameter(request,"jiesuan");
    String notes    =ParamUtil.getParameter(request,"notes");

    IOtherManager otherMgr = otherPeer.getInstance();
    Other other = new Other();

    other.setGH_Adderss(address);
    other.setGH_Email(email);
    other.setGH_Kind(kind);
    other.setGH_Lianxiren(lianxiren);
    other.setGH_Name(name);
    other.setGH_Notes(notes);
    other.setGH_Phone(telphone);
    other.setGH_Postcode(postcode);
    other.setGH_Siteid(siteid);
    otherMgr.newGH(other);

    out.println("<script language=\"javascript\">");
    out.println("alert(\"供货商信息添加完毕\");");
    out.println("opener.history.go(0);");
    out.println("window.close();");
    out.println("</script>");
  }
%>


<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<meta http-equiv="Pragma" content="no-cache">
<script language="javascript">
function check(){
  if((addform.name.value == "")||(addform.name.value == null)){
    alert("请输入供应商名称！");
    return false;
  }
  if((addform.kind.value == "")||(addform.kind.value == null)){
    alert("请输入货物种类！");
    return false;
  }
  if((addform.lianxiren.value == "")||(addform.lianxiren.value == null)){
    alert("请输入联系人！");
    return false;
  }
  if((addform.address.value == "")||(addform.address.value == null)){
    alert("请输入供应商地址！");
    return false;
  }
  if((addform.telphone.value == "")||(addform.telphone.value == null)){
    alert("请输入供应商电话！");
    return false;
  }
  addform.action="addnew.jsp";
  //addform.submit();
  return true;
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


<form action="#" method="post" name="addform">
<input type="hidden" name="startflag" value="1">
<center>
<table width="770" border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">供货商信息</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2">
                <tr>
                  <td>供货商信息<font color="#FF0000">*</font>必填</td>
                </tr>
              </table>
                <table width="100%" border="0" cellpadding="2" cellspacing="1">
                  <!--DWLayoutTable-->
                  <tr bgcolor="#FFFFFF">
                    <td width="20%">供货商名称：</td>
                    <td width="30%"> <input name="name" type="text" id="name" size="20">
                      <font color="#FF0000"> * </font></td>
                    <td>请您仔细填写<font color="#FF0000">供货商名称</font>等详细信息。</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>货物类型：</td>
                    <td> <input type="text" name="kind" size="20"> <font color="#FF0000"> *
                      </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>联系人：</td>
                    <td> <input name="lianxiren" type="text" size="20">
                      <font color="#FF0000"> * </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>地址：</td>
                    <td> <input name="address" type="text"  size="20">
                      <font color="#FF0000"> * </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>邮编：</td>
                    <td> <input name="postcode" type="text"  size="20">
                    </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>电话：</td>
                    <td> <input name="telphone" type="text"  size="20">
                      <font color="#FF0000"> * </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>电子邮件：</td>
                    <td> <input name="email" type="text"  size="20"></td>
                    <td></td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>备注：</td>
                    <td> <input name="notes" type="text" size="20">
                      &nbsp; </td>
                    <td>&nbsp;</td>
                  </tr>
                </table>
            </td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr align="center">
            <td>
                <input name="submit" type="submit"  value="提交" onclick="javascript:return check()"></td>
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
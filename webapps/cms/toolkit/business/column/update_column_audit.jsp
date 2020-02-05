<%@ page import = "java.io.*,
		   java.util.*,
		   java.sql.Timestamp,
		   com.bizwink.cms.util.*,
		   com.bizwink.cms.server.*,
		   com.bizwink.cms.security.*,
		   com.bizwink.cms.audit.*"
		   contentType = "text/html;charset=gbk"
%>

<%
   Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
   if(authToken == null)
   {
     response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
     return;
   }

   boolean success  = true;
   int ID	      = ParamUtil.getIntParameter(request,"ID",0);
   int columnID       = ParamUtil.getIntParameter(request,"columnID",-1);
   String auditRules  = ParamUtil.getParameter(request,"auditRules");

   boolean doSubmit   = ParamUtil.getBooleanParameter(request,"doSubmit");
   String Audit_Rules = ParamUtil.getParameter(request,"Audit_Rules");
   String editor      = authToken.getUserID();

   IAuditManager auditManager = AuditPeer.getInstance();

   //修改栏目审批规则
   if (doSubmit && Audit_Rules == null)
   {
     success = false;
   }

   if (doSubmit && success)
   {
     try
     {
       //需规范规则
       Audit_Rules = Audit_Rules.toString().trim();
       int len = Audit_Rules.length();

       if (Audit_Rules.substring(len-3,len).compareTo("AND") == 0)
       {
   	    	Audit_Rules = Audit_Rules.substring(0,len-4);
       }
       else if (Audit_Rules.substring(len-2,len).compareTo("OR") == 0)
       {
         Audit_Rules = Audit_Rules.substring(0,len-3);
       }

       Audit audit = new Audit();

       audit.setAuditRules(Audit_Rules);
       audit.setLastUpdated(new Timestamp(System.currentTimeMillis()));
       audit.setEditor(editor);
       audit.setID(ID);

       auditManager.Update_Column_AuditRules(audit);
     }
     catch (CmsException c)
     {
       success = false;
     }
     success = true;
   }

   //读出所有有<文章审核权限>的用户列表
   List userList = null;
   int userCount = 0;

   if (!doSubmit)
   {
     userList = auditManager.getRights_Users(columnID);
     userCount = userList.size();
   }
%>

<html>
<head>
<title>修改栏目规则</title>
<meta http-equiv="Pragma" content="no-cache">
<link rel=stylesheet type="text/css" href="../style/global.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript">
var bool_and = false;
var bool_bor = false;
var bool_blb = true;
var bool_brb = false;

function Item_OnClick()
{
   document.form1.Audit_Rules.value =
   document.form1.Audit_Rules.value +
   document.form1.User_List.options[document.form1.User_List.selectedIndex].value;

   document.form1.User_List.options[document.form1.User_List.selectedIndex] = null;
   document.form1.User_List.disabled = true;

   var i = document.form1.User_List.options.length;
   if (i == 0)
   {
   	document.form1.button_and.disabled = true;
   	document.form1.button_or.disabled = true;
   }
   else if (i > 0)
   {
   	document.form1.button_and.disabled = false;
   	document.form1.button_or.disabled = false;
   }

   document.form1.button_left_bracket.disabled = true;
   document.form1.button_right_bracket.disabled = false;
}

function Button_OnClick(type)
{
   switch (type)
   {
   	case "and":  	document.form1.Audit_Rules.value = document.form1.Audit_Rules.value + " AND ";

			document.form1.User_List.disabled = false;
			document.form1.button_and.disabled = true;
			document.form1.button_or.disabled = true;
			document.form1.button_left_bracket.disabled = false;
			document.form1.button_right_bracket.disabled = true;
   			break;

   	case "bor":  	document.form1.Audit_Rules.value = document.form1.Audit_Rules.value + " OR ";

			document.form1.User_List.disabled = false;
			document.form1.button_and.disabled = true;
			document.form1.button_or.disabled = true;
			document.form1.button_left_bracket.disabled = false;
			document.form1.button_right_bracket.disabled = true;
   			break;

   	case "blb":  	document.form1.Audit_Rules.value = document.form1.Audit_Rules.value + "(";

			document.form1.User_List.disabled = false;
			document.form1.button_and.disabled = true;
			document.form1.button_or.disabled = true;
			document.form1.button_left_bracket.disabled = true;
			document.form1.button_right_bracket.disabled = true;
   			break;

   	case "brb":  	document.form1.Audit_Rules.value = document.form1.Audit_Rules.value + ")";

			document.form1.User_List.disabled = true;
			document.form1.button_and.disabled = false;
			document.form1.button_or.disabled = false;
			document.form1.button_left_bracket.disabled = true;
			document.form1.button_right_bracket.disabled = true;
   			break;

   	case "bcm":  	document.form1.Audit_Rules.value = document.form1.Audit_Rules.value + ",";break;
   }
}

function Form_Submit()
{
   if (document.form1.Audit_Rules.value == "")
     alert("审核规则不能为空！");
   else
     form1.submit();
}

function window_close()
{
   <%if (success && doSubmit){%>
   	window.close();
   	opener.window.close();
   <%}%>
}
</script>
</head>

<body onload="javascript:window_close()" bgcolor="#D6D3CE">
<form action="update_column_audit.jsp?columnID=<%=columnID%>&ID=<%=ID%>" method="post" name="form1">
<input type=hidden name="doSubmit" value="true">
<table border="0" width="100%" height="310">
  <tr>
    <td width="50%" height="170">
      <p align="left">&nbsp;&nbsp;
      <select size="10" name="User_List" id="User_List" onchange="javascript:Item_OnClick()">
        <%
        for (int i=0; i<userCount; i++)
        {
            Audit audit = (Audit)userList.get(i);
            String userID = audit.getUserID();
        %>
        <option value="[<%=userID.toString().toLowerCase().trim()%>]"><%=userID%></option>
        <%}%>
      </select></td>
    <td width="50%" height="170" valign="bottom">
      <p align="left">
      <%if (userCount > 0){%>
      <input type="button" value=" AND " onclick="javascript:Button_OnClick('and')" name="button_and" style="font-weight: bold">&nbsp;
      <input type="button" value=" OR  " onclick="javascript:Button_OnClick('bor')" name="button_or" style="font-weight: bold"></p><p align="left">
      <input type="hidden" value="  (  " onclick="javascript:Button_OnClick('blb')" name="button_left_bracket" style="font-weight: bold">&nbsp;
      <input type="hidden" value="  )  " onclick="javascript:Button_OnClick('brb')" name="button_right_bracket" style="font-weight: bold"><br> <br>
      <input type="hidden" value="  ,  " onclick="javascript:Button_OnClick('bcm')" name="button_comma" style="font-weight: bold">
      <%}else{%>
      <input type="button" value=" AND " disabled onclick="javascript:Button_OnClick('and')" name="button_and" style="font-weight: bold">&nbsp;
      <input type="button" value=" OR  " disabled onclick="javascript:Button_OnClick('bor')" name="button_or" style="font-weight: bold"></p><p align="left">
      <input type="hidden" value="  (  " disabled onclick="javascript:Button_OnClick('blb')" name="button_left_bracket" style="font-weight: bold">&nbsp;
      <input type="hidden" value="  )  " disabled onclick="javascript:Button_OnClick('brb')" name="button_right_bracket" style="font-weight: bold"><br> <br>
      <input type="hidden" value="  ,  " disabled onclick="javascript:Button_OnClick('bcm')" name="button_comma" style="font-weight: bold">
      <%}%>
    </td>
  </tr>
  <tr>
    <td width="50%" height="18">&nbsp;&nbsp;&nbsp;
      栏目规则：</td>
    <td width="50%" height="18"></td>
  </tr>
  <tr>
    <td width="100%" colspan="2" height="62">
      <p align="left">&nbsp;&nbsp;
      <textarea rows="4" name="Audit_Rules" readonly cols="49" style="background-color: #C0C0C0; border-style: solid; border-width: 1"></textarea></td>
  </tr>
  <tr>
  	<td colspan="2"><font color=red>&nbsp;&nbsp;&nbsp;<b>原有规则：</b><%=auditRules%></font></td>
  </tr>
  <tr>
    <td width="100%" colspan="2" height="44">
      <p align="center">
      <%if (userCount > 0){%>
      <input type="button" value=" 修改 " name="Submit_Audit" onclick="javascript:Form_Submit();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <%}else{%>
      <input type="button" value=" 修改 " name="Submit_Audit" disabled>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <%}%>
      <input type="button" value=" 取消 " name="Close_Audit" onclick="javascript:window.close();">
    </td>
  </tr>
  <%if (userCount <= 0){%>
  <tr><td colspan=2>&nbsp;&nbsp;&nbsp;
  <font color=red><b>没有授权用户！请先添加拥有<文章审核>的权限用户！</b></font></td></tr>
  <%}%>
</table>
</form>

</body>
</html>
<script language="javascript">
if (bool_and == false)
    document.form1.button_and.disabled = true;
if (bool_bor == false)
    document.form1.button_or.disabled = true;
if (bool_blb == false)
    document.form1.button_left_bracket.disabled = true;
if (bool_brb == false)
    document.form1.button_right_bracket.disabled = true;
</script>
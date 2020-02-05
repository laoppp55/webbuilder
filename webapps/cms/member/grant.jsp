<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"
%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if( authToken == null )
    {
        response.sendRedirect( "../login.jsp?url=member/createMember.jsp" );
	return;
    }
    if (!SecurityCheck.hasPermission(authToken, 54))
    {
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    com.bizwink.cms.security.IRightsManager rightsManager = RightsPeer.getInstance();
    boolean error = false;
    boolean doGrant = ParamUtil.getBooleanParameter(request,"doGrant");
    String userid = ParamUtil.getParameter(request,"userid");    //被授权人的userid，不是登录者的userid
    String colCname = ParamUtil.getParameter(request,"colChname");
    int columnid = ParamUtil.getIntParameter(request,"column",-1);
    int errcode = 0;
    int siteid = authToken.getSiteID();

    if (doGrant == true && !error && userid != null && columnid != -1) {

      List rlist = ParamUtil.getParameterValues(request,"rightList");
      if (columnid != 0)
        errcode = rightsManager.grantToColumns(userid,columnid,rlist);          //对某个栏目进行授权
      else
        errcode = rightsManager.grantToUser(userid,rlist);                      //对某个用户进行授权
    }

    //get the rights definations from tbl_rights;
    List rightsList = new ArrayList();
    if (columnid != 0 && columnid != -1)
      rightsList = rightsManager.getCrRights();                                 //获取所有与栏目相关的权限
    else
      rightsList = rightsManager.getUrRights();                                 //获取与用户相关的所有权限

    List grantedRights = new ArrayList();
    if (userid != null && columnid != -1) {
      if(columnid != 0 )
        grantedRights = rightsManager.getUserColumnRight(userid,columnid);      //如果对栏目授权，获取已经授给该栏目的权限
      else
        grantedRights = rightsManager.getGrantedUserRights(userid,siteid);      //如果对用户授权，获取已经授给该用户的权限
    }

    int rightid = 0;
    String rightname = null;
    int i = 0;
%>

<html>
<head>
<title>用户授权</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script LANGUAGE="JavaScript" SRC="../js/check.js">
</script>

<SCRIPT LANGUAGE=javascript>
var rights_sel = "";

function selectList(srcList,desList)
{
	seleflg = false;

	for(i = srcList.length - 1; i >= 0; i--)
	{

		if(srcList.options[i].selected == true){

			var oOption = document.createElement("OPTION");
			oOption.value = srcList.options[i].value;
			oOption.text = srcList.options[i].text;
			desList.add(oOption);
			srcList.options[i] = null;
			seleflag = true;
		}else{
			continue;
		}
	}

	if( !seleflag ){
		alert ("您没有选择权限，请选择！");
		return false;
	}
}


function checkFrm(frm)
{
    if( frm.rightList.length == 0 )
    {
        alert("你没选任何权限，请从左边的备选列表中选择权限！");
        return false;
    } else {
      var el = form1.rightList;

      for(var i = el.options.length-1; i >=0; i--) {
        el.options[i].selected = true;
      }
    }
}

</SCRIPT>
</head>

<body bgcolor="#FFFFFF">
<form name="form1" method="post" action="grant.jsp">
  <%
     if ((userid !=null) && (colCname != null)) {
         out.print("<p>授予用户" + userid + "在" + colCname + "栏目的权限如下：</p>");
         out.print("<input type=hidden name=userid value=" + userid + ">");
         out.print("<input type=hidden name=column value=" + columnid + ">");
     } else if(userid != null && columnid==0){
         out.print("<p>授予用户" + userid +" 的权限如下：</p>");
         out.print("<input type=hidden name=userid value=" + userid + ">");
         out.print("<input type=hidden name=column value=" + columnid + ">");
     } else {
         out.print("<p>请选择需要授权的栏目或用户：</p>");
     }
  %>
  <input type=hidden name=doGrant value=true>
  <table width="46%" height="370" border="0">
    <tr hight="95%">
      <td width="90%" valign="top" height="366">
        <div align="center">
          <center>
            <table width="55%" border="1" bgcolor="#CCCCCC" height="389">
              <tr>
            <td width="44%" height="225" align="right" valign="top"  hight="5%">
              <div align="center">备选权限:<br>
                <br>
                <select name="leftList" size="20" multiple  style="width: 135; height: 251">
                  <%
                  for(i = 0; i < rightsList.size(); i++)
                  {
                      rightid = ((Rights)(rightsList.get(i))).getRightID();
                      rightname =  StringUtil.gb2iso4View(((Rights)(rightsList.get(i))).getRightCName());
                  %>
                  <option value="<%=rightid%>">&nbsp;<%=rightname%></option>
                  <%}%>
                </select>
              </div></td>
            <td colspan="2" valign="middle" height="225"> <input type="button" name="add" value="〉〉" onclick="return selectList(this.form.leftList,this.form.rightList)">
              <br> <br> <input type="button" name="delete" value="〈〈" onclick="return selectList(this.form.rightList,this.form.leftList)"></td>
            <td width="45%" valign="top" height="225">
              <div align="center">授与权限:<br>
                <br>
                <select name="rightList" size="20" multiple  style="width: 135; height: 251">
                  <%
                  for(i = 0; i < grantedRights.size(); i++)
                  {
                      rightid = ((Rights)(grantedRights.get(i))).getRightID();
                      rightname =  StringUtil.gb2iso4View(((Rights)(grantedRights.get(i))).getRightCName());
                  %>
                  <option value="<%=rightid%>">&nbsp;<%=rightname%></option>
                  <%}%>
                </select>
              </div></td>
          </tr>
          <tr >
                <td height="66" colspan="4" hight="24">
                  <div align="center">
                <input type="submit" name="SubBtn" value="授权" onclick="javascript:return checkFrm(this.form)">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" name="CancelBtn" value="返回" onclick="javascript:parent.window.close()">
              </div></td>
          </tr>
        </table></center>
        </div>
      </td>
    </tr>
  </table>

</form>

</body>
</html>

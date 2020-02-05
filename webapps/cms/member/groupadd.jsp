<%@ page import="java.util.*,
		 java.net.URLEncoder,
                 com.bizwink.cms.security.*,
                 org.apache.regexp.*,
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
    if (!SecurityCheck.hasPermission(authToken, "member"))
    {
        response.sendRedirect("../error.jsp?message=无管理用户组的权限");
        return;
    }

    boolean error = false;
    boolean doCreate = false;

    String columnid = ParamUtil.getParameter(request,"columnid");
    RE regExp = new RE(":");
    String[] column = regExp.split(columnid);

    int len = 0;	//求得数组长度
    while (columnid.indexOf(":") != -1)
    {
	columnid = columnid.substring(0,columnid.lastIndexOf(":"));
	len ++;
    }

    com.bizwink.cms.security.IRightsManager rightsManager = RightsPeer.getInstance();
    List rightsList = new ArrayList();
    rightsList = rightsManager.getRights();

    String rightid = null;
    String rightname = null;
    int i = 0;
%>

<html>
<head>
<title>用户组授权</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script LANGUAGE="JavaScript" SRC="../js/check.js">
</script>

<SCRIPT LANGUAGE=javascript>
function submit_end()
{
  <% if( !error && doCreate ) {%>
    parent.window.opener.location.href="index.jsp?msg=用户组授权成功";
    parent.window.close();
  <%}%>
}

function selectList(srcList,desList)
{
	seleflg = false;
	for(var i = srcList.length - 1; i >= 0; i--)
	{
		if(srcList.options[i].selected == true)
		{
			var oOption = document.createElement("OPTION");
			oOption.value = srcList.options[i].value;
			oOption.text = srcList.options[i].text;

			desList.add(oOption);
			srcList.options[i] = null;
			seleflag = true;
		}
		else
		{
			continue;
		}
	}

	if( !seleflag )
	{
		alert ("您没有选择权限，请选择！");
		return false;
	}
}

var rights_sel = ""

function checkFrm(frm)
{
    for (var i = 0; i<frm.hiddenList.length; i++)
    {
    	var right = "";
    	for(var j = 0; j<frm.rightList.length; j++)
    	{
    	    right = right + ":" + frm.rightList.options[j].value;
    	}

    	rights_sel = rights_sel + "|" + frm.hiddenList.options[i].value + right;
    }

    //if( frm.groupid.value == "null" || frm.groupid.value == "" ||  frm.groupid.value == null ){
    //    alert("请回上一页面，输入用户组标识符!");
    //    return false;
    //}

    if( frm.rightList.length == 0 )
    {
        alert("你没选任何权限，请从左边的备选列表中选择权限！");
        return false;
    }
    else
    {
    	opener.newuser.rightids.value = opener.newuser.rightids.value + rights_sel;
    	window.close();
	return true;
    }
}
</SCRIPT>
</head>

<body bgcolor="#FFFFFF">
<form name="form1" method="post" action="">
  <table width="46%" height="388" border="0">
    <tr hight="95%">
      <td width="90%" valign="top"> <table width="55%" border="1" bgcolor="#CCCCCC" height="299">
          <tr>
            <td width="44%" height="254" align="right" valign="top"  hight="5%">
              <div align="center"><font size="2">备选权限:</font><br>
                <br>
                <select name="leftList" size="20" multiple  style="width: 135; height: 231">
                  <%
                  for(i = 0; i < rightsList.size(); i++)
                  {
                      rightid = ((Rights)(rightsList.get(i))).getRightID();
                      rightname =  ((Rights)(rightsList.get(i))).getRightCName();
                  %>
                  <option value="<%=rightid%>">&nbsp;<%=rightname%></option>
                  <%}%>
                </select>
              </div></td>
            <td colspan="2" valign="middle" height="254"> <input type="button" name="add" value="〉〉" onclick="return selectList(this.form.leftList,this.form.rightList)">
              <br> <br> <input type="button" name="delete" value="〈〈" onclick="return selectList(this.form.rightList,this.form.leftList)"></td>
            <td width="45%" valign="top" height="254"><div align="center"><font size="2">授与权限:<br>
                </font>
                <br>
                <select name="rightList" size="20" multiple  style="width: 135; height: 230"></select>
              </div></td>
          </tr>

          <div style="display:none">
          <select name="hiddenList" size="20" multiple  style="width: 135; height: 251">
          <%for (i=1;i<len+1;i++){%>
          	<option value="<%=column[i]%>"><%=column[i]%></option>
          <%}%>
          </select>
          </div>

          <tr >
            <td height="33" colspan="4" hight="24">
              <div align="center">
                <input type="button" name="SubBtn" value="确定" onclick="javascript:return checkFrm(this.form)">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" name="CancelBtn" value="取消" onclick="javascript:window.close()">
              </div></td>
          </tr>
        </table></td>
    </tr>
  </table>

</form>

</body>
</html>

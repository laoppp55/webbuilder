<%@ page import="java.util.*,
		 java.net.URLEncoder,
                 com.bizwink.cms.security.*,
                 org.apache.regexp.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"

%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if( authToken == null ) {
        response.sendRedirect( "../login.jsp?url=member/createMember.jsp" );
				return;
    }
    if (!SecurityCheck.hasPermission(authToken, "member")){
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    boolean error = false;
    boolean doCreate = false;
    int columnid    = ParamUtil.getIntParameter(request,"columnid",0);
    String userid   = ParamUtil.getParameter(request,"userid");

    String options1 = "";
    String options2 = "";

    if (columnid != 0 && userid != null)
    {
    	IGroupManager groupMgr = GroupPeer.getInstance();
    	IRightsManager rightsManager = RightsPeer.getInstance();
    	List rightsList;
	int rightsCount = 0;

	//得到已选权限
	rightsList = rightsManager.getUserColumnRight(userid,columnid);
	rightsCount = rightsList.size();

	for (int i=0; i<rightsCount; i++)
	{
	    Rights rightsColumn = (Rights)rightsList.get(i);
	    String coloumn_ID = rightsColumn.getRightID();
    	    String RetVal = groupMgr.getRightID(coloumn_ID);

	    options1 = options1 + "<option value=" + coloumn_ID + ">&nbsp;" + RetVal + "</option>";
     	}

	//得到备选权限
	rightsList = rightsManager.getUserColumnRemainRight(userid,columnid);
	rightsCount = rightsList.size();

	for (int j=0; j<rightsCount; j++)
	{
	    Rights rightsColumn = (Rights)rightsList.get(j);
	    String coloumn_ID = rightsColumn.getRightID();
    	    String RetVal = groupMgr.getRightID(coloumn_ID);

	    options2 = options2 + "<option value=" + coloumn_ID + ">&nbsp;" + RetVal + "</option>";
     	}
    }
%>

<html>
<head>
<title>用户授权</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script LANGUAGE="JavaScript" SRC="../js/check.js">
</script>

<SCRIPT LANGUAGE=javascript>
function submit_end() {
  <% if( !error && doCreate ) {%>
    parent.window.opener.location.href="index.jsp?msg=用户授权成功";
    parent.window.close();
  <%}%>
}

var rights_sel = ""

function selectList(srcList,desList){

	seleflg = false;

	for(var i = srcList.length - 1; i >= 0; i--) {

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
    var right = "";
    for(var j = 0; j<frm.rightList.length; j++)
    {
        right = right + ":" + frm.rightList.options[j].value;
    }

    rights_sel = "|" + "<%=columnid%>" + right;

    //if( frm.userid.value == "null" || frm.userid.value == "" ||  frm.userid.value == null )
    //{
    //    alert("请回上一页面，输入栏目!");
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
      <td width="90%" valign="top"> <table width="55%" border="1" bgcolor="#CCCCCC" height="307">
          <tr>
            <td width="44%" height="279" align="right" valign="top"  hight="5%">
              <div align="center">备选权限:<br>
                <br>
                <select name="leftList" size="20" multiple  style="width: 135; height: 236"><%=options2%></select>
              </div></td>
            <td colspan="2" valign="middle" height="279"> <input type="button" name="add" value="〉〉" onclick="return selectList(this.form.leftList,this.form.rightList)">
              <br> <br> <input type="button" name="delete" value="〈〈" onclick="return selectList(this.form.rightList,this.form.leftList)"></td>
            <td width="45%" valign="top" height="279"><div align="center">授与权限:<br>
                <br>
                <select name="rightList" size="20" multiple  style="width: 135; height: 236"><%=options1%></select>
              </div></td>
          </tr>
          <tr >
            <td height="35" colspan="4" hight="24">
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

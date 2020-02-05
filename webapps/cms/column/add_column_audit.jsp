<%@ page import="java.io.*,
                 java.util.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.audit.*"
         contentType = "text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if(authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    session.setAttribute("Current_URL",request.getRequestURI());
    boolean success    = true;
    int siteID         = authToken.getSiteID();
    String creator     = authToken.getUserID();
    int columnID       = ParamUtil.getIntParameter(request, "columnID", -1);
    boolean doSubmit   = ParamUtil.getBooleanParameter(request, "doSubmit");
    String Audit_Rules = ParamUtil.getParameter(request, "Audit_Rules");
    int audittype = ParamUtil.getIntParameter(request,"audittype",0);
    //将栏目审批规则入库
    if (doSubmit && Audit_Rules == null) {
        success = false;
    }

    IAuditManager auditManager = AuditPeer.getInstance();
    if (doSubmit && success) {
        int a = ParamUtil.getIntParameter(request,"types",0);
        try {
            //需规范规则
            Audit_Rules = Audit_Rules.toString().trim();
            int len = Audit_Rules.length();

            if (Audit_Rules.substring(len-3,len).compareTo("AND") == 0)
                Audit_Rules = Audit_Rules.substring(0,len-4);
            else if (Audit_Rules.substring(len-2,len).compareTo("OR") == 0)
                Audit_Rules = Audit_Rules.substring(0,len-3);

            Audit audit = new Audit();
            audit.setColumnID(columnID);
            audit.setAuditRules(Audit_Rules);
            audit.setCreator(creator);
            audit.setAudittype(a);
            auditManager.create(audit);
        } catch (CmsException c) {
            c.printStackTrace();
            success = false;
        }
        success = true;
    }

    //读出所有有<文章审核权限>的用户列表
    List userList = null;
    if (!doSubmit) {
        userList = auditManager.getUsers_hasAuditRight(columnID, siteID);
    }
%>

<html>
<head>
    <title>增加栏目审核规则</title>
    <meta http-equiv="Pragma" content="no-cache">
    <link rel=stylesheet type="text/css" href="../style/global.css">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <script language="javascript">
        function window_close()
        {
        <%if (success && doSubmit){%>
            window.close();
        <%}%>
        }

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
            var rules = document.form1.Audit_Rules.value;
            var types = document.form1.types.value;
            if (rules == "")
            {
                alert("审核规则不能为空！");
            }
            else
            {
                var objXml = new ActiveXObject("Microsoft.XMLHTTP");
                objXml.open("POST","add_column_audit.jsp?columnID=<%=columnID%>&doSubmit=true&Audit_Rules="+rules+"&types="+types,false);
                objXml.Send();

                window.returnValue = "ok";
                window.close();
            }
        }
        function audittypes(type)
        {
            document.form1.Audit_Rules.value = "";
            document.form1.User_List.disabled = false;
            document.form1.button_and.disabled = true;
            document.form1.button_or.disabled = true;
            var srcList =  form1.User_List;
            document.getElementById("User_List").options.length = 0;
            if(type == 0)
            {
                document.form1.types.value = 0;
            <% if (userList != null) {
            for (int i=0; i<userList.size(); i++)
            {
                String userID = (String)userList.get(i);
                %>
                var userid = '<%=userID%>';
                var oOption = document.createElement("OPTION");
                oOption.value = "["+userid+"]";
                oOption.text = "["+userid+"]";
                srcList.add(oOption);
            <%}}%>
            }else
            {
                document.form1.types.value = 1;
                var oOption = document.createElement("OPTION");
                oOption.value = "[部门领导]";
                oOption.text = "[部门领导]";
                srcList.add(oOption);
                var oOption1 = document.createElement("OPTION");
                oOption1.value = "[主管领导]";
                oOption1.text = "[主管领导]";
                srcList.add(oOption1);
            }
        }
    </script>
</head>

<body onload="javascript:window_close();" bgcolor="#D6D3CE">

<form action="" method="post" name="form1">
    <input type="hidden" name="types" value="0">
    <table border="0" width="100%" height="310">
        <tr>
            <td colspan="2" align="center">
                <input type="radio" name="audittype" value="0"<%if(audittype == 0){%> checked  <%}%> onclick="javascript:audittypes(0);">按用户定义审核规则&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="audittype" value="1"<%if(audittype == 1){%> checked  <%}%> onclick="javascript:audittypes(1);">按定角色义审核规则
            </td>
        </tr>
        <tr>
            <td width="50%" height="170">
                <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <select size="10" name="User_List" id="User_List" onchange="javascript:Item_OnClick()">

                    </select></td>
            <td width="50%" height="170" valign="bottom">
                <p align="left">
                    <%if (userList != null) {
                        if (userList.size() > 0){%>
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
                    <%}}%>
            </td>
        </tr>
        <tr>
            <td width="50%" height="18">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                栏目规则：</td>
            <td width="50%" height="18"></td>
        </tr>
        <tr>
            <td width="100%" colspan="2" height="62">
                <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <textarea rows="4" name="Audit_Rules" readonly cols="49" style="background-color: #C0C0C0; border-style: solid; border-width: 1"></textarea></td>
        </tr>
        <tr>
            <td width="100%" colspan="2" height="44">
                <p align="center">
                        <%if (userList!=null) {
                        if (userList.size() > 0){%>
                    <input type="button" value="保 存" name="Submit_Audit" onclick="javascript:Form_Submit();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <%}else{%>
                    <input type="button" value="保 存" disabled name="Submit_Audit">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <%}}%>
                    <input type="button" value="取 消" name="Close_Audit" onclick="javascript:window.close();">
            </td>
        </tr>
        <%if (userList!=null) {
            if (userList.size() <= 0){%>
        <tr><td colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <font color=red><b>没有授权用户！请先添加拥有<文章审核>的权限用户！</b></font></td></tr>
        <%}}%>
    </table>
</form>

</body>
</html>
<script language="javascript">
    audittypes(0);
    if (bool_and == false)
        document.form1.button_and.disabled = true;
    if (bool_bor == false)
        document.form1.button_or.disabled = true;
    if (bool_blb == false)
        document.form1.button_left_bracket.disabled = true;
    if (bool_brb == false)
        document.form1.button_right_bracket.disabled = true;
</script>
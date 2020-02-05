<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.program.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int id = ParamUtil.getIntParameter(request, "id", -1);
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);

    int siteid=authToken.getSiteID();
    String programid = null;
    String programname = null;
    String programaddress=null;
    int errcode = 0;

    IProgramManager programMgr = ProgramPeer.getInstance();
    programOftoolkit tprogram = new programOftoolkit();
    if(startflag == 1){
        programid = ParamUtil.getParameter(request, "progid");
        programname = ParamUtil.getParameter(request, "progname");
        programaddress = ParamUtil.getParameter(request, "progaddr");
        tprogram.setSiteid(siteid);
        tprogram.setProgid(programid);
        tprogram.setProgname(programname);
        tprogram.setProguri(programaddress);
        errcode = programMgr.createProgramForToolkit(tprogram);
        if (errcode < 0) {
            response.sendRedirect("programsfortoolkit.jsp");
            return;
        } else {
            response.sendRedirect(response.encodeRedirectURL("closewin.jsp"));
            return;
        }
    }
%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="ProgId" content="FrontPage.Editor.Document">
    <link href="/images/common.css" rel=stylesheet>
    <title>工具箱程序库管理</title>
    <script LANGUAGE="JavaScript" SRC="js/check.js"></script>
    <script language=javascript>
        function Form_Check(form)
        {
            if (pform.progname.value == null || pform.progname.value == "") {
                alert("请输入程序模块中文名称");
                return false;
            }

            if (pform.progid.value == null || pform.progid.value == "") {
                alert("请输入程序模块英文名称");
                return false;
            }

            if (pform.progaddr.value == null || pform.progaddr.value == "") {
                alert("请输入程序模块运行地址");
                return false;
            }

            return true;
        }
    </script>
</head>

<body>
<table cellSpacing="0" cellPadding="0" width="770" border="0" align=center>
    <form name="pform" method="post" action="createProgramForToolkit.jsp" onSubmit="return Form_Check(pform)">
        <input type="hidden" name="startflag" value="1">
        <tbody>
        <tr>
            <td width="20%" height="79" class=txt>程序中文名称</td>
            <td><input name="progname" type=text id="progname_id" height="20" size=50 maxlength="50"></td>
        </tr>

        <tr>
            <td height="43" class=txt>程序英文名称</td>
            <td><input name="progid" type=text id="progid_id" height="20" size="20" maxlength="20"></td>
        </tr>
        <tr>
            <td width="10%" height="43" class=txt>程序运行地址</td>
            <td><input name="progaddr" type=text id="progaddr_id" height="20" size="80" maxlength="80"></td>
        </tr>
        <tr>
            <td width="10%" class=txt>&nbsp;</td>
            <td><input type=submit  name="ok "value="保存">
                <input type=button  name="cancel" value="返回" ONCLICK="javascript:window.close();">            </td>
        </tr>
        </tbody>
    </form>
</table>
</body>
</html>
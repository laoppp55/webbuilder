<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.program.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int id = ParamUtil.getIntParameter(request, "id", -1);
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);

    int p_type;
    int l_type;
    int position;
    String notes;
    String explain;
    String code;
    int errcode = 0;

    IProgramManager programMgr = ProgramPeer.getInstance();
    Program program = new Program();
    if(startflag == 1){
        p_type = ParamUtil.getIntParameter(request, "program_name", 0);
        l_type = ParamUtil.getIntParameter(request, "language_name", 0);
        position = ParamUtil.getIntParameter(request, "posi_name", 0);
        notes = ParamUtil.getParameter(request, "notes");
        explain = ParamUtil.getParameter(request, "notes");
        code = ParamUtil.getParameter(request, "program");

        program.setP_type(p_type);
        program.setL_type(l_type);
        program.setPosition(position);
        program.setNotes(notes);
        program.setExplain(explain);
        program.setProgram(code);
        errcode = programMgr.createProgram(program);
    }
%>
<html>

<head>
    <title>程序管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="../css/cms_css.css" rel=stylesheet>
    <script LANGUAGE="JavaScript" SRC="js/check.js"></script>
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script language=javascript>
        $(document).ready(function(){
            var errcode = <%=errcode%>;
            if (errcode === 1) {
                alert("程序模块增加成功");
                window.opener.programInfoList();
                window.close();
            } else if (errcode < 0){
                alert("程序模块增加失败");
                window.close();
            }
        });

        function Form_Check(form) {
            if (pform.program_id.value == 0) {
                alert("请选择程序功能模块");
                return false;
            }

            if (pform.language_id.value == 0) {
                alert("请选择语言类型");
                return false;
            }

            if (pform.posi_id.value == 0) {
                alert("请选择页面位置");
                return false;
            }

            return true;
        }
    </script>
</head>

<body>
<table cellSpacing="0" cellPadding="0" width="770" border="0" align=center>
    <form name="pform" method="post" action="paddprogram.jsp" onsubmit="return Form_Check(pform)">
        <input type="hidden" name="startflag" value="1">
        <tbody>
        <tr>
            <td width="15%" height="35" class=txt>功能类型</td>
            <td><select ID="program_id" name="program_name">
                <option value="0" selected>请选择</option>
                <option value="11">信息检索</option>
                <option value="12">购物车页</option>
                <option value="13">订单生成</option>
                <option value="14">订单回显</option>
                <option value="15">订单查询</option>
                <option value="16">信息反馈</option>
                <option value="17">用户评论</option>
                <option value="18">用户注册</option>
                <option value="19">用户登录</option>
                <option value="20">订单明细查询</option>
                <option value="21">用户留言</option>
                <option value="22">修改注册</option>
                <option value="24">地图标注</option>
            </select>
            </td>
            <td width="15%" class=txt>语言类型</td>
            <td><select ID="language_id" name="language_name">
                <option value="0" selected>请选择
                <option value="1">Java
                <option value="2">Javascript
            </select></td>
            <td width="15%" class=txt>位置</td>
            <td><select ID="posi_id" name="posi_name">
                <option value="0" selected>请选择
                <option value="1">页头
                <option value="3">页尾
            </select>
            </td>
        </tr>
        <tr>
            <td width="20%" class=txt>说明</td>
            <td colspan="5"><input type=text name="notes" size=80 value=""></td>
        </tr>

        <tr>
            <td width="10%" class=txt>程序体</td>
            <td colspan="5"><textarea name=program cols=80 rows=20></textarea></td>
        </tr>
        <tr>
            <td width="10%" class=txt>&nbsp;</td>
            <td colspan="5"><input type=submit  name="ok "value="保存">
                <input type=button  name="cancel" value="返回" ONCLICK="javascript:window.close();">
            </td>
        </tr>
        </tbody>
    </form>
</table>
</body>
</html>
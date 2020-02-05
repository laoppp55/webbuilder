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

    int p_type = 0;
    int l_type = 0;
    int position = 0;
    String notes = null;
    String explain = null;
    String code = null;
    int errcode = 0;

    IProgramManager programMgr = ProgramPeer.getInstance();
    Program program = new Program();
    if(startflag == -1){
        if (id != -1) {
            program = programMgr.getAPrograms(id);
            p_type = program.getP_type();
            l_type = program.getL_type();
            position = program.getPosition();
            notes = StringUtil.gb2iso4View(program.getNotes());
            explain = StringUtil.gb2iso4View(program.getExplain());
            code = StringUtil.gb2iso4View(program.getProgram());
            if (code.indexOf("textarea") > -1 ) code =StringUtil.replace(code,"textarea","cmstextarea");
        }
    }else{
        p_type = ParamUtil.getIntParameter(request, "program_name", 0);
        l_type = ParamUtil.getIntParameter(request, "language_name", 0);
        position = ParamUtil.getIntParameter(request, "posi_name", 0);
        notes = ParamUtil.getParameter(request, "notes");
        explain = notes;
        code = ParamUtil.getParameter(request, "program");
        if (code.indexOf("cmstextarea") > -1 ) code =StringUtil.replace(code,"cmstextarea","textarea");

        program.setId(id);
        program.setP_type(p_type);
        program.setL_type(l_type);
        program.setPosition(position);
        program.setNotes(notes);
        program.setExplain(explain);
        program.setProgram(code);
        errcode = programMgr.updateProgram(program);
    }
%>
<html>
<head>
    <title>程序管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="../css/mycss.css" rel=stylesheet>
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script>
        $(document).ready(function(){
            var errcode = <%=errcode%>;
            if (errcode === 1) {
                alert("程序模块修改成功");
                window.opener.programInfoList();
                window.close();
            } else if (errcode < 0){
                alert("程序模块修改失败");
                window.close();
            }
        });
    </script>
</head>

<table cellSpacing="0" cellPadding="0" width="770" border="0" align=center>
    <form name="leavewordForm" method="post" action="pedit.jsp">
        <input type="hidden" name="startflag" value="1">
        <input type="hidden" name="id" value="<%=id%>">
        <tbody>
        <tr>
            <td width="15%" height="35" class=txt>功能类型</td>
            <td><select ID="program_id" name="program_name" readonly>
                <option value="0" <%=(p_type==0)?"selected":""%>>请选择</option>
                <option value="11" <%=(p_type==11)?"selected":""%>>信息检索</option>
                <option value="12" <%=(p_type==12)?"selected":""%>>购物车</option>
                <option value="13" <%=(p_type==13)?"selected":""%>>订单生成</option>
                <option value="14" <%=(p_type==14)?"selected":""%>>订单回显</option>
                <option value="15" <%=(p_type==15)?"selected":""%>>订单查询</option>
                <option value="16" <%=(p_type==16)?"selected":""%>>信息反馈</option>
                <option value="17" <%=(p_type==17)?"selected":""%>>用户评论</option>
                <option value="18" <%=(p_type==18)?"selected":""%>>用户这册</option>
                <option value="19" <%=(p_type==19)?"selected":""%>>用户登录</option>
                <option value="20" <%=(p_type==20)?"selected":""%>>订单明细查询</option>
                <option value="21" <%=(p_type==21)?"selected":""%>>用户留言</option>
                <option value="22" <%=(p_type==22)?"selected":""%>>修改注册</option>
                <option value="24" <%=(p_type==24)?"selected":""%>>地图标注</option>
            </select>
            </td>
            <td width="15%" class=txt>语言类型</td>
            <td><select ID="language_id" name="language_name" readonly>
                <option value="0" <%=(l_type==0)?"selected":""%>>请选择
                <option value="1" <%=(l_type==1)?"selected":""%>>Java
                <option value="2" <%=(l_type==2)?"selected":""%>>Javascript
            </select></td>
            <td width="15%" class=txt>位置</td>
            <td>
                <select ID="posi_id" name="posi_name">
                    <option value="0" <%=(position==0)?"selected":""%>>请选择
                    <option value="1" <%=(position==1)?"selected":""%>>页头
                    <option value="3" <%=(position==3)?"selected":""%>>页尾
                </select>
            </td>
        </tr>

        <tr>
            <td width="20%" class=txt>说明</td>
            <td colspan="5"><input type=text name="notes" size=80 value="<%=(notes!=null)?notes:""%>"></td>
        </tr>
        <tr>
            <td colspan="6" height="15px"></td>
        </tr>
        <tr>
            <td width="10%" class=txt>程序体</td>
            <td colspan="5"><textarea name=program cols=80 rows=20><%=code%></textarea></td>
        </tr>
        <tr>
            <td width="10%" class=txt>&nbsp;</td>
            <td colspan="5">
                <input type=submit  name="ok "value="修改">
                <input type=button  name="cancel" value="返回" ONCLICK="javascript:window.close();">
            </td>
        </tr>
        </tbody>
    </form>
</table>
</html>
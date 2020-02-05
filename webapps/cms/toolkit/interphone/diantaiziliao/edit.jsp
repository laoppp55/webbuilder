<%@page import="com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil,
                com.bizwink.cms.util.SessionUtil,
                java.util.*,
                com.xml.Form,
                com.xml.FormPeer,
                com.xml.IFormManager" contentType="text/html;charset=GBK"
        %>
<script language="JavaScript" src="../images/setday.js"></script>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    int id = ParamUtil.getIntParameter(request, "id", 0);
    int dtzlid = ParamUtil.getIntParameter(request,"dtzlid",0);
    String shebeixinghao = ParamUtil.getParameter(request,"shebeixinghao");
    String chuchanghaoma = ParamUtil.getParameter(request,"chuchanghaoma");
    String beizhu = ParamUtil.getParameter(request,"beizhu");
    Form form = new Form();
    IFormManager formMgr = FormPeer.getInstance();
    List list = new ArrayList();
    list = formMgr.getAllDuiJiangJi();

    if (startflag == 1) {
        form.setDtzlid(dtzlid);
        form.setShebeixinghao(shebeixinghao);
        form.setChuchanghaoma(chuchanghaoma);
        form.setBeizhu(beizhu);
        formMgr.updateZiliao(form, id);
        response.sendRedirect("zhuduijiangji.jsp");
    }
    form = formMgr.getByIdziliao(id);
%>
<HTML>
<HEAD><TITLE>修改无线电台资料</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
    <SCRIPT language=javascript>
       function check()
        {
            if (form1.dtzlid.value == "")
            {
                alert("请输入电台申报ID！");
                return false;
            }
            if (form1.shebeixinghao.value == "")
            {
                alert("请输入设备型号！");
                return false;
            }
            return true;
        }
        function goto()
        {
            form1.action = "diantaiziliao.jsp";
            form1.submit();
        }
    </SCRIPT>

    <META content="MSHTML 6.00.2800.1479" name=GENERATOR>
</HEAD>
<BODY bgColor=#ffffff>
<FORM name=form1 action=edit.jsp method=post>
    <INPUT type=hidden value=1 name=startflag>
    <input type=hidden name="id" value="<%=id%>">

    <CENTER>
        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80%
               borderColorLight=#008000 border=1>
            <TBODY>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>修改资料</P></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>电台申报ID：</TD>
                <TD align=left width=70% height=32>&nbsp;
                    <select name="dtzlid">
                                <option value="-1">选择电台申报ID
                                <%
                                        if(list != null){
                                            for (int i = 0; i < list.size(); i++) {
                                                int djjid = 0;
                                                Form fo = (Form)list.get(i);
                                                djjid = fo.getDjjid();
                                        %>
                                <OPTION VALUE="<%=djjid%>"<%if(form.getDtzlid() == djjid){%>selected<%}%> ><%=djjid%>
                                </OPTION>
                                <%}}%>
                            </select><FONT
                        color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>设备型号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=shebeixinghao value="<%=form.getShebeixinghao()==null?"":form.getShebeixinghao()%>"><FONT
                        color=red>*</FONT>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>出厂号码：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=chuchanghaoma value="<%=form.getChuchanghaoma()==null?"":form.getChuchanghaoma()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>备注：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=beizhu value="<%=form.getBeizhu()==null?"":form.getBeizhu()%>">
                </TD>
            </TR>            
            <TR height=32>
                <TD colSpan=2><FONT
                        color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：带有*的项为必填项</FONT></TD>
            </TR>
            </TBODY>
        </TABLE>
        <P align=center><INPUT onclick="javascript:return check();" type=submit value=" 确 认 " name=Ok>&nbsp;&nbsp;
            <INPUT onclick=javascript:goto(); type=button value=返回列表 name=golist>
        </P>
    </CENTER>
</FORM>
</BODY>
</HTML>

<%@page import="com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil,
                com.bizwink.cms.util.SessionUtil,
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
    String shetaidanwei = ParamUtil.getParameter(request, "shetaidanwei");
    String suoshuxitong = ParamUtil.getParameter(request, "suoshuxitong");
    String address = ParamUtil.getParameter(request, "address");
    String lianxiren = ParamUtil.getParameter(request, "lianxiren");
    String phone = ParamUtil.getParameter(request, "phone");
    String chuanzhen = ParamUtil.getParameter(request, "chuanzhen");
    String shiyongdiqu = ParamUtil.getParameter(request, "shiyongdiqu");
    String diantaishuliang = ParamUtil.getParameter(request, "diantaishuliang");
    String diantaileibie = ParamUtil.getParameter(request, "diantaileibie");
    String shetailiyou = ParamUtil.getParameter(request, "shetailiyou");
    String lingdaoyijian = ParamUtil.getParameter(request, "lingdaoyijian");
    String guanlijuyijian = ParamUtil.getParameter(request, "guanlijuyijian");
    String xitongbianhao = ParamUtil.getParameter(request, "xitongbianhao");
    String zhizhaobianhao = ParamUtil.getParameter(request, "zhizhaobianhao");

    if (startflag == 1) {
        Form form = new Form();
        IFormManager formMgr = FormPeer.getInstance();
        form.setShetaidanwei(shetaidanwei);
        form.setSuoshuxitong(suoshuxitong);
        form.setAddress(address);
        form.setLianxiren(lianxiren);
        form.setPhone(phone);
        form.setChuanzhen(chuanzhen);
        form.setShiyongdiqu(shiyongdiqu);
        form.setDiantaishuliang(diantaishuliang);
        form.setDiantaileibie(diantaileibie);
        form.setShetailiyou(shetailiyou);
        form.setLingdaoyijian(lingdaoyijian);
        form.setGuanlijuyijian(guanlijuyijian);
        form.setXitongbianhao(xitongbianhao);
        form.setZhizhaobianhao(zhizhaobianhao);
        formMgr.insertDuiJiangJi(form);
        response.sendRedirect("zhuduijiangji.jsp");
    }
%>
<HTML>
<HEAD><TITLE>无线电台申报录入</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
    <SCRIPT language=javascript>
        function check()
        {
            if (form1.shetaidanwei.value == "")
            {
                alert("请输入设台单位！");
                return false;
            }
            if (form1.suoshuxitong.value == "")
            {
                alert("请输入所属系统！");
                return false;
            }
            return true;
        }

        function goto()
        {
            form1.action = "zhuduijiangji.jsp";
            form1.submit();
        }
    </SCRIPT>

    <META content="MSHTML 6.00.2800.1479" name=GENERATOR>
</HEAD>
<BODY bgColor=#ffffff>
<FORM name=form1 action=add.jsp method=post>
    <INPUT type=hidden value=1 name=startflag>

    <CENTER>
        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80%
               borderColorLight=#008000 border=1>
            <TBODY>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>添加新申报</P></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>设台单位：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=shetaidanwei><FONT
                        color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>所属系统：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=suoshuxitong> <FONT
                        color=red>*</FONT>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>地址：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=address></TD>
            </TR>
            <TR height=32>
                <TD align=right>联系人：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=lianxiren>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>电话：</TD>
                <TD align=left>&nbsp;<INPUT name=phone></TD>
            </TR>
            <TR height=32>
                <TD align=right>传真：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=chuanzhen>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>使用地区：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=shiyongdiqu></TD>
            </TR>
            <TR height=32>
                <TD align=right>电台数量：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=diantaishuliang></TD>
            </TR>
            <TR height=32>
                <TD align=right>电台类别：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=diantaileibie></TD>
            </TR>
            <TR height=32>
                <TD align=right>设台理由：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=shetailiyou></TD>
            </TR>
            <TR height=32>
                <TD align=right>上级主管单位领导意见：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=lingdaoyijian></TD>
            </TR>
            <TR height=32>
                <TD align=right>北京市无线电管理局意见：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=guanlijuyijian></TD>
            </TR>
            <TR height=32>
                <TD align=right>系统编号：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=xitongbianhao></TD>
            </TR>
            <TR height=32>
                <TD align=right>执照编号：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=zhizhaobianhao></TD>
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

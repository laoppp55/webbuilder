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
    String susongdanwei = ParamUtil.getParameter(request, "susongdanwei");
    String lianxiren1 = ParamUtil.getParameter(request, "lianxiren1");
    String lianxiren2 = ParamUtil.getParameter(request, "lianxiren2");
    String phone = ParamUtil.getParameter(request, "phone");
    String taizhanweizhi = ParamUtil.getParameter(request, "taizhanweizhi");
    String shebeixinghao = ParamUtil.getParameter(request, "shebeixinghao");
    String gongzuofangshi = ParamUtil.getParameter(request, "gongzuofangshi");
    String ganraoleixing = ParamUtil.getParameter(request, "ganraoleixing");
    String zhizhaobianhao = ParamUtil.getParameter(request, "zhizhaobianhao");
    String ganraopinlv = ParamUtil.getParameter(request, "ganraopinlv");
    String ganraoquyu = ParamUtil.getParameter(request, "ganraoquyu");
    String ganraoshiduan = ParamUtil.getParameter(request, "ganraoshiduan");
    String ganraoshijian = ParamUtil.getParameter(request, "ganraoshijian");
    String tezhengmiaoshu = ParamUtil.getParameter(request, "tezhengmiaoshu");
    String shenshuyaoqiu = ParamUtil.getParameter(request, "shenshuyaoqiu");

    if (startflag == 1) {
        Form form = new Form();
        IFormManager formMgr = FormPeer.getInstance();
        form.setSusongdanwei(susongdanwei);
        form.setLianxiren1(lianxiren1);
        form.setLianxiren2(lianxiren2);
        form.setPhone(phone);
        form.setTaizhanweizhi(taizhanweizhi);
        form.setShebeixinghao(shebeixinghao);
        form.setGongzuofangshi(gongzuofangshi);
        form.setGanraoleixing(ganraoleixing);
        form.setZhizhaobianhao(zhizhaobianhao);
        form.setGanraopinlv(ganraopinlv);
        form.setGanraoquyu(ganraoquyu);
        form.setGanraoshiduan(ganraoshiduan);
        form.setGanraoshijian(ganraoshijian);
        form.setTezhengmiaoshu(tezhengmiaoshu);
        form.setShenshuyaoqiu(shenshuyaoqiu);
        formMgr.insertGanRao(form);
        response.sendRedirect("ganrao.jsp");
    }
%>
<HTML>
<HEAD><TITLE>无线电干扰申诉录入</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
    <SCRIPT language=javascript>
        function check()
        {
            if (form1.susongdanwei.value == "")
            {
                alert("请输入诉讼单位！");
                return false;
            }
            if (form1.lianxiren1.value == "")
            {
                alert("请输入联系人1！");
                return false;
            }
            return true;
        }

        function goto()
        {
            form1.action = "ganrao.jsp";
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
                    <P align=center>添加新申诉</P></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>诉讼单位：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=susongdanwei><FONT
                        color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>联系人1：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=lianxiren1> <FONT
                        color=red>*</FONT>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>联系人2：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=lianxiren2></TD>
            </TR>
            <TR height=32>
                <TD align=right>联系电话：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=phone>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>台站位置：</TD>
                <TD align=left>&nbsp;<INPUT name=taizhanweizhi></TD>
            </TR>
            <TR height=32>
                <TD align=right>设备型号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=shebeixinghao>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>工作方式：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=gongzuofangshi></TD>
            </TR>
            <TR height=32>
                <TD align=right>干扰类型：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=ganraoleixing></TD>
            </TR>
            <TR height=32>
                <TD align=right>执照编号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=zhizhaobianhao></TD>
            </TR>
            <TR height=32>
                <TD align=right>干扰频率：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=ganraopinlv></TD>
            </TR>
            <TR height=32>
                <TD align=right>干扰区域：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=ganraoquyu></TD>
            </TR>
            <TR height=32>
                <TD align=right>初次干扰时间及发生时段：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=ganraoshiduan></TD>
            </TR>
            <TR height=32>
                <TD align=right>出现频度及每次持续时间：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=ganraoshijian></TD>
            </TR>
            <TR height=32>
                <TD align=right>干扰信号特征描述：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=tezhengmiaoshu></TD>
            </TR>
            <TR height=32>
                <TD align=right>申述要求：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=shenshuyaoqiu></TD>
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

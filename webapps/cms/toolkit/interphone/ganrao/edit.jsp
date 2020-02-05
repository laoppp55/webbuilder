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
    int id = ParamUtil.getIntParameter(request, "id", 0);
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
    Form form = new Form();
    IFormManager formMgr = FormPeer.getInstance();

    if (startflag == 1) {
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
        formMgr.updateGanRao(form, id);
        response.sendRedirect("ganrao.jsp");
    }
    form = formMgr.getByIdganrao(id);
%>
<HTML>
<HEAD><TITLE>修改无线电干扰信息</TITLE>
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
<FORM name=form1 action=edit.jsp method=post>
    <INPUT type=hidden value=1 name=startflag>
    <input type=hidden name="id" value="<%=id%>">

    <CENTER>
        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80%
               borderColorLight=#008000 border=1>
            <TBODY>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>修改申诉</P></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>诉讼单位：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=susongdanwei value="<%=form.getSusongdanwei()==null?"":form.getSusongdanwei()%>"><FONT
                        color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right>联系人1：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=lianxiren1 value="<%=form.getLianxiren1()==null?"":form.getLianxiren1()%>"><FONT
                        color=red>*</FONT>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>联系人2：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=lianxiren2 value="<%=form.getLianxiren2()==null?"":form.getLianxiren2()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>联系电话：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=phone value="<%=form.getPhone()==null?"":form.getPhone()%>">
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>台站位置：</TD>
                <TD align=left>&nbsp;<INPUT name=taizhanweizhi value="<%=form.getTaizhanweizhi()==null?"":form.getTaizhanweizhi()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>设备型号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=shebeixinghao value="<%=form.getShebeixinghao()==null?"":form.getShebeixinghao()%>">
                </TD>
            </TR>
            <TR height=32>
                <TD align=right>工作方式：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=gongzuofangshi value="<%=form.getGongzuofangshi()==null?"":form.getGongzuofangshi()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>干扰类型：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=ganraoleixing value="<%=form.getGanraoleixing()==null?"":form.getGanraoleixing()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>执照编号：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=zhizhaobianhao value="<%=form.getZhizhaobianhao()==null?"":form.getZhizhaobianhao()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>干扰频率：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=ganraopinlv value="<%=form.getGanraopinlv()==null?"":form.getGanraopinlv()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>干扰区域：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=ganraoquyu value="<%=form.getGanraoquyu()==null?"":form.getGanraoquyu()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>初次干扰时间及发生时段：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=ganraoshiduan value="<%=form.getGanraoshiduan()==null?"":form.getGanraoshiduan()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>出现频度及每次持续时间：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=ganraoshijian value="<%=form.getGanraoshijian()==null?"":form.getGanraoshijian()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>干扰信号特征描述：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=tezhengmiaoshu value="<%=form.getTezhengmiaoshu()==null?"":form.getTezhengmiaoshu()%>"></TD>
            </TR>
            <TR height=32>
                <TD align=right>申述要求：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=shenshuyaoqiu value="<%=form.getShenshuyaoqiu()==null?"":form.getShenshuyaoqiu()%>"></TD>
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

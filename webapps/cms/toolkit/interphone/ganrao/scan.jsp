<%@page import="com.bizwink.cms.kings.supplier.ISupplierSuManager,
                com.bizwink.cms.kings.supplier.SupplierSu,
                com.bizwink.cms.kings.supplier.SupplierSuPeer,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.xml.*" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    Form form = new Form();
    IFormManager formMgr = FormPeer.getInstance();
    form = formMgr.getByIdganrao(id);

    String susongdanwei = form.getSusongdanwei();
    String lianxiren1 = form.getLianxiren1();
    String lianxiren2 = form.getLianxiren2();
    String phone = form.getPhone();
    String taizhanweizhi = form.getTaizhanweizhi();
    String shebeixinghao = form.getShebeixinghao();
    String gongzuofangshi = form.getGongzuofangshi();
    String ganraoleixing = form.getGanraoleixing();
    String zhizhaobianhao = form.getZhizhaobianhao();
    String ganraopinlv = form.getGanraopinlv();
    String ganraoquyu = form.getGanraoquyu();
    String ganraoshiduan = form.getGanraoshiduan();
    String ganraoshijian = form.getGanraoshijian();
    String tezhengmiaoshu = form.getTezhengmiaoshu();
    String shenshuyaoqiu = form.getShenshuyaoqiu();
%>
<HTML>
<HEAD><TITLE>New Page 1</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
</HEAD>
<BODY>
<CENTER>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=497
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR>
            <TD width=493 bgColor=#33ccff colSpan=2 height=32>
                <P align=center>无线电干扰申诉信息</P></TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>诉讼单位：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=susongdanwei == null?"--":susongdanwei%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>联系人1：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=lianxiren1 == null ? "--" : lianxiren1%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>联系人2：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=lianxiren2 == null ? "--" : lianxiren2%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>联系电话：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=phone == null ? "--" : phone%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>台站位置：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=taizhanweizhi == null ? "--" : taizhanweizhi%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>设备型号：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=shebeixinghao == null ? "--" : shebeixinghao%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>工作方式：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=gongzuofangshi == null ? "--" : gongzuofangshi%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>干扰类型：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=ganraoleixing == null ? "--" : ganraoleixing%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>执照编号：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=zhizhaobianhao == null ? "--" : zhizhaobianhao%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>干扰频率：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=ganraopinlv == null ? "--" : ganraopinlv%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>干扰区域：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=ganraoquyu == null ? "--" : ganraoquyu%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>初次干扰时间及发生时段：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=ganraoshiduan == null ? "--" : ganraoshiduan%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>出现频度及每次持续时间：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=ganraoshijian == null ? "--" : ganraoshijian%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>干扰信号特征描述：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=tezhengmiaoshu == null ? "--" : tezhengmiaoshu%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>申述要求：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=shenshuyaoqiu == null ? "--" : shenshuyaoqiu%>
            </TD>
        </TR>
        </TBODY>
    </TABLE>
</CENTER>
<BR><BR>
<CENTER><INPUT onclick=javascript:history.go(-1); type=button value=" 返 回 ">
</CENTER>
</BODY>
</HTML>

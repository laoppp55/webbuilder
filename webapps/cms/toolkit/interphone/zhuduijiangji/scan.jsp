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
    form = formMgr.getByIdduijiangji(id);

    String shetaidanwei = form.getShetaidanwei();
    String suoshuxitong = form.getSuoshuxitong();
    String address = form.getAddress();
    String phone = form.getPhone();
    String lianxiren = form.getLianxiren();
    String chuanzhen = form.getChuanzhen();
    String shiyongdiqu = form.getShiyongdiqu();
    String diantaishuliang = form.getDiantaishuliang();
    String diantaileibie = form.getDiantaileibie();
    String shetailiyou = form.getShetailiyou();
    String lingdaoyijian = form.getLingdaoyijian();
    String guanlijuyijian = form.getGuanlijuyijian();
    String xitongbianhao = form.getXitongbianhao();
    String zhizhaobianhao = form.getZhizhaobianhao();
    
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
                <P align=center>无线电台申报信息</P></TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>设台单位：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=shetaidanwei == null?"--":shetaidanwei%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>所属系统：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=suoshuxitong == null ? "--" : suoshuxitong%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>地址：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=address == null ? "--" : address%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>联系人：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=lianxiren == null ? "--" : lianxiren%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>电话：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=phone == null ? "--" : phone%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>传真：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=chuanzhen == null ? "--" : chuanzhen%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>使用地区：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=shiyongdiqu == null ? "--" : shiyongdiqu%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>电台数量：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=diantaishuliang == null ? "--" : diantaishuliang%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>电台类别：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=diantaileibie == null ? "--" : diantaileibie%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>设台理由：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=shetailiyou == null ? "--" : shetailiyou%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>上级主管单位领导意见：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=lingdaoyijian == null ? "--" : lingdaoyijian%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>北京市无线电管理局意见：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=guanlijuyijian == null ? "--" : guanlijuyijian%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>系统编号：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=xitongbianhao == null ? "--" : xitongbianhao%>
            </TD>
        </TR>
        <TR>
            <TD align=right width=113 height=32>执照编号：</TD>
            <TD align=left width=378 height=32>&nbsp;<%=zhizhaobianhao == null ? "--" : zhizhaobianhao%>
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

<%@ page import="java.util.*,
                 com.bizwink.cms.toolkit.companyinfo.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.server.CmsServer" contentType="text/html;charset=GBK" %>
<%@ page import="com.bizwink.webapps.feedback.*" %>

<%
    String sitename = request.getServerName();
    IFeedbackManager feedMgr = FeedbackPeer.getInstance();
    int siteid = feedMgr.getSiteID(sitename);
    int mappointid = ParamUtil.getIntParameter(request, "id", 0);
    int curpage = ParamUtil.getIntParameter(request, "curpage", 0);
    ICompanyinfoManager companyManager = CompanyinfoPeer.getInstance();
    Companyinfo com = null;
    List medias = new ArrayList();
    try {
        com = companyManager.getACompanyInfo(mappointid,siteid);
        medias = companyManager.getCompanyMediasInfos(siteid,mappointid);
    } catch (Exception e) {
        e.printStackTrace();
    }

    //计算总页数
    int pagesize = 20;
    int total = medias.size();
    int pages = total / pagesize;
    int extra = total % pagesize;
    if (extra > 0) pages = pages + 1;

    //计算当前页所在导航棒的区间
    int navbar_len = 10;
    int navbar_num = pages / navbar_len;
    int cur_navbar = curpage / navbar_len;
    int navbar_extra = pages % navbar_len;
    if (navbar_extra > 0) navbar_num = navbar_num + 1;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>牛街政务网 - 全程办事代理</title>
    <SCRIPT language="JavaScript" src="menu.js"></SCRIPT>
    <SCRIPT src="yc.js" type="text/javascript"></SCRIPT>
    <link href="web_cs.css" rel="stylesheet" type="text/css" />
</head>

<body>
<center>
<TABLE cellSpacing=0 cellPadding=0>
    <TBODY>
    <TR>
        <TD><IMG alt="" src="/images/86851Snap6.jpg"
                 border=0></TD></TR></TBODY></TABLE><TABLE style="MARGIN-TOP: 0px; MARGIN-LEFT: 0px; WIDTH: 1002px" cellSpacing=0
                                                           cellPadding=0>
    <TBODY>
    <TR>
        <TD>
            <TABLE
                    style=" background:url(/images/menu_bg.jpg); WIDTH: 1002px; HEIGHT: 32px"
                    cellSpacing=0 cellPadding=0>
                <TBODY>
                <TR>
                    <TD>
                        <TABLE
                                style="MARGIN-TOP: 0px; MARGIN-LEFT: 20px; WIDTH: 950px; HEIGHT: 10px"
                                cellSpacing=0 cellPadding=0>
                            <TBODY>
                            <TR>
                                <TD style="BORDER-LEFT: #d2d2d2 0px solid" align=middle><A
                                        style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: #008000"
                                        href="http://nj.bjxw.gov.cn/NJZWIndex.ycs">首　　页</A></TD>
                                <TD style="BORDER-LEFT: #d2d2d2 1px solid" align=middle><A
                                        style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: #008000"
                                        href="http://nj.bjxw.gov.cn/NJZWjdgk.ycs">街道概况</A></TD>
                                <TD style="BORDER-LEFT: #d2d2d2 1px solid" align=middle><A
                                        style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: #008000"
                                        href="http://nj.bjxw.gov.cn/NJZWzwgk.ycs">政务公开</A></TD>
                                <TD style="BORDER-LEFT: #d2d2d2 1px solid" align=middle><A
                                        style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: #008000"
                                        href="http://nj.bjxw.gov.cn/NJZWqcbs.ycs">全程办事代理</A></TD>
                                <TD style="BORDER-LEFT: #d2d2d2 1px solid" align=middle><A
                                        style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: #008000"
                                        href="http://nj.bjxw.gov.cn/NJZWsqjs.ycs">社区建设</A></TD>
                                <TD style="BORDER-LEFT: #d2d2d2 1px solid" align=middle><A
                                        style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: #008000"
                                        href="http://nj.bjxw.gov.cn/NJZWmztj.ycs">民族团结</A></TD>
                                <TD style="BORDER-LEFT: #d2d2d2 1px solid" align=middle><A
                                        style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: #008000"
                                        href="http://nj.bjxw.gov.cn/NJZWshbz.ycs">社会保障</A></TD>
                                <TD style="BORDER-LEFT: #d2d2d2 1px solid" align=middle><A
                                        style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: #008000"
                                        href="http://nj.bjxw.gov.cn/NJZWsqfw.ycs">社区服务</A></TD>
                                <TD style="BORDER-LEFT: #d2d2d2 1px solid" align=middle><A
                                        style="FONT-WEIGHT: bold; FONT-SIZE: 14px; COLOR: #008000"
                                        href="http://nj.bjxw.gov.cn/NJZWqyjj.ycs">区域经济</A></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<table width="1002" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" valign="middle"><img src="/images/tm.gif" width="1" height="2" /></td>
    </tr>
    <tr>
        <td align="left" valign="middle" style="background:url(/images/title_bg.jpg) no-repeat; height:29px; font-size:14px; font-weight:bold; color:#FFFFFF;"><span style="padding:3px 0px 0px 35px; display:block;">北京市宣武区人民政府牛街街道办事处</span></td>
    </tr>
</table><table width="1000" border="0" cellpadding="0" cellspacing="0"style="border-right-width: 1px;border-bottom-width: 1px;border-left-width: 1px;border-right-style: solid;border-bottom-style: solid;border-left-style: solid;border-right-color: #6DBE09;border-bottom-color: #6DBE09;border-left-color: #6DBE09;">
    <tr>
        <td align="left" valign="top" style="padding:20px;"><table width="100%" border="0" cellspacing="0" cellpadding="0" style=" line-height:25px; font-size:14px;">
            <tr>
                <td>企业名称：<%=com.getCompanyname()%>
                    <br />
                    联 系 人：
                    <br />
                    地    址：<%=com.getCompanyaddress()%>
                    <br />
                    电    话：<%=com.getCompanyphone()%>
                    <br />
                    邮    编：<%=com.getPostcode()%><br />
                    企业介绍：<%=com.getSummary()%></td>
            </tr>
        </table></td>
    </tr>
</table> <table width="1002" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" valign="middle"><img src="/images/tm.gif" width="1" height="10" /></td>
    </tr>
    <tr>
        <td align="left" valign="middle" style="background:url(/images/title_bg.jpg) no-repeat; height:29px; font-size:14px; font-weight:bold; color:#FFFFFF;"><span style="padding:3px 0px 0px 35px; display:block;">企业视频</span></td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0"style="border-right-width: 1px;border-bottom-width: 1px;border-left-width: 1px;border-right-style: solid;border-bottom-style: solid;border-left-style: solid;border-right-color: #6DBE09;border-bottom-color: #6DBE09;border-left-color: #6DBE09;">
    <tr>
        <td align="left" valign="top" >
            <table width="1000" border="0" cellspacing="16" cellpadding="0" style=" line-height:18px; font-size:14px;">
                <tr>
                    <%
                        int num = curpage * pagesize;
                        String mediaplayurl = "/_commons/play.jsp";
                    %>
                    <%
                        if ((curpage+1)*pagesize > total) {
                            for (int i=num; i<total; i++) {
                                String mediaurl = (String)medias.get(i);
                                int posi = mediaurl.indexOf(".");
                                String small_picurl = mediaurl.substring(0,posi) + ".jpg";
                    %>
                    <td width="224" align="left" valign="top"><a href="<%=mediaplayurl%>?mf=<%=mediaurl%>" target="_blank"><img src="<%=small_picurl%>" width="220" height="195" vspace="7" border="0" /></a></td>
                    <%}} else {
                        for (int i=num; i<num + pagesize; i++) {
                            String mediaurl = (String)medias.get(i);
                            int posi = mediaurl.indexOf(".");
                            String small_picurl = mediaurl.substring(0,posi) + ".jpg";
                    %>
                    <td width="224" align="left" valign="top"><a href="<%=mediaplayurl%>?mf=<%=mediaurl%>" target="_blank"><img src="<%=small_picurl%>" width="220" height="195" vspace="7" border="0" /></a></td>
                    <%}}%>
                </tr>
            </table>
        </td>
    </tr>

    <tr>
        <td>
            <%
                if(pages > 1) {
                    int startnum = cur_navbar * 10 + 1;
                    if ((cur_navbar+1) == navbar_num) {
                        for(int i=startnum; i<pages; i++) {

                        }
                    } else {
                        for(int i=startnum; i<(cur_navbar+1)*10; i++) {

                        }
                    }
                }
            %>
        </td>
    </tr>
</table>
<TABLE width="1002" cellPadding=0 cellSpacing=0 bgColor=#ffffff>
<TBODY>
<TR>
<TD vAlign=top>
<TABLE style="MARGIN-TOP: 5px; MARGIN-LEFT: 0px; WIDTH: 960px"
       cellSpacing=0 cellPadding=0 border=0>
    <TBODY>
    <TR>
        <TD>
            <TABLE style="BACKGROUND-IMAGE: url(/img/tm.gif); HEIGHT: 34px"
                   cellSpacing=0 cellPadding=0>
                <TBODY>
                <TR>
                    <TD
                            style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"><IMG
                            style="HEIGHT: 0px" src="/images/tm.gif"></TD>
                    <TD
                            style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: url(/images/84013Snap21.jpg); WIDTH: 100%"
                            vAlign=top>
                        <DIV id=CNT_4701/NJZWqcbs/14274
                             style="MARGIN-TOP: 5px; MARGIN-LEFT: 15px; WIDTH: 965px; MARGIN-RIGHT: 20px">
                            <TABLE align="center" cellPadding=0 cellSpacing=0>
                                <TBODY>
                                <TR>
                                    <TD align="center">
                                        <TABLE cellSpacing=0 cellPadding=0>
                                            <TBODY>
                                            <TR>
                                                <TD vAlign=top align=left>
                                                    <DIV id=CNT_4701/NJZWqcbs/14274/14275><SELECT
                                                            style="WIDTH: 160px; COLOR: #191919; FONT-FAMILY: verdana; BACKGROUND-COLOR: #ffffff"
                                                            onchange="if (this.selectedIndex != 0){window.open(this.options[this.selectedIndex].value,'_blank','');}"
                                                            name=PostWeb value> <OPTION value=""
                                                                                        selected>国家部委</OPTION></SELECT><!--PAGELETSUCCESS--></DIV></TD>
                                                <TD vAlign=top align=left>
                                                    <TABLE
                                                            style="MARGIN-TOP: 0px; MARGIN-LEFT: 15px; WIDTH: 195px"
                                                            cellSpacing=0 cellPadding=0 border=0>
                                                        <TBODY>
                                                        <TR>
                                                            <TD>
                                                                <TABLE
                                                                        style="BACKGROUND-IMAGE: url(/img/tm.gif); HEIGHT: 10px"
                                                                        cellSpacing=0 cellPadding=0>
                                                                    <TBODY>
                                                                    <TR>
                                                                        <TD
                                                                                style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"><IMG
                                                                                style="HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD>
                                                                        <TD
                                                                                style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none; WIDTH: 100%"
                                                                                vAlign=top>
                                                                            <DIV id=CNT_4701/NJZWqcbs/14274/14276
                                                                                 style="MARGIN-TOP: 0px; MARGIN-LEFT: 0px; WIDTH: 100%; MARGIN-RIGHT: 0px"><SELECT
                                                                                    style="WIDTH: 195px; COLOR: #191919; FONT-FAMILY: verdana; BACKGROUND-COLOR: #ffffff"
                                                                                    onchange="if (this.selectedIndex != 0){window.open(this.options[this.selectedIndex].value,'_blank','');}"
                                                                                    name=PostWeb value> <OPTION value=""
                                                                                                                selected>市政府委办局</OPTION></SELECT><!--PAGELETSUCCESS--></DIV></TD>
                                                                        <TD
                                                                                style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"><IMG
                                                                                style="HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD></TR></TBODY></TABLE></TD></TR>
                                                        <TR>
                                                            <TD>
                                                                <TABLE
                                                                        style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"
                                                                        cellSpacing=0 cellPadding=0>
                                                                    <TBODY>
                                                                    <TR>
                                                                        <TD style="BACKGROUND-IMAGE: none"><IMG
                                                                                style="WIDTH: 0px; HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD>
                                                                        <TD style="WIDTH: 100%"><IMG style="WIDTH: 0px"
                                                                                                     src="/images/tm.gif"></TD>
                                                                        <TD style="BACKGROUND-IMAGE: none"><IMG
                                                                                style="WIDTH: 0px; HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
                                                <TD vAlign=top align=left>
                                                    <TABLE
                                                            style="MARGIN-TOP: 0px; MARGIN-LEFT: 15px; WIDTH: 195px"
                                                            cellSpacing=0 cellPadding=0 border=0>
                                                        <TBODY>
                                                        <TR>
                                                            <TD>
                                                                <TABLE
                                                                        style="BACKGROUND-IMAGE: url(/img/tm.gif); HEIGHT: 10px"
                                                                        cellSpacing=0 cellPadding=0>
                                                                    <TBODY>
                                                                    <TR>
                                                                        <TD
                                                                                style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"><IMG
                                                                                style="HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD>
                                                                        <TD
                                                                                style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none; WIDTH: 100%"
                                                                                vAlign=top>
                                                                            <DIV id=CNT_4701/NJZWqcbs/14274/14277
                                                                                 style="MARGIN-TOP: 0px; MARGIN-LEFT: 0px; WIDTH: 100%; MARGIN-RIGHT: 0px"><SELECT
                                                                                    style="WIDTH: 195px; COLOR: #191919; FONT-FAMILY: verdana; BACKGROUND-COLOR: #ffffff"
                                                                                    onchange="if (this.selectedIndex != 0){window.open(this.options[this.selectedIndex].value,'_blank','');}"
                                                                                    name=PostWeb value> <OPTION value=""
                                                                                                                selected>街道办事处网站</OPTION> <OPTION
                                                                                    value=/NJZWxxxq.ycs?GUID=497834>申请廉租住房租房补贴须知</OPTION></SELECT><!--PAGELETSUCCESS--></DIV></TD>
                                                                        <TD
                                                                                style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"><IMG
                                                                                style="HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD></TR></TBODY></TABLE></TD></TR>
                                                        <TR>
                                                            <TD>
                                                                <TABLE
                                                                        style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"
                                                                        cellSpacing=0 cellPadding=0>
                                                                    <TBODY>
                                                                    <TR>
                                                                        <TD style="BACKGROUND-IMAGE: none"><IMG
                                                                                style="WIDTH: 0px; HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD>
                                                                        <TD style="WIDTH: 100%"><IMG style="WIDTH: 0px"
                                                                                                     src="/images/tm.gif"></TD>
                                                                        <TD style="BACKGROUND-IMAGE: none"><IMG
                                                                                style="WIDTH: 0px; HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD>
                                                <TD vAlign=top align=left>
                                                    <TABLE
                                                            style="MARGIN-TOP: 0px; MARGIN-LEFT: 15px; WIDTH: 195px"
                                                            cellSpacing=0 cellPadding=0 border=0>
                                                        <TBODY>
                                                        <TR>
                                                            <TD>
                                                                <TABLE
                                                                        style="BACKGROUND-IMAGE: url(/img/tm.gif); HEIGHT: 10px"
                                                                        cellSpacing=0 cellPadding=0>
                                                                    <TBODY>
                                                                    <TR>
                                                                        <TD
                                                                                style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"><IMG
                                                                                style="HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD>
                                                                        <TD
                                                                                style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none; WIDTH: 100%"
                                                                                vAlign=top>
                                                                            <DIV id=CNT_4701/NJZWqcbs/14274/14278
                                                                                 style="MARGIN-TOP: 0px; MARGIN-LEFT: 0px; WIDTH: 100%; MARGIN-RIGHT: 0px"><SELECT
                                                                                    style="WIDTH: 195px; COLOR: #191919; FONT-FAMILY: verdana; BACKGROUND-COLOR: #ffffff"
                                                                                    onchange="if (this.selectedIndex != 0){window.open(this.options[this.selectedIndex].value,'_blank','');}"
                                                                                    name=PostWeb value> <OPTION value=""
                                                                                                                selected>其他网站</OPTION></SELECT><!--PAGELETSUCCESS--></DIV></TD>
                                                                        <TD
                                                                                style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"><IMG
                                                                                style="HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD></TR></TBODY></TABLE></TD></TR>
                                                        <TR>
                                                            <TD>
                                                                <TABLE
                                                                        style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"
                                                                        cellSpacing=0 cellPadding=0>
                                                                    <TBODY>
                                                                    <TR>
                                                                        <TD style="BACKGROUND-IMAGE: none"><IMG
                                                                                style="WIDTH: 0px; HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD>
                                                                        <TD style="WIDTH: 100%"><IMG style="WIDTH: 0px"
                                                                                                     src="/images/tm.gif"></TD>
                                                                        <TD style="BACKGROUND-IMAGE: none"><IMG
                                                                                style="WIDTH: 0px; HEIGHT: 0px"
                                                                                src="/images/tm.gif"></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><!--PAGELETSUCCESS--></DIV></TD>
                    <TD
                            style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"><IMG
                            style="HEIGHT: 0px"
                            src="/images/tm.gif"></TD></TR></TBODY></TABLE></TD></TR>
    <TR>
        <TD>
            <TABLE style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"
                   cellSpacing=0 cellPadding=0>
                <TBODY>
                <TR>
                    <TD style="BACKGROUND-IMAGE: none"><IMG
                            style="WIDTH: 0px; HEIGHT: 0px"
                            src="/images/tm.gif"></TD>
                    <TD style="WIDTH: 100%"><IMG style="WIDTH: 0px"
                                                 src="/images/tm.gif"></TD>
                    <TD style="BACKGROUND-IMAGE: none"><IMG
                            style="WIDTH: 0px; HEIGHT: 0px"
                            src="/images/tm.gif"></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<TABLE style="MARGIN-TOP: 0px; MARGIN-LEFT: 0px; WIDTH: 1002px"
       cellSpacing=0 cellPadding=0 border=0>
    <TBODY>
    <TR>
        <TD>
            <TABLE style="BACKGROUND-IMAGE: url(/img/tm.gif); HEIGHT: 50px"
                   cellSpacing=0 cellPadding=0>
                <TBODY>
                <TR>
                    <TD
                            style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"><IMG
                            style="HEIGHT: 0px" src="/images/tm.gif"></TD>
                    <TD
                            style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none; WIDTH: 100%"
                            vAlign=top>
                        <DIV id=CNT_4701/NJZWqcbs/14279
                             style="MARGIN-TOP: 5px; MARGIN-LEFT: 0px; WIDTH: 1000px; MARGIN-RIGHT: 0px">
                            <TABLE width="100%" cellPadding=0 cellSpacing=0>
                                <TBODY>
                                <TR>
                                    <TD>
                                        <P align=center><FONT face=Verdana
                                                              color=#06a506>版权所有：北京市西城区牛街街道办事处<BR>最佳观看效果：1024＊768；IE5.0或以上版本<BR>ICP备案编号：京ICP备05083559号</FONT></P></TD></TR></TBODY></TABLE><!--PAGELETSUCCESS--></DIV></TD>
                    <TD
                            style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"><IMG
                            style="HEIGHT: 0px"
                            src="/images/tm.gif"></TD></TR></TBODY></TABLE></TD></TR>
    <TR>
        <TD>
            <TABLE style="BACKGROUND-POSITION: 0% 0%; BACKGROUND-IMAGE: none"
                   cellSpacing=0 cellPadding=0>
                <TBODY>
                <TR>
                    <TD style="BACKGROUND-IMAGE: none"><IMG
                            style="WIDTH: 0px; HEIGHT: 0px"
                            src="/images/tm.gif"></TD>
                    <TD style="WIDTH: 100%"><IMG style="WIDTH: 0px"
                                                 src="/images/tm.gif"></TD>
                    <TD style="BACKGROUND-IMAGE: none"><IMG
                            style="WIDTH: 0px; HEIGHT: 0px"
                            src="/images/tm.gif"></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
</center>
</body>
</html>

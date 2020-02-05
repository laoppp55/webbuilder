<%@ page import="com.sinopec.stock.IStockManager" %>
<%@ page import="com.sinopec.stock.StockPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sinopec.stock.Stock" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    String d = (ParamUtil.getParameter(request, "d", false) == null) ? "" : ParamUtil.getParameter(request, "d", false);

    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    boolean doSearch = ParamUtil.getBooleanParameter(request, "doSearch");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String startDate = null;
    String endDate = null;
    int gid = 0;
    List stocks = null;
    int ranges = 20;
    int range = 0;
    int count = 0;
    String para = "";
    String stockcode = "";

    if (doSearch == true) {
        range = Integer.parseInt(request.getParameter("range"));
        startDate = ParamUtil.getParameter(request, "startDate");
        endDate = ParamUtil.getParameter(request, "endDate");
        gid = ParamUtil.getIntParameter(request, "gid", 0);

        System.out.println("gid=" + gid);

        if (gid == 5)
            stockcode = "600028.SS";
        else if (gid == 1)
            stockcode = "SNP";
        else if (gid == 3)
            stockcode = "0386.HK";

        java.sql.Date s_date = java.sql.Date.valueOf(startDate);
        java.sql.Date e_date = java.sql.Date.valueOf(endDate);

        para = "&range=" + range + "&doSearch=true&startDate=" + ((startDate != null) ? startDate : "");
        para = para + "&endDate=" + ((endDate != null) ? endDate : "");
        para = para + "&gid=" + gid;

        IStockManager stockpriceManager = StockPeer.getInstance();
        //out.println("***"+range);

        if (range == 2) {        //按月查询
            //count = stockpriceManager.getStockpriceCountByMonths(s_date,e_date,gid);
            stocks = stockpriceManager.getStockpriceCountByMonths(s_date, e_date, stockcode);
            count = stocks.size();
        } else if (range == 1) {   //按周查询
            //count = stockpriceManager.getStockpriceCountByWeeks(s_date,e_date,gid);
            stocks = stockpriceManager.getStockpricesByWeeks(s_date, e_date, stockcode, 0, 0);
            count = stocks.size();
        } else {                  //按天查询           
            count = stockpriceManager.getStockpriceCount(s_date, e_date, stockcode);
            stocks = stockpriceManager.getStockprices(s_date, e_date, stockcode, startrow, 20);
        }
    }
    java.util.Date sysDate = new java.util.Date();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="">
<title>欢迎访问中国石油化工股份有限公司网站</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
<link href="/images/sinopec.css" type="text/css" rel="stylesheet"/>
<script language="JavaScript" src="/inc/public.js"></script>
<script language="JavaScript" src="/inc/functions.js"></script>
<script type="text/JavaScript">
    <!--
    function MM_preloadImages() { //v3.0
        var d = document;
        if (d.images) {
            if (!d.MM_p) d.MM_p = new Array();
            var i,j = d.MM_p.length,a = MM_preloadImages.arguments;
            for (i = 0; i < a.length; i++)
                if (a[i].indexOf("#") != 0) {
                    d.MM_p[j] = new Image;
                    d.MM_p[j++].src = a[i];
                }
        }
    }
    function MM_swapImgRestore() { //v3.0
        var i,x,a = document.MM_sr;
        for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++) x.src = x.oSrc;
    }
    function MM_findObj(n, d) { //v4.01
        var p,i,x;
        if (!d) d = document;
        if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
            d = parent.frames[n.substring(p + 1)].document;
            n = n.substring(0, p);
        }
        if (!(x = d[n]) && d.all) x = d.all[n];
        for (i = 0; !x && i < d.forms.length; i++) x = d.forms[i][n];
        for (i = 0; !x && d.layers && i < d.layers.length; i++) x = MM_findObj(n, d.layers[i].document);
        if (!x && d.getElementById) x = d.getElementById(n);
        return x;
    }
    function MM_swapImage() { //v3.0
        var i,j = 0,x,a = MM_swapImage.arguments;
        document.MM_sr = new Array;
        for (i = 0; i < (a.length - 2); i += 3)
            if ((x = MM_findObj(a[i])) != null) {
                document.MM_sr[j++] = x;
                if (!x.oSrc) x.oSrc = x.src;
                x.src = a[i + 2];
            }
    }
    var currentid = 0;
    var currentpid = 0;
    //-->

    function queryFunc(frm) {

        var stDateStr = frm.startDate.value;
        var edDateStr = frm.endDate.value;

        if (frm.startDate.value == "") {
            alert("起始日期不能为空。");
            frm.startDate.focus();
            return false;
        }
        if (frm.gid.value == 1) {
            if (frm.startDate.value < "2000-10-19") {
                alert("起始日期必须在2000-10-19之后");
                return false;
            }
        }
        if (frm.gid.value == 3) {
            if (frm.startDate.value < "2000-10-19") {
                alert("起始日期必须在2000-10-19之后");
                return false;
            }
        }
        if (frm.gid.value == 5) {
            if (frm.startDate.value < "2001-08-09") {
                alert("起始日期必须在2001-08-09之后");
                return false;
            }
        }
        if (frm.endDate.value == "") {
            alert("终止日期不能为空。");
            frm.endDate.focus();
            return false;
        }
        frm.yearstart.value = stDateStr.substring(0, stDateStr.indexOf("-"));
        frm.monthstart.value = stDateStr.substring(stDateStr.indexOf("-") + 1, stDateStr.lastIndexOf("-"));
        frm.daystart.value = stDateStr.substring(stDateStr.lastIndexOf("-") + 1, stDateStr.length);

        frm.yearend.value = edDateStr.substring(0, edDateStr.indexOf("-"));
        frm.monthend.value = edDateStr.substring(edDateStr.indexOf("-") + 1, edDateStr.lastIndexOf("-"));
        frm.dayend.value = edDateStr.substring(edDateStr.lastIndexOf("-") + 1, edDateStr.length);
    }

function getDates(values){
  if(values==1){
    document.getElementById("startDate").value="2000-10-19";
  }else if(values==3){
    document.getElementById("startDate").value="2000-10-19";
  }else if(values==5){
     document.getElementById("startDate").value="2001-08-09";
  }else
     document.getElementById("startDate").value="2001-08-09";
}
</script>
<style type="text/css">
    <!--
    body {
        margin-top: 5px;
        margin-left: 0px;
        margin-right: 0px;
        margin-bottom: 0px;
    }

    --></style>
</head>
<body xmlns="">
<center>
<%@ include file="/inc/head.shtml" %>
<%@ include file="/inc/investormenu.shtml" %>
<table cellspacing="0" cellpadding="0" width="950" border="0">
<tbody>
<tr>
<td valign="top" width="178">
    <%@ include file="/investor_centre/menuTree.shtml" %>
</td>
<td valign="top" width="1"><img height="419" alt="" width="1" src="/images/line-insite.gif"/></td>
<td width="25"><img height="1" alt="" width="1" src="/images/space.gif"/></td>
<td valign="top">
<table cellspacing="0" cellpadding="0" width="100%" border="0">
    <tbody>
        <tr>
            <td><img height="148" alt="" width="748" src="/images/top-pic-investor.jpg"/></td>
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="0" width="100%" border="0">
    <tbody>
        <tr>
            <td><img height="2" alt="" width="2" src="/images/space.gif"/></td>
        </tr>
        <tr>
            <td><img height="2" alt="" width="746" src="/images/colo-bar-insite.gif"/></td>
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="0" border="0">
    <tbody>
        <tr>
            <td><img height="15" alt="" width="15" src="/images/space.gif"/></td>
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="0" width="100%" border="0">
<tbody>
<tr>
<td valign="top" width="546">
<table cellspacing="0" cellpadding="4" width="100%" border="0">
    <tbody>
        <tr>
            <td align="left"><img height="7" alt="" width="4" src="/images/arrow-blue-1.gif"/>&nbsp;<A class=left-nav
                                                                                                       HREF=/index.shtml>首页</A>
                <img height="7" alt="" width="4" src="/images/arrow-blue-1.gif"/> <A class=left-nav
                                                                                     HREF=/investor_centre/index.shtml>投资者关系</A>
                <img height="7" alt="" width="4" src="/images/arrow-blue-1.gif"/> <A class=left-nav
                                                                                     HREF=/investor_centre/share_price/index.jsp>实时股价</A>
            </td>
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="0" width="100%" background="/images/nav-tittle-bg.gif" border="0">
    <tbody>
        <tr>
            <td align="left"><img height="23" alt="" width="88" src="/images/nav-tittle-investor.gif"/></td>
            <td align="right"><img height="23" alt="" width="6" src="/images/nav-tittle-corner.gif"/></td>
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="0" border="0">
    <tbody>
        <tr>
            <td><img height="15" alt="" width="15" src="/images/space.gif"/></td>
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="4" width="100%" border="0">
<tbody>
<tr>
    <td align="right" width="1%"><img height="7" alt="" width="4" src="/images/arrow-red-1.gif"/></td>
    <td class="tittle-blue" align="left">历史股价</td>
</tr>
<tr>
<td class="content-text1">&nbsp;</td>
<td class="content-text1" align="left">
<table cellspacing="0" cellpadding="4" width="100%" border="0">
    <tbody>
        <tr>
            <!--td align="right"-->
                <%

                int iBar = ParamUtil.getIntParameter(request, "iBar", 0);
            int totalBarNum = 0;    //totalBarNum 表示总共有多少条PageNo Bar
            int iBarLen = 10;        //iBarLen 表示每条PageNo Bar 包含多少个PageNo
            //最后一条PageNo Bar较特殊，其iBarLen不一定等于10
            int totalPageNum = 0;    //totalPageNum 表示总共有多少页，即PageNo的最大值
            int iEndPageNo = 0;        //iEndPageNo 表示当前PageNo Bar中的结尾PageNo
            int i = 0;
                if(range == 0){
            //iBar 表示当前是第几条PageNo Bar            
            if (count % ranges == 0)
                totalPageNum = count / ranges;
            else
                totalPageNum = count / ranges + 1;

            if (totalPageNum % iBarLen == 0)
                totalBarNum = totalPageNum / iBarLen;
            else
                totalBarNum = totalPageNum / iBarLen + 1;

            //如果iBar * iBarLen >= totalPageNum,则可以证明是最后一条PageNo Bar.
            //否则，不是最后一条PageNo Bar.
            if (iBar * iBarLen >= totalPageNum)
                iEndPageNo = iBar * iBarLen + totalPageNum % iBarLen;
            else
                iEndPageNo = iBar * iBarLen + iBarLen;

            if (iBar >= 1) {


                %>
            <td align="right" width="100">&nbsp;<a class="deep-blue-text"
                                       href=historyQuery.jsp?startrow=<%=iBarLen*ranges*(iBar-1)%>&iBar=<%=iBar - 1%><%=para%>>
                &lt;&lt;前10页</a>&nbsp;</td>
            <%
                }

                out.print("<td align=\"right\" >");
                for (i = iBar * iBarLen; i < iEndPageNo; i++) {
                    if (i < totalPageNum) {
                        out.print("<a class=\"deep-blue-text\" href = historyQuery.jsp?startrow=" + (ranges * i) + "&iBar=" + iBar + para + ">[" + (i + 1) + "]</a>&nbsp;");
                    }
                }
                out.print("</td>");

                //如果iEndPageNo >= totalPageNum,则可以证明是最后一条PageNo Bar.
                //否则，不是最后一条PageNo Bar.
                if (iEndPageNo < totalPageNum) {
            %>
            <td align="left" width="100">&nbsp;<a class="deep-blue-text"
                                      href=historyQuery.jsp?startrow=<%=iBarLen*ranges*(iBar+1)%>&iBar=<%=(iBar * iBarLen >= totalPageNum) ? (iBar) : (iBar + 1)%><%=para%>>后10页&gt;&gt;</a>&nbsp;</td>
            <%
                    }
                }
            %>
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="0" width="100%" border="0" class="content-text1">
    <tbody>
        <tr bgcolor="#999999">
            <td class="link-blue" width="100%" bgcolor="#e3f2fd">
                <%
                    String Gname = "&nbsp;";
                    switch (gid) {
                        case 3:
                            Gname = "香港联交所：(单位：港元/股)";
                            break;
                        case 1:
                            Gname = "纽约股票交易所：(单位：美元/股)";
                            break;
                        case 5:
                            Gname = "上海证交所：(单位：人民币/股)";
                            break;
                        default:
                            Gname = "香港联交所：(单位：港元/股)";
                    }
                    out.print(Gname);
                %>
            </td>
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="0" width="100%" border="0">
    <tbody>
        <tr>
            <td><img height="1" alt="" width="1" src="/images/space.gif"/></td>
        </tr>
    </tbody>
</table>
<table cellspacing="1" cellpadding="4" width="100%" bgcolor="#bad6e2" border="0" class="content-text1">
    <tbody>
        <tr bgcolor="#f0f0f0" width="100%">
            <td width="18%" bgcolor="#e3f2fd" align=center>时间</td>
            <td width="14%" bgcolor="#e3f2fd" align=center>开盘</td>
            <td width="14%" bgcolor="#e3f2fd" align=center>最高</td>
            <td width="14%" bgcolor="#e3f2fd" align=center>最低</td>
            <td width="14%" bgcolor="#e3f2fd" align=center>收盘</td>
            <td width="14%" bgcolor="#e3f2fd" align=center>成交量</td>
        </tr>
        <%
            if (stocks != null) {
                Stock stockprice = new Stock();
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                String backColor = "#FFFFFF";
                for (int j = 0; j < stocks.size(); j++) {
                    if (j % 2 == 0) {
                        backColor = "#e1e1e1";
                    } else {
                        backColor = "#FFFFFF";
                    }
                    stockprice = (Stock) stocks.get(j);
                    String s_date = formatter.format(stockprice.getThedate());
                    if (range == 2) {
                        int posi = -1;
                        posi = s_date.lastIndexOf("-");
                        s_date = s_date.substring(2, posi);
                    }
        %>
        <TR bgcolor=<%=backColor%>>
            <TD align=center><%=(s_date != null) ? s_date : ""%>
            </TD>
            <TD align=center><%=(stockprice.getPrecloseprice() != null) ? stockprice.getPrecloseprice() : ""%>
            </TD>
            <TD align=center><%=(stockprice.getMaxprice() != null) ? stockprice.getMaxprice() : ""%>
            </TD>
            <TD align=center><%=(stockprice.getMinprice() != null) ? stockprice.getMinprice() : ""%>
            </TD>
            <TD align=center><%=(stockprice.getCloseprice() != null) ? stockprice.getCloseprice() : ""%>
            </TD>
            <TD align=middle><%=(stockprice.getExchange() != null) ? stockprice.getExchange() : ""%>
            </TD>
        </TR>
        <%
                }
            }
        %>
    </tbody>
</table>
<table cellspacing="0" cellpadding="4" width="100%" border="0">
    <tbody>
        <tr>
            <!--td align="right"-->
                <%

                if(range == 0){
            //iBar 表示当前是第几条PageNo Bar
            iBar = ParamUtil.getIntParameter(request, "iBar", 0);
            totalBarNum = 0;    //totalBarNum 表示总共有多少条PageNo Bar
            iBarLen = 10;        //iBarLen 表示每条PageNo Bar 包含多少个PageNo
            //最后一条PageNo Bar较特殊，其iBarLen不一定等于10
            totalPageNum = 0;    //totalPageNum 表示总共有多少页，即PageNo的最大值
            iEndPageNo = 0;        //iEndPageNo 表示当前PageNo Bar中的结尾PageNo

            if (count % ranges == 0)
                totalPageNum = count / ranges;
            else
                totalPageNum = count / ranges + 1;

            if (totalPageNum % iBarLen == 0)
                totalBarNum = totalPageNum / iBarLen;
            else
                totalBarNum = totalPageNum / iBarLen + 1;

            //如果iBar * iBarLen >= totalPageNum,则可以证明是最后一条PageNo Bar.
            //否则，不是最后一条PageNo Bar.
            if (iBar * iBarLen >= totalPageNum)
                iEndPageNo = iBar * iBarLen + totalPageNum % iBarLen;
            else
                iEndPageNo = iBar * iBarLen + iBarLen;

            if (iBar >= 1) {


                %>
            <td width="100" align="right">&nbsp;<a class="deep-blue-text"
                                                  href=historyQuery.jsp?startrow=<%=iBarLen*ranges*(iBar-1)%>&iBar=<%=iBar - 1%><%=para%>>
                &lt;&lt;前10页</a>&nbsp;</td>
            <%
                }

                i = 0;
                out.print("<td align=\"right\" >");
                for (i = iBar * iBarLen; i < iEndPageNo; i++) {
                    if (i < totalPageNum) {
                        out.print("<a class=\"deep-blue-text\" href = historyQuery.jsp?startrow=" + (ranges * i) + "&iBar=" + iBar + para + ">[" + (i + 1) + "]</a>&nbsp;");
                    }
                }
                out.print("</td>");

                //如果iEndPageNo >= totalPageNum,则可以证明是最后一条PageNo Bar.
                //否则，不是最后一条PageNo Bar.
                if (iEndPageNo < totalPageNum) {
            %>
            <td width="100" align="left">&nbsp;<a class="deep-blue-text"
                                                 href=historyQuery.jsp?startrow=<%=iBarLen*ranges*(iBar+1)%>&iBar=<%=(iBar * iBarLen >= totalPageNum) ? (iBar) : (iBar + 1)%><%=para%>>后10页&gt;&gt;</a>&nbsp;
            </td>
            <%
                    }
                }
            %>           
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="0" border="0">
    <tbody>
        <tr>
            <td><img height="10" alt="" width="10" src="/images/space.gif"/></td>
        </tr>
    </tbody>
</table>
<table cellspacing="1" cellpadding="7" width="100%" bgcolor="#bad6e2" border="0">
    <tbody>
        <tr>
            <td align="center" bgcolor="#ffffff">
                <form name="stock_form" method=post action="historyQuery.jsp"
                      onsubmit="javascript:return queryFunc(this)">
                    <input type=hidden name=doSearch value="true">
                    <input type="hidden" name="d" value=<%=d%>>
                    <input type="hidden" name="yearstart">
                    <input type="hidden" name="monthstart">
                    <input type="hidden" name="daystart">
                    <input type="hidden" name="yearend">
                    <input type="hidden" name="monthend">
                    <input type="hidden" name="dayend">
                    <table cellspacing="0" cellpadding="4" width="100%" bgcolor="#e3f2fd" border="0"
                           class="content-text1">
                        <tbody>
                            <tr>
                                <td class="link-blue" align="left">历史股价查询</td>
                                <td align="right"><input type="radio" name="range"
                                                         value="0" <%=(range == 0) ? "checked" : ""%>/>
                                    每天
                                    <input type="radio" name="range" value="1" <%=(range == 1) ? "checked" : ""%>/>
                                    每周
                                    <input type="radio" name="range" value="2" <%=(range == 2) ? "checked" : ""%>/>
                                    每月
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table cellspacing="0" cellpadding="0" border="0">
                        <tbody>
                            <tr>
                                <td><img height="10" alt="" width="10" src="/images/space.gif"/></td>
                            </tr>
                        </tbody>
                    </table>
                    <table cellspacing="0" cellpadding="4" width="95%" border="0" class="content-text1">
                        <tbody>
                            <tr>
                                <td align="left">交易所：</td>
                                <td align="left"><select name=gid onchange="javascript:getDates(this.value);">
                                    <option
                                            value=3 <%if(gid==3){%>selected<%}%>>香港交易所
                                    </option>
                                    <option value=1
                                            <%if(gid==1){%>selected<%}%>>纽约交易所
                                    </option>
                                    <option value=5 <%if(gid==5){%>selected<%}%>>上海证交所</option>
                                </select></td>
                                <td align="left">&nbsp;</td>
                                <td align="left">&nbsp;</td>
                            </tr>
                            <tr>
                                <td>起始日期:</td>
                                <td><input maxlength=10 size=12 name=startDate value=<%=startDate%> id=startDate>
                                    <a href="JavaScript: openLookup('calendar.jsp?form=stock_form&ip=startDate&d=<%=d%>')"
                                       onclick="setLastMousePosition(event)" tabindex="3">
                                        <img src="/images/button-calender.gif" width="34" height="21" align="absmiddle"
                                             border=0></a>(yyyy-mm-dd)
                                </td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>终止日期:</td>
                                <td><input maxlength=10 size=12 name=endDate value=<%=endDate%>>
                                    <a href="JavaScript: openLookup('calendar.jsp?form=stock_form&ip=endDate&d=<%=d%>')"
                                       onclick="setLastMousePosition(event)" tabindex="3">
                                        <img src="/images/button-calender.gif" width="34" height="21" align="absmiddle"
                                             border=0></a>(yyyy-mm-dd)
                                </td>
                                <td><input type=submit value=查询 name="query"></td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </td>
        </tr>
    </tbody>
</table>
</td>
</tr>
</tbody>
</table>
</td>
<td width="20"><img height="1" alt="" width="1" src="/images/space.gif"/></td>
<td valign="top">
    <%@ include file="/inc/investorright.shtml" %>
</td>
</tr>
</tbody>
</table>
</td>
</tr>
</tbody>
</table>
<table cellspacing="0" cellpadding="0" border="0">
    <tbody>
        <tr>
            <td><img height="30" alt="" width="30" src="/images/space.gif"/></td>
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="0" width="950" border="0">
    <tbody>
        <tr>
            <td bgcolor="#cccccc"><img height="1" alt="" width="1" src="/images/space.gif"/></td>
        </tr>
    </tbody>
</table>
<table cellspacing="0" cellpadding="0" border="0">
    <tbody>
        <tr>
            <td><img height="5" alt="" width="5" src="/images/space.gif"/></td>
        </tr>
    </tbody>
</table>
<%@ include file="/inc/bottom.shtml" %>
</center>
</body>
</html>

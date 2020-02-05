<%@ page import="com.sinopec.stock.IStockManager" %>
<%@ page import="com.sinopec.stock.StockPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sinopec.stock.Stock" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<link href="/images/sinopec.css" type="text/css" rel="stylesheet" />
<%
    IStockManager stoMgr = StockPeer.getInstance();
    List shanghaiList = stoMgr.getStockNameInfo(1);
    List xianggangList = stoMgr.getStockNameInfo(2);
    List niuyueList = stoMgr.getStockNameInfo(3);
    
    	String d = (ParamUtil.getParameter(request,"d",false) == null)? "":ParamUtil.getParameter(request,"d",false);

  java.util.Date sysDate = new java.util.Date();

  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head xmlns="">
        <title>欢迎访问中国石油化工股份有限公司网站</title>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <script language="JavaScript" src="/inc/public.js"></script>
<script language="JavaScript" src="/inc/functions.js"></script>
        <link href="/images/sinopec.css" type="text/css" rel="stylesheet" /><script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
var currentid=0;
var currentpid=0;
//-->

function queryFunc(frm){

  var stDateStr = frm.startDate.value;
  var edDateStr = frm.endDate.value;

  if(frm.startDate.value==""){
     alert("起始日期不能为空。");
     frm.startDate.focus();
     return false;
  }
  if (frm.gid.value == 1) {
            if(frm.startDate.value < "2000-10-19"){
		 alert("起始日期必须在2000-10-19之后");
		 return false;
	     }
        }
	 if (frm.gid.value == 3) {
            if(frm.startDate.value < "2000-10-19"){
		 alert("起始日期必须在2000-10-19之后");
		 return false;
	     }
        }
	 if (frm.gid.value == 5) {
            if(frm.startDate.value < "2001-08-09"){
		 alert("起始日期必须在2001-08-09之后");
		 return false;
	     }
        }
  if(frm.endDate.value==""){
     alert("终止日期不能为空。");
     frm.endDate.focus();
     return false;
  }
	  frm.yearstart.value = stDateStr.substring(0,stDateStr.indexOf("-"));
	  frm.monthstart.value = stDateStr.substring(stDateStr.indexOf("-")+1,stDateStr.lastIndexOf("-"));
	  frm.daystart.value = stDateStr.substring(stDateStr.lastIndexOf("-")+1,stDateStr.length);
	  
	  frm.yearend.value = edDateStr.substring(0,edDateStr.indexOf("-"));
	  frm.monthend.value = edDateStr.substring(edDateStr.indexOf("-")+1,edDateStr.lastIndexOf("-"));
	  frm.dayend.value = edDateStr.substring(edDateStr.lastIndexOf("-")+1,edDateStr.length);
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

function openline(getPage){
  window.open(getPage,"kline","width=600,height=380,scrollbars=yes,status=yes");
}
</script>
    </head>
    <body xmlns="">
        <center><%@include file="/inc/head.shtml"%><%@include file="/inc/investormenu.shtml"%>
        <table cellspacing="0" cellpadding="0" width="950" border="0">
            <tbody>
                <tr>
                    <td valign="top" width="178"><%@include file="/investor_centre/menuTree.shtml"%></td>
                    <td valign="top" width="1"><img height="419" alt="" width="1" src="/images/line-insite.gif" /></td>
                    <td width="25"><img height="1" alt="" width="1" src="/images/space.gif" /></td>
                    <td valign="top">
                    <table cellspacing="0" cellpadding="0" width="100%" border="0">
                        <tbody>
                            <tr>
                                <td><img height="148" alt="" width="748" src="/images/top-pic-investor.jpg" /></td>
                            </tr>
                        </tbody>
                    </table>
                    <table cellspacing="0" cellpadding="0" width="100%" border="0">
                        <tbody>
                            <tr>
                                <td><img height="2" alt="" width="2" src="/images/space.gif" /></td>
                            </tr>
                            <tr>
                                <td><img height="2" alt="" width="746" src="/images/colo-bar-insite.gif" /></td>
                            </tr>
                        </tbody>
                    </table>
                    <table cellspacing="0" cellpadding="0" border="0">
                        <tbody>
                            <tr>
                                <td><img height="15" alt="" width="15" src="/images/space.gif" /></td>
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
                                            <td align="left"><img height="7" alt="" width="4" src="/images/arrow-blue-1.gif" />&nbsp;<A class=left-nav HREF=/index.shtml>首页</A>  <img height="7" alt="" width="4" src="/images/arrow-blue-1.gif" /> <A class=left-nav HREF=/investor_centre/index.shtml>投资者关系</A>  <img height="7" alt="" width="4" src="/images/arrow-blue-1.gif" />  <A class=left-nav HREF=/investor_centre/share_price/index.jsp>实时股价</A></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <table cellspacing="0" cellpadding="0" width="100%" background="/images/nav-tittle-bg.gif" border="0">
                                    <tbody>
                                        <tr>
                                            <td align="left"><img height="23" alt="" width="86" src="/images/nav-tittle-investor.gif" /></td>
                                            <td align="right"><img height="23" alt="" width="6" src="/images/nav-tittle-corner.gif" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <table cellspacing="0" cellpadding="0" border="0">
                                    <tbody>
                                        <tr>
                                            <td><img height="15" alt="" width="15" src="/images/space.gif" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <table cellspacing="0" cellpadding="4" width="100%" border="0">
                                    <tbody>
                                        <tr>
                                            <td align="right" width="1%"><img height="7" alt="" width="4" src="/images/arrow-red-1.gif" /></td>
                                            <td class="tittle-blue" align="left">实时股价</td>
                                        </tr>
                                        <tr>
                                            <td class="content-text1">&nbsp;</td>
                                            <td class="content-text1" align="left">
                                            <table cellspacing="0" cellpadding="4" width="100%" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td class="mark"><%=formatter.format(sysDate)%></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <table cellspacing="1" cellpadding="7" width="100%" bgcolor="#bad6e2" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td bgcolor="#ffffff">
                                                        <table cellspacing="0" cellpadding="4" width="100%" border="0" class="content-text1">
                                                            <tbody>
                                                                <tr>
                                                                    <td bgcolor="#e3f2fd">&nbsp;</td>
                                                                    <td bgcolor="#e3f2fd">股票代码</td>
                                                                    <td bgcolor="#e3f2fd">当前价</td>
                                                                    <td bgcolor="#e3f2fd">开盘价</td>
                                                                    <td bgcolor="#e3f2fd">前收盘</td>
                                                                    <td bgcolor="#e3f2fd">涨跌</td>
                                                                    <td align="center" bgcolor="#e3f2fd">K线图</td>
                                                                </tr>
           <%
                if((shanghaiList != null)&&(shanghaiList.size() > 0)){
                for (int i = 0; i < 1; i++) {
                    Stock sto = (Stock) shanghaiList.get(i);
            %>
                                                                <tr>
                                                                    <td>上海</td>
                                                                    <td>600028</td>
                                                                    <td><%=sto.getLast_trade() == null ? "" : sto.getLast_trade()%></td>
                                                                    <td><%=sto.getOpenprice() == null ? "" : sto.getOpenprice()%></td>
                                                                    <td><%=sto.getCloseprice() == null ? "" : sto.getCloseprice()%></td>
                                                                    <td><%=sto.getChange() == null ? "" : sto.getChange()%></td>
                                                                    <td align="center"><a href="#" onclick=javascript:openline("shanghai.shtml");><img height="11" alt="" width="14" src="/images/icon-quxian.gif" border=0/></a></td>
                                                                </tr>
                                                                <%}}%>                                                                
<%
            if((xianggangList != null)&&(xianggangList.size() > 0)){
                for (int i = 0; i < 1; i++) {
                    Stock sto = (Stock) xianggangList.get(i);
            %>
                                                                <tr>
                                                                    <td>香港</td>
                                                                    <td>0386.HK</td>
                                                                    <td><%=sto.getLast_trade() == null ? "" : sto.getLast_trade()%></td>
                                                                    <td><%=sto.getOpenprice() == null ? "" : sto.getOpenprice()%></td>
                                                                    <td><%=sto.getCloseprice() == null ? "" : sto.getCloseprice()%></td>
                                                                    <td><%=sto.getChange() == null ? "" : sto.getChange()%></td>
                                                                    <td align="center"><a href="#" onclick=javascript:openline("hongkong.shtml");><img height="11" alt="" width="14" src="/images/icon-quxian.gif" border=0/></a></td>
                                                                </tr>
                                                                <%}}%>
<%
            if((niuyueList != null)&&(niuyueList.size() > 0)){
                for (int i = 0; i < 1; i++) {
                    Stock sto = (Stock) niuyueList.get(i);
            %>                                                                
                                                                <tr>
                                                                    <td>纽约</td>
                                                                    <td>SNP.N</td>
                                                                    <td><%=sto.getLast_trade() == null ? "" : sto.getLast_trade()%></td>
                                                                    <td><%=sto.getOpenprice() == null ? "" : sto.getOpenprice()%></td>
                                                                    <td><%=sto.getCloseprice() == null ? "" : sto.getCloseprice()%></td>
                                                                    <td><%=sto.getChange() == null ? "" : sto.getChange()%></td>
                                                                    <td align="center"><a href="#" onclick=javascript:openline("newyork.shtml");><img height="11" alt="" width="14" src="/images/icon-quxian.gif" border=0/></a></td>
                                                                </tr>
                                                                <%}}%>
<%
            if((niuyueList != null)&&(niuyueList.size() > 0)){
                for (int i = 0; i < 1; i++) {
                    Stock sto = (Stock) niuyueList.get(i);
            %>                                                                
                                                                <tr>
                                                                    <td>伦敦</td>
                                                                    <td>SNP.N</td>
                                                                    <td><%=sto.getLast_trade() == null ? "" : sto.getLast_trade()%></td>
                                                                    <td><%=sto.getOpenprice() == null ? "" : sto.getOpenprice()%></td>
                                                                    <td><%=sto.getCloseprice() == null ? "" : sto.getCloseprice()%></td>
                                                                    <td><%=sto.getChange() == null ? "" : sto.getChange()%></td>
                                                                    <td align="center"><a href="#" onclick=javascript:openline("london.shtml");><img height="11" alt="" width="14" src="/images/icon-quxian.gif" border=0/></a></td>
                                                                </tr>
                                                                <%}}%>                                                                
                                                            </tbody>
                                                        </table>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <table cellspacing="0" cellpadding="0" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td><img height="15" alt="" width="15" src="/images/space.gif" /></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <table cellspacing="0" cellpadding="4" width="100%" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td class="link-blue">历史股价查询</td>
                                                    </tr>
                                                </tbody>
                                            </table>
<table width="100%" border="0" cellpadding="7" cellspacing="1" bgcolor="#BAD6E2" class="content-text1">
<form name="stock_form" method=post TARGET="_blank" action="historyQuery.jsp" onsubmit="javascript:return queryFunc(this)">
		<input type=hidden name=doSearch value="true">
        <input type="hidden" name="d" value=>
        <input type="hidden" name="yearstart">
        <input type="hidden" name="monthstart">
        <input type="hidden" name="daystart">
        <input type="hidden" name="yearend">
        <input type="hidden" name="monthend">
        <input type="hidden" name="dayend">
  <tr>
	<td align="center" bgcolor="#FFFFFF"><table width="100%" border="0" cellpadding="4" cellspacing="0" bgcolor="#E3F2FD" class="content-text1">
	  <tr>
		<td align="left" class="link-blue">历史股价查询</td>
		<td align="right"><input type="radio" name="range" value="0" checked />
		  每天 
			<input type="radio" name="range" value="1" />
			每周
			<input type="radio" name="range" value="2" />
			每月</td>
	  </tr>
	</table>
	  <table border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td><img src="/images/space.gif" width="10" height="10" /></td>
		</tr>
	  </table>
	  <table width="95%" border="0" cellpadding="4" cellspacing="0" class="content-text1">
		<tr>
		  <td align="left">交易所：</td>
		  <td align="left"><select name=gid onchange="javascript:getDates(this.value);">
		<option value=3>香港交易所</option>
		<option value=1>纽约交易所</option>
		<option value=5 selected>上海证交所</option>
	    </select>
		  </td>
		  <td align="left">&nbsp;</td>
		  <td align="left">&nbsp;</td>
		</tr>
		<tr>
		  <td align="left">起始日期: </td>
		  <td><input maxlength=10 size=12 name=startDate value="2001-08-09" id=startDate>
              <a href="JavaScript: openLookup('calendar.jsp?form=stock_form&ip=startDate&d=')" onclick="setLastMousePosition(event)" tabindex="3">
	    				<img src="/images/button-calender.gif" width="34" height="21" align="absmiddle" border=0></a>(yyyy-mm-dd)</td>
		  <td align="left">&nbsp;</td>
		</tr>
		<tr>
		  <td align="left">终止日期:</td>
		  <td><input maxlength=10 size=12 name=endDate value="<%=formatter.format(sysDate)%>">
              <a href="JavaScript: openLookup('calendar.jsp?form=stock_form&ip=endDate&d=')" onclick="setLastMousePosition(event)" tabindex="3">
	    				<img src="/images/button-calender.gif" width="34" height="21" align="absmiddle" border=0></a>(yyyy-mm-dd)</td>
                                                        <td><input type=submit value=查询 name="query"></td>
		</tr>
	  </table></td>
  </tr>
  </form>
</table>                                          
</td>
                                        </tr>
                                    </tbody>
                                </table>
                                </td>
                                <td width="20"><img height="1" alt="" width="1" src="/images/space.gif" /></td>
                                <td valign="top"><%@include file="/inc/investorright.shtml"%></td>
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
                    <td><img height="30" alt="" width="30" src="/images/space.gif" /></td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" width="950" border="0">
            <tbody>
                <tr>
                    <td bgcolor="#cccccc"><img height="1" alt="" width="1" src="/images/space.gif" /></td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0">
            <tbody>
                <tr>
                    <td><img height="5" alt="" width="5" src="/images/space.gif" /></td>
                </tr>
            </tbody>
        </table>
        <%@include file="/inc/bottom.shtml"%></center>
    </body>
</html>

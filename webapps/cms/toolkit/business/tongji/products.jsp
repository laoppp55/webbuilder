<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Other.*,
                 com.bizwink.cms.business.Product.*"
                 contentType="text/html;charset=gbk"%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int siteid = authToken.getSiteID();
  String indate = ParamUtil.getParameter(request,"indate");
  String outdate = ParamUtil.getParameter(request,"outdate");
  int kind    = ParamUtil.getIntParameter(request,"kind",1);
  Timestamp today = new Timestamp(System.currentTimeMillis());
  int byear    = today.getYear()-1;
  int bmonth   = today.getMonth();
  int bday     = Integer.parseInt(String.valueOf(today).substring(8,10))-1;
  int eyear    = today.getYear();
  int emonth   = today.getMonth();
  int eday     = Integer.parseInt(String.valueOf(today).substring(8,10))-1;
  IProductManager productMgr = productPeer.getInstance();
  Product product = new Product();
  List productlist = new ArrayList();
  productlist=productMgr.getProductList(siteid);

  if(indate!= null){
    byear = Integer.parseInt(indate.substring(0,4))-1900;
    bmonth = Integer.parseInt(indate.substring(5,7))-1;
    bday = Integer.parseInt(indate.substring(8,10))-1;
  }
  if(outdate!= null){
    eyear = Integer.parseInt(outdate.substring(0,4))-1900;
    emonth = Integer.parseInt(outdate.substring(5,7))-1;
    eday = Integer.parseInt(outdate.substring(8,10))-1;
  }
  if(kind==2){
   bday=0;
   eday=0;
  }

//kind = 1 为以周为单位,2为以月为单位
  IOtherManager otherMgr = otherPeer.getInstance();

    Timestamp begintime = Timestamp.valueOf("1900-01-01 00:00:00");
    Timestamp endtime = Timestamp.valueOf("1900-01-01 00:00:00");
    begintime.setYear(byear);
    begintime.setMonth(bmonth);
    endtime.setYear(eyear);
    endtime.setMonth(emonth);
  long oneday =24*3600*1000;
  long begint = begintime.getTime()+oneday*bday;
  long endt = endtime.getTime()+oneday*eday;

  if(kind ==1)
  {
    while(new Timestamp(begint).getDay() != 0)
      begint = begint - oneday;
    while(new Timestamp(endt).getDay() != 0)
      endt = endt + oneday;
  }
  begintime = new Timestamp(begint);
  endtime = new Timestamp(endt);

  Tongji tongji = new Tongji();

  List orderlist = otherMgr.getOrderNums(begint,endt,kind,siteid);
  int points = 60;
  int onelength = 73;
  if(kind==1)
    if(orderlist.size()>=60) points = orderlist.size()+2;
  if(kind==2)
    if(orderlist.size()*4>=60) points = orderlist.size()*4+4;
  int width = onelength*points+500;
  String rpathstr = "";
  String opathstr = "";
  float rlossheight=0;
  float olossheight=0;
  float bigmoney = 0;
  float omoney = 0;
  float rmoney = 0;
  int onumber = 0;
  int onepiece = 0;
  int onemark = 0;
  for(int i=0;i<orderlist.size();i++)
  {
    tongji = (Tongji)orderlist.get(i);
    omoney += tongji.getTotalMoney();
    rmoney += tongji.getReceiveMoney();
    onumber += tongji.getOrderNum();
    if(bigmoney<tongji.getTotalMoney()) bigmoney = tongji.getTotalMoney();
    if(bigmoney<tongji.getReceiveMoney()) bigmoney = tongji.getReceiveMoney();
  }
  while(bigmoney>=onepiece*35)
  {
    onepiece++;
  }
  onemark = onepiece * 5;
  for(int i=0;i<orderlist.size();i++)
  {
    tongji = (Tongji)orderlist.get(i);
    olossheight = tongji.getTotalMoney()/onepiece;
    rlossheight = tongji.getReceiveMoney()/onepiece;
    if(kind==1)
    {
      rpathstr = rpathstr + " " + String.valueOf(273+i*onelength) + "," + String.valueOf(2800-onelength*rlossheight);
      opathstr = opathstr + " " + String.valueOf(273+i*onelength) + "," + String.valueOf(2800-onelength*olossheight);
    }
  }

  String detailstr="";
  String rshowtotal="";
  String oshowtotal="";
  String rcolor = "";
  String ocolor = "";
  if(kind==1){ rcolor="red";      ocolor="green"; }
  if(kind==2){ rcolor="#362a81";  ocolor = "#5c8226"; }
  detailstr = "从<input id=indate type=text value='"+String.valueOf(new Timestamp(begint)).substring(0,10)+
              "' readonly class='indate' onclick='setday(this)'>";
  if(kind==1) detailstr = detailstr + "(第0周)起始&nbsp;";
  if(kind==2) detailstr = detailstr + "(第1月)起始&nbsp;";
  detailstr = detailstr + "至<input id=outdate type=text value='"+String.valueOf(new Timestamp(endt)).substring(0,10)+
              "' readonly class='indate' onclick='setday(this)'>&nbsp;";
  if(kind==1)  detailstr = detailstr + "每周 <font color="+rcolor+">收款</font>/<font color="+ocolor+
                           ">订单总额</font> 数目 "+
                           "<font color=red><b><a href='###' onclick='submit(1)' target=_self><u>统计</u></a></b></font> 表";
  if(kind==2)  detailstr = detailstr + "每月 <font color=white style=\"background:"+rcolor+
                           "\">收款</font>/<font color=white style=\"background:"+ocolor+"\">订单总额</font> 数目 "+
                           "<font color=red><b><a href='###' onclick='submit(2)' target=_self><u>统计</u></a></b></font> 表";
  rshowtotal = "<font color=#be3a4c>"+String.valueOf(new Timestamp(begint)).substring(0,10) + "</font>"+
               " 至 "+"<font color=#be3a4c>"+String.valueOf(new Timestamp(endt)).substring(0,10)+"</font>"+
               " 共收到 "+"<font color=#384efe>"+String.valueOf(rmoney)+"</font>"+" 货款<br>";
  oshowtotal = "<font color=#be3a4c>"+String.valueOf(new Timestamp(begint)).substring(0,10) + "</font>"+
               " 至 "+"<font color=#be3a4c>"+String.valueOf(new Timestamp(endt)).substring(0,10)+"</font>"+
               " 共有订单总额 <font color=#384efe>"+String.valueOf(omoney)+"</font>。"+
               " 共有 <font color=#384efe>"+String.valueOf(onumber)+"</font> 条用户订单";

  String drawcolor="#C50023#D0770B#F5B16D#C7C300#F9F400#367517#AFD788#008489"+
                   "#00B2BF#184785#426EB4#79378B#AF4A92#FF00FF#EE6A50#CDB5CD"+
                   "#C67171#A020F0#6E8B3D#00CED1#8B5A2B#B03060";
  drawcolor=drawcolor.toLowerCase();
%>
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
        <title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<script language="JavaScript" src="../include/setday.js" ></script>
<meta http-equiv="Pragma" content="no-cache">
<style>
 <!--
　a {text-decoration:none}
  a:hover {color: red;text-decoration:none}
  a:link{color:red}
 -->
 .indate{cursor:hand;font-weight:bold;color:#EE6A50;height:16;width:<%if(kind==1){%>83<%}else{%>57<%}%>;border-top:0px solid #537bf5;
         border-left:0px solid #537bf5;border-right:0px solid #537bf5;border-bottom:1.5px solid #A2B5CD;}
 .selpro{width:120;margin:-2px;}
 .tdborder{border-style:double;border-width:2px;border-color:black;}
 .divbtn{cursor:hand;margin:-1px -2px;padding:2px;height:14;font-size:9pt;overflow:hidden;}
</style>
</head>
<STYLE>
 v\:* { BEHAVIOR: url(#default#VML) }
</STYLE>

<script language=javascript>
function drawLines()
{
 var count=0;//画横坐标
 for(var i=0;i<=<%=points%>;i++){
    var px=200+73*i;
    var newLine = document.createElement("<v:line from='"+px+" 200' to='"+px+" 2800' style='position:absolute;z-index:7'></v:line>");
<%if(kind==2){%>
   if(count%4==0)
<%}%>
    group1.insertBefore(newLine);
    if(count%4!=0){
<%if(kind==1){%>
            var newStroke = document.createElement("<v:stroke dashstyle='dot' color='black'/>");
            newLine.insertBefore(newStroke);
<%}%>
        }
        else
        {
            var newStroke = document.createElement("<v:stroke color='#babbae'>");
            newLine.insertBefore(newStroke);
        }
        count++;
 }
 count=0; //画纵坐标
 for(var i=0;i<=35;i++){
    var py=2800-73*i;
    var newLine = document.createElement("<v:line from='200 "+py+"' to='<%=width-300%> "+py+"' style='position:absolute;z-index:7'></v:line>");
    group1.insertBefore(newLine);
        if(count%5!=0){
            var newStroke = document.createElement("<v:stroke dashstyle='dot' color='black'/>");
            newLine.insertBefore(newStroke);
        }
        else
        {
            var newStroke = document.createElement("<v:stroke color='#babbae' />");
            newLine.insertBefore(newStroke);
        }
        count++;
 }
 showdetail(1);
}

function addpoint(x,y,title,title2)
{
  var newElment = document.createElement("<v:oval strokecolor=blue title='"+ title +"' title2='"+title2+"' onmouseout=showdetail(1) onmouseover=showdetail(this.title2) style='cursor:hand;position:absolute;left:"+x+";top:"+y+";width:20;height:20;z-index:10'/>");
  group1.insertBefore(newElment);
}

function addrect(color,left,top,height,title,title2)
{
  var newElment = document.createElement('<v:rect title="'+ title +'" title2="'+title2+'" onmouseout=showdetail(1) onmouseover=showdetail(this.title2)  style="position:relative;top:'+top+';left:'+left+';width:73;height:'+height+';z-index:8;" fillcolor="'+color+'" strokeColor="black"/>');
  group1.insertBefore(newElment);
}

function showdetail(v)
{
  if(v==1)
  {
    detail.innerHTML = '<%=rshowtotal+oshowtotal%>';
    return;
  }
  detail.innerHTML = v;
}

var oldtext = '';
var closetime;

function showhelp(baridx){
  var thetext = '';
  if(baridx==0)
    thetext = thetext + '<a href="index.jsp?kind=1">按周分析</a><br>'+
              '<a href="index.jsp?kind=2">按月分析</a>';
  if(baridx==1)
    thetext = thetext + '<a href="orders.jsp?kind=1">按周分析</a><br>'+
                        '<a href="orders.jsp?kind=2">按月分析</a>';
  if(baridx==2)
    thetext = thetext + '<a href="product.jsp?kind=1">按周分析</a><br>'+
                        '<a href="product.jsp?kind=2">按月分析</a>';
try{
  if(oldtext!=thetext)
  {
    clearTimeout(closetime);
    vSrc = window.event.srcElement;
    h = vSrc.offsetHeight;
    l = vSrc.offsetLeft;
    t = vSrc.offsetTop + h;
    vParent = vSrc.offsetParent;
    while (vParent.tagName.toUpperCase() != "BODY")
    {
      l += vParent.offsetLeft;
      t += vParent.offsetTop;
      vParent = vParent.offsetParent;
    }

      showdiv.style.left=l;
      showdiv.style.top=t+5;

    oldtext=thetext;
    showdiv.innerHTML=thetext;
    showdiv.style.backgroundColor= '#d9eda1';
 /*   if(event.x>document.body.offsetWidth/2)
    {
      showdiv.style.left=event.x-(oldtext.length*12);showdiv.style.top=event.y+document.body.scrollTop+28;
    }else
    {
      showdiv.style.left=event.x-10;
      showdiv.style.top=event.y+document.body.scrollTop+20;
    }
*/
    showdiv.style.display='';
    closetime=setTimeout('hiddenDiv()',4000);
  }
 }catch(e){}
}

function hiddenDiv()
{
  showdiv.style.display="none";
  oldtext = '';
}

function submit(v)
{
  window.location='orders.jsp?kind='+v+'&indate='+indate.value+'&outdate='+outdate.value;
}

var colors= '<%=drawcolor%>'

function chgcolor(obj,objname)
{
  var obj2 = eval(objname);
  var objstr='';
  if(obj.id==1){
    colors = colors+obj2.style.background;
    objstr = 'td'+obj2.style.background;
    objstr = objstr.substr(0,2)+objstr.substr(3,6);
    eval(objstr).style.background='transparent';
    obj.style.background='transparent';
    obj2.style.background='transparent';
    obj.id=0;
  }else{
    if(colors.length==0){
      alert('选择过多')
      return false;
    }
    obj.style.background='#CAE5E8';
    obj2.style.background=colors.substr(0,7);
    eval('td'+colors.substr(1,6)).style.background=colors.substr(0,7);
    colors=colors.substring(7,colors.length);
    obj.id=1;
  }
  return true;
}

</script>
<body onload="drawLines()">
<%
      String[][] titlebars = {
              { "首页", "" },
              { "统计管理", "" }
          };

      String[][] operations = {
              { "注册量统计", "index.jsp" },
              { "订单统计", "orders.jsp" },
              { "销售统计", "products.jsp" }
      };
%>
<%@ include file="../inc/titlebar2.jsp" %>
<table align="center">

<tr>
<td >
<center>
<font style="font-size=9pt">
<%=detailstr%>
</font>
<br><br>
<table>
<tr>
<td style="width:150" align="center">
<table width=140>
<tr>
<td class="tdborder" height=390 >
<div style="height:390;overflow-x:hidden;overflow-y:scroll">
  <table>
  <%
    for(int i=0;i<productlist.size();i++)
    {
      product = (Product)productlist.get(i);
  %>
  <tr>
  <td>
  <div id="divname<%=product.getArticleid()%>" onclick="javascript:return chgcolor(this,'divnum<%=product.getArticleid()%>')" class="divbtn" align="center" title='<%=StringUtil.gb2iso4View(product.getShortName())%>'>
   <%=StringUtil.gb2iso4View(product.getShortName())%>
  </div>
  </td>
  <td>
  <div id="divnum<%=product.getArticleid()%>"  class="divbtn" align="center" title='已购买数:<%=product.getStockNum()%>'>
   <%=product.getStockNum()%>
  </div>
  </td>
  </tr>
  <%}%>
  </table>
</div>
</td>
</tr>
</table>
</td>
<td>
<v:group ID="group1" style="WIDTH:500pt;HEIGHT:300pt;" coordsize="5000,3000">
<v:line from="200,100" to="200,2800" style="Z-INDEX:8;POSITION:absolute" strokeweight="1pt">
<v:stroke StartArrow="classic"/>
</v:line>
<v:line from="200,2800" to="<%=width-100%>,2800" style="Z-INDEX:8;POSITION:absolute" strokeweight="1pt">
<v:stroke EndArrow="classic"/>
</v:line>
<v:rect style="WIDTH:<%=width%>px;HEIGHT:3000px" coordsize="21600,21600" fillcolor="white" strokecolor="black">
<v:shadow on="t" type="single" color="silver" offset="4pt,3pt"></v:shadow>
<div id="detail" style="POSITION:relative;padding-top:10px;padding-left:55px;padding-right:5px;padding-bottom:5px;font-size:9pt;z-index:10;"></div>
</v:rect>
<%if(kind==1){%>
<v:polyLine id="poly1" style="z-index:9" filled=f strokecolor=red strokeweight=1pt points="<%=rpathstr%>"></v:polyLine>
<v:polyLine id="poly1" style="z-index:9" filled=f strokecolor=green strokeweight=1pt points="<%=opathstr%>"></v:polyLine>
<%}%>
<%for(int i=1;i<=7;i++){%>
<P id="p2" class="txt" style="LEFT:5px;WIDTH:11.9pt;POSITION:absolute;TOP:<%=370-49*i%>px;HEIGHT:5.6pt;TEXT-ALIGN:center"><%=i*onemark%></P>
<%}%>

<P class="txt" style="LEFT:20px;POSITION:absolute;TOP:380px;HEIGHT:5.6pt;TEXT-ALIGN:center">0</P>
<%for(int i=1;i<=points/4;i++){%>
<P class="txt" style="LEFT:<%=20+39*i%>px;POSITION:absolute;TOP:380px;HEIGHT:5.6pt;TEXT-ALIGN:center"><font style="font-size:9pt"><%if(kind==1){%><%=i*4%>周<%}else if(kind==2){%><%=i%>月<%}%></font></P>
<%}%>

</v:group>
</td>
</tr>
<tr>
<td colspan=2 align=center>
<table border=1>
<tr>
<%for(int i=0;i*7<drawcolor.length();i++){%>
  <td  id="td<%=drawcolor.substring(i*7+1,i*7+7)%>" >&nbsp;</td>
<%}%>
</tr>
</table>
</td>
</tr>
</table>
</center>
</td>
</tr>

</table>
<DIV  onclick='hiddenDiv()' id=showdiv
 style="TABLE-LAYOUT: fixed; PADDING-RIGHT: 2px; DISPLAY: none; PADDING-LEFT: 2px;
 FONT-SIZE: 12px; Z-INDEX: 500; FILTER: alpha(style=1,finishopacity=80); LEFT: 0px;
 PADDING-BOTTOM: 2px; COLOR: navy; PADDING-TOP: 2px; WHITE-SPACE: nowrap;
 POSITION: absolute; TOP: 0px; BACKGROUND-COLOR: #fff7ff">
</DIV>
</body>
<script language=javascript>
<%
  String rtitle = "";
  String rtitle2 = "";
  String otitle = "";
  String otitle2 = "";
  for(int i=0;i<orderlist.size();i++)
  {
    tongji = (Tongji)orderlist.get(i);
    rlossheight = tongji.getReceiveMoney();
    olossheight = tongji.getTotalMoney();
    rtitle = String.valueOf(tongji.getBegintime()).substring(0,10);
    rtitle2 = "<font color=#be3a4c>"+rtitle+"</font>";
    rtitle = rtitle + " 至 " + ""+String.valueOf(tongji.getEndtime()).substring(0,10)+"" ;
    rtitle = rtitle + " 共收到货款: " + String.valueOf(tongji.getReceiveMoney())+"";
    rtitle2 = rtitle2 +" 至 " + "<font color=#be3a4c>"+String.valueOf(tongji.getEndtime()).substring(0,10)+"</font>" +
              " 共收到货款: <font color=#384efe>" + String.valueOf(tongji.getReceiveMoney())+"</font>";
    otitle = String.valueOf(tongji.getBegintime()).substring(0,10);
    otitle2 = "<font color=#be3a4c>"+otitle+"</font>";
    otitle = otitle + " 至 " + ""+String.valueOf(tongji.getEndtime()).substring(0,10)+"" ;
    otitle = otitle + " 订单总额: " + String.valueOf(tongji.getTotalMoney())+" 共有订单: " + String.valueOf(tongji.getOrderNum());
    otitle2 = otitle2 +" 至 " + "<font color=#be3a4c>"+String.valueOf(tongji.getEndtime()).substring(0,10)+"</font>" +
              " 订单总额: <font color=#384efe>" + String.valueOf(tongji.getTotalMoney())+"</font>"+
              " 共有订单: <font color=#384efe>" + String.valueOf(tongji.getOrderNum())+"</font>";
    if(kind==1){
%>
addpoint('<%=273+i*onelength-10%>','<%=2800-onelength*(rlossheight/onepiece)-10%>','<%=rtitle%>','<%=rtitle2%>');
addpoint('<%=273+i*onelength-10%>','<%=2800-onelength*(olossheight/onepiece)-10%>','<%=otitle%>','<%=otitle2%>');
<%}else if(kind==2){%>
addrect('#362a81','<%=492+292*i+73%>','<%=2800-onelength*(rlossheight/onepiece)%>','<%=onelength*(rlossheight/onepiece)%>','<%=rtitle%>','<%=rtitle2%>');
addrect('#5c8226','<%=492+292*i%>','<%=2800-onelength*(olossheight/onepiece)%>','<%=onelength*(olossheight/onepiece)%>','<%=otitle%>','<%=otitle2%>');
<%}}%>
</script>
</html>

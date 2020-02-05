<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.bookincome.*,
                com.booyee.search.*"
                contentType="text/html;charset=gbk"
%>

<%
  boolean success = false;
  int startflag = ParamUtil.getIntParameter(request, "startflag", 0);

  int sid                       = ParamUtil.getIntParameter(request, "sid", 0);
  int number                    = ParamUtil.getIntParameter(request, "number", 0);
  //bookname                      = ParamUtil.getParameter(request, "bookname");
  //in_price                      = ParamUtil.getFloatParameter(request, "inprice", 0);

  IBookincomeManager bookincomeMgr = BookincomePeer.getInstance();
  Bookincome bookincome = new Bookincome();
  String bookname = "";
  if(startflag == 1)
  {
    int book_type_id              = ParamUtil.getIntParameter(request, "book_type_id", 0);
    bookname               = ParamUtil.getParameter(request, "bookname");
    float in_price                = ParamUtil.getFloatParameter(request, "in_price", 0);
    String isbn                   = ParamUtil.getParameter(request, "isbn");
    String author                 = ParamUtil.getParameter(request, "author");
    String publisher              = ParamUtil.getParameter(request, "publisher");
    int publishtime_year          = ParamUtil.getIntParameter(request, "publishtime_year" ,0);
    int publishtime_month         = ParamUtil.getIntParameter(request, "publishtime_month" ,0);
    int publishtime_day           = ParamUtil.getIntParameter(request, "publishtime_day" ,0);
    //int book_banci                = ParamUtil.getIntParameter(request, "book_banci", 0);
    int printingtime_year         = ParamUtil.getIntParameter(request, "printingtime_year" ,0);
    int printingtime_month        = ParamUtil.getIntParameter(request, "printingtime_month" ,0);
    int printingtime_day          = ParamUtil.getIntParameter(request, "printingtime_day" ,0);
    //int printing_yinci            = ParamUtil.getIntParameter(request, "printing_yinci", 0);
    String banciyinci             = ParamUtil.getParameter(request, "banciyinci");
    int printing_number           = ParamUtil.getIntParameter(request, "printing_number", 0);
    float book_weight             = ParamUtil.getFloatParameter(request, "book_weight", 0);
    String kaiben                 = ParamUtil.getParameter(request, "kaiben");
    //int ceshu                     = ParamUtil.getIntParameter(request, "ceshu", 0);
    int yema                      = ParamUtil.getIntParameter(request, "yema", 0);
    int yinzhang                  = ParamUtil.getIntParameter(request, "yinzhang", 0);
    int wordnum                   = ParamUtil.getIntParameter(request, "wordnum", 0);
    int havepic                   = ParamUtil.getIntParameter(request, "havepic", 0);
    String zhuangzhen             = ParamUtil.getParameter(request, "zhuangzhen");
    String c_bookname             = ParamUtil.getParameter(request, "c_bookname");
    int pinxiang                  = ParamUtil.getIntParameter(request, "pinxiang", 0);
    float last_price              = ParamUtil.getFloatParameter(request, "last_price", 0);
    float sale_price              = ParamUtil.getFloatParameter(request, "sale_price", 0);
    float disc_price              = ParamUtil.getFloatParameter(request, "disc_price", 0);
    String describe               = ParamUtil.getParameter(request, "describe");
    String picture1               = ParamUtil.getParameter(request, "picture1");
    String picture2               = ParamUtil.getParameter(request, "picture2");
    String picture3               = ParamUtil.getParameter(request, "picture3");
    String picture4               = ParamUtil.getParameter(request, "picture4");
    int recommend_flag            = ParamUtil.getIntParameter(request, "recommend_flag", 0);
    int recommend                 = ParamUtil.getIntParameter(request, "recommend", 0);
    int stocknum                  = ParamUtil.getIntParameter(request, "stocknum", 0);
    int salesnum                  = ParamUtil.getIntParameter(request, "salesnum", 0);
    int showflag                  = ParamUtil.getIntParameter(request, "showflag", 0);
    int newflag                   = ParamUtil.getIntParameter(request, "newflag", 0);
    int volnum                    = ParamUtil.getIntParameter(request, "volnum", 0);
    String caizhi                 = ParamUtil.getParameter(request, "caizhi");
    float bk_height               = ParamUtil.getFloatParameter(request, "bk_height", 0);
    float bk_width                = ParamUtil.getFloatParameter(request, "bk_width", 0);
    float layout_height           = ParamUtil.getFloatParameter(request, "layout_height", 0);
    float layout_width            = ParamUtil.getFloatParameter(request, "layout_width", 0);
    String hangge                 = ParamUtil.getParameter(request, "hangge");
    int yuwei                     = ParamUtil.getIntParameter(request, "yuwei", 0);
    String banxin                 = ParamUtil.getParameter(request, "banxin");
    String createdate             = ParamUtil.getParameter(request, "createdate");
    String author_introduce       = ParamUtil.getParameter(request, "author_introduce");
    String directory              = ParamUtil.getParameter(request, "directory");

    java.sql.Date publishtime = null;
    java.sql.Date printingtime = null;
    if(publishtime_day != 0){
      publishtime = new java.sql.Date(publishtime_year-1900 , publishtime_month-1 , publishtime_day);
    }else{
      publishtime = null;
    }
    if(printingtime_day != 0){
      printingtime = new java.sql.Date(printingtime_year-1900 , printingtime_month-1 , printingtime_day);
    }else{
      printingtime = null;
    }

    bookincome.setSid(sid);
    bookincome.setBook_Type_ID(book_type_id);
    bookincome.setISBN(isbn);
    bookincome.setBookName(bookname);
    bookincome.setAuthor(author);
    bookincome.setPublisher(publisher);
    bookincome.setPublishtime(publishtime);
    bookincome.setBanCiYinCi(banciyinci);
    bookincome.setPrinting_Time(printingtime);
    //bookincome.setPrinting_YinCi(printing_yinci);
    bookincome.setPrinting_Number(printing_number);
    bookincome.setBook_Weight(book_weight);
    bookincome.setKaiBen(kaiben);
    bookincome.setCeShu(number);
    bookincome.setYeMa(yema);
    bookincome.setYinZhang(yinzhang);
    bookincome.setWordNum(wordnum);
    bookincome.setHavePic(havepic);
    bookincome.setZhuangZhen(zhuangzhen);
    bookincome.setC_BookName(bookname);
    bookincome.setPinXiang(pinxiang);
    bookincome.setIn_Price(in_price);
    bookincome.setLast_Price(last_price);
    bookincome.setSale_Price(sale_price);
    bookincome.setDisc_Price(disc_price);
    bookincome.setDescribe(describe);
    bookincome.setPicture1(picture1);
    bookincome.setPicture2(picture2);
    bookincome.setPicture3(picture3);
    bookincome.setPicture4(picture4);
    bookincome.setRecommend(recommend);
    bookincome.setRecommend_Flag(recommend_flag);
    bookincome.setStockNum(stocknum);
    bookincome.setSalesNum(salesnum);
    bookincome.setShowFlag(showflag);
    bookincome.setNewFlag(newflag);
    bookincome.setVolnum(volnum);
    bookincome.setCaiZhi(caizhi);
    bookincome.setBK_Height(bk_height);
    bookincome.setBK_Width(bk_width);
    bookincome.setLayout_Height(layout_height);
    bookincome.setLayout_Width(layout_width);
    bookincome.setHangGe(hangge);
    bookincome.setYuWei(yuwei);
    bookincome.setBanXin(banxin);
    bookincome.setAuthor_Introduce(author_introduce);
    bookincome.setDirectory(directory);

    bookincomeMgr.Bookinfo(bookincome);
    success = true;
  }

  if (startflag == 1 && success)
  {
    long bookid = 0;
    bookid = bookincomeMgr.getBookID(sid,bookname);
    response.sendRedirect("../upload/uploadfiles.jsp?bookid="+bookid);
  }
%>

<html>
<head>
<title>布衣书局 图书入库</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../../images/pt9.css">
<link rel=stylesheet type=text/css href=../style/global.css>
<script language="javascript">
function check(frm)
{
  if(bookincome.book_type_id.value == 0){
    alert("请选择图书类型！");
    return false;
  }

  /*if((bookincome.isbn.value == "")||(bookincome.isbn.value == null)){
    alert("请输入ISBN号！");
    return false;
  }*/

  /*if((bookincome.author.value == "")||(bookincome.author.value == null)){
    alert("请输入图书著作人！");
    return false;
  }

  if((bookincome.publisher.value == "")||(bookincome.publisher.value == null)){
    alert("请输入出版人名称！");
    return false;
  }

  if((bookincome.publishtime_year.value == "")||(bookincome.publishtime_year.value == null)){
    alert("请输入完整的出版时间！");
    return false;
  }

  if((bookincome.publishtime_month.value == "")||(bookincome.publishtime_month.value == null)){
    alert("请输入完整的出版时间！");
    return false;
  }

  if((bookincome.publishtime_day.value == "")||(bookincome.publishtime_day.value == null)){
    alert("请输入完整的出版时间！");
    return false;
  }

  if((bookincome.printingtime_year.value == "")||(bookincome.printingtime_year.value == null)){
    alert("请输入完整的印刷时间！");
    return false;
  }

  if((bookincome.printingtime_month.value == "")||(bookincome.printingtime_month.value == null)){
    alert("请输入完整的印刷时间！");
    return false;
  }

  if((bookincome.printingtime_day.value == "")||(bookincome.printingtime_day.value == null)){
    alert("请输入完整的印刷时间！");
    return false;
  }*/

  if(bookincome.newflag.value == -1){
    alert("请选择是否新书！");
    return false;
  }

  return true;
}
</script>
</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form action="bookinfo.jsp" method="post" name="bookincome">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="sid" value=<%=sid%>>
<input type="hidden" name="number" value=<%=number%>>
<center>
<table width="770" border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">图书入库信息</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2">
                <tr>
                  <td>图书基本信息<font color="#FF0000">*</font>必填</td>
                </tr>
              </table>
              <table width="757" border="0" cellpadding="2" cellspacing="1">
<%
  List list = new ArrayList();
  list = bookincomeMgr.getAllBookType();
%>
               <tr bgcolor="#FFFFFF">
                  <td width="143">图书类别：</td>
                  <td width="301"><select name="book_type_id">
                      <option>请选择</option>
                    <%
                      for(int m=0;m<list.size();m++){
                        bookincome = (Bookincome)list.get(m);
                    %>
                      <option value=<%=bookincome.getTypeID()%>><%=new String(bookincome.getTypename().getBytes("iso8859_1"),"GBK")%></option>
                    <%}%></select><font color="#FF0000"> * </font>
                  </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">ISBN号：</td>
                  <td width="301">
                    <input type="text" name="isbn" size="20">
                  </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">名称：</td>
                  <td width="301">
                    <input type="text" name="bookname" size="20">
                    <font color="#FF0000"> * </font> </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">著作人：</td>
                  <td width="301">
                    <input type="text" name="author" size="20">
                  </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">出版人：</td>
                  <td width="301">
                    <input type="text" name="publisher" size="20">
                  </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">出版时间：</td>
                  <td width="301">
                    <input name="publishtime_year" type="text" size="4">
                      年
                      <select name="publishtime_month" id="publishtime_month">
                        <option></option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                      </select>
                      月
                      <select name="publishtime_day" id="publishtime_day">
                        <option></option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                      </select>
                      日</td>
                  <td width="287"></td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">版次印次：</td>
                  <td width="301">
                    <input type="text" name="banciyinci" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">印刷时间：</td>
                    <td width="301">
                      <input name="printingtime_year" type="text" id="printingtime_year" size="4">
                      年
                      <select name="printingtime_month" id="printingtime_month">
                        <option></option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                      </select>
                      月
                      <select name="printingtime_day" id="select3">
                        <option></option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                        <option value="24">24</option>
                        <option value="25">25</option>
                        <option value="26">26</option>
                        <option value="27">27</option>
                        <option value="28">28</option>
                        <option value="29">29</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                      </select>
                      日</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">印数：</td>
                  <td width="301">
                    <input type="text" name="printing_number" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">重量：</td>
                  <td width="301">
                    <input type="text" name="book_weight" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">开本：</td>
                  <td width="301">
                    <input type="text" name="kaiben" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">册数：</td>
                  <td width="301">
                    <input type="text" name="ceshu" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">页码：</td>
                  <td width="301">
                    <input type="text" name="yema" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">印张：</td>
                  <td width="301">
                    <input type="text" name="yinzhang" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">字数：</td>
                  <td width="301">
                    <input type="text" name="wordnum" size="20">
                    &nbsp;</td>
                  <td width="287"></td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">是否有图片：</td>
                  <td width="301">
                    <select name="havepic" size="1">
                        <option>请选择</option>
                        <option value="1">有</option>
                        <option value="0">无</option>
                      </select></td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">装帧：</td>
                  <td width="301">
                    <input type="text" name="zhuangzhen" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">丛书名：</td>
                  <td width="301">
                    <input type="text" name="c_bookname" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr><tr bgcolor="#FFFFFF">
                  <td width="143">品相：</td>
                  <td width="301">
                    <input type="text" name="pinxiang" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">进价：</td>
                  <td width="301">
                    <input type="text" name="inprice" size="20">
                    &nbsp;元</td>
                  <td width="287">
                  </td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">定价：</td>
                  <td width="301">
                    <input type="text" name="last_price" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">售价：</td>
                  <td width="301">
                    <input type="text" name="sale_price" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">折扣价：</td>
                  <td width="301">
                    <input type="text" name="disc_price" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">描述：</td>
                  <td width="301">
                    <input type="text" name="describe" size="20">
                    &nbsp; </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <!--tr bgcolor="#FFFFFF">
                  <td width="143">图片1：</td>
                  <td width="301">
                    <input type="button" value="上传" onclick="javascript:upload(1)">
                  </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">图片2：</td>
                  <td width="301">
                    <input type="button" value="上传" onclick="javascript:upload(2)">
                  </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">图片3：</td>
                  <td width="301">
                    <input type="button" value="上传" onclick="javascript:upload(3)">
                  </td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">图片4：</td>
                  <td width="301">
                    <input type="button" value="上传" onclick="javascript:upload(4)">
                  </td>
                  <td width="287">&nbsp;</td>
                </tr-->
                <tr bgcolor="#FFFFFF">
                    <td width="143">是否推荐：</td>
                  <td width="301">
                    <select name="recommend_flag" size="1">
                        <option>请选择</option>
                        <option value="1">推荐</option>
                        <option value="0">不推荐</option>
                      </select>
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">推荐指数：</td>
                  <td width="301">
                    <input type="text" name="recommend" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">库存数量：</td>
                  <td width="301">
                    <input type="text" name="stocknum" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">销售数量：</td>                  <td width="301">
                    <input type="text" name="salesnum" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">是否上架：</td>
                  <td width="301">
                    <select name="showflag" size="1">
                        <option value="1">上架</option>
                        <option value="0">不上架</option>
                      </select>
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">是否新书：</td>
                  <td width="301">
                    <select name="newflag" size="1">
                        <option value="-1">请选择</option>
                        <option value="0">新书</option>
                        <option value="1">旧书</option>
                        <option value="2">古籍</option>
                        <option value="3">民国书</option>
                      </select>
                    <font color="#FF0000"> * </font></td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">卷数：</td>
                  <td width="301">
                    <input type="text" name="volnum" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">材质：</td>                  <td width="301">
                    <input type="text" name="caizhi" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">版框高度：</td>
                  <td width="301">
                    <input type="text" name="bk_height" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">版框宽度：</td>
                  <td width="301">
                    <input type="text" name="bk_width" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">外观高度：</td>
                  <td width="301">
                    <input type="text" name="layout_height" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">外观宽度：</td>                  <td width="301">
                    <input type="text" name="layout_width" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">行格：</td>
                  <td width="301">
                    <input type="text" name="hangge" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">鱼尾：</td>
                  <td width="301">
                    <input type="text" name="yuwei" size="20">
                    &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">版心：</td>
                  <td width="301">
                    <input type="text" name="banxin" size="20">
                      &nbsp;</td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">著作人介绍：</td>
                    <td width="301"> <input name="author_introduce" type="text" id="author_introduce" size="20"></td>
                  <td width="287">&nbsp;</td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td width="143">目录：</td>
                  <td width="301">
                    <input type="text" name="directory" size="20">
                  </td>
                  <td width="287">&nbsp;</td>
                </tr>

              </table>
            </td>
          </tr>
          <tr align="center">
            <td><input type="submit" name="Submit" value="下一步" onclick="javascript:return check(this.form);">
                &nbsp;&nbsp;&nbsp;
                <input name="Reset" type="reset" id="Reset" value="重置"></td>
          </tr>
        </table>
      </td>
</tr>
</table>
</center>
</form>

</body>
</html>

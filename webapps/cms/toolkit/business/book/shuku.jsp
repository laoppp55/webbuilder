<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.bookincome.*"
                contentType="text/html;charset=gbk"
%>

<%
  boolean success = false;
  int startflag = ParamUtil.getIntParameter(request, "startflag", 0);

  String currentyear = String.valueOf(new Timestamp(System.currentTimeMillis())).substring(0, 4) ;
  int nextflag = 0;
  int sid = 0;
  int batchid = 0;
  String bookname = "";
  int number = 0;
  float inprice = 0;

  if(startflag == 1)
  {
    batchid                       = ParamUtil.getIntParameter(request, "batchid", 0);
    int book_type_id              = ParamUtil.getIntParameter(request, "book_type_id", 0);
    String describe               = ParamUtil.getParameter(request, "describe");
    bookname                      = ParamUtil.getParameter(request, "bookname");
    number                    = ParamUtil.getIntParameter(request, "number" ,0);
    inprice                 = ParamUtil.getFloatParameter(request, "inprice" ,0);
    int intype                    = ParamUtil.getIntParameter(request, "intype" ,0);
    float booksvalue              = ParamUtil.getFloatParameter(request, "booksvalue" ,0);
    String bookshost              = ParamUtil.getParameter(request, "bookshost");
    String jing_ban_ren           = ParamUtil.getParameter(request, "jing_ban_ren");
    float discount                = ParamUtil.getFloatParameter(request, "discount" ,0);
    int buydate_year              = ParamUtil.getIntParameter(request, "buydate_year" ,0);
    int buydate_month             = ParamUtil.getIntParameter(request, "buydate_month" ,0);
    int buydate_day               = ParamUtil.getIntParameter(request, "buydate_day", 0);
    nextflag              = ParamUtil.getIntParameter(request, "nextflag",0);


    java.sql.Date buydate = new java.sql.Date(buydate_year-1900 , buydate_month-1 , buydate_day);

    IBookincomeManager bookincomeMgr = BookincomePeer.getInstance();
    Bookincome bookincome = new Bookincome();

    bookincome.setBatchid(batchid);
    bookincome.setBook_Type_ID(book_type_id);
    bookincome.setDescribe(describe);
    bookincome.setBookName(bookname);
    bookincome.setNumber(number);
    bookincome.setInprice(inprice);
    bookincome.setIntype(intype);
    bookincome.setBooksvalue(booksvalue);
    bookincome.setBookshost(bookshost);
    bookincome.setJing_ban_ren(jing_ban_ren);
    bookincome.setDiscount(discount);
    bookincome.setBuyDate(buydate);

    sid = bookincomeMgr.getSaveID(batchid,bookname);

    if (sid == -1)
    {
      bookincomeMgr.shuku(bookincome);
      sid = bookincomeMgr.getSaveID(batchid,bookname);
      success = true;

    }else{
      success=false;
    }
  }

  if (startflag == 1 && success){
    if (nextflag == 1){
      response.sendRedirect("bookinfo.jsp?sid="+sid+"&bookname="+bookname+"&number="+number+"&inprice="+inprice);
    }
    else{
      response.sendRedirect("ok.htm");
    }
  }
%>

<html>
<head>
<title>布衣书局 图书入库</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../booyee/images/pt9.css">
</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form action="shuku.jsp" method="post" name="bookincome">
<input type="hidden" name="startflag" value="1">
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
                  <td>会员基本信息<font color="#FF0000">*</font>必填</td>
                </tr>
              </table>
                <table width="100%" border="0" cellpadding="2" cellspacing="1">
                  <!--DWLayoutTable-->
                  <tr bgcolor="#FFFFFF">
                    <td width="20%">图书批号：</td>
                    <td width="30%"> <input name="batchid" type="text" id="bantchid" size="20">
                      <font color="#FF0000"> * </font></td>
                    <td>注册成功后，用户名不能再修改！用户名可以是中、英文、数字，下划线及短横线中的组合。(最大长度１６字节)。 </td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>图书类别：</td>
                    <td> <input type="text" name="book_type_id" size="20"> <font color="#FF0000">&nbsp;
                      </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>购买信息：</td>
                    <td> <input name="describe" type="text" id="desc" size="20">
                      <font color="#FF0000">&nbsp; </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>图书名称：</td>
                    <td> <input name="bookname" type="text" id="bookname" size="20">
                      <font color="#FF0000"> * </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>册数：</td>
                    <td> <input name="number" type="text" id="number" size="20">
                      <font color="#FF0000"> * </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>价格：</td>
                    <td> <input name="inprice" type="text" id="inprice" size="20">
                      <font color="#FF0000"> * </font> </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>执行操作：</td>
                    <td> <select name="intype" size="1" id="intype">
                        <option>请选择</option>
                        <option value="0">购新书</option>
                        <option value="1">购旧书</option>
                        <option value="2">购古籍</option>
                        <option value="3">寄售新书</option>
                        <option value="4">寄售旧书</option>
                        <option value="5">寄售古籍</option>
                      </select> <font color="#FF0000"> * </font></td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>货值：</td>
                    <td> <input name="booksvalue" type="text" id="booksvalue" size="20"></td>
                    <td></td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>货主：</td>
                    <td> <input name="bookshost" type="text" id="bookshost" size="20">
                      &nbsp; </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>经办人：</td>
                    <td> <input name="jing_ban_ren" type="text" id="jing_ban_ren" size="20">
                      <font color="#FF0000"> * </font></td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>折扣：</td>
                    <td> <input name="discount" type="text" id="discount" size="20">
                      &nbsp; </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>购进时间：</td>
                    <td> <input name="buydate_year" type="text" id="buydate_year" size="4" maxlength="4" value="<%=currentyear%>">
                      年
                      <select name="buydate_month" id="buydate_month">
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
                      <select name="buytime_day" id="buytime_day">
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
                      日&nbsp; </td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td>&nbsp;</td>
                    <td>
                      <input name="nextflag" type="checkbox" value="1" checked>
                      继续输入详细信息</td>
                    <td>&nbsp;</td>
                  </tr>
                </table>
            </td>
          </tr>
          <tr align="center">
            <td><input type="submit" name="Submit" value="提交">
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

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>购物车</title>
</head>

<body>

<center>
<table width="770" border="0" cellspacing="0" cellpadding="10" background="../images/top-bg.gif" height="75">
  <tr>
    <td><img src="../images/logo.gif" width="217" height="59"></td>
    <td align="right" valign="top">
      <table width="305" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="2"><img src="../images/bar1.gif" width="2" height="25"></td>
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" background="../images/bar-bg.gif">
              <tr>
                <td align="center" valign="bottom">
                  <table width="100%" border="0" cellspacing="0" cellpadding="1">
                    <tr align="center">
                      <td><img src="../images/icon-buy.gif" width="12" height="12"> 
                        <a href="#">购物车</a></td>
                    </tr>
                  </table>
                </td>
                <td width="2"><img src="../images/bar-line.gif" width="2" height="25"></td>
                <td align="center" valign="bottom">
                  <table width="100%" border="0" cellspacing="0" cellpadding="1">
                    <tr align="center">
                      <td><img src="../images/icon-id.gif" width="14" height="14"> 
                        <a href="#">我的帐户</a></td>
                    </tr>
                  </table>
                </td>
                <td width="2"><img src="../images/bar-line.gif" width="2" height="25"></td>
                <td align="center" valign="bottom">
                  <table width="100%" border="0" cellspacing="0" cellpadding="1">
                    <tr align="center">
                      <td><img src="../images/icon-mybook.gif" width="13" height="14"> 
                        <a href="#">我的书架</a></td>
                    </tr>
                  </table>
                </td>
                <td width="2"><img src="../images/bar-line.gif" width="2" height="25"></td>
                <td align="center" valign="bottom">
                  <table width="100%" border="0" cellspacing="0" cellpadding="1">
                    <tr align="center">
                      <td><img src="../images/icon-help.gif" width="11" height="13"> 
                        <a href="#">帮助</a></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
          <td width="2"><img src="../images/bar2.gif" width="2" height="25"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="770" border="0" cellspacing="0" cellpadding="0" background="../images/table-bg.gif">
  <tr>
    <td bgcolor="#FFFFFF" width="147"><img src="../images/pic1.gif" width="145" height="23"></td>
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="55%"></td>
          <td width="25%" align="right"></td>
          <td align="right" width="20%"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="770" bgcolor="#FFFFFF">
  <tr>
    <td><img src="../images/space.gif" width="5" height="5"></td>
  </tr>
</table>
<table width="770" border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
  <tr>
    <td>
      <table width="100%" border="0" cellpadding="0">
        <tr bgcolor="#F4F4F4" align="center">
          <td class="moduleTitle"><font color="#48758C">图书录入信息</font></td>
        </tr>
        <tr>
          <td background="../images/dot-line.gif">　</td>
        </tr>
        <tr bgcolor="#d4d4d4" align="right">
          <td>
            <table width="100%" border="0" cellpadding="2">
              <tr>
                <td>图书基本信息<font color="#FF0000">*</font>必填</td>
              </tr>
            </table>
            <table width="100%" border="0" cellpadding="2" cellspacing="1" height="455">
              <tr bgcolor="#FFFFFF">
                <td width="20%" height="25">图书编号：</td>
                <td width="40%" height="25"><input name="Bookid" type="text" id="Bookid" size="20">
                    <font color="#FF0000"> 
                  *</font></td>     
                <td width="40%" height="25"></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">图书类型：</td>
                <td height="25"><select name="Book_type_id" id="Book_type_id">
                      <option>请选择</option>
                      <option>文学类</option>
                      <option>历史类</option>
                      <option>纪实类</option>
                    </select> <font color="#FF0000">*</font></td>       
                <td height="25"></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">ISBN号：</td>
                <td height="25"><input name="Isbn" type="text" id="Isbn" size="20"> <font color="#FF0000">*</font></td>      
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">图书名称：</td>
                <td height="25"><input name="Bookname" type="text" id="Bookname" size="20"> <font color="#FF0000">*</font></td>      
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">著作人：</td>
                <td height="25"><input name="Author" type="text" id="Author" size="20"> <font color="#FF0000">*</font></td>      
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">出版人：</td>
                <td height="25"><input name="Publisher" type="text" id="Publisher" size="20"> <font color="#FF0000">*</font></td>      
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="34">出版时间：</td>
                <td height="34"><input name="Publishtime_year" type="text" id="Publishtime_year" size="4"> 
                  年 
                    <input name="Publishtime_month" type="text" id="Publishtime_month" size="2">
                     月 
                    <input name="Publishtime_day" type="text" id="Publishtime_day" size="2">  
                  日  
                  <font color="#FF0000">*</font></td>     
                <td height="34"></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="43">印刷时间：</td>
                <td height="43"><input name="Printingtime_year" type="text" id="Printingtime_year" size="4"> 
                  年 
                    <input name="Printingtime_month" type="text" id="Printingtime_month" size="2">
                     月 
                    <input name="Printingtime_day" type="text" id="Printingtime_day" size="2">  
                  日<font color="#FF0000">*</font></td> 
                <td height="43">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">印刷次数：</td>
                <td height="25"><input name="Printing_yinci" type="text" id="Printing_yinci" size="20"> 
                  次 <font color="#FF0000">*</font></td>     
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">印数：</td>
                <td height="25"><input name="Printing_number" type="text" id="Printing_number" size="20"></td>
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="21">重量：</td>
                <td height="21"><input name="Book_weight" type="text" id="Book_weight" size="20"></td>
                <td height="21">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">开本：</td>
                <td height="25"><input name="Kaiben" type="text" id="Kaiben" size="20"> 
                  开&nbsp;</td>     
                <td height="25"></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">册数：</td>
                <td height="25"><input name="Ceshu" type="text" id="Ceshu" size="20"> &nbsp;</td>     
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">页码：</td>
                <td height="25"><input name="Yema" type="text" id="Yema" size="20">  
                  页</td>     
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">印张：</td>
                <td height="25"><input name="Yinzhang" type="text" id="Yinzhang" size="20"></td>     
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">字数：</td>
                <td height="25"><input name="Wordnum" type="text" id="Wordnum" size="20">  
                  字</td>     
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="34">图片有否：</td>
                <td height="34"><input type="radio" name="Havepic" value="1"> 
                  有 
                    <input type="radio" name="Havepic" value="0"> 
                  无</td>     
                <td height="34"></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="43">装帧：</td>
                <td height="43"><input name="Zhuangzhen" type="text" id="Zhuangzhen" size="20"></td>
                <td height="43">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">丛书名：</td>
                <td height="25"><input name="C_bookname" type="text" id="C_bookname" size="20"></td>     
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">进价：</td>
                <td height="25"><input name="In_price" type="text" id="In_price" size="20"> 
                  元</td>
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="21">定价：</td>
                <td height="21"><input name="Last_price" type="text" id="Last_price" size="20"> 
                  元</td>
                <td height="21">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">售价：</td>
                <td height="25"><input name="Sale_price" type="text" id="Sale_price" size="20"> 
                  元</td>  
                <td height="25"></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">折扣价：</td>
                <td height="25"><input name="Disc_price" type="text" id="Disc_price" size="20"> 
                  元</td>  
                <td height="25"></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">描述：</td>
                <td height="25"><input name="Desc" type="text" id="Desc" size="20"> &nbsp;</td>   
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">图片1：</td>
                <td height="25"><input name="Picture1" type="text" id="Picture1" size="20"> 
                  选择图片路径&nbsp;</td>    
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">图片2：</td>
                <td height="25"><input name="Picture2" type="text" id="Picture2" size="20"> 
                  选择图片路径&nbsp;</td>    
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">图片3：</td>
                <td height="25"><input name="Picture3" type="text" id="Picture3" size="20"> 
                  选择图片路径&nbsp;</td>   
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="34">图片4：</td>
                <td height="34"><input name="Picture4" type="text" id="Picture4" size="20"> 
                  选择图片路径&nbsp;</td>   
                <td height="34"></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="43">推荐指数：</td>
                <td height="43"><input name="Recommend" type="text" id="Recommend" size="20"> 
                </td>
                <td height="43">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">库存数量：</td>
                <td height="25"><input name="Stocknum" type="text" id="Stocknum" size="20"> 
                  &nbsp;</td>    
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">销售数量：</td>
                <td height="25"><input name="Salesnum" type="text" id="Salesnum" size="20"> 
                </td>
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="21">是否显示：</td>
                <td height="21"><input name="Showflag" type="radio" value="1" checked> 
                  显示 
                    <input type="radio" name="Showflag" value="0">  
                  不显示&nbsp;</td> 
                  <td height="21">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">是否新书：</td>
                <td height="25"><input type="radio" name="Newflag" value="0"> 
                  新书 
                    <input type="radio" name="Newflag" value="1"> 
                  旧书 
                    <input type="radio" name="Newflag" value="2"> 
                  古籍</td>   
                <td height="25"></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">卷数：</td>
                <td height="25"><input name="Volnum" type="text" id="Volnum" size="20"> &nbsp;</td>    
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">材质（古籍）：</td>
                <td height="25"><input name="Caizhi" type="text" id="Caizhi" size="20"> &nbsp;</td>    
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">版框高度（古籍）：</td>
                <td height="25"><input name="BK_height" type="text" id="BK_height" size="20"> 
                </td>   
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">版框宽度（古籍）：</td>
                <td height="25"><input name="BK_width" type="text" id="BK_width" size="20"> &nbsp;</td>    
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="34">外观高度（古籍）：</td>
                <td height="34"><input name="Layout_height" type="text" id="Layout_height" size="20"> 
                  &nbsp;</td>    
                <td height="34"></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="43">外观宽度（古籍）：</td>
                <td height="43"><input name="Layout_width" type="text" id="Layout_width" size="20"> 
                </td>
                <td height="43">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">行格（古籍）：</td>
                <td height="25"><input name="Hangge" type="text" id="Hangge" size="20"> 
                  &nbsp;</td>    
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="25">鱼尾（古籍）：</td>
                <td height="25"><input type="radio" name="Yuwei" value="0"> 
                  无 
                    <input type="radio" name="Yuwei" value="1"> 
                  单 
                    <input type="radio" name="Yuwei" value="2"> 
                  双</td>
                <td height="25">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td height="21">版心（古籍）：</td>
                <td height="21"><input name="Banxin" type="text" id="Banxin" size="20"> 
                </td>
                <td height="21">&nbsp;</td>
              </tr>              
              <tr bgcolor="#FFFFFF">
                <td height="34">入库日期（古，现）：</td>
                  <td height="34">
<input name="Createdate_year" type="text" id="Createdate_year" size="4"> 
                  年 
                    <input name="Createdate_month" type="text" id="Createdate_month" size="2">
                     月 
                    <input name="Createdate_day" type="text" id="Createdate_day" size="2">   
                  日<font color="#FF0000">*</font></td>  
                <td height="34"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr align="center">
          <td><img src="../images/button-register.gif" width="51" height="20">&nbsp;&nbsp;&nbsp;<img src="../images/button-reset.gif" width="51" height="20"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="770" border="0" cellspacing="0" cellpadding="0">
  <tr bgcolor="#FFFFFF">
    <td><img src="../images/space.gif" width="5" height="5"></td>
  </tr>
</table>
<table width="770" border="0" cellspacing="0" cellpadding="4">
  <tr align="center" bgcolor="#F1F2EC">
    <td class="pt9"></td>    
  </tr>
</table>
<table width="770" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="3"><img src="../images/table-corner2.gif" width="3" height="4"></td>
    <td bgcolor="#999797"><img src="../images/space.gif" width="4" height="4"></td>
  </tr>
</table>
<table width="770" border="0" cellspacing="0" cellpadding="4">
  <tr align="center">
    <td><font color="#FFFFFF">版权所有 2004年  
      北京布衣文化传播有限公司</font></td>
  </tr>
</table>
</center>

</body>

</html>

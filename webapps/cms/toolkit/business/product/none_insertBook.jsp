<%@ page import="java.sql.*,
                 java.util.*,
                 java.io.*,
                 jxl.*,
                 java.net.URL,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.booyee.transdate.*"
                 contentType="text/html;charset=gbk"
%>
<%
  ITransDateManager transdateMgr = TransDatePeer.getInstance();
  TransDate transdate = new TransDate();

  long bookid;
  int book_type_id;
  String isbn;
  String bookname;
  String author;
  String publisher;

  java.sql.Date publishtime;
  String pubtime;
  String pubyear;
  String pubmonth;
  String pubday;

  java.sql.Date printingtime;
  String printtime;
  String printyear;
  String printmonth;
  String printday;

  int book_banci;
  int printingtime_year;
  int printingtime_month;
  int printingtime_day;
  int printing_yinci;
  int printing_number;
  float book_weight;
  String kaiben;
  int ceshu;
  int yema;
  int yinzhang;
  int wordnum;
  int havepic;
  String zhuangzhen;
  String c_bookname;
  int pinxiang;
  float in_price;
  float last_price;
  float sale_price;
  float disc_price;
  String describe;
  int recommend_flag;
  int recommend;
  int stocknum;
  int salesnum;
  int showflag;
  int newflag;
  int volnum;
  String caizhi;
  float bk_height;
  float bk_width;
  float layout_height;
  float layout_width;
  String hangge;
  int yuwei;
  String banxin;
  String createdate;
  String author_introduce;
  String directory;
  String gonghuoshang;

  jxl.Workbook rwb1 = null;
  jxl.Workbook rwb2 = null;

  try
  {
      String userfilename = "d:\\sites\\booyee\\uploadbook\\insertBook.xls";
      InputStream is = new FileInputStream(userfilename);
      rwb1 = Workbook.getWorkbook(is);
  }
  catch (Exception e)
  {
        e.printStackTrace();
  }

  //从sheet2中读取信息，入tbl_shuku表
  Sheet rs_w = rwb1.getSheet(1);
  int rows_w = rs_w.getRows();

  for(int j=1;j<rows_w;j++){
      Cell wc0x = rs_w.getCell(0, j);
        String batchid = wc0x.getContents();
      Cell wc1x = rs_w.getCell(1, j);
        String intype = wc1x.getContents();
      Cell wc2x = rs_w.getCell(2, j);
        String number = wc2x.getContents();
      Cell wc4x = rs_w.getCell(3, j);
        String inprice = wc4x.getContents();
      Cell wc5x = rs_w.getCell(4, j);
        String bookhost = wc5x.getContents();
      Cell wc6x = rs_w.getCell(5, j);
        String jingbanren = wc6x.getContents();
      Cell wc7x = rs_w.getCell(6, j);
        describe = wc7x.getContents();

      transdate.setBatchid(Integer.parseInt(batchid));
      transdate.setIntype(Integer.parseInt(intype));
      transdate.setNumber(Integer.parseInt(number));
      transdate.setInprice(Float.parseFloat(inprice));
      transdate.setBookshost(bookhost);
      transdate.setJing_ban_ren(jingbanren);
      transdate.setDescribe(describe);

      transdateMgr.createShuKu(transdate);
  }
  rwb1.close();

  try
  {
      String userfilename = "d:\\sites\\booyee\\uploadbook\\insertBook.xls";
      InputStream is = new FileInputStream(userfilename);
      rwb2 = Workbook.getWorkbook(is);
  }
  catch (Exception e)
  {
      e.printStackTrace();
  }

  //从sheet1中读取信息，入tbl_bookinfo表
  Sheet rs = rwb2.getSheet(0);
  int rows = rs.getRows();

  for(int i=1;i<rows;i++){
      Cell c0x = rs.getCell(0, i);
        int batchid = 0;
        String batchidstr = c0x.getContents();
        if((batchidstr == "")||(batchidstr == null))
          batchid = 0;
        else
          batchid = Integer.parseInt(c0x.getContents());
      Cell c1x = rs.getCell(1, i);
        String typestr = c1x.getContents();
        if((typestr == "")||(typestr == null)){
          book_type_id = -1;
        }else{
          book_type_id = Integer.parseInt(c1x.getContents());
        }
      Cell c2x = rs.getCell(2, i);
        String newflagstr = c2x.getContents();
        if((newflagstr == "")||(newflagstr == null)){
          newflag = -1;
        }else{
          newflag = Integer.parseInt(c2x.getContents());
        }
      Cell c3x = rs.getCell(3, i);
        bookname = c3x.getContents();
      Cell c4x = rs.getCell(4, i);
        isbn = c4x.getContents();
        if((isbn == "")||(isbn == null)){
          isbn = "";
        }
      Cell c5x = rs.getCell(5, i);
        author = c5x.getContents();
        if((author == "")||(author == null)){
          author = "";
        }
      Cell c6x = rs.getCell(6, i);
        publisher = c6x.getContents();
        if((publisher == "")||(publisher == null)){
          publisher = "";
        }
      Cell c7x = rs.getCell(7, i);
        pubtime = c7x.getContents();
        if((pubtime != "")&&(pubtime != null)&&(!pubtime.equals("null"))){
          pubyear = pubtime.substring(pubtime.lastIndexOf("/")+1, pubtime.length());
          if(Integer.parseInt(pubyear)>4){
            pubyear = "19"+pubyear;
          }else{
            pubyear = "20"+pubyear;
          }
          pubmonth = pubtime.substring(0, pubtime.indexOf("/"));
          pubday = pubtime.substring(pubtime.indexOf("/")+1, pubtime.lastIndexOf("/"));
          publishtime = new java.sql.Date(Integer.parseInt(pubyear)-1900 , Integer.parseInt(pubmonth)-1 , Integer.parseInt(pubday));
        }else{
          publishtime = null;
        }
      Cell c8x = rs.getCell(8, i);
        printtime = c8x.getContents();
        if((printtime != "")&&(printtime != null)&&(!printtime.equals("null"))){
          printyear = printtime.substring(printtime.lastIndexOf("/")+1, printtime.length());
          if(Integer.parseInt(printyear)>4){
            printyear = "19"+printyear;
          }else{
            printyear = "20"+printyear;
          }
          printmonth = printtime.substring(0, printtime.indexOf("/"));
          printday = printtime.substring(printtime.indexOf("/")+1, printtime.lastIndexOf("/"));
          printingtime = new java.sql.Date(Integer.parseInt(printyear)-1900 , Integer.parseInt(printmonth)-1 , Integer.parseInt(printday));
        }else{
          printingtime = null;
        }
      Cell c9x = rs.getCell(9, i);
        String banciyinci = c9x.getContents();
        if((banciyinci == "")||(banciyinci == null)){
          banciyinci = "";
        }
      Cell c10x = rs.getCell(10, i);
        String printnum = c10x.getContents();
        if((printnum == "")||(printnum == null)){
          printing_number = 0;
        }else{
          printing_number = Integer.parseInt(c10x.getContents());
        }
      Cell c11x = rs.getCell(11, i);
        kaiben = c11x.getContents();
        if((kaiben == "")||(kaiben == null)){
          kaiben = "";
        }
      Cell c12x = rs.getCell(12, i);
        String yemastr = c12x.getContents();
        if((yemastr == "")||(yemastr == null)){
          yema = 0;
        }else{
          yema = Integer.parseInt(c12x.getContents());
        }
      Cell c13x = rs.getCell(13, i);
        String havepicstr = c13x.getContents();
        if((havepicstr == "")||(havepicstr == null)){
          havepic = 0;
        }else{
          havepic = Integer.parseInt(c13x.getContents());
        }
      Cell c14x = rs.getCell(14, i);
        zhuangzhen = c14x.getContents();
        if((zhuangzhen == "")||(zhuangzhen == null))
        {
          zhuangzhen = "";
        }
      Cell c15x = rs.getCell(15, i);
        String pinxiangstr = c15x.getContents();
        if((pinxiangstr == "")||(pinxiangstr == null)){
          pinxiang = 10;
        }else{
          pinxiang = Integer.parseInt(c15x.getContents());
        }
     Cell c16x = rs.getCell(16, i);
        String inpricestr = c16x.getContents();
        if((inpricestr == "")||(inpricestr == null))
          in_price = 0;
        else
          in_price = Float.parseFloat(c16x.getContents());
      Cell c17x = rs.getCell(17, i);
        String lastpricestr = c17x.getContents();
        if((lastpricestr == "")||(lastpricestr == null))
          last_price = 0;
        else
          last_price = Float.parseFloat(c17x.getContents());
      Cell c18x = rs.getCell(18, i);
        String salepricestr = c18x.getContents();
        if((salepricestr == "")||(salepricestr == null))
          sale_price = 0;
        else
          sale_price = Float.parseFloat(c18x.getContents());
      Cell c19x = rs.getCell(19, i);
        String bookweightstr = c19x.getContents();
        if((bookweightstr == "")||(bookweightstr == null))
          book_weight = 0;
        else
          book_weight = Float.parseFloat(c19x.getContents());
      Cell c20x = rs.getCell(20, i);
        describe = c20x.getContents();
        if((describe == "")||(describe == null))
          describe = "";
      Cell c21x = rs.getCell(21, i);
        c_bookname = c21x.getContents();
        if((c_bookname == "")||(c_bookname == null)){
          c_bookname = "";
        }
      Cell c22x = rs.getCell(22, i);
        String stocknumstr = c22x.getContents();
        if((stocknumstr == "")||(stocknumstr == null))
          stocknum = 0;
        else
          stocknum = Integer.parseInt(c22x.getContents());
      Cell c23x = rs.getCell(23, i);
        String recommendstr = c23x.getContents();
        if((recommendstr == "")||(recommendstr == null))
          recommend = 0;
        else
          recommend = Integer.parseInt(c23x.getContents());
      Cell c24x = rs.getCell(24, i);
        gonghuoshang = c24x.getContents();
        if((gonghuoshang == "")||(gonghuoshang == null))
          gonghuoshang = "";
      Cell c25x = rs.getCell(25, i);
        author_introduce = c25x.getContents();
        if((author_introduce == "")||(author_introduce == null))
          author_introduce = "";
      Cell c26x = rs.getCell(26, i);
        directory = c26x.getContents();
        if((directory == "")||(directory == null))
          directory = "";

      //根据batchid从表tbl_shuku中获得sid
      int sid = 0;
      sid = transdateMgr.getSid(batchid);

      transdate.setSid(sid);
      transdate.setBook_Type_ID(book_type_id);
      transdate.setNewFlag(newflag);
      transdate.setBookName(bookname);
      transdate.setISBN(isbn);
      transdate.setAuthor(author);
      transdate.setPublisher(publisher);
      transdate.setPublishtime(publishtime);
      transdate.setPrinting_Time(printingtime);
      transdate.setBanCiYinCi(banciyinci);
      transdate.setPrinting_Number(printing_number);
      transdate.setKaiBen(kaiben);
      transdate.setYeMa(yema);
      transdate.setHavePic(havepic);
      transdate.setZhuangZhen(zhuangzhen);
      transdate.setPinXiang(pinxiang);
      transdate.setIn_Price(in_price);
      transdate.setLast_Price(last_price);
      transdate.setSale_Price(sale_price);
      transdate.setBook_Weight(book_weight);
      transdate.setDescribe(describe);
      transdate.setC_BookName(c_bookname);
      transdate.setStockNum(stocknum);
      transdate.setRecommend(recommend);
      transdate.setGongHuoShang(gonghuoshang);
      transdate.setAuthor_Introduce(author_introduce);
      transdate.setDirectory(directory);

      //将数据插入数据库
      transdateMgr.createBookInfo(transdate);

    }
    rwb2.close();
    out.println("批量上书完成！");
%>

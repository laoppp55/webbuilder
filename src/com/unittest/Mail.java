package com.unittest;

import com.bizwink.cms.news.Article;
import com.bizwink.cms.util.DBUtil;
import com.bizwink.cms.util.FileUtil;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mail
{
    public static void main(String args[])
    {
        try {
            Connection conn = null;
            PreparedStatement pstmt =  null;
            ResultSet rs = null;
            // Create a variable for the connection string.
            //String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=dblog;integratedSecurity=true;";
            String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=dblog;user=weblog;password=1qaz2wsx";

            List<Article> articles = new ArrayList();

            //Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            //mysql_conn = DriverManager.getConnection("jdbc:mysql://116.90.87.233:3306/dns?characterEncoding=gbk", "dns", "1qaz2wsx");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(connectionUrl);

            pstmt = conn.prepareStatement("select * from tbl_ip");           //每个栏目必须改
            rs = pstmt.executeQuery();
            int count = 0;
            StringBuffer buf = new StringBuffer();

            while (rs.next()) {
                String tbuf = null;
                tbuf = rs.getInt("pid") + "," + rs.getString("beginip") + "," + rs.getString("endip") + "," + rs.getLong("num_beginip") +
                rs.getLong("num_endip") + "," + rs.getString("country") + "," + rs.getString("city") + "," + rs.getInt("cityid") + "," + rs.getInt("countryid") + "\r\n";
                count = count + 1;
                buf.append(tbuf);
                System.out.println(count + "===" + tbuf);
            }
            rs.close();
            pstmt.close();
            conn.close();

            FileUtil.writeFile(buf,"c:\\ipdata.txt");
            //createArticles(articles);
        } catch (Exception sqlexp) {
            sqlexp.printStackTrace();
            System.out.println("增加DNS记录出现错误");
        }
    }

    private static final String SQL_CREATE_ARTICLE_FOR_ORACLE =
            "INSERT INTO TBL_Article (siteid,ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
                    "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,downfilename,Doclevel,PubFlag,Status,Auditflag,Subscriber," +
                    "Editor,creator,DirName,Publishtime,SalePrice,vipprice,InPrice,MarketPrice,score,voucher,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
                    "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,articlepic,urltype," +
                    "defineurl,t1,t2,t3,t4,t5,deptid,beidate,changepic,auditor,notearticleid,fromsiteid,sarticleid,mediafile,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static void createArticles(List<Article> articles) {
        Connection conn = null;
        PreparedStatement pstmt =  null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        boolean doflag = false;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:oracle11g", "nsbdadmin", "qazwsxokm");
            conn.setAutoCommit(false);
            for(int ii=0; ii<articles.size(); ii++) {
                pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_FOR_ORACLE);
                Article article = articles.get(ii);
                pstmt.setInt(1, article.getSiteID());
                pstmt.setInt(2, article.getColumnID());
                pstmt.setInt(3, article.getSortID());
                pstmt.setString(4, article.getMainTitle());
                pstmt.setString(5, article.getViceTitle());
                pstmt.setString(6, article.getSummary());
                pstmt.setString(7, article.getKeyword());
                pstmt.setString(8, article.getSource());
                DBUtil.setBigString("oracle", pstmt, 9, article.getContent());
                pstmt.setInt(10, article.getNullContent());
                pstmt.setString(11, article.getAuthor());
                pstmt.setTimestamp(12, article.getCreateDate());
                pstmt.setTimestamp(13, article.getLastUpdated());
                pstmt.setString(14, article.getFileName());
                pstmt.setString(15,article.getDownfilename());
                pstmt.setInt(16, article.getDocLevel());
                pstmt.setInt(17, article.getPubFlag());
                pstmt.setInt(18, article.getStatus());
                pstmt.setInt(19, article.getAuditFlag());
                pstmt.setInt(20, article.getSubscriber());
                pstmt.setString(21, article.getEditor());
                pstmt.setString(22, article.getCreator());
                pstmt.setString(23, article.getDirName());
                pstmt.setTimestamp(24, article.getPublishTime());
                pstmt.setFloat(25, article.getSalePrice());
                pstmt.setFloat(26, article.getVIPPrice());
                pstmt.setFloat(27, article.getInPrice());
                pstmt.setFloat(28, article.getMarketPrice());
                pstmt.setInt(29, article.getScore());
                pstmt.setInt(30, article.getVoucher());
                pstmt.setFloat(31, article.getProductWeight());
                pstmt.setInt(32, article.getStockNum());
                pstmt.setString(33, article.getBrand());
                pstmt.setString(34, article.getProductPic());
                pstmt.setString(35, article.getProductBigPic());
                pstmt.setString(36, article.getRelatedArtID());
                pstmt.setInt(37, 0);
                pstmt.setInt(38, 0);
                pstmt.setInt(39, 0);
                pstmt.setInt(40, article.getViceDocLevel());
                pstmt.setInt(41, 0);
                pstmt.setInt(42, 0);
                pstmt.setInt(43, article.getReferArticleID());
                pstmt.setInt(44, article.getModelID());
                pstmt.setString(45, article.getArticlepic());
                pstmt.setInt(46, article.getUrltype());
                pstmt.setString(47, article.getOtherurl());
                pstmt.setInt(48, article.getT1());
                pstmt.setInt(49, article.getT2());
                pstmt.setInt(50, article.getT3());
                pstmt.setInt(51, article.getT4());
                pstmt.setInt(52, article.getT5());
                pstmt.setString(53,article.getDeptid());
                pstmt.setDate(54,article.getBbdate());
                pstmt.setInt(55,article.getChangepic());
                pstmt.setString(56,article.getAuditor());
                pstmt.setInt(57,article.getNotes());
                pstmt.setInt(58,article.getFromsiteid());
                pstmt.setString(59,article.getSarticleid());
                pstmt.setString(60,article.getMediafile());
                int articleID = sequnceMgr.getSequenceNum("Article");
                pstmt.setInt(61, articleID);
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
            conn.close();
            System.out.println("文章导入完毕");
        } catch (SQLException exp) {
            exp.printStackTrace();
        } catch (ClassNotFoundException exp1) {
            exp1.printStackTrace();
        }
    }

    public static void createArticle(Article article) {
        Connection conn = null;
        PreparedStatement pstmt =  null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        boolean doflag = false;

        int trycount = 0;
        while (!doflag && trycount<5) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:oracle11g", "nsbdadmin", "qazwsxokm");
                pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_FOR_ORACLE);
                pstmt.setInt(1, article.getSiteID());
                pstmt.setInt(2, article.getColumnID());
                pstmt.setInt(3, article.getSortID());
                pstmt.setString(4, article.getMainTitle());
                pstmt.setString(5, article.getViceTitle());
                pstmt.setString(6, article.getSummary());
                pstmt.setString(7, article.getKeyword());
                pstmt.setString(8, article.getSource());
                DBUtil.setBigString("oracle", pstmt, 9, article.getContent());
                pstmt.setInt(10, article.getNullContent());
                pstmt.setString(11, article.getAuthor());
                pstmt.setTimestamp(12, article.getCreateDate());
                pstmt.setTimestamp(13, article.getLastUpdated());
                pstmt.setString(14, article.getFileName());
                pstmt.setString(15,article.getDownfilename());
                pstmt.setInt(16, article.getDocLevel());
                pstmt.setInt(17, article.getPubFlag());
                pstmt.setInt(18, article.getStatus());
                pstmt.setInt(19, article.getAuditFlag());
                pstmt.setInt(20, article.getSubscriber());
                pstmt.setString(21, article.getEditor());
                pstmt.setString(22, article.getCreator());
                pstmt.setString(23, article.getDirName());
                pstmt.setTimestamp(24, article.getPublishTime());
                pstmt.setFloat(25, article.getSalePrice());
                pstmt.setFloat(26, article.getVIPPrice());
                pstmt.setFloat(27, article.getInPrice());
                pstmt.setFloat(28, article.getMarketPrice());
                pstmt.setInt(29, article.getScore());
                pstmt.setInt(30, article.getVoucher());
                pstmt.setFloat(31, article.getProductWeight());
                pstmt.setInt(32, article.getStockNum());
                pstmt.setString(33, article.getBrand());
                pstmt.setString(34, article.getProductPic());
                pstmt.setString(35, article.getProductBigPic());
                pstmt.setString(36, article.getRelatedArtID());
                pstmt.setInt(37, 0);
                pstmt.setInt(38, 0);
                pstmt.setInt(39, 0);
                pstmt.setInt(40, article.getViceDocLevel());
                pstmt.setInt(41, 0);
                pstmt.setInt(42, 0);
                pstmt.setInt(43, article.getReferArticleID());
                pstmt.setInt(44, article.getModelID());
                pstmt.setString(45, article.getArticlepic());
                pstmt.setInt(46, article.getUrltype());
                pstmt.setString(47, article.getOtherurl());
                pstmt.setInt(48, article.getT1());
                pstmt.setInt(49, article.getT2());
                pstmt.setInt(50, article.getT3());
                pstmt.setInt(51, article.getT4());
                pstmt.setInt(52, article.getT5());
                pstmt.setString(53,article.getDeptid());
                pstmt.setDate(54,article.getBbdate());
                pstmt.setInt(55,article.getChangepic());
                pstmt.setString(56,article.getAuditor());
                pstmt.setInt(57,article.getNotes());
                pstmt.setInt(58,article.getFromsiteid());
                pstmt.setString(59,article.getSarticleid());
                pstmt.setString(60,article.getMediafile());
                int articleID = sequnceMgr.getSequenceNum("Article");
                pstmt.setInt(61, articleID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                doflag=true;
            } catch (SQLException exp) {
                exp.printStackTrace();
                trycount = trycount + 1;
            } catch (ClassNotFoundException exp1) {
                exp1.printStackTrace();
                trycount = trycount + 1;
            }
        }
    }

    public static void readXML()
    {
        StringBuffer xmlStr = null;
        String encoding="GBK";
        File file=new File("C:\\cms\\lz8.txt");
        if(file.isFile() && file.exists()){ //判断文件是否存在
            try {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                xmlStr = new StringBuffer();
                while((lineTxt = bufferedReader.readLine()) != null){
                    lineTxt = lineTxt.replace("&","&amp;");
                    lineTxt = lineTxt.replace("</br>","");
                    //lineTxt = lineTxt.replace("<","&lt;");
                    //lineTxt = lineTxt.replace(">","&gt;");
                    //lineTxt = lineTxt.replace("\"","&quot;");
                    xmlStr.append(lineTxt);
                }
                read.close();
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        }

        StringReader sr = new StringReader(xmlStr.toString());
        InputSource is = new InputSource(sr);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document doc = builder.parse(is);
        } catch (ParserConfigurationException exp) {

        } catch (IOException exp) {

        } catch (SAXException exp) {

        }
    }


    private static class TheColumn {
        int columnid;
        String columnname;

        public int getColumnid() {
            return columnid;
        }

        public void setColumnid(int columnid) {
            this.columnid = columnid;
        }

        public String getColumnname() {
            return columnname;
        }

        public void setColumnname(String columnname) {
            this.columnname = columnname;
        }
    }
}
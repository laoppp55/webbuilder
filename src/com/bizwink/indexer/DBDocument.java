package com.bizwink.indexer;
import org.apache.lucene.document.*;
import com.bizwink.cms.news.Article;
import com.bizwink.cms.util.StringUtil;

public class DBDocument {
    //文档
    public static Document Document(int siteid,String sitename,String dbtype,String classid,Article article,String dirname,String createdate){
        int articleid=0;
        int sortid = 0;
        String maintitle = null;
        String keyword = null;
        String summary = null;
        String content = null;
        String articlepic = "";
        float saleprice = 0.00f;            	            //电子商务使用  商品售价
        float inprice = 0.00f;                            	//电子商务使用  商品进价
        float marketprice = 0.00f;          	            //电子商务使用  商品市场价
        float vipprice = 0.00f;          	                //电子商务使用  商品VIP价
        int   stocknum = 0;             	                //电子商务使用  商品库存量
        String brand = "";                	            //电子商务使用  商品品牌
        String pic = "";                  	            //电子商务使用  商品小图片
        String bigpic = "";               	            //电子商务使用  商品大图片
        String lastupdate = null;
        String publishtime = null;
        String filename = null;

        articleid = article.getID();
        if (dbtype.equalsIgnoreCase("mssql")) {
            content = StringUtil.gb2iso4View(article.getContent());
            maintitle = StringUtil.gb2iso4View(article.getMainTitle());
            summary = StringUtil.gb2iso4View(article.getSummary());
            keyword = StringUtil.gb2iso4View(article.getKeyword());
        } else if(dbtype.equalsIgnoreCase("mysql")) {
            content = article.getContent();
            maintitle = article.getMainTitle();
            summary = article.getSummary();
            keyword = article.getKeyword();
        } else {
            content = article.getContent();
            maintitle = article.getMainTitle();
            summary = article.getSummary();
            keyword = article.getKeyword();
        }
        articlepic = article.getArticlepic();
        if (articlepic == null) articlepic = "";
        saleprice = article.getSalePrice();
        inprice = article.getInPrice();
        marketprice = article.getMarketPrice();
        vipprice = article.getVIPPrice();
        stocknum = article.getStockNum();
        brand = article.getBrand();
        if (brand == null) brand = "";
        pic = article.getProductPic();
        if (pic == null) pic = "";
        bigpic = article.getProductBigPic();
        if (bigpic == null) bigpic = "";
        dirname = article.getDirName();
        lastupdate = article.getLastUpdated().toString();
        publishtime = article.getPublishTime().toString();
        filename = article.getFileName();

        Document doc = new Document();
        try {
            doc.add(new Field("id", String.valueOf(articleid), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("siteid", String.valueOf(siteid), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("classid", classid, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("sortid", String.valueOf(sortid), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("saleprice", String.valueOf(saleprice), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("inprice", String.valueOf(inprice), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("marketprice", String.valueOf(marketprice), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("vipprice", String.valueOf(vipprice), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("stocknum", String.valueOf(stocknum), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("brand", brand, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("pic", pic, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("bigpic", bigpic, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("columnid", String.valueOf(article.getColumnID()), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("articlepic", articlepic, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("createdate", createdate, Field.Store.YES, Field.Index.NOT_ANALYZED));
        }catch(Exception e){
            e.printStackTrace();
        }

        if (sitename != null) {
            try {
                doc.add(new Field("sitename", sitename, Field.Store.YES, Field.Index.NOT_ANALYZED));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        if(maintitle != null){
            try{
                doc.add(new Field("maintitle", maintitle, Field.Store.YES, Field.Index.ANALYZED));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(summary != null){
            //System.out.println("have summary=" + summary);
            try{
                doc.add(new Field("summary", summary, Field.Store.YES, Field.Index.ANALYZED));
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            summary = StringUtil.getContentFromHTML(content);
            summary = summary.trim();
            //content = StringUtil.getContentFromHTML(content,"<style ","</style>");
            //summary = StringUtil.getContentFromHTML(content,"<",">");
            //System.out.println(summary.length());
            if (summary.length() > 50) summary = summary.substring(0,50);
            try{
                doc.add(new Field("summary", summary, Field.Store.YES, Field.Index.ANALYZED));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(keyword != null){
            try{
                doc.add(new Field("keyword", keyword, Field.Store.YES, Field.Index.ANALYZED));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(content != null){
            try{
                doc.add(new Field("content", content, Field.Store.NO, Field.Index.ANALYZED));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        try{
            doc.add(new Field("lastupdated", article.getLastUpdated().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("publishtime", String.valueOf(publishtime), Field.Store.YES, Field.Index.NOT_ANALYZED));

            if (filename != null)
                doc.add(new Field("filename", filename, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("dirname", dirname, Field.Store.YES, Field.Index.NOT_ANALYZED));
        }catch(Exception e){
            e.printStackTrace();
        }

        return doc;
    }

    public static Document Document(int siteid,String articlePathinfo,com.bizwink.po.Article article){
        int articleid=0;
        int sortid = 0;
        String maintitle = null;
        String keyword = null;
        String summary = null;
        String content = null;
        String articlepic = "";
        float saleprice = 0.00f;            	            //电子商务使用  商品售价
        float inprice = 0.00f;                            	//电子商务使用  商品进价
        float marketprice = 0.00f;          	            //电子商务使用  商品市场价
        float vipprice = 0.00f;          	                //电子商务使用  商品VIP价
        int   stocknum = 0;             	                //电子商务使用  商品库存量
        String brand = "";                	            //电子商务使用  商品品牌
        String pic = "";                  	            //电子商务使用  商品小图片
        String bigpic = "";               	            //电子商务使用  商品大图片
        String publishtime = null;
        String filename = null;

        articleid = article.getID().intValue();
        content = article.getCONTENT();
        maintitle = article.getMAINTITLE();
        summary = article.getSUMMARY();
        keyword = article.getKEYWORD();
        articlepic = article.getARTICLEPIC();
        if (articlepic == null) articlepic = "";
        brand = article.getBRAND();
        if (brand == null) brand = "";
        if (bigpic == null) bigpic = "";
        String dirname = article.getDIRNAME();
        filename = article.getFILENAME();

        Document doc = new Document();
        try {
            doc.add(new IntField("id", articleid, IntField.Store.YES));
            doc.add(new IntField("siteid", siteid, IntField.Store.YES));
            doc.add(new StringField("classid", articlePathinfo, StringField.Store.YES));
            doc.add(new IntField("sortid", sortid, IntField.Store.YES));
            doc.add(new FloatField("saleprice", saleprice, FloatField.Store.YES));
            doc.add(new FloatField("inprice", inprice, FloatField.Store.YES));
            doc.add(new FloatField("marketprice", marketprice, FloatField.Store.YES));
            doc.add(new FloatField("vipprice", vipprice, FloatField.Store.YES));
            doc.add(new IntField("stocknum", stocknum, IntField.Store.YES));
            doc.add(new StringField("brand", brand, StringField.Store.YES));
            doc.add(new StringField("pic", pic, StringField.Store.YES));
            doc.add(new StringField("bigpic", bigpic, StringField.Store.YES));
            doc.add(new IntField("columnid", article.getCOLUMNID().intValue(), Field.Store.YES));
            doc.add(new StringField("articlepic", articlepic, Field.Store.YES));
            IntField createDateField = new IntField("createdate", Integer.parseInt(DateTools.dateToString(article.getCREATEDATE(), DateTools.Resolution.DAY)),Field.Store.YES);
            doc.add(createDateField);
            IntField pubDateField = new IntField("publishtime", Integer.parseInt(DateTools.dateToString(article.getPUBLISHTIME(), DateTools.Resolution.DAY)),Field.Store.YES);
            doc.add(pubDateField);
            System.out.println("createdate==="  + DateTools.dateToString(article.getCREATEDATE(),DateTools.Resolution.DAY));
        }catch(Exception e){
            e.printStackTrace();
        }

        if(maintitle != null){
            try{
                doc.add(new TextField("maintitle", maintitle, TextField.Store.YES));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(summary != null){
            //System.out.println("have summary=" + summary);
            try{
                doc.add(new TextField("summary", summary, TextField.Store.YES));
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            summary = com.bizwink.util.CommUtil.getContentFromHTML(content);
            summary = summary.trim();
            //content = StringUtil.getContentFromHTML(content,"<style ","</style>");
            //summary = StringUtil.getContentFromHTML(content,"<",">");
            //System.out.println(summary.length());
            if (summary.length() > 50) summary = summary.substring(0,50);
            try{
                doc.add(new TextField("summary", summary, TextField.Store.YES));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(keyword != null){
            try{
                doc.add(new TextField("keyword", keyword, TextField.Store.YES));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(content != null){
            try{
                doc.add(new TextField("content", content, TextField.Store.NO));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        try{
            if (filename != null)
                doc.add(new StringField("filename", filename, StringField.Store.YES));
            doc.add(new StringField("dirname", dirname, StringField.Store.YES));
        }catch(Exception e){
            e.printStackTrace();
        }

        return doc;
    }

    public static Document Document(Article article){
        Document doc = new Document();

        doc.add(new Field("id", String.valueOf(article.getID()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("siteid", String.valueOf(article.getSiteID()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("sortid", String.valueOf(article.getSortID()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("columnid", String.valueOf(article.getColumnID()), Field.Store.YES, Field.Index.NOT_ANALYZED));

        if (article.getSiteName() != null) {
            try {
                doc.add(new Field("sitename", article.getSiteName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
            } catch(Exception e) { }
        }

        if(article.getMainTitle() != null){
            try{
                doc.add(new Field("maintitle", article.getMainTitle(), Field.Store.YES, Field.Index.ANALYZED));
            }catch(Exception e){
            }
        }

        if(article.getSummary() != null){
            try{
                doc.add(new Field("summary", article.getSummary(), Field.Store.YES, Field.Index.ANALYZED));
            }catch(Exception e){
            }
        }

        if(article.getKeyword() != null){
            try{
                doc.add(new Field("keyword", article.getKeyword(), Field.Store.YES, Field.Index.ANALYZED));
            }catch(Exception e){
            }
        }

        if(article.getContent() != null){
            try{
                doc.add(new Field("content", article.getContent(), Field.Store.NO, Field.Index.ANALYZED));
            }catch(Exception e){
            }
        }

        doc.add(new Field("lastupdated", article.getLastUpdated().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("publishtime", article.getPublishTime().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));

        if (article.getFileName() != null)
            doc.add(new Field("filename", article.getFileName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("dirname", article.getDirName(), Field.Store.YES, Field.Index.NOT_ANALYZED));

        return doc;
    }

    public DBDocument() {

    }
}

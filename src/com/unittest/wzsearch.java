package com.unittest;

import com.bizwink.cms.util.StringUtil;
import com.bizwink.util.POIUtil;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 17-9-19.
 */
public class wzsearch {
    public static Document Document(String company,String products){
        Document doc = new Document();

        if (company != null) {
            try {
                doc.add(new Field("company", company, TextField.Store.YES,TextField.Index.ANALYZED));
            } catch(Exception e) { }
        }

        if(products != null){
            try{
                doc.add(new Field("products", products, Field.Store.YES, Field.Index.ANALYZED));
            }catch(Exception e){
            }
        }

        return doc;
    }


    public static void writeIndexFromExcel(String excelFilename) {
        IndexWriter writer=null; // new index being built
        Sheet aSheet = null;
        try {
            try {
                String filepath = "C:\\cms\\indexer\\awzindex";
                File file = new File(filepath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                /*
                //lucene5.5以上版本打开索引文件的方式
                //Path path = Paths.get("C:/", "Xmp");
                //Path path = Paths.get("C:/Xmp");
                URI u = URI.create("file:///C:/cms/indexer/awzindex");
                Path path = Paths.get(u);
                Directory directory = new SimpleFSDirectory(path);
                */

                /*
                //lucene5.5以上版本定义indexwriter的方式
                //Analyzer analyzer = new SmartChineseAnalyzer(cas);
                Analyzer analyzer = new IKAnalyzer();
                writer = new IndexWriter(directory, new IndexWriterConfig(analyzer));
                // 删除所有document
                writer.deleteAll();
                */

                //lucene5.5以下版本打开索引文件的方式
                Configuration cfg = DefaultConfig.getInstance();
                System.out.println("主词库：" +cfg.getMainDictionary());
                List wordlib = cfg.getExtDictionarys();
                for(int ii=0; ii<wordlib.size(); ii++) {
                    System.out.println((String)wordlib.get(ii));
                }

                Directory directory = new SimpleFSDirectory(new File(filepath));
                // 加入系统默认停用词
                CharArraySet cas = new CharArraySet(Version.LUCENE_45,0,true);
                Iterator<Object> itor = SmartChineseAnalyzer.getDefaultStopSet().iterator();
                while (itor.hasNext()) {
                    cas.add(itor.next());
                }

                if (file.exists()) {
                    if (IndexWriter.isLocked(directory)) {
                        IndexWriter.unlock(directory);
                    }
                    IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_45,new IKAnalyzer(true));
                    writer =new IndexWriter(directory,config);
                } else {
                    file.mkdirs();
                    IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_45,new IKAnalyzer(true));
                    writer =new IndexWriter(directory,config);
                }

                //writer.deleteAll();
            } catch (Exception ioexp) {
                ioexp.printStackTrace();
            }

            Workbook workbook = POIUtil.getWeebWork(excelFilename);
            int sheet_num = workbook.getNumberOfSheets();
            for(int jj=0; jj<sheet_num; jj++) {
                aSheet = workbook.getSheetAt(jj);
                if (aSheet != null) {
                    for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                        Row aRow = aSheet.getRow(rowNumOfSheet);
                        String company = null;
                        String products = null;
                        for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
                            String buf = "";
                            if (null != aRow.getCell(cellNumOfRow)) {
                                Cell aCell = aRow.getCell(cellNumOfRow);
                                if (aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                    buf = aCell.getStringCellValue();
                                    buf = buf.trim();
                                } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                                    buf = String.valueOf((long)aCell.getNumericCellValue());

                                if (cellNumOfRow == 1){
                                    company = buf;
                                }

                                if (cellNumOfRow == 2 && !buf.equalsIgnoreCase("--")) {
                                    products = buf;
                                }
                            }
                        }

                        //       System.out.println(company + "==" + products);

                        writer.addDocument(Document(company, products));
                    }
                }
            }
            try {
                writer.close();
            }catch (IOException exp) {

            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    public static List getDocs(String searchstr, int start, int end) {
        String indexPath = "C:\\cms\\indexer\\awzindex";
        List artList = new ArrayList();
        int hitcount = 0;

        try {
            /*
            //lucene5.5以上版本打开读索引文件的方式
            Path path = Paths.get(indexPath);
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
            IndexSearcher searcher = new IndexSearcher(indexReader);
            */

            Configuration cfg = DefaultConfig.getInstance();
            System.out.println("主词库：" +cfg.getMainDictionary());
            List wordlib = cfg.getExtDictionarys();
            for(int ii=0; ii<wordlib.size(); ii++) {
                System.out.println((String)wordlib.get(ii));
            }

            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
            IndexSearcher searcher = new IndexSearcher(indexReader);

            // 加入系统默认停用词
            CharArraySet cas = new CharArraySet(Version.LUCENE_45,0,true);
            Iterator<Object> itor = SmartChineseAnalyzer.getDefaultStopSet().iterator();
            while (itor.hasNext()) {
                cas.add(itor.next());
            }

            Analyzer analyzer = new IKAnalyzer(true);
            if (searchstr.length() != -1) {
                searchstr = StringUtil.replace(searchstr, ";", " OR ");
                String[] fields = {"company", "products"};
                TokenStream stream = analyzer.tokenStream("field", new StringReader(searchstr));
                //保存分词后的结果词汇
                CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
                stream.reset(); //这句很重要
                while(stream.incrementToken()) {
                    System.out.println(cta.toString());
                }
                stream.end(); //这句很重要
                stream.close();

                MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields,analyzer);
                mfq.setPhraseSlop(1);
                Query q = mfq.parse(searchstr);

                TopDocs docs = searcher.search(q,null, 10000);
                ScoreDoc[] scoreDocs=docs.scoreDocs;

                int resultCount=scoreDocs.length;
                hitcount = resultCount;
                Document doc=null;
                int docId;
                for(int i=0;i<resultCount;i++){
                    //找到这个document原来的索引值
                    docId=scoreDocs[i].doc;
                    //根据这个值找到对象的document
                    doc=searcher.doc(docId);
                    System.out.println(doc.getField("company").stringValue() + "===" + doc.getField("products").stringValue());
                }
            }
            searcher = null;
        }
        catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }

        return artList;
    }

    public static void ik_CAnalyzer(String str) throws IOException{
        //      String text = "对焊法兰";
        Configuration configuration = DefaultConfig.getInstance();
        configuration.setUseSmart(true);
        IKSegmenter ik = new IKSegmenter(new StringReader(str), configuration);
        Lexeme lexeme = null;
        while ((lexeme = ik.next()) != null) {
            System.out.println(lexeme.getLexemeText());
        }
    }

/*    public static void CJK_Analyzer(String str) throws Exception{
        Analyzer analyzer = new CJKAnalyzer();
        Reader r = new StringReader(str);
        StopFilter sf = (StopFilter) analyzer.tokenStream("", r);
        System.out.println("=====CJKAnalyzer====");
        System.out.println("分析方法:交叉双字分割（二元分词）");
        Token t;
        while ((t = sf.next()) != null) {
            System.out.println(t.termText());
        }
    }
*/

    public static List<String> readTemplateHeadinfoFromExcel(String excelFilename) {
        List<String>  tt = new ArrayList();
        try{
            //读取表头配置的XML文件
            List<String> headNameInfo = new ArrayList();
            try {
                File f = new File("C:\\projects\\webbuilder\\webapps\\cms\\sites\\www.abc.com\\TemplateHead.xml");
                SAXReader reader = new SAXReader();
                org.dom4j.Document doc = reader.read(f);
                org.dom4j.Element root = doc.getRootElement();
                org.dom4j.Element foo = null,myfoo=null;
                for (Iterator i = root.elementIterator("columns"); i.hasNext();) {
                    foo = (Element) i.next();
                    for(Iterator j = foo.elementIterator();j.hasNext();) {
                        myfoo = (Element) j.next();
                        headNameInfo.add(myfoo.getText());
                        //System.out.print(myfoo.getName() + "==" + myfoo.attributeValue("name") + "===" + myfoo.getText());  //.elementText("column"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Workbook workbook = POIUtil.getWeebWork(excelFilename);
            int sheet_num = workbook.getNumberOfSheets();
            Sheet aSheet = null;
            int productcolumn = 0;
            List<String> wzname=new ArrayList();
            for(int jj=0; jj<sheet_num; jj++) {
                aSheet = workbook.getSheetAt(jj);
                if (aSheet != null) {
                    for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                        Row aRow = aSheet.getRow(rowNumOfSheet);
                        for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
                            String buf = "";
                            if (null != aRow.getCell(cellNumOfRow)) {
                                Cell aCell = aRow.getCell(cellNumOfRow);
                                if (aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                    buf = aCell.getStringCellValue();
                                    buf = buf.trim();
                                } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                                    buf = String.valueOf((long)aCell.getNumericCellValue());

                                //确定那一列是保存的物资名称的列
                                if (productcolumn == 0) {
                                    if ("材料名称".equalsIgnoreCase(buf.trim())) productcolumn = cellNumOfRow;
                                }

                                if (productcolumn>0) {
                                    if (cellNumOfRow == productcolumn) {
                                        wzname.add(buf.trim());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch(IOException exp) {

        }

        return tt;
    }

    public static void main(String[] args) {
        //writeIndexFromExcel("C:\\data\\11.xlsx");
        try {
            ik_CAnalyzer("这个代码主要是对传入的参数进行分词,并统");
        } catch (Exception exp) {

        }

        readTemplateHeadinfoFromExcel("C:\\Document\\长城产品\\客户资料\\鑫泰石化\\鑫泰石化询价单8.16(人孔，透光孔）.xls");
        List aa = getDocs("泥浆泵",0,100);
    }
}
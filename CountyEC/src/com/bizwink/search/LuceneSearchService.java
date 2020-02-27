package com.bizwink.search;

import com.bizwink.cms.SearchResults;
import com.bizwink.po.Companyinfo;
import com.bizwink.util.SpringInit;
import com.bizwink.util.StringUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 18-4-14.
 */
public class LuceneSearchService {
    private static String indexPath=null;
    //private static String indexPath="C:\\cms\\indexer\\111";
    //private static String indexPath="/data/indexer/gugulx/";

    public static void main(String[] args) {
        String searchstr= "吉顺源";
        if (args.length==2) {
            indexPath = args[0];
            searchstr = args[1];
        }
        System.out.println(indexPath);
        System.out.println(searchstr);
        getSearchResultByClassid(2991, null, searchstr);
    }

    public  static void getSearchResult(String searchstr) {
        try {
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
            IndexSearcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();
            if (searchstr.length() != -1) {
                searchstr = StringUtil.replace(searchstr, ";", " OR ");
                String[] fields = {"companyname", "companyaddress","summary","content","zhprovince","zhcity","zhzone","zhtown","zhvillage","classid"};
                MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields, new IKAnalyzer());  //lucene 2.4
                mfq.setPhraseSlop(1);
                Query q = mfq.parse(searchstr);


                //SortField sf = new SortField("publishtime", SortField.STRING, true);
                //Sort sort = new Sort(sf);
                //Hits hits = searcher.search(q, sort);
                TopDocs docs = searcher.search(q,null, 10000);
                ScoreDoc[] scoreDocs=docs.scoreDocs;

                int resultCount=scoreDocs.length;
                Document doc=null;
                int docId;
                for(int i=0;i<resultCount;i++){
                    //找到这个document原来的索引值
                    docId=scoreDocs[i].doc;
                    //根据这个值找到对象的document
                    doc=searcher.doc(docId);
                    System.out.println(doc.getField("companyname") + doc.get("createdate") + doc.get("zhprovince") + "==" + doc.get("zhcity") + "==" + doc.get("zhzone")+"==" + doc.get("zhtown") + "==" +doc.get("zhvillage"));
                }
            }
            searcher = null;
        } catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }
    }

    public static List<SearchResults> getSearchResultByClassid(int infotype,String zoneid,String keyword) {
        List<SearchResults> searchResultsList = new ArrayList<SearchResults>();
        //if (indexPath == null)
        indexPath = SpringInit.getIndexPath();
        System.out.println("indexpath==" + indexPath);

        try {
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
            IndexSearcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();

            String[] fields = {"companyname", "companyaddress","summary","content"};
            MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields, new IKAnalyzer());  //lucene 2.4
            mfq.setPhraseSlop(1);
            Query q_keyword = mfq.parse(keyword);
            BooleanQuery contentAndArticleClass = new BooleanQuery();
            if (zoneid!=null) {
                //定义查找信息的区域
                String[] zones = {"zone"};
                MultiFieldQueryParser mfq_zone = new MultiFieldQueryParser(Version.LUCENE_45,zones, new IKAnalyzer());  //lucene 2.4
                mfq_zone.setPhraseSlop(1);
                Query q_zone = mfq_zone.parse(zoneid);

                //定义按信息类型进行检索，2991-农家院，2992-采摘园，4592--美丽乡村
                Query q_infotype = NumericRangeQuery.newIntRange("classid", infotype, infotype, true, true);

                contentAndArticleClass.add(q_keyword,BooleanClause.Occur.MUST);
                contentAndArticleClass.add(q_zone,BooleanClause.Occur.MUST);
                contentAndArticleClass.add(q_infotype,BooleanClause.Occur.MUST);
            } else {
                //定义按信息类型进行检索，2991-农家院，2992-采摘园，4592--美丽乡村
                Query q_infotype = NumericRangeQuery.newIntRange("classid", infotype, infotype, true, true);

                contentAndArticleClass.add(q_keyword,BooleanClause.Occur.MUST);
                contentAndArticleClass.add(q_infotype,BooleanClause.Occur.MUST);
            }

            TopDocs docs = searcher.search(contentAndArticleClass,null, 10000);
            ScoreDoc[] scoreDocs=docs.scoreDocs;

            /**************关键字高亮显示****************/
            Formatter formatter =  new SimpleHTMLFormatter("<font color=\"red\">", "</font>");   //前缀和后缀
            Scorer scorer = new QueryScorer(contentAndArticleClass);
            Highlighter highlighter = new Highlighter(formatter, scorer);
            highlighter.setTextFragmenter(new SimpleFragmenter(200)); //字长度

            int resultCount=scoreDocs.length;
            Document doc=null;
            int docId;
            SearchResults searchResult = null;
            for(int i=0;i<resultCount;i++){
                //找到这个document原来的索引值
                docId=scoreDocs[i].doc;
                //根据这个值找到对象的document
                doc=searcher.doc(docId);
                searchResult = new SearchResults();
                searchResult.setCOMPANYNAME(doc.get("companyname"));
                searchResult.setSUMMARY(doc.get("summary"));
                searchResult.setCOMPANYPIC(doc.get("pic"));
                searchResult.setPROVINCE(doc.get("province"));
                searchResult.setZHPROVINCE(doc.get("zhprovince"));
                searchResult.setCITY(doc.get("city"));
                searchResult.setZHCITY(doc.get("zhcity"));
                searchResult.setZONE(doc.get("zone"));
                searchResult.setZHZONE(doc.get("zhzone"));
                searchResult.setTOWN(doc.get("town"));
                searchResult.setZHTOWN(doc.get("zhtown"));
                searchResult.setVILLAGE(doc.get("village"));
                searchResult.setZHVILLAGE(doc.get("zhvillage"));
                searchResult.setSITEID(BigDecimal.valueOf(doc.getField("siteid").numericValue().intValue()));
                searchResultsList.add(searchResult);
                System.out.println(doc.getField("companyname") + doc.get("createdate") + doc.get("zhprovince") + "==" + doc.get("zhcity") + "==" + doc.get("zhzone")+"==" + doc.get("zhtown") + "==" +doc.get("zhvillage"));
            }
        } catch (Exception exp) {

        }

        return searchResultsList;
    }
}

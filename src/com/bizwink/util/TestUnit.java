package com.bizwink.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jackzhang
 * Date: 13-10-15
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public class TestUnit {
    public static void main(String[] argv) {
       //Map resultList = LuceneSearchService.searchContent("龙淼",0,10,"",null);
       //Map resultList = LuceneSearchService.searchByField("柳浪",0,10,"","","content");
        Map map=new HashMap();
        map.put("summary", "采摘");
        map.put("content", "果园");

     /*   Map resultList = LuceneSearchService.searchMoreKeyField(0,10,"",map);
        List<Article> articleList = (List<Article>) resultList.get("searcheResult");
        Article article;
        if  (articleList!=null &&(articleList.size()>0)){
            for(int i=0; i<articleList.size(); i++)   {
                Article doc = (Article) articleList.get(i);
                String maintitle = doc.getMaintitle();
                System.out.println(maintitle);
                int id = doc.getId();
                String dirname = doc.getDirname();
                String summary = doc.getSummary();
                System.out.println(summary);
                String createdate = CommUtil.timestamp2string(doc.getCreatedate(),"yyyyMMdd")   ;
                String publishtime =  CommUtil.timestamp2string(doc.getPublishtime(),"yyyy-MM-dd")   ;

                String filename = doc.getFilename();
                String url = "";
                if ((filename != null) && (!filename.equals("")) && (filename.indexOf(".html") == -1) && (filename.indexOf(".shtml") == -1))
                    url =   dirname + createdate + "/download/" + filename;
                else
                    url =  dirname + createdate + "/" + id + ".shtml";
                System.out.println(url);

            }
            System.out.println("count is  " +  resultList.get("resultCount"));
        }
*/
    }
}

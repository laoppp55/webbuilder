package com.bizwink.service;

import com.bizwink.indexer.DBDocument;
import com.bizwink.persistence.ArticleMapper;
import com.bizwink.po.Article;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jackzhang
 * Date: 13-10-14
 * Time: 下午7:29
 * To change this template use File | Settings | File Templates.
 */
@Service
public class LuceneIndexService {
    @Autowired
    private ArticleMapper articleMapper;
    public static Logger logger = Logger.getLogger(LuceneIndexService.class.getName());

    public void createIndex(){
        //int siteid =SearchConfig.getInstance().getSiteidConfig();
        //createIndex(SearchConfig.getInstance().getIndexpathConfig(),siteid);
        //用于测试索引生成
        //createIndex(SearchConfig.getInstance().getTestIndexpathConfig(),siteid);
    }

    public void createIndex(String indexPath,int siteid) {
        Date start = new Date();
        logger.debug("create index begin");

        IndexWriter writer =null;
        try {
            try {
                File file = new File(indexPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                // 配置索引
                //Analyzer analyzer = new SmartChineseAnalyzer();
                Analyzer analyzer = new IKAnalyzer();
                Directory directory = new SimpleFSDirectory(new File(indexPath));
                writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_45,analyzer));
                // 删除所有document
                //writer.deleteAll();
            } catch (IOException ioexp) {
            }
            indexDoc(writer, siteid);
            logger.debug("index end");
            writer.commit();
            Date end = new Date();
            logger.debug(end.getTime() - start.getTime() + " total milliseconds");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }
    }

    void  indexDoc(IndexWriter writer,int siteid) {
        List<Article> articleList =articleMapper.getNeedIndex(siteid);
        int looptime=0;
        String artcileids ="";
        while (articleList !=null &&(articleList.size()>0) ){
            artcileids = indexResult(writer,siteid,articleList);
            articleMapper.updateIdxFlagByids(artcileids);
            looptime++;
            if (looptime <4 ){
                articleList =articleMapper.getNeedIndex(siteid);
            }else{
                articleList =null;
            }
        }
    }

    String  indexResult(IndexWriter writer,int siteid,List artlist ) {
        String artcileids ="";
        try {
            if (artlist !=null && (artlist.size() > 0)) {
                Article article=null;
                Document doc =null;
                for(int i=0; i<artlist.size(); i++) {
                    article = (Article)artlist.get(i);

                    if(artcileids ==""){
                        artcileids += article.getID();
                    }else{
                        artcileids += ","+article.getID();
                    }

                    //将该文章所在栏目及该栏目的所有父节点的id取出来拼成逗号字符串
                    String articlePathinfo = getArticlePathInfo(articleMapper.getColumnParents(article.getCOLUMNID().intValue()));
                    String dirname = articleMapper.getColumnDirname(article.getCOLUMNID().intValue());
                    if (dirname != null) {
                        article.setDIRNAME(dirname);
                        System.out.println(article.getMAINTITLE() + ":被索引");
                        doc  = DBDocument.Document(siteid,articlePathinfo,article);
                        writer.addDocument(doc);
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return artcileids;
    }

    private String getArticlePathInfo(String columnids) {

        return null;
    }
}

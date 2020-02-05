package com.bizwink.indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.net.URI;
//import java.nio.file.Path;
//import java.nio.file.Paths;

public class DeleteDocument extends Thread {
    private IndexReader reader;
    private String indexPath;
    private String delFlag;

    public DeleteDocument(IndexReader reader) {
        this.reader = reader;
    }

    public DeleteDocument(String indexPath) {
        this.indexPath = indexPath;
    }

    public DeleteDocument(String indexPath, int articleid) {
        this.indexPath = indexPath;
        this.delFlag = "shtml" + articleid;
    }

    public DeleteDocument() {

    }

    public void run() {
        try {
            // 配置索引
            //Path path = Paths.get(new URI(indexPath));
            //Directory directory = new SimpleFSDirectory(path);
            //Analyzer analyzer = new SmartChineseAnalyzer();
            Analyzer analyzer = new IKAnalyzer();
            Directory directory = new SimpleFSDirectory(new File(indexPath));
            IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_45,analyzer));

            Term term = new Term("DELID", delFlag);
            writer.deleteDocuments(term);           //for lucene-1.4
            //indexReader.deleteDocuments(term);    //for lucene-2.0

            writer.close();
            System.out.println("被删除的文件中包含如下信息：" + term);

            directory.close();
        } catch (Exception e) {
            System.out.println("捕获如下类别错误：" + e.getClass() +
                    "，包括错误信息---: " + e.getMessage());
        }
    }
}

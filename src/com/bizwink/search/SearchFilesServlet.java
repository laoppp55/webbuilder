package com.bizwink.search;

import java.net.URI;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;
import com.bizwink.cms.news.Article;
import com.bizwink.util.StringUtil;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchFilesServlet {
    private String filename = "com/bizwink/cms/server/config.properties";

    public SearchFilesServlet() {
    }

    public List getRelatedArticles(String searchstr, int selectNum, String cIds) {
        List artList = new ArrayList();
        try
        {
            //Path path = Paths.get(getProperties("main.indexPath"));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(getProperties("main.indexPath"))));
            IndexSearcher searcher = new IndexSearcher(indexReader);

            Analyzer analyzer = new IKAnalyzer();
            if (searchstr.length() != -1) {
                String[] searchcolumn = new String[4];
                searchcolumn[0] = "maintitle";
                searchcolumn[1] = "keyword";
                searchcolumn[2] = "content";
                searchcolumn[3] = "summary";

                searchstr = searchstr.replaceAll(";", " OR ");
                //Query query = MultiFieldQueryParser.parse(searchstr, searchcolumn, analyzer);
                MultiFieldQueryParser query = new MultiFieldQueryParser(Version.LUCENE_45,searchcolumn, new IKAnalyzer());  //lucene 2.4
                query.setPhraseSlop(1);
                Query q = query.parse(searchstr);

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
                    artList.add(doc);
                }
            }
            searcher = null;
        } catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf(new StringBuffer(" caught a ").append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }

        return artList;
    }

    private Article loadforcafte(Document doc) {
        Article article = new Article();
        try
        {
            article.setID(Integer.parseInt(doc.get("articleid")));
            article.setMainTitle(doc.get("maintitle"));
            article.setViceTitle(doc.get("vicetitle"));
            article.setSummary(doc.get("summary"));
            article.setKeyword(doc.get("keyword"));
            article.setAuthor(doc.get("author"));
            article.setColumnID(Integer.parseInt(doc.get("columnid")));
            article.setSortID(Integer.parseInt(doc.get("sortid")));
            article.setDirName(doc.get("dirname"));
            article.setFileName(doc.get("filename"));
            article.setPublishTime(new Timestamp(Long.parseLong(doc.get("publishtime"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    //用于全库寻找相关文章
    public List getRelatedArticles(String searchstr, int selectNum, String Ids, int articleId) {
        List artList = new ArrayList();
        try {
            //Path path = Paths.get(getProperties("main.indexPath"));
            //IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(getProperties("main.indexPath"))));
            IndexSearcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();
            if (searchstr.length() != -1) {
                searchstr = StringUtil.replace(searchstr,";"," OR ");
                String[] fields = {"maintitle", "content","keyword","summary"};
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
                    artList.add(doc);
                }
            }
            searcher = null;
        } catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }
        return artList;
    }

    //用于本站点寻找相关文章
    public List getRelatedArticles(String searchstr, int selectNum, String Ids, int articleId,int siteid) {
        List artList = new ArrayList();
        try {
            //Path path = Paths.get(getProperties("main.indexPath"));
            //IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(getProperties("main.indexPath"))));
            IndexSearcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();
            if (searchstr.length() != -1) {
                searchstr = StringUtil.replace(searchstr,";"," OR ");
                String[] fields = {"maintitle", "content","keyword","summary"};
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
                    artList.add(doc);
                }
            }
            searcher = null;
        } catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }
        return artList;
    }

    //用于本站点寻找相关文章
    public List getRelatedArticles(String searchstr,String title, int selectNum, String Ids, int articleId,String sitename) {
        List<Article> artList = new ArrayList();
        try {
            String indexPath = null;
            indexPath = getProperties("main.indexPath");
            if (indexPath.endsWith(File.separator))
                indexPath = indexPath + sitename;
            else
                indexPath = indexPath + File.separator + sitename;

            //Path path = Paths.get(indexPath);
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
            //IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(getProperties("main.indexPath"))));
            IndexSearcher searcher = new IndexSearcher(indexReader);

            if (searchstr!=null) {
                searchstr = StringUtil.replace(searchstr,";"," OR ");
                String[] fields = {"maintitle", "content","keyword","summary"};
                MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields, new IKAnalyzer());  //lucene 2.4
                mfq.setPhraseSlop(1);
                Query q = mfq.parse(searchstr);

                //定义搜索分类
                String[] articleclass = {"classid"};
                MultiFieldQueryParser ac = new MultiFieldQueryParser(Version.LUCENE_45,articleclass, new IKAnalyzer());  //lucene 2.4
                Query acq = ac.parse("c*");

                BooleanQuery contentAndArticleClass = new BooleanQuery();
                contentAndArticleClass.add(q,BooleanClause.Occur.MUST);
                contentAndArticleClass.add(acq,BooleanClause.Occur.MUST);

                TopDocs docs = searcher.search(q,null, 10000);
                ScoreDoc[] scoreDocs=docs.scoreDocs;

                int resultCount=scoreDocs.length;
                Document doc=null;
                int docId;
                Article article = null;
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                int count = 0;
                for(int i=0;i<resultCount && count<selectNum;i++){
                    //找到这个document原来的索引值
                    docId=scoreDocs[i].doc;
                    article = new Article();
                    //根据这个值找到对象的document
                    doc=searcher.doc(docId);
                    article.setID(Integer.parseInt(doc.get("id")));

                    article.setMainTitle(doc.get("maintitle"));
                    article.setDirName(doc.get("dirname"));
                    article.setProductBigPic(doc.get("bigpic"));
                    article.setProductPic(doc.get("pic"));
                    article.setArticlepic(doc.get("articlepic"));
                    article.setSiteName(doc.get("sitename"));
                    article.setSummary(doc.get("summary"));
                    article.setKeyword(doc.get("keyword"));
                    article.setFileName(doc.get("filename"));
                    article.setColumnID(Integer.parseInt(doc.get("columnid")));
                    if (doc.get("createdate")!=null)
                        article.setCreateDate(new Timestamp(sf.parse(doc.get("createdate")).getTime()));
                    if (doc.get("publishtime") != null)
                        article.setPublishTime(new Timestamp(sf.parse(doc.get("publishtime")).getTime()));
                    if (doc.get("lastupdated")!=null)
                        article.setLastUpdated(new Timestamp(sf.parse(doc.get("lastupdated")).getTime()));
                    artList.add(article);
                    count = count + 1;
                }
            }
            searcher = null;
        } catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }

        return artList;
    }


    //用于索引的测试
    public List getArticles(String searchstr,String sitename,String artclass) {
        List artList = new ArrayList();
        try {
            //Path path = Paths.get(getProperties("main.indexPath"));
            //IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(getProperties("main.indexPath"))));
            IndexSearcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();
            if (searchstr.length() != -1) {
                searchstr = StringUtil.replace(searchstr,";"," OR ");

                //定义搜索内容
                String[] fields = {"maintitle", "content","keyword","summary"};
                MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields, new IKAnalyzer());  //lucene 2.4
                mfq.setPhraseSlop(1);
                Query q = mfq.parse(searchstr);

                //定义搜索分类
                String[] articleclass = {"classid"};
                MultiFieldQueryParser ac = new MultiFieldQueryParser(Version.LUCENE_45,articleclass, new IKAnalyzer());  //lucene 2.4
                Query acq = ac.parse(artclass);

                BooleanQuery contentAndArticleClass = new BooleanQuery();
                contentAndArticleClass.add(q,BooleanClause.Occur.MUST);
                contentAndArticleClass.add(acq,BooleanClause.Occur.MUST);
                TopDocs docs = searcher.search(contentAndArticleClass,null, 10000);
                ScoreDoc[] scoreDocs=docs.scoreDocs;

                int resultCount=scoreDocs.length;
                Document doc=null;
                int docId;
                for(int i=0;i<resultCount;i++){
                    //找到这个document原来的索引值
                    docId=scoreDocs[i].doc;
                    //根据这个值找到对象的document
                    doc=searcher.doc(docId);
                    artList.add(doc);
                }
            }
            searcher = null;
        } catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }
        return artList;
    }

    //用于页面信息检索
    public List getArticles(String searchstr, int start, int end) {
        List artList = new ArrayList();
        try {
            //Path path = Paths.get(getProperties("main.indexPath"));
            //IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(getProperties("main.indexPath"))));
            IndexSearcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();
            if (searchstr.length() != -1) {
                searchstr = StringUtil.replace(searchstr,";"," OR ");
                String[] fields = {"maintitle", "content","keyword","summary"};
                MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields, new IKAnalyzer());  //lucene 2.4
                mfq.setPhraseSlop(1);
                Query q = mfq.parse(searchstr);
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
                    artList.add(doc);
                }
            }
            searcher = null;
        } catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }
        return artList;
    }

    //为页面信息检索返回总的命中文章的数量
    public int getArticlesNum(String searchstr) {
        int hitcount = 0;
        try {
            //Path path = Paths.get(getProperties("main.indexPath"));
            //IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(getProperties("main.indexPath"))));
            IndexSearcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();
            if (searchstr.length() != -1) {
                searchstr = StringUtil.replace(searchstr,";"," OR ");
                String[] fields = {"maintitle", "content","keyword","summary"};
                MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields, new IKAnalyzer());  //lucene 2.4
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
                }
            }
            searcher = null;
        }
        catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }
        return hitcount;
    }

    //用于某个站点页面信息检索
    public List getArticles(String sitename,String searchstr, int start, int end) {
        List artList = new ArrayList();
        try {
            String indexPath = null;
            //URLEncoder.encode("北京","utf-8")
            indexPath = getProperties("main.indexPath");
            if (indexPath.endsWith(File.separator))
                indexPath = indexPath + sitename;
            else
                indexPath = indexPath + File.separator + sitename;
            //Path path = Paths.get(indexPath);
            //IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));

            IndexSearcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();
            if (searchstr.length() != -1) {
                searchstr = StringUtil.replace(searchstr,";"," OR ");
                String[] fields = {"maintitle", "content","keyword","summary"};
                MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields, new IKAnalyzer());  //lucene 2.4
                mfq.setPhraseSlop(1);
                Query q = mfq.parse(searchstr);
                TopDocs docs = searcher.search(q,null, 10000);
                ScoreDoc[] scoreDocs=docs.scoreDocs;

                int resultCount=scoreDocs.length;
                Document doc=null;
                int docId;
                for(int i=start;i<end;i++){
                    //找到这个document原来的索引值
                    if (i<resultCount) {
                        docId = scoreDocs[i].doc;
                        //根据这个值找到对象的document
                        doc = searcher.doc(docId);
                        artList.add(doc);
                    } else {
                        break;
                    }
                }
            }
            searcher = null;
        } catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }
        return artList;
    }

    //为某个站点页面信息检索返回总的命中文章的数量
    public int getArticlesNum(String sitename,String searchstr) {
        int hitcount = 0;
        try {
            String indexPath = null;
            indexPath = getProperties("main.indexPath");
            if (indexPath.endsWith(File.separator))
                indexPath = indexPath + sitename;
            else
                indexPath = indexPath + File.separator + sitename;

            //Path path = Paths.get(indexPath);
            //IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
            IndexSearcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();
            if (searchstr.length() != -1) {
                searchstr = StringUtil.replace(searchstr,";"," OR ");
                String[] fields = {"maintitle", "content","keyword","summary"};
                MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields, new IKAnalyzer());  //lucene 2.4
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
                }
            }
            searcher = null;
        }
        catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }
        return hitcount;
    }

    //用于某个站点页面信息检索，按分类检索信息
    public List getArticles(String sitename,String searchstr,String artclass,int start, int end) {
        List artList = new ArrayList();
        try {
            String indexPath = null;
            indexPath = getProperties("main.indexPath");
            if (indexPath.endsWith(File.separator))
                indexPath = indexPath + sitename;
            else
                indexPath = indexPath + File.separator + sitename;
            //Path path = Paths.get(indexPath);
            //IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(getProperties("main.indexPath"))));
            IndexSearcher searcher = new IndexSearcher(indexReader);

            if (searchstr != null ) {
                if (!searchstr.isEmpty()) {
                    searchstr = StringUtil.replace(searchstr,";"," OR ");

                    //定义搜索内容
                    String[] fields = {"maintitle", "content","keyword","summary"};
                    MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields, new IKAnalyzer());  //lucene 2.4
                    mfq.setPhraseSlop(1);
                    Query q = mfq.parse(searchstr);

                    //定义搜索分类
                    String[] articleclass = {"classid"};
                    MultiFieldQueryParser ac = new MultiFieldQueryParser(Version.LUCENE_45,articleclass, new IKAnalyzer());  //lucene 2.4
                    Query acq = ac.parse(artclass);

                    BooleanQuery contentAndArticleClass = new BooleanQuery();
                    contentAndArticleClass.add(q,BooleanClause.Occur.MUST);
                    contentAndArticleClass.add(acq,BooleanClause.Occur.MUST);

                    TopDocs docs = searcher.search(contentAndArticleClass,null, 10000);
                    ScoreDoc[] scoreDocs=docs.scoreDocs;

                    int resultCount=scoreDocs.length;
                    Document doc=null;
                    int docId;
                    for(int i=0;i<resultCount;i++){
                        //找到这个document原来的索引值
                        docId=scoreDocs[i].doc;
                        //根据这个值找到对象的document
                        doc=searcher.doc(docId);
                        artList.add(doc);
                    }
                }
                searcher = null;
            }
        } catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }

        return artList;
    }

    //为某个站点页面信息检索返回总的命中文章的数量，按分类检索信息
    public int getArticlesNum(String sitename,String searchstr,String artclass) {
        int hitcount = 0;
        try {
            String indexPath = null;
            indexPath = getProperties("main.indexPath");
            if (indexPath.endsWith(File.separator))
                indexPath = indexPath + sitename;
            else
                indexPath = indexPath + File.separator + sitename;
            //Path path = Paths.get(indexPath);
            //IndexReader indexReader = DirectoryReader.open(FSDirectory.open(path));
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(getProperties("main.indexPath"))));
            IndexSearcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new IKAnalyzer();
            if (searchstr.length() != -1) {
                searchstr = StringUtil.replace(searchstr,";"," OR ");

                //定义搜索内容
                String[] fields = {"maintitle", "content","keyword","summary"};
                MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_45,fields, new IKAnalyzer());  //lucene 2.4
                mfq.setPhraseSlop(1);
                Query q = mfq.parse(searchstr);

                //定义搜索分类
                String[] articleclass = {"classid"};
                MultiFieldQueryParser ac = new MultiFieldQueryParser(Version.LUCENE_45,articleclass, new IKAnalyzer());  //lucene 2.4
                Query acq = ac.parse(artclass);

                BooleanQuery contentAndArticleClass = new BooleanQuery();
                contentAndArticleClass.add(q,BooleanClause.Occur.MUST);
                contentAndArticleClass.add(acq,BooleanClause.Occur.MUST);

                TopDocs docs = searcher.search(contentAndArticleClass,null, 10000);
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
                }
            }
            searcher = null;
        } catch (Exception e) {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer(" caught a ")).append(e.getClass()).append("\n with message: ").append(e.getMessage()))));
        }
        return hitcount;
    }

    private Article load(Document doc) {
        Article article = new Article();

        try {
            article.setID(Integer.parseInt(doc.get("id")));
            if (doc.get("siteid") != null)
                article.setSiteID(Integer.parseInt(doc.get("siteid")));
            else
                article.setSiteID(0);
            article.setSiteName(doc.get("sitename"));
            article.setMainTitle(doc.get("maintitle"));
            article.setViceTitle(doc.get("vicetitle"));
            article.setSummary(doc.get("summary"));
            article.setKeyword(doc.get("keyword"));
            article.setAuthor(doc.get("author"));
            article.setColumnID(Integer.parseInt(doc.get("columnid")));
            article.setSortID(Integer.parseInt(doc.get("sortid")));
            article.setDirName(doc.get("dirname"));
            article.setFileName(doc.get("filename"));
            article.setArticleclass(doc.get("classid"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            article.setPublishTime(new Timestamp(formatter.parse(doc.get("publishtime")).getTime()));
            article.setCreateDate(new Timestamp(formatter.parse(doc.get("createdate")).getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    public String getProperties(String name) {
        String value = "";
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
            Properties props = new Properties();
            props.load(in);
            in.close();

            value = props.getProperty(name);
            if (value.length() > 0) {
                if (name.equalsIgnoreCase("indexPath")) {
                    if (!value.substring(value.length() - 1).equals(File.separator))
                        value = value + File.separator;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
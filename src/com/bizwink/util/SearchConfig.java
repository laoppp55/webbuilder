package com.bizwink.util;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: jackzhang
 * Date: 13-10-15
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
public class SearchConfig {
    private static String filename = "searchconfig.properties";
    private Properties props;
    private static SearchConfig singleton = new SearchConfig();
    public static Analyzer analyzer = new IKAnalyzer();
     //   Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_45);
    //Analyzer analyzer = new PaodingAnalyzer();

    private SearchConfig(){
    }
    public static synchronized SearchConfig getInstance() {
        singleton.initEnv();
        return singleton;
    }
    void initEnv() {
        try {
            //System.out.println(this.getClass().getResourceAsStream());
            InputStream in = this.getClass().getResourceAsStream(filename);
            this.props = new Properties();
            this.props.load(in);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIndexpathConfig(){
        String indexpath = props.getProperty("indexPath");
        return indexpath;
    }

    public String getTestIndexpathConfig(){
        String indexpath = props.getProperty("testIndexPath");
        return indexpath;
    }

    public  int getSiteidConfig(){
        return Integer.parseInt(props.getProperty("siteid"));
    }

    public int getSiteID() {
        String siteid = this.props.getProperty("siteid");
        if (siteid != null) {
            return Integer.parseInt(siteid);
        }
        return 0;
    }
}

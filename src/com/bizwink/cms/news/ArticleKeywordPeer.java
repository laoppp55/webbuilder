package com.bizwink.cms.news;

import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jdom.input.SAXBuilder;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import com.bizwink.cms.util.XmlUtil;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;

public class ArticleKeywordPeer implements IArticleKeywordManager {

    PoolServer cpool;

    public ArticleKeywordPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IArticleKeywordManager getInstance() {
        return CmsServer.getInstance().getFactory().getArticleKeywordManager();
    }

    //读取所有的列表
    private static String GET_ALL_DEFINE_KEYWORD_LINK = "select * from tbl_article_keyword where siteid = ? and columnid = ?";

    public List getAllListKeyLink(int siteid, int columnid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ArticleKeyword articlekeyword;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_DEFINE_KEYWORD_LINK);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, columnid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                articlekeyword = load(rs);
                list.add(articlekeyword);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public boolean createKeyLinkXML(String sitename, ArticleKeyword articleKeyword, String rootPath, String dirname) {
        boolean success = false;
        FileOutputStream fo = null;

        rootPath = rootPath + "sites" + java.io.File.separator + sitename;
        String dataPath = rootPath + StringUtil.replace(dirname, "/", File.separator);

        String xmlFile = dataPath + "keylink.xml";
        File file = new File(xmlFile);
        if (!file.exists()) {
            XmlUtil.createXMLFile(xmlFile, "keywords");
        }

        SAXBuilder builder = new SAXBuilder();

        try {
            Document doc = builder.build(file);
            List nodeList = doc.getRootElement().getChildren();

            Element e = new Element("keyword");
            e.setAttribute("id", String.valueOf(articleKeyword.getId()));

            Element subElement = new Element("key");
            subElement.setText(StringUtil.iso2gbindb(articleKeyword.getKeyword()));
            e.addContent(subElement);

            subElement = new Element("url");
            subElement.setText(articleKeyword.getUrl());
            e.addContent(subElement);

            subElement = new Element("title");
            subElement.setText(String.valueOf(articleKeyword.getTitle()));
            e.addContent(subElement);

            nodeList.add(e);

            XMLOutputter out = new XMLOutputter();
            fo = new FileOutputStream(xmlFile);
            out.output(doc, fo);
            fo.close();

            success = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }



    private ArticleKeyword load(ResultSet rs){
        ArticleKeyword articlekeyword = new ArticleKeyword();

        try{
            articlekeyword.setId(rs.getInt("id"));
            articlekeyword.setKeyword(rs.getString("keyword"));
            articlekeyword.setUrl(rs.getString("url"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return articlekeyword;
    }
}
